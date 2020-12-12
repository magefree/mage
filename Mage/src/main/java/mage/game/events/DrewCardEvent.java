package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class DrewCardEvent extends GameEvent {

    public DrewCardEvent(UUID cardId, UUID playerId, Ability source, GameEvent originalDrawEvent) {
        super(EventType.DREW_CARD, cardId, source, playerId, 0, false);

        // source of draw events must be kept between replacements, example: UnpredictableCycloneTest
        if (originalDrawEvent != null) {
            //this.addAppliedEffects(originalDrawEvent.getAppliedEffects()); // event can't used for replace, so no needs in applied effects
            this.setSourceId(originalDrawEvent.getSourceId());
        }
    }
}
