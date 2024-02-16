package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;

/**
 * 702.94. Overload
 * <p>
 * 702.94a. Overload is a keyword that represents two static abilities: one that
 * functions from any zone in which the spell with overload can be cast and
 * another that functions while the card is on the stack. Overload [cost] means
 * "You may choose to pay [cost] rather than pay this spell's mana cost" and "If
 * you chose to pay this spell's overload cost, change its text by replacing all
 * instances of the word 'target' with the word 'each.'" Using the overload
 * ability follows the rules for paying alternative costs in rules 601.2b and
 * 601.2e-g.
 * <p>
 * 702.94b. If a player chooses to pay the overload cost of a spell, that spell
 * won't require any targets. It may affect objects that couldn't be chosen as
 * legal targets if the spell were cast without its overload cost being paid.
 * <p>
 * 702.94c. Overload's second ability creates a text-changing effect. See rule
 * 612, "Text-Changing Effects."
 *
 * @author LevelX2
 */
public class OverloadAbility extends SpellAbility {

    public OverloadAbility(Card card, Effect effect, ManaCosts costs) {
        super(costs, card.getName() + " with overload");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.addEffect(effect);
        this.timing = (card.isSorcery(null) ? TimingRule.SORCERY : TimingRule.INSTANT);
    }

    protected OverloadAbility(final OverloadAbility ability) {
        super(ability);
    }

    @Override
    public OverloadAbility copy() {
        return new OverloadAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Overload " + getManaCostsToPay().getText() + " <i>(You may cast this spell for its overload cost. If you do, change its text by replacing all instances of \"target\" with \"each.\")</i>";
    }

}
