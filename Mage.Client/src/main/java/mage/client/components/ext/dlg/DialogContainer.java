package mage.client.components.ext.dlg;

import mage.client.components.ext.MessageDialogType;
import mage.client.components.ext.dlg.impl.ChoiceDialog;
import mage.client.components.ext.dlg.impl.StackDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author mw, noxx
 */
public class DialogContainer extends JPanel {

    private static final int X_OFFSET = 30;
    private static final int Y_OFFSET = 30;
    private final BufferedImage shadow = null;
    //private DialogManager.MTGDialogs dialogType;
    //private DlgParams params;
    private Color backgroundColor = new Color(0, 255, 255, 60);
    private int alpha = 50;

    private static final boolean isGradient = false;
    private final TexturePaint tp = null;
    private final Image gradient = null;
    private BufferedImage b;

    private boolean drawContainer = true;
    private final DialogManager.MTGDialogs dialogType;

    public DialogContainer(DialogManager.MTGDialogs dialogType, DlgParams params) {
        setOpaque(false);
        this.dialogType = dialogType;

        setLayout(null);
        drawContainer = true;

        switch (dialogType) {
            case MESSAGE:
                //backgroundColor = new Color(0, 255, 255, 60);
                if (params.type == MessageDialogType.WARNING) {
                    backgroundColor = new Color(255, 0, 0, 90);
                } else {
                    backgroundColor = new Color(0, 0, 0, 90);
                }
                alpha = 0;
                //MessageDlg dlg = new MessageDlg(params);
                //add(dlg);
                //dlg.setLocation(X_OFFSET + 10, Y_OFFSET);
                //dlg.updateSize(params.rect.width, params.rect.height);
                break;
            case STACK: {
                //backgroundColor = new Color(0, 255, 255, 60);
                backgroundColor = new Color(0, 0, 0, 50);
                alpha = 0;
                StackDialog dlg = new StackDialog(params);
                add(dlg);
                dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
                //int width = Math.min(params.rect.width - 80, 600);
                int width = params.rect.width;
                int height = params.rect.height - 80;
                dlg.updateSize(width, height);
                break;
            }
        /*
        else if (dialogType == DialogManager.MTGDialogs.COMBAT) {
            backgroundColor = new Color(0, 0, 0, 60);
            alpha = 0;
            COMBAT dlg = new COMBAT(params);
            add(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
            dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

        }*/
            case CHOICE: {

                //backgroundColor = new Color(200, 200, 172, 120);
                //backgroundColor = new Color(180, 150, 200, 120);
                //backgroundColor = new Color(0, 255, 0, 60);

                //backgroundColor = new Color(139, 46, 173, 20);
                backgroundColor = new Color(0, 0, 0, 110);
                //backgroundColor = new Color(139, 46, 173, 0);

                alpha = 0;
                ChoiceDialog dlg = new ChoiceDialog(params, "Choose");
                add(dlg);
                //GameManager.getManager().setCurrentChoiceDlg(dlg);
                dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
                dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

                break;
            }
            case GRAVEYARD: {

                backgroundColor = new Color(0, 0, 0, 110);

                alpha = 0;
                ChoiceDialog dlg = new ChoiceDialog(params, "Graveyard");
                add(dlg);
                dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
                dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

                break;
            }
            case EXILE: {

                backgroundColor = new Color(250, 250, 250, 50);

                alpha = 0;
                ChoiceDialog dlg = new ChoiceDialog(params, "Exile");
                add(dlg);
                dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
                dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

                break;
            }
            case EMBLEMS: {

                backgroundColor = new Color(0, 0, 50, 110);

                alpha = 0;
                ChoiceDialog dlg = new ChoiceDialog(params, "Command Zone (Commander, Emblems and Planes)");
                add(dlg);
                dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
                dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

                break;
            }
        }
    }

    public void cleanUp() {
        for (Component component:this.getComponents()) {
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

        g2.dispose();
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
