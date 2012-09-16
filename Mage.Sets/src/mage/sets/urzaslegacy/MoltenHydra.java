/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.urzaslegacy;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Plopman
 */
public class MoltenHydra extends CardImpl<MoltenHydra> {



    public MoltenHydra(UUID ownerId) {
        super(ownerId, 85, "Molten Hydra", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ULG";
        this.subtype.add("Hydra");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}{R}: Put a +1/+1 counter on Molten Hydra.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new ManaCostsImpl("{1}{R}{R}")));
        // {tap}, Remove all +1/+1 counters from Molten Hydra: Molten Hydra deals damage to target creature or player equal to the number of +1/+1 counters removed this way.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(new MotltenHydraDynamicValue()), new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreatureOrPlayer());
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



class RemoveAllCountersSourceCost extends CostImpl<RemoveAllCountersSourceCost> {

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
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            this.amount = permanent.getCounters().getCount(name);
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
    public int calculate(Game game, Ability source) {
        int count = 0;
        for(Cost cost : source.getCosts()){
            if(cost instanceof RemoveAllCountersSourceCost){
                count += ((RemoveAllCountersSourceCost)cost).getAmount();
            }
        }
        return count;
    }

    @Override
    public MotltenHydraDynamicValue clone() {
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

