package mage.constants;

/**
 * The time a player receives whenever the timer starts. This ticks down before their normal time,
 * and refreshes to full every time the timer starts, creating a sort of buffer. Similar to how to
 * chess clocks work.
 * 
 * Based off of MatchTimeLimit
 *
 * @author alexander-novo
 */
public enum MatchBufferTime {
    NONE(0, "None"),
    MIN__10(5, "5 Seconds"),
    MIN__15(10, "10 Seconds"),
    MIN__20(15, "15 Seconds");

    private final int matchSeconds;
    private final String name;

    MatchBufferTime(int matchSeconds, String name) {
        this.matchSeconds = matchSeconds;
        this.name = name;
    }

    public int getBufferTime() {
        return matchSeconds;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
