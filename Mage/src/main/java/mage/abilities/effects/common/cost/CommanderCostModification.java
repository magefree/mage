package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.common.PlayLandAsCommanderAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.ManaUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author Plopman
 */
    /*
    903.8. A player may cast a commander they own from the command zone. A commander cast from the
    command zone costs an additional {2} for each previous time the player casting it has cast it from
    the command zone that game. This additional cost is informally known as the “commander tax.”
     */

// cast from hand like Remand do not increase commander tax

public class CommanderCostModification extends CostModificationEffectImpl {

    private final UUID commanderId;

    public CommanderCostModification(UUID commanderId) {
        super(Duration.Custom, Outcome.Neutral, CostModificationType.INCREASE_COST);
        this.commanderId = commanderId;
    }

    public CommanderCostModification(final CommanderCostModification effect) {
        super(effect);
        this.commanderId = effect.commanderId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        int castCount = watcher.getPlaysCount(commanderId);
        if (castCount > 0) {
            abilityToModify.getManaCostsToPay().add(ManaUtil.createManaCost(2 * castCount, false));
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return commanderId.equals(abilityToModify.getSourceId())
                && (abilityToModify instanceof CastCommanderAbility || abilityToModify instanceof PlayLandAsCommanderAbility);
    }

    @Override
    public CommanderCostModification copy() {
        return new CommanderCostModification(this);
    }
}
