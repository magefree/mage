package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TuiAndLaMoonAndOcean extends CardImpl {

    public TuiAndLaMoonAndOcean(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Tui and La become tapped, draw a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .setTriggerPhrase("Whenever {this} become tapped, "));

        // Whenever Tui and La become untapped, put a +1/+1 counter on them.
        this.addAbility(new InspiredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on them"))
                .setTriggerPhrase("Whenever {this} become untapped, ")
                .setAbilityWord(null));
    }

    private TuiAndLaMoonAndOcean(final TuiAndLaMoonAndOcean card) {
        super(card);
    }

    @Override
    public TuiAndLaMoonAndOcean copy() {
        return new TuiAndLaMoonAndOcean(this);
    }
}
