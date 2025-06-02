package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.LinkedEffectIdStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author JayDi85
 */
public class GainAbilityTargetEffect extends ContinuousEffectImpl {

    protected final Ability ability;

    // shall a card gain the ability (otherwise a permanent)
    private final boolean useOnCard; // only one card per ability supported
    private boolean waitingCardPermanent = false; // wait the permanent from card's resolve (for inner usage only)

    public GainAbilityTargetEffect(Ability ability) {
        this(ability, Duration.EndOfTurn);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration) {
        this(ability, duration, null);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule) {
        this(ability, duration, rule, false);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule, boolean useOnCard) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, ability.getEffects().getOutcome(ability, Outcome.AddAbility));
        this.ability = copyAbility(ability); // See the method's comment, ability.copy() is not enough.

        this.staticText = rule;
        this.useOnCard = useOnCard;

        this.generateGainAbilityDependencies(ability, null);
    }

    protected GainAbilityTargetEffect(final GainAbilityTargetEffect effect) {
        super(effect);
        this.ability = copyAbility(effect.ability); // See the method's comment, ability.copy() is not enough.
        this.useOnCard = effect.useOnCard;
        this.waitingCardPermanent = effect.waitingCardPermanent;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            if (getAffectedObjectsSet()
                    && (this.affectedObjectList.isEmpty() || (duration == Duration.Custom && !this.waitingCardPermanent))) {
                this.discard();
            }
            return false;
        }
        for (MageItem object : objects) {
            if(!(object instanceof Card)) {
                continue;
            }
            if (this.useOnCard) {
                game.getState().addOtherAbility((Card) object, ability);
            } else {
                ((Permanent) object).addAbility(ability);
            }
        }
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        // must support dynamic targets from static ability and static targets from activated abilities
        if (getAffectedObjectsSet()) {
            // target permanents (by default)
            getTargetPointer().getTargets(game, source)
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .forEach(permanent -> {
                        this.affectedObjectList.add(new MageObjectReference(permanent, game));
                    });

            // target cards with linked permanents
            if (this.useOnCard) {
                getTargetPointer().getTargets(game, source)
                        .stream()
                        .map(game::getCard)
                        .filter(Objects::nonNull)
                        .forEach(card -> {
                            this.affectedObjectList.add(new MageObjectReference(card, game));
                        });
                waitingCardPermanent = true;
                if (this.affectedObjectList.size() > 1) {
                    throw new IllegalArgumentException("Gain ability can't target a multiple cards (unsupported)");
                }
            }
        }
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        List<MageItem> objects = new ArrayList<>();
        if (getAffectedObjectsSet()) {
            // STATIC TARGETS
            List<MageObjectReference> newWaitingPermanents = new ArrayList<>();
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
                MageObjectReference mor = it.next();

                // look for permanent
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null) {
                    this.waitingCardPermanent = false;
                    objects.add(permanent);
                    continue;
                }

                // look for card with linked permanent
                if (this.useOnCard) {
                    Card card = mor.getCard(game);
                    if (card != null) {
                        objects.add(card);
                        continue;
                    } else {
                        // start waiting a spell's permanent (example: Tyvar Kell's emblem)
                        Permanent perm = game.getPermanent(mor.getSourceId());
                        if (perm != null) {
                            objects.add(perm);
                            newWaitingPermanents.add(new MageObjectReference(perm, game));
                            this.waitingCardPermanent = false;
                        }
                    }
                }
                // bad target, can be removed
                it.remove();
            }

            // add new linked permanents to targets
            if (!newWaitingPermanents.isEmpty()) {
                this.affectedObjectList.addAll(newWaitingPermanents);
            }
        } else {
            // DYNAMIC TARGETS
            for (UUID objectId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(objectId);
                if (permanent != null) {
                    objects.add(permanent);
                    continue;
                }
                if (this.useOnCard) {
                    Card card = game.getCard(objectId);
                    if (card != null) {
                        objects.add(card);
                    }
                }
            }
        }
        return objects;
    }

    @Override
    public GainAbilityTargetEffect copy() {
        return new GainAbilityTargetEffect(this);
    }

    /**
     * Copying the ability and providing ability is needed in a few situations,
     * The copy in order to have internal fields be proper to that ability in particular.
     * Id must be different for the copy, for a few things like the GainAbilityTargetEffect gained
     * by a clone, or in the case of an activated ability, called multiple times on the same target,
     * and thus the ability should be gained multiple times.
     */
    private Ability copyAbility(Ability toCopyAbility) {
        Ability abilityToCopy = toCopyAbility.copy();
        abilityToCopy.newId();
        if (abilityToCopy instanceof LinkedEffectIdStaticAbility) {
            ((LinkedEffectIdStaticAbility) abilityToCopy).setEffectIdManually();
        }
        return abilityToCopy;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        sb.append(getTargetPointer().isPlural(mode.getTargets()) ? " gain " : " gains ");
        sb.append(CardUtil.stripReminderText(ability.getRule()));
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
