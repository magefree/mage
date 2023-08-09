
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
 * Each player may play an additional land on each of their turns.
 *
 * @author nantuko
 */
public class PlayAdditionalLandsAllEffect extends ContinuousEffectImpl {

    private int numExtraLands = 1;

    public PlayAdditionalLandsAllEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Each player may play an additional land on each of their turns";
        numExtraLands = 1;
    }

    public PlayAdditionalLandsAllEffect(int numExtraLands) {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.numExtraLands = numExtraLands;
        if (numExtraLands == Integer.MAX_VALUE) {
            staticText = "Each player may play any number of additional lands on each of their turns";
        } else {
            staticText = "Each player may play an additional " + numExtraLands + " lands on each of their turns";
        }
    }

    protected PlayAdditionalLandsAllEffect(final PlayAdditionalLandsAllEffect effect) {
        super(effect);
        this.numExtraLands = effect.numExtraLands;
        this.staticText = effect.staticText;
    }

    @Override
    public PlayAdditionalLandsAllEffect copy() {
        return new PlayAdditionalLandsAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            if (numExtraLands == Integer.MAX_VALUE) {
                player.setLandsPerTurn(Integer.MAX_VALUE);
            } else {
                player.setLandsPerTurn(player.getLandsPerTurn() + numExtraLands);
            }
            return true;
        }
        return true;
    }
}
