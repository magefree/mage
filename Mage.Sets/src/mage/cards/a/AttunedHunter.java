package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class AttunedHunter extends CardImpl {

    public AttunedHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever one or more cards leave your graveyard during your turn, put a +1/+1 counter on this creature.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CARD_CARDS, true
        ));
    }

    private AttunedHunter(final AttunedHunter card) {
        super(card);
    }

    @Override
    public AttunedHunter copy() {
        return new AttunedHunter(this);
    }
}
