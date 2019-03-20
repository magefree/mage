package org.mage.test.mulligan;

import mage.game.mulligan.MulliganType;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CanadianHighlanderMulliganTest extends MulliganTestBase {

    @Test
    public void testCanadianHighlanderMulligan_NoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.CANADIAN_HIGHLANDER, 0);
        Set<UUID> hand1 = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, scenario.getHand());
        });
    }

    @Test
    public void testCanadianHighlanderMulligan_OneMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.CANADIAN_HIGHLANDER, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> scry = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            hand2.addAll(scenario.getHand());
            return false;
        });
        scenario.scry(() -> {
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getHand()));
            scry.add(scenario.getLibraryTopCard());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getHand()));
            assertEquals(scry, new HashSet<>(scenario.getNTopOfLibrary(1)));
        });
    }

    @Test
    public void testCanadianHighlanderMulligan_OneMulligan_Scry() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.CANADIAN_HIGHLANDER, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> scry = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            hand2.addAll(scenario.getHand());
            return false;
        });
        scenario.scry(() -> {
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getHand()));
            scry.add(scenario.getLibraryTopCard());
            return true;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getHand()));
            assertEquals(scry, new HashSet<>(scenario.getNBottomOfLibrary(1)));
        });
    }

    @Test
    public void testCanadianHighlanderMulligan_TwoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.CANADIAN_HIGHLANDER, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> hand3 = new HashSet<>();
        Set<UUID> scry = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            hand2.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(21, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(28, 6)));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.scry(() -> {
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(21, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(28, 6)));
            assertEquals(hand3, new HashSet<>(scenario.getHand()));
            scry.add(scenario.getLibraryTopCard());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(21, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(28, 6)));
            assertEquals(hand3, new HashSet<>(scenario.getHand()));
            assertEquals(scry, new HashSet<>(scenario.getNTopOfLibrary(1)));
        });
    }

    @Test
    public void testCanadianHighlanderMulligan_ThreeMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.CANADIAN_HIGHLANDER, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> hand3 = new HashSet<>();
        Set<UUID> hand4 = new HashSet<>();
        Set<UUID> scry = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            hand2.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(21, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(28, 6)));
            hand3.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(5, 35);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(16, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(23, 6)));
            assertEquals(hand3, new HashSet<>(scenario.getLibraryRangeSize(29, 6)));
            hand4.addAll(scenario.getHand());
            return false;
        });
        scenario.scry(() -> {
            scenario.assertSizes(5, 35);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(16, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(23, 6)));
            assertEquals(hand3, new HashSet<>(scenario.getLibraryRangeSize(29, 6)));
            assertEquals(hand4, scenario.getHand());
            scry.add(scenario.getLibraryTopCard());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(5, 35);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(16, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(23, 6)));
            assertEquals(hand3, new HashSet<>(scenario.getLibraryRangeSize(29, 6)));
            assertEquals(hand4, scenario.getHand());
            assertEquals(scry, new HashSet<>(scenario.getNTopOfLibrary(1)));
        });
    }

    @Test
    public void testCanadianHighlanderMulligan_AlwaysMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.CANADIAN_HIGHLANDER, 0);
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(5, 35);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(5, 35);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(4, 36);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(4, 36);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(3, 37);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(3, 37);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(2, 38);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(2, 38);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(1, 39);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(1, 39);
            return true;
        });
        scenario.scry(() -> {
            scenario.assertSizes(0, 40);
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(0, 40);
        });
    }

}
