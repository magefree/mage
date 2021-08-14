package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum HistoricPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.isHistoric(game);
    }

    @Override
    public String toString() {
        return "Historic";
    }
}
