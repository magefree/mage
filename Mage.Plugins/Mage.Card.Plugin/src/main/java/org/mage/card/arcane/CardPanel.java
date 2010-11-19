package org.mage.card.arcane;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JRootPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;

import mage.Constants.CardType;
import mage.cards.MagePermanent;
import mage.cards.TextPopup;
import mage.cards.action.ActionCallback;
import mage.utils.CardUtil;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.PermanentView;
import mage.view.StackAbilityView;

import org.apache.log4j.Logger;
import org.mage.card.arcane.ScaledImagePanel.MultipassType;
import org.mage.card.arcane.ScaledImagePanel.ScalingType;
import org.mage.plugins.card.images.ImageCache;

import com.google.common.collect.Sets;


@SuppressWarnings({"unchecked","rawtypes"})
public class CardPanel extends MagePermanent implements MouseListener {
	private static final long serialVersionUID = -3272134219262184410L;
	
	private static final Logger log = Logger.getLogger(CardPanel.class);
	
	static public final double TAPPED_ANGLE = Math.PI / 2;
	static public final float ASPECT_RATIO = 3.5f / 2.5f;
	//static public final float ASPECT_RATIO = 1.0f;

	static public CardPanel dragAnimationPanel;

	public static final Rectangle CARD_SIZE_FULL = new Rectangle(101, 149); 
	
	static private final float ROUNDED_CORNER_SIZE = 0.1f;
	//static private final float SELECTED_BORDER_SIZE = 0.01f;
	static private final float BLACK_BORDER_SIZE = 0.03f;
	static private final int TEXT_GLOW_SIZE = 6;
	static private final float TEXT_GLOW_INTENSITY = 3f;
	static private final float rotCenterToTopCorner = 1.0295630140987000315797369464196f;
	static private final float rotCenterToBottomCorner = 0.7071067811865475244008443621048f;

	public PermanentView gameCard;
	public CardPanel attachedToPanel;
	public List<CardPanel> attachedPanels = new ArrayList();
	public double tappedAngle = 0;
	public ScaledImagePanel imagePanel;
	public ScaledImagePanel overlayPanel;

	private GlowText titleText;
	private GlowText ptText;
	private List<CardPanel> imageLoadListeners = new ArrayList(2);
	private boolean displayEnabled = true;
	private boolean isAnimationPanel;
	private int cardXOffset, cardYOffset, cardWidth, cardHeight;

	private boolean isSelected;
	private boolean showCastingCost;
	private boolean hasImage = false;
	private float alpha = 1.0f;
	
	private ActionCallback callback;
	
	protected Popup popup;
	protected boolean popupShowing;
	protected TextPopup popupText = new TextPopup();

