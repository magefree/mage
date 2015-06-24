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
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.SpinnerNumberModel;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.table.TournamentPlayerPanel;
import mage.constants.MatchTimeLimit;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.constants.SkillLevel;
import mage.game.draft.DraftOptions;
import mage.game.draft.DraftOptions.TimingOption;
import mage.game.tournament.LimitedOptions;
import mage.game.tournament.TournamentOptions;
import mage.remote.Session;
import mage.view.GameTypeView;
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
    private final Session session;
    private String lastSessionId;
    private JList randomList = new JList();
    private final List<TournamentPlayerPanel> players = new ArrayList<>();
    private final List<JComboBox> packs = new ArrayList<>();
    private final int CONSTRUCTION_TIME_MIN = 6;
    private final int CONSTRUCTION_TIME_MAX = 30;
    private final String randomDraftDescription = ("The selected packs will be randomly distributed to players. Each player may open different packs. Duplicates will be avoided.");

    private boolean automaticChange = false;

    /** Creates new form NewTournamentDialog */
    public NewTournamentDialog() {
        initComponents();
        session = MageFrame.getSession();
        lastSessionId = "";
        txtName.setText("Tournament");
        this.spnNumWins.setModel(new SpinnerNumberModel(2, 1, 5, 1));
        this.spnFreeMulligans.setModel(new SpinnerNumberModel(0, 0, 5, 1));
        this.spnConstructTime.setModel(new SpinnerNumberModel(10, CONSTRUCTION_TIME_MIN, CONSTRUCTION_TIME_MAX, 2));
        this.spnNumRounds.setModel(new SpinnerNumberModel(2, 2, 10, 1));
    }

    public void showDialog(UUID roomId) {
        this.roomId = roomId;
        if (!lastSessionId.equals(MageFrame.getSession().getSessionId())) {
            lastSessionId = session.getSessionId();
            this.player1Panel.setPlayerName(session.getUserName());
            this.player1Panel.showLevel(false); // no computer
            cbTournamentType.setModel(new DefaultComboBoxModel(session.getTournamentTypes().toArray()));

            cbGameType.setModel(new DefaultComboBoxModel(session.getTournamentGameTypes().toArray()));
            cbDeckType.setModel(new DefaultComboBoxModel(session.getDeckTypes()));

            cbTimeLimit.setModel(new DefaultComboBoxModel(MatchTimeLimit.values()));
            cbSkillLevel.setModel(new DefaultComboBoxModel(SkillLevel.values()));
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
        lbSkillLevel = new javax.swing.JLabel();
        cbSkillLevel = new javax.swing.JComboBox();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        lblTournamentType = new javax.swing.JLabel();
        cbTournamentType = new javax.swing.JComboBox();
        lbDeckType = new javax.swing.JLabel();
        cbDeckType = new javax.swing.JComboBox();
        lblGameType = new javax.swing.JLabel();
        cbGameType = new javax.swing.JComboBox();
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
        lblNbrPlayers = new javax.swing.JLabel();
        spnNumPlayers = new javax.swing.JSpinner();
        pnlDraftOptions = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbDraftTiming = new javax.swing.JComboBox();
        cbAllowSpectators = new javax.swing.JCheckBox();
        lblPlayer1 = new javax.swing.JLabel();
        lblConstructionTime = new javax.swing.JLabel();
        chkRollbackTurnsAllowed = new javax.swing.JCheckBox();
        spnConstructTime = new javax.swing.JSpinner();
        player1Panel = new mage.client.table.NewPlayerPanel();
        pnlPlayers = new javax.swing.JPanel();
        pnlOtherPlayers = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        pnlRandomPacks = new javax.swing.JPanel();

        setTitle("New Tournament");

        lblName.setText("Name:");

        lbTimeLimit.setText("Time Limit:");
        lbTimeLimit.setToolTipText("The time a player has for the whole match. If a player runs out of time during a game, he loses the complete match. ");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cbTimeLimit, org.jdesktop.beansbinding.ObjectProperty.create(), lbTimeLimit, org.jdesktop.beansbinding.BeanProperty.create("labelFor"));
        bindingGroup.addBinding(binding);

        cbTimeLimit.setToolTipText("The time a player has for the whole match. If a player runs out of time during a game, he loses the complete match. ");

        lbSkillLevel.setText("Skill Level:");
        lbSkillLevel.setToolTipText("The time a player has for the whole match. If a player runs out of time during a game, he loses the complete match. ");

        cbSkillLevel.setToolTipText("<HTML>This option can be used to make it easier to find matches<br>\nwith opponents of the appropriate skill level.");

        lblPassword.setText("Password:");
        lblPassword.setToolTipText("Players have to enter the password to be able to join this table.");

        txtPassword.setToolTipText("Players have to enter the password to be able to join this table.");

        lblTournamentType.setText("Tournament Type:");

        cbTournamentType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTournamentType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTournamentTypeActionPerformed(evt);
            }
        });

        lbDeckType.setText("Deck Type:");
        lbDeckType.setFocusable(false);

        lblGameType.setText("Game Type:");
        lblGameType.setFocusable(false);

        cbGameType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGameTypeActionPerformed(evt);
            }
        });

        lblFreeMulligans.setText("Free Mulligans:");

        spnFreeMulligans.setToolTipText("Players can take this number of free mulligans (their hand size will not be reduced).");

        lblNumWins.setText("Wins:");

        spnNumWins.setToolTipText("To win a match a player has to win this number of games.");
        spnNumWins.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnNumWinsnumPlayersChanged(evt);
            }
        });

        lblDraftCube.setText("Draft Cube:");

        cbDraftCube.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDraftCube.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDraftCubeActionPerformed(evt);
            }
        });

        lblNumRounds.setText("Number of Swiss Rounds:");
        lblNumRounds.setToolTipText("<html>The number of rounds the swiss tournament has in total.<br>\nThe tournaments ends after that number of rounds or<br> \nif there are less than two players left in the tournament.");

        spnNumRounds.setToolTipText("<html>The number of rounds the swiss tournament has in total.<br>\nThe tournaments ends after that number of rounds or<br> \nif there are less than two players left in the tournament.");
        spnNumRounds.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnNumRoundsnumPlayersChanged(evt);
            }
        });

        lblPacks.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPacks.setText("Packs");

        pnlPacks.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlPacks.setLayout(new java.awt.GridLayout(0, 1, 2, 0));

        lblNbrPlayers.setText("Players:");

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
        cbAllowSpectators.setToolTipText("Allow other players to watch the games of this table.");

        lblPlayer1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPlayer1.setText("Player 1 (You)");

        lblConstructionTime.setText("Construction Time (Minutes):");

        chkRollbackTurnsAllowed.setText("Allow rollbacks");
        chkRollbackTurnsAllowed.setToolTipText("<HTML>Allow to rollback to the start of previous turns<br> if all players agree. ");

        spnConstructTime.setToolTipText("The time players have to build their deck.");

        player1Panel.setPreferredSize(new java.awt.Dimension(400, 44));

        pnlOtherPlayers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlOtherPlayers.setLayout(new java.awt.GridLayout(0, 1, 2, 0));

        javax.swing.GroupLayout pnlPlayersLayout = new javax.swing.GroupLayout(pnlPlayers);
        pnlPlayers.setLayout(pnlPlayersLayout);
        pnlPlayersLayout.setHorizontalGroup(
            pnlPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlOtherPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlPlayersLayout.setVerticalGroup(
            pnlPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlOtherPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        pnlRandomPacks.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlRandomPacks.setToolTipText("");
        pnlRandomPacks.setLayout(new java.awt.GridLayout(0, 1, 2, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPacks, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNbrPlayers)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnNumPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPacks)
                            .addComponent(lblPlayer1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(pnlDraftOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNumRounds))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblConstructionTime)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spnConstructTime, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkRollbackTurnsAllowed))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spnNumRounds, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbAllowSpectators))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnOk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblDraftCube)
                                        .addComponent(lblTournamentType)
                                        .addComponent(lbDeckType)
                                        .addComponent(lblGameType))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbDraftCube, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbDeckType, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbGameType, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(cbTournamentType, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblFreeMulligans)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(spnFreeMulligans, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblNumWins)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(spnNumWins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblName)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbTimeLimit)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbTimeLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lbSkillLevel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cbSkillLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblPassword)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(player1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRandomPacks, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName)
                    .addComponent(lbTimeLimit)
                    .addComponent(cbTimeLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSkillLevel)
                    .addComponent(cbSkillLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(lblDraftCube))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDeckType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDeckType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGameType)
                    .addComponent(cbGameType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPacks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlPacks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlRandomPacks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbAllowSpectators, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spnNumRounds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNumRounds))
                            .addComponent(lblNbrPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spnNumPlayers)
                            .addComponent(pnlDraftOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spnConstructTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblConstructionTime)
                        .addComponent(chkRollbackTurnsAllowed)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        tOptions.setPassword(txtPassword.getText());
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
        if (tOptions.getLimitedOptions() == null) {
            tOptions.setLimitedOptions(new LimitedOptions());
        }
        if (tournamentType.isLimited()) {
            tOptions.getLimitedOptions().setConstructionTime((Integer)this.spnConstructTime.getValue() * 60);
            tOptions.getLimitedOptions().setIsRandom(tournamentType.isRandom());
            if (tournamentType.isCubeBooster()) {
                tOptions.getLimitedOptions().setDraftCubeName(this.cbDraftCube.getSelectedItem().toString());
            } else if (tournamentType.isRandom()) {
                for (Object pack : randomList.getSelectedValuesList()) {
                    String packStr = (String) pack;
                    String code = packStr.substring(0, 3);
                    tOptions.getLimitedOptions().getSetCodes().add(code);
                }
                if (tOptions.getLimitedOptions().getSetCodes().size() < 2) {
                    // At least two sets must be chosen.
                    return;
                }
            } else {
                for (JComboBox pack: packs) {
                    tOptions.getLimitedOptions().getSetCodes().add(((ExpansionInfo) pack.getSelectedItem()).getCode());
                }
            }
            tOptions.getMatchOptions().setDeckType("Limited");
            tOptions.getMatchOptions().setGameType("Two Player Duel");
            tOptions.getMatchOptions().setLimited(true);
        } else {
            tOptions.getLimitedOptions().setConstructionTime(0);
            tOptions.getLimitedOptions().setNumberBoosters(0);
            tOptions.getLimitedOptions().setDraftCube(null);
            tOptions.getLimitedOptions().setDraftCubeName("");
            tOptions.getMatchOptions().setDeckType((String) this.cbDeckType.getSelectedItem());
            tOptions.getMatchOptions().setGameType(((GameTypeView) this.cbGameType.getSelectedItem()).getName());
            tOptions.getMatchOptions().setLimited(false);
        }
        
        tOptions.getMatchOptions().setMatchTimeLimit((MatchTimeLimit) this.cbTimeLimit.getSelectedItem());
        tOptions.getMatchOptions().setSkillLevel((SkillLevel) this.cbSkillLevel.getSelectedItem());
        tOptions.getMatchOptions().setWinsNeeded((Integer)this.spnNumWins.getValue());
        tOptions.getMatchOptions().setFreeMulligans((Integer)this.spnFreeMulligans.getValue());
        tOptions.getMatchOptions().setAttackOption(MultiplayerAttackOption.LEFT);
        tOptions.getMatchOptions().setRange(RangeOfInfluence.ALL);
        tOptions.getMatchOptions().setRollbackTurnsAllowed(this.chkRollbackTurnsAllowed.isSelected());
        saveTournamentSettingsToPrefs(tOptions);

        table = session.createTournamentTable(roomId, tOptions);
        if (table == null) {
            // message must be send by server!
            return;
        }
        if (session.joinTournamentTable(
                roomId,
                table.getTableId(),
                this.player1Panel.getPlayerName(), 
                "Human", 1,
                DeckImporterUtil.importDeck(this.player1Panel.getDeckFile()),
                tOptions.getPassword())) {
            for (TournamentPlayerPanel player: players) {
                if (!player.getPlayerType().toString().equals("Human")) {
                    if (!player.joinTournamentTable(roomId, table.getTableId(), DeckImporterUtil.importDeck(this.player1Panel.getDeckFile()))) {
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

    private void cbGameTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGameTypeActionPerformed
        setGameOptions();
    }//GEN-LAST:event_cbGameTypeActionPerformed
    private void setGameOptions() {
        GameTypeView gameType = (GameTypeView) cbGameType.getSelectedItem();
//        int oldValue = (Integer) this.spnNumPlayers.getValue();
//        this.spnNumPlayers.setModel(new SpinnerNumberModel(gameType.getMinPlayers(), gameType.getMinPlayers(), gameType.getMaxPlayers(), 1));
//        this.spnNumPlayers.setEnabled(gameType.getMinPlayers() != gameType.getMaxPlayers());
//        if (oldValue >= gameType.getMinPlayers() && oldValue <= gameType.getMaxPlayers()){
//            this.spnNumPlayers.setValue(oldValue);
//        }
        // this.cbAttackOption.setEnabled(gameType.isUseAttackOption());
        // this.cbRange.setEnabled(gameType.isUseRange());
        createPlayers((Integer) spnNumPlayers.getValue() - 1);
    }
    private void setTournamentOptions(int numPlayers) {
        TournamentTypeView tournamentType = (TournamentTypeView) cbTournamentType.getSelectedItem();
        activatePanelElements(tournamentType);

        if (numPlayers < tournamentType.getMinPlayers() || numPlayers > tournamentType.getMaxPlayers()) {
            numPlayers = tournamentType.getMinPlayers();
            createPlayers(numPlayers - 1);
        }
        this.spnNumPlayers.setModel(new SpinnerNumberModel(numPlayers, tournamentType.getMinPlayers(), tournamentType.getMaxPlayers(), 1));
        this.spnNumPlayers.setEnabled(tournamentType.getMinPlayers() != tournamentType.getMaxPlayers());
        createPlayers((Integer) spnNumPlayers.getValue() - 1);
        
        if (tournamentType.isLimited()) {
            if (tournamentType.isRandom()){
                createRandomPacks();
            }else{
                createPacks(tournamentType.getNumBoosters());
            }
        }

    }

    private void setNumberOfSwissRoundsMin(int numPlayers) {
        // set the number of minimum swiss rounds related to the number of players
        int minRounds =  (int) Math.ceil(Math.log(numPlayers + 1) / Math.log(2));
        int newValue = Math.max((Integer)spnNumRounds.getValue(), minRounds);
        this.spnNumRounds.setModel(new SpinnerNumberModel(newValue, minRounds, 10, 1));
        this.pack();
        this.revalidate();
        this.repaint();
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

        this.lblConstructionTime.setVisible(tournamentType.isLimited());
        this.spnConstructTime.setVisible(tournamentType.isLimited());
        
        this.lbDeckType.setVisible(!tournamentType.isLimited());
        this.cbDeckType.setVisible(!tournamentType.isLimited());
        this.lblGameType.setVisible(!tournamentType.isLimited());
        this.cbGameType.setVisible(!tournamentType.isLimited());
        this.player1Panel.showDeckElements(!tournamentType.isLimited());

        if (tournamentType.isLimited()) {
            if (tournamentType.isCubeBooster()) {
                this.lblDraftCube.setVisible(true);
                this.cbDraftCube.setVisible(true);
                this.lblPacks.setVisible(false);
                this.pnlPacks.setVisible(false);
                this.pnlRandomPacks.setVisible(false);
            } else if (tournamentType.isRandom()){
                this.lblDraftCube.setVisible(false);
                this.cbDraftCube.setVisible(false);
                this.lblPacks.setVisible(true);
                this.pnlRandomPacks.setVisible(true);
                this.pnlPacks.setVisible(false);
            } else {
                this.lblDraftCube.setVisible(false);
                this.cbDraftCube.setVisible(false);
                this.lblPacks.setVisible(true);
                this.pnlPacks.setVisible(true);
                this.pnlRandomPacks.setVisible(false);
            }
        } else {
            // construced
            this.lblDraftCube.setVisible(false);
            this.cbDraftCube.setVisible(false);
            this.pnlPacks.setVisible(false);
            this.pnlPacks.setVisible(false);
            this.pnlRandomPacks.setVisible(false);
        }
    }

    private void createRandomPacks() {
        if (pnlRandomPacks.getComponentCount() == 0) {
            
            DefaultListModel randomListModel = new DefaultListModel();
            randomList = new JList(randomListModel);
            randomList.setToolTipText(randomDraftDescription);
            ExpansionInfo[] allExpansions = ExpansionRepository.instance.getWithBoostersSortedByReleaseDate();
            for (ExpansionInfo expansion : allExpansions) {
                String exp = expansion.getCode() + " - " + expansion.getName();
                randomListModel.addElement(exp);
            }
            randomList.setSelectionModel(new DefaultListSelectionModel() {
                private boolean mGestureStarted;

                @Override
                public void setSelectionInterval(int index0, int index1) {
                    // Toggle only one element while the user is dragging the mouse
                    if (!mGestureStarted) {
                        if (isSelectedIndex(index0)) {
                            super.removeSelectionInterval(index0, index1);
                        } else {
                            if (getSelectionMode() == SINGLE_SELECTION) {
                                super.setSelectionInterval(index0, index1);
                            } else {
                                super.addSelectionInterval(index0, index1);
                            }
                        }
                    }
                    // Disable toggling till the adjusting is over, or keep it
                    // enabled in case setSelectionInterval was called directly.
                    mGestureStarted = getValueIsAdjusting();
                }

                @Override
                public void setValueIsAdjusting(boolean isAdjusting) {
                    super.setValueIsAdjusting(isAdjusting);

                    if (isAdjusting == false) {
                        // Enable toggling
                        mGestureStarted = false;
                    }
                }
            });

            String randomPrefs = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_RANDOM_DRAFT, "");
            if (randomPrefs.length() > 0) {
                for (String exp : randomPrefs.split(";")) {
                    randomList.setSelectedValue(exp, false);
                }
            } else {
                randomList.setSelectionInterval(0, randomListModel.size() - 1);
            }
            JScrollPane list1scr = new JScrollPane(randomList);
            randomList.setVisibleRowCount(4);
            pnlRandomPacks.add(list1scr);
        }
        this.pack();
        this.revalidate();
        this.repaint();
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

        setNumberOfSwissRoundsMin(numPlayers);

    }

    private void drawPlayers() {
        this.pnlOtherPlayers.removeAll();
        for (TournamentPlayerPanel panel: players) {
            this.pnlOtherPlayers.add(panel);
            panel.getPlayerType().addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (!automaticChange) {
                        playerActionPerformed(evt);
                    }
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
        automaticChange = true;
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
        automaticChange = false;
    }


    /**
     * set the tournament settings from java prefs
     */
    private void setTournamentSettingsFromPrefs () {
        int numPlayers;
        txtName.setText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NAME, "Tournament"));
        txtPassword.setText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PASSWORD, ""));
        int timeLimit = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_TIME_LIMIT, "1500"));
        for (MatchTimeLimit mtl :MatchTimeLimit.values()) {
            if (mtl.getTimeLimit() == timeLimit) {
                this.cbTimeLimit.setSelectedItem(mtl);
                break;
            }
        }
        String skillLevelDefault = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TABLE_SKILL_LEVEL, "Casual");
        for (SkillLevel skillLevel :SkillLevel.values()) {
            if (skillLevel.toString().equals(skillLevelDefault)) {
                this.cbSkillLevel.setSelectedItem(skillLevel);
                break;
            }
        }        
        int constructionTime = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_CONSTR_TIME, "600")) / 60;
        if (constructionTime < CONSTRUCTION_TIME_MIN || constructionTime > CONSTRUCTION_TIME_MAX) {
            constructionTime = CONSTRUCTION_TIME_MIN;
        }
        this.spnConstructTime.setValue(constructionTime);
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
                if (!tournamentType.isRandom()){
                    loadBoosterPacks(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_DRAFT, ""));
                }

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
        this.chkRollbackTurnsAllowed.setSelected(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TOURNAMENT_ALLOW_ROLLBACKS, "Yes").equals("Yes"));
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
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PASSWORD, tOptions.getPassword());
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_TIME_LIMIT, Integer.toString(tOptions.getMatchOptions().getPriorityTime()));
        if (this.spnConstructTime.isVisible()) {
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_CONSTR_TIME, Integer.toString(tOptions.getLimitedOptions().getConstructionTime()));
        }
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_TYPE, tOptions.getTournamentType());
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS, Integer.toString(tOptions.getMatchOptions().getFreeMulligans()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_WINS, Integer.toString(tOptions.getMatchOptions().getWinsNeeded()));

        if (tOptions.getTournamentType().startsWith("Sealed")) {
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_SEALED, tOptions.getLimitedOptions().getSetCodes().toString());
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PLAYERS_SEALED, Integer.toString(tOptions.getPlayerTypes().size()));
        }

        if (tOptions.getTournamentType().startsWith("Booster")) {
            DraftOptions draftOptions = (DraftOptions) tOptions.getLimitedOptions();
            if (draftOptions != null) {
                PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_DRAFT, draftOptions.getSetCodes().toString());
                PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PLAYERS_DRAFT, Integer.toString(tOptions.getPlayerTypes().size()));
                PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_DRAFT_TIMING, draftOptions.getTiming().name());
            }
            String deckFile = this.player1Panel.getDeckFile();
            if (deckFile != null && !deckFile.isEmpty()) {
                PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TABLE_DECK_FILE, deckFile);
            }

            if (tOptions.getLimitedOptions().getIsRandom()){
                // save random boosters to prefs
                StringBuilder packlist = new StringBuilder();
                for (Object pack: randomList.getSelectedValuesList()){                    
                    packlist.append((String)pack);
                    packlist.append(";");
                }
                PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_PACKS_RANDOM_DRAFT, packlist.toString());
            }
        }
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_ALLOW_SPECTATORS, (tOptions.isWatchingAllowed()?"Yes":"No"));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_TOURNAMENT_ALLOW_ROLLBACKS, (tOptions.getMatchOptions().isRollbackTurnsAllowed()?"Yes":"No"));
    }


    public TableView getTable() {
        return table;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox cbAllowSpectators;
    private javax.swing.JComboBox cbDeckType;
    private javax.swing.JComboBox cbDraftCube;
    private javax.swing.JComboBox cbDraftTiming;
    private javax.swing.JComboBox cbGameType;
    private javax.swing.JComboBox cbSkillLevel;
    private javax.swing.JComboBox cbTimeLimit;
    private javax.swing.JComboBox cbTournamentType;
    private javax.swing.JCheckBox chkRollbackTurnsAllowed;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lbDeckType;
    private javax.swing.JLabel lbSkillLevel;
    private javax.swing.JLabel lbTimeLimit;
    private javax.swing.JLabel lblConstructionTime;
    private javax.swing.JLabel lblDraftCube;
    private javax.swing.JLabel lblFreeMulligans;
    private javax.swing.JLabel lblGameType;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNbrPlayers;
    private javax.swing.JLabel lblNumRounds;
    private javax.swing.JLabel lblNumWins;
    private javax.swing.JLabel lblPacks;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPlayer1;
    private javax.swing.JLabel lblTournamentType;
    private mage.client.table.NewPlayerPanel player1Panel;
    private javax.swing.JPanel pnlDraftOptions;
    private javax.swing.JPanel pnlOtherPlayers;
    private javax.swing.JPanel pnlPacks;
    private javax.swing.JPanel pnlPlayers;
    private javax.swing.JPanel pnlRandomPacks;
    private javax.swing.JSpinner spnConstructTime;
    private javax.swing.JSpinner spnFreeMulligans;
    private javax.swing.JSpinner spnNumPlayers;
    private javax.swing.JSpinner spnNumRounds;
    private javax.swing.JSpinner spnNumWins;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
