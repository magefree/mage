package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.SubType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 * Warning, chosen type assign to original source ability, but after gain you will see another sourceId,
 * see Traveler's Cloak for workaround to trasfer settings
 *
 * @author LoneFox
 */
public enum ChosenSubtypePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    TRUE(true), FALSE(false);

    private final boolean value;

    ChosenSubtypePredicate(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(input.getSourceId(), game);
        return input.getObject().hasSubtype(subType, game) == value;
    }

    @Override
    public String toString() {
        return "Chosen subtype";
    }
}
