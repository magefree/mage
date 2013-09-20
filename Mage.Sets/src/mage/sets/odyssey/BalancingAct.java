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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman (Restore Balance), cbt33
 */
public class BalancingAct extends CardImpl<BalancingAct> {

    public BalancingAct(UUID ownerId) {
        super(ownerId, 10, "Balancing Act", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        this.expansionSetCode = "ODY";

        this.color.setWhite(true);

        // Each player chooses a number of permanents he or she controls equal to the number of permanents controlled by the player who controls the fewest, then sacrifices the rest. Each player discards cards the same way.
        this.getSpellAbility().addEffect(new BalancingActEffect());
    }

    public BalancingAct(final BalancingAct card) {
        super(card);
    }

    @Override
    public BalancingAct copy() {
        return new BalancingAct(this);
    }
}

class BalancingActEffect extends OneShotEffect<BalancingActEffect> {

   
    public BalancingActEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players sacrifice creatures and discard cards the same way";
    }

    public BalancingActEffect(final mage.sets.odyssey.BalancingActEffect effect) {
        super(effect);
    }

    @Override
    public mage.sets.odyssey.BalancingActEffect copy() {
        return new mage.sets.odyssey.BalancingActEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int minPermanent = Integer.MAX_VALUE, minCard = Integer.MAX_VALUE;
            // count minimal permanets
            for(UUID playerId : controller.getInRange()){
                Player player = game.getPlayer(playerId);
                if(player != null){
                    int count = game.getBattlefield().getActivePermanents(new FilterControlledPermanent(), player.getId(), source.getId(), game).size();
                    if(count < minPermanent){
                        minPermanent = count;
                    }
                }
            }
            // sacrifice permanents over the minimum
            for(UUID playerId : controller.getInRange()){
                Player player = game.getPlayer(playerId);
                if(player != null){
                    TargetControlledPermanent target = new TargetControlledPermanent(minPermanent, minPermanent, new FilterControlledPermanent(), true);
                    target.setRequired(true);
                    if(target.choose(Outcome.Benefit, player.getId(), source.getId(), game)){
                        for(Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledPermanent(), player.getId(), source.getId(), game)){
                            if(permanent != null && !target.getTargets().contains(permanent.getId())){
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
            }

            // count minimal cards in hand
            for(UUID playerId : controller.getInRange()){
                Player player = game.getPlayer(playerId);
                if(player != null){
                    int count = player.getHand().size();
                    if(count < minCard){
                        minCard = count;
                    }
                }
            }
            
            // discard cards over the minimum
            for(UUID playerId : controller.getInRange()){
                Player player = game.getPlayer(playerId);
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
        return false;
    }

}
