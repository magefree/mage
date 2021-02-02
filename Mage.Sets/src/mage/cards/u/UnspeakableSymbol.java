
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class UnspeakableSymbol extends CardImpl {

    public UnspeakableSymbol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");


        // Pay 3 life: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new PayLifeCost(3));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private UnspeakableSymbol(final UnspeakableSymbol card) {
        super(card);
    }

    @Override
    public UnspeakableSymbol copy() {
        return new UnspeakableSymbol(this);
    }
}
