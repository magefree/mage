

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
public class DealsCombatDamageToACreatureTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setTargetPointer;

    public DealsCombatDamageToACreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DealsCombatDamageToACreatureTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever {this} deals combat damage to a creature, ");
    }

    public DealsCombatDamageToACreatureTriggeredAbility(final DealsCombatDamageToACreatureTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsCombatDamageToACreatureTriggeredAbility copy() {
        return new DealsCombatDamageToACreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null
                || !permanent.isCreature(game)
                || !event.getSourceId().equals(this.sourceId)
                || !((DamagedEvent) event).isCombatDamage()) {
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
}
