package mage.client.table;

public enum TableAction {
    Join("lblJoin"),
    Remove("lblRemove"),
    Show("lblShow"),
    Watch("lblWatch"),
    Replay("lblReplay"),
    None("lblNone");

    TableAction(String name) {
        this.actionKey = name;
    }

    static public String getName(TableAction action) {
        return java.util.ResourceBundle.getBundle("otherMessage").getString(action.actionKey);
    }

    static public TableAction toAction(String actionName) {
        if (actionName == getName(Join)) {
            return Join;
        }
        if (actionName == getName(Remove)) {
            return Remove;
        }
        if (actionName == getName(Show)) {
            return Show;
        }
        if (actionName == getName(Watch)) {
            return Watch;
        }
        if (actionName == getName(Replay)) {
            return Replay;
        }
        return None;
    }

    final private String actionKey;
}