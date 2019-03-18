
package mage.game.events;

import java.util.UUID;
import mage.constants.EnterEventType;
import static mage.constants.EnterEventType.SELF;
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
        this(target, sourceId, playerId, fromZone, EnterEventType.OTHER);
    }

    public EntersTheBattlefieldEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone, EnterEventType enterType) {
        super(EventType.ENTERS_THE_BATTLEFIELD, target.getId(), sourceId, playerId);
        switch (enterType) {
            case SELF:
                type = EventType.ENTERS_THE_BATTLEFIELD_SELF;
                break;
            case CONTROL:
                type = EventType.ENTERS_THE_BATTLEFIELD_CONTROL;
                break;
            case COPY:
                type = EventType.ENTERS_THE_BATTLEFIELD_COPY;
                break;
        }
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
