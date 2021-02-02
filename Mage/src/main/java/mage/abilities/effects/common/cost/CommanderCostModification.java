package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.common.PlayLandAsCommanderAbility;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

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

    private final Card commander;

    public CommanderCostModification(Card commander) {
        super(Duration.Custom, Outcome.Neutral, CostModificationType.INCREASE_COST);
        this.commander = commander;
    }

    public CommanderCostModification(final CommanderCostModification effect) {
        super(effect);
        this.commander = effect.commander;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        return commander.commanderCost(game, source, abilityToModify);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Card cardToCheck = game.getCard(abilityToModify.getSourceId()); // split/mdf cards support
        if (cardToCheck == null) {
            return false;
        }

        return commander.getId().equals(cardToCheck.getMainCard().getId())
                && (abilityToModify instanceof CastCommanderAbility
                || abilityToModify instanceof PlayLandAsCommanderAbility);
    }

    @Override
    public CommanderCostModification copy() {
        return new CommanderCostModification(this);
    }
}
