package org.mage.plugins.card;

import mage.cards.Card;
import mage.cards.CardDimensions;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.interfaces.plugin.CardPlugin;
import mage.utils.CardUtil;
import mage.view.CardView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;
import net.xeoh.plugins.base.annotations.meta.Author;
import org.apache.log4j.Logger;
import org.mage.card.arcane.Animation;
import org.mage.card.arcane.CardPanel;
import org.mage.card.arcane.ManaSymbols;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.dl.DownloadGui;
import org.mage.plugins.card.dl.DownloadJob;
import org.mage.plugins.card.dl.Downloader;
import org.mage.plugins.card.dl.sources.GathererSets;
import org.mage.plugins.card.dl.sources.GathererSymbols;
import org.mage.plugins.card.images.DownloadPictures;
import org.mage.plugins.card.info.CardInfoPaneImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * {@link CardPlugin} implementation.
 *
 * @author nantuko
 * @version 0.1 01.11.2010 Mage permanents. Sorting card layout.
 */
@PluginImplementation
@Author(name = "nantuko")
public class CardPluginImpl implements CardPlugin {

    private final static Logger log = Logger.getLogger(CardPluginImpl.class);

    static private final int GUTTER_Y = 15;
    static private final int GUTTER_X = 5;
    static final float EXTRA_CARD_SPACING_X = 0.04f;
    static private final float CARD_SPACING_Y = 0.03f;
    static private final float STACK_SPACING_X = 0.07f;
    static private final float STACK_SPACING_Y = 0.13f;
    //static private final int MW_GUIDE_HEIGHT = 30;

    private int landStackMax = 5;
    private int cardWidthMin = 50, cardWidthMax = Constants.CARD_SIZE_FULL.width;
    private boolean stackVertical = false;

    private int playAreaWidth, playAreaHeight;
    private int cardWidth, cardHeight;
    private int extraCardSpacingX, cardSpacingX, cardSpacingY;
    private int stackSpacingX, stackSpacingY;
    private List<Row> rows = new ArrayList<Row>();

    @Init
    public void init() {
    }

    @PluginLoaded
    public void newPlugin(CardPlugin plugin) {
        ManaSymbols.loadImages();
        log.info(plugin.toString() + " has been loaded.");
    }

    public String toString() {
        return "[Card plugin, version 0.5]";
    }

    @Override
    public MagePermanent getMagePermanent(PermanentView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil) {
        boolean foil = canBeFoil && (new Random()).nextInt(5) == 0;
        CardPanel cardPanel = new CardPanel(permanent, gameId, true, callback, foil);
        cardPanel.setCardBounds(0, 0, dimension.width, dimension.height);
        boolean implemented = !permanent.getRarity().equals(mage.Constants.Rarity.NA);
        cardPanel.setShowCastingCost(implemented);
        return cardPanel;
    }

    @Override
    public MagePermanent getMageCard(CardView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil) {
        boolean foil = canBeFoil && (new Random()).nextInt(5) == 0;
        CardPanel cardPanel = new CardPanel(permanent, gameId, true, callback, foil);
        cardPanel.setCardBounds(0, 0, dimension.width, dimension.height);
        boolean implemented = !permanent.getRarity().equals(mage.Constants.Rarity.NA);
        cardPanel.setShowCastingCost(implemented);
        return cardPanel;
    }

