package mage.cards;

/**
 * Interface for card parts that reference a parent card.
 *
 * @param <P> the type of the parent card
 */
public interface SubCard<P extends Card> extends Card {

    void setParentCard(P card);

    P getParentCard();

    @Override
    SubCard<P> copy();
}
