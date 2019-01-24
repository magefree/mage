package mage.client.table;

import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * @author JayDi85
 */
public class TablesUtil {

    private static final Logger logger = Logger.getLogger(TablesUtil.class);

    public static String getSearchIdFromTable(JTable table, int row) {
        // tableUUID;gameUUID
        String searchId = null;
        if (table.getModel() instanceof TablesTableModel) {
            searchId = ((TablesTableModel) table.getModel()).findTableAndGameInfoByRow(row);
        } else if (table.getModel() instanceof MatchesTableModel) {
            searchId = ((MatchesTableModel) table.getModel()).findTableAndGameInfoByRow(row);
        } else if (table.getModel() instanceof TournamentMatchesTableModel) {
            searchId = ((TournamentMatchesTableModel) table.getModel()).findTableAndGameInfoByRow(row);
        } else {
            logger.error("Not supported tables model " + table.getModel().getClass().toString());
        }
        return searchId;
    }

    public static int findTableRowFromSearchId(Object tableModel, String searchId) {
        // tableUUID;gameUUID
        int row = -1;
        if (searchId != null) {
            if (tableModel instanceof TablesTableModel) {
                row = ((TablesTableModel) tableModel).findRowByTableAndGameInfo(searchId);
            } else if (tableModel instanceof MatchesTableModel) {
                row = ((MatchesTableModel) tableModel).findRowByTableAndGameInfo(searchId);
            } else if (tableModel instanceof TournamentMatchesTableModel) {
                row = ((TournamentMatchesTableModel) tableModel).findRowByTableAndGameInfo(searchId);
            } else {
                logger.error("Not supported tables model " + tableModel.getClass().toString());
            }
        }
        return row;
    }

    public static int getSelectedModelRow(JTable table) {
        return getModelRowFromView(table, table.getSelectedRow());
    }

    public static int getModelRowFromView(JTable table, int viewRow) {
        if (viewRow != -1 && viewRow < table.getModel().getRowCount()) {
            return table.convertRowIndexToModel(viewRow);
        }
        return -1;
    }

    public static int getViewRowFromModel(JTable table, int modelRow) {
        if (modelRow != -1 && modelRow < table.getModel().getRowCount()) {
            return table.convertRowIndexToView(modelRow);
        }
        return -1;
    }
}
