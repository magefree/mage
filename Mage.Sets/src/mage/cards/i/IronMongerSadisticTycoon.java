package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.CreatureConnivesTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class IronMongerSadisticTycoon extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.VILLAIN, "Villain you control");

    public IronMongerSadisticTycoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature you control connives, put a +1/+1 counter on each Villain you control.
        this.addAbility(new CreatureConnivesTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)));
    }

    private IronMongerSadisticTycoon(final IronMongerSadisticTycoon card) {
        super(card);
    }

    @Override
    public IronMongerSadisticTycoon copy() {
        return new IronMongerSadisticTycoon(this);
    }
}
