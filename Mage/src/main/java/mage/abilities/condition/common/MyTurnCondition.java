
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public enum MyTurnCondition implements Condition {
   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getActivePlayerId().equals(source.getControllerId());
    }
    
    @Override
    public String toString() {
	return "during your turn";
    }
}
