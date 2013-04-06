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
package mage.sets.timespiral;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class RestoreBalance extends CardImpl<RestoreBalance> {

    public RestoreBalance(UUID ownerId) {
        super(ownerId, 38, "Restore Balance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "");
        this.expansionSetCode = "TSP";

        // Suspend 6-{W}
        this.addAbility(new SuspendAbility(6, new ManaCostsImpl("{W}"), this));
        // Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players sacrifice creatures and discard cards the same way.
        this.getSpellAbility().addEffect(new RestoreBalanceEffect());
    }

    public RestoreBalance(final RestoreBalance card) {
        super(card);
    }

    @Override
    public RestoreBalance copy() {
        return new RestoreBalance(this);
    }
}


class RestoreBalanceEffect extends OneShotEffect<RestoreBalanceEffect> {

   
    public RestoreBalanceEffect() {
        super(Constants.Outcome.Sacrifice);
    }

    public RestoreBalanceEffect(final RestoreBalanceEffect effect) {
        super(effect);
    }

    @Override
    public RestoreBalanceEffect copy() {
        return new RestoreBalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int minLand = Integer.MAX_VALUE, minCreature = Integer.MAX_VALUE, minCard = Integer.MAX_VALUE;
        //LAND
        for(Player player : game.getPlayers().values()){
            if(player != null){
                int count = game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), player.getId(), source.getId(), game).size();
                if(count < minLand){
                    minLand = count;
                }
            }
        }
        
        for(Player player : game.getPlayers().values()){
            if(player != null){
                TargetControlledPermanent target = new TargetControlledPermanent(minLand, minLand, new FilterControlledLandPermanent(), true);
                target.setRequired(true);
                if(target.choose(Outcome.Benefit, player.getId(), source.getId(), game)){
                    for(Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), player.getId(), source.getId(), game)){
                        if(permanent != null && !target.getTargets().contains(permanent.getId())){
                            permanent.sacrifice(source.getSourceId(), game);
                        }
                    }
                }
            }
        }
         
        
        //CREATURE
        for(Player player : game.getPlayers().values()){
            if(player != null){
                int count = game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), player.getId(), source.getId(), game).size();
                if(count < minCreature){
                    minCreature = count;
                }
            }
        }
        
        for(Player player : game.getPlayers().values()){
            if(player != null){
                TargetControlledPermanent target = new TargetControlledPermanent(minCreature, minCreature, new FilterControlledCreaturePermanent(), true);
                target.setRequired(true);
                if(target.choose(Outcome.Benefit, player.getId(), source.getId(), game)){
                    for(Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), player.getId(), source.getId(), game)){
                        if(permanent != null && !target.getTargets().contains(permanent.getId())){
                            permanent.sacrifice(source.getSourceId(), game);
                        }
                    }
                }
            }
        }
           
        //CARD IN HAND
        for(Player player : game.getPlayers().values()){
            if(player != null){
                int count = player.getHand().size();
                if(count < minCard){
                    minCard = count;
                }
            }
        }
        
        for(Player player : game.getPlayers().values()){
            if(player != null){
                TargetCardInHand target = new TargetCardInHand(minCard, new FilterCard());
                target.setRequired(true);
                if(target.choose(Outcome.Benefit, player.getId(), source.getId(), game)){
                    Cards cards =  player.getHand().copy();
                    for(UUID cardUUID : cards){
                        Card card = player.getHand().get(cardUUID, game);
                        if(card != null && !target.getTargets().contains(cardUUID)){
                            player.discard(card, source, game);
                        }
                    }
                }
            }
        }
           
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players sacrifice creatures and discard cards the same way";
    }
}
