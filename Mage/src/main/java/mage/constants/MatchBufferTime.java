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
    SEC__01(1, "1 Second"),
    SEC__02(2, "2 Seconds"),
    SEC__03(3, "3 Seconds"),
    SEC__05(5, "5 Seconds"),
    SEC__10(10, "10 Seconds"),
    SEC__15(15, "15 Seconds"),
    SEC__20(20, "20 Seconds"),
    SEC__25(25, "25 Seconds"),
    SEC__30(30, "30 Seconds");

    private final int bufferSecs;
    private final String name;

    MatchBufferTime(int bufferSecs, String name) {
        this.bufferSecs = bufferSecs;
        this.name = name;
    }

    public int getBufferSecs() {
        return bufferSecs;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
