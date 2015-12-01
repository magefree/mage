package mage.constants;

/**
 *
 * @author North
 */
public enum Zone {

    HAND, GRAVEYARD, LIBRARY, BATTLEFIELD, STACK, EXILED, ALL, OUTSIDE, PICK, COMMAND;

    public boolean match(Zone zone) {
        return (this == zone || this == ALL || zone == ALL);
    }

    @Override
    public String toString() {
        if (this.equals(EXILED)) {
            return "exile zone";
        }
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
