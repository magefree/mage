package mage.cards.t;

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
public final class TomakulScrapsmith extends CardImpl {

    public TomakulScrapsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Tomakul Scrapsmith enters the battlefield, mill three cards. You may put an artifact card from among the cards milled this way into your hand. If you don't, put a +1/+1 counter on Tomakul Scrapsmith.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                3, StaticFilters.FILTER_CARD_ARTIFACT_AN,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        )));
    }

    private TomakulScrapsmith(final TomakulScrapsmith card) {
        super(card);
    }

    @Override
    public TomakulScrapsmith copy() {
        return new TomakulScrapsmith(this);
    }
}
