package mage.util.functions;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author noxx
 */
public abstract class ApplyToPermanent extends ApplyToMageObject implements Serializable {

    // WARNING: see comments in ApplyToMageObject
    public abstract boolean apply(Game game, Permanent permanent, Ability source, UUID targetObjectId);
}
