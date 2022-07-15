
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.counters.CounterType;
import mage.game.Game;
import mage.filter.FilterPermanent;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Objects;

/**
 * @author jeffwadsworth
 */
public class PermanentHasCounterCondition implements Condition {

    private CounterType counterType;
    private int amount;
    private FilterPermanent filter;
    private ComparisonType countType;
    private boolean anyPlayer;

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter) {
        this(counterType, amount, filter, ComparisonType.EQUAL_TO);
        this.anyPlayer = false;
    }

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter, ComparisonType type) {
        this.counterType = counterType;
        this.amount = amount;
        this.filter = filter;
        this.countType = type;
        this.anyPlayer = false;
    }

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter, ComparisonType type, boolean any) {
        this.counterType = counterType;
        this.amount = amount;
        this.filter = filter;
        this.countType = type;
        this.anyPlayer = any; 
    }
    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(this.filter, source.getControllerId(), game);
        if(this.anyPlayer) {
            permanents = game.getBattlefield().getAllActivePermanents(this.filter, game);
        }
        for (Permanent permanent : permanents) {
            if(ComparisonType.compare(permanent.getCounters(game).getCount(this.counterType), countType, this.amount))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PermanentHasCounterCondition that = (PermanentHasCounterCondition) obj;
        return this.anyPlayer == that.anyPlayer
                && this.counterType == that.counterType
                && this.countType == that.countType
                && this.amount == that.amount
                && Objects.equals(this.filter, that.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(counterType, amount, filter, countType, anyPlayer);
    }
}
