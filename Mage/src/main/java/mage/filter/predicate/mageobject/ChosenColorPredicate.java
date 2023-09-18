package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.ObjectColor;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum ChosenColorPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    TRUE(true), FALSE(false);

    private final boolean value;

    ChosenColorPredicate(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        ObjectColor color = (ObjectColor) game.getState().getValue(input.getSourceId() + "_color");
        return color != null && input.getObject().getColor(game).shares(color) == value;
    }

    @Override
    public String toString() {
        return "Chosen subtype";
    }
}
