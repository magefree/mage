package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;

/**
 * @author TheElk801
 */
public class WarpAbility extends SpellAbility {

    public static final String WARP_ACTIVATION_VALUE_KEY = "warpActivation";

    public WarpAbility(Card card, String manaString) {
        super(new ManaCostsImpl<>(manaString), card.getName() + " with Warp");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.setAdditionalCostsRuleVisible(false);
        this.timing = TimingRule.SORCERY;
    }

    private WarpAbility(final WarpAbility ability) {
        super(ability);
    }

    @Override
    public WarpAbility copy() {
        return new WarpAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Warp");
        if (getCosts().isEmpty()) {
            sb.append(' ');
        } else {
            sb.append("&mdash;");
        }
        sb.append(getManaCosts().getText());
        if (!getCosts().isEmpty()) {
            sb.append(", ");
            sb.append(getCosts().getText());
            sb.append('.');
        }
        sb.append(" <i>(You may cast this card from your hand for its warp cost. ");
        sb.append("Exile this creature at the beginning of the next end step, ");
        sb.append("then you may cast it from exile on a later turn.)</i>");
        return sb.toString();
    }
}
