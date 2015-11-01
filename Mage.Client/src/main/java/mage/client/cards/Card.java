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
import java.awt.Image;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import mage.cards.CardDimensions;
import mage.cards.MagePermanent;
import mage.cards.Sets;
import mage.cards.TextPopup;
import mage.cards.action.ActionCallback;
import mage.client.MageFrame;
import static mage.client.constants.Constants.CONTENT_MAX_XOFFSET;
import static mage.client.constants.Constants.FRAME_MAX_HEIGHT;
import static mage.client.constants.Constants.FRAME_MAX_WIDTH;
import static mage.client.constants.Constants.NAME_FONT_MAX_SIZE;
import static mage.client.constants.Constants.NAME_MAX_YOFFSET;
import static mage.client.constants.Constants.POWBOX_TEXT_MAX_LEFT;
import static mage.client.constants.Constants.POWBOX_TEXT_MAX_TOP;
import static mage.client.constants.Constants.SYMBOL_MAX_XOFFSET;
import static mage.client.constants.Constants.SYMBOL_MAX_YOFFSET;
import static mage.client.constants.Constants.TYPE_MAX_YOFFSET;
import mage.client.game.PlayAreaPanel;
import mage.client.util.Config;
import mage.client.util.DefaultActionCallback;
import mage.client.util.ImageHelper;
import mage.client.util.gui.ArrowBuilder;
import mage.constants.CardType;
import mage.constants.EnlargeMode;
import mage.remote.Session;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;
import mage.view.StackAbilityView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
@SuppressWarnings("serial")
public class Card extends MagePermanent implements MouseMotionListener, MouseListener, FocusListener, ComponentListener {

    protected static Session session = MageFrame.getSession();
    protected static DefaultActionCallback callback = DefaultActionCallback.getInstance();

    protected Point p;
    protected CardDimensions dimension;

    protected final UUID gameId;
    protected final BigCard bigCard;
    protected CardView card;
    protected Popup tooltipPopup;
    protected boolean tooltipShowing;

    protected TextPopup tooltipText = new TextPopup();
    protected BufferedImage background;
    protected BufferedImage image = new BufferedImage(FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
    protected BufferedImage small;
    protected String backgroundName;

    // if this is set, it's opened if the user right clicks on the card panel
    private JPopupMenu popupMenu;

    /**
     * Creates new form Card
     *
     * @param card
     * @param bigCard
     * @param dimension
     * @param gameId
     */
    public Card(CardView card, BigCard bigCard, CardDimensions dimension, UUID gameId) {
        this.dimension = dimension;
        initComponents();

        this.gameId = gameId;
        this.card = card;
        this.bigCard = bigCard;
        small = new BufferedImage(Config.dimensions.frameWidth, Config.dimensions.frameHeight, BufferedImage.TYPE_INT_RGB);
        backgroundName = getBackgroundName();
        background = ImageHelper.getBackground(card, backgroundName);

        StyledDocument doc = text.getStyledDocument();
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "arial");
        Style s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 9);

