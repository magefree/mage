package mage.util.functions;

import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public class EmptyApplyToPermanent extends ApplyToPermanent {

    public Boolean apply(Game game, Permanent permanent) {
        // do nothing
        return true;
    }
}
