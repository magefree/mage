
package mage.game.command;

import java.util.UUID;
import mage.MageObject;

/**
 *
 * @author Viserion, nantuko
 */
public interface CommandObject extends MageObject {

    UUID getSourceId();

    UUID getControllerId();

    void assignNewId();

    MageObject getSourceObject();

    @Override
    CommandObject copy();
}
