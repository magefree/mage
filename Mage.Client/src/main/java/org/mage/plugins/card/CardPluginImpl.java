package org.mage.plugins.card;

import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.client.util.GUISizeHelper;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * {@link CardPlugin} implementation.
 *
 * @author nantuko, JayDi85
 */
@PluginImplementation
@Author(name = "nantuko")
public class CardPluginImpl implements CardPlugin {

    private static final Logger LOGGER = Logger.getLogger(CardPluginImpl.class);

    private static final int GUTTER_Y = 15; // top offset before cards
    private static final int GUTTER_X = 15; // left offset before cards
    static final float EXTRA_CARD_SPACING_X = 0.04f;
    private static final float CARD_SPACING_Y = 0.03f;
    private static final float STACK_SPACING_X = 0.07f;
    private static final float STACK_SPACING_Y = 0.10f;
    private static final float ATTACHMENT_SPACING_Y = 0.13f;

    private static final int landStackMax = 5;
    private int cardWidthMin = (int) GUISizeHelper.battlefieldCardMinDimension.getWidth();
    private int cardWidthMax = (int) GUISizeHelper.battlefieldCardMaxDimension.getWidth();
    // card width increment for auto-size searching (bigger value - faster draw speed on screen size, but not as accurate)
    private static final int CARD_WIDTH_AUTO_FIT_INCREMENT = 10;

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
    private CardPanel makeCardPanel(CardView view, UUID gameId, boolean loadImage, ActionCallback callback, boolean isFoil, Dimension dimension, int renderMode, boolean needFullPermanentRender) {
        switch (renderMode) {
            case 0:
                return new CardPanelRenderModeMTGO(view, gameId, loadImage, callback, isFoil, dimension, needFullPermanentRender);
            case 1:
                return new CardPanelRenderModeImage(view, gameId, loadImage, callback, isFoil, dimension, needFullPermanentRender);
            default:
                throw new IllegalStateException("Unknown render mode " + renderMode);

        }
    }

    @Override
    public MageCard getMagePermanent(PermanentView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage, int renderMode, boolean needFullPermanentRender) {
        CardPanel cardPanel = makeCardPanel(permanent, gameId, loadImage, callback, false, dimension, renderMode, needFullPermanentRender);
        cardPanel.setShowCastingCost(true);
        return cardPanel;
    }

    @Override
    public MageCard getMageCard(CardView cardView, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage, int renderMode, boolean needFullPermanentRender) {
        CardPanel cardPanel = makeCardPanel(cardView, gameId, loadImage, callback, false, dimension, renderMode, needFullPermanentRender);
        cardPanel.setShowCastingCost(true);
        return cardPanel;
    }

