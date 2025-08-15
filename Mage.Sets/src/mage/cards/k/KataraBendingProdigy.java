package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
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
public final class KataraBendingProdigy extends CardImpl {

    public KataraBendingProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if Katara is tapped, put a +1/+1 counter on her.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on her")
        ).withInterveningIf(SourceTappedCondition.TAPPED));

        // Waterbend {6}: Draw a card.
        this.addAbility(new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new WaterbendCost(6)));
    }

    private KataraBendingProdigy(final KataraBendingProdigy card) {
        super(card);
    }

    @Override
    public KataraBendingProdigy copy() {
        return new KataraBendingProdigy(this);
    }
}
