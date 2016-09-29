package org.mage.test.sets;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import mage.cards.Card;
import mage.cards.repository.CardScanner;
import mage.sets.FateReforged;
import mage.sets.MastersEditionII;
import mage.sets.MastersEditionIV;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

/**
 *
 * @author nigelzor
 */
public class BoosterGenerationTest extends MageTestBase {

    @Before
    public void setUp() {
        CardScanner.scan();
    }

    private static final List<String> basics = Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest");

    @Test
    public void testFateReforged() {
        List<String> tapland = Arrays.asList(
                "Bloodfell Caves", "Blossoming Sands", "Dismal Backwater", "Jungle Hollow", "Rugged Highlands",
                "Scoured Barrens", "Swiftwater Cliffs", "Thornwood Falls", "Tranquil Cove", "Wind-Scarred Crag");
        List<String> fetchland = Arrays.asList(
                "Bloodstained Mire", "Flooded Strand", "Polluted Delta", "Windswept Heath", "Wooded Foothills");

        List<Card> booster = FateReforged.getInstance().createBooster();
        assertTrue(str(booster), contains(booster, tapland, "FRF") || contains(booster, fetchland, "KTK")
                || contains(booster, basics, null));
        // assertFalse(str(booster), contains(booster, basics, null));
    }

    @Test
    public void testMastersEditionII() {
        List<String> snowCoveredLand = Arrays.asList(
                "Snow-Covered Plains",
                "Snow-Covered Island",
                "Snow-Covered Swamp",
                "Snow-Covered Mountain",
                "Snow-Covered Forest"
        );
        List<Card> booster = MastersEditionII.getInstance().createBooster();
        assertTrue(str(booster), contains(booster, snowCoveredLand, "ME2"));
        assertFalse(str(booster), contains(booster, basics, null));
    }

    @Test
    public void testMastersEditionIV() {
        List<String> urzaLand = Arrays.asList(
                "Urza's Mine",
                "Urza's Power Plant",
                "Urza's Tower"
        );
        List<Card> booster = MastersEditionIV.getInstance().createBooster();
        assertTrue(str(booster), contains(booster, urzaLand, "ME4"));
        assertFalse(str(booster), contains(booster, basics, null));
    }

    private static String str(List<Card> cards) {
        StringBuilder sb = new StringBuilder("[");
        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card next = iterator.next();
            sb.append(next.getName());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("] (").append(cards.size()).append(")");
        return sb.toString();
    }

    private static boolean contains(List<Card> cards, List<String> names, String code) {
        return names.stream().anyMatch((name) -> (contains(cards, name, code)));
    }

    private static boolean contains(List<Card> cards, String name, String code) {
        return cards.stream().anyMatch((card)
                -> (card.getName().equals(name)
                && (code == null || card.getExpansionSetCode().equals(code)))
        );
    }

}
