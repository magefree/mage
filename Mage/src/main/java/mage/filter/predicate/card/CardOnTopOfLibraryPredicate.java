package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author JayDi85
 */

public enum CardOnTopOfLibraryPredicate implements ObjectPlayerPredicate<ObjectPlayer<Card>> {
    instance;

    @Override
    public boolean apply(ObjectPlayer<Card> input, Game game) {
        Player player = game.getPlayer(input.getObject().getOwnerId());
        if (player == null) {
            return false;
        }

        Card topCard = player.getLibrary().getFromTop(game);
        return topCard != null && topCard.getId().equals(input.getObject().getMainCard().getId());
    }
}