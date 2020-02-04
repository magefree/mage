
package mage.game.command;

import java.util.UUID;
import mage.MageObject;
import mage.game.Controllable;

/**
 *
 * @author Viserion, nantuko
 */
public interface CommandObject extends MageObject, Controllable {

    UUID getSourceId();

    void assignNewId();

    MageObject getSourceObject();

    @Override
    CommandObject copy();
}
