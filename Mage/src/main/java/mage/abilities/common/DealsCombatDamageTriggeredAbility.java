package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;

/**
 * This triggers only once for each combat damage step the source creature deals damage.
 * So a creature blocked by two creatures and dealing damage to both blockers in the same
 * combat damage step triggers only once.
 *
 * @author LevelX, xenohedron
 */
public class DealsCombatDamageTriggeredAbility extends TriggeredAbilityImpl {

    private boolean usedThisStep;

    public DealsCombatDamageTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.usedThisStep = false;
        setTriggerPhrase(getWhen() + "{this} deals combat damage, ");
        this.replaceRuleText = true;
    }

    protected DealsCombatDamageTriggeredAbility(final DealsCombatDamageTriggeredAbility ability) {
        super(ability);
        this.usedThisStep = ability.usedThisStep;
    }

    @Override
    public DealsCombatDamageTriggeredAbility copy() {
        return new DealsCombatDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event instanceof DamagedBatchEvent || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE) {
            usedThisStep = false; // clear before damage
            return false;
        }
        if (usedThisStep || !(event instanceof DamagedBatchEvent)) {
            return false; // trigger only on DamagedEvent and if not yet triggered this step
        }
        int amount = ((DamagedBatchEvent) event)
                .getEvents()
                .stream()
                .filter(DamagedEvent::isCombatDamage)
                .filter(e -> e.getAttackerId().equals(this.sourceId))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (amount < 1) {
            return false;
        }
        usedThisStep = true;
        this.getEffects().setValue("damage", amount);
        // TODO: this value saved will not be correct if both permanent and player damaged by a single creature
        // Need to rework engine logic to fire a single DamagedBatchEvent including both permanents and players
        // Only Aisha of Sparks and Smoke is currently affected.
        return true;
    }
}
