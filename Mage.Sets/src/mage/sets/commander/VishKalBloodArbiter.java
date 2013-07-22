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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class VishKalBloodArbiter extends CardImpl<VishKalBloodArbiter> {

    public VishKalBloodArbiter(UUID ownerId) {
        super(ownerId, 234, "Vish Kal, Blood Arbiter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{B}{B}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Vampire");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Sacrifice a creature: Put X +1/+1 counters on Vish Kal, Blood Arbiter, where X is the sacrificed creature's power.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new SacrificeCostCreaturesPower(), true),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,new FilterControlledCreaturePermanent("a creature"), false))));
        // Remove all +1/+1 counters from Vish Kal: Target creature gets -1/-1 until end of turn for each +1/+1 counter removed this way.
        DynamicValue removedCounters = new SignInversionDynamicValue(new VishKalBloodArbiterDynamicValue());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(removedCounters, removedCounters, Duration.EndOfTurn), new VishKalBloodArbiterCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public VishKalBloodArbiter(final VishKalBloodArbiter card) {
        super(card);
    }

    @Override
    public VishKalBloodArbiter copy() {
        return new VishKalBloodArbiter(this);
    }
}

class VishKalBloodArbiterCost extends CostImpl<VishKalBloodArbiterCost> {

    private int amount;
    private String name;

    public VishKalBloodArbiterCost(Counter counter) {
        this.name = counter.getName();
        this.amount = counter.getCount();
        this.text = "Remove all " + name + " counters from {this}";
    }

    public VishKalBloodArbiterCost(VishKalBloodArbiterCost cost) {
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
    public VishKalBloodArbiterCost copy() {
        return new VishKalBloodArbiterCost(this);
    }

    public int getAmount() {
        return amount;
    }


}

class VishKalBloodArbiterDynamicValue implements DynamicValue {


    public VishKalBloodArbiterDynamicValue() {

    }

    public VishKalBloodArbiterDynamicValue(final VishKalBloodArbiterDynamicValue dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability source) {
        int count = 0;
        for(Cost cost : source.getCosts()){
            if(cost instanceof VishKalBloodArbiterCost){
                count += ((VishKalBloodArbiterCost)cost).getAmount();
            }
        }
        return count;
    }

    @Override
    public VishKalBloodArbiterDynamicValue copy() {
        return new VishKalBloodArbiterDynamicValue(this);
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
