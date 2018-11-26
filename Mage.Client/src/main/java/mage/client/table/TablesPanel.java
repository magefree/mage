 /*
  * TablesPanel.java
  *
  * Created on 15-Dec-2009, 10:54:01 PM
  */
 package mage.client.table;

 import mage.cards.decks.importer.DeckImporterUtil;
 import mage.client.MageFrame;
 import mage.client.SessionHandler;
 import mage.client.chat.ChatPanelBasic;
 import mage.client.components.MageComponents;
 import mage.client.dialog.*;
 import mage.client.util.*;
 import mage.client.util.gui.GuiDisplayUtil;
 import mage.client.util.gui.TableUtil;
 import mage.constants.*;
 import mage.game.match.MatchOptions;
 import mage.players.PlayerType;
 import mage.remote.MageRemoteException;
 import mage.view.MatchView;
 import mage.view.RoomUsersView;
 import mage.view.TableView;
 import mage.view.UserRequestMessage;
 import org.apache.log4j.Logger;
 import org.mage.card.arcane.CardRendererUtils;
 import org.ocpsoft.prettytime.Duration;
 import org.ocpsoft.prettytime.PrettyTime;
 import org.ocpsoft.prettytime.units.JustNow;

 import javax.swing.*;
 import javax.swing.border.EmptyBorder;
 import javax.swing.table.AbstractTableModel;
 import javax.swing.table.DefaultTableCellRenderer;
 import javax.swing.table.TableCellRenderer;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.beans.PropertyVetoException;
 import java.io.File;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.*;
 import java.util.concurrent.CancellationException;
 import java.util.concurrent.ExecutionException;
 import java.util.concurrent.Executors;
 import java.util.concurrent.TimeUnit;

 import static mage.client.dialog.PreferencesDialog.*;

 /**
  * @author BetaSteward_at_googlemail.com
  */
 public class TablesPanel extends javax.swing.JPanel {

     private static final Logger LOGGER = Logger.getLogger(TablesPanel.class);
     private static final int[] DEFAULT_COLUMNS_WIDTH = {35, 150, 120, 180, 80, 120, 80, 60, 40, 40, 60};

     private final TableTableModel tableModel;
     private final MatchesTableModel matchesModel;
     private UUID roomId;
     private UpdateTablesTask updateTablesTask;
     private UpdatePlayersTask updatePlayersTask;
     private UpdateMatchesTask updateMatchesTask;
     private JoinTableDialog joinTableDialog;
     private NewTableDialog newTableDialog;
     private NewTournamentDialog newTournamentDialog;
     private final GameChooser gameChooser;
     private java.util.List<String> messages;
     private int currentMessage;
     private final MageTableRowSorter activeTablesSorter;
     private final MageTableRowSorter completedTablesSorter;

     private final ButtonColumn actionButton1;
     private final ButtonColumn actionButton2;

     final JToggleButton[] filterButtons;

     // time formater
     private PrettyTime timeFormater = new PrettyTime();

     // time ago renderer
     TableCellRenderer timeAgoCellRenderer = new DefaultTableCellRenderer() {
         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
             JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             Date d = (Date) value;
             label.setText(timeFormater.format(d));
             return label;
         }
     };

     // duration renderer
     TableCellRenderer durationCellRenderer = new DefaultTableCellRenderer() {
         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
             JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             Long ms = (Long) value;

             if (ms != 0) {
                 Duration dur = timeFormater.approximateDuration(new Date(ms));
                 label.setText((timeFormater.formatDuration(dur)));
             } else {
                 label.setText("");
             }
             return label;
         }
     };

     // datetime render
     TableCellRenderer datetimeCellRenderer = new DefaultTableCellRenderer() {
         DateFormat datetimeFormater = new SimpleDateFormat("HH:mm:ss");

         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
             JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             Date d = (Date) value;
             if (d != null) {
                 label.setText(datetimeFormater.format(d));
             } else {
                 label.setText("");
             }

             return label;
         }
     };

     // skill renderer
     TableCellRenderer skillCellRenderer = new DefaultTableCellRenderer() {

         // base panel to render
         private JPanel renderPanel = new JPanel();
         private ImageIcon skillIcon = new ImageIcon(this.getClass().getResource("/info/yellow_star_16.png"));

         @Override
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

             // get table text cell settings
             DefaultTableCellRenderer baseRenderer = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
             JLabel baseComp = (JLabel) baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             String skillCode = baseComp.getText();

             // apply settings to render panel from parent
             renderPanel.setOpaque(baseComp.isOpaque());
             renderPanel.setForeground(CardRendererUtils.copyColor(baseComp.getForeground()));
             renderPanel.setBackground(CardRendererUtils.copyColor(baseComp.getBackground()));
             renderPanel.setBorder(baseComp.getBorder());

             // create each skill symbol as child label
             renderPanel.removeAll();
             renderPanel.setLayout(new BoxLayout(renderPanel, BoxLayout.X_AXIS));
             for (char skillSymbol : skillCode.toCharArray()) {
                 JLabel symbolLabel = new JLabel();
                 symbolLabel.setBorder(new EmptyBorder(0, 3, 0, 0));
                 symbolLabel.setIcon(skillIcon);
                 renderPanel.add(symbolLabel);
             }

             return renderPanel;
         }
     };

     /**
      * Creates new form TablesPanel
      */
     public TablesPanel() {

         tableModel = new TableTableModel();
         matchesModel = new MatchesTableModel();
         gameChooser = new GameChooser();

         initComponents();
         //  tableModel.setSession(session);

         // formater
         timeFormater.setLocale(Locale.ENGLISH);
         JustNow jn = timeFormater.getUnit(JustNow.class);
         jn.setMaxQuantity(1000L * 30L); // 30 seconds gap (show "just now" from 0 to 30 secs)

         // 1. TABLE CURRENT
         tableTables.createDefaultColumnsFromModel();
         activeTablesSorter = new MageTableRowSorter(tableModel);
         tableTables.setRowSorter(activeTablesSorter);

         // time ago
         tableTables.getColumnModel().getColumn(TableTableModel.COLUMN_CREATED).setCellRenderer(timeAgoCellRenderer);
         // skill level
         tableTables.getColumnModel().getColumn(TableTableModel.COLUMN_SKILL).setCellRenderer(skillCellRenderer);

        /* date sorter (not need, default is good - see getColumnClass)
        activeTablesSorter.setComparator(TableTableModel.COLUMN_CREATED, new Comparator<Date>() {
            @Override
            public int compare(Date v1, Date v2) {
                return v1.compareTo(v2);
            }

        });*/
         // default sort by created date (last games from above)
         ArrayList list = new ArrayList();
         list.add(new RowSorter.SortKey(TableTableModel.COLUMN_CREATED, SortOrder.DESCENDING));
         activeTablesSorter.setSortKeys(list);

         TableUtil.setColumnWidthAndOrder(tableTables, DEFAULT_COLUMNS_WIDTH, KEY_TABLES_COLUMNS_WIDTH, KEY_TABLES_COLUMNS_ORDER);

         // 2. TABLE COMPLETED
         completedTablesSorter = new MageTableRowSorter(matchesModel);
         tableCompleted.setRowSorter(completedTablesSorter);

         // duration
         tableCompleted.getColumnModel().getColumn(MatchesTableModel.COLUMN_DURATION).setCellRenderer(durationCellRenderer);
         // start-end
         tableCompleted.getColumnModel().getColumn(MatchesTableModel.COLUMN_START).setCellRenderer(datetimeCellRenderer);
         tableCompleted.getColumnModel().getColumn(MatchesTableModel.COLUMN_END).setCellRenderer(datetimeCellRenderer);
         // default sort by ended date (last games from above)
         ArrayList list2 = new ArrayList();
         list2.add(new RowSorter.SortKey(MatchesTableModel.COLUMN_END, SortOrder.DESCENDING));
         completedTablesSorter.setSortKeys(list2);

         // 3. CHAT
         chatPanelMain.getUserChatPanel().useExtendedView(ChatPanelBasic.VIEW_MODE.NONE);
         chatPanelMain.getUserChatPanel().setBorder(null);
         chatPanelMain.getUserChatPanel().setChatType(ChatPanelBasic.ChatType.TABLES);

         // 4. BUTTONS
         filterButtons = new JToggleButton[]{btnStateWaiting, btnStateActive, btnStateFinished,
                 btnTypeMatch, btnTypeTourneyConstructed, btnTypeTourneyLimited,
                 btnFormatBlock, btnFormatStandard, btnFormatModern, btnFormatLegacy, btnFormatVintage, btnFormatCommander, btnFormatTinyLeader, btnFormatLimited, btnFormatOther,
                 btnSkillBeginner, btnSkillCasual, btnSkillSerious, btnRated, btnUnrated, btnOpen, btnPassword};

         JComponent[] components = new JComponent[]{chatPanelMain, jSplitPane1, jScrollPaneTablesActive, jScrollPaneTablesFinished, jPanelTop, jPanelTables};
         for (JComponent component : components) {
             component.setOpaque(false);
         }

         jScrollPaneTablesActive.getViewport().setBackground(new Color(255, 255, 255, 50));
         jScrollPaneTablesFinished.getViewport().setBackground(new Color(255, 255, 255, 50));

         restoreFilters();
         setGUISize();

         Action openTableAction;
         openTableAction = new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 int modelRow = Integer.valueOf(e.getActionCommand());
                 UUID tableId = (UUID) tableModel.getValueAt(modelRow, TableTableModel.ACTION_COLUMN + 3);
                 UUID gameId = (UUID) tableModel.getValueAt(modelRow, TableTableModel.ACTION_COLUMN + 2);
                 String action = (String) tableModel.getValueAt(modelRow, TableTableModel.ACTION_COLUMN);
                 String deckType = (String) tableModel.getValueAt(modelRow, TableTableModel.COLUMN_DECK_TYPE);
                 boolean isTournament = (Boolean) tableModel.getValueAt(modelRow, TableTableModel.ACTION_COLUMN + 1);
                 String owner = (String) tableModel.getValueAt(modelRow, TableTableModel.COLUMN_OWNER);
                 String pwdColumn = (String) tableModel.getValueAt(modelRow, TableTableModel.COLUMN_PASSWORD);
                 switch (action) {
                     case "Join":
                         if (owner.equals(SessionHandler.getUserName()) || owner.startsWith(SessionHandler.getUserName() + ',')) {
                             try {
                                 JDesktopPane desktopPane = (JDesktopPane) MageFrame.getUI().getComponent(MageComponents.DESKTOP_PANE);
                                 JInternalFrame[] windows = desktopPane.getAllFramesInLayer(javax.swing.JLayeredPane.DEFAULT_LAYER);
                                 for (JInternalFrame frame : windows) {
                                     if (frame.getTitle().equals("Waiting for players")) {
                                         frame.toFront();
                                         frame.setVisible(true);
                                         try {
                                             frame.setSelected(true);
                                         } catch (PropertyVetoException ve) {
                                             LOGGER.error(ve);
                                         }
                                     }

                                 }
                             } catch (InterruptedException ex) {
                                 LOGGER.error(ex);
                             }
                             return;
                         }
                         if (isTournament) {
                             LOGGER.info("Joining tournament " + tableId);
                             if (deckType.startsWith("Limited")) {
                                 if (TableTableModel.PASSWORD_VALUE_YES.equals(pwdColumn)) {
                                     joinTableDialog.showDialog(roomId, tableId, true, deckType.startsWith("Limited"));
                                 } else {
                                     SessionHandler.joinTournamentTable(roomId, tableId, SessionHandler.getUserName(), PlayerType.HUMAN, 1, null, "");
                                 }
                             } else {
                                 joinTableDialog.showDialog(roomId, tableId, true, deckType.startsWith("Limited"));
                             }
                         } else {
                             LOGGER.info("Joining table " + tableId);
                             joinTableDialog.showDialog(roomId, tableId, false, false);
                         }
                         break;
                     case "Remove":
                         UserRequestMessage message = new UserRequestMessage("Removing table", "Are you sure you want to remove table?");
                         message.setButton1("No", null);
                         message.setButton2("Yes", PlayerAction.CLIENT_REMOVE_TABLE);
                         MageFrame.getInstance().showUserRequestDialog(message);
                         break;
                     case "Show":
                         if (isTournament) {
                             LOGGER.info("Showing tournament table " + tableId);
                             SessionHandler.watchTable(roomId, tableId);
                         }
                         break;
                     case "Watch":
                         if (!isTournament) {
                             LOGGER.info("Watching table " + tableId);
                             SessionHandler.watchTable(roomId, tableId);
                         }
                         break;
                     case "Replay":
                         LOGGER.info("Replaying game " + gameId);
                         SessionHandler.replayGame(gameId);
                         break;
                 }
             }
         };

         Action closedTableAction;
         closedTableAction = new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 int modelRow = Integer.valueOf(e.getActionCommand());
                 String action = (String) matchesModel.getValueAt(modelRow, MatchesTableModel.COLUMN_ACTION);
                 switch (action) {
                     case "Replay":
                         java.util.List<UUID> gameList = matchesModel.getListofGames(modelRow);
                         if (gameList != null && !gameList.isEmpty()) {
                             if (gameList.size() == 1) {
                                 SessionHandler.replayGame(gameList.get(0));
                             } else {
                                 gameChooser.show(gameList, MageFrame.getDesktop().getMousePosition());
                             }
                         }
                         // MageFrame.getDesktop().showTournament(tournamentId);
                         break;
                     case "Show":
                         if (matchesModel.isTournament(modelRow)) {
                             LOGGER.info("Showing tournament table " + matchesModel.getTableId(modelRow));
                             SessionHandler.watchTable(roomId, matchesModel.getTableId(modelRow));
                         }
                         break;
                 }
             }
         };

         // !!!! adds action buttons to the table panel (don't delete this)
         actionButton1 = new ButtonColumn(tableTables, openTableAction, tableTables.convertColumnIndexToView(TableTableModel.ACTION_COLUMN));
         actionButton2 = new ButtonColumn(tableCompleted, closedTableAction, tableCompleted.convertColumnIndexToView(MatchesTableModel.COLUMN_ACTION));
         // !!!!
         addTableDoubleClickListener(tableTables, openTableAction);
         addTableDoubleClickListener(tableCompleted, closedTableAction);
     }

     private void addTableDoubleClickListener(JTable table, Action action) {
         table.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 if (e.getClickCount() == 2) {
                     int selRow = table.getSelectedRow();
                     if (selRow != -1) {
                         int dataRow = table.convertRowIndexToModel(selRow);
                         if (dataRow != -1) {
                             action.actionPerformed(new ActionEvent(e.getSource(), e.getID(), "" + dataRow));
                         }
                     }
                 }
             }
         });
     }

     public void cleanUp() {
         saveGuiSettings();
         chatPanelMain.cleanUp();
     }

     public void changeGUISize() {
         chatPanelMain.changeGUISize();
         actionButton1.changeGUISize();
         actionButton2.changeGUISize();
         setGUISize();
     }

     private void setGUISize() {
         tableTables.getTableHeader().setFont(GUISizeHelper.tableFont);
         tableTables.setFont(GUISizeHelper.tableFont);
         tableTables.setRowHeight(GUISizeHelper.getTableRowHeight());

         tableCompleted.getTableHeader().setFont(GUISizeHelper.tableFont);
         tableCompleted.setFont(GUISizeHelper.tableFont);
         tableCompleted.setRowHeight(GUISizeHelper.getTableRowHeight());

         jSplitPane1.setDividerSize(GUISizeHelper.dividerBarSize);
         jSplitPaneTables.setDividerSize(GUISizeHelper.dividerBarSize);
         jScrollPaneTablesActive.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
         jScrollPaneTablesActive.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));

         ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/buttons/state_waiting.png"));
         Image img = icon.getImage();
         Image newimg = img.getScaledInstance(GUISizeHelper.menuFont.getSize(), GUISizeHelper.menuFont.getSize(), java.awt.Image.SCALE_SMOOTH);
         btnStateWaiting.setIcon(new ImageIcon(newimg));

         icon = new javax.swing.ImageIcon(getClass().getResource("/buttons/state_active.png"));
         img = icon.getImage();
         newimg = img.getScaledInstance(GUISizeHelper.menuFont.getSize(), GUISizeHelper.menuFont.getSize(), java.awt.Image.SCALE_SMOOTH);
         btnStateActive.setIcon(new ImageIcon(newimg));

         icon = new javax.swing.ImageIcon(getClass().getResource("/buttons/state_finished.png"));
         img = icon.getImage();
         newimg = img.getScaledInstance(GUISizeHelper.menuFont.getSize(), GUISizeHelper.menuFont.getSize(), java.awt.Image.SCALE_SMOOTH);
         btnStateFinished.setIcon(new ImageIcon(newimg));

         int iconSize = 48 + GUISizeHelper.menuFont.getSize() * 2 - 15;
         icon = new javax.swing.ImageIcon(getClass().getResource("/buttons/match_new.png"));
         img = icon.getImage();
         newimg = img.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
         btnNewTable.setIcon(new ImageIcon(newimg));

         icon = new javax.swing.ImageIcon(getClass().getResource("/buttons/tourney_new.png"));
         img = icon.getImage();
         newimg = img.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
         btnNewTournament.setIcon(new ImageIcon(newimg));

         for (JToggleButton component : filterButtons) {
             component.setFont(GUISizeHelper.menuFont);
         }
         Dimension newDimension = new Dimension((int) jPanelBottom.getPreferredSize().getWidth(), GUISizeHelper.menuFont.getSize() + 28);
         jPanelBottom.setMinimumSize(newDimension);
         jPanelBottom.setPreferredSize(newDimension);
         jButtonFooterNext.setFont(GUISizeHelper.menuFont);
         jLabelFooterLabel.setFont(new Font(GUISizeHelper.menuFont.getName(), Font.BOLD, GUISizeHelper.menuFont.getSize()));
         jLabelFooterText.setFont(GUISizeHelper.menuFont);
     }

     private void saveDividerLocations() {
         // save divider locations and divider saveDividerLocations
         GuiDisplayUtil.saveCurrentBoundsToPrefs();
         GuiDisplayUtil.saveDividerLocationToPrefs(KEY_TABLES_DIVIDER_LOCATION_1, this.jSplitPane1.getDividerLocation());
         GuiDisplayUtil.saveDividerLocationToPrefs(KEY_TABLES_DIVIDER_LOCATION_2, this.jSplitPaneTables.getDividerLocation());
         GuiDisplayUtil.saveDividerLocationToPrefs(KEY_TABLES_DIVIDER_LOCATION_3, chatPanelMain.getSplitDividerLocation());
     }

     private void restoreFilters() {
         TableUtil.setActiveFilters(KEY_TABLES_FILTER_SETTINGS, filterButtons);
         setTableFilter();
     }

     private void saveGuiSettings() {
         TableUtil.saveActiveFiltersToPrefs(KEY_TABLES_FILTER_SETTINGS, filterButtons);
         TableUtil.saveColumnWidthAndOrderToPrefs(tableTables, KEY_TABLES_COLUMNS_WIDTH, KEY_TABLES_COLUMNS_ORDER);
     }

     private void restoreDividers() {
         Rectangle currentBounds = MageFrame.getDesktop().getBounds();
         if (currentBounds != null) {
             String firstDivider = PreferencesDialog.getCachedValue(KEY_TABLES_DIVIDER_LOCATION_1, null);
             String tableDivider = PreferencesDialog.getCachedValue(KEY_TABLES_DIVIDER_LOCATION_2, null);
             String chatDivider = PreferencesDialog.getCachedValue(KEY_TABLES_DIVIDER_LOCATION_3, null);
             GuiDisplayUtil.restoreDividerLocations(currentBounds, firstDivider, jSplitPane1);
             GuiDisplayUtil.restoreDividerLocations(currentBounds, tableDivider, jSplitPaneTables);
             GuiDisplayUtil.restoreDividerLocations(currentBounds, chatDivider, chatPanelMain);
         }
     }

     public Map<String, JComponent> getUIComponents() {
         Map<String, JComponent> components = new HashMap<>();

         components.put("jScrollPane1", jScrollPaneTablesActive);
         components.put("jScrollPane1ViewPort", jScrollPaneTablesActive.getViewport());
         components.put("jPanel1", jPanelTop);
         components.put("tablesPanel", this);

         return components;
     }

     public void updateTables(Collection<TableView> tables) {
         try {
             tableModel.loadData(tables);
             this.tableTables.repaint();
         } catch (MageRemoteException ex) {
             hideTables();
         }
     }

     public void updateMatches(Collection<MatchView> matches) {
         try {
             matchesModel.loadData(matches);
             this.tableCompleted.repaint();
         } catch (MageRemoteException ex) {
             hideTables();
         }
     }

     public void startTasks() {
         if (SessionHandler.getSession() != null) {
             if (updateTablesTask == null || updateTablesTask.isDone()) {
                 updateTablesTask = new UpdateTablesTask(roomId, this);
                 updateTablesTask.execute();
             }
             if (updatePlayersTask == null || updatePlayersTask.isDone()) {
                 updatePlayersTask = new UpdatePlayersTask(roomId, this.chatPanelMain);
                 updatePlayersTask.execute();
             }
             if (this.btnStateFinished.isSelected()) {
                 if (updateMatchesTask == null || updateMatchesTask.isDone()) {
                     updateMatchesTask = new UpdateMatchesTask(roomId, this);
                     updateMatchesTask.execute();
                 }
             } else if (updateMatchesTask != null) {
                 updateMatchesTask.cancel(true);
             }
         }
     }

     public void stopTasks() {
         if (updateTablesTask != null) {
             updateTablesTask.cancel(true);
         }
         if (updatePlayersTask != null) {
             updatePlayersTask.cancel(true);
         }
         if (updateMatchesTask != null) {
             updateMatchesTask.cancel(true);
         }
     }

     public void showTables(UUID roomId) {
         this.roomId = roomId;
         UUID chatRoomId = null;
         if (SessionHandler.getSession() != null) {
             btnQuickStart.setVisible(SessionHandler.isTestMode());
             gameChooser.init();
             chatRoomId = SessionHandler.getRoomChatId(roomId).orElse(null);
         }
         if (newTableDialog == null) {
             newTableDialog = new NewTableDialog();
             MageFrame.getDesktop().add(newTableDialog, JLayeredPane.MODAL_LAYER);
         }
         if (newTournamentDialog == null) {
             newTournamentDialog = new NewTournamentDialog();
             MageFrame.getDesktop().add(newTournamentDialog, JLayeredPane.MODAL_LAYER);
         }
         if (joinTableDialog == null) {
             joinTableDialog = new JoinTableDialog();
             MageFrame.getDesktop().add(joinTableDialog, JLayeredPane.MODAL_LAYER);
         }
         if (chatRoomId != null) {
             this.chatPanelMain.getUserChatPanel().connect(chatRoomId);
             startTasks();
             this.setVisible(true);
             this.repaint();
         } else {
             hideTables();
         }
         //tableModel.setSession(session);

         reloadMessages();

         MageFrame.getUI().addButton(MageComponents.NEW_GAME_BUTTON, btnNewTable);

         // divider locations have to be set with delay else values set are overwritten with system defaults
         Executors.newSingleThreadScheduledExecutor().schedule(() -> restoreDividers(), 300, TimeUnit.MILLISECONDS);

     }

     protected void reloadMessages() {
         // reload server messages
         java.util.List<String> serverMessages = SessionHandler.getServerMessages();
         synchronized (this) {
             this.messages = serverMessages;
             this.currentMessage = 0;
         }
         if (serverMessages.isEmpty()) {
             this.jPanelBottom.setVisible(false);
         } else {
             this.jPanelBottom.setVisible(true);
             URLHandler.RemoveMouseAdapter(jLabelFooterText);
             URLHandler.handleMessage(serverMessages.get(0), this.jLabelFooterText);
             this.jButtonFooterNext.setVisible(serverMessages.size() > 1);
         }
     }

     public void hideTables() {
         this.saveDividerLocations();
         for (Component component : MageFrame.getDesktop().getComponents()) {
             if (component instanceof TableWaitingDialog) {
                 ((TableWaitingDialog) component).closeDialog();
             }
         }
         stopTasks();
         this.chatPanelMain.getUserChatPanel().disconnect();

         Component c = this.getParent();
         while (c != null && !(c instanceof TablesPane)) {
             c = c.getParent();
         }
         if (c != null) {
             ((TablesPane) c).hideFrame();
         }
     }

     public ChatPanelBasic getChatPanel() {
         return chatPanelMain.getUserChatPanel();
     }

     public void setTableFilter() {
         // state
         java.util.List<RowFilter<Object, Object>> stateFilterList = new ArrayList<>();
         if (btnStateWaiting.isSelected()) {
             stateFilterList.add(RowFilter.regexFilter("Waiting", TableTableModel.COLUMN_STATUS));
         }
         if (btnStateActive.isSelected()) {
             stateFilterList.add(RowFilter.regexFilter("Dueling|Constructing|Drafting|Sideboard", TableTableModel.COLUMN_STATUS));
         }

         // type
         java.util.List<RowFilter<Object, Object>> typeFilterList = new ArrayList<>();
         if (btnTypeMatch.isSelected()) {
             typeFilterList.add(RowFilter.regexFilter("Two|Commander|Free|Tiny|Momir", TableTableModel.COLUMN_GAME_TYPE));
         }
         if (btnTypeTourneyConstructed.isSelected()) {
             typeFilterList.add(RowFilter.regexFilter("Constructed", TableTableModel.COLUMN_GAME_TYPE));
         }
         if (btnTypeTourneyLimited.isSelected()) {
             typeFilterList.add(RowFilter.regexFilter("Booster|Sealed", TableTableModel.COLUMN_GAME_TYPE));
         }

         // format
         java.util.List<RowFilter<Object, Object>> formatFilterList = new ArrayList<>();
         if (btnFormatBlock.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Constructed.*Block", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatStandard.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Constructed - Standard", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatModern.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Constructed - Modern", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatLegacy.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Constructed - Legacy", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatVintage.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Constructed - Vintage", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatCommander.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Commander|^Duel Commander|^Penny Dreadful Commander|^Freeform Commander|^MTGO 1v1 Commander|^Duel Brawl|^Brawl", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatTinyLeader.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Tiny", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatLimited.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Limited", TableTableModel.COLUMN_DECK_TYPE));
         }
         if (btnFormatOther.isSelected()) {
             formatFilterList.add(RowFilter.regexFilter("^Momir Basic|^Constructed - Pauper|^Constructed - Frontier|^Constructed - Extended|^Constructed - Eternal|^Constructed - Historical|^Constructed - Super|^Constructed - Freeform|^Australian Highlander|^Canadian Highlander|^Constructed - Old", TableTableModel.COLUMN_DECK_TYPE));
         }

         // skill
         java.util.List<RowFilter<Object, Object>> skillFilterList = new ArrayList<>();
         if (btnSkillBeginner.isSelected()) {
             skillFilterList.add(RowFilter.regexFilter(this.tableModel.getSkillLevelAsCode(SkillLevel.BEGINNER, true), TableTableModel.COLUMN_SKILL));
         }
         if (btnSkillCasual.isSelected()) {
             skillFilterList.add(RowFilter.regexFilter(this.tableModel.getSkillLevelAsCode(SkillLevel.CASUAL, true), TableTableModel.COLUMN_SKILL));
         }
         if (btnSkillSerious.isSelected()) {
             skillFilterList.add(RowFilter.regexFilter(this.tableModel.getSkillLevelAsCode(SkillLevel.SERIOUS, true), TableTableModel.COLUMN_SKILL));
         }

         String ratedMark = TableTableModel.RATED_VALUE_YES;
         java.util.List<RowFilter<Object, Object>> ratingFilterList = new ArrayList<>();
         if (btnRated.isSelected()) {
             // yes word
             ratingFilterList.add(RowFilter.regexFilter("^" + ratedMark, TableTableModel.COLUMN_RATING));
         }
         if (btnUnrated.isSelected()) {
             // not yes word, see https://stackoverflow.com/a/406408/1276632
             ratingFilterList.add(RowFilter.regexFilter("^((?!" + ratedMark + ").)*$", TableTableModel.COLUMN_RATING));
         }

         // Password
         String passwordMark = TableTableModel.PASSWORD_VALUE_YES;
         java.util.List<RowFilter<Object, Object>> passwordFilterList = new ArrayList<>();
         if (btnPassword.isSelected()) {
             // yes
             passwordFilterList.add(RowFilter.regexFilter("^" + passwordMark, TableTableModel.COLUMN_PASSWORD));
         }
         if (btnOpen.isSelected()) {
             // no
             passwordFilterList.add(RowFilter.regexFilter("^((?!" + passwordMark + ").)*$", TableTableModel.COLUMN_PASSWORD));
         }

         // Hide games of ignored players
         java.util.List<RowFilter<Object, Object>> ignoreListFilterList = new ArrayList<>();
         String serverAddress = SessionHandler.getSession().getServerHostname().orElseGet(() -> "");
         final Set<String> ignoreListCopy = IgnoreList.ignoreList(serverAddress);
         if (!ignoreListCopy.isEmpty()) {
             ignoreListFilterList.add(new RowFilter<Object, Object>() {
                 @Override
                 public boolean include(Entry<? extends Object, ? extends Object> entry) {
                     final String owner = entry.getStringValue(TableTableModel.COLUMN_OWNER);
                     return !ignoreListCopy.contains(owner);
                 }
             });
         }

         if (stateFilterList.isEmpty() || typeFilterList.isEmpty() || formatFilterList.isEmpty()
                 || skillFilterList.isEmpty() || ratingFilterList.isEmpty()
                 || passwordFilterList.isEmpty()) { // no selection
             activeTablesSorter.setRowFilter(RowFilter.regexFilter("Nothing", TableTableModel.COLUMN_SKILL));
         } else {
             java.util.List<RowFilter<Object, Object>> filterList = new ArrayList<>();

             if (stateFilterList.size() > 1) {
                 filterList.add(RowFilter.orFilter(stateFilterList));
             } else if (stateFilterList.size() == 1) {
                 filterList.addAll(stateFilterList);
             }

             if (typeFilterList.size() > 1) {
                 filterList.add(RowFilter.orFilter(typeFilterList));
             } else if (typeFilterList.size() == 1) {
                 filterList.addAll(typeFilterList);
             }

             if (formatFilterList.size() > 1) {
                 filterList.add(RowFilter.orFilter(formatFilterList));
             } else if (formatFilterList.size() == 1) {
                 filterList.addAll(formatFilterList);
             }

             if (skillFilterList.size() > 1) {
                 filterList.add(RowFilter.orFilter(skillFilterList));
             } else if (skillFilterList.size() == 1) {
                 filterList.addAll(skillFilterList);
             }

             if (ratingFilterList.size() > 1) {
                 filterList.add(RowFilter.orFilter(ratingFilterList));
             } else if (ratingFilterList.size() == 1) {
                 filterList.addAll(ratingFilterList);
             }

             if (passwordFilterList.size() > 1) {
                 filterList.add(RowFilter.orFilter(passwordFilterList));
             } else if (passwordFilterList.size() == 1) {
                 filterList.addAll(passwordFilterList);
             }

             if (ignoreListFilterList.size() > 1) {
                 filterList.add(RowFilter.orFilter(ignoreListFilterList));
             } else if (ignoreListFilterList.size() == 1) {
                 filterList.addAll(ignoreListFilterList);
             }

             if (filterList.size() == 1) {
                 activeTablesSorter.setRowFilter(filterList.get(0));
             } else {
                 activeTablesSorter.setRowFilter(RowFilter.andFilter(filterList));
             }
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
         java.awt.GridBagConstraints gridBagConstraints;

         jPanelTop = new javax.swing.JPanel();
         btnNewTable = new javax.swing.JButton();
         btnNewTournament = new javax.swing.JButton();
         filterBar1 = new javax.swing.JToolBar();
         btnStateWaiting = new javax.swing.JToggleButton();
         btnStateActive = new javax.swing.JToggleButton();
         btnStateFinished = new javax.swing.JToggleButton();
         jSeparator1 = new javax.swing.JToolBar.Separator();
         btnTypeMatch = new javax.swing.JToggleButton();
         btnTypeTourneyConstructed = new javax.swing.JToggleButton();
         btnTypeTourneyLimited = new javax.swing.JToggleButton();
         jSeparator4 = new javax.swing.JToolBar.Separator();
         btnSkillBeginner = new javax.swing.JToggleButton();
         btnSkillCasual = new javax.swing.JToggleButton();
         btnSkillSerious = new javax.swing.JToggleButton();
         jSeparator5 = new javax.swing.JToolBar.Separator();
         btnRated = new javax.swing.JToggleButton();
         btnUnrated = new javax.swing.JToggleButton();
         filterBar2 = new javax.swing.JToolBar();
         btnFormatBlock = new javax.swing.JToggleButton();
         btnFormatStandard = new javax.swing.JToggleButton();
         btnFormatModern = new javax.swing.JToggleButton();
         btnFormatLegacy = new javax.swing.JToggleButton();
         btnFormatVintage = new javax.swing.JToggleButton();
         jSeparator3 = new javax.swing.JToolBar.Separator();
         btnFormatCommander = new javax.swing.JToggleButton();
         btnFormatTinyLeader = new javax.swing.JToggleButton();
         jSeparator2 = new javax.swing.JToolBar.Separator();
         btnFormatLimited = new javax.swing.JToggleButton();
         btnFormatOther = new javax.swing.JToggleButton();
         jSeparator5 = new javax.swing.JToolBar.Separator();
         btnOpen = new javax.swing.JToggleButton();
         btnPassword = new javax.swing.JToggleButton();
         btnQuickStart = new javax.swing.JButton();
         jSplitPane1 = new javax.swing.JSplitPane();
         jPanelTables = new javax.swing.JPanel();
         jSplitPaneTables = new javax.swing.JSplitPane();
         jScrollPaneTablesActive = new javax.swing.JScrollPane();
         tableTables = new javax.swing.JTable();
         jScrollPaneTablesFinished = new javax.swing.JScrollPane();
         tableCompleted = new javax.swing.JTable();
         chatPanelMain = new mage.client.table.PlayersChatPanel();
         jPanelBottom = new javax.swing.JPanel();
         jButtonFooterNext = new javax.swing.JButton();
         jLabelFooterLabel = new javax.swing.JLabel();
         jLabelFooterText = new javax.swing.JLabel();

         setLayout(new java.awt.GridBagLayout());

         jPanelTop.setBackground(java.awt.Color.white);
         jPanelTop.setOpaque(false);

         btnNewTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/match_new.png"))); // NOI18N
         btnNewTable.setToolTipText("Creates a new match table.");
         btnNewTable.setMargin(new java.awt.Insets(2, 2, 2, 2));
         btnNewTable.addActionListener(evt -> btnNewTableActionPerformed(evt));

         btnNewTournament.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/tourney_new.png"))); // NOI18N
         btnNewTournament.setToolTipText("Creates a new tourney table.");
         btnNewTournament.setMargin(new java.awt.Insets(2, 2, 2, 2));
         btnNewTournament.addActionListener(evt -> btnNewTournamentActionPerformed(evt));

         filterBar1.setFloatable(false);
         filterBar1.setForeground(new java.awt.Color(102, 102, 255));
         filterBar1.setFocusable(false);
         filterBar1.setOpaque(false);

         btnStateWaiting.setSelected(true);
         btnStateWaiting.setToolTipText("Shows all tables waiting for players.");
         btnStateWaiting.setActionCommand("stateWait");
         btnStateWaiting.setFocusPainted(false);
         btnStateWaiting.setFocusable(false);
         btnStateWaiting.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnStateWaiting.setRequestFocusEnabled(false);
         btnStateWaiting.setVerifyInputWhenFocusTarget(false);
         btnStateWaiting.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnStateWaiting.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnStateWaiting);

         btnStateActive.setSelected(true);
         btnStateActive.setToolTipText("Shows all tables with active matches.");
         btnStateActive.setActionCommand("stateActive");
         btnStateActive.setFocusPainted(false);
         btnStateActive.setFocusable(false);
         btnStateActive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnStateActive.setRequestFocusEnabled(false);
         btnStateActive.setVerifyInputWhenFocusTarget(false);
         btnStateActive.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnStateActive.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnStateActive);

         btnStateFinished.setSelected(true);
         btnStateFinished.setToolTipText("<HTML>Toggles the visibility of the table of completed <br>matches and tournaments in the lower area.\n<br>Showing the last 50 finished matches.");
         btnStateFinished.setActionCommand("stateFinished");
         btnStateFinished.setFocusPainted(false);
         btnStateFinished.setFocusable(false);
         btnStateFinished.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnStateFinished.setRequestFocusEnabled(false);
         btnStateFinished.setVerifyInputWhenFocusTarget(false);
         btnStateFinished.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnStateFinished.addActionListener(evt -> btnStateFinishedActionPerformed(evt));
         filterBar1.add(btnStateFinished);
         filterBar1.add(jSeparator1);

         btnTypeMatch.setSelected(true);
         btnTypeMatch.setText("Matches");
         btnTypeMatch.setToolTipText("Shows all non tournament tables.");
         btnTypeMatch.setActionCommand("typeMatch");
         btnTypeMatch.setFocusPainted(false);
         btnTypeMatch.setFocusable(false);
         btnTypeMatch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnTypeMatch.setRequestFocusEnabled(false);
         btnTypeMatch.setVerifyInputWhenFocusTarget(false);
         btnTypeMatch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnTypeMatch.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnTypeMatch);

         btnTypeTourneyConstructed.setSelected(true);
         btnTypeTourneyConstructed.setText("Constructed tourn.");
         btnTypeTourneyConstructed.setToolTipText("Shows all constructed tournament tables.");
         btnTypeTourneyConstructed.setActionCommand("typeTourneyConstructed");
         btnTypeTourneyConstructed.setFocusPainted(false);
         btnTypeTourneyConstructed.setFocusable(false);
         btnTypeTourneyConstructed.setRequestFocusEnabled(false);
         btnTypeTourneyConstructed.setVerifyInputWhenFocusTarget(false);
         btnTypeTourneyConstructed.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnTypeTourneyConstructed);

         btnTypeTourneyLimited.setSelected(true);
         btnTypeTourneyLimited.setText("Limited tourn.");
         btnTypeTourneyLimited.setToolTipText("Shows all limited tournament tables.");
         btnTypeTourneyLimited.setActionCommand("typeTourneyLimited");
         btnTypeTourneyLimited.setFocusPainted(false);
         btnTypeTourneyLimited.setFocusable(false);
         btnTypeTourneyLimited.setMaximumSize(new java.awt.Dimension(72, 20));
         btnTypeTourneyLimited.setRequestFocusEnabled(false);
         btnTypeTourneyLimited.setVerifyInputWhenFocusTarget(false);
         btnTypeTourneyLimited.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnTypeTourneyLimited);
         filterBar1.add(jSeparator4);

         btnSkillBeginner.setSelected(true);
         btnSkillBeginner.setText("Beginner");
         btnSkillBeginner.setToolTipText("Shows all tables with skill level beginner.");
         btnSkillBeginner.setActionCommand("typeMatch");
         btnSkillBeginner.setFocusPainted(false);
         btnSkillBeginner.setFocusable(false);
         btnSkillBeginner.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnSkillBeginner.setRequestFocusEnabled(false);
         btnSkillBeginner.setVerifyInputWhenFocusTarget(false);
         btnSkillBeginner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnSkillBeginner.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnSkillBeginner);

         btnSkillCasual.setSelected(true);
         btnSkillCasual.setText("Casual");
         btnSkillCasual.setToolTipText("Shows all tables with skill level casual.");
         btnSkillCasual.setActionCommand("typeMatch");
         btnSkillCasual.setFocusPainted(false);
         btnSkillCasual.setFocusable(false);
         btnSkillCasual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnSkillCasual.setRequestFocusEnabled(false);
         btnSkillCasual.setVerifyInputWhenFocusTarget(false);
         btnSkillCasual.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnSkillCasual.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnSkillCasual);

         btnSkillSerious.setSelected(true);
         btnSkillSerious.setText("Serious");
         btnSkillSerious.setToolTipText("Shows all tables with skill level serious.");
         btnSkillSerious.setActionCommand("typeMatch");
         btnSkillSerious.setFocusPainted(false);
         btnSkillSerious.setFocusable(false);
         btnSkillSerious.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnSkillSerious.setRequestFocusEnabled(false);
         btnSkillSerious.setVerifyInputWhenFocusTarget(false);
         btnSkillSerious.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnSkillSerious.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnSkillSerious);

         filterBar1.add(jSeparator4);

         btnRated.setSelected(true);
         btnRated.setText("Rated");
         btnRated.setToolTipText("Shows all rated tables.");
         btnRated.setFocusPainted(false);
         btnRated.setFocusable(false);
         btnRated.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnRated.setRequestFocusEnabled(false);
         btnRated.setVerifyInputWhenFocusTarget(false);
         btnRated.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnRated.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnRated);

         btnUnrated.setSelected(true);
         btnUnrated.setText("Unrated");
         btnUnrated.setToolTipText("Shows all unrated tables.");
         btnUnrated.setFocusPainted(false);
         btnUnrated.setFocusable(false);
         btnUnrated.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnUnrated.setRequestFocusEnabled(false);
         btnUnrated.setVerifyInputWhenFocusTarget(false);
         btnUnrated.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnUnrated.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar1.add(btnUnrated);

         // second filter line
         filterBar2.setFloatable(false);
         filterBar2.setFocusable(false);
         filterBar2.setOpaque(false);

         btnFormatBlock.setSelected(true);
         btnFormatBlock.setText("Block");
         btnFormatBlock.setToolTipText("Block constructed formats.");
         btnFormatBlock.setFocusPainted(false);
         btnFormatBlock.setFocusable(false);
         btnFormatBlock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnFormatBlock.setRequestFocusEnabled(false);
         btnFormatBlock.setVerifyInputWhenFocusTarget(false);
         btnFormatBlock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnFormatBlock.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatBlock);

         btnFormatStandard.setSelected(true);
         btnFormatStandard.setText("Standard");
         btnFormatStandard.setToolTipText("Standard format.");
         btnFormatStandard.setFocusPainted(false);
         btnFormatStandard.setFocusable(false);
         btnFormatStandard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnFormatStandard.setRequestFocusEnabled(false);
         btnFormatStandard.setVerifyInputWhenFocusTarget(false);
         btnFormatStandard.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnFormatStandard.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatStandard);

         btnFormatModern.setSelected(true);
         btnFormatModern.setText("Modern");
         btnFormatModern.setToolTipText("Modern format.");
         btnFormatModern.setFocusPainted(false);
         btnFormatModern.setFocusable(false);
         btnFormatModern.setRequestFocusEnabled(false);
         btnFormatModern.setVerifyInputWhenFocusTarget(false);
         btnFormatModern.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatModern);

         btnFormatLegacy.setSelected(true);
         btnFormatLegacy.setText("Legacy");
         btnFormatLegacy.setToolTipText("Legacy format.");
         btnFormatLegacy.setFocusPainted(false);
         btnFormatLegacy.setFocusable(false);
         btnFormatLegacy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnFormatLegacy.setRequestFocusEnabled(false);
         btnFormatLegacy.setVerifyInputWhenFocusTarget(false);
         btnFormatLegacy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnFormatLegacy.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatLegacy);

         btnFormatVintage.setSelected(true);
         btnFormatVintage.setText("Vintage");
         btnFormatVintage.setToolTipText("Vintage format.");
         btnFormatVintage.setFocusPainted(false);
         btnFormatVintage.setFocusable(false);
         btnFormatVintage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnFormatVintage.setRequestFocusEnabled(false);
         btnFormatVintage.setVerifyInputWhenFocusTarget(false);
         btnFormatVintage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnFormatVintage.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatVintage);
         filterBar2.add(jSeparator3);

         btnFormatCommander.setSelected(true);
         btnFormatCommander.setText("Commander");
         btnFormatCommander.setToolTipText("Commander format.");
         btnFormatCommander.setFocusPainted(false);
         btnFormatCommander.setFocusable(false);
         btnFormatCommander.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnFormatCommander.setRequestFocusEnabled(false);
         btnFormatCommander.setVerifyInputWhenFocusTarget(false);
         btnFormatCommander.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnFormatCommander.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatCommander);

         btnFormatTinyLeader.setSelected(true);
         btnFormatTinyLeader.setText("Tiny Leader");
         btnFormatTinyLeader.setToolTipText("Tiny Leader format.");
         btnFormatTinyLeader.setFocusPainted(false);
         btnFormatTinyLeader.setFocusable(false);
         btnFormatTinyLeader.setRequestFocusEnabled(false);
         btnFormatTinyLeader.setVerifyInputWhenFocusTarget(false);
         btnFormatTinyLeader.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatTinyLeader);
         filterBar2.add(jSeparator2);

         btnFormatLimited.setSelected(true);
         btnFormatLimited.setText("Limited");
         btnFormatLimited.setToolTipText("Limited format.");
         btnFormatLimited.setFocusPainted(false);
         btnFormatLimited.setFocusable(false);
         btnFormatLimited.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnFormatLimited.setRequestFocusEnabled(false);
         btnFormatLimited.setVerifyInputWhenFocusTarget(false);
         btnFormatLimited.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnFormatLimited.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatLimited);

         btnFormatOther.setSelected(true);
         btnFormatOther.setText("Other");
         btnFormatOther.setToolTipText("Other formats (Freeform, Pauper, Extended, etc.)");
         btnFormatOther.setFocusPainted(false);
         btnFormatOther.setFocusable(false);
         btnFormatOther.setRequestFocusEnabled(false);
         btnFormatOther.setVerifyInputWhenFocusTarget(false);
         btnFormatOther.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnFormatOther);
         filterBar2.add(jSeparator5);

         btnOpen.setSelected(true);
         btnOpen.setText("Open");
         btnOpen.setToolTipText("Show open games");
         btnOpen.setFocusPainted(false);
         btnOpen.setFocusable(false);
         btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnOpen.setRequestFocusEnabled(false);
         btnOpen.setVerifyInputWhenFocusTarget(false);
         btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnOpen.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnOpen);

         btnPassword.setSelected(true);
         btnPassword.setText("PW");
         btnPassword.setToolTipText("Show passworded games");
         btnPassword.setFocusPainted(false);
         btnPassword.setFocusable(false);
         btnPassword.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnPassword.setRequestFocusEnabled(false);
         btnPassword.setVerifyInputWhenFocusTarget(false);
         btnPassword.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnPassword.addActionListener(evt -> btnFilterActionPerformed(evt));
         filterBar2.add(btnPassword);

         btnQuickStart.setText("Quick Start");
         btnQuickStart.setFocusable(false);
         btnQuickStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         btnQuickStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
         btnQuickStart.addActionListener(evt -> btnQuickStartActionPerformed(evt));

         javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
         jPanelTop.setLayout(jPanelTopLayout);
         jPanelTopLayout.setHorizontalGroup(
                 jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(jPanelTopLayout.createSequentialGroup()
                                 .addContainerGap()
                                 .addComponent(btnNewTable)
                                 .addGap(6, 6, 6)
                                 .addComponent(btnNewTournament)
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                         .addComponent(filterBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                                         .addComponent(filterBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addComponent(btnQuickStart)
                                 .addContainerGap(835, Short.MAX_VALUE))
         );
         jPanelTopLayout.setVerticalGroup(
                 jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(jPanelTopLayout.createSequentialGroup()
                                 .addContainerGap()
                                 .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                 .addComponent(btnNewTable)
                                                 .addComponent(btnNewTournament))
                                         .addGroup(jPanelTopLayout.createSequentialGroup()
                                                 .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                         .addComponent(filterBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                         .addComponent(btnQuickStart))
                                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                 .addComponent(filterBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                 .addContainerGap())
         );

         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
         add(jPanelTop, gridBagConstraints);

         jSplitPane1.setBorder(null);
         jSplitPane1.setDividerSize(10);
         jSplitPane1.setResizeWeight(1.0);

         jSplitPaneTables.setBorder(null);
         jSplitPaneTables.setDividerSize(10);
         jSplitPaneTables.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
         jSplitPaneTables.setResizeWeight(0.5);

         jScrollPaneTablesActive.setBorder(null);
         jScrollPaneTablesActive.setViewportBorder(null);

         tableTables.setModel(this.tableModel);
         jScrollPaneTablesActive.setViewportView(tableTables);

         jSplitPaneTables.setLeftComponent(jScrollPaneTablesActive);

         jScrollPaneTablesFinished.setBorder(null);
         jScrollPaneTablesFinished.setViewportBorder(null);
         jScrollPaneTablesFinished.setMinimumSize(new java.awt.Dimension(23, 0));

         tableCompleted.setModel(this.matchesModel);
         jScrollPaneTablesFinished.setViewportView(tableCompleted);

         jSplitPaneTables.setRightComponent(jScrollPaneTablesFinished);

         javax.swing.GroupLayout jPanelTablesLayout = new javax.swing.GroupLayout(jPanelTables);
         jPanelTables.setLayout(jPanelTablesLayout);
         jPanelTablesLayout.setHorizontalGroup(
                 jPanelTablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addComponent(jSplitPaneTables, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
         );
         jPanelTablesLayout.setVerticalGroup(
                 jPanelTablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addComponent(jSplitPaneTables, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
         );

         jSplitPane1.setLeftComponent(jPanelTables);
         jSplitPane1.setRightComponent(chatPanelMain);

         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridy = 1;
         gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
         gridBagConstraints.weightx = 1.0;
         gridBagConstraints.weighty = 1.0;
         add(jSplitPane1, gridBagConstraints);

         jPanelBottom.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
         jPanelBottom.setPreferredSize(new java.awt.Dimension(516, 37));
         jPanelBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

         jButtonFooterNext.setText("Next");
         jButtonFooterNext.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         jButtonFooterNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         jButtonFooterNext.setOpaque(false);
         jButtonFooterNext.addActionListener(evt -> jButtonFooterNextActionPerformed(evt));
         jPanelBottom.add(jButtonFooterNext);

         jLabelFooterLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
         jLabelFooterLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         jLabelFooterLabel.setText("Message of the Day:");
         jLabelFooterLabel.setAlignmentY(0.3F);
         jPanelBottom.add(jLabelFooterLabel);

         jLabelFooterText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         jLabelFooterText.setText("You are playing Mage version 0.7.5. Welcome! -- Mage dev team --");
         jPanelBottom.add(jLabelFooterText);

         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridy = 2;
         gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
         add(jPanelBottom, gridBagConstraints);
     }// </editor-fold>//GEN-END:initComponents

     private void btnNewTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTournamentActionPerformed
         newTournamentDialog.showDialog(roomId);
     }//GEN-LAST:event_btnNewTournamentActionPerformed

     private void btnQuickStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuickStartActionPerformed
         TableView table;
         try {
             File f = new File("test.dck");
             if (!f.exists()) {
                 JOptionPane.showMessageDialog(null, "Couldn't find test.dck file for quick game start", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
             }

             MatchOptions options = new MatchOptions("1", "Two Player Duel", false, 2);
             options.getPlayerTypes().add(PlayerType.HUMAN);
             options.getPlayerTypes().add(PlayerType.COMPUTER_MAD);
             options.setDeckType("Limited");
             options.setAttackOption(MultiplayerAttackOption.LEFT);
             options.setRange(RangeOfInfluence.ALL);
             options.setWinsNeeded(1);
             options.setMatchTimeLimit(MatchTimeLimit.NONE);
             options.setFreeMulligans(2);
             options.setSkillLevel(SkillLevel.CASUAL);
             options.setRollbackTurnsAllowed(true);
             options.setQuitRatio(100);
             String serverAddress = SessionHandler.getSession().getServerHostname().orElseGet(() -> "");
             options.setBannedUsers(IgnoreList.ignoreList(serverAddress));
             table = SessionHandler.createTable(roomId, options);

             SessionHandler.joinTable(roomId, table.getTableId(), "Human", PlayerType.HUMAN, 1, DeckImporterUtil.importDeck("test.dck"), "");
             SessionHandler.joinTable(roomId, table.getTableId(), "Computer", PlayerType.COMPUTER_MAD, 5, DeckImporterUtil.importDeck("test.dck"), "");
             SessionHandler.startMatch(roomId, table.getTableId());
         } catch (HeadlessException ex) {
             handleError(ex);
         }
     }//GEN-LAST:event_btnQuickStartActionPerformed

     private void btnNewTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTableActionPerformed
         newTableDialog.showDialog(roomId);
     }//GEN-LAST:event_btnNewTableActionPerformed

     private void jButtonFooterNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFooterNextActionPerformed
         synchronized (this) {
             if (messages != null && !messages.isEmpty()) {
                 currentMessage++;
                 if (currentMessage >= messages.size()) {
                     currentMessage = 0;
                 }

                 URLHandler.RemoveMouseAdapter(jLabelFooterText);
                 URLHandler.handleMessage(messages.get(currentMessage), this.jLabelFooterText);
             }
         }
     }//GEN-LAST:event_jButtonFooterNextActionPerformed

     private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
         setTableFilter();
     }//GEN-LAST:event_btnFilterActionPerformed

     private void btnStateFinishedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStateFinishedActionPerformed
         if (this.btnStateFinished.isSelected()) {
             this.jSplitPaneTables.setDividerLocation(-1);
         } else {
             this.jSplitPaneTables.setDividerLocation(this.jPanelTables.getHeight());
         }
         this.startTasks();
     }//GEN-LAST:event_btnStateFinishedActionPerformed

     private void handleError(Exception ex) {
         LOGGER.fatal("Error loading deck: ", ex);
         JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Error loading deck.", "Error", JOptionPane.ERROR_MESSAGE);
     }

     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JToggleButton btnFormatBlock;
     private javax.swing.JToggleButton btnFormatCommander;
     private javax.swing.JToggleButton btnFormatLegacy;
     private javax.swing.JToggleButton btnFormatLimited;
     private javax.swing.JToggleButton btnFormatModern;
     private javax.swing.JToggleButton btnFormatOther;
     private javax.swing.JToggleButton btnFormatStandard;
     private javax.swing.JToggleButton btnFormatTinyLeader;
     private javax.swing.JToggleButton btnFormatVintage;
     private javax.swing.JButton btnNewTable;
     private javax.swing.JButton btnNewTournament;
     private javax.swing.JToggleButton btnOpen;
     private javax.swing.JToggleButton btnPassword;
     private javax.swing.JButton btnQuickStart;
     private javax.swing.JToggleButton btnSkillBeginner;
     private javax.swing.JToggleButton btnSkillCasual;
     private javax.swing.JToggleButton btnSkillSerious;
     private javax.swing.JToggleButton btnRated;
     private javax.swing.JToggleButton btnUnrated;
     private javax.swing.JToggleButton btnStateActive;
     private javax.swing.JToggleButton btnStateFinished;
     private javax.swing.JToggleButton btnStateWaiting;
     private javax.swing.JToggleButton btnTypeMatch;
     private javax.swing.JToggleButton btnTypeTourneyConstructed;
     private javax.swing.JToggleButton btnTypeTourneyLimited;
     private mage.client.table.PlayersChatPanel chatPanelMain;
     private javax.swing.JToolBar filterBar1;
     private javax.swing.JToolBar filterBar2;
     private javax.swing.JButton jButtonFooterNext;
     private javax.swing.JLabel jLabelFooterLabel;
     private javax.swing.JLabel jLabelFooterText;
     private javax.swing.JPanel jPanelBottom;
     private javax.swing.JPanel jPanelTables;
     private javax.swing.JPanel jPanelTop;
     private javax.swing.JScrollPane jScrollPaneTablesActive;
     private javax.swing.JScrollPane jScrollPaneTablesFinished;
     private javax.swing.JToolBar.Separator jSeparator1;
     private javax.swing.JToolBar.Separator jSeparator2;
     private javax.swing.JToolBar.Separator jSeparator3;
     private javax.swing.JToolBar.Separator jSeparator4;
     private javax.swing.JToolBar.Separator jSeparator5;
     private javax.swing.JSplitPane jSplitPane1;
     private javax.swing.JSplitPane jSplitPaneTables;
     private javax.swing.JTable tableCompleted;
     private javax.swing.JTable tableTables;
     // End of variables declaration//GEN-END:variables

 }

 class TableTableModel extends AbstractTableModel {

     final ImageIcon tourneyIcon = new javax.swing.ImageIcon(getClass().getResource("/tables/tourney_icon.png"));
     final ImageIcon matchIcon = new javax.swing.ImageIcon(getClass().getResource("/tables/match_icon.png"));

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
     public static final int ACTION_COLUMN = 11; // column the action is located (starting with 0)

     public static final String RATED_VALUE_YES = "YES";
     public static final String RATED_VALUE_NO = "";

     public static final String PASSWORD_VALUE_YES = "YES";

     private final String[] columnNames = new String[]{"M/T", "Deck Type", "Owner / Players", "Game Type", "Info", "Status", "Password", "Created / Started", "Skill Level", "Rating", "Quit %", "Action"};

     private TableView[] tables = new TableView[0];

     TableTableModel() {
     }

     public void loadData(Collection<TableView> tables) throws MageRemoteException {
         this.tables = tables.toArray(new TableView[0]);
         this.fireTableDataChanged();
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
             case 12:
                 return tables[arg0].isTournament();
             case 13:
                 if (!tables[arg0].getGames().isEmpty()) {
                     return tables[arg0].getGames().get(0);
                 }
                 return null;
             case 14:
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

 class UpdateTablesTask extends SwingWorker<Void, Collection<TableView>> {

     private final UUID roomId;
     private final TablesPanel panel;

     private static final Logger logger = Logger.getLogger(UpdateTablesTask.class);

     private int count = 0;

     UpdateTablesTask(UUID roomId, TablesPanel panel) {

         this.roomId = roomId;
         this.panel = panel;
     }

     @Override
     protected Void doInBackground() throws Exception {
         while (!isCancelled()) {
             Collection<TableView> tables = SessionHandler.getTables(roomId);
             if (tables != null) {
                 this.publish(tables);
             }
             TimeUnit.SECONDS.sleep(3);
         }
         return null;
     }

     @Override
     protected void process(java.util.List<Collection<TableView>> view) {
         panel.updateTables(view.get(0));
         count++;
         if (count > 60) {
             count = 0;
             panel.reloadMessages();
         }
     }

     @Override
     protected void done() {
         try {
             get();
         } catch (InterruptedException | ExecutionException ex) {
             logger.fatal("Update Tables Task error", ex);
         } catch (CancellationException ex) {
         }
     }

 }

 class UpdatePlayersTask extends SwingWorker<Void, Collection<RoomUsersView>> {

     private final UUID roomId;
     private final PlayersChatPanel chat;

     private static final Logger logger = Logger.getLogger(UpdatePlayersTask.class);

     UpdatePlayersTask(UUID roomId, PlayersChatPanel chat) {

         this.roomId = roomId;
         this.chat = chat;
     }

     @Override
     protected Void doInBackground() throws Exception {
         while (!isCancelled()) {
             this.publish(SessionHandler.getRoomUsers(roomId));
             TimeUnit.SECONDS.sleep(3);
         }
         return null;
     }

     @Override
     protected void process(java.util.List<Collection<RoomUsersView>> roomUserInfo) {
         chat.setRoomUserInfo(roomUserInfo);
     }

     @Override
     protected void done() {
         try {
             get();
         } catch (InterruptedException | ExecutionException ex) {
             logger.fatal("Update Players Task error", ex);
         } catch (CancellationException ex) {
         }
     }

 }

 class MatchesTableModel extends AbstractTableModel {

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
                 return matches[arg0].isRated() ? TableTableModel.RATED_VALUE_YES : TableTableModel.RATED_VALUE_NO;
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
         }
         return "";
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

 class UpdateMatchesTask extends SwingWorker<Void, Collection<MatchView>> {

     private final UUID roomId;
     private final TablesPanel panel;

     private static final Logger logger = Logger.getLogger(UpdateTablesTask.class);

     UpdateMatchesTask(UUID roomId, TablesPanel panel) {
         this.roomId = roomId;
         this.panel = panel;
     }

     @Override
     protected Void doInBackground() throws Exception {
         while (!isCancelled()) {
             Collection<MatchView> matches = SessionHandler.getFinishedMatches(roomId);
             if (!matches.isEmpty()) {
                 this.publish(matches);
             }
             TimeUnit.SECONDS.sleep(10);
         }
         return null;
     }

     @Override
     protected void process(java.util.List<Collection<MatchView>> view) {
         panel.updateMatches(view.get(0));
     }

     @Override
     protected void done() {
         try {
             get();
         } catch (InterruptedException | ExecutionException ex) {
             logger.fatal("Update Matches Task error", ex);
         } catch (CancellationException ex) {
         }
     }

 }

 class GameChooser extends JPopupMenu {

     public void init() {

     }

     public void show(java.util.List<UUID> games, Point p) {
         if (p == null) {
             return;
         }
         this.removeAll();
         for (UUID gameId : games) {
             this.add(new GameChooserAction(gameId, gameId.toString()));
         }
         this.show(MageFrame.getDesktop(), p.x, p.y);
         GuiDisplayUtil.keepComponentInsideScreen(p.x, p.y, this);
     }

     private class GameChooserAction extends AbstractAction {

         private final UUID id;

         public GameChooserAction(UUID id, String choice) {
             this.id = id;
             putValue(Action.NAME, choice);
         }

         @Override
         public void actionPerformed(ActionEvent e) {
             SessionHandler.replayGame(id);
             setVisible(false);
         }

     }

 }
