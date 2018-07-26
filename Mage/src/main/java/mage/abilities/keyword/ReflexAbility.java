
package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;

/**
 *
 * @author EikePeace
 */
public class ReflexAbility extends SpellAbility {

    public ReflexAbility(Card card, String manaString, Effect effect) {
        super(new ManaCostsImpl(manaString), card.getName() + " using Reflex");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = TimingRule.INSTANT;
        this.addEffect(effect);
    }

    public ReflexAbility(final ReflexAbility ability) {
        super(ability);
    }

    @Override
    public ReflexAbility copy() {
        return new ReflexAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Reflex " + getManaCostsToPay().getText() + " <i>(You may cast this spell any time you could cast an instant for its reflex cost.)</i>";
    }
}

