package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchAllEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801, xenohedron
 */
public class DealsCombatDamageEquippedTriggeredAbility extends TriggeredAbilityImpl {

    public DealsCombatDamageEquippedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DealsCombatDamageEquippedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever equipped creature deals combat damage, ");
    }

    protected DealsCombatDamageEquippedTriggeredAbility(final DealsCombatDamageEquippedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsCombatDamageEquippedTriggeredAbility copy() {
        return new DealsCombatDamageEquippedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ALL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        if (sourcePermanent == null || sourcePermanent.getAttachedTo() == null) {
            return false;
        }
        int amount = ((DamagedBatchAllEvent) event)
                .getEvents()
                .stream()
                .filter(DamagedEvent::isCombatDamage)
                .filter(e -> e.getAttackerId().equals(sourcePermanent.getAttachedTo()))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (amount < 1) {
            return false;
        }
        this.getEffects().setValue("damage", amount);
        return true;
    }
}
