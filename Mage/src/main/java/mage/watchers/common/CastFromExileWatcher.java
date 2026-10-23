package mage.watchers.common;

import mage.constants.Zone;
import mage.watchers.common.CastFromZoneWatcher;

public class CastFromExileWatcher extends CastFromZoneWatcher {

    public CastFromExileWatcher() {
        super(Zone.EXILED);
    }

}
