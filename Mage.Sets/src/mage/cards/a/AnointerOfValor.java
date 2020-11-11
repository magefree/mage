package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnointerOfValor extends CardImpl {

    public AnointerOfValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature attacks, you may pay {3}. When you do, put a +1/+1 counter on that creature.
        this.addAbility(new AttacksAllTriggeredAbility(
                new DoWhenCostPaid(
                        new ReflexiveTriggeredAbility(
                                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                                false, "put a +1/+1 counter on that creature"
                        ), new GenericManaCost(3), "Pay {3}?"
                ), false, StaticFilters.FILTER_PERMANENT_CREATURE,
                SetTargetPointer.PERMANENT, false
        ));
    }

    private AnointerOfValor(final AnointerOfValor card) {
        super(card);
    }

    @Override
    public AnointerOfValor copy() {
        return new AnointerOfValor(this);
    }
}
