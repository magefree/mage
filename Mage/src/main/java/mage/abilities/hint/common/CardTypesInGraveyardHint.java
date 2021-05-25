package mage.abilities.hint.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author JayDi85
 */
public enum CardTypesInGraveyardHint implements Hint {
    YOU("your graveyard"),
    ALL("all graveyards"),
    OPPONENTS("your opponents' graveyards");
    private final String message;

    CardTypesInGraveyardHint(String message) {
        this.message = message;
    }

    @Override
    public String getText(Game game, Ability ability) {
        Stream<Card> stream = getStream(game, ability);
        if (stream == null) {
            return null;
        }
        List<String> types = stream
                .map(MageObject::getCardType)
                .flatMap(Collection::stream)
                .distinct()
                .map(CardType::toString)
                .sorted()
                .collect(Collectors.toList());
        String message = "" + types.size();
        if (types.size() > 0) {
            message += " (" + String.join(" , ", types) + ')';
        }
        return "Card types in " + message + ": " + message;
    }

    @Override
    public Hint copy() {
        return YOU;
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
