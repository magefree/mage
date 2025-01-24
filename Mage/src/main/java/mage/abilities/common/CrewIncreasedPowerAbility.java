package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class CrewIncreasedPowerAbility extends StaticAbility {

    public CrewIncreasedPowerAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect("this creature crews Vehicles as though its power were 2 greater."));
    }

    private CrewIncreasedPowerAbility(final CrewIncreasedPowerAbility ability) {
        super(ability);
    }

    @Override
    public CrewIncreasedPowerAbility copy() {
        return new CrewIncreasedPowerAbility(this);
    }
}
