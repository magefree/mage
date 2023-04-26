package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.abilities.condition.common.CountersOnPermanentsCondition;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A hint which keeps track of how many counters of a specific type there are
 * among some type of permanents
 * 
 * @author alexander-novo
 */
public class CountersOnPermanentsHint implements Hint {

    // Which permanents to consider counters on
    public final FilterPermanent filter;
    // Which counter type to count
    public final CounterType counterType;

    /**
     * @param filter      Which permanents to consider counters on
     * @param counterType Which counter type to count
     */
    public CountersOnPermanentsHint(FilterPermanent filter, CounterType counterType) {
        this.filter = filter;
        this.counterType = counterType;
    }

    /**
     * Copy parameters from a {@link CountersOnPermanentsCondition}
     */
    public CountersOnPermanentsHint(CountersOnPermanentsCondition condition) {
        this.filter = condition.filter;
        this.counterType = condition.counterType;
    }

    @Override
    public String getText(Game game, Ability ability) {
        int totalCounters = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(this.filter,
                ability.getControllerId(), ability, game)) {
            for (Counter counter : permanent.getCounters(game).values()) {
                if (counter.getName().equals(this.counterType.getName())) {
                    totalCounters += counter.getCount();
                }

            }
        }
        return this.counterType.getName() + " counters among " + this.filter.getMessage() + ": " + totalCounters;
    }

    @Override
    public Hint copy() {
        return this;
    }
}
