
package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * A simple {@link Condition} to invert a decorated conditions
 * {@link Condition#apply(mage.game.Game, mage.abilities.Ability) apply(mage.game.Game, mage.abilities.Ability)}
 *  method invocation.
 * 
 * @author maurer.it_at_gmail.com
 */
public class InvertCondition implements Condition {

    private final Condition condition;

    public InvertCondition ( Condition condition ) {
        this.condition = condition;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public boolean apply(Game game, Ability source) {
        return !condition.apply(game, source);
    }

    @Override
    public String toString() {
        return condition.toString();
    }
    
}
