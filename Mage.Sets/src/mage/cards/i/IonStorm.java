
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class IonStorm extends CardImpl {

    public IonStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");


        // {1}{R}, Remove a +1/+1 counter or a charge counter from a permanent you control: Ion Storm deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new OrCost(" Remove a +1/+1 counter or a charge counter from a permanent you control", new RemoveCounterCost(new TargetControlledPermanent(), CounterType.P1P1), new RemoveCounterCost(new TargetControlledPermanent(), CounterType.CHARGE)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private IonStorm(final IonStorm card) {
        super(card);
    }

    @Override
    public IonStorm copy() {
        return new IonStorm(this);
    }
}
