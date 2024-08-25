package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class DrawTwoOrMoreCardsEvent extends GameEvent {

    private int cardsDrawn = 0; // for replacement effects to keep track for "cards drawn this way"

    public DrawTwoOrMoreCardsEvent(UUID playerId, Ability source, GameEvent originalDrawEvent, int amount) {
        super(GameEvent.EventType.DRAW_TWO_OR_MORE_CARDS, playerId, null, playerId, amount, false);

        // source of draw events must be kept between replacements, example: UnpredictableCycloneTest
        this.setSourceId(originalDrawEvent == null
                ? source == null ? null : source.getSourceId()
                : originalDrawEvent.getSourceId());

        // source of draw events must be kept between replacements, example: UnpredictableCycloneTest
        if (originalDrawEvent != null) {
            this.addAppliedEffects(originalDrawEvent.getAppliedEffects());
        }
    }

    public void incrementCardsDrawn(int cardsDrawn) {
        this.cardsDrawn += cardsDrawn;
    }

    public int getCardsDrawn() {
        return cardsDrawn;
    }

}
