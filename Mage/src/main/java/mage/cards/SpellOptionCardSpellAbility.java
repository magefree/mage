package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Shared rules presentation for the inset spell on cards such as Adventures, Omens, and Preparations
 *
 * @author nandmp
 */
abstract class SpellOptionCardSpellAbility extends SpellAbility {

    private final String fullName;
    private final String reminderText;

    protected SpellOptionCardSpellAbility(SpellAbility baseSpellAbility, String spellType,
                                          String spellName, CardType[] cardTypes, String costs,
                                          String reminderText) {
        super(baseSpellAbility);
        String typePrefix = spellType.isEmpty() ? "" : spellType + " ";
        this.fullName = typePrefix
                + Arrays.stream(cardTypes).map(CardType::toString).collect(Collectors.joining(" "))
                + " &mdash; " + spellName;
        this.reminderText = reminderText;
        this.name = this.fullName + " " + costs;
        this.setCardName(spellName);
    }

    protected SpellOptionCardSpellAbility(SpellOptionCardSpellAbility ability) {
        super(ability);
        this.fullName = ability.fullName;
        this.reminderText = ability.reminderText;
    }

    /** Rules shown inside the inset, whose frame already displays its name, type, and cost. */
    final String getInsetRule() {
        return CardUtil.getTextWithFirstCharUpperCase(super.getRule(false)) + reminderText;
    }

    /**
     * Copies the rules implementation without the multipart card presentation.
     * This is used when alternative characteristics become a standalone card.
     */
    final SpellAbility copyForStandaloneCard() {
        SpellAbility ability = super.copy();
        ability.setCardName(getCardName());
        return ability;
    }

    @Override
    public final String getRule(boolean all) {
        return fullName
                + " "
                + getManaCosts().getText()
                + " &mdash; "
                + getInsetRule();
    }
}
