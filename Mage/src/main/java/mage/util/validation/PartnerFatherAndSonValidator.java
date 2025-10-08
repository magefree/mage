package mage.util.validation;

import mage.abilities.keyword.PartnerFatherAndSonAbility;
import mage.cards.Card;

/**
 * @author TheElk801
 */
public enum PartnerFatherAndSonValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return commander1.getAbilities().containsClass(PartnerFatherAndSonAbility.class);
    }
}
