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

    private void assertDayNight(boolean daytime) {
        Assert.assertTrue("It should not be neither day nor night", currentGame.hasDayNight());
        if (daytime) {
            Assert.assertTrue("It should be day", currentGame.checkDayNight(true));
            Assert.assertFalse("It should not be night", currentGame.checkDayNight(false));
        } else {
            Assert.assertTrue("It should be night", currentGame.checkDayNight(false));
            Assert.assertFalse("It should not be day", currentGame.checkDayNight(true));
        }
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

        assertDayNight(true);
        assertPermanentCount(playerA, ruffian, 1);
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

        assertDayNight(false);
        assertPermanentCount(playerA, smasher, 1);
    }
}
