package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * @author androosss
 */
public enum IsMainPhaseCondition implements Condition {

    YOURS(true),
    NOT_YOURS(false);

    private final boolean yours;

    IsMainPhaseCondition(boolean yours) {
        this.yours = yours;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getTurnPhaseType().isMain() && yours == game.isActivePlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "it" + (yours ? "'s" : " isn't") + " your main phase";
    }
}
