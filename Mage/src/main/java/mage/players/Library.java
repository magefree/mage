package mage.players;

import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.RandomUtil;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Library implements Serializable {

    private boolean emptyDraw;
    private final Deque<UUID> library = new ArrayDeque<>();
    private final UUID playerId;

    public Library(UUID playerId) {
        this.playerId = playerId;
    }

    public Library(final Library lib) {
        this.emptyDraw = lib.emptyDraw;
        this.playerId = lib.playerId;
        for (UUID id : lib.library) {
            this.library.addLast(id);
        }
    }

    /**
     * Don't use this directly. Use <player.shuffleLibrary(game)> instead.
     */
    public void shuffle() {
        UUID[] shuffled = library.toArray(new UUID[0]);
        for (int n = shuffled.length - 1; n > 0; n--) {
            int r = RandomUtil.nextInt(n + 1);
            UUID temp = shuffled[n];
            shuffled[n] = shuffled[r];
            shuffled[r] = temp;
        }
        library.clear();
        library.addAll(Arrays.asList(shuffled));
    }

    /**
     * Removes the top card of the Library and returns it
     *
     * @param game
     * @return Card
     * @see Card
     */
    public Card removeFromTop(Game game) {
        UUID cardId = library.pollFirst();
        Card card = game.getCard(cardId);
        if (card == null) {
            emptyDraw = true;
        }
        return card;
    }

    /**
     * Removes the bottom card of the Library and returns it
     *
     * @param game
     * @return Card
     * @see Card
     */
    public Card removeFromBottom(Game game) {
        UUID cardId = library.pollLast();
        Card card = game.getCard(cardId);
        if (card == null) {
            emptyDraw = true;
        }
        return card;
    }

    /**
     * Returns the top card of the Library without removing it
     *
     * @param game
     * @return Card
     * @see Card
     */
    public Card getFromTop(Game game) {
        return game.getCard(library.peekFirst());
    }

    /**
     * Returns the bottommost card of the Library without removing it
     *
     * @param game
     * @return Card
     * @see Card
     */
    public Card getFromBottom(Game game) {
        return game.getCard(library.pollLast());
    }

    public void putOnTop(Card card, Game game) {
        if (card.isOwnedBy(playerId)) {
            card.setZone(Zone.LIBRARY, game);
            library.addFirst(card.getId());
        } else {
            game.getPlayer(card.getOwnerId()).getLibrary().putOnTop(card, game);
        }
    }

    public void putCardToTopXPos(Card card, int pos, Game game) {
        if (card != null && pos > -1) {
            LinkedList<Card> save = new LinkedList<>();
            int idx = 1;
            while (hasCards() && idx < pos) {
                idx++;
                save.add(removeFromTop(game));
            }
            putOnTop(card, game);
            while (!save.isEmpty()) {
                putOnTop(save.removeLast(), game);
            }
        }
    }

    public void putOnBottom(Card card, Game game) {
        if (card.isOwnedBy(playerId)) {
            card.setZone(Zone.LIBRARY, game);
            library.remove(card.getId());
            library.add(card.getId());
        } else {
            game.getPlayer(card.getOwnerId()).getLibrary().putOnBottom(card, game);
        }
    }

    public Library copy() {
        return new Library(this);
    }

    public void clear() {
        library.clear();
    }

    public int size() {
        return library.size();
    }

    public void set(Library newLibrary) {
        library.clear();
        for (UUID card : newLibrary.getCardList()) {
            library.add(card);
        }
    }

    public List<UUID> getCardList() {
        return new ArrayList<>(library);
    }

    /**
     * Returns the cards of the library in a list ordered from top to buttom
     *
     * @param game
     * @return
     */
    public List<Card> getCards(Game game) {
        return library.stream().map(game::getCard).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Set<Card> getTopCards(Game game, int amount) {
        Set<Card> cards = new LinkedHashSet<>();
        Iterator<UUID> it = library.iterator();
        int count = 0;
        while (it.hasNext() && count < amount) {
            UUID cardId = it.next();
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.add(card);
                ++count;
            }
        }
        return cards;
    }

    public Collection<Card> getUniqueCards(Game game) {
        Map<String, Card> cards = new HashMap<>();
        for (UUID cardId : library) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.putIfAbsent(card.getName(), card);
            }
        }
        return cards.values();
    }

    public int count(FilterCard filter, Game game) {
        return (int) library.stream().filter(cardId -> filter.match(game.getCard(cardId), game)).count();
    }

    public boolean isEmptyDraw() {
        return emptyDraw;
    }

    public void addAll(Set<Card> cards, Game game) {
        for (Card card : cards) {
            card.setZone(Zone.LIBRARY, game);
            library.add(card.getId());
        }
    }

    public Card getCard(UUID cardId, Game game) {
        for (UUID card : library) {
            if (card.equals(cardId)) {
                return game.getCard(card);
            }
        }
        return null;
    }

    public Card remove(UUID cardId, Game game) {
        Iterator<UUID> it = library.iterator();
        while (it.hasNext()) {
            UUID card = it.next();
            if (card.equals(cardId)) {
                it.remove();
                return game.getCard(card);
            }
        }
        return null;
    }

    public boolean hasCards() {
        return size() > 0;
    }

    public void reset() {
        this.emptyDraw = false;
    }

    /**
     * Tests only -- find card position in library
     * @param cardId
     * @return 
     */
    public int getCardPosition(UUID cardId) {
        UUID[] list = library.toArray(new UUID[0]);
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(cardId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Cards: " + library.size();
    }
}
