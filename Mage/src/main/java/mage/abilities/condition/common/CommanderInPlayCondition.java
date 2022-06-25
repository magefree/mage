package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * Checks if the player has its commander in play and controls it
 *
 * @author LevelX2
 */
public enum CommanderInPlayCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ControlYourCommanderCondition.instance.apply(game, source);
    }

    @Override
    public String toString() {
        return "As long as you control your commander";
    }

}
