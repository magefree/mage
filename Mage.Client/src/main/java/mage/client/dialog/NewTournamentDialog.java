/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

/*
 * NewTournamentDialog.java
 *
 * Created on Jan 28, 2011, 12:15:56 PM
 */

package mage.client.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.table.TablePlayerPanel;
import mage.client.table.TournamentPlayerPanel;
import mage.constants.MatchTimeLimit;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.draft.DraftOptions;
import mage.game.draft.DraftOptions.TimingOption;
import mage.game.tournament.LimitedOptions;
import mage.game.tournament.TournamentOptions;
import mage.remote.Session;
import mage.view.TableView;
import mage.view.TournamentTypeView;
import org.apache.log4j.Logger;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NewTournamentDialog extends MageDialog {

    private static final Logger logger = Logger.getLogger(NewTournamentDialog.class);

    private TableView table;
    private UUID playerId;
    private UUID roomId;
    private Session session;
    private String lastSessionId;
    private final List<TournamentPlayerPanel> players = new ArrayList<>();
    private final List<JComboBox> packs = new ArrayList<>();

    /** Creates new form NewTournamentDialog */
    public NewTournamentDialog() {
        initComponents();
        session = MageFrame.getSession();
        lastSessionId = "";
        txtName.setText("Tournament");
        this.spnNumWins.setModel(new SpinnerNumberModel(2, 1, 5, 1));
        this.spnFreeMulligans.setModel(new SpinnerNumberModel(0, 0, 5, 1));
        this.spnConstructTime.setModel(new SpinnerNumberModel(10, 10, 30, 5));
        this.spnNumRounds.setModel(new SpinnerNumberModel(2, 2, 10, 1));
    }

    public void showDialog(UUID roomId) {
        this.roomId = roomId;
        if (!lastSessionId.equals(MageFrame.getSession().getSessionId())) {
            lastSessionId = session.getSessionId();
            this.txtPlayer1Name.setText(session.getUserName());
            cbTournamentType.setModel(new DefaultComboBoxModel(session.getTournamentTypes().toArray()));
            cbTimeLimit.setModel(new DefaultComboBoxModel(MatchTimeLimit.values()));
            cbDraftCube.setModel(new DefaultComboBoxModel(session.getDraftCubes()));
            cbDraftTiming.setModel(new DefaultComboBoxModel(DraftOptions.TimingOption.values()));
            // update player types
            int i=2;
            for (TournamentPlayerPanel tournamentPlayerPanel :players) {
                tournamentPlayerPanel.init(i++);
            }
            cbAllowSpectators.setSelected(true);
            setTournamentSettingsFromPrefs();
            this.setModal(true);
            this.setLocation(150, 100);            
        }        
        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lbTimeLimit = new javax.swing.JLabel();
        cbTimeLimit = new javax.swing.JComboBox();
        lblConstructionTime = new javax.swing.JLabel();
        spnConstructTime = new javax.swing.JSpinner();
        lblTournamentType = new javax.swing.JLabel();
        cbTournamentType = new javax.swing.JComboBox();
        lblFreeMulligans = new javax.swing.JLabel();
        spnFreeMulligans = new javax.swing.JSpinner();
        lblNumWins = new javax.swing.JLabel();
        spnNumWins = new javax.swing.JSpinner();
        lblDraftCube = new javax.swing.JLabel();
        cbDraftCube = new javax.swing.JComboBox();
        lblNumRounds = new javax.swing.JLabel();
        spnNumRounds = new javax.swing.JSpinner();
        lblPacks = new javax.swing.JLabel();
        pnlPacks = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        spnNumPlayers = new javax.swing.JSpinner();
        pnlDraftOptions = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbDraftTiming = new javax.swing.JComboBox();
        cbAllowSpectators = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPlayer1Name = new javax.swing.JTextField();
        pnlOtherPlayers = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setTitle("New Tournament");

        lblName.setText("Name:");

        lbTimeLimit.setText("Time Limit:");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cbTimeLimit, org.jdesktop.beansbinding.ObjectProperty.create(), lbTimeLimit, org.jdesktop.beansbinding.BeanProperty.create("labelFor"));
        bindingGroup.addBinding(binding);

        lblConstructionTime.setText("Construction Time (Minutes):");

        lblTournamentType.setText("Tournament Type:");

        cbTournamentType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTournamentType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTournamentTypeActionPerformed(evt);
            }
        });

        lblFreeMulligans.setText("Free Mulligans:");

        lblNumWins.setText("Wins:");

        spnNumWins.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnNumWinsnumPlayersChanged(evt);
            }
        });

        lblDraftCube.setText("Draft Cube");

        cbDraftCube.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDraftCube.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDraftCubeActionPerformed(evt);
            }
        });

        lblNumRounds.setText("Number of Swiss Rounds:");

        spnNumRounds.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnNumRoundsnumPlayersChanged(evt);
            }
        });

        lblPacks.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPacks.setText("Packs");

        pnlPacks.setLayout(new java.awt.GridLayout(0, 1, 2, 0));

        jLabel2.setText("Players:");

        spnNumPlayers.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnNumPlayersStateChanged(evt);
            }
        });

        jLabel6.setText("Timing:");

        cbDraftTiming.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDraftTiming.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDraftTimingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDraftOptionsLayout = new javax.swing.GroupLayout(pnlDraftOptions);
        pnlDraftOptions.setLayout(pnlDraftOptionsLayout);
        pnlDraftOptionsLayout.setHorizontalGroup(
            pnlDraftOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDraftOptionsLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDraftTiming, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnlDraftOptionsLayout.setVerticalGroup(
            pnlDraftOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDraftOptionsLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnlDraftOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDraftTiming, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        cbAllowSpectators.setText("Allow spectators");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Player 1 (You)");

        jLabel4.setText("Name:");

        txtPlayer1Name.setEditable(false);

        pnlOtherPlayers.setLayout(new java.awt.GridLayout(0, 1, 2, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPlayer1Name))
            .addComponent(pnlOtherPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPlayer1Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlOtherPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnNumPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(pnlDraftOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbAllowSpectators))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPacks, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbTimeLimit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbTimeLimit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblTournamentType)
                                    .addComponent(lblDraftCube))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbDraftCube, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTournamentType, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblConstructionTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnConstructTime, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFreeMulligans)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnFreeMulligans, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNumWins)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spnNumWins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblNumRounds)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnNumRounds, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblPacks))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName)
                    .addComponent(lbTimeLimit)
                    .addComponent(cbTimeLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConstructionTime)
                    .addComponent(spnConstructTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTournamentType)
                    .addComponent(cbTournamentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFreeMulligans)
                    .addComponent(spnFreeMulligans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumWins)
                    .addComponent(spnNumWins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDraftCube, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDraftCube)
                    .addComponent(spnNumRounds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumRounds))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPacks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPacks, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbAllowSpectators, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spnNumPlayers)
                    .addComponent(pnlDraftOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTournamentTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTournamentTypeActionPerformed
        setTournamentOptions((Integer) this.spnNumPlayers.getValue());
    }//GEN-LAST:event_cbTournamentTypeActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        TournamentTypeView tournamentType = (TournamentTypeView) cbTournamentType.getSelectedItem();
        TournamentOptions tOptions = new TournamentOptions(this.txtName.getText());
        tOptions.setTournamentType(tournamentType.getName());
        tOptions.getPlayerTypes().add("Human");
        tOptions.setWatchingAllowed(cbAllowSpectators.isSelected());
        for (TournamentPlayerPanel player: players) {
            tOptions.getPlayerTypes().add((String) player.getPlayerType().getSelectedItem());
        }
        if (!tournamentType.isElimination()) {
            tOptions.setNumberRounds((Integer)spnNumRounds.getValue());
        }
        if (tournamentType.isDraft()) {
            DraftOptions options = new DraftOptions();
            options.setDraftType("");
            options.setTiming((TimingOption) this.cbDraftTiming.getSelectedItem());
            tOptions.setLimitedOptions(options);
        }
        if (tournamentType.isLimited()) {
            if (tOptions.getLimitedOptions() == null) {
                tOptions.setLimitedOptions(new LimitedOptions());
            }
            tOptions.getLimitedOptions().setConstructionTime((Integer)this.spnConstructTime.getValue() * 60);
            if (tournamentType.isCubeBooster()) {
                tOptions.getLimitedOptions().setDraftCubeName(this.cbDraftCube.getSelectedItem().toString());
            } else {
                for (JComboBox pack: packs) {
                    tOptions.getLimitedOptions().getSetCodes().add(((ExpansionInfo) pack.getSelectedItem()).getCode());
                }
            }
        }        
        tOptions.getMatchOptions().setMatchTimeLimit((MatchTimeLimit) this.cbTimeLimit.getSelectedItem());
        tOptions.getMatchOptions().setDeckType("Limited");
        tOptions.getMatchOptions().setWinsNeeded((Integer)this.spnNumWins.getValue());
        tOptions.getMatchOptions().setFreeMulligans((Integer)this.spnFreeMulligans.getValue());
        tOptions.getMatchOptions().setAttackOption(MultiplayerAttackOption.LEFT);
        tOptions.getMatchOptions().setRange(RangeOfInfluence.ALL);
        tOptions.getMatchOptions().setLimited(true);

        saveTournamentSettingsToPrefs(tOptions);

        table = session.createTournamentTable(roomId, tOptions);
        if (table == null) {
            // message must be send by server!
            return;
        }
        if (session.joinTournamentTable(roomId, table.getTableId(), this.txtPlayer1Name.getText(), "Human", 1)) {
            for (TournamentPlayerPanel player: players) {
                if (!player.getPlayerType().toString().equals("Human")) {
                    if (!player.joinTournamentTable(roomId, table.getTableId())) {
                        // error message must be send by sever
                        session.removeTable(roomId, table.getTableId());
                        table = null;
                        return;
                    }
                }
            }
            this.hideDialog();
            return;
        }
        JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Error joining tournament.", "Error", JOptionPane.ERROR_MESSAGE);
        session.removeTable(roomId, table.getTableId());
        table = null;
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.table = null;
        this.playerId = null;
        this.hideDialog();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void spnNumPlayersStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnNumPlayersStateChanged
        int numPlayers = (Integer)this.spnNumPlayers.getValue() - 1;
        createPlayers(numPlayers);
    }//GEN-LAST:event_spnNumPlayersStateChanged

    private void spnNumWinsnumPlayersChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnNumWinsnumPlayersChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_spnNumWinsnumPlayersChanged

    private void cbDraftCubeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDraftCubeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDraftCubeActionPerformed

    private void cbDraftTimingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDraftTimingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDraftTimingActionPerformed

    private void spnNumRoundsnumPlayersChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnNumRoundsnumPlayersChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_spnNumRoundsnumPlayersChanged

    private void setTournamentOptions(int numbPlayers) {
        TournamentTypeView tournamentType = (TournamentTypeView) cbTournamentType.getSelectedItem();
        activatePanelElements(tournamentType);

        if (numbPlayers < tournamentType.getMinPlayers() || numbPlayers > tournamentType.getMaxPlayers()) {
            numbPlayers = tournamentType.getMinPlayers();
            createPlayers(numbPlayers - 1);
        }
        this.spnNumPlayers.setModel(new SpinnerNumberModel(numbPlayers, tournamentType.getMinPlayers(), tournamentType.getMaxPlayers(), 1));
        this.spnNumPlayers.setEnabled(tournamentType.getMinPlayers() != tournamentType.getMaxPlayers());

        if (tournamentType.isLimited()) {
            createPacks(tournamentType.getNumBoosters());
        }

    }

    /**
     * Sets elements of the panel to visible or not visible
     * 
     * @param tournamentType 
     */
    private void activatePanelElements(TournamentTypeView tournamentType) {
        this.pnlDraftOptions.setVisible(tournamentType.isDraft());
        this.lblNumRounds.setVisible(!tournamentType.isElimination());
        this.spnNumRounds.setVisible(!tournamentType.isElimination());
        if (tournamentType.isLimited()) {
            if (tournamentType.isCubeBooster()) {
                this.lblDraftCube.setVisible(true);
                this.cbDraftCube.setVisible(true);
                this.lblPacks.setVisible(false);
                this.pnlPacks.setVisible(false);
            } else {
                this.lblDraftCube.setVisible(false);
                this.cbDraftCube.setVisible(false);
                this.lblPacks.setVisible(true);
                this.pnlPacks.setVisible(true);
            }
        } else {
            // construced
            this.lblDraftCube.setVisible(false);
            this.cbDraftCube.setVisible(false);
            this.pnlPacks.setVisible(true);
        }
    }

    private void createPacks(int numPacks) {
        while (packs.size() > numPacks) {
            pnlPacks.remove(packs.get(packs.size() - 1));
            packs.remove(packs.size() - 1);
        }
        while (packs.size() < numPacks) {
            JComboBox pack = new JComboBox();
            pack.setModel(new DefaultComboBoxModel(ExpansionRepository.instance.getWithBoostersSortedByReleaseDate()));
            pnlPacks.add(pack);
            packs.add(pack);
            pack.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    packActionPerformed(evt);
                }
            });
        }
        this.pack();
        this.revalidate();
        this.repaint();
    }

    private void packActionPerformed(java.awt.event.ActionEvent evt) {
        boolean start = false;
        int selectedIndex = 0;
        for (JComboBox pack: packs) {
            if (!start) {
                if (evt.getSource().equals(pack)) {
                    start = true;
                    selectedIndex = pack.getSelectedIndex();
                }
            }
            else {
                pack.setSelectedIndex(selectedIndex);
            }
        }
    }

    private void createPlayers(int numPlayers) {
        // add/remove player panels        
        if (numPlayers > players.size()) {
            while (players.size() != numPlayers) {
                TournamentPlayerPanel playerPanel = new TournamentPlayerPanel();
                playerPanel.init(players.size() + 2);
                players.add(playerPanel);
            }
        }
        else if (numPlayers < players.size()) {
            while (players.size() != numPlayers) {
                players.remove(players.size() - 1);
            }
        }
        drawPlayers();
    }

    private void drawPlayers() {
        this.pnlOtherPlayers.removeAll();
        for (TournamentPlayerPanel panel: players) {
            this.pnlOtherPlayers.add(panel);
            panel.getPlayerType().addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    playerActionPerformed(evt);
                }
            });
        }
        this.pack();
        this.revalidate();
        this.repaint();
    }

    private void playerActionPerformed(java.awt.event.ActionEvent evt) {
        boolean start = false;
        int selectedIndex = 0;
        for (TournamentPlayerPanel player: players) {
            if (!start) {
                if (evt.getSource().equals(player.getPlayerType())) {
                    start = true;
                    selectedIndex = player.getPlayerType().getSelectedIndex();
                }
            }
            else {
                player.getPlayerType().setSelectedIndex(selectedIndex);
            }
        }
    }


    /**
     * set the tournament settings from java prefs
     */
    private void setTournamentSettingsFromPrefs () {
        int numPlayers;
        txtName.setText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NAME, "Tournament"));
        int timeLimit = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_TIME_LIMIT, "1500"));
        for (MatchTimeLimit mtl :MatchTimeLimit.values()) {
            if (mtl.getTimeLimit() == timeLimit) {
                this.cbTimeLimit.setSelectedItem(mtl);
                break;
            }
        }
        this.spnConstructTime.setValue(Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_CONSTR_TIME, "600"))/60);
        String tournamentTypeName = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_TYPE, "Sealed Elimination");
        for (TournamentTypeView tournamentTypeView : session.getTournamentTypes()) {
            if (tournamentTypeView.getName().equals(tournamentTypeName)) {
                cbTournamentType.setSelectedItem(tournamentTypeView);
                break;
            }
        }
        this.spnFreeMulligans.setValue(Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS, "0")));
        this.spnNumWins.setValue(Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_WINS, "2")));


        TournamentTypeView tournamentType = (TournamentTypeView) cbTournamentType.getSelectedItem();
        activatePanelElements(tournamentType);

        if (tournamentType.isLimited()) {
            if (!tournamentType.isDraft()) {
                numPlayers = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PLAYERS_SEALED, "2"));
                setTournamentOptions(numPlayers);
                loadBoosterPacks(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_SEALED, ""));
            }

            if (tournamentType.isDraft()) {
                numPlayers = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PLAYERS_DRAFT, "4"));
                setTournamentOptions(numPlayers);
                loadBoosterPacks(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_DRAFT, ""));

                String draftTiming = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_DRAFT_TIMING, "REGULAR");
                for (TimingOption timingOption : DraftOptions.TimingOption.values()) {
                    if (timingOption.toString().equals(draftTiming)) {
                        cbDraftTiming.setSelectedItem(draftTiming);
                        break;
                    }
                }
            }
        }
        this.cbAllowSpectators.setSelected(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_ALLOW_SPECTATORS, "Yes").equals("Yes"));
    }

    private void loadBoosterPacks(String packString) {
        if (packString.length()>0) {
            String[] packsArray = packString.substring(1, packString.length() - 1).split(",");
            int packNumber = 0;
            for (String pack : packsArray ){
                packNumber++;
                if (this.packs.size() >= packNumber - 1) {
                    JComboBox comboBox = this.packs.get(packNumber-1);
                    ComboBoxModel model = comboBox.getModel();
                    int size = model.getSize();
                    for(int i=0;i<size;i++) {
                        ExpansionInfo element = (ExpansionInfo) model.getElementAt(i);
                        if (element.getCode().equals(pack.trim())) {
                            comboBox.setSelectedIndex(i);
                            break;
                        }
                    }
                }

            }
        }
    }

    /**
     * Save the settings to java prefs to reload it next time the dialog will be created
     *
     * @param tOptions Tournament options

     */
    private void saveTournamentSettingsToPrefs(TournamentOptions tOptions) {
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NAME, tOptions.getName());
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_TIME_LIMIT, Integer.toString(tOptions.getMatchOptions().getPriorityTime()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_CONSTR_TIME, Integer.toString(tOptions.getLimitedOptions().getConstructionTime()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_TYPE, tOptions.getTournamentType());
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS, Integer.toString(tOptions.getMatchOptions().getFreeMulligans()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_WINS, Integer.toString(tOptions.getMatchOptions().getWinsNeeded()));
        switch (tOptions.getTournamentType()) {
            case "Sealed Elimination":
                PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_SEALED, tOptions.getLimitedOptions().getSetCodes().toString());
                PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PLAYERS_SEALED, Integer.toString(tOptions.getPlayerTypes().size()));
                break;
            case "Elimination Booster Draft":
                DraftOptions draftOptions = (DraftOptions) tOptions.getLimitedOptions();
                if (draftOptions != null) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_DRAFT, draftOptions.getSetCodes().toString());
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PLAYERS_DRAFT, Integer.toString(tOptions.getPlayerTypes().size()));
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_DRAFT_TIMING, draftOptions.getTiming().name());
                }
                break;
        }

        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_ALLOW_SPECTATORS, (tOptions.isWatchingAllowed()?"Yes":"No"));
    }


    public TableView getTable() {
        return table;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox cbAllowSpectators;
    private javax.swing.JComboBox cbDraftCube;
    private javax.swing.JComboBox cbDraftTiming;
    private javax.swing.JComboBox cbTimeLimit;
    private javax.swing.JComboBox cbTournamentType;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbTimeLimit;
    private javax.swing.JLabel lblConstructionTime;
    private javax.swing.JLabel lblDraftCube;
    private javax.swing.JLabel lblFreeMulligans;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNumRounds;
    private javax.swing.JLabel lblNumWins;
    private javax.swing.JLabel lblPacks;
    private javax.swing.JLabel lblTournamentType;
    private javax.swing.JPanel pnlDraftOptions;
    private javax.swing.JPanel pnlOtherPlayers;
    private javax.swing.JPanel pnlPacks;
    private javax.swing.JSpinner spnConstructTime;
    private javax.swing.JSpinner spnFreeMulligans;
    private javax.swing.JSpinner spnNumPlayers;
    private javax.swing.JSpinner spnNumRounds;
    private javax.swing.JSpinner spnNumWins;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPlayer1Name;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
