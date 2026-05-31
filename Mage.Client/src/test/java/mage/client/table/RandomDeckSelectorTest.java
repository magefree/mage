package mage.client.table;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomDeckSelectorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void returnsFilePathUnchanged() throws Exception {
        File deckFile = temporaryFolder.newFile("deck.dck");

        assertEquals(deckFile.getPath(), RandomDeckSelector.resolveDeckPath(deckFile.getPath()));
    }

    @Test
    public void selectsDeckFromDirectoryTree() throws Exception {
        File firstDeck = temporaryFolder.newFile("first.dck");
        File subFolder = temporaryFolder.newFolder("sub");
        File secondDeck = new File(subFolder, "second.dck");
        assertTrue(secondDeck.createNewFile());
        assertTrue(new File(subFolder, "notes.txt").createNewFile());

        Set<String> expectedDecks = new HashSet<>(Arrays.asList(
                firstDeck.toPath().toString(),
                secondDeck.toPath().toString()));

        assertTrue(expectedDecks.contains(RandomDeckSelector.resolveDeckPath(temporaryFolder.getRoot().getPath())));
    }

    @Test
    public void selectsDifferentDecksWhenTrackingUsedDecks() throws Exception {
        File firstDeck = temporaryFolder.newFile("first.dck");
        File secondDeck = temporaryFolder.newFile("second.dck");
        Set<String> usedDecks = new HashSet<>();

        String selectedFirst = RandomDeckSelector.resolveDeckPath(temporaryFolder.getRoot().getPath(), usedDecks);
        String selectedSecond = RandomDeckSelector.resolveDeckPath(temporaryFolder.getRoot().getPath(), usedDecks);

        Set<String> expectedDecks = new HashSet<>(Arrays.asList(
                firstDeck.toPath().toString(),
                secondDeck.toPath().toString()));

        assertTrue(expectedDecks.contains(selectedFirst));
        assertTrue(expectedDecks.contains(selectedSecond));
        assertTrue(!selectedFirst.equals(selectedSecond));
    }

    @Test
    public void allowsDuplicateExplicitDeckWhenTrackingUsedDecks() throws Exception {
        File deckFile = temporaryFolder.newFile("deck.dck");
        Set<String> usedDecks = new HashSet<>();

        assertEquals(deckFile.getPath(), RandomDeckSelector.resolveDeckPath(deckFile.getPath(), usedDecks));
        assertEquals(deckFile.getPath(), RandomDeckSelector.resolveDeckPath(deckFile.getPath(), usedDecks));
    }

    @Test
    public void fallsBackToUsedDecksWhenAllDecksAlreadyUsed() throws Exception {
        temporaryFolder.newFile("only.dck");
        Set<String> usedDecks = new HashSet<>();

        String selectedFirst = RandomDeckSelector.resolveDeckPath(temporaryFolder.getRoot().getPath(), usedDecks);
        String selectedSecond = RandomDeckSelector.resolveDeckPath(temporaryFolder.getRoot().getPath(), usedDecks);

        assertEquals(selectedFirst, selectedSecond);
    }

    @Test(expected = FileNotFoundException.class)
    public void rejectsDirectoryWithoutDecks() throws Exception {
        RandomDeckSelector.resolveDeckPath(temporaryFolder.newFolder("empty").getPath());
    }
}
