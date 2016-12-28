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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public class SoulExchange extends CardImpl {

    public SoulExchange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");

        // As an additional cost to cast Soul Exchange, exile a creature you control.
        Cost cost = new ExileTargetCost(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addCost(cost);
        // Return target creature card from your graveyard to the battlefield. Put a +2/+2 counter on that creature if the exiled creature was a Thrull.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));        
        this.getSpellAbility().addEffect(new SoulExchangeEffect());        
        
    }

    public SoulExchange(final SoulExchange card) {
        super(card);
    }

    @Override
    public SoulExchange copy() {
        return new SoulExchange(this);
    }
}

class SoulExchangeEffect extends OneShotEffect{
                          
    public SoulExchangeEffect() {
        super(Outcome.Benefit);
        this.setText("Return target creature card from your graveyard to the battlefield. Put a +2/+2 counter on that creature if the exiled creature was a Thrull.");
    }
    
    public SoulExchangeEffect(final SoulExchangeEffect effect) {
        super(effect);
    }
    
    @Override
    public SoulExchangeEffect copy() {
        return new SoulExchangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReturnFromGraveyardToBattlefieldTargetEffect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        if (!effect.apply(game, source)) return false;
        
        for (Cost c : source.getCosts()){
         /*   if (!c.getTargets().isEmpty()){
                UUID t = c.getTargets().getFirstTarget();
                Permanent exiled = game.getPermanentOrLKIBattlefield(t);*/
            if (c.isPaid() && c instanceof ExileTargetCost) {
                for (Permanent exiled : ((ExileTargetCost) c).getPermanents()) {
                  if (exiled != null){
                      if(exiled.getSubtype(game).contains("Thrull")){
                        game.getPermanent(source.getFirstTarget()).addCounters(CounterType.P2P2.createInstance(), source, game);
                        return true;
                        }
                  } else return false;                   
                }                               
            }
        }     
        return true;         
    }
}