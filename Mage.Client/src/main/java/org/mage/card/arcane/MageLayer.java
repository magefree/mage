package org.mage.card.arcane;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconCategory;
import mage.abilities.icon.CardIconRenderSettings;
import mage.abilities.icon.system.PlayableCountIcon;
import mage.cards.MageCard;
import mage.cards.MageCardAnimationSettings;
import mage.cards.MageCardLocation;
import mage.cards.MageCardSpace;
import mage.client.cards.CardIconsPanel;
import mage.client.cards.CardIconsPanelFactory;
import mage.client.dialog.PreferencesDialog;
import mage.constants.Zone;
import mage.util.DebugUtil;
import mage.view.CardView;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Additional layer for mage cards (example: layer with card icons).
 * One MageCard can have multiple layers in the future.
 * <p>
 * WARNING, all added listeners goes to card's main panel (mouse, events, etc)
 *
 * @author JayDi85
 */
public class MageLayer extends MageCard {

    private static final Logger logger = Logger.getLogger(MageLayer.class);

    JLayeredPane mainContainer;
    JPanel mainLayerCard;
    JPanel mainLayerIcons;
    JPanel mainLayerDebug = null;

    MageCard mainPanel;

    // empty spaces to control real card size in the center
    JPanel spaceLeft;
    JPanel spaceRight;
    JPanel spaceTop;
    JPanel spaceBottom;

    // drawing spaces, you must ignore it in animations and other calcs
    MageCardSpace lastOuterSpace = MageCardSpace.empty;

    // card icons
    CardIconRenderSettings iconsRender;
    List<CardIconsPanel> iconsPanels = new ArrayList<>(); // for calcs only
    CardIconsPanel iconsDebugPanel;
    CardIconsPanel iconsAbilitiesPanel;
    CardIconsPanel iconsPlayablePanel;
    CardIconsPanel iconsCommanderPanel;

    public MageLayer(MageCard mainPanel, CardIconRenderSettings iconsRender) {
        this.mainPanel = mainPanel;
        this.mainPanel.setTopPanelRef(this);

        // component structure (border layout):
        //  - main container: JLayeredPane (center)
        //    * layer with card: custom size, border layout
        //      - main panel + spaces
        //    * layer with icons: custom size
        //    * layer with debug drawing

        // component
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        // main container
        this.mainContainer = new JLayeredPane();
        this.mainContainer.setOpaque(false);
        this.add(this.mainContainer, BorderLayout.CENTER);

        // card layer
        this.mainLayerCard = new JPanel(new BorderLayout());
        this.mainLayerCard.setOpaque(false);
        this.mainContainer.add(this.mainLayerCard, (Integer) 0);
        // main panel + spaces
        this.mainLayerCard.add(this.mainPanel, BorderLayout.CENTER);
        this.initEmptySpaces();

        // icons layer
        this.mainLayerIcons = new JPanel(null);
        this.mainLayerIcons.setOpaque(false);
        this.mainContainer.add(this.mainLayerIcons, (Integer) 10);

        // debug layer
        if (DebugUtil.GUI_CARD_DRAW_MOUSE_CONTAINS_BOUNDS) {
            this.mainLayerDebug = new JPanel(null);
            this.mainLayerDebug.setOpaque(false);
            this.mainLayerDebug.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            this.mainContainer.add(this.mainLayerDebug, (Integer) 20);
        }

        // init icons panels, real icons and sizes will added by setCardBounds and setSizes
        this.iconsRender = iconsRender;
        this.initCardIconsPanels();

        // Warning, you must ignore outer/draw spaces, use getCardLocation to find a real component and card size/position
        // If you inherits a MageLayer then you must implements contains(int x, int y) for correct mouse events

        if (DebugUtil.GUI_CARD_DRAW_OUTER_BORDER) {
            this.setBorder(BorderFactory.createLineBorder(Color.red));
        }
        if (DebugUtil.GUI_CARD_DRAW_INNER_BORDER) {
            this.mainPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        }
    }

