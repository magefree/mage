package mage.util.validation;

import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum PartnerWithValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return CardUtil
                .castStream(commander2.getAbilities().stream(), PartnerWithAbility.class)
                .anyMatch(ability -> ability.checkPartner(commander1));
    }
}
