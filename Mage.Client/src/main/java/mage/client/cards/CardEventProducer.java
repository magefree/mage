package mage.client.cards;

/**
 * Add it to components that will be generates card's events (example: panel with cards list)
 *
 * @author JayDi85
 */
public interface CardEventProducer {

    CardEventSource getCardEventSource();

}
