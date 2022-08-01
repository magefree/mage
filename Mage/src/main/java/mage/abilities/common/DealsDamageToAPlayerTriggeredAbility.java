

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author jeff
 */
public class DealsDamageToAPlayerTriggeredAbility extends TriggeredAbilityImpl {
    private final boolean setTargetPointer;
    private final boolean orPlaneswalker;

    public DealsDamageToAPlayerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealsDamageToAPlayerTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        this(effect, optional, setTargetPointer, false);
    }

    public DealsDamageToAPlayerTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer, boolean orPlaneswalker) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.orPlaneswalker = orPlaneswalker;
        setTriggerPhrase("Whenever {this} deals damage to a player" + (orPlaneswalker ? " or planeswalker" : "") + ", ");
    }

    public DealsDamageToAPlayerTriggeredAbility(final DealsDamageToAPlayerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.orPlaneswalker = ability.orPlaneswalker;
    }

    @Override
    public DealsDamageToAPlayerTriggeredAbility copy() {
        return new DealsDamageToAPlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.sourceId)) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null
                    || !permanent.isPlaneswalker(game)
                    || !orPlaneswalker) {
                return false;
            }
        }
        if (setTargetPointer) {
            getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            getEffects().setValue("damage", event.getAmount());
        }
        return true;
    }
}
