package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author JayDi85
 */
public class GainAbilityTargetEffect extends ContinuousEffectImpl {

    protected Ability ability;

    // shall a card gain the ability (otherwise a permanent)
    private final boolean useOnCard; // only one card per ability supported
    private boolean waitingCardPermanent = false; // wait the permanent from card's resolve (for inner usage only)

    // Duration until next phase step of player
    private PhaseStep durationPhaseStep = null;
    private UUID durationPlayerId;
    private boolean sameStep;

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
        this(ability, duration, rule, useOnCard, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule, boolean useOnCard, Layer layer, SubLayer subLayer) {
        super(duration, layer, subLayer, ability.getEffects().getOutcome(ability, Outcome.AddAbility));
        this.ability = ability;
        this.staticText = rule;
        this.useOnCard = useOnCard;

        this.generateGainAbilityDependencies(ability, null);
    }

    public GainAbilityTargetEffect(final GainAbilityTargetEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.useOnCard = effect.useOnCard;
        this.waitingCardPermanent = effect.waitingCardPermanent;
        this.durationPhaseStep = effect.durationPhaseStep;
        this.durationPlayerId = effect.durationPlayerId;
        this.sameStep = effect.sameStep;
    }

    /**
     * Used to set a duration to the next durationPhaseStep of the first
     * controller of the effect.
     *
     * @param phaseStep
     */
    public void setDurationToPhase(PhaseStep phaseStep) {
        durationPhaseStep = phaseStep;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        if (durationPhaseStep != null) {
            durationPlayerId = source.getControllerId();
            sameStep = true;
        }

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
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game)) {
            return true;
        }
        if (durationPhaseStep != null && durationPhaseStep == game.getPhase().getStep().getType()) {
            return !sameStep && game.isActivePlayer(durationPlayerId) || game.getPlayer(durationPlayerId).hasReachedNextTurnAfterLeaving();
        } else {
            sameStep = false;
        }
        return false;
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

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();

        if (mode.getTargets().size() > 0) {
            Target target = mode.getTargets().get(0);
            if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE) {
                sb.append("any number of target ").append(target.getTargetName()).append(" gain ");
            } else if (target.getMaxNumberOfTargets() > 1) {
                if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                    sb.append("up to ");
                }
                sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName()).append(" gain ");
            } else {
                if (!target.getTargetName().toLowerCase(Locale.ENGLISH).startsWith("another")) {
                    sb.append("target ");
                }
                sb.append(target.getTargetName()).append(" gains ");
            }
        } else {
            sb.append("gains ");
        }

        sb.append(ability.getRule());
        if (durationPhaseStep != null) {
            sb.append(" until your next ").append(durationPhaseStep.toString().toLowerCase(Locale.ENGLISH));
        } else if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
