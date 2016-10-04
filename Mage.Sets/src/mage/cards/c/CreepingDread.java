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
package mage.sets.shadowsoverinnistrad;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class CreepingDread extends CardImpl {

    public CreepingDread(UUID ownerId) {
        super(ownerId, 104, "Creeping Dread", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "SOI";

        // At the beginning of your upkeep, each player discards a card. Each opponent who discarded a card that shares a card type with the card you discarded loses 3 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreepingDreadEffect(), TargetController.YOU, false));
    }

    public CreepingDread(final CreepingDread card) {
        super(card);
    }

    @Override
    public CreepingDread copy() {
        return new CreepingDread(this);
    }
}

class CreepingDreadEffect extends OneShotEffect {

    public CreepingDreadEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player discards a card. Each opponent who discarded a card that shares a card type with the card you discarded loses 3 life.";
    }

    public CreepingDreadEffect(final CreepingDreadEffect effect) {
        super(effect);
    }

    @Override
    public CreepingDreadEffect copy() {
        return new CreepingDreadEffect(this);
    }

    /*
    * When a spell or ability instructs each player to discard a card, 
    starting with the player whose turn it is and proceeding in turn order, 
    each player selects a card from his or her hand without revealing it, 
    sets it aside, and then all of those cards are revealed and discarded at once.
    
    http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=409851
    */
    @Override
    public boolean apply(Game game, Ability source) {
                
        // controller discards a card - store info on card type
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            
            Set<CardType> typesChosen = new HashSet<>();
            Map<Player,Card> cardsChosen = new HashMap<>();
            if(!controller.getHand().isEmpty()) {      
                
                TargetCard controllerTarget = new TargetCard(Zone.HAND, new FilterCard());
                if(controller.choose(Outcome.Discard, controller.getHand(), controllerTarget, game)) {
                    Card card = controller.getHand().get(controllerTarget.getFirstTarget(), game);
                    if (card != null) {
                        typesChosen = new HashSet<>(card.getCardType());
                        cardsChosen.put(controller, card);
                    }
                }
            }
            
            Set<Player> opponentsAffected = new HashSet<>();
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                // opponent discards a card - if it is same card type as controller, add to opponentsAffected
                if(!opponent.getHand().isEmpty()) {
                    TargetCard target = new TargetCard(Zone.HAND, new FilterCard());
                    if(opponent.choose(Outcome.Discard, opponent.getHand(), target, game)) {
                        Card card = opponent.getHand().get(target.getFirstTarget(), game);
                        if (card != null) {                            
                            if (!typesChosen.isEmpty()) {
                                for (CardType cType : typesChosen) {
                                    for (CardType oType : card.getCardType()) {
                                        if (cType == oType) {
                                            opponentsAffected.add(opponent);
                                            break;
                                        }
                                    }
                                }
                            }    
                            
                            cardsChosen.put(opponent, card);
                        }
                    }
                }
            }
            
            // everyone discards the card at the same time
            if (!cardsChosen.isEmpty()) {                
                for (Map.Entry<Player, Card> entry : cardsChosen.entrySet()) {
                    Player player = entry.getKey();
                    Card cardChosen = entry.getValue();
                    if (player != null && cardChosen != null) {
                        player.discard(cardChosen, source, game);
                    }
                }
            }            
            
            // each opponent who discarded a card of the same type loses 3 life
            if (!opponentsAffected.isEmpty()) {
                for(Player opponent : opponentsAffected) {
                    opponent.loseLife(3, game, false);
                }
            }
            
            return true;
        }

        return false;
    }
}