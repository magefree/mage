package mage.util.validation;

import mage.abilities.keyword.FriendsForeverAbility;
import mage.cards.Card;

/**
 * @author TheElk801
 */
public enum FriendsForeverValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return commander1.getAbilities().containsClass(FriendsForeverAbility.class);
    }
}
