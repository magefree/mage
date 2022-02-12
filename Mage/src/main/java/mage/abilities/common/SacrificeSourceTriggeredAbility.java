package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */

public class SacrificeSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setTargetPointer;

    public SacrificeSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public SacrificeSourceTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.ALL, effect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public SacrificeSourceTriggeredAbility(final SacrificeSourceTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public SacrificeSourceTriggeredAbility copy() {
        return new SacrificeSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        if (this.setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "When you sacrifice {this}, ";
    }
}
