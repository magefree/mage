package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
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
public final class SpringmantleCleric extends CardImpl {

    public SpringmantleCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Springmantle Cleric enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ColorsOfManaSpentToCastCount.getInstance(), true
        ), "with a +1/+1 counter on it for each color of mana spent to cast it."));
    }

    private SpringmantleCleric(final SpringmantleCleric card) {
        super(card);
    }

    @Override
    public SpringmantleCleric copy() {
        return new SpringmantleCleric(this);
    }
}
