package mage.util.validation;

import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.Card;
import mage.constants.SubType;

/**
 * @author TheElk801
 * <p>
 * We check for Brushwagg because it can't be a changeling
 */
public enum DoctorsCompanionValidator implements CommanderValidator {
    instance;

    @Override
    public boolean checkPartner(Card commander1, Card commander2) {
        return commander1.getAbilities().containsClass(DoctorsCompanionAbility.class)
                && commander2.hasSubTypeForDeckbuilding(SubType.TIME_LORD)
                && commander2.hasSubTypeForDeckbuilding(SubType.DOCTOR)
                && !commander2.hasSubTypeForDeckbuilding(SubType.BRUSHWAGG);
    }

    @Override
    public boolean checkBothPartners(Card commander1, Card commander2) {
        return checkPartner(commander1, commander2) || checkPartner(commander2, commander1);
    }

    @Override
    public boolean specialCheck(Card commander) {
        return commander.hasSubTypeForDeckbuilding(SubType.TIME_LORD)
                && commander.hasSubTypeForDeckbuilding(SubType.DOCTOR)
                && !commander.hasSubTypeForDeckbuilding(SubType.BRUSHWAGG);
    }
}
