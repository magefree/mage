package org.mage.test.mulligan;

import com.google.common.collect.Sets;
import mage.game.mulligan.MulliganType;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(remainingHand, scenario.getHand());
            hand2.addAll(scenario.getHand());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(remainingHand, new HashSet<>(scenario.getHand()));
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(hand2, scenario.getHand());
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
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            hand2.addAll(scenario.getHand());
            return true;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(discarded, scenario.getLibraryRangeSize(26, 1));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(27, 6)));
            discarded.clear();
            remainingHand.clear();
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(27, 6)));
            discarded.clear();
            remainingHand.clear();
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(5, 35);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(27, 6)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(5, 35);
            assertEquals(remainingHand, new HashSet<>(scenario.getHand()));
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(27, 6)));
            assertEquals(hand3, scenario.getHand());
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
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
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(19, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(26, 7)));
            assertEquals(hand3, scenario.getHand());
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
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(6, 34);
            return true;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(5, 35);
            return true;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(5, 35);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(4, 36);
            return true;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(5, 35);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(4, 36);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(3, 37);
            return true;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(5, 35);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(4, 36);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(3, 37);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(2, 38);
            return true;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(5, 35);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(4, 36);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(3, 37);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(2, 38);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(1, 39);
            return true;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(5, 35);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(4, 36);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(3, 37);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(2, 38);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(1, 39);
            assertEquals(1, count);
            return scenario.getHand().stream().limit(count).collect(Collectors.toList());
        });
        scenario.run(() -> {
            scenario.assertSizes(0, 40);
        });
    }
}
