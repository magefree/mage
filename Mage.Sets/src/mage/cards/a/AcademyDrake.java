package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

public final class AcademyDrake extends CardImpl {

    public AcademyDrake(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        subtype.add(SubType.DRAKE);
        power = new MageInt(2);
        toughness = new MageInt(2);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Flying
        addAbility(FlyingAbility.getInstance());

        // If Academy Drake was kicked, it enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it.", ""));

    }

    public AcademyDrake(final AcademyDrake academyDrake) {
        super(academyDrake);
    }

    public AcademyDrake copy() {
        return new AcademyDrake(this);
    }
}
