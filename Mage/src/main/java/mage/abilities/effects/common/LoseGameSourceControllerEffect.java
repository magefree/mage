

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */

public class LoseGameSourceControllerEffect extends OneShotEffect {

    public LoseGameSourceControllerEffect() {
        super(Outcome.Detriment);
        this.staticText = "you lose the game";
    }

    protected LoseGameSourceControllerEffect(final LoseGameSourceControllerEffect effect) {
        super(effect);
    }

    @Override
    public LoseGameSourceControllerEffect copy() {
        return new LoseGameSourceControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.lost(game);
            return true;
        }
        return false;
    }
}
