package mage.client.cards;

import mage.cards.Card;
import mage.cards.MageCard;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLayout;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.util.RandomUtil;
import mage.view.CardView;
import mage.view.CardsView;
import org.apache.log4j.Logger;
import org.mage.card.arcane.CardRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by StravantUser on 2016-09-20.
 */
public class DragCardGrid extends JPanel implements DragCardSource, DragCardTarget {

    private static final Logger LOGGER = Logger.getLogger(DragCardGrid.class);
    private Constants.DeckEditorMode mode;

    @Override
    public Collection<CardView> dragCardList() {
        return allCards.stream().filter(CardView::isSelected).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void dragCardBegin() {

    }

    @Override
    public void dragCardEnd(DragCardTarget target) {
        if (target == this) {
            // Already handled by dragged onto handler
        } else if (target == null) {
            // Don't remove the cards, no target
        } else {
            // Remove dragged cards
            for (List<List<CardView>> gridRow : cardGrid) {
                for (List<CardView> stack : gridRow) {
                    for (int i = 0; i < stack.size(); ++i) {
                        CardView card = stack.get(i);
                        if (card.isSelected()) {
                            stack.set(i, null);
                            removeCardView(card);
                            eventSource.fireEvent(card, ClientEventType.REMOVE_SPECIFIC_CARD);
                        }
                    }
                }
            }
            trimGrid();
            layoutGrid();
            cardScroll.revalidate();
            cardScroll.repaint();
        }
    }

    @Override
    public void dragCardEnter(MouseEvent e) {
        insertArrow.setVisible(true);
    }

    @Override
    public void dragCardMove(MouseEvent e) {
        e = SwingUtilities.convertMouseEvent(this, e, cardContent);
        showDropPosition(e.getX(), e.getY());
    }

    private void showDropPosition(int x, int y) {
        // Clamp to region
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }

        // Determine column
        int cardWidth = getCardWidth();
        int cardHeight = getCardHeight();
        int cardTopHeight = CardRenderer.getCardTopHeight(cardWidth);
        int dx = x % (cardWidth + GRID_PADDING);
        int col = x / (cardWidth + GRID_PADDING);
        int gridWidth = cardGrid.isEmpty() ? 0 : cardGrid.get(0).size();

        if (dx < GRID_PADDING && col < gridWidth) {
            // Which row to add to?
            int curY = COUNT_LABEL_HEIGHT;
            int rowIndex = 0;
            for (int i = 0; i < cardGrid.size(); ++i) {
                int maxStack = maxStackSize.get(i);
                int rowHeight = cardTopHeight * (maxStack - 1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    break;
                } else {
                    rowIndex = i + 1;
                    curY = rowBottom;
                }
            }

            // Insert between two columns
            insertArrow.setIcon(INSERT_COL_ICON);
            insertArrow.setSize(64, 64);
            insertArrow.setLocation((cardWidth + GRID_PADDING) * col + GRID_PADDING / 2 - 32, curY);
        } else {
            // Clamp to a new col one after the current last one
            col = Math.min(col, gridWidth);

            // Determine place in the col
            int curY = COUNT_LABEL_HEIGHT;
            int rowIndex = 0;
            int offsetIntoStack = 0;
            for (int i = 0; i < cardGrid.size(); ++i) {
                int maxStack = maxStackSize.get(i);
                int rowHeight = cardTopHeight * (maxStack - 1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    offsetIntoStack = y - curY;
                    break;
                } else {
                    rowIndex = i + 1;
                    offsetIntoStack = y - rowBottom;
                    curY = rowBottom;
                }
            }

            // Get the appropirate stack
            List<CardView> stack;
            if (rowIndex < cardGrid.size() && col < cardGrid.get(0).size()) {
                stack = cardGrid.get(rowIndex).get(col);
            } else {
                stack = new ArrayList<>();
            }

            // Figure out position in the stack based on the offsetIntoRow
            int stackInsertIndex = (offsetIntoStack + cardTopHeight / 2) / cardTopHeight;
            stackInsertIndex = Math.max(0, Math.min(stackInsertIndex, stack.size()));

            // Position arrow
            insertArrow.setIcon(INSERT_ROW_ICON);
            insertArrow.setSize(64, 32);
            insertArrow.setLocation((cardWidth + GRID_PADDING) * col + GRID_PADDING + cardWidth / 2 - 32, curY + stackInsertIndex * cardTopHeight - 32);
        }
    }

    @Override
    public void dragCardExit(MouseEvent e) {
        insertArrow.setVisible(false);
    }

    @Override
    public void dragCardDrop(MouseEvent e, DragCardSource source, Collection<CardView> cards) {
        e = SwingUtilities.convertMouseEvent(this, e, cardContent);
        int x = e.getX();
        int y = e.getY();

        // Clamp to region
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }

        // If we're dragging onto ourself, erase the old cards (just null them out, we will
        // compact the grid removing the null gaps / empty rows & cols later)
        if (source == this) {
            for (List<List<CardView>> gridRow : cardGrid) {
                for (List<CardView> stack : gridRow) {
                    for (int i = 0; i < stack.size(); ++i) {
                        if (cards.contains(stack.get(i))) {
                            stack.set(i, null);
                        }
                    }
                }
            }
        }

        // Determine column
        int cardWidth = getCardWidth();
        int cardHeight = getCardHeight();
        int cardTopHeight = CardRenderer.getCardTopHeight(cardWidth);
        int dx = x % (cardWidth + GRID_PADDING);
        int col = x / (cardWidth + GRID_PADDING);
        int gridWidth = cardGrid.isEmpty() ? 0 : cardGrid.get(0).size();

        if (dx < GRID_PADDING && col < gridWidth) {
            // Which row to add to?
            int curY = COUNT_LABEL_HEIGHT;
            int rowIndex = 0;
            for (int i = 0; i < cardGrid.size(); ++i) {
                int maxStack = maxStackSize.get(i);
                int rowHeight = cardTopHeight * (maxStack - 1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    break;
                } else {
                    rowIndex = i + 1;
                    curY = rowBottom;
                }
            }

            // Add a new row if needed
            if (rowIndex >= cardGrid.size()) {
                List<List<CardView>> newRow = new ArrayList<>();
                if (!cardGrid.isEmpty()) {
                    for (int colIndex = 0; colIndex < cardGrid.get(0).size(); ++colIndex) {
                        newRow.add(new ArrayList<>());
                    }
                }
                cardGrid.add(newRow);
                maxStackSize.add(0);
            }

            // Insert the new column to add to
            for (int i = 0; i < cardGrid.size(); ++i) {
                cardGrid.get(i).add(col, new ArrayList<>());
            }

            // Add the cards
            cardGrid.get(rowIndex).get(col).addAll(cards);
        } else {
            // Clamp to a new col one after the current last one
            col = Math.min(col, gridWidth);

            // Determine place in the col
            int curY = COUNT_LABEL_HEIGHT;
            int rowIndex = 0;
            int offsetIntoStack = 0;
            for (int i = 0; i < cardGrid.size(); ++i) {
                int maxStack = maxStackSize.get(i);
                int rowHeight = cardTopHeight * (maxStack - 1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    offsetIntoStack = y - curY;
                    break;
                } else {
                    rowIndex = i + 1;
                    offsetIntoStack = y - rowBottom;
                    curY = rowBottom;
                }
            }

            // Add a new row if needed
            if (rowIndex >= cardGrid.size()) {
                List<List<CardView>> newRow = new ArrayList<>();
                if (!cardGrid.isEmpty()) {
                    for (int colIndex = 0; colIndex < cardGrid.get(0).size(); ++colIndex) {
                        newRow.add(new ArrayList<>());
                    }
                }
                cardGrid.add(newRow);
                maxStackSize.add(0);
            }

            // Add a new col if needed
            if (col >= cardGrid.get(0).size()) {
                for (int i = 0; i < cardGrid.size(); ++i) {
                    cardGrid.get(i).add(new ArrayList<>());
                }
            }

            // Get the appropirate stack
            List<CardView> stack = cardGrid.get(rowIndex).get(col);

            // Figure out position in the stack based on the offsetIntoRow
            int stackInsertIndex = (offsetIntoStack + cardTopHeight / 2) / cardTopHeight;
            stackInsertIndex = Math.max(0, Math.min(stackInsertIndex, stack.size()));

            // Insert the cards
            stack.addAll(stackInsertIndex, cards);
        }

