package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class PayMoreToCastAsThoughtItHadFlashAbility extends SpellAbility {

    private final Cost costsToAdd;
    private final String rule;

    public PayMoreToCastAsThoughtItHadFlashAbility(Card card, Cost costsToAdd) {
        this(card, costsToAdd, null);
    }

    public PayMoreToCastAsThoughtItHadFlashAbility(Card card, Cost costsToAdd, String rule) {
        super(card.getSpellAbility().getManaCosts().copy(), card.getName(), Zone.HAND, SpellAbilityType.BASE_ALTERNATE);
        this.costsToAdd = costsToAdd;
        this.rule = rule;
        this.timing = TimingRule.INSTANT;
        this.setRuleAtTheTop(true);

        if (costsToAdd instanceof ManaCosts<?>) {
            ManaCosts<ManaCost> manaCosts = (ManaCosts<ManaCost>) costsToAdd;
            CardUtil.increaseCost(this, manaCosts);
        } else {
            this.addCost(costsToAdd);
        }
    }

    protected PayMoreToCastAsThoughtItHadFlashAbility(final PayMoreToCastAsThoughtItHadFlashAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.costsToAdd = ability.costsToAdd;
    }

    @Override
    public PayMoreToCastAsThoughtItHadFlashAbility copy() {
        return new PayMoreToCastAsThoughtItHadFlashAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        if (costsToAdd instanceof ManaCosts) {
            return "You may cast this spell as though it had flash if you pay " + costsToAdd.getText() + " more to cast it. <i>(You may cast it any time you could cast an instant.)</i>";
        } else {
            return "You may cast this spell as though it had flash by " + costsToAdd.getText() + " in addition to paying its other costs.";
        }
    }

}
