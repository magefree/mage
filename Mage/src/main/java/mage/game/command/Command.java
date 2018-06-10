

package mage.game.command;

import java.util.ArrayList;

/**
 *
 * @author Viserion
 */
public class Command extends ArrayList<CommandObject> {

    public Command () {}

    public Command(final Command command) {
        addAll(command);
    }

    /*public void checkTriggers(GameEvent event, Game game) {
        for (CommandObject commandObject: this) {
            commandObject.checkTriggers(event, game);
        }
    }*/

    public Command copy() {
        return new Command(this);
    }
}
