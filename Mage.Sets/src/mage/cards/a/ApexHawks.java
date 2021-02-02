
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class ApexHawks extends CardImpl {

    public ApexHawks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Multikicker (You may pay an additional {1}{W} any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{1}{W}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Apex Hawks enters the battlefield with a +1/+1 counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), MultikickerCount.instance, true)
                ,"with a +1/+1 counter on it for each time it was kicked"));
    }

    private ApexHawks(final ApexHawks card) {
        super(card);
    }

    @Override
    public ApexHawks copy() {
        return new ApexHawks(this);
    }
}
