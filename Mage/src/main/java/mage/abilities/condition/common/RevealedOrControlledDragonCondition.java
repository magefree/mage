package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.RevealDragonFromHandCost;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 * @author TheElk801
 */
public enum RevealedOrControlledDragonCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return source
                    .getCosts()
                    .stream()
                    .filter(RevealDragonFromHandCost.class::isInstance)
                    .map(RevealDragonFromHandCost.class::cast)
                    .anyMatch(RevealDragonFromHandCost::isRevealedOrControlled);
        }
        DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher
                = game.getState().getWatcher(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class);
        return watcher != null && watcher.checkCondition(source, game);
    }
}
