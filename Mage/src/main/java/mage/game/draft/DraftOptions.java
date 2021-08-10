package mage.game.draft;

import mage.game.tournament.LimitedOptions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class DraftOptions extends LimitedOptions implements Serializable {

    protected TimingOption timing;

    public enum TimingOption {

        // seconds per card's pick
        BEGINNER("x2.0", "Beginner (x2.0)", 2.0,
                Arrays.asList(150, 140, 130, 120, 110, 100, 90, 80, 45, 35, 30, 25, 20, 15, 10)
        ),
        REGULAR("x1.5", "Regular (x1.5)", 1.5,
                Arrays.asList(113, 105, 98, 90, 83, 75, 68, 60, 35, 30, 25, 20, 15, 10, 8)
        ),
        PROFESSIONAL("x1.0", "Professional (x1.0)", 1.0,
                Arrays.asList(75, 70, 65, 60, 55, 50, 45, 40, 30, 25, 20, 15, 12, 10, 7)
        ),
        NONE("ERROR", "", 0,
                Arrays.asList(1)
        );

        private final String shortName;
        private final String name;
        private final double customTimeoutFactor; // profi as x1.0, other modes increases by factor from x1.5 to x2.0
        private final List<Integer> times;

        TimingOption(String shortName, String name, double customTimeoutFactor, List<Integer> times) {
            this.shortName = shortName;
            this.name = name;
            this.customTimeoutFactor = customTimeoutFactor;
            this.times = times;
        }

        public String getShortName() {
            return shortName;
        }

        public String getName() {
            return name;
        }

        public double getCustomTimeoutFactor() {
            return customTimeoutFactor;
        }

        public int getPickTimeout(int cardNum) {
            if (cardNum > 15) {
                cardNum = 15;
            }

            if (times.size() >= cardNum) {
                return times.get(cardNum - 1);
            } else {
                return times.get(times.size() - 1);
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public TimingOption getTiming() {
        return timing;
    }

    public void setTiming(TimingOption timing) {
        this.timing = timing;
    }
}
