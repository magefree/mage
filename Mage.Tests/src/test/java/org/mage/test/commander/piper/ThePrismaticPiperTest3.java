package org.mage.test.commander.piper;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest3 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 98 Island
    // 1 Mountain
    // SB: 1 The Prismatic Piper
    // SB: 1 Kraum, Ludevic's Opus

    public ThePrismaticPiperTest3() {
        super(3);
    }

    @Test
    public void testColor() {
        setChoice(playerA, "White");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertColor(playerA, piper, "W", true);
    }
}
