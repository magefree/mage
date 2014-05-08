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
 * CardSelector.java
 *
 * Created on Feb 18, 2010, 2:49:03 PM
 */

package mage.client.deckeditor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import mage.MageObject;
import mage.ObjectColor;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.cards.BigCard;
import mage.client.cards.CardGrid;
import mage.client.cards.ICardGrid;
import mage.client.constants.Constants.SortBy;
import mage.client.deckeditor.table.TableModel;
import mage.client.util.sets.ConstructedFormats;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.other.CardTextPredicate;
import mage.filter.predicate.other.ExpansionSetPredicate;
import mage.view.CardsView;


/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class CardSelector extends javax.swing.JPanel implements ComponentListener {

    private final List<Card> cards = new ArrayList<>();
    private BigCard bigCard;
    private boolean limited = false;
    private final SortSetting sortSetting;

    private final ActionListener searchAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            jButtonSearchActionPerformed(evt);
        }
    };

    /** Creates new form CardSelector */
    public CardSelector() {
        sortSetting = SortSettingBase.getInstance();
        initComponents();
        cardGrid = new CardGrid();
        makeTransparent();
        initListViewComponents();
        currentView = mainModel; // by default we use List View        
    }

    private void makeTransparent() {
        this.addComponentListener(this);
        setOpaque(false);
        
        cardGrid.setOpaque(false);
        cardSelectorScrollPane.setOpaque(false);
        cardSelectorScrollPane.getViewport().setOpaque(false);
        cbSortBy.setModel(new DefaultComboBoxModel<>(SortBy.values()));
        cbSortBy.setSelectedItem(sortSetting.getSortBy());
        jTextFieldSearch.addActionListener(searchAction);
        // make the components more readable
        tbColor.setBackground(new Color(250, 250, 250, 150));
        tbColor.setOpaque(true); // false = transparent
        tbTypes.setBackground(new Color(250, 250, 250, 150));
        tbTypes.setOpaque(true); // false = transparent
        cardSelectorBottomPanel.setBackground(new Color(250, 250, 250, 150));
        cardSelectorBottomPanel.setOpaque(true); // false = transparent
    }

    private void initListViewComponents() {
        mainTable = new JTable();

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

        cardSelectorScrollPane.setViewportView(mainTable);
        mainTable.setOpaque(false);
        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    if (e.isShiftDown()) {
                        jButtonAddToSideboardActionPerformed(null);                        
                    } else {
                        jButtonAddToMainActionPerformed(null);
                    }
                }
            }
        });
        
        jToggleCardView.setToolTipText(jToggleCardView.getToolTipText() + " (works only up to " + CardGrid.MAX_IMAGES + " cards).");
    }
    
    /**
     * Free all references
     * 
     */
    public void cleanUp() {
        this.cardGrid.clear();
        this.mainModel.clear();
    }

    public void switchToGrid(){
        jToggleListView.setSelected(false);
        jToggleCardView.setSelected(true);
        currentView = cardGrid;
        cardSelectorScrollPane.setViewportView(cardGrid);
        cbSortBy.setEnabled(true);
        chkPiles.setEnabled(true);
        jButtonAddToMain.setEnabled(false);
        jButtonAddToSideboard.setEnabled(false);
        filterCards();
        chkPiles.setSelected(true);
        this.currentView.drawCards(sortSetting);
    }

    public void loadSideboard(List<Card> sideboard, BigCard bigCard) {
        this.bigCard = bigCard;
        this.btnBooster.setVisible(false);
        this.btnClear.setVisible(false);
        this.cbExpansionSet.setVisible(false);
        this.limited = true;
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
        cbExpansionSet.setModel(new DefaultComboBoxModel<>(ConstructedFormats.getTypes()));
        // Action event on Expansion set triggers loadCards method
        cbExpansionSet.setSelectedIndex(0);
    }

    private FilterCard buildFilter() {
        FilterCard filter = new FilterCard();

        String name = jTextFieldSearch.getText().trim();
        filter.add(new CardTextPredicate(name));

        if (limited) {
            ArrayList<Predicate<MageObject>> predicates = new ArrayList<>();

            if (this.tbGreen.isSelected()) {
                predicates.add(new ColorPredicate(ObjectColor.GREEN));
            }
            if (this.tbRed.isSelected()) {
                predicates.add(new ColorPredicate(ObjectColor.RED));
            }
            if (this.tbBlack.isSelected()) {
                predicates.add(new ColorPredicate(ObjectColor.BLACK));
            }
            if (this.tbBlue.isSelected()) {
                predicates.add(new ColorPredicate(ObjectColor.BLUE));
            }
            if (this.tbWhite.isSelected()) {
                predicates.add(new ColorPredicate(ObjectColor.WHITE));
            }
            if (this.tbColorless.isSelected()) {
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


            if (this.cbExpansionSet.isVisible()) {
                String expansionSelection = this.cbExpansionSet.getSelectedItem().toString();
                if (!expansionSelection.equals("- All Sets")) {
                    ArrayList<Predicate<Card>> expansionPredicates = new ArrayList<>();
                    for (String setCode : ConstructedFormats.getSetsByFormat(expansionSelection)) {
                        expansionPredicates.add(new ExpansionSetPredicate(setCode));
                    }
                    filter.add(Predicates.or(expansionPredicates));
                }
            }
        }

        return filter;
    }

    private CardCriteria buildCriteria() {
        CardCriteria criteria = new CardCriteria();
        criteria.black(this.tbBlack.isSelected());
        criteria.blue(this.tbBlue.isSelected());
        criteria.green(this.tbGreen.isSelected());
        criteria.red(this.tbRed.isSelected());
        criteria.white(this.tbWhite.isSelected());
        criteria.colorless(this.tbColorless.isSelected());

        if (this.rdoLand.isSelected()) {
            criteria.types(CardType.LAND);
        }
        if (this.rdoArtifacts.isSelected()) {
            criteria.types(CardType.ARTIFACT);
        }
        if (this.rdoCreatures.isSelected()) {
            criteria.types(CardType.CREATURE);
        }
        if (this.rdoEnchantments.isSelected()) {
            criteria.types(CardType.ENCHANTMENT);
        }
        if (this.rdoInstants.isSelected()) {
            criteria.types(CardType.INSTANT);
        }
        if (this.rdoSorceries.isSelected()) {
            criteria.types(CardType.SORCERY);
        }
        if (this.rdoPlaneswalkers.isSelected()) {
            criteria.types(CardType.PLANESWALKER);
        }

        if (this.cbExpansionSet.isVisible()) {
            String expansionSelection = this.cbExpansionSet.getSelectedItem().toString();
            if (!expansionSelection.equals("- All Sets")) {
                List<String> setCodes = ConstructedFormats.getSetsByFormat(expansionSelection);
                criteria.setCodes(setCodes.toArray(new String[0]));
            }
        }

        return criteria;
    }

    private boolean inverter(boolean invert, String string1, String string2) {
        if (invert) {
            return string1.equals(string2);
        } else {
            return !string1.equals(string2);
        }
    }
    
    private void filterCardsColor(int modifiers, String actionCommand) {
        // ALT Button was pushed
        if ((modifiers & ActionEvent.ALT_MASK) == ActionEvent.ALT_MASK || (modifiers & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
            boolean invert = (modifiers & ActionEvent.ALT_MASK) == ActionEvent.ALT_MASK;
            tbBlack.setSelected(inverter(invert, tbBlack.getActionCommand(), actionCommand));
            tbBlue.setSelected(inverter(invert, tbBlue.getActionCommand(), actionCommand));
            tbColorless.setSelected(inverter(invert, tbColorless.getActionCommand(), actionCommand));
            tbGreen.setSelected(inverter(invert, tbGreen.getActionCommand(), actionCommand));
            tbRed.setSelected(inverter(invert, tbRed.getActionCommand(), actionCommand));
            tbWhite.setSelected(inverter(invert, tbWhite.getActionCommand(), actionCommand));
        } 
        filterCards();        
    }
    
    private void filterCardsType(int modifiers, String actionCommand) {
        // ALT Button was pushed
        if ((modifiers & ActionEvent.ALT_MASK) == ActionEvent.ALT_MASK) {
            rdoArtifacts.setSelected(rdoArtifacts.getActionCommand().equals(actionCommand));
            rdoCreatures.setSelected(rdoCreatures.getActionCommand().equals(actionCommand));
            rdoEnchantments.setSelected(rdoEnchantments.getActionCommand().equals(actionCommand));
            rdoInstants.setSelected(rdoInstants.getActionCommand().equals(actionCommand));
            rdoLand.setSelected(rdoLand.getActionCommand().equals(actionCommand));
            rdoPlaneswalkers.setSelected(rdoPlaneswalkers.getActionCommand().equals(actionCommand));
            rdoSorceries.setSelected(rdoSorceries.getActionCommand().equals(actionCommand));
        } 
        filterCards();        
    }
    
    private void filterCards() {
        FilterCard filter = buildFilter();
        try {
            List<Card> filteredCards = new ArrayList<>();
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if (limited) {
                for (Card card: cards) {
                    if (filter.match(card, null)) {
                        filteredCards.add(card);
                    }
                }
            }
            else {
                List<CardInfo> foundCards = CardRepository.instance.findCards(buildCriteria());
                for (CardInfo cardInfo : foundCards) {
                    Card card = cardInfo.getMockCard();
                    if (filter.match(card, null)) {
                        filteredCards.add(card);
                    }
                }                
            }
            if (currentView instanceof CardGrid && filteredCards.size() > CardGrid.MAX_IMAGES) {
                this.toggleViewMode();
            }
            this.currentView.loadCards(new CardsView(filteredCards), sortSetting, bigCard, null, false);
            this.cardCount.setText(String.valueOf(filteredCards.size()));
        }
        finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void setCardCount(int value) {
        this.cardCount.setText(String.valueOf(value));
    }

    public List<ICardGrid> getCardGridComponents() {
        List<ICardGrid> components = new ArrayList<>();
        components.add(mainModel);
        components.add(cardGrid);
        return components;
    }

    public void removeCard(UUID cardId) {
        this.mainModel.removeCard(cardId);
        this.cardGrid.removeCard(cardId);
        for (Card card: cards) {
            if (card.getId().equals(cardId)) {
                cards.remove(card);
                break;
            }
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

        bgView = new javax.swing.ButtonGroup();
        tbColor = new javax.swing.JToolBar();
        tbRed = new javax.swing.JToggleButton();
        tbGreen = new javax.swing.JToggleButton();
        tbBlue = new javax.swing.JToggleButton();
        tbBlack = new javax.swing.JToggleButton();
        tbWhite = new javax.swing.JToggleButton();
        tbColorless = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cbExpansionSet = new javax.swing.JComboBox<String>();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnBooster = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        tbTypes = new javax.swing.JToolBar();
        rdoLand = new javax.swing.JRadioButton();
        rdoCreatures = new javax.swing.JRadioButton();
        rdoArtifacts = new javax.swing.JRadioButton();
        rdoEnchantments = new javax.swing.JRadioButton();
        rdoInstants = new javax.swing.JRadioButton();
        rdoSorceries = new javax.swing.JRadioButton();
        rdoPlaneswalkers = new javax.swing.JRadioButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        chkPiles = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cbSortBy = new javax.swing.JComboBox<SortBy>();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jToggleListView = new javax.swing.JToggleButton();
        jToggleCardView = new javax.swing.JToggleButton();
        cardSelectorScrollPane = new javax.swing.JScrollPane();
        cardSelectorBottomPanel = new javax.swing.JPanel();
        jButtonAddToMain = new javax.swing.JButton();
        jButtonAddToSideboard = new javax.swing.JButton();
        jLabelSearch = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jButtonClean = new javax.swing.JButton();
        cardCountLabel = new javax.swing.JLabel();
        cardCount = new javax.swing.JLabel();
        jButtonRemoveFromMain = new javax.swing.JButton();
        jButtonRemoveFromSideboard = new javax.swing.JButton();

        tbColor.setFloatable(false);
        tbColor.setRollover(true);
        tbColor.setToolTipText("Click with ALT to deselct all other colors or CTRL for invert selection.");
        tbColor.setBorderPainted(false);
        tbColor.setName(""); // NOI18N

        tbRed.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_red_off.png")); // NOI18N
        tbRed.setSelected(true);
        tbRed.setToolTipText("<html><font color='red'><strong>Red</strong><font/><br/>" 
            + tbColor.getToolTipText());
        tbRed.setActionCommand("Red");
        tbRed.setFocusable(false);
        tbRed.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbRed.setSelectedIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_red.png")); // NOI18N
        tbRed.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbRedActionPerformed(evt);
            }
        });
        tbColor.add(tbRed);

        tbGreen.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_green_off.png")); // NOI18N
        tbGreen.setSelected(true);
        tbGreen.setToolTipText("<html>Green<br/>" + tbColor.getToolTipText());
        tbGreen.setActionCommand("Green");
        tbGreen.setFocusable(false);
        tbGreen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbGreen.setSelectedIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_green.png")); // NOI18N
        tbGreen.setVerifyInputWhenFocusTarget(false);
        tbGreen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbGreenActionPerformed(evt);
            }
        });
        tbColor.add(tbGreen);

        tbBlue.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_blueOff.png")); // NOI18N
        tbBlue.setSelected(true);
        tbBlue.setToolTipText("<html>Blue<br/>" + tbColor.getToolTipText());
        tbBlue.setActionCommand("Blue");
        tbBlue.setFocusable(false);
        tbBlue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbBlue.setSelectedIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_blue.png")); // NOI18N
        tbBlue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbBlueActionPerformed(evt);
            }
        });
        tbColor.add(tbBlue);

        tbBlack.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_black_off.png")); // NOI18N
        tbBlack.setSelected(true);
        tbBlack.setToolTipText("<html>Black<br/>" + tbColor.getToolTipText());
        tbBlack.setActionCommand("Black");
        tbBlack.setFocusable(false);
        tbBlack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbBlack.setSelectedIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_black.png")); // NOI18N
        tbBlack.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbBlackActionPerformed(evt);
            }
        });
        tbColor.add(tbBlack);

        tbWhite.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_white_off.png")); // NOI18N
        tbWhite.setSelected(true);
        tbWhite.setToolTipText("<html>White<br/>" + tbColor.getToolTipText());
        tbWhite.setActionCommand("White");
        tbWhite.setFocusable(false);
        tbWhite.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbWhite.setSelectedIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\color_white.png")); // NOI18N
        tbWhite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbWhiteActionPerformed(evt);
            }
        });
        tbColor.add(tbWhite);

        tbColorless.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\colorless_off.png")); // NOI18N
        tbColorless.setSelected(true);
        tbColorless.setToolTipText("<html>Colorless<br/>" + tbColor.getToolTipText());
        tbColorless.setActionCommand("Colorless");
        tbColorless.setFocusable(false);
        tbColorless.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbColorless.setSelectedIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\colorless.png")); // NOI18N
        tbColorless.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbColorless.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbColorlessActionPerformed(evt);
            }
        });
        tbColor.add(tbColorless);
        tbColor.add(jSeparator1);

        cbExpansionSet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbExpansionSet.setMaximumSize(new java.awt.Dimension(250, 25));
        cbExpansionSet.setMinimumSize(new java.awt.Dimension(250, 25));
        cbExpansionSet.setPreferredSize(new java.awt.Dimension(250, 25));
        cbExpansionSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbExpansionSetActionPerformed(evt);
            }
        });
        tbColor.add(cbExpansionSet);
        tbColor.add(jSeparator2);

        btnBooster.setText("Open Booster");
        btnBooster.setToolTipText("(CURRENTLY NOT WORKING) Generates a booster of the selected set and adds the cards to the card selector.");
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

        tbTypes.setFloatable(false);
        tbTypes.setRollover(true);
        tbTypes.setToolTipText("Click card type with ALT-KEY to only get the clicked card type.");
        tbTypes.setPreferredSize(new java.awt.Dimension(732, 27));

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
        tbTypes.add(jSeparator5);

        chkPiles.setText("Piles");
        chkPiles.setToolTipText("Shows the card in piles by the selected sort.");
        chkPiles.setFocusable(false);
        chkPiles.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkPiles.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chkPiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPilesActionPerformed(evt);
            }
        });
        tbTypes.add(chkPiles);
        tbTypes.add(jSeparator3);

        cbSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSortBy.setMaximumSize(new java.awt.Dimension(120, 20));
        cbSortBy.setMinimumSize(new java.awt.Dimension(120, 20));
        cbSortBy.setPreferredSize(new java.awt.Dimension(120, 20));
        cbSortBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortByActionPerformed(evt);
            }
        });
        tbTypes.add(cbSortBy);
        tbTypes.add(jSeparator4);

        bgView.add(jToggleListView);
        jToggleListView.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\list_panel.png")); // NOI18N
        jToggleListView.setSelected(true);
        jToggleListView.setToolTipText("Shows the cards as a list.");
        jToggleListView.setBorderPainted(false);
        jToggleListView.setFocusable(false);
        jToggleListView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleListView.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleListView.setMaximumSize(new java.awt.Dimension(37, 22));
        jToggleListView.setMinimumSize(new java.awt.Dimension(37, 22));
        jToggleListView.setPreferredSize(new java.awt.Dimension(37, 22));
        jToggleListView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleListViewActionPerformed(evt);
            }
        });
        tbTypes.add(jToggleListView);

        bgView.add(jToggleCardView);
        jToggleCardView.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\card_panel.png")); // NOI18N
        jToggleCardView.setToolTipText("Shows the card as images.");
        jToggleCardView.setBorderPainted(false);
        jToggleCardView.setFocusable(false);
        jToggleCardView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleCardView.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleCardView.setMaximumSize(new java.awt.Dimension(37, 22));
        jToggleCardView.setMinimumSize(new java.awt.Dimension(37, 22));
        jToggleCardView.setName(""); // NOI18N
        jToggleCardView.setPreferredSize(new java.awt.Dimension(37, 22));
        jToggleCardView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleCardView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleCardViewActionPerformed(evt);
            }
        });
        tbTypes.add(jToggleCardView);

        cardSelectorBottomPanel.setOpaque(false);
        cardSelectorBottomPanel.setPreferredSize(new java.awt.Dimension(897, 40));

        jButtonAddToMain.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\deck_in.png")); // NOI18N
        jButtonAddToMain.setToolTipText("Add selected cards to deck");
        jButtonAddToMain.setMargin(null);
        jButtonAddToMain.setMaximumSize(new java.awt.Dimension(42, 23));
        jButtonAddToMain.setMinimumSize(new java.awt.Dimension(42, 23));
        jButtonAddToMain.setPreferredSize(new java.awt.Dimension(28, 22));
        jButtonAddToMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToMainActionPerformed(evt);
            }
        });

        jButtonAddToSideboard.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\sideboard_in.png")); // NOI18N
        jButtonAddToSideboard.setToolTipText("Add selected cards to sideboard.");
        jButtonAddToSideboard.setMargin(new java.awt.Insets(2, 0, 2, 0));
        jButtonAddToSideboard.setMaximumSize(new java.awt.Dimension(100, 30));
        jButtonAddToSideboard.setMinimumSize(new java.awt.Dimension(10, 30));
        jButtonAddToSideboard.setPreferredSize(new java.awt.Dimension(28, 22));
        jButtonAddToSideboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToSideboardActionPerformed(evt);
            }
        });

        jLabelSearch.setText("Search:");
        jLabelSearch.setToolTipText("Searches for card names and in the rule text of the card.");

        jTextFieldSearch.setToolTipText("Searches for card names and in the rule text of the card.");

        jButtonSearch.setText("Search");
        jButtonSearch.setToolTipText("Performs the search.");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButtonClean.setText("Clear");
        jButtonClean.setToolTipText("Clears the search field.");
        jButtonClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCleanActionPerformed(evt);
            }
        });

        cardCountLabel.setText("Card count:");
        cardCountLabel.setToolTipText("Number of cards currently shown.");

        cardCount.setText("0");

        jButtonRemoveFromMain.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\deck_out.png")); // NOI18N
        jButtonRemoveFromMain.setToolTipText("Remove selected cards from deck");
        jButtonRemoveFromMain.setMargin(null);
        jButtonRemoveFromMain.setMaximumSize(new java.awt.Dimension(42, 23));
        jButtonRemoveFromMain.setMinimumSize(new java.awt.Dimension(42, 23));
        jButtonRemoveFromMain.setPreferredSize(new java.awt.Dimension(28, 22));
        jButtonRemoveFromMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveFromMainActionPerformed(evt);
            }
        });

        jButtonRemoveFromSideboard.setIcon(new javax.swing.ImageIcon("D:\\Projekte\\mag\\mageRep\\localmage\\Mage.Client\\src\\main\\resources\\buttons\\sideboard_out.png")); // NOI18N
        jButtonRemoveFromSideboard.setToolTipText("Remove selected cards from sideboard.");
        jButtonRemoveFromSideboard.setMargin(new java.awt.Insets(2, 0, 2, 0));
        jButtonRemoveFromSideboard.setMaximumSize(new java.awt.Dimension(10, 30));
        jButtonRemoveFromSideboard.setMinimumSize(new java.awt.Dimension(100, 30));
        jButtonRemoveFromSideboard.setPreferredSize(new java.awt.Dimension(28, 22));
        jButtonRemoveFromSideboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveFromSideboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cardSelectorBottomPanelLayout = new javax.swing.GroupLayout(cardSelectorBottomPanel);
        cardSelectorBottomPanel.setLayout(cardSelectorBottomPanelLayout);
        cardSelectorBottomPanelLayout.setHorizontalGroup(
            cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardSelectorBottomPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jButtonAddToMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRemoveFromMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddToSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRemoveFromSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jButtonSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClean)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardCount, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardSelectorBottomPanelLayout.setVerticalGroup(
            cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardSelectorBottomPanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRemoveFromMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAddToSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRemoveFromSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSearch)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch)
                    .addComponent(jButtonClean)
                    .addComponent(cardCountLabel)
                    .addComponent(cardCount)
                    .addComponent(jButtonAddToMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        cardCountLabel.getAccessibleContext().setAccessibleName("cardCountLabel");
        cardCount.getAccessibleContext().setAccessibleName("cardCount");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tbTypes, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
            .addComponent(cardSelectorScrollPane)
            .addComponent(cardSelectorBottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardSelectorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(cardSelectorBottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoLandActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_rdoLandActionPerformed

    private void rdoCreaturesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCreaturesActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_rdoCreaturesActionPerformed

    private void rdoArtifactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoArtifactsActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_rdoArtifactsActionPerformed

    private void rdoEnchantmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoEnchantmentsActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_rdoEnchantmentsActionPerformed

    private void rdoInstantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoInstantsActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_rdoInstantsActionPerformed

    private void rdoSorceriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSorceriesActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_rdoSorceriesActionPerformed

    private void rdoPlaneswalkersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPlaneswalkersActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_rdoPlaneswalkersActionPerformed

    private void cbExpansionSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbExpansionSetActionPerformed
        filterCards();
    }//GEN-LAST:event_cbExpansionSetActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        cards.clear();
        this.limited = false;
        filterCards();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnBoosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoosterActionPerformed
        List<String> sets = ConstructedFormats.getSetsByFormat(this.cbExpansionSet.getSelectedItem().toString());
        if (sets.size() == 1) {
            if (!this.limited) {
                this.limited = true;
                cards.clear();
            }
            ExpansionSet expansionSet = Sets.getInstance().get(sets.get(0));
            if (expansionSet != null) {
                List<Card> booster = expansionSet.createBooster();
                cards.addAll(booster);
                filterCards();
            }
        } else {
            JOptionPane.showMessageDialog(null, "An expansion set must be selected to be able to generate a booster.");
        }
    }//GEN-LAST:event_btnBoosterActionPerformed

    private void cbSortByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortByActionPerformed
        if (cbSortBy.getSelectedItem() instanceof SortBy) {
            if (this.currentView != null) {
                sortSetting.setSortBy((SortBy) cbSortBy.getSelectedItem());
                this.currentView.drawCards(sortSetting);
            }
        }
    }//GEN-LAST:event_cbSortByActionPerformed

    private void chkPilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPilesActionPerformed
        if (cbSortBy.getSelectedItem() instanceof SortBy) {
            if (this.currentView != null) {
                sortSetting.setPilesToggle(chkPiles.isSelected());
                this.currentView.drawCards(sortSetting);
            }
        }
    }//GEN-LAST:event_chkPilesActionPerformed

    private void jToggleListViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleListViewActionPerformed
        if (!(currentView instanceof TableModel)) {
            toggleViewMode();
        }
        filterCards();
    }//GEN-LAST:event_jToggleListViewActionPerformed

    private void jToggleCardViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleCardViewActionPerformed
        if (currentView.cardsSize() > CardGrid.MAX_IMAGES) {
            jToggleCardView.setSelected(false);
            jToggleListView.setSelected(true);
            JOptionPane.showMessageDialog(this, new StringBuilder("The card view can't be used for more than ").append(CardGrid.MAX_IMAGES).append(" cards.").toString());
        } else {
            if (!(currentView instanceof CardGrid)) {
                toggleViewMode();
            }
            filterCards();
        }
    }//GEN-LAST:event_jToggleCardViewActionPerformed

    private void jButtonAddToMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddToMainActionPerformed
        if (mainTable.getSelectedRowCount() > 0) {
            int[] n = mainTable.getSelectedRows();
            List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                mainModel.doubleClick(index);
            }
            //if (!mode.equals(Constants.DeckEditorMode.Constructed))
            if (limited) {
                mainModel.fireTableDataChanged();
            }
        }
    }//GEN-LAST:event_jButtonAddToMainActionPerformed

    private void jButtonAddToSideboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddToSideboardActionPerformed
        if (mainTable.getSelectedRowCount() > 0) {
            int[] n = mainTable.getSelectedRows();
            List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                mainModel.shiftDoubleClick(index);
            }
            //if (!mode.equals(Constants.DeckEditorMode.Constructed))
            if (limited) {
                mainModel.fireTableDataChanged();
            }
        }
    }//GEN-LAST:event_jButtonAddToSideboardActionPerformed

    private void jButtonRemoveFromMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveFromMainActionPerformed
        mainModel.removeFromMainEvent(0);
    }//GEN-LAST:event_jButtonRemoveFromMainActionPerformed

    private void jButtonRemoveFromSideboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveFromSideboardActionPerformed
        mainModel.removeFromSideEvent(0);
    }//GEN-LAST:event_jButtonRemoveFromSideboardActionPerformed


    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        filterCards();
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCleanActionPerformed
        jTextFieldSearch.setText("");
        filterCards();
    }//GEN-LAST:event_jButtonCleanActionPerformed

    private void tbRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbRedActionPerformed
        filterCardsColor(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbRedActionPerformed

    private void tbGreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbGreenActionPerformed
        filterCardsColor(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbGreenActionPerformed

    private void tbBlueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbBlueActionPerformed
        filterCardsColor(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbBlueActionPerformed

    private void tbBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbBlackActionPerformed
        filterCardsColor(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbBlackActionPerformed

    private void tbWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbWhiteActionPerformed
        filterCardsColor(evt.getModifiers(), evt.getActionCommand());        
    }//GEN-LAST:event_tbWhiteActionPerformed

    private void tbColorlessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbColorlessActionPerformed
        filterCardsColor(evt.getModifiers(), evt.getActionCommand());     
    }//GEN-LAST:event_tbColorlessActionPerformed

    private void toggleViewMode() {
        if (currentView instanceof CardGrid) {
            jToggleListView.setSelected(true);
            jToggleCardView.setSelected(false);
            currentView = mainModel;
            cardSelectorScrollPane.setViewportView(mainTable);
            cbSortBy.setEnabled(false);
            chkPiles.setEnabled(false);
            jButtonAddToMain.setEnabled(true);
            jButtonAddToSideboard.setEnabled(true);            
        } else {
            jToggleCardView.setSelected(true);
            jToggleListView.setSelected(false);
            currentView = cardGrid;
            cardSelectorScrollPane.setViewportView(cardGrid);
            cbSortBy.setEnabled(true);
            chkPiles.setEnabled(true);
            jButtonAddToMain.setEnabled(false);
            jButtonAddToSideboard.setEnabled(false);        
        }
    }

    public List<Integer> asList(final int[] is) {
        List<Integer> list = new ArrayList<>();
        for (int i : is) {
            list.add(i);
        }
        return list;
    }

    public void refresh() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                currentView.refresh();
            }
        });
    }

    private TableModel mainModel;
    private JTable mainTable;
    private ICardGrid currentView;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgView;
    private javax.swing.JButton btnBooster;
    private javax.swing.JButton btnClear;
    private javax.swing.JLabel cardCount;
    private javax.swing.JLabel cardCountLabel;
    private javax.swing.JPanel cardSelectorBottomPanel;
    private javax.swing.JScrollPane cardSelectorScrollPane;
    private javax.swing.JComboBox<String> cbExpansionSet;
    private javax.swing.JComboBox<SortBy> cbSortBy;
    private javax.swing.JCheckBox chkPiles;
    private javax.swing.JButton jButtonAddToMain;
    private javax.swing.JButton jButtonAddToSideboard;
    private javax.swing.JButton jButtonClean;
    private javax.swing.JButton jButtonRemoveFromMain;
    private javax.swing.JButton jButtonRemoveFromSideboard;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JToggleButton jToggleCardView;
    private javax.swing.JToggleButton jToggleListView;
    private javax.swing.JRadioButton rdoArtifacts;
    private javax.swing.JRadioButton rdoCreatures;
    private javax.swing.JRadioButton rdoEnchantments;
    private javax.swing.JRadioButton rdoInstants;
    private javax.swing.JRadioButton rdoLand;
    private javax.swing.JRadioButton rdoPlaneswalkers;
    private javax.swing.JRadioButton rdoSorceries;
    private javax.swing.JToggleButton tbBlack;
    private javax.swing.JToggleButton tbBlue;
    private javax.swing.JToolBar tbColor;
    private javax.swing.JToggleButton tbColorless;
    private javax.swing.JToggleButton tbGreen;
    private javax.swing.JToggleButton tbRed;
    private javax.swing.JToolBar tbTypes;
    private javax.swing.JToggleButton tbWhite;
    // End of variables declaration//GEN-END:variables

    private final mage.client.cards.CardGrid cardGrid;

    @Override
    public void componentResized(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy) {
            this.currentView.drawCards(sortSetting);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy) {
            this.currentView.drawCards(sortSetting);
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy) {
            this.currentView.drawCards(sortSetting);
        }
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy) {
            this.currentView.drawCards(sortSetting);
        }
    }

}
