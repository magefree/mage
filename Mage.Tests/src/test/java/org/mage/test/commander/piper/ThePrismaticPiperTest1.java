package org.mage.test.commander.piper;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest1 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 99 Island
    // SB: 1 The Prismatic Piper

    public ThePrismaticPiperTest1() {
        super(1);
    }

    @Test
    public void testColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertColor(playerA, piper, "U", true);
    }
}
