package mage.util.validation;

import mage.cards.Card;
import mage.constants.PartnerVariantType;

/**
 * @author TheElk801
 */
public enum PartnerVariantValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return PartnerVariantType.checkCommanders(commander1, commander2);
    }
}
