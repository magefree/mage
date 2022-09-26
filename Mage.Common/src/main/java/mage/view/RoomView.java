

package mage.view;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RoomView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final RoomUsersView roomUsersView;
    private final List<MatchView> matchViews;
    private final List<TableView> tableViews;

    public RoomView(RoomUsersView roomUsersView, List<TableView> tableViews, List<MatchView> matchViews) {

        this.roomUsersView = roomUsersView;
        this.matchViews = matchViews;
        this.tableViews = tableViews;
        
    }

    public RoomUsersView getRoomUsersView() {
        return roomUsersView;
    }

    public List<MatchView> getMatchViews() {
        return matchViews;
    }

    public List<TableView> getTableViews() {
        return tableViews;
    }
}
