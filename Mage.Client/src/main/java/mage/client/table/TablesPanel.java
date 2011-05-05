/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * TablesPanel.java
 *
 * Created on 15-Dec-2009, 10:54:01 PM
 */

package mage.client.table;

import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.client.MageFrame;
import mage.client.components.MageComponents;
import mage.client.dialog.JoinTableDialog;
import mage.client.dialog.NewTableDialog;
import mage.client.dialog.TableWaitingDialog;
import mage.client.remote.MageRemoteException;
import mage.client.remote.Session;
import mage.client.util.ButtonColumn;
import mage.game.match.MatchOptions;
import mage.sets.Sets;
import mage.util.Logging;
import mage.view.TableView;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.client.dialog.NewTournamentDialog;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TablesPanel extends javax.swing.JPanel implements Observer {

	private final static Logger logger = Logging.getLogger(TablesPanel.class.getName());

	private TableTableModel tableModel;
	private UUID roomId;
	private TablesWatchdog tablesWatchdog = new TablesWatchdog();
	private JoinTableDialog joinTableDialog;
	private NewTableDialog newTableDialog;
	private NewTournamentDialog newTournamentDialog;
	private TableWaitingDialog tableWaitingDialog;
	private Session session;

    /** Creates new form TablesPanel */
    public TablesPanel() {
		
		tableModel = new TableTableModel();

        initComponents();

		tableTables.createDefaultColumnsFromModel();

		Action join = new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int modelRow = Integer.valueOf( e.getActionCommand() );
				UUID tableId = UUID.fromString((String)tableModel.getValueAt(modelRow, 0));
				UUID gameId = (UUID)tableModel.getValueAt(modelRow, 6);
				String state = (String)tableModel.getValueAt(modelRow, 4);
				boolean isTournament = (Boolean)tableModel.getValueAt(modelRow, 5);

				if (state.equals("Join")) {
					if (isTournament) {
						logger.info("Joining tournament " + tableId);
						if (session.joinTournamentTable(roomId, tableId, session.getUserName(), "Human", 1))
							tableWaitingDialog.showDialog(roomId, tableId, true);
					}
					else {
						logger.info("Joining table " + tableId);
						joinTableDialog.showDialog(roomId, tableId);
						if (joinTableDialog.isJoined())
							tableWaitingDialog.showDialog(roomId, tableId, false);
					}
				} else if (state.equals("Watch")) {
					logger.info("Watching table " + tableId);
					session.watchTable(roomId, tableId);
				} else if (state.equals("Replay")) {
					logger.info("Replaying game " + gameId);
					session.replayGame(gameId);
				}
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(tableTables, join, 4);
		
		jSplitPane1.setOpaque(false);
		jScrollPane1.setOpaque(false);
		jPanel1.setOpaque(false);
		jScrollPane1.getViewport().setBackground(new Color(20,20,20,150));
    }

    public Map<String, JComponent> getUIComponents() {
    	Map<String, JComponent> components = new HashMap<String, JComponent>();
    	
		components.put("jScrollPane1", jScrollPane1);
		components.put("jScrollPane1ViewPort", jScrollPane1.getViewport());
		components.put("jPanel1", jPanel1);
		components.put("tablesPanel", this);
		
		return components;
    }
    
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			tableModel.loadData(MageFrame.getSession().getTables(roomId));
			this.tableTables.repaint();
		} catch (Exception ex) {
			hideTables();
		}
	}

	public void showTables(UUID roomId) {

		this.roomId = roomId;
		session = MageFrame.getSession();
		if (session != null) {
			btnQuickStart.setVisible(session.isTestMode());
		}
		if (newTableDialog == null) {
			newTableDialog = new NewTableDialog();
			MageFrame.getDesktop().add(newTableDialog);
		}
		if (newTournamentDialog == null) {
			newTournamentDialog = new NewTournamentDialog();
			MageFrame.getDesktop().add(newTournamentDialog);
		}
		if (joinTableDialog == null) {
			joinTableDialog = new JoinTableDialog();
			MageFrame.getDesktop().add(joinTableDialog);
		}
		if (tableWaitingDialog == null) {
			tableWaitingDialog = new TableWaitingDialog();
			MageFrame.getDesktop().add(tableWaitingDialog);
		}
		UUID chatRoomId = session.getRoomChatId(roomId);
		if (chatRoomId != null) {
			this.chatPanel.connect(chatRoomId);
			tablesWatchdog.addObserver(this);
			this.setVisible(true);
			this.repaint();
		}
		else {
			hideTables();
		}
		
		session.getUI().addButton(MageComponents.NEW_GAME_BUTTON, btnNewTable);
	}

	public void hideTables() {
		if (tableWaitingDialog.isVisible()) {
			tableWaitingDialog.closeDialog();
		}
		tablesWatchdog.deleteObservers();
		this.chatPanel.disconnect();

		Component c = this.getParent();
		while (c != null && !(c instanceof TablesPane)) {
			c = c.getParent();
		}
		if (c != null)
			c.setVisible(false);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNewTable = new javax.swing.JButton();
        btnQuickStart = new javax.swing.JButton();
        btnNewTournament = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        chatPanel = new mage.client.chat.ChatPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTables = new javax.swing.JTable();

        btnNewTable.setText("New Match");
        btnNewTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTableActionPerformed(evt);
            }
        });

        btnQuickStart.setText("Quick Start");
        btnQuickStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuickStartActionPerformed(evt);
            }
        });

        btnNewTournament.setText("New Tournament");
        btnNewTournament.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTournamentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNewTable)
                .addGap(6, 6, 6)
                .addComponent(btnNewTournament)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuickStart)
                .addContainerGap(414, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTable)
                    .addComponent(btnQuickStart)
                    .addComponent(btnNewTournament))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jSplitPane1.setDividerSize(3);
        jSplitPane1.setResizeWeight(1.0);

        chatPanel.setMinimumSize(new java.awt.Dimension(100, 43));
        jSplitPane1.setRightComponent(chatPanel);

        tableTables.setModel(this.tableModel);
        jScrollPane1.setViewportView(tableTables);

        jSplitPane1.setLeftComponent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void btnNewTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTableActionPerformed
		newTableDialog.showDialog(roomId);
		if (newTableDialog.getTable() != null) {
			tableWaitingDialog.showDialog(roomId, newTableDialog.getTable().getTableId(), false);
		}
}//GEN-LAST:event_btnNewTableActionPerformed

	private void btnQuickStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuickStartActionPerformed
		TableView table;
		try {
			MatchOptions options = new MatchOptions("1", "Two Player Duel");
			options.getPlayerTypes().add("Human");
			options.getPlayerTypes().add("Computer - default");
			options.setDeckType("Limited");
			options.setAttackOption(MultiplayerAttackOption.LEFT);
			options.setRange(RangeOfInfluence.ALL);
			options.setWinsNeeded(1);
			table = session.createTable(roomId,	options);
			session.joinTable(roomId, table.getTableId(), "Human", "Human", 1, Sets.loadDeck("test.dck"));
			session.joinTable(roomId, table.getTableId(), "Computer", "Computer - default", 1, Sets.loadDeck("test.dck"));
			session.startGame(roomId, table.getTableId());
		} catch (Exception ex) {
			handleError(ex);
		}
	}//GEN-LAST:event_btnQuickStartActionPerformed

	private void btnNewTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTournamentActionPerformed
		newTournamentDialog.showDialog(roomId);
		if (newTournamentDialog.getTable() != null) {
			tableWaitingDialog.showDialog(roomId, newTournamentDialog.getTable().getTableId(), true);
		}
	}//GEN-LAST:event_btnNewTournamentActionPerformed

	private void handleError(Exception ex) {
		logger.log(Level.SEVERE, "Error loading deck", ex);
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Error loading deck.", "Error", JOptionPane.ERROR_MESSAGE);
	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewTable;
    private javax.swing.JButton btnNewTournament;
    private javax.swing.JButton btnQuickStart;
    private mage.client.chat.ChatPanel chatPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tableTables;
    // End of variables declaration//GEN-END:variables

}

