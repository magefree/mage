package mage.abilities.costs.common;

import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

/**
 * @author magenoxx_at_googlemail.com
 */
public class DiscardCardCost extends DiscardTargetCost {

    public DiscardCardCost() {
        this(false);
    }

    public DiscardCardCost(boolean randomDiscard) {
        this(new FilterCard(randomDiscard ? "a card at random" : "a card"), randomDiscard);
    }

    public DiscardCardCost(FilterCard filter) {
        this(filter, false);
    }

    public DiscardCardCost(FilterCard filter, boolean randomDiscard) {
        super(new TargetCardInHand(filter).withChooseHint("discard cost"), randomDiscard);
    }

    protected DiscardCardCost(final DiscardCardCost cost) {
        super(cost);
    }

    @Override
    public DiscardCardCost copy() {
        return new DiscardCardCost(this);
    }

}
