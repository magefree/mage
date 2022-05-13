package org.mage.test.commander.piper;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest4 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 98 Mountain
    // 1 Island
    // SB: 1 The Prismatic Piper

    public ThePrismaticPiperTest4() {
        super(4);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testColor() {
        execute();
    }
}
