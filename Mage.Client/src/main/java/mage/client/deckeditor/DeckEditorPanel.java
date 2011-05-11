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
 * DeckEditorPanel.java
 *
 * Created on Feb 18, 2010, 2:47:04 PM
 */

package mage.client.deckeditor;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.dialog.AddLandDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.components.CardInfoPane;
import mage.game.GameException;
import mage.sets.Sets;
import mage.view.CardView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DeckEditorPanel extends javax.swing.JPanel {

	private JFileChooser fcSelectDeck;
	private JFileChooser fcImportDeck;
	private Deck deck = new Deck();
    private boolean isShowCardInfo = false;
	private UUID tableId;
	private DeckEditorMode mode;
	private int timeout;
	private Timer countdown;


    /** Creates new form DeckEditorPanel */
    public DeckEditorPanel() {
        initComponents();
		fcSelectDeck = new JFileChooser();
		fcSelectDeck.setAcceptAllFileFilterUsed(false);
		fcSelectDeck.addChoosableFileFilter(new DeckFilter());
		fcImportDeck = new JFileChooser();
		fcImportDeck.setAcceptAllFileFilterUsed(false);
		fcImportDeck.addChoosableFileFilter(new ImportFilter());
		
		deckArea.setOpaque(false);
	    jPanel1.setOpaque(false);
	    jSplitPane1.setOpaque(false);
 		countdown = new Timer(1000,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (--timeout > 0) {
						setTimeout(Integer.toString(timeout));
					}
					else {
						setTimeout("0");
						countdown.stop();
					}
				}
			}
		);
   }

	public void showDeckEditor(DeckEditorMode mode, Deck deck, UUID tableId, int time) {
		if (deck != null)
			this.deck = deck;
		this.tableId = tableId;
		this.mode = mode;
		this.btnAddLand.setVisible(false);
		switch (mode) {
			case Limited:
				this.btnAddLand.setVisible(true);
				this.txtTimeRemaining.setVisible(true);
			case Sideboard:
				this.btnSubmit.setVisible(true);
				this.cardSelector.loadCards(new ArrayList<Card>(deck.getSideboard()), this.bigCard, mode == DeckEditorMode.Limited);
				this.btnExit.setVisible(false);
				this.btnImport.setVisible(false);
				if (!MageFrame.getSession().isTestMode())
					this.btnLoad.setVisible(false);
				this.deckArea.showSideboard(false);
				countdown.stop();
				this.timeout = time;
				setTimeout(Integer.toString(timeout));
				if (timeout != 0) {
					countdown.start();
				}
				break;
			case Constructed:
				this.btnSubmit.setVisible(false);
				this.cardSelector.loadCards(this.bigCard);
				this.btnExit.setVisible(true);
				this.btnImport.setVisible(true);
				if (!MageFrame.getSession().isTestMode())
					this.btnLoad.setVisible(true);
				this.deckArea.showSideboard(true);
				this.txtTimeRemaining.setVisible(false);
				break;
		}
		init();
	}

	private void init() {
		this.cardSelector.setVisible(true);
		this.jPanel1.setVisible(true);
		this.cardSelector.getCardsList().clearCardEventListeners();
		this.cardSelector.getCardsList().addCardEventListener(
			new Listener<Event> () {
				@Override
				public void event(Event event) {
					if (event.getEventName().equals("double-click")) {
						Card card = cardSelector.getCard((UUID) event.getSource());
						if (card != null) {
							deck.getCards().add(Sets.createCard(card.getClass()));
							if (mode == DeckEditorMode.Sideboard || mode == DeckEditorMode.Limited) {
								deck.getSideboard().remove(card);
								cardSelector.removeCard(card.getId());
							}
							if (cardInfoPane instanceof  CardInfoPane)  {
								((CardInfoPane)cardInfoPane).setCard(new CardView(card));
							}
						}
						refreshDeck();
					}
				}
			}
		);
		this.deckArea.clearDeckEventListeners();
		this.deckArea.addDeckEventListener(
			new Listener<Event> () {
				@Override
				public void event(Event event) {
					if (event.getEventName().equals("double-click")) {
						for (Card card: deck.getCards()) {
							if (card.getId().equals((UUID)event.getSource())) {
								deck.getCards().remove(card);
								if (mode == DeckEditorMode.Limited || mode == DeckEditorMode.Sideboard) {
									deck.getSideboard().add(card);
									cardSelector.loadCards(new ArrayList<Card>(deck.getSideboard()), getBigCard(), mode == DeckEditorMode.Limited);
								}
								break;
							}
						}
						refreshDeck();
					}
					else if (event.getEventName().equals("shift-double-click") && mode == DeckEditorMode.Constructed) {
						for (Card card: deck.getCards()) {
							if (card.getId().equals((UUID)event.getSource())) {
								deck.getCards().remove(card);
								deck.getSideboard().add(card);
								break;
							}
						}
						refreshDeck();
					}
				}
			}
		);
		this.deckArea.addSideboardEventListener(
			new Listener<Event> () {
				@Override
				public void event(Event event) {
					if (event.getEventName().equals("double-click")) {
						for (Card card: deck.getSideboard()) {
							if (card.getId().equals((UUID)event.getSource())) {
								deck.getSideboard().remove(card);
								deck.getCards().add(card);
								break;
							}
						}
						refreshDeck();
					}
				}
			}
		);
		refreshDeck();
		this.setVisible(true);
		this.repaint();
	}

	public void hideDeckEditor() {
		Component c = this.getParent();
		while (c != null && !(c instanceof DeckEditorPane)) {
			c = c.getParent();
		}
		if (c != null)
			c.setVisible(false);
	}

	private BigCard getBigCard() {
		return this.bigCard;
	}

	private void refreshDeck() {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			this.txtDeckName.setText(deck.getName());
			deckArea.loadDeck(deck, bigCard);
		}
		finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	private void setTimeout(String text) {
		this.txtTimeRemaining.setText(text);
	}

	private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        //cardSelector = new mage.client.deckeditor.table.CardTableSelector();
        cardSelector = new mage.client.deckeditor.CardSelector();
        deckArea = new mage.client.deckeditor.DeckArea();
        jPanel1 = new javax.swing.JPanel();
        bigCard = new mage.client.cards.BigCard();
        txtDeckName = new javax.swing.JTextField();
        lblDeckName = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
		btnAddLand = new javax.swing.JButton();
		txtTimeRemaining = new javax.swing.JTextField();

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setTopComponent(cardSelector);
        jSplitPane1.setRightComponent(deckArea);

        bigCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cardInfoPane = Plugins.getInstance().getCardInfoPane();
        if (cardInfoPane != null && System.getProperty("testCardInfo") != null) {
            cardInfoPane.setPreferredSize(new Dimension(170,150));
            cardInfoPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            isShowCardInfo = true;
        } else {
            cardInfoPane = new JLabel();
            cardInfoPane.setVisible(false);
        }

        lblDeckName.setLabelFor(txtDeckName);
        lblDeckName.setText("Deck Name:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnImport.setText("Import");
        btnImport.setName("btnImport"); // NOI18N
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.setName("btnSubmit"); // NOI18N
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnAddLand.setText("Add Land");
        btnAddLand.setName("btnAddLand"); // NOI18N
        btnAddLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLandActionPerformed(evt);
            }
        });

        txtTimeRemaining.setEditable(false);
        txtTimeRemaining.setForeground(java.awt.Color.red);
        txtTimeRemaining.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimeRemaining.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(lblDeckName)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDeckName, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addComponent(cardInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnImport)
						.addContainerGap()
                        .addComponent(btnAddLand)
						.addContainerGap()
						.addComponent(btnSubmit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTimeRemaining))
					)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDeckName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDeckName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave)
                        .addComponent(btnLoad)
                        .addComponent(btnNew)
                        .addComponent(btnExit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnImport)
						.addComponent(btnAddLand)
                        .addComponent(btnSubmit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimeRemaining))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, isShowCardInfo ? 30 : 159, Short.MAX_VALUE)
                .addComponent(cardInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );
    }

	private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
		String lastFolder = MageFrame.getPreferences().get("lastDeckFolder", "");
		if (!lastFolder.isEmpty())
			fcSelectDeck.setCurrentDirectory(new File(lastFolder));
		int ret = fcSelectDeck.showOpenDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fcSelectDeck.getSelectedFile();
			try {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				deck = Deck.load(Sets.loadDeck(file.getPath()), true);
			} catch (GameException ex) {
				JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				Logger.getLogger(DeckEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			refreshDeck();
			try {
				MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
			} catch (IOException ex) {	}
		}
		fcSelectDeck.setSelectedFile(null);
	}//GEN-LAST:event_btnLoadActionPerformed

	private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
		String lastFolder = MageFrame.getPreferences().get("lastDeckFolder", "");
		if (!lastFolder.isEmpty())
			fcSelectDeck.setCurrentDirectory(new File(lastFolder));
		deck.setName(this.txtDeckName.getText());
		int ret = fcSelectDeck.showSaveDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fcSelectDeck.getSelectedFile();
			try {
				String fileName = file.getPath();
				if (!fileName.endsWith(".dck"))
					fileName += ".dck";
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				Sets.saveDeck(fileName, deck.getDeckCardLists());
			} catch (Exception ex) {
				Logger.getLogger(DeckEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			try {
				MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
			} catch (IOException ex) {	}
		}
	}//GEN-LAST:event_btnSaveActionPerformed

	private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
		if (mode == DeckEditorMode.Sideboard || mode == DeckEditorMode.Limited) {
			for (Card card: deck.getCards()) {
				deck.getSideboard().add(card);
			}
			deck.getCards().clear();
			cardSelector.loadCards(new ArrayList<Card>(deck.getSideboard()), this.bigCard, mode == DeckEditorMode.Limited);
		}
		else {
			deck = new Deck();
		}
		refreshDeck();
	}//GEN-LAST:event_btnNewActionPerformed

	private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
		hideDeckEditor();
	}//GEN-LAST:event_btnExitActionPerformed

	private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
		String lastFolder = MageFrame.getPreferences().get("lastImportFolder", "");
		if (!lastFolder.isEmpty())
			fcImportDeck.setCurrentDirectory(new File(lastFolder));
		int ret = fcImportDeck.showOpenDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fcImportDeck.getSelectedFile();
			try {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				DeckImporter importer = getDeckImporter(file.getPath());
				if (importer != null) {
					deck = Deck.load(importer.importDeck(file.getPath()));
				}
				else {
					JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Unknown deck format", "Error importing deck", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				Logger.getLogger(DeckEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			refreshDeck();
			try {
				MageFrame.getPreferences().put("lastImportFolder", file.getCanonicalPath());
			} catch (IOException ex) {	}
		}
		fcImportDeck.setSelectedFile(null);
	}//GEN-LAST:event_btnImportActionPerformed

	private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {
		if (MageFrame.getSession().submitDeck(tableId, deck.getDeckCardLists()))
			hideDeckEditor();
	}

	private void btnAddLandActionPerformed(java.awt.event.ActionEvent evt) {
		AddLandDialog addLand = new AddLandDialog();
		addLand.showDialog(deck);
		refreshDeck();
	}

	public DeckImporter getDeckImporter(String file) {
		if (file.toLowerCase().endsWith("dec"))
			return new DecDeckImporter();
		else if (file.toLowerCase().endsWith("mwdeck"))
			return new MWSDeckImporter();
		else if (file.toLowerCase().endsWith("txt"))
			return new TxtDeckImporter();
		else
			return null;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    //private mage.client.deckeditor.table.CardTableSelector cardSelector;
    private mage.client.deckeditor.CardSelector cardSelector;
    private mage.client.deckeditor.DeckArea deckArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblDeckName;
    private javax.swing.JTextField txtDeckName;
    // End of variables declaration//GEN-END:variables

    private JComponent cardInfoPane;
	private javax.swing.JButton btnSubmit;
	private javax.swing.JButton btnAddLand;
	private javax.swing.JTextField txtTimeRemaining;
}

class DeckFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;

        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
		return (ext==null)?false:ext.equals("dck");
	}

	@Override
	public String getDescription() {
		return "Deck Files";
	}
}

class ImportFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;

        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
		if (ext != null) {
			if (ext.toLowerCase().equals("dec") || ext.toLowerCase().equals("mwdeck") || ext.toLowerCase().equals("txt"))
				return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "*.dec | *.mwDeck | *.txt";
	}


}