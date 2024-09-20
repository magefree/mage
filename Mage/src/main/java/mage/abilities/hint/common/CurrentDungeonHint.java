package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.players.Player;

/**
 * @author JayDi85
 */
public enum CurrentDungeonHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(CitysBlessingCondition.instance, "You have city's blessing");

    @Override
    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return "";
        }

        Dungeon dungeon = game.getPlayerDungeon(ability.getControllerId());
        if (dungeon == null) {
            return "Current dungeon: not yet entered";
        }

        String dungeonInfo = "Current dungeon: " + dungeon.getLogName();
        if (dungeon.getCurrentRoom() != null) {
            dungeonInfo += ", room: " + dungeon.getCurrentRoom().getName();
        }

        return dungeonInfo;
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
