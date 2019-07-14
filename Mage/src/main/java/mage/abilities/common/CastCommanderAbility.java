package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author Plopman, JayDi85
 */
public class CastCommanderAbility extends SpellAbility {

    private String ruleText;

    public CastCommanderAbility(Card card, SpellAbility spellTemplate) {
        super(spellTemplate);
        this.newId();
        this.setCardName(spellTemplate.getCardName());
        this.zone = Zone.COMMAND;
        this.spellAbilityType = spellTemplate.getSpellAbilityType();
        this.ruleText = spellTemplate.getRule(); // need to support custom rule texts like OverloadAbility
    }

    public CastCommanderAbility(final CastCommanderAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public CastCommanderAbility copy() {
        return new CastCommanderAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }

}
