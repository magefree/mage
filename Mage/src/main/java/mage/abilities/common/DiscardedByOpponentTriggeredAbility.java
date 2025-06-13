package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 * @author Styxo
 */
public class DiscardedByOpponentTriggeredAbility extends TriggeredAbilityImpl {

    public DiscardedByOpponentTriggeredAbility(Effect effect) {
        super(Zone.GRAVEYARD, effect, false);
        setTriggerPhrase("When a spell or ability an opponent controls causes you to discard this card, ");
    }

    protected DiscardedByOpponentTriggeredAbility(final DiscardedByOpponentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiscardedByOpponentTriggeredAbility copy() {
        return new DiscardedByOpponentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getSourceId().equals(event.getTargetId())) {
            return false;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        return stackObject != null && game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId());
    }
}
