
package mage.server.rating;

import java.util.Date;

/**
 *
 * @author Quercitron
 */
public class GlickoRatingSystem {

    // rating deviation will grow back from 50 to max 350 in 2 years
    public static final double C = 0.00137934314767061324980397708525;

    public static final double BASE_RATING = 1500;
    public static final double BASE_RD = 350;
    public static final double MIN_RD = 30;

    private static final double Q = Math.log(10) / 400;

    public static GlickoRating getInitialRating() {
        return new GlickoRating(GlickoRatingSystem.BASE_RATING, GlickoRatingSystem.BASE_RD, 0);
    }

    public static int getDisplayedRating(GlickoRating rating) {
        long currentTime = new Date().getTime();
        double updatedRatingDeviation = getUpdatedRD(rating, currentTime);
        return (int) Math.max(rating.getRating() - 2 * updatedRatingDeviation, 0);
    }

    public static int getDefaultDisplayedRating() {
        return getDisplayedRating(getInitialRating());
    }

    public void updateRating(GlickoRating playerRating, GlickoRating opponentRating, double outcome, long gameTimeMs) {
        playerRating.setRatingDeviation(getUpdatedRD(playerRating, gameTimeMs));
        opponentRating.setRatingDeviation(getUpdatedRD(opponentRating, gameTimeMs));

        GlickoRating newPlayerRating = getNewRating(playerRating, opponentRating, outcome);
        GlickoRating newOpponentRating = getNewRating(opponentRating, playerRating, 1 - outcome);

        playerRating.setRating(newPlayerRating.getRating());
        playerRating.setRatingDeviation(newPlayerRating.getRatingDeviation());
        playerRating.setLastGameTimeMs(gameTimeMs);

        opponentRating.setRating(newOpponentRating.getRating());
        opponentRating.setRatingDeviation(newOpponentRating.getRatingDeviation());
        opponentRating.setLastGameTimeMs(gameTimeMs);
    }

    private static double getUpdatedRD(GlickoRating rating, long gameTimeMs) {
        double newRatingDeviation;
        if (rating.getLastGameTimeMs() != 0)
        {
            double newRD = Math.sqrt(
                    rating.getRatingDeviation() * rating.getRatingDeviation()
                            + C * C * Math.max(gameTimeMs - rating.getLastGameTimeMs(), 0));
            newRatingDeviation = Math.max(Math.min(BASE_RD, newRD), MIN_RD);
        }
        else
        {
            newRatingDeviation = BASE_RD;
        }
        return newRatingDeviation;
    }

    private GlickoRating getNewRating(GlickoRating playerRating, GlickoRating opponentRating, double outcome) {
        double playerRatingDeviation = playerRating.getRatingDeviation();

        double g = gFunc(opponentRating.getRatingDeviation());
        double p = -g * (playerRating.getRating() - opponentRating.getRating()) / 400;
        double e = 1 / (1 + Math.pow(10, p));
        double d2 = 1 / (Q * Q * g * g * e * (1 - e));

        // todo: set minimum K?
        double newRating = playerRating.getRating() + Q / (1 / playerRatingDeviation / playerRatingDeviation + 1 / d2) * g * (outcome - e);
        double newRD = Math.sqrt(1 / (1 / playerRatingDeviation / playerRatingDeviation + 1 / d2));

        return new GlickoRating(newRating, newRD);
    }

    private double gFunc(double rd) {
        return 1 / Math.sqrt(1 + 3 * Q * Q * rd * rd / Math.PI / Math.PI);
    }

}
