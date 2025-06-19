package mage.cards.g;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class GixianSkullflayer extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_CREATURES);

    public GixianSkullflayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, if there are three or more creature cards in your graveyard, put a +1/+1 counter on Gixian Skullflayer.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())).withInterveningIf(condition));
    }

    private GixianSkullflayer(final GixianSkullflayer card) {
        super(card);
    }

    @Override
    public GixianSkullflayer copy() {
        return new GixianSkullflayer(this);
    }
}
