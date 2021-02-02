    
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class SkitterOfLizards extends CardImpl {

    public SkitterOfLizards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Multikicker (You may pay an additional {1}{R} any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{1}{R}"));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Skitter of Lizards enters the battlefield with a +1/+1 counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), MultikickerCount.instance, true),
                "with a +1/+1 counter on it for each time it was kicked"));

    }

    private SkitterOfLizards(final SkitterOfLizards card) {
        super(card);
    }

    @Override
    public SkitterOfLizards copy() {
        return new SkitterOfLizards(this);
    }
}
