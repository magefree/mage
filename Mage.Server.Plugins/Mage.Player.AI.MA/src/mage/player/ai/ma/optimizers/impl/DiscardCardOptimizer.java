package mage.player.ai.ma.optimizers.impl;

import java.util.List;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * AI: removes abilities that require only discard a card for activation.
 *
 * @author magenoxx_at_gmail.com
 */
public class DiscardCardOptimizer extends BaseTreeOptimizer {

    @Override
    public void filter(Game game, List<Ability> actions, List<Ability> actionsToRemove) {
        for (Ability ability : actions) {
            // TODO: add more discard restictions here? See ExileSourceUnlessPaysEffect for a possible list
            if (ability.toString().startsWith("Discard card")) {
                actionsToRemove.add(ability);
            }
        }
    }
}
