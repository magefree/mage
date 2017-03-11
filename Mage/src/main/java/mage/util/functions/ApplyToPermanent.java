package mage.util.functions;

import java.io.Serializable;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public abstract class ApplyToPermanent extends ApplyToMageObject implements Serializable {

    public abstract boolean apply(Game game, Permanent permanent);
}
