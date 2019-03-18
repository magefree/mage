package mage.client.table;

import mage.remote.MageRemoteException;
import mage.view.MatchView;

import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class MatchesTableModel extends AbstractTableModel {

    private final String[] columnNames = new String[]{"Deck Type", "Players", "Game Type", "Rating", "Result", "Duration", "Start Time", "End Time", "Action"};
    public static final int COLUMN_DURATION = 5;
    public static final int COLUMN_START = 6;
    public static final int COLUMN_END = 7;
    public static final int COLUMN_ACTION = 8; // column the action is located (starting with 0)

    private MatchView[] matches = new MatchView[0];

    public void loadData(Collection<MatchView> matches) throws MageRemoteException {
        this.matches = matches.toArray(new MatchView[0]);
        this.fireTableDataChanged();
    }

    MatchesTableModel() {
    }


    public String getTableAndGameInfo(int row) {
        return this.matches[row].getTableId().toString() + ";" + (!matches[row].getGames().isEmpty() ? matches[row].getGames().get(0).toString() : "null");
    }

    public String findTableAndGameInfoByRow(int row) {
        if (row >= 0 && row < this.matches.length) {
            return getTableAndGameInfo(row);
        } else {
            return null;
        }
    }

    public int findRowByTableAndGameInfo(String tableAndGame) {
        for (int i = 0; i < this.matches.length; i++) {
            String rowID = this.matches[i].getTableId().toString() + ";" + (!this.matches[i].getGames().isEmpty() ? this.matches[i].getGames().get(0).toString() : "null");
            if (tableAndGame.equals(rowID)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getRowCount() {
        return matches.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return matches[arg0].getDeckType();
            case 1:
                return matches[arg0].getPlayers();
            case 2:
                return matches[arg0].getGameType();
            case 3:
                return matches[arg0].isRated() ? TablesTableModel.RATED_VALUE_YES : TablesTableModel.RATED_VALUE_NO;
            case 4:
                return matches[arg0].getResult();
            case 5:
                if (matches[arg0].getEndTime() != null) {
                    return matches[arg0].getEndTime().getTime() - matches[arg0].getStartTime().getTime() + new Date().getTime();
                } else {
                    return 0L;
                }
            case 6:
                return matches[arg0].getStartTime();
            case 7:
                return matches[arg0].getEndTime();
            case 8:
                if (matches[arg0].isTournament()) {
                    return "Show";
                } else if (matches[arg0].isReplayAvailable()) {
                    return "Replay";
                } else {
                    return "None";
                }
            case 9:
                return matches[arg0].getGames();
            default:
                return "";
        }
    }

    public java.util.List<UUID> getListofGames(int row) {
        return matches[row].getGames();
    }

    public boolean isTournament(int row) {
        return matches[row].isTournament();
    }

    public UUID getMatchId(int row) {
        return matches[row].getMatchId();
    }

    public UUID getTableId(int row) {
        return matches[row].getTableId();
    }

    @Override
    public String getColumnName(int columnIndex) {
        String colName = "";

        if (columnIndex <= getColumnCount()) {
            colName = columnNames[columnIndex];
        }

        return colName;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_DURATION:
                return Long.class;
            case COLUMN_START:
                return Date.class;
            case COLUMN_END:
                return Date.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == COLUMN_ACTION;
    }

}
