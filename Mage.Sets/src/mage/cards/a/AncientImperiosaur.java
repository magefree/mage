package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ConvokedSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientImperiosaur extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ConvokedSourceCount.SPELL, 2);

    public AncientImperiosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Ancient Imperiosaur enters the battlefield with two +1/+1 counters on it for each creature that convoked it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), xValue, true
        ), null, "{this} enters the battlefield with two " +
                "+1/+1 counters on it for each creature that convoked it.", null));
    }

    private AncientImperiosaur(final AncientImperiosaur card) {
        super(card);
    }

    @Override
    public AncientImperiosaur copy() {
        return new AncientImperiosaur(this);
    }
}
