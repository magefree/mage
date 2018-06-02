
package mage.filter.predicate.mageobject;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public class ChosenSubtypePredicate implements Predicate<MageObject> {

    private final UUID cardID;

    public ChosenSubtypePredicate(UUID cardID) {
        this.cardID = cardID;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChoosenCreatureType(cardID, game);
        return input.hasSubtype(subType, game);
    }

    @Override
    public String toString() {
        return "Chosen subtype";
    }
}
