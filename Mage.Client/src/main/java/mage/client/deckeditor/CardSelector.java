package mage.client.deckeditor;

import mage.MageObject;
import mage.ObjectColor;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.PennyDreadfulLegalityUtil;
import mage.cards.repository.*;
import mage.client.MageFrame;
import mage.client.cards.*;
import mage.client.constants.Constants.SortBy;
import mage.client.deckeditor.table.TableModel;
import mage.client.dialog.CheckBoxList;
import mage.client.util.GUISizeHelper;
import mage.client.util.gui.FastSearchUtil;
import mage.client.util.sets.ConstructedFormats;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.card.CardTextPredicate;
import mage.filter.predicate.card.ExpansionSetPredicate;
import mage.game.events.Listener;
import mage.view.CardView;
import mage.view.CardsView;
import org.apache.log4j.Logger;
import org.mage.card.arcane.ManaSymbolsCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.*;

import static mage.client.dialog.PreferencesDialog.*;

/**
 * GUI: deck editor's panel with filters and search
 *
 * @author BetaSteward_at_googlemail.com, nantuko, JayDi85
 */
public class CardSelector extends javax.swing.JPanel implements ComponentListener, DragCardTarget {

    private static final Logger logger = Logger.getLogger(CardSelector.class);
    private final String MULTI_SETS_SELECTION_TEXT = "Multiple sets selected";

    private final java.util.List<Card> cards = new ArrayList<>();
    private BigCard bigCard;
    private boolean limited = false;
    private final SortSetting sortSetting;
    private static final Map<String, Integer> pdAllowed = new HashMap<>();
    private static Listener<RepositoryEvent> setsDbListener = null;
    private boolean isSetsFilterLoading = false; // use it on sets combobox modify

    private final ActionListener searchAction = evt -> jButtonSearchActionPerformed(evt);