        //addMouseListener(this);
        //text.addMouseListener(this);
        //addFocusListener(this);
        //addMouseMotionListener(this);
        //text.addMouseMotionListener(this);
        //addComponentListener(this);
    }

    public UUID getCardId() {
        return card.getId();
    }

    @Override
    public void update(PermanentView permanent) {
        this.update((CardView) permanent);
    }

    @Override
    public void update(CardView card) {
        this.card = card;
        Graphics2D gImage = image.createGraphics();
        Graphics2D gSmall = small.createGraphics();
        String cardType = getType(card);
        String testBackgroundName = getBackgroundName();
        if (!testBackgroundName.equals(backgroundName)) {
            backgroundName = testBackgroundName;
            background = ImageHelper.getBackground(card, backgroundName);
        }

        tooltipText.setText(getText(cardType));

        gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gImage.setColor(Color.BLACK);
        gImage.drawImage(background, 0, 0, this);

        if (card.getManaCost().size() > 0) {
            ImageHelper.drawCosts(card.getManaCost(), gImage, FRAME_MAX_WIDTH - SYMBOL_MAX_XOFFSET, SYMBOL_MAX_YOFFSET, this);
        }

        gSmall.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gSmall.setColor(Color.BLACK);
        gSmall.drawImage(ImageHelper.scaleImage(image, Config.dimensions.frameWidth, Config.dimensions.frameHeight), 0, 0, this);

        gImage.setFont(new Font("Arial", Font.PLAIN, NAME_FONT_MAX_SIZE));
        gImage.drawString(card.getName(), CONTENT_MAX_XOFFSET, NAME_MAX_YOFFSET);
        if (card.getCardTypes().contains(CardType.CREATURE)) {
            gImage.drawString(card.getPower() + "/" + card.getToughness(), POWBOX_TEXT_MAX_LEFT, POWBOX_TEXT_MAX_TOP);
        } else if (card.getCardTypes().contains(CardType.PLANESWALKER)) {
            gImage.drawString(card.getLoyalty(), POWBOX_TEXT_MAX_LEFT, POWBOX_TEXT_MAX_TOP);
        }

        if (card.getCardTypes().size() > 0) {
            gImage.drawString(cardType, CONTENT_MAX_XOFFSET, TYPE_MAX_YOFFSET);
        }

        gImage.dispose();

        gSmall.setFont(new Font("Arial", Font.PLAIN, Config.dimensions.nameFontSize));
        gSmall.drawString(card.getName(), Config.dimensions.contentXOffset, Config.dimensions.nameYOffset);
        if (card.getCardTypes().contains(CardType.CREATURE)) {
            gSmall.drawString(card.getPower() + "/" + card.getToughness(), Config.dimensions.powBoxTextLeft, Config.dimensions.powBoxTextTop);
        } else if (card.getCardTypes().contains(CardType.PLANESWALKER)) {
            gSmall.drawString(card.getLoyalty(), Config.dimensions.powBoxTextLeft, Config.dimensions.powBoxTextTop);
        }

        if (card.getCardTypes().size() > 0) {
            gSmall.drawString(cardType, Config.dimensions.contentXOffset, Config.dimensions.typeYOffset);
        }
        drawText();

        gSmall.dispose();
    }

    @Override
    public void updateImage() {
    }

    protected String getText(String cardType) {
        StringBuilder sb = new StringBuilder();
        if (card instanceof StackAbilityView || card instanceof AbilityView) {
            for (String rule : getRules()) {
                sb.append("\n").append(rule);
            }
        } else {
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
            } else if (card.getCardTypes().contains(CardType.PLANESWALKER)) {
                sb.append("\n").append(card.getLoyalty());
            }
            for (String rule : getRules()) {
                sb.append("\n").append(rule);
            }
            if (card.getExpansionSetCode() != null && card.getExpansionSetCode().length() > 0) {
                sb.append("\n").append(card.getCardNumber()).append(" - ");
                sb.append(Sets.getInstance().get(card.getExpansionSetCode()).getName()).append(" - ");
                sb.append(card.getRarity().toString());
            }
        }
