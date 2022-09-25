package org.mage.test.utils;

import mage.Mana;
import mage.abilities.mana.ManaValueComparator;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.*;

/**
 * Tests to ensure that the ManaValueComparator works correctly.
 *
 * @author Alex-Vasile
 */
public class ManaValueComparatorTest {

    private static final ManaValueComparator comperator = new ManaValueComparator();

    /**
     *
     */
    @Test
    public void individualComparisons() {

        final List<Mana> listOfAllManas = Arrays.asList(
                Mana.ColorlessMana(1),
                Mana.AnyMana(1),
                Mana.WhiteMana(1),
                Mana.BlueMana(1),
                Mana.BlackMana(1),
                Mana.RedMana(1),
                Mana.GreenMana(1),
                Mana.GenericMana(1));

        // 1. Each mana compared with itself should return 0 (i.e. equivalent)
        for (Mana mana : listOfAllManas) {
            Assert.assertEquals(0, comperator.compare(mana, mana));
        }

        // 2. Mana should sort itself into {C}{ANY}{W}{U}{B}{R}{G}{1} order
        //    listOfAllManas is already in the list, so comparing the current nad next value at any point
        //    throughout the list should always return 1 (or the current value is passed 1st)
        //    or -1 (if the second next value is passed 1st).

        // The -1 is on purpose since we don't want to index past the end of the list when grabbing the next value
        for (int i = 0; i <listOfAllManas.size()-1 ; i++) {
            Assert.assertEquals(
                    "" + listOfAllManas.get(i) + "," + listOfAllManas.get(i+1),
                    -1,
                    comperator.compare(listOfAllManas.get(i), listOfAllManas.get(i+1)));
            Assert.assertEquals(
                    "" + listOfAllManas.get(i+1) + "," + listOfAllManas.get(i),
                    1,
                    comperator.compare(listOfAllManas.get(i+1), listOfAllManas.get(i)));
        }
    }

    @Test
    public void sorting() {
    final List<Mana> listOfAllManas = Arrays.asList(
            Mana.ColorlessMana(1),
            Mana.AnyMana(1),
            Mana.WhiteMana(1),
            Mana.BlueMana(1),
            Mana.BlackMana(1),
            Mana.RedMana(1),
            Mana.GreenMana(1),
            Mana.GenericMana(1));

        // 1. For an equal amount of mana, the expected order is [{C}, {ANY}, {W}, {U}, {B}, {R}, {G}, {1}]
        List<Mana> listToSort1 = new ArrayList<>(listOfAllManas);
        Collections.shuffle(listToSort1);
        listToSort1.sort(comperator);

        for (int i = 0; i < listOfAllManas.size(); i++) {
            Assert.assertEquals(
                    "" + listOfAllManas.get(i) + "," + listToSort1.get(i),
                    listOfAllManas.get(i),
                    listToSort1.get(i));
        }

        // 2. For the same mana type, the expect order is from most number to fewest number, e.g. [{3}, {2}, {1}]
        // Length of the list to check, should work for any size
        int n = 10;
        for (Mana mana : listOfAllManas) {
            List<Mana> listToSort2 = new ArrayList<>(n);
            for (int i = 1; i <= n; i++) {
                Mana manaI = new Mana();
                for (int j = 0; j < i; j++) {
                    manaI.add(mana);
                }
                listToSort2.add(manaI);
            }
            Collections.shuffle(listToSort2);
            listToSort2.sort(comperator);

            for (int i = 0; i < n; i++) {
                Assert.assertEquals(10 - (i), listToSort2.get(i).count());
            }
        }

        // 3.
        final List<Mana> listInitial3 = Arrays.asList(
                Mana.ColorlessMana(3),
                Mana.ColorlessMana(2),
                Mana.ColorlessMana(1),
                Mana.AnyMana(3),
                Mana.AnyMana(2),
                Mana.AnyMana(1));

        List<Mana> listToSort3 = new ArrayList<>(listInitial3);
        Collections.shuffle(listToSort3);
        listToSort3.sort(comperator);

        for (int i = 0; i < listToSort3.size(); i++) {
            Assert.assertEquals(
                    "" + listInitial3.get(i) + "," + listToSort3.get(i),
                    listInitial3.get(i),
                    listToSort3.get(i));
        }
    }
}
