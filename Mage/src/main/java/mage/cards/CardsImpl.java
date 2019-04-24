
package mage.cards;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import mage.MageObject;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.RandomUtil;
import mage.util.ThreadLocalStringBuilder;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardsImpl extends LinkedHashSet<UUID> implements Cards, Serializable {

    private static final ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(200);

    private UUID ownerId;

    public CardsImpl() {
    }

    public CardsImpl(Card card) {
        if (card != null) {
            this.add(card.getId());
        }
    }

    public CardsImpl(Set<Card> cards) {
        for (Card card : cards) {
            this.add(card.getId());
        }
    }

    public CardsImpl(Collection<UUID> cardIds) {
        if (cardIds != null) {
            this.addAll(cardIds);
        }
    }

    public CardsImpl(final CardsImpl cards) {
        this.addAll(cards);
        this.ownerId = cards.ownerId;
    }

    @Override
    public Cards copy() {
        return new CardsImpl(this);
    }

    @Override
    public void add(Card card) {
        this.add(card.getId());
    }

    @Override
    public Card get(UUID cardId, Game game) {
        if (this.contains(cardId)) {
            return game.getCard(cardId);
        }
        return null;
    }

    @Override
    public void remove(Card card) {
        if (card == null) {
            return;
        }
        this.remove(card.getId());
    }

    @Override
    public void setOwner(UUID ownerId, Game game) {
        this.ownerId = ownerId;
        for (UUID card : this) {
            game.getCard(card).setOwnerId(ownerId);
        }
    }

    @Override
    public Card getRandom(Game game) {
        if (this.isEmpty()) {
            return null;
        }
        UUID[] cards = this.toArray(new UUID[this.size()]);
        MageObject object = game.getObject(cards[RandomUtil.nextInt(cards.length)]); // neccessary if permanent tokens are in the collection
        if (object instanceof Card) {
            return (Card) object;
        }
        return null;
    }

    @Override
    public int count(FilterCard filter, Game game) {
        return (int) stream().filter(cardId -> filter.match(game.getCard(cardId), game)).count();
    }

    @Override
    public int count(FilterCard filter, UUID playerId, Game game) {
        return (int) this.stream().filter(card -> filter.match(game.getCard(card), playerId, game)).count();

    }

    @Override
    public int count(FilterCard filter, UUID sourceId, UUID playerId, Game game) {
        if (sourceId == null) {
            return count(filter, playerId, game);
        }
        return (int) this.stream().filter(card -> filter.match(game.getCard(card), sourceId, playerId, game)).count();

    }

    @Override
    public Set<Card> getCards(FilterCard filter, UUID sourceId, UUID playerId, Game game) {
        Set<Card> cards = new LinkedHashSet<>();
        for (UUID cardId : this) {
            Card card = game.getCard(cardId);
            if (card != null) {
                boolean match = filter.match(card, sourceId, playerId, game);
                if (match) {
                    cards.add(game.getCard(cardId));
                }
            }
        }
        return cards;
    }

    @Override
    public Set<Card> getCards(FilterCard filter, Game game) {
        return stream().map(game::getCard).filter(card -> filter.match(card, game)).collect(Collectors.toSet());
    }

    @Override
    public Set<Card> getCards(Game game) {
        Set<Card> cards = new LinkedHashSet<>();
        for (Iterator<UUID> it = this.iterator(); it.hasNext();) { // Changed to iterator because of ConcurrentModificationException
            UUID cardId = it.next();

            Card card = game.getCard(cardId);
            if (card == null) {
                card = game.getPermanent(cardId); // needed to get TokenCard objects
            }
            if (card != null) { // this can happen during the cancelation (player concedes) of a game
                cards.add(card);
            }
        }
        return cards;
    }

    @Override
    public String getValue(Game game) {
        StringBuilder sb = threadLocalBuilder.get();
        List<String> cards = new ArrayList<>();
        for (UUID cardId : this) {
            Card card = game.getCard(cardId);
            cards.add(card.getName());
        }
        Collections.sort(cards);
        for (String name : cards) {
            sb.append(name).append(':');
        }
        return sb.toString();
    }

    @Override
    public void addAll(List<Card> cards) {
        for (Card card : cards) {
            add(card.getId());
        }
    }

    @Override
    public void addAll(Set<Card> cards) {
        for (Card card : cards) {
            add(card.getId());
        }
    }

    @Override
    public Collection<Card> getUniqueCards(Game game) {
        Map<String, Card> cards = new HashMap<>();
        for (UUID cardId : this) {
            Card card = game.getCard(cardId);
            cards.putIfAbsent(card.getName(), card);
        }
        return cards.values();
    }

}
