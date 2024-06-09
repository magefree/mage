package org.mage.test.serverside.rating;

import mage.server.rating.GlickoRating;
import mage.server.rating.GlickoRatingSystem;
import mage.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Quercitron
 */
public class GlickoRatingSystemTest {

    @Test
    public void testRatingsAreEqualAfterDraws() {
        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();

        int count = 1000;
        for (int i = 0; i < count; i++) {
            double startRating = RandomUtil.nextDouble() * 2500 + 500;
            double startRatingDeviation = Math.min(RandomUtil.nextDouble() * 300 + 100, GlickoRatingSystem.BASE_RD);
            GlickoRating player1 = new GlickoRating(startRating, startRatingDeviation, 1);
            GlickoRating player2 = new GlickoRating(startRating, startRatingDeviation, 1);

            int gamesCount = RandomUtil.nextInt(50) + 1;

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
            double startRating1 = RandomUtil.nextDouble() * 2500 + 500;
            double startRating2 = RandomUtil.nextDouble() * 2500 + 500;
            double startRatingDeviation = Math.min(RandomUtil.nextDouble() * 300 + 100, GlickoRatingSystem.BASE_RD);
            GlickoRating player1 = new GlickoRating(startRating1, startRatingDeviation, 1);
            GlickoRating player2 = new GlickoRating(startRating2, startRatingDeviation, 1);

            glickoRatingSystem.updateRating(player1, player2, RandomUtil.nextDouble(), 1);
            Assert.assertEquals(player1.getRating() - startRating1, startRating2 - player2.getRating(), 1e-5);
            Assert.assertEquals(player1.getRatingDeviation(), player2.getRatingDeviation(), 1e-5);
        }
    }

    @Test
    public void testExactResult1() {
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
    public void testExactResult2() {
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
    public void testExactResult3() {
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
    public void testExactResult4() {
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
    public void testExactResult5() {
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
