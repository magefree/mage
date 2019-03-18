
package mage.client.dialog;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import mage.Mana;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.cards.decks.Deck;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.util.gui.FastSearchUtil;
import mage.constants.Rarity;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;
import org.mage.card.arcane.ManaSymbols;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AddLandDialog extends MageDialog {

    private static final Logger logger = Logger.getLogger(MageDialog.class);

    private Deck deck;

    private static final int DEFAULT_SEALED_DECK_CARD_NUMBER = 40;

    /**
     * Creates new form AddLandDialog
     */
    public AddLandDialog() {
        initComponents();
        this.setModal(true);
    }

    public void showDialog(Deck deck, DeckEditorMode mode) {
        this.deck = deck;
        SortedSet<String> landSetNames = new TreeSet<>();
        String defaultSetName = null;
        if (mode != DeckEditorMode.FREE_BUILDING) {
            // decide from which sets basic lands are taken from
            for (String setCode : deck.getExpansionSetCodes()) {
                ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                if (expansionInfo != null && expansionInfo.hasBasicLands()) {
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
                            if (blockSet.hasBasicLands()) {
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

        // windows settings
        if (this.isModal()) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        } else {
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
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
            MageFrame.getInstance().showMessage("Unable to add enough " + landName + "s.  You encountered an error in adding chosen lands.  Unable to find enough full art lands in the set " + landSetName + ".");
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
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panelSet = new javax.swing.JPanel();
        cbLandSet = new javax.swing.JComboBox();
        btnSetFastSearch = new javax.swing.JButton();
        ckbFullArtLands = new javax.swing.JCheckBox();

        jButton2.setText("jButton2");

        jLabel1.setText("jLabel1");

        setTitle("Add Land");

        lblLandSet.setText("Set:");

        lblForest.setText("Forest:");

        spnForest.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblForestIcon.setToolTipText("");
        lblForestIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblForestIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblForestIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblIsland.setText("Island:");

        spnIsland.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblIslandIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblIslandIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblIslandIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblMountain.setText("Mountain:");

        spnMountain.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblMountainIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblMountainIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblMountainIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblPains.setText("Plains:");

        spnPlains.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblPlainsIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblPlainsIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblPlainsIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblSwamp.setText("Swamp:");

        spnSwamp.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblSwampIcon.setMaximumSize(new java.awt.Dimension(22, 20));
        lblSwampIcon.setMinimumSize(new java.awt.Dimension(22, 20));
        lblSwampIcon.setPreferredSize(new java.awt.Dimension(22, 20));

        lblDeckSize.setText("Deck size:");

        spnDeckSize.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        btnAutoAdd.setText("Suggest");
        btnAutoAdd.setToolTipText("<HTML>Propose related to the mana costs of the cards in the deck<br>\nthe number of lands to add to get to the set deck size.");
        btnAutoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoAddActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.setToolTipText("Add the selected number of basic lands to the deck.");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
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
        btnSetFastSearch.setToolTipText("Search for set");
        btnSetFastSearch.setAlignmentX(1.0F);
        btnSetFastSearch.setPreferredSize(new java.awt.Dimension(23, 23));
        btnSetFastSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetFastSearchActionPerformed(evt);
            }
        });
        panelSet.add(btnSetFastSearch);

        ckbFullArtLands.setText("Only use full art lands");
        ckbFullArtLands.setToolTipText("For example, lands from ZEN/UST/HOU");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblMountain)
                        .addComponent(lblForest, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblLandSet, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblIsland, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblPains, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblSwamp, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(lblDeckSize))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(btnAdd)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCancel)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblForest)
                    .addComponent(spnForest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblForestIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(btnAdd)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.removeDialog();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
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
    }//GEN-LAST:event_btnAddActionPerformed

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
        int redcards = Math.round(land_number * ((float) red / (float) total));
        total -= red;
        land_number -= redcards;
        int greencards = Math.round(land_number * ((float) green / (float) total));
        total -= green;
        land_number -= greencards;
        int blackcards = Math.round(land_number * ((float) black / (float) total));
        total -= black;
        land_number -= blackcards;
        int bluecards = Math.round(land_number * ((float) blue / (float) total));
        total -= blue;
        land_number -= bluecards;
        int whitecards = land_number;
        spnMountain.setValue(redcards);
        spnForest.setValue(greencards);
        spnSwamp.setValue(blackcards);
        spnIsland.setValue(bluecards);
        spnPlains.setValue(whitecards);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAutoAdd;
    private javax.swing.JButton btnCancel;
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
