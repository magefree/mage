package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AinokWayfarer extends CardImpl {

    public AinokWayfarer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, mill three cards. You may put a land card from among them into your hand. If you don't, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                3, StaticFilters.FILTER_CARD_LAND_A,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true)
        )));
    }

    private AinokWayfarer(final AinokWayfarer card) {
        super(card);
    }

    @Override
    public AinokWayfarer copy() {
        return new AinokWayfarer(this);
    }
}
