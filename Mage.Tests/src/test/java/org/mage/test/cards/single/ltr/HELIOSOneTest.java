package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class HELIOSOneTest extends CardTestPlayerBase {

    /**
     * {@link HELIOSOneTest HELIOS One}
     * Land
     * {T}: Add {C}.
     * {1}, {T}: You get {E} (an energy counter).
     * {3}, {T}, Pay X {E}, Sacrifice HELIOS One: Destroy target nonland permanent with mana value X. Activate only as a sorcery.
     */
    private static final String helios = "HELIOS One";

    @Test
    public void test_Target_0MV_0Energy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, helios, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        setChoice(playerA, "X=0");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, helios, 1);
        assertTappedCount("Plains", true, 3);
        assertCounterCount(playerA, CounterType.ENERGY, 0);
    }

    @Test
    public void test_NoTarget_1MV_0Energy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, helios, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        // TODO: So the test suite let's you activate the ability (as it does not go to adjust targets to check them.)
        //       But X=0 is not a valid choice once targets are checked (no nonland card with that MV in play).
        setChoice(playerA, "X=0");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        addTarget(playerA, "Elite Vanguard"); // not a valid target for X=0 energy payment

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
            Assert.fail("Should not be possible to activate the {3} ability without energy and without a 0 mana target");
        } catch (AssertionError e) {
            Assert.assertTrue(
                    "X=0 is not a valid choice. Error message:\n" + e.getMessage(),
                    e.getMessage().contains("Message: Announce the number of {E} to pay")
            );
        }
    }

    @Test
    public void test_Target_1MV_1Energy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, helios, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        // TODO: checkPlayableAbility doesn't simulate all possible for X, so the targetAdjuster is never involved
        //       to identify if the ability can actually be activated.
        checkPlayableAbility("can't activate due to lack of energy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}", true); // should be false.
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("can activate with energy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}", true);
        setChoice(playerA, "X=1");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        addTarget(playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Elite Vanguard", 1);
        assertGraveyardCount(playerA, helios, 1);
        assertTappedCount("Plains", true, 4);
        assertCounterCount(playerA, CounterType.ENERGY, 0);
    }

    @Test
    public void test_Target_0MV_1Energy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, helios, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        setChoice(playerA, "X=0");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, helios, 1);
        assertTappedCount("Plains", true, 4);
        assertCounterCount(playerA, CounterType.ENERGY, 1);
    }
}
