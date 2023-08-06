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
 * @author notgreat
 */
public class DealsCombatDamageEquippedTriggeredAbility extends TriggeredAbilityImpl {
    private boolean usedInPhase;

    public DealsCombatDamageEquippedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DealsCombatDamageEquippedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.usedInPhase = false;
        setTriggerPhrase("Whenever equipped creature deals combat damage, ");
    }

    protected DealsCombatDamageEquippedTriggeredAbility(final DealsCombatDamageEquippedTriggeredAbility ability) {
        super(ability);
        this.usedInPhase = ability.usedInPhase;
    }

    @Override
    public DealsCombatDamageEquippedTriggeredAbility copy() {
        return new DealsCombatDamageEquippedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event instanceof DamagedEvent || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!usedInPhase && event instanceof DamagedEvent &&  ((DamagedEvent) event).isCombatDamage()) {
            Permanent sourcePermanent = getSourcePermanentOrLKI(game);
            if (sourcePermanent != null && sourcePermanent.getAttachedTo() != null
                    && event.getSourceId().equals(sourcePermanent.getAttachedTo())) {
                usedInPhase = true;
                getEffects().setValue("damage", event.getAmount());
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE) {
            usedInPhase = false;
        }
        return false;
    }
}
