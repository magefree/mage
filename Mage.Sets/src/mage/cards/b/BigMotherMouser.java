package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.Robot11Token;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BigMotherMouser extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public BigMotherMouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // This creature enters with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(2)));

        // Whenever this creature attacks, double the number of +1/+1 counters on it.
        this.addAbility(new AttacksTriggeredAbility(new DoubleCountersSourceEffect(CounterType.P1P1)));

        // When this creature dies, create a number of 1/1 colorless Robot artifact creature tokens equal to the number of +1/+1 counters on this creature.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(
            new Robot11Token(), xValue
        ).setText("create a number of 1/1 colorless Robot artifact creature tokens equal to the number of +1/+1 counters on this creature")));
    }

    private BigMotherMouser(final BigMotherMouser card) {
        super(card);
    }

    @Override
    public BigMotherMouser copy() {
        return new BigMotherMouser(this);
    }
}
