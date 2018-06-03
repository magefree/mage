
package mage.abilities.condition.common;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.command.CommandObject;

/**
 * As long as the sourceId permanent is on the battlefield, the condition is
 * true.
 *
 * @author LevelX2
 */
public enum SourceOnBattlefieldOrCommandZoneCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Iterator<CommandObject> commandZone = game.getState().getCommand().iterator();
        while (commandZone.hasNext()) {
            UUID thing = commandZone.next().getId();
            if (thing.equals(source.getSourceId())) {
                return true;
            }
        }
        return (game.getPermanent(source.getSourceId()) != null);
    }

    @Override
    public String toString() {
        return "if {this} is on the battlefield";
    }

}
