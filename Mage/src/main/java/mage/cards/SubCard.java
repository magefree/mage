package mage.cards;

public interface SubCard<T extends Card> extends Card {

    void setParentCard(T card);

    T getParentCard();
}
