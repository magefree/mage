package mage.client.cards;

import mage.cards.MageCard;
import mage.cards.decks.Deck;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.client.MageFrame;
import mage.client.plugins.impl.Plugins;
import mage.client.util.CardViewColorComparator;
import mage.client.util.CardViewCostComparator;
import mage.client.util.CardViewRarityComparator;
import mage.client.util.GUISizeHelper;
import mage.constants.CardType;
import mage.game.GameException;
import mage.interfaces.plugin.CardPlugin;
import mage.view.CardView;
import mage.view.CardsView;
import org.apache.log4j.Logger;
import org.mage.card.arcane.CardRenderer;
import org.mage.plugins.card.CardPluginImpl;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorMap;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by StravantUser on 2016-09-20.
 */
public class DragCardGrid extends JPanel implements DragCardSource, DragCardTarget {
    private static Logger LOGGER = Logger.getLogger(DragCardGrid.class);

    @Override
    public Collection<CardView> dragCardList() {
        ArrayList<CardView> selectedCards = new ArrayList<>();
        for (CardView card : allCards) {
            if (card.isSelected()) {
                selectedCards.add(card);
            }
        }
        return selectedCards;
    }

    @Override
    public void dragCardBegin() {
        LOGGER.info("Begin drag");
    }

    @Override
    public void dragCardEnd(DragCardTarget target) {
        LOGGER.info("End drag (" + target + ")");
        if (target == this) {
            // Already handled by dragged onto handler
        } else {
            // Remove dragged cards
            for (ArrayList<ArrayList<CardView>> gridRow : cardGrid) {
                for (ArrayList<CardView> stack : gridRow) {
                    for (int i = 0; i < stack.size(); ++i) {
                        CardView view = stack.get(i);
                        if (view.isSelected()) {
                            stack.set(i, null);
                        }
                    }
                }
            }
            trimGrid();
            layoutGrid();
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
                int rowHeight = cardTopHeight*(maxStack-1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    break;
                } else {
                    rowIndex = i+1;
                    curY = rowBottom;
                }
            }

            // Insert between two columns
            insertArrow.setIcon(INSERT_COL_ICON);
            insertArrow.setSize(64, 64);
            insertArrow.setLocation((cardWidth + GRID_PADDING)*col + GRID_PADDING/2 - 32, curY);
        } else {
            // Clamp to a new col one after the current last one
            col = Math.min(col, gridWidth);

            // Determine place in the col
            int curY = COUNT_LABEL_HEIGHT;
            int rowIndex = 0;
            int offsetIntoStack = 0;
            for (int i = 0; i < cardGrid.size(); ++i) {
                int maxStack = maxStackSize.get(i);
                int rowHeight = cardTopHeight*(maxStack-1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    offsetIntoStack = y - curY;
                    break;
                } else {
                    rowIndex = i+1;
                    offsetIntoStack = y - rowBottom;
                    curY = rowBottom;
                }
            }

            // Get the appropirate stack
            ArrayList<CardView> stack;
            if (rowIndex < cardGrid.size() && col < cardGrid.get(0).size()) {
                stack = cardGrid.get(rowIndex).get(col);
            } else {
                stack = new ArrayList<>();
            }

            // Figure out position in the stack based on the offsetIntoRow
            int stackInsertIndex = (offsetIntoStack + cardTopHeight/2) / cardTopHeight;
            stackInsertIndex = Math.max(0, Math.min(stackInsertIndex, stack.size()));

            // Position arrow
            insertArrow.setIcon(INSERT_ROW_ICON);
            insertArrow.setSize(64, 32);
            insertArrow.setLocation((cardWidth + GRID_PADDING)*col + GRID_PADDING + cardWidth/2 - 32, curY + stackInsertIndex*cardTopHeight - 32);
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
            for (ArrayList<ArrayList<CardView>> gridRow : cardGrid) {
                for (ArrayList<CardView> stack : gridRow) {
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
                int rowHeight = cardTopHeight*(maxStack-1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    break;
                } else {
                    rowIndex = i+1;
                    curY = rowBottom;
                }
            }

            // Add a new row if needed
            if (rowIndex >= cardGrid.size()) {
                ArrayList<ArrayList<CardView>> newRow = new ArrayList<>();
                if (!cardGrid.isEmpty()) {
                    for (int colIndex = 0; colIndex < cardGrid.get(0).size(); ++colIndex) {
                        newRow.add(new ArrayList<CardView>());
                    }
                }
                cardGrid.add(newRow);
                maxStackSize.add(0);
            }

            // Insert the new column to add to
            for (int i = 0; i < cardGrid.size(); ++i) {
                cardGrid.get(i).add(col, new ArrayList<CardView>());
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
                int rowHeight = cardTopHeight*(maxStack-1) + cardHeight;
                int rowBottom = curY + rowHeight + COUNT_LABEL_HEIGHT;

                // Break out if we're in that row
                if (y < rowBottom) {
                    // Set the row
                    rowIndex = i;
                    offsetIntoStack = y - curY;
                    break;
                } else {
                    rowIndex = i+1;
                    offsetIntoStack = y - rowBottom;
                    curY = rowBottom;
                }
            }

            // Add a new row if needed
            if (rowIndex >= cardGrid.size()) {
                ArrayList<ArrayList<CardView>> newRow = new ArrayList<>();
                if (!cardGrid.isEmpty()) {
                    for (int colIndex = 0; colIndex < cardGrid.get(0).size(); ++colIndex) {
                        newRow.add(new ArrayList<CardView>());
                    }
                }
                cardGrid.add(newRow);
                maxStackSize.add(0);
            }

            // Add a new col if needed
            if (col >= cardGrid.get(0).size()) {
                for (int i = 0; i < cardGrid.size(); ++i) {
                    cardGrid.get(i).add(new ArrayList<CardView>());
                }
            }

            // Get the appropirate stack
            ArrayList<CardView> stack = cardGrid.get(rowIndex).get(col);

            // Figure out position in the stack based on the offsetIntoRow
            int stackInsertIndex = (offsetIntoStack + cardTopHeight/2) / cardTopHeight;
            stackInsertIndex = Math.max(0, Math.min(stackInsertIndex, stack.size()));

            // Insert the cards
            stack.addAll(stackInsertIndex, cards);
        }

        // Remove empty rows / cols / spaces in stacks
        if (source == this) {
            trimGrid();
            layoutGrid();
            cardScroll.repaint();
        }
    }

