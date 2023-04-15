package mage.client.cards;

import mage.abilities.icon.CardIconRenderSettings;
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
import mage.client.util.ClientEventType;
import mage.client.util.Event;
import mage.client.util.GUISizeHelper;
import mage.client.util.Listener;
import mage.client.util.comparators.*;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.util.DebugUtil;
import mage.util.RandomUtil;
import mage.view.CardView;
import mage.view.CardsView;
import org.apache.log4j.Logger;
import org.mage.card.arcane.CardRenderer;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author StravantUser, JayDi85
 */
public class DragCardGrid extends JPanel implements DragCardSource, DragCardTarget, CardEventProducer {

    private static final Logger logger = Logger.getLogger(DragCardGrid.class);
    private static final String DOUBLE_CLICK_MODE_INFO = "<html>Double click mode: <b>%s</b>";

    private Constants.DeckEditorMode mode;
    Listener<Event> cardListener;
    MouseListener countLabelListener; // clicks on the count label

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
                            eventSource.fireEvent(card, ClientEventType.DECK_REMOVE_SPECIFIC_CARD);
                        }
                    }
                }
            }
            trimGrid();
            layoutGrid();
            repaintGrid();
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

    @Override
    public CardEventSource getCardEventSource() {
        return this.eventSource;
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
            repaintGrid();
        } else {
            // Add new cards to grid
            for (CardView card : cards) {
                card.setSelected(true);
                addCardView(card, false);
                eventSource.fireEvent(card, ClientEventType.DECK_ADD_SPECIFIC_CARD);
            }
            layoutGrid();
            repaintGrid();
        }
    }

    public void changeGUISize() {
        layoutGrid();
        cardScroll.getVerticalScrollBar().setUnitIncrement(CardRenderer.getCardTopHeight(getCardWidth()));
        repaintGrid();
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
            mouseDoubleClickMode.setVisible(false);
        } else {
            creatureCountLabel.setVisible(true);
            landCountLabel.setVisible(true);
            cardSizeSliderLabel.setVisible(true);
            mouseDoubleClickMode.setVisible(true);
        }
        updateCounts();
    }

    private void updateMouseDoubleClicksInfo(boolean isHotKeyPressed) {
        boolean gameMode = isHotKeyPressed
                || mode != Constants.DeckEditorMode.FREE_BUILDING;
        String oldText = mouseDoubleClickMode.getText();
        String newText = String.format(DOUBLE_CLICK_MODE_INFO, gameMode ? "MOVE" : "DELETE");
        if (!oldText.equals(newText)) {
            mouseDoubleClickMode.setText(newText);
        }
    }

    public void removeSelection() {
        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                for (int i = 0; i < stack.size(); ++i) {
                    CardView card = stack.get(i);
                    if (card.isSelected()) {
                        eventSource.fireEvent(card, ClientEventType.DECK_REMOVE_SPECIFIC_CARD);
                        stack.set(i, null);
                        removeCardView(card);
                    }
                }
            }
        }
        trimGrid();
        layoutGrid();
        repaintGrid();
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
        updateMouseDoubleClicksInfo(false);
    }

    public enum Sort {
        NONE("No Sort", new CardViewNoneComparator()),
        CARD_TYPE("Card Type", new CardViewCardTypeComparator()),
        CMC("Mana Value", new CardViewCostComparator()),
        COLOR("Color", new CardViewColorComparator()),
        COLOR_IDENTITY("Color Identity", new CardViewColorIdentityComparator()),
        RARITY("Rarity", new CardViewRarityComparator()),
        EDH_POWER_LEVEL("EDH Power Level", new CardViewEDHPowerLevelComparator());

        Sort(String text, CardViewComparator comparator) {
            this.comparator = comparator;
            this.text = text;
        }

        public CardViewComparator getComparator() {
            return comparator;
        }

        public String getText() {
            return text;
        }

        private final CardViewComparator comparator;
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
            return card.isPlaneswalker();
        }
    };
    private final CardTypeCounter battleCounter = new CardTypeCounter() {
        @Override
        protected boolean is(CardView card) {
            return card.isBattle();
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
            battleCounter,
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
    public static final int COUNT_LABEL_HEIGHT = 40; // can contains 1 or 2 lines
    public static final int GRID_PADDING = 10;

    private static final ImageIcon INSERT_ROW_ICON = new ImageIcon(DragCardGrid.class.getClassLoader().getResource("editor_insert_row.png"));
    private static final ImageIcon INSERT_COL_ICON = new ImageIcon(DragCardGrid.class.getClassLoader().getResource("editor_insert_col.png"));

    // All of the current card views
    private final Map<UUID, MageCard> cardViews = new LinkedHashMap<>();
    private final List<CardView> allCards = new ArrayList<>();

    // card listeners
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
    JButton oldVersionButton;
    JLabel mouseDoubleClickMode;

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
                    s.cardSize = Integer.parseInt(m.group(3));
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

    public DragCardGrid() {
        // Make sure that the card grid is populated with at least one (empty) stack to begin with
        cardGrid = new ArrayList<>();

        // Component init
        setLayout(new BorderLayout());
        setOpaque(false);

        // default game mode (real game mode will be set after all components init)
        this.mode = Constants.DeckEditorMode.LIMITED_BUILDING;

        // Content
        cardContent = new JLayeredPane();
        cardContent.setLayout(null);
        cardContent.setOpaque(false);

        // ENABLE MOUSE CLICKS (cards, menu)
        this.cardListener = event -> {
            switch (event.getEventType()) {
                case CARD_POPUP_MENU:
                    if (event.getSource() != null) {
                        // menu for card
                        CardView card = (CardView) event.getSource();
                        MouseEvent me = event.getMouseEvent();
                        if (!card.isSelected()) {
                            selectCard(card);
                        }
                        showCardRightClickMenu(card, me);
                    }
                    break;

                case CARD_CLICK:
                    if (event.getSource() != null) {
                        CardView card = (CardView) event.getSource();
                        MouseEvent me = event.getMouseEvent();
                        cardClicked(card, me);
                    }
                    break;
            }
        };
        eventSource.addListener(this.cardListener);

        // keyboard listener for ALT status update
        // it requires GLOBAL listener
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ALT) {
                            updateMouseDoubleClicksInfo(e.isAltDown());
                        }
                        return false;
                    }
                });


        // ENABLE MULTI-CARDS SELECTION
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

        // Toolbar
        sortButton = new JButton("Sort");
        filterButton = new JButton("Filter");
        visibilityButton = new JButton("V"); // "Visibility" button
        selectByButton = new JButton("Select By");
        analyseButton = new JButton("M"); // "Mana" button
        blingButton = new JButton("B"); // "Bling" button
        oldVersionButton = new JButton("O"); // "Old version" button
        mouseDoubleClickMode = new JLabel(DOUBLE_CLICK_MODE_INFO);
        mouseDoubleClickMode.setToolTipText("<html>Mouse modes for double clicks:"
                + "<br> * &lt;Double Click&gt;: <b>DELETE</b> card from mainboard/sideboard (it works as <b>MOVE</b> in games);"
                + "<br> * &lt;ALT + Double Click&gt;: <b>MOVE</b> card between mainboard/sideboard (default for games);"
                + "<br> * Deck editor: use &lt;ALT + Double Click&gt; on cards list to add card to the sideboard instead mainboard."
        );
        updateMouseDoubleClicksInfo(false);

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
        toolbarInner.add(oldVersionButton);
        toolbarInner.add(mouseDoubleClickMode);
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
                repaintGrid();
            }
        });
        cardSizeSliderLabel = new JLabel("Card size:");
        sliderPanel.add(cardSizeSliderLabel);
        sliderPanel.add(cardSizeSlider);
        toolbar.add(sliderPanel, BorderLayout.EAST);
        this.add(toolbar, BorderLayout.NORTH);

        // Insert arrow
        insertArrow = new JLabel();
        insertArrow.setSize(20, 20);
        insertArrow.setVisible(false);
        cardContent.add(insertArrow, (Integer) 1000);

        // Selection panel
        selectionPanel = new SelectionBox();
        selectionPanel.setVisible(false);
        cardContent.add(selectionPanel, (Integer) 1001);

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
            separateCreaturesCb.setText("Puts creatures in separate first row");
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

        // Old version button - Switch cards to the oldest non-promo printing. In case of multiples in a set, take the lowest card number.
        oldVersionButton.setToolTipText("Switch cards to the oldest non-promo printing");
        oldVersionButton.addActionListener(evt -> oldVersionDeck());

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

        // ENABLE popup menu on non card (e.g. show all or sorting)
        cardContent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
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

    public void selectByName(List<String> cardNames) {
        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                for (CardView card : stack) {
                    if (cardNames.contains(card.getName())) {
                        card.setSelected(true);
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
        // clear grid
        for (List<List<CardView>> gridRow : cardGrid) {
            for (List<CardView> stack : gridRow) {
                stack.clear();
            }
        }
        trimGrid();

        // sort
        allCards.sort(new CardViewNameComparator());

        // re-insert
        for (CardView card : allCards) {
            sortIntoGrid(card);
        }
        trimGrid();

        // Deselect everything
        deselectAll();

        // render new grid
        layoutGrid();
        repaintGrid();
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
                        Map<String, CardView> cardNames = new HashMap<>();

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
                            Rarity r = card.getRarity();
                            if (r != null) {
                                s |= r.toString().toLowerCase(Locale.ENGLISH).contains(searchStr);
                            }
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
                            s |= card.getManaCostStr().toLowerCase(Locale.ENGLISH).contains(searchStr);
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
        repaintGrid();
    }

    private static final Pattern pattern = Pattern.compile(".*Add(.*)(\\{[WUBRGXC]\\})");

    public void analyseDeck() {
        Map<String, Integer> qtys = new HashMap<>();
        Map<String, Integer> pips = new HashMap<>();
        Map<String, Integer> pips_at_cmcs = new HashMap<>();
        Map<String, Integer> sourcePips = new HashMap<>();
        Map<String, Integer> manaCounts = new HashMap<>();
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
                    String mc = card.getManaCostStr();
                    mc = mc.replaceAll("\\{([WUBRG]).([WUBRG])\\}", "{$1}{$2}");
                    mc = mc.replaceAll("\\{", "#");
                    mc = mc.replaceAll("#2\\/", "#");
                    mc = mc.replaceAll("p}", "}");
                    mc = mc.toLowerCase(Locale.ENGLISH);
                    int cmc = card.getManaValue();

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
        // TODO: Why are these a HashMap? It can be a HashSet<String> instead, as the value is never used in the code.
        Map<String, Integer> pimpedSets = new HashMap<>();
        Map<CardView, Integer> pimpedCards = new HashMap<>();
        pimpedSets.put("PCMP", 1);
        pimpedSets.put("MPS", 1);
        pimpedSets.put("MP2", 1);
        pimpedSets.put("EXP", 1);
        pimpedSets.put("CP1", 1);
        pimpedSets.put("CP2", 1);
        pimpedSets.put("CP3", 1);

        // Judge Reward Gifts
        pimpedSets.put("JGP", 1);
        pimpedSets.put("G99", 1);
        pimpedSets.put("G00", 1);
        pimpedSets.put("G01", 1);
        pimpedSets.put("G02", 1);
        pimpedSets.put("G03", 1);
        pimpedSets.put("G04", 1);
        pimpedSets.put("G05", 1);
        pimpedSets.put("G06", 1);
        pimpedSets.put("G07", 1);
        pimpedSets.put("G08", 1);
        pimpedSets.put("G09", 1);
        pimpedSets.put("G10", 1);
        pimpedSets.put("G11", 1);
        pimpedSets.put("J12", 1);
        pimpedSets.put("J13", 1);
        pimpedSets.put("J14", 1);
        pimpedSets.put("J15", 1);
        pimpedSets.put("J16", 1);
        pimpedSets.put("J17", 1);
        pimpedSets.put("J18", 1);
        pimpedSets.put("J19", 1);
        pimpedSets.put("J20", 1);

        // Arena League
        pimpedSets.put("PARL", 1);
        pimpedSets.put("PAL99", 1);
        pimpedSets.put("PAL00", 1);
        pimpedSets.put("PAL01", 1);
        pimpedSets.put("PAL02", 1);
        pimpedSets.put("PAL03", 1);
        pimpedSets.put("PAL04", 1);
        pimpedSets.put("PAL05", 1);
        pimpedSets.put("PAL06", 1);

        pimpedSets.put("UGIN", 1);
        pimpedSets.put("PALP", 1);
        pimpedSets.put("PELP", 1);

        //Friday Night Magic
        pimpedSets.put("FNM", 1);
        pimpedSets.put("F01", 1);
        pimpedSets.put("F02", 1);
        pimpedSets.put("F03", 1);
        pimpedSets.put("F04", 1);
        pimpedSets.put("F05", 1);
        pimpedSets.put("F06", 1);
        pimpedSets.put("F07", 1);
        pimpedSets.put("F08", 1);
        pimpedSets.put("F09", 1);
        pimpedSets.put("F10", 1);
        pimpedSets.put("F11", 1);
        pimpedSets.put("F12", 1);
        pimpedSets.put("F13", 1);
        pimpedSets.put("F14", 1);
        pimpedSets.put("F15", 1);
        pimpedSets.put("F16", 1);
        pimpedSets.put("F17", 1);
        pimpedSets.put("F18", 1);

        // Magic Player Rewards 2001-2011, except for 2002 (P02), which only contains tokens
        pimpedSets.put("MPR", 1);
        pimpedSets.put("P03", 1);
        pimpedSets.put("P04", 1);
        pimpedSets.put("P05", 1);
        pimpedSets.put("P06", 1);
        pimpedSets.put("P07", 1);
        pimpedSets.put("P08", 1);
        pimpedSets.put("P09", 1);
        pimpedSets.put("P10", 1);
        pimpedSets.put("P11", 1);

        pimpedSets.put("OVNT", 1); // Vintage Championship
        pimpedSets.put("PJSE", 1); // Junior Series Europe
        pimpedSets.put("P2HG", 1); // Two-Headed Giant Tournament
        pimpedSets.put("PGTW", 1); // Gateway 2006
        pimpedSets.put("PJAS", 1); // Junior APAC Series

        pimpedSets.put("EXP", 1);
        pimpedSets.put("PGPX", 1);
        pimpedSets.put("PMEI", 1);
        pimpedSets.put("PLS", 1);

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
                                eventSource.fireEvent(pimpedCard, ClientEventType.DECK_ADD_SPECIFIC_CARD);
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
                trimGrid();

                layoutGrid();
                repaintGrid();

                JOptionPane.showMessageDialog(null, "Added " + pimpedCards.size() + " cards.  You can select them and the originals by choosing 'Multiples'");
            }
        }
    }

    private void oldVersionDeck() {
        if (this.mode != Constants.DeckEditorMode.FREE_BUILDING) {
            return;
        }

        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to switch your card versions to the oldest ones?", "WARNING",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        List<List<List<CardView>>> newCardGrid = new ArrayList<>();
        for (List<List<CardView>> gridRow : cardGrid) {
            List<List<CardView>> newGridRow = new ArrayList<>();
            for (List<CardView> stack : gridRow) {
                List<CardView> newStack = new ArrayList<>();
                for (CardView card : stack) {
                    CardInfo oldestCardInfo = CardRepository.instance.findOldestNonPromoVersionCard(card.getName());
                    if (oldestCardInfo != null) {
                        CardView oldestCardView = new CardView(oldestCardInfo.getMockCard());
                        this.removeCardView(card);
                        eventSource.fireEvent(card, ClientEventType.DECK_REMOVE_SPECIFIC_CARD);
                        this.addCardView(oldestCardView, false);
                        eventSource.fireEvent(oldestCardView, ClientEventType.DECK_ADD_SPECIFIC_CARD);
                        newStack.add(oldestCardView);
                    } else {
                        newStack.add(card);
                    }
                }
                newGridRow.add(newStack);
            }
            newCardGrid.add(newGridRow);
        }
        cardGrid = newCardGrid;
        layoutGrid();
        repaintGrid();
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

        // clear grid from empty rows
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
                    List<CardView> gridStack = new ArrayList<>();
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
                        logger.info("Orphan when setting with layout: ");
                        sortIntoGrid(orphan);
                    }
                }
            }
        }

        if (didModify) {
            // clear grid from empty rows
            trimGrid();

            // Update layout
            layoutGrid();
            repaintGrid();
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
        final MageCard cardPanel = Plugins.instance.getMageCard(card, lastBigCard, new CardIconRenderSettings(), new Dimension(getCardWidth(), getCardHeight()), null, true, true, PreferencesDialog.getRenderMode(), true);
        cardPanel.setCardContainerRef(this);
        cardPanel.update(card);
        // cards bounds set in layoutGrid()
        cardPanel.setCardCaptionTopOffset(0);

        // ENABLE DRAG SUPPORT FOR CARDS
        // TODO: rewrite mouseDragged in MageActionCallback, so it can support any drags, not hands only
        cardPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragger.isDragging()) {
                    // If the card isn't already selected, make sure it is
                    if (!card.isSelected()) {
                        selectCard(card);
                    }
                    dragger.handleDragStart(cardPanel, e);
                }
            }
        });

        // And add it
        cardContent.add(cardPanel);
        cardViews.put(card.getId(), cardPanel);

        if (duplicated) {
            sortIntoGrid(card);
            eventSource.fireEvent(card, ClientEventType.DECK_ADD_SPECIFIC_CARD);

            // clear grid from empty rows
            trimGrid();

            // Update layout
            layoutGrid();
            repaintGrid();
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
     * dictates.
     * <p>
     * Warning, you must call trimGrid() after all cards inserted
     *
     * @param newCard Card to add to the cardGrid array.
     */
    private void sortIntoGrid(CardView newCard) {
        // row 1 must exists
        if (cardGrid.isEmpty()) {
            cardGrid.add(0, new ArrayList<>());
            maxStackSize.add(0, 0);
        }

        // target row
        List<List<CardView>> targetRow;
        if (separateCreatures) {
            // separate mode (two rows mode)
            // row 1 for creatures
            // row 2 for other

            // row 2 must exists
            if (cardGrid.size() < 2) {
                cardGrid.add(1, new ArrayList<>());
                maxStackSize.add(1, 0);
                // Populate with stacks matching the first row
                for (int i = 0; i < cardGrid.get(0).size(); ++i) {
                    cardGrid.get(1).add(new ArrayList<>());
                }
            }

            // workaround to fix wrong sorting on one card insert (if no creatures then trim will remove first row)
            boolean isFirstRowWithCreatures = true;
            for (int i = 0; i < cardGrid.get(0).size(); ++i) {
                if (cardGrid.get(0).get(i).stream().anyMatch(card -> !card.isCreature())) {
                    isFirstRowWithCreatures = false;
                    break;
                }
            }

            if (!isFirstRowWithCreatures) {
                // move all cards to row 2 (at first position)
                for (int i = 0; i < cardGrid.get(0).size(); ++i) {
                    if (!cardGrid.get(0).get(i).isEmpty()) {
                        cardGrid.get(1).add(cardGrid.get(0).get(i));
                    }
                }
                cardGrid.get(0).clear();
                while (cardGrid.get(0).size() < cardGrid.get(1).size()) {
                    cardGrid.get(0).add(new ArrayList<>());
                }
            }

            if (newCard.isCreature()) {
                // creature cards goes to row 1
                targetRow = cardGrid.get(0);
            } else {
                // non-creature cards goes to row 2
                targetRow = cardGrid.get(1);
            }
        } else {
            // one row mode
            targetRow = cardGrid.get(0);
        }

        // Find the right column to insert into
        boolean didInsert = false;
        for (int currentColumn = 0; currentColumn < targetRow.size(); ++currentColumn) {
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
                logger.error("Empty column! " + currentColumn);
            } else {
                CardViewComparator cardComparator = cardSort.getComparator();
                int res = cardComparator.compare(newCard, cardInColumn);
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

        // empty row trim (trimGrid) must be called from outside after all cards inserted
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

    private void repaintGrid() {
        cardScroll.revalidate();
        cardScroll.repaint();
        repaint();
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
                    // add new count label for the column

                    // ENABLE cards auto-selection in the stack
                    if (this.countLabelListener == null) {
                        this.countLabelListener = new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                JLabel countLabel = (JLabel) e.getComponent();
                                List<CardView> cards = findCardStackByCountLabel(countLabel);
                                boolean selected = !cards.isEmpty() && cards.get(0).isSelected();
                                cards.forEach(card -> {
                                    card.setSelected(!selected);
                                    cardViews.get(card.getId()).update(card);
                                });
                            }
                        };
                    }

                    JLabel countLabel = DragCardGrid.createCountLabel(this.countLabelListener);
                    cardContent.add(countLabel, (Integer) 0);
                    stackCountLabels.get(rowIndex).add(countLabel);
                }

                JLabel countLabel = stackCountLabels.get(rowIndex).get(colIndex);
                if (stack.isEmpty()) {
                    countLabel.setVisible(false);
                } else {
                    String description = cardSort.getComparator().getCategoryName(stack.get(0));
                    DragCardGrid.updateCountLabel(countLabel, stack.size(), description);
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

    public static JLabel createCountLabel(MouseListener mouseListener) {
        JLabel countLabel = new JLabel("", JLabel.CENTER);
        countLabel.setForeground(Color.WHITE); // TODO: add theme support
        if (mouseListener != null) {
            countLabel.addMouseListener(mouseListener);
        }
        return countLabel;
    }

    public static void updateCountLabel(JLabel countLabel, int amount, String description) {
        // two modes:
        // * small: one line with count
        // * big: two lines with count and description
        String descHtml = ManaSymbols.replaceSymbolsWithHTML(description, ManaSymbols.Type.TABLE);
        boolean smallMode = description.isEmpty();
        boolean supportCustomClicks = countLabel.getMouseListeners().length > 0
                && !(countLabel.getMouseListeners()[0] instanceof ToolTipManager); // ignore auto-added ToolTipManager for mouse over hints

        // border: 1px solid black
        // white-space: nowrap;
        countLabel.setText("<html>"
                + "<div style='text-align: center;'>"
                + "  <div>" + amount + "</div>"
                + (smallMode ? "" : "  <div style=''>" + descHtml + "</div>")
                + "</div>"
                + "");
        countLabel.setToolTipText("<html>"
                + amount + (smallMode ? "" : " - " + description)
                + (supportCustomClicks ? "<br>Click on the count label to select/unselect cards stack." : "")
        );
        countLabel.setVerticalAlignment(smallMode ? JLabel.CENTER : JLabel.TOP);
        if (DebugUtil.GUI_DECK_EDITOR_DRAW_COUNT_LABEL_BORDER) {
            countLabel.setBorder(BorderFactory.createLineBorder(Color.green));
        }
    }

    private List<CardView> findCardStackByCountLabel(JLabel countLabel) {
        for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
            List<List<CardView>> gridRow = cardGrid.get(rowIndex);
            for (int colIndex = 0; colIndex < gridRow.size(); ++colIndex) {
                if (stackCountLabels.size() < rowIndex
                        || stackCountLabels.get(rowIndex).size() < colIndex
                        || !countLabel.equals(stackCountLabels.get(rowIndex).get(colIndex))) {
                    continue;
                }
                return gridRow.get(colIndex);
            }
        }
        return new ArrayList<>();
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
