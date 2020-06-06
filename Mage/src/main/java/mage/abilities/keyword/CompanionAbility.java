package mage.abilities.keyword;

import mage.abilities.SpecialAction;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.CompanionEffect;
import mage.cards.Card;
import mage.constants.TimingRule;
import mage.constants.Zone;

import java.util.Set;

/*
 * @author emerald000
 */
public class CompanionAbility extends SpecialAction {

    private final CompanionCondition condition;

    public CompanionAbility(CompanionCondition condition) {
        super(Zone.OUTSIDE);
        this.condition = condition;
        this.addCost(new GenericManaCost(3));
        this.addEffect(new CompanionEffect());
        this.setTiming(TimingRule.SORCERY);
    }

    private CompanionAbility(final CompanionAbility ability) {
        super(ability);
        this.condition = ability.condition;
    }

    @Override
    public CompanionAbility copy() {
        return new CompanionAbility(this);
    }

    @Override
    public String getRule() {
        return "Companion &mdash; " + condition.getRule();
    }

    public boolean isLegal(Set<Card> cards, int startingSize) {
        return condition.isLegal(cards, startingSize);
    }
}

