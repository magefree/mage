package mage.actions.impl;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Base class for mage actions.
 *
 * @author ayratn
 */
public abstract class MageAction {

    /**
     * Execute action.
     *
     *
     * @param source
     * @param game Game context.
     * @return
     */
    public abstract int doAction(Ability source, final Game game);

    @Override
    public String toString() {
        return "";
    }
}
