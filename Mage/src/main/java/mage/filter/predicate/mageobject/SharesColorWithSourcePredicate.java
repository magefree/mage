package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 * @author LevelX2
 */

public enum SharesColorWithSourcePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject sourceObject = game.getObject(input.getSourceId());
        if (sourceObject != null) {
            return input.getObject().getColor(game).shares(sourceObject.getColor(game));
        }
        return false;

    }

    @Override
    public String toString() {
        return "shares a color";
    }
}
