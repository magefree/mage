package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.CardType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 *
 * @author jmlundeen
 */
public enum ChosenCardTypePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    TRUE(true), FALSE(false);

    private final boolean value;

    ChosenCardTypePredicate(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        Object savedType = game.getState().getValue(input.getSourceId() + "_type");
        if (!(savedType instanceof String)) {
            return false;
        }
        CardType cardType = CardType.fromString((String) savedType);
        return input.getObject().getCardType(game).contains(cardType) == value;
    }

    @Override
    public String toString() {
        return "Chosen card type";
    }
}
