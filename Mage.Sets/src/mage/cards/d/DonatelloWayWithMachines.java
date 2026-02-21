package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DonatelloWayWithMachines extends CardImpl {

    public DonatelloWayWithMachines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an artifact you control enters, put a +1/+1 counter on Donatello.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ));
    }

    private DonatelloWayWithMachines(final DonatelloWayWithMachines card) {
        super(card);
    }

    @Override
    public DonatelloWayWithMachines copy() {
        return new DonatelloWayWithMachines(this);
    }
}
