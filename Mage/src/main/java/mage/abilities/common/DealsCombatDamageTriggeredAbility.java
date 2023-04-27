package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;

/**
 * This triggers only once for each phase the source creature deals damage.
 * So a creature blocked by two creatures and dealing damage to both blockers in the same
 * combat damage step triggers only once.
 *
 * @author LevelX
 */
public class DealsCombatDamageTriggeredAbility extends TriggeredAbilityImpl {

    private boolean usedInPhase;

    public DealsCombatDamageTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.usedInPhase = false;
        setTriggerPhrase("Whenever {this} deals combat damage, ");
    }

    public DealsCombatDamageTriggeredAbility(final DealsCombatDamageTriggeredAbility ability) {
        super(ability);
        this.usedInPhase = ability.usedInPhase;
    }

    @Override
    public DealsCombatDamageTriggeredAbility copy() {
        return new DealsCombatDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event instanceof DamagedEvent || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedEvent
                && !usedInPhase
                && event.getSourceId().equals(this.sourceId)
                && ((DamagedEvent) event).isCombatDamage()) {
            usedInPhase = true;
            getEffects().setValue("damage", event.getAmount());
            return true;
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE) {
            usedInPhase = false;
        }
        return false;
    }
}