    @Override
    public void sortPermanents(Map<String, JComponent> ui, Collection<MagePermanent> permanents) {
        if (ui == null)
            throw new RuntimeException("Error: no components");
        JComponent component = ui.get("jScrollPane");
        JComponent component2 = ui.get("battlefieldPanel");
        if (component == null)
            throw new RuntimeException("Error: jScrollPane is missing");
        if (component2 == null)
            throw new RuntimeException("Error: battlefieldPanel is missing");
        if (!(component instanceof JScrollPane))
            throw new RuntimeException("Error: jScrollPane has wrong type.");
        if (!(component instanceof JScrollPane))
            throw new RuntimeException("Error: battlefieldPanel is missing");

        JScrollPane jScrollPane = (JScrollPane) component;
        JLayeredPane battlefieldPanel = (JLayeredPane) component2;

        Row allLands = new Row();

        outerLoop:
        //
        for (MagePermanent permanent : permanents) {
            if (!CardUtil.isLand(permanent) || CardUtil.isCreature(permanent))
                continue;

            int insertIndex = -1;

            // Find lands with the same name.
            for (int i = 0, n = allLands.size(); i < n; i++) {
                Stack stack = allLands.get(i);
                MagePermanent firstPanel = stack.get(0);
                if (firstPanel.getOriginal().getName().equals(permanent.getOriginal().getName())) {
                    if (!empty(firstPanel.getLinks())) {
                        // Put this land to the left of lands with the same name and attachments.
                        insertIndex = i;
                        break;
                    }
                    if (!empty(permanent.getLinks()) || stack.size() == landStackMax) {
                        // If this land has attachments or the stack is full, put it to the right.
                        insertIndex = i + 1;
                        continue;
                    }
                    // Add to stack.
                    stack.add(0, permanent);
                    continue outerLoop;
                }
                if (insertIndex != -1)
                    break;
            }

            Stack stack = new Stack();
            stack.add(permanent);
            allLands.add(insertIndex == -1 ? allLands.size() : insertIndex, stack);
        }

        Row allCreatures = new Row(permanents, RowType.creature);
        Row allOthers = new Row(permanents, RowType.other);

        cardWidth = cardWidthMax;
        Rectangle rect = jScrollPane.getVisibleRect();
        playAreaWidth = rect.width;
        playAreaHeight = rect.height;
        while (true) {
            rows.clear();
            cardHeight = Math.round(cardWidth * CardPanel.ASPECT_RATIO);
            extraCardSpacingX = (int) Math.round(cardWidth * EXTRA_CARD_SPACING_X);
            cardSpacingX = cardHeight - cardWidth + extraCardSpacingX;
            cardSpacingY = (int) Math.round(cardHeight * CARD_SPACING_Y);
            stackSpacingX = stackVertical ? 0 : (int) Math.round(cardWidth * STACK_SPACING_X);
            stackSpacingY = (int) Math.round(cardHeight * STACK_SPACING_Y);
            Row creatures = (Row) allCreatures.clone();
            Row lands = (Row) allLands.clone();
            Row others = (Row) allOthers.clone();
            // Wrap all creatures and lands.
            wrap(creatures, rows, -1);
            int afterCreaturesIndex = rows.size();
            wrap(lands, rows, afterCreaturesIndex);
            // Store the current rows and others.
            List<Row> storedRows = new ArrayList<Row>(rows.size());
            for (Row row : rows)
                storedRows.add((Row) row.clone());
            Row storedOthers = (Row) others.clone();
            // Fill in all rows with others.
            for (Row row : rows)
                fillRow(others, rows, row);

            // Stop if everything fits, otherwise revert back to the stored values.
            if (creatures.isEmpty() && lands.isEmpty() && others.isEmpty())
                break;
            rows = storedRows;
            others = storedOthers;
            // Try to put others on their own row(s) and fill in the rest.
            wrap(others, rows, afterCreaturesIndex);
            for (Row row : rows)
                fillRow(others, rows, row);
            // If that still doesn't fit, scale down.
            if (creatures.isEmpty() && lands.isEmpty() && others.isEmpty())
                break;
            //cardWidth = (int)(cardWidth / 1.2);
            cardWidth--;
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
        //setPreferredSize(new Dimension(maxRowWidth - cardSpacingX, y - cardSpacingY));
        //revalidate();

        // Position all card panels.
        x = 0;
        y = GUTTER_Y;
        for (Row row : rows) {
            int rowBottom = 0;
            x = GUTTER_X;
            for (int stackIndex = 0, stackCount = row.size(); stackIndex < stackCount; stackIndex++) {
                Stack stack = row.get(stackIndex);
                // Align others to the right.
                if (RowType.other.isType(stack.get(0))) {
                    x = playAreaWidth - GUTTER_X + extraCardSpacingX;
                    for (int i = stackIndex, n = row.size(); i < n; i++)
                        x -= row.get(i).getWidth();
                }
                for (int panelIndex = 0, panelCount = stack.size(); panelIndex < panelCount; panelIndex++) {
                    MagePermanent panel = stack.get(panelIndex);
                    int stackPosition = panelCount - panelIndex - 1;
                    ///setComponentZOrder((Component)panel, panelIndex);
                    int panelX = x + (stackPosition * stackSpacingX);
                    int panelY = y + (stackPosition * stackSpacingY);
                    //panel.setLocation(panelX, panelY);
                    battlefieldPanel.moveToBack(panel);
                    panel.setCardBounds(panelX, panelY, cardWidth, cardHeight);
                }
                rowBottom = Math.max(rowBottom, y + stack.getHeight());
                x += stack.getWidth();
            }
            y = rowBottom;
        }
    }

    private boolean empty(List<?> list) {
        return list == null || list.size() == 0;
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
                if (!allowHeightOverflow && rowWidth > playAreaWidth)
                    break;
                if (!allowHeightOverflow && getRowsHeight(rows) + sourceRow.getHeight() > playAreaHeight)
                    break;
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
        for (Row row : rows)
            for (Stack stack : row)
                sourceRow.remove(stack);
        return insertIndex;
    }

    private void fillRow(Row sourceRow, List<Row> rows, Row row) {
        int rowWidth = row.getWidth();
        while (!sourceRow.isEmpty()) {
            Stack stack = sourceRow.get(0);
            rowWidth += stack.getWidth();
            if (rowWidth > playAreaWidth)
                break;
            if (stack.getHeight() > row.getHeight()) {
                if (getRowsHeight(rows) - row.getHeight() + stack.getHeight() > playAreaHeight)
                    break;
            }
            row.add(sourceRow.remove(0));
        }
    }

    private int getRowsHeight(List<Row> rows) {
        int height = 0;
        for (Row row : rows)
            height += row.getHeight();
        return height - cardSpacingY + GUTTER_Y * 2;
    }

    static private enum RowType {
        land, creature, other;

        public boolean isType(MagePermanent card) {
            switch (this) {
                case land:
                    return CardUtil.isLand(card);
                case creature:
                    return CardUtil.isCreature(card);
                case other:
                    return !CardUtil.isLand(card) && !CardUtil.isCreature(card);
                default:
                    throw new RuntimeException("Unhandled type: " + this);
            }
        }
    }

    private class Row extends ArrayList<Stack> {
        public Row() {
            super(16);
        }

        public Row(Collection<MagePermanent> permanents, RowType type) {
            this();
            addAll(permanents, type);
        }

        private void addAll(Collection<MagePermanent> permanents, RowType type) {
            for (MagePermanent panel : permanents) {
                if (!type.isType(panel))
                    continue;
                Stack stack = new Stack();
                stack.add(panel);
                add(stack);
            }
        }

        public boolean addAll(Collection<? extends Stack> c) {
            boolean changed = super.addAll(c);
            c.clear();
            return changed;
        }

        private int getWidth() {
            if (isEmpty())
                return 0;
            int width = 0;
            for (Stack stack : this)
                width += stack.getWidth();
            return width + GUTTER_X * 2 - extraCardSpacingX;
        }

        private int getHeight() {
            if (isEmpty())
                return 0;
            int height = 0;
            for (Stack stack : this)
                height = Math.max(height, stack.getHeight());
            return height;
        }
    }

    private class Stack extends ArrayList<MagePermanent> {
        public Stack() {
            super(8);
        }

        public boolean add(MagePermanent panel) {
            boolean appended = super.add(panel);
            //for (CardPanel attachedPanel : panel.attachedPanels)
            //add(attachedPanel);
            return appended;
        }

        private int getWidth() {
            return cardWidth + (size() - 1) * stackSpacingX + cardSpacingX;
        }

        private int getHeight() {
            return cardHeight + (size() - 1) * stackSpacingY + cardSpacingY;
        }
    }

    @Override
    public void downloadImages(Set<Card> allCards) {
        DownloadPictures.startDownload(null, allCards);
    }

    @Override
    public void downloadSymbols() {
        final DownloadGui g = new DownloadGui(new Downloader());

        Iterable<DownloadJob> it = new GathererSymbols();

        for (DownloadJob job : it) {
            g.getDownloader().add(job);
        }

        it = new GathererSets();
	    for(DownloadJob job:it) {
	            g.getDownloader().add(job);
	    }

        JDialog d = new JDialog((Frame) null, "Download pictures", false);
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        d.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                g.getDownloader().dispose();
            }
        });
        d.setLayout(new BorderLayout());
        d.add(g);
        d.pack();
        d.setVisible(true);
    }

    @Override
    public Image getManaSymbolImage(String symbol) {
        return ManaSymbols.getManaSymbolImage(symbol);
    }

    @Override
    public void onAddCard(MagePermanent card, int count) {
        if (card != null) {
            Animation.showCard((CardPanel) card, count > 0 ? count : 1);
            try {
                while ((card).getAlpha() + 0.05f < 1) {
                    Thread.sleep(30);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRemoveCard(MagePermanent card, int count) {
        if (card != null) {
            Animation.hideCard((CardPanel) card, count > 0 ? count : 1);
            try {
                while ((card).getAlpha() - 0.05f > 0) {
                    Thread.sleep(30);
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
}
