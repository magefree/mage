
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *    10/4/2004 : If there is only one card in your graveyard, it is exiled by the first part of the effect and you do not get to put any cards into your hand since the second part fails.
 *    10/15/2006: It does not target the cards, but it targets the opponent. If you can't target an opponent, you can't activate this ability.
 * 
 * @author Plopman
 */
public final class PhyrexianGrimoire extends CardImpl {

    public PhyrexianGrimoire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {4}, {tap}: Target opponent chooses one of the top two cards of your graveyard. Exile that card and put the other one into your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianGrimoireEffect(), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PhyrexianGrimoire(final PhyrexianGrimoire card) {
        super(card);
    }

    @Override
    public PhyrexianGrimoire copy() {
        return new PhyrexianGrimoire(this);
    }
}

class PhyrexianGrimoireEffect extends OneShotEffect {

    public PhyrexianGrimoireEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Target opponent chooses one of the top two cards of your graveyard. Exile that card and put the other one into your hand";
    }

    public PhyrexianGrimoireEffect(final PhyrexianGrimoireEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianGrimoireEffect copy() {
        return new PhyrexianGrimoireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            if(!controller.getGraveyard().isEmpty())
            {
                Cards cards = new CardsImpl();
                
                UUID card1 = null;
                UUID card2 = null;
                for (UUID cardId : controller.getGraveyard()) {
                    card2 = card1;
                    card1 = cardId;
                }
                if(card1 != null){
                    cards.add(card1);
                }
                if(card2 != null){
                    cards.add(card2);
                }
                
                TargetCard target = new TargetCard(Zone.GRAVEYARD, new FilterCard());
                target.setRequired(true);
                if(opponent.choose(Outcome.Exile, cards, target, game))
                {
                    Card card = game.getCard(target.getFirstTarget()); 
                    cards.remove(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.EXILED, source, game);
                    }
                    
                    if(!cards.isEmpty())
                    {
                        card = game.getCard(cards.iterator().next()); 
                        if (card != null) {
                            controller.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                    
                }
            }
            return true;
        }
        return false;
    }
}
