package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

import java.util.Objects;
import java.util.Optional;

/**
 * @author TheElk801
 */
public enum SourceIsRingBearerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isControlledBy(source.getControllerId()))
                .map(permanent -> permanent.isRingBearer())
                .orElse(false);
    }

    @Override
    public String toString() {
        return "{this} is your Ring-bearer";
    }
}
