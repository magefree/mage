package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

public class AttacksAndIsNotBlockedTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setTargetPointer;

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever {this} attacks and isn't blocked, ");
    }

    public AttacksAndIsNotBlockedTriggeredAbility(final AttacksAndIsNotBlockedTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public AttacksAndIsNotBlockedTriggeredAbility copy() {
        return new AttacksAndIsNotBlockedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(
                    game.getCombat().getDefendingPlayerId(getSourceId(), game), game
            ));
        }
        return true;
    }
}
