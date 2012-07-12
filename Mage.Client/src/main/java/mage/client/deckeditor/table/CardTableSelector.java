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

package mage.client.deckeditor.table;

import mage.Constants.CardType;
import mage.MageObject;
import mage.ObjectColor;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.client.cards.BigCard;
import mage.client.cards.CardEventSource;
import mage.client.cards.CardsStorage;
import mage.client.cards.ICardGrid;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.constants.Constants.SortBy;
import mage.client.util.sets.ConstructedFormats;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.sets.Sets;
import mage.view.CardsView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class CardTableSelector extends javax.swing.JPanel implements ComponentListener {

    private final List<Card> cards = new ArrayList<Card>();
    private FilterCard filter = new FilterCard();
    private BigCard bigCard;
    protected CardEventSource cardEventSource = new CardEventSource();
    private DeckEditorMode mode = DeckEditorMode.Constructed;

    public CardTableSelector() {
        initComponents();
        this.addComponentListener(this);
        setOpaque(false);
        mainTable.setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        cbSortBy.setModel(new DefaultComboBoxModel(SortBy.values()));
        cbSortBy.setVisible(false);
        chkPiles.setVisible(false);

        mainTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    jButtonAddToMainActionPerformed(null);
                }
            }
        });

    }

    private void buildFilter() {
        filter = new FilterCard();
        ArrayList<Predicate<MageObject>> predicates = new ArrayList<Predicate<MageObject>>();

        if (this.rdoGreen.isSelected()) {
            predicates.add(new ColorPredicate(ObjectColor.GREEN));
        }
        if (this.rdoRed.isSelected()) {
            predicates.add(new ColorPredicate(ObjectColor.RED));
        }
        if (this.rdoBlack.isSelected()) {
            predicates.add(new ColorPredicate(ObjectColor.BLACK));
        }
        if (this.rdoBlue.isSelected()) {
            predicates.add(new ColorPredicate(ObjectColor.BLUE));
        }
        if (this.rdoWhite.isSelected()) {
            predicates.add(new ColorPredicate(ObjectColor.WHITE));
        }
        if (this.rdoColorless.isSelected()) {
            predicates.add(new ColorlessPredicate());
        }
        filter.add(Predicates.or(predicates));

        predicates.clear();
        if (this.rdoLand.isSelected()) {
            predicates.add(new CardTypePredicate(CardType.LAND));
        }
        if (this.rdoArtifacts.isSelected()) {
            predicates.add(new CardTypePredicate(CardType.ARTIFACT));
        }
        if (this.rdoCreatures.isSelected()) {
            predicates.add(new CardTypePredicate(CardType.CREATURE));
        }
        if (this.rdoEnchantments.isSelected()) {
            predicates.add(new CardTypePredicate(CardType.ENCHANTMENT));
        }
        if (this.rdoInstants.isSelected()) {
            predicates.add(new CardTypePredicate(CardType.INSTANT));
        }
        if (this.rdoSorceries.isSelected()) {
            predicates.add(new CardTypePredicate(CardType.SORCERY));
        }
        if (this.rdoPlaneswalkers.isSelected()) {
            predicates.add(new CardTypePredicate(CardType.PLANESWALKER));
        }
        filter.add(Predicates.or(predicates));

        String name = jTextFieldSearch.getText().trim();
        filter.setText(name);

        if (this.cbExpansionSet.getSelectedItem() instanceof ExpansionSet) {
            filter.getExpansionSetCode().add(((ExpansionSet) this.cbExpansionSet.getSelectedItem()).getCode());
        } else if (this.cbExpansionSet.getSelectedItem().equals("-- Standard")) {
            filter.getExpansionSetCode().addAll(ConstructedFormats.getSetsByFormat("Standard"));
        }
    }

    public void loadCards(List<Card> sideboard, BigCard bigCard, boolean construct) {
        this.bigCard = bigCard;
        this.btnBooster.setVisible(false);
        this.btnClear.setVisible(false);
        this.cbExpansionSet.setVisible(false);
        this.cards.clear();
        for (Card card: sideboard) {
            this.cards.add(card);
        }

        filterCards();
    }

    public void loadCards(BigCard bigCard) {
        this.bigCard = bigCard;
        this.btnBooster.setVisible(true);
        this.btnClear.setVisible(true);
        this.cbExpansionSet.setVisible(true);
        Object[] l = Sets.getInstance().values().toArray();
        Arrays.sort(l, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((ExpansionSet)o1).getName().compareTo(((ExpansionSet)o2).getName());
            }
        });
        cbExpansionSet.setModel(new DefaultComboBoxModel(l));
        cbExpansionSet.insertItemAt("-- All sets -- ", 0);
        cbExpansionSet.setSelectedIndex(0);

        filterCards();
    }

    private void filterCards() {
        buildFilter();
        try {
            List<Card> filteredCards = new ArrayList<Card>();
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if (!cards.isEmpty()) {
                for (Card card: cards) {
                    if (filter.match(card, null))
                        filteredCards.add(card);
                }
            }
            else {
                for (Card card: CardsStorage.getAllCards()) {
                    if (filter.match(card, null))
                        filteredCards.add(card);
                }
            }
            this.mainModel.loadCards(new CardsView(filteredCards), (SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected(), bigCard, null);
        }
        finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public ICardGrid getCardsList() {
        return this.mainModel;
    }

    public void removeCard(UUID cardId) {
        this.mainModel.removeCard(cardId);
        for (Card card: cards) {
            if (card.getId().equals(cardId)) {
                cards.remove(card);
                break;
            }
        }
    }

    public Card getCard(UUID cardId) {
        if (!cards.isEmpty()) {
            for (Card card: cards) {
                if (card.getId().equals(cardId))
                    return card;
            }
        }
        else {
            for (Card card: CardsStorage.getAllCards()) {
                if (card.getId().equals(cardId))
                    return card;
            }
        }
        return null;
    }

    private void initComponents() {

        tbColor = new javax.swing.JToolBar();
        rdoRed = new javax.swing.JRadioButton();
        rdoGreen = new javax.swing.JRadioButton();
        rdoBlue = new javax.swing.JRadioButton();
        rdoBlack = new javax.swing.JRadioButton();
        rdoWhite = new javax.swing.JRadioButton();
        rdoColorless = new javax.swing.JRadioButton();
        cbExpansionSet = new javax.swing.JComboBox();
        btnBooster = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainTable = new JTable();
        tbTypes = new javax.swing.JToolBar();
        rdoLand = new javax.swing.JRadioButton();
        rdoCreatures = new javax.swing.JRadioButton();
        rdoArtifacts = new javax.swing.JRadioButton();
        rdoEnchantments = new javax.swing.JRadioButton();
        rdoInstants = new javax.swing.JRadioButton();
        rdoSorceries = new javax.swing.JRadioButton();
        rdoPlaneswalkers = new javax.swing.JRadioButton();
        chkPiles = new javax.swing.JCheckBox();
        cbSortBy = new javax.swing.JComboBox();
        jTextFieldSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButtonSearch = new javax.swing.JButton();
        jButtonClean = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonAddToMain = new javax.swing.JButton();
        jButtonAddToSideboard = new javax.swing.JButton();

        tbColor.setFloatable(false);
        tbColor.setRollover(true);

        rdoRed.setSelected(true);
        rdoRed.setText("Red ");
        rdoRed.setFocusable(false);
        rdoRed.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoRed.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRedActionPerformed(evt);
            }
        });
        tbColor.add(rdoRed);

        rdoGreen.setSelected(true);
        rdoGreen.setText("Green ");
        rdoGreen.setFocusable(false);
        rdoGreen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoGreen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGreenActionPerformed(evt);
            }
        });
        tbColor.add(rdoGreen);

        rdoBlue.setSelected(true);
        rdoBlue.setText("Blue ");
        rdoBlue.setFocusable(false);
        rdoBlue.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoBlue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBlueActionPerformed(evt);
            }
        });
        tbColor.add(rdoBlue);

        rdoBlack.setSelected(true);
        rdoBlack.setText("Black ");
        rdoBlack.setFocusable(false);
        rdoBlack.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoBlack.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBlackActionPerformed(evt);
            }
        });
        tbColor.add(rdoBlack);

        rdoWhite.setSelected(true);
        rdoWhite.setText("White ");
        rdoWhite.setFocusable(false);
        rdoWhite.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoWhite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoWhiteActionPerformed(evt);
            }
        });
        tbColor.add(rdoWhite);

        rdoColorless.setSelected(true);
        rdoColorless.setText("Colorless ");
        rdoColorless.setFocusable(false);
        rdoColorless.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoColorless.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoColorless.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoColorlessActionPerformed(evt);
            }
        });
        tbColor.add(rdoColorless);

        cbExpansionSet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbExpansionSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbExpansionSetActionPerformed(evt);
            }
        });
        tbColor.add(cbExpansionSet);

        btnBooster.setText("Open Booster");
        btnBooster.setFocusable(false);
        btnBooster.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBooster.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBooster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoosterActionPerformed(evt);
            }
        });
        tbColor.add(btnBooster);

        btnClear.setText("Clear");
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        tbColor.add(btnClear);

        mainModel = new TableModel();
        mainModel.addListeners(mainTable);

        mainTable.setModel(mainModel);
        mainTable.setForeground(Color.white);
        DefaultTableCellRenderer myRenderer = (DefaultTableCellRenderer) mainTable.getDefaultRenderer(String.class);
        myRenderer.setBackground(new Color(0, 0, 0, 100));
        mainTable.getColumnModel().getColumn(0).setMaxWidth(0);
        mainTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        mainTable.getColumnModel().getColumn(1).setPreferredWidth(110);
        mainTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        mainTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        mainTable.getColumnModel().getColumn(4).setPreferredWidth(170);
        mainTable.getColumnModel().getColumn(5).setPreferredWidth(30);
        mainTable.getColumnModel().getColumn(6).setPreferredWidth(15);
        mainTable.getColumnModel().getColumn(7).setPreferredWidth(15);

        jScrollPane1.setViewportView(mainTable);

        tbTypes.setFloatable(false);
        tbTypes.setRollover(true);

        rdoLand.setSelected(true);
        rdoLand.setFocusable(false);
        rdoLand.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoLand.setLabel("Land ");
        rdoLand.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoLandActionPerformed(evt);
            }
        });
        tbTypes.add(rdoLand);

        rdoCreatures.setSelected(true);
        rdoCreatures.setFocusable(false);
        rdoCreatures.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoCreatures.setLabel("Creatures ");
        rdoCreatures.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoCreatures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCreaturesActionPerformed(evt);
            }
        });
        tbTypes.add(rdoCreatures);

        rdoArtifacts.setSelected(true);
        rdoArtifacts.setText("Artifacts ");
        rdoArtifacts.setFocusable(false);
        rdoArtifacts.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoArtifacts.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoArtifacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoArtifactsActionPerformed(evt);
            }
        });
        tbTypes.add(rdoArtifacts);

        rdoEnchantments.setSelected(true);
        rdoEnchantments.setText("Enchantments ");
        rdoEnchantments.setFocusable(false);
        rdoEnchantments.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoEnchantments.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoEnchantments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoEnchantmentsActionPerformed(evt);
            }
        });
        tbTypes.add(rdoEnchantments);

        rdoInstants.setSelected(true);
        rdoInstants.setText("Instants ");
        rdoInstants.setFocusable(false);
        rdoInstants.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoInstants.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoInstants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoInstantsActionPerformed(evt);
            }
        });
        tbTypes.add(rdoInstants);

        rdoSorceries.setSelected(true);
        rdoSorceries.setText("Sorceries ");
        rdoSorceries.setFocusable(false);
        rdoSorceries.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoSorceries.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoSorceries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSorceriesActionPerformed(evt);
            }
        });
        tbTypes.add(rdoSorceries);

        rdoPlaneswalkers.setSelected(true);
        rdoPlaneswalkers.setText("Planeswalkers ");
        rdoPlaneswalkers.setFocusable(false);
        rdoPlaneswalkers.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoPlaneswalkers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoPlaneswalkers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPlaneswalkersActionPerformed(evt);
            }
        });
        tbTypes.add(rdoPlaneswalkers);

        chkPiles.setText("Piles");
        chkPiles.setFocusable(false);
        chkPiles.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkPiles.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chkPiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPilesActionPerformed(evt);
            }
        });
        tbTypes.add(chkPiles);

        cbSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSortBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortByActionPerformed(evt);
            }
        });
        tbTypes.add(cbSortBy);

        jLabel1.setText("Search (by name,in rules):");

        jButtonSearch.setLabel("Search");
        jButtonSearch.setMaximumSize(new java.awt.Dimension(85, 23));
        jButtonSearch.setMinimumSize(new java.awt.Dimension(85, 23));
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButtonClean.setText("Clear");
        jButtonClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCleanActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButtonAddToMain.setLabel("Add to Main");
        jButtonAddToMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToMainActionPerformed(evt);
            }
        });

        jButtonAddToSideboard.setLabel("Add to Sideboard");
        jButtonAddToSideboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToSideboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tbColor, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                .addComponent(tbTypes, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonAddToMain, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonAddToSideboard)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClean)
                        .addContainerGap(322, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonAddToMain)
                        .addComponent(jButtonAddToSideboard))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonClean)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }

    private void rdoGreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGreenActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoGreenActionPerformed

    private void rdoBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBlackActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoBlackActionPerformed

    private void rdoWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoWhiteActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoWhiteActionPerformed

    private void rdoRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRedActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoRedActionPerformed

    private void rdoBlueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBlueActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoBlueActionPerformed

    private void rdoColorlessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoColorlessActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoColorlessActionPerformed

    private void rdoLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoLandActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoLandActionPerformed

    private void rdoCreaturesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCreaturesActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoCreaturesActionPerformed

    private void rdoArtifactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoArtifactsActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoArtifactsActionPerformed

    private void rdoEnchantmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoEnchantmentsActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoEnchantmentsActionPerformed

    private void rdoInstantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoInstantsActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoInstantsActionPerformed

    private void rdoSorceriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSorceriesActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoSorceriesActionPerformed

    private void rdoPlaneswalkersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPlaneswalkersActionPerformed
        filterCards();
    }//GEN-LAST:event_rdoPlaneswalkersActionPerformed

    private void cbExpansionSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbExpansionSetActionPerformed
        filterCards();
    }//GEN-LAST:event_cbExpansionSetActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        cards.clear();
        filterCards();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnBoosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoosterActionPerformed
        if (this.cbExpansionSet.getSelectedItem() instanceof ExpansionSet) {
            List<Card> booster = ((ExpansionSet)this.cbExpansionSet.getSelectedItem()).createBooster();
            for (Card card: booster) {
                cards.add(card);
            }
            filterCards();
        }
    }//GEN-LAST:event_btnBoosterActionPerformed

    private void cbSortByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortByActionPerformed
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.mainModel.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }//GEN-LAST:event_cbSortByActionPerformed

    private void chkPilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPilesActionPerformed
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.mainModel.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }//GEN-LAST:event_chkPilesActionPerformed

    private void jButtonAddToMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (mainTable.getSelectedRowCount() > 0) {
            int[] n = mainTable.getSelectedRows();
            List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                mainModel.doubleClick(index);
            }
            if (!mode.equals(DeckEditorMode.Constructed))
                mainModel.fireTableDataChanged();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonAddToSideboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (mainTable.getSelectedRowCount() > 0) {
            int[] n = mainTable.getSelectedRows();
            List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                mainModel.shiftDoubleClick(index);
            }
            if (!mode.equals(DeckEditorMode.Constructed))
                mainModel.fireTableDataChanged();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    public List<Integer> asList(final int[] is) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i : is) list.add(i);
        return list;
    }

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        filterCards();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextFieldSearch.setText("");
        filterCards();
    }//GEN-LAST:event_jButton2ActionPerformed

    private TableModel mainModel;

    private javax.swing.JButton btnBooster;
    private javax.swing.JButton btnClear;
    private JTable mainTable = new JTable();
    private javax.swing.JComboBox cbExpansionSet;
    private javax.swing.JComboBox cbSortBy;
    private javax.swing.JCheckBox chkPiles;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonClean;
    private javax.swing.JButton jButtonAddToMain;
    private javax.swing.JButton jButtonAddToSideboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JRadioButton rdoArtifacts;
    private javax.swing.JRadioButton rdoBlack;
    private javax.swing.JRadioButton rdoBlue;
    private javax.swing.JRadioButton rdoColorless;
    private javax.swing.JRadioButton rdoCreatures;
    private javax.swing.JRadioButton rdoEnchantments;
    private javax.swing.JRadioButton rdoGreen;
    private javax.swing.JRadioButton rdoInstants;
    private javax.swing.JRadioButton rdoLand;
    private javax.swing.JRadioButton rdoPlaneswalkers;
    private javax.swing.JRadioButton rdoRed;
    private javax.swing.JRadioButton rdoSorceries;
    private javax.swing.JRadioButton rdoWhite;
    private javax.swing.JToolBar tbColor;
    private javax.swing.JToolBar tbTypes;

    @Override
    public void componentResized(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.mainModel.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.mainModel.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

    @Override
    public void componentShown(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.mainModel.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.mainModel.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

    public void setMode(DeckEditorMode mode) {
        this.mode = mode;
    }
}
