package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class BecomesBlockedSourceTriggeredAbility extends TriggeredAbilityImpl {

    boolean setTargetPointer;

    public BecomesBlockedSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public BecomesBlockedSourceTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public BecomesBlockedSourceTriggeredAbility(final BecomesBlockedSourceTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // set the defending player via targetPointer
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(
                    new FixedTarget(game.getCombat().getDefendingPlayerId(getSourceId(), game)));
        }
        return event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} becomes blocked, " ;
    }

    @Override
    public BecomesBlockedSourceTriggeredAbility copy() {
        return new BecomesBlockedSourceTriggeredAbility(this);
    }
}
