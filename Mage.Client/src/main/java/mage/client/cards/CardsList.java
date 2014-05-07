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



import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import mage.constants.CardType;
import mage.cards.MageCard;
import mage.client.constants.Constants.SortBy;
import mage.client.deckeditor.SortSetting;
import mage.client.deckeditor.table.TableModel;
import mage.client.deckeditor.table.UpdateCountsCallback;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.*;
import mage.client.util.Event;
import mage.view.CardView;
import mage.view.CardsView;
import org.mage.card.arcane.CardPanel;

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
    private SortSetting sortSetting;

    private TableModel mainModel;
    private JTable mainTable;
    private ICardGrid currentView;

    /** Creates new form Cards */
    public CardsList() {
        initComponents();
        makeTransparent();
        initListViewComponents();
    }

    public void cleanUp() {
        this.clearCardEventListeners();
        if (cards != null) {
            cards.clear();
        }
        if (mainModel != null) {
            mainModel.removeTableModelListener(mainTable);
            mainModel.clear();
        }
        if(mainTable != null) {
            for(MouseListener ml: mainTable.getMouseListeners()) {
                mainTable.removeMouseListener(ml);
            }
        }
        if (currentView != null) {
            currentView.clearCardEventListeners();
        }
        for (Component comp :cardArea.getComponents()) {
            if (comp instanceof CardPanel) {                
                ((CardPanel)comp).cleanUp();
            }
        }
        cardArea.removeAll();
        this.bigCard = null;

    }

    private void makeTransparent() {
        panelCardArea.setOpaque(false);
        cardArea.setOpaque(false);
        panelCardArea.getViewport().setOpaque(false);
        panelControl.setBackground(new Color(250, 250, 250, 150));
        panelControl.setOpaque(true);
        cbSortBy.setModel(new DefaultComboBoxModel<>(SortBy.values()));
    }

    private void initListViewComponents() {
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

        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_DRAFT_VIEW, "cardView").equals("listView")) {
            panelCardArea.setViewportView(mainTable);
            currentView = mainModel;
            cbSortBy.setEnabled(false);
            chkPiles.setEnabled(false);
        } else {
            jToggleCardView.setSelected(true);
            jToggleListView.setSelected(false);
            currentView = this;
            panelCardArea.setViewportView(cardArea);
            cbSortBy.setEnabled(true);
            chkPiles.setEnabled(true);
        }
        

        mainTable.setOpaque(false);


        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    if (e.isShiftDown()) {
                        handleShiftDoubleClick();
                    } else {
                        handleDoubleClick();
                    }
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
    
    public void handleShiftDoubleClick() {
        if (mainTable.getSelectedRowCount() > 0) {
            int[] n = mainTable.getSelectedRows();
            List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                mainModel.shiftDoubleClick(index);
            }
        }
    }
    
    public ICardGrid getMainModel() {
        return mainModel;
    }

    public List<Integer> asList(final int[] is) {
        List<Integer> list = new ArrayList<>();
        for (int i : is) {
            list.add(i);
        }
        return list;
    }


    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
        this.cards = showCards;
        this.bigCard = bigCard;
        this.gameId = gameId;

        cbSortBy.setSelectedItem(sortSetting.getSortBy());
        chkPiles.setSelected(sortSetting.isPilesToggle());
        currentView.loadCards(showCards, sortSetting, bigCard, gameId);
    }

    private void redrawCards() {
        if (cards == null) {
            cards = new CardsView();
        }
           currentView.loadCards(cards, sortSetting, bigCard, gameId);
    }

    @Override
    public void drawCards(SortSetting sortSetting) {
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
            List<CardView> sortedCards = new ArrayList<>(cards.values());
            switch (sortSetting.getSortBy()) {
                case NAME:
                    Collections.sort(sortedCards, new CardViewNameComparator());
                    break;
                case RARITY:
                    Collections.sort(sortedCards, new CardViewRarityComparator());
                    break;
                case COLOR:
                    Collections.sort(sortedCards, new CardViewColorComparator());
                    break;
                case COLOR_DETAILED:
                    Collections.sort(sortedCards, new CardViewColorDetailedComparator());
                    break;
                case CASTING_COST:
                    Collections.sort(sortedCards, new CardViewCostComparator());
                    break;
            }
            CardView lastCard = null;
            for (CardView card: sortedCards) {
                if (sortSetting.isPilesToggle()) {
                    if (lastCard == null) {
                        lastCard = card;
                    }
                    switch (sortSetting.getSortBy()) {
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
                        case COLOR_DETAILED:
                            if (card.getColor().hashCode() != lastCard.getColor().hashCode()) {
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
                    if (card.getCardTypes().contains(CardType.LAND)) {
                        landCount++;
                    }
                    if (card.getCardTypes().contains(CardType.CREATURE)) {
                        creatureCount++;
                    }
                    curRow++;
                    lastCard = card;
                }
                else {
                    rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
                    addCard(card, bigCard, gameId, rectangle);
                    if (card.getCardTypes().contains(CardType.LAND)) {
                        landCount++;
                    }
                    if (card.getCardTypes().contains(CardType.CREATURE)) {
                        creatureCount++;
                    }
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

    @Override
    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
        mainModel.addCardEventListener(listener);
    }

    @Override
    public void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId) {
        this.loadCards(showCards, sortSetting, bigCard, gameId, true);
    }

    @Override
    public void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId, boolean merge) {
        cards = showCards;
        this.bigCard = bigCard;
        this.gameId = gameId;
        drawCards(sortSetting);
    }

    @Override
    public void refresh() {
        redrawCards();
    }

    @Override
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

        panelControl = new javax.swing.JPanel();
        lblCount = new javax.swing.JLabel();
        lblCreatureCount = new javax.swing.JLabel();
        lblLandCount = new javax.swing.JLabel();
        chkPiles = new javax.swing.JCheckBox();
        cbSortBy = new javax.swing.JComboBox<SortBy>();
        jToggleListView = new javax.swing.JToggleButton();
        jToggleCardView = new javax.swing.JToggleButton();
        panelCardArea = new javax.swing.JScrollPane();
        cardArea = new javax.swing.JLayeredPane();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(30, 30));
        setPreferredSize((!Beans.isDesignTime())?(new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight)):(new Dimension(100, 100)));

        panelControl.setMaximumSize(new java.awt.Dimension(32767, 23));
        panelControl.setMinimumSize(new java.awt.Dimension(616, 23));
        panelControl.setName(""); // NOI18N
        panelControl.setPreferredSize(new java.awt.Dimension(616, 23));
        panelControl.setRequestFocusEnabled(false);

        lblCount.setText("Card Count");

        lblCreatureCount.setText("Creature Count");

        lblLandCount.setText("Land Count");

        chkPiles.setText("Piles");
        chkPiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPilesActionPerformed(evt);
            }
        });

        cbSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SortBy" }));
        cbSortBy.setToolTipText("Sort the cards if card view is active.");
        cbSortBy.setMaximumSize(new java.awt.Dimension(80, 20));
        cbSortBy.setMinimumSize(new java.awt.Dimension(80, 20));
        cbSortBy.setName("SortBy"); // NOI18N
        cbSortBy.setPreferredSize(new java.awt.Dimension(80, 20));
        cbSortBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortByActionPerformed(evt);
            }
        });

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
        jToggleCardView.setPreferredSize(new java.awt.Dimension(77, 21));
        jToggleCardView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleCardViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelControlLayout = new javax.swing.GroupLayout(panelControl);
        panelControl.setLayout(panelControlLayout);
        panelControlLayout.setHorizontalGroup(
            panelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCreatureCount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLandCount, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleListView, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleCardView, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );
        panelControlLayout.setVerticalGroup(
            panelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cbSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblCount)
                .addComponent(lblCreatureCount)
                .addComponent(lblLandCount)
                .addComponent(jToggleCardView, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(chkPiles)
                .addComponent(jToggleListView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelCardArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        panelCardArea.setViewportView(cardArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCardArea)
            .addComponent(panelControl, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelCardArea, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleCardViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleCardViewActionPerformed
        jToggleListView.setSelected(false);
        currentView = this;
        panelCardArea.setViewportView(cardArea);
        cbSortBy.setEnabled(true);
        chkPiles.setEnabled(true);
        redrawCards();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_DRAFT_VIEW, "cardView");
    }//GEN-LAST:event_jToggleCardViewActionPerformed

    private void jToggleListViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleListViewActionPerformed
        jToggleCardView.setSelected(false);
        currentView = mainModel;
        panelCardArea.setViewportView(mainTable);
        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);
        redrawCards();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_DRAFT_VIEW, "listView");
    }//GEN-LAST:event_jToggleListViewActionPerformed

    private void cbSortByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortByActionPerformed
        sortSetting.setSortBy((SortBy) cbSortBy.getSelectedItem());
        drawCards(sortSetting);
    }//GEN-LAST:event_cbSortByActionPerformed

    private void chkPilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPilesActionPerformed
        sortSetting.setPilesToggle(chkPiles.isSelected());
        drawCards(sortSetting);
    }//GEN-LAST:event_chkPilesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane cardArea;
    private javax.swing.JComboBox<SortBy> cbSortBy;
    private javax.swing.JCheckBox chkPiles;
    private javax.swing.JToggleButton jToggleCardView;
    private javax.swing.JToggleButton jToggleListView;
    private javax.swing.JLabel lblCount;
    private javax.swing.JLabel lblCreatureCount;
    private javax.swing.JLabel lblLandCount;
    private javax.swing.JScrollPane panelCardArea;
    private javax.swing.JPanel panelControl;
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
                if (e.isShiftDown()) {
                    cardEventSource.shiftDoubleClick(((Card)obj).getOriginal(), "shift-double-click");
                }
                else {
                    cardEventSource.doubleClick(((Card)obj).getOriginal(), "double-click");
                }
            } else if (obj instanceof MageCard) {
                if (e.isShiftDown()) {
                    cardEventSource.shiftDoubleClick(((MageCard)obj).getOriginal(), "shift-double-click");
                }
                else {
                    cardEventSource.doubleClick(((MageCard)obj).getOriginal(), "double-click");
                }
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

    @Override
    public int cardsSize() {
        return cards.size();
    }

    public void setSortBy(SortBy sortBy) {
        if (sortBy != null) {
            cbSortBy.setSelectedIndex(sortBy.ordinal());
        }
    }

    public void setSortSetting(SortSetting sortSetting) {
        this.sortSetting = sortSetting;
    }

}
