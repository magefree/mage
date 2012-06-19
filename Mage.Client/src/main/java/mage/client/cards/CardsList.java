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
 * CardsList.java
 *
 * Created on Dec 18, 2009, 10:40:12 AM
 */

package mage.client.cards;

import mage.Constants.CardType;
import mage.cards.MageCard;
import mage.client.constants.Constants.SortBy;
import mage.client.deckeditor.table.TableModel;
import mage.client.deckeditor.table.UpdateCountsCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.*;
import mage.client.util.Event;
import mage.view.CardView;
import mage.view.CardsView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardsList extends javax.swing.JPanel implements MouseListener, ICardGrid {

    protected CardEventSource cardEventSource = new CardEventSource();
    private Dimension cardDimension;
    private CardsView cards;
    protected BigCard bigCard;
    protected UUID gameId;

    private TableModel mainModel;
    private JTable mainTable;
    private ICardGrid currentView;

    /** Creates new form Cards */
    public CardsList() {
        initComponents();
        makeTransparent();
        initListViewComponents();
        currentView = mainModel; // by default we use List View
    }

    public void makeTransparent() {
        jScrollPane1.setOpaque(false);
        cardArea.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        cbSortBy.setModel(new DefaultComboBoxModel(SortBy.values()));
    }

    public void initListViewComponents() {
        mainTable = new JTable();

        mainModel = new TableModel();
        mainModel.addListeners(mainTable);

        mainTable.setModel(mainModel);
        mainTable.setForeground(Color.white);
        DefaultTableCellRenderer myRenderer = (DefaultTableCellRenderer) mainTable.getDefaultRenderer(String.class);
        myRenderer.setBackground(new Color(0, 0, 0, 100));
        mainTable.getColumnModel().getColumn(0).setMaxWidth(25);
        mainTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        mainTable.getColumnModel().getColumn(1).setPreferredWidth(110);
        mainTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        mainTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        mainTable.getColumnModel().getColumn(4).setPreferredWidth(170);
        mainTable.getColumnModel().getColumn(5).setPreferredWidth(30);
        mainTable.getColumnModel().getColumn(6).setPreferredWidth(15);
        mainTable.getColumnModel().getColumn(7).setPreferredWidth(15);

        jScrollPane1.setViewportView(mainTable);

        mainTable.setOpaque(false);

        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);

        mainTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    handleDoubleClick();
                }
            }
        });

        mainModel.setUpdateCountsCallback(new UpdateCountsCallback(lblCount, lblCreatureCount, lblLandCount));
    }

    public void handleDoubleClick() {
        if (mainTable.getSelectedRowCount() > 0) {
            int[] n = mainTable.getSelectedRows();
            List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                mainModel.doubleClick(index);
            }
        }
    }

    public ICardGrid getMainModel() {
        return mainModel;
    }

    public List<Integer> asList(final int[] is) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i : is) list.add(i);
        return list;
    }

    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
        this.cards = showCards;
        this.bigCard = bigCard;
        this.gameId = gameId;
        currentView.loadCards(showCards, null, false, bigCard, gameId);
        //loadCards(showCards, null, false, bigCard, gameId);
    }

    private void redrawCards() {
        if (cards == null) cards = new CardsView();
           currentView.loadCards(cards, null, false, bigCard, gameId);
    }

    public void drawCards(SortBy sortBy, boolean piles) {
        int maxWidth = this.getParent().getWidth();
        int numColumns = maxWidth / Config.dimensions.frameWidth;
        int curColumn = 0;
        int curRow = 0;
        int landCount = 0;
        int creatureCount = 0;
        //FIXME: why we remove all cards? for performance it's better to merge changes
        // as it is already done in ListView
        cardArea.removeAll();
        if (cards != null && cards.size() > 0) {
            Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
            int count = 0;
            List<CardView> sortedCards = new ArrayList<CardView>(cards.values());
            switch (sortBy) {
                case NAME:
                    Collections.sort(sortedCards, new CardViewNameComparator());
                    break;
                case RARITY:
                    Collections.sort(sortedCards, new CardViewRarityComparator());
                    break;
                case COLOR:
                    Collections.sort(sortedCards, new CardViewColorComparator());
                    break;
                case CASTING_COST:
                    Collections.sort(sortedCards, new CardViewCostComparator());
                    break;
            }
            CardView lastCard = null;
            for (CardView card: sortedCards) {
                if (chkPiles.isSelected()) {
                    if (lastCard == null)
                        lastCard = card;
                    switch (sortBy) {
                        case NAME:
                            if (!card.getName().equals(lastCard.getName())) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                        case RARITY:
                            if (!card.getRarity().equals(lastCard.getRarity())) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                        case COLOR:
                            if (card.getColor().compareTo(lastCard.getColor()) != 0) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                        case CASTING_COST:
                            if (card.getConvertedManaCost() != lastCard.getConvertedManaCost()) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                    }
                    rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
                    addCard(card, bigCard, gameId, rectangle);
                    if (card.getCardTypes().contains(CardType.LAND))
                        landCount++;
                    if (card.getCardTypes().contains(CardType.CREATURE))
                        creatureCount++;
                    curRow++;
                    lastCard = card;
                }
                else {
                    rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
                    addCard(card, bigCard, gameId, rectangle);
                    if (card.getCardTypes().contains(CardType.LAND))
                        landCount++;
                    if (card.getCardTypes().contains(CardType.CREATURE))
                        creatureCount++;
                    curColumn++;
                    if (curColumn == numColumns) {
                        curColumn = 0;
                        curRow++;
                    }
                }
            }
        }
        int count = cards != null ? cards.size() : 0;
        this.lblCount.setText("Count: " + Integer.toString(count));
        this.lblCreatureCount.setText("Creatures: " + Integer.toString(creatureCount));
        this.lblLandCount.setText("Lands: " + Integer.toString(landCount));
        cardArea.setPreferredSize(new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight + 200));
        cardArea.revalidate();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        if (cardDimension == null) {
            cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        }
        MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, cardDimension, gameId, true);
        cardImg.setBounds(rectangle);
        cardArea.add(cardImg);
        cardArea.moveToFront(cardImg);
        cardImg.update(card);
        cardImg.addMouseListener(this);
        cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
    }

    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
        mainModel.addCardEventListener(listener);
    }

    public void drawCards(SortBy sortBy) {
        drawCards(sortBy, false);
    }

    @Override
    public void loadCards(CardsView showCards, SortBy sortBy, boolean piles, BigCard bigCard, UUID gameId) {
        cards = showCards;
        this.bigCard = bigCard;
        this.gameId = gameId;
        drawCards((SortBy) cbSortBy.getSelectedItem());
    }

    @Override
    public void refresh() {
        redrawCards();
    }

    public void clearCardEventListeners() {
        cardEventSource.clearListeners();
        mainModel.clearCardEventListeners();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        cardArea = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        cbSortBy = new javax.swing.JComboBox();
        lblCount = new javax.swing.JLabel();
        lblCreatureCount = new javax.swing.JLabel();
        lblLandCount = new javax.swing.JLabel();
        jToggleListView = new javax.swing.JToggleButton();
        jToggleCardView = new javax.swing.JToggleButton();
        chkPiles = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setPreferredSize((!Beans.isDesignTime()) ? (new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight)) : (new Dimension(100, 100)));

        jScrollPane1.setViewportView(cardArea);

        cbSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        cbSortBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortByActionPerformed(evt);
            }
        });

        lblCount.setText("Card Count");

        lblCreatureCount.setText("Creature Count");

        lblLandCount.setText("Land Count");

        jToggleListView.setSelected(true);
        jToggleListView.setText("ListView");
        jToggleListView.setFocusable(false);
        jToggleListView.setMaximumSize(new java.awt.Dimension(45, 21));
        jToggleListView.setMinimumSize(new java.awt.Dimension(54, 21));
        jToggleListView.setPreferredSize(new java.awt.Dimension(45, 21));
        jToggleListView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleListViewActionPerformed(evt);
            }
        });

        jToggleCardView.setText("CardView");
        jToggleCardView.setMaximumSize(new java.awt.Dimension(77, 21));
        jToggleCardView.setMinimumSize(new java.awt.Dimension(77, 21));
        jToggleCardView.setOpaque(true);
        jToggleCardView.setPreferredSize(new java.awt.Dimension(77, 21));
        jToggleCardView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleCardViewActionPerformed(evt);
            }
        });

        chkPiles.setText("Piles");
        chkPiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPilesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCreatureCount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLandCount, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSortBy, 0, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleListView, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleCardView, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cbSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblCount)
                .addComponent(lblCreatureCount)
                .addComponent(lblLandCount)
                .addComponent(jToggleCardView, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(chkPiles)
                .addComponent(jToggleListView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbSortByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortByActionPerformed
        drawCards((SortBy) cbSortBy.getSelectedItem());
    }//GEN-LAST:event_cbSortByActionPerformed

    private void chkPilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPilesActionPerformed
        drawCards((SortBy) cbSortBy.getSelectedItem());
    }//GEN-LAST:event_chkPilesActionPerformed

    private void jToggleListViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleListViewActionPerformed
        jToggleCardView.setSelected(false);
        currentView = mainModel;
        jScrollPane1.setViewportView(mainTable);
        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);
        //drawCards((SortBy) cbSortBy.getSelectedItem());
        redrawCards();
    }//GEN-LAST:event_jToggleListViewActionPerformed

    private void jToggleCardViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleCardViewActionPerformed
        jToggleListView.setSelected(false);
        currentView = this;
        jScrollPane1.setViewportView(cardArea);
        cbSortBy.setEnabled(true);
        chkPiles.setEnabled(true);
        //drawCards((SortBy) cbSortBy.getSelectedItem());
        redrawCards();
    }//GEN-LAST:event_jToggleCardViewActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane cardArea;
    private javax.swing.JComboBox cbSortBy;
    private javax.swing.JCheckBox chkPiles;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleCardView;
    private javax.swing.JToggleButton jToggleListView;
    private javax.swing.JLabel lblCount;
    private javax.swing.JLabel lblCreatureCount;
    private javax.swing.JLabel lblLandCount;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            Object obj = e.getSource();
            if (obj instanceof Card) {
                if (e.isShiftDown())
                    cardEventSource.shiftDoubleClick(((Card)obj).getCardId(), "shift-double-click");
                else
                    cardEventSource.doubleClick(((Card)obj).getCardId(), "double-click");
            } else if (obj instanceof MageCard) {
                if (e.isShiftDown())
                    cardEventSource.shiftDoubleClick(((MageCard)obj).getOriginal().getId(), "shift-double-click");
                else
                    cardEventSource.doubleClick(((MageCard)obj).getOriginal().getId(), "double-click");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setDisplayNoCopies(boolean value) {
        mainModel.setDisplayNoCopies(value);
    }
}
