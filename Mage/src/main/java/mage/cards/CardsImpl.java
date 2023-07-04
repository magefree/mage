package mage.cards;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.RandomUtil;
import mage.util.ThreadLocalStringBuilder;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    public CardsImpl(List<? extends Card> cards) {
        this.addAllCards(cards);
    }

    public CardsImpl(Set<? extends Card> cards) {
        this.addAllCards(cards);
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
        if (card == null) {
            return;
        }
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
    public boolean remove(Card card) {
        if (card == null) {
            return false;
        }
        return this.remove(card.getId());
    }

    @Override
    public Card getRandom(Game game) {
        if (this.isEmpty()) {
            return null;
        }

        // neccessary if permanent tokens are in the collection
        Set<MageObject> cardsForRandomPick = this
                .stream().map(uuid -> game.getObject(uuid))
                .filter(Objects::nonNull)
                .filter(mageObject -> mageObject instanceof Card)
                .collect(Collectors.toSet());

        return (Card) RandomUtil.randomFromCollection(cardsForRandomPick);
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
    public int count(FilterCard filter, UUID playerId, Ability source, Game game) {
        if (source == null) {
            return count(filter, playerId, game);
        }
        return (int) this.stream().filter(card -> filter.match(game.getCard(card), playerId, source, game)).count();

    }

    @Override
    public Set<Card> getCards(FilterCard filter, UUID playerId, Ability source, Game game) {
        Set<Card> cards = new LinkedHashSet<>();
        for (UUID cardId : this) {
            Card card = game.getCard(cardId);
            if (card != null) {
                boolean match = filter.match(card, playerId, source, game);
                if (match) {
                    cards.add(game.getCard(cardId));
                }
            }
        }
        return cards;
    }

    // TODO: Why is this used a completely different implementation than the version without the filter?
    @Override
    public Set<Card> getCards(FilterCard filter, Game game) {
        return stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> filter.match(card, game))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Card> getCards(Game game) {
        Set<Card> cards = new LinkedHashSet<>();
        for (Iterator<UUID> it = this.iterator(); it.hasNext(); ) { // Changed to iterator because of ConcurrentModificationException
            UUID cardId = it.next();

            // cards from battlefield must be as permanent, not card (moveCards uses instanceOf Permanent)
            Card card = game.getPermanent(cardId);
            if (card == null) {
                card = game.getCard(cardId);
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
            if (card != null) {
                cards.add(card.getName());
            }
        }
        Collections.sort(cards);
        for (String name : cards) {
            sb.append(name).append(':');
        }
        return sb.toString();
    }

    @Override
    public void addAllCards(Collection<? extends Card> cards) {
        if (cards != null) {
            cards.stream()
                    .filter(Objects::nonNull)
                    .map(MageItem::getId)
                    .forEach(this::add);
        }
    }

    @Override
    public Collection<Card> getUniqueCards(Game game) {
        Map<String, Card> cards = new HashMap<>(this.size());

        for (UUID cardId : this) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.putIfAbsent(card.getName(), card);
            }
        }

        return cards.values();
    }

    @Override
    public void retainZone(Zone zone, Game game) {
        removeIf(uuid -> game.getState().getZone(uuid) != zone);
    }

    @Override
    public void removeZone(Zone zone, Game game) {
        removeIf(uuid -> game.getState().getZone(uuid) == zone);
    }
}
