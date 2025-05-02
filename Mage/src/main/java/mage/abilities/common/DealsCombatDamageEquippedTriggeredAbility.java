package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchBySourceEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801, xenohedron
 */
public class DealsCombatDamageEquippedTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedEvent> {

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
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedBatchBySourceEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        if (sourcePermanent == null || !event.getSourceId().equals(sourcePermanent.getAttachedTo())) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }
}
