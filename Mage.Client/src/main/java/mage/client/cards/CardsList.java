

 /*
 * CardsList.java
 *
 * Created on Dec 18, 2009, 10:40:12 AM
 */
package mage.client.cards;

import mage.cards.MageCard;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.constants.Constants.SortBy;
import mage.client.deckeditor.SortSetting;
import mage.client.deckeditor.table.TableModel;
import mage.client.deckeditor.table.UpdateCountsCallback;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.*;
import mage.client.util.Event;
import mage.client.util.gui.TableSpinnerEditor;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.SimpleCardView;
import org.mage.card.arcane.CardPanel;
import org.mage.card.arcane.ManaSymbolsCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Beans;
import java.util.*;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardsList extends javax.swing.JPanel implements MouseListener, ICardGrid {

    protected final CardEventSource cardEventSource = new CardEventSource();
    private Dimension cardDimension;
    private int rowHeight;
    private CardsView cards;
    private Map<UUID, MageCard> mageCards = new LinkedHashMap<>();
    protected BigCard bigCard;
    protected UUID gameId;
    private SortSetting sortSetting;

    private TableModel mainModel;
    private JTable mainTable;
    private ICardGrid currentView;

    /**
     * Creates new form Cards
     */
    public CardsList() {
        initComponents();
        makeTransparent();
        initListViewComponents();
        setGUISize();
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
        if (cardArea != null) {
            for (MouseListener ml : cardArea.getMouseListeners()) {
                cardArea.removeMouseListener(ml);
            }
        }
        if (mainTable != null) {
            for (MouseListener ml : mainTable.getMouseListeners()) {
                mainTable.removeMouseListener(ml);
            }
        }
        if (currentView != null) {
            currentView.clearCardEventListeners();
        }
        for (Component comp : cardArea.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
            }
        }
        mageCards.clear();
        cardArea.removeAll();
        this.bigCard = null;

    }

    public void changeGUISize() {
        setGUISize();
        redrawCards();
    }

    private void setGUISize() {
        mainTable.getTableHeader().setFont(GUISizeHelper.tableFont);
        mainTable.setFont(GUISizeHelper.tableFont);
        mainTable.setRowHeight(GUISizeHelper.getTableRowHeight());
        cardDimension = GUISizeHelper.editorCardDimension;
        rowHeight = GUISizeHelper.editorCardOffsetSize;
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

        // new mana render (svg support)
        mainTable.getColumnModel().getColumn(mainModel.COLUMN_INDEX_COST).setCellRenderer(new ManaSymbolsCellRenderer());

        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_DRAFT_VIEW, "cardView").equals("listView")) {
            jToggleListView.setSelected(true);
            panelCardArea.setViewportView(mainTable);
            currentView = mainModel;
            cbSortBy.setEnabled(false);
            chkPiles.setEnabled(false);
        } else {
            jToggleCardView.setSelected(true);
            currentView = this;
            panelCardArea.setViewportView(cardArea);
            cbSortBy.setEnabled(true);
            chkPiles.setEnabled(true);
        }

        cardArea.addMouseListener(this);

        mainTable.setOpaque(false);
        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if ((e.getClickCount() & 1) == 0 && (e.getClickCount() > 0) && !e.isConsumed()) { // double clicks and repeated double clicks
                    e.consume();
                    if (e.isAltDown()) {
                        handleAltDoubleClick();
                    } else {
                        handleDoubleClick();
                    }
                }
            }
        });

        mainModel.setUpdateCountsCallback(new UpdateCountsCallback(lblCount, lblCreatureCount, lblLandCount, null, null, null, null));
    }

    // if you use the deck ediot to build a free deck, numbers can be set directly in deck and sideboard
    public void setDeckEditorMode(DeckEditorMode mode) {
        if (mode == DeckEditorMode.FREE_BUILDING) {
            // activate spinner for card number change
            mainModel.setNumberEditable(true);
            TableColumnModel tcm = mainTable.getColumnModel();
            TableColumn tc = tcm.getColumn(0);
            tc.setMaxWidth(55);
            tc.setMinWidth(55);
            tc.setPreferredWidth(55);
            tc.setCellEditor(new TableSpinnerEditor(this));
        }
    }

    public void handleSetNumber(int number) {
        if (mainTable.getSelectedRowCount() == 1) {
            mainModel.setNumber(mainTable.getSelectedRow(), number);
        }
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

    public void handleAltDoubleClick() {
        if (mainTable.getSelectedRowCount() > 0) {
            int[] n = mainTable.getSelectedRows();
            List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                mainModel.altDoubleClick(index);
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
        int selectedRow = -1;
        if (currentView.equals(mainModel)) {
            selectedRow = mainTable.getSelectedRow();
        }
        this.cards = showCards;
        this.bigCard = bigCard;
        this.gameId = gameId;

        cbSortBy.setSelectedItem(sortSetting.getSortBy());
        chkPiles.setSelected(sortSetting.isPilesToggle());
        currentView.loadCards(showCards, sortSetting, bigCard, gameId);
        if (selectedRow >= 0) {
            selectedRow = Math.min(selectedRow, mainTable.getRowCount() - 1);
            if (selectedRow >= 0) {
                mainTable.setRowSelectionInterval(selectedRow, selectedRow);
            }
        }
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
        int numColumns = maxWidth / cardDimension.width;
        int curColumn = 0;
        int curRow = 0;
        int maxRow = 0;
        int maxColumn = 0;
        Comparator<CardView> comparator = null;
        Map<UUID, MageCard> oldMageCards = mageCards;
        mageCards = new LinkedHashMap<>();

        //Find card view
        for (UUID uuid : cards.keySet()) {
            if (oldMageCards.containsKey(uuid)) {
                mageCards.put(uuid, oldMageCards.get(uuid));
                oldMageCards.remove(uuid);
            } else {
                mageCards.put(uuid, addCard(cards.get(uuid), bigCard, gameId));
            }
        }
        //Remove unused cards
        for (MageCard card : oldMageCards.values()) {
            cardArea.remove(card);
        }

        if (cards != null && !cards.isEmpty()) {
            Rectangle rectangle = new Rectangle(cardDimension.width, cardDimension.height);
            List<CardView> sortedCards = new ArrayList<>(cards.values());
            switch (sortSetting.getSortBy()) {
                case NAME:
                    comparator = new CardViewNameComparator();
                    break;
                case RARITY:
                    comparator = new CardViewRarityComparator();
                    break;
                case CARD_TYPE:
                    comparator = new CardViewCardTypeComparator();
                    break;
                case COLOR:
                    comparator = new CardViewColorComparator();
                    break;
                case COLOR_IDENTITY:
                    comparator = new CardViewColorIdentityComparator();
                    break;
                case CASTING_COST:
                    comparator = new CardViewCostComparator();
                    break;
            }
            if (comparator != null) {
                sortedCards.sort(new CardViewNameComparator());
                sortedCards.sort(comparator);
            }
            CardView lastCard = null;
            for (CardView card : sortedCards) {
                if (sortSetting.isPilesToggle()) {
                    if (lastCard == null) {
                        lastCard = card;
                    }
                    if (comparator != null) {
                        if (comparator.compare(card, lastCard) > 0) {
                            curColumn++;
                            maxRow = Math.max(maxRow, curRow);
                            curRow = 0;
                        }
                    }
                    rectangle.setLocation(curColumn * cardDimension.width, curRow * rowHeight);
                    setCardBounds(mageCards.get(card.getId()), rectangle);

                    curRow++;
                    lastCard = card;
                } else {
                    rectangle.setLocation(curColumn * cardDimension.width, curRow * rowHeight);
                    setCardBounds(mageCards.get(card.getId()), rectangle);
                    curColumn++;
                    if (curColumn == numColumns) {
                        maxColumn = Math.max(maxColumn, curColumn);
                        curColumn = 0;
                        curRow++;
                    }
                }
            }
        }
        maxRow = Math.max(maxRow, curRow);
        maxColumn = Math.max(maxColumn, curColumn);
        updateCounts();
        cardArea.setPreferredSize(new Dimension((maxColumn + 1) * cardDimension.width, cardDimension.height + maxRow * rowHeight));
        cardArea.revalidate();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    private void updateCounts() {
        int landCount = 0;
        int creatureCount = 0;
        int sorceryCount = 0;
        int instantCount = 0;
        int enchantmentCount = 0;
        int artifactCount = 0;

        for (CardView card : cards.values()) {
            if (card.isLand()) {
                landCount++;
            }
            if (card.isCreature()) {
                creatureCount++;
            }
            if (card.isSorcery()) {
                sorceryCount++;
            }
            if (card.isInstant()) {
                instantCount++;
            }
            if (card.isEnchantment()) {
                enchantmentCount++;
            }
            if (card.isArtifact()) {
                artifactCount++;
            }
        }

        int count = cards != null ? cards.size() : 0;
        this.lblCount.setText(Integer.toString(count));
        this.lblCreatureCount.setText(Integer.toString(creatureCount));
        this.lblLandCount.setText(Integer.toString(landCount));
    }

    private MageCard addCard(CardView card, BigCard bigCard, UUID gameId) {
        MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, cardDimension, gameId, true, true);
        cardArea.add(cardImg);
        cardImg.update(card);
        cardImg.addMouseListener(this);
        return cardImg;
    }

    private void setCardBounds(MageCard card, Rectangle rectangle) {
        card.setBounds(rectangle);
        card.setCardBounds(rectangle.x, rectangle.y, cardDimension.width, cardDimension.height);
        cardArea.moveToFront(card);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgView = new javax.swing.ButtonGroup();
        panelControl = new javax.swing.JPanel();
        lblCount = new javax.swing.JLabel();
        lblLandCount = new javax.swing.JLabel();
        lblCreatureCount = new javax.swing.JLabel();
        chkPiles = new javax.swing.JCheckBox();
        cbSortBy = new javax.swing.JComboBox();
        jToggleListView = new javax.swing.JToggleButton();
        jToggleCardView = new javax.swing.JToggleButton();
        panelCardArea = new javax.swing.JScrollPane();
        cardArea = new javax.swing.JLayeredPane();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(30, 30));
        setPreferredSize((!Beans.isDesignTime()) ?
                (GUISizeHelper.editorCardDimension)
                : (new Dimension(600, 600)));
        setRequestFocusEnabled(false);

        panelControl.setMaximumSize(new java.awt.Dimension(32767, 23));
        panelControl.setMinimumSize(new java.awt.Dimension(616, 23));
        panelControl.setName(""); // NOI18N
        panelControl.setPreferredSize(new java.awt.Dimension(616, 23));
        panelControl.setRequestFocusEnabled(false);

        lblCount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/deck_pack.png"))); // NOI18N
        lblCount.setText("999");
        lblCount.setToolTipText("Number of all cards in this area.");
        lblCount.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblCount.setFocusable(false);
        lblCount.setInheritsPopupMenu(false);
        lblCount.setRequestFocusEnabled(false);
        lblCount.setVerifyInputWhenFocusTarget(false);

        lblLandCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblLandCount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_land.png"))); // NOI18N
        lblLandCount.setText("999");
        lblLandCount.setToolTipText("Number of lands.");
        lblLandCount.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblLandCount.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblLandCount.setFocusable(false);
        lblLandCount.setInheritsPopupMenu(false);
        lblLandCount.setRequestFocusEnabled(false);
        lblLandCount.setVerifyInputWhenFocusTarget(false);

        lblCreatureCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCreatureCount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_creatures.png"))); // NOI18N
        lblCreatureCount.setText("999");
        lblCreatureCount.setToolTipText("Number of creatures.");
        lblCreatureCount.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblCreatureCount.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblCreatureCount.setFocusable(false);
        lblCreatureCount.setInheritsPopupMenu(false);
        lblCreatureCount.setRequestFocusEnabled(false);
        lblCreatureCount.setVerifyInputWhenFocusTarget(false);

        chkPiles.setText("Piles");
        chkPiles.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPiles.setMargin(new java.awt.Insets(3, 2, 2, 2));
        chkPiles.addActionListener(evt -> chkPilesActionPerformed(evt));

        cbSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"SortBy"}));
        cbSortBy.setToolTipText("Sort the cards if card view is active.");
        cbSortBy.setMaximumSize(new java.awt.Dimension(120, 20));
        cbSortBy.setMinimumSize(new java.awt.Dimension(120, 20));
        cbSortBy.setName("SortBy"); // NOI18N
        cbSortBy.setOpaque(false);
        cbSortBy.setPreferredSize(new java.awt.Dimension(120, 20));
        cbSortBy.addActionListener(evt -> cbSortByActionPerformed(evt));

        bgView.add(jToggleListView);
        jToggleListView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/list_panel.png"))); // NOI18N
        jToggleListView.setToolTipText("Shows the cards as a list.");
        jToggleListView.setBorder(null);
        jToggleListView.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jToggleListView.setMargin(new java.awt.Insets(2, 6, 2, 6));
        jToggleListView.setMaximumSize(new java.awt.Dimension(37, 25));
        jToggleListView.setMinimumSize(new java.awt.Dimension(37, 25));
        jToggleListView.setPreferredSize(new java.awt.Dimension(44, 22));
        jToggleListView.addActionListener(evt -> jToggleListViewActionPerformed(evt));

        bgView.add(jToggleCardView);
        jToggleCardView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/card_panel.png"))); // NOI18N
        jToggleCardView.setToolTipText("Shows the card as images.");
        jToggleCardView.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jToggleCardView.setMargin(new java.awt.Insets(2, 6, 2, 6));
        jToggleCardView.setPreferredSize(new java.awt.Dimension(40, 22));
        jToggleCardView.addActionListener(evt -> jToggleCardViewActionPerformed(evt));

        javax.swing.GroupLayout panelControlLayout = new javax.swing.GroupLayout(panelControl);
        panelControl.setLayout(panelControlLayout);
        panelControlLayout.setHorizontalGroup(
                panelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelControlLayout.createSequentialGroup()
                                .addComponent(lblCount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLandCount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCreatureCount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkPiles)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jToggleListView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleCardView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelControlLayout.setVerticalGroup(
                panelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelControlLayout.createSequentialGroup()
                                .addGroup(panelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lblCount)
                                                .addComponent(lblLandCount)
                                                .addComponent(lblCreatureCount)
                                                .addComponent(chkPiles))
                                        .addComponent(cbSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jToggleListView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jToggleCardView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
        );

        jToggleListView.getAccessibleContext().setAccessibleDescription("Switch between image and table view.");

        panelCardArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        panelCardArea.setViewportView(cardArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelControl, javax.swing.GroupLayout.PREFERRED_SIZE, 467, Short.MAX_VALUE)
                        .addComponent(panelCardArea)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(panelCardArea, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleCardViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleCardViewActionPerformed
        currentView = this;
        panelCardArea.setViewportView(cardArea);
        cbSortBy.setEnabled(true);
        chkPiles.setEnabled(true);
        PreferencesDialog.saveValue(PreferencesDialog.KEY_DRAFT_VIEW, "cardView");
        redrawCards();
    }//GEN-LAST:event_jToggleCardViewActionPerformed

    private void jToggleListViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleListViewActionPerformed
        currentView = mainModel;
        panelCardArea.setViewportView(mainTable);
        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);
        PreferencesDialog.saveValue(PreferencesDialog.KEY_DRAFT_VIEW, "listView");
        redrawCards();
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
    private javax.swing.ButtonGroup bgView;
    private javax.swing.JLayeredPane cardArea;
    private javax.swing.JComboBox cbSortBy;
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
        if (e.getClickCount() >= 1 && !e.isConsumed()) {
            Object obj = e.getSource();
            if ((e.getClickCount() & 1) == 0 && (e.getClickCount() > 0)) { // double clicks and repeated double clicks
                e.consume();
                if (obj instanceof Card) {
                    if (e.isAltDown()) {
                        cardEventSource.fireEvent(((Card) obj).getOriginal(), ClientEventType.ALT_DOUBLE_CLICK);
                    } else {
                        cardEventSource.fireEvent(((Card) obj).getOriginal(), ClientEventType.DOUBLE_CLICK);
                    }
                } else if (obj instanceof MageCard) {
                    if (e.isAltDown()) {
                        cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.ALT_DOUBLE_CLICK);
                    } else {
                        cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.DOUBLE_CLICK);
                    }
                }
            }
            if (obj instanceof MageCard) {
                checkMenu(e, ((MageCard) obj).getOriginal());
            } else {
                checkMenu(e, null);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!e.isConsumed()) {
            Object obj = e.getSource();
            if (obj instanceof MageCard) {
                checkMenu(e, ((MageCard) obj).getOriginal());
            } else {
                checkMenu(e, null);
            }
        }
    }

    private void checkMenu(MouseEvent Me, SimpleCardView card) {
        if (Me.isPopupTrigger()) {
            Me.consume();
            cardEventSource.fireEvent(card, Me.getComponent(), Me.getX(), Me.getY(), ClientEventType.SHOW_POP_UP_MENU);
        }
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
