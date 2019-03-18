
package mage.abilities.effects.common.cost;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
//20130711
/*903.10. A player may cast a commander he or she owns from the command zone.
 * Doing so costs that player an additional {2} for each previous time he or she cast that commander from the command zone that game.
 * */
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
        Integer castCount = (Integer) game.getState().getValue(commanderId + "_castCount");
        if (castCount > 0) {
            abilityToModify.getManaCostsToPay().add(new GenericManaCost(2 * castCount));
        }
        return true;

    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof CastCommanderAbility && abilityToModify.getSourceId().equals(commanderId);
    }

    @Override
    public CommanderCostModification copy() {
        return new CommanderCostModification(this);
    }
}
