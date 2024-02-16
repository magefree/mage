package mage.constants;

import mage.abilities.Ability;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 * @author awjackson
 */

public enum PutCards {
    HAND(Outcome.DrawCard, Zone.HAND, "into your hand"),
    GRAVEYARD(Outcome.Discard, Zone.GRAVEYARD, "into your graveyard"),
    BATTLEFIELD(Outcome.PutCardInPlay, Zone.BATTLEFIELD, "onto the battlefield"),
    BATTLEFIELD_TAPPED(Outcome.PutCardInPlay, Zone.BATTLEFIELD, "onto the battlefield tapped"),
    BATTLEFIELD_TRANSFORMED(Outcome.PutCardInPlay, Zone.BATTLEFIELD, "onto the battlefield transformed"),
    EXILED(Outcome.Exile, Zone.EXILED, "into exile"), // may need special case code to generate correct text
    TOP_OR_BOTTOM(Outcome.Benefit, Zone.LIBRARY, "on the top or bottom of your library"),
    TOP_ANY(Outcome.Benefit, Zone.LIBRARY, "on top of your library", " in any order"),
    BOTTOM_ANY(Outcome.Benefit, Zone.LIBRARY, "on the bottom of your library", " in any order"),
    BOTTOM_RANDOM(Outcome.Benefit, Zone.LIBRARY, "on the bottom of your library", " in a random order"),
    SHUFFLE(Outcome.Benefit, Zone.LIBRARY, "shuffled into your library"); // may need special case code to generate correct text

    private final Outcome outcome;
    private final Zone zone;
    private final String messageYour;
    private final String messageOwner;
    private final String order;

    PutCards(Outcome outcome, Zone zone, String message) {
        this(outcome, zone, message, "");
    }

    PutCards(Outcome outcome, Zone zone, String message, String order) {
        this.outcome = outcome;
        this.zone = zone;
        this.messageYour = message;
        this.messageOwner = message.replace("your", "its owner's");
        this.order = order;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Zone getZone() {
        return zone;
    }

    public String getMessage(boolean owner, boolean withOrder) {
        String message = owner ? messageOwner : messageYour;
        return withOrder ? message + order : message;
    }

    public boolean moveCard(Player player, Card card, Ability source, Game game, String description) {
        switch (this) {
            case TOP_OR_BOTTOM:
                if (player.chooseUse(Outcome.Neutral,
                        "Put the " + description + " on the top or bottom of its owner's library?",
                        null, "Top", "Bottom", source, game
                )) {
                    return player.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, true);
                } else {
                    return player.putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, true);
                }
            case TOP_ANY:
                return player.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, true);
            case BOTTOM_ANY:
                return player.putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, true);
            case BOTTOM_RANDOM:
                return player.putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, false);
            case BATTLEFIELD_TAPPED:
                return player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
            case SHUFFLE:
                return player.shuffleCardsToLibrary(card, game, source);
            case BATTLEFIELD_TRANSFORMED:
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
            case BATTLEFIELD:
            case EXILED:
            case HAND:
            case GRAVEYARD:
                return player.moveCards(card, this.zone, source, game);
            default:
                throw new UnsupportedOperationException("Missing case for " + this.name() + "in PutCards.moveCard");
        }
    }

    public boolean moveCards(Player player, Cards cards, Ability source, Game game) {
        switch (this) {
            case TOP_OR_BOTTOM:
                throw new UnsupportedOperationException("PutCards.TOP_OR_BOTTOM does not support moving multiple cards");
            case TOP_ANY:
                return player.putCardsOnTopOfLibrary(cards, game, source, true);
            case BOTTOM_ANY:
                return player.putCardsOnBottomOfLibrary(cards, game, source, true);
            case BOTTOM_RANDOM:
                return player.putCardsOnBottomOfLibrary(cards, game, source, false);
            case BATTLEFIELD_TAPPED:
                return player.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
            case SHUFFLE:
                return player.shuffleCardsToLibrary(cards, game, source);
            case BATTLEFIELD_TRANSFORMED:
                cards.stream().forEach(uuid -> game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + uuid, Boolean.TRUE));
            case BATTLEFIELD:
            case EXILED:
            case HAND:
            case GRAVEYARD:
                return player.moveCards(cards, this.zone, source, game);
            default:
                throw new UnsupportedOperationException("Missing case for " + this.name() + "in PutCards.moveCards");
        }
    }
}
