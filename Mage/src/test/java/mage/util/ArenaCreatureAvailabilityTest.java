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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ArenaCreatureAvailabilityTest {

    private static final Instant LAUNCH = Instant.parse("2030-06-01T00:00:00Z");
    private Instant now = LAUNCH.minusSeconds(1);

    private static ArenaCreaturePool catalog() throws IOException {
        return new ArenaCreaturePool(new StringReader("2\tKnown creature\n"));
    }

    @Test
    public void testNewStandardCreatureAppearsOnReleaseWithoutCatalogEditOrRestart() throws IOException {
        TestSet set = new TestSet("NEW", SetType.EXPANSION, LAUNCH);
        Info card = new Info("Future creature", "NEW");
        ArenaCreatureSelector selector = new ArenaCreatureSelector(
                Collections.singletonList(card), catalog(), code -> set, () -> now);
        assertTrue(selector.getCards().isEmpty());
        now = LAUNCH;
        assertEquals(Collections.singletonList(card), selector.getCards().get(2));
        now = LAUNCH.plusSeconds(86400);
        assertEquals(Collections.singletonList(card), selector.getCards().get(2));
        now = LAUNCH.minusSeconds(1);
        assertTrue(selector.getCards().isEmpty());
    }

    @Test
    public void testCatalogCreaturesRemainAvailableBeforeAReprintSetLaunches() throws IOException {
        TestSet set = new TestSet("NEW", SetType.EXPANSION, LAUNCH);
        Info known = new Info("Known creature", "NEW");
        Info future = new Info("New creature", "NEW");
        ArenaCreatureSelector selector = new ArenaCreatureSelector(
                Arrays.asList(known, future), catalog(), code -> set, () -> now);
        assertEquals(Collections.singletonList(known), selector.getCards().get(2));
        now = LAUNCH;
        assertEquals(Arrays.asList(known, future), selector.getCards().get(2));
    }

    @Test
    public void testDigitalCoreAndStandardSupplementDefaults() throws IOException {
        for (SetType type : new SetType[]{SetType.MAGIC_ARENA, SetType.CORE, SetType.SUPPLEMENTAL_STANDARD_LEGAL}) {
            TestSet set = new TestSet("NEW", type, LAUNCH);
            Info card = new Info("New creature", "NEW");
            assertTrue(index(set, LAUNCH.minusSeconds(1), card).isEmpty());
            assertEquals(Collections.singletonList(card), index(set, LAUNCH, card).get(2));
        }
    }

    @Test
    public void testPartialAndPreArenaSetsStillNeedCardLevelEvidence() throws IOException {
        for (SetType type : new SetType[]{SetType.SUPPLEMENTAL, SetType.SUPPLEMENTAL_MODERN_LEGAL,
                SetType.PROMOTIONAL, SetType.MAGIC_ONLINE, SetType.JOKE_SET, SetType.REMIX}) {
            TestSet set = new TestSet("PART", type, LAUNCH.minusSeconds(100));
            Info unknown = new Info("Paper only", "PART");
            Info known = new Info("Known creature", "PART");
            assertEquals(Collections.singletonList(known), index(set, LAUNCH, unknown, known).get(2));
        }
        TestSet legacy = new TestSet("OLD", SetType.EXPANSION, Instant.parse("2014-01-01T00:00:00Z"));
        assertTrue(index(legacy, LAUNCH, new Info("Old paper only", "OLD")).isEmpty());
    }

    @Test
    public void testStandardAutomaticCoverageStartsWithIxalan() throws IOException {
        TestSet before = new TestSet("OLD", SetType.EXPANSION, ExpansionSet.buildDate(2017, 9, 28).toInstant());
        TestSet ixalan = new TestSet("NEW", SetType.EXPANSION, ExpansionSet.buildDate(2017, 9, 29).toInstant());
        assertNull(before.getArenaReleaseDate());
        assertNotNull(ixalan.getArenaReleaseDate());
        assertTrue(index(before, LAUNCH, new Info("Legacy paper card", "OLD")).isEmpty());
        assertEquals(1, index(ixalan, LAUNCH, new Info("Arena-era Standard card", "NEW")).get(2).size());
    }

    @Test
    public void testEntireSetArenaOverrideUsesArenaDate() throws IOException {
        TestSet set = new TestSet("PORT", SetType.SUPPLEMENTAL_MODERN_LEGAL, LAUNCH.minusSeconds(86400));
        set.launchOnArena(LAUNCH);
        Info card = new Info("New ported creature", "PORT");
        assertTrue(index(set, LAUNCH.minusSeconds(1), card).isEmpty());
        assertEquals(Collections.singletonList(card), index(set, LAUNCH, card).get(2));
    }

    @Test
    public void testSetCanOptOutOfAutomaticAvailability() throws IOException {
        TestSet set = new TestSet("NONE", SetType.EXPANSION, LAUNCH) {
            @Override
            public Date getArenaReleaseDate() {
                return null;
            }
        };
        assertTrue(index(set, LAUNCH, new Info("Not on Arena", "NONE")).isEmpty());
    }

    @Test
    public void testAutomaticCandidatesExcludeBackFacesSplitHalvesAndRebalancedVersions() throws IOException {
        TestSet set = new TestSet("NEW", SetType.MAGIC_ARENA, LAUNCH);
        Info back = new Info("Back face", "NEW");
        back.night();
        Info half = new Info("Split half", "NEW");
        half.half();
        Info spell = new Info("Noncreature", "NEW");
        spell.setTypes(Collections.singletonList(CardType.SORCERY));
        Info creature = new Info("Front creature", "NEW");
        assertEquals(Collections.singletonList(creature), index(set, LAUNCH, back, half, spell,
                new Info("A-Front creature", "NEW"), creature).get(2));
    }

    @Test
    public void testCustomSetsDoNotBecomeArenaSets() throws IOException {
        TestSet custom = new TestSet("CUSTOM", SetType.CUSTOM_SET, LAUNCH);
        custom.launchOnArena(LAUNCH);
        assertTrue(index(custom, LAUNCH, new Info("Known creature", "CUSTOM")).isEmpty());
        TestSet tutorial = new TestSet("ATC", SetType.CUSTOM_SET, LAUNCH);
        assertEquals(1, index(tutorial, LAUNCH, new Info("Known creature", "ATC"),
                new Info("Unknown tutorial", "ATC")).get(2).size());
    }

    @Test
    public void testReleaseReprintsAndAliasesDoNotAddWeight() throws IOException {
        TestSet set = new TestSet("NEW", SetType.EXPANSION, LAUNCH);
        Info first = new Info("New creature", "NEW");
        assertEquals(1, index(set, LAUNCH, first,
                new Info("NEW CREATURE", "NEW"), new Info("New creature", "NEW")).get(2).size());
        Info wrongValue = new Info("Known creature", "NEW");
        wrongValue.manaValue(3);
        assertTrue(index(set, LAUNCH, wrongValue).isEmpty());
    }

    @Test
    public void testCreaturesBecomeAvailableAtEachSetRelease() throws IOException {
        TestSet firstSet = new TestSet("ONE", SetType.EXPANSION, LAUNCH);
        TestSet secondSet = new TestSet("TWO", SetType.MAGIC_ARENA, LAUNCH.plusSeconds(86400));
        Map<String, ExpansionSet> sets = new HashMap<>();
        sets.put("ONE", firstSet);
        sets.put("TWO", secondSet);
        ArenaCreatureSelector selector = new ArenaCreatureSelector(Arrays.asList(
                new Info("First creature", "ONE"), new Info("Second creature", "TWO")), catalog(), sets::get, () -> now);
        assertTrue(selector.getCards().isEmpty());
        now = LAUNCH;
        List<CardInfo> firstRelease = selector.getCards().get(2);
        assertEquals(1, firstRelease.size());
        assertEquals("First creature", firstRelease.get(0).getName());
        now = LAUNCH.plusSeconds(86400);
        assertEquals(2, selector.getCards().get(2).size());
        now = LAUNCH.minusSeconds(1);
        assertTrue(selector.getCards().isEmpty());
        now = LAUNCH.plusSeconds(172800);
        assertEquals(2, selector.getCards().get(2).size());
    }

    @Test
    public void testLaterReprintDoesNotDelayAvailabilityOrAddWeight() throws IOException {
        Map<String, ExpansionSet> sets = new HashMap<>();
        sets.put("EARLY", new TestSet("EARLY", SetType.EXPANSION, LAUNCH));
        sets.put("LATE", new TestSet("LATE", SetType.EXPANSION, LAUNCH.plusSeconds(86400)));
        Info early = new Info("Same creature", "EARLY");
        ArenaCreatureSelector selector = new ArenaCreatureSelector(Arrays.asList(
                new Info("Same creature", "LATE"), early), catalog(), sets::get, () -> now);
        assertTrue(selector.getCards().isEmpty());
        now = LAUNCH;
        assertEquals(1, selector.getCards().get(2).size());
        assertEquals("Same creature", selector.getCards().get(2).get(0).getName());
        now = LAUNCH.plusSeconds(86400);
        assertEquals(1, selector.getCards().get(2).size());
        assertEquals("Same creature", selector.getCards().get(2).get(0).getName());
    }

    private static Map<Integer, List<CardInfo>> index(ExpansionSet set, Instant now, CardInfo... cards) throws IOException {
        return new ArenaCreatureSelector(Arrays.asList(cards), catalog(), code -> set,
                () -> now).getCards();
    }

    private static class TestSet extends ExpansionSet {

        TestSet(String code, SetType type, Instant release) {
            super(code, code, Date.from(release), type);
        }

        void launchOnArena(Instant release) {
            arenaReleaseDate = Date.from(release);
        }
    }

    private static class Info extends CardInfo {

        Info(String name, String setCode) {
            this.name = name;
            this.setCode = setCode;
            this.manaValue = 2;
            setTypes(Collections.singletonList(CardType.CREATURE));
        }

        void night() {
            nightCard = true;
        }

        void half() {
            splitCardHalf = true;
        }

        void manaValue(int value) {
            manaValue = value;
        }
    }
}
