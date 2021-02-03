package org.mage.card.arcane;

import mage.cards.*;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.GUISizeHelper;
import mage.client.util.audio.AudioManager;
import mage.constants.*;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.PermanentView;
import mage.view.StackAbilityView;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Main class for drawing Mage card object.
 *
 * WARNING, if you want to catch mouse events then use cardEventSource and related code. You can't use outer listeners.
 *
 * @author arcane, nantuko, noxx, JayDi85
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class CardPanel extends MagePermanent implements ComponentListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static final long serialVersionUID = -3272134219262184410L;

    private static final Logger logger = Logger.getLogger(CardPanel.class);

    public static final double TAPPED_ANGLE = Math.PI / 2;
    public static final double FLIPPED_ANGLE = Math.PI;
    public static final float ASPECT_RATIO = 3.5f / 2.5f;
    public static final int POPUP_X_GAP = 1; // prevent tooltip window from blinking

    public static final Rectangle CARD_SIZE_FULL = new Rectangle(101, 149);

    private static final float ROT_CENTER_TO_TOP_CORNER = 1.0295630140987000315797369464196f;
    private static final float ROT_CENTER_TO_BOTTOM_CORNER = 0.7071067811865475244008443621048f;

    private CardView gameCard;
    private CardView updateCard;

    // for two faced cards
    private CardView temporary;

    private double tappedAngle = 0;
    private double flippedAngle = 0;

    private final List<MageCard> links = new ArrayList<>();

    public final JPanel buttonPanel;
    private JButton dayNightButton;
    private JButton showCopySourceButton;

    private boolean displayEnabled = true;
    private boolean isAnimationPanel;
    private int cardWidth, cardHeight;
    private int symbolWidth;

    private boolean isSelected;
    private boolean isChoosable;
    private boolean showCastingCost;
    private float alpha = 1.0f;

    private ActionCallback callback;

    protected boolean tooltipShowing;
    protected final TextPopup tooltipText;
    protected UUID gameId;
    private TransferData data = new TransferData();

    private boolean isPermanent;
    private boolean hasSickness;
    private Zone zone;

    // mouse clicks
    private int mouseClicksCount = 0;
    private java.util.Timer mouseResetTimer = null;
    static private final int MOUSE_DOUBLE_CLICK_RESET_MS = 200;

    // Permanent and card renders are different (another sizes and positions of panel, tapped, etc -- that's weird)
    // Some card view components support only permanents (BattlefieldPanel), but another support only cards (CardArea)
    // TODO: remove crop/size logic from CardPanel to viewers panels or make compatible for all panels
    // But testing render needs both cards and permanents. That's settings allows to disable different render logic
    private boolean needFullPermanentRender = true;

    public double transformAngle = 1;

    private boolean transformed;
    private boolean animationInProgress = false;

    private Container cardContainer;
    private MageCard topPanel;

    // default offset, e.g. for battlefield
    private int yCardCaptionOffsetPercent = 8; // card caption offset (use for moving card caption view center, below mana icons -- for more good UI)

    // if this is set, it's opened if the user right clicks on the card panel
    private JPopupMenu popupMenu;

    public CardPanel(CardView newGameCard, UUID gameId, final boolean loadImage, ActionCallback callback, final boolean foil, Dimension dimension, boolean needFullPermanentRender) {
        // warning, it can be used under MageLayer so make all rotates or other card manipulation as parent

        // Store away params
        this.setGameCard(newGameCard);
        this.callback = callback;
        this.gameId = gameId;
        this.needFullPermanentRender = needFullPermanentRender;

        /*
        this.setFocusable(true);
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                //LOGGER.warn("focus gained " + getCard().getName());
            }

            public void focusLost(FocusEvent e) {
                //LOGGER.warn("focus lost " + getCard().getName());
            }
        });
         */

        // Gather info about the card (all card maniputations possible with permanents only, also render can be different)
        this.isPermanent = this.getGameCard() instanceof PermanentView && !this.getGameCard().inViewerOnly();
        if (isPermanent) {
            this.hasSickness = ((PermanentView) this.getGameCard()).hasSummoningSickness();
        }

        // Set to requested size
        this.setCardBounds(0, 0, dimension.width, dimension.height);

        // Create button panel for Transform and Show Source (copied cards)
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);
        buttonPanel.setVisible(true);
        add(buttonPanel);

        // Both card rendering implementations have a transform button
        if (this.getGameCard().canTransform()) {
            // Create the day night button
            dayNightButton = new JButton("");
            dayNightButton.setSize(32, 32);
            dayNightButton.setToolTipText("This permanent is a double faced card. To see the back face card, push this button or turn mouse wheel down while hovering with the mouse pointer over the permanent.");
            BufferedImage day = ImageManagerImpl.instance.getDayImage();
            dayNightButton.setIcon(new ImageIcon(day));
            dayNightButton.addActionListener(e -> {
                // if card is being rotated, ignore action performed
                // if card is tapped, no visual transforming is possible (implementation limitation)
                // if card is permanent, it will be rotated by Mage, so manual rotate should be possible
                if (animationInProgress || isTapped() || isPermanent) {
                    return;
                }
                Animation.transformCard(this);
            });

            // Add it
            buttonPanel.add(dayNightButton);
        }

        // Both card rendering implementations have a view copy source button
        if (this.getGameCard() instanceof PermanentView) {
            // Create the show source button
            showCopySourceButton = new JButton("");
            showCopySourceButton.setSize(32, 32);
            showCopySourceButton.setToolTipText("This permanent is copying a target. To see original card, push this button or turn mouse wheel down while hovering with the mouse pointer over the permanent.");
            showCopySourceButton.setVisible(((PermanentView) this.getGameCard()).isCopy());
            showCopySourceButton.setIcon(new ImageIcon(ImageManagerImpl.instance.getCopyInformIconImage()));
            showCopySourceButton.addActionListener(e -> {
                ActionCallback callback1 = Plugins.instance.getActionCallback();
                ((MageActionCallback) callback1).enlargeCard(EnlargeMode.COPY);
            });

            // Add it
            buttonPanel.add(showCopySourceButton);
        }

        // JPanel setup
        setBackground(Color.black);
        setOpaque(false);

        // JPanel event listeners

        // all listeneres to process mouse and another events
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addComponentListener(this);

        // Tooltip for card details hover
        String cardType = getType(newGameCard);
        tooltipText = new TextPopup();
        tooltipText.setText(getText(cardType, newGameCard));

        // Animation setup
        setTappedAngle(isTapped() ? CardPanel.TAPPED_ANGLE : 0);
        setFlippedAngle(isFlipped() ? CardPanel.FLIPPED_ANGLE : 0);
    }

    @Override
    public void doLayout() {
        // Position transform and show source buttons
        buttonPanel.setLocation(0, 0);
        buttonPanel.setSize(cardWidth, cardHeight);
        int x = cardWidth / 20;
        int y = cardHeight / 10;
        if (dayNightButton != null) {
            dayNightButton.setLocation(x, y);
            y += 25;
            y += 5;
        }
        if (showCopySourceButton != null) {
            showCopySourceButton.setLocation(x, y);
        }
    }

    public final void initialDraw() {
        // Kick off
        if (getGameCard().isTransformed()) {
            // this calls updateImage
            toggleTransformed();
        } else {
            updateArtImage();
        }
    }

    public void setIsPermanent(boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    public void cleanUp() {
        if (dayNightButton != null) {
            for (ActionListener al : dayNightButton.getActionListeners()) {
                dayNightButton.removeActionListener(al);
            }
        }
        for (MouseListener ml : this.getMouseListeners()) {
            this.removeMouseListener(ml);
        }
        for (MouseMotionListener ml : this.getMouseMotionListeners()) {
            this.removeMouseMotionListener(ml);
        }
        for (MouseWheelListener ml : this.getMouseWheelListeners()) {
            this.removeMouseWheelListener(ml);
        }
        // this holds reference to ActionCallback forever so set it to null to prevent
        this.callback = null;
        this.data = null;
    }

    // Copy the graphical resources of another CardPanel over to this one,
    // if possible (may not be possible if they have different implementations)
    // Used when cards are moving between zones
    public abstract void transferResources(CardPanel panel);

    @Override
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public Zone getZone() {
        return zone;
    }

    public void setDisplayEnabled(boolean displayEnabled) {
        this.displayEnabled = displayEnabled;
    }

    public boolean isDisplayEnabled() {
        return displayEnabled;
    }

    public void setAnimationPanel(boolean isAnimationPanel) {
        this.isAnimationPanel = isAnimationPanel;
    }

    public boolean isAnimationPanel() {
        return this.isAnimationPanel;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public List<MageCard> getLinks() {
        return links;
    }

    @Override
    public MageCardSpace getOuterSpace() {
        return MageCardSpace.empty;
    }

    @Override
    public void setChoosable(boolean isChoosable) {
        this.isChoosable = isChoosable;
    }

    @Override
    public boolean isChoosable() {
        return this.isChoosable;
    }

    public boolean hasSickness() {
        return this.hasSickness;
    }

    public boolean isPermanent() {
        return this.isPermanent;
    }

    @Override
    public void setCardContainerRef(Container cardContainer) {
        this.cardContainer = cardContainer;
    }

    @Override
    public void setTopPanelRef(MageCard topPanel) {
        this.topPanel = topPanel;
    }

    @Override
    public MageCard getTopPanelRef() {
        return this.topPanel;
    }

    public void setShowCastingCost(boolean showCastingCost) {
        this.showCastingCost = showCastingCost;
    }

    public boolean getShowCastingCost() {
        return this.showCastingCost;
    }

    /**
     * Overridden by different card rendering styles
     *
     * @param g
     */
    protected abstract void paintCard(Graphics2D g);

    @Override
    public void paint(Graphics g) {
        // card rotating implemented by top layer panel
        // TODO: is CardPanel can be used without MageLayer?
        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) (g.create());

        // Deferr to subclasses
        paintCard(g2d);

        // Done, dispose of the context
        g2d.dispose();
    }

    @Override
    public String toString() {
        return getGameCard().toString();
    }

    @Override
    public void setCardBounds(int x, int y, int cardWidth, int cardHeight) {
        if (cardWidth == this.cardWidth && cardHeight == this.cardHeight) {
            // coords changed
            //noinspection deprecation
            setBounds(x, y, getWidth(), getHeight());
        } else {
            // coords + sizes changed
            this.cardWidth = cardWidth;
            this.symbolWidth = cardWidth / 7;
            this.cardHeight = cardHeight;
            // no needs in size settings here - all outer/draw spaces calcs by top parent panel
            //noinspection deprecation
            setBounds(x, y, cardWidth, cardHeight);
        }
    }

    public final int getCardX() {
        return getX() + this.getOuterSpace().getLeft();
    }

    public final int getCardY() {
        return getY() + this.getOuterSpace().getTop();
    }

    @Override
    public final int getCardWidth() {
        return cardWidth;
    }

    @Override
    public final int getCardHeight() {
        return cardHeight;
    }

    public final int getSymbolWidth() {
        return symbolWidth;
    }

    public final CardView getCard() {
        return this.getGameCard();
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public final float getAlpha() {
        return alpha;
    }

    @Override
    public MageCardAnimationSettings getAnimationSettings(int offsetX, int offsetY, float cardBoundWidth, float cardBoundHeight) {
        // card panel can be rotated after tap so send drawning settings to rotate parent panel too
        MageCardAnimationSettings settings = new MageCardAnimationSettings();

        // display
        settings.withVisible(this.displayEnabled);

        // TODO: remove cardXOffset and cardYOffset

        // animate tap
        if (getTappedAngle() + getFlippedAngle() > 0) {
            // Rectangle rotation to keep bottom left corner
            // Algorithm logic:
            // 1. Take the start and the final figure positions (example: vertical and horizontal)
            // 2. Find share figure between start/end positions;
            // 3. Find center of the share figure;
            // 4. Rotate from that center.
            // Rotate center schema: https://user-images.githubusercontent.com/8344157/104398558-6981b500-5568-11eb-9e97-5c16926d481b.png
            double angle = getTappedAngle() + (Math.abs(getFlippedAngle() - FLIPPED_ANGLE) < 0.001 ? 0 : getFlippedAngle());
            float edgeOffset = cardBoundWidth / 2f;
            settings.withRotate(angle, offsetX + edgeOffset, offsetY + cardBoundHeight - edgeOffset);
        }

        // animate transform (shrink/flip animation)
        if (transformAngle < 1) {
            float edgeOffset = cardBoundWidth / 2f;
            settings.withTranslate((offsetX + edgeOffset) * (1 - transformAngle), 0);
            settings.withScale(transformAngle, 1);
        }

        return settings;
    }

    @Override
    public final boolean isTapped() {
        if (isPermanent) {
            return ((PermanentView) getGameCard()).isTapped();
        }
        return false;
    }

    @Override
    public final boolean isFlipped() {
        if (isPermanent) {
            return ((PermanentView) getGameCard()).isFlipped();
        }
        return false;
    }

    @Override
    public final boolean isTransformed() {
        if (isPermanent) {
            if (getGameCard().isTransformed()) {
                return !this.transformed;
            } else {
                return this.transformed;
            }
        } else {
            return this.transformed;
        }
    }

    @Override
    public void onBeginAnimation() {
        animationInProgress = true;
    }

    @Override
    public void onEndAnimation() {
        animationInProgress = false;
    }

    /**
     * Inheriting classes should implement update(CardView card) by using this.
     * However, they should ALSO call repaint() after the superclass call to
     * this function, that can't be done here as the overriders may need to do
     * things both before and after this call before repainting.
     *
     * @param card
     */
    @Override
    public void update(CardView card) {
        if (card == null) {
            return;
        }

        if (transformed && card.equals(this.temporary)) {
            // update can be called from different places (after transform click, after selection change, etc)
            // if card temporary transformed before (by icon click) then do not update full data (as example, after selection changed)
            this.isChoosable = card.isChoosable();
            this.isSelected = card.isSelected();
            return;
        } else {
            this.setUpdateCard(card);
        }

        // Animation update
        if (isPermanent && (card instanceof PermanentView)) {
            boolean needsTapping = isTapped() != ((PermanentView) card).isTapped();
            boolean needsFlipping = isFlipped() != ((PermanentView) card).isFlipped();
            if (needsTapping || needsFlipping) {
                Animation.tapCardToggle(this, needsTapping, needsFlipping);
            }
            if (needsTapping && ((PermanentView) card).isTapped()) {
                AudioManager.playTapPermanent();
            }
            boolean needsTranforming = isTransformed() != card.isTransformed();
            if (needsTranforming) {
                Animation.transformCard(this);
            }
        }

        // Update panel attributes
        this.isChoosable = card.isChoosable();
        this.isSelected = card.isSelected();

        // Update art?
        boolean mustUpdateArt
                = (!getGameCard().getName().equals(card.getName()))
                || (getGameCard().isFaceDown() != card.isFaceDown());

        // Set the new card
        this.setGameCard(card);

        // Update tooltip text
        String cardType = getType(card);
        tooltipText.setText(getText(cardType, card));

        // Update the image
        if (mustUpdateArt) {
            updateArtImage();
        }

        // Update transform circle
        if (card.canTransform()) {
            BufferedImage transformIcon;
            if (isTransformed() || card.isTransformed()) {
                transformIcon = ImageManagerImpl.instance.getNightImage();
            } else {
                transformIcon = ImageManagerImpl.instance.getDayImage();
            }
            if (dayNightButton != null) {
                dayNightButton.setVisible(!isPermanent);
                dayNightButton.setIcon(new ImageIcon(transformIcon));
            }
        }
    }

    @Override
    public CardView getOriginal() {
        return this.getGameCard();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        data.setComponent(this);
        data.setCard(this.getGameCard());
        data.setGameId(this.gameId);

        // popup menu processing
        if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
            callback.popupMenuCard(e, data);
            return;
        }

        // double clicks processing, see https://stackoverflow.com/questions/4051659/identifying-double-click-in-java
        // logic: run timer to reset clicks counter
        mouseClicksCount = e.getClickCount();
        if (mouseClicksCount > 1) {
            // forced to double click
            if (mouseResetTimer != null) {
                mouseResetTimer.cancel();
            }
            callback.mouseClicked(e, data, true);
        } else {
            // can be single or double click, start the reset timer
            if (mouseResetTimer != null) {
                mouseResetTimer.cancel();
            }
            mouseResetTimer = new java.util.Timer("mouseResetTimer", false);
            mouseResetTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mouseClicksCount == 1) {
                        callback.mouseClicked(e, data, false);
                    } else if (mouseClicksCount > 1) {
                        callback.mouseClicked(e, data, true);
                    }
                    mouseClicksCount = 0;
                }
            }, MOUSE_DOUBLE_CLICK_RESET_MS);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (getGameCard().hideInfo()) {
            return;
        }
        if (!tooltipShowing) {
            synchronized (this) {
                if (!tooltipShowing) { // TODO: remove tooltip showing to callback processing code, not here
                    TransferData transferData = getTransferDataForMouseEntered();
                    if (this.isShowing()) {
                        tooltipShowing = true;
                        callback.mouseEntered(e, transferData);
                    }
                }
            }
        }
    }

   @Override
    public void mouseExited(MouseEvent e) {
        if (getGameCard().hideInfo()) {
            return;
        }
        if (tooltipShowing) {
            synchronized (this) {
                if (tooltipShowing) {
                    tooltipShowing = false; // TODO: same, move code for callback processing
                    data.setComponent(this);
                    data.setCard(this.getGameCard());
                    data.setPopupText(tooltipText);
                    callback.mouseExited(e, data);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        data.setComponent(this);
        data.setCard(this.getGameCard());
        data.setGameId(this.gameId);
        callback.mousePressed(e, data);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        data.setComponent(this);
        data.setCard(this.getGameCard());
        data.setGameId(this.gameId);
        callback.mouseReleased(e, data);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        data.setComponent(this);
        data.setCard(this.getGameCard());
        data.setGameId(this.gameId);
        callback.mouseDragged(e, data);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (getGameCard().hideInfo()) {
            return;
        }
        data.setComponent(this);
        data.setCard(this.getGameCard());
        data.setGameId(this.gameId);
        callback.mouseMoved(e, data);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (getGameCard().hideInfo()) {
            return;
        }
        data.setComponent(this);
        data.setCard(this.getGameCard());
        data.setGameId(this.gameId);
        callback.mouseWheelMoved(e, data);
    }

    /**
     * Prepares data to be sent to action callback on client side.
     *
     * @return
     */
    private TransferData getTransferDataForMouseEntered() {
        MageCard cardPanel = this.getTopPanelRef();
        data.setComponent(this);
        data.setCard(this.getGameCard());
        data.setPopupText(tooltipText);
        data.setGameId(this.gameId);
        data.setLocationOnScreen(cardPanel.getCardLocationOnScreen().getCardPoint()); // we need this for popup
        data.setPopupOffsetX(isTapped() ? cardHeight + POPUP_X_GAP : cardWidth + POPUP_X_GAP);
        data.setPopupOffsetY(40);
        return data;
    }

    protected final String getType(CardView card) {
        StringBuilder sbType = new StringBuilder();

        for (SuperType superType : card.getSuperTypes()) {
            sbType.append(superType.toString()).append(' ');
        }

        for (CardType cardType : card.getCardTypes()) {
            sbType.append(cardType.toString()).append(' ');
        }

        if (!card.getSubTypes().isEmpty()) {
            sbType.append("- ");
            for (SubType subType : card.getSubTypes()) {
                sbType.append(subType).append(' ');
            }
        }

        return sbType.toString().trim();
    }

    protected final String getText(String cardType, CardView card) {
        StringBuilder sb = new StringBuilder();
        if (card instanceof StackAbilityView || card instanceof AbilityView) {
            for (String rule : card.getRules()) {
                sb.append('\n').append(rule);
            }
        } else {
            sb.append(card.getName());
            if (!card.getManaCost().isEmpty()) {
                sb.append('\n').append(card.getManaCost());
            }
            sb.append('\n').append(cardType);
            if (card.getColor().hasColor()) {
                sb.append('\n').append(card.getColor().toString());
            }
            if (card.isCreature()) {
                sb.append('\n').append(card.getPower()).append('/').append(card.getToughness());
            } else if (card.isPlanesWalker()) {
                sb.append('\n').append(card.getLoyalty());
            }
            if (card.getRules() == null) {
                card.overrideRules(new ArrayList<>());
            }
            for (String rule : card.getRules()) {
                sb.append('\n').append(rule);
            }
            if (card.getExpansionSetCode() != null && !card.getExpansionSetCode().isEmpty()) {
                sb.append('\n').append(card.getCardNumber()).append(" - ");
                sb.append(card.getExpansionSetCode()).append(" - ");
                sb.append(card.getRarity() == null ? "none" : card.getRarity().toString());
            }
        }
        return sb.toString();
    }

    @Override
    public void update(PermanentView card) {
        this.hasSickness = card.hasSummoningSickness();
        this.showCopySourceButton.setVisible(card.isCopy());

        // must update from top layer (e.g. card icons)
        this.getTopPanelRef().update(card);
    }

    @Override
    public PermanentView getOriginalPermanent() {
        if (isPermanent) {
            return (PermanentView) this.getGameCard();
        }
        throw new IllegalStateException("Is not permanent.");
    }

    public void setTransformed(boolean transformed) {
        this.transformed = transformed;
    }

    private void copySelections(CardView source, CardView dest) {
        if (source != null && dest != null) {
            dest.setSelected(source.isSelected());
            dest.setChoosable(source.isChoosable());
            dest.setPlayableStats(source.getPlayableStats());
        }
    }

    @Override
    public void toggleTransformed() {
        this.transformed = !this.transformed;
        if (transformed) {
            // show night card
            if (dayNightButton != null) { // if transformbable card is copied, button can be null
                BufferedImage night = ImageManagerImpl.instance.getNightImage();
                dayNightButton.setIcon(new ImageIcon(night));
            }
            if (this.getGameCard().getSecondCardFace() == null) {
                logger.error("no second side for card to transform!");
                return;
            }
            if (!isPermanent) { // use only for custom transformation (when pressing day-night button)
                copySelections(this.getGameCard(), this.getGameCard().getSecondCardFace());
                this.setTemporary(this.getGameCard());
                update(this.getGameCard().getSecondCardFace());
            }
        } else {
            // show day card
            if (dayNightButton != null) { // if transformbable card is copied, button can be null
                BufferedImage day = ImageManagerImpl.instance.getDayImage();
                dayNightButton.setIcon(new ImageIcon(day));
            }

            if (!isPermanent) { // use only for custom transformation (when pressing day-night button)
                copySelections(this.getGameCard().getSecondCardFace(), this.getGameCard());
                update(this.getTemporary());
                this.setTemporary(null);
            }
        }

        // switch card names for render
        String temp = this.getGameCard().getAlternateName();
        this.getGameCard().setAlternateName(this.getGameCard().getOriginalName());
        this.getGameCard().setOriginalName(temp);

        updateArtImage();
    }

    @Override
    public Container getCardContainer() {
        return cardContainer;
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        doLayout();
        // this update removes the isChoosable mark from targetCardsInLibrary
        // so only done for permanents because it's needed to redraw counters in different size, if window size was changed
        // no perfect solution yet (maybe also other not wanted effects for PermanentView objects)
        if ((getUpdateCard() instanceof PermanentView)) {
            update(getUpdateCard());
        }
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
    }

    @Override
    public void componentShown(ComponentEvent ce) {
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
    }

    @Override
    public void setCardCaptionTopOffset(int yOffsetPercent) {
        yCardCaptionOffsetPercent = yOffsetPercent;
    }

    public int getCardCaptionTopOffset() {
        return yCardCaptionOffsetPercent;
    }

    @Override
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    public CardView getGameCard() {
        return gameCard;
    }

    public void setGameCard(CardView gameCard) {
        this.gameCard = gameCard;
    }

    public CardView getUpdateCard() {
        return updateCard;
    }

    public void setUpdateCard(CardView updateCard) {
        this.updateCard = updateCard;
    }

    public CardView getTemporary() {
        return temporary;
    }

    public void setTemporary(CardView temporary) {
        this.temporary = temporary;
    }

    public double getTappedAngle() {
        return tappedAngle;
    }

    public void setTappedAngle(double tappedAngle) {
        this.tappedAngle = tappedAngle;
    }

    public double getFlippedAngle() {
        return flippedAngle;
    }

    public void setFlippedAngle(double flippedAngle) {
        this.flippedAngle = flippedAngle;
    }

    @Override
    public boolean contains(int x, int y) {
        // if you need a mouse related features in the tapped state then implement contains here (see MageLayer for info)
        // example: you want a working button
        //return super.contains(x, y);

        // Swing uses relative coords here (0,0 is component's top left corner)
        MageCardLocation needLocation = this.getCardLocation();
        Rectangle normalRect = new Rectangle(
                0,
                0,
                needLocation.getCardWidth(),
                needLocation.getCardHeight()
        );
        Rectangle animatedRect = MageLayer.animateCoords(this, normalRect);
        return animatedRect.contains(x, y);
    }

    @Override
    public Font getFont() {
        Font res = super.getFont();
        if (res == null) {
            // workaround: sometimes the card panels haven't default font
            res = GUISizeHelper.getCardFont();
        }
        return res;
    }
}
