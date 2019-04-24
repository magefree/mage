
package org.mage.test.serverside.rating;

import org.junit.Assert;
import mage.server.rating.GlickoRating;
import mage.server.rating.GlickoRatingSystem;
import org.junit.Test;

import java.util.Random;

/**
 *
 * @author Quercitron
 */
public class GlickoRatingSystemTest {

    private final Random random = new Random();

    @Test
    public void testRatingsAreEqualAfterDraws() {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        int count = 1000;
        for (int i = 0; i < count; i++) {
            double startRating = random.nextDouble() * 2500 + 500;
            double startRatingDeviation = Math.min(random.nextDouble() * 300 + 100, GlickoRatingSystem.BaseRD);
            GlickoRating player1 = new GlickoRating(startRating, startRatingDeviation, 1);
            GlickoRating player2 = new GlickoRating(startRating, startRatingDeviation, 1);

            int gamesCount = random.nextInt(50) + 1;

            for (int j = 0; j < gamesCount; j++) {
                glickoRatingSystem.updateRating(player1, player2, 0.5, j + 2);
                Assert.assertEquals(player1.getRating(), player2.getRating(), 1e-5);
                Assert.assertEquals(player1.getRatingDeviation(), player2.getRatingDeviation(), 1e-5);
            }
        }
    }

    @Test
    public void testRatingChangesAreSymmetric() {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        int count = 1000;
        for (int i = 0; i < count; i++) {
            double startRating1 = random.nextDouble() * 2500 + 500;
            double startRating2 = random.nextDouble() * 2500 + 500;
            double startRatingDeviation = Math.min(random.nextDouble() * 300 + 100, GlickoRatingSystem.BaseRD);
            GlickoRating player1 = new GlickoRating(startRating1, startRatingDeviation, 1);
            GlickoRating player2 = new GlickoRating(startRating2, startRatingDeviation, 1);

            glickoRatingSystem.updateRating(player1, player2, random.nextDouble(), 1);
            Assert.assertEquals(player1.getRating() - startRating1, startRating2 - player2.getRating(), 1e-5);
            Assert.assertEquals(player1.getRatingDeviation(), player2.getRatingDeviation(), 1e-5);
        }
    }

    @Test
    public void testExactResult1()
    {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        GlickoRating player1 = new GlickoRating(1500, 350, 1);
        GlickoRating player2 = new GlickoRating(1500, 350, 1);

        glickoRatingSystem.updateRating(player1, player2, 1, 1);

        Assert.assertEquals(1662, player1.getRating(), 1);
        Assert.assertEquals(290.2, player1.getRatingDeviation(), 0.1);

        Assert.assertEquals(1338, player2.getRating(), 1);
        Assert.assertEquals(290.2, player2.getRatingDeviation(), 0.1);
    }

    @Test
    public void testExactResult2()
    {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        GlickoRating player1 = new GlickoRating(1500, 350, 1);
        GlickoRating player2 = new GlickoRating(1200, 50, 1);

        glickoRatingSystem.updateRating(player1, player2, 1, 1);

        Assert.assertEquals(1571, player1.getRating(), 1);
        Assert.assertEquals(284.3, player1.getRatingDeviation(), 0.1);

        Assert.assertEquals(1198, player2.getRating(), 1);
        Assert.assertEquals(49.8, player2.getRatingDeviation(), 0.1);
    }

    @Test
    public void testExactResult3()
    {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        GlickoRating player1 = new GlickoRating(1500, 350, 1);
        GlickoRating player2 = new GlickoRating(1200, 50, 1);

        glickoRatingSystem.updateRating(player1, player2, 0, 1);

        Assert.assertEquals(1111, player1.getRating(), 1);
        Assert.assertEquals(284.3, player1.getRatingDeviation(), 0.1);

        Assert.assertEquals(1207, player2.getRating(), 1);
        Assert.assertEquals(49.8, player2.getRatingDeviation(), 0.1);
    }

    @Test
    public void testExactResult4()
    {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        GlickoRating player1 = new GlickoRating(1500, 250, 1);
        GlickoRating player2 = new GlickoRating(2000, 100, 1);

        glickoRatingSystem.updateRating(player1, player2, 0.5, 1);

        Assert.assertEquals(1636, player1.getRating(), 1);
        Assert.assertEquals(237.6, player1.getRatingDeviation(), 0.1);

        Assert.assertEquals(1982, player2.getRating(), 1);
        Assert.assertEquals(99.1, player2.getRatingDeviation(), 0.1);
    }

    @Test
    public void testExactResult5()
    {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        GlickoRating player1 = new GlickoRating(1500, 100, 1);
        GlickoRating player2 = new GlickoRating(2000, 100, 1);

        glickoRatingSystem.updateRating(player1, player2, 1, 1);

        Assert.assertEquals(1551, player1.getRating(), 1);
        Assert.assertEquals(99.2, player1.getRatingDeviation(), 0.1);

        Assert.assertEquals(1949, player2.getRating(), 1);
        Assert.assertEquals(99.2, player2.getRatingDeviation(), 0.1);
    }

}
