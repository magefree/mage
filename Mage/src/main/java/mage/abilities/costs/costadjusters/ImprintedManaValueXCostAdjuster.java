package mage.abilities.costs.costadjusters;

import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Used for {X} mana cost that must be replaced by imprinted mana value
 * <p>
 * Example:
 * - Elite Arcanist
 * - {X}, {T}: Copy the exiled card. ... X is the converted mana cost of the exiled card.
 *
 * @author JayDi85
 */
public enum ImprintedManaValueXCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void prepareX(Ability ability, Game game) {
        int manaValue = Integer.MAX_VALUE;

        Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
        if (sourcePermanent != null
                && sourcePermanent.getImprinted() != null
                && !sourcePermanent.getImprinted().isEmpty()) {
            Card imprintedInstant = game.getCard(sourcePermanent.getImprinted().get(0));
            if (imprintedInstant != null) {
                manaValue = imprintedInstant.getManaValue();
            }
        }

        ability.setVariableCostsValue(manaValue);
    }
}
