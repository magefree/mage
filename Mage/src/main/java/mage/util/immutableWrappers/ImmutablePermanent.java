package mage.util.immutableWrappers;

import mage.game.Game;
import mage.game.permanent.Permanent;

public interface ImmutablePermanent extends ImmutableObject {

    /**
     * Returns a copy of the permanent with its values reset. Used by {@link mage.game.GameImpl#copyPermanent}
     * @param game
     * @return copy of permanent with values reset
     */
    default Permanent getResetPermanent(Game game) {
        throw new UnsupportedOperationException("Unsupported method for this object");
    }
}
