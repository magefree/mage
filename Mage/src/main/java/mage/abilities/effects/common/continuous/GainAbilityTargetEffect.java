package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.Locale;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityTargetEffect extends ContinuousEffectImpl {

    protected Ability ability;
    // shall a card gain the ability (otherwise permanent)
    private final boolean onCard;

    // Duration until next phase step of player
    private PhaseStep durationPhaseStep = null;
    private UUID durationPlayerId;
    private boolean sameStep;

    public GainAbilityTargetEffect(Ability ability, Duration duration) {
        this(ability, duration, null);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule) {
        this(ability, duration, rule, false);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule, boolean onCard) {
        this(ability, duration, rule, onCard, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule, boolean onCard, Layer layer, SubLayer subLayer) {
        super(duration, layer, subLayer, ability.getEffects().getOutcome(ability, Outcome.AddAbility));
        this.ability = ability;
        staticText = rule;
        this.onCard = onCard;

        this.generateGainAbilityDependencies(ability, null);
    }

    public GainAbilityTargetEffect(final GainAbilityTargetEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.onCard = effect.onCard;
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
        if (onCard) {
            for (UUID cardId : targetPointer.getTargets(game, source)) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    game.getState().addOtherAbility(card, ability);
                    affectedTargets++;
                }
            }
            if (duration == Duration.OneUse) {
                discard();
            }
        } else {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
                if (permanent != null) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                    affectedTargets++;
                }
            }
        }
        if (duration == Duration.Custom && affectedTargets == 0) {
            this.discard();
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
                sb.append(target.getMaxNumberOfTargets()).append(" target ").append(target.getTargetName()).append(" gain ");
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
