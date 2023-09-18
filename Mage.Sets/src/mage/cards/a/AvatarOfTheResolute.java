
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class AvatarOfTheResolute extends CardImpl {

    public AvatarOfTheResolute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Avatar of the Resolute enters the battlefield with a +1/+1 counter on it for each other creature you control with a +1/+1 counter on it.
        DynamicValue numberCounters = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE_P1P1);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), numberCounters, true),
                "with a +1/+1 counter on it for each other creature you control with a +1/+1 counter on it")
        );
        
    }

    private AvatarOfTheResolute(final AvatarOfTheResolute card) {
        super(card);
    }

    @Override
    public AvatarOfTheResolute copy() {
        return new AvatarOfTheResolute(this);
    }
}
