
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class MagmaMine extends CardImpl {

    public MagmaMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {4}: Put a pressure counter on Magma Mine.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new AddCountersSourceEffect(CounterType.PRESSURE.createInstance(), true), 
                new GenericManaCost(4)));
        
        // {tap}, Sacrifice Magma Mine: Magma Mine deals damage equal to the number of pressure counters on it to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new CountersSourceCount(CounterType.PRESSURE)), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MagmaMine(final MagmaMine card) {
        super(card);
    }

    @Override
    public MagmaMine copy() {
        return new MagmaMine(this);
    }
}
