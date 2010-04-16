/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * Card.java
 *
 * Created on 17-Dec-2009, 9:20:50 PM
 */

package mage.client.cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import mage.Constants.CardType;
import mage.client.MageFrame;
import mage.client.remote.Session;
import mage.client.util.ImageHelper;
import mage.view.CardView;
import static mage.client.util.Constants.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Card extends javax.swing.JPanel implements MouseMotionListener, MouseListener, FocusListener, ComponentListener {

	protected static Session session = MageFrame.getSession();

	protected Point p;

	protected UUID gameId;
	protected BigCard bigCard;
	protected CardView card;
	protected Popup popup;

	protected TextPopup popupText = new TextPopup();
	protected BufferedImage background;
	protected BufferedImage image = new BufferedImage(FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
	protected BufferedImage small = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);

    /** Creates new form Card */
    public Card(CardView card, BigCard bigCard, UUID gameId) {
        initComponents();

		this.gameId = gameId;
		this.card = card;
		this.bigCard = bigCard;
		background = ImageHelper.getBackground(card);
		
		StyledDocument doc = text.getStyledDocument();
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "arial");
        Style s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 9);

		addMouseListener(this);
		text.addMouseListener(this);
	    addFocusListener(this);
	    addMouseMotionListener(this);
		text.addMouseMotionListener(this);
		addComponentListener(this);
    }

	public UUID getCardId() {
		return card.getId();
	}

	public void update(CardView card) {
		this.card = card;
		Graphics2D gImage = image.createGraphics();
	    Graphics2D gSmall = small.createGraphics();
		String cardType = getType(card);

		popupText.setText(getText(cardType));

	    gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gImage.setColor(Color.BLACK);
		gImage.drawImage(background, 0, 0, this);

		if (card.getManaCost().size() > 0)
			ImageHelper.DrawCosts(card.getManaCost(), gImage, FRAME_MAX_WIDTH - SYMBOL_MAX_XOFFSET, SYMBOL_MAX_YOFFSET, this);

	    gSmall.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gSmall.setColor(Color.BLACK);
	    gSmall.drawImage(ImageHelper.ScaleImage(image, FRAME_WIDTH, FRAME_HEIGHT), 0, 0, this);

		gImage.setFont(new Font("Arial", Font.PLAIN, NAME_FONT_MAX_SIZE));
		gImage.drawString(card.getName(), CONTENT_MAX_XOFFSET, NAME_MAX_YOFFSET);
		if (card.getCardTypes().contains(CardType.CREATURE)) {
			gImage.drawString(card.getPower() + "/" + card.getToughness(), POWBOX_MAX_LEFT + 10, POWBOX_MAX_TOP + 15);
		}
		else if (card.getCardTypes().contains(CardType.PLANESWALKER)) {
			gImage.drawString(card.getLoyalty(), POWBOX_MAX_LEFT + 10, POWBOX_MAX_TOP + 15);
		}

		if (card.getCardTypes().size() > 0)
			gImage.drawString(cardType, CONTENT_MAX_XOFFSET, TYPE_MAX_YOFFSET);

		gImage.dispose();

		gSmall.setFont(new Font("Arial", Font.PLAIN, NAME_FONT_SIZE));
		gSmall.drawString(card.getName(), CONTENT_XOFFSET, NAME_YOFFSET+1);
		if (card.getCardTypes().contains(CardType.CREATURE)) {
			gSmall.drawString(card.getPower() + "/" + card.getToughness(), POWBOX_LEFT + 5, POWBOX_TOP + 8);
		}
		else if (card.getCardTypes().contains(CardType.PLANESWALKER)) {
			gSmall.drawString(card.getLoyalty(), POWBOX_LEFT + 5, POWBOX_TOP + 8);
		}

		if (card.getCardTypes().size() > 0)
			gSmall.drawString(cardType, CONTENT_XOFFSET, TYPE_YOFFSET);
		drawText();

	    gSmall.dispose();
	}

	protected String getText(String cardType) {
		StringBuilder sb = new StringBuilder();
		sb.append(card.getName());
		if (card.getManaCost().size() > 0) {
			sb.append("\n").append(card.getManaCost());
		}
		sb.append("\n").append(cardType);
		sb.append("\n").append(card.getColor().toString());
		for (String rule: getRules()) {
			sb.append("\n").append(rule);
		}
		if (card.getCardTypes().contains(CardType.CREATURE)) {
			sb.append(card.getPower()).append("/").append(card.getToughness());
		}
		else if (card.getCardTypes().contains(CardType.PLANESWALKER)) {
			sb.append(card.getLoyalty());
		}
		return sb.toString();
	}

	protected void drawText() {
		text.setText("");
		StyledDocument doc = text.getStyledDocument();

		try {
			for (String rule: getRules())
				doc.insertString(doc.getLength(), rule + "\n", doc.getStyle("small"));
		} catch (BadLocationException e) {}

		text.setCaretPosition(0);
	}

	protected List<String> getRules() {
		return card.getRules();
	}

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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextPane();

        setMinimumSize(getPreferredSize());
        setOpaque(false);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLayout(null);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setOpaque(false);

        text.setBorder(null);
        text.setEditable(false);
        text.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        text.setFocusable(false);
        text.setOpaque(false);
        jScrollPane1.setViewportView(text);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 110, 130, 100);
        jScrollPane1.setBounds(new Rectangle(CONTENT_XOFFSET, TEXT_YOFFSET, TEXT_WIDTH, TEXT_HEIGHT));
    }// </editor-fold>//GEN-END:initComponents

	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;
		g2.drawImage(small, 0, 0, this);

		//Add a border, red if card currently has focus
		if (isFocusOwner()) {
			g2.setColor(Color.RED);
		} else {
			g2.setColor(Color.BLACK);
		}
		g2.drawRect(0, 0, FRAME_WIDTH - 1, FRAME_HEIGHT - 1);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.bigCard.setCard(card.getId(), image, getRules());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	    requestFocusInWindow();
		if (gameId != null)
			session.sendPlayerUUID(gameId, card.getId());
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (popup != null)
			popup.hide();
		PopupFactory factory = PopupFactory.getSharedInstance();
		popup = factory.getPopup(this, popupText, (int) this.getLocationOnScreen().getX() + FRAME_WIDTH, (int) this.getLocationOnScreen().getY() + 40);
		popup.show();
		//hack to get popup to resize to fit text
		popup.hide();
		popup = factory.getPopup(this, popupText, (int) this.getLocationOnScreen().getX() + FRAME_WIDTH, (int) this.getLocationOnScreen().getY() + 40);
		popup.show();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (popup != null)
			popup.hide();
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		this.repaint();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (popup != null)
			popup.hide();
		this.repaint();
	}

	protected JScrollPane getText() {
		return jScrollPane1;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTextPane text;
    // End of variables declaration//GEN-END:variables

	@Override
	public void componentResized(ComponentEvent e) { }

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) {
		if (popup != null)
			popup.hide();
	}

}
