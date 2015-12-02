package mage.constants;

/**
 *
 * @author North
 */
public enum RangeOfInfluence {
    ONE(1),
    TWO(2),
    ALL(0);

    private int range;

    RangeOfInfluence(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