    private void initEmptySpaces() {
        this.spaceLeft = new JPanel(null);
        this.spaceLeft.setOpaque(false);
        this.mainLayerCard.add(this.spaceLeft, BorderLayout.WEST);
        //
        this.spaceRight = new JPanel(null);
        this.spaceRight.setOpaque(false);
        this.mainLayerCard.add(this.spaceRight, BorderLayout.EAST);
        //
        this.spaceTop = new JPanel(null);
        this.spaceTop.setOpaque(false);
        this.mainLayerCard.add(this.spaceTop, BorderLayout.NORTH);
        //
        this.spaceBottom = new JPanel(null);
        this.spaceBottom.setOpaque(false);
        this.mainLayerCard.add(this.spaceBottom, BorderLayout.SOUTH);
    }

    private void initCardIconsPanels() {
        this.iconsDebugPanel = null;
        this.iconsAbilitiesPanel = null;
        this.iconsPlayablePanel = null;
        this.iconsCommanderPanel = null;

        if (this.iconsRender.isDebugMode()) {
            // DEBUG MODE -- only one debug panel
            this.iconsDebugPanel = CardIconsPanelFactory.createDebugPanel(this.iconsRender);
            this.iconsPanels.add(this.iconsDebugPanel);
        } else {
            // NORMAL mode -- multiple panels
            if (PreferencesDialog.getRenderIconsForAbilities()) {
                // abilities
                this.iconsAbilitiesPanel = CardIconsPanelFactory.createAbilitiesPanel();
                this.iconsPanels.add(this.iconsAbilitiesPanel);

                // commander
                this.iconsCommanderPanel = CardIconsPanelFactory.createCommanderPanel();
                this.iconsPanels.add(this.iconsCommanderPanel);
            }
            // playable amount
            if (PreferencesDialog.getRenderIconsForPlayable()) {
                this.iconsPlayablePanel = CardIconsPanelFactory.createPlayablePanel();
                this.iconsPanels.add(this.iconsPlayablePanel);
            }
        }

        this.iconsPanels.forEach(panel -> this.mainLayerIcons.add(panel));
    }

    private void setEmptySpaces(int left, int right, int top, int bottom) {
        this.setEmptySpaces(new MageCardSpace(left, right, top, bottom));
    }

    private void setEmptySpaces(MageCardSpace space) {
        Border border = space.getDebugColor() == null ? null : BorderFactory.createLineBorder(space.getDebugColor());

        this.spaceLeft.setPreferredSize(new Dimension(space.getLeft(), 0));
        this.spaceLeft.setBorder(border);
        //
        this.spaceRight.setPreferredSize(new Dimension(space.getRight(), 0));
        this.spaceRight.setBorder(border);
        //
        this.spaceTop.setPreferredSize(new Dimension(0, space.getTop()));
        this.spaceTop.setBorder(border);
        //
        this.spaceBottom.setPreferredSize(new Dimension(0, space.getBottom()));
        this.spaceBottom.setBorder(border);
    }

    @Override
    public MageCard getMainPanel() {
        return mainPanel.getMainPanel();
    }

    @Override
    public void onBeginAnimation() {
        mainPanel.onBeginAnimation();
    }

    @Override
    public void onEndAnimation() {
        mainPanel.onEndAnimation();
    }

    @Override
    public boolean isTapped() {
        return mainPanel.isTapped();
    }

    @Override
    public boolean isFlipped() {
        return mainPanel.isFlipped();
    }

    @Override
    public void setAlpha(float transparency) {
        mainPanel.setAlpha(transparency);
    }

    @Override
    public float getAlpha() {
        return mainPanel.getAlpha();
    }

    @Override
    public CardView getOriginal() {
        return mainPanel.getOriginal();
    }

    @Override
    public void setCardCaptionTopOffset(int yOffsetPercent) {
        mainPanel.setCardCaptionTopOffset(yOffsetPercent);
    }

    /**
     * Scale inner card to draw additional icons or something (example: card icons in outer space)
     *
     * @param renderWidth
     * @param renderHeight
     * @return
     */
    private MageCardSpace getAdditionalSpaces(int renderWidth, int renderHeight) {
        return new MageCardSpace(0, 0, Math.round(renderHeight * 0f), 0);
    }

