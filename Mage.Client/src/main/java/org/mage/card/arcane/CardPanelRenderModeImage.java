package org.mage.card.arcane;

import mage.MageInt;
import mage.cards.MageCardLocation;
import mage.cards.action.ActionCallback;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.ImageCaches;
import mage.client.util.ImageHelper;
import mage.client.util.SoftValuesLoadingCache;
import mage.components.ImagePanel;
import mage.components.ImagePanelStyle;
import mage.constants.AbilityType;
import mage.constants.SubType;
import mage.util.DebugUtil;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;
import mage.view.StackAbilityView;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.mage.plugins.card.images.ImageCache;
import org.mage.plugins.card.images.ImageCacheData;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Render mode: IMAGE
 *
 * @author arcane, nantuko, noxx, stravant, JayDi85
 */
public class CardPanelRenderModeImage extends CardPanel {

    private static final long serialVersionUID = -3272134219262184411L;

    private static final SoftValuesLoadingCache<Key, BufferedImage> IMAGE_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(CardPanelRenderModeImage::createImage));

    private static final int WIDTH_LIMIT = 90; // card width limit to create smaller counter

    private static final float ROUNDED_CORNER_SIZE = 0.1f;
    private static final float BLACK_BORDER_SIZE = 0.03f;
    private static final float SELECTION_BORDER_SIZE = 0.03f;
    private static final int TEXT_GLOW_SIZE = 6;
    private static final float TEXT_GLOW_INTENSITY = 3f;

    // text min size for image render mode
    private static final int CARD_TITLE_FONT_MIN_SIZE = 13;
    private static final int CARD_PT_FONT_MIN_SIZE = 17;

    public final ScaledImagePanel imagePanel;
    private ImagePanel overlayPanel;

    // triggered/activated/permanent icon
    private JPanel typeIconPanel;
    private JButton typeIconButton;
    private final JPanel ptPanel;

    private JPanel counterPanel;
    private JLabel loyaltyCounterLabel;
    private JLabel plusCounterLabel;
    private JLabel otherCounterLabel;
    private JLabel minusCounterLabel;
    private int loyaltyCounter;
    private int plusCounter;
    private int otherCounter;
    private int minusCounter;
    private int lastCardWidth;

    private final GlowText titleText;
    private final GlowText ptText1;
    private final GlowText ptText2;
    private final GlowText ptText3;
    private final JLabel fullImageText;
    private String fullImagePath = null;

    private boolean hasImage = false;

    private boolean displayTitleAnyway;
    private final boolean displayFullImagePath;

    private int updateArtImageStamp;

    private static class Key {

        final Insets border;
        final int width;
        final int height;
        final int cardWidth;
        final int cardHeight;
        final int cardXOffset;
        final int cardYOffset;
        final boolean hasImage;
        final boolean isSelected;
        final boolean isChoosable;
        final boolean isPlayable;
        final boolean canAttack;
        final boolean canBlock;

        public Key(Insets border, int width, int height, int cardWidth, int cardHeight, int cardXOffset, int cardYOffset, boolean hasImage, boolean isSelected, boolean isChoosable, boolean isPlayable, boolean canAttack, boolean canBlock) {
            this.border = border;
            this.width = width;
            this.height = height;
            this.cardWidth = cardWidth;
            this.cardHeight = cardHeight;
            this.cardXOffset = cardXOffset;
            this.cardYOffset = cardYOffset;
            this.hasImage = hasImage;
            this.isSelected = isSelected;
            this.isChoosable = isChoosable;
            this.isPlayable = isPlayable;
            this.canAttack = canAttack;
            this.canBlock = canBlock;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            Key that = (Key) obj;
            return Objects.equals(this.border, that.border)
                    && this.width == that.width
                    && this.height == that.height
                    && this.cardWidth == that.cardWidth
                    && this.cardHeight == that.cardHeight
                    && this.cardXOffset == that.cardXOffset
                    && this.cardYOffset == that.cardYOffset
                    && this.hasImage == that.hasImage
                    && this.isSelected == that.isSelected
                    && this.isChoosable == that.isChoosable
                    && this.isPlayable == that.isPlayable
                    && this.canAttack == that.canAttack
                    && this.canBlock == that.canBlock;
        }

        @Override
        public int hashCode() {
            return Objects.hash(border, width, height, cardWidth, cardHeight, cardXOffset, cardYOffset, hasImage, isSelected, isChoosable, isPlayable, canAttack, canBlock);
        }
    }

    private static class CardSizes {

        // from bigger to smaller
        Rectangle rectFull;
        Rectangle rectSelection;
        Rectangle rectBorder;
        Rectangle rectCard;

        CardSizes(Insets border, int offsetX, int offsetY, int fullWidth, int fullHeight) {

            int realBorderSizeX = Math.max(1, Math.round(fullWidth * BLACK_BORDER_SIZE));
            int realBorderSizeY = Math.max(1, Math.round(fullHeight * BLACK_BORDER_SIZE));
            int realSelectionSizeX = Math.max(1, Math.round(fullWidth * SELECTION_BORDER_SIZE));
            int realSelectionSizeY = Math.max(1, Math.round(fullHeight * SELECTION_BORDER_SIZE));

            // card full size = select border + black border + real card
            rectFull = new Rectangle(offsetX + border.left, offsetY + border.top, fullWidth - border.left - border.right, fullHeight - border.top - border.bottom);
            rectSelection = new Rectangle(rectFull.x, rectFull.y, rectFull.width, rectFull.height);
            rectBorder = new Rectangle(rectSelection.x + realSelectionSizeX, rectSelection.y + realSelectionSizeY, rectSelection.width - 2 * realSelectionSizeX, rectSelection.height - 2 * realSelectionSizeY);
            rectCard = new Rectangle(rectBorder.x + realBorderSizeX, rectBorder.y + realBorderSizeY, rectBorder.width - 2 * realBorderSizeX, rectBorder.height - 2 * realBorderSizeY);
        }
    }

    static private boolean canShowCardIcons(int cardFullWidth, boolean cardHasImage) {
        // cards without images show icons and text always
        // TODO: apply "card names on card" setting to icon too?
        // TODO: fix card min-max size to hide (compare to settings size, not direct 60 and 200)
        return ((cardFullWidth > 60) && (cardFullWidth < 200)) || (!cardHasImage);
    }

    private static BufferedImage createImage(Key key) {
        // draw background image with selection
        Insets componentBorder = key.border;
        int renderWidth = key.cardWidth;
        int renderHeight = key.cardHeight;
        int cardXOffset = key.cardXOffset;
        int cardYOffset = key.cardYOffset;

        BufferedImage image = GraphicsUtilities.createCompatibleTranslucentImage(renderWidth, renderHeight);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // card full size = select border + black border + real card
        CardSizes sizes = new CardSizes(componentBorder, cardXOffset, cardYOffset, renderWidth, renderHeight);

        // corners for selection and for border
        int cornerSizeSelection = Math.max(4, Math.round(sizes.rectSelection.width * ROUNDED_CORNER_SIZE));
        int cornerSizeBorder = Math.max(4, Math.round(sizes.rectBorder.width * ROUNDED_CORNER_SIZE));

        // DRAW ORDER from big to small: select -> select info -> border -> card

        // draw selection
        if (key.isSelected) {
            // TODO: add themes color support
            g2d.setColor(Color.green);
            g2d.fillRoundRect(sizes.rectSelection.x + 1, sizes.rectSelection.y + 1, sizes.rectSelection.width - 2, sizes.rectSelection.height - 2, cornerSizeSelection, cornerSizeSelection);
        } else if (key.isChoosable) {
            g2d.setColor(new Color(250, 250, 0, 230));
            g2d.fillRoundRect(sizes.rectSelection.x + 1, sizes.rectSelection.y + 1, sizes.rectSelection.width - 2, sizes.rectSelection.height - 2, cornerSizeSelection, cornerSizeSelection);
        } else if (key.isPlayable) {
            g2d.setColor(new Color(153, 102, 204, 200));
            g2d.fillRoundRect(sizes.rectSelection.x, sizes.rectSelection.y, sizes.rectSelection.width, sizes.rectSelection.height, cornerSizeSelection, cornerSizeSelection);
        }

        // draw attack or block border (?inner part of a selection?)
        if (key.canAttack || key.canBlock) {
            g2d.setColor(new Color(255, 50, 50, 230));
            g2d.fillRoundRect(sizes.rectSelection.x + 1, sizes.rectSelection.y + 1, sizes.rectSelection.width - 2, sizes.rectSelection.height - 2, cornerSizeSelection, cornerSizeSelection);
        }

        // draw empty card with border
        if (!key.hasImage) {
            // gray 1 px border
            g2d.setColor(new Color(125, 125, 125, 255));
            g2d.fillRoundRect(sizes.rectBorder.x, sizes.rectBorder.y, sizes.rectBorder.width, sizes.rectBorder.height, cornerSizeBorder, cornerSizeBorder);
            // color plate
            g2d.setColor(new Color(30, 200, 200, 200));
            g2d.fillRoundRect(sizes.rectBorder.x + 1, sizes.rectBorder.y + 1, sizes.rectBorder.width - 2, sizes.rectBorder.height - 2, cornerSizeBorder, cornerSizeBorder);
        }

        // draw real card by component (see imagePanel and other layout's items)
        g2d.dispose();

        return image;
    }

    private static ImageIcon getCounterImageWithAmount(int amount, BufferedImage image, int cardWidth) {
        int factor = cardWidth > WIDTH_LIMIT ? 2 : 1;
        int xOffset = amount > 9 ? 2 : 5;
        int fontSize = factor == 1 ? amount < 10 ? 12 : amount < 100 ? 10 : amount < 1000 ? 7 : 6
                : amount < 10 ? 19 : amount < 100 ? 15 : amount < 1000 ? 12 : amount < 10000 ? 9 : 8;
        BufferedImage newImage;
        if (cardWidth > WIDTH_LIMIT) {
            newImage = ImageManagerImpl.deepCopy(image);
        } else {
            newImage = ImageHelper.getResizedImage(image, 20, 20);
        }
        Graphics graphics = newImage.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial Black", amount > 100 ? Font.PLAIN : Font.BOLD, fontSize));
        graphics.drawString(Integer.toString(amount), xOffset * factor, 11 * factor);
        return new ImageIcon(newImage);
    }

    public CardPanelRenderModeImage(CardView newGameCard, UUID gameId, final boolean loadImage, ActionCallback callback, final boolean foil, Dimension dimension, boolean needFullPermanentRender) {
        // Call to super
        super(newGameCard, gameId, loadImage, callback, foil, dimension, needFullPermanentRender);

        // Counter panel
        if (!newGameCard.isAbility()) {
            // panel to show counters on the card
            counterPanel = new JPanel();
            counterPanel.setLayout(null);
            counterPanel.setOpaque(false);
            add(counterPanel);

            plusCounterLabel = new JLabel("");
            plusCounterLabel.setToolTipText("+1/+1");
            counterPanel.add(plusCounterLabel);

            minusCounterLabel = new JLabel("");
            minusCounterLabel.setToolTipText("-1/-1");
            counterPanel.add(minusCounterLabel);

            loyaltyCounterLabel = new JLabel("");
            loyaltyCounterLabel.setToolTipText("loyalty");
            counterPanel.add(loyaltyCounterLabel);

            otherCounterLabel = new JLabel("");
            counterPanel.add(otherCounterLabel);

            counterPanel.setVisible(false);
        }

        // Ability icon
        if (newGameCard.isAbility()) {
            if (newGameCard.getAbilityType() == AbilityType.TRIGGERED) {
                setTypeIcon(ImageManagerImpl.instance.getTriggeredAbilityImage(), "Triggered Ability");
            } else if (newGameCard.getAbilityType() == AbilityType.ACTIVATED) {
                setTypeIcon(ImageManagerImpl.instance.getActivatedAbilityImage(), "Activated Ability");
            }
        }

        // Token icon
        if (this.getGameCard().isToken()) {
            setTypeIcon(ImageManagerImpl.instance.getTokenIconImage(), "Token Permanent");
        }

        displayTitleAnyway = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_CARD_NAMES, "true").equals("true");
        displayFullImagePath = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_FULL_IMAGE_PATH, "false").equals("true");

        // Title Text
        titleText = new GlowText();
        setTitle(getGameCard());
        titleText.setForeground(Color.white);
        titleText.setGlow(Color.black, TEXT_GLOW_SIZE, TEXT_GLOW_INTENSITY);
        titleText.setWrap(true);
        add(titleText);

        // Full path to image text 
        fullImageText = new JLabel();
        fullImageText.setText(fullImagePath);
        fullImageText.setForeground(Color.BLACK);
        add(fullImageText);

        // PT Text
        ptPanel = new JPanel();
        ptPanel.setOpaque(false);
        ptPanel.setLayout(new BoxLayout(ptPanel, BoxLayout.X_AXIS));
        ptPanel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(Integer.MAX_VALUE, 0)));
        ptText1 = new GlowText();
        ptText2 = new GlowText();
        ptText3 = new GlowText();
        updatePTTexts(getGameCard());
        ptPanel.add(ptText1);
        ptPanel.add(ptText2);
        ptPanel.add(ptText3);
        //
        add(ptPanel);

        // Sickness overlay
        BufferedImage sickness = ImageManagerImpl.instance.getSicknessImage();
        overlayPanel = new ImagePanel(sickness, ImagePanelStyle.SCALED);
        overlayPanel.setOpaque(false);
        add(overlayPanel);

        // Imagel panel
        imagePanel = new ScaledImagePanel();
        if (DebugUtil.GUI_RENDER_IMAGE_DRAW_IMAGE_BORDER) {
            imagePanel.setBorder(BorderFactory.createLineBorder(Color.white));
        }
        add(imagePanel);

        // Do we need to load?
        if (loadImage) {
            initialDraw();
        } else {
            // Nothing to do
        }
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        counterPanel = null;
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int cardWidth;
        int cardHeight;
        int cardXOffset = 0;
        int cardYOffset = 0;

        CardView cardView = getGameCard();

        // workaround to fix a rare NPE error with image loading
        // reason: panel runs image load in another thread and that thread can be completed before top panel init, see updateArtImage
        if (getTopPanelRef() == null) {
            //noinspection deprecation - it's ok for workaround
            cardWidth = this.getWidth();
            //noinspection deprecation - it's ok for workaround
            cardHeight = this.getHeight();
        } else {
            cardWidth = getCardLocation().getCardWidth(); // must use real card sizes to scale real image
            cardHeight = getCardLocation().getCardHeight();
        }

        CardSizes sizes = new CardSizes(getInsets(), cardXOffset, cardYOffset, cardWidth, cardHeight);

        // origin card without selection
        Rectangle realCardSize = sizes.rectBorder;
        imagePanel.setLocation(realCardSize.x, realCardSize.y);
        imagePanel.setSize(realCardSize.width, realCardSize.height);

        if (hasSickness() && cardView.isCreature() && isPermanent()) {
            overlayPanel.setLocation(realCardSize.x, realCardSize.y);
            overlayPanel.setSize(realCardSize.width, realCardSize.height);
        } else {
            overlayPanel.setVisible(false);
        }

        if (typeIconPanel != null) {
            typeIconPanel.setLocation(realCardSize.x, realCardSize.y);
            typeIconPanel.setSize(realCardSize.width, realCardSize.height);
        }

        if (counterPanel != null) {
            counterPanel.setLocation(realCardSize.x, realCardSize.y);
            counterPanel.setSize(realCardSize.width, realCardSize.height);
            int size = cardWidth > WIDTH_LIMIT ? 40 : 20;

            minusCounterLabel.setLocation(counterPanel.getWidth() - size, counterPanel.getHeight() - size * 2);
            minusCounterLabel.setSize(size, size);

            plusCounterLabel.setLocation(5, counterPanel.getHeight() - size * 2);
            plusCounterLabel.setSize(size, size);

            loyaltyCounterLabel.setLocation(counterPanel.getWidth() - size, counterPanel.getHeight() - size);
            loyaltyCounterLabel.setSize(size, size);

            otherCounterLabel.setLocation(5, counterPanel.getHeight() - size);
            otherCounterLabel.setSize(size, size);

        }

        // TITLE

        boolean showText = !isAnimationPanel() && canShowCardIcons(cardWidth, hasImage);
        titleText.setVisible(showText);
        ptText1.setVisible(showText && !ptText1.getText().isEmpty());
        ptText2.setVisible(showText && !ptText2.getText().isEmpty());
        ptText3.setVisible(showText && !ptText3.getText().isEmpty());
        fullImageText.setVisible(fullImagePath != null);

        if (showText) {
            int fontSize = cardHeight / 13; // startup font size (it same size on all zoom levels)
            titleText.setFont(getFont().deriveFont(Font.BOLD, Math.max(CARD_TITLE_FONT_MIN_SIZE, fontSize)));

            // margins from card black border to text, not need? text show up good without margins
            int titleMarginLeft = 0; //Math.round(28f / 672f * cardWidth);
            int titleMarginRight = 0;
            int titleMarginTop = Math.round(getCardCaptionTopOffset() / 100f * cardHeight);//Math.round(28f / 936f * cardHeight);
            int titleMarginBottom = 0;
            titleText.setBounds(
                    imagePanel.getX() + titleMarginLeft,
                    imagePanel.getY() + titleMarginTop,
                    imagePanel.getBounds().width - titleMarginLeft - titleMarginRight,
                    imagePanel.getBounds().height - titleMarginTop - titleMarginBottom
            );

            fullImageText.setFont(getFont().deriveFont(Font.PLAIN, 10));
            fullImageText.setBounds(titleText.getX(), titleText.getY(), titleText.getBounds().width, titleText.getBounds().height);

            // PT (font as title)
            if (cardView.showPT()) {

                // real PT info
                MageInt currentPower;
                MageInt currentToughness;
                if (cardView.getOriginalCard() != null) {
                    // card
                    currentPower = cardView.getOriginalCard().getPower();
                    currentToughness = cardView.getOriginalCard().getToughness();
                } else if (cardView.getOriginalToken() != null) {
                    // token
                    currentPower = cardView.getOriginalToken().getPower();
                    currentToughness = cardView.getOriginalToken().getToughness();
                } else {
                    currentPower = null;
                    currentToughness = null;
                }

                prepareGlowFont(ptText1, Math.max(CARD_PT_FONT_MIN_SIZE, fontSize), currentPower, false);
                prepareGlowFont(ptText2, Math.max(CARD_PT_FONT_MIN_SIZE, fontSize), null, false);
                prepareGlowFont(ptText3, Math.max(CARD_PT_FONT_MIN_SIZE, fontSize), currentToughness, CardRendererUtils.isCardWithDamage(cardView));

                // right bottom corner with margin (sizes from any sample card)
                int ptMarginRight = Math.round(64f / 672f * cardWidth);
                int ptMarginBottom = Math.round(62f / 936f * cardHeight);

                int ptWidth = cardWidth - ptMarginRight * 2;
                int ptHeight = ptText2.getHeight();
                int ptX = cardXOffset + ptMarginRight;
                int ptY = cardYOffset + cardHeight - ptMarginBottom - ptHeight;
                ptPanel.setBounds(ptX, ptY, ptWidth, ptHeight);
            }

            // old version was with TEXT_GLOW_SIZE
            //ptText.setLocation(cardXOffset + ptX - TEXT_GLOW_SIZE / 2 - offsetX, cardYOffset + ptY - TEXT_GLOW_SIZE / 2);
        }
    }

    @Override
    public Image getImage() {
        if (this.hasImage) {
            if (getGameCard().isFaceDown()) {
                return getFaceDownImage().getImage();
            } else {
                return ImageCache.getImageOriginal(getGameCard()).getImage();
            }
        }
        return null;
    }

    @Override
    protected void paintCard(Graphics2D g2d) {
        float alpha = getAlpha();
        if (alpha != 1.0f) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
            g2d.setComposite(composite);
        }

        // draw background (selected/chooseable/playable)
        MageCardLocation cardLocation = getCardLocation();
        g2d.drawImage(
                IMAGE_CACHE.getOrThrow(
                        new Key(getInsets(),
                                cardLocation.getCardWidth(), cardLocation.getCardHeight(),
                                cardLocation.getCardWidth(), cardLocation.getCardHeight(),
                                0,
                                0,
                                hasImage, isSelected(), isChoosable(), getGameCard().isPlayable(), getGameCard().isCanAttack(),
                                getGameCard().isCanBlock())),
                0, 0, cardLocation.getCardWidth(), cardLocation.getCardHeight(), null);
        g2d.dispose();
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        /*
        // debug draw recs

        // full card
        g.setColor(new Color(255, 0, 0));
        g.drawRect(realCard.rectFull.x, realCard.rectFull.y, realCard.rectFull.width, realCard.rectFull.height);

        // real card - image
        g.setColor(new Color(0, 0, 255));
        g.drawRect(imagePanel.getX(), imagePanel.getY(), imagePanel.getBounds().width, imagePanel.getBounds().height);

        // caption
        g.setColor(new Color(0, 255, 255));
        g.drawRect(titleText.getX(), titleText.getY(), titleText.getBounds().width, titleText.getBounds().height);

        // life points
        g.setColor(new Color(120, 0, 120));
        g.drawRect(ptText.getX(), ptText.getY(), ptText.getBounds().width, ptText.getBounds().height);
        //*/

        if (getShowCastingCost() && !isAnimationPanel() && canShowCardIcons(getCardWidth(), hasImage)) {

            int symbolMarginX = 2; // 2 px between icons

            String manaCost = ManaSymbols.getClearManaCost(getGameCard().getManaCostStr());
            int manaWidth = getManaWidth(manaCost, symbolMarginX);

            // right top corner with margin (sizes from any sample card, length from black border to mana icon)
            int manaMarginRight = Math.round(22f / 672f * getCardWidth());
            int manaMarginTop = Math.round(24f / 936f * getCardHeight());

            int cardOffsetX = 0;
            int cardOffsetY = 0;
            int manaX = cardOffsetX + getCardWidth() - manaMarginRight - manaWidth;
            int manaY = cardOffsetY + manaMarginTop;

            ManaSymbols.draw(g, manaCost, manaX, manaY, getSymbolWidth(), ModernCardRenderer.MANA_ICONS_TEXT_COLOR, symbolMarginX);
        }
    }

    @Override
    public void setCardBounds(int x, int y, int cardWidth, int cardHeight) {
        // Call to super
        super.setCardBounds(x, y, cardWidth, cardHeight);

        // Update image
        if (imagePanel != null && imagePanel.getSrcImage() != null) {
            updateArtImage();
        }
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);

        // Update components
        if (alpha == 0) {
            this.ptText1.setVisible(false);
            this.ptText2.setVisible(false);
            this.ptText3.setVisible(false);
            this.titleText.setVisible(false);
        } else if (alpha == 1.0f) {
            this.ptText1.setVisible(true);
            this.ptText2.setVisible(true);
            this.ptText3.setVisible(true);
            this.titleText.setVisible(true);
        }
    }

    @Override
    public void setSelected(boolean isSelected) {
        super.setSelected(isSelected);
        if (isSelected) {
            this.titleText.setGlowColor(Color.green);
        } else {
            this.titleText.setGlowColor(Color.black);
        }
    }

    @Override
    public void showCardTitle() {
        displayTitleAnyway = true;
        setTitle(getGameCard());
    }

    @Override
    public String toString() {
        return getGameCard().toString();
    }

    @Override
    public void transferResources(final CardPanel panelAbstract) {
        if (panelAbstract instanceof CardPanelRenderModeImage) {
            CardPanelRenderModeImage panel = (CardPanelRenderModeImage) panelAbstract;
            synchronized (panel.imagePanel) {
                if (panel.imagePanel.hasImage()) {
                    setImage(panel.imagePanel.getSrcImage());
                }
            }
        }
    }

    @Override
    public void update(CardView card) {
        // Super
        super.update(card);

        // real card to show stores in getGameCard (e.g. after user clicks on night icon -- night card must be rendered)
        updatePTTexts(getGameCard());
        setTitle(getGameCard());

        // Summoning Sickness overlay
        overlayPanel.setVisible(hasSickness() && getGameCard().isCreature() && isPermanent());

        // Update counters panel
        if (counterPanel != null) {
            updateCounters(card);
        }

        // Finally, queue a repaint
        repaint();
    }

    @Override
    public void updateArtImage() {
        setTappedAngle(isTapped() ? CardPanel.TAPPED_ANGLE : 0);
        setFlippedAngle(isFlipped() ? CardPanel.FLIPPED_ANGLE : 0);

        final int stamp = ++updateArtImageStamp;

        Util.threadPool.submit(() -> {
            try {
                final ImageCacheData data;
                if (getGameCard().isFaceDown()) {
                    data = getFaceDownImage();
                } else {
                    data = ImageCache.getImage(getGameCard(), getCardWidth(), getCardHeight());
                }

                // show path on miss image
                if (data.getImage() == null) {
                    setFullPath(data.getPath());
                }

                UI.invokeLater(() -> {
                    if (stamp == updateArtImageStamp) {
                        hasImage = data.getImage() != null;
                        setTitle(getGameCard());
                        setImage(data.getImage());
                    }
                });
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
        });
    }

    private ImageCacheData getFaceDownImage() {
        // TODO: add download default images
        if (isPermanent() && getGameCard() instanceof PermanentView) {
            if (((PermanentView) getGameCard()).isMorphed()) {
                return ImageCache.getMorphImage();
            } else {
                return ImageCache.getManifestImage();
            }
        } else if (this.getGameCard() instanceof StackAbilityView) {
            return ImageCache.getMorphImage();
        } else {
            return ImageCache.getCardbackImage();
        }
    }

    private int getManaWidth(String manaCost, int symbolMarginX) {
        int width = 0;
        manaCost = manaCost.replace("\\", "");
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            tok.nextToken();
            if (width != 0) {
                width += symbolMarginX;
            }
            width += getSymbolWidth();
        }
        return width;
    }

    private void prepareGlowFont(GlowText label, int fontSize, MageInt value, boolean drawAsDamaged) {
        label.setFont(getFont().deriveFont(Font.BOLD, fontSize));
        label.setForeground(CardRendererUtils.getCardTextColor(value, drawAsDamaged, titleText.getForeground(), true));
        Dimension ptSize = label.getPreferredSize();
        label.setSize(ptSize.width, ptSize.height);
    }

    private void setFullPath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
        this.fullImagePath = this.fullImagePath.replaceAll("\\\\", "\\\\<br>");
        this.fullImagePath = this.fullImagePath.replaceAll("/", "/<br>");
        this.fullImagePath = "<html>" + this.fullImagePath + "</html>";
        fullImageText.setText(!displayFullImagePath ? "" : this.fullImagePath);
        doLayout();
    }

    private void setImage(BufferedImage srcImage) {
        synchronized (imagePanel) {
            if (srcImage != null) {
                imagePanel.setImage(srcImage);
            } else {
                imagePanel.clearImage();
            }
            repaint();
        }
        doLayout();
    }

    private void setTitle(CardView card) {
        titleText.setText(!displayTitleAnyway && hasImage ? "" : card.getDisplayName());
    }

    private void setTypeIcon(BufferedImage bufferedImage, String toolTipText) {
        typeIconPanel = new JPanel();
        typeIconPanel.setLayout(null);
        typeIconPanel.setOpaque(false);
        add(typeIconPanel);

        typeIconButton = new JButton("");
        typeIconButton.setLocation(2, 2);
        typeIconButton.setSize(25, 25);

        typeIconPanel.setVisible(true);
        typeIconButton.setIcon(new ImageIcon(bufferedImage));
        if (toolTipText != null) {
            typeIconButton.setToolTipText(toolTipText);
        }
        typeIconPanel.add(typeIconButton);
    }

    private void updateCounters(CardView card) {
        if (card.getCounters() != null && !card.getCounters().isEmpty()) {
            String name = "";
            if (lastCardWidth != getCardWidth()) {
                lastCardWidth = getCardWidth();
                plusCounter = 0;
                minusCounter = 0;
                otherCounter = 0;
                loyaltyCounter = 0;
            }
            plusCounterLabel.setVisible(false);
            minusCounterLabel.setVisible(false);
            loyaltyCounterLabel.setVisible(false);
            otherCounterLabel.setVisible(false);
            for (CounterView counterView : card.getCounters()) {
                if (counterView.getCount() == 0) {
                    continue;
                }
                switch (counterView.getName()) {
                    case "+1/+1":
                        if (counterView.getCount() != plusCounter) {
                            plusCounter = counterView.getCount();
                            plusCounterLabel.setIcon(getCounterImageWithAmount(plusCounter, ImageManagerImpl.instance.getCounterImageGreen(), getCardWidth()));
                        }
                        plusCounterLabel.setVisible(true);
                        break;
                    case "-1/-1":
                        if (counterView.getCount() != minusCounter) {
                            minusCounter = counterView.getCount();
                            minusCounterLabel.setIcon(getCounterImageWithAmount(minusCounter, ImageManagerImpl.instance.getCounterImageRed(), getCardWidth()));
                        }
                        minusCounterLabel.setVisible(true);
                        break;
                    case "loyalty":
                        if (counterView.getCount() != loyaltyCounter) {
                            loyaltyCounter = counterView.getCount();
                            loyaltyCounterLabel.setIcon(getCounterImageWithAmount(loyaltyCounter, ImageManagerImpl.instance.getCounterImageViolet(), getCardWidth()));
                        }
                        loyaltyCounterLabel.setVisible(true);
                        break;
                    default:
                        if (name.isEmpty()) { // only first other counter is shown
                            name = counterView.getName();
                            otherCounter = counterView.getCount();
                            otherCounterLabel.setToolTipText(name);
                            otherCounterLabel.setIcon(getCounterImageWithAmount(otherCounter, ImageManagerImpl.instance.getCounterImageGrey(), getCardWidth()));
                            otherCounterLabel.setVisible(true);
                        }
                }
            }

            counterPanel.setVisible(true);
        } else {
            plusCounterLabel.setVisible(false);
            minusCounterLabel.setVisible(false);
            loyaltyCounterLabel.setVisible(false);
            otherCounterLabel.setVisible(false);
            counterPanel.setVisible(false);
        }
    }

    private void updatePTTexts(CardView card) {
        if (card.isCreature() || card.getSubTypes().contains(SubType.VEHICLE)) {
            ptText1.setText(getGameCard().getPower());
            ptText2.setText("/");
            ptText3.setText(CardRendererUtils.getCardLifeWithDamage(getGameCard()));
        } else if (card.isPlaneswalker()) {
            ptText1.setText("");
            ptText2.setText("");
            ptText3.setText(getGameCard().getLoyalty());
        } else if (card.isBattle()) {
            ptText1.setText("");
            ptText2.setText("");
            ptText3.setText(getGameCard().getDefense());
        } else {
            ptText1.setText("");
            ptText2.setText("");
            ptText3.setText("");
        }

        ptText1.setForeground(Color.white);
        ptText1.setGlow(Color.black, TEXT_GLOW_SIZE, TEXT_GLOW_INTENSITY);

        ptText2.setForeground(Color.white);
        ptText2.setGlow(Color.black, TEXT_GLOW_SIZE, TEXT_GLOW_INTENSITY);

        ptText3.setForeground(Color.white);
        ptText3.setGlow(Color.black, TEXT_GLOW_SIZE, TEXT_GLOW_INTENSITY);
    }
}
