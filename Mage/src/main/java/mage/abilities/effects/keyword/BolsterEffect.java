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
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BolsterEffect extends OneShotEffect {
    
    private final DynamicValue amount;
    
    public BolsterEffect(int amount) {
        this(new StaticValue(amount));        
    }
    
    public BolsterEffect(DynamicValue amount) {
        super(Outcome.BoostCreature);
        this.amount = amount;
        this.staticText = setText();
    }
    
    public BolsterEffect(final BolsterEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }
    
    @Override
    public BolsterEffect copy() {
        return new BolsterEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (amount.calculate(game, source, this) <= 0) {
                return true;
            }
            int leastToughness = Integer.MAX_VALUE;
            Permanent selectedCreature = null;
            for(Permanent permanent: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), controller.getId(), game)) {
                if (leastToughness > permanent.getToughness().getValue()) {
                    leastToughness = permanent.getToughness().getValue();
                    selectedCreature = permanent;
                } else if (leastToughness == permanent.getToughness().getValue()) {
                    leastToughness = permanent.getToughness().getValue();
                    selectedCreature = null;
                }
            }
            if (leastToughness != Integer.MAX_VALUE) {
                if (selectedCreature == null) {
                    FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with toughness " + leastToughness);
                    filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, leastToughness));
                    Target target = new TargetPermanent(1,1, filter, true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        selectedCreature = game.getPermanent(target.getFirstTarget());
                    }
                }
                if (selectedCreature != null) {
                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(amount.calculate(game, source, this)));
                    effect.setTargetPointer(new FixedTarget(selectedCreature.getId()));
                    return effect.apply(game, source);
                }                
            }
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("bolster ");
        if (amount instanceof StaticValue) {
            sb.append(amount);
            sb.append(". <i>(Choose a creature with the least toughness or tied with the least toughness among creatures you control. Put ");
            sb.append(amount).append(" +1/+1 counters on it.)</i>");
        } else {
            sb.append("X, where X is the number of ");
            sb.append(amount.getMessage());
            sb.append(". (Choose a creature with the least toughness among creatures you control and put X +1/+1 counters on it.)");
        }
        return sb.toString();
    }
}
