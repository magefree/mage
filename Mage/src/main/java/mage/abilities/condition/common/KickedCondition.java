package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.KickerAbility;
import mage.game.Game;


/**
 * Describes condition when spell was kicked.
 *
 * @author LevelX2
 */
public enum KickedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return KickerAbility.getSourceObjectKickedCount(game, source) > 0;
    }

    @Override
    public String toString() {
        return "{this} was kicked";
    }

}
