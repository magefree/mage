package mage.game.command;

import mage.MageObject;
import mage.game.Controllable;

import java.util.UUID;

/**
 * @author Viserion, nantuko
 */
public interface CommandObject extends MageObject, Controllable {

    UUID getSourceId();

    void assignNewId();

    MageObject getSourceObject();

    @Override
    CommandObject copy();
}
