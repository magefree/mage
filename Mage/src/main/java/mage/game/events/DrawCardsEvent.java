package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class DrawCardsEvent extends GameEvent {

    public DrawCardsEvent(UUID playerId, Ability source, GameEvent originalDrawEvent, int amount) {
        super(GameEvent.EventType.DRAW_CARDS, playerId, source, playerId, amount, false);

        // source of draw events must be kept between replacements, example: UnpredictableCycloneTest
        if (originalDrawEvent != null) {
            this.addAppliedEffects(originalDrawEvent.getAppliedEffects());
            this.setSourceId(originalDrawEvent.getSourceId());
        }
    }
}
