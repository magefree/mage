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

import mage.client.dialog.NewTableDialog;
import mage.client.dialog.JoinTableDialog;
import mage.client.dialog.TableWaitingDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import mage.cards.decks.DeckCardLists;
import mage.client.MageFrame;
import mage.client.remote.MageRemoteException;
import mage.client.remote.Session;
import mage.client.util.ButtonColumn;
import mage.util.Logging;
import mage.view.TableView;


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
				String state = (String)tableModel.getValueAt(modelRow, 4);

				if (state.equals("Join")) {
					logger.info("Joining table " + tableId);

					joinTableDialog.showDialog(roomId, tableId);
					if (joinTableDialog.isJoined())
						tableWaitingDialog.showDialog(roomId, tableId);
				} else if (state.equals("Watch")) {
					logger.info("Watching table " + tableId);
					if (!session.watchTable(roomId, tableId))
						hideTables();
				} else if (state.equals("Replay")) {
					logger.info("Replaying table " + tableId);
					if (!session.replayTable(roomId, tableId))
						hideTables();
				}
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(tableTables, join, 4);

    }

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			tableModel.loadData(roomId);
			this.tableTables.repaint();
		} catch (MageRemoteException ex) {
			hideTables();
		}
	}

	public void showTables(UUID roomId) {

		this.roomId = roomId;
		session = MageFrame.getSession();
		this.btnQuickStart.setVisible(true);
		if (newTableDialog == null) {
			newTableDialog = new NewTableDialog();
			MageFrame.getDesktop().add(newTableDialog);
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
			this.chatPanel.connect(session.getRoomChatId(roomId));
			tablesWatchdog.addObserver(this);
			this.setVisible(true);
			this.repaint();
		}
		else {
			hideTables();
		}
	}

	public void hideTables() {
		if (tableWaitingDialog.isVisible()) {
			tableWaitingDialog.closeDialog();
		}
		tablesWatchdog.deleteObservers();
		this.chatPanel.disconnect();
		this.setVisible(false);
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
        jSplitPane1 = new javax.swing.JSplitPane();
        chatPanel = new mage.client.chat.ChatPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTables = new javax.swing.JTable();

        btnNewTable.setText("New Table");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNewTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuickStart)
                .addContainerGap(537, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTable)
                    .addComponent(btnQuickStart))
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
			tableWaitingDialog.showDialog(roomId, newTableDialog.getTable().getTableId());
		}
}//GEN-LAST:event_btnNewTableActionPerformed

	private void btnQuickStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuickStartActionPerformed
		TableView table;
		try {
			List<String> playerTypes = new ArrayList<String>();
			playerTypes.add("Human");
			playerTypes.add("Computer - default");
			table = session.createTable(
					roomId,
					"Two Player Duel",
					"Constructed",
					playerTypes,
					null, null
			);
			session.joinTable(
					roomId,
					table.getTableId(),
					"Human",
					DeckCardLists.load("test.dck")
			);
			session.joinTable(
					roomId,
					table.getTableId(),
					"Computer",
					DeckCardLists.load("test.dck")
			);
			hideTables();
			session.startGame(roomId, table.getTableId());
		} catch (Exception ex) {
			handleError(ex);
		}
	}//GEN-LAST:event_btnQuickStartActionPerformed

	private void handleError(Exception ex) {
		logger.log(Level.SEVERE, "Error loading deck", ex);
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Error loading deck.", "Error", JOptionPane.ERROR_MESSAGE);
	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewTable;
    private javax.swing.JButton btnQuickStart;
    private mage.client.chat.ChatPanel chatPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tableTables;
    // End of variables declaration//GEN-END:variables

}

class TableTableModel extends AbstractTableModel {
    private String[] columnNames = new String[]{"Game Id", "Game Type", "Deck Type", "Status", "Action"};
	private TableView[] tables = new TableView[0];


	public void loadData(UUID roomId) throws MageRemoteException {
		tables = MageFrame.getSession().getTables(roomId).toArray(new TableView[0]);
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