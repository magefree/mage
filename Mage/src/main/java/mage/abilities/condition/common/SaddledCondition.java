package mage.abilities.condition.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.SaddledMountWatcher;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum SaddledCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(p -> SaddledMountWatcher.hasBeenSaddledThisTurn(new MageObjectReference(p, game), game))
                .orElse(false);
    }
}
