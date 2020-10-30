package mage.client.table;

import mage.view.RoundView;
import mage.view.TournamentGameView;
import mage.view.TournamentView;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JayDi85
 */
public class TournamentMatchesTableModel extends AbstractTableModel {
    public static final int ACTION_COLUMN = 4; // column the action is located

    private final String[] columnNames = new String[]{"Round Number", "Players", "State", "Result", "Action"};
    private TournamentGameView[] games = new TournamentGameView[0];
    private boolean watchingAllowed;

    public void loadData(TournamentView tournament) {
        List<TournamentGameView> views = new ArrayList<>();
        watchingAllowed = tournament.isWatchingAllowed();
        for (RoundView round : tournament.getRounds()) {
            views.addAll(round.getGames());
        }
        games = views.toArray(new TournamentGameView[0]);
        this.fireTableDataChanged();
    }

    public String getTableAndGameInfo(int row) {
        return this.games[row].getTableId().toString() + ";" + games[row].toString();
    }

    public String findTableAndGameInfoByRow(int row) {
        if (row >= 0 && row < this.games.length) {
            return getTableAndGameInfo(row);
        } else {
            return null;
        }
    }

    public int findRowByTableAndGameInfo(String tableAndGame) {
        for (int i = 0; i < this.games.length; i++) {
            String rowID = this.games[i].getTableId().toString() + ";" + this.games[i].toString();
            if (tableAndGame.equals(rowID)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getRowCount() {
        return games.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return Integer.toString(games[arg0].getRoundNum());
            case 1:
                return games[arg0].getPlayers();
            case 2:
                return games[arg0].getState();
            case 3:
                return games[arg0].getResult();
            case 4:
//                if (games[arg0].getState().equals("Finished")) {
//                    return "Replay";
//                }
                if (watchingAllowed && games[arg0].getState().startsWith("Dueling")) {
                    return "Watch";
                }
                return "";
            case 5:
                return games[arg0].getTableId().toString();
            case 6:
                return games[arg0].getMatchId().toString();
            case 7:
                return games[arg0].getGameId().toString();

        }
        return "";
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
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == ACTION_COLUMN;
    }

}