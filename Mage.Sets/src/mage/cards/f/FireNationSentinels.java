package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationSentinels extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("nontoken creature an opponent controls");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public FireNationSentinels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a nontoken creature an opponent controls dies, put a +1/+1 counter on each creature you control.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), false, filter));
    }

    private FireNationSentinels(final FireNationSentinels card) {
        super(card);
    }

    @Override
    public FireNationSentinels copy() {
        return new FireNationSentinels(this);
    }
}
