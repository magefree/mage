package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EruditeWizard extends CardImpl {

    public EruditeWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you draw your second card each turn, put a +1/+1 counter on this creature.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, 2
        ));
    }

    private EruditeWizard(final EruditeWizard card) {
        super(card);
    }

    @Override
    public EruditeWizard copy() {
        return new EruditeWizard(this);
    }
}
