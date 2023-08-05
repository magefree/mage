package mage.abilities.effects.common;

import mage.abilities.costs.common.DiscardCardCost;

/**
 * @author stravant
 */
public class RummageEffect extends DoIfCostPaid {
    public RummageEffect() {
        super(new DrawCardSourceControllerEffect(1), new DiscardCardCost());
    }

    protected RummageEffect(final RummageEffect effect) {
        super(effect);
    }

    @Override
    public RummageEffect copy() {
        return new RummageEffect(this);
    }
}