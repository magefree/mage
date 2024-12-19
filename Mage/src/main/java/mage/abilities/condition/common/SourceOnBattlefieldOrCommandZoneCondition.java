package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.command.CommandObject;

import java.util.UUID;

public enum SourceOnBattlefieldOrCommandZoneCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        for (CommandObject commandObject : game.getState().getCommand()) {
            UUID thing = commandObject.getId();
            if (thing.equals(source.getSourceId())) {
                return true;
            }
        }
        return (game.getPermanent(source.getSourceId()) != null);
    }

    @Override
    public String toString() {
        return "if {this} is in the command zone or on the battlefield";
    }

}
