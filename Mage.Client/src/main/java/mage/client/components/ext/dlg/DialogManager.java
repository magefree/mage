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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Game GUI: part of the old dialog system, transparent dialog with cards list (example: exile button on player's panel)
 *
 * @author mw, noxx
 */
public class DialogManager extends JComponent implements MouseListener, MouseMotionListener {

    private static final Map<UUID, DialogManager> dialogManagers = new HashMap<>();

    public static DialogManager getManager(UUID gameId) {
        if (!dialogManagers.containsKey(gameId)) {
            synchronized (dialogManagers) {
                if (!dialogManagers.containsKey(gameId)) {
                    DialogManager dialogManager = new DialogManager();
                    dialogManager.screen_width = 768;
                    dialogManager.screen_height = 1024;
                    dialogManager.setBounds(0, 0, 768, 1024);
                    dialogManager.setVisible(false);
                    dialogManagers.put(gameId, dialogManager);
                }
            }
        }
        return dialogManagers.get(gameId);
    }

    public enum MTGDialogs {
        NONE, ABOUT, MESSAGE, ASSIGN_DAMAGE, MANA_CHOICE, CHOICE, EMBLEMS, GRAVEYARD, DialogContainer, COMBAT,
        CHOOSE_DECK, CHOOSE_COMMON, REVEAL, EXILE
    }

    /**
     * Remove the DialogManager of the gameId
     * 
     * @param gameId 
     */
    public static void removeGame(UUID gameId) {
        if (dialogManagers.containsKey(gameId)) {
            synchronized (dialogManagers) {
                DialogManager dialogManager = dialogManagers.get(gameId);
                dialogManager.cleanUp();
                dialogManagers.remove(gameId);
            }
        }
    }

    private MTGDialogs currentDialog = MTGDialogs.NONE;

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
    }

    public void cleanUp() {
        this.currentDialog = null;
        if (dialogContainer != null) {
            this.dialogContainer.cleanUp();
            this.dialogContainer = null;
        }
        this.removeMouseListener(this);
        this.removeMouseMotionListener(this);

    }

    public void setScreenWidth(int screen_width) {
        this.screen_width = screen_width;
    }

    public static void updateParams(int width, int height, boolean isVisible) {
        synchronized (dialogManagers) {
            for (DialogManager dialogManager : dialogManagers.values()) {
                dialogManager.screen_width = width;
                dialogManager.screen_height = height;
                dialogManager.setBounds(0, 0, width, height);
            }
        }
    }

    public void setScreenHeight(int screen_height) {
        this.screen_height = screen_height;
    }

    public void showExileDialog(CardsView cards, BigCard bigCard, UUID gameId) {

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
        params.setCards(cards);
        dialogContainer = new DialogContainer(MTGDialogs.EXILE, params);
        dialogContainer.setVisible(true);
        add(dialogContainer);

        this.currentDialog = MTGDialogs.DialogContainer;

        setDlgBounds(new Rectangle(x, y, w, h));

        dialogContainer.showDialog(true);

        setVisible(true);
    }

    public void showEmblemsDialog(CardsView cards, BigCard bigCard, UUID gameId) {

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
        dialogContainer = new DialogContainer(MTGDialogs.EMBLEMS, params);
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

        this.currentDialog = MTGDialogs.NONE;

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

    @Override
    public void mouseDragged(MouseEvent e) {
        if (bDragged) {
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

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        j = (JComponent) getComponentAt(e.getX(), e.getY());
        if (j instanceof DialogContainer) {
            rec = j.getBounds();
            bDragged = true;
            mx = e.getX();
            my = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        bDragged = false;
        if (j instanceof DialogManager) {
            return;
        }

        if (j != null && rec != null) {
            j.setBounds(rec);
        }
        oldRec = null;
        if (rec == null) {
            return;
        }
        if (rec.x < 0) {
            rec.x = 0;
            if (j != null) {
                j.setBounds(rec);
            }
        }
        if (rec.y < 0) {
            rec.y = 0;
            if (j != null) {
                j.setBounds(rec);
            }
        }
        j = null;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
//        System.out.println("outx:"+notches);
//        if (currentDialog != null && currentDialog.equals(MTGDialogs.CHOOSE_COMMON)) {
//            System.out.println("out:"+1);
//        }
    }

    /**
     * Default UID.
     */
    private static final long serialVersionUID = 1L;

}
