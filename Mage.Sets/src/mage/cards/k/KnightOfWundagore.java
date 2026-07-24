package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import java.util.UUID;

/**
 * @author muz
 */
public final class KnightOfWundagore extends CardImpl {

    public KnightOfWundagore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.VILLAIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you put a +1/+1 counter on another creature, put a +1/+1 counter on this creature. This ability triggers only once each turn.
        Ability ability = new PutCounterOnPermanentTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            CounterType.P1P1, StaticFilters.FILTER_ANOTHER_CREATURE
        ).setTriggersLimitEachTurn(1);
        this.addAbility(ability);
    }

    private KnightOfWundagore(final KnightOfWundagore card) {
        super(card);
    }

    @Override
    public KnightOfWundagore copy() {
        return new KnightOfWundagore(this);
    }
}