    @Override
    public int sortPermanents(Map<String, JComponent> ui, Map<UUID, MageCard> cards, boolean nonPermanentsOwnRow, boolean topPanel) {
        //requires to find out is position have been changed that includes:
        //adding/removing permanents, type change

        // must return new height, so battlefield scrolls can be enabled on too big sizes

        if (ui == null) {
            throw new RuntimeException("No battlefield ui for layout");
        }

        JLayeredPane battlefieldPanel = (JLayeredPane) ui.get("battlefieldPanel");
        JComponent cardsPanel = ui.get("jPanel");
        JScrollPane scrollPane = (JScrollPane) ui.get("scrollPane");
        if (battlefieldPanel == null || cardsPanel == null || scrollPane == null) {
            throw new RuntimeException("No battlefield components for layout");
        }

        Row rowAllLands = new Row();

        outerLoop:
        //
        for (MageCard card : cards.values()) {
            MagePermanent perm = (MagePermanent) card.getMainPanel(); // all cards must be MagePermanent on battlefield

            if (!perm.isLand() || perm.isCreature()) {
                continue;
            }

            int insertIndex = -1;

            // Find already added lands with the same name.
            for (int i = 0, n = rowAllLands.size(); i < n; i++) {
                // stack contains main card panel, but for any size/order manipulation you must use top layer panel
                Stack stack = rowAllLands.get(i);
                MagePermanent firstPanelPerm = stack.get(0);
                if (firstPanelPerm.getOriginal().getName().equals(perm.getOriginal().getName())) {

                    if (!empty(firstPanelPerm.getOriginalPermanent().getAttachments())) {
                        // Put this land to the left of lands with the same name and attachments.
                        insertIndex = i;
                        break;
                    }
                    List<CounterView> counters = firstPanelPerm.getOriginalPermanent().getCounters();
                    if (counters != null && !counters.isEmpty()) {
                        // don't put to first panel if it has counters
                        insertIndex = i;
                        break;
                    }

                    if (!empty(perm.getOriginalPermanent().getAttachments()) || stack.size() == landStackMax) {
                        // If this land has attachments or the stack is full, put it to the right.
                        insertIndex = i + 1;
                        continue;
                    }
                    counters = perm.getOriginalPermanent().getCounters();
                    if (counters != null && !counters.isEmpty()) {
                        // if a land has counter, put it to the right
                        insertIndex = i + 1;
                        continue;
                    }
                    // Add to stack.
                    stack.add(0, perm);
                    continue outerLoop;
                }
                if (insertIndex != -1) {
                    break;
                }
            }

            Stack stack = new Stack();

            if (perm.getOriginalPermanent().getAttachments() != null
                    && !perm.getOriginalPermanent().getAttachments().isEmpty()
                    && !perm.getOriginalPermanent().isAttachedTo()) {
                // get the number of all attachements and sub attachments
                AttachmentLayoutInfos ali = calculateNeededNumberOfVerticalColumns(0, cards, card);
                stack.setMaxAttachedCount(ali.getAttachments());
                stack.setAttachmentColumns(ali.getColumns());
            }

            stack.add(perm);
            rowAllLands.add(insertIndex == -1 ? rowAllLands.size() : insertIndex, stack);
        }

        Row rowAllCreatures = new Row(cards, RowType.creature);
        Row rowAllOthers = new Row(cards, RowType.other);
        Row rowAllAttached = new Row(cards, RowType.attached);

        boolean othersOnTheRight = true;
        if (nonPermanentsOwnRow) {
            othersOnTheRight = false;
            rowAllCreatures.addAll(rowAllOthers);
            rowAllOthers.clear();
        }

        // try to auto-fit cards
        cardWidth = cardWidthMax;
        Rectangle rect = battlefieldPanel.getVisibleRect();
        playAreaWidth = rect.width;
        playAreaHeight = rect.height;
        while (true) {
            rows.clear();
            // calculate values based on the card size that is changing with every iteration
            cardHeight = Math.round(cardWidth * CardPanel.ASPECT_RATIO);
            extraCardSpacingX = Math.round(cardWidth * EXTRA_CARD_SPACING_X);
            cardSpacingX = cardHeight - cardWidth + extraCardSpacingX; // need space for tap animation (horizontal position)
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
            cardWidth -= CARD_WIDTH_AUTO_FIT_INCREMENT;
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

        // Position all card panels
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
                    MagePermanent panelPerm = stack.get(panelIndex); // it's original card panel, but you must change top layer
                    int stackPosition = panelCount - panelIndex - 1;
                    if (cardsPanel != null) {
                        cardsPanel.setComponentZOrder(panelPerm.getTopPanelRef(), panelIndex);
                    }
                    int panelX = x + (stackPosition * stackSpacingX);
                    int panelY = y + (stackPosition * stackSpacingY);
                    try {
                        // may cause:
                        // java.lang.IllegalArgumentException: illegal component position 26 should be less then 26
                        battlefieldPanel.moveToFront(panelPerm.getTopPanelRef());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    panelPerm.getTopPanelRef().setCardBounds(panelX, panelY, cardWidth, cardHeight);
                }
                rowBottom = Math.max(rowBottom, y + stack.getHeight());
                x += stack.getWidth();
            }
            y = rowBottom;
        }

        // we need this only for defining card size
        // attached permanents will be handled separately
        for (Stack stack : rowAllAttached) {
            for (MagePermanent panelPerm : stack) {
                panelPerm.getTopPanelRef().setCardBounds(0, 0, cardWidth, cardHeight);
            }
        }

        // scrollbars speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(GUISizeHelper.getCardsScrollbarUnitInc(cardHeight));