    /**
     * Creates new form CardSelector
     */
    public CardSelector() {
        sortSetting = SortSettingBase.getInstance();
        initComponents();
        cardGrid = new CardGrid();
        makeTransparent();
        initListViewComponents();
        setGUISize();
        currentView = mainModel; // by default we use List View

        // prepare search dialog with checkboxes
        listCodeSelected = new CheckBoxList();
        List<String> checkboxes = new ArrayList<>();
        for (String item : ConstructedFormats.getTypes()) {
            if (!item.equals(ConstructedFormats.ALL_SETS)) {
                checkboxes.add(item);
            }
        }
        listCodeSelected.setListData(checkboxes.toArray());
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

        // new mana render (svg support)
        mainTable.getColumnModel().getColumn(mainModel.COLUMN_INDEX_COST).setCellRenderer(new ManaSymbolsCellRenderer());
        mainTable.getColumnModel().getColumn(mainModel.COLUMN_INDEX_COLOR_IDENTITY).setCellRenderer(new ManaSymbolsCellRenderer());

        // mainTable.setToolTipText(cardSelectorScrollPane.getToolTipText());
        cardSelectorScrollPane.setViewportView(mainTable);
        mainTable.setOpaque(false);
        cbSortBy.setEnabled(false);
        chkPiles.setEnabled(false);

        chkNames.setSelected("true".equals(MageFrame.getPreferences().get(KEY_DECK_EDITOR_SEARCH_NAMES, "true")));
        chkTypes.setSelected("true".equals(MageFrame.getPreferences().get(KEY_DECK_EDITOR_SEARCH_TYPES, "true")));
        chkRules.setSelected("true".equals(MageFrame.getPreferences().get(KEY_DECK_EDITOR_SEARCH_RULES, "true")));
        chkUnique.setSelected("true".equals(MageFrame.getPreferences().get(KEY_DECK_EDITOR_SEARCH_UNIQUE, "false")));

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                if ((e.getClickCount() & 1) == 0 && (e.getClickCount() > 0) && !e.isConsumed()) { // double clicks and repeated double clicks
                    e.consume();
                    if (e.isAltDown()) {
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
     */
    public void cleanUp() {
        this.cardGrid.clear();
        this.mainModel.clear();
        MageFrame.getPreferences().put(KEY_DECK_EDITOR_SEARCH_NAMES, Boolean.toString(chkNames.isSelected()));
        MageFrame.getPreferences().put(KEY_DECK_EDITOR_SEARCH_RULES, Boolean.toString(chkRules.isSelected()));
        MageFrame.getPreferences().put(KEY_DECK_EDITOR_SEARCH_TYPES, Boolean.toString(chkTypes.isSelected()));
        MageFrame.getPreferences().put(KEY_DECK_EDITOR_SEARCH_UNIQUE, Boolean.toString(chkUnique.isSelected()));
        ExpansionRepository.instance.unsubscribe(setsDbListener);
    }

    public void changeGUISize() {
        setGUISize();
        cardGrid.changeGUISize();
        if (currentView instanceof CardGrid) {
            this.currentView.drawCards(sortSetting);
        }
    }

    private void setGUISize() {
        mainTable.getTableHeader().setFont(GUISizeHelper.tableFont);
        mainTable.setFont(GUISizeHelper.tableFont);
        mainTable.setRowHeight(GUISizeHelper.getTableRowHeight());

    }

    public void switchToGrid() {
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

    public void loadSideboard(java.util.List<Card> sideboard, BigCard bigCard) {
        this.bigCard = bigCard;
        this.btnBooster.setVisible(false);
        this.btnClear.setVisible(false);
        this.cbExpansionSet.setVisible(false);
        this.btnExpansionSearch.setVisible(false);
        this.limited = true;
        this.cards.clear();
        this.cards.addAll(sideboard);
        filterCards();
    }

    public void loadCards(BigCard bigCard) {
        this.bigCard = bigCard;
        this.btnBooster.setVisible(true);
        this.btnClear.setVisible(true);
        this.cbExpansionSet.setVisible(true);
        this.btnExpansionSearch.setVisible(true);

        // select "all" by default
        try {
            isSetsFilterLoading = true;
            cbExpansionSet.setSelectedIndex(0);
        } finally {
            isSetsFilterLoading = false;
        }
        filterCards();
    }

    private FilterCard buildFilter() {
        FilterCard filter = new FilterCard();

        String name = jTextFieldSearch.getText().trim();
        filter.add(new CardTextPredicate(name, chkNames.isSelected(), chkTypes.isSelected(), chkRules.isSelected(), chkUnique.isSelected()));

        if (limited) {
            List<Predicate<MageObject>> predicates = new ArrayList<>();

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
                predicates.add(ColorlessPredicate.instance);
            }
            filter.add(Predicates.or(predicates));

            predicates.clear();
            if (this.tbLand.isSelected()) {
                predicates.add(CardType.LAND.getPredicate());
            }
            if (this.tbArifiacts.isSelected()) {
                predicates.add(CardType.ARTIFACT.getPredicate());
            }
            if (this.tbCreatures.isSelected()) {
                predicates.add(CardType.CREATURE.getPredicate());
            }
            if (this.tbEnchantments.isSelected()) {
                predicates.add(CardType.ENCHANTMENT.getPredicate());
            }
            if (this.tbInstants.isSelected()) {
                predicates.add(CardType.INSTANT.getPredicate());
            }
            if (this.tbSorceries.isSelected()) {
                predicates.add(CardType.SORCERY.getPredicate());
            }
            if (this.tbPlaneswalkers.isSelected()) {
                predicates.add(CardType.PLANESWALKER.getPredicate());
            }
            filter.add(Predicates.or(predicates));

            List<String> filteredSets = getFilteredSets();
            if (!filteredSets.isEmpty()) {
                List<Predicate<Card>> expansionPredicates = new ArrayList<>();
                for (String setCode : filteredSets) {
                    expansionPredicates.add(new ExpansionSetPredicate(setCode));
                }
                filter.add(Predicates.or(expansionPredicates));
            }
        }

        return filter;
    }

    private void setSetsSelection(String newSelection) {
        // combo and checkbox can contain different elements (e.g. without "all" or "multi" options),
        // so must search by string value
        for (int index = 0; index < cbExpansionSet.getItemCount(); index++) {
            if (cbExpansionSet.getItemAt(index).equals(newSelection)) {
                if (cbExpansionSet.getSelectedIndex() != index) {
                    cbExpansionSet.setSelectedIndex(index);
                }
            }
        }
    }

    private List<String> getFilteredSets() {
        // empty list - show all sets

        List<String> res = new ArrayList<>();
        if (!this.cbExpansionSet.isVisible()) {
            return res;
        }

        if (listCodeSelected.getCheckedIndices().length <= 1) {
            // single set selected
            String expansionSelection = this.cbExpansionSet.getSelectedItem().toString();
            if (!expansionSelection.equals(ConstructedFormats.ALL_SETS)
                    && !expansionSelection.startsWith(MULTI_SETS_SELECTION_TEXT)) {
                res.addAll(ConstructedFormats.getSetsByFormat(expansionSelection));
            }
        } else {
            // multiple sets selected
            int[] choiseValue = listCodeSelected.getCheckedIndices();
            ListModel x = listCodeSelected.getModel();

            for (int itemIndex : choiseValue) {
                java.util.List<String> listReceived = ConstructedFormats.getSetsByFormat(x.getElementAt(itemIndex).toString());
                listReceived.stream()
                        .filter(item -> !res.contains(item))
                        .forEachOrdered(res::add);
            }
        }

        return res;
    }

    private CardCriteria buildCriteria() {
        CardCriteria criteria = new CardCriteria();
        criteria.black(this.tbBlack.isSelected());
        criteria.blue(this.tbBlue.isSelected());
        criteria.green(this.tbGreen.isSelected());
        criteria.red(this.tbRed.isSelected());
        criteria.white(this.tbWhite.isSelected());
        criteria.colorless(this.tbColorless.isSelected());

        // if you add new type filter then sync it with CardType
        if (this.tbLand.isSelected()) {
            criteria.types(CardType.LAND);
        }
        if (this.tbArifiacts.isSelected()) {
            criteria.types(CardType.ARTIFACT);
        }
        if (this.tbCreatures.isSelected()) {
            criteria.types(CardType.CREATURE);
        }
        if (this.tbEnchantments.isSelected()) {
            criteria.types(CardType.ENCHANTMENT);
        }
        if (this.tbInstants.isSelected()) {
            criteria.types(CardType.INSTANT);
        }
        if (this.tbSorceries.isSelected()) {
            criteria.types(CardType.SORCERY);
        }
        if (this.tbPlaneswalkers.isSelected()) {
            criteria.types(CardType.PLANESWALKER);
        }

        if (this.tbCommon.isSelected()) {
            criteria.rarities(Rarity.COMMON);
            criteria.rarities(Rarity.LAND);
        }
        if (this.tbUncommon.isSelected()) {
            criteria.rarities(Rarity.UNCOMMON);
        }
        if (this.tbRare.isSelected()) {
            criteria.rarities(Rarity.RARE);
        }
        if (this.tbMythic.isSelected()) {
            criteria.rarities(Rarity.MYTHIC);
        }
        if (this.tbSpecial.isSelected()) {
            criteria.rarities(Rarity.SPECIAL);
            criteria.rarities(Rarity.BONUS);
        }

        List<String> filteredSets = getFilteredSets();
        if (!filteredSets.isEmpty()) {
            criteria.setCodes(filteredSets.toArray(new String[0]));
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
        // ALT or CTRL button was pushed
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
        // ALT or CTRL button was pushed
        if ((modifiers & ActionEvent.ALT_MASK) == ActionEvent.ALT_MASK || (modifiers & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
            boolean invert = (modifiers & ActionEvent.ALT_MASK) == ActionEvent.ALT_MASK;
            tbArifiacts.setSelected(inverter(invert, tbArifiacts.getActionCommand(), actionCommand));
            tbCreatures.setSelected(inverter(invert, tbCreatures.getActionCommand(), actionCommand));
            tbEnchantments.setSelected(inverter(invert, tbEnchantments.getActionCommand(), actionCommand));
            tbInstants.setSelected(inverter(invert, tbInstants.getActionCommand(), actionCommand));
            tbLand.setSelected(inverter(invert, tbLand.getActionCommand(), actionCommand));
            tbPlaneswalkers.setSelected(inverter(invert, tbPlaneswalkers.getActionCommand(), actionCommand));
            tbSorceries.setSelected(inverter(invert, tbSorceries.getActionCommand(), actionCommand));
        }
        filterCards();
    }

    private void filterCardsRarity(int modifiers, String actionCommand) {
        // ALT or CTRL button was pushed
        if ((modifiers & ActionEvent.ALT_MASK) == ActionEvent.ALT_MASK || (modifiers & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
            boolean invert = (modifiers & ActionEvent.ALT_MASK) == ActionEvent.ALT_MASK;
            tbCommon.setSelected(inverter(invert, tbCommon.getActionCommand(), actionCommand));
            tbUncommon.setSelected(inverter(invert, tbUncommon.getActionCommand(), actionCommand));
            tbRare.setSelected(inverter(invert, tbRare.getActionCommand(), actionCommand));
            tbMythic.setSelected(inverter(invert, tbMythic.getActionCommand(), actionCommand));
            tbSpecial.setSelected(inverter(invert, tbSpecial.getActionCommand(), actionCommand));
        }
        filterCards();
    }

    private void filterCards() {
        FilterCard filter = buildFilter();
        MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {

            // debug
            //debugObjectMemorySize("Old cards size", this.currentView.getCardsStore());
            this.currentView.clearCardsStoreBeforeUpdate();

            java.util.List<Card> filteredCards = new ArrayList<>();

            if (chkPennyDreadful.isSelected() && pdAllowed.isEmpty()) {
                pdAllowed.putAll(PennyDreadfulLegalityUtil.getLegalCardList());
            }

            if (limited) {
                for (Card card : cards) {
                    if (filter.match(card, null)) {
                        filteredCards.add(card);
                    }
                }
            } else {
                java.util.List<CardInfo> foundCards = CardRepository.instance.findCards(buildCriteria());

                for (CardInfo cardInfo : foundCards) {
                    // filter by penny
                    if (chkPennyDreadful.isSelected()) {
                        if (!pdAllowed.containsKey(cardInfo.getName())) {
                            continue;
                        }
                    }
                    // filter by settings
                    Card card = cardInfo.getMockCard();
                    if (!filter.match(card, null)) {
                        continue;
                    }
                    // found
                    filteredCards.add(card);
                }
            }

            // force to list mode on too much cards
            if (currentView instanceof CardGrid && filteredCards.size() > CardGrid.MAX_IMAGES) {
                this.toggleViewMode();
            }

            // debug
            //debugObjectMemorySize("New cards size", filteredCards);

            this.currentView.loadCards(new CardsView(filteredCards), sortSetting, bigCard, null, false);
            this.cardCount.setText(String.valueOf(filteredCards.size()));
        } finally {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void debugObjectMemorySize(String name, Object object) {
        // just debug code, don't use it in production
        // need 2x memory to find a size
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            logger.info(name + ": " + baos.size());
        } catch (Throwable e) {
            logger.fatal("Can't find object size: " + e.getMessage(), e);
        }
    }

    public void setCardCount(int value) {
        this.cardCount.setText(String.valueOf(value));
    }

    public java.util.List<ICardGrid> getCardGridComponents() {
        java.util.List<ICardGrid> components = new ArrayList<>();
        components.add(mainModel);
        components.add(cardGrid);
        return components;
    }

    public void removeCard(UUID cardId) {
        this.mainModel.removeCard(cardId);
        this.cardGrid.removeCard(cardId);
        for (Card card : cards) {
            if (card.getId().equals(cardId)) {
                cards.remove(card);
                break;
            }
        }
    }

    private void reloadSetsCombobox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel<>(ConstructedFormats.getTypes());
        cbExpansionSet.setModel(model);
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
        tbColor = new javax.swing.JToolBar();
        tbRed = new javax.swing.JToggleButton();
        tbGreen = new javax.swing.JToggleButton();
        tbBlue = new javax.swing.JToggleButton();
        tbBlack = new javax.swing.JToggleButton();
        tbWhite = new javax.swing.JToggleButton();
        tbColorless = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cbExpansionSet = new javax.swing.JComboBox<>();
        btnExpansionSearch = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        chkPennyDreadful = new javax.swing.JCheckBox();
        btnBooster = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        tbTypes = new javax.swing.JToolBar();
        tbLand = new javax.swing.JToggleButton();
        tbCreatures = new javax.swing.JToggleButton();
        tbArifiacts = new javax.swing.JToggleButton();
        tbSorceries = new javax.swing.JToggleButton();
        tbInstants = new javax.swing.JToggleButton();
        tbEnchantments = new javax.swing.JToggleButton();
        tbPlaneswalkers = new javax.swing.JToggleButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        chkPiles = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cbSortBy = new javax.swing.JComboBox<>();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jToggleListView = new javax.swing.JToggleButton();
        jToggleCardView = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        tbRarities = new javax.swing.JToolBar();
        tbCommon = new javax.swing.JToggleButton();
        tbUncommon = new javax.swing.JToggleButton();
        tbRare = new javax.swing.JToggleButton();
        tbMythic = new javax.swing.JToggleButton();
        tbSpecial = new javax.swing.JToggleButton();
        cardSelectorScrollPane = new javax.swing.JScrollPane();
        cardSelectorBottomPanel = new javax.swing.JPanel();
        jButtonAddToMain = new javax.swing.JButton();
        jButtonRemoveFromMain = new javax.swing.JButton();
        jButtonAddToSideboard = new javax.swing.JButton();
        jButtonRemoveFromSideboard = new javax.swing.JButton();
        jTextFieldSearch = new javax.swing.JTextField();
        chkNames = new javax.swing.JCheckBox();
        chkTypes = new javax.swing.JCheckBox();
        chkRules = new javax.swing.JCheckBox();
        chkUnique = new javax.swing.JCheckBox();
        jButtonSearch = new javax.swing.JButton();
        jButtonClean = new javax.swing.JButton();
        cardCountLabel = new javax.swing.JLabel();
        cardCount = new javax.swing.JLabel();

        tbColor.setFloatable(false);
        tbColor.setRollover(true);
        tbColor.setToolTipText("Hold the ALT-key while clicking to deselect all other colors or hold the CTRL-key to select only all other colors.");
        tbColor.setBorderPainted(false);
        tbColor.setName(""); // NOI18N

        tbRed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_red_off.png"))); // NOI18N
        tbRed.setSelected(true);
        tbRed.setToolTipText("<html><font color='red'><strong>Red</strong></font><br/>"
                + tbColor.getToolTipText());
        tbRed.setActionCommand("Red");
        tbRed.setFocusable(false);
        tbRed.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbRed.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_red.png"))); // NOI18N
        tbRed.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbRedActionPerformed(evt);
            }
        });
        tbColor.add(tbRed);

        tbGreen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_green_off.png"))); // NOI18N
        tbGreen.setSelected(true);
        tbGreen.setToolTipText("<html><font color='Green'><strong>Green</strong></font><br/>" + tbColor.getToolTipText());
        tbGreen.setActionCommand("Green");
        tbGreen.setFocusable(false);
        tbGreen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbGreen.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_green.png"))); // NOI18N
        tbGreen.setVerifyInputWhenFocusTarget(false);
        tbGreen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbGreenActionPerformed(evt);
            }
        });
        tbColor.add(tbGreen);

        tbBlue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_blueOff.png"))); // NOI18N
        tbBlue.setSelected(true);
        tbBlue.setToolTipText("<html><font color='blue'><strong>Blue</strong></font><br/>" + tbColor.getToolTipText());
        tbBlue.setActionCommand("Blue");
        tbBlue.setFocusable(false);
        tbBlue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbBlue.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_blue.png"))); // NOI18N
        tbBlue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbBlueActionPerformed(evt);
            }
        });
        tbColor.add(tbBlue);

        tbBlack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_black_off.png"))); // NOI18N
        tbBlack.setSelected(true);
        tbBlack.setToolTipText("<html><font color='black'><strong>Black</strong></font><br/>" + tbColor.getToolTipText());
        tbBlack.setActionCommand("Black");
        tbBlack.setFocusable(false);
        tbBlack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbBlack.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_black.png"))); // NOI18N
        tbBlack.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbBlackActionPerformed(evt);
            }
        });
        tbColor.add(tbBlack);

        tbWhite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_white_off.png"))); // NOI18N
        tbWhite.setSelected(true);
        tbWhite.setToolTipText("<html><font color='grey'><strong>White</strong></font><br/>" + tbColor.getToolTipText());
        tbWhite.setActionCommand("White");
        tbWhite.setFocusable(false);
        tbWhite.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbWhite.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/color_white.png"))); // NOI18N
        tbWhite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbWhiteActionPerformed(evt);
            }
        });
        tbColor.add(tbWhite);

        tbColorless.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/colorless_off.png"))); // NOI18N
        tbColorless.setSelected(true);
        tbColorless.setToolTipText("<html>Colorless<br/>" + tbColor.getToolTipText());
        tbColorless.setActionCommand("Colorless");
        tbColorless.setFocusable(false);
        tbColorless.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbColorless.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/colorless.png"))); // NOI18N
        tbColorless.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbColorless.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbColorlessActionPerformed(evt);
            }
        });
        tbColor.add(tbColorless);
        tbColor.add(jSeparator1);

        reloadSetsCombobox();
        cbExpansionSet.setMaximumSize(new java.awt.Dimension(250, 25));
        cbExpansionSet.setMinimumSize(new java.awt.Dimension(250, 25));
        cbExpansionSet.setName("cbExpansionSet"); // NOI18N
        cbExpansionSet.setPreferredSize(new java.awt.Dimension(250, 25));
        cbExpansionSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbExpansionSetActionPerformed(evt);
            }
        });

        // auto-update sets list on changes
        // doesn't use any more due to db recreation on update
        setsDbListener = new Listener<RepositoryEvent>() {
            @Override
            public void event(RepositoryEvent event) {
                if (event.getEventType().equals(RepositoryEvent.RepositoryEventType.DB_UPDATED)) {
                    reloadSetsCombobox();
                    // TODO: auto-refresh cards list
                }
            }
        };
        ExpansionRepository.instance.subscribe(setsDbListener);
        tbColor.add(cbExpansionSet);

        btnExpansionSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_32.png"))); // NOI18N
        btnExpansionSearch.setToolTipText("Fast search set or expansion");
        btnExpansionSearch.setAlignmentX(1.0F);
        btnExpansionSearch.setFocusable(false);
        btnExpansionSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExpansionSearch.setPreferredSize(new java.awt.Dimension(23, 23));
        btnExpansionSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExpansionSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpansionSearchActionPerformed(evt);
            }
        });
        tbColor.add(btnExpansionSearch);
        tbColor.add(jSeparator2);

        chkPennyDreadful.setText("Penny Dreadful Only");
        chkPennyDreadful.setToolTipText("Will only allow Penny Dreadful legal cards to be shown.");
        chkPennyDreadful.setFocusable(false);
        chkPennyDreadful.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkPennyDreadful.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chkPennyDreadful.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPilesActionPerformed(evt);
            }
        });
        tbColor.add(chkPennyDreadful);

        btnBooster.setText("Open Booster");
        btnBooster.setToolTipText("Generates a booster of the selected set and adds the cards to the card selector.");
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
        tbTypes.setToolTipText("Hold the ALT-key while clicking to deselect all other card types or hold the CTRL-key to only select all other card types."); // NOI18N
        tbTypes.setPreferredSize(new java.awt.Dimension(732, 27));

        tbLand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_land.png"))); // NOI18N
        tbLand.setSelected(true);
        tbLand.setToolTipText("<html><strong>Land</strong><br/>"
                + tbTypes.getToolTipText());
        tbLand.setActionCommand("Lands");
        tbLand.setFocusable(false);
        tbLand.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbLand.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbLandActionPerformed(evt);
            }
        });
        tbTypes.add(tbLand);

        tbCreatures.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_creatures.png"))); // NOI18N
        tbCreatures.setSelected(true);
        tbCreatures.setToolTipText("<html><strong>Creatures</strong><br/>"
                + tbTypes.getToolTipText());
        tbCreatures.setActionCommand("Creatures");
        tbCreatures.setFocusable(false);
        tbCreatures.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbCreatures.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbCreatures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbCreaturesActionPerformed(evt);
            }
        });
        tbTypes.add(tbCreatures);

        tbArifiacts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_artifact.png"))); // NOI18N
        tbArifiacts.setSelected(true);
        tbArifiacts.setToolTipText("<html><strong>Artifacts</strong><br/>"
                + tbTypes.getToolTipText());
        tbArifiacts.setActionCommand("Artifacts");
        tbArifiacts.setFocusable(false);
        tbArifiacts.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbArifiacts.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbArifiacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbArifiactsActionPerformed(evt);
            }
        });
        tbTypes.add(tbArifiacts);

        tbSorceries.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_sorcery.png"))); // NOI18N
        tbSorceries.setSelected(true);
        tbSorceries.setToolTipText("<html><strong>Sorceries</strong><br/>"
                + tbTypes.getToolTipText());
        tbSorceries.setActionCommand("Soceries");
        tbSorceries.setFocusable(false);
        tbSorceries.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbSorceries.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbSorceries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbSorceriesActionPerformed(evt);
            }
        });
        tbTypes.add(tbSorceries);

        tbInstants.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_instant.png"))); // NOI18N
        tbInstants.setSelected(true);
        tbInstants.setToolTipText("<html><strong>Instants</strong><br/>"
                + tbTypes.getToolTipText());
        tbInstants.setActionCommand("Instants");
        tbInstants.setFocusable(false);
        tbInstants.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbInstants.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbInstants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbInstantsActionPerformed(evt);
            }
        });
        tbTypes.add(tbInstants);

        tbEnchantments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_enchantment.png"))); // NOI18N
        tbEnchantments.setSelected(true);
        tbEnchantments.setToolTipText("<html><strong>Enchantments</strong><br/>"
                + tbTypes.getToolTipText());
        tbEnchantments.setActionCommand("Enchantments");
        tbEnchantments.setFocusable(false);
        tbEnchantments.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbEnchantments.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbEnchantments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbEnchantmentsActionPerformed(evt);
            }
        });
        tbTypes.add(tbEnchantments);

        tbPlaneswalkers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_planeswalker.png"))); // NOI18N
        tbPlaneswalkers.setSelected(true);
        tbPlaneswalkers.setToolTipText("<html><strong>Planeswalker</strong><br/>"
                + tbTypes.getToolTipText());
        tbPlaneswalkers.setActionCommand("Planeswalkers");
        tbPlaneswalkers.setFocusable(false);
        tbPlaneswalkers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbPlaneswalkers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbPlaneswalkers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbPlaneswalkersActionPerformed(evt);
            }
        });
        tbTypes.add(tbPlaneswalkers);
        tbTypes.add(jSeparator6);

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
        jToggleListView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/list_panel.png"))); // NOI18N
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
        jToggleCardView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/card_panel.png"))); // NOI18N
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
        tbTypes.add(jSeparator5);

        tbRarities.setFloatable(false);
        tbRarities.setRollover(true);
        tbRarities.setToolTipText("Hold the ALT-key while clicking to deselect all other card rarities or hold the CTRL-key to only select all other card rarities.");

        tbCommon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/rarity_common_20.png"))); // NOI18N
        tbCommon.setSelected(true);
        tbCommon.setToolTipText("<html><strong>Common</strong><br/>"
                + tbRarities.getToolTipText());
        tbCommon.setActionCommand("Common");
        tbCommon.setFocusable(false);
        tbCommon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbCommon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbCommon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbCommonActionPerformed(evt);
            }
        });
        tbRarities.add(tbCommon);

        tbUncommon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/rarity_uncommon_20.png"))); // NOI18N
        tbUncommon.setSelected(true);
        tbUncommon.setToolTipText("<html><strong>Uncommon</strong><br/>"
                + tbRarities.getToolTipText());
        tbUncommon.setActionCommand("Uncommon");
        tbUncommon.setFocusable(false);
        tbUncommon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbUncommon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbUncommon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbUncommonActionPerformed(evt);
            }
        });
        tbRarities.add(tbUncommon);

        tbRare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/rarity_rare_20.png"))); // NOI18N
        tbRare.setSelected(true);
        tbRare.setToolTipText("<html><strong>Rare</strong><br/>"
                + tbRarities.getToolTipText());
        tbRare.setActionCommand("Rare");
        tbRare.setFocusable(false);
        tbRare.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbRare.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbRare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbRareActionPerformed(evt);
            }
        });
        tbRarities.add(tbRare);

        tbMythic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/rarity_mythic_20.png"))); // NOI18N
        tbMythic.setSelected(true);
        tbMythic.setToolTipText("<html><strong>Mythic</strong><br/>"
                + tbRarities.getToolTipText());
        tbMythic.setActionCommand("Mythic");
        tbMythic.setFocusable(false);
        tbMythic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbMythic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMythic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbMythicActionPerformed(evt);
            }
        });
        tbRarities.add(tbMythic);

        tbSpecial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/rarity_special_20.png"))); // NOI18N
        tbSpecial.setSelected(true);
        tbSpecial.setToolTipText("<html><strong>Special</strong><br/>"
                + tbRarities.getToolTipText());
        tbSpecial.setActionCommand("Special");
        tbSpecial.setFocusable(false);
        tbSpecial.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbSpecial.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbSpecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbSpecialActionPerformed(evt);
            }
        });
        tbRarities.add(tbSpecial);

        tbTypes.add(tbRarities);

        cardSelectorScrollPane.setToolTipText("<HTML>Double click to add the card to the main deck.<br/>\nALT + Double click to add the card to the sideboard.");

        cardSelectorBottomPanel.setOpaque(false);
        cardSelectorBottomPanel.setPreferredSize(new java.awt.Dimension(897, 40));

        jButtonAddToMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/deck_in.png"))); // NOI18N
        jButtonAddToMain.setToolTipText("<html>Add selected cards to deck.<br/>\nAlternative: <strong>Double click</strong> the card in card selector to move a card to the deck.");
        jButtonAddToMain.setMargin(null);
        jButtonAddToMain.setMaximumSize(new java.awt.Dimension(35, 23));
        jButtonAddToMain.setMinimumSize(new java.awt.Dimension(35, 23));
        jButtonAddToMain.setPreferredSize(new java.awt.Dimension(30, 28));
        jButtonAddToMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToMainActionPerformed(evt);
            }
        });

        jButtonRemoveFromMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/deck_out.png"))); // NOI18N
        jButtonRemoveFromMain.setToolTipText("Remove selected cards from deck");
        jButtonRemoveFromMain.setMargin(null);
        jButtonRemoveFromMain.setMaximumSize(new java.awt.Dimension(42, 23));
        jButtonRemoveFromMain.setMinimumSize(new java.awt.Dimension(42, 23));
        jButtonRemoveFromMain.setPreferredSize(new java.awt.Dimension(30, 28));
        jButtonRemoveFromMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveFromMainActionPerformed(evt);
            }
        });

        jButtonAddToSideboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/sideboard_in.png"))); // NOI18N
        jButtonAddToSideboard.setToolTipText("<html>Add selected cards to sideboard.<br/>\nAlternative: <strong>ALT key + Double click</strong> the card in card selector to move a card to the sideboard.");
        jButtonAddToSideboard.setMargin(new java.awt.Insets(2, 0, 2, 0));
        jButtonAddToSideboard.setMaximumSize(new java.awt.Dimension(100, 30));
        jButtonAddToSideboard.setMinimumSize(new java.awt.Dimension(10, 30));
        jButtonAddToSideboard.setPreferredSize(new java.awt.Dimension(30, 28));
        jButtonAddToSideboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToSideboardActionPerformed(evt);
            }
        });

        jButtonRemoveFromSideboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/sideboard_out.png"))); // NOI18N
        jButtonRemoveFromSideboard.setToolTipText("Remove selected cards from sideboard.");
        jButtonRemoveFromSideboard.setMargin(new java.awt.Insets(2, 0, 2, 0));
        jButtonRemoveFromSideboard.setMaximumSize(new java.awt.Dimension(10, 30));
        jButtonRemoveFromSideboard.setMinimumSize(new java.awt.Dimension(100, 30));
        jButtonRemoveFromSideboard.setPreferredSize(new java.awt.Dimension(30, 28));
        jButtonRemoveFromSideboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveFromSideboardActionPerformed(evt);
            }
        });

        jTextFieldSearch.setToolTipText("Searches for card names and in the rule text of the card.");

        chkNames.setSelected(true);
        chkNames.setText("Names");
        chkNames.setToolTipText("Search in card names.");
        chkNames.setFocusable(false);
        chkNames.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkNames.setMaximumSize(new java.awt.Dimension(67, 16));
        chkNames.setMinimumSize(new java.awt.Dimension(67, 16));
        chkNames.setPreferredSize(new java.awt.Dimension(67, 16));
        chkNames.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chkNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNamesActionPerformed(evt);
            }
        });

        chkTypes.setSelected(true);
        chkTypes.setText("Types");
        chkTypes.setToolTipText("Search in card types.");
        chkTypes.setFocusable(false);
        chkTypes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkTypes.setMaximumSize(new java.awt.Dimension(63, 16));
        chkTypes.setMinimumSize(new java.awt.Dimension(63, 16));
        chkTypes.setPreferredSize(new java.awt.Dimension(63, 16));
        chkTypes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chkTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTypesActionPerformed(evt);
            }
        });

        chkRules.setSelected(true);
        chkRules.setText("Rules");
        chkRules.setToolTipText("Search in card rules.");
        chkRules.setFocusable(false);
        chkRules.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkRules.setMaximumSize(new java.awt.Dimension(59, 16));
        chkRules.setMinimumSize(new java.awt.Dimension(59, 16));
        chkRules.setPreferredSize(new java.awt.Dimension(59, 16));
        chkRules.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chkRules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRulesActionPerformed(evt);
            }
        });

        chkUnique.setSelected(false);
        chkUnique.setText("Unique");
        chkUnique.setToolTipText("Show only the first found card of every card name.");
        chkUnique.setFocusable(false);
        chkUnique.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkUnique.setMaximumSize(new java.awt.Dimension(69, 16));
        chkUnique.setMinimumSize(new java.awt.Dimension(69, 16));
        chkUnique.setPreferredSize(new java.awt.Dimension(69, 16));
        chkUnique.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chkUnique.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkUniqueActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout cardSelectorBottomPanelLayout = new javax.swing.GroupLayout(cardSelectorBottomPanel);
        cardSelectorBottomPanel.setLayout(cardSelectorBottomPanelLayout);
        cardSelectorBottomPanelLayout.setHorizontalGroup(
                cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardSelectorBottomPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButtonAddToMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jButtonRemoveFromMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jButtonAddToSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jButtonRemoveFromSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonClean)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkNames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkRules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(chkUnique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(cardCountLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cardCount, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardSelectorBottomPanelLayout.setVerticalGroup(
                cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardSelectorBottomPanelLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(chkTypes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(chkRules, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(chkUnique, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(chkNames, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(cardSelectorBottomPanelLayout.createSequentialGroup()
                                                .addGroup(cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jButtonRemoveFromMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButtonAddToSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButtonRemoveFromSideboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(cardSelectorBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jButtonSearch)
                                                                .addComponent(jButtonClean)
                                                                .addComponent(cardCount)
                                                                .addComponent(jButtonAddToMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(cardCountLabel)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        cardCountLabel.getAccessibleContext().setAccessibleName("cardCountLabel");
        cardCount.getAccessibleContext().setAccessibleName("cardCount");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tbColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tbTypes, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
                        .addComponent(cardSelectorScrollPane)
                        .addComponent(cardSelectorBottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(tbTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(cardSelectorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(cardSelectorBottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbExpansionSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbExpansionSetActionPerformed
        // one item selected by user

        // ignore combobox modifying
        if (isSetsFilterLoading) {
            return;
        }

        // auto-remove unused multi-select item and sync checkboxes
        if (!cbExpansionSet.getSelectedItem().toString().startsWith(MULTI_SETS_SELECTION_TEXT)) {
            int currentSelection = cbExpansionSet.getSelectedIndex();
            isSetsFilterLoading = true;
            try {
                // remove multi item and fix selection
                if (cbExpansionSet.getItemAt(0).startsWith(MULTI_SETS_SELECTION_TEXT)) {
                    cbExpansionSet.removeItemAt(0);
                    currentSelection = Math.max(0, currentSelection - 1);
                }
            } finally {
                isSetsFilterLoading = false;
            }

            // sync checkboxes
            listCodeSelected.uncheckAll();
            if (currentSelection > 0) {
                // if not "all" option selected
                // checkbox haven't "all", so use another indexes
                listCodeSelected.setChecked(currentSelection - 1, true);
            }
        }

        // update data
        filterCards();
    }//GEN-LAST:event_cbExpansionSetActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        cards.clear();
        this.limited = false;
        filterCards();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnBoosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoosterActionPerformed
        List<String> sets = getFilteredSets();
        if (sets.size() == 1) {
            if (!this.limited) {
                this.limited = true;
                cards.clear();
            }
            // accumulate boosters in one list
            ExpansionSet expansionSet = Sets.getInstance().get(sets.get(0));
            if (expansionSet != null) {
                java.util.List<Card> booster = expansionSet.createBooster();
                cards.addAll(booster);
                filterCards();
            }
        } else {
            MageFrame.getInstance().showMessage("An expansion set must be selected to be able to generate a booster.");
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
            MageFrame.getInstance().showMessage("The card view can't be used for more than " + CardGrid.MAX_IMAGES + " cards.");
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
            java.util.List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                // normal double click emulation
                mainModel.doubleClick(index, null, false);
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
            java.util.List<Integer> indexes = asList(n);
            Collections.reverse(indexes);
            for (Integer index : indexes) {
                // ALT double click emulation
                mainModel.doubleClick(index, null, true);
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

    private void tbCreaturesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbCreaturesActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbCreaturesActionPerformed

    private void tbArifiactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbArifiactsActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbArifiactsActionPerformed

    private void tbSorceriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbSorceriesActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbSorceriesActionPerformed

    private void tbInstantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbInstantsActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbInstantsActionPerformed

    private void tbEnchantmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbEnchantmentsActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbEnchantmentsActionPerformed

    private void tbPlaneswalkersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbPlaneswalkersActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbPlaneswalkersActionPerformed

    private void tbLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbLandActionPerformed
        filterCardsType(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbLandActionPerformed

    private void chkNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNamesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkNamesActionPerformed

    private void chkTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTypesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTypesActionPerformed

    private void chkRulesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void chkUniqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRulesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkRulesActionPerformed

    private void btnExpansionSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpansionSearchActionPerformed
        // search and check multiple items

        int[] oldChecks = listCodeSelected.getCheckedIndices();

        // call dialog
        FastSearchUtil.showFastSearchForStringComboBox(listCodeSelected, FastSearchUtil.DEFAULT_EXPANSION_SEARCH_MESSAGE);

        int[] newChecks = listCodeSelected.getCheckedIndices();
        if (Arrays.equals(oldChecks, newChecks)) {
            // no changes or cancel
            return;
        }

        isSetsFilterLoading = true;
        try {
            // delete old item
            if (cbExpansionSet.getItemAt(0).startsWith(MULTI_SETS_SELECTION_TEXT)) {
                cbExpansionSet.removeItemAt(0);
            }

            // set new selection
            if (newChecks.length == 0) {
                // all
                cbExpansionSet.setSelectedIndex(0);
            } else if (newChecks.length == 1) {
                // one
                setSetsSelection(listCodeSelected.getModel().getElementAt(newChecks[0]).toString());
            } else {
                // multiple
                // insert custom text
                String message = String.format("%s: %d", MULTI_SETS_SELECTION_TEXT, newChecks.length);
                cbExpansionSet.insertItemAt(message, 0);
                cbExpansionSet.setSelectedIndex(0);
            }
        } finally {
            isSetsFilterLoading = false;
        }

        // update data
        filterCards();
    }//GEN-LAST:event_btnExpansionSearchActionPerformed

    private void tbCommonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbCommonActionPerformed
        filterCardsRarity(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbCommonActionPerformed

    private void tbUncommonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbUncommonActionPerformed
        filterCardsRarity(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbUncommonActionPerformed

    private void tbRareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbRareActionPerformed
        filterCardsRarity(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbRareActionPerformed

    private void tbMythicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbMythicActionPerformed
        filterCardsRarity(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbMythicActionPerformed

    private void tbSpecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbSpecialActionPerformed
        filterCardsRarity(evt.getModifiers(), evt.getActionCommand());
    }//GEN-LAST:event_tbSpecialActionPerformed

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

    public java.util.List<Integer> asList(final int[] is) {
        java.util.List<Integer> list = new ArrayList<>();
        for (int i : is) {
            list.add(i);
        }
        return list;
    }

    public void refresh() {
        SwingUtilities.invokeLater(() -> currentView.refresh());
    }

    private TableModel mainModel;
    private JTable mainTable;
    private ICardGrid currentView;

    private final CheckBoxList listCodeSelected;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgView;
    private javax.swing.JButton btnBooster;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExpansionSearch;
    private javax.swing.JLabel cardCount;
    private javax.swing.JLabel cardCountLabel;
    private javax.swing.JPanel cardSelectorBottomPanel;
    private javax.swing.JScrollPane cardSelectorScrollPane;
    private javax.swing.JComboBox<String> cbExpansionSet;
    private javax.swing.JComboBox<SortBy> cbSortBy;
    private javax.swing.JCheckBox chkNames;
    private javax.swing.JCheckBox chkPennyDreadful;
    private javax.swing.JCheckBox chkPiles;
    private javax.swing.JCheckBox chkRules;
    private javax.swing.JCheckBox chkTypes;
    private javax.swing.JCheckBox chkUnique;
    private javax.swing.JButton jButtonAddToMain;
    private javax.swing.JButton jButtonAddToSideboard;
    private javax.swing.JButton jButtonClean;
    private javax.swing.JButton jButtonRemoveFromMain;
    private javax.swing.JButton jButtonRemoveFromSideboard;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JToggleButton jToggleCardView;
    private javax.swing.JToggleButton jToggleListView;
    private javax.swing.JToggleButton tbArifiacts;
    private javax.swing.JToggleButton tbBlack;
    private javax.swing.JToggleButton tbBlue;
    private javax.swing.JToolBar tbColor;
    private javax.swing.JToggleButton tbColorless;
    private javax.swing.JToggleButton tbCommon;
    private javax.swing.JToggleButton tbCreatures;
    private javax.swing.JToggleButton tbEnchantments;
    private javax.swing.JToggleButton tbGreen;
    private javax.swing.JToggleButton tbInstants;
    private javax.swing.JToggleButton tbLand;
    private javax.swing.JToggleButton tbMythic;
    private javax.swing.JToggleButton tbPlaneswalkers;
    private javax.swing.JToggleButton tbRare;
    private javax.swing.JToolBar tbRarities;
    private javax.swing.JToggleButton tbRed;
    private javax.swing.JToggleButton tbSorceries;
    private javax.swing.JToggleButton tbSpecial;
    private javax.swing.JToolBar tbTypes;
    private javax.swing.JToggleButton tbUncommon;
    private javax.swing.JToggleButton tbWhite;
    // End of variables declaration//GEN-END:variables

    private final mage.client.cards.CardGrid cardGrid; // grid for piles view mode (example: selected cards in drafting)

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

    @Override
    public void dragCardEnter(MouseEvent e) {
        // Nothing to do for now. We could show something
    }

    @Override
    public void dragCardMove(MouseEvent e) {
        // Nothing to do for now. We could show something
    }

    @Override
    public void dragCardExit(MouseEvent e) {
        // Nothing to do for now. We could show something
    }

    @Override
    public void dragCardDrop(MouseEvent e, DragCardSource source, Collection<CardView> cards) {
        // Need to add cards back to tally
    }

    private static void makeButtonPopup(final AbstractButton button, final JPopupMenu popup) {
        button.addActionListener(e -> popup.show(button, 0, button.getHeight()));
    }
}
