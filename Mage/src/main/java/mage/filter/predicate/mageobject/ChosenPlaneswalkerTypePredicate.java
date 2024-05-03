package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.ChoosePlaneswalkerTypeEffect;
import mage.constants.SubType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 *
 * @author jimga150
 */
public enum ChosenPlaneswalkerTypePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    TRUE(true), FALSE(false);

    private final boolean value;

    ChosenPlaneswalkerTypePredicate(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        SubType subType = ChoosePlaneswalkerTypeEffect.getChosenPlaneswalkerType(input.getSourceId(), game);
        return input.getObject().hasSubtype(subType, game) == value;
    }

    @Override
    public String toString() {
        return "Chosen Planeswalker type";
    }
}
