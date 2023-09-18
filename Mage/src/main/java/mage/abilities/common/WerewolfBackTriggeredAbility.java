package mage.abilities.common;

import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.constants.TargetController;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class WerewolfBackTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    public WerewolfBackTriggeredAbility() {
        super(new TransformSourceEffect(), TargetController.ANY, false);
    }

    private WerewolfBackTriggeredAbility(final WerewolfBackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return TwoOrMoreSpellsWereCastLastTurnCondition.instance.apply(game, this);
    }

    @Override
    public WerewolfBackTriggeredAbility copy() {
        return new WerewolfBackTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "At the beginning of each upkeep, if a player cast two or more spells last turn, transform {this}.";
    }
}
