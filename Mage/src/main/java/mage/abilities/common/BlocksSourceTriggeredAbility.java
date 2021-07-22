
package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class BlocksSourceTriggeredAbility extends TriggeredAbilityImpl {

    private boolean setTargetPointer;
    private boolean once = false;

    public BlocksSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public BlocksSourceTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        this(effect, optional, setTargetPointer, false);
    }

    public BlocksSourceTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer, boolean once) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.once = once;
    }

    public BlocksSourceTriggeredAbility(final BlocksSourceTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.once = ability.once;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            if (setTargetPointer) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When" + (once ? "" : "ever") + " {this} blocks" + (setTargetPointer ? " a creature, " : ", ") ;
    }

    @Override
    public BlocksSourceTriggeredAbility copy() {
        return new BlocksSourceTriggeredAbility(this);
    }
}
