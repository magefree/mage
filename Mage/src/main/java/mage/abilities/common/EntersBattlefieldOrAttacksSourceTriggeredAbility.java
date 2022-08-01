
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Styxo
 */
public class EntersBattlefieldOrAttacksSourceTriggeredAbility extends TriggeredAbilityImpl {

    public EntersBattlefieldOrAttacksSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EntersBattlefieldOrAttacksSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} enters the battlefield or attacks, ");
    }

    public EntersBattlefieldOrAttacksSourceTriggeredAbility(final EntersBattlefieldOrAttacksSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldOrAttacksSourceTriggeredAbility copy() {
        return new EntersBattlefieldOrAttacksSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId());
    }
}
