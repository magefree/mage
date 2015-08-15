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
package mage.sets.masterseditioniii;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class WanderingMage extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Cleric or Wizard");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate("Cleric"),
                new SubtypePredicate("Wizard")));
    }

    public WanderingMage(UUID ownerId) {
        super(ownerId, 186, "Wandering Mage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.expansionSetCode = "ME3";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.subtype.add("Wizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {W}, Pay 1 life: Prevent the next 2 damage that would be dealt to target creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl("{W}"));
        ability.addCost(new PayLifeCost(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {U}: Prevent the next 1 damage that would be dealt to target Cleric or Wizard creature this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new ManaCostsImpl("{U}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        
        // {B}, Put a -1/-1 counter on a creature you control: Prevent the next 2 damage that would be dealt to target player this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl("{B}"));
        ability.addCost(new WanderingMageCost());
        ability.addTarget(new TargetPlayer());
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public WanderingMage(final WanderingMage card) {
        super(card);
    }

    @Override
    public WanderingMage copy() {
        return new WanderingMage(this);
    }
}

class WanderingMageCost extends CostImpl {

    public WanderingMageCost() {
        this.text = "Put a -1/-1 counter on a creature you control";
    }

    public WanderingMageCost(WanderingMageCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(ability.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(), game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public WanderingMageCost copy() {
        return new WanderingMageCost(this);
    }
}
