package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.YoureDealtDamageTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodHound extends CardImpl {

    public BloodHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you're dealt damage, you may put that many +1/+1 counters on Blood Hound.
        this.addAbility(new YoureDealtDamageTriggeredAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), SavedDamageValue.MANY), true));

        // At the beginning of your end step, remove all +1/+1 counters from Blood Hound.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new RemoveAllCountersSourceEffect(CounterType.P1P1)
                        .setText("remove all +1/+1 counters from {this}")
        ));
    }

    private BloodHound(final BloodHound card) {
        super(card);
    }

    @Override
    public BloodHound copy() {
        return new BloodHound(this);
    }
}
