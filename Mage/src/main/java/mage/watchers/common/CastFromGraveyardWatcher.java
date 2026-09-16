
package mage.watchers.common;

import mage.constants.Zone;
import mage.watchers.common.CastFromZoneWatcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CastFromGraveyardWatcher extends CastFromZoneWatcher {

    public CastFromGraveyardWatcher() {
        super(Zone.GRAVEYARD);
    }

    public boolean spellWasCastFromGraveyard(UUID sourceId, int zcc) {
        return this.spellWasCastFromZone(sourceId, zcc);
    }

}
