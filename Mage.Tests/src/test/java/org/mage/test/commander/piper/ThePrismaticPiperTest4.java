package org.mage.test.commander.piper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testColor() {
        Assertions.assertThrows(UnsupportedOperationException.class, this::execute);
    }
}