    @Override
    public void setCardBounds(int x, int y, int width, int height) {
        // base idea: child layers should not know about parent layer
        //
        // render logic:
        // * scale the current layer to fit additional elemenst like icons
        // * draw child layer with new sizes
        //
        // animation logic (maybe it can be change in the future):
        // * animation implemented as g2d graphic context scale in Paint() method
        // * all layers and elements must be moved as one object
        // * only the main panel (child) can do a calcs for the animation (so send parent sizes to recalc it)

        // if (this.getTopPanelRef() == this && this.getOriginal().getName().equals("Kathari Remnant")) { // for debug only
        if (this.getTopPanelRef() == this) { // TODO: is it support multi layer drawing?

            // scale inner card and create space for additional drawing like icons
            MageCardSpace innerSpace = getAdditionalSpaces(width, height);

            // extra space for animation and other drawing
            // WTF, I'm tired with render calcs, so make BIG draw spaces for any needs
            MageCardSpace outerSpace = new MageCardSpace(width * 2, width * 2, height * 2, height * 2);
            //MageCardSpace outerSpace = new MageCardSpace(50, 30, 150, 20);
            this.lastOuterSpace = outerSpace;

            // construct new spaces (outer + inner)
            MageCardSpace fullSpace = MageCardSpace.combine(innerSpace, outerSpace).withDebugColor(innerSpace.getDebugColor());
            this.setEmptySpaces(fullSpace);
            //noinspection deprecation - it's ok to use inner setBounds here
            this.setBounds(x - outerSpace.getLeft(), y - outerSpace.getTop(), width + outerSpace.getWidth(), height + outerSpace.getHeight());
            mainPanel.setCardBounds(x + innerSpace.getLeft(), y + innerSpace.getTop(), width - innerSpace.getWidth(), height - innerSpace.getHeight());
        } else {
            this.setEmptySpaces(0, 0, 0, 0);
            //noinspection deprecation - it's ok to use inner setBounds here
            this.setBounds(x, y, width, height);
            mainPanel.setCardBounds(x, y, width, height);
        }

        MageCardLocation location = this.getCardLocation();

        // panel sizes
        this.mainLayerCard.setBounds(0, 0, location.getComponentWidth(), location.getComponentHeight());
        this.mainLayerIcons.setBounds(0, 0, location.getComponentWidth(), location.getComponentHeight());

        // icons sizes
        Rectangle cardSize = new Rectangle(location.getCardRelativeX(), location.getCardRelativeY(), location.getCardWidth(), location.getCardHeight());
        iconsPanels.forEach(panel -> {
            panel.updateSizes(cardSize);
        });
    }

    @Override
    public void update(CardView card) {
        // icons update
        updateCardIcons(card);

        // card update
        mainPanel.update(card);
    }

    private void updateCardIcons(CardView card) {
        Map<CardIconsPanel, List<CardIcon>> newIcons = new HashMap<>();
        this.iconsPanels.forEach(panel -> newIcons.put(panel, new ArrayList<>()));

        List<CardIcon> allIcons = new ArrayList<>();
        // main icons
        allIcons.addAll(card.getCardIcons());
        // playable icons
        if (card.getPlayableStats().getPlayableImportantAmount() > 0) {
            allIcons.add(new PlayableCountIcon(card.getPlayableStats()));
        }

        // create panels
        allIcons.forEach(cardIcon -> {
            CardIconCategory category = cardIcon.getIconType().getCategory();
            // debug panel must take all icons (position depends on render settings)
            if (iconsDebugPanel != null) {
                newIcons.get(iconsDebugPanel).add(cardIcon);
            }

            // playable panel (bottom left corner)
            if (iconsPlayablePanel != null && category == CardIconCategory.PLAYABLE_COUNT) {
                newIcons.get(iconsPlayablePanel).add(cardIcon);
            }

            // abilities panel (left side)
            if (iconsAbilitiesPanel != null && category == CardIconCategory.ABILITY) {
                newIcons.get(iconsAbilitiesPanel).add(cardIcon);
            }

            // commander panel (top center)
            if (iconsCommanderPanel != null && category == CardIconCategory.COMMANDER) {
                newIcons.get(iconsCommanderPanel).add(cardIcon);
            }
        });
        this.iconsPanels.forEach(panel -> panel.updateIcons(newIcons.get(panel)));
    }

    @Override
    public void updateArtImage() {
        mainPanel.updateArtImage();
    }

    @Override
    public Image getImage() {
        return mainPanel.getImage();
    }

    @Override
    public void setZone(Zone zone) {
        mainPanel.setZone(zone);
    }

