package mage.cards.decks;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.game.GameException;
import mage.util.Copyable;
import mage.util.DeckUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Deck implements Serializable, Copyable<Deck> {

    static final int MAX_CARDS_PER_DECK = 1000;

    private String name;
    private final Set<Card> cards = new LinkedHashSet<>();
    private final Set<Card> sideboard = new LinkedHashSet<>();
    private DeckCardLayout cardsLayout;
    private DeckCardLayout sideboardLayout;
    private long deckHashCode = 0;
    private long deckCompleteHashCode = 0;

    public Deck() {
        super();
    }

    public Deck(final Deck deck) {
        this.name = deck.name;
        this.cards.addAll(deck.cards.stream().map(Card::copy).collect(Collectors.toList()));
        this.sideboard.addAll(deck.sideboard.stream().map(Card::copy).collect(Collectors.toList()));
        this.cardsLayout = deck.cardsLayout == null ? null : deck.cardsLayout.copy();
        this.sideboardLayout = deck.sideboardLayout == null ? null : deck.sideboardLayout.copy();
        this.deckHashCode = deck.deckHashCode;
        this.deckCompleteHashCode = deck.deckCompleteHashCode;
    }

    public static Deck load(DeckCardLists deckCardLists) throws GameException {
        return Deck.load(deckCardLists, false);
    }

    public static Deck load(DeckCardLists deckCardLists, boolean ignoreErrors) throws GameException {
        return Deck.load(deckCardLists, ignoreErrors, true);
    }

    public static Deck append(Deck deckToAppend, Deck currentDeck) throws GameException {
        List<String> deckCardNames = new ArrayList<>();

        for (Card card : deckToAppend.getCards()) {
            if (card != null) {
                currentDeck.cards.add(card);
                deckCardNames.add(card.getName());
            }
        }
        List<String> sbCardNames = new ArrayList<>();
        for (Card card : deckToAppend.getSideboard()) {
            if (card != null) {
                currentDeck.sideboard.add(card);
                deckCardNames.add(card.getName());
            }
        }
        Collections.sort(deckCardNames);
        Collections.sort(sbCardNames);
        String deckString = deckCardNames.toString() + sbCardNames.toString();
        currentDeck.setDeckHashCode(DeckUtil.fixedHash(deckString));
        return currentDeck;
    }

    public static Deck load(DeckCardLists deckCardLists, boolean ignoreErrors, boolean mockCards) throws GameException {
        return load(deckCardLists, ignoreErrors, mockCards, null);
    }

    /**
     * Warning, AI can't play Mock cards, so call it with extra params in real games or tests
     *
     * @param deckCardLists cards to load
     * @param ignoreErrors  - do not raise exception error on wrong deck
     * @param mockCards     - use it for GUI only code, real game cards must be real
     * @return
     * @throws GameException
     */
    public static Deck load(DeckCardLists deckCardLists, boolean ignoreErrors, boolean mockCards, Map<String, CardInfo> cardInfoCache) throws GameException {
        Deck deck = new Deck();
        deck.setName(deckCardLists.getName());
        deck.cardsLayout = deckCardLists.getCardLayout() == null ? null : deckCardLists.getCardLayout().copy();
        deck.sideboardLayout = deckCardLists.getSideboardLayout() == null ? null : deckCardLists.getSideboardLayout().copy();
        List<String> deckCardNames = new ArrayList<>();
        int totalCards = 0;
        for (DeckCardInfo deckCardInfo : deckCardLists.getCards()) {
            Card card = createCard(deckCardInfo, mockCards, cardInfoCache);
            if (card != null) {
                if (totalCards > MAX_CARDS_PER_DECK) {
                    break;
                }
                deck.cards.add(card);
                deckCardNames.add(card.getName());
                totalCards++;

            } else if (!ignoreErrors) {
                throw createCardNotFoundGameException(deckCardInfo, deckCardLists.getName());
            }
        }
        List<String> sbCardNames = new ArrayList<>();
        for (DeckCardInfo deckCardInfo : deckCardLists.getSideboard()) {
            Card card = createCard(deckCardInfo, mockCards, cardInfoCache);
            if (card != null) {
                if (totalCards > MAX_CARDS_PER_DECK) {
                    break;
                }
                deck.sideboard.add(card);
                sbCardNames.add(card.getName());
                totalCards++;
            } else if (!ignoreErrors) {
                throw createCardNotFoundGameException(deckCardInfo, deckCardLists.getName());
            }
        }
        Collections.sort(deckCardNames);
        Collections.sort(sbCardNames);
        String deckString = deckCardNames.toString() + sbCardNames.toString();
        deck.setDeckHashCode(DeckUtil.fixedHash(deckString));
        if (sbCardNames.isEmpty()) {
            deck.setDeckCompleteHashCode(deck.getDeckHashCode());
        } else {
            List<String> deckAllCardNames = new ArrayList<>();
            deckAllCardNames.addAll(deckCardNames);
            deckAllCardNames.addAll(sbCardNames);
            Collections.sort(deckAllCardNames);
            deck.setDeckCompleteHashCode(DeckUtil.fixedHash(deckAllCardNames.toString()));
        }
        return deck;
    }

    private static GameException createCardNotFoundGameException(DeckCardInfo deckCardInfo, String deckName) {
        // Try WORKAROUND for Card DB error: Try to read a card that does exist
        CardInfo cardInfo = CardRepository.instance.findCard("Silvercoat Lion");
        if (cardInfo == null) {
            // DB seems to have a problem - try to restart the DB
            CardRepository.instance.closeDB();
            CardRepository.instance.openDB();
            cardInfo = CardRepository.instance.findCard("Silvercoat Lion");
            Logger.getLogger(Deck.class).error("Tried to restart the DB: " + (cardInfo == null ? "not successful" : "successful"));
        }
        return new GameException("Card not found - " + deckCardInfo.getCardName() + " - " + deckCardInfo.getSetCode() + "/" + deckCardInfo.getCardNum() + " for deck - " + deckName + '\n'
                + "Possible reason is, that you use cards in your deck, that are only supported in newer versions of the server.\n"
                + "So it can help to use the same card from another set, that's already supported from this server.");

    }

    private static Card createCard(DeckCardInfo deckCardInfo, boolean mockCards, Map<String, CardInfo> cardInfoCache) {
        CardInfo cardInfo;
        if (cardInfoCache != null) {
            // from cache
            String key = String.format("%s_%s", deckCardInfo.getSetCode(), deckCardInfo.getCardNum());
            cardInfo = cardInfoCache.getOrDefault(key, null);
            if (cardInfo == null) {
                cardInfo = CardRepository.instance.findCard(deckCardInfo.getSetCode(), deckCardInfo.getCardNum());
                cardInfoCache.put(key, cardInfo);
            }
        } else {
            // from db
            cardInfo = CardRepository.instance.findCard(deckCardInfo.getSetCode(), deckCardInfo.getCardNum());
        }

        if (cardInfo == null) {
            return null;
        }

        if (mockCards) {
            return cardInfo.getMockCard();
        } else {
            return cardInfo.getCard();
        }
    }

    public DeckCardLists getDeckCardLists() {
        DeckCardLists deckCardLists = new DeckCardLists();

        deckCardLists.setName(name);
        for (Card card : cards) {
            deckCardLists.getCards().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getExpansionSetCode()));
        }
        for (Card card : sideboard) {
            deckCardLists.getSideboard().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getExpansionSetCode()));
        }

        return deckCardLists;
    }

    public Set<String> getExpansionSetCodes() {
        Set<String> sets = new LinkedHashSet<>();
        for (Card card : getCards()) {
            sets.add(card.getExpansionSetCode());
        }
        for (Card card : getSideboard()) {
            sets.add(card.getExpansionSetCode());
        }
        return sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public Card findCard(UUID cardId) {
        return cards
                .stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElse(null);
    }

    public DeckCardLayout getCardsLayout() {
        return cardsLayout;
    }

    public Set<Card> getSideboard() {
        return sideboard;
    }

    public Card findSideboardCard(UUID cardId) {
        return sideboard
                .stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElse(null);
    }

    public DeckCardLayout getSideboardLayout() {
        return sideboardLayout;
    }

    public long getDeckHashCode() {
        return deckHashCode;
    }

    public void setDeckHashCode(long deckHashCode) {
        this.deckHashCode = deckHashCode;
    }

    public long getDeckCompleteHashCode() {
        return deckCompleteHashCode;
    }

    public void setDeckCompleteHashCode(long deckHashCode) {
        this.deckCompleteHashCode = deckHashCode;
    }

    public void clearLayouts() {
        this.cardsLayout = null;
        this.sideboardLayout = null;
    }

    @Override
    public Deck copy() {
        return new Deck(this);
    }
}
