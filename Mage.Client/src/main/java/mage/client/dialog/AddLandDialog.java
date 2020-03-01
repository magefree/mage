package mage.client.dialog;

import mage.Mana;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.cards.decks.Deck;
import mage.cards.repository.*;
import mage.client.MageFrame;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.util.Localizer;
import mage.client.util.gui.FastSearchUtil;
import mage.constants.Rarity;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddLandDialog extends MageDialog {

    private static final Logger logger = Logger.getLogger(MageDialog.class);

    private Deck deck;

    private static final int DEFAULT_SEALED_DECK_CARD_NUMBER = 40;

    public AddLandDialog() {
        initComponents();
        this.setModal(true);
    }

    private boolean setHaveSnowLands(ExpansionInfo exp) {
        return CardRepository.instance.haveSnowLands(exp.getCode());
    }

    public void showDialog(Deck deck, DeckEditorMode mode) {
        this.deck = deck;
        SortedSet<String> landSetNames = new TreeSet<>();
        String defaultSetName = null;
        if (mode != DeckEditorMode.FREE_BUILDING) {
            // decide from which sets basic lands are taken from
            for (String setCode : deck.getExpansionSetCodes()) {
                ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                if (expansionInfo != null && expansionInfo.hasBasicLands() && !setHaveSnowLands(expansionInfo)) {
                    defaultSetName = expansionInfo.getName();
                    break;
                }
            }

            // if sets have no basic land, take land from block
            if (defaultSetName == null) {
                for (String setCode : deck.getExpansionSetCodes()) {
                    ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                    if (expansionInfo != null) {
                        List<ExpansionInfo> blockSets = ExpansionRepository.instance.getSetsFromBlock(expansionInfo.getBlockName());
                        for (ExpansionInfo blockSet : blockSets) {
                            if (blockSet.hasBasicLands() && !setHaveSnowLands(expansionInfo)) {
                                defaultSetName = expansionInfo.getName();
                                break;
                            }
                        }
                    }
                }
            }
        }
        // if still no set with lands found, add list of all available
        List<ExpansionInfo> basicLandSets = ExpansionRepository.instance.getSetsWithBasicLandsByReleaseDate();
        for (ExpansionInfo expansionInfo : basicLandSets) {
            // snow lands only in free mode
            if (mode != DeckEditorMode.FREE_BUILDING && setHaveSnowLands(expansionInfo)) {
                continue;
            }
            landSetNames.add(expansionInfo.getName());
        }
        if (landSetNames.isEmpty()) {
            throw new IllegalArgumentException("No set with basic land was found");
        }
        if (landSetNames.size() > 1) {
            landSetNames.add("<Random lands>");
        }
        cbLandSet.setModel(new DefaultComboBoxModel(landSetNames.toArray()));
        if (defaultSetName != null) {
            String item;
            for (int i = 0; i < cbLandSet.getItemCount(); i++) {
                item = (String) cbLandSet.getItemAt(i);
                if (item.equalsIgnoreCase(defaultSetName)) {
                    cbLandSet.setSelectedIndex(i);
                    break;
                }
            }

        }

        spnDeckSize.setValue(DEFAULT_SEALED_DECK_CARD_NUMBER);
        BufferedImage image = ManaSymbols.getSizedManaSymbol("G", 15);
        if (image != null) {
            lblForestIcon.setIcon(new ImageIcon(image));
        }
        image = ManaSymbols.getSizedManaSymbol("U", 15);
        if (image != null) {
            lblIslandIcon.setIcon(new ImageIcon(image));
        }
        image = ManaSymbols.getSizedManaSymbol("W", 15);
        if (image != null) {
            lblPlainsIcon.setIcon(new ImageIcon(image));
        }
        image = ManaSymbols.getSizedManaSymbol("R", 15);
        if (image != null) {
            lblMountainIcon.setIcon(new ImageIcon(image));
        }
        image = ManaSymbols.getSizedManaSymbol("B", 15);
        if (image != null) {
            lblSwampIcon.setIcon(new ImageIcon(image));
        }

        getRootPane().setDefaultButton(btnOK);

        // windows settings
        MageFrame.getDesktop().remove(this);
        if (this.isModal()) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        } else {
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }
        this.makeWindowCentered();

        // Close on "ESC"
        registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setVisible(true);
    }

    private void addLands(String landName, int number, boolean useFullArt) {
        String landSetName = (String) cbLandSet.getSelectedItem();

        CardCriteria criteria = new CardCriteria();
        if (!landSetName.equals("<Random lands>")) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByName(landSetName);
            if (expansionInfo == null) {
                throw new IllegalArgumentException("Code of Set " + landSetName + " not found");
            }
            criteria.setCodes(expansionInfo.getCode());
        }
        criteria.rarities(Rarity.LAND).name(landName);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);
        if (cards.isEmpty()) {
            logger.error("No basic lands found in Set: " + landSetName);
            criteria = new CardCriteria();
            criteria.rarities(Rarity.LAND).name(landName);
            criteria.setCodes("M15");
            cards = CardRepository.instance.findCards(criteria);
        }

        int foundLands = 0;
        int foundNoneAfter = 0;
        for (int i = 0; foundLands != number && foundNoneAfter < 1000; i++) {
            Card land = cards.get(RandomUtil.nextInt(cards.size())).getMockCard();
            boolean useLand = !useFullArt;
            if (useFullArt && (land.getFrameStyle() == FrameStyle.BFZ_FULL_ART_BASIC
                    || land.getFrameStyle() == FrameStyle.UGL_FULL_ART_BASIC
                    || land.getFrameStyle() == FrameStyle.UNH_FULL_ART_BASIC
                    || land.getFrameStyle() == FrameStyle.UST_FULL_ART_BASIC
                    || land.getFrameStyle() == FrameStyle.ZEN_FULL_ART_BASIC)) {
                useLand = true;
            }
            if (useLand) {
                deck.getCards().add(land);
                foundLands++;
                foundNoneAfter = 0;
            } else {
                foundNoneAfter++;
            }
        }

        if (foundNoneAfter >= 1000 && useFullArt) {
            MageFrame.getInstance().showMessage(Localizer.getInstance().getMessage("lblUnableAddEnoughLands", landName, landSetName));
        }
    }

    private void onCancel() {
        this.removeDialog();
    }

    private void onOK() {
        int nForest = ((Number) spnForest.getValue()).intValue();
        int nIsland = ((Number) spnIsland.getValue()).intValue();
        int nMountain = ((Number) spnMountain.getValue()).intValue();
        int nPlains = ((Number) spnPlains.getValue()).intValue();
        int nSwamp = ((Number) spnSwamp.getValue()).intValue();
        boolean useFullArt = ckbFullArtLands.isSelected();

        addLands("Forest", nForest, useFullArt);
        addLands("Island", nIsland, useFullArt);
        addLands("Mountain", nMountain, useFullArt);
        addLands("Plains", nPlains, useFullArt);
        addLands("Swamp", nSwamp, useFullArt);

        this.removeDialog();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblLandSet = new javax.swing.JLabel();
        lblForest = new javax.swing.JLabel();
        spnForest = new javax.swing.JSpinner();
        lblForestIcon = new javax.swing.JLabel();
        lblIsland = new javax.swing.JLabel();
        spnIsland = new javax.swing.JSpinner();
        lblIslandIcon = new javax.swing.JLabel();
        lblMountain = new javax.swing.JLabel();
        spnMountain = new javax.swing.JSpinner();
        lblMountainIcon = new javax.swing.JLabel();
        lblPains = new javax.swing.JLabel();
        spnPlains = new javax.swing.JSpinner();
        lblPlainsIcon = new javax.swing.JLabel();
        lblSwamp = new javax.swing.JLabel();
        spnSwamp = new javax.swing.JSpinner();
        lblSwampIcon = new javax.swing.JLabel();
        lblDeckSize = new javax.swing.JLabel();
        spnDeckSize = new javax.swing.JSpinner();
        btnAutoAdd = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panelSet = new javax.swing.JPanel();
        cbLandSet = new javax.swing.JComboBox();
        btnSetFastSearch = new javax.swing.JButton();
        ckbFullArtLands = new javax.swing.JCheckBox();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("languages/Bundle"); // NOI18N
        jButton2.setText(bundle.getString("AddLandDialog.jButton2.text")); // NOI18N

        jLabel1.setText(bundle.getString("AddLandDialog.jLabel1.text")); // NOI18N

        setTitle(bundle.getString("AddLandDialog.title")); // NOI18N

        lblLandSet.setText(bundle.getString("AddLandDialog.lblLandSet.text")); // NOI18N

        lblForest.setText(bundle.getString("AddLandDialog.lblForest.text")); // NOI18N

        spnForest.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblForestIcon.setToolTipText(bundle.getString("AddLandDialog.lblForestIcon.toolTipText")); // NOI18N
        lblForestIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblForestIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblForestIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblIsland.setText(bundle.getString("AddLandDialog.lblIsland.text")); // NOI18N

        spnIsland.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblIslandIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblIslandIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblIslandIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblMountain.setText(bundle.getString("AddLandDialog.lblMountain.text")); // NOI18N

        spnMountain.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblMountainIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblMountainIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblMountainIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblPains.setText(bundle.getString("AddLandDialog.lblPains.text")); // NOI18N

        spnPlains.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblPlainsIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblPlainsIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblPlainsIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblSwamp.setText(bundle.getString("AddLandDialog.lblSwamp.text")); // NOI18N

        spnSwamp.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblSwampIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblSwampIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblSwampIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblDeckSize.setText(bundle.getString("AddLandDialog.lblDeckSize.text")); // NOI18N

        spnDeckSize.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        btnAutoAdd.setText(bundle.getString("AddLandDialog.btnAutoAdd.text")); // NOI18N
        btnAutoAdd.setToolTipText(bundle.getString("AddLandDialog.btnAutoAdd.toolTipText")); // NOI18N
        btnAutoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoAddActionPerformed(evt);
            }
        });

        btnOK.setText(bundle.getString("AddLandDialog.btnOK.text")); // NOI18N
        btnOK.setToolTipText(bundle.getString("AddLandDialog.btnOK.toolTipText")); // NOI18N
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel.setText(bundle.getString("AddLandDialog.btnCancel.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        panelSet.setLayout(new javax.swing.BoxLayout(panelSet, javax.swing.BoxLayout.LINE_AXIS));

        cbLandSet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbLandSet.setMinimumSize(new java.awt.Dimension(20, 20));
        panelSet.add(cbLandSet);

        btnSetFastSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_24.png"))); // NOI18N
        btnSetFastSearch.setToolTipText(bundle.getString("AddLandDialog.btnSetFastSearch.toolTipText")); // NOI18N
        btnSetFastSearch.setAlignmentX(1.0F);
        btnSetFastSearch.setPreferredSize(new java.awt.Dimension(23, 23));
        btnSetFastSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetFastSearchActionPerformed(evt);
            }
        });
        panelSet.add(btnSetFastSearch);

        ckbFullArtLands.setText(bundle.getString("AddLandDialog.ckbFullArtLands.text")); // NOI18N
        ckbFullArtLands.setToolTipText(bundle.getString("AddLandDialog.ckbFullArtLands.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblForest)
                    .addComponent(lblLandSet)
                    .addComponent(lblIsland)
                    .addComponent(lblPains)
                    .addComponent(lblSwamp)
                    .addComponent(lblDeckSize)
                    .addComponent(lblMountain))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addComponent(ckbFullArtLands)
                        .addComponent(panelSet, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(spnForest, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblForestIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(spnIsland, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblIslandIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(spnMountain, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblMountainIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(spnSwamp, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblSwampIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(spnDeckSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnAutoAdd)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spnPlains, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPlainsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLandSet)
                    .addComponent(panelSet, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblForestIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblForest)
                        .addComponent(spnForest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblIsland)
                        .addComponent(spnIsland, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblIslandIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMountain)
                        .addComponent(spnMountain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMountainIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPains)
                        .addComponent(spnPlains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPlainsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSwamp)
                        .addComponent(spnSwamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSwampIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckbFullArtLands)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAutoAdd)
                    .addComponent(lblDeckSize)
                    .addComponent(spnDeckSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        onCancel();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        onOK();
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnAutoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoAddActionPerformed
        autoAddLands();
    }//GEN-LAST:event_btnAutoAddActionPerformed

    private void btnSetFastSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetFastSearchActionPerformed
        FastSearchUtil.showFastSearchForStringComboBox(cbLandSet, FastSearchUtil.DEFAULT_EXPANSION_SEARCH_MESSAGE);
    }//GEN-LAST:event_btnSetFastSearchActionPerformed

    private void autoAddLands() {
        int red = 0;
        int green = 0;
        int black = 0;
        int blue = 0;
        int white = 0;
        Set<Card> cards = deck.getCards();
        int land_number = ((Number) spnDeckSize.getValue()).intValue() - cards.size();
        if (land_number < 0) {
            land_number = 0;
        }
        for (Card cd : cards) {
            Mana m = cd.getManaCost().getMana();
            red += m.getRed();
            green += m.getGreen();
            black += m.getBlack();
            blue += m.getBlue();
            white += m.getWhite();
        }
        int total = red + green + black + blue + white;

        int redcards = 0;
        int greencards = 0;
        int blackcards = 0;
        int bluecards = 0;
        int whitecards = 0;
        if (total > 0) {
            redcards = Math.round(land_number * ((float) red / (float) total));
            total -= red;
            land_number -= redcards;

            greencards = Math.round(land_number * ((float) green / (float) total));
            total -= green;
            land_number -= greencards;

            blackcards = Math.round(land_number * ((float) black / (float) total));
            total -= black;
            land_number -= blackcards;

            bluecards = Math.round(land_number * ((float) blue / (float) total));
            total -= blue;
            land_number -= bluecards;

            whitecards = land_number;
        }

        spnMountain.setValue(redcards);
        spnForest.setValue(greencards);
        spnSwamp.setValue(blackcards);
        spnIsland.setValue(bluecards);
        spnPlains.setValue(whitecards);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutoAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnSetFastSearch;
    private javax.swing.JComboBox cbLandSet;
    private javax.swing.JCheckBox ckbFullArtLands;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDeckSize;
    private javax.swing.JLabel lblForest;
    private javax.swing.JLabel lblForestIcon;
    private javax.swing.JLabel lblIsland;
    private javax.swing.JLabel lblIslandIcon;
    private javax.swing.JLabel lblLandSet;
    private javax.swing.JLabel lblMountain;
    private javax.swing.JLabel lblMountainIcon;
    private javax.swing.JLabel lblPains;
    private javax.swing.JLabel lblPlainsIcon;
    private javax.swing.JLabel lblSwamp;
    private javax.swing.JLabel lblSwampIcon;
    private javax.swing.JPanel panelSet;
    private javax.swing.JSpinner spnDeckSize;
    private javax.swing.JSpinner spnForest;
    private javax.swing.JSpinner spnIsland;
    private javax.swing.JSpinner spnMountain;
    private javax.swing.JSpinner spnPlains;
    private javax.swing.JSpinner spnSwamp;
    // End of variables declaration//GEN-END:variables

}
