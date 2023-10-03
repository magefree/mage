
package mage.filter.common;

import mage.constants.AsThoughEffectType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class FilterCreatureForCombatBlock extends FilterCreatureForCombatBase {

    public FilterCreatureForCombatBlock() {
        this("");
    }

    public FilterCreatureForCombatBlock(String name) {
        super(name);
        this.add(new BlockTappedPredicate());
    }

    protected FilterCreatureForCombatBlock(final FilterCreatureForCombatBlock filter) {
        super(filter);
    }

    @Override
    public FilterCreatureForCombatBlock copy() {
        return new FilterCreatureForCombatBlock(this);
    }
}

class BlockTappedPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return !input.isTapped() || !game.getState().getContinuousEffects().asThough(input.getId(), AsThoughEffectType.BLOCK_TAPPED, null, input.getControllerId(), game).isEmpty();
    }

    @Override
    public String toString() {
        return "untapped or can block tapped";
    }
}
