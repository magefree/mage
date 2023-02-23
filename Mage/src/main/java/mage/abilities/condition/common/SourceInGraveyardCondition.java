package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public enum SourceInGraveyardCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId())
                .getGraveyard()
                .stream()
                .anyMatch(uuid -> uuid.equals(source.getSourceId()));
    }

    @Override
    public String toString() {
        return "if {this} is in its owner's graveyard";
    }
}