    public enum Sort {
        NONE(new Comparator<CardView>() {
            @Override
            public int compare(CardView o1, CardView o2) {
                // Always equal, sort into the first row
                return 0;
            }
        }),
        CMC(new CardViewCostComparator()),
        COLOR(new CardViewColorComparator()),
        RARITY(new CardViewRarityComparator());

        Sort(Comparator<CardView> comparator) {
            this.comparator = comparator;
        }

        public Comparator<CardView> getComparator() {
            return comparator;
        }

        private Comparator<CardView> comparator;
    }



    // Constants
    public static int CARD_WIDTH = 150;
    public static int COUNT_LABEL_HEIGHT = 20;
    public static int GRID_PADDING = 10;

    private static ImageIcon INSERT_ROW_ICON = new ImageIcon(DragCardGrid.class.getClassLoader().getResource("editor_insert_row.png"));
    private static ImageIcon INSERT_COL_ICON = new ImageIcon(DragCardGrid.class.getClassLoader().getResource("editor_insert_col.png"));

    // All of the current card views
    private Map<UUID, MageCard> cardViews = new LinkedHashMap<>();
    private ArrayList<CardView> allCards = new ArrayList<>();

    // Top bar with dropdowns for sort / filter / etc
    JButton sortButton;
    JButton filterButton;

    // Popup for toolbar
    JPopupMenu filterPopup;
    JPopupMenu sortPopup;

    // Main two controls holding the scrollable card grid
    JScrollPane cardScroll;
    JLayeredPane cardContent;

