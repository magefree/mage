package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;

/**
 * @author xenohedron
 */

public class DealsDamageSourceTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedEvent> {

    public DealsDamageSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DealsDamageSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} deals damage, ");
    }

    protected DealsDamageSourceTriggeredAbility(final DealsDamageSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageSourceTriggeredAbility copy() {
        return new DealsDamageSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant
        if (event.getSourceId().equals(this.getSourceId())) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }
}
