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
    NONE(0),
    SEC__01(1),
    SEC__02(2),
    SEC__03(3),
    SEC__05(5),
    SEC__10(10),
    SEC__15(15),
    SEC__20(20),
    SEC__25(25),
    SEC__30(30);

    private final int bufferSecs;

    MatchBufferTime(int bufferSecs) {
        this.bufferSecs = bufferSecs;
    }

    public int getBufferSecs() {
        return bufferSecs;
    }

    public String getName() {
        if (this == NONE){
            return "None";
        } else if (this == SEC__01){
            return "1 Second";
        } else {
            return bufferSecs + " Seconds";
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getShortName() {
        if (this == NONE){
            return "None";
        } else {
            return bufferSecs + "s";
        }
    }
}
