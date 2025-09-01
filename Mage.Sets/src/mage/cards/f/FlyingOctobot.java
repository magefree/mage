package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlyingOctobot extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VILLAIN, "another Villain you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FlyingOctobot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another Villain you control enters, put a +1/+1 counter on this creature. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ).setTriggersLimitEachTurn(1));
    }

    private FlyingOctobot(final FlyingOctobot card) {
        super(card);
    }

    @Override
    public FlyingOctobot copy() {
        return new FlyingOctobot(this);
    }
}
