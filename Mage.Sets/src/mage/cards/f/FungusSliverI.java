
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

/**
 *
 * @author mschatz
 */
public final class FungusSliverI extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(SubType.INCARNATION.getPredicate());
    }

    public FungusSliverI(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.SLIVER);
        this.subtype.add(SubType.ASSEMBLY_WORKER); // Apply Olivia Voldaren

        // Absorb initial Dread of Night Black
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Apply Prismatic Lace
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // All Sliver creatures have "Whenever this creature is dealt damage, put a +1/+1 counter on it."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new DealtDamageToSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false),
                Duration.WhileOnBattlefield,
                filter,
                "All Incarnation creatures have \"Whenever this creature is dealt damage, put a +1/+1 counter on it.\""))); 
    }

    private FungusSliverI(final FungusSliverI card) {
        super(card);
    }

    @Override
    public FungusSliverI copy() {
        return new FungusSliverI(this);
    }
}
