package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePermanentEvent;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;

/**
 * @author xenohedron
 */
public class DealtCombatDamageToSourceTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    public DealtCombatDamageToSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} is dealt combat damage, ");
        this.withRuleTextReplacement(true);
    }

    protected DealtCombatDamageToSourceTriggeredAbility(final DealtCombatDamageToSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealtCombatDamageToSourceTriggeredAbility copy() {
        return new DealtCombatDamageToSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant if triggers at all
        if (!getSourceId().equals(event.getTargetId()) || !((DamagedBatchForOnePermanentEvent) event).isCombatDamage()) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }
}
