package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerBoostCreaturesEmblem extends Emblem {

    // You get an emblem with "Creatures you control get +2/+0."
    public DavrielSoulBrokerBoostCreaturesEmblem() {
        super("Emblem Davriel");

        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostControlledEffect(
                        2, 0, Duration.EndOfGame,
                        StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED,
                        false
                ).setText("creatures you control get +2/+0")
        );
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerBoostCreaturesEmblem(final DavrielSoulBrokerBoostCreaturesEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerBoostCreaturesEmblem copy() {
        return new DavrielSoulBrokerBoostCreaturesEmblem(this);
    }
}
