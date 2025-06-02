package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeymourFlux extends CardImpl {

    public SeymourFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you may pay 1 life. If you do, draw a card and put a +1/+1 counter on Seymour Flux.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new PayLifeCost(1)
        ).addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"))));
    }

    private SeymourFlux(final SeymourFlux card) {
        super(card);
    }

    @Override
    public SeymourFlux copy() {
        return new SeymourFlux(this);
    }
}
