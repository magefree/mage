package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class AbstractPerformance extends CardImpl {

    public AbstractPerformance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Exile the top four cards of your library in a face-down pile, then exile the top four cards of your library in a face-up pile.
        // An opponent chooses one of those piles. Put that pile into your graveyard. Look at the cards in the other pile.
        // You may cast a spell from among them without paying its mana cost. Put the rest into your hand.
        this.getSpellAbility().addEffect(new AbstractPerformanceEffect());
    }

    private AbstractPerformance(final AbstractPerformance card) {
        super(card);
    }

    @Override
    public AbstractPerformance copy() {
        return new AbstractPerformance(this);
    }
}

class AbstractPerformanceEffect extends OneShotEffect {

    AbstractPerformanceEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top four cards of your library in a face-down pile, then exile the top four "
                + "cards of your library in a face-up pile. An opponent chooses one of those piles. Put that "
                + "pile into your graveyard. Look at the cards in the other pile. You may cast a spell from "
                + "among them without paying its mana cost. Put the rest into your hand";
    }

    private AbstractPerformanceEffect(final AbstractPerformanceEffect effect) {
        super(effect);
    }

    @Override
    public AbstractPerformanceEffect copy() {
        return new AbstractPerformanceEffect(this);
    }

    private static Cards exileTopCards(Player controller, Ability source, Game game, int amount, boolean faceDown) {
        Set<Card> cardsToExile = controller.getLibrary().getTopCards(game, amount);
        controller.moveCardsToExile(cardsToExile, source, game, !faceDown, null, "");
        Cards exiledCards = new CardsImpl();
        for (Card card : cardsToExile) {
            if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                card.setFaceDown(faceDown, game);
                exiledCards.add(card);
            }
        }
        return exiledCards;
    }

    private static void turnFaceUp(Cards cards, Game game) {
        for (UUID cardId : cards) {
            Card card = game.getCard(cardId);
            if (card != null && card.isFaceDown(game)) {
                card.setFaceDown(false, game);
            }
        }
    }

    private static Player chooseOpponent(Player controller, Ability source, Game game) {
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.isEmpty()) {
            return null;
        }
        if (opponents.size() == 1) {
            return game.getPlayer(opponents.iterator().next());
        }
        Target targetOpponent = new TargetOpponent(true);
        controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent != null) {
            game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to choose a pile");
        }
        return opponent;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Player opponent = chooseOpponent(controller, source, game);
        if (opponent == null) {
            return false;
        }

        Cards faceDownPile = exileTopCards(controller, source, game, 4, true);
        Cards faceUpPile = exileTopCards(controller, source, game, 4, false);
        controller.revealCards(source, "Face-up pile", faceUpPile, game);
        game.informPlayers(controller.getLogName() + " exiles " + faceDownPile.size() + " cards in a face-down pile");

        boolean chooseFaceDown = opponent.chooseUse(
                outcome,
                "Choose a pile to put into " + controller.getLogName() + "'s graveyard.",
                null,
                "Face-down",
                "Face-up",
                source,
                game
        );
        Cards graveyardPile = chooseFaceDown ? faceDownPile : faceUpPile;
        Cards otherPile = chooseFaceDown ? faceUpPile : faceDownPile;

        game.informPlayers(opponent.getLogName() + " chooses the "
                + (chooseFaceDown ? "face-down" : "face-up") + " pile to put into "
                + controller.getLogName() + "'s graveyard");
        turnFaceUp(graveyardPile, game);
        controller.moveCards(graveyardPile, Zone.GRAVEYARD, source, game);

        controller.lookAtCards(source, "Other pile", otherPile, game);
        CardUtil.castSpellWithAttributesForFree(controller, source, game, otherPile, StaticFilters.FILTER_CARD);
        otherPile.retainZone(Zone.EXILED, game);
        if (!otherPile.isEmpty()) {
            turnFaceUp(otherPile, game);
            controller.moveCards(otherPile, Zone.HAND, source, game);
        }
        return true;
    }
}
