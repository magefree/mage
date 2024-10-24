package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerTriggeredLoseLifeEmblem extends Emblem {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);

    // You get an emblem with "At the beginning of your upkeep, you lose 1 life for each creature you control."
    public DavrielSoulBrokerTriggeredLoseLifeEmblem() {
        super("Emblem Davriel");

        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.COMMAND, new LoseLifeSourceControllerEffect(xValue), TargetController.YOU, false);
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerTriggeredLoseLifeEmblem(final DavrielSoulBrokerTriggeredLoseLifeEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerTriggeredLoseLifeEmblem copy() {
        return new DavrielSoulBrokerTriggeredLoseLifeEmblem(this);
    }
}
