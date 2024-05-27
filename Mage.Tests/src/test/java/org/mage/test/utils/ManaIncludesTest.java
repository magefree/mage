package org.mage.test.utils;

import mage.Mana;
import mage.abilities.costs.mana.ManaCostsImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ManaIncludesTest extends CardTestPlayerBase {

    private void assertFullyIncludes(boolean canPay, String cost, String pool) {
        // workaround to add {Any} mana by string param
        String strictPool = pool.replace("{Any}", "");
        String stringCost = cost.replace("{Any}", "");

        Mana manaPool = new ManaCostsImpl<>(strictPool).getMana();
        Mana manaCost = new ManaCostsImpl<>(stringCost).getMana();

        manaPool.add(new Mana(0, 0, 0, 0, 0, 0, (pool.length() - strictPool.length()) / 5, 0));
        manaCost.add(new Mana(0, 0, 0, 0, 0, 0, (cost.length() - stringCost.length()) / 5, 0));

        Assert.assertEquals(canPay, manaPool.includesMana(manaCost));
    }

    @Test
    public void test_ManaIncludes() {
        assertFullyIncludes(true, "", "");
        assertFullyIncludes(true, "", "{R}");
        assertFullyIncludes(true, "", "{C}");
        assertFullyIncludes(true, "", "{1}");
        assertFullyIncludes(true, "", "{Any}");
        assertFullyIncludes(true, "", "{R}{G}");
        assertFullyIncludes(true, "", "{C}{G}");
        assertFullyIncludes(true, "", "{1}{G}");
        assertFullyIncludes(true, "", "{Any}{G}");

        assertFullyIncludes(false, "{B}", "");
        assertFullyIncludes(false, "{B}", "{R}");
        assertFullyIncludes(false, "{B}", "{C}");
        assertFullyIncludes(false, "{B}", "{1}");
        assertFullyIncludes(true, "{B}", "{Any}");
        assertFullyIncludes(false, "{B}", "{R}{G}");
        assertFullyIncludes(false, "{B}", "{C}{G}");
        assertFullyIncludes(false, "{B}", "{1}{G}");
        assertFullyIncludes(true, "{B}", "{Any}{G}");

        assertFullyIncludes(false, "{G}", "");
        assertFullyIncludes(false, "{G}", "{R}");
        assertFullyIncludes(false, "{G}", "{C}");
        assertFullyIncludes(false, "{G}", "{1}");
        assertFullyIncludes(true, "{G}", "{Any}");
        assertFullyIncludes(true, "{G}", "{R}{G}");
        assertFullyIncludes(true, "{G}", "{C}{G}");
        assertFullyIncludes(true, "{G}", "{1}{G}");
        assertFullyIncludes(true, "{G}", "{Any}{G}");

        assertFullyIncludes(false, "{C}", "");
        assertFullyIncludes(false, "{C}", "{R}");
        assertFullyIncludes(true, "{C}", "{C}");
        assertFullyIncludes(false, "{C}", "{1}");
        assertFullyIncludes(false, "{C}", "{Any}");
        assertFullyIncludes(false, "{C}", "{R}{G}");
        assertFullyIncludes(true, "{C}", "{C}{G}");
        assertFullyIncludes(false, "{C}", "{1}{G}");
        assertFullyIncludes(false, "{C}", "{Any}{G}");

        assertFullyIncludes(false, "{1}", "");
        assertFullyIncludes(true, "{1}", "{R}");
        assertFullyIncludes(true, "{1}", "{C}");
        assertFullyIncludes(true, "{1}", "{1}");
        assertFullyIncludes(true, "{1}", "{Any}");
        assertFullyIncludes(true, "{1}", "{R}{G}");
        assertFullyIncludes(true, "{1}", "{C}{G}");
        assertFullyIncludes(true, "{1}", "{1}{G}");
        assertFullyIncludes(true, "{1}", "{Any}{G}");

        assertFullyIncludes(false, "{Any}", "");
        assertFullyIncludes(false, "{Any}", "{R}");
        assertFullyIncludes(false, "{Any}", "{C}");
        assertFullyIncludes(false, "{Any}", "{1}");
        assertFullyIncludes(true, "{Any}", "{Any}");
        assertFullyIncludes(false, "{Any}", "{R}{G}");
        assertFullyIncludes(false, "{Any}", "{C}{G}");
        assertFullyIncludes(false, "{Any}", "{1}{G}");
        assertFullyIncludes(true, "{Any}", "{Any}{G}");

        // possible integer overflow problems
        String maxGeneric = "{" + Integer.MAX_VALUE + "}";
        assertFullyIncludes(false, maxGeneric, "");
        assertFullyIncludes(false, maxGeneric, "{1}");
        assertFullyIncludes(false, maxGeneric, "{R}");
        assertFullyIncludes(true, "", maxGeneric);
        assertFullyIncludes(true, "{1}", maxGeneric);
        assertFullyIncludes(false, "{R}", maxGeneric);
        assertFullyIncludes(true, maxGeneric, maxGeneric);

        // data from infinite mana calcs bug
        assertFullyIncludes(false, "{R}{R}", "{R}");
        assertFullyIncludes(true, "{R}{R}", "{R}{R}");
        assertFullyIncludes(true, "{R}{R}", "{R}{R}{R}");
        assertFullyIncludes(true, "{R}{R}", "{R}{R}{R}{R}");
        assertFullyIncludes(true, "{R}{R}", "{R}{R}{R}{R}{R}");
        assertFullyIncludes(false, "{Any}{Any}", "{Any}");
        assertFullyIncludes(true, "{Any}{Any}", "{Any}{Any}");
        assertFullyIncludes(true, "{Any}{Any}", "{Any}{Any}{Any}");
        assertFullyIncludes(true, "{Any}{Any}", "{Any}{Any}{Any}{Any}");
        assertFullyIncludes(true, "{Any}{Any}", "{Any}{Any}{Any}{Any}{Any}");

        // additional use cases
        assertFullyIncludes(false, "{W}{W}", "{W}{B}");
    }
}
