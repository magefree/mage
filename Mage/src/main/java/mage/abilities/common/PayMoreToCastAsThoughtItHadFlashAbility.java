package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
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

    private final CostsImpl<Cost> costsToAdd = new CostsImpl<>();
    private boolean manaCost = true;

    public PayMoreToCastAsThoughtItHadFlashAbility(Card card, ManaCosts<ManaCost> costsToAdd) {
        this(card);
        this.costsToAdd.addAll(costsToAdd);
        this.costsToAdd.setText(costsToAdd.getText());
        CardUtil.increaseCost(this, costsToAdd);
    }

    public PayMoreToCastAsThoughtItHadFlashAbility(Card card, Costs<Cost> costsToAdd) {
        this(card);
        this.costsToAdd.addAll(costsToAdd);
        this.costsToAdd.setText(costsToAdd.getText());
        this.addCost(costsToAdd);
        this.manaCost = false;
    }

    public PayMoreToCastAsThoughtItHadFlashAbility(Card card) {
        super(card.getSpellAbility().getManaCosts().copy(), card.getName() + " as though it had flash", Zone.HAND, SpellAbilityType.BASE_ALTERNATE);
        this.timing = TimingRule.INSTANT;
        this.ruleAtTheTop = true;
    }

    protected PayMoreToCastAsThoughtItHadFlashAbility(final PayMoreToCastAsThoughtItHadFlashAbility ability) {
        super(ability);
        this.costsToAdd.addAll(ability.costsToAdd);
        this.manaCost = ability.manaCost;
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
        if (manaCost) {
            return "You may cast {this} as though it had flash if you pay " + costsToAdd.getText() + " more to cast it. <i>(You may cast it any time you could cast an instant.)</i>";
        } else {
            return "You may cast {this} as though it had flash by " + costsToAdd.getText() + " in addition to paying its other costs.";
        }
    }

}
