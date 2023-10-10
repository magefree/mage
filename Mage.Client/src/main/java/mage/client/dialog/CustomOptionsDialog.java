package mage.client.dialog;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckFileFilter;
import mage.cards.decks.importer.DeckImporter;
import mage.client.MageFrame;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.mulligan.MulliganType;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * App GUI: custom options for match/tournament
 *
 * @author artemiswkearney
 */
public class CustomOptionsDialog extends MageDialog {

    public enum SaveLoadKeys {
        TABLE(
                PreferencesDialog.KEY_NEW_TABLE_NUMBER_OF_FREE_MULLIGANS,
                PreferencesDialog.KEY_NEW_TABLE_MULLIGAN_TYPE,
                PreferencesDialog.KEY_NEW_TABLE_CUSTOM_STARTING_LIFE,
                PreferencesDialog.KEY_NEW_TABLE_NUMBER_OF_LIFE_AT_START,
                PreferencesDialog.KEY_NEW_TABLE_CUSTOM_STARTING_HAND_SIZE,
                PreferencesDialog.KEY_NEW_TABLE_NUMBER_OF_HAND_SIZE_AT_START,
                PreferencesDialog.KEY_NEW_TABLE_PLANECHASE,
                PreferencesDialog.KEY_NEW_TABLE_EMBLEM_CARDS_ENABLED,
                PreferencesDialog.KEY_NEW_TABLE_EMBLEM_CARDS_PER_PLAYER_FILE,
                PreferencesDialog.KEY_NEW_TABLE_EMBLEM_CARDS_STARTING_PLAYER_FILE
        ),

        TOURNEY(
                PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS,
                PreferencesDialog.KEY_NEW_TOURNAMENT_MULLIGUN_TYPE,
                PreferencesDialog.KEY_NEW_TOURNAMENT_CUSTOM_STARTING_LIFE,
                PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_LIFE_AT_START,
                PreferencesDialog.KEY_NEW_TOURNAMENT_CUSTOM_STARTING_HAND_SIZE,
                PreferencesDialog.KEY_NEW_TOURNAMENT_NUMBER_OF_HAND_SIZE_AT_START,
                PreferencesDialog.KEY_NEW_TOURNAMENT_PLANE_CHASE,
                PreferencesDialog.KEY_NEW_TOURNAMENT_EMBLEM_CARDS_ENABLED,
                PreferencesDialog.KEY_NEW_TOURNAMENT_EMBLEM_CARDS_PER_PLAYER_FILE,
                PreferencesDialog.KEY_NEW_TOURNAMENT_EMBLEM_CARDS_STARTING_PLAYER_FILE
        );
        public final String NUMBER_OF_FREE_MULLIGANS;
        public final String MULLIGAN_TYPE;
        public final String BOOL_CUSTOM_STARTING_LIFE;
        public final String NUMBER_OF_LIFE_AT_START;
        public final String BOOL_CUSTOM_STARTING_HAND_SIZE;
        public final String NUMBER_OF_HAND_SIZE_AT_START;
        public final String PLANECHASE;
        public final String EMBLEM_CARDS_ENABLED;
        public final String EMBLEM_CARDS_PER_PLAYER_FILE;
        public final String EMBLEM_CARDS_STARTING_PLAYER_FILE;

        SaveLoadKeys(
                String numberOfFreeMulligans,
                String mulliganType,
                String customStartLife,
                String valueStartLife,
                String customStartHandSize,
                String valueStartHandSize,
                String planechase,
                String emblemCardsEnabled,
                String emblemCardsPerPlayerFile,
                String emblemCardsStartingPlayerFile
        ) {
            NUMBER_OF_FREE_MULLIGANS = numberOfFreeMulligans;
            MULLIGAN_TYPE = mulliganType;
            BOOL_CUSTOM_STARTING_LIFE = customStartLife;
            NUMBER_OF_LIFE_AT_START = valueStartLife;
            BOOL_CUSTOM_STARTING_HAND_SIZE = customStartHandSize;
            NUMBER_OF_HAND_SIZE_AT_START = valueStartHandSize;
            PLANECHASE = planechase;
            EMBLEM_CARDS_ENABLED = emblemCardsEnabled;
            EMBLEM_CARDS_PER_PLAYER_FILE = emblemCardsPerPlayerFile;
            EMBLEM_CARDS_STARTING_PLAYER_FILE = emblemCardsStartingPlayerFile;
        }
    }

