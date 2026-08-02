package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.players.Player;

public enum PlayersLeftRightHint implements Hint {
    instance;
 
    @Override
    public String getText(Game game, Ability ability) {
        final Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return null;
        }
        final StringBuilder ret = new StringBuilder();
        final Player left = game.getState().getPlayerList(ability.getControllerId()).getNext(game, false);
        if (left != null) {
            ret.append("Player to left: " + left.getName() + "<br>");
        }
        final Player right = game.getState().getPlayerList(ability.getControllerId()).getPrevious(game);
        if (right != null) {
            ret.append("Player to right: " + right.getName() + "<br>");
        }
        return ret.toString();
    }
 
    @Override
    public Hint copy() {
        return instance;
    }
}
