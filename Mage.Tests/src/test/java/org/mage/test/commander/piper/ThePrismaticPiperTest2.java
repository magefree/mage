package org.mage.test.commander.piper;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest2 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 98 Island
    // 1 Mountain
    // SB: 1 The Prismatic Piper
    // SB: 1 Ghost of Ramirez DePietro

    public ThePrismaticPiperTest2() {
        super(2);
    }

    @Test
    public void testColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertColor(playerA, piper, "R", true);
    }
}
