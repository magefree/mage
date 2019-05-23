package mage.abilities.common;

import mage.abilities.PlayLandAbility;
import mage.abilities.costs.common.CommanderAdditionalCost;
import mage.constants.Zone;

/**
 * @author JayDi85
 */
public class PlayLandAsCommanderAbility extends PlayLandAbility {

    public PlayLandAsCommanderAbility(PlayLandAbility originalAbility) {
        super(originalAbility);
        zone = Zone.COMMAND;

        // extra cost
        this.addCost(new CommanderAdditionalCost());
    }

    private PlayLandAsCommanderAbility(PlayLandAsCommanderAbility ability) {
        super(ability);
    }

    @Override
    public PlayLandAsCommanderAbility copy() {
        return new PlayLandAsCommanderAbility(this);
    }
}
