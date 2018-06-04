
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AttachedToCounterCondition implements Condition {

    private final CounterType counterType;
    private int amount = 1;
    private int from = -1;
    private int to;

    public AttachedToCounterCondition(CounterType type) {
        this.counterType = type;
    }

    public AttachedToCounterCondition(CounterType type, int amount) {
        this.counterType = type;
        this.amount = amount;
    }

    public AttachedToCounterCondition(CounterType type, int from, int to) {
        this.counterType = type;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
        if (attachedTo == null) {
            return false;
        }
        if (from != -1) { //range compare
            int count = attachedTo.getCounters(game).getCount(counterType);
            if (to == Integer.MAX_VALUE) {
                return count >= from;
            }
            return count >= from && count <= to;
        } else { // single compare (lte)
            return attachedTo.getCounters(game).getCount(counterType) >= amount;
        }
    }
}
