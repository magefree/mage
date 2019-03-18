
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author nantuko
 */
public enum NoSpellsWereCastLastTurnCondition implements Condition {

    instance;



    @Override
    public boolean apply(Game game, Ability source) {
        // Do not check at start of game.
        // Needed for tests to keep add to battlefield cards setting off condition when not intended.
        if (game.getTurnNum() < 2) {
            return false;
        }

        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if(watcher == null){
            return false;
        }
        // if any player cast spell, return false
        for (Integer count : watcher.getAmountOfSpellsCastOnPrevTurn().values()) {
            if (count > 0) {
                return false;
            }
        }

        // no one cast spell last turn
        return true;
    }
}
