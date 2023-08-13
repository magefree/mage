

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

/**
 * @author nantuko
 */
public class LoseControlOnOtherPlayersControllerEffect extends OneShotEffect {

    public LoseControlOnOtherPlayersControllerEffect(String controllingPlayerName, String controlledPlayerName) {
        super(Outcome.Detriment);
        staticText = controllingPlayerName + " lost control over " + controlledPlayerName;
    }

    protected LoseControlOnOtherPlayersControllerEffect(final LoseControlOnOtherPlayersControllerEffect effect) {
        super(effect);
    }

    @Override
    public LoseControlOnOtherPlayersControllerEffect copy() {
        return new LoseControlOnOtherPlayersControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.resetOtherTurnsControlled();
            return true;
        }
        return false;
    }

}
