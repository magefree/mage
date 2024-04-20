package mage.filter.predicate.permanent;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.SaddledMountWatcher;

/**
 * requires SaddledMountWatcher
 *
 * @author TheElk801
 */
public enum SaddledSourceThisTurnPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Ability source = input.getSource();
        if (source == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        MageObjectReference mountMOR = new MageObjectReference(
                sourceObject.getId(),
                source.getSourceObjectZoneChangeCounter(),
                game
        );
        return SaddledMountWatcher.checkIfSaddledThisTurn(
                input.getObject(), mountMOR, game
        );
    }

    @Override
    public String toString() {
        return "saddled {this} this turn";
    }
}
