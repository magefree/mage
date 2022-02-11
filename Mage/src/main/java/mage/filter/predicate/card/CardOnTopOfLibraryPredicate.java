package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author JayDi85
 */

public enum CardOnTopOfLibraryPredicate implements ObjectSourcePlayerPredicate<Card> {
    YOUR,
    ANY;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {

        Player player;
        switch (this) {
            case YOUR:
                player = game.getPlayer(input.getPlayerId());
                break;

            case ANY:
            default:
                player = game.getPlayer(input.getObject().getOwnerId());
                break;
        }

        if (player == null) {
            return false;
        }

        Card topCard = player.getLibrary().getFromTop(game);
        return topCard != null && topCard.getId().equals(input.getObject().getMainCard().getId());
    }
}