	public CardPanel (PermanentView newGameCard, boolean loadImage, ActionCallback callback) {
		this.gameCard = newGameCard;
		this.callback = callback;
		
		//for container debug (don't remove)
		//setBorder(BorderFactory.createLineBorder(Color.green));

		setBackground(Color.black);
		setOpaque(false);
		
		addMouseListener(this);

		titleText = new GlowText();
		setText(gameCard);
		titleText.setFont(getFont().deriveFont(Font.BOLD, 13f));
		titleText.setForeground(Color.white);
		titleText.setGlow(Color.black, TEXT_GLOW_SIZE, TEXT_GLOW_INTENSITY);
		titleText.setWrap(true);
		add(titleText);

		ptText = new GlowText();
		if (CardUtil.isCreature(gameCard)) {
			ptText.setText(gameCard.getPower() + "/" + gameCard.getToughness());
		} else if (CardUtil.isPlaneswalker(gameCard)) {
			ptText.setText(gameCard.getOriginal().getLoyalty());
		}
		ptText.setFont(getFont().deriveFont(Font.BOLD, 13f));
		ptText.setForeground(Color.white);
		ptText.setGlow(Color.black, TEXT_GLOW_SIZE, TEXT_GLOW_INTENSITY);
		add(ptText);
		
		overlayPanel = new ScaledImagePanel();
		overlayPanel.setBorder(BorderFactory.createLineBorder(Color.white));
		add(overlayPanel);
		overlayPanel.setScaleLarger(true);
		overlayPanel.setScalingType(ScalingType.nearestNeighbor);
		overlayPanel.setScalingBlur(true);
		overlayPanel.setScalingMultiPassType(MultipassType.none);
		
		//TODO: Image sickness = ImageManager.getSicknessImage();
		BufferedImage sickness = null;
		overlayPanel.setImage(sickness, sickness);

		imagePanel = new ScaledImagePanel();
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.white));
		add(imagePanel);
		imagePanel.setScaleLarger(true);
		imagePanel.setScalingType(ScalingType.nearestNeighbor);
		imagePanel.setScalingBlur(true);
		imagePanel.setScalingMultiPassType(MultipassType.none);
		
		String cardType = getType(newGameCard);
		popupText.setText(getText(cardType, newGameCard));
		
		if (!loadImage) return;
		
		Util.threadPool.submit(new Runnable() {
			public void run () {
				try {
					tappedAngle = gameCard.isTapped() ? CardPanel.TAPPED_ANGLE : 0;
					BufferedImage srcImage = ImageCache.getImageOriginal(gameCard);
					srcImage = ImageCache.getNormalSizeImage(srcImage);
					if (srcImage != null) {
						hasImage = true;
						setText(gameCard);
						setImage(srcImage, srcImage);
					} else {
						log.warn("image wasn't found, card=" + gameCard.getName() + ", set=" + gameCard.getExpansionSetCode() + ", cid=" + gameCard.getCardNumber());
					}
				} catch (Exception e) {
					e.printStackTrace();
				} catch (Error err) {
					err.printStackTrace();
				}
			}
		});
	}
	
	private void setText(PermanentView card) {
		if (hasImage) {
			titleText.setText("");
		} else {
			titleText.setText(card.getName());
		}
	}

	private void setImage (Image srcImage, Image srcImageBlurred) {
		synchronized (imagePanel) {
			imagePanel.setImage(srcImage, srcImageBlurred);
			repaint();
			for (CardPanel cardPanel : imageLoadListeners) {
				cardPanel.setImage(srcImage, srcImageBlurred);
				cardPanel.repaint();
			}
			imageLoadListeners.clear();
		}
		layout();
	}

	public void setImage (final CardPanel panel) {
		synchronized (panel.imagePanel) {
			if (panel.imagePanel.hasImage())
				setImage(panel.imagePanel.srcImage, panel.imagePanel.srcImageBlurred);
			else
				panel.imageLoadListeners.add(this);
		}
	}

	public void setScalingType (ScalingType scalingType) {
		imagePanel.setScalingType(scalingType);
	}

	public void setDisplayEnabled (boolean displayEnabled) {
		this.displayEnabled = displayEnabled;
	}

	public boolean isDisplayEnabled () {
		return displayEnabled;
	}

	public void setAnimationPanel (boolean isAnimationPanel) {
		this.isAnimationPanel = isAnimationPanel;
	}

	public void setSelected (boolean isSelected) {
		this.isSelected = isSelected;
		repaint();
	}
	
	public void setAttacking (boolean isAttacking) {
		//TODO:uncomment
		//this.gameCard.setAttacking(isAttacking);
		repaint();
	}
	
	public boolean getSelected() {
		return this.isSelected;
	}
	
	public void setShowCastingCost (boolean showCastingCost) {
		this.showCastingCost = showCastingCost;
	}

	public void paint (Graphics g) {
		if (!displayEnabled) return;
		if (!isValid()) super.validate();
		Graphics2D g2d = (Graphics2D)g;
		if (tappedAngle > 0) {
			g2d = (Graphics2D)g2d.create();
			float edgeOffset = cardWidth / 2f;
			g2d.rotate(tappedAngle, cardXOffset + edgeOffset, cardYOffset + cardHeight - edgeOffset);
		}
		super.paint(g2d);
	}

	protected void paintComponent (Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (alpha != 1.0f) {
			AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
			g2d.setComposite(composite);
		}
		
		//TODO:uncomment
		if (!hasImage /*&& gameCard.getTableID() > 0*/) {
			g2d.setColor(new Color(30,200,200,120));
		} else {
			g2d.setColor(new Color(0,0,0,200));	
		}
		
		//for debug repainting
		//g2d.setColor(new Color(MyRandom.random.nextInt(255),MyRandom.random.nextInt(255),MyRandom.random.nextInt(255),150));
		int cornerSize = Math.max(4, Math.round(cardWidth * ROUNDED_CORNER_SIZE));
		g2d.fillRoundRect(cardXOffset, cardYOffset, cardWidth, cardHeight, cornerSize, cornerSize);
		if (isSelected) {
			//g2d.setColor(new Color(0,250,0,200));
			g2d.setColor(new Color(200,120,40,200));
			g2d.fillRoundRect(cardXOffset+1, cardYOffset+1, cardWidth-2, cardHeight-2, cornerSize, cornerSize);
		}
		
		//TODO:uncomment
		/*
		if (gameCard.isAttacking()) {
			g2d.setColor(new Color(200,10,10,200));
			g2d.fillRoundRect(cardXOffset+1, cardYOffset+1, cardWidth-2, cardHeight-2, cornerSize, cornerSize);
		}*/
		
		/*if (isSelected) {
			g2d.setColor(Color.green);
			int offset = gameCard.isTapped() ? 1 : 0;
			for (int i = 1, n = Math.max(1, Math.round(cardWidth * SELECTED_BORDER_SIZE)); i <= n; i++)
				g2d.drawRoundRect(cardXOffset - i, cardYOffset - i + offset, cardWidth + i * 2 - 1, cardHeight + i * 2 - 1,
					cornerSize, cornerSize);
		}*/
		
		
		//for debugging
		// REMOVEME
		/*
		Point component = getLocation();
		
		int cx = getCardX() + component.x;
		int cy = getCardY() + component.y;
		int cw = getCardWidth();
		int ch = getCardHeight();
		
		g2d.setColor(Color.white);
		g2d.drawRect(getCardX() - component.x, getCardY() - component.y, cw, ch);
		*/
	}

	protected void paintChildren (Graphics g) {
		super.paintChildren(g);

		if (showCastingCost && !isAnimationPanel && cardWidth < 200 && cardWidth > 60) {
			//TODO:uncomment
			/*int width = ManaSymbols.getWidth(gameCard.getManaCost());
			ManaSymbols.draw(g, gameCard.getManaCost(), cardXOffset + 8, cardHeight - 9);
			*/
		}
	}

	public void layout () {
		int borderSize = Math.round(cardWidth * BLACK_BORDER_SIZE);
		imagePanel.setLocation(cardXOffset + borderSize, cardYOffset + borderSize);
		imagePanel.setSize(cardWidth - borderSize * 2, cardHeight - borderSize * 2);
		
		//TODO: uncomment
		/*if (gameCard.hasSickness() && gameCard.isCreature() && gameCard.getTableID() != 0) {
			overlayPanel.setLocation(cardXOffset + borderSize, cardYOffset + borderSize);
			overlayPanel.setSize(cardWidth - borderSize * 2, cardHeight - borderSize * 2);
		} else {
			overlayPanel.setVisible(false);
		}*/
		overlayPanel.setVisible(false);
		
		int fontHeight = Math.round(cardHeight * (27f / 680));
		boolean showText = (!isAnimationPanel && fontHeight < 12);
		titleText.setVisible(showText);
		ptText.setVisible(showText);

		int titleX = Math.round(cardWidth * (20f / 480));
		int titleY = Math.round(cardHeight * (9f / 680));
		titleText.setBounds(cardXOffset + titleX, cardYOffset + titleY, cardWidth - titleX, cardHeight);

		Dimension ptSize = ptText.getPreferredSize();
		ptText.setSize(ptSize.width, ptSize.height);
		int ptX = Math.round(cardWidth * (420f / 480)) - ptSize.width / 2;
		int ptY = Math.round(cardHeight * (675f / 680)) - ptSize.height;
		
		int offsetX = Math.round((CARD_SIZE_FULL.width - cardWidth) / 10.0f);
		
		ptText.setLocation(cardXOffset + ptX - TEXT_GLOW_SIZE / 2 - offsetX, cardYOffset + ptY - TEXT_GLOW_SIZE / 2);

		if (isAnimationPanel || cardWidth < 200)
			imagePanel.setScalingType(ScalingType.nearestNeighbor);
		else
			imagePanel.setScalingType(ScalingType.bilinear);
	}

	public String toString () {
		return gameCard.toString();
	}

	@Override
	public void setCardBounds (int x, int y, int width, int height) {
		cardWidth = width;
		cardHeight = height;
		int rotCenterX = Math.round(width / 2f);
		int rotCenterY = height - rotCenterX;
		int rotCenterToTopCorner = Math.round(width * CardPanel.rotCenterToTopCorner);
		int rotCenterToBottomCorner = Math.round(width * CardPanel.rotCenterToBottomCorner);
		int xOffset = rotCenterX - rotCenterToBottomCorner;
		int yOffset = rotCenterY - rotCenterToTopCorner;
		cardXOffset = -xOffset;
		cardYOffset = -yOffset;
		width = -xOffset + rotCenterX + rotCenterToTopCorner;
		height = -yOffset + rotCenterY + rotCenterToBottomCorner;
		setBounds(x + xOffset, y + yOffset, width, height);
	}

	public void repaint () {
		Rectangle b = getBounds();
		JRootPane rootPane = SwingUtilities.getRootPane(this);
		if (rootPane == null) return;
		Point p = SwingUtilities.convertPoint(getParent(), b.x, b.y, rootPane);
		rootPane.repaint(p.x, p.y, b.width, b.height);
	}

	public int getCardX () {
		return getX() + cardXOffset;
	}

	public int getCardY () {
		return getY() + cardYOffset;
	}

	public int getCardWidth () {
		return cardWidth;
	}

	public int getCardHeight () {
		return cardHeight;
	}

	public Point getCardLocation () {
		Point p = getLocation();
		p.x += cardXOffset;
		p.y += cardYOffset;
		return p;
	}

	public PermanentView getCard() {
		return this.gameCard;
	}
	
	@Override
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public int getCardXOffset() {
		return cardXOffset;
	}

	public int getCardYOffset() {
		return cardYOffset;
	}
	
	public void updateImage() {
		if (!hasImage) {
			Util.threadPool.submit(new Runnable() {
				public void run () {
					//TODO: BufferedImage srcImage = ImageCache.getImageOriginal(gameCard);
					BufferedImage srcImage = null;
					tappedAngle = gameCard.isTapped() ? CardPanel.TAPPED_ANGLE : 0;
					if (srcImage != null) {
						hasImage = true;
						setText(gameCard);
						setImage(srcImage, srcImage);
					}
				}
			});
		}
	}

	@Override
	public List<MagePermanent> getLinks() {
		List<MagePermanent> list = new ArrayList<MagePermanent>();
		if (attachedPanels == null) return list;
		for (MagePermanent p : attachedPanels) {
			list.add(p);
		}
		return list;
	}

	@Override
	public boolean isTapped() {
		return gameCard.isTapped();
	}

	@Override
	public void onBeginAnimation() {
	}

	@Override
	public void onEndAnimation() {
	}

	@Override
	public void update(PermanentView card) {
		if (this.gameCard.isTapped() != card.isTapped()) {
			Animation.tapCardToggle(this, this);
		}
		if (CardUtil.isCreature(card) && CardUtil.isPlaneswalker(card)) {
			ptText.setText(card.getPower() + "/" + card.getToughness() + " (" + card.getLoyalty() + ")");
		} else if (CardUtil.isCreature(card)) {
			ptText.setText(card.getPower() + "/" + card.getToughness());
		} else if (CardUtil.isPlaneswalker(card)) {
			ptText.setText(card.getLoyalty());
		} else {
			ptText.setText("");
		}
		setText(card);
		this.gameCard = card;
		
		String cardType = getType(card);
		popupText.setText(getText(cardType, card));
		
		//TODO: uncomment
		/*if (gameCard.hasSickness() && gameCard.isCreature() && gameCard.getTableID() != 0) {
			overlayPanel.setVisible(true);
		} else {
			overlayPanel.setVisible(false);
		}*/
		
		repaint();
	}

	@Override
	public boolean contains(int x, int y) {
		if (containsThis(x, y, true)) return true;

		/*
		 * if (attachedCount > 0) { for (MWCardImpl card :
		 * mwAttachedCards.keySet()) { if (card.contains(x, y)) return true; } }
		 */

		return false;
	}
	
	public boolean containsThis(int x, int y, boolean root) {
		//log.info("x="+x+", y="+y);
		Point component = getLocation();
		
		//int dy = component.y;
		//if (root) dy = 0;
		
		int cx = getCardX() - component.x;
		int cy = getCardY() - component.y;
		int cw = getCardWidth();
		int ch = getCardHeight();
		if (isTapped()) {
			cy = ch - cw + cx /*+ attachedDy*attachedCount*/;
			ch = cw;
			cw = getCardHeight();
		}
		//int dx = drawIcons ? 19 : 0;
		//int dx = 0;
		
		if (x >= cx && x <= cx + cw && y >= cy && y <= cy + ch) {
			//log.info("!cx="+cx+", cy="+cy+", dx="+cw +", ch="+ch);
			//log.info(getOriginal().getId());
			return true;
		} else {
			//log.info("cx="+cx+", cy="+cy+", dx="+cw +", ch="+ch);	
		}
    	return false;
    }
	
	@Override
	public PermanentView getOriginal() {
		return this.gameCard;
	}

	@Override
	public Image getImage() {
		return ImageCache.getImageOriginal(gameCard);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (!popupShowing) {
			if (popup != null)
				popup.hide();
			PopupFactory factory = PopupFactory.getSharedInstance();
			popup = factory.getPopup(this, popupText, (int) this.getLocationOnScreen().getX() + cardWidth + cardXOffset, (int) this.getLocationOnScreen().getY() + 40);
			popup.show();
			//hack to get popup to resize to fit text
			popup.hide();
			popup = factory.getPopup(this, popupText, (int) this.getLocationOnScreen().getX() + cardWidth + cardXOffset, (int) this.getLocationOnScreen().getY() + 40);
			popup.show();
			popupShowing = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(getMousePosition(true) != null) return;
		if (popup != null) {
			popup.hide();
			popupShowing = false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	protected String getType(CardView card) {
		StringBuilder sbType = new StringBuilder();

		for (String superType: card.getSuperTypes()) {
			sbType.append(superType).append(" ");
		}

		for (mage.Constants.CardType cardType: card.getCardTypes()) {
			sbType.append(cardType.toString()).append(" ");
		}

		if (card.getSubTypes().size() > 0) {
			sbType.append("- ");
			for (String subType: card.getSubTypes()) {
				sbType.append(subType).append(" ");
			}
		}

		return sbType.toString();
	}
	
	protected String getText(String cardType, CardView card) {
		StringBuilder sb = new StringBuilder();
		if (card instanceof StackAbilityView || card instanceof AbilityView) {
			for (String rule: card.getRules()) {
				sb.append("\n").append(rule);
			}
		}
		else {
			sb.append(card.getName());
			if (card.getManaCost().size() > 0) {
				sb.append("\n").append(card.getManaCost());
			}
			sb.append("\n").append(cardType);
			if (card.getColor().hasColor()) {
				sb.append("\n").append(card.getColor().toString());
			}
			if (card.getCardTypes().contains(CardType.CREATURE)) {
				sb.append("\n").append(card.getPower()).append("/").append(card.getToughness());
			}
			else if (card.getCardTypes().contains(CardType.PLANESWALKER)) {
				sb.append("\n").append(card.getLoyalty());
			}
			for (String rule: card.getRules()) {
				sb.append("\n").append(rule);
			}
			if (card.getExpansionSetCode() != null && card.getExpansionSetCode().length() > 0) {
				sb.append("\n").append(card.getCardNumber()).append(" - ");
				//sb.append(Sets.getInstance().get(card.getExpansionSetCode()).getName()).append(" - ");
				sb.append(card.getExpansionSetCode()).append(" - ");
				sb.append(card.getRarity().toString());
			}
		}
//		sb.append("\n").append(card.getId());
		return sb.toString();
	}
}
