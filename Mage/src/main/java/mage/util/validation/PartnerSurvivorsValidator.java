package mage.util.validation;

import mage.abilities.keyword.PartnerSurvivorsAbility;
import mage.cards.Card;

/**
 * @author TheElk801
 */
public enum PartnerSurvivorsValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return commander1.getAbilities().containsClass(PartnerSurvivorsAbility.class);
    }
}
