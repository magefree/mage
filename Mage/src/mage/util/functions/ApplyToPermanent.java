package mage.util.functions;

import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public abstract class ApplyToPermanent {

    public abstract Boolean apply(Game game, Permanent permanent);
}
