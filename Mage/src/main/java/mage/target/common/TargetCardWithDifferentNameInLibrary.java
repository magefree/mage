package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class TargetCardWithDifferentNameInLibrary extends TargetCardInLibrary {

    public TargetCardWithDifferentNameInLibrary(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, filter);
    }

    protected TargetCardWithDifferentNameInLibrary(final TargetCardWithDifferentNameInLibrary target) {
        super(target);
    }

    @Override
    public TargetCardWithDifferentNameInLibrary copy() {
        return new TargetCardWithDifferentNameInLibrary(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null
                && this.getTargets()
                .stream()
                .map(game::getCard)
                .noneMatch(c -> CardUtil.haveSameNames(c, card));
    }
}
