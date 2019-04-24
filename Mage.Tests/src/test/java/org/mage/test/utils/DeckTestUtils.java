package org.mage.test.utils;

import mage.cards.Card;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.ColoredManaSymbol;
import mage.player.ai.ComputerPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JayDi85
 */
public class DeckTestUtils {

    private static final int DECK_SIZE = 40;

    public static Deck buildRandomDeck(String colors, boolean onlyBasicLands) {
        return buildRandomDeck(colors, onlyBasicLands, "");
    }

    public static Deck buildRandomDeck(String colors, boolean onlyBasicLands, String allowedSets) {

        List<ColoredManaSymbol> allowedColors = new ArrayList<>();
        for (int i = 0; i < colors.length(); i++) {
            char c = colors.charAt(i);
            allowedColors.add(ColoredManaSymbol.lookup(c));
        }

        List<String> allowedList = new ArrayList<>();
        if (allowedSets != null && !allowedSets.isEmpty()) {
            String[] codes = allowedSets.split(",");
            for (String code : codes) {
                if (!code.trim().isEmpty()) {
                    allowedList.add(code.trim());
                }
            }
        }

        List<Card> cardPool = Sets.generateRandomCardPool(45, allowedColors, onlyBasicLands, allowedList);
        return ComputerPlayer.buildDeck(DECK_SIZE, cardPool, allowedColors, onlyBasicLands);
    }

    public static DeckCardLists buildRandomDeckAndInitCards(String colors, boolean onlyBasicLands) {
        return buildRandomDeckAndInitCards(colors, onlyBasicLands, "");
    }

    public static DeckCardLists buildRandomDeckAndInitCards(String colors, boolean onlyBasicLands, String allowedSets) {
        Deck deck = buildRandomDeck(colors, onlyBasicLands, allowedSets);

        DeckCardLists deckList = new DeckCardLists();
        for (Card card : deck.getCards()) {
            CardInfo cardInfo = CardRepository.instance.findCard(card.getExpansionSetCode(), card.getCardNumber());
            if (cardInfo != null) {
                deckList.getCards().add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
            }
        }
        return deckList;
    }
}
