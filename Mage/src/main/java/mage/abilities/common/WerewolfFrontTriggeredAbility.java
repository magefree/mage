package mage.abilities.common;

import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.constants.TargetController;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class WerewolfFrontTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    public WerewolfFrontTriggeredAbility() {
        super(new TransformSourceEffect(), TargetController.ANY, false);
    }

    private WerewolfFrontTriggeredAbility(final WerewolfFrontTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return NoSpellsWereCastLastTurnCondition.instance.apply(game, this);
    }

    @Override
    public WerewolfFrontTriggeredAbility copy() {
        return new WerewolfFrontTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "At the beginning of each upkeep, if no spells were cast last turn, transform {this}.";
    }
}
