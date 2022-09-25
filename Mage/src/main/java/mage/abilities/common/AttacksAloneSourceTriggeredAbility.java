package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 */
public class AttacksAloneSourceTriggeredAbility extends TriggeredAbilityImpl {

    public AttacksAloneSourceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} attacks alone, ");
    }

    protected AttacksAloneSourceTriggeredAbility(final AttacksAloneSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AttacksAloneSourceTriggeredAbility copy() {
        return new AttacksAloneSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getSourceId().equals(event.getSourceId()) || !game.getCombat().attacksAlone()) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(game.getCombat().getDefendingPlayerId(getSourceId(), game)));
        return true;
    }
}
