package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Hiddevb
 */
public class BlocksCreatureWithFlyingTriggeredAbility extends TriggeredAbilityImpl {

    public BlocksCreatureWithFlyingTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BlocksCreatureWithFlyingTriggeredAbility(final BlocksCreatureWithFlyingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId())
                && game.getPermanent(event.getTargetId()).getAbilities().containsKey(FlyingAbility.getInstance().getId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} blocks a creature with flying, " ;
    }

    @Override
    public BlocksCreatureWithFlyingTriggeredAbility copy() {
        return new BlocksCreatureWithFlyingTriggeredAbility(this);
    }
}