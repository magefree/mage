package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class GixianSkullflayer extends CardImpl {

    private static final CardsInControllerGraveyardCondition condition
            = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_CREATURES);

    public GixianSkullflayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, if there are three or more creature cards in your graveyard, put a +1/+1 counter on Gixian Skullflayer.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false),
                condition,
                "At the beginning of your upkeep, if there are three or more creature cards in your graveyard, put a +1/+1 counter on {this}."
        ));
    }

    private GixianSkullflayer(final GixianSkullflayer card) {
        super(card);
    }

    @Override
    public GixianSkullflayer copy() {
        return new GixianSkullflayer(this);
    }
}
