package org.mage.plugins.card;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.constants.Rarity;
import mage.interfaces.plugin.CardPlugin;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;
import net.xeoh.plugins.base.annotations.meta.Author;
import org.apache.log4j.Logger;
import org.mage.card.arcane.*;
import org.mage.plugins.card.dl.DownloadGui;
import org.mage.plugins.card.dl.DownloadJob;
import org.mage.plugins.card.dl.Downloader;
import org.mage.plugins.card.dl.sources.DirectLinksForDownload;
import org.mage.plugins.card.dl.sources.GathererSets;
import org.mage.plugins.card.dl.sources.GathererSymbols;
import org.mage.plugins.card.dl.sources.ScryfallSymbolsSource;
import org.mage.plugins.card.images.ImageCache;
import org.mage.plugins.card.info.CardInfoPaneImpl;

/**
 * {@link CardPlugin} implementation.
 *
 * @author nantuko
 * @version 0.1 01.11.2010 Mage permanents. Sorting card layout.
 * @version 0.6 17.07.2011 #sortPermanents got option to display non-land
 * permanents in one pile
 * @version 0.7 29.07.2011 face down cards support
 */
@PluginImplementation
@Author(name = "nantuko")
public class CardPluginImpl implements CardPlugin {

    private static final Logger LOGGER = Logger.getLogger(CardPluginImpl.class);

    private static final int GUTTER_Y = 15;
    private static final int GUTTER_X = 5;
    static final float EXTRA_CARD_SPACING_X = 0.04f;
    private static final float CARD_SPACING_Y = 0.03f;
    private static final float STACK_SPACING_X = 0.07f;
    private static final float STACK_SPACING_Y = 0.10f;
    private static final float ATTACHMENT_SPACING_Y = 0.13f;

    private static final int landStackMax = 5;
    // private int cardWidthMin = 50, cardWidthMax = Constants.CARD_SIZE_FULL.width;
    private int cardWidthMin = (int) GUISizeHelper.battlefieldCardMinDimension.getWidth();
    private int cardWidthMax = (int) GUISizeHelper.battlefieldCardMaxDimension.getWidth();

    private static final boolean stackVertical = false;

    private int playAreaWidth, playAreaHeight;
    private int cardWidth, cardHeight;
    private int extraCardSpacingX, cardSpacingX, cardSpacingY;
    private int stackSpacingX, stackSpacingY, attachmentSpacingY;
    private List<Row> rows = new ArrayList<>();

    public CardPluginImpl() {
        setGUISize();
    }

    @Init
    public void init() {
    }

    @PluginLoaded
    public void newPlugin(CardPlugin plugin) {
        LOGGER.info(plugin.toString() + " has been loaded.");
    }

    @Override
    public String toString() {
        return "[Card plugin, version 0.7]";
    }

