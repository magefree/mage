
package mage.cards.decks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.game.GameException;
import mage.util.DeckUtil;
import org.apache.log4j.Logger;

public class Deck implements Serializable {

    private String name;
    private final Set<Card> cards = new LinkedHashSet<>();
    private final Set<Card> sideboard = new LinkedHashSet<>();
    private DeckCardLayout cardsLayout;
    private DeckCardLayout sideboardLayout;
    private long deckHashCode = 0;
    private long deckCompleteHashCode = 0;

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
        Deck deck = new Deck();
        deck.setName(deckCardLists.getName());
        deck.cardsLayout = deckCardLists.getCardLayout();
        deck.sideboardLayout = deckCardLists.getSideboardLayout();
        List<String> deckCardNames = new ArrayList<>();
        int totalCards = 0;
        for (DeckCardInfo deckCardInfo : deckCardLists.getCards()) {
            Card card = createCard(deckCardInfo, mockCards);
            if (card != null) {
                if (totalCards > 1000) {
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
            Card card = createCard(deckCardInfo, mockCards);
            if (card != null) {
                if (totalCards > 1000) {
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

    private static Card createCard(DeckCardInfo deckCardInfo, boolean mockCards) {
        CardInfo cardInfo = CardRepository.instance.findCard(deckCardInfo.getSetCode(), deckCardInfo.getCardNum());
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

    public DeckCardLayout getCardsLayout() {
        return cardsLayout;
    }

    public Set<Card> getSideboard() {
        return sideboard;
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

}
