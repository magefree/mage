package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author fireshoes
 */
public class UntapSourceDuringEachOtherPlayersUntapStepEffect extends ContinuousEffectImpl {

    public UntapSourceDuringEachOtherPlayersUntapStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Untap);
        staticText = "Untap {this} during each other player's untap step";
    }

    public UntapSourceDuringEachOtherPlayersUntapStepEffect(final UntapSourceDuringEachOtherPlayersUntapStepEffect effect) {
        super(effect);
    }

    @Override
    public UntapSourceDuringEachOtherPlayersUntapStepEffect copy() {
        return new UntapSourceDuringEachOtherPlayersUntapStepEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean applied = Boolean.TRUE.equals(game.getState().getValue(source.getSourceId() + "applied"));
        if (!applied && layer == Layer.RulesEffects) {
            if (!source.isControlledBy(game.getActivePlayerId())
                    && game.getStep() != null
                    && game.getTurnStepType() == PhaseStep.UNTAP) {
                game.getState().setValue(source.getSourceId() + "applied", true);
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    boolean untap = true;
                    for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(permanent, game).keySet()) {
                        untap &= effect.canBeUntapped(permanent, source, game, true);
                    }
                    if (untap) {
                        permanent.untap(game);
                    }
                }
            }
        } else if (applied && layer == Layer.RulesEffects) {
            if (game.getStep() != null && game.getTurnStepType() == PhaseStep.END_TURN) {
                game.getState().setValue(source.getSourceId() + "applied", false);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
