package mage.util.validation;

import mage.abilities.common.ChooseABackgroundAbility;
import mage.cards.Card;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public enum ChooseABackgroundValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return commander1.getAbilities().containsClass(ChooseABackgroundAbility.class)
                && commander2.hasSubTypeForDeckbuilding(SubType.BACKGROUND);
    }

    @Override
    public boolean checkBothPartners(Card commander1, Card commander2) {
        return checkPartner(commander1, commander2) || checkPartner(commander2, commander1);
    }

    @Override
    public boolean specialCheck(Card commander) {
        return commander.hasSubTypeForDeckbuilding(SubType.BACKGROUND);
    }
}
