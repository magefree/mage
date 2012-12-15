/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */


package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */


public class CumulativeUpkeepAbility extends BeginningOfUpkeepTriggeredAbility {
    
    private Cost cumulativeCost;
    
    public CumulativeUpkeepAbility(Cost cumulativeCost) {
        super(new AddCountersSourceEffect(CounterType.AGE.createInstance()), Constants.TargetController.YOU, false);
        this.addEffect(new CumulativeUpkeepEffect(cumulativeCost));
        this.cumulativeCost = cumulativeCost;
    }

    public CumulativeUpkeepAbility(final CumulativeUpkeepAbility ability) {
        super(ability);
        this.cumulativeCost = ability.cumulativeCost.copy();
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new CumulativeUpkeepAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Cumulative upkeep");
        if(cumulativeCost instanceof ManaCost){
            sb.append(" ");
        }
        else{
            sb.append("-");
        }
        sb.append(cumulativeCost.getText());
        return sb.toString();
    }
}

class CumulativeUpkeepEffect extends OneShotEffect<CumulativeUpkeepEffect> {
    
    private Cost cumulativeCost;
    
    CumulativeUpkeepEffect(Cost cumulativeCost) {
        super(Constants.Outcome.Sacrifice);
        this.cumulativeCost = cumulativeCost;
    }

    CumulativeUpkeepEffect(final CumulativeUpkeepEffect effect) {
        super(effect);
        this.cumulativeCost = effect.cumulativeCost.copy();
    }


    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) { 
            int ageCounter = permanent.getCounters().getCount(CounterType.AGE);
            if(cumulativeCost instanceof ManaCost){
                ManaCostsImpl totalCost = new ManaCostsImpl();
                for(int i = 0 ; i < ageCounter; i++){
                    totalCost.add(cumulativeCost.copy());
                }
                if (player.chooseUse(Constants.Outcome.Benefit, "Pay " + totalCost.getText() + "?", game)) {
                    totalCost.clearPaid();
                    if (totalCost.payOrRollback(source, game, source.getId(), source.getControllerId())){
                        return true;
                    }
                }
                permanent.sacrifice(source.getSourceId(), game);
                return true;    
            }
            else{
                CostsImpl totalCost = new CostsImpl();
                for(int i = 0 ; i < ageCounter; i++){
                    totalCost.add(cumulativeCost.copy());
                }
                if (player.chooseUse(Constants.Outcome.Benefit, totalCost.getText() + "?", game)) {
                    totalCost.clearPaid();
                    int bookmark = game.bookmarkState();
                    if (totalCost.pay(source, game, source.getId(), source.getControllerId(), false)){
                        return true;
                    }
                    else{
                        game.restoreState(bookmark);
                    }
                }
                permanent.sacrifice(source.getSourceId(), game);
                return true;    
            }
            
        }
        return false;
    }

    @Override
    public CumulativeUpkeepEffect copy() {
        return new CumulativeUpkeepEffect(this);
    }
}
