package mage.cards;

import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Cards extends Set<UUID>, Serializable {

    /**
     * Add the passed in card to the set if it's not null.
     *
     * @param card the card to add
     */
    void add(Card card);

    /**
     * Get the Card corresponding to the UUID passed IF it is in the set.
     * Returns null if the card is not in the set
     *
     * @param cardId UUID of the card to get
     * @param game   the current game
     * @return       The Card corresponding to the UUID, or null if that UUID is not in the set
     */
    Card get(UUID cardId, Game game);

    /**
     * Remove a specific card from the set in a safe manner.
     *
     * @param card  the card to remove from this set
     * @return      boolean indicating if removing the card was done successfully
     */
    boolean remove(Card card);

    void addAllCards(Collection<? extends Card> createCards);

    Set<Card> getCards(Game game);

    Set<Card> getCards(FilterCard filter, Game game);

    Set<Card> getCards(FilterCard filter, UUID playerId, Ability source, Game game);

    String getValue(Game game);

    /**
     * Get a collection view of the unique non-null cards in this set.
     *
     * @param game  The current game
     * @return      Collection of unique non-null cards.
     */
    Collection<Card> getUniqueCards(Game game);

    Card getRandom(Game game);

    int count(FilterCard filter, Game game);

    int count(FilterCard filter, UUID playerId, Game game);

    int count(FilterCard filter, UUID playerId, Ability source, Game game);

    Cards copy();

    /**
     * Remove all cards except those in the provided zone.
     *
     * @param zone cards from this zone will be kept.
     * @param game the ongoing game.
     */
    void retainZone(Zone zone, Game game);

    /**
     * Remove all cards which are in the provided zone.
     *
     * @param zone cards from this zone will be removed.
     * @param game The ongoing game.
     */
    void removeZone(Zone zone, Game game);
}
