package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.game.Game;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Jmlundeen
 */
public enum CardsInExileCount implements DynamicValue {
    YOU("you"),
    ALL("all players"),
    OPPONENTS("your opponents'");

    private final String message;
    private final ValueHint hint;

    CardsInExileCount(String message) {
        this.message = "The number of cards owned by " + message + " in exile";
        this.hint = new ValueHint(this.message, this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getExileCards(game, sourceAbility)
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public CardsInExileCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Hint getHint() {
        return hint;
    }

    public Stream<Card> getExileCards(Game game, Ability ability) {
        Collection<UUID> playerIds;
        switch (this) {
            case YOU:
                playerIds = Collections.singletonList(ability.getControllerId());
                break;
            case OPPONENTS:
                playerIds = game.getOpponents(ability.getControllerId());
                break;
            case ALL:
                playerIds = game.getState().getPlayersInRange(ability.getControllerId(), game);
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage: miss implementation for " + this);
        }
        return playerIds.stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> game.getExile().getCardsOwned(game, player.getId()))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull);
    }
}
