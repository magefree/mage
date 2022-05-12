package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThrivingTurtle extends CardImpl {

    public ThrivingTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Thriving Turtle enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Thriving Turtle attacks, you may pay {E}{E}. If you do, put a +1/+1 counter on it.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                new PayEnergyCost(2)
        ), false));
    }

    private ThrivingTurtle(final ThrivingTurtle card) {
        super(card);
    }

    @Override
    public ThrivingTurtle copy() {
        return new ThrivingTurtle(this);
    }
}
