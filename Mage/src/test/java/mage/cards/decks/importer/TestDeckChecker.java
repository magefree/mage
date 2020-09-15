package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        assertEquals("main deck size", nMain, main.size());
        assertEquals("main deck loaded size", nMain, deck.getCards().size());
        assertEquals("sideboard size", nSide, side.size());
        assertEquals("sideboard loaded size", nSide, deck.getSideboard().size());

        for (int i = 0; i < main.size(); i++) {
            String expected = main.get(i);
            String actual = deck.getCards().get(i).getCardName();
            assertEquals(String.format("Expected: '%s' Actual: '%s' at index: %s",
                    expected, actual, i), expected, actual);
        }
    }

    public static TestDeckChecker checker() {
        return new TestDeckChecker();
    }

}
