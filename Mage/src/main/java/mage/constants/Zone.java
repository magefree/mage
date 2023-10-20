
package mage.constants;

/**
 * @author North
 */
public enum Zone {

    HAND(false),
    GRAVEYARD(true),
    LIBRARY(false),
    BATTLEFIELD(true),
    PHASED_OUT(true), // This is a fake zone for the phased out triggers to work.
    STACK(true),
    EXILED(true),
    ALL(false),
    OUTSIDE(false),
    COMMAND(true);

    private final boolean isPublic;

    Zone(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean match(Zone zone) {
        return (this == zone || this == ALL || zone == ALL);
    }

    @Override
    public String toString() {
        if (this == EXILED) {
            return "exile zone";
        }
        return super.toString();
    }

    public boolean isPublicZone() {
        return isPublic;
    }
}
