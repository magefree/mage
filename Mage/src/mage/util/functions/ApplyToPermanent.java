package mage.util.functions;

import java.io.Serializable;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public abstract class ApplyToPermanent implements Serializable {

    public abstract Boolean apply(Game game, Permanent permanent);
}
