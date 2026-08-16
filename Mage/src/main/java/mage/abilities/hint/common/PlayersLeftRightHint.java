package mage.abilities.hint.common;

import java.util.ArrayList;
import java.util.List;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

public enum PlayersLeftRightHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        final Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return null;
        }

        PlayerList players = game.getState().getPlayersInRange(ability.getControllerId(), game, true);

        List<String> info = new ArrayList<>();
        String leftInfo = "Player to left (next after you): %s";
        Player leftPlayer = players.copy().getNext(game, false);
        String rightInfo = "Player to right: %s";
        Player rightPlayer = players.copy().getPrevious(game);
        if (game.isTurnOrderReversed()) {
            info.add("Turn order reversed by effects");
            leftInfo = "Player to left: %s";
            leftPlayer = players.copy().getPrevious(game);
            rightInfo = "Player to right (next after you): %s";
            rightPlayer = players.copy().getNext(game, false);
        }
        info.add(String.format(leftInfo, leftPlayer == null ? "-" : leftPlayer.getLogName()));
        info.add(String.format(rightInfo, rightPlayer == null ? "-" : rightPlayer.getLogName()));
        return String.join("<br>", info);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
