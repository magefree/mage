package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class BlocksSourceTriggeredAbility extends TriggeredAbilityImpl {

    public BlocksSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public BlocksSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} blocks, ");
    }

    public BlocksSourceTriggeredAbility(final BlocksSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getBlocking() > 0;
    }

    @Override
    public BlocksSourceTriggeredAbility copy() {
        return new BlocksSourceTriggeredAbility(this);
    }
}
