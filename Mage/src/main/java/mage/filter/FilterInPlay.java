package mage.filter;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.UUID;

/**
 * @param <E>
 * @author BetaSteward_at_googlemail.com
 */
public interface FilterInPlay<E> extends Filter<E> {

    boolean match(E o, UUID playerId, Ability source, Game game);

    @Override
    FilterInPlay<E> copy();
}
