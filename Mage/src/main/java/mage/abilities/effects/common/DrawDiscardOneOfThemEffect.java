
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DrawDiscardOneOfThemEffect extends OneShotEffect {

    private final int cardsToDraw;

    public DrawDiscardOneOfThemEffect(int cardsToDraw) {
        super(Outcome.DrawCard);
        this.cardsToDraw = cardsToDraw;
        staticText = "draw "
                + (cardsToDraw == 1 ? "a" : CardUtil.numberToText(cardsToDraw))
                + " card" + (cardsToDraw == 1 ? "" : "s")
                + ", then discard one of them";
    }

    public DrawDiscardOneOfThemEffect(final DrawDiscardOneOfThemEffect effect) {
        super(effect);
        this.cardsToDraw = effect.cardsToDraw;
    }

    @Override
    public DrawDiscardOneOfThemEffect copy() {
        return new DrawDiscardOneOfThemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards initialHand = controller.getHand().copy();
            controller.drawCards(cardsToDraw, source, game);
            Cards drawnCards = new CardsImpl(controller.getHand().copy());
            drawnCards.removeAll(initialHand);
            if (!drawnCards.isEmpty()) {
                TargetCard cardToDiscard = new TargetCard(Zone.HAND, new FilterCard("card to discard"));
                cardToDiscard.setNotTarget(true);
                if (controller.choose(Outcome.Discard, drawnCards, cardToDiscard, source, game)) {
                    Card card = controller.getHand().get(cardToDiscard.getFirstTarget(), game);
                    if (card != null) {
                        return controller.discard(card, false, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
