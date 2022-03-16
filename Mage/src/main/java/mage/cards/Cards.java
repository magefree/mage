package mage.cards;

import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Cards extends Set<UUID>, Serializable {

    void add(Card card);

    Card get(UUID cardId, Game game);

    boolean remove(Card card);

    void setOwner(UUID ownerId, Game game);

    void addAll(List<? extends Card> createCards);

    void addAll(Set<? extends Card> createCards);

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

    void retainZone(Zone zone, Game game);

    void removeZone(Zone zone, Game game);
}
