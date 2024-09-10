
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author nantuko
 */
public class SourceHasCounterCondition implements Condition {

    private final CounterType counterType;
    private int amount = 1;
    private int from = -1;
    private int to;

    public SourceHasCounterCondition(CounterType type) {
        this.counterType = type;
    }

    public SourceHasCounterCondition(CounterType type, int amount) {
        this.counterType = type;
        this.amount = amount;
    }

    public SourceHasCounterCondition(CounterType type, int from, int to) {
        this.counterType = type;
        this.from = from;
        this.to = to;
    }

    @Override
    @SuppressWarnings("null")
    public boolean apply(Game game, Ability source) {
        Card card = null;
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            card = game.getCard(source.getSourceId());
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
        } else // single compare (lte)
        {
            if (card != null) {
                return card.getCounters(game).getCount(counterType) >= amount;
            } else {
                return permanent.getCounters(game).getCount(counterType) >= amount;
            }
        }
    }

    @Override
    public String toString() {
        if (from != -1) {
            if (from == 0) {
                if (to == 0) {
                    return "{this} has no " + this.counterType.toString() + " counters on it";
                }
                return "{this} has " + CardUtil.numberToText(to) + " or fewer " + this.counterType.toString() + " counters on it";
            }
            if (to == Integer.MAX_VALUE) {
                return "{this} has " + CardUtil.numberToText(from) + " or more " + this.counterType.toString() + " counters on it";
            }
            return "{this} has between " + from + " and " + to + " " + this.counterType.toString() + " counters on it";
        } else {
            return "{this} has " + CardUtil.numberToText(amount) + " or more " + this.counterType.toString() + " counters on it";
        }
    }
}
