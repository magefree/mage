package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author muz
 */
public final class BlackPantherClawsOfBast extends CardImpl {

    public BlackPantherClawsOfBast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever a creature you control attacks alone, put a +1/+1 counter on it.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"),
            true, false
        ));
    }

    private BlackPantherClawsOfBast(final BlackPantherClawsOfBast card) {
        super(card);
    }

    @Override
    public BlackPantherClawsOfBast copy() {
        return new BlackPantherClawsOfBast(this);
    }
}
