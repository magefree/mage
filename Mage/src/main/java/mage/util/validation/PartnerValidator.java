package mage.util.validation;

import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;

/**
 * @author TheElk801
 */
public enum PartnerValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return commander1.getAbilities().containsClass(PartnerAbility.class);
    }
}
