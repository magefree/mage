package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

public class AttacksAndIsNotBlockedTriggeredAbility extends TriggeredAbilityImpl {

    private final SetTargetPointer setTargetPointer;

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, SetTargetPointer.NONE);
    }

    public AttacksAndIsNotBlockedTriggeredAbility(Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever {this} attacks and isn't blocked, ");
        this.withRuleTextReplacement(true); // default true to replace "{this}" with "it"
    }

    protected AttacksAndIsNotBlockedTriggeredAbility(final AttacksAndIsNotBlockedTriggeredAbility ability) {
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
        switch (setTargetPointer) {
            case NONE:
                break;
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(
                        game.getCombat().getDefendingPlayerId(getSourceId(), game), game
                ));
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage: not supported setTargetPointer");
        }
        return true;
    }
}
