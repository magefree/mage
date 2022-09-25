package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TurnPhase;
import mage.game.Game;

public enum FirstCombatPhaseCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getTurn().getPhase(TurnPhase.COMBAT).getCount() == 0;
    }

    @Override
    public String toString() {
    return "it's the first combat phase of the turn";
    }
}
