package mage.client.table;

import mage.client.SessionHandler;
import mage.constants.SkillLevel;
import mage.remote.MageRemoteException;
import mage.view.TableView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.Date;

public class TablesTableModel extends AbstractTableModel {

    final ImageIcon tourneyIcon = new ImageIcon(getClass().getResource("/tables/tourney_icon.png"));
    final ImageIcon matchIcon = new ImageIcon(getClass().getResource("/tables/match_icon.png"));

    public static final int COLUMN_ICON = 0;
    public static final int COLUMN_DECK_TYPE = 1; // column the deck type is located (starting with 0) Start string is used to check for Limited
    public static final int COLUMN_OWNER = 2;
    public static final int COLUMN_GAME_TYPE = 3;
    public static final int COLUMN_INFO = 4;
    public static final int COLUMN_STATUS = 5;
    public static final int COLUMN_PASSWORD = 6;
    public static final int COLUMN_CREATED = 7;
    public static final int COLUMN_SKILL = 8;
    public static final int COLUMN_RATING = 9;
    public static final int COLUMN_QUIT_RATIO = 10;
    public static final int COLUMN_MINIMUM_RATING = 11;
    public static final int ACTION_COLUMN = 12; // column the action is located (starting with 0)

    public static final String RATED_VALUE_YES = "YES";
    public static final String RATED_VALUE_NO = "";

    public static final String PASSWORD_VALUE_YES = "YES";

    private final String[] columnNames = new String[]{"M/T", "Deck Type", "Owner / Players", "Game Type", "Info", "Status", "Password", "Created / Started", "Skill Level", "Rated", "Quit %", "Min Rating", "Action"};

    private TableView[] tables = new TableView[0];

    TablesTableModel() {
    }

    public void loadData(Collection<TableView> tables) throws MageRemoteException {
        this.tables = tables.toArray(new TableView[0]);
        this.fireTableDataChanged();
    }

    public String getTableAndGameInfo(int row) {
        return this.tables[row].getTableId().toString() + ";" + (!tables[row].getGames().isEmpty() ? tables[row].getGames().get(0).toString() : "null");
    }

    public String findTableAndGameInfoByRow(int row) {
        if (row >= 0 && row < this.tables.length) {
            return getTableAndGameInfo(row);
        } else {
            return null;
        }
    }

    public int findRowByTableAndGameInfo(String tableAndGame) {
        for (int i = 0; i < this.tables.length; i++) {
            String rowID = this.tables[i].getTableId().toString() + ";" + (!this.tables[i].getGames().isEmpty() ? this.tables[i].getGames().get(0).toString() : "null");
            if (tableAndGame.equals(rowID)) {
                return i;
            }
        }
        return -1;
    }

    public String getSkillLevelAsCode(SkillLevel skill, boolean asRegExp) {
        String res;
        switch (skill) {
            case BEGINNER:
                res = "*";
                break;
            case CASUAL:
                res = "**";
                break;
            case SERIOUS:
                res = "***";
                break;
            default:
                res = "";
                break;
        }

        // regexp format for search table rows
        if (asRegExp) {
            res = String.format("^%s$", res.replace("*", "\\*"));
        }

        return res;
    }

    @Override
    public int getRowCount() {
        return tables.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return tables[arg0].isTournament() ? tourneyIcon : matchIcon;
            case 1:
                return tables[arg0].getDeckType();
            case 2:
                return tables[arg0].getControllerName();
            case 3:
                return tables[arg0].getGameType();
            case 4:
                return tables[arg0].getAdditionalInfo();
            case 5:
                return tables[arg0].getTableStateText();
            case 6:
                return tables[arg0].isPassworded() ? PASSWORD_VALUE_YES : "";
            case 7:
                return tables[arg0].getCreateTime(); // use cell render, not format here
            case 8:
                return this.getSkillLevelAsCode(tables[arg0].getSkillLevel(), false);
            case 9:
                return tables[arg0].isRated() ? RATED_VALUE_YES : RATED_VALUE_NO;
            case 10:
                return tables[arg0].getQuitRatio();
            case 11:
                return tables[arg0].getMinimumRating();
            case 12:
                switch (tables[arg0].getTableState()) {

                    case WAITING:
                        String owner = tables[arg0].getControllerName();
                        if (SessionHandler.getSession() != null && owner.equals(SessionHandler.getUserName())) {
                            return "";
                        }
                        return "Join";
                    case CONSTRUCTING:
                    case DRAFTING:
                        if (tables[arg0].isTournament()) {
                            return "Show";
                        }
                    case DUELING:
                        if (tables[arg0].isTournament()) {
                            return "Show";
                        } else {
                            owner = tables[arg0].getControllerName();
                            if (SessionHandler.getSession() != null && owner.equals(SessionHandler.getUserName())) {
                                return "";
                            }
                            if (tables[arg0].getSpectatorsAllowed()) {
                                return "Watch";
                            }
                            return "";
                        }
                    default:
                        return "";
                }
            case 13:
                return tables[arg0].isTournament();
            case 14:
                if (!tables[arg0].getGames().isEmpty()) {
                    return tables[arg0].getGames().get(0);
                }
                return null;
            case 15:
                return tables[arg0].getTableId();
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
        switch (columnIndex) {
            case COLUMN_ICON:
                return Icon.class;
            case COLUMN_SKILL:
                return SkillLevel.class;
            case COLUMN_CREATED:
                return Date.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == ACTION_COLUMN;
    }

}
