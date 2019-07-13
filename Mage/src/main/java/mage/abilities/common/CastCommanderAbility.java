package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author Plopman, JayDi85
 */
public class CastCommanderAbility extends SpellAbility {

    public CastCommanderAbility(Card card, SpellAbility spellTemplate) {
        super(spellTemplate);
        this.newId();
        this.setCardName(spellTemplate.getCardName());
        zone = Zone.COMMAND;
        spellAbilityType = spellTemplate.getSpellAbilityType();
    }

    public CastCommanderAbility(final CastCommanderAbility ability) {
        super(ability);
    }

    @Override
    public CastCommanderAbility copy() {
        return new CastCommanderAbility(this);
    }

}