    @Override
    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        cardWidthMin = (int) GUISizeHelper.battlefieldCardMinDimension.getWidth();
        cardWidthMax = (int) GUISizeHelper.battlefieldCardMaxDimension.getWidth();
    }

    /**
     * Temporary card rendering shim. Split card rendering isn't implemented
     * yet, so use old component based rendering for the split cards.
     */
    private CardPanel makePanel(CardView view, UUID gameId, boolean loadImage, ActionCallback callback, boolean isFoil, Dimension dimension) {
        String fallback = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_FALLBACK, "false");
        if (fallback.equals("true")) {
            return new CardPanelComponentImpl(view, gameId, loadImage, callback, isFoil, dimension);
        } else {
            return new CardPanelRenderImpl(view, gameId, loadImage, callback, isFoil, dimension);
        }
    }

    @Override
    public MagePermanent getMagePermanent(PermanentView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage) {
        CardPanel cardPanel = makePanel(permanent, gameId, loadImage, callback, false, dimension);
        cardPanel.setShowCastingCost(true);
        return cardPanel;
    }

    @Override
    public MagePermanent getMageCard(CardView cardView, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage) {
        CardPanel cardPanel = makePanel(cardView, gameId, loadImage, callback, false, dimension);
        cardPanel.setShowCastingCost(true);
        return cardPanel;
    }

    @Override
    public int sortPermanents(Map<String, JComponent> ui, Map<UUID, MagePermanent> permanents, boolean nonPermanentsOwnRow, boolean topPanel) {
        //TODO: add caching
        //requires to find out is position have been changed that includes:
        //adding/removing permanents, type change

        if (ui == null) {
            throw new RuntimeException("Error: no components");
        }
        JComponent component = ui.get("battlefieldPanel");

        if (component == null) {
            throw new RuntimeException("Error: battlefieldPanel is missing");
        }

        JLayeredPane battlefieldPanel = (JLayeredPane) component;
        JComponent jPanel = ui.get("jPanel");

        Row rowAllLands = new Row();

        outerLoop:
        //
        for (MagePermanent permanent : permanents.values()) {
            if (!permanent.isLand() || permanent.isCreature()) {
                continue;
            }

            int insertIndex = -1;

            // Find already added lands with the same name.
            for (int i = 0, n = rowAllLands.size(); i < n; i++) {
                Stack stack = rowAllLands.get(i);
                MagePermanent firstPanel = stack.get(0);
                if (firstPanel.getOriginal().getName().equals(permanent.getOriginal().getName())) {

                    if (!empty(firstPanel.getOriginalPermanent().getAttachments())) {
                        // Put this land to the left of lands with the same name and attachments.
                        insertIndex = i;
                        break;
                    }
                    List<CounterView> counters = firstPanel.getOriginalPermanent().getCounters();
                    if (counters != null && !counters.isEmpty()) {
                        // don't put to first panel if it has counters
                        insertIndex = i;
                        break;
                    }

                    if (!empty(permanent.getOriginalPermanent().getAttachments()) || stack.size() == landStackMax) {
                        // If this land has attachments or the stack is full, put it to the right.
                        insertIndex = i + 1;
                        continue;
                    }
                    counters = permanent.getOriginalPermanent().getCounters();
                    if (counters != null && !counters.isEmpty()) {
                        // if a land has counter, put it to the right
                        insertIndex = i + 1;
                        continue;
                    }
                    // Add to stack.
                    stack.add(0, permanent);
                    continue outerLoop;
                }
                if (insertIndex != -1) {
                    break;
                }
            }

            Stack stack = new Stack();

            if (permanent.getOriginalPermanent().getAttachments() != null
                    && !permanent.getOriginalPermanent().getAttachments().isEmpty()
                    && !permanent.getOriginalPermanent().isAttachedTo()) {
                // get the number of all attachements and sub attachments
                AttachmentLayoutInfos ali = calculateNeededNumberOfVerticalColumns(0, permanents, permanent);
                stack.setMaxAttachedCount(ali.getAttachments());
                stack.setAttachmentColumns(ali.getColumns());
            }

            stack.add(permanent);
            rowAllLands.add(insertIndex == -1 ? rowAllLands.size() : insertIndex, stack);
        }

        Row rowAllCreatures = new Row(permanents, RowType.creature);
        Row rowAllOthers = new Row(permanents, RowType.other);
        Row rowAllAttached = new Row(permanents, RowType.attached);

        boolean othersOnTheRight = true;
        if (nonPermanentsOwnRow) {
            othersOnTheRight = false;
            rowAllCreatures.addAll(rowAllOthers);
            rowAllOthers.clear();
        }

        cardWidth = cardWidthMax;
        Rectangle rect = battlefieldPanel.getVisibleRect();
        playAreaWidth = rect.width;
        playAreaHeight = rect.height;
        while (true) {
            rows.clear();
            // calculate values based on the card size that is changing with every iteration
            cardHeight = Math.round(cardWidth * CardPanel.ASPECT_RATIO);
            extraCardSpacingX = Math.round(cardWidth * EXTRA_CARD_SPACING_X);
            cardSpacingX = cardHeight - cardWidth + extraCardSpacingX;
            cardSpacingY = Math.round(cardHeight * CARD_SPACING_Y);
            stackSpacingX = stackVertical ? 0 : Math.round(cardWidth * STACK_SPACING_X);
            stackSpacingY = Math.round(cardHeight * STACK_SPACING_Y);
            attachmentSpacingY = Math.round(cardHeight * ATTACHMENT_SPACING_Y);
            // clone data
            Row creatures = (Row) rowAllCreatures.clone();
            Row lands = (Row) rowAllLands.clone();
            Row others = (Row) rowAllOthers.clone();

            // Wrap all creatures and lands.
            int addOthersIndex;
            if (topPanel) {
                wrap(lands, rows, -1);
                wrap(others, rows, rows.size());
                addOthersIndex = rows.size();
                wrap(creatures, rows, addOthersIndex);
            } else {
                wrap(creatures, rows, -1);
                addOthersIndex = rows.size();
                wrap(lands, rows, rows.size());
                wrap(others, rows, rows.size());

            }

            // Store the current rows and others.
            List<Row> storedRows = new ArrayList<>(rows.size());
            for (Row row : rows) {
                storedRows.add((Row) row.clone());
            }
            Row storedOthers = (Row) others.clone();
            // Fill in all rows with others.
            for (Row row : rows) {
                fillRow(others, rows, row);
            }

            // Stop if everything fits, otherwise revert back to the stored values.
            if (creatures.isEmpty() && lands.isEmpty() && others.isEmpty()) {
                break;
            }
            rows = storedRows;
            others = storedOthers;
            // Try to put others on their own row(s) and fill in the rest.
            wrap(others, rows, addOthersIndex);
            for (Row row : rows) {
                fillRow(others, rows, row);
            }
            // If that still doesn't fit, scale down.
            if (creatures.isEmpty() && lands.isEmpty() && others.isEmpty()) {
                break;
            }
            //FIXME: -1 is too slow. why not binary search?
            cardWidth -= 3;

        }

        // Get size of all the rows.
        int x, y = GUTTER_Y;
        int maxRowWidth = 0;
        for (Row row : rows) {
            int rowBottom = 0;
            x = GUTTER_X;
            for (int stackIndex = 0, stackCount = row.size(); stackIndex < stackCount; stackIndex++) {
                Stack stack = row.get(stackIndex);
                rowBottom = Math.max(rowBottom, y + stack.getHeight());
                x += stack.getWidth();
            }
            y = rowBottom;
            maxRowWidth = Math.max(maxRowWidth, x);
        }

        // Position all card panels.
        y = GUTTER_Y;
        for (Row row : rows) {
            int rowBottom = 0;
            x = GUTTER_X;
            for (int stackIndex = 0, stackCount = row.size(); stackIndex < stackCount; stackIndex++) {
                Stack stack = row.get(stackIndex);
                // Align others to the right.
                if (othersOnTheRight && RowType.other.isType(stack.get(0))) {
                    x = playAreaWidth - GUTTER_X + extraCardSpacingX;
                    for (int i = stackIndex, n = row.size(); i < n; i++) {
                        x -= row.get(i).getWidth();
                    }
                }
                for (int panelIndex = 0, panelCount = stack.size(); panelIndex < panelCount; panelIndex++) {
                    MagePermanent panel = stack.get(panelIndex);
                    int stackPosition = panelCount - panelIndex - 1;
                    if (jPanel != null) {
                        jPanel.setComponentZOrder(panel, panelIndex);
                    }
                    int panelX = x + (stackPosition * stackSpacingX);
                    int panelY = y + (stackPosition * stackSpacingY);
                    try {
                        // may cause:
                        // java.lang.IllegalArgumentException: illegal component position 26 should be less then 26
                        battlefieldPanel.moveToFront(panel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    panel.setCardBounds(panelX, panelY, cardWidth, cardHeight);
                }
                rowBottom = Math.max(rowBottom, y + stack.getHeight());
                x += stack.getWidth();
            }
            y = rowBottom;
        }

        // we need this only for defining card size
        // attached permanents will be handled separately
        for (Stack stack : rowAllAttached) {
            for (MagePermanent panel : stack) {
                panel.setCardBounds(0, 0, cardWidth, cardHeight);
            }
        }

        return y;
    }

    private boolean empty(List<?> list) {
        return list == null || list.isEmpty();
    }

    private int wrap(Row sourceRow, List<Row> rows, int insertIndex) {
        // The cards are sure to fit (with vertical scrolling) at the minimum card width.
        boolean allowHeightOverflow = cardWidth == cardWidthMin;

        Row currentRow = new Row();
        for (int i = 0, n = sourceRow.size() - 1; i <= n; i++) {
            Stack stack = sourceRow.get(i);
            // If the row is not empty and this stack doesn't fit, add the row.
            int rowWidth = currentRow.getWidth();
            if (!currentRow.isEmpty() && rowWidth + stack.getWidth() > playAreaWidth) {
                // Stop processing if the row is too wide or tall.
                if (!allowHeightOverflow && rowWidth > playAreaWidth) {
                    break;
                }
                if (!allowHeightOverflow && getRowsHeight(rows) + sourceRow.getHeight() > playAreaHeight) {
                    break;
                }
                rows.add(insertIndex == -1 ? rows.size() : insertIndex, currentRow);
                currentRow = new Row();
            }
            currentRow.add(stack);
        }
        // Add the last row if it is not empty and it fits.
        if (!currentRow.isEmpty()) {
            int rowWidth = currentRow.getWidth();
            if (allowHeightOverflow || rowWidth <= playAreaWidth) {
                if (allowHeightOverflow || getRowsHeight(rows) + sourceRow.getHeight() <= playAreaHeight) {
                    rows.add(insertIndex == -1 ? rows.size() : insertIndex, currentRow);
                }
            }
        }
        // Remove the wrapped stacks from the source row.
        for (Row row : rows) {
            for (Stack stack : row) {
                sourceRow.remove(stack);
            }
        }
        return insertIndex;
    }

    private void fillRow(Row sourceRow, List<Row> rows, Row row) {
        int rowWidth = row.getWidth();
        while (!sourceRow.isEmpty()) {
            Stack stack = sourceRow.get(0);
            rowWidth += stack.getWidth();
            if (rowWidth > playAreaWidth) {
                break;
            }
            if (stack.getHeight() > row.getHeight()
                    && getRowsHeight(rows) - row.getHeight() + stack.getHeight() > playAreaHeight) {
                break;
            }
            row.add(sourceRow.remove(0));
        }
    }

    private int getRowsHeight(List<Row> rows) {
        int height = 0;
        for (Row row : rows) {
            height += row.getHeight();
        }
        return height - cardSpacingY + GUTTER_Y * 2;
    }

    private AttachmentLayoutInfos calculateNeededNumberOfVerticalColumns(int currentCol, Map<UUID, MagePermanent> permanents, MagePermanent permanentWithAttachments) {
        int maxCol = ++currentCol;
        int attachments = 0;
        for (UUID attachmentId : permanentWithAttachments.getOriginalPermanent().getAttachments()) {
            MagePermanent attachedPermanent = permanents.get(attachmentId);
            if (attachedPermanent != null) {
                attachments++;
                if (attachedPermanent.getOriginalPermanent().getAttachments() != null && !attachedPermanent.getOriginalPermanent().getAttachments().isEmpty()) {
                    AttachmentLayoutInfos attachmentLayoutInfos = calculateNeededNumberOfVerticalColumns(currentCol, permanents, attachedPermanent);
                    if (attachmentLayoutInfos.getColumns() > maxCol) {
                        maxCol = attachmentLayoutInfos.getColumns();
                        attachments += attachmentLayoutInfos.getAttachments();
                    }
                }
            }
        }
        return new AttachmentLayoutInfos(maxCol, attachments);
    }

    private enum RowType {
        land, creature, other, attached;

        public boolean isType(MagePermanent card) {
            switch (this) {
                case land:
                    return card.isLand();
                case creature:
                    return card.isCreature();
                case other:
                    return !card.isLand() && !card.isCreature();
                case attached:
                    return card.getOriginalPermanent().isAttachedToPermanent();
                default:
                    throw new RuntimeException("Unhandled type: " + this);
            }
        }
    }

    private class Row extends ArrayList<Stack> {

        private static final long serialVersionUID = 1L;

        public Row() {
            super(16);
        }

        public Row(Map<UUID, MagePermanent> permanents, RowType type) {
            this();
            addAll(permanents, type);
        }

        private void addAll(Map<UUID, MagePermanent> permanents, RowType type) {
            for (MagePermanent permanent : permanents.values()) {
                if (!type.isType(permanent)) {
                    continue;
                }
                // all attached permanents are grouped separately later
                if (type != RowType.attached && RowType.attached.isType(permanent)) {
                    continue;
                }
                Stack stack = new Stack();
                stack.add(permanent);
                if (permanent.getOriginalPermanent().getAttachments() != null) {
                    AttachmentLayoutInfos ali = calculateNeededNumberOfVerticalColumns(0, permanents, permanent);
                    stack.setMaxAttachedCount(ali.getAttachments());
                    stack.setAttachmentColumns(ali.getColumns());
                }
                add(stack);
            }
        }

        @Override
        public boolean addAll(Collection<? extends Stack> c) {
            boolean changed = super.addAll(c);
            c.clear();
            return changed;
        }

        private int getWidth() {
            if (isEmpty()) {
                return 0;
            }
            int width = 0;
            for (Stack stack : this) {
                width += stack.getWidth();
            }
            return width + GUTTER_X * 2 - extraCardSpacingX;
        }

        private int getHeight() {
            if (isEmpty()) {
                return 0;
            }
            int height = 0;
            for (Stack stack : this) {
                height = Math.max(height, stack.getHeight());
            }
            return height;
        }
    }

    private class Stack extends ArrayList<MagePermanent> {

        private static final long serialVersionUID = 1L;

        /**
         * Max attached object count attached to single permanent in the stack.
         */
        private int maxAttachedCount = 0;
        private int attachmentColumns = 0;

        public Stack() {
            super(8);
        }

        private int getWidth() {
            return cardWidth + (size() - 1) * stackSpacingX + cardSpacingX + (12 * attachmentColumns);
        }

        private int getHeight() {
            return cardHeight + (size() - 1) * stackSpacingY + cardSpacingY + attachmentSpacingY * maxAttachedCount;
        }

        public int getMaxAttachedCount() {
            return maxAttachedCount;
        }

        public void setMaxAttachedCount(int maxAttachedCount) {
            this.maxAttachedCount = maxAttachedCount;
        }

        public void setAttachmentColumns(int attachmentColumns) {
            this.attachmentColumns = attachmentColumns;
        }
    }

    private final class AttachmentLayoutInfos {

        private int columns;
        private int attachments;

        public AttachmentLayoutInfos(int columns, int attachments) {
            this.columns = columns;
            this.attachments = attachments;
        }

        public int getColumns() {
            return columns;
        }

        public int getAttachments() {
            return attachments;
        }

        public void increaseAttachments() {
            attachments++;
        }

        public void increaseColumns() {
            columns++;
        }
    }

    /**
     * Download various symbols (mana, tap, set).
     *
     * @param imagesDir Path to check in and store symbols to. Can't be null.
     */
    @Override
    public void downloadSymbols(String imagesDir) {
        final DownloadGui g = new DownloadGui(new Downloader());

        Iterable<DownloadJob> it;

        it = new GathererSymbols();
        for (DownloadJob job : it) {
            g.getDownloader().add(job);
        }

        it = new GathererSets();
        for (DownloadJob job : it) {
            g.getDownloader().add(job);
        }

        it = new ScryfallSymbolsSource();
        for (DownloadJob job : it) {
            g.getDownloader().add(job);
        }

        /*
        it = new CardFrames(imagesDir); // TODO: delete frames download (not need now)
        for (DownloadJob job : it) {
            g.getDownloader().add(job);
        }
         */
        it = new DirectLinksForDownload();
        for (DownloadJob job : it) {
            g.getDownloader().add(job);
        }

        JDialog d = new JDialog((Frame) null, "Download symbols", false);
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        d.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                g.getDownloader().dispose();
                ManaSymbols.loadImages();
                // TODO: check reload process after download (icons do not update)
            }
        });
        d.setLayout(new BorderLayout());
        d.add(g);
        d.pack();
        d.setVisible(true);
    }

    @Override
    public void onAddCard(MagePermanent card, int count) {
        if (card != null) {
            Animation.showCard(card, count > 0 ? count : 1);
            try {
                while ((card).getAlpha() + 0.05f < 1) {
                    TimeUnit.MILLISECONDS.sleep(30);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRemoveCard(MagePermanent card, int count) {
        if (card != null) {
            Animation.hideCard(card, count > 0 ? count : 1);
            try {
                while ((card).getAlpha() - 0.05f > 0) {
                    TimeUnit.MILLISECONDS.sleep(30);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JComponent getCardInfoPane() {
        return new CardInfoPaneImpl();
    }

    @Override
    public BufferedImage getOriginalImage(CardView card) {
        return ImageCache.getImageOriginal(card);
    }
}
