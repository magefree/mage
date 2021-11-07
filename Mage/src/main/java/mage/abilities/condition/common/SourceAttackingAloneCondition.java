package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author weirddan455
 */
public enum SourceAttackingAloneCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getCombat().attacksAlone() && game.getCombat().getAttackers().contains(source.getSourceId());
    }

    @Override
    public String toString() {
        return "{this} is attacking alone";
    }
}
