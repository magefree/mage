package org.mage.test.utils;

import mage.Mana;
import mage.abilities.mana.ManaOptions;
import org.junit.Assert;

import java.util.HashSet;
import java.util.Set;
import mage.ConditionalMana;

public class ManaOptionsTestUtils {

    public static String bear1W = "Silvercoat Lion"; // {1}{W}
    public static String bearG = "Basking Rootwalla"; // {G}
    public static String bear1 = "Augmenting Automaton"; // {1}
    public static String bear1G = "Balduvian Bears"; // {1}{G}
    public static String bear2C = "Matter Reshaper"; // {2}{C}

    //mana info
    //logger.info(playerA.getManaPool().getMana().toString());
    //logger.info(playerA.getManaAvailable(currentGame).toString());
    public static boolean manaOptionsContain(ManaOptions list, String searchMana) {
        for (Mana mana : list) {
            if (mana instanceof ConditionalMana) {
                if ((mana.toString() + ((ConditionalMana)mana).getConditionString()).equals(searchMana)) {
                    return true;
                }
            } else {
                if (mana.toString().equals(searchMana)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void assertManaOptions(String searchMana, ManaOptions manaList) {
        if (!manaOptionsContain(manaList, searchMana)) {
            Assert.fail("Can't find " + searchMana + " in " + manaList.toString());
        }
    }

    public static void assertDuplicatedManaOptions(ManaOptions manaList) {
        Set<String> list = new HashSet<>();
        for (Mana mana : manaList) {
            String s = mana.toString();
            if (list.contains(s)) {
                Assert.fail("Found duplicated mana option " + s + " in " + manaList.toString());
            } else {
                list.add(s);
            }
        }
    }
}
