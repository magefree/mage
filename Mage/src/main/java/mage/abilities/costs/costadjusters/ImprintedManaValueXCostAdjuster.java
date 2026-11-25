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
        // CR 107.3c: X is defined by the ability's text. With no imprinted card
        // (nothing was exiled, or the card is gone) there is no card whose mana
        // value the text can read, so X is 0; the ability is still activatable
        // (CR 609.3, the effect does as much as it can) and creates no token.
        int manaValue = 0;

        Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
        if (sourcePermanent != null
                && sourcePermanent.getImprinted() != null
                && !sourcePermanent.getImprinted().isEmpty()) {
            Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
            if (imprintedCard != null) {
                manaValue = imprintedCard.getManaValue();
            }
        }

        ability.setVariableCostsValue(manaValue);
    }
}
