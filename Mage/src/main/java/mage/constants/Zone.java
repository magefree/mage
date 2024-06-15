
package mage.constants;

/**
 * @author North
 */
public enum Zone {

    HAND(false),
    GRAVEYARD(true),
    LIBRARY(false),
    BATTLEFIELD(true),
    STACK(true),
    EXILED(true),
    ALL(false),
    OUTSIDE(false),
    COMMAND(true),
    MUTATE(true);

    private final boolean isPublic;

    Zone(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean match(Zone zone) {
        return (this == zone || this == ALL || zone == ALL || (this == BATTLEFIELD && zone == MUTATE));
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
