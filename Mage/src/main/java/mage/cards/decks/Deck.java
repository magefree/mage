package mage.cards.decks;

import mage.MageObject;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.game.GameException;
import mage.util.Copyable;
import mage.util.DeckUtil;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Server side deck with workable cards, also can be used in GUI
 * <p>
 * If you need text only deck then look at DeckCardLists
 */
public class Deck implements Serializable, Copyable<Deck> {

    static final int MAX_CARDS_PER_DECK = 2000;

    private String name; // TODO: must rework somehow - tiny leaders use deck name to find commander card and hide it for a user
    private final Set<Card> cards = new LinkedHashSet<>();
    private final Set<Card> sideboard = new LinkedHashSet<>();
    private DeckCardLayout cardsLayout; // client side only
    private DeckCardLayout sideboardLayout; // client side only

    public Deck() {
        super();
    }

    protected Deck(final Deck deck) {
        this.name = deck.name;
        this.cards.addAll(deck.cards.stream().map(Card::copy).collect(Collectors.toList()));
        this.sideboard.addAll(deck.sideboard.stream().map(Card::copy).collect(Collectors.toList()));
        this.cardsLayout = deck.cardsLayout == null ? null : deck.cardsLayout.copy();
        this.sideboardLayout = deck.sideboardLayout == null ? null : deck.sideboardLayout.copy();
    }

    public static Deck load(DeckCardLists deckCardLists) throws GameException {
        return Deck.load(deckCardLists, false);
    }

    public static Deck load(DeckCardLists deckCardLists, boolean ignoreErrors) throws GameException {
        return Deck.load(deckCardLists, ignoreErrors, true);
    }

    public static Deck append(Deck sourceDeck, Deck currentDeck) throws GameException {
        Deck newDeck = currentDeck.copy();
        sourceDeck.getCards().forEach(card -> {
            newDeck.cards.add(card.copy());
        });
        sourceDeck.getSideboard().forEach(card -> {
            newDeck.sideboard.add(card.copy());
        });
        return newDeck;
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

        // load only real cards
        int totalCards = 0;

        // main
        main:
        for (DeckCardInfo deckCardInfo : deckCardLists.getCards()) {
            for (int i = 1; i <= deckCardInfo.getAmount(); i++) {
                if (totalCards > MAX_CARDS_PER_DECK) {
                    break main;
                }

                Card card = createCard(deckCardInfo, mockCards, cardInfoCache);
                if (card != null) {
                    deck.cards.add(card);
                    totalCards++;
                } else if (!ignoreErrors) {
                    throw createCardNotFoundGameException(deckCardInfo, deckCardLists.getName());
                }
            }
        }

        // sideboard
        side:
        for (DeckCardInfo deckCardInfo : deckCardLists.getSideboard()) {
            for (int i = 1; i <= deckCardInfo.getAmount(); i++) {
                if (totalCards > MAX_CARDS_PER_DECK) {
                    break side;
                }
                Card card = createCard(deckCardInfo, mockCards, cardInfoCache);
                if (card != null) {
                    deck.sideboard.add(card);
                    totalCards++;
                } else if (!ignoreErrors) {
                    throw createCardNotFoundGameException(deckCardInfo, deckCardLists.getName());
                }
            }
        }

        return deck;
    }

    private static GameException createCardNotFoundGameException(DeckCardInfo deckCardInfo, String deckName) {
        // Try WORKAROUND for Card DB error: Try to read a card that does exist
        CardInfo cardInfo = CardRepository.instance.findCard("Silvercoat Lion");
        if (cardInfo == null) {
            // DB seems to have a problem - try to restart the DB (useless in 99%)
            CardRepository.instance.closeDB();
            CardRepository.instance.openDB();
            cardInfo = CardRepository.instance.findCard("Silvercoat Lion");
            Logger.getLogger(Deck.class).error("Tried to restart the DB: " + (cardInfo == null ? "not successful" : "successful"));
        }

        if (cardInfo != null) {
            // it's ok, just unknown card
            String cardError = String.format("Card not found - %s - %s - %s in deck %s.",
                    deckCardInfo.getCardName(),
                    deckCardInfo.getSetCode(),
                    deckCardInfo.getCardNumber(),
                    deckName
            );
            cardError += "\n\nPossible reasons:";
            cardError += "\n - deck problem: un-implemented card or outdated set (fix it by open in deck editor);";
            cardError += "\n - server problem: memory issue (load your deck again or wait a server's restart).";
            return new GameException(cardError);
        } else {
            // critical error, server must be restarted
            // TODO: add auto-restart task here someday (with a docker support)
            //  see https://github.com/magefree/mage/issues/8130
            return new GameException("Problems detected on the server side (memory issue), wait for a restart.");
        }
    }

    private static Card createCard(DeckCardInfo deckCardInfo, boolean mockCards, Map<String, CardInfo> cardInfoCache) {
        CardInfo cardInfo;
        if (cardInfoCache != null) {
            // from cache
            String key = String.format("%s_%s", deckCardInfo.getSetCode(), deckCardInfo.getCardNumber());
            cardInfo = cardInfoCache.getOrDefault(key, null);
            if (cardInfo == null) {
                cardInfo = CardRepository.instance.findCard(deckCardInfo.getSetCode(), deckCardInfo.getCardNumber());
                cardInfoCache.put(key, cardInfo);
            }
        } else {
            // from db
            cardInfo = CardRepository.instance.findCard(deckCardInfo.getSetCode(), deckCardInfo.getCardNumber());
        }

        if (cardInfo == null) {
            return null;
        }

        if (mockCards) {
            return cardInfo.createMockCard();
        } else {
            return cardInfo.createCard();
        }
    }

    /**
     * Prepare new deck with cards only without layout and other client side settings.
     * Use it for any export and server's deck update/submit
     */
    public DeckCardLists prepareCardsOnlyDeck() {
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

    // TODO: delete and replace by getCards()
    public Set<Card> getMaindeckCards() {
        return cards
                .stream()
                .filter(card -> !card.isExtraDeckCard())
                .collect(Collectors.toSet());
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

    public void clearLayouts() {
        this.cardsLayout = null;
        this.sideboardLayout = null;
    }

    @Override
    public Deck copy() {
        return new Deck(this);
    }

    public long getDeckHash() {
        return DeckUtil.getDeckHash(
                this.cards.stream().map(MageObject::getName).collect(Collectors.toList()),
                this.sideboard.stream().map(MageObject::getName).collect(Collectors.toList())
        );
    }
}
