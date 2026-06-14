package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MockingbirdAceAgent extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets a creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public MockingbirdAceAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever you cast a spell that targets a creature you control, put a +1/+1 counter on Mockingbird.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), filter, false
        ));
    }

    private MockingbirdAceAgent(final MockingbirdAceAgent card) {
        super(card);
    }

    @Override
    public MockingbirdAceAgent copy() {
        return new MockingbirdAceAgent(this);
    }
}
