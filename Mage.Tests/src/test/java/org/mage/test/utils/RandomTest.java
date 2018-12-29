package org.mage.test.utils;

import mage.cards.decks.DeckCardLists;
import mage.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class RandomTest {

    @Test
    public void test_SeedAndSameResults() {
        RandomUtil.setSeed(123);
        List<Integer> listSameA = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            listSameA.add(RandomUtil.nextInt());
        }

        RandomUtil.setSeed(321);
        List<Integer> listDifferent = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            listDifferent.add(RandomUtil.nextInt());
        }

        RandomUtil.setSeed(123);
        List<Integer> listSameB = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            listSameB.add(RandomUtil.nextInt());
        }

        Assert.assertEquals("same seed must have same random values", listSameA.stream().mapToInt(Integer::intValue).sum(), listSameB.stream().mapToInt(Integer::intValue).sum());
        Assert.assertNotEquals("different seed must have different random values", listSameA.stream().mapToInt(Integer::intValue).sum(), listDifferent.stream().mapToInt(Integer::intValue).sum());
    }

    @Test
    public void test_SeedAndSameRandomDecks() {
        RandomUtil.setSeed(123);
        DeckCardLists listSameA = DeckTestUtils.buildRandomDeckAndInitCards("WGUBR", false, "GRN");
        String infoSameA = listSameA.getCards().stream().map(c -> (c.getSetCode() + "-" + c.getCardName())).collect(Collectors.joining(","));

        RandomUtil.setSeed(321);
        DeckCardLists listDifferent = DeckTestUtils.buildRandomDeckAndInitCards("WGUBR", false, "GRN");
        String infoDifferent = listDifferent.getCards().stream().map(c -> (c.getSetCode() + "-" + c.getCardName())).collect(Collectors.joining(","));

        RandomUtil.setSeed(123);
        DeckCardLists listSameB = DeckTestUtils.buildRandomDeckAndInitCards("WGUBR", false, "GRN");
        String infoSameB = listSameB.getCards().stream().map(c -> (c.getSetCode() + "-" + c.getCardName())).collect(Collectors.joining(","));

        Assert.assertEquals("same seed must have same deck", infoSameA, infoSameB);
        Assert.assertNotEquals("different seed must have different deck", infoSameA, infoDifferent);
    }


}
