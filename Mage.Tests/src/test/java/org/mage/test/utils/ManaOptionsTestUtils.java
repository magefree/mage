package org.mage.test.utils;

import mage.Mana;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.ManaOptions;
import mage.util.ManaUtil;
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
    public static boolean manaOptionsContain(String searchMana, ManaOptions manaOptions) {
        for (Mana mana : manaOptions) {
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

    public static void assertManaOptions(String searchMana, ManaOptions manaOptions) {
        if (!manaOptionsContain(searchMana, manaOptions)) {
            Assert.fail("Can't find " + searchMana + " in " + manaOptions.toString());
        }
    }
}
