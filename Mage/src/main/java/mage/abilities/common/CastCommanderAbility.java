package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.DashAbility;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Plopman, JayDi85
 */
public class CastCommanderAbility extends SpellAbility {

    private final String ruleText;

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

    @Override
    public boolean activate(Game game, boolean noMana) {
        // added this to CastCommanderAbility to support commanders with blitz ability
        if (!super.activate(game, noMana)) {
            return false;
        }
        Card card = game.getCard(this.sourceId);
        if (card != null) {
             CardUtil.castStream(card.getAbilities(game).stream(), BlitzAbility.class)
                     .forEach(blitzAbility -> blitzAbility.setBlitzActivationValueKey(game));
        }
        return true;
    }

}
