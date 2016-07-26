/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.serverside.deck;

import java.util.ArrayList;
import java.util.List;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.deck.Limited;
import mage.deck.Modern;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DeckValidatorTest extends CardTestPlayerBase {

    class CardNameAmount {

        String name;
        String setCode;
        String cardNumber;

        int number;

        public CardNameAmount(String setCode, int cardNumber, int number) {
            this.name = "";
            this.setCode = setCode;
            this.cardNumber = String.valueOf(cardNumber);
            this.number = number;
        }

        public CardNameAmount(String name, int number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }

        public String getSetCode() {
            return setCode;
        }

        public String getCardNumber() {
            return cardNumber;
        }

    }

    @Test
    public void testLimitedValid() {
        ArrayList<CardNameAmount> deck = new ArrayList<>();

        deck.add(new CardNameAmount("Counterspell", 4));
        deck.add(new CardNameAmount("Mountain", 36));

        Assert.assertTrue("Deck should be valid", testDeckValid(new Limited(), deck));
    }

    @Test
    public void testLimitedNotValidToLessCards() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();

        deckList.add(new CardNameAmount("Counterspell", 4));
        deckList.add(new CardNameAmount("Mountain", 35));

        Assert.assertFalse("Deck should not be valid", testDeckValid(new Limited(), deckList));
    }

    @Test
    public void testModern1() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();

        deckList.add(new CardNameAmount("Counterspell", 5));
        deckList.add(new CardNameAmount("Mountain", 56));

        Assert.assertFalse("only 4 of a card are allowed", testDeckValid(new Modern(), deckList));
    }

    @Test
    public void testModernCounterspell1() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();
        deckList.add(new CardNameAmount("DD3JVC", 24, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("6ED", 61, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("5ED", 77, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("4ED", 65, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("JR", 5, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("DD2", 24, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("ICE", 64, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("FNMP", 66, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("LEA", 55, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("LEB", 55, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("ME4", 45, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("ME2", 44, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("S99", 34, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("7ED", 67, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("3ED", 54, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("MMQ", 69, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("VMA", 64, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("2ED", 55, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("TPR", 43, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("TMP", 57, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

        deckList.clear();
        deckList.add(new CardNameAmount("S00", 24, 4));
        deckList.add(new CardNameAmount("Mountain", 56));
        Assert.assertFalse("Counterspell not allowed in modern", testDeckValid(new Modern(), deckList));

    }

    @Test
    public void testModernBanned() {
        ArrayList<CardNameAmount> deckList = new ArrayList<>();
        deckList.add(new CardNameAmount("Ancestral Vision", 4));
        deckList.add(new CardNameAmount("Ancient Den", 4));
        deckList.add(new CardNameAmount("Birthing Pod", 4));
        deckList.add(new CardNameAmount("Blazing Shoal", 4));
        deckList.add(new CardNameAmount("Bloodbraid Elf", 4));
        deckList.add(new CardNameAmount("Chrome Mox", 4));
        deckList.add(new CardNameAmount("Cloudpost", 4));
        deckList.add(new CardNameAmount("Dark Depths", 4));
        deckList.add(new CardNameAmount("Deathrite Shaman", 4));
        deckList.add(new CardNameAmount("Dig Through Time", 4));
        deckList.add(new CardNameAmount("Dread Return", 4));
        deckList.add(new CardNameAmount("Glimpse of Nature", 4));
        deckList.add(new CardNameAmount("Great Furnace", 4));
        deckList.add(new CardNameAmount("Green Sun's Zenith", 4));
        deckList.add(new CardNameAmount("Hypergenesis", 4));
        deckList.add(new CardNameAmount("Jace, the Mind Sculptor", 4));
        deckList.add(new CardNameAmount("Mental Misstep", 4));
        Assert.assertFalse("banned cards are not allowed", testDeckValid(new Modern(), deckList));
    }

    private boolean testDeckValid(DeckValidator validator, List<CardNameAmount> cards) {
        Deck deckToTest = new Deck();
        for (CardNameAmount cardNameAmount : cards) {
            CardInfo cardinfo;
            if (cardNameAmount.getName().isEmpty()) {
                cardinfo = CardRepository.instance.findCard(cardNameAmount.getSetCode(), cardNameAmount.getCardNumber());
            } else {
                cardinfo = CardRepository.instance.findCard(cardNameAmount.getName());
            }
            for (int i = 0; i < cardNameAmount.getNumber(); i++) {
                deckToTest.getCards().add(cardinfo.getCard());
            }
        }
        return validator.validate(deckToTest);
    }
}
