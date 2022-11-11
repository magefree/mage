
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SpittingHydra extends CardImpl {

    public SpittingHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Spitting Hydra enters the battlefield with four +1/+1 counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(4));
        effect.setText("with four +1/+1 counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        
        // {1}{R}, Remove a +1/+1 counter from Spitting Hydra: Spitting Hydra deals 1 damage to target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpittingHydra(final SpittingHydra card) {
        super(card);
    }

    @Override
    public SpittingHydra copy() {
        return new SpittingHydra(this);
    }
}