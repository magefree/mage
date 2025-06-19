package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * A simple {@link Condition} to invert a decorated conditions
 * {@link Condition#apply(mage.game.Game, mage.abilities.Ability) apply(mage.game.Game, mage.abilities.Ability)}
 * method invocation.
 *
 * @author maurer.it_at_gmail.com
 */
public class InvertCondition implements Condition {

    private final Condition condition;
    private final String message;

    public InvertCondition(Condition condition) {
        this(condition, null);
    }

    public InvertCondition(Condition condition, String message) {
        this.condition = condition;
        this.message = message;
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
        if (message != null && !message.isEmpty()) {
            return message;
        }
        return condition.toString();
    }

}
