
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.counters.CounterType;
import mage.game.Game;
import mage.filter.FilterPermanent;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author jeffwadsworth
 */
public class PermanentHasCounterCondition implements Condition {



    private CounterType counterType;
    private int amount;
    private FilterPermanent filter;
    private ComparisonType counttype;
    private boolean anyPlayer;

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter) {
        this(counterType, amount, filter, ComparisonType.EQUAL_TO);
        this.anyPlayer = false;
    }

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter, ComparisonType type) {
        this.counterType = counterType;
        this.amount = amount;
        this.filter = filter;
        this.counttype = type;
        this.anyPlayer = false;
    }

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter, ComparisonType type, boolean any) {
        this.counterType = counterType;
        this.amount = amount;
        this.filter = filter;
        this.counttype = type;
        this.anyPlayer = any; 
    }
    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(this.filter, source.getControllerId(), game);
        if(this.anyPlayer) {
            permanents = game.getBattlefield().getAllActivePermanents(this.filter, game);
        }
        for (Permanent permanent : permanents) {
            if(ComparisonType.compare(permanent.getCounters(game).getCount(this.counterType), counttype, this.amount))
            {
                return true;
            }
        }
        return false;
    }
}
