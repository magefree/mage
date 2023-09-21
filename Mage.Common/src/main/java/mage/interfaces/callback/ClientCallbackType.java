package mage.interfaces.callback;

/**
 * Server event type for processing on the client
 *
 * @author JayDi85
 */
public enum ClientCallbackType {

    TABLE_CHANGE,
    UPDATE(true, true),
    MESSAGE(true, false),
    DIALOG,
    CLIENT_SIDE_EVENT(true, true);

    final boolean canProcessInAnyOrder;
    final boolean mustIgnoreOnOutdated;

    ClientCallbackType() {
        this(false, false);
    }

    ClientCallbackType(boolean canProcessInAnyOrder, boolean mustIgnoreOnOutdated) {
        this.canProcessInAnyOrder = canProcessInAnyOrder;
        this.mustIgnoreOnOutdated = mustIgnoreOnOutdated;
    }

    public boolean canProcessInAnyOrder() {
        return this.canProcessInAnyOrder;
    }

    public boolean mustIgnoreOnOutdated() {
        return this.mustIgnoreOnOutdated;
    }
}
