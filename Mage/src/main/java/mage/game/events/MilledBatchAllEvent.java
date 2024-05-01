package mage.game.events;

import mage.cards.Cards;
import mage.cards.CardsImpl;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public class MilledBatchAllEvent extends BatchEvent<MilledCardEvent> {

    public MilledBatchAllEvent(MilledCardEvent event) {
        super(EventType.MILLED_CARDS_BATCH_FOR_ALL, false, false, false, event);
    }

    public Cards getCards() {
        return new CardsImpl(getEvents()
                .stream()
                .map(MilledCardEvent::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
        );
    }
}
