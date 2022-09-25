package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class DealsCombatDamageEquippedTriggeredAbility extends TriggeredAbilityImpl {

    public DealsCombatDamageEquippedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DealsCombatDamageEquippedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever equipped creature deals combat damage, ");
    }

    public DealsCombatDamageEquippedTriggeredAbility(final DealsCombatDamageEquippedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsCombatDamageEquippedTriggeredAbility copy() {
        return new DealsCombatDamageEquippedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        if (sourcePermanent == null || sourcePermanent.getAttachedTo() == null) {
            return false;
        }
        int amount = ((DamagedBatchEvent) event)
                .getEvents()
                .stream()
                .filter(DamagedEvent::isCombatDamage)
                .filter(e -> e.getSourceId().equals(sourcePermanent.getAttachedTo()))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (amount < 1) {
            return false;
        }
        this.getEffects().setValue("damage", amount);
        return true;
    }
}
