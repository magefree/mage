
// author jeffwadsworth
package mage.abilities.costs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

public class RevealTargetFromHandCost extends CostImpl {

    public int convertedManaCosts = 0;
    protected int numberCardsRevealed = 0;
    protected List<Card> revealedCards;

    public RevealTargetFromHandCost(TargetCardInHand target) {
        this.addTarget(target);
        this.text = (target.getNumberOfTargets() == 0 ? "You may reveal " : "Reveal ") + target.getTargetName();
        this.revealedCards = new ArrayList<>();
    }

    public RevealTargetFromHandCost(final RevealTargetFromHandCost cost) {
        super(cost);
        this.convertedManaCosts = cost.convertedManaCosts;
        this.numberCardsRevealed = cost.numberCardsRevealed;
        this.revealedCards = new ArrayList<>(cost.revealedCards);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Benefit, controllerId, sourceId, game)) {
            convertedManaCosts = 0;
            numberCardsRevealed = 0;
            Player player = game.getPlayer(controllerId);
            Cards cards = new CardsImpl();
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card != null) {
                    convertedManaCosts += card.getConvertedManaCost();
                    numberCardsRevealed++;
                    cards.add(card);
                    revealedCards.add(card);
                }
            }
            if (numberCardsRevealed > 0) {
                MageObject baseObject = game.getBaseObject(sourceId);
                player.revealCards(baseObject == null ? "card cost" : baseObject.getIdName(), cards, game);
            }
            if (targets.get(0).getNumberOfTargets() <= numberCardsRevealed) {
                paid = true; // e.g. for optional additional costs.  example: Dragonlord's Prerogative also true if 0 cards shown
                return paid;
            }
        }
        paid = false;
        return paid;
    }

    public int getConvertedCosts() {
        return convertedManaCosts;
    }

    public int getNumberRevealedCards() {
        return numberCardsRevealed;
    }

    public List<Card> getRevealedCards() {
        return revealedCards;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public RevealTargetFromHandCost copy() {
        return new RevealTargetFromHandCost(this);
    }
}
