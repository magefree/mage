
package mage.cards.c;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
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
public final class CreepingDread extends CardImpl {

    public CreepingDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");

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
    each player selects a card from their hand without revealing it,
    sets it aside, and then all of those cards are revealed and discarded at once.
    
    https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=409851
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
                if(opponent != null && !opponent.getHand().isEmpty()) {
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
                    if (player != null) {
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