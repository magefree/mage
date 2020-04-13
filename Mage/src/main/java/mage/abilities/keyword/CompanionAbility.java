package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.effects.keyword.CompanionEffect;
import mage.cards.Card;
import mage.constants.Zone;

import java.util.Set;

/*
 * @author emerald000
 */
public class CompanionAbility extends StaticAbility {

    private final CompanionCondition condition;

    public CompanionAbility(CompanionCondition condition) {
        super(Zone.OUTSIDE, new CompanionEffect());
        this.condition = condition;
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

    public boolean isLegal(Set<Card> cards) {
        return condition.isLegal(cards);
    }
}

