package org.mage.test.cards.rolldice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RollDiceTest extends CardTestPlayerBase {

    private static final String goblins = "Swarming Goblins";

    private void runGoblinTest(int roll, int goblinCount) {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, goblins);

        setDieRollResult(playerA, roll);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblins);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, goblins, 1);
        assertPermanentCount(playerA, "Goblin", goblinCount);
    }

    @Test
    public void testGoblinRoll1() {
        runGoblinTest(1, 1);
    }

    @Test
    public void testGoblinRoll9() {
        runGoblinTest(9, 1);
    }

    @Test
    public void testGoblinRoll10() {
        runGoblinTest(10, 2);
    }

    @Test
    public void testGoblinRoll19() {
        runGoblinTest(19, 2);
    }

    @Test
    public void testGoblinRoll20() {
        runGoblinTest(20, 3);
    }
}
