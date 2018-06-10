

 /*
 * ChatPanel.java
 *
 * Created on 15-Dec-2009, 11:04:31 PM
 */
package mage.client.table;

import mage.client.MageFrame;
import mage.client.chat.ChatPanelBasic;
import mage.client.util.GUISizeHelper;
import mage.client.util.MageTableRowSorter;
import mage.client.util.gui.TableUtil;
import mage.client.util.gui.countryBox.CountryCellRenderer;
import mage.remote.MageRemoteException;
import mage.view.RoomUsersView;
import mage.view.UsersView;
import net.java.balloontip.utils.ToolTipUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.List;

import static mage.client.chat.ChatPanelBasic.CHAT_ALPHA;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_ORDER;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_WIDTH;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class PlayersChatPanel extends javax.swing.JPanel {

    private final List<String> players = new ArrayList<>();
    private final UserTableModel userTableModel;
    private static final int[] DEFAULT_COLUMNS_WIDTH = {20, 100, 40, 40, 40, 100, 40, 100, 80, 80};


    /*
     * Creates new form ChatPanel
     *
     */
    public PlayersChatPanel() {
        userTableModel = new UserTableModel(); // needs to be set before initComponents();

        initComponents();
        setBackground(new Color(0, 0, 0, CHAT_ALPHA));

        jTablePlayers.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
        jTablePlayers.setForeground(Color.white);
        jTablePlayers.setRowSorter(new MageTableRowSorter(userTableModel));
        setGUISize();

        TableUtil.setColumnWidthAndOrder(jTablePlayers, DEFAULT_COLUMNS_WIDTH, KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);
        userTableModel.initHeaderTooltips();

        jTablePlayers.setDefaultRenderer(Icon.class, new CountryCellRenderer());

        jScrollPaneTalk.setSystemMessagesPane(colorPaneSystem);
        jScrollPaneTalk.setOpaque(false);

        jScrollPaneSystem.getViewport().setOpaque(false);
        jScrollPaneSystem.setViewportBorder(null);

        colorPaneSystem.setExtBackgroundColor(new Color(0, 0, 0, CHAT_ALPHA)); // Alpha = 255 not transparent
        colorPaneSystem.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (jScrollPanePlayers != null) {
            jScrollPanePlayers.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPanePlayers.getViewport().setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPanePlayers.setViewportBorder(null);
        }

    }

    public ChatPanelBasic getUserChatPanel() {
        return jScrollPaneTalk;
    }

    public void cleanUp() {
        TableUtil.saveColumnWidthAndOrderToPrefs(jTablePlayers, KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);
        jScrollPaneTalk.cleanUp();
    }

    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        jTablePlayers.getTableHeader().setFont(GUISizeHelper.tableFont);
        jTablePlayers.setFont(GUISizeHelper.tableFont);
        jTablePlayers.setRowHeight(GUISizeHelper.getTableRowHeight());
        jScrollPanePlayers.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPanePlayers.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        jScrollPaneSystem.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPaneSystem.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));

        jTabbedPaneText.setFont(GUISizeHelper.getTabFont());
        jSplitPane1.setDividerSize(GUISizeHelper.dividerBarSize);
    }

    public void setSplitDividerLocation(int location) {
        if (jSplitPane1 != null) {
            jSplitPane1.setDividerLocation(location);
        }
    }

    public int getSplitDividerLocation() {
        if (jSplitPane1 == null) {
            return 0;
        }
        return this.jSplitPane1.getDividerLocation();
    }

    class UserTableModel extends AbstractTableModel {

        private final String[] columnNames = new String[]{"Loc", "Players", "Constructed Rating", "Limited Rating", "Matches", "MQP", "Tourneys", "TQP", "Games", "Connection"};
        private UsersView[] players = new UsersView[0];

        public void loadData(Collection<RoomUsersView> roomUserInfoList) throws MageRemoteException {
            RoomUsersView roomUserInfo = roomUserInfoList.iterator().next();
            this.players = roomUserInfo.getUsersView().toArray(new UsersView[0]);
            JTableHeader th = jTablePlayers.getTableHeader();
            TableColumnModel tcm = th.getColumnModel();

            tcm.getColumn(jTablePlayers.convertColumnIndexToView(1)).setHeaderValue("Players (" + this.players.length + ')');
            tcm.getColumn(jTablePlayers.convertColumnIndexToView(8)).setHeaderValue(
                    "Games " + roomUserInfo.getNumberActiveGames()
                    + (roomUserInfo.getNumberActiveGames() != roomUserInfo.getNumberGameThreads() ? " (T:" + roomUserInfo.getNumberGameThreads() : " (")
                    + " limit: " + roomUserInfo.getNumberMaxGames() + ')');
            th.repaint();
            this.fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return players.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int arg0, int arg1) {
            switch (arg1) {
                case 0:
                    return players[arg0].getFlagName();
                case 1:
                    return players[arg0].getUserName();
                case 2:
                    return players[arg0].getConstructedRating();
                case 3:
                    return players[arg0].getLimitedRating();
                case 4:
                    return players[arg0].getMatchHistory();
                case 5:
                    return players[arg0].getMatchQuitRatio();
                case 6:
                    return players[arg0].getTourneyHistory();
                case 7:
                    return players[arg0].getTourneyQuitRatio();
                case 8:
                    return players[arg0].getInfoGames();
                case 9:
                    return players[arg0].getInfoPing();
            }
            return "";
        }

        public void initHeaderTooltips() {
            ColumnHeaderToolTips tips = new ColumnHeaderToolTips();
            for (int c = 0; c < jTablePlayers.getColumnCount(); c++) {
                String tooltipText = "";
                switch (c) {
                    case 0:
                        tooltipText = "<HTML><b>The flag the user has assigned to his profile</b>"
                                + "<br>You can assign the flag in the connect to server dialog window";
                        break;
                    case 1:
                        tooltipText = "<HTML><b>Name of the user</b>"
                                + "<br>(the number behind the header text is the number of currently connected users to the server)";
                        break;
                    case 2:
                        tooltipText = "<HTML><b>Constructed  player rating</b>";
                        break;
                    case 3:
                        tooltipText = "<HTML><b>Limited  player rating</b>";
                        break;
                    case 4:
                        tooltipText = "<HTML><b>Number of matches the user played so far</b>"
                                + "<br>Q = number of matches quit"
                                + "<br>I = number of matches lost because of idle timeout"
                                + "<br>T = number of matches lost because of match timeout";
                        break;
                    case 5:
                        tooltipText = "<HTML><b>Percent-Ratio of matches played related to matches quit</b>"
                                + "<br>this calculation does not include tournament matches";
                        break;
                    case 6:
                        tooltipText = "<HTML><b>Number of tournaments the user played so far</b>"
                                + "<br>D = number of tournaments left during draft phase"
                                + "<br>C = number of tournaments left during constructing phase"
                                + "<br>R = number of tournaments left during rounds";
                        break;
                    case 7:
                        tooltipText = "<HTML><b>Percent-Ratio of tournament matches played related to tournament matches quit</b>"
                                + "<br>this calculation does not include non tournament matches";
                        break;
                    case 8:
                        tooltipText = "<HTML><b>Current activities of the player</b>"
                                + "<BR>the header itself shows the number of currently active games"
                                + "<BR>T: = number of games threads "
                                + "<BR><i>(that can vary from active games because of sideboarding or crashed games)</i>"
                                + "<BR>limt: the maximum of games the server is configured to"
                                + "<BR><i>(if the number of started games exceed that limit, the games have to wait"
                                + "<BR>until active games end)</i>";
                        break;
                    case 9:
                        tooltipText = "<HTML><b>Latency of the user's connection to the server</b>";
                        break;
                }
                tips.setToolTip(c, tooltipText);
            }
            JTableHeader header = jTablePlayers.getTableHeader();
            header.addMouseMotionListener(tips);
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
                case 0:
                    return Icon.class;
                case 2:
                case 3:
                case 5:
                case 7:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPanePlayers = new javax.swing.JScrollPane();
        jTablePlayers = new javax.swing.JTable();
        jTabbedPaneText = new javax.swing.JTabbedPane();
        jScrollPaneTalk = new mage.client.chat.ChatPanelSeparated();
        jScrollPaneSystem = new javax.swing.JScrollPane();
        colorPaneSystem = new mage.client.components.ColorPane();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.25);

        jScrollPanePlayers.setBorder(null);

        jTablePlayers.setModel(this.userTableModel);
        jTablePlayers.setToolTipText("Connected players");
        jTablePlayers.setAutoscrolls(false);
        jTablePlayers.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTablePlayers.setOpaque(false);
        jTablePlayers.setRequestFocusEnabled(false);
        jTablePlayers.setRowSelectionAllowed(false);
        jTablePlayers.setShowHorizontalLines(false);
        jTablePlayers.setShowVerticalLines(false);
        jTablePlayers.setUpdateSelectionOnSort(false);
        jTablePlayers.setVerifyInputWhenFocusTarget(false);
        jScrollPanePlayers.setViewportView(jTablePlayers);
        jTablePlayers.getAccessibleContext().setAccessibleDescription("");

        jSplitPane1.setTopComponent(jScrollPanePlayers);

        jTabbedPaneText.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPaneText.addTab("Talk", jScrollPaneTalk);

        jScrollPaneSystem.setBorder(null);
        jScrollPaneSystem.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneSystem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPaneSystem.setFocusable(false);
        jScrollPaneSystem.setOpaque(false);

        colorPaneSystem.setEditable(false);
        colorPaneSystem.setBackground(new java.awt.Color(0, 0, 0));
        colorPaneSystem.setBorder(null);
        colorPaneSystem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        colorPaneSystem.setMargin(new java.awt.Insets(0, 0, 0, 0));
        colorPaneSystem.setOpaque(false);
        jScrollPaneSystem.setViewportView(colorPaneSystem);

        jTabbedPaneText.addTab("System", jScrollPaneSystem);

        jSplitPane1.setRightComponent(jTabbedPaneText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setRoomUserInfo(List<Collection<RoomUsersView>> view) {
        try {
            userTableModel.loadData(view.get(0));
        } catch (Exception ex) {
            this.players.clear();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.components.ColorPane colorPaneSystem;
    private javax.swing.JScrollPane jScrollPanePlayers;
    private javax.swing.JScrollPane jScrollPaneSystem;
    private mage.client.chat.ChatPanelSeparated jScrollPaneTalk;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPaneText;
    private javax.swing.JTable jTablePlayers;
    // End of variables declaration//GEN-END:variables

    static class ColumnHeaderToolTips extends MouseMotionAdapter {

        int curCol;
        final Map<Integer, String> tips = new HashMap<>();

        public void setToolTip(Integer mCol, String tooltip) {
            if (tooltip == null) {
                tips.remove(mCol);
            } else {
                tips.put(mCol, tooltip);
            }
        }

        @Override
        public void mouseMoved(MouseEvent evt) {
            JTableHeader header = (JTableHeader) evt.getSource();
            JTable table = header.getTable();
            TableColumnModel colModel = table.getColumnModel();
            int vColIndex = colModel.getColumnIndexAtX(evt.getX());
            TableColumn col = null;
            if (vColIndex >= 0) {
                col = colModel.getColumn(table.convertColumnIndexToModel(vColIndex));
            }
            if (table.convertColumnIndexToModel(vColIndex) != curCol) {
                if (col != null) {
                    MageFrame.getInstance().getBalloonTip().setAttachedComponent(header);
                    JLabel content = new JLabel(tips.get(table.convertColumnIndexToModel(vColIndex)));
                    content.setFont(GUISizeHelper.balloonTooltipFont);
                    MageFrame.getInstance().getBalloonTip().setContents(content);
                    ToolTipUtils.balloonToToolTip(MageFrame.getInstance().getBalloonTip(), 600, 10000);
                } else {
                    MageFrame.getInstance().getBalloonTip().setTextContents("");
                }
                curCol = table.convertColumnIndexToModel(vColIndex);
            }
        }
    }

}