    @Override
    public Zone getZone() {
        return mainPanel.getZone();
    }

    @Override
    public void toggleTransformed() {
        mainPanel.toggleTransformed();
    }

    @Override
    public boolean isTransformed() {
        return mainPanel.isTransformed();
    }

    @Override
    public void showCardTitle() {
        mainPanel.showCardTitle();
    }

    @Override
    public void setSelected(boolean selected) {
        mainPanel.setSelected(selected);
    }

    @Override
    public void setCardContainerRef(Container cardContainer) {
        mainPanel.setCardContainerRef(cardContainer);
    }

    @Override
    public void setTopPanelRef(MageCard mageCard) {
        mainPanel.setTopPanelRef(mageCard);
    }

    @Override
    public MageCard getTopPanelRef() {
        return mainPanel.getTopPanelRef();
    }

    @Override
    public Container getCardContainer() {
        return mainPanel.getCardContainer();
    }

    @Override
    public void setChoosable(boolean isChoosable) {
        mainPanel.setChoosable(isChoosable);
    }

    @Override
    public boolean isChoosable() {
        return mainPanel.isChoosable();
    }

    @Override
    public void setPopupMenu(JPopupMenu popupMenu) {
        mainPanel.setPopupMenu(popupMenu);
    }

    @Override
    public JPopupMenu getPopupMenu() {
        return mainPanel.getPopupMenu();
    }

    @Override
    public void cleanUp() {
        mainPanel.cleanUp();
    }

    @Override
    public int getCardWidth() {
        return mainPanel.getCardWidth();
    }

    @Override
    public int getCardHeight() {
        return mainPanel.getCardHeight();
    }

    @Override
    public MageCardAnimationSettings getAnimationSettings(int offsetX, int offsetY, float cardBoundWidth, float cardBoundHeight) {
        return mainPanel.getAnimationSettings(offsetX, offsetY, cardBoundWidth, cardBoundHeight);
    }

    @Override
    public List<MageCard> getLinks() {
        return mainPanel.getLinks();
    }

    @Override
    public MageCardSpace getOuterSpace() {
        return this.lastOuterSpace;
    }

    @Override
    public MageCardLocation getCardLocation() {
        // TODO: is it support multi layers?
        if (this.getTopPanelRef() == this) {
            //noinspection deprecation (it's ok to call native getLocation here)
            return new MageCardLocation(this.getLocation(), this.getOuterSpace(), this.getBounds());
        } else {
            return super.getCardLocation();
        }
    }

    @Override
    public void setCardLocation(int x, int y) {
        // TODO: is it support multi layers?
        if (this.getTopPanelRef() == this) {
            // see setCardBounds for more coords cals
            //noinspection deprecation - it's ok to use inner setLocation here
            this.setLocation(x - lastOuterSpace.getLeft(), y - lastOuterSpace.getTop());
        } else {
            this.getTopPanelRef().setCardLocation(x, y);
        }
    }

    @Override
    public MageCardLocation getCardLocationOnScreen() {
        // TODO: is it support multi layers?
        if (this.getTopPanelRef() == this) {
            //noinspection deprecation - it's ok to use inner getLocation here
            return new MageCardLocation(this.getLocationOnScreen(), this.getOuterSpace(), this.getBounds());
        } else {
            return super.getCardLocationOnScreen();
        }
    }

    // ADDITIONAL METHODS FROM real components (e.g. set bounds or other things)
    // TODO: move it to interface for require?

    @Override
    public int hashCode() {
        return mainPanel.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        //super.addMouseListener(l);
        mainPanel.addMouseListener(l);
    }

