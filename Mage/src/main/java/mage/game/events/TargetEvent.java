package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class TargetEvent extends GameEvent {

    /**
     * @param target
     * @param sourceId
     * @param sourceControllerId can be different from real controller (example: ability instructs another player to targeting)
     */
    public TargetEvent(Card target, UUID sourceId, UUID sourceControllerId) {
        super(GameEvent.EventType.TARGET, target.getId(), null, sourceControllerId);
        this.setSourceId(sourceId);
    }

    public TargetEvent(Player target, UUID sourceId, UUID sourceControllerId) {
        super(GameEvent.EventType.TARGET, target.getId(), null, sourceControllerId);
        this.setSourceId(sourceId);
    }

    /**
     * @param targetId
     * @param source
     */
    public TargetEvent(UUID targetId, Ability source) {
        super(GameEvent.EventType.TARGET, targetId, source, source.getControllerId());
    }
}
