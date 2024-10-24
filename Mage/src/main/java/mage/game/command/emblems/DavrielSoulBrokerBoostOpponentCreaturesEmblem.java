package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerBoostOpponentCreaturesEmblem extends Emblem {

    // You get an emblem with "Creatures you control get -1/-0."
    public DavrielSoulBrokerBoostOpponentCreaturesEmblem() {
        super("Emblem Davriel");

        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostControlledEffect(
                        -1, -0, Duration.EndOfGame,
                        StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED,
                        false
                ).setText("creatures you control get -1/-0")
        );
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerBoostOpponentCreaturesEmblem(final DavrielSoulBrokerBoostOpponentCreaturesEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerBoostOpponentCreaturesEmblem copy() {
        return new DavrielSoulBrokerBoostOpponentCreaturesEmblem(this);
    }
}
