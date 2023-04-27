package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author JayDi85
 */
public enum CardTypesInGraveyardCount implements DynamicValue {
    YOU("your graveyard"),
    ALL("all graveyards"),
    OPPONENTS("your opponents' graveyards");
    private final String message;

    CardTypesInGraveyardCount(String message) {
        this.message = "the number of card types among cards in " + message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getStream(game, sourceAbility)
                .filter(card -> !card.isCopy() && !(card instanceof PermanentToken))
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public CardTypesInGraveyardCount copy() {
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

    private final Stream<Card> getStream(Game game, Ability ability) {
        Collection<UUID> playerIds;
        switch (this) {
            case YOU:
                Player player = game.getPlayer(ability.getControllerId());
                return player == null
                        ? null : player
                        .getGraveyard()
                        .getCards(game)
                        .stream();
            case OPPONENTS:
                playerIds = game.getOpponents(ability.getControllerId());
                break;
            case ALL:
                playerIds = game.getState().getPlayersInRange(ability.getControllerId(), game);
                break;
            default:
                return null;
        }
        return playerIds.stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.getCards(game))
                .flatMap(Collection::stream);
    }
}
