
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class QuagVampires extends CardImpl {

    public QuagVampires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Multikicker (You may pay an additional {1}{B} any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{1}{B}"));

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // Quag Vampires enters the battlefield with a +1/+1 counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), MultikickerCount.instance, true),
                "with a +1/+1 counter on it for each time it was kicked"));
    }

    private QuagVampires(final QuagVampires card) {
        super(card);
    }

    @Override
    public QuagVampires copy() {
        return new QuagVampires(this);
    }
}
