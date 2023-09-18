package mage.cards;

/**
 * @author TheElk801
 */
public interface CardWithHalves extends Card {

    Card getLeftHalfCard();

    Card getRightHalfCard();
}