    // Drag onto insert arrow
    JLabel insertArrow;

    // Card area selection panel
    SelectionBox selectionPanel;
    int selectionDragStartX;
    int selectionDragStartY;

    // Dragging
    private CardDraggerGlassPane dragger = new CardDraggerGlassPane(this);

    // The grid of cards
    // The outermost array contains multiple rows of stacks of cards
    // The next inner array represents a row of stacks of cards
    // The innermost array represents a single vertical stack of cards
    private ArrayList<ArrayList<ArrayList<CardView>>> cardGrid;
    private ArrayList<Integer> maxStackSize = new ArrayList<>();
    private ArrayList<ArrayList<JLabel>> stackCountLabels = new ArrayList<>();
    private Sort cardSort = Sort.CMC;
    private boolean separateCreatures = true;

    // Constructor
    public DragCardGrid() {
        // Make sure that the card grid is populated with at least one (empty) stack to begin with
        cardGrid = new ArrayList<>();

        // Component init
        setLayout(new BorderLayout());

        // Toolbar
        sortButton = new JButton("Sort");
        filterButton = new JButton("Filter");

        // Tmp load button
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDeck();
            }
        });

        JPanel toolbar = new JPanel(new BorderLayout());
        JPanel toolbarInner = new JPanel();
        toolbar.setBackground(new Color(250, 250, 250, 150));
        toolbar.setOpaque(true);
        toolbarInner.setOpaque(false);
        toolbarInner.add(sortButton);
        toolbarInner.add(filterButton);
        toolbarInner.add(loadButton);
        toolbar.add(toolbarInner, BorderLayout.WEST);
        this.add(toolbar, BorderLayout.NORTH);

        // Content
        cardContent = new JLayeredPane();
        cardContent.setLayout(null);
        cardContent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                beginSelectionDrag(e.getX(), e.getY());
                updateSelectionDrag(e.getX(), e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                updateSelectionDrag(e.getX(), e.getY());
                endSelectionDrag(e.getX(), e.getY());
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
        this.add(cardScroll, BorderLayout.CENTER);

        // Insert arrow
        insertArrow = new JLabel();
        insertArrow.setSize(20, 20);
        insertArrow.setVisible(false);
        cardContent.add(insertArrow, new Integer(1000));

        // Selection panel
        selectionPanel = new SelectionBox();
        selectionPanel.setVisible(false);
        cardContent.add(selectionPanel, new Integer(1001));

        // Sort popup
        sortPopup = new JPopupMenu();
        sortPopup.setPreferredSize(new Dimension(300, 300));
        makeButtonPopup(sortButton, sortPopup);

        // Filter popup
        filterPopup = new JPopupMenu();
        filterPopup.setPreferredSize(new Dimension(300, 300));
        makeButtonPopup(filterButton, filterPopup);




    }

    /**
     * Selection drag handling
     */
    private void beginSelectionDrag(int x, int y) {
        // Show the selection panel
        selectionPanel.setVisible(true);
        selectionPanel.setLocation(x, y);
        cardScroll.revalidate();

        // Store the drag start location
        selectionDragStartX = x;
        selectionDragStartY = y;
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
            ArrayList<ArrayList<CardView>> gridRow = cardGrid.get(rowIndex);
            for (int col = 0; col < gridRow.size(); ++col) {
                ArrayList<CardView> stack = gridRow.get(col);
                int stackBottomBegin = curY + cardTopHeight*(stack.size());
                int stackBottomEnd = curY + cardTopHeight*(stack.size()-1) + cardHeight;
                for (int i = 0; i < stack.size(); ++i) {
                    CardView card = stack.get(i);
                    MageCard view = cardViews.get(card.getId());
                    boolean inBoundsX = (col >= col1 && col <= col2);
                    boolean inBoundsY =  (i >= stackStartIndex && i <= stackEndIndex);
                    boolean lastCard = (i == stack.size()-1);
                    if (inBoundsX && (inBoundsY || (lastCard && (y2 >= stackBottomBegin && y1 <= stackBottomEnd)))) {
                        if (!card.isSelected()) {
                            card.setSelected(true);
                            view.update(card);
                        }
                    } else {
                        if (card.isSelected()) {
                            card.setSelected(false);
                            view.update(card);
                        }
                    }
                }
            }
            curY += cardTopHeight*(maxStackSize.get(rowIndex)-1) + cardHeight + COUNT_LABEL_HEIGHT;
        }
    }

    private void endSelectionDrag(int x, int y) {
        // Hide the selection panel
        selectionPanel.setVisible(false);
    }


    private void loadDeck() {
        JFileChooser fcSelectDeck = new JFileChooser();
        String lastFolder = MageFrame.getPreferences().get("lastDeckFolder", "");
        if (!lastFolder.isEmpty()) {
            fcSelectDeck.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fcSelectDeck.showOpenDialog(DragCardGrid.this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcSelectDeck.getSelectedFile();
            try {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                Deck deck = Deck.load(DeckImporterUtil.importDeck(file.getPath()), true, true);
                Logger.getLogger(DragCardGrid.class).info("Loaded " + deck.getCards().size());
                setCards(new CardsView(deck.getCards()));
            } catch (GameException ex) {
                JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            try {
                if (file != null) {
                    MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
                }
            } catch (IOException ex) {
            }
        }
        fcSelectDeck.setSelectedFile(null);
    }

    // Update the contents of the card grid
    public void setCards(CardsView cardsView) {
        // Remove all of the cards not in the cardsView
        boolean didModify = false; // Until contested
        for (int i = 0; i < cardGrid.size(); ++i) {
            ArrayList<ArrayList<CardView>> gridRow = cardGrid.get(i);
            for (int j = 0; j < gridRow.size(); ++j) {
                ArrayList<CardView> stack = gridRow.get(j);
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

        // Add any new card views
        for (CardView newCard: cardsView.values()) {
            if (!cardViews.containsKey(newCard.getId())) {
                // Is a new card
                addCardView(newCard);

                try {
                    // Put it into the appropirate place in the grid given the current sort
                    sortIntoGrid(newCard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Mark
                didModify = true;
            }
        }

        // Modifications?
        if (didModify) {
            // Trim extra rows / columns from the grid
            trimGrid();

            // Update layout
            layoutGrid();

            // Update draw
            cardScroll.revalidate();
            repaint();
        }
    }

    private void addCardView(final CardView card) {
        allCards.add(card);

        // Create the card view
        final MageCard cardPanel = Plugins.getInstance().getMageCard(card, null, new Dimension(100, 140), null, true);
        cardPanel.update(card);

        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardClicked(card, e);
            }
        });
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
    }

    private void cardClicked(CardView targetCard, MouseEvent e) {
        // Set the selected card to the target card
        for (CardView card : allCards) {
            if (card == targetCard) {
                if (!card.isSelected()) {
                    card.setSelected(true);
                    cardViews.get(card.getId()).update(card);
                }
            } else {
                if (card.isSelected()) {
                    card.setSelected(false);
                    cardViews.get(card.getId()).update(card);
                }
            }
        }
    }

    private void removeCardView(CardView card) {
        allCards.remove(card);
        cardContent.remove(cardViews.get(card.getId()));
        cardViews.remove(card.getId());
    }

    /**
     * Add a card to the cardGrid, in the position that the current sort dictates
     * @param newCard
     */
    private void sortIntoGrid(CardView newCard) {
        // Ensure row 1 exists
        if (cardGrid.size() == 0) {
            cardGrid.add(0, new ArrayList<ArrayList<CardView>>());
            maxStackSize.add(0, 0);
        }
        // What row to add it to?
        ArrayList<ArrayList<CardView>> targetRow;
        if (separateCreatures && !newCard.getCardTypes().contains(CardType.CREATURE)) {
            // Ensure row 2 exists
            if (cardGrid.size() < 2) {
                cardGrid.add(1, new ArrayList<ArrayList<CardView>>());
                maxStackSize.add(1, 0);
                // Populate with stacks matching the first row
                for (int i = 0; i < cardGrid.get(0).size(); ++i) {
                    cardGrid.get(1).add(new ArrayList<CardView>());
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
            for (ArrayList<ArrayList<CardView>> gridRow : cardGrid) {
                for (CardView card : gridRow.get(currentColumn)) {
                    cardInColumn = card;
                    break;
                }
            }

            // No card in this column?
            if (cardInColumn == null) {
                // Error, should not have an empty column
                LOGGER.error("Empty column!");
            } else {
                int res = cardSort.getComparator().compare(newCard, cardInColumn);
                if (res <= 0) {
                    // Insert into this col, but if less, then we need to create a new col here first
                    if (res < 0) {
                        for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
                            cardGrid.get(rowIndex).add(currentColumn, new ArrayList<CardView>());
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
                cardGrid.get(rowIndex).add(new ArrayList<CardView>());
            }
            targetRow.get(targetRow.size()-1).add(newCard);
        }
    }

    /**
     * Delete any empty columns / rows from the grid, and eleminate any empty space in stacks
     */
    private void trimGrid() {
        // Compact stacks and rows
        for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
            ArrayList<ArrayList<CardView>> gridRow = cardGrid.get(rowIndex);
            int rowMaxStackSize = 0;
            for (ArrayList<CardView> stack : gridRow) {
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
                for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
                    if (!cardGrid.get(rowIndex).get(colIndex).isEmpty()) {
                        hasContent = true;
                        break;
                    }
                }
                if (!hasContent) {
                    for (int rowIndex = 0; rowIndex < cardGrid.size(); ++rowIndex) {
                        cardGrid.get(rowIndex).remove(colIndex);
                    }
                    --colIndex;
                }
            }
        }

        // Clean up extra column header count labels
        while (stackCountLabels.size() > cardGrid.size()) {
            ArrayList<JLabel> labels = stackCountLabels.remove(cardGrid.size());
            for (JLabel label : labels) {
                cardContent.remove(label);
            }
        }
        int colCount = cardGrid.isEmpty() ? 0 : cardGrid.get(0).size();
        for (ArrayList<JLabel> labels : stackCountLabels) {
            while (labels.size() > colCount) {
                cardContent.remove(labels.remove(colCount));
            }
        }
    }

    private int getCardWidth() {
        return CARD_WIDTH;
    }

    private int getCardHeight() {
        return (int)(1.4*getCardWidth());
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
            ArrayList<ArrayList<CardView>> gridRow = cardGrid.get(rowIndex);
            for (int colIndex = 0; colIndex < gridRow.size(); ++colIndex) {
                ArrayList<CardView> stack = gridRow.get(colIndex);

                // Stack count label
                if (stackCountLabels.size() <= rowIndex) {
                    stackCountLabels.add(new ArrayList<JLabel>());
                }
                if (stackCountLabels.get(rowIndex).size() <= colIndex) {
                    JLabel countLabel = new JLabel("", SwingConstants.CENTER);
                    cardContent.add(countLabel, new Integer(0));
                    stackCountLabels.get(rowIndex).add(countLabel);
                }
                JLabel countLabel = stackCountLabels.get(rowIndex).get(colIndex);
                if (stack.isEmpty()) {
                    countLabel.setVisible(false);
                } else {
                    countLabel.setText("" + stack.size());
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
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popup.show(button, 0, button.getHeight());
            }
        });
        popup.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                button.setSelected(false);
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }

    public static void main(String[] args) {
        GUISizeHelper.calculateGUISizes();
        Plugins.getInstance().loadPlugins();
        JFrame frame = new JFrame();
        frame.setTitle("Test");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLUE);
        DragCardGrid grid = new DragCardGrid();
        grid.setPreferredSize(new Dimension(800, 600));
        frame.add(grid, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}

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
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        g.dispose();
    }
}