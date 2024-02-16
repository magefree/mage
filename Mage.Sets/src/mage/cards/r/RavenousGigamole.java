package mage.cards.r;

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
public final class RavenousGigamole extends CardImpl {

    public RavenousGigamole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Ravenous Gigamole enters the battlefield, mill three cards. You may put a creature card from among the cards milled this way into your hand. If you don't, put a +1/+1 counter on Ravenous Gigamole.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                3, StaticFilters.FILTER_CARD_CREATURE_A,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        )));
    }

    private RavenousGigamole(final RavenousGigamole card) {
        super(card);
    }

    @Override
    public RavenousGigamole copy() {
        return new RavenousGigamole(this);
    }
}
