package mage.abilities.costs.common;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class RevealTargetFromHandCost extends CostImpl {

    public int manaValues = 0;
    protected int numberCardsRevealed = 0;
    protected List<Card> revealedCards;

    private boolean allowNoReveal;

    public RevealTargetFromHandCost(TargetCardInHand target) {
        this.addTarget(target);
        this.allowNoReveal = target.getNumberOfTargets() == 0;
        this.text = "reveal " + target.getDescription();
        this.revealedCards = new ArrayList<>();
    }

    public RevealTargetFromHandCost(final RevealTargetFromHandCost cost) {
        super(cost);
        this.manaValues = cost.manaValues;
        this.numberCardsRevealed = cost.numberCardsRevealed;
        this.revealedCards = new ArrayList<>(cost.revealedCards);
        this.allowNoReveal = cost.allowNoReveal;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Benefit, controllerId, source.getSourceId(), source, game)) {
            manaValues = 0;
            numberCardsRevealed = 0;
            Player player = game.getPlayer(controllerId);
            Cards cards = new CardsImpl();
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card != null) {
                    manaValues += card.getManaValue();
                    numberCardsRevealed++;
                    cards.add(card);
                    revealedCards.add(card);
                }
            }
            if (numberCardsRevealed > 0) {
                MageObject baseObject = game.getBaseObject(source.getSourceId());
                player.revealCards(baseObject == null ? "card cost" : baseObject.getIdName(), cards, game);
            }
            if (targets.get(0).getNumberOfTargets() <= numberCardsRevealed) {
                paid = true; // e.g. for optional additional costs.  example: Dragonlord's Prerogative also true if 0 cards shown
                return paid;
            }
        } else if(allowNoReveal) {
            paid = true; // optional reveal with nothing to reveal.
            return paid;
        }

        paid = false;
        return paid;
    }

    public int getConvertedCosts() {
        return manaValues;
    }

    public int getNumberRevealedCards() {
        return numberCardsRevealed;
    }

    public List<Card> getRevealedCards() {
        return revealedCards;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return allowNoReveal || targets.canChoose(controllerId, source, game);
    }

    @Override
    public RevealTargetFromHandCost copy() {
        return new RevealTargetFromHandCost(this);
    }
}
