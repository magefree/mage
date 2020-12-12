
package mage.game.events;

import java.util.UUID;

import mage.abilities.Ability;
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

    public EntersTheBattlefieldEvent(Permanent target, Ability source, UUID playerId, Zone fromZone) {
        this(target, source, playerId, fromZone, EnterEventType.OTHER);
    }

    /**
     *
     * @param target
     * @param source can be null for default game actions like cheats
     * @param playerId
     * @param fromZone
     * @param enterType
     */
    public EntersTheBattlefieldEvent(Permanent target, Ability source, UUID playerId, Zone fromZone, EnterEventType enterType) {
        super(GameEvent.EventType.ENTERS_THE_BATTLEFIELD, target.getId(), source, playerId);
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
