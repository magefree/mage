
package mage.cards;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.filter.FilterCard;
import mage.game.Game;

public interface Cards extends Set<UUID>, Serializable {

    void add(Card card);

    Card get(UUID cardId, Game game);

    void remove(Card card);

    void setOwner(UUID ownerId, Game game);

    void addAll(List<Card> createCards);

    void addAll(Set<Card> createCards);

    Set<Card> getCards(Game game);

    Set<Card> getCards(FilterCard filter, Game game);

    Set<Card> getCards(FilterCard filter, UUID sourceId, UUID playerId, Game game);

    String getValue(Game game);

    Collection<Card> getUniqueCards(Game game);

    Card getRandom(Game game);

    int count(FilterCard filter, Game game);

    int count(FilterCard filter, UUID playerId, Game game);

    int count(FilterCard filter, UUID sourceId, UUID playerId, Game game);

    Cards copy();
}
