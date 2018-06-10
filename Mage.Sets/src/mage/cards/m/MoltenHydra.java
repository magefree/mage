
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class MoltenHydra extends CardImpl {



    public MoltenHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}{R}: Put a +1/+1 counter on Molten Hydra.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new ManaCostsImpl("{1}{R}{R}")));
        // {tap}, Remove all +1/+1 counters from Molten Hydra: Molten Hydra deals damage to any target equal to the number of +1/+1 counters removed this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new MotltenHydraDynamicValue()), new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        
    }

    public MoltenHydra(final MoltenHydra card) {
        super(card);
    }

    @Override
    public MoltenHydra copy() {
        return new MoltenHydra(this);
    }
}



class RemoveAllCountersSourceCost extends CostImpl {

    private int amount;
    private String name;

    public RemoveAllCountersSourceCost(Counter counter) {
        this.name = counter.getName();
        this.amount = counter.getCount();
        this.text = "Remove all " + name + " counters from {this}";
    }

    public RemoveAllCountersSourceCost(RemoveAllCountersSourceCost cost) {
        super(cost);
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            this.amount = permanent.getCounters(game).getCount(name);
            permanent.removeCounters(name, amount, game);
            this.paid = true;
        }
        else
        {
            this.amount = 0;
        }
        return paid;
    }

    @Override
    public RemoveAllCountersSourceCost copy() {
        return new RemoveAllCountersSourceCost(this);
    }

    public int getAmount() {
        return amount;
    }
    
    
}

class MotltenHydraDynamicValue implements DynamicValue {


    public MotltenHydraDynamicValue() {
        
    }

    public MotltenHydraDynamicValue(final MotltenHydraDynamicValue dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        for(Cost cost : source.getCosts()){
            if(cost instanceof RemoveAllCountersSourceCost){
                count += ((RemoveAllCountersSourceCost)cost).getAmount();
            }
        }
        return count;
    }

    @Override
    public MotltenHydraDynamicValue copy() {
        return new MotltenHydraDynamicValue(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the number of +1/+1 counters removed this way";
    }
}

