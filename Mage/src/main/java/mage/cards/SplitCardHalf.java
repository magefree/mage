package mage.cards;

/**
 * @author LevelX2
 */
public interface SplitCardHalf extends Card {

    @Override
    SplitCardHalf copy();

    void setParentCard(SplitCard card);

    SplitCard getParentCard();
}
