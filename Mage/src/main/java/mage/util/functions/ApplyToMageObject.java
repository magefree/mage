
package mage.util.functions;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public abstract class ApplyToMageObject {

    public abstract boolean apply(Game game, MageObject mageObject, Ability source, UUID targetObjectId);
}