        if (source == this) {
            // Remove empty rows / cols / spaces in stacks
            trimGrid();
            layoutGrid();
            cardScroll.revalidate();
            cardScroll.repaint();
        } else {
            // Add new cards to grid
            for (CardView card : cards) {
                card.setSelected(true);
                addCardView(card, false);
                eventSource.fireEvent(card, ClientEventType.ADD_SPECIFIC_CARD);
            }
            layoutGrid();
            cardContent.repaint();
        }
    }

    public void changeGUISize() {
        layoutGrid();
        cardScroll.getVerticalScrollBar().setUnitIncrement(CardRenderer.getCardTopHeight(getCardWidth()));
        cardContent.repaint();
    }

    public void cleanUp() {
        // Remove all of the cards from us
        for (MageCard cardView : cardViews.values()) {
            cardContent.remove(cardView);
        }

        // Clear out our tracking of stuff
        cardGrid.clear();
        maxStackSize.clear();
        allCards.clear();
        lastBigCard = null;
        clearCardEventListeners();
    }

    public void addCardEventListener(Listener<mage.client.util.Event> listener) {
        eventSource.addListener(listener);
    }

    public void clearCardEventListeners() {
        eventSource.clearListeners();
    }

    public void setRole(Role role) {
        this.role = role;
        if (role == Role.SIDEBOARD) {
            creatureCountLabel.setVisible(false);
            landCountLabel.setVisible(false);
            cardSizeSliderLabel.setVisible(false);
        } else {
            creatureCountLabel.setVisible(true);
            landCountLabel.setVisible(true);
            cardSizeSliderLabel.setVisible(true);
        }
        updateCounts();
    }

    public void removeSelection() {
        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                for (int i = 0; i < stack.size(); ++i) {
                    CardView card = stack.get(i);
                    if (card.isSelected()) {
                        eventSource.fireEvent(card, ClientEventType.REMOVE_SPECIFIC_CARD);
                        stack.set(i, null);
                        removeCardView(card);
                    }
                }
            }
        }
        trimGrid();
        layoutGrid();
        cardContent.repaint();
    }

    public DeckCardLayout getCardLayout() {
        // 2D Array to put entries into
        List<List<List<DeckCardInfo>>> info = new ArrayList<>();
        for (List<List<CardView>> gridRow : cardGrid) {
            List<List<DeckCardInfo>> row = new ArrayList<>();
            info.add(row);
            for (List<CardView> stack : gridRow) {
                row.add(stack.stream()
                        .map(card -> new DeckCardInfo(card.getName(), card.getCardNumber(), card.getExpansionSetCode()))
                        .collect(Collectors.toList()));
            }
        }

        // Store layout and settings then return them
        return new DeckCardLayout(info, saveSettings().toString());
    }

    public void setDeckEditorMode(Constants.DeckEditorMode mode) {
        this.mode = mode;
    }

    public enum Sort {
        NONE("No Sort", (o1, o2) -> {
            // Always equal, sort into the first row
            return 0;
        }),
        CARD_TYPE("Card Type", new CardViewCardTypeComparator()),
        CMC("Converted Mana Cost", new CardViewCostComparator()),
        COLOR("Color", new CardViewColorComparator()),
        COLOR_IDENTITY("Color Identity", new CardViewColorIdentityComparator()),
        RARITY("Rarity", new CardViewRarityComparator()),
        EDH_POWER_LEVEL("EDH Power Level", new CardViewEDHPowerLevelComparator());

        Sort(String text, Comparator<CardView> comparator) {
            this.comparator = comparator;
            this.text = text;
        }

        public Comparator<CardView> getComparator() {
            return comparator;
        }

        public String getText() {
            return text;
        }

        private final Comparator<CardView> comparator;
        private final String text;
    }

    private abstract class CardTypeCounter {

        protected abstract boolean is(CardView card);

        int get() {
            return count;
        }

        void add(CardView card) {
            if (is(card)) {
                ++count;
            }
        }

        void remove(CardView card) {
            if (is(card)) {
                --count;
            }
        }

        private int count = 0;
    }

    // Counters we use
    private final CardTypeCounter creatureCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isCreature();
        }
    };
    private final CardTypeCounter landCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isLand();
        }
    };

    private final CardTypeCounter artifactCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isArtifact();
        }
    };
    private final CardTypeCounter enchantmentCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isEnchantment();
        }
    };
    private final CardTypeCounter instantCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isInstant();
        }
    };
    private final CardTypeCounter sorceryCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isSorcery();
        }
    };
    private final CardTypeCounter planeswalkerCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isPlanesWalker();
        }
    };
    private final CardTypeCounter tribalCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isTribal();
        }
    };

    private final CardTypeCounter[] allCounters = {
            creatureCounter,
            landCounter,
            artifactCounter,
            enchantmentCounter,
            instantCounter,
            planeswalkerCounter,
            sorceryCounter,
            tribalCounter
    };

    // Listener
    public interface DragCardGridListener {

        void cardsSelected();

        void hideCards(Collection<CardView> card);

        void duplicateCards(Collection<CardView> cards);

        void invertCardSelection(Collection<CardView> cards);

        void showAll();
    }

    // Constants
    public static final int COUNT_LABEL_HEIGHT = 20;
    public static final int GRID_PADDING = 10;

    private static final ImageIcon INSERT_ROW_ICON = new ImageIcon(DragCardGrid.class.getClassLoader().getResource("editor_insert_row.png"));
    private static final ImageIcon INSERT_COL_ICON = new ImageIcon(DragCardGrid.class.getClassLoader().getResource("editor_insert_col.png"));

    // All of the current card views
    private final Map<UUID, MageCard> cardViews = new LinkedHashMap<>();
    private final List<CardView> allCards = new ArrayList<>();

    // Card listeners
    private final CardEventSource eventSource = new CardEventSource();

    // Last big card
    BigCard lastBigCard = null;

    // Top bar with dropdowns for sort / filter / etc
    JButton sortButton;
    JButton filterButton;
    JButton visibilityButton;
    JButton selectByButton;
    JButton analyseButton;
    JButton blingButton;

    // Popup for toolbar
    final JPopupMenu filterPopup;
    JPopupMenu selectByTypePopup;

    final JPopupMenu sortPopup;
    final JPopupMenu selectByPopup;
    final JCheckBox separateCreaturesCb;
    final JTextField searchByTextField;
    JToggleButton multiplesButton;

    final JSlider cardSizeSlider;
    final JLabel cardSizeSliderLabel;

    final Map<Sort, AbstractButton> sortButtons = new EnumMap<>(Sort.class);
    final Map<CardType, AbstractButton> selectByTypeButtons = new EnumMap<>(CardType.class);

    final JLabel deckNameAndCountLabel;
    final JLabel landCountLabel;
    final JLabel creatureCountLabel;

    // Main two controls holding the scrollable card grid
    final JScrollPane cardScroll;
    JLayeredPane cardContent;

    // Drag onto insert arrow
    final JLabel insertArrow;

    // Card area selection panel
    final SelectionBox selectionPanel;
    Set<CardView> selectionDragStartCards;
    int selectionDragStartX;
    int selectionDragStartY;

    // Card size mod
    float cardSizeMod = 1.0f;

    // The role (maindeck or sideboard)
    Role role = Role.MAINDECK;

    // Dragging
    private final CardDraggerGlassPane dragger = new CardDraggerGlassPane(this);

    // The grid of cards
    // The outermost array contains multiple rows of stacks of cards
    // The next inner array represents a row of stacks of cards
    // The innermost array represents a single vertical stack of cards
    private List<List<List<CardView>>> cardGrid;
    private List<Integer> maxStackSize = new ArrayList<>();
    private final List<List<JLabel>> stackCountLabels = new ArrayList<>();
    private Sort cardSort = Sort.CMC;
    private final List<CardType> selectByTypeSelected = new ArrayList<>();
    private boolean separateCreatures = true;

    public enum Role {
        MAINDECK("Maindeck"),
        SIDEBOARD("Sideboard");

        Role(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private final String name;
    }

    public static class Settings {

        public Sort sort;
        public boolean separateCreatures;
        public int cardSize;

        private static final Pattern parser = Pattern.compile("\\(([^,]*),([^,]*),([^)]*)\\)");

        public static Settings parse(String str) {
            Matcher m = parser.matcher(str);
            if (m.find()) {
                Settings s = new Settings();
                if (m.groupCount() > 0) {
                    s.sort = Sort.valueOf(m.group(1));
                }
                if (m.groupCount() > 1) {
                    s.separateCreatures = Boolean.valueOf(m.group(2));
                }
                if (m.groupCount() > 2) {
                    s.cardSize = Integer.valueOf(m.group(3));
                } else {
                    s.cardSize = 50;
                }
                return s;
            } else {
                return null;
            }
        }

        @Override
        public String toString() {
            return '(' + sort.toString() + ',' + separateCreatures + ',' + cardSize + ')';
        }
    }

    public Settings saveSettings() {
        Settings s = new Settings();
        s.sort = cardSort;
        s.separateCreatures = separateCreatures;
        s.cardSize = cardSizeSlider.getValue();
        return s;
    }

    public void loadSettings(Settings s) {
        if (s != null) {
            setSort(s.sort);
            setSeparateCreatures(s.separateCreatures);
            setCardSize(s.cardSize);
            resort();
        }
    }

    public void setSeparateCreatures(boolean state) {
        separateCreatures = state;
        separateCreaturesCb.setSelected(state);
    }

    public void setSort(Sort s) {
        cardSort = s;
        sortButtons.get(s).setSelected(true);
    }

    public void setCardSize(int size) {
        cardSizeSlider.setValue(size);
    }

    // Constructor
    public DragCardGrid() {
        // Make sure that the card grid is populated with at least one (empty) stack to begin with
        cardGrid = new ArrayList<>();

        // Component init
        setLayout(new BorderLayout());
        setOpaque(false);

        // Editting mode
        this.mode = Constants.DeckEditorMode.LIMITED_BUILDING;

        // Toolbar
        sortButton = new JButton("Sort");
        filterButton = new JButton("Filter");
        visibilityButton = new JButton("V"); // "Visibility" button
        selectByButton = new JButton("Select By");
        analyseButton = new JButton("M"); // "Mana" button
        blingButton = new JButton("B"); // "Bling" button

        // Name and count label
        deckNameAndCountLabel = new JLabel();

        // Count labels
        landCountLabel = new JLabel("", new ImageIcon(getClass().getResource("/buttons/type_land.png")), SwingConstants.LEFT);
        landCountLabel.setToolTipText("Number of lands in deck");
        creatureCountLabel = new JLabel("", new ImageIcon(getClass().getResource("/buttons/type_creatures.png")), SwingConstants.LEFT);
        creatureCountLabel.setToolTipText("Number of creatures in deck");

        JPanel toolbar = new JPanel(new BorderLayout());
        JPanel toolbarInner = new JPanel();
        toolbar.setBackground(new Color(250, 250, 250, 150));
        toolbar.setOpaque(true);
        toolbarInner.setOpaque(false);
        toolbarInner.add(deckNameAndCountLabel);
        toolbarInner.add(landCountLabel);
        toolbarInner.add(creatureCountLabel);
        toolbarInner.add(sortButton);
        toolbarInner.add(filterButton);
        toolbarInner.add(selectByButton);
        toolbarInner.add(visibilityButton);
        toolbarInner.add(analyseButton);
        toolbarInner.add(blingButton);
        toolbar.add(toolbarInner, BorderLayout.WEST);
        JPanel sliderPanel = new JPanel(new GridBagLayout());
        sliderPanel.setOpaque(false);
        cardSizeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 50);
        cardSizeSlider.setOpaque(false);
        cardSizeSlider.setPreferredSize(new Dimension(100, (int) cardSizeSlider.getPreferredSize().getHeight()));
        cardSizeSlider.addChangeListener(e -> {
            if (!cardSizeSlider.getValueIsAdjusting()) {
                // Fraction in [-1, 1]
                float sliderFrac = ((float) (cardSizeSlider.getValue() - 50)) / 50;
                // Convert to frac in [0.5, 2.0] exponentially
                cardSizeMod = (float) Math.pow(2, sliderFrac);
                // Update grid
                layoutGrid();
                cardContent.repaint();
            }
        });
        cardSizeSliderLabel = new JLabel("Card Size:");
        sliderPanel.add(cardSizeSliderLabel);
        sliderPanel.add(cardSizeSlider);
        toolbar.add(sliderPanel, BorderLayout.EAST);
        this.add(toolbar, BorderLayout.NORTH);

        // Content
        cardContent = new JLayeredPane();
        cardContent.setLayout(null);
        cardContent.setOpaque(false);
        cardContent.addMouseListener(new MouseAdapter() {
            private boolean isDragging = false;

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    isDragging = true;
                    beginSelectionDrag(e.getX(), e.getY(), e.isShiftDown());
                    updateSelectionDrag(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDragging) {
                    isDragging = false;
                    updateSelectionDrag(e.getX(), e.getY());
                    endSelectionDrag(e.getX(), e.getY());
                }
            }
        });
        cardContent.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateSelectionDrag(e.getX(), e.getY());
            }
        });
        cardScroll = new JScrollPane(cardContent,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardScroll.setOpaque(false);
        cardScroll.getViewport().setOpaque(false);
        cardScroll.setViewportBorder(BorderFactory.createEmptyBorder());
        cardScroll.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        cardScroll.getVerticalScrollBar().setUnitIncrement(CardRenderer.getCardTopHeight(getCardWidth()));
        this.add(cardScroll, BorderLayout.CENTER);

        // Insert arrow
        insertArrow = new JLabel();
        insertArrow.setSize(20, 20);
        insertArrow.setVisible(false);
        cardContent.add(insertArrow, 1000);

        // Selection panel
        selectionPanel = new SelectionBox();
        selectionPanel.setVisible(false);
        cardContent.add(selectionPanel, new Integer(1001));

        // Load separate creatures setting
        separateCreatures = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_DECK_EDITOR_LAST_SEPARATE_CREATURES, "false").equals("true");
        try {
            cardSort = Sort.valueOf(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_DECK_EDITOR_LAST_SORT, Sort.NONE.toString()));
        } catch (IllegalArgumentException ex) {
            cardSort = Sort.NONE;
        }
        // Sort popup
        {
            sortPopup = new JPopupMenu();
            sortPopup.setLayout(new GridBagLayout());

            JPanel sortMode = new JPanel();
            sortMode.setLayout(new GridLayout(Sort.values().length, 1, 0, 2));
            sortMode.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sort by..."));
            GridBagConstraints sortModeC = new GridBagConstraints();
            sortModeC.gridx = 0;
            sortModeC.gridy = 0;
            sortModeC.gridwidth = 1;
            sortModeC.gridheight = 1;
            sortModeC.fill = GridBagConstraints.HORIZONTAL;
            sortPopup.add(sortMode, sortModeC);

            ButtonGroup sortModeGroup = new ButtonGroup();
            for (final Sort s : Sort.values()) {
                JToggleButton button = new JToggleButton(s.getText());
                if (s == cardSort) {
                    button.setSelected(true);
                }
                sortButtons.put(s, button);
                sortMode.add(button);
                sortModeGroup.add(button);
                button.addActionListener(e -> {
                    cardSort = s;
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_DECK_EDITOR_LAST_SORT, s.toString());
                    resort();
                });
            }

            JPanel sortOptions = new JPanel();
            sortOptions.setLayout(new BoxLayout(sortOptions, BoxLayout.Y_AXIS));
            sortOptions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sort options"));
            GridBagConstraints sortOptionsC = new GridBagConstraints();
            sortOptionsC.gridx = 0;
            sortOptionsC.gridy = 1;
            sortOptionsC.gridwidth = 1;
            sortOptionsC.gridheight = 1;
            sortPopup.add(sortOptions, sortOptionsC);

            separateCreaturesCb = new JCheckBox();
            separateCreaturesCb.setText("Creatures in separate row");
            separateCreaturesCb.setSelected(separateCreatures);
            separateCreaturesCb.addItemListener(e -> {
                setSeparateCreatures(separateCreaturesCb.isSelected());
                PreferencesDialog.saveValue(PreferencesDialog.KEY_DECK_EDITOR_LAST_SEPARATE_CREATURES, Boolean.toString(separateCreatures));
                resort();
            });
            sortOptions.add(separateCreaturesCb);
            sortPopup.pack();

            makeButtonPopup(sortButton, sortPopup);
        }

        // Visibility popup
        {
            final JPopupMenu visPopup = new JPopupMenu();
            JMenuItem hideSelected = new JMenuItem("Hide selected");
            hideSelected.addActionListener(e -> hideSelection());
            visPopup.add(hideSelected);
            JMenuItem showAll = new JMenuItem("Show all");
            showAll.addActionListener(e -> showAll());
            visPopup.add(showAll);
            visibilityButton.setToolTipText("Visibility of cards.  Right click to get the same options this provides");
            visibilityButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    visPopup.show(e.getComponent(), 0, e.getComponent().getHeight());
                }
            });
        }

        // selectBy.. popup
        {
            selectByPopup = new JPopupMenu();
            selectByPopup.setLayout(new GridBagLayout());

            JPanel selectByTypeMode = new JPanel();
            selectByTypeMode.setLayout(new GridLayout(CardType.values().length, 1, 0, 2));
            selectByTypeMode.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select by Type"));
            GridBagConstraints selectByTypeModeC = new GridBagConstraints();
            selectByTypeModeC.gridx = 0;
            selectByTypeModeC.gridy = 0;
            selectByTypeModeC.gridwidth = 1;
            selectByTypeModeC.gridheight = 1;
            selectByTypeModeC.fill = GridBagConstraints.HORIZONTAL;
            selectByPopup.add(selectByTypeMode, selectByTypeModeC);

            ButtonGroup selectByTypeModeGroup = new ButtonGroup();
            for (final CardType cardType : CardType.values()) {

                if (cardType == CardType.CONSPIRACY) {
                    multiplesButton = new JToggleButton("Multiples");
                    selectByTypeButtons.put(cardType, multiplesButton);
                    selectByTypeMode.add(multiplesButton);
                    selectByTypeModeGroup.add(multiplesButton);
                    multiplesButton.addActionListener(e -> {
                        multiplesButton.setSelected(!multiplesButton.isSelected());
                        reselectBy();
                    });
                    continue;
                }

                JToggleButton button = new JToggleButton(cardType.toString());
                selectByTypeButtons.put(cardType, button);
                selectByTypeMode.add(button);
                selectByTypeModeGroup.add(button);
                button.addActionListener(e -> {
                    //selectByTypeSelected.add(cardType);
                    button.setSelected(!button.isSelected());
                    reselectBy();
                });
            }

            JPanel selectBySearchPanel = new JPanel();
            selectBySearchPanel.setPreferredSize(new Dimension(150, 60));
            selectBySearchPanel.setLayout(new GridLayout(1, 1));
            selectBySearchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search:"));
            GridBagConstraints selectBySearchPanelC = new GridBagConstraints();
            selectBySearchPanelC.gridx = 0;
            selectBySearchPanelC.gridy = 1;
            selectBySearchPanelC.gridwidth = 1;
            selectBySearchPanelC.gridheight = 1;
            selectBySearchPanelC.fill = GridBagConstraints.HORIZONTAL;
            selectBySearchPanelC.fill = GridBagConstraints.VERTICAL;

            searchByTextField = new JTextField();
            searchByTextField.setToolTipText("Searches for card names, types, rarity, casting cost and rules text.  NB: Mana symbols are written like {W},{U},{C} etc");
            searchByTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    reselectBy();
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }
            });

            selectBySearchPanel.add(searchByTextField);
            selectByPopup.add(selectBySearchPanel, selectBySearchPanelC);
            makeButtonPopup(selectByButton, selectByPopup);
        }

        // Analyse Mana (aka #blue pips, #islands, #white pips, #plains etc.)
        analyseButton.setToolTipText("Mana Analyser! Counts coloured/colourless mana costs. Counts land types.");

        analyseButton.addActionListener(evt -> analyseDeck());

        // Bling button - aka Add in a premium 'JR', 'MBP', 'CS' etc card
        blingButton.setToolTipText("Bling your deck! Select the original and added cards by selecting 'Multiples' in the selection options");

        blingButton.addActionListener(evt -> blingDeck());

        // Filter popup
        filterPopup = new JPopupMenu();
        filterPopup.setPreferredSize(new Dimension(300, 300));
        makeButtonPopup(filterButton, filterPopup);
        filterButton.setVisible(false);

        // Right click in card area
        initCardAreaPopup();

        // Update counts
        updateCounts();
    }

    public void initCardAreaPopup() {
        final JPopupMenu menu = new JPopupMenu();

        final JMenuItem hideSelected = new JMenuItem("Hide selected");
        hideSelected.addActionListener(e -> hideSelection());
        menu.add(hideSelected);

        JMenuItem showAll = new JMenuItem("Show all");
        showAll.addActionListener(e -> showAll());
        menu.add(showAll);

        JMenu sortMenu = new JMenu("Sort by...");
        final Map<Sort, JMenuItem> sortMenuItems = new LinkedHashMap<>();
        for (final Sort sort : Sort.values()) {
            JMenuItem subSort = new JCheckBoxMenuItem(sort.getText());
            sortMenuItems.put(sort, subSort);
            subSort.addActionListener(e -> {
                cardSort = sort;
                resort();
            });
            sortMenu.add(subSort);
        }
        sortMenu.add(new JPopupMenu.Separator());
        final JCheckBoxMenuItem separateButton = new JCheckBoxMenuItem("Separate creatures");
        separateButton.addActionListener(e -> {
            setSeparateCreatures(!separateCreatures);
            resort();
        });
        sortMenu.add(separateButton);
        menu.add(sortMenu);

        // Hook up to card content
        cardContent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    for (Sort s : sortMenuItems.keySet()) {
                        sortMenuItems.get(s).setSelected(cardSort == s);
                    }
                    hideSelected.setEnabled(!dragCardList().isEmpty());
                    separateButton.setSelected(separateCreatures);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Deselect all cards in this DragCardGrid
     */
    public void deselectAll() {
        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                for (CardView card : stack) {
                    if (card.isSelected()) {
                        card.setSelected(false);
                        cardViews.get(card.getId()).update(card);
                    }
                }
            }
        }
    }

    private void hideSelection() {
        Collection<CardView> toHide = dragCardList();
        for (DragCardGridListener l : listeners) {
            l.hideCards(toHide);
        }
    }

    private void duplicateSelection() {
        Collection<CardView> toDuplicate = dragCardList();
        for (DragCardGridListener l : listeners) {
            l.duplicateCards(toDuplicate);
        }
    }

    private void invertSelection() {
        Collection<CardView> toInvert = allCards;
        for (DragCardGridListener l : listeners) {
            l.invertCardSelection(toInvert);
            for (CardView card : allCards) {
                MageCard view = cardViews.get(card.getId());
                view.update(card);
            }
        }
        repaint();
    }

    private void chooseMatching() {
        Collection<CardView> toMatch = dragCardList();

        for (DragCardGridListener l : listeners) {
            for (CardView card : allCards) {
                for (CardView aMatch : toMatch) {
                    if (card.getName().equals(aMatch.getName())) {
                        card.setSelected(true);
                        cardViews.get(card.getId()).update(card);
                    }
                }
            }
        }
        repaint();
    }

    private void showAll() {
        for (DragCardGridListener l : listeners) {
            l.showAll();
        }
    }

    /**
     * Selection drag handling
     */
    private void beginSelectionDrag(int x, int y, boolean shiftHeld) {
        // Show the selection panel
        selectionPanel.setVisible(true);
        selectionPanel.setLocation(x, y);
        cardScroll.revalidate();

        // Store the drag start location
        selectionDragStartX = x;
        selectionDragStartY = y;

        // Store the starting cards to include in the selection
        selectionDragStartCards = new HashSet<>();
        if (shiftHeld) {
            selectionDragStartCards.addAll(dragCardList());
        }

        // Notify selection
        notifyCardsSelected();
    }

    private void updateSelectionDrag(int x, int y) {
        // Coords
        int cardWidth = getCardWidth();
        int cardHeight = getCardHeight();
        int cardTopHeight = CardRenderer.getCardTopHeight(cardWidth);
        int x1 = Math.min(x, selectionDragStartX);
        int x2 = Math.max(x, selectionDragStartX);
        int y1 = Math.min(y, selectionDragStartY);
        int y2 = Math.max(y, selectionDragStartY);

        // Update selection panel size
        selectionPanel.setLocation(x1, y1);
        selectionPanel.setSize(x2 - x1, y2 - y1);

        // First and last cols
        int col1 = x1 / (cardWidth + GRID_PADDING);
        int col2 = x2 / (cardWidth + GRID_PADDING);
        int offsetIntoCol2 = x2 % (cardWidth + GRID_PADDING);
        if (offsetIntoCol2 < GRID_PADDING) {
            --col2;
        }

        // avoids a null ref issue but only deals with symptom of problem. not sure how it gets to this state ever. see issue #3197
        // if (selectionDragStartCards == null) return;
        int curY = COUNT_LABEL_HEIGHT;
        for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
            int stackStartIndex;
            if (y1 < curY) {
                stackStartIndex = 0;
            } else {
                stackStartIndex = (y1 - curY) / cardTopHeight;
            }
            int stackEndIndex;
            if (y2 < curY) {
                stackEndIndex = -1;
            } else {
                stackEndIndex = (y2 - curY) / cardTopHeight;
            }
            List<List<CardView>> gridRow = cardGrid.get(rowIndex);
            for (int col = 0; col < gridRow.size(); ++col) {
                List<CardView> stack = gridRow.get(col);
                int stackBottomBegin = curY + cardTopHeight * (stack.size());
                int stackBottomEnd = curY + cardTopHeight * (stack.size() - 1) + cardHeight;
                for (int i = 0; i < stack.size(); ++i) {
                    CardView card = stack.get(i);
                    MageCard view = cardViews.get(card.getId());
                    boolean inBoundsX = (col >= col1 && col <= col2);
                    boolean inBoundsY = (i >= stackStartIndex && i <= stackEndIndex);
                    boolean lastCard = (i == stack.size() - 1);
                    boolean inSeletionDrag = inBoundsX && (inBoundsY || (lastCard && (y2 >= stackBottomBegin && y1 <= stackBottomEnd)));
                    if (inSeletionDrag || selectionDragStartCards != null && selectionDragStartCards.contains(card)) {
                        if (!card.isSelected()) {
                            card.setSelected(true);
                            view.update(card);
                        }
                    } else if (card.isSelected()) {
                        card.setSelected(false);
                        view.update(card);
                    }
                }
            }
            curY += cardTopHeight * (maxStackSize.get(rowIndex) - 1) + cardHeight + COUNT_LABEL_HEIGHT;
        }
    }

    private void endSelectionDrag(@SuppressWarnings("unused") int x, @SuppressWarnings("unused") int y) {
        // Hide the selection panel
        selectionPanel.setVisible(false);
    }

    // Resort the existing cards based on the current sort
    public void resort() {
        // First null out the grid and trim it down
        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                stack.clear();
            }
        }
        trimGrid();

        // First sort all cards by name
        allCards.sort(new CardViewNameComparator());

        // Now re-insert all of the cards using the current sort
        for (CardView card : allCards) {
            sortIntoGrid(card);
        }

        // Deselect everything
        deselectAll();

        // And finally rerender
        layoutGrid();
        repaint();
    }

    public void reselectBy() {
        // Deselect everything
        deselectAll();

        boolean useText = false;
        String searchStr = "";
        if (searchByTextField.getText().length() >= 3) {
            useText = true;
            searchStr = searchByTextField.getText().toLowerCase(Locale.ENGLISH);
        }

        for (CardType cardType : selectByTypeButtons.keySet()) {
            AbstractButton button = selectByTypeButtons.get(cardType);
            if (button != null) {
                if (button.isSelected()) {
                    // Special case - "Multiples"  (CONSPIRACY type)
                    if (cardType == CardType.CONSPIRACY) {
                        HashMap<String, CardView> cardNames = new HashMap<>();

                        for (List<List<CardView>> gridRow : cardGrid) {
                            for (List<CardView> stack : gridRow) {
                                for (CardView card : stack) {
                                    if (cardNames.get(card.getName()) == null) {
                                        cardNames.put(card.getName(), card);
                                    } else {
                                        card.setSelected(true);
                                        cardViews.get(card.getId()).update(card);

                                        CardView origCard = cardNames.get(card.getName());
                                        origCard.setSelected(true);
                                        cardViews.get(origCard.getId()).update(origCard);
                                    }
                                }
                            }
                        }
                        continue;
                    }

                    for (List<List<CardView>> gridRow : cardGrid) {
                        for (List<CardView> stack : gridRow) {
                            for (CardView card : stack) {
                                boolean s = card.isSelected() || card.getCardTypes().contains(cardType);
                                card.setSelected(s);
                                cardViews.get(card.getId()).update(card);
                            }
                        }
                    }
                }
            }
        }

        if (useText) {
            for (List<List<CardView>> gridRow : cardGrid) {
                for (List<CardView> stack : gridRow) {
                    for (CardView card : stack) {
                        boolean s = card.isSelected();
                        // Name
                        if (!s) {
                            s = card.getName().toLowerCase(Locale.ENGLISH).contains(searchStr);
                        }
                        // Sub & Super Types
                        if (!s) {
                            for (SuperType str : card.getSuperTypes()) {
                                s |= str.toString().toLowerCase(Locale.ENGLISH).contains(searchStr);
                            }
                            for (SubType str : card.getSubTypes()) {
                                s |= str.toString().toLowerCase(Locale.ENGLISH).contains(searchStr);
                            }
                        }
                        // Rarity
                        if (!s) {
                            s |= card.getRarity().toString().toLowerCase(Locale.ENGLISH).contains(searchStr);
                        }
                        // Type line
                        if (!s) {
                            String t = "";
                            for (CardType type : card.getCardTypes()) {
                                t += ' ' + type.toString();
                            }
                            s |= t.toLowerCase(Locale.ENGLISH).contains(searchStr);
                        }
                        // Casting cost
                        if (!s) {
                            String mc = "";
                            for (String m : card.getManaCost()) {
                                mc += m;
                            }
                            s |= mc.toLowerCase(Locale.ENGLISH).contains(searchStr);
                        }
                        // Rules
                        if (!s) {
                            for (String str : card.getRules()) {
                                s |= str.toLowerCase(Locale.ENGLISH).contains(searchStr);
                            }
                        }
                        card.setSelected(s);
                        cardViews.get(card.getId()).update(card);
                    }
                }
            }
        }

        // And finally rerender
        layoutGrid();
        repaint();
    }

    private static final Pattern pattern = Pattern.compile(".*Add(.*)(\\{[WUBRGXC]\\})");

    public void analyseDeck() {
        HashMap<String, Integer> qtys = new HashMap<>();
        HashMap<String, Integer> pips = new HashMap<>();
        HashMap<String, Integer> pips_at_cmcs = new HashMap<>();
        HashMap<String, Integer> sourcePips = new HashMap<>();
        HashMap<String, Integer> manaCounts = new HashMap<>();
        pips.put("#w}", 0);
        pips.put("#u}", 0);
        pips.put("#b}", 0);
        pips.put("#r}", 0);
        pips.put("#g}", 0);
        pips.put("#c}", 0);
        qtys.put("plains", 0);
        qtys.put("island", 0);
        qtys.put("swamp", 0);
        qtys.put("mountain", 0);
        qtys.put("forest", 0);
        qtys.put("basic", 0);
        qtys.put("wastes", 0);
        manaCounts = new HashMap<>();

        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                for (CardView card : stack) {
                    // Type line
                    String t = card.getCardTypes().stream().map(CardType::toString).collect(Collectors.joining(" "));
                    t += card.getSuperTypes().stream().map(st -> st.toString().toLowerCase(Locale.ENGLISH)).collect(Collectors.joining(" "));
                    t += card.getSubTypes().stream().map(st -> st.toString().toLowerCase(Locale.ENGLISH)).collect(Collectors.joining(" "));

                    for (String qty : qtys.keySet()) {
                        int value = qtys.get(qty);
                        if (t.toLowerCase(Locale.ENGLISH).contains(qty)) {
                            qtys.put(qty, ++value);
                        }

                        // Rules
                        for (String str : card.getRules()) {
                            if (str.toLowerCase(Locale.ENGLISH).contains(qty)) {
                                qtys.put(qty, ++value);
                            }
                        }
                    }
                    // Wastes (special case)
                    if (card.getName().equals("Wastes")) {
                        int value = qtys.get("wastes");
                        qtys.put("wastes", ++value);
                    }

                    // Mana Cost
                    String mc = "";
                    for (String m : card.getManaCost()) {
                        mc += m;
                    }
                    mc = mc.replaceAll("\\{([WUBRG]).([WUBRG])\\}", "{$1}{$2}");
                    mc = mc.replaceAll("\\{", "#");
                    mc = mc.replaceAll("#2\\/", "#");
                    mc = mc.replaceAll("p}", "}");
                    mc = mc.toLowerCase(Locale.ENGLISH);
                    int cmc = card.getConvertedManaCost();

                    // Do colorless mana pips
                    Pattern regex = Pattern.compile("#([0-9]+)}");
                    Matcher regexMatcher = regex.matcher(mc);
                    while (regexMatcher.find()) {
                        String val = regexMatcher.group(1);
                        int colorless_val = Integer.parseInt(val);

                        int total_c_pip = 0;
                        if (pips.get("#c}") != null) {
                            total_c_pip = pips.get("#c}");
                        }
                        pips.put("#c}", colorless_val + total_c_pip);

                        int cmc_pip_value = 0;
                        if (pips_at_cmcs.get(cmc + "##c}") != null) {
                            cmc_pip_value = pips_at_cmcs.get(cmc + "##c}");
                        }
                        pips_at_cmcs.put(cmc + "##c}", colorless_val + cmc_pip_value);
                    }

                    for (String pip : pips.keySet()) {
                        int value = pips.get(pip);
                        while (mc.toLowerCase(Locale.ENGLISH).contains(pip)) {
                            pips.put(pip, ++value);
                            int pip_value = 0;
                            if (pips_at_cmcs.get(cmc + "#" + pip) != null) {
                                pip_value = pips_at_cmcs.get(cmc + "#" + pip);
                            }
                            pips_at_cmcs.put(cmc + "#" + pip, ++pip_value);
                            mc = mc.replaceFirst(pip, "");
                        }
                    }

                    // Adding mana
                    for (String str : card.getRules()) {
                        Matcher m = pattern.matcher(str);
                        // ".*Add(.*)(\\{[WUBRGXC]\\})(.*)"
                        while (m.find()) {
                            str = "Add" + m.group(1);
                            int num = 1;
                            if (manaCounts.get(m.group(2)) != null) {
                                num = manaCounts.get(m.group(2));
                                num++;
                            }
                            manaCounts.put(m.group(2), num);
                            m = pattern.matcher(str);
                        }
                    }
                }
            }
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        ManaPieChart chart = new ManaPieChart(pips.get("#w}"), pips.get("#u}"), pips.get("#b}"), pips.get("#r}"), pips.get("#g}"), pips.get("#c}"));
        chart.setMinimumSize(new Dimension(200, 200));
        panel2.add(new JLabel("Casting Costs found:"));
        panel2.add(chart);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        ManaPieChart chart2 = new ManaPieChart(qtys.get("plains"), qtys.get("island"), qtys.get("swamp"), qtys.get("mountain"), qtys.get("forest"), qtys.get("wastes"));
        chart2.setMinimumSize(new Dimension(200, 200));
        panel3.add(new JLabel("Basic Land types found:"));
        panel3.add(chart2);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        ManaPieChart chart3 = new ManaPieChart(manaCounts.get("{W}"), manaCounts.get("{U}"), manaCounts.get("{B}"), manaCounts.get("{R}"), manaCounts.get("{G}"), manaCounts.get("{C}"));
        chart3.setMinimumSize(new Dimension(200, 200));
        panel4.add(new JLabel("Mana sources found:"));
        panel4.add(chart3);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        ManaBarChart chart4 = new ManaBarChart(pips_at_cmcs);
        chart4.setMinimumSize(new Dimension(200, 200));
        panel5.add(new JLabel("Mana distribution:"));
        panel5.add(chart4);

        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);

        JFrame frame = new JFrame("JOptionPane showMessageDialog component example");
        JOptionPane.showMessageDialog(frame, panel, "This is the distribution of colors found", JOptionPane.INFORMATION_MESSAGE);
    }

    public void blingDeck() {
        if (this.mode != Constants.DeckEditorMode.FREE_BUILDING) {
            return;
        }

        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to bling your deck?  This process will add cards!", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            return;
        }

        Map<String, Integer> pimpedSets = new HashMap<>();
        Map<CardView, Integer> pimpedCards = new HashMap<>();
        pimpedSets.put("CP", 1);
        pimpedSets.put("JR", 1);
        pimpedSets.put("MPS", 1);
        pimpedSets.put("CLASH", 1);
        pimpedSets.put("ARENA", 1);
        pimpedSets.put("UGIN", 1);
        pimpedSets.put("WMCQ", 1);
        pimpedSets.put("APAC", 1);
        pimpedSets.put("EURO", 1);
        pimpedSets.put("FNMP", 1);
        pimpedSets.put("MGDC", 1);
        pimpedSets.put("MPRP", 1);
        pimpedSets.put("EXP", 1);
        pimpedSets.put("GPX", 1);
        pimpedSets.put("GRC", 1);
        pimpedSets.put("MBP", 1);
        pimpedSets.put("MLP", 1);
        pimpedSets.put("PLS", 1);
        pimpedSets.put("PTC", 1);
        pimpedSets.put("SUS", 1);

        String[] sets = pimpedSets.keySet().toArray(new String[pimpedSets.keySet().size()]);
        Boolean didModify = false;

        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                for (CardView card : stack) {
                    if (card.getSuperTypes().contains(SuperType.BASIC)) {
                        continue;
                    }

                    if (!pimpedSets.containsKey(card.getExpansionSetCode())) {
                        final CardCriteria cardCriteria = new CardCriteria();
                        cardCriteria.setCodes(sets);
                        cardCriteria.name(card.getName());

                        java.util.List<CardInfo> cardPool = CardRepository.instance.findCards(cardCriteria);

                        if (!cardPool.isEmpty()) {
                            Card acard = cardPool.get(RandomUtil.nextInt(cardPool.size())).getMockCard();

                            if (acard.getName().equals(card.getName())) {
                                CardView pimpedCard = new CardView(acard);
                                addCardView(pimpedCard, false);
                                eventSource.fireEvent(pimpedCard, ClientEventType.ADD_SPECIFIC_CARD);
                                pimpedCards.put(pimpedCard, 1);
                                didModify = true;
                            }
                        }
                    }
                }
            }

            if (didModify) {
                for (CardView c : pimpedCards.keySet()) {
                    sortIntoGrid(c);
                }

                layoutGrid();
                cardScroll.revalidate();
                repaint();
                JOptionPane.showMessageDialog(null, "Added " + pimpedCards.size() + " cards.  You can select them and the originals by choosing 'Multiples'");
            }
        }
    }

    // Update the contents of the card grid
    public void setCards(CardsView cardsView, DeckCardLayout layout, BigCard bigCard) {
        if (bigCard != null) {
            lastBigCard = bigCard;
        }

        // Remove all of the cards not in the cardsView
        boolean didModify = false; // Until contested
        for (int i = 0; i < cardGrid.size(); ++i) {
            List<List<CardView>> gridRow = cardGrid.get(i);
            for (int j = 0; j < gridRow.size(); ++j) {
                List<CardView> stack = gridRow.get(j);
                for (int k = 0; k < stack.size(); ++k) {
                    CardView card = stack.get(k);
                    if (!cardsView.containsKey(card.getId())) {
                        // Remove it
                        removeCardView(card);
                        stack.remove(k--);

                        // Mark
                        didModify = true;
                    }
                }
            }
        }

        // Trim the grid
        if (didModify) {
            trimGrid();
        }

        if (layout == null) {
            // No layout -> add any new card views one at a time as par the current sort
            for (CardView newCard : cardsView.values()) {
                if (!cardViews.containsKey(newCard.getId())) {
                    // Is a new card
                    addCardView(newCard, false);

                    // Put it into the appropirate place in the grid given the current sort
                    sortIntoGrid(newCard);

                    // Mark
                    didModify = true;
                }
            }
        } else {
            // Layout given -> Build card grid using layout, and set sort / separate

            // Always modify when given a layout
            didModify = true;

            // Load in settings
            loadSettings(Settings.parse(layout.getSettings()));

            // Traverse the cards once and track them so we can pick ones to insert into the grid
            Map<String, Map<String, List<CardView>>> trackedCards = new HashMap<>();
            for (CardView newCard : cardsView.values()) {
                if (!cardViews.containsKey(newCard.getId())) {
                    // Add the new card
                    addCardView(newCard, false);

                    // Add the new card to tracking
                    Map<String, List<CardView>> forSetCode;
                    if (trackedCards.containsKey(newCard.getExpansionSetCode())) {
                        forSetCode = trackedCards.get(newCard.getExpansionSetCode());
                    } else {
                        forSetCode = new HashMap<>();
                        trackedCards.put(newCard.getExpansionSetCode(), forSetCode);
                    }
                    List<CardView> list;
                    if (forSetCode.containsKey(newCard.getCardNumber())) {
                        list = forSetCode.get(newCard.getCardNumber());
                    } else {
                        list = new ArrayList<>();
                        forSetCode.put(newCard.getCardNumber(), list);
                    }
                    list.add(newCard);
                }
            }

            // Now go through the layout and use it to build the cardGrid
            cardGrid = new ArrayList<>();
            maxStackSize = new ArrayList<>();
            for (List<List<DeckCardInfo>> row : layout.getCards()) {
                List<List<CardView>> gridRow = new ArrayList<>();
                int thisMaxStackSize = 0;
                cardGrid.add(gridRow);
                for (List<DeckCardInfo> stack : row) {
                    ArrayList<CardView> gridStack = new ArrayList<>();
                    gridRow.add(gridStack);
                    for (DeckCardInfo info : stack) {
                        if (trackedCards.containsKey(info.getSetCode()) && trackedCards.get(info.getSetCode()).containsKey(info.getCardNum())) {
                            List<CardView> candidates
                                    = trackedCards.get(info.getSetCode()).get(info.getCardNum());
                            if (!candidates.isEmpty()) {
                                gridStack.add(candidates.remove(0));
                                thisMaxStackSize = Math.max(thisMaxStackSize, gridStack.size());
                            }
                        }
                    }
                }
                maxStackSize.add(thisMaxStackSize);
            }

            // Check that there aren't any "orphans" not referenced in the layout. There should
            // never be any under normal operation, but as a failsafe in case the user screwed with
            // the file in an invalid way, sort them into the grid so that they aren't just left hanging.
            for (Map<String, List<CardView>> tracked : trackedCards.values()) {
                for (List<CardView> orphans : tracked.values()) {
                    for (CardView orphan : orphans) {
                        LOGGER.info("Orphan when setting with layout: ");
                        sortIntoGrid(orphan);
                    }
                }
            }
        }

        // Modifications?
        if (didModify) {
            // Update layout
            layoutGrid();

            // Update draw
            cardScroll.revalidate();
            repaint();
        }
    }

    private int getCount(CardType cardType) {
        if (null != cardType) {
            switch (cardType) {
                case CREATURE:
                    return creatureCounter.get();
                case LAND:
                    return landCounter.get();
                case ARTIFACT:
                    return artifactCounter.get();
                case ENCHANTMENT:
                    return enchantmentCounter.get();
                case INSTANT:
                    return instantCounter.get();
                case PLANESWALKER:
                    return planeswalkerCounter.get();
                case SORCERY:
                    return sorceryCounter.get();
                case TRIBAL:
                    return tribalCounter.get();
                default:
                    break;
            }
        }
        return 0;
    }

    private void updateCounts() {
        deckNameAndCountLabel.setText(role.getName() + " - " + allCards.size());
        creatureCountLabel.setText(String.valueOf(creatureCounter.get()));
        landCountLabel.setText(String.valueOf(landCounter.get()));
        for (CardType cardType : selectByTypeButtons.keySet()) {
            AbstractButton button = selectByTypeButtons.get(cardType);
            String text = cardType.toString();
            int numCards = getCount(cardType);
            if (cardType == CardType.CONSPIRACY) {
                continue;
            }

            if (numCards > 0) {
                button.setForeground(Color.BLACK);
                text = text + " - " + numCards;
            } else {
                button.setForeground(new Color(100, 100, 100));
            }
            button.setText(text);
        }
    }

    private void showCardRightClickMenu(@SuppressWarnings("unused") final CardView card, MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem hide = new JMenuItem("Hide");
        hide.addActionListener(e2 -> hideSelection());
        menu.add(hide);

        JMenuItem invertSelection = new JMenuItem("Invert Selection");
        invertSelection.addActionListener(e2 -> invertSelection());
        menu.add(invertSelection);

        JMenuItem chooseMatching = new JMenuItem("Choose Matching");
        chooseMatching.addActionListener(e2 -> chooseMatching());
        menu.add(chooseMatching);

        // Show 'Duplicate Selection' for FREE_BUILDING
        if (this.mode == Constants.DeckEditorMode.FREE_BUILDING) {
            JMenuItem duplicateSelection = new JMenuItem("Duplicate Selection");
            duplicateSelection.addActionListener(e2 -> duplicateSelection());
            menu.add(duplicateSelection);
        }
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    public void addCardView(final CardView card, boolean duplicated) {
        allCards.add(card);

        // Update counts
        for (CardTypeCounter counter : allCounters) {
            counter.add(card);
        }
        updateCounts();

        // Create the card view
        final MageCard cardPanel = Plugins.instance.getMageCard(card, lastBigCard, new Dimension(getCardWidth(), getCardHeight()), null, true, true, PreferencesDialog.getRenderMode());
        cardPanel.update(card);
        cardPanel.setCardCaptionTopOffset(0);

        // Remove mouse wheel listeners so that scrolling works
        // Scrolling works on all areas without cards or by using the scroll bar, that's enough
//        for (MouseWheelListener l : cardPanel.getMouseWheelListeners()) {
//            cardPanel.removeMouseWheelListener(l);
//        }
        // Add a click listener for selection / drag start
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Select if not selected
                    if (!card.isSelected()) {
                        selectCard(card);
                    }
                    // Show menu
                    showCardRightClickMenu(card, e);
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    if (e.getClickCount() == 1) {
                        cardClicked(card, e);
                    } else if (e.isAltDown()) {
                        eventSource.fireEvent(card, ClientEventType.ALT_DOUBLE_CLICK);
                    } else {
                        eventSource.fireEvent(card, ClientEventType.DOUBLE_CLICK);
                    }
                }
            }
        });

        // Add a motion listener to process drags
        cardPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragger.isDragging()) {
                    // If the card isn't already selected, make sure it is
                    if (!card.isSelected()) {
                        cardClicked(card, e);
                    }
                    dragger.beginDrag(cardPanel, e);
                }
            }
        });

        // And add it
        cardContent.add(cardPanel);
        cardViews.put(card.getId(), cardPanel);

        if (duplicated) {
            sortIntoGrid(card);
            eventSource.fireEvent(card, ClientEventType.ADD_SPECIFIC_CARD);
            // Update layout
            layoutGrid();
            // Update draw
            cardScroll.revalidate();
            repaint();
        }
    }

    private final ArrayList<DragCardGridListener> listeners = new ArrayList<>();

    public void addDragCardGridListener(DragCardGridListener l) {
        listeners.add(l);
    }

    private void notifyCardsSelected() {
        for (DragCardGridListener listener : listeners) {
            listener.cardsSelected();
        }
    }

    private void selectCard(CardView targetCard) {
        // Set the selected card to the target card
        for (CardView card : allCards) {
            if (card == targetCard) {
                if (!card.isSelected()) {
                    card.setSelected(true);
                    cardViews.get(card.getId()).update(card);
                }
            } else if (card.isSelected()) {
                card.setSelected(false);
                cardViews.get(card.getId()).update(card);
            }
        }
    }

    private void toggleSelected(CardView targetCard) {
        targetCard.setSelected(!targetCard.isSelected());
        cardViews.get(targetCard.getId()).update(targetCard);
    }

    private void cardClicked(CardView targetCard, MouseEvent e) {
        if (e.isShiftDown()) {
            toggleSelected(targetCard);
        } else {
            selectCard(targetCard);
        }
        notifyCardsSelected();
    }

    private void removeCardView(CardView card) {
        allCards.remove(card);

        // Remove fromcounts
        for (CardTypeCounter counter : allCounters) {
            counter.remove(card);
        }
        updateCounts();

        cardContent.remove(cardViews.get(card.getId()));
        cardViews.remove(card.getId());
    }

    /**
     * Add a card to the cardGrid, in the position that the current sort
     * dictates
     *
     * @param newCard Card to add to the cardGrid array.
     */
    private void sortIntoGrid(CardView newCard) {
        // Ensure row 1 exists
        if (cardGrid.isEmpty()) {
            cardGrid.add(0, new ArrayList<>());
            maxStackSize.add(0, 0);
        }
        // What row to add it to?
        List<List<CardView>> targetRow;
        if (separateCreatures && !newCard.isCreature()) {
            // Ensure row 2 exists
            if (cardGrid.size() < 2) {
                cardGrid.add(1, new ArrayList<>());
                maxStackSize.add(1, 0);
                // Populate with stacks matching the first row
                for (int i = 0; i < cardGrid.get(0).size(); ++i) {
                    cardGrid.get(1).add(new ArrayList<>());
                }
            }
            targetRow = cardGrid.get(1);
        } else {
            targetRow = cardGrid.get(0);
        }

        // Find the right column to insert into
        boolean didInsert = false;
        for (int currentColumn = 0; currentColumn < cardGrid.get(0).size(); ++currentColumn) {
            // Find an item from this column
            CardView cardInColumn = null;
            for (List<List<CardView>> gridRow : cardGrid) {
                for (CardView card : gridRow.get(currentColumn)) {
                    cardInColumn = card;
                    break;
                }
            }

            // No card in this column?
            if (cardInColumn == null) {
                // Error, should not have an empty column
                LOGGER.error("Empty column! " + currentColumn);
            } else {
                int res = cardSort.getComparator().compare(newCard, cardInColumn);
                if (res <= 0) {
                    // Insert into this col, but if less, then we need to create a new col here first
                    if (res < 0) {
                        for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
                            cardGrid.get(rowIndex).add(currentColumn, new ArrayList<>());
                        }
                    }
                    targetRow.get(currentColumn).add(newCard);
                    didInsert = true;
                    break;
                } else {
                    // Nothing to do, go to next iteration
                }
            }
        }

        // If nothing else, insert in a new column after everything else
        if (!didInsert) {
            for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
                cardGrid.get(rowIndex).add(new ArrayList<>());
            }
            targetRow.get(targetRow.size() - 1).add(newCard);
        }
    }

    /**
     * Delete any empty columns / rows from the grid, and eleminate any empty
     * space in stacks
     */
    private void trimGrid() {
        // Compact stacks and rows
        for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
            List<List<CardView>> gridRow = cardGrid.get(rowIndex);
            int rowMaxStackSize = 0;
            for (List<CardView> stack : gridRow) {
                // Clear out nulls in the stack
                for (int i = 0; i < stack.size(); ++i) {
                    if (stack.get(i) == null) {
                        stack.remove(i--);
                    }
                }
                // Is the stack still non-empty?
                rowMaxStackSize = Math.max(rowMaxStackSize, stack.size());
            }
            // Is the row empty? If so remove it
            if (rowMaxStackSize == 0) {
                cardGrid.remove(rowIndex);
                maxStackSize.remove(rowIndex);
                --rowIndex;
            } else {
                maxStackSize.set(rowIndex, rowMaxStackSize);
            }
        }

        // Remove empty columns
        if (!cardGrid.isEmpty()) {
            for (int colIndex = 0; colIndex < cardGrid.get(0).size(); ++colIndex) {
                boolean hasContent = false; // Until contested
                for (List<List<CardView>> aCardGrid : cardGrid) {
                    if (!aCardGrid.get(colIndex).isEmpty()) {
                        hasContent = true;
                        break;
                    }
                }
                if (!hasContent) {
                    for (List<List<CardView>> aCardGrid : cardGrid) {
                        aCardGrid.remove(colIndex);
                    }
                    --colIndex;
                }
            }
        }

        // Clean up extra column header count labels
        while (stackCountLabels.size() > cardGrid.size()) {
            List<JLabel> labels = stackCountLabels.remove(cardGrid.size());
            for (JLabel label : labels) {
                cardContent.remove(label);
            }
        }
        int colCount = cardGrid.isEmpty() ? 0 : cardGrid.get(0).size();
        for (List<JLabel> labels : stackCountLabels) {
            while (labels.size() > colCount) {
                cardContent.remove(labels.remove(colCount));
            }
        }
    }

    private int getCardWidth() {
        if (GUISizeHelper.editorCardDimension == null) {
            return 200;
        }
        return (int) (GUISizeHelper.editorCardDimension.width * cardSizeMod);
    }

    private int getCardHeight() {
        return (int) (1.4 * getCardWidth());
    }

    /**
     * Position all of the card views correctly
     */
    private void layoutGrid() {
        // Basic dimensions
        int cardWidth = getCardWidth();
        int cardHeight = getCardHeight();
        int cardTopHeight = CardRenderer.getCardTopHeight(cardWidth);

        // Layout one at a time
        int layerIndex = 0;
        int currentY = COUNT_LABEL_HEIGHT;
        int maxWidth = 0;
        for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
            int rowMaxStackSize = 0;
            List<List<CardView>> gridRow = cardGrid.get(rowIndex);
            for (int colIndex = 0; colIndex < gridRow.size(); ++colIndex) {
                List<CardView> stack = gridRow.get(colIndex);

                // Stack count label
                if (stackCountLabels.size() <= rowIndex) {
                    stackCountLabels.add(new ArrayList<>());
                }
                if (stackCountLabels.get(rowIndex).size() <= colIndex) {
                    JLabel countLabel = new JLabel("", SwingConstants.CENTER);
                    countLabel.setForeground(Color.WHITE);
                    cardContent.add(countLabel, new Integer(0));
                    stackCountLabels.get(rowIndex).add(countLabel);
                }
                JLabel countLabel = stackCountLabels.get(rowIndex).get(colIndex);
                if (stack.isEmpty()) {
                    countLabel.setVisible(false);
                } else {
                    countLabel.setText(String.valueOf(stack.size()));
                    countLabel.setLocation(GRID_PADDING + (cardWidth + GRID_PADDING) * colIndex, currentY - COUNT_LABEL_HEIGHT);
                    countLabel.setSize(cardWidth, COUNT_LABEL_HEIGHT);
                    countLabel.setVisible(true);
                }

                // Max stack size
                rowMaxStackSize = Math.max(rowMaxStackSize, stack.size());

                // Layout cards in stack
                for (int i = 0; i < stack.size(); ++i) {
                    CardView card = stack.get(i);
                    MageCard view = cardViews.get(card.getId());
                    int x = GRID_PADDING + (cardWidth + GRID_PADDING) * colIndex;
                    int y = currentY + i * cardTopHeight;
                    view.setCardBounds(x, y, cardWidth, cardHeight);
                    cardContent.setLayer(view, layerIndex++);
                }
            }

            // Update the max stack size for this row and the max width
            maxWidth = Math.max(maxWidth, GRID_PADDING + (GRID_PADDING + cardWidth) * gridRow.size());
            maxStackSize.set(rowIndex, rowMaxStackSize);
            currentY += (cardTopHeight * (rowMaxStackSize - 1) + cardHeight) + COUNT_LABEL_HEIGHT;
        }

        // Resize card container
        cardContent.setPreferredSize(new Dimension(maxWidth, currentY - COUNT_LABEL_HEIGHT + GRID_PADDING));
        //cardContent.setSize(maxWidth, currentY - COUNT_LABEL_HEIGHT + GRID_PADDING);
    }

    private static void makeButtonPopup(final AbstractButton button, final JPopupMenu popup) {
        button.addActionListener(e -> popup.show(button, 0, button.getHeight()));
    }
}

/**
 * Note: This class can't just be a JPanel, because a JPanel doesn't draw when
 * it has Opaque = false, but this class needs to go into a JLayeredPane while
 * being translucent, so it NEEDS Opaque = false in order to behave correctly.
 * Thus this simple class is needed to implement a translucent box in a
 * JLayeredPane.
 */
class SelectionBox extends JComponent {

    public SelectionBox() {
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g = g.create();
        g.setColor(new Color(100, 100, 200, 128));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(0, 0, 255));
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.dispose();
    }
}
