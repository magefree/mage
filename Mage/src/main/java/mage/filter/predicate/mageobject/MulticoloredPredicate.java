package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.cards.SplitCardHalf;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author jeffwadsworth
 */
public enum MulticoloredPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return 1 < input.getColor(game).getColorCount();
    }

    @Override
    public String toString() {
        return "Multicolored";
    }
}
