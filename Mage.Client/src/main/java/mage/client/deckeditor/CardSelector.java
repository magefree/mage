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

import mage.Constants.CardType;
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
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.other.CardTextPredicate;
import mage.filter.predicate.other.ExpansionSetPredicate;
import mage.view.CardsView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class CardSelector extends javax.swing.JPanel implements ComponentListener {

    private final List<Card> cards = new ArrayList<Card>();
    private BigCard bigCard;
    private boolean limited = false;

    private final ActionListener searchAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            jButtonSearchActionPerformed(evt);
        }
    };

    /** Creates new form CardSelector */
    public CardSelector() {
        initComponents();
        cardGrid = new CardGrid();
        makeTransparent();
        initListViewComponents();
        currentView = mainModel; // by default we use List View        
    }

    public void makeTransparent() {
        this.addComponentListener(this);
        setOpaque(false);
        cardGrid.setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        cbSortBy.setModel(new DefaultComboBoxModel(SortBy.values()));
        jTextFieldSearch.addActionListener(searchAction);
        // make the components more readable
        tbColor.setBackground(new Color(250, 250, 250, 150));
        tbColor.setOpaque(true); // false = transparent
        tbTypes.setBackground(new Color(250, 250, 250, 150));
        tbTypes.setOpaque(true); // false = transparent
    }

    public void initListViewComponents() {
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

        jScrollPane1.setViewportView(mainTable);

        mainTable.setOpaque(false);

        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);

        mainTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    jButtonAddToMainActionPerformed(null);
                }
            }
        });
    }
    
    public void switchToGrid(){
        jToggleListView.setSelected(false);
        jToggleCardView.setSelected(true);
        currentView = cardGrid;
        jScrollPane1.setViewportView(cardGrid);
        cbSortBy.setEnabled(true);
        chkPiles.setEnabled(true);
        jButtonAddToMain.setEnabled(false);
        jButtonAddToSideboard.setEnabled(false);
        filterCards();
        chkPiles.setSelected(true);
        this.currentView.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
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
        switchToGrid();
        filterCards();
    }

    public void loadCards(BigCard bigCard) {
        this.bigCard = bigCard;
        this.btnBooster.setVisible(true);
        this.btnClear.setVisible(true);
        this.cbExpansionSet.setVisible(true);
        cbExpansionSet.setModel(new DefaultComboBoxModel(ConstructedFormats.getTypes()));
        cbExpansionSet.setSelectedIndex(0);

        filterCards();
    }

    private FilterCard buildFilter() {
        FilterCard filter = new FilterCard();
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
        filter.add(new CardTextPredicate(name));

        if (this.cbExpansionSet.isVisible()) {
            String expansionSelection = this.cbExpansionSet.getSelectedItem().toString();
            if (!expansionSelection.equals("- All Sets")) {
                ArrayList<Predicate<Card>> expansionPredicates = new ArrayList<Predicate<Card>>();
                for (String setCode : ConstructedFormats.getSetsByFormat(expansionSelection)) {
                    expansionPredicates.add(new ExpansionSetPredicate(setCode));
                }
                filter.add(Predicates.or(expansionPredicates));
            }
        }

        return filter;
    }

    private CardCriteria buildCriteria() {
        CardCriteria criteria = new CardCriteria();
        criteria.black(this.rdoBlack.isSelected());
        criteria.blue(this.rdoBlue.isSelected());
        criteria.green(this.rdoGreen.isSelected());
        criteria.red(this.rdoRed.isSelected());
        criteria.white(this.rdoWhite.isSelected());
        criteria.colorless(this.rdoColorless.isSelected());

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

        String text = jTextFieldSearch.getText().trim();
        if (!text.isEmpty()) {
            // criteria.rules(text);
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

    private void filterCards() {
        FilterCard filter = buildFilter();
        try {
            List<Card> filteredCards = new ArrayList<Card>();
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
                    Card card = cardInfo.getCard();
                    if (filter.match(card, null)) {
                        filteredCards.add(card);
                    }
                }
            }
            this.currentView.loadCards(new CardsView(filteredCards), (SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected(), bigCard, null);
            this.cardCount.setText(String.valueOf(filteredCards.size()));
        }
        finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void setCardCount(int value) {
        this.cardCount.setText(String.valueOf(value));
    }

    public ICardGrid getCardsList() {
        return this.currentView;
    }

    public List<ICardGrid> getCardGridComponents() {
        List<ICardGrid> components = new ArrayList<ICardGrid>();
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
        jToggleListView = new javax.swing.JToggleButton();
        jToggleCardView = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jButtonAddToMain = new javax.swing.JButton();
        jButtonAddToSideboard = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jButtonClean = new javax.swing.JButton();
        cardCountLabel = new javax.swing.JLabel();
        cardCount = new javax.swing.JLabel();
        jButtonRemoveFromMain = new javax.swing.JButton();
        jButtonRemoveFromSideboard = new javax.swing.JButton();

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

        jToggleListView.setSelected(true);
        jToggleListView.setText("ListView");
        jToggleListView.setFocusable(false);
        jToggleListView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleListView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleListView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleListViewActionPerformed(evt);
            }
        });
        tbTypes.add(jToggleListView);

        jToggleCardView.setText("CardView");
        jToggleCardView.setFocusable(false);
        jToggleCardView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleCardView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleCardView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleCardViewActionPerformed(evt);
            }
        });
        tbTypes.add(jToggleCardView);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(897, 35));

        jButtonAddToMain.setText("+");
        jButtonAddToMain.setToolTipText("Add to Main");
        jButtonAddToMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToMainActionPerformed(evt);
            }
        });

        jButtonAddToSideboard.setText("+S");
        jButtonAddToSideboard.setToolTipText("Add to Sideboard");
        jButtonAddToSideboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToSideboardActionPerformed(evt);
            }
        });

        jLabel1.setText("Search (by name,in rules):");

        jButtonSearch.setText("Search");
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

        cardCountLabel.setForeground(java.awt.SystemColor.textHighlightText);
        cardCountLabel.setText("Card count:");

        cardCount.setForeground(java.awt.SystemColor.text);
        cardCount.setText("0");

        jButtonRemoveFromMain.setText("-");
        jButtonRemoveFromMain.setToolTipText("Remove from Main");
        jButtonRemoveFromMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveFromMainActionPerformed(evt);
            }
        });

        jButtonRemoveFromSideboard.setText("-S");
        jButtonRemoveFromSideboard.setToolTipText("Remove from Sideboard");
        jButtonRemoveFromSideboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveFromSideboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButtonAddToMain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRemoveFromMain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAddToSideboard)
                .addGap(5, 5, 5)
                .addComponent(jButtonRemoveFromSideboard)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClean)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cardCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardCount, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButtonAddToMain)
                .addComponent(jLabel1)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButtonSearch)
                .addComponent(jButtonClean)
                .addComponent(cardCountLabel)
                .addComponent(cardCount)
                .addComponent(jButtonRemoveFromMain)
                .addComponent(jButtonAddToSideboard)
                .addComponent(jButtonRemoveFromSideboard))
        );

        cardCountLabel.getAccessibleContext().setAccessibleName("cardCountLabel");
        cardCount.getAccessibleContext().setAccessibleName("cardCount");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbColor, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
            .addComponent(tbTypes, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

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
        if (this.cbExpansionSet.getSelectedItem().equals("-- Standard")) {
            filterCards();
        } else {
            // auto switch for ListView for "All sets" (too many cards to load)
            jToggleListView.doClick();
            jToggleListView.setSelected(true);
        }
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
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.currentView.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }//GEN-LAST:event_cbSortByActionPerformed

    private void chkPilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPilesActionPerformed
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.currentView.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }//GEN-LAST:event_chkPilesActionPerformed

    private void jToggleListViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleListViewActionPerformed
        jToggleCardView.setSelected(false);
        currentView = mainModel;
        jScrollPane1.setViewportView(mainTable);
        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);
        jButtonAddToMain.setEnabled(true);
        jButtonAddToSideboard.setEnabled(true);
        filterCards();
    }//GEN-LAST:event_jToggleListViewActionPerformed

    private void jToggleCardViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleCardViewActionPerformed
        jToggleListView.setSelected(false);
        currentView = cardGrid;
        jScrollPane1.setViewportView(cardGrid);
        cbSortBy.setEnabled(true);
        chkPiles.setEnabled(true);
        jButtonAddToMain.setEnabled(false);
        jButtonAddToSideboard.setEnabled(false);
        filterCards();
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
            if (limited)
                mainModel.fireTableDataChanged();
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
            if (limited)
                mainModel.fireTableDataChanged();
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

    public List<Integer> asList(final int[] is) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i : is) list.add(i);
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
    private javax.swing.JButton btnBooster;
    private javax.swing.JButton btnClear;
    private javax.swing.JLabel cardCount;
    private javax.swing.JLabel cardCountLabel;
    private javax.swing.JComboBox cbExpansionSet;
    private javax.swing.JComboBox cbSortBy;
    private javax.swing.JCheckBox chkPiles;
    private javax.swing.JButton jButtonAddToMain;
    private javax.swing.JButton jButtonAddToSideboard;
    private javax.swing.JButton jButtonClean;
    private javax.swing.JButton jButtonRemoveFromMain;
    private javax.swing.JButton jButtonRemoveFromSideboard;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JToggleButton jToggleCardView;
    private javax.swing.JToggleButton jToggleListView;
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
    // End of variables declaration//GEN-END:variables

    private mage.client.cards.CardGrid cardGrid;

    @Override
    public void componentResized(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.currentView.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.currentView.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

    @Override
    public void componentShown(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.currentView.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if (cbSortBy.getSelectedItem() instanceof SortBy)
            this.currentView.drawCards((SortBy) cbSortBy.getSelectedItem(), chkPiles.isSelected());
    }

}
