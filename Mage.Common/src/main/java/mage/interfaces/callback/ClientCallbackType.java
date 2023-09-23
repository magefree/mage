package mage.interfaces.callback;

/**
 * Server event type for processing on the client
 *
 * @author JayDi85
 */
public enum ClientCallbackType {

    UPDATE(true, true), // game update
    TABLE_CHANGE, // all important game events + game update
    MESSAGE(true, false), // show message/log without game update
    DIALOG, // all dialogs + game update
    CLIENT_SIDE_EVENT(true, true); // without game uodate

    final boolean canComeInAnyOrder;
    final boolean mustIgnoreOnOutdated; // if event come in any order and contain game update then it must be ignored on outdate

    ClientCallbackType() {
        this(false, false);
    }

    ClientCallbackType(boolean canComeInAnyOrder, boolean mustIgnoreOnOutdated) {
        this.canComeInAnyOrder = canComeInAnyOrder;
        this.mustIgnoreOnOutdated = mustIgnoreOnOutdated;
    }

    public boolean canComeInAnyOrder() {
        return this.canComeInAnyOrder;
    }

    public boolean mustIgnoreOnOutdated() {
        return this.mustIgnoreOnOutdated;
    }
}
