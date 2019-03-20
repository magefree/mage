package org.mage.test.mulligan;

import com.google.common.collect.Sets;
import mage.game.mulligan.MulliganType;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class LondonMulliganTest extends MulliganTestBase {

    @Test
    public void testLondonMulligan_NoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.LONDON, 0);
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
    public void testLondonMulligan_OneMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.LONDON, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        List<UUID> discarded = new ArrayList<>();
        Set<UUID> remainingHand = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            hand2.addAll(scenario.getHand());
            return false;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(hand2, scenario.getHand());
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(remainingHand, new HashSet<>(scenario.getHand()));
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
        });
    }

    @Test
    public void testLondonMulligan_TwoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.LONDON, 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> hand3 = new HashSet<>();
        List<UUID> discarded = new ArrayList<>();
        Set<UUID> remainingHand = new HashSet<>();
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
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(2, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(hand3, scenario.getHand());
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.run(() -> {
            scenario.assertSizes(5, 35);
            assertEquals(remainingHand, new HashSet<>(scenario.getHand()));
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(2));
        });
    }

    @Test
    public void testLondonMulligan_FreeMulligan_NoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.LONDON, 1);
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
    public void testLondonMulligan_FreeMulligan_OneMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.LONDON, 1);
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
    public void testLondonMulligan_FreeMulligan_TwoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.LONDON, 1);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> hand3 = new HashSet<>();
        List<UUID> discarded = new ArrayList<>();
        Set<UUID> remainingHand = new HashSet<>();
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
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(hand3, scenario.getHand());
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(remainingHand, new HashSet<>(scenario.getHand()));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
        });
    }

    @Test
    public void testLondonMulligan_AlwaysMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(MulliganType.LONDON, 0);
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            return true;
        });
        scenario.discardBottom(count -> {
            assertEquals(7, count);
            return new ArrayList<>(scenario.getHand());
        });
        scenario.run(() -> {
            scenario.assertSizes(0, 40);
        });
    }

}
