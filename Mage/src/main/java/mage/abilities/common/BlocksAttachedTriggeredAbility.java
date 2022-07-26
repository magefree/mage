
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author garnold
 */
public class BlocksAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setFixedTargetPointer;
    private final String attachedDescription;
    private boolean setFixedTargetPointerToBlocked;

    public BlocksAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional) {
        this(effect, attachedDescription, optional, false);
    }

    public BlocksAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setFixedTargetPointer = setFixedTargetPointer;
        this.attachedDescription = attachedDescription;
    }

    public BlocksAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean setFixedTargetPointer, boolean setFixedTargetPointerToBlocked) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setFixedTargetPointer = setFixedTargetPointer;
        this.attachedDescription = attachedDescription;
        this.setFixedTargetPointerToBlocked = setFixedTargetPointerToBlocked;
    }

    public BlocksAttachedTriggeredAbility(final BlocksAttachedTriggeredAbility ability) {
        super(ability);
        this.setFixedTargetPointer = ability.setFixedTargetPointer;
        this.attachedDescription = ability.attachedDescription;
        this.setFixedTargetPointerToBlocked = ability.setFixedTargetPointerToBlocked;
    }

    @Override
    public BlocksAttachedTriggeredAbility copy() {
        return new BlocksAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent p = game.getPermanent(event.getSourceId());
        if (p != null && p.getAttachments().contains(this.getSourceId())) {
            if (setFixedTargetPointer) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
            }
            if (setFixedTargetPointerToBlocked) {
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
        return "Whenever " + attachedDescription + " creature blocks" + (setFixedTargetPointerToBlocked ? " a creature, " : ", ") ;
    }
}
