package mage.client.components.ext.dlg;

import mage.client.cards.BigCard;
import mage.client.game.FeedbackPanel;
import mage.view.CardsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.util.UUID;

/**
 * @author mw, noxx
 */
public class DialogManager extends JComponent implements MouseListener,
        MouseMotionListener {

    private static DialogManager dialogManager = null;
    //private static final Logger log = Logger.getLogger(DialogManager.class);

    public static DialogManager getManager() {
        if (dialogManager == null) {
            dialogManager = new DialogManager();
            dialogManager.setVisible(true);
        }
        return dialogManager;
    }

    public enum MTGDialogs {
        none, AboutDialog, MessageDialog, StackDialog, AssignDamageDialog, ManaChoiceDialog, ChoiceDialog, GraveDialog, DialogContainer, CombatDialog,
        ChooseDeckDialog, ChooseCommonDialog, RevealDialog
    }

    private MTGDialogs currentDialog = MTGDialogs.none;

    private DialogContainer dialogContainer = null;

    private int screen_width = 0;
    private int screen_height = 0;

    // /////////////////////////////// *** for drag and drop ***
    // /////////////////////////////////
    private boolean bDragged = false;
    private int dx, dy;
    private int mx, my;
    private Rectangle rec, oldRec;
    private JComponent j;

    public DialogManager() {
        addMouseListener(this);
        addMouseMotionListener(this);
        //addMouseWheelListener(this);
    }

    public void setScreenWidth(int screen_width) {
        this.screen_width = screen_width;
    }

    public void setScreenHeight(int screen_height) {
        this.screen_height = screen_height;
    }

    public void showStackDialog(CardsView cards, BigCard bigCard, FeedbackPanel feedbackPanel, UUID gameId) {

        int w = (int) (screen_width * 0.7);
        //int h = (int) (screen_height * 0.5);
        int h = 360;

        /*if (h < 200) {
            h = 200;
        }*/

        if (w > 800) {
            w = 800;
        }

        int height = getHeight();
        int width = getWidth();

        int x = ((width - w) / 2);
        int y = ((height - h) / 2);

        DlgParams params = new DlgParams();
        params.rect = new Rectangle(x, y, w, h);
        params.bigCard = bigCard;
        params.gameId = gameId;
        params.feedbackPanel = feedbackPanel;
        params.setCards(cards);
        dialogContainer = new DialogContainer(MTGDialogs.StackDialog, params);
        dialogContainer.setVisible(true);
        add(dialogContainer);

        this.currentDialog = MTGDialogs.DialogContainer;

        setDlgBounds(new Rectangle(x, y, w, h));

        dialogContainer.showDialog(true);

        setVisible(true);
    }

    public void showChoiceDialog(CardsView cards, BigCard bigCard, UUID gameId) {

        int w = 720;
        int h = 550;

        int height = getHeight();
        int width = getWidth();

        int x = ((width - w) / 2);
        int y = ((height - h) / 2);

        DlgParams params = new DlgParams();
        params.rect = new Rectangle(x, y, w, h);
        params.bigCard = bigCard;
        params.gameId = gameId;
        //params.feedbackPanel = feedbackPanel;
        params.setCards(cards);
        dialogContainer = new DialogContainer(MTGDialogs.ChoiceDialog, params);
        dialogContainer.setVisible(true);
        add(dialogContainer);

        this.currentDialog = MTGDialogs.DialogContainer;

        setDlgBounds(new Rectangle(x, y, w, h));

        dialogContainer.showDialog(true);

        setVisible(true);
    }

    public void setDlgBounds(Rectangle r) {
        if (currentDialog == MTGDialogs.DialogContainer) {
            dialogContainer.setBounds(r.x, r.y, r.width, r.height);
        }
    }

    public void fadeOut() {

        if (dialogContainer != null) {
            dialogContainer.showDialog(false);
            removeAll();
        }

        this.currentDialog = MTGDialogs.none;

        setVisible(false);

        repaint();
    }

    public void fadeOut(DialogContainer dc) {
        //log.debug("start:fadeOut:"+dc.toString());

        dc.showDialog(false);
        remove(dc);

        Component[] components = getComponents();
        boolean bFound = false;
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof DialogContainer) {
                bFound = true;
            }
        }

        if (!bFound) {
            setVisible(false);
        }

        //revalidate();
        validate();
    }

    public void mouseDragged(MouseEvent e) {
        if (bDragged == true) {
            dx = e.getX() - mx;
            dy = e.getY() - my;

            rec.x += dx;
            rec.y += dy;
            mx = e.getX();
            my = e.getY();
            if (oldRec == null) {
                oldRec = new Rectangle(rec);
            }
            int i = Math.abs(oldRec.x - rec.x) + Math.abs(oldRec.y - rec.y);
            if (i > 3) {
                oldRec = new Rectangle(rec);
                j.setBounds(oldRec);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            j = (JComponent) getComponentAt(e.getX(), e.getY());

            if (j != null && j instanceof DialogContainer) {
                rec = j.getBounds();
                bDragged = true;
                mx = e.getX();
                my = e.getY();
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        bDragged = false;
        if (j instanceof DialogManager) {
            return;
        }

        if (j != null && rec != null) {
            j.setBounds(rec);
        }
        oldRec = null;
        if (rec == null)
            return;
        if (rec.x < 0) {
            rec.x = 0;
            if (j != null)
                j.setBounds(rec);
        }
        if (rec.y < 0) {
            rec.y = 0;
            if (j != null)
                j.setBounds(rec);
        }
        j = null;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        System.out.println("outx:"+notches);
        if (currentDialog != null && currentDialog.equals(MTGDialogs.ChooseCommonDialog)) {
            System.out.println("out:"+1);
        }
    }

    /**
     * Default UID.
     */
    private static final long serialVersionUID = 1L;

}
