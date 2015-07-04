/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
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

public class Deck implements Serializable {

    private String name;
    private final Set<Card> cards = new LinkedHashSet<>();
    private final Set<Card> sideboard = new LinkedHashSet<>();
    private long deckHashCode = 0;

    public static Deck load(DeckCardLists deckCardLists) throws GameException {
        return Deck.load(deckCardLists, false);
    }

    public static Deck load(DeckCardLists deckCardLists, boolean ignoreErrors) throws GameException {
        return Deck.load(deckCardLists, ignoreErrors, true);
    }

    public static Deck load(DeckCardLists deckCardLists, boolean ignoreErrors, boolean mockCards) throws GameException {
        Deck deck = new Deck();
        deck.setName(deckCardLists.getName());
        List<String> deckCardNames = new ArrayList<>();
        for (DeckCardInfo deckCardInfo : deckCardLists.getCards()) {
            Card card = createCard(deckCardInfo, mockCards);
            if (card != null) {
                deck.cards.add(card);
                deckCardNames.add(card.getName());
            } else if (!ignoreErrors) {
                throw createCardNotFoundGameException(deckCardInfo, deckCardLists.getName());
            }
        }
        List<String> sbCardNames = new ArrayList<>();
        for (DeckCardInfo deckCardInfo : deckCardLists.getSideboard()) {
            Card card = createCard(deckCardInfo, mockCards);
            if (card != null) {
                deck.sideboard.add(card);
                sbCardNames.add(card.getName());
            } else if (!ignoreErrors) {
                throw createCardNotFoundGameException(deckCardInfo, deckCardLists.getName());
            }
        }
        Collections.sort(deckCardNames);
        Collections.sort(sbCardNames);
        String deckString = deckCardNames.toString() + sbCardNames.toString();
        deck.setDeckHashCode(DeckUtil.fixedHash(deckString));
//        try{
//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//            messageDigest.update(deckString.getBytes());
//            String encryptedString = new String(messageDigest.digest());
//            deck.setDeckHashCode(encryptedString.hashCode());
//        }
//        catch (NoSuchAlgorithmException e) {
//            // nothing
//        }
        return deck;
    }

    private static GameException createCardNotFoundGameException(DeckCardInfo deckCardInfo, String deckName) {
        return new GameException("Card not found - " + deckCardInfo.getCardName() + " - " + deckCardInfo.getSetCode() + " for deck - " + deckName + "\n"
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
            if (!sets.contains(card.getExpansionSetCode())) {
                sets.add(card.getExpansionSetCode());
            }
        }
        for (Card card : getSideboard()) {
            if (!sets.contains(card.getExpansionSetCode())) {
                sets.add(card.getExpansionSetCode());
            }
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

    public Set<Card> getSideboard() {
        return sideboard;
    }

    public long getDeckHashCode() {
        return deckHashCode;
    }

    public void setDeckHashCode(long deckHashCode) {
        this.deckHashCode = deckHashCode;
    }

}