    private static final Logger logger = Logger.getLogger(CustomOptionsDialog.class);
    private final SaveLoadKeys saveLoadKeys;
    private final JButton openButton;
    private final JFileChooser fcSelectEmblemCardsPerPlayer;
    private final JFileChooser fcSelectEmblemCardsStartingPlayer;

    /**
     * Creates new form NewTableDialog
     */
    public CustomOptionsDialog(SaveLoadKeys saveLoadKeys, JButton openButton) {
        this.saveLoadKeys = saveLoadKeys;
        this.openButton = openButton;
        initComponents();
        this.spnFreeMulligans.setModel(new SpinnerNumberModel(0, 0, 5, 1));
        cbMulliganType.setModel(new DefaultComboBoxModel(MulliganType.values()));
        this.spnCustomLifeTotal.setModel(new SpinnerNumberModel(20, 1, 100, 1));
        this.spnCustomLifeTotal.setEnabled(false);
        this.spnCustomStartingHand.setModel(new SpinnerNumberModel(7, 0, 20, 1));
        this.spnCustomStartingHand.setEnabled(false);
        this.setModal(true);
        fcSelectEmblemCardsPerPlayer = new JFileChooser();
        fcSelectEmblemCardsPerPlayer.setAcceptAllFileFilterUsed(false);
        fcSelectEmblemCardsPerPlayer.addChoosableFileFilter(new DeckFileFilter("dck", "XMage's deck files (*.dck)"));
        fcSelectEmblemCardsStartingPlayer = new JFileChooser();
        fcSelectEmblemCardsStartingPlayer.setAcceptAllFileFilterUsed(false);
        fcSelectEmblemCardsStartingPlayer.addChoosableFileFilter(new DeckFileFilter("dck", "XMage's deck files (*.dck)"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblGeneralOptions = new javax.swing.JLabel();
        lblMulliganType = new javax.swing.JLabel();
        cbMulliganType = new javax.swing.JComboBox<>();
        lblFreeMulligans = new javax.swing.JLabel();
        spnFreeMulligans = new javax.swing.JSpinner();
        jSeparator2 = new javax.swing.JSeparator();
        lblVariantOptions = new javax.swing.JLabel();
        chkPlaneChase = new javax.swing.JCheckBox();
        planechaseDescriptionLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        btnOK = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        chkEmblemCards = new javax.swing.JCheckBox();
        btnEmblemCardsPerPlayer = new javax.swing.JButton();
        txtEmblemCardsPerPlayer = new javax.swing.JTextField();
        lblEmblemCardsPerPlayer = new javax.swing.JLabel();
        btnEmblemCardsStartingPlayer = new javax.swing.JButton();
        txtEmblemCardsStartingPlayer = new javax.swing.JTextField();
        lblEmblemCardsStartingPlayer = new javax.swing.JLabel();
        emblemCardsDescriptionLabel = new javax.swing.JLabel();
        spnCustomLifeTotal = new javax.swing.JSpinner();
        checkStartingLife = new javax.swing.JCheckBox();
        checkStartingHandSize = new javax.swing.JCheckBox();
        spnCustomStartingHand = new javax.swing.JSpinner();

        setTitle("Custom Options");

        lblGeneralOptions.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblGeneralOptions.setText("General Options");

        lblMulliganType.setLabelFor(cbMulliganType);
        lblMulliganType.setText("Mulligan type:");
        lblMulliganType.setToolTipText("What style of mulligan?");

        cbMulliganType.setToolTipText("Selections the type of mulligan for games.");
        cbMulliganType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMulliganTypeActionPerformed(evt);
            }
        });

