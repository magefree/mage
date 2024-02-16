package mage.abilities.effects.common.continuous;

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
    public void init(Ability source, Game game) {
        super.init(source, game);

        // must support dynamic targets from static ability and static targets from activated abilities
        if (this.affectedObjectsSet) {
            // target permanents (by default)
            targetPointer.getTargets(game, source)
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .forEach(permanent -> {
                        this.affectedObjectList.add(new MageObjectReference(permanent, game));
                    });

            // target cards with linked permanents
            if (this.useOnCard) {
                targetPointer.getTargets(game, source)
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
    public GainAbilityTargetEffect copy() {
        return new GainAbilityTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        if (affectedObjectsSet) {
            // STATIC TARGETS
            List<MageObjectReference> newWaitingPermanents = new ArrayList<>();
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
                MageObjectReference mor = it.next();

                // look for permanent
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null) {
                    this.waitingCardPermanent = false;
                    permanent.addAbility(ability, source.getSourceId(), game);
                    affectedTargets++;
                    continue;
                }

                // look for card with linked permanent
                if (this.useOnCard) {
                    Card card = mor.getCard(game);
                    if (card != null) {
                        game.getState().addOtherAbility(card, ability);
                        affectedTargets++;
                        continue;
                    } else {
                        // start waiting a spell's permanent (example: Tyvar Kell's emblem)
                        Permanent perm = game.getPermanent(mor.getSourceId());
                        if (perm != null) {
                            perm.addAbility(ability, source.getSourceId(), game);
                            affectedTargets++;
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
                return affectedTargets > 0;
            }

            // no more valid targets
            if (this.affectedObjectList.isEmpty()) {
                discard();
            }

            // no more valid permanents (card was countered without new permanent)
            if (duration == Duration.Custom && affectedTargets == 0 && !this.waitingCardPermanent) {
                discard();
            }
        } else {
            // DYNAMIC TARGETS
            for (UUID objectId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(objectId);
                if (permanent != null) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                    affectedTargets++;
                    continue;
                }
                if (this.useOnCard) {
                    Card card = game.getCard(objectId);
                    if (card != null) {
                        game.getState().addOtherAbility(card, ability);
                        affectedTargets++;
                    }
                }
            }
        }
        return affectedTargets > 0;
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
