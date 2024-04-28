package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class DrawCardEvent extends GameEvent {

    public DrawCardEvent(UUID playerId, Ability source, GameEvent originalDrawEvent) {
        super(GameEvent.EventType.DRAW_CARD, playerId,
                // source of draw events must be kept between replacements, example: UnpredictableCycloneTest
                originalDrawEvent == null
                        ? source == null ? null : source.getSourceId()
                        : originalDrawEvent.getSourceId(),
                playerId, 0, false);

        // source of draw events must be kept between replacements, example: UnpredictableCycloneTest
        if (originalDrawEvent != null) {
            this.addAppliedEffects(originalDrawEvent.getAppliedEffects());
        }
    }
}
