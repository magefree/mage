package org.mage.test.cards.designations;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class StartYourEnginesTest extends CardTestPlayerBase {

    private static final String sarcophagus = "Walking Sarcophagus";

    private void assertSpeed(Player player, int speed) {
        Assert.assertEquals(player.getName() + " speed should be " + speed, speed, player.getSpeed());
    }

    @Test
    public void testRegular() {
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 0);
    }

    @Test
    public void testSpeed1() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.HAND, playerA, sarcophagus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sarcophagus);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 1);
        assertPowerToughness(playerA, sarcophagus, 2, 1);
    }
}
