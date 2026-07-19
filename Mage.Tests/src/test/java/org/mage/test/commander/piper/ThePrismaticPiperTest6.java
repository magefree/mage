package org.mage.test.commander.piper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest6 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 96 Mountain
    // 1 Island
    // 1 Plains
    // 1 Forest
    // SB: 1 The Prismatic Piper
    // SB: 1 Kraum, Ludevic's Opus

    public ThePrismaticPiperTest6() {
        super(6);
    }

    @Test
    public void testColor() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            execute();
        });
    }
}