//        sb.append("\n").append(card.getId());
        return sb.toString();
    }

    private String getBackgroundName() {
        if (card instanceof StackAbilityView || card instanceof AbilityView) {
            return "effect";
        }
        StringBuilder sb = new StringBuilder();
        if (card.getCardTypes().contains(CardType.LAND)) {
            sb.append("land").append(card.getSuperTypes()).append(card.getSubTypes());
        } else if (card.getCardTypes() != null && (card.getCardTypes().contains(CardType.CREATURE) || card.getCardTypes().contains(CardType.PLANESWALKER))) {
            sb.append("creature");
        }
        sb.append(card.getColor()).append(card.getRarity()).append(card.getExpansionSetCode());
        return sb.toString();
    }

    protected void drawText() {
        text.setText("");
        StyledDocument doc = text.getStyledDocument();

        try {
            for (String rule : getRules()) {
                doc.insertString(doc.getLength(), rule + "\n", doc.getStyle("small"));
            }
        } catch (BadLocationException e) {
        }

        text.setCaretPosition(0);
    }

    protected List<String> getRules() {
        if (card.getCounters() != null) {
            List<String> rules = new ArrayList<>(card.getRules());
            for (CounterView counter : card.getCounters()) {
                rules.add(counter.getCount() + " x " + counter.getName());
            }
            return rules;
        } else {
            return card.getRules();
        }
    }

    protected String getType(CardView card) {
        StringBuilder sbType = new StringBuilder();

        for (String superType : card.getSuperTypes()) {
            sbType.append(superType).append(" ");
        }

        for (CardType cardType : card.getCardTypes()) {
            sbType.append(cardType.toString()).append(" ");
        }

        if (card.getSubTypes().size() > 0) {
            sbType.append("- ");
            for (String subType : card.getSubTypes()) {
                sbType.append(subType).append(" ");
            }
        }

        return sbType.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextPane();

        setMinimumSize(getPreferredSize());
        setOpaque(false);
        setPreferredSize(new Dimension(dimension.frameWidth, dimension.frameHeight));
        setLayout(null);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setOpaque(false);

        text.setBorder(null);
        text.setEditable(false);
        text.setFont(new java.awt.Font("Arial", 0, 9));
        text.setFocusable(false);
        text.setOpaque(false);
        jScrollPane1.setViewportView(text);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 110, 130, 100);
        jScrollPane1.setBounds(new Rectangle(dimension.contentXOffset, dimension.textYOffset, dimension.textWidth, dimension.textHeight));
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
        g2.drawRect(0, 0, Config.dimensions.frameWidth - 1, Config.dimensions.frameHeight - 1);
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        this.bigCard.showTextComponent();
        this.bigCard.setCard(card.getId(), EnlargeMode.NORMAL, image, getRules());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();
        callback.mouseClicked(e, gameId, session, card);
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        if (!tooltipShowing) {
            if (tooltipPopup != null) {
                tooltipPopup.hide();
            }
            PopupFactory factory = PopupFactory.getSharedInstance();
            tooltipPopup = factory.getPopup(this, tooltipText, (int) this.getLocationOnScreen().getX() + Config.dimensions.frameWidth, (int) this.getLocationOnScreen().getY() + 40);
            tooltipPopup.show();
            //hack to get tooltipPopup to resize to fit text
            tooltipPopup.hide();
            tooltipPopup = factory.getPopup(this, tooltipText, (int) this.getLocationOnScreen().getX() + Config.dimensions.frameWidth, (int) this.getLocationOnScreen().getY() + 40);
            tooltipPopup.show();
            tooltipShowing = true;

            // Draw Arrows for targets
            List<UUID> targets = card.getTargets();
            if (targets != null) {
                for (UUID uuid : targets) {
                    PlayAreaPanel playAreaPanel = MageFrame.getGame(gameId).getPlayers().get(uuid);
                    if (playAreaPanel != null) {
                        Point target = playAreaPanel.getLocationOnScreen();
                        Point me = this.getLocationOnScreen();
                        ArrowBuilder.getBuilder().addArrow(gameId, (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() - 40, Color.red, ArrowBuilder.Type.TARGET);
                    } else {
                        for (PlayAreaPanel pa : MageFrame.getGame(gameId).getPlayers().values()) {
                            MagePermanent permanent = pa.getBattlefieldPanel().getPermanents().get(uuid);
                            if (permanent != null) {
                                Point target = permanent.getLocationOnScreen();
                                Point me = this.getLocationOnScreen();
                                ArrowBuilder.getBuilder().addArrow(gameId, (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() + 10, Color.red, ArrowBuilder.Type.TARGET);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        if (getMousePosition(true) != null) {
            return;
        }
        if (tooltipPopup != null) {
            tooltipPopup.hide();
            tooltipShowing = false;
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.TARGET);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.PAIRED);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.SOURCE);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.ENCHANT_PLAYERS);
        }
    }

    @Override
    public void focusGained(FocusEvent arg0) {
        this.repaint();
    }

    @Override
    public void focusLost(FocusEvent arg0) {
        if (tooltipPopup != null) {
            tooltipPopup.hide();
        }
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
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if (tooltipPopup != null) {
            tooltipPopup.hide();
        }
    }

    @Override
    public List<MagePermanent> getLinks() {
        return null;
    }

    @Override
    public boolean isTapped() {
        return false;
    }

    @Override
    public boolean isFlipped() {
        return false;
    }

    @Override
    public void onBeginAnimation() {
    }

    @Override
    public void onEndAnimation() {
    }

    @Override
    public void setAlpha(float transparency) {
    }

    @Override
    public CardView getOriginal() {
        return card;
    }

    @Override
    public void setCardBounds(int x, int y, int width, int height) {
        // do nothing
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void setZone(String zone) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getZone() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateCallback(ActionCallback callback, UUID gameId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PermanentView getOriginalPermanent() {
        return null;
    }

    @Override
    public float getAlpha() {
        return 0;
    }

    @Override
    public void toggleTransformed() {
    }

    @Override
    public boolean isTransformed() {
        return false;
    }

    @Override
    public void showCardTitle() {
    }

    @Override
    public void setSelected(boolean selected) {
    }

    @Override
    public void setCardAreaRef(JPanel cardArea) {
    }

    @Override
    public void setChoosable(boolean isChoosable) {
    }

    @Override
    public void setTextOffset(int yOffset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }
}
