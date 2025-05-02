package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class CrewSaddleIncreasedPowerAbility extends StaticAbility {

    public CrewSaddleIncreasedPowerAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect("this creature saddles Mounts and crews Vehicles as though its power were 2 greater."));
    }

    private CrewSaddleIncreasedPowerAbility(final CrewSaddleIncreasedPowerAbility ability) {
        super(ability);
    }

    @Override
    public CrewSaddleIncreasedPowerAbility copy() {
        return new CrewSaddleIncreasedPowerAbility(this);
    }
}
