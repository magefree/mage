
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class DealtDamageToSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean enrage;
    private final boolean useValue;
    private boolean usedForCombatDamageStep;

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional, boolean enrage) {
        this(effect, optional, enrage, false);
    }

    public DealtDamageToSourceTriggeredAbility(Effect effect, boolean optional, boolean enrage, boolean useValue) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.enrage = enrage;
        this.useValue = useValue;
        this.usedForCombatDamageStep = false;
    }

    public DealtDamageToSourceTriggeredAbility(final DealtDamageToSourceTriggeredAbility ability) {
        super(ability);
        this.enrage = ability.enrage;
        this.useValue = ability.useValue;
        this.usedForCombatDamageStep = ability.usedForCombatDamageStep;
    }

    @Override
    public DealtDamageToSourceTriggeredAbility copy() {
        return new DealtDamageToSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(getSourceId())) {
            if (useValue) {
//              TODO: this ability should only trigger once for multiple creatures dealing combat damage.  
//              If the damaged creature uses the amount (e.g. Boros Reckoner), this will still trigger separately instead of all at once
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", event.getAmount());
                }
                return true;
            } else {
                if (((DamagedCreatureEvent) event).isCombatDamage()) {
                    if (!usedForCombatDamageStep) {
                        usedForCombatDamageStep = true;
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            usedForCombatDamageStep = false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return (enrage ? "<i>Enrage</i> &mdash; " : "") + "Whenever {this} is dealt damage, " + super.getRule();
    }
}
