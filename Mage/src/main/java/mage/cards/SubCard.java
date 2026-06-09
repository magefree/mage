package mage.cards;

/**
 * Interface for card parts that reference a parent card.
 *
 * @param <P> the type of the parent card
 */
public interface SubCard<P extends Card> extends Card {

    void setParentCard(P card);

    P getParentCard();

    default Card getOtherSide() {
        Card otherSide;
        CardWithParts parentCard = (CardWithParts) getParentCard();
        if (!parentCard.getLeftHalfCard().getId().equals(this.getId())) {
            otherSide = parentCard.getLeftHalfCard();
        } else if (!parentCard.getId().equals(this.getId())) {
            otherSide = parentCard.getRightHalfCard();
        } else {
            throw new IllegalStateException("Wrong code usage: Card halves must use different ids");
        }
        return otherSide;
    }

    @Override
    SubCard<P> copy();
}
