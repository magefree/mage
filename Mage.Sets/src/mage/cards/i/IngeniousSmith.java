package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author weirddan455
 */
public final class IngeniousSmith extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("one or more artifacts");

    public IngeniousSmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Ingenious Smith enters the battlefield, look at the top four cards of your library.
        // You may reveal an artifact card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_ARTIFACT_AN, PutCards.HAND, PutCards.BOTTOM_RANDOM)));

        // Whenever one or more artifacts enter the battlefield under your control, put a +1/+1 counter on Ingenious Smith.
        // This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ).setTriggersOnce(true));
    }

    private IngeniousSmith(final IngeniousSmith card) {
        super(card);
    }

    @Override
    public IngeniousSmith copy() {
        return new IngeniousSmith(this);
    }
}
