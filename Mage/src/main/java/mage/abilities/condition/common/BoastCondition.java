package mage.abilities.condition.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BoastAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author weirddan455
 */
public enum BoastCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield(source.getSourceId());
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (source instanceof BoastAbility && creature != null && watcher != null) {
            BoastAbility ability = (BoastAbility) source;
            return ability.hasMoreActivationsThisTurn(game) &&
                    watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(creature, game));
        }
        return false;
    }
}
