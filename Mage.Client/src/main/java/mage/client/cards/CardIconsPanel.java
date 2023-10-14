package mage.client.cards;

import mage.abilities.icon.*;
import mage.abilities.icon.system.CombinedCountIcon;
import mage.client.components.StretchIcon;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.util.DebugUtil;
import org.mage.card.arcane.CardRendererUtils;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.SvgUtils;
import org.mage.plugins.card.images.ImageCache;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GUI panel to drawning icons (one of the card's side)
 *
 * @author JayDi85
 */
public class CardIconsPanel extends JPanel {

    private static final CardIconPosition DEFAULT_POSITION = CardIconPosition.LEFT;
    private static final CardIconOrder DEFAULT_ORDER = CardIconOrder.START;
    private static final CardIconColor DEFAULT_COLOR = CardIconColor.DEFAULT; // from theme
    private static final int DEFAULT_MAX_VISIBLE_COUNT = 5;
    private static final int DEFAULT_ICON_SIZE_PERCENT = 30; // TODO: add support of big icons (current: wrong align, wrong size)

    private static final int MINIMUM_ICON_SIZE = 32; // TODO: not working?
    private static final int KEEP_ICON_IN_CARD_INSIDE_PERCENT = 70; // example: 66% - 2/3 keep inside and 1/3 keep outside
    private static final int MAXIMUM_CARD_WIDTH_FOR_ICONS_SMALL_MODE = 100; // enable icons small mode for too small cards (combine ability icons to one);

    private final CardIconPosition position;
    private final CardIconOrder order;
    private final CardIconColor color;
    private final int iconSizePercent; // icons size, related to card's width
    private final List<CardIcon> icons;
    private final int cellsMaxCount; // split card side to cells, can be 1, 3, 5, 7 (x left + 1x center + x right)
    private final int cellsVisibleCount; // if it contains too much elements then replace it by combined element (example: cells x7, visible x3)
    private final int cellsOffset; // how many cells must be offset from the start and the end. Example: 0 - nothing, 1 - 1x from left and 1x from right
    private int iconsGap = 3; // gap between icons in the cells (aplies from left and right sides)
    private int halfSize = 0; // offset for icons from the card's border
    private Font font = null;

    // auto-calced for small mode, see calcSizes
    private int calcedCellsMaxCount = 1;
    private int calcedCellsVisibleCount = 1;
    private int calcedSizeSizePercent = 30;
    private int calcedCellsOffset = 1;
    private CardIconPosition calcedPosition;
    private CardIconOrder calcedOrder;

    public CardIconsPanel(CardIconRenderSettings render) {
        this(render.getCustomPosition() != null ? render.getCustomPosition() : DEFAULT_POSITION,
                render.getCustomOrder() != null ? render.getCustomOrder() : DEFAULT_ORDER,
                render.getCustomColor() != null ? render.getCustomColor() : DEFAULT_COLOR,
                render.getCustomMaxVisibleCount() > 0 ? render.getCustomMaxVisibleCount() : DEFAULT_MAX_VISIBLE_COUNT,
                render.getCustomIconSizePercent() > 0 ? render.getCustomIconSizePercent() : DEFAULT_ICON_SIZE_PERCENT
        );
    }

    public CardIconsPanel(CardIconPosition position, CardIconOrder order, CardIconColor color, int cellsVisibleCount, int iconSizePercent) {
        this(position, order, color, cellsVisibleCount, iconSizePercent, new ArrayList<>(), new Rectangle(100, 100));
    }

    public CardIconsPanel(CardIconPosition position, CardIconOrder order, CardIconColor color, int cellsVisibleCount, int iconSizePercent, List<CardIcon> icons, Rectangle startingCardSize) {
        super(null);
        this.position = (position != null ? position : DEFAULT_POSITION);
        this.color = (color != null ? color : DEFAULT_COLOR);
        this.iconSizePercent = iconSizePercent;
        this.icons = icons;

        // corners have only one icon with center order
        if (this.position.getMaxIconsAmount() == 1) {
            this.order = CardIconOrder.START;
            this.cellsOffset = 0;
            this.cellsMaxCount = 1;
        } else {
            this.order = order != null ? order : DEFAULT_ORDER;
            this.cellsOffset = 1;
            this.cellsMaxCount = 7;
        }
        int maxIcons = Math.max(1, Math.min(this.cellsMaxCount, cellsVisibleCount)); // must be in [1..cells];
        this.cellsVisibleCount = Math.min(maxIcons, this.position.getMaxIconsAmount());

        this.setVisible(false);
        this.setOpaque(false);
        if (DebugUtil.GUI_CARD_ICONS_DRAW_PANEL_BORDER) {
            this.setBorder(BorderFactory.createLineBorder(Color.red));
        }

        this.updateSizes(startingCardSize);
    }

    public void updateSizes(Rectangle cardSize) {
        this.calcSizes(cardSize);

        // panel uses GridLayout with gaps, grid is static size, so the sizes structure:
        // [gap + icon + gap + icon + ... gap]

        // corner icons must be same sizes as max possible on left/right (for a more beautiful look)
        int panelFullSize = this.halfSize * 2 + cardSize.height;
        int panelIconSize = (panelFullSize - (7 + 1) * this.iconsGap) / 7;
        int cornerHalfSize = Math.min(panelIconSize, this.halfSize * 2) / 2; // real icons can be limited by height or width

        // move panel to the inner (for a more beautiful look)
        // 2/3 keep inside and 1/3 keep outside
        // panels already centered by halfSize, so use "- this.halfSize"
        int panelOffset = Math.round(this.halfSize * 2 * KEEP_ICON_IN_CARD_INSIDE_PERCENT / 100f) - this.halfSize;

        Rectangle panelRect;
        Point panelTranslate;
        switch (this.calcedPosition) {
            case TOP:
                panelRect = new Rectangle(cardSize.x - this.halfSize, cardSize.y - this.halfSize, cardSize.width + this.halfSize * 2, this.halfSize * 2);
                panelTranslate = new Point(0, panelOffset);
                this.setLayout(new GridLayout(1, this.calcedCellsMaxCount, iconsGap, 0));
                break;
            case LEFT:
                panelRect = new Rectangle(cardSize.x - this.halfSize, cardSize.y - this.halfSize, this.halfSize * 2, cardSize.height + this.halfSize * 2);
                panelTranslate = new Point(panelOffset, 0);
                this.setLayout(new GridLayout(this.calcedCellsMaxCount, 1, 0, iconsGap));
                break;
            case RIGHT:
                panelRect = new Rectangle(cardSize.x + cardSize.width - this.halfSize, cardSize.y - this.halfSize, this.halfSize * 2, cardSize.height + this.halfSize * 2);
                panelTranslate = new Point(-panelOffset, 0);
                this.setLayout(new GridLayout(this.calcedCellsMaxCount, 1, 0, iconsGap));
                break;
            case BOTTOM:
                panelRect = new Rectangle(cardSize.x - this.halfSize, cardSize.y + cardSize.height - this.halfSize, cardSize.width + this.halfSize * 2, this.halfSize * 2);
                panelTranslate = new Point(0, -panelOffset);
                this.setLayout(new GridLayout(1, this.calcedCellsMaxCount, iconsGap, 0));
                break;
            case CORNER_TOP_LEFT:
                panelRect = new Rectangle(cardSize.x - cornerHalfSize, cardSize.y - cornerHalfSize, cornerHalfSize * 2, cornerHalfSize * 2);
                panelTranslate = new Point(panelOffset, panelOffset);
                this.setLayout(new GridLayout(1, 1, 0, 0));
                break;
            case CORNER_TOP_RIGHT:
                panelRect = new Rectangle(cardSize.x + cardSize.width - cornerHalfSize, cardSize.y - cornerHalfSize, cornerHalfSize * 2, cornerHalfSize * 2);
                panelTranslate = new Point(-panelOffset, panelOffset);
                this.setLayout(new GridLayout(1, 1, 0, 0));
                break;
            case CORNER_BOTTOM_LEFT:
                panelRect = new Rectangle(cardSize.x - cornerHalfSize, cardSize.y + cardSize.height - cornerHalfSize, cornerHalfSize * 2, cornerHalfSize * 2);
                panelTranslate = new Point(panelOffset, -panelOffset);
                this.setLayout(new GridLayout(1, 1, 0, 0));
                break;
            case CORNER_BOTTOM_RIGHT:
                panelRect = new Rectangle(cardSize.x + cardSize.width - cornerHalfSize, cardSize.y + cardSize.height - cornerHalfSize, cornerHalfSize * 2, cornerHalfSize * 2);
                panelTranslate = new Point(-panelOffset, -panelOffset);
                this.setLayout(new GridLayout(1, 1, 0, 0));
                break;
            default:
                throw new IllegalArgumentException("Card icons do not support position " + this.calcedPosition);
        }
        panelRect.translate(panelTranslate.x, panelTranslate.y);
        this.setBounds(panelRect);

        // reload icons for new size
        this.updateIcons();
    }

    public void updateIcons() {
        updateIcons(null);
    }

    public void updateIcons(List<CardIcon> newIcons) {
        this.removeAll();
        if (newIcons != null) {
            this.icons.clear();
            TreeMap<CardIconType, List<CardIcon>> cardIconMap = new TreeMap<>();
            newIcons.forEach(icon -> cardIconMap.computeIfAbsent(icon.getIconType(), k -> new ArrayList<>()).add(icon));

            for (Map.Entry<CardIconType, List<CardIcon>> entry : cardIconMap.entrySet()) {
                List<CardIcon> combined = entry.getValue()
                        .stream()
                        .filter(icon -> icon != null && icon.canBeCombined())
                        .sorted(CardIconComparator.instance)
                        .collect(Collectors.toList());

                if (combined.size() > 1) {
                    entry.getValue().removeAll(combined);

                    String combinedHint = combined.stream()
                            .map(CardIcon::getCombinedInfo)
                            .collect(Collectors.joining("<br>"));

                    CardIcon combinedIcon = new CardIconImpl(entry.getKey(), combinedHint);

                    this.icons.add(combinedIcon);
                    this.icons.addAll(entry.getValue());
                } else {
                    this.icons.addAll(entry.getValue());
                }

            }
        }

        // auto-hide panel on empty icons
        if (this.icons.isEmpty()) {
            this.setVisible(false);
            return;
        } else {
            this.setVisible(true);
        }

        int actualMaxVisibleCount = Math.min(this.calcedCellsVisibleCount, this.calcedCellsMaxCount - this.calcedCellsOffset * 2); // preserve offset cells

        List<Component> visibleComponents = new ArrayList<>();
        List<Component> combinedComponents = new ArrayList<>();
        List<Component> orderedComponents = new ArrayList<>();

        // structure:
        // * icons panel - control the icons size and offsets in card;
        // * grid panel - control the icons order and position (put empty panel for empty space)
        // * grid's cell - control one icon
        // * label - stretched icon image that occupy all cell's space
        Map<Component, CardIcon> cardLinks = new HashMap<>();
        this.icons.stream()
                .sorted(CardIconComparator.instance)
                .forEach(icon -> {
                    Component iconComponent = createIconComponent(icon);
                    if (iconComponent != null) {
                        visibleComponents.add(iconComponent);
                        cardLinks.put(iconComponent, icon);
                    }
                });

        // OPTIMIZE visible components (if card contains too much icons then combine it in one "...")
        if (visibleComponents.size() > actualMaxVisibleCount) {
            while (visibleComponents.size() > actualMaxVisibleCount - 1) {
                // combined must contains minimum 2 elements
                combinedComponents.add(visibleComponents.remove(visibleComponents.size() - 1));
            }
            String combinedHint = combinedComponents
                    .stream()
                    .map(cardLinks::get)
                    .filter(Objects::nonNull)
                    .sorted(CardIconComparator.instance)
                    .map(CardIcon::getCombinedInfo)
                    .collect(Collectors.joining("<br>"));
            CardIcon combinedIcon = new CombinedCountIcon(combinedComponents.size(), combinedHint);
            Component combinedComponent = createIconComponent(combinedIcon);
            if (combinedComponent != null) {
                visibleComponents.add(combinedComponent);
            }
        }

        // add offsets to the start of the list
        if (this.calcedOrder == CardIconOrder.START || this.calcedOrder == CardIconOrder.END) {
            for (int i = 0; i < this.calcedCellsOffset; i++) {
                JPanel panel = new JPanel(null);
                panel.setOpaque(false);
                visibleComponents.add(0, panel);
            }
        }

        // fill components list to max (grid can't put elements to cells, so must fill all)
        while (visibleComponents.size() < this.calcedCellsMaxCount) {
            JPanel panel = new JPanel(null);
            panel.setOpaque(false);
            visibleComponents.add(panel);
        }

        // ORDER visible components
        // icons sort order example with CENTER order:
        // 1: [1]
        // 3: [2 1 3]
        // 5: [4 2 1 3 5]
        // 7: [6 4 2 1 3 5 7]
        //
        // icons sort order example with START order (END order is same but reversed):
        // 1: [1]
        // 3: [1 2 3]
        // 5: [1 2 3 4 5]
        // 7: [1 2 3 4 5 6 7]
        if (this.calcedOrder == CardIconOrder.CENTER) {
            // CENTER
            if (this.calcedCellsMaxCount == 1) {
                Arrays.asList(1).forEach(i -> orderedComponents.add(visibleComponents.get(i - 1)));
            } else if (this.calcedCellsMaxCount == 3) {
                Arrays.asList(2, 1, 3).forEach(i -> orderedComponents.add(visibleComponents.get(i - 1)));
            } else if (this.calcedCellsMaxCount == 5) {
                Arrays.asList(4, 2, 1, 3, 5).forEach(i -> orderedComponents.add(visibleComponents.get(i - 1)));
            } else if (this.calcedCellsMaxCount == 7) {
                Arrays.asList(6, 4, 2, 1, 3, 5, 7).forEach(i -> orderedComponents.add(visibleComponents.get(i - 1)));
            } else {
                throw new IllegalArgumentException("Card icons do not support max size as " + this.calcedCellsMaxCount);
            }
        } else if (this.calcedOrder == CardIconOrder.START) {
            // START
            orderedComponents.addAll(visibleComponents);
        } else if (this.calcedOrder == CardIconOrder.END) {
            // END
            orderedComponents.addAll(visibleComponents);
            Collections.reverse(orderedComponents);
        } else {
            throw new IllegalArgumentException("Card icons do not support order type " + this.calcedOrder);
        }

        // ADD real components to the grid
        orderedComponents.forEach(this::add);
    }

    private Component createIconComponent(CardIcon icon) {
        if (!SvgUtils.haveSvgSupport()) {
            return null;
        }

        // direct call
        //BufferedImage iconImage = ImageManagerImpl.instance.getCardIcon(icon.getIconType().getResourceName(), this.halfSize * 2);

        // cached call
        BufferedImage iconImageCached = ImageCache.getCardIconImage(
                icon.getIconType().getResourceName(),
                this.halfSize * 2,
                color.toString()
        ).getImage();

        if (iconImageCached != null && this.font != null) {
            BufferedImage iconImageWithText = ImageManagerImpl.deepCopy(iconImageCached); // must copy cached value before modify

            // text
            JLabel label = new JLabel();
            label.setToolTipText("<html>" + ManaSymbols.replaceSymbolsWithHTML(icon.getHint(), ManaSymbols.Type.CARD_ICON_HINT));
            if (!icon.getText().isEmpty()) {
                Graphics2D g2d = iconImageWithText.createGraphics();
                g2d.setColor(PreferencesDialog.getCurrentTheme().getCardIconsTextColor(this.color));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Rectangle rect = CardRendererUtils.reduceRect(new Rectangle(0, 0, iconImageWithText.getWidth(), iconImageWithText.getHeight()), 0.8f);
                CardRendererUtils.drawCenteredText(g2d, icon.getText(), rect, this.font, true);
                g2d.dispose();
            }

            // the stretch icon can occupy all space (full grid's cell)
            StretchIcon s = new StretchIcon(iconImageWithText, true);
            label.setIcon(s);
            label.setIconTextGap(0);
            if (DebugUtil.GUI_CARD_ICONS_DRAW_ICON_BORDER) {
                label.setBorder(BorderFactory.createLineBorder(Color.green));
            }
            return label;
        }
        return null;
    }

    private void calcSizes(Rectangle cardSize) {
        // small mode takes 20% of card's sizes diff
        boolean smallMode = false;
        int maxW = GUISizeHelper.battlefieldCardMaxDimension.width;
        int minW = GUISizeHelper.battlefieldCardMinDimension.width;
        if (minW > maxW) {
            // on wrong settings
            maxW = GUISizeHelper.battlefieldCardMinDimension.width;
            minW = GUISizeHelper.battlefieldCardMaxDimension.width;
        }
        // cardSize.width < 120 - disable small mode on too big cards
        if (cardSize.width < MAXIMUM_CARD_WIDTH_FOR_ICONS_SMALL_MODE && (cardSize.width < minW + (maxW - minW) * 0.2f)) {
            smallMode = true;
        }

        // auto-sizeable icons (smaller for small card, normal for big)
        this.calcedSizeSizePercent = this.iconSizePercent;
        if (smallMode) {
            this.calcedSizeSizePercent = Math.round(this.calcedSizeSizePercent * 1.5f);
        }

        // auto-amount for icons (less for small, normal for big)
        this.calcedCellsMaxCount = this.cellsMaxCount;
        this.calcedCellsVisibleCount = this.cellsVisibleCount;
        this.calcedCellsOffset = this.cellsOffset;
        this.calcedPosition = this.position;
        this.calcedOrder = this.order;
        if (smallMode) {
            this.calcedCellsMaxCount = Math.min(5, this.calcedCellsMaxCount);
            this.calcedCellsVisibleCount = Math.min(1, this.calcedCellsVisibleCount);
            this.calcedCellsOffset = Math.min(1, this.calcedCellsOffset);
            // change order of multi-icons and ignore corners (make icons it centered)
            if (this.calcedPosition.getMaxIconsAmount() > 1) {
                this.calcedOrder = CardIconOrder.CENTER;
            }
        }

        // REAL SIZES

        // auto-sizeable gaps (use test render form to find best values)
        this.iconsGap = Math.floorDiv(cardSize.width, 100) * 2;

        // icons intersect the card like mtg arena
        this.halfSize = Math.max(MINIMUM_ICON_SIZE / 2, Math.round(cardSize.width / 100.0f * this.calcedSizeSizePercent / 2.0f));
        this.font = new Font("Arial", Font.PLAIN + Font.BOLD, Math.round(this.halfSize * 1.5f));
    }

    public int getHalfSize() {
        return halfSize;
    }
}
