package mage.client.cards;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
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
import mage.client.util.TransformedImageCache;
import mage.client.util.gui.HiDpiRenderUtil;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

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
        if (!Plugins.instance.isCardPluginLoaded()) {
            initBounds(rotated);
        }
        setDoubleBuffered(true);
        setOpaque(true);
        this.scrollPane.setOpaque(true);
        this.scrollPane.setVisible(false);
        
        UI.setHTMLEditorKit(text);
        text.setEditable(false);
    }

    private void initBounds(boolean rotated) {
        oldWidth = this.getWidth();
        if (rotated) {
            scrollPane.setBounds(50, 50, 100, 100);
        } else {
            scrollPane.setBounds(this.getWidth()*1000/17777,this.getWidth()*1000/1150,
                                 this.getWidth()*1000/1130,this.getWidth()*1000/2100);
        }
    }

    public void clearUp() {

    }
    
    public void setCard(UUID cardId, EnlargeMode enlargeMode, Image image, List<String> strings, boolean rotate) {
        if (rotate && getWidth() > getHeight()) {
            image = TransformedImageCache.getRotatedImage((BufferedImage) image, Math.toRadians(90.0));
        }

        if (this.cardId == null || enlargeMode != this.enlargeMode || !this.cardId.equals(cardId)) {
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
            StringBuilder displayedText = new StringBuilder();
            for (String textLine: strings) {
                if (textLine != null && !textLine.replace(".", "").trim().isEmpty()) {
                    displayedText.append("<p style='margin: 2px'>").append(textLine).append("</p>");
                }                
            }        
            this.text.setText(ManaSymbols.replaceSymbolsWithHTML(displayedText.toString(), ManaSymbols.Type.DIALOG));
            repaint();
        }
    }

    public UUID getCardId() {
        return cardId;
    }

    public void resetCardId() {
        this.cardId = null;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (bigImage != null) {
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                double scale = Math.min(getWidth() / (double) bigImage.getWidth(null), getHeight() / (double) bigImage.getHeight(null));
                int width = (int) Math.round(bigImage.getWidth(null) * scale);
                int height = (int) Math.round(bigImage.getHeight(null) * scale);
                if (width <= 0 || height <= 0) {
                    return;
                }
                Dimension raster = HiDpiRenderUtil.getRasterSize(g, width, height);
                BufferedImage scaled = TransformedImageCache.getResizedImageExact((BufferedImage) bigImage, raster.width, raster.height);
                HiDpiRenderUtil.configureRasterDraw(g, scaled, width, height);
                g.drawImage(scaled, 0, 0, width, height, this);
            } finally {
                g.dispose();
            }
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
        this.cardId = cardId;
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
        setPreferredSize(getMinimumSize());
        setLayout(null);

        scrollPane.setBackground(new java.awt.Color(220, 220, 220));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        text.setFocusable(false);
        scrollPane.setViewportView(text);

        add(scrollPane);
        scrollPane.setBounds(20, 220, 210, 130);
        scrollPane.setBounds(new Rectangle(CONTENT_MAX_XOFFSET, TEXT_MAX_YOFFSET, TEXT_MAX_WIDTH, TEXT_MAX_HEIGHT));
    }// </editor-fold>//GEN-END:initComponents

    public void setDefaultImage() {
        bigImage = ImageHelper.getImageFromResources("/empty.png");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextPane text;
    // End of variables declaration//GEN-END:variables

}
