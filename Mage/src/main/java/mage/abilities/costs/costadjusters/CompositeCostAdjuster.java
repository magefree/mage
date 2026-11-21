package mage.abilities.costs.costadjusters;

import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.game.Game;

/**
 * Two cost adjusters for the same ability, e.g. the card's own adjuster and the one from its flashback or harmonize
 *
 * @author DarioBarrabinoDev
 */
public class CompositeCostAdjuster implements CostAdjuster {

    private final CostAdjuster firstAdjuster;
    private final CostAdjuster secondAdjuster;

    private CompositeCostAdjuster(CostAdjuster firstAdjuster, CostAdjuster secondAdjuster) {
        this.firstAdjuster = firstAdjuster;
        this.secondAdjuster = secondAdjuster;
    }

    /**
     * @return combined adjuster, or the other one if any of them is null
     */
    public static CostAdjuster of(CostAdjuster firstAdjuster, CostAdjuster secondAdjuster) {
        if (firstAdjuster == null || firstAdjuster == secondAdjuster) {
            return secondAdjuster;
        }
        if (secondAdjuster == null) {
            return firstAdjuster;
        }
        return new CompositeCostAdjuster(firstAdjuster, secondAdjuster);
    }

    @Override
    public void prepareX(Ability ability, Game game) {
        firstAdjuster.prepareX(ability, game);
        secondAdjuster.prepareX(ability, game);
    }

    @Override
    public void prepareCost(Ability ability, Game game) {
        firstAdjuster.prepareCost(ability, game);
        secondAdjuster.prepareCost(ability, game);
    }

    @Override
    public void reduceCost(Ability ability, Game game) {
        firstAdjuster.reduceCost(ability, game);
        secondAdjuster.reduceCost(ability, game);
    }

    @Override
    public void increaseCost(Ability ability, Game game) {
        firstAdjuster.increaseCost(ability, game);
        secondAdjuster.increaseCost(ability, game);
    }
}