class TableTableModel extends AbstractTableModel {
    private String[] columnNames = new String[]{"Table Id", "Game Type", "Deck Type", "Status", "Action"};
	private TableView[] tables = new TableView[0];


	public void loadData(Collection<TableView> tables) throws MageRemoteException {
		this.tables = tables.toArray(new TableView[0]);
		this.fireTableDataChanged();
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
				return tables[arg0].getTableId().toString();
			case 1:
				return tables[arg0].getGameType().toString();
			case 2:
				return tables[arg0].getDeckType().toString();
			case 3:
				return tables[arg0].getTableState().toString();
			case 4:
				switch (tables[arg0].getTableState()) {
					case WAITING:
						return "Join";
					case DUELING:
						return "Watch";
					case FINISHED:
						return "Replay";
					default:
						return "";
				}
			case 5:
				return tables[arg0].isTournament();
			case 6:
				if (!tables[arg0].getGames().isEmpty())
					return tables[arg0].getGames().get(0);
				return null;
		}
		return "";
	}

	@Override
	public String getColumnName(int columnIndex) {
        String colName = "";

        if (columnIndex <= getColumnCount())
            colName = columnNames[columnIndex];

        return colName;
    }

	@Override
    public Class getColumnClass(int columnIndex){
		return String.class;
    }

	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex != 4)
			return false;
		return true;
    }

}

class TablesWatchdog extends Observable implements ActionListener {

	Timer t = new Timer(1000, this); // check every second

	public TablesWatchdog() {
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		setChanged();
		notifyObservers();
	}

}