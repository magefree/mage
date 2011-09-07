package mage.client.components;

import mage.client.util.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;

/**
 * Image button with hover.
 *
 * @author nantuko
 */
public class HoverButton extends JPanel implements MouseListener {

	private Image image;
	private Image hoverImage;
	private Image disabledImage;
	private Image selectedImage;
	protected Image overlayImage;
	private Rectangle imageSize;
	private Rectangle buttonSize;
	private String text;
	private int textOffsetY = 0;
	private int textOffsetX = -1;
	private Dimension overlayImageSize;

	private boolean isHovered = false;
	private boolean isSelected = false;
	private boolean drawSet = false;
	private String set = null;

	private Command observer = null;
	private Command onHover = null;
	private Color textColor = Color.white;

	final static Font textFont = new Font("Arial", Font.PLAIN, 12);
	final static Font textFontMini = new Font("Arial", Font.PLAIN, 11);
	final static Font textSetFontBoldMini = new Font("Arial", Font.BOLD, 12);
	final static Font textSetFontBold = new Font("Arial", Font.BOLD, 14);
	private boolean useMiniFont = false;

	public HoverButton(String text, Image image, Image hover, Image disabled, Rectangle size) {
		this(text, image, hover, null, disabled, size);
	}

	public HoverButton(String text, Image image, Image hover, Image selected, Image disabled, Rectangle size) {
		this.image = image;
		this.hoverImage = hover;
		this.selectedImage = selected;
		this.disabledImage = disabled;
		this.imageSize = size;
		this.text = text;
		setOpaque(false);
		addMouseListener(this);
	}

	public HoverButton(HoverButton button) {
		this(button.text, button.image, button.hoverImage, button.selectedImage, button.disabledImage, button.imageSize);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (isEnabled()) {
			if (isHovered) {
				g.drawImage(hoverImage, 0, 0, imageSize.width, imageSize.height, this);
				if (text != null) {
					if (textColor != null) g2d.setColor(textColor);
					if (useMiniFont) g2d.setFont(textFontMini);
					else g2d.setFont(textFont);
					textOffsetX = calculateOffset(g2d);
					g2d.drawString(text, textOffsetX, textOffsetY);
				}
			} else {
				g.drawImage(image, 0, 0, imageSize.width, imageSize.height, this);
			}
			if (isSelected) {
				if (selectedImage != null) {
					g.drawImage(selectedImage, 0, 0, imageSize.width, imageSize.height, this);
				} else {
					System.err.println("No selectedImage for button.");
				}
			}
		} else {
			g.drawImage(disabledImage, 0, 0, imageSize.width, imageSize.height, this);
		}
		if (overlayImage != null) {
			g.drawImage(overlayImage, (imageSize.width - overlayImageSize.width) / 2, 10, this);
		} else if (set != null) {
			// draw only if it is not current tab
			if (!drawSet) {
				g2d.setFont(textSetFontBoldMini);
				g2d.drawString(set, 5, 25);
			}
		}

		if (drawSet && set != null) {
			g2d.setFont(textSetFontBold);
			int w = (int) (getWidth() / 2.0);
			int h = (int) (getHeight() / 2.0);
			int dy = overlayImage == null ? 15 : 25;
			g2d.translate(w + 5, h + dy);
			g2d.rotate(-Math.PI / 2.0);
			g2d.drawString(set, 0, 0);
		}
	}

	private int calculateOffset(Graphics2D g2d) {
		if (textOffsetX == -1) { // calculate once
			FontRenderContext frc = g2d.getFontRenderContext();
			int textWidth = (int) textFont.getStringBounds(text, frc).getWidth();
			if (textWidth > buttonSize.width) {
				g2d.setFont(textFontMini);
				useMiniFont = true;
				frc = g2d.getFontRenderContext();
				textWidth = (int) textFontMini.getStringBounds(text, frc).getWidth();
			}
			textOffsetX = (int) ((imageSize.width - textWidth) / 2);
		}
		return textOffsetX;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void setOverlayImage(Image image) {
		this.overlayImage = image;
		this.overlayImageSize = new Dimension(image.getWidth(null), image.getHeight(null));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		isHovered = true;
		this.repaint();
		if (onHover != null) {
			onHover.execute();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isHovered = false;
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (isEnabled() && observer != null) {
				observer.execute();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public void setObserver(Command observer) {
		this.observer = observer;
	}

	public void setOnHover(Command onHover) {
		this.onHover = onHover;
	}

	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		this.textOffsetY = r.height - 2;
		this.buttonSize = r;
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.textOffsetY = height - 2;
		this.buttonSize = new Rectangle(x, y, width, height);
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void changeSelected() {
		this.isSelected = !this.isSelected;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public void drawSet() {
		this.drawSet = true;
	}

	public void update(String text, Image image, Image hover, Image selected, Image disabled, Rectangle size) {
		this.image = image;
		this.hoverImage = hover;
		this.selectedImage = selected;
		this.disabledImage = disabled;
		this.imageSize = size;
		this.text = text;
		repaint();
	}
}
