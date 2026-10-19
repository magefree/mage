package mage.util;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.CardType;
import mage.constants.SetType;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ArenaCreaturePoolTest {

    @Test
    public void testSnapshotAvailability() {
        ArenaCreaturePool pool = ArenaCreaturePool.getInstance();
        assertTrue(pool.getAllCardNames().size() > 9000);
        assertEquals(Collections.singleton("Emrakul, the Promised End"), pool.getCardNames(13));
        assertTrue(pool.getCardNames(9).contains("Bringer of the Blue Dawn"));
        assertTrue(pool.getCardNames(3).contains("Anina, Natural Parallelist"));
        assertTrue(pool.getCardNames(0).contains("Stonecoil Serpent"));
        assertTrue(pool.getCardNames(15).contains("Emrakul, the Aeons Torn"));
        assertFalse(pool.getAllCardNames().contains("Draco"));
        assertFalse(pool.getAllCardNames().contains("Walking Ballista"));
        assertFalse(pool.getAllCardNames().contains("Autochthon Wurm"));
        assertFalse(pool.getAllCardNames().contains("Insectile Aberration"));
        assertFalse(pool.getAllCardNames().contains("Etali, Primal Sickness"));
        assertFalse(pool.getAllCardNames().contains("Ragnarok, Divine Deliverance"));
        assertEquals("Sol'Kanar the Tainted", pool.getCanonicalName("Sol'kanar the Tainted", 5));
        assertEquals(1, pool.getAllCardNames().stream()
                .filter(name -> name.equalsIgnoreCase("Sol'kanar the Tainted")).count());
        assertTrue(pool.getCardNames(14).isEmpty());
        assertTrue(pool.getCardNames(16).isEmpty());
        assertTrue(pool.getCardNames(-1).isEmpty());
    }

    @Test
    public void testReprintsDoNotAddWeightAndNoFallbackForUnsupportedNames() throws IOException {
        ArenaCreaturePool pool = new ArenaCreaturePool(new StringReader("2\tAvailable\n2\tUnsupported\n"));
        CardInfo available = new Info("Available", 2);
        Map<Integer, List<CardInfo>> index = index(pool, Arrays.asList(
                available, new Info("Available", 2), new Info("Paper only", 2),
                new Info("Available", 3)));
        assertEquals(Collections.singletonList(available), index.get(2));
        assertFalse(index.containsKey(3));
        assertTrue(index(pool, Collections.singletonList(new Info("Paper only", 2))).isEmpty());
    }

    @Test
    public void testAsciiAndCaseAliasesKeepOneCatalogIdentity() throws IOException {
        ArenaCreaturePool pool = new ArenaCreaturePool(new StringReader("3\tNazgûl\n5\tSol'kanar the Tainted\n"));
        assertEquals("Nazgûl", pool.getCanonicalName("Nazgul", 3));
        assertEquals("Sol'kanar the Tainted", pool.getCanonicalName("Sol'Kanar the Tainted", 5));
        assertNull(pool.getCanonicalName("Nazgul", 4));
        assertNull(pool.getCanonicalName("Naz-gul", 3));
        Map<Integer, List<CardInfo>> index = index(pool, Arrays.asList(
                new Info("Nazgul", 3), new Info("Nazgûl", 3), new Info("NAZGUL", 3)));
        assertEquals(1, index.get(3).size());
    }

    private static Map<Integer, List<CardInfo>> index(ArenaCreaturePool pool, List<CardInfo> cards) {
        ExpansionSet legacy = new ExpansionSet("Legacy", "TEST", ExpansionSet.buildDate(2000, 1, 1), SetType.CORE) { };
        return new ArenaCreatureSelector(cards, pool, code -> legacy,
                () -> Instant.parse("2030-01-01T00:00:00Z")).getCards();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCatalogIsImmutable() {
        ArenaCreaturePool.getInstance().getCardNames(13).clear();
    }

    @Test
    public void testMalformedCatalogsFailInsteadOfBroadeningPool() {
        for (String input : Arrays.asList("", "# comments only\n", "-1\tExample\n", "2\t\n",
                "2\tExample\textra\n", "2\tExample\n3\tExample\n", "999999999999\tExample\n",
                "3\tNazgul\n3\tNazgûl\n")) {
            try {
                new ArenaCreaturePool(new StringReader(input));
                fail("Accepted invalid catalog: " + input);
            } catch (IOException expected) {
                // Required: unavailable data must not become a paper-card fallback.
            }
        }
    }

    private static class Info extends CardInfo {

        Info(String name, int manaValue) {
            this.name = name;
            this.manaValue = manaValue;
            this.setCode = "TEST";
            setTypes(Collections.singletonList(CardType.CREATURE));
        }
    }
}
