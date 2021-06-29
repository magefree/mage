package mage.game.command;

import java.util.ArrayList;

/**
 * @author Viserion
 */
public class Command extends ArrayList<CommandObject> {

    public Command() {
    }

    private Command(final Command command) {
        for (CommandObject commandObject : command) {
            add(commandObject.copy());
        }
    }

    public Command copy() {
        return new Command(this);
    }
}
