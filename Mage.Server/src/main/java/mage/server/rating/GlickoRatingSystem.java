/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.server.rating;

import java.util.Date;

/**
 *
 * @author Quercitron
 */
public class GlickoRatingSystem {

    // rating deviation will grow back from 50 to max 350 in 2 years
    public static final double C = 0.00137934314767061324980397708525;

    public static final double BaseRating = 1500;
    public static final double BaseRD = 350;
    public static final double MinRD = 30;

    private static final double Q = Math.log(10) / 400;

    public static GlickoRating getInitialRating() {
        return new GlickoRating(GlickoRatingSystem.BaseRating, GlickoRatingSystem.BaseRD, 0);
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
            newRatingDeviation = Math.max(Math.min(BaseRD, newRD), MinRD);
        }
        else
        {
            newRatingDeviation = BaseRD;
        }
        return newRatingDeviation;
    }

    private GlickoRating getNewRating(GlickoRating playerRating, GlickoRating opponentRating, double outcome) {
        double RD = playerRating.getRatingDeviation();

        double g = gFunc(opponentRating.getRatingDeviation());
        double p = -g * (playerRating.getRating() - opponentRating.getRating()) / 400;
        double e = 1 / (1 + Math.pow(10, p));
        double d2 = 1 / (Q * Q * g * g * e * (1 - e));

        // todo: set minimum K?
        double newRating = playerRating.getRating() + Q / (1 / RD / RD + 1 / d2) * g * (outcome - e);
        double newRD = Math.sqrt(1 / (1 / RD / RD + 1 / d2));

        return new GlickoRating(newRating, newRD);
    }

    private double gFunc(double rd) {
        return 1 / Math.sqrt(1 + 3 * Q * Q * rd * rd / Math.PI / Math.PI);
    }

}
