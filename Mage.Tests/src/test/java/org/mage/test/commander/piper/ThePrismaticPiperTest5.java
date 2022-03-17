package org.mage.test.commander.piper;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest5 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 97 Mountain
    // 1 Island
    // 1 Plains
    // SB: 1 The Prismatic Piper
    // SB: 1 Kraum, Ludevic's Opus

    public ThePrismaticPiperTest5() {
        super(5);
    }

    @Test
    public void testColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertColor(playerA, piper, "W", true);
    }
}
