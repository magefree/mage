
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author nantuko
 */
public enum TwoOrMoreSpellsWereCastLastTurnCondition implements Condition {

   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if(watcher == null){
            return false;
        }
        // if any player cast more than two spells, return true
        for (Integer count : watcher.getAmountOfSpellsCastOnPrevTurn().values()) {
            if (count >= 2) {
                return true;
            }
        }
        // no one cast two or more spells last turn
        return false;
    }
}
