package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author LevelX2
 */

public enum AddendumCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.isActivePlayer(source.getControllerId()) ||
                !game.getPhase().getType().isMain()) {
            return false;
        }
        if (CastFromEverywhereSourceCondition.instance.apply(game, source)) {
            return true;
        }
        Spell spell = game.getSpell(source.getSourceId());
        return spell != null && !spell.isCopy();
    }
}
