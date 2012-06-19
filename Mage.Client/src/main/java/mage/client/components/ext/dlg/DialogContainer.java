package mage.client.components.ext.dlg;

import mage.client.components.ext.MessageDlg;
import mage.client.components.ext.dlg.impl.StackDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author mw, noxx
 */
public class DialogContainer extends JPanel {

    private static int X_OFFSET = 30;
    private static int Y_OFFSET = 30;
    private BufferedImage shadow = null;
    //private DialogManager.MTGDialogs dialogType;
    //private DlgParams params;
    private Color backgroundColor = new Color(0, 255, 255, 60);
    private int alpha = 50;

    private boolean isGradient = false;
    private TexturePaint tp = null;
    private Image gradient = null;
    private BufferedImage b;

    private boolean drawContainer = true;
    private DialogManager.MTGDialogs dialogType;

    public DialogContainer(DialogManager.MTGDialogs dialogType, DlgParams params) {
        setOpaque(false);
        this.dialogType = dialogType;

        setLayout(null);
        drawContainer = true;

        if (dialogType == DialogManager.MTGDialogs.MessageDialog) {
            //backgroundColor = new Color(0, 255, 255, 60);
            if (params.type.equals(MessageDlg.Types.Warning)) {
                backgroundColor = new Color(255, 0, 0, 90);
            } else {
                backgroundColor = new Color(0, 0, 0, 90);
            }
            alpha = 0;
            //MessageDlg dlg = new MessageDlg(params);
            //add(dlg);
            //dlg.setLocation(X_OFFSET + 10, Y_OFFSET);
            //dlg.updateSize(params.rect.width, params.rect.height);
        } else if (dialogType == DialogManager.MTGDialogs.StackDialog) {
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
        }
        /*
        else if (dialogType == DialogManager.MTGDialogs.CombatDialog) {
            backgroundColor = new Color(0, 0, 0, 60);
            alpha = 0;
            CombatDialog dlg = new CombatDialog(params);
            add(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
            dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

        } else if (dialogType == DialogManager.MTGDialogs.ChoiceDialog) {

            //backgroundColor = new Color(200, 200, 172, 120);
            //backgroundColor = new Color(180, 150, 200, 120);
            //backgroundColor = new Color(0, 255, 0, 60);

            //latest:
            backgroundColor = new Color(139, 46, 173, 20);
            //backgroundColor = new Color(139, 46, 173, 0);

            alpha = 0;
            ChoiceDialog dlg = new ChoiceDialog(params);
            add(dlg);
            GameManager.getManager().setCurrentChoiceDlg(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
            dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

        } else if (dialogType == DialogManager.MTGDialogs.GraveDialog) {
            backgroundColor = new Color(20, 20, 20, 120);
            alpha = 0;
            GraveDialog dlg = new GraveDialog(params);
            add(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
            dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

        } else if (dialogType == DialogManager.MTGDialogs.RevealDialog) {
            backgroundColor = new Color(90, 135, 190, 80);
            alpha = 0;
            RevealDialog dlg = new RevealDialog(params);
            add(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
            dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

        } else if (dialogType == DialogManager.MTGDialogs.AssignDamageDialog) {
            backgroundColor = new Color(255, 255, 255, 130);
            alpha = 0;
            AssignDamageDialog dlg = new AssignDamageDialog(params);
            add(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
            dlg.updateSize(params.rect.width - 80, params.rect.height - 80);
        } else if (dialogType == DialogManager.MTGDialogs.ManaChoiceDialog) {
            backgroundColor = new Color(0, 255, 255, 60);
            alpha = 20;
            ManaChoiceDialog dlg = new ManaChoiceDialog(params);
            add(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
            dlg.updateSize(params.rect.width - 80, params.rect.height - 80);

            //isGradient = true;
            gradient = ImageManager.getGradientImage();
            if (gradient != null) {
                b = ImageToBufferedImage.toBufferedImage(gradient);
                b = Transparency.makeImageTranslucent(b, 0.35);
                Rectangle2D tr = new Rectangle2D.Double(0, 0, params.rect.width, params.rect.height);
                //gradient = gradient.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                tp = new TexturePaint(b, tr);
            }
        } else if (dialogType == DialogManager.MTGDialogs.ChooseDeckDialog) {
            MWDeckPanel deckPanel = new MWDeckPanel(params.getDeckList(), params.isAI);
            deckPanel.setVisible(true);
            deckPanel.setBounds(0,0,480,320);
            add(deckPanel);
            drawContainer = false;
        } else if (dialogType == DialogManager.MTGDialogs.ChooseCommonDialog) {
            MWChoosePanel choosePanel = new MWChoosePanel(params.getObjectList(), params.getTitle());
            choosePanel.setVisible(true);
            choosePanel.setBounds(0,0,440,240);
            add(choosePanel);
            drawContainer = false;
        } else if (dialogType == DialogManager.MTGDialogs.AboutDialog) {
            backgroundColor = new Color(255, 255, 255, 120);
            alpha = 0;
            AboutDialog dlg = new AboutDialog();
            add(dlg);
            dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
        }
        */
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
