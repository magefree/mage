package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author JayDi85
 */
public enum CardTypesInGraveyardCount implements DynamicValue {
    YOU("your graveyard"),
    ALL("all graveyards"),
    OPPONENTS("your opponents' graveyards");

    private final String message;
    private final CardTypesInGraveyardHint hint;

    CardTypesInGraveyardCount(String message) {
        this.message = "the number of card types among cards in " + message;
        this.hint = new CardTypesInGraveyardHint(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getGraveyardCards(game, sourceAbility)
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

    public Hint getHint() {
        return hint;
    }

    public Stream<Card> getGraveyardCards(Game game, Ability ability) {
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
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.getCards(game))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(card -> !card.isCopy() && !(card instanceof PermanentToken));
    }
}

class CardTypesInGraveyardHint implements Hint {

    CardTypesInGraveyardCount value;

    CardTypesInGraveyardHint(CardTypesInGraveyardCount value) {
        this.value = value;
    }

    private CardTypesInGraveyardHint(final CardTypesInGraveyardHint hint) {
        this.value = hint.value;
    }

    @Override
    public String getText(Game game, Ability ability) {
        Stream<Card> stream = this.value.getGraveyardCards(game, ability);
        List<String> types = stream
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .distinct()
                .map(CardType::toString)
                .sorted()
                .collect(Collectors.toList());
        return "Card types in " + this.value.getMessage() + ": " + types.size()
                + (types.size() > 0 ? " (" + String.join(", ", types) + ')' : "");
    }

    @Override
    public CardTypesInGraveyardHint copy() {
        return new CardTypesInGraveyardHint(this);
    }
}