package org.mage.test.cards.flipcoin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FlipCoinTest extends CardTestPlayerBase {

    @Test
    public void test_RandomResult() {
        // {3}, {T}: Flip a coin. If you win the flip, create a 2/2 colorless Insect artifact creature token with flying named Wirefly.
        // If you lose the flip, destroy all permanents named Wirefly.
        addCard(Zone.BATTLEFIELD, playerA, "Wirefly Hive");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");

        //setStrictChooseMode(true); // normal play without errors
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test(expected = AssertionError.class)
    public void test_StrictMode_MustFailWithoutResultSetup() {
        // {3}, {T}: Flip a coin. If you win the flip, create a 2/2 colorless Insect artifact creature token with flying named Wirefly.
        // If you lose the flip, destroy all permanents named Wirefly.
        addCard(Zone.BATTLEFIELD, playerA, "Wirefly Hive");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");

        setStrictChooseMode(true); // no coinresult in choices, so it must fail
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_StrictMode_MustWorkWithResultSetup() {
        // {3}, {T}: Flip a coin. If you win the flip, create a 2/2 colorless Insect artifact creature token with flying named Wirefly.
        // If you lose the flip, destroy all permanents named Wirefly.
        addCard(Zone.BATTLEFIELD, playerA, "Wirefly Hive");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // activates 5 times with same flip coin result
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setFlipCoinResult(playerA, true);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setFlipCoinResult(playerA, true);
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setFlipCoinResult(playerA, true);
        activateAbility(7, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setFlipCoinResult(playerA, true);
        activateAbility(9, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setFlipCoinResult(playerA, true);

        setStrictChooseMode(true);
        setStopAt(9, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wirefly", 5);
    }
}
