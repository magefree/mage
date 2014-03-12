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
 * BigCard.java
 *
 * Created on Jan 18, 2010, 3:21:33 PM
 */

package mage.client.cards;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import static mage.client.constants.Constants.CONTENT_MAX_XOFFSET;
import static mage.client.constants.Constants.FRAME_MAX_HEIGHT;
import static mage.client.constants.Constants.FRAME_MAX_WIDTH;
import static mage.client.constants.Constants.TEXT_MAX_HEIGHT;
import static mage.client.constants.Constants.TEXT_MAX_WIDTH;
import static mage.client.constants.Constants.TEXT_MAX_YOFFSET;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ImageHelper;
import mage.constants.EnlargeMode;
import org.jdesktop.swingx.JXPanel;

/**
 * Class for displaying big image of the card
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class BigCard extends JComponent {

    protected Image bigImage;
    protected BufferedImage source;
    protected volatile BufferedImage foil;
    protected UUID cardId;
    protected JXPanel panel;
    protected int oldWidth;
    protected boolean foilState;
    protected Thread foilThread;
    protected float hue = 0.005f;
    protected float dh = 0.005f;
    protected EnlargeMode enlargeMode;

    public BigCard() {
        this(false);
    }

    public BigCard(boolean rotated) {
        initComponents();
        if (!Plugins.getInstance().isCardPluginLoaded()) {
            initBounds(rotated);
        }
        setDoubleBuffered(true);
        setOpaque(true);
        this.scrollPane.setOpaque(true);
        this.scrollPane.setVisible(false);
    }

    private void initBounds(boolean rotated) {
        oldWidth = this.getWidth();
        if (rotated) {
            scrollPane.setBounds(50, 50, 100, 100);
        } else {
            scrollPane.setBounds(this.getWidth()*1000/17777,this.getWidth()*1000/1100,
                                 this.getWidth()*1000/1142,this.getWidth()*1000/2539);
        }
    }

    public void clearUp() {

    }
    
    public void setCard(UUID cardId, EnlargeMode enlargeMode, Image image, List<String> strings) {
        if (this.cardId == null || !enlargeMode.equals(this.enlargeMode) || !this.cardId.equals(cardId)) {
            if (this.panel != null) {
                remove(this.panel);
            }
            this.cardId = cardId;
            this.enlargeMode = enlargeMode;
            bigImage = image;
            synchronized (this) {
                source = null;
                hue = 0.000f;
            }
            drawText(strings);
            repaint();
        }
    }

    public UUID getCardId() {
        return cardId;
    }

    public void resetCardId() {
        this.cardId = null;
    }

    private void drawText(java.util.List<String> strings) {
        text.setText("");
        StyledDocument doc = text.getStyledDocument();

        try {
            for (String line : strings) {
                doc.insertString(doc.getLength(), line + "\n", doc.getStyle("regular"));
            }
        } catch (BadLocationException ble) {
        }
        text.setCaretPosition(0);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (bigImage != null) {
            graphics.drawImage(bigImage, 0, 0, this);
        }
        super.paintComponent(graphics);
    }

    public void hideTextComponent() {
        this.scrollPane.setVisible(false);
    }

    public void showTextComponent() {
        if (oldWidth != this.getWidth()) {
            initBounds(false);
        }
        this.scrollPane.setVisible(true);
    }

    public void addJXPanel(UUID cardId, JXPanel jxPanel) {
        bigImage = null;
        synchronized (this) {
            if (this.panel != null) { remove(this.panel); }
            this.panel = jxPanel;
            add(jxPanel);
        }
        this.repaint();
    }


    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        text = new javax.swing.JTextPane();

        setFocusable(false);
        setMinimumSize(new Dimension(FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT));
        setName("bigCardPanel"); // NOI18N
        setOpaque(false);
        setPreferredSize(getMinimumSize());
        setLayout(null);

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);

        text.setEditable(false);
        text.setFocusable(false);
        text.setOpaque(false);
        scrollPane.setViewportView(text);

        add(scrollPane);
        scrollPane.setBounds(20, 230, 210, 120);
        scrollPane.setBounds(new Rectangle(CONTENT_MAX_XOFFSET, TEXT_MAX_YOFFSET, TEXT_MAX_WIDTH, TEXT_MAX_HEIGHT));
    }// </editor-fold>//GEN-END:initComponents

    public void setDefaultImage() {
        bigImage = ImageHelper.getImageFromResources("/empty.png");
        bigImage = ImageHelper.getResizedImage((BufferedImage) bigImage, getWidth(), getHeight());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextPane text;
    // End of variables declaration//GEN-END:variables

}
