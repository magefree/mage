package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;

/**
 * @author TheElk801
 */
public class CleaveAbility extends SpellAbility {

    public CleaveAbility(Card card, Effect effect, String manaString) {
        super(new ManaCostsImpl<>(manaString), card.getName() + " with cleave");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.addEffect(effect);
        this.setRuleAtTheTop(true);
        this.timing = (card.isSorcery(null) ? TimingRule.SORCERY : TimingRule.INSTANT);
    }

    public CleaveAbility(final CleaveAbility ability) {
        super(ability);
    }

    @Override
    public CleaveAbility copy() {
        return new CleaveAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Cleave " + getManaCostsToPay().getText() + " <i>(You may cast this spell for its cleave cost. If you do, remove the words in square brackets.)</i>";
    }
}
