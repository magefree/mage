

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author plopman
 */


public class WinGameSourceControllerEffect extends OneShotEffect {

    public WinGameSourceControllerEffect() {
        super(Outcome.Win);
        this.staticText = "you win the game";
    }

    protected WinGameSourceControllerEffect(final WinGameSourceControllerEffect effect) {
        super(effect);
    }

    @Override
    public WinGameSourceControllerEffect copy() {
        return new WinGameSourceControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.won(game);
            return true;
        }
        return false;
    }
}