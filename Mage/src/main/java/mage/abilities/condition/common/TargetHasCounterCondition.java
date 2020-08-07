
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public class TargetHasCounterCondition implements Condition {

    private final CounterType counterType;
    private int amount = 1;
    private int from = -1;
    private int to;

    public TargetHasCounterCondition(CounterType type) {
        this.counterType = type;
    }

    public TargetHasCounterCondition(CounterType type, int amount) {
        this.counterType = type;
        this.amount = amount;
    }

    public TargetHasCounterCondition(CounterType type, int from, int to) {
        this.counterType = type;
        this.from = from;
        this.to = to;
    }

    @Override
    @SuppressWarnings("null")
    public boolean apply(Game game, Ability source) {
        Card card = null;
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (permanent == null) {
            card = game.getCard(source.getFirstTarget());
            if (card == null) {
                return false;
            }
        }
        if (from != -1) { //range compare
            int count;
            if (card != null) {
                count = card.getCounters(game).getCount(counterType);
            } else {
                count = permanent.getCounters(game).getCount(counterType);
            }
            if (to == Integer.MAX_VALUE) {
                return count >= from;
            }
            return count >= from && count <= to;
        } else { // single compare (lte)
            if (card != null) {
                return card.getCounters(game).getCount(counterType) >= amount;
            } else {
                return permanent.getCounters(game).getCount(counterType) >= amount;
            }
        }
    }

    @Override
    public String toString() {
        return "if it has a " + counterType.getName() + " on it";
    }
}
