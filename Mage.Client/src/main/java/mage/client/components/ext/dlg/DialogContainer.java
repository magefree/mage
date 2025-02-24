package mage.client.components.ext.dlg;

import mage.client.components.ext.MessageDialogType;
import mage.client.components.ext.dlg.impl.ChoiceDialog;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

/**
 * Game GUI: part of the old dialog system
 *
 * @author mw, noxx, JayDi85
 */
public class DialogContainer extends JPanel {

    //private static final Logger logger = Logger.getLogger(DialogContainer.class);
    private static final int X_OFFSET = 30;
    private static final int Y_OFFSET = 30;
    private final BufferedImage shadow = null;
    private Color backgroundColor = new Color(0, 255, 255, 60);
    private int alpha = 50;

    private static final boolean isGradient = false;
    private final TexturePaint tp = null;
    private final Image gradient = null;
    private BufferedImage b;

    private boolean drawContainer = true;
    private final DialogManager.MTGDialogs dialogType;

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public DialogContainer(DialogManager.MTGDialogs dialogType, DlgParams params) {
        setOpaque(false);
        this.dialogType = dialogType;

        setLayout(null);
        drawContainer = true;

        if(dialogType.getDialogClass() != null) {
            try {
                MTGDialog dialog = dialogType.getDialogClass().getDeclaredConstructor().newInstance();
                backgroundColor = dialog.initialize(this, params, X_OFFSET, Y_OFFSET);
            } catch(NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
//                logger.error("Error while creating DialogClass instance.");
            }
        }
    }

    public void cleanUp() {
        for (Component component : this.getComponents()) {
            if (component instanceof ChoiceDialog) {
                ((ChoiceDialog) component).cleanUp();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (!drawContainer) {
            return;
        }

        int x = X_OFFSET;
        int y = Y_OFFSET;
        int w = getWidth() - 2 * X_OFFSET;
        int h = getHeight() - 2 * Y_OFFSET;
        int arc = 30;

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (shadow != null) {
                int xOffset = (shadow.getWidth() - w) / 2;
                int yOffset = (shadow.getHeight() - h) / 2;
                g2.drawImage(shadow, x - xOffset, y - yOffset, null);
            }

            // ////////////////////////////////////////////////////////////////
            // fill content

            /**
             * Add white translucent substrate
             */
            if (alpha != 0) {
                g2.setColor(new Color(255, 255, 255, alpha));
                g2.fillRoundRect(x, y, w, h, arc, arc);
            }

            if (!isGradient) {
                g2.setColor(backgroundColor);
                g2.fillRoundRect(x, y, w, h, arc, arc);
            } else {
                RoundRectangle2D r = new RoundRectangle2D.Float(x, y, w, h, arc, arc);
                g2.setPaint(tp);
                g2.fill(r);
            }
            // ////////////////////////////////////////////////////////////////

            // ////////////////////////////////////////////////////////////////
            // draw border
            g2.setStroke(new BasicStroke(3f));
            g2.setColor(Color.BLACK);
            //g2.setColor(Color.GRAY);
            g2.drawRoundRect(x, y, w, h, arc, arc);
            // ////////////////////////////////////////////////////////////////
        } finally {
            g2.dispose();
        }
    }

    public void showDialog(boolean bShow) {
        setVisible(bShow);
    }

    public DialogManager.MTGDialogs getType() {
        return this.dialogType;
    }

    /**
     * Default UID.
     */
    private static final long serialVersionUID = 1L;
}
