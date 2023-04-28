package mage.filter.predicate.permanent;

import mage.MageObjectReference;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.ConvokeWatcher;

/**
 * @author TheElk801
 */
public enum ConvokedSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    PERMANENT(-1),
    SPELL(0);
    private final int offset;

    ConvokedSourcePredicate(int offset) {
        this.offset = offset;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return ConvokeWatcher.checkConvoke(
                new MageObjectReference(input.getSource(), offset), input.getObject(), game
        );
    }
}
