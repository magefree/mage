
package mage.server.rating;

/**
 *
 * @author Quercitron
 */
public class GlickoRating {

    private double rating;

    private double ratingDeviation;

    private long lastGameTimeMs;

    public GlickoRating(double rating, double ratingDeviation) {
        this(rating, ratingDeviation, 0);
    }

    public GlickoRating(double rating, double ratingDeviation, long lastGameTimeMs) {
        this.rating = rating;
        this.ratingDeviation = ratingDeviation;
        this.lastGameTimeMs = lastGameTimeMs;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRatingDeviation() {
        return ratingDeviation;
    }

    public void setRatingDeviation(double ratingDeviation) {
        this.ratingDeviation = ratingDeviation;
    }

    public long getLastGameTimeMs() {
        return lastGameTimeMs;
    }

    public void setLastGameTimeMs(long lastGameTimeMs) {
        this.lastGameTimeMs = lastGameTimeMs;
    }

}
