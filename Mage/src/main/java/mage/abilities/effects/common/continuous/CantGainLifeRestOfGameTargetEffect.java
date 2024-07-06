package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Susucr
 */
public class CantGainLifeRestOfGameTargetEffect extends ContinuousEffectImpl {

    public CantGainLifeRestOfGameTargetEffect() {
        super(Duration.EndOfGame, Layer.PlayerEffects, SubLayer.NA, Outcome.Neutral);
        staticText = "that player can't gain life for the rest of the game";
    }

    private CantGainLifeRestOfGameTargetEffect(final CantGainLifeRestOfGameTargetEffect effect) {
        super(effect);
    }

    @Override
    public CantGainLifeRestOfGameTargetEffect copy() {
        return new CantGainLifeRestOfGameTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.setCanGainLife(false);
        }
        return true;
    }
}
