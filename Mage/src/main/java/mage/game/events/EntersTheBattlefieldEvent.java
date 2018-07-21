
package mage.game.events;

import java.util.UUID;

import mage.constants.Zone;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersTheBattlefieldEvent extends GameEvent {

    private final Zone fromZone;
    private Permanent target;

    public EntersTheBattlefieldEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone) {
        super(EventType.ENTERS_THE_BATTLEFIELD, target.getId(), sourceId, playerId);
        this.fromZone = fromZone;
        this.target = target;
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Permanent getTarget() {
        return target;
    }

    public void setTarget(Permanent target) {
        this.target = target;
    }

}
