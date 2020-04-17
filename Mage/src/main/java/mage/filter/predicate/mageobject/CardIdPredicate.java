package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class CardIdPredicate implements Predicate<MageObject> {

    private final UUID cardId;

    public CardIdPredicate(UUID cardId) {
        this.cardId = cardId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getId().equals(cardId);
    }

    @Override
    public String toString() {
        return "CardId (" + (cardId != null ? cardId.toString() : "null") + ')';
    }
}
