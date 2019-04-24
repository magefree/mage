package org.mage.card.arcane;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import mage.cards.action.ActionCallback;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.ImageCaches;
import mage.client.util.ImageHelper;
import mage.components.ImagePanel;
import mage.components.ImagePanelStyle;
import mage.constants.AbilityType;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;
import mage.view.StackAbilityView;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.mage.plugins.card.images.ImageCache;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;
import mage.client.constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Class for drawing the mage card object by using a form based JComponent
 * approach
 *
 * @author arcane, nantuko, noxx, stravant, JayDi85
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CardPanelComponentImpl extends CardPanel {

    private static final long serialVersionUID = -3272134219262184411L;

    private static final Logger LOGGER = Logger.getLogger(CardPanelComponentImpl.class);

    private static final int WIDTH_LIMIT = 90; // card width limit to create smaller counter

    private static final float ROUNDED_CORNER_SIZE = 0.1f;
    private static final float BLACK_BORDER_SIZE = 0.03f;
    private static final float SELECTION_BORDER_SIZE = 0.03f;
    private static final int TEXT_GLOW_SIZE = 6;
    private static final float TEXT_GLOW_INTENSITY = 3f;

    // size to show icons and text (help to see full size card without text)
    private static final int CARD_MIN_SIZE_FOR_ICONS = 60;
    private static final int CARD_MAX_SIZE_FOR_ICONS = 200;

    public final ScaledImagePanel imagePanel;
    public ImagePanel overlayPanel;

    public JPanel iconPanel;
    private JButton typeButton;

    public JPanel counterPanel;
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
    private final GlowText ptText;
    private final JLabel fullImageText;
    private String fullImagePath = null;

    private boolean hasImage = false;

    private boolean displayTitleAnyway;
    private boolean displayFullImagePath;

    private final static Map<Key, BufferedImage> IMAGE_CACHE;

    static class Key {

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

        public Key(int width, int height, int cardWidth, int cardHeight, int cardXOffset, int cardYOffset, boolean hasImage, boolean isSelected, boolean isChoosable, boolean isPlayable, boolean canAttack) {
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
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 19 * hash + this.width;
            hash = 19 * hash + this.height;
            hash = 19 * hash + this.cardWidth;
            hash = 19 * hash + this.cardHeight;
            hash = 19 * hash + this.cardXOffset;
            hash = 19 * hash + this.cardYOffset;
            hash = 19 * hash + (this.hasImage ? 1 : 0);
            hash = 19 * hash + (this.isSelected ? 1 : 0);
            hash = 19 * hash + (this.isChoosable ? 1 : 0);
            hash = 19 * hash + (this.isPlayable ? 1 : 0);
            hash = 19 * hash + (this.canAttack ? 1 : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            if (this.width != other.width) {
                return false;
            }
            if (this.height != other.height) {
                return false;
            }
            if (this.cardWidth != other.cardWidth) {
                return false;
            }
            if (this.cardHeight != other.cardHeight) {
                return false;
            }
            if (this.cardXOffset != other.cardXOffset) {
                return false;
            }
            if (this.cardYOffset != other.cardYOffset) {
                return false;
            }
            if (this.hasImage != other.hasImage) {
                return false;
            }
            if (this.isSelected != other.isSelected) {
                return false;
            }
            if (this.isChoosable != other.isChoosable) {
                return false;
            }
            if (this.isPlayable != other.isPlayable) {
                return false;
            }
            if (this.canAttack != other.canAttack) {
                return false;
            }
            return true;
        }
    }

    static {
        IMAGE_CACHE = ImageCaches.register(new MapMaker().softValues().makeComputingMap((Function<Key, BufferedImage>) key -> createImage(key)));
    }

    static private boolean canShowCardIcons(int cardFullWidth, boolean cardHasImage){
        // cards without images show icons and text always
        // TODO: apply "card names on card" setting to icon too?
        // TODO: fix card min-max size to hide (compare to settings size, not direct 60 and 200)
        return ((cardFullWidth > 60) && (cardFullWidth < 200)) || (!cardHasImage);
    }

    private static class CardSizes{
        Rectangle rectFull;
        Rectangle rectSelection;
        Rectangle rectBorder;
        Rectangle rectCard;

        CardSizes(int offsetX, int offsetY, int fullWidth, int fullHeight){

            int realBorderSizeX = Math.round(fullWidth * BLACK_BORDER_SIZE);
            int realBorderSizeY = Math.round(fullWidth * BLACK_BORDER_SIZE);
            int realSelectionSizeX = Math.round(fullWidth * SELECTION_BORDER_SIZE);
            int realSelectionSizeY = Math.round(fullWidth * SELECTION_BORDER_SIZE);

            // card full size = select border + black border + real card
            rectFull = new Rectangle(offsetX, offsetY, fullWidth, fullHeight);
            rectSelection = new Rectangle(rectFull.x, rectFull.y, rectFull.width, rectFull.height);
            rectBorder = new Rectangle(rectSelection.x + realSelectionSizeX, rectSelection.y + realSelectionSizeY, rectSelection.width - 2 * realSelectionSizeX, rectSelection.height - 2 * realSelectionSizeY);
            rectCard = new Rectangle(rectBorder.x + realBorderSizeX, rectBorder.y + realBorderSizeY, rectBorder.width - 2 * realBorderSizeX, rectBorder.height - 2 * realBorderSizeY);
        }
    }

    public CardPanelComponentImpl(CardView newGameCard, UUID gameId, final boolean loadImage, ActionCallback callback, final boolean foil, Dimension dimension) {
        // Call to super
        super(newGameCard, gameId, loadImage, callback, foil, dimension);

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
        if (this.gameCard.isToken()) {
            setTypeIcon(ImageManagerImpl.instance.getTokenIconImage(), "Token Permanent");
        }

        displayTitleAnyway = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_CARD_NAMES, "true").equals("true");
        displayFullImagePath = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_FULL_IMAGE_PATH, "false").equals("true");

        // Title Text
        titleText = new GlowText();
        setText(gameCard);
//        int fontSize = (int) cardHeight / 11;
//        titleText.setFont(getFont().deriveFont(Font.BOLD, fontSize));
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
        ptText = new GlowText();
        if (gameCard.isCreature()) {
            ptText.setText(gameCard.getPower() + '/' + gameCard.getToughness());
        } else if (gameCard.isPlanesWalker()) {
            ptText.setText(gameCard.getLoyalty());
        }
//        ptText.setFont(getFont().deriveFont(Font.BOLD, fontSize));
        ptText.setForeground(Color.white);
        ptText.setGlow(Color.black, TEXT_GLOW_SIZE, TEXT_GLOW_INTENSITY);
        add(ptText);

        // Sickness overlay
        BufferedImage sickness = ImageManagerImpl.instance.getSicknessImage();
        overlayPanel = new ImagePanel(sickness, ImagePanelStyle.SCALED);
        overlayPanel.setOpaque(false);
        add(overlayPanel);

        // Imagel panel
        imagePanel = new ScaledImagePanel();
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.white));
        add(imagePanel);

        // Do we need to load?
        if (loadImage) {
            initialDraw();
        } else {
            // Nothing to do
        }
    }

    private void setTypeIcon(BufferedImage bufferedImage, String toolTipText) {
        iconPanel = new JPanel();
        iconPanel.setLayout(null);
        iconPanel.setOpaque(false);
        add(iconPanel);

        typeButton = new JButton("");
        typeButton.setLocation(2, 2);
        typeButton.setSize(25, 25);

        iconPanel.setVisible(true);
        typeButton.setIcon(new ImageIcon(bufferedImage));
        if (toolTipText != null) {
            typeButton.setToolTipText(toolTipText);
        }
        iconPanel.add(typeButton);
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        this.counterPanel = null;
    }

    private void setText(CardView card) {
        titleText.setText(!displayTitleAnyway && hasImage ? "" : card.getName());
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

    private void setFullPath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
        this.fullImagePath = this.fullImagePath.replaceAll("\\\\", "\\\\<br>");
        this.fullImagePath = this.fullImagePath.replaceAll("/", "/<br>");
        this.fullImagePath = "<html>" + this.fullImagePath + "</html>";
        fullImageText.setText(!displayFullImagePath ? "" : this.fullImagePath);
        doLayout();
    }

    @Override
    public void transferResources(final CardPanel panelAbstract) {
        if (panelAbstract instanceof CardPanelComponentImpl) {
            CardPanelComponentImpl panel = (CardPanelComponentImpl) panelAbstract;
            synchronized (panel.imagePanel) {
                if (panel.imagePanel.hasImage()) {
                    setImage(panel.imagePanel.getSrcImage());
                }
            }
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
    protected void paintCard(Graphics2D g2d) {
        float alpha = getAlpha();
        if (alpha != 1.0f) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
            g2d.setComposite(composite);
        }

        g2d.drawImage(
                IMAGE_CACHE.get(
                        new Key(getWidth(), getHeight(), getCardWidth(), getCardHeight(), getCardXOffset(), getCardYOffset(),
                                hasImage, isSelected(), isChoosable(), gameCard.isPlayable(), gameCard.isCanAttack())),
                0, 0, null);
        g2d.dispose();
    }

    private static BufferedImage createImage(Key key) {
        int cardWidth = key.cardWidth;
        int cardHeight = key.cardHeight;
        int cardXOffset = key.cardXOffset;
        int cardYOffset = key.cardYOffset;

        BufferedImage image = GraphicsUtilities.createCompatibleTranslucentImage(key.width, key.height);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // card full size = select border + black border + real card
        CardSizes sizes = new CardSizes(cardXOffset, cardYOffset, cardWidth, cardHeight);

        // corners for selection and for border
        int cornerSizeSelection = Math.max(4, Math.round(sizes.rectSelection.width * ROUNDED_CORNER_SIZE));
        int cornerSizeBorder = Math.max(4, Math.round(sizes.rectBorder.width * ROUNDED_CORNER_SIZE));

        // DRAW ORDER from big to small: select -> select info -> border -> card

        // draw selection
        if (key.isSelected) {
            g2d.setColor(Color.green);
            g2d.fillRoundRect(sizes.rectSelection.x + 1, sizes.rectSelection.y + 1, sizes.rectSelection.width - 2, sizes.rectSelection.height - 2, cornerSizeSelection, cornerSizeSelection);
        } else if (key.isChoosable) {
            g2d.setColor(new Color(250, 250, 0, 230));
            g2d.fillRoundRect(sizes.rectSelection.x + 1, sizes.rectSelection.y + 1, sizes.rectSelection.width - 2, sizes.rectSelection.height - 2, cornerSizeSelection, cornerSizeSelection);
        } else if (key.isPlayable) {
            g2d.setColor(new Color(153, 102, 204, 200));
            g2d.fillRoundRect(sizes.rectSelection.x, sizes.rectSelection.y, sizes.rectSelection.width, sizes.rectSelection.height, cornerSizeSelection, cornerSizeSelection);
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

        // draw attack border (inner part of selection)
        if (key.canAttack) {
            g2d.setColor(new Color(0, 0, 255, 230));
            g2d.fillRoundRect(sizes.rectBorder.x + 1, sizes.rectBorder.y + 1, sizes.rectBorder.width - 2, sizes.rectBorder.height - 2, cornerSizeBorder, cornerSizeBorder);
        }

        // draw real card by component (see imagePanel and other layout's items)

        //TODO:uncomment
        /*
         if (gameCard.isAttacking()) {
         g2d.setColor(new Color(200,10,10,200));
         g2d.fillRoundRect(cardXOffset+1, cardYOffset+1, cardWidth-2, cardHeight-2, cornerSize, cornerSize);
         }*/
        g2d.dispose();

        return image;
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        CardSizes realCard = new CardSizes(getCardXOffset(), getCardYOffset(), getCardWidth(), getCardHeight());

        /*
        // draw recs for debug

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

            String manaCost = ManaSymbols.getStringManaCost(gameCard.getManaCost());
            int manaWidth = getManaWidth(manaCost, symbolMarginX);

            // right top corner with margin (sizes from any sample card, length from black border to mana icon)
            int manaMarginRight = Math.round(22f / 672f * getCardWidth());
            int manaMarginTop = Math.round(24f / 936f * getCardHeight());

            int manaX = getCardXOffset() + getCardWidth() - manaMarginRight - manaWidth;
            int manaY = getCardYOffset() + manaMarginTop;

            ManaSymbols.draw(g, manaCost, manaX, manaY, getSymbolWidth(), Color.black, symbolMarginX);
        }
    }

    private int getManaWidth(String manaCost, int symbolMarginX) {
        int width = 0;
        manaCost = manaCost.replace("\\", "");
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            tok.nextToken();
            if(width != 0) {
                width += symbolMarginX;
            }
            width += getSymbolWidth();
        }
        return width;
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int cardWidth = getCardWidth();
        int cardHeight = getCardHeight();
        int cardXOffset = getCardXOffset();
        int cardYOffset = getCardYOffset();

        CardSizes sizes = new CardSizes(cardXOffset, cardYOffset, cardWidth, cardHeight);

        // origin card without selection
        Rectangle realCardSize = sizes.rectBorder;
        imagePanel.setLocation(realCardSize.x, realCardSize.y);
        imagePanel.setSize(realCardSize.width, realCardSize.height);

        if (hasSickness() && gameCard.isCreature() && isPermanent()) {
            overlayPanel.setLocation(realCardSize.x, realCardSize.y);
            overlayPanel.setSize(realCardSize.width, realCardSize.height);
        } else {
            overlayPanel.setVisible(false);
        }

        if (iconPanel != null) {
            iconPanel.setLocation(realCardSize.x, realCardSize.y);
            iconPanel.setSize(realCardSize.width, realCardSize.height);
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

        //old version - text hide on small fonts, why?
        //int fontHeight = Math.round(cardHeight * (26f / 672));
        //boolean showText = (!isAnimationPanel() && fontHeight < 12);

        boolean showText = !isAnimationPanel() && canShowCardIcons(cardWidth, hasImage);
        titleText.setVisible(showText);
        ptText.setVisible(showText);
        fullImageText.setVisible(fullImagePath != null);

        if (showText) {
            int fontSize = cardHeight / 13; // startup font size (it same size on all zoom levels)
            titleText.setFont(getFont().deriveFont(Font.BOLD, fontSize));

            // margins from card black border to text, not need? text show up good without margins
            int titleMarginLeft = 0; //Math.round(28f / 672f * cardWidth);
            int titleMarginRight = 0;
            int titleMarginTop = 0 + Math.round(getCardCaptionTopOffset() / 100f * cardHeight);//Math.round(28f / 936f * cardHeight);
            int titleMarginBottom = 0;
            titleText.setBounds(
                    imagePanel.getX() + titleMarginLeft,
                    imagePanel.getY() + titleMarginTop,
                    imagePanel.getBounds().width - titleMarginLeft - titleMarginRight,
                    imagePanel.getBounds().height - titleMarginTop - titleMarginBottom
            );

            fullImageText.setFont(getFont().deriveFont(Font.PLAIN, 10));
            fullImageText.setBounds(titleText.getX(), titleText.getY(), titleText.getBounds().width, titleText.getBounds().height);

            // life points location (font as title)
            ptText.setFont(getFont().deriveFont(Font.BOLD, fontSize));
            Dimension ptSize = ptText.getPreferredSize();
            ptText.setSize(ptSize.width, ptSize.height);

            // right bottom corner with margin (sizes from any sample card)
            int ptMarginRight = Math.round(64f / 672f * cardWidth);
            int ptMarginBottom = Math.round(62f / 936f * cardHeight);

            int ptX = cardXOffset + cardWidth - ptMarginRight - ptSize.width;
            int ptY = cardYOffset + cardHeight - ptMarginBottom - ptSize.height;
            ptText.setLocation(ptX, ptY);

            // old version was with TEXT_GLOW_SIZE
            //ptText.setLocation(cardXOffset + ptX - TEXT_GLOW_SIZE / 2 - offsetX, cardYOffset + ptY - TEXT_GLOW_SIZE / 2);
        }
    }

    @Override
    public String toString() {
        return gameCard.toString();
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
            this.ptText.setVisible(false);
            this.titleText.setVisible(false);
        } else if (alpha == 1.0f) {
            this.ptText.setVisible(true);
            this.titleText.setVisible(true);
        }
    }

    ///////////////////////////////////////////////////////////
    // Image updating code
    private int updateArtImageStamp;

    @Override
    public void updateArtImage() {
        tappedAngle = isTapped() ? CardPanel.TAPPED_ANGLE : 0;
        flippedAngle = isFlipped() ? CardPanel.FLIPPED_ANGLE : 0;

        //final CardView gameCard = this.gameCard;
        final int stamp = ++updateArtImageStamp;

        Util.threadPool.submit(() -> {
            try {
                final BufferedImage srcImage;
                if (gameCard.isFaceDown()) {
                    srcImage = getFaceDownImage();
                } else if (getCardWidth() > Constants.THUMBNAIL_SIZE_FULL.width) {
                    srcImage = ImageCache.getImage(gameCard, getCardWidth(), getCardHeight());
                } else {
                    srcImage = ImageCache.getThumbnail(gameCard);
                }
                if (srcImage == null) {
                    setFullPath(ImageCache.getFilePath(gameCard, getCardWidth()));
                }
                UI.invokeLater(() -> {
                    if (stamp == updateArtImageStamp) {
                        hasImage = srcImage != null;
                        setText(gameCard);
                        setImage(srcImage);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error err) {
                err.printStackTrace();
            }
        });
    }

    private BufferedImage getFaceDownImage() {
        if (isPermanent()) {
            if (((PermanentView) gameCard).isMorphed()) {
                return ImageCache.getMorphImage();
            } else {
                return ImageCache.getManifestImage();
            }
        } else if (this.gameCard instanceof StackAbilityView) {
            return ImageCache.getMorphImage();
        } else {
            return ImageCache.getCardbackImage();
        }
    }

    @Override
    public void showCardTitle() {
        displayTitleAnyway = true;
        setText(gameCard);
    }

    @Override
    public void update(CardView card) {
        // Super
        super.update(card);

        // Update card text
        if (card.isCreature() && card.isPlanesWalker()) {
            ptText.setText(card.getPower() + '/' + card.getToughness() + " (" + card.getLoyalty() + ')');
        } else if (card.isCreature()) {
            ptText.setText(card.getPower() + '/' + card.getToughness());
        } else if (card.isPlanesWalker()) {
            ptText.setText(card.getLoyalty());
        } else {
            ptText.setText("");
        }
        setText(card);

        // Summoning Sickness overlay
        if (hasSickness() && card.isCreature() && isPermanent()) {
            overlayPanel.setVisible(true);
        } else {
            overlayPanel.setVisible(false);
        }

        // Update counters panel
        if (counterPanel != null) {
            updateCounters(card);
        }

        // Finally, queue a repaint
        repaint();
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

    @Override
    public Image getImage() {
        if (this.hasImage) {
            if (gameCard.isFaceDown()) {
                return getFaceDownImage();
            } else {
                return ImageCache.getImageOriginal(gameCard);
            }
        }
        return null;
    }
}
