

package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX
 */
public class DealsCombatDamageToACreatureTriggeredAbility extends TriggeredAbilityImpl {

    private boolean setTargetPointer;

    public DealsCombatDamageToACreatureTriggeredAbility(Effect effect, boolean optional) {
            this(effect, optional, false);
    }

    public DealsCombatDamageToACreatureTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
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
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
      if (event.getSourceId().equals(this.sourceId)
        && ((DamagedCreatureEvent) event).isCombatDamage()) {
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                            effect.setValue("damage", event.getAmount());
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public String getRule() {
            return "Whenever {this} deals combat damage to a creature, " + super.getRule();
    }

}
