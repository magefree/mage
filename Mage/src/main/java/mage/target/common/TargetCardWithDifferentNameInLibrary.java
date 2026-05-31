package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        final Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        if (possibleTargets == null) {
            return Collections.emptySet();
        }

        final Cards existingTargets = new CardsImpl(this.getTargets());
        if (existingTargets == null) {
            return Collections.emptySet();
        }

        return this.keepValidPossibleTargets(
            possibleTargets.stream()
                .filter(c -> existingTargets.stream().noneMatch(t -> CardUtil.haveSameNames(game.getCard(c), game.getCard(t))))
                .collect(Collectors.toSet()),
        sourceControllerId, source, game);
    }
}