        return y;
    }

    private boolean empty(List<?> list) {
        return list == null || list.isEmpty();
    }

    private int wrap(Row sourceRow, List<Row> rows, int insertIndex) {
        // The cards are sure to fit (with vertical scrolling) at the minimum card width.
        boolean allowHeightOverflow = (cardWidth <= cardWidthMin);

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

    private AttachmentLayoutInfos calculateNeededNumberOfVerticalColumns(int currentCol, Map<UUID, MageCard> cards, MageCard cardWithAttachments) {
        int maxCol = ++currentCol;
        int attachments = 0;
        MagePermanent permWithAttachments = (MagePermanent) cardWithAttachments.getMainPanel();
        for (UUID attachmentId : permWithAttachments.getOriginalPermanent().getAttachments()) {
            MageCard attachedCard = cards.get(attachmentId);
            if (attachedCard != null) {
                attachments++;
                MagePermanent attachedPerm = (MagePermanent) attachedCard.getMainPanel();
                if (attachedPerm.getOriginalPermanent().getAttachments() != null && !attachedPerm.getOriginalPermanent().getAttachments().isEmpty()) {
                    AttachmentLayoutInfos attachmentLayoutInfos = calculateNeededNumberOfVerticalColumns(currentCol, cards, attachedCard);
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

        public boolean isType(MagePermanent permanent) {
            switch (this) {
                case land:
                    return permanent.isLand();
                case creature:
                    return permanent.isCreature();
                case other:
                    return !permanent.isLand() && !permanent.isCreature();
                case attached:
                    return permanent.getOriginalPermanent().isAttachedToPermanent();
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

        public Row(Map<UUID, MageCard> cards, RowType type) {
            this();
            addAll(cards, type);
        }

        private void addAll(Map<UUID, MageCard> cards, RowType type) {
            for (MageCard card : cards.values()) {
                MagePermanent perm = (MagePermanent) card.getMainPanel();

                if (!type.isType(perm)) {
                    continue;
                }
                // all attached permanents are grouped separately later
                if (type != RowType.attached && RowType.attached.isType(perm)) {
                    continue;
                }
                Stack stack = new Stack();
                stack.add(perm);
                if (perm.getOriginalPermanent().getAttachments() != null) {
                    AttachmentLayoutInfos ali = calculateNeededNumberOfVerticalColumns(0, cards, card);
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

    private void symbolsOnFinish() {

    }

    /**
     * Download various symbols (mana, tap, set).
     *
     * @param imagesDir Path to check in and store symbols to. Can't be null.
     */
    @Override
    public void downloadSymbols(String imagesDir) {
        final Downloader downloader = new Downloader();
        final DownloadGui downloadGui = new DownloadGui(downloader);

        LOGGER.info("Symbols download prepare...");
        Iterable<DownloadJob> jobs;

        jobs = new GathererSymbols();
        for (DownloadJob job : jobs) {
            downloader.add(job);
        }

        jobs = new GathererSets();
        for (DownloadJob job : jobs) {
            downloader.add(job);
        }

        jobs = new ScryfallSymbolsSource();
        for (DownloadJob job : jobs) {
            downloader.add(job);
        }

        /*
        it = new CardFrames(imagesDir); // TODO: delete frames download (not need now)
        for (DownloadJob job : it) {
            g.getDownloader().add(job);
        }
        */

        jobs = new DirectLinksForDownload();
        for (DownloadJob job : jobs) {
            downloader.add(job);
        }

        LOGGER.info("Symbols download needs " + downloader.getJobs().size() + " files");

        // download GUI dialog
        JDialog dialog = new JDialog((Frame) null, "Download symbols", false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // user force to close window/downloader
                downloader.cleanup();
            }
        });
        dialog.setLayout(new BorderLayout());
        dialog.add(downloadGui);
        dialog.pack();
        dialog.setVisible(true);

        // downloader controller thread
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                downloader.publishAllJobs();
                downloader.waitFinished();
                downloader.cleanup();
                return null;
            }
        };
        // downloader finisher
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        // all done, can close dialog and refresh symbols for UI
                        LOGGER.info("Symbols download finished");
                        dialog.dispose();
                        ManaSymbols.loadImages();
                        ImageCache.clearCache();
                    }
                }
            }
        });
        worker.execute();
    }

    @Override
    public void onAddCard(MageCard card, int count) {
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
    public void onRemoveCard(MageCard card, int count) {
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
        return ImageCache.getImageOriginal(card).getImage();
    }
}
