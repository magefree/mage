
package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class RetraceAbility extends SpellAbility {

    public RetraceAbility(Card card) {
        super(card.getManaCost(), card.getName() + " with retrace", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE);
        this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
        Cost cost = new DiscardTargetCost(new TargetCardInHand(new FilterLandCard()));
        cost.setText("");
        this.addCost(cost);
        this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
        this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = card.getSpellAbility().getTiming();

    }

    public RetraceAbility(final RetraceAbility ability) {
        super(ability);
    }

    @Override
    public RetraceAbility copy() {
        return new RetraceAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Retrace <i>(You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.)</i>";
    }
}
