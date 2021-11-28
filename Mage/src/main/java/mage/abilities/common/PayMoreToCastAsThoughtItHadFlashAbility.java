package mage.abilities.common;

import mage.abilities.SpellAbility;
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

    private final ManaCosts costsToAdd;

    public PayMoreToCastAsThoughtItHadFlashAbility(Card card, ManaCosts<ManaCost> costsToAdd) {
        super(card.getSpellAbility().getManaCosts().copy(), card.getName() + " as though it had flash", Zone.HAND, SpellAbilityType.BASE_ALTERNATE);
        this.costsToAdd = costsToAdd;
        this.timing = TimingRule.INSTANT;
        this.ruleAtTheTop = true;
        CardUtil.increaseCost(this, costsToAdd);
    }

    public PayMoreToCastAsThoughtItHadFlashAbility(final PayMoreToCastAsThoughtItHadFlashAbility ability) {
        super(ability);
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
        return "You may cast this spell as though it had flash if you pay " + costsToAdd.getText() + " more to cast it. <i>(You may cast it any time you could cast an instant.)</i>";
    }

}
