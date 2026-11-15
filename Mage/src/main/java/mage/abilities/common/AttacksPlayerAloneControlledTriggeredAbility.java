package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author muz
 */
public class AttacksPlayerAloneControlledTriggeredAbility extends TriggeredAbilityImpl {

    public AttacksPlayerAloneControlledTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever a creature you control attacks a player alone, ");
    }

    protected AttacksPlayerAloneControlledTriggeredAbility(final AttacksPlayerAloneControlledTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AttacksPlayerAloneControlledTriggeredAbility copy() {
        return new AttacksPlayerAloneControlledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        UUID defenderId = game.getCombat().getDefenderId(permanent.getId());
        if (defenderId == null || game.getPlayer(defenderId) == null) {
            return false;
        }
        if (game.getCombat().getAttackers().stream()
                .map(game.getCombat()::getDefenderId)
                .filter(defenderId::equals)
                .count() != 1) {
            return false;
        }
        this.getAllEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}
