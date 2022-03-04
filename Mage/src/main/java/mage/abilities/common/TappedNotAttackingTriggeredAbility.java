package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class TappedNotAttackingTriggeredAbility extends TriggeredAbilityImpl {

    public TappedNotAttackingTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    private TappedNotAttackingTriggeredAbility(final TappedNotAttackingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TappedNotAttackingTriggeredAbility copy() {
        return new TappedNotAttackingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getFlag()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isCreature(game)
                && game.getOpponents(permanent.getControllerId()).contains(getControllerId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature an opponent controls becomes tapped, " +
                "if it isn't being declared as an attacker, ";
    }
}
