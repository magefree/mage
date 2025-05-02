package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

public enum CreaturesYouControlDiedCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getState()
                .getWatcher(CreaturesDiedWatcher.class)
                .getAmountOfCreaturesDiedThisTurnByController(sourceAbility.getControllerId());
    }

    @Override
    public CreaturesYouControlDiedCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature that died under your control this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
