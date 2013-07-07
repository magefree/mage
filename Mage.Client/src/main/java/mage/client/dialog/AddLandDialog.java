/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.client.dialog;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JLayeredPane;
import mage.Mana;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.constants.Rarity;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AddLandDialog extends MageDialog {

    private Deck deck;
    private Set<String> setCodesland = new HashSet<String>();
    
    private static int DEFAULT_SEALED_DECK_CARD_NUMBER = 40;

    /** Creates new form AddLandDialog */
    public AddLandDialog() {
        initComponents();
        this.setModal(true);
    }

    public void showDialog(Deck deck) {
        this.deck = deck;

        // decide from which sets basic lands are taken from
        for (String setCode :deck.getExpansionSetCodes()) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
            if (expansionInfo.hasBasicLands()) {
                this.setCodesland.add(expansionInfo.getCode());
            }
        }

        // if sets have no basic land, take land from block
        if (this.setCodesland.isEmpty()) {
            for (String setCode :deck.getExpansionSetCodes()) {
                ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                ExpansionInfo [] blockSets = ExpansionRepository.instance.getSetsFromBlock(expansionInfo.getBlockName());
                for (ExpansionInfo blockSet: blockSets) {
                    if (blockSet.hasBasicLands()) {
                        this.setCodesland.add(blockSet.getCode());
                    }
                }
            }
        }
        // if still no set with lands found, take one by random
        if (this.setCodesland.isEmpty()) {
            // if sets have no basic lands and also it has no parent or parent has no lands get last set with lands
            // select a set with basic lands by random
            Random generator = new Random();
            ExpansionInfo [] landSets = ExpansionRepository.instance.getSetsWithBasicLandsByReleaseDate();
            if (landSets.length > 0) {
                this.setCodesland.add(landSets[generator.nextInt(landSets.length)-1].getCode());
            }
        }

        if (this.setCodesland.isEmpty()) {
            throw new IllegalArgumentException("No set with basic land was found");
        }
        
        MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        this.setVisible(true);
    }

    private void addLands(String landName, int number) {
        Random random = new Random();
        CardCriteria criteria = new CardCriteria();
        if (!setCodesland.isEmpty()) {
            criteria.setCodes(setCodesland.toArray(new String[setCodesland.size()]));
        }
        criteria.rarities(Rarity.LAND).name(landName);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);
        if (cards.isEmpty()) {
            return;
        }

        for (int i = 0; i < number; i++) {
            Card land = cards.get(random.nextInt(cards.size())).getMockCard();
            deck.getCards().add(land);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        lblForest = new javax.swing.JLabel();
        spnForest = new javax.swing.JSpinner();
        spnIsland = new javax.swing.JSpinner();
        lblIsland = new javax.swing.JLabel();
        lblPains = new javax.swing.JLabel();
        spnPlains = new javax.swing.JSpinner();
        spnMountain = new javax.swing.JSpinner();
        lblMountain = new javax.swing.JLabel();
        spnSwamp = new javax.swing.JSpinner();
        lblSwamp = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnAutoAdd = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setTitle("Add Land");

        lblForest.setText("Forest");

        spnForest.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        spnIsland.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        lblIsland.setText("Island");

        lblPains.setText("Plains");

        spnPlains.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        spnMountain.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        lblMountain.setText("Mountain");

        spnSwamp.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        lblSwamp.setText("Swamp");

        btnAdd.setText("Add");
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

        btnAutoAdd.setText("Suggest");
        btnAutoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIsland)
                                    .addComponent(lblMountain)
                                    .addComponent(lblForest))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spnMountain, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(spnIsland)
                                    .addComponent(spnForest)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblPains)
                                .addGap(21, 21, 21)
                                .addComponent(spnPlains))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblSwamp)
                                .addGap(14, 14, 14)
                                .addComponent(spnSwamp)))
                        .addGap(114, 114, 114))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAutoAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAdd)
                        .addGap(0, 40, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblForest)
                    .addComponent(spnForest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIsland)
                    .addComponent(spnIsland, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMountain)
                    .addComponent(spnMountain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPains)
                    .addComponent(spnPlains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSwamp)
                    .addComponent(spnSwamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnCancel)
                    .addComponent(btnAutoAdd))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.hideDialog();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int nForest = ((Number)spnForest.getValue()).intValue();
        int nIsland = ((Number)spnIsland.getValue()).intValue();
        int nMountain = ((Number)spnMountain.getValue()).intValue();
        int nPlains = ((Number)spnPlains.getValue()).intValue();
        int nSwamp = ((Number)spnSwamp.getValue()).intValue();

        addLands("Forest", nForest);
        addLands("Island", nIsland);
        addLands("Mountain", nMountain);
        addLands("Plains", nPlains);
        addLands("Swamp", nSwamp);
        this.hideDialog();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAutoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoAddActionPerformed
        autoAddLands();
    }//GEN-LAST:event_btnAutoAddActionPerformed

    private void autoAddLands(){
        int red = 0;
        int green = 0;
        int black = 0;
        int blue = 0;
        int white = 0;
        Set<Card> cards = deck.getCards();
        int land_number = DEFAULT_SEALED_DECK_CARD_NUMBER - cards.size();
        if(land_number < 0) land_number = 0;
        for (Card cd : cards) {
            Mana m = cd.getManaCost().getMana();
            red += m.getRed();
            green += m.getGreen();
            black += m.getBlack();
            blue += m.getBlue();
            white += m.getWhite();
        }
        int total = red + green + black + blue + white;
        int redcards = Math.round(land_number*((float)red/(float)total));
        total -= red;   land_number -= redcards;
        int greencards = Math.round(land_number*((float)green/(float)total));
        total -= green;   land_number -= greencards;
        int blackcards = Math.round(land_number*((float)black/(float)total));
        total -= black;   land_number -= blackcards;
        int bluecards = Math.round(land_number*((float)blue/(float)total));
        total -= blue;   land_number -= bluecards;
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
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lblForest;
    private javax.swing.JLabel lblIsland;
    private javax.swing.JLabel lblMountain;
    private javax.swing.JLabel lblPains;
    private javax.swing.JLabel lblSwamp;
    private javax.swing.JSpinner spnForest;
    private javax.swing.JSpinner spnIsland;
    private javax.swing.JSpinner spnMountain;
    private javax.swing.JSpinner spnPlains;
    private javax.swing.JSpinner spnSwamp;
    // End of variables declaration//GEN-END:variables

}
