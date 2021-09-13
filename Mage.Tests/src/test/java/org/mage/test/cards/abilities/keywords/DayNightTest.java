package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class DayNightTest extends CardTestPlayerBase {

    private static final String ruffian = "Tavern Ruffian";
    private static final String smasher = "Tavern Smasher";
    private static final String moonmist = "Moonmist";
    private static final String outcasts = "Grizzled Outcasts";
    private static final String wantons = "Krallenhorde Wantons";
    private static final String immerwolf = "Immerwolf";

    private void assertDayNight(boolean daytime) {
        Assert.assertTrue("It should not be neither day nor night", currentGame.hasDayNight());
        Assert.assertTrue("It should be " + (daytime ? "day" : "night"), currentGame.checkDayNight(daytime));
        Assert.assertFalse("It should not be " + (daytime ? "night" : "day"), currentGame.checkDayNight(!daytime));
    }

    private void assertRuffianSmasher(boolean daytime) {
        assertDayNight(daytime);
        if (daytime) {
            assertPowerToughness(playerA, ruffian, 2, 5);
            assertPermanentCount(playerA, smasher, 0);
        } else {
            assertPermanentCount(playerA, ruffian, 0);
            assertPowerToughness(playerA, smasher, 6, 5);
        }
    }

    private void setDayNight(int turn, PhaseStep phaseStep, boolean daytime) {
        runCode("set game to " + (daytime ? "day" : "night"), turn, phaseStep, playerA, (i, p, game) -> game.setDaytime(daytime));
    }

    @Test
    public void testRegularDay() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertRuffianSmasher(true);
    }

    @Test
    public void testNightbound() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertRuffianSmasher(false);
    }

    @Test
    public void testDayToNightTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertRuffianSmasher(false);
    }

    @Test
    public void testNightToDayTransform() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertRuffianSmasher(true);
    }

    @Test
    public void testMoonmistFails() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, ruffian);
        addCard(Zone.BATTLEFIELD, playerA, outcasts);
        addCard(Zone.HAND, playerA, moonmist);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, moonmist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertRuffianSmasher(true);
        assertPermanentCount(playerA, outcasts, 0);
        assertPowerToughness(playerA, wantons, 7, 7);
    }

    @Test
    public void testImmerwolf() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, immerwolf);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertDayNight(true);
        assertPowerToughness(playerA, smasher, 6 + 1, 5 + 1);
        assertPermanentCount(playerA, ruffian, 0);
    }
}
