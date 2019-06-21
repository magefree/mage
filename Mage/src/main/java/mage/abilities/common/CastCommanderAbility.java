package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;

/**
 * @author Plopman, JayDi85
 */
public class CastCommanderAbility extends SpellAbility {

    public CastCommanderAbility(Card card) {
        super(card.getSpellAbility());
        this.setCardName(cardName = card.getName());
        zone = Zone.COMMAND;
        spellAbilityType = SpellAbilityType.BASE;
    }

    public CastCommanderAbility(final CastCommanderAbility ability) {
        super(ability);
    }

    @Override
    public CastCommanderAbility copy() {
        return new CastCommanderAbility(this);
    }

}
