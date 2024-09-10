package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class LeelaSevateemWarrior extends CardImpl {

    public LeelaSevateemWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent draws a card except the first one they draw in each of their draw steps, put a +1/+1 counter on Leela, Sevateem Warrior.
        this.addAbility(new OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());

    }

    private LeelaSevateemWarrior(final LeelaSevateemWarrior card) {
        super(card);
    }

    @Override
    public LeelaSevateemWarrior copy() {
        return new LeelaSevateemWarrior(this);
    }
}
