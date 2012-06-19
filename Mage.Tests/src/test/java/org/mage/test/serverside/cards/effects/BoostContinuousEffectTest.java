package org.mage.test.serverside.cards.effects;

import mage.Constants;
import mage.Constants.PhaseStep;
import mage.filter.Filter;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Tests continuous boost effect like "White creatures you control get +1/+1".
 *
 * @author ayratn
 */
public class BoostContinuousEffectTest extends CardTestBase {

    @Test
    @Ignore
    public void testHonorOfThePoor() throws Exception {
        load("M11/Honor of the Pure.test");
        execute();

        checkPermanentPT(playerA, "Tine Shrike", 3, 2, Filter.ComparisonScope.Any);
        checkPermanentPT(playerA, "Runeclaw Bear", 2, 2, Filter.ComparisonScope.Any);
    }

    @Test
    public void testHonorOfThePoor2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Honor of the Pure", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Black Knight");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        assertPowerToughness(playerA, "White Knight", 4, 4, Filter.ComparisonScope.Any);
        assertPowerToughness(playerA, "Black Knight", 2, 2, Filter.ComparisonScope.Any);
    }

    @Test
    public void testHonorOfThePoor3() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Honor of the Pure");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Black Knight");

        setStopAt(2, PhaseStep.CLEANUP);
        execute();
        assertPowerToughness(playerA, "White Knight", 3, 3, Filter.ComparisonScope.Any);
        assertPowerToughness(playerA, "Black Knight", 2, 2, Filter.ComparisonScope.Any);
    }

    @Test
    public void testGiantGrowth() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Black Knight");
        addCard(Constants.Zone.HAND, playerA, "Giant Growth");

        castSpell(playerA, "Giant Growth");
        addFixedTarget(playerA, "Giant Growth", "White Knight");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        assertPowerToughness(playerA, "White Knight", 5, 5, Filter.ComparisonScope.Any);
        assertPowerToughness(playerA, "Black Knight", 2, 2, Filter.ComparisonScope.Any);
    }

}
