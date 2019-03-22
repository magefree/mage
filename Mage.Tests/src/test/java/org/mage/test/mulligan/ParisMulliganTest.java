package org.mage.test.mulligan;

import mage.game.mulligan.MulliganType;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ParisMulliganTest extends MulliganTestBase {

    @Test
    public void testParisMulligan_NoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.PARIS, 0);
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
    public void testParisMulligan_OneMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.PARIS, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            hand2.addAll(scenario.getHand());
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getHand()));
        });
    }

    @Test
    public void testParisMulligan_OneMulligan_Scry() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.PARIS, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            hand2.addAll(scenario.getHand());
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getHand()));
        });
    }

    @Test
    public void testParisMulligan_FreeMulligan_NoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.PARIS, 1);
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
    public void testParisMulligan_FreeMulligan_OneMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.PARIS, 1);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand2.addAll(scenario.getHand());
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getHand()));
        });
    }

    @Test
    public void testParisMulligan_FreeMulligan_TwoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.PARIS, 1);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> hand3 = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            hand2.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(20, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(20, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(27, 7)));
            assertEquals(hand3, new HashSet<>(scenario.getHand()));
        });
    }

    @Test
    public void testParisMulligan_AlwaysMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.PARIS, 0);
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
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
            scenario.assertSizes(4, 36);
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
            scenario.assertSizes(1, 39);
            return true;
        });
        scenario.run(() -> {
            scenario.assertSizes(0, 40);
        });
    }

}
