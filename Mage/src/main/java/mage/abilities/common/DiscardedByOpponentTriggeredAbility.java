package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;

/**
 *
 * @author Styxo
 */
public class DiscardedByOpponentTriggeredAbility extends TriggeredAbilityImpl {

    public DiscardedByOpponentTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiscardedByOpponentTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.GRAVEYARD, effect, optional);
        setTriggerPhrase("When a spell or ability an opponent controls causes you to discard this card, ");
    }

    public DiscardedByOpponentTriggeredAbility(final DiscardedByOpponentTriggeredAbility ability) {
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
        if (getSourceId().equals(event.getTargetId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null) {
                return game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId());
            }
        }
        return false;
    }
}
