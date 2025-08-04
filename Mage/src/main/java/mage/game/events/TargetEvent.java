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
     * @param sourceControllerId can be different from real controller (example: ability instructs another player to targeting)
     */
    public TargetEvent(Card target, UUID sourceId, UUID sourceControllerId) {
        this(target.getId(), sourceId, sourceControllerId);
    }

    public TargetEvent(Player target, UUID sourceId, UUID sourceControllerId) {
        this(target.getId(), sourceId, sourceControllerId);
    }

    public TargetEvent(UUID targetId, UUID sourceId, UUID sourceControllerId) {
        super(GameEvent.EventType.TARGET, targetId, null, sourceControllerId);
        this.setSourceId(sourceId);
    }

    public TargetEvent(UUID targetId, Ability source) {
        super(GameEvent.EventType.TARGET, targetId, source, source.getControllerId());
    }
}
