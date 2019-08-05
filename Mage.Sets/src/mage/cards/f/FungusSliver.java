
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author emerald000
 */
public final class FungusSliver extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new SubtypePredicate(SubType.SLIVER));
    }

    public FungusSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Sliver creatures have "Whenever this creature is dealt damage, put a +1/+1 counter on it."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new DealtDamageToSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false),
                Duration.WhileOnBattlefield,
                filter,
                "All Sliver creatures have \"Whenever this creature is dealt damage, put a +1/+1 counter on it.\""))); 
    }

    public FungusSliver(final FungusSliver card) {
        super(card);
    }

    @Override
    public FungusSliver copy() {
        return new FungusSliver(this);
    }
}
