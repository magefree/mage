package mage.abilities.common;

import mage.abilities.PlayLandAbility;
import mage.constants.Zone;

public class PlayLandFromGraveyardAbility extends PlayLandAbility {
    public PlayLandFromGraveyardAbility(String name){
        super(name);
        zone = Zone.GRAVEYARD;
    }
}
