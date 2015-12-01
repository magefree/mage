package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Loki
 */
public class FatefulHourCondition implements Condition {
    private static FatefulHourCondition fInstance = new FatefulHourCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() <= 5;
    }
}
