package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum SourceModifiedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(permanent -> ModifiedPredicate.instance.apply(permanent, game))
                .isPresent();
    }

    @Override
    public String toString() {
        return "it's modified";
    }
}
