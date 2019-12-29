package mage.abilities.common;

import mage.abilities.PlayLandAbility;
import mage.constants.Zone;

/**
 * @author JayDi85
 */
public class PlayLandAsCommanderAbility extends PlayLandAbility {

    public PlayLandAsCommanderAbility(PlayLandAbility originalAbility) {
        super(originalAbility);
        zone = Zone.COMMAND;
    }

    private PlayLandAsCommanderAbility(PlayLandAsCommanderAbility ability) {
        super(ability);
    }

    @Override
    public PlayLandAsCommanderAbility copy() {
        return new PlayLandAsCommanderAbility(this);
    }
}
