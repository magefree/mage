package mage.util.validation;

import mage.cards.Card;

/**
 * interface for validating two commanders
 *
 * @author TheElk801
 */
public interface CommanderValidator {

    boolean checkPartner(Card commander1, Card commander2);

    default boolean checkBothPartners(Card commander1, Card commander2) {
        return checkPartner(commander1, commander2) && checkPartner(commander2, commander1);
    }

    default boolean specialCheck(Card commander) {
        return false;
    }
}
