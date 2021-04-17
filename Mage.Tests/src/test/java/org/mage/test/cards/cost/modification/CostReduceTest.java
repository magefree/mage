package org.mage.test.cards.cost.modification;

import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CostReduceTest extends CardTestPlayerBase {

    private void testReduce(String sourceCost, int reduceAmount, String needReducedCost) {
        // load mana by sctrict mode (e.g. for real mana usage in param)
        ManaCosts<ManaCost> source = new ManaCostsImpl<>();
        source.load(sourceCost, true);
        ManaCosts<ManaCost> need = new ManaCostsImpl<>();
        need.load(needReducedCost, true);
        ManaCosts<ManaCost> reduced = CardUtil.reduceCost(source, reduceAmount);

        if (!reduced.getText().equals(need.getText())) {
            Assert.fail(sourceCost + " after reduction by " + reduceAmount + " must be " + need.getText() + ", but get " + reduced.getText());
        }
    }

    @Test
    public void test_Monohybrid() {
        // extra test to ensure about mono hybrid test code
        ManaCosts<ManaCost> testCost = new ManaCostsImpl<>();
        testCost.load("{1/R}");
        Assert.assertEquals("normal mono hybrid always 2 generics", "{2/R}", testCost.getText());
        testCost = new ManaCostsImpl<>();
        testCost.load("{1/R}", true);
        Assert.assertEquals("test mono hybrid have variant generic", "{1/R}", testCost.getText());
        testReduce("{5/R}", 0, "{5/R}"); // ensure that mono hybrid in test mode

        // mana order must be same (e.g. cost {R}{2}{G} must be reduced to {R}{1}{G})

        // DECREASE COST

        // colorless is not reduce
        testReduce("{C}", 1, "{C}");
        testReduce("{C}{G}", 1, "{C}{G}");

        // 0 generic, decrease cost by 1
        testReduce("", 1, "");
        testReduce("{R}", 1, "{R}");
        testReduce("{R}{G}", 1, "{R}{G}");

        // 1 generic, decrease cost by 1
        testReduce("{1}", 1, "");
        testReduce("{R}{1}", 1, "{R}");
        testReduce("{1}{R}", 1, "{R}");
        testReduce("{1}{R}{G}", 1, "{R}{G}");
        testReduce("{R}{1}{G}", 1, "{R}{G}");
        testReduce("{R}{G}{1}", 1, "{R}{G}");

        // 2 generics, decrease cost by 1
        testReduce("{2}", 1, "{1}");
        testReduce("{R}{2}", 1, "{R}{1}");
        testReduce("{2}{R}", 1, "{1}{R}");
        testReduce("{2}{R}{G}", 1, "{1}{R}{G}");
        testReduce("{R}{2}{G}", 1, "{R}{1}{G}");
        testReduce("{R}{G}{2}", 1, "{R}{G}{1}");

        // 3 generics, decrease cost by 2
        testReduce("{2}", 2, "");
        testReduce("{3}", 2, "{1}");
        testReduce("{R}{3}", 2, "{R}{1}");
        testReduce("{3}{R}", 2, "{1}{R}");
        testReduce("{3}{R}{G}", 2, "{1}{R}{G}");
        testReduce("{R}{3}{G}", 2, "{R}{1}{G}");
        testReduce("{R}{G}{3}", 2, "{R}{G}{1}");

        // multi generics, decrease cost by 2 (you can't get multigeneric in real game example)
        testReduce("{2}{2}", 2, "{2}");
        testReduce("{3}{3}", 2, "{1}{3}");
        testReduce("{3}{R}{3}", 2, "{1}{R}{3}");
        testReduce("{3}{R}{3}{G}", 2, "{1}{R}{3}{G}");
        testReduce("{R}{3}{G}{3}", 2, "{R}{1}{G}{3}");
        //
        testReduce("{2}{2}", 3, "{1}");
        testReduce("{3}{3}", 3, "{3}");
        testReduce("{3}{R}{3}", 5, "{R}{1}");
        testReduce("{3}{R}{3}{G}", 5, "{R}{1}{G}");
        testReduce("{R}{3}{G}{3}", 5, "{R}{G}{1}");

        // INCREASE COST

        // colorless, increase cost by 1
        testReduce("{C}", -1, "{1}{C}");
        testReduce("{C}{G}", -1, "{1}{C}{G}");

        // 0 generic, increase cost by 1
        testReduce("", -1, "{1}");
        testReduce("{R}", -1, "{1}{R}");
        testReduce("{R}{G}", -1, "{1}{R}{G}");

        // 1 generic, increase cost by 1
        testReduce("{1}", -1, "{2}");
        testReduce("{R}{1}", -1, "{R}{2}");
        testReduce("{1}{R}", -1, "{2}{R}");
        testReduce("{1}{R}{G}", -1, "{2}{R}{G}");
        testReduce("{R}{1}{G}", -1, "{R}{2}{G}");
        testReduce("{R}{G}{1}", -1, "{R}{G}{2}");

        // 2 generics, increase cost by 1
        testReduce("{2}", -1, "{3}");
        testReduce("{R}{2}", -1, "{R}{3}");
        testReduce("{2}{R}", -1, "{3}{R}");
        testReduce("{2}{R}{G}", -1, "{3}{R}{G}");
        testReduce("{R}{2}{G}", -1, "{R}{3}{G}");
        testReduce("{R}{G}{2}", -1, "{R}{G}{3}");

        // 3 generics, increase cost by 2
        testReduce("{3}", -2, "{5}");
        testReduce("{R}{3}", -2, "{R}{5}");
        testReduce("{3}{R}", -2, "{5}{R}");
        testReduce("{3}{R}{G}", -2, "{5}{R}{G}");
        testReduce("{R}{3}{G}", -2, "{R}{5}{G}");
        testReduce("{R}{G}{3}", -2, "{R}{G}{5}");

        // HYBRID
        // from Reaper King
        // If an effect reduces the cost to cast a spell by an amount of generic mana, it applies to a monocolored hybrid
        // spell only if you’ve chosen a method of paying for it that includes generic mana.
        // (2008-05-01)

        // MONO HYBRID
        // 1. Mono hybrid always 2 generic mana like 2/R
        // 2. Generic must have priority over hybrid

        // no generic, normal amount
        // mono hybrid, decrease cost by 1
        testReduce("{2/R}", 1, "{1/R}");
        testReduce("{2/R}{2/G}", 1, "{1/R}{2/G}"); // TODO: add or/or reduction? (see https://github.com/magefree/mage/issues/6130 )
        // mono hybrid, increase cost by 1
        testReduce("{2/R}", -1, "{1}{2/R}");
        testReduce("{2/R}{2/G}", -1, "{1}{2/R}{2/G}");

        // generic, normal amount
        // mono hybrid + 1 generic, decrease cost by 1
        testReduce("{2/R}{1}", 1, "{2/R}");
        testReduce("{2/R}{2/G}{1}", 1, "{2/R}{2/G}");
        // mono hybrid + 1 generic, increase cost by 1
        testReduce("{2/R}{1}", -1, "{2/R}{2}");
        testReduce("{2/R}{2/G}{1}", -1, "{2/R}{2/G}{2}");

        // generic, too much generic
        // mono hybrid + 2 generic, decrease cost by 1
        testReduce("{2/R}{2}", 1, "{2/R}{1}");
        testReduce("{2/R}{2/G}{2}", 1, "{2/R}{2/G}{1}");
        // mono hybrid + 2 generic, increase cost by 1
        testReduce("{2/R}{2}", -1, "{2/R}{3}");
        testReduce("{2/R}{2/G}{2}", -1, "{2/R}{2/G}{3}");

        // generic, too much reduce
        // mono hybrid + 1 generic, decrease cost by 2
        testReduce("{2/R}{1}", 2, "{1/R}");
        testReduce("{2/R}{2/G}{1}", 2, "{1/R}{2/G}");  // TODO: add or/or reduction? (see https://github.com/magefree/mage/issues/6130 )
        // mono hybrid + 1 generic, increase cost by 2
        testReduce("{2/R}{1}", -2, "{2/R}{3}");
        testReduce("{2/R}{2/G}{1}", -2, "{2/R}{2/G}{3}");

        // EXTRA
        // TODO: add or/or reduction? (see https://github.com/magefree/mage/issues/6130 )
        testReduce("{2}{2/R}{2/G}", 3, "{1/R}{2/G}");
        testReduce("{2}{2/R}{2/G}", 4, "{0/R}{2/G}");
        testReduce("{2}{2/R}{2/G}", 5, "{0/R}{1/G}");
    }
}
