package mage.filter.predicate.permanent;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.SaddledMountWatcher;

import java.util.Optional;

/**
 * requires SaddledMountWatcher
 *
 * @author TheElk801
 */
public enum SaddledSourceThisTurnPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        // TODO: remove temporary logs
        int zccSource = Optional
                .of(input.getSource())
                .map(Ability::getSourceObjectZoneChangeCounter)
                .orElse(-1);
        int zccPermanentOrLKI = Optional
                .of(input.getSource())
                .map(a -> a.getSourcePermanentOrLKI(game))
                .map(p -> p.getZoneChangeCounter(game))
                .orElse(-1);
        int zccObject = Optional
                .of(input.getSource())
                .map(a -> a.getSourceObject(game))
                .map(o -> o.getZoneChangeCounter(game))
                .orElse(-1);
        int zccSourceOfSource = Optional
                .of(input.getSource())
                .map(Ability::getSourceId)
                .map(game::getObject)
                .map(o -> o.getZoneChangeCounter(game))
                .orElse(-1);
        System.out.println("zccs: " + zccSource + " - " + zccPermanentOrLKI + " - " + zccObject + " - " + zccSourceOfSource);
        System.out.println("ids: " + input.getSource().getId() + " - " + input.getSourceId());
        return SaddledMountWatcher.checkIfSaddledThisTurn(
                input.getObject(), new MageObjectReference(input.getSourceId(), input.getSource().getSourceObjectZoneChangeCounter(), game), game
        );
    }

    @Override
    public String toString() {
        return "saddled {this} this turn";
    }
}