    @Override
    public synchronized void removeMouseListener(MouseListener l) {
        //super.removeMouseListener(l);
        mainPanel.removeMouseListener(l);
    }

    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        //super.addMouseMotionListener(l);
        mainPanel.addMouseMotionListener(l);
    }

    @Override
    public synchronized void removeMouseMotionListener(MouseMotionListener l) {
        //super.removeMouseMotionListener(l);
        mainPanel.removeMouseMotionListener(l);
    }

    @Override
    public synchronized void addMouseWheelListener(MouseWheelListener l) {
        //super.addMouseWheelListener(l);
        mainPanel.addMouseWheelListener(l);
    }

    @Override
    public synchronized void removeMouseWheelListener(MouseWheelListener l) {
        //super.removeMouseWheelListener(l);
        mainPanel.removeMouseWheelListener(l);
    }

    @Override
    public void paint(Graphics g) {
        // inner card panel can decide about transform/scale settings (example: tapped),
        // so the top parent layer must be scaled too

        // real card can be put on any parent layer's position (see outer spaces), so the render logic here:
        // * find real card sizes (without outer spaces)
        // * calc new sizes for the animation/rotation
        // * apply outer spaces to calculated sizes
        // TODO: is it support multi layers?
        int offsetX = this.getOuterSpace().getLeft();
        int offsetY = this.getOuterSpace().getTop();
        int extraWidth = this.getOuterSpace().getWidth();
        int extraHeight = this.getOuterSpace().getHeight();
        Rectangle componentRect = this.getCardLocation().getComponentBounds();
        MageCardAnimationSettings settings = getAnimationSettings(
                offsetX,
                offsetY,
                componentRect.width - extraWidth,
                componentRect.height - extraHeight
        );

        if (!settings.isVisible()) {
            return;
        }

        if (!isValid() || !mainPanel.isValid()) {
            mainPanel.validate();
            super.validate();
        }

        Graphics2D g2d = (Graphics2D) g;
        settings.doTransforms(g2d);

        super.paint(g);
    }

    @Override
    public boolean contains(int x, int y) {
        // TODO: is it work with multi layer?
        // Mouse coords checking to find a child component under the mouse (example: show card hint on mouse over or button click)
        // Swing uses relative coords here (0,0 is component's top left corner)
        // WARNING, when you fail a parent coord check then all childs goes to ignore (example: top layer miss check,
        // then no card panel get it and no card hints on mouse over)
        MageCardLocation needLocation = this.getCardLocation();

        // TODO: added contains support for icons hint
        //   implement idea: use custom "contains" methods for all components structure: from top layer to icon label
        //   another implement idea: save last AffineTransforms from paint method, translate it to current component and check coords (most accurate method)

        // extra size for icons (workaround to fix tooltips over icons)
        Rectangle iconsOffset = new Rectangle(0, 0);
        if (this.iconsPanels.stream().anyMatch(Component::isVisible)) {
            CardIconsPanel samplePanel = this.iconsPanels.stream().findFirst().get();
            iconsOffset.x = -samplePanel.getHalfSize();
            iconsOffset.y = -samplePanel.getHalfSize();
            iconsOffset.height = samplePanel.getHalfSize();
            iconsOffset.width = samplePanel.getHalfSize();
        }

        Rectangle normalRect = new Rectangle(
                needLocation.getCardRelativeX() + iconsOffset.x,
                needLocation.getCardRelativeY() + iconsOffset.y,
                needLocation.getCardWidth() + iconsOffset.width,
                needLocation.getCardHeight() + iconsOffset.height
        );
        Rectangle animatedRect = animateCoords(this, normalRect);

        // debug draw just for color info, real draw will be transformed/animated with card, so you can look at draw rect
        if (DebugUtil.GUI_CARD_DRAW_MOUSE_CONTAINS_BOUNDS) {
            this.mainLayerDebug.setBounds(animatedRect.x, animatedRect.y, animatedRect.width, animatedRect.height);
            if (animatedRect.contains(x, y)) {
                this.mainLayerDebug.setBorder(BorderFactory.createLineBorder(Color.green));
            } else {
                this.mainLayerDebug.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }
        }

        return animatedRect.contains(x, y);
    }

    public static Rectangle animateCoords(MageCard card, Rectangle normalRect) {
        int needX = normalRect.x;
        int needY = normalRect.y;
        int needW = normalRect.width;
        int needH = normalRect.height;
        int cx = needX;
        int cy = needY;
        int cw = needW;
        int ch = needH;

        if (card.isTapped()) {
            // TODO: add rotate support for non 90 angles in the future
            // rotate by 90 only
            // example before:
            // * coord: 50, 150
            // * size: 126 x 176
            // example after:
            // * coord: 50, 150 + 176 - 126
            // * size: 176 x 126
            cx = needX;
            cy = needY + needH - needW;
            cw = needH;
            ch = needW;
        }

        return new Rectangle(cx, cy, cw, ch);
    }
}