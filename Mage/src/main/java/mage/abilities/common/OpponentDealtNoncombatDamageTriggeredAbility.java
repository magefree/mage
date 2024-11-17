package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.GameEvent;

/**
 * @author xenohedron
 */
public class OpponentDealtNoncombatDamageTriggeredAbility extends TriggeredAbilityImpl {

    public OpponentDealtNoncombatDamageTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect, false);
    }

    public OpponentDealtNoncombatDamageTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever an opponent is dealt noncombat damage, ");
    }

    protected OpponentDealtNoncombatDamageTriggeredAbility(final OpponentDealtNoncombatDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OpponentDealtNoncombatDamageTriggeredAbility copy() {
        return new OpponentDealtNoncombatDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant if triggers at all
        if (game.getOpponents(getControllerId()).contains(event.getTargetId())
                && !((DamagedBatchForOnePlayerEvent) event).isCombatDamage()) {
            this.getAllEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

}
