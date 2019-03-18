
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * Checks if permanent was paid tribute to as it entered the battlefield
 *
 * @author LevelX2
 */
public enum TributeNotPaidCondition implements Condition {

   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Object tribute = game.getState().getValue("tributeValue" + source.getSourceId());
        if (tribute != null) {
            return ((String) tribute).equals("no");
        }
        return false;
    }
}
