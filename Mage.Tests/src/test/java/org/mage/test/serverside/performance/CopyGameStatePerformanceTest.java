package org.mage.test.serverside.performance;

import mage.game.Game;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Test for copying game state.
 *
 * @ayratn
 */
public class CopyGameStatePerformanceTest extends CardTestBase {

    public void run() throws Exception {
        init();
        reset();
        System.out.println("Started copying...");
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 2000; i++) {
            Game game = currentGame.copy();
            Game game2 = game.copy();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Test took: " + (t2-t1) + " ms");
    }

    public static void main(String[] args) {
        CopyGameStatePerformanceTest test = new CopyGameStatePerformanceTest();
        try {
            test.run();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
