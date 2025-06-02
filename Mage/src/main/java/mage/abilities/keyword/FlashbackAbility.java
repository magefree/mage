package mage.abilities.keyword;

import mage.abilities.costs.Cost;
import mage.cards.Card;
import mage.constants.SpellAbilityCastMode;

/**
 * 702.32. Flashback
 * <p>
 * 702.32a. Flashback appears on some instants and sorceries. It represents two
 * static abilities: one that functions while the card is in a player‘s
 * graveyard and the other that functions while the card is on the stack.
 * Flashback [cost] means, "You may cast this card from your graveyard by paying
 * [cost] rather than paying its mana cost" and, "If the flashback cost was
 * paid, exile this card instead of putting it anywhere else any time it would
 * leave the stack." Casting a spell using its flashback ability follows the
 * rules for paying alternative costs in rules 601.2b and 601.2e–g.
 *
 * @author nantuko
 */
public class FlashbackAbility extends CastFromGraveyardAbility {

    public FlashbackAbility(Card card, Cost cost) {
        super(card, cost, SpellAbilityCastMode.FLASHBACK);
    }

    protected FlashbackAbility(final FlashbackAbility ability) {
        super(ability);
    }

    @Override
    public FlashbackAbility copy() {
        return new FlashbackAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Flashback");
        if (!getCosts().isEmpty()) {
            sbRule.append("&mdash;");
        } else {
            sbRule.append(' ');
        }
        if (!getManaCosts().isEmpty()) {
            sbRule.append(getManaCosts().getText());
        }
        if (!getCosts().isEmpty()) {
            if (!getManaCosts().isEmpty()) {
                sbRule.append(", ");
            }
            sbRule.append(getCosts().getText());
            sbRule.append('.');
        }
        if (abilityName != null) {
            sbRule.append(". ");
            sbRule.append(abilityName);
        }
        sbRule.append(" <i>(You may cast this card from your graveyard for its flashback cost. Then exile it.)</i>");
        return sbRule.toString();
    }
}
