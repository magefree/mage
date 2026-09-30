package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.MageObjectReference;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.watchers.common.SaddledMountWatcher;

/**
 * @author LevelX2
 */
public enum SaddledPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return SaddledMountWatcher.hasBeenSaddledThisTurn(new MageObjectReference(input, game), game);
    }

    @Override
    public String toString() {
        return "Saddled";
    }
}
