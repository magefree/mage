package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class DrewCardEvent extends GameEvent {

    public DrewCardEvent(UUID cardId, UUID playerId, Ability source, GameEvent originalDrawEvent) {
        super(EventType.DREW_CARD, cardId, null, playerId, 0, false);

        // source of draw events must be kept between replacements, example: UnpredictableCycloneTest
        this.setSourceId(originalDrawEvent == null
                ? source == null ? null : source.getSourceId()
                : originalDrawEvent.getSourceId());
    }
}
