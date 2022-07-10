

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX
 */
public class DealsDamageToACreatureAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private boolean combatOnly;
    private final boolean setTargetPointer;
    private final String attachedDescription;

    public DealsDamageToACreatureAttachedTriggeredAbility(Effect effect, boolean combatOnly, String attachedDescription, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.combatOnly = combatOnly;
        this.setTargetPointer = setTargetPointer;
        this.attachedDescription = attachedDescription;
    }

    public DealsDamageToACreatureAttachedTriggeredAbility(final DealsDamageToACreatureAttachedTriggeredAbility ability) {
        super(ability);
        this.combatOnly = ability.combatOnly;
        this.setTargetPointer = ability.setTargetPointer;
        this.attachedDescription = ability.attachedDescription;
    }

    @Override
    public DealsDamageToACreatureAttachedTriggeredAbility copy() {
        return new DealsDamageToACreatureAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        if (combatOnly && !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent attachment = game.getPermanent(this.getSourceId());
        if (attachment == null || !attachment.isAttachedTo(event.getSourceId())) {
            return false;
        }
        if (setTargetPointer) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                effect.setValue("damage", event.getAmount());
            }
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + attachedDescription + " deals "
                + (combatOnly ? "combat " : "") + "damage to a creature, ";
    }
}
