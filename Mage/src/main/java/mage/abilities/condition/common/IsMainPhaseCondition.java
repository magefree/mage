
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * @author androosss
 */
public enum IsMainPhaseCondition implements Condition {

    YOUR(true),
    ANY(false);

    private final boolean yourMainPhaseOnly;

    IsMainPhaseCondition(boolean yourMainPhaseOnly) {
        this.yourMainPhaseOnly = yourMainPhaseOnly;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getTurnPhaseType().isMain() &&
                (!yourMainPhaseOnly || game.getActivePlayerId().equals(source.getControllerId()));
    }

    @Override
    public String toString() {
        return "it's" + (yourMainPhaseOnly ? " your " : " ") + "main phase";
    }
}
