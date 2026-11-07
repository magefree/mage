package org.mage.plugins.card.images;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.constants.SetType;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

public class ImageDownloadSetsTest {

    @Test
    public void everyAuditedExpansionMatchesTheSourcePrintingInventory() throws Exception {
        Set<String> historic = new HashSet<>(ImageDownloadSets.historic());
        Set<String> vintage = new HashSet<>(ImageDownloadSets.vintage());
        Set<String> checked = new HashSet<>();
        Properties audited = new Properties();
        try (InputStream input = getClass().getResourceAsStream("download-format-sets.properties")) {
            assertNotNull("Missing runtime set inventory", input);
            audited.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        }
        InputStream input = getClass().getResourceAsStream("download-format-coverage.csv");
        assertNotNull("Missing Scryfall printing-count fixture", input);
        try (BufferedReader rows = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String row;
            while ((row = rows.readLine()) != null) {
                if (row.startsWith("#") || row.isEmpty()) {
                    continue;
                }
                String[] values = row.split(",", -1);
                assertEquals("Malformed printing-count row: " + row, 4, values.length);
                String code = values[0];
                assertTrue("Duplicate audited set: " + code, checked.add(code));
                assertTrue("Audited set must exist: " + code, Sets.getInstance().containsKey(code));
                assertEquals(code + " expansion type", Sets.getInstance().get(code).getSetType().name(), values[1]);
                int arenaPrintings = Integer.parseInt(values[2]);
                int vintagePrintings = Integer.parseInt(values[3]);
                assertTrue(code + " negative printing count", arenaPrintings >= 0 && vintagePrintings >= 0);
                assertEquals(code + " Arena printing coverage", arenaPrintings > 0, historic.contains(code));
                assertEquals(code + " Vintage printing coverage", vintagePrintings > 0, vintage.contains(code));
                String expected = (arenaPrintings > 0 ? "H" : "") + (vintagePrintings > 0 ? "V" : "");
                assertEquals(code + " audited membership", expected, audited.getProperty(code));
            }
        }
        assertFalse("Empty printing-count fixture", checked.isEmpty());
        assertEquals("Every audited entry must have source printing counts", audited.stringPropertyNames(), checked);
    }

    @Test
    public void historicCoversPublishedArenaReleasesAndPartialIntroductions() {
        // Wizards: mtg-arena-formats; announcing/introducing Timeless; MH3 and J25 announcements.
        assertIncluded(ImageDownloadSets.historic(), "XLN", "KTK", "JMP", "J21", "STA", "LTR", "MH3",
                "J25", "BRR", "MUL", "WOT", "OTP", "BIG", "SPG", "FCA", "EOS", "SOA",
                "M3C", "FIC", "ANA", "ANB", "YDMU");
        assertExcluded(ImageDownloadSets.historic(), "LEA", "SWS", "ATC", "CMB1");
    }

    @Test
    public void vintageCoversEternalReleasesButNotArenaOnlyOrPurePlaytestSets() {
        // Wizards: formats/vintage; Unfinity and Mystery Booster 2 release notes.
        assertIncluded(ImageDownloadSets.vintage(), "LEA", "VMA", "JMP", "LTR", "MH3", "M3C", "FIC", "UNF", "MB2");
        assertExcluded(ImageDownloadSets.vintage(), "ANA", "ANB", "J21", "YDMU", "SWS", "ATC", "CMB1");
    }

    @Test
    public void mixedSetsRemainWholeImageGroupsIncludingTheirNonlegalCards() {
        // UNH/UST contain tournament-legal basic lands; MB2 also has regular reprints.
        assertIncluded(ImageDownloadSets.vintage(), "UNH", "UST", "UNF", "MB2");
        // M3C has a curated Arena introduction, not a release of the whole Commander product.
        assertIncluded(ImageDownloadSets.historic(), "M3C");
    }

    @Test
    public void newlyAddedSetsKeepTheExistingTypeDefaultsUntilAudited() {
        ExpansionSet regular = new ExpansionSet("New regular release", "TEST-NEW", ExpansionSet.buildDate(2027, 1, 1), SetType.EXPANSION) { };
        ExpansionSet arena = new ExpansionSet("New Arena release", "TEST-ARENA", ExpansionSet.buildDate(2027, 1, 1), SetType.MAGIC_ARENA) { };
        ExpansionSet supplemental = new ExpansionSet("New supplemental release", "TEST-SUPP", ExpansionSet.buildDate(2027, 1, 1), SetType.SUPPLEMENTAL) { };
        assertTrue(ImageDownloadSets.isHistoric(regular));
        assertTrue(ImageDownloadSets.isVintage(regular));
        assertTrue(ImageDownloadSets.isHistoric(arena));
        assertFalse(ImageDownloadSets.isVintage(arena));
        assertFalse(ImageDownloadSets.isHistoric(supplemental));
        assertTrue(ImageDownloadSets.isVintage(supplemental));
    }

    private static void assertIncluded(java.util.List<String> actual, String... codes) {
        for (String code : codes) assertTrue("Missing " + code, actual.contains(code));
    }

    private static void assertExcluded(java.util.List<String> actual, String... codes) {
        Set<String> unexpected = new HashSet<>(Arrays.asList(codes));
        unexpected.retainAll(actual);
        assertTrue("Unexpected sets: " + unexpected, unexpected.isEmpty());
    }
}
