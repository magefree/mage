
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.SubType;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public class ChosenSubtypePredicate implements ObjectPlayerPredicate<ObjectSourcePlayer<MageObject>> {

    public ChosenSubtypePredicate() {
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(input.getSourceId(), game);
        return input.getObject().hasSubtype(subType, game);
    }

    @Override
    public String toString() {
        return "Chosen subtype";
    }
}
