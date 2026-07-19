package org.mage.test.decks.importer;

import mage.cards.decks.DeckCardLists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests only: helper class to test decks
 * <p>
 * See decks format examples under download button
 * at <a href="https://mtg.fandom.com/wiki/Khans_of_Tarkir/Intro_packs">fandom wiki page</a>
 */
public class TestDeckChecker {

    private final List<String> main = new ArrayList<>();
    private final List<String> side = new ArrayList<>();

    public TestDeckChecker addMain(String name) {
        return addMain(name, 1);
    }

    public TestDeckChecker addMain(String name, int quantity) {
        main.addAll(Collections.nCopies(quantity, name));
        return this;
    }

    public TestDeckChecker addSide(String name) {
        return addSide(name, 1);
    }

    public TestDeckChecker addSide(String name, int quantity) {
        side.addAll(Collections.nCopies(quantity, name));
        return this;
    }

    public void verify(DeckCardLists deck, int nMain, int nSide) {
        assertEquals(nMain, main.size(), "main deck size");
        assertEquals(nMain, deck.getCards().size(), "main deck loaded size");
        assertEquals(nSide, side.size(), "sideboard size");
        assertEquals(nSide, deck.getSideboard().size(), "sideboard loaded size");

        for (int i = 0; i < main.size(); i++) {
            String expected = main.get(i);
            String actual = deck.getCards().get(i).getCardName();
            assertEquals(expected, actual, String.format("Expected: '%s' Actual: '%s' at index: %s",
                    expected, actual, i));
        }
    }

    public static TestDeckChecker checker() {
        return new TestDeckChecker();
    }
}
