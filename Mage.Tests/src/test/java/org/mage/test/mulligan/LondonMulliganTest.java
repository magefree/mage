package org.mage.test.mulligan;

import com.google.common.collect.Sets;
import mage.game.mulligan.MulliganType;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class LondonMulliganTest extends MulliganTestBase {
    protected MulliganType getMullType() {
        return MulliganType.LONDON;
    }
    protected int getCardsPerMull() {
        return 7;
    }
    @Test
    public void testLondonMulligan_NoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(getMullType(), 0);
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
        MulliganScenarioTest scenario = new MulliganScenarioTest(getMullType(), 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        List<UUID> discarded = new ArrayList<>();
        List<UUID> bottomed = new ArrayList<>();
        Set<UUID> remainingHand = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            hand2.addAll(scenario.getHand());
            return false;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            assertEquals(hand2, scenario.getHand());
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            bottomed.addAll(discarded);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(remainingHand, scenario.getHand());
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            assertEquals(hand2, Sets.union(remainingHand, new HashSet<>(discarded)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
            assertEquals(bottomed, scenario.getNBottomOfLibrary(1));
        });
    }

    @Test
    public void testLondonMulligan_TwoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(getMullType(), 0);
        Set<UUID> hand1 = new HashSet<>();
        Set<UUID> hand2 = new HashSet<>();
        Set<UUID> hand3 = new HashSet<>();
        List<UUID> discarded = new ArrayList<>();
        List<UUID> bottomed = new ArrayList<>();
        Set<UUID> remainingHand = new HashSet<>();
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            hand1.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            hand2.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull()*2, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull()*2, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            bottomed.addAll(discarded);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull()*2, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
            discarded.clear();
            remainingHand.clear();
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            bottomed.addAll(discarded);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.run(() -> {
            scenario.assertSizes(5, 35);
            assertEquals(remainingHand, scenario.getHand());
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull()*2, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            assertEquals(hand3, Sets.union(remainingHand, new HashSet<>(bottomed)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
            assertEquals(bottomed, scenario.getNBottomOfLibrary(2));
        });
    }

    @Test
    public void testLondonMulligan_FreeMulligan_NoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(getMullType(), 1);
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
        MulliganScenarioTest scenario = new MulliganScenarioTest(getMullType(), 1);
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
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            return false;
        });
        scenario.run(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            assertEquals(hand2, scenario.getHand());
        });
    }

    @Test
    public void testLondonMulligan_FreeMulligan_TwoMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(getMullType(), 1);
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
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            hand2.addAll(scenario.getHand());
            return true;
        });
        scenario.mulligan(() -> {
            scenario.assertSizes(7, 33);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull()*2, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            hand3.addAll(scenario.getHand());
            return false;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull()*2, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            scenario.getHand().stream().limit(count).forEach(discarded::add);
            remainingHand.addAll(Sets.difference(scenario.getHand(), new HashSet<>(discarded)));
            return discarded;
        });
        scenario.run(() -> {
            scenario.assertSizes(6, 34);
            assertEquals(remainingHand, scenario.getHand());
            assertEquals(hand1, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull()*2, 7)));
            assertEquals(hand2, new HashSet<>(scenario.getLibraryRangeSize(33-getCardsPerMull(), 7)));
            assertEquals(hand3, Sets.union(remainingHand, new HashSet<>(discarded)));
            assertEquals(discarded, scenario.getNBottomOfLibrary(1));
        });
    }

    @Test
    public void testLondonMulligan_AlwaysMulligan() {
        MulliganScenarioTest scenario = new MulliganScenarioTest(getMullType(), 0);
        List<UUID> bottomed = new ArrayList<>();
        List<UUID> handAtKeep = new ArrayList<>();
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
            scenario.assertSizes(7, 33);
            assertEquals(1, count);
            if (bottomed.isEmpty()) {
                handAtKeep.addAll(scenario.getHand());
            }
            List<UUID> chosen = scenario.getHand().stream().limit(count).collect(Collectors.toList());
            bottomed.addAll(chosen);
            return chosen;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(6, 34);
            assertEquals(1, count);
            List<UUID> chosen = scenario.getHand().stream().limit(count).collect(Collectors.toList());
            bottomed.addAll(chosen);
            return chosen;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(5, 35);
            assertEquals(1, count);
            List<UUID> chosen = scenario.getHand().stream().limit(count).collect(Collectors.toList());
            bottomed.addAll(chosen);
            return chosen;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(4, 36);
            assertEquals(1, count);
            List<UUID> chosen = scenario.getHand().stream().limit(count).collect(Collectors.toList());
            bottomed.addAll(chosen);
            return chosen;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(3, 37);
            assertEquals(1, count);
            List<UUID> chosen = scenario.getHand().stream().limit(count).collect(Collectors.toList());
            bottomed.addAll(chosen);
            return chosen;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(2, 38);
            assertEquals(1, count);
            List<UUID> chosen = scenario.getHand().stream().limit(count).collect(Collectors.toList());
            bottomed.addAll(chosen);
            return chosen;
        });
        scenario.discardBottom(count -> {
            scenario.assertSizes(1, 39);
            assertEquals(1, count);
            List<UUID> chosen = scenario.getHand().stream().limit(count).collect(Collectors.toList());
            bottomed.addAll(chosen);
            return chosen;
        });
        scenario.run(() -> {
            scenario.assertSizes(0, 40);
            assertEquals(bottomed, scenario.getNBottomOfLibrary(7));
            assertEquals(new HashSet<>(handAtKeep), new HashSet<>(bottomed));
        });
    }
}
