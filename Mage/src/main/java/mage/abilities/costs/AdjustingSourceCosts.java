package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Interface for abilities that adjust source and only source costs. For the
 * cases when some permanent adjusts costs of other spells use
 * {@link mage.abilities.effects.CostModificationEffect}.
 * <p>
 * Example of such source costs adjusting:
 * {@link mage.abilities.keyword.AffinityForArtifactsAbility}
 *
 * @author nantuko
 */
@Deprecated
// replace all AdjustingSourceCosts with "extends CostModificationEffectImpl with zone.ALL" (see Affinity example)
@FunctionalInterface
public interface AdjustingSourceCosts {

    void adjustCosts(Ability ability, Game game);
}