        lblFreeMulligans.setLabelFor(cbMulliganType);
        lblFreeMulligans.setText("Free mulligans:");
        lblFreeMulligans.setToolTipText("What style of mulligan?");

        spnFreeMulligans.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnFreeMulligansStateChanged(evt);
            }
        });

        lblVariantOptions.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblVariantOptions.setText("Variant Options");

        chkPlaneChase.setText("Planechase");
        chkPlaneChase.setToolTipText("Use the PlaneChase variant for your game.");
        chkPlaneChase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPlaneChaseActionPerformed(evt);
            }
        });

        planechaseDescriptionLabel.setText("<html>Shared planar deck of all implemented planes.<br>Uses a 9-sided planar die with 2 planeswalk sides and 2 chaos sides.<br>Some ability text may be incorrect.<br>Some rules details (such as who controls plane abilities) may be incorrect.");
        planechaseDescriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        chkEmblemCards.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chkEmblemCards.setText("Emblem Cards (Experimental)");
        chkEmblemCards.setToolTipText("If enabled, select cards to give players emblem copies of");
        chkEmblemCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEmblemCardsActionPerformed(evt);
            }
        });

        btnEmblemCardsPerPlayer.setText("...");
        btnEmblemCardsPerPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmblemCardsPerPlayerActionPerformed(evt);
            }
        });

        txtEmblemCardsPerPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmblemCardsPerPlayerActionPerformed(evt);
            }
        });

        lblEmblemCardsPerPlayer.setText("Per-Player File");
        lblEmblemCardsPerPlayer.setToolTipText("An emblem of each card in this file is given to each player");

        btnEmblemCardsStartingPlayer.setText("...");
        btnEmblemCardsStartingPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmblemCardsStartingPlayerActionPerformed(evt);
            }
        });

        txtEmblemCardsStartingPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmblemCardsStartingPlayerActionPerformed(evt);
            }
        });

        lblEmblemCardsStartingPlayer.setText("Starting Player File");
        lblEmblemCardsStartingPlayer.setToolTipText("An emblem of every card in this file is given to the starting player (useful for symmetric effects)");

        emblemCardsDescriptionLabel.setText("<html>Give players emblems with the abilities of cards.<br>Note that some abilities may not function correctly from the command zone.<br>If anything breaks, please report it on GitHub.");
        emblemCardsDescriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        spnCustomLifeTotal.setToolTipText("Custom starting life total");
        spnCustomLifeTotal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnCustomLifeTotalStateChanged(evt);
            }
        });

        checkStartingLife.setToolTipText("Check to use a specific starting life total");
        checkStartingLife.setActionCommand("checkCustomLife");
        checkStartingLife.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        checkStartingLife.setLabel("Custom Starting Life");
        checkStartingLife.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkStartingLifeActionPerformed(evt);
            }
        });

        checkStartingHandSize.setText("Custom Starting Hand");
        checkStartingHandSize.setToolTipText("Check to use a specific starting hand size");
        checkStartingHandSize.setActionCommand("checkCustomHandSize");
        checkStartingHandSize.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        checkStartingHandSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkStartingHandSizeActionPerformed(evt);
            }
        });

        spnCustomStartingHand.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnCustomStartingHandStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOK))
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtEmblemCardsPerPlayer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmblemCardsPerPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtEmblemCardsStartingPlayer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmblemCardsStartingPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMulliganType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMulliganType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFreeMulligans)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnFreeMulligans))
                    .addComponent(planechaseDescriptionLabel)
                    .addComponent(emblemCardsDescriptionLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVariantOptions)
                            .addComponent(chkPlaneChase)
                            .addComponent(chkEmblemCards)
                            .addComponent(lblEmblemCardsPerPlayer)
                            .addComponent(lblEmblemCardsStartingPlayer)
                            .addComponent(lblGeneralOptions))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkStartingLife)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnCustomLifeTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkStartingHandSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnCustomStartingHand, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGeneralOptions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMulliganType)
                    .addComponent(cbMulliganType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnFreeMulligans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFreeMulligans))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spnCustomStartingHand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkStartingHandSize))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spnCustomLifeTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkStartingLife)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVariantOptions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPlaneChase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(planechaseDescriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkEmblemCards)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emblemCardsDescriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmblemCardsPerPlayer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmblemCardsPerPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmblemCardsPerPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmblemCardsStartingPlayer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmblemCardsStartingPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmblemCardsStartingPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblMulliganType.getAccessibleContext().setAccessibleName("Mulligan Type:");
        lblMulliganType.getAccessibleContext().setAccessibleParent(lblGeneralOptions);
        cbMulliganType.getAccessibleContext().setAccessibleName("");
        cbMulliganType.getAccessibleContext().setAccessibleDescription("Select the type of mulligan for games.");
        cbMulliganType.getAccessibleContext().setAccessibleParent(lblMulliganType);
        lblFreeMulligans.getAccessibleContext().setAccessibleDescription("");
        lblFreeMulligans.getAccessibleContext().setAccessibleParent(lblGeneralOptions);
        spnFreeMulligans.getAccessibleContext().setAccessibleDescription("Select the number of free mulligans");
        spnFreeMulligans.getAccessibleContext().setAccessibleParent(lblFreeMulligans);
        chkPlaneChase.getAccessibleContext().setAccessibleParent(lblVariantOptions);
        planechaseDescriptionLabel.getAccessibleContext().setAccessibleName("Planechase Description");
        planechaseDescriptionLabel.getAccessibleContext().setAccessibleDescription("Shared planar deck of all implemented planes.\nUses a 9-sided planar die with 2 planeswalk sides and 2 chaos sides.\nSome ability text may be incorrect.\nSome rules details (such as who controls plane abilities) may be incorrect.");
        planechaseDescriptionLabel.getAccessibleContext().setAccessibleParent(chkPlaneChase);
        lblEmblemCardsPerPlayer.getAccessibleContext().setAccessibleParent(chkEmblemCards);
        txtEmblemCardsStartingPlayer.getAccessibleContext().setAccessibleDescription("");
        lblEmblemCardsStartingPlayer.getAccessibleContext().setAccessibleParent(chkEmblemCards);
        emblemCardsDescriptionLabel.getAccessibleContext().setAccessibleName("Emblem Cards description");
        emblemCardsDescriptionLabel.getAccessibleContext().setAccessibleDescription("Give players emblems with the abilities of cards.\nNote that some abilities may not function correctly from the command zone.\nIf anything breaks, please report it on GitHub.");
        spnCustomLifeTotal.getAccessibleContext().setAccessibleName("Custom starting life total");
        spnCustomLifeTotal.getAccessibleContext().setAccessibleDescription("Set a custom starting life total");
        checkStartingLife.getAccessibleContext().setAccessibleName("Custom starting life");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviousConfigurationActionPerformed(java.awt.event.ActionEvent evt, int i) {//GEN-FIRST:event_btnPreviousConfigurationActionPerformed
    }//GEN-LAST:event_btnPreviousConfigurationActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        this.hideDialog();
    }//GEN-LAST:event_btnOKActionPerformed

    private void cbMulliganTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMulliganTypeActionPerformed
        updateActiveCount();
    }//GEN-LAST:event_cbMulliganTypeActionPerformed

    private void spnFreeMulligansStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnFreeMulligansStateChanged
        updateActiveCount();
    }//GEN-LAST:event_spnFreeMulligansStateChanged

    private void chkPlaneChaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPlaneChaseActionPerformed
        updateActiveCount();
    }//GEN-LAST:event_chkPlaneChaseActionPerformed

    private void chkEmblemCardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEmblemCardsActionPerformed
        updateActiveCount();
    }//GEN-LAST:event_chkEmblemCardsActionPerformed

    private void btnEmblemCardsPerPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmblemCardsPerPlayerActionPerformed
        loadEmblemCardFile(false);
    }//GEN-LAST:event_btnEmblemCardsPerPlayerActionPerformed

    private void txtEmblemCardsPerPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmblemCardsPerPlayerActionPerformed

    }//GEN-LAST:event_txtEmblemCardsPerPlayerActionPerformed

    private void btnEmblemCardsStartingPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmblemCardsStartingPlayerActionPerformed
        loadEmblemCardFile(true);
    }//GEN-LAST:event_btnEmblemCardsStartingPlayerActionPerformed

    private void txtEmblemCardsStartingPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmblemCardsStartingPlayerActionPerformed

    }//GEN-LAST:event_txtEmblemCardsStartingPlayerActionPerformed

    private void spnCustomLifeTotalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnCustomLifeTotalStateChanged
        updateActiveCount();
    }//GEN-LAST:event_spnCustomLifeTotalStateChanged

    private void spnCustomStartingHandStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnCustomStartingHandStateChanged
        updateActiveCount();
    }//GEN-LAST:event_spnCustomStartingHandStateChanged

    private void checkStartingLifeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkStartingLifeActionPerformed
        updateActiveCount();
    }//GEN-LAST:event_checkStartingLifeActionPerformed

    private void checkStartingHandSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkStartingHandSizeActionPerformed
        updateActiveCount();
    }//GEN-LAST:event_checkStartingHandSizeActionPerformed

    public void showDialog() {
        this.setLocation(150, 100);
        this.setVisible(true);
    }

    private void loadEmblemCardFile(boolean isStartingPlayer) {
        JFileChooser fileChooser = isStartingPlayer ? fcSelectEmblemCardsStartingPlayer : fcSelectEmblemCardsPerPlayer;
        JTextField textField = isStartingPlayer ? txtEmblemCardsStartingPlayer : txtEmblemCardsPerPlayer;
        String prefKey = isStartingPlayer ? "lastStartingPlayerEmblemCardsFolder" : "lastPerPlayerEmblemCardsFolder";

        String lastFolder = MageFrame.getPreferences().get(prefKey, "");
        if (!lastFolder.isEmpty()) {
            fileChooser.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fileChooser.showDialog(this, "Select Emblem Cards");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textField.setText(file.getPath());
            try {
                MageFrame.getPreferences().put(prefKey, file.getCanonicalPath());
            } catch (IOException ex) {
            }
        }
        fileChooser.setSelectedFile(null);
    }

    public void onLoadSettings(int version) {

        String versionStr = "";
        switch (version) {
            case -1:
                versionStr = "-1"; // default (empty)
                break;
            case 1:
                versionStr = "1";
                break;
            case 2:
                versionStr = "2";
                break;
            default:
                versionStr = "";
                break;
        }

        this.chkPlaneChase.setSelected(PreferencesDialog.getCachedValue(saveLoadKeys.PLANECHASE + versionStr, "No").equals("Yes"));
        this.spnFreeMulligans.setValue(Integer.parseInt(PreferencesDialog.getCachedValue(saveLoadKeys.NUMBER_OF_FREE_MULLIGANS + versionStr, "0")));
        this.cbMulliganType.setSelectedItem(MulliganType.valueByName(PreferencesDialog.getCachedValue(saveLoadKeys.MULLIGAN_TYPE + versionStr, MulliganType.GAME_DEFAULT.toString())));
        this.checkStartingLife.setSelected(PreferencesDialog.getCachedValue(saveLoadKeys.BOOL_CUSTOM_STARTING_LIFE + versionStr, "No").equals("Yes"));
        this.spnCustomLifeTotal.setValue(Integer.parseInt(PreferencesDialog.getCachedValue(saveLoadKeys.NUMBER_OF_LIFE_AT_START + versionStr, "20")));
        this.checkStartingHandSize.setSelected(PreferencesDialog.getCachedValue(saveLoadKeys.BOOL_CUSTOM_STARTING_HAND_SIZE + versionStr, "No").equals("Yes"));
        this.spnCustomStartingHand.setValue(Integer.parseInt(PreferencesDialog.getCachedValue(saveLoadKeys.NUMBER_OF_HAND_SIZE_AT_START + versionStr, "7")));
        this.chkEmblemCards.setSelected(PreferencesDialog.getCachedValue(saveLoadKeys.EMBLEM_CARDS_ENABLED + versionStr, "No").equals("Yes"));
        this.txtEmblemCardsPerPlayer.setText(PreferencesDialog.getCachedValue(saveLoadKeys.EMBLEM_CARDS_PER_PLAYER_FILE, ""));
        this.txtEmblemCardsStartingPlayer.setText(PreferencesDialog.getCachedValue(saveLoadKeys.EMBLEM_CARDS_STARTING_PLAYER_FILE, ""));
        updateActiveCount();
    }

    public void onSaveSettings(int version, MatchOptions options) {
        String versionStr = "";
        switch (version) {
            case 1:
                versionStr = "1";
                break;
            case 2:
                versionStr = "2";
                break;
            default:
                versionStr = "";
                break;
        }
        PreferencesDialog.saveValue(saveLoadKeys.NUMBER_OF_FREE_MULLIGANS + versionStr, Integer.toString(options.getFreeMulligans()));
        PreferencesDialog.saveValue(saveLoadKeys.MULLIGAN_TYPE + versionStr, options.getMulliganType().toString());
        PreferencesDialog.saveValue(saveLoadKeys.BOOL_CUSTOM_STARTING_LIFE + versionStr, options.isCustomStartLifeEnabled() ? "Yes" : "No");
        PreferencesDialog.saveValue(saveLoadKeys.NUMBER_OF_LIFE_AT_START + versionStr, Integer.toString(options.getCustomStartLife()));
        PreferencesDialog.saveValue(saveLoadKeys.BOOL_CUSTOM_STARTING_HAND_SIZE + versionStr, options.isCustomStartHandSizeEnabled() ? "Yes" : "No");
        PreferencesDialog.saveValue(saveLoadKeys.NUMBER_OF_HAND_SIZE_AT_START + versionStr, Integer.toString(options.getCustomStartHandSize()));
        PreferencesDialog.saveValue(saveLoadKeys.PLANECHASE + versionStr, options.isPlaneChase() ? "Yes" : "No");
        PreferencesDialog.saveValue(saveLoadKeys.EMBLEM_CARDS_ENABLED + versionStr,
                !(options.getGlobalEmblemCards().isEmpty() && options.getPerPlayerEmblemCards().isEmpty()) ? "Yes" : "No");
        PreferencesDialog.saveValue(saveLoadKeys.EMBLEM_CARDS_PER_PLAYER_FILE + versionStr, txtEmblemCardsPerPlayer.getText());
        PreferencesDialog.saveValue(saveLoadKeys.EMBLEM_CARDS_STARTING_PLAYER_FILE + versionStr, txtEmblemCardsStartingPlayer.getText());
    }

    /**
     * Applies this dialog's configured match options to a MatchOptions object.
     */
    public void writeMatchOptionsTo(MatchOptions options) {
        options.setFreeMulligans((Integer) spnFreeMulligans.getValue());
        options.setMullgianType((MulliganType) cbMulliganType.getSelectedItem());
        options.setCustomStartLifeEnabled(checkStartingLife.isSelected());
        options.setCustomStartLife((Integer) spnCustomLifeTotal.getValue());
        options.setCustomStartHandSizeEnabled(checkStartingHandSize.isSelected());
        options.setCustomStartHandSize((Integer) spnCustomStartingHand.getValue());
        options.setPlaneChase(chkPlaneChase.isSelected());
        if (chkEmblemCards.isSelected()) {
            if (!txtEmblemCardsPerPlayer.getText().isEmpty()) {
                Deck perPlayerEmblemDeck = null;
                try {
                    perPlayerEmblemDeck = Deck.load(DeckImporter.importDeckFromFile(txtEmblemCardsPerPlayer.getText(), true), true, true);
                } catch (GameException e1) {
                    JOptionPane.showMessageDialog(MageFrame.getDesktop(), e1.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
                }
                if (perPlayerEmblemDeck != null) {
                    perPlayerEmblemDeck.clearLayouts();
                    options.setPerPlayerEmblemCards(perPlayerEmblemDeck.getDeckCardLists().getCards());
                } else {
                    options.setPerPlayerEmblemCards(Collections.emptySet());
                }
            }
            if (!txtEmblemCardsStartingPlayer.getText().isEmpty()) {
                Deck startingPlayerEmblemDeck = null;
                try {
                    startingPlayerEmblemDeck = Deck.load(DeckImporter.importDeckFromFile(txtEmblemCardsStartingPlayer.getText(), true), true, true);
                } catch (GameException e1) {
                    JOptionPane.showMessageDialog(MageFrame.getDesktop(), e1.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
                }
                if (startingPlayerEmblemDeck != null) {
                    startingPlayerEmblemDeck.clearLayouts();
                    options.setGlobalEmblemCards(startingPlayerEmblemDeck.getDeckCardLists().getCards());
                } else {
                    options.setGlobalEmblemCards(Collections.emptySet());
                }
            }
        } else {
            options.setPerPlayerEmblemCards(Collections.emptySet());
            options.setGlobalEmblemCards(Collections.emptySet());
        }
    }

    public void updateActiveCount() {
        int activeCount = 0;
        if ((Integer) spnFreeMulligans.getValue() > 0) activeCount++;
        if (checkStartingLife.isSelected()) activeCount++;
        spnCustomLifeTotal.setEnabled(checkStartingLife.isSelected());
        if (checkStartingHandSize.isSelected()) activeCount++;
        spnCustomStartingHand.setEnabled(checkStartingHandSize.isSelected());
        if (!cbMulliganType.getSelectedItem().toString().equals(MulliganType.GAME_DEFAULT.toString())) activeCount++;
        if (chkPlaneChase.isSelected()) activeCount++;
        if (chkEmblemCards.isSelected()) activeCount++;
        if (activeCount == 0) {
            openButton.setText("Custom Options...");
        } else {
            openButton.setText("Custom Options (" + activeCount + ")");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmblemCardsPerPlayer;
    private javax.swing.JButton btnEmblemCardsStartingPlayer;
    private javax.swing.JButton btnOK;
    private javax.swing.JComboBox<String> cbMulliganType;
    private javax.swing.JCheckBox checkStartingHandSize;
    private javax.swing.JCheckBox checkStartingLife;
    private javax.swing.JCheckBox chkEmblemCards;
    private javax.swing.JCheckBox chkPlaneChase;
    private javax.swing.JLabel emblemCardsDescriptionLabel;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblEmblemCardsPerPlayer;
    private javax.swing.JLabel lblEmblemCardsStartingPlayer;
    private javax.swing.JLabel lblFreeMulligans;
    private javax.swing.JLabel lblGeneralOptions;
    private javax.swing.JLabel lblMulliganType;
    private javax.swing.JLabel lblVariantOptions;
    private javax.swing.JLabel planechaseDescriptionLabel;
    private javax.swing.JSpinner spnCustomLifeTotal;
    private javax.swing.JSpinner spnCustomStartingHand;
    private javax.swing.JSpinner spnFreeMulligans;
    private javax.swing.JTextField txtEmblemCardsPerPlayer;
    private javax.swing.JTextField txtEmblemCardsStartingPlayer;
    // End of variables declaration//GEN-END:variables
}
