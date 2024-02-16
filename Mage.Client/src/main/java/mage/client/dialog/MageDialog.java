package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.components.MageDesktopIconifySupport;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.InvocationEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class MageDialog extends javax.swing.JInternalFrame {

    private static final Logger LOGGER = Logger.getLogger(MageDialog.class);

    protected boolean modal = false;

    /**
     * Creates new form MageDialog
     */
    public MageDialog() {
        initComponents();

        // enable a minimizing window on double clicks
        if (this instanceof MageDesktopIconifySupport) {
            BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
            ui.getNorthPane().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // double clicks and repeated double clicks
                    // minimize window to title bar
                    if (!SwingUtilities.isLeftMouseButton(e)) {
                        return;
                    }
                    if ((e.getClickCount() & 1) == 0 && (e.getClickCount() > 0) && !e.isConsumed()) {
                        e.consume();
                        try {
                            MageDialog.this.setIcon(!MageDialog.this.isIcon());
                        } catch (PropertyVetoException exp) {
                            // ignore read only
                        }
                    }
                }
            });
        }
    }

    public void changeGUISize() {

    }

    public static boolean isModalDialogActivated() {
        for (JInternalFrame frame : MageFrame.getDesktop().getAllFrames()) {
            if (frame instanceof MageDialog) {
                MageDialog md = (MageDialog) frame;
                if (md.isVisible() && md.isModal()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printFramesOrder(String name) {
        ///*
        JInternalFrame[] frames = MageFrame.getDesktop().getAllFrames();
        System.out.println("--- " + name + " ---");
        int order = 0;
        for (JInternalFrame frame : frames) {
            order++;
            int zorder = -1;
            if (frame.getParent() != null) {
                zorder = frame.getParent().getComponentZOrder(frame);
            }
            System.out.println(order + ". " + frame.getClass() + " (" + frame.getTitle() + ") : layer = " + frame.getLayer() + ", zorder = " + zorder);
        }
        //*/
    }

    @Override
    public void show() {
        super.show();

        // frames desktop ordering
        // more info https://docs.oracle.com/javase/7/docs/api/javax/swing/JLayeredPane.html
        // WARNING, use
        // - JLayeredPane.DEFAULT_LAYER: tables and games (tabs)
        // - JLayeredPane.PALETTE_LAYER: toolbars and info windows like cards list, not modal dialogs (not required user actions)
        // - JLayeredPane.MODAL_LAYER: all modal dialogs (user required actions - select cards in game, new game window, error windows)
        // - JLayeredPane.POPUP_LAYER: hints and other top level graphics
        // - JLayeredPane.DRAG_LAYER: top most layer for critical actions and user controls

        if (modal) {
            this.setClosable(false);
        }

        this.toFront();

        if (modal) {
            startModal();
        }
    }

    @Override
    public void setVisible(boolean value) {
        super.setVisible(value);

        if (value) {
            this.toFront();
            try {
                this.setSelected(true);
            } catch (PropertyVetoException e) {
                //
            }
        }

        if (modal) {
            this.setClosable(false);
            if (value) {
                startModal();
            } else if (SwingUtilities.isEventDispatchThread()) {
                stopModal();
            } else {
                try {
                    SwingUtilities.invokeAndWait(() -> stopModal());
                } catch (InterruptedException ex) {
                    LOGGER.fatal("MageDialog error", ex);
                    Thread.currentThread().interrupt();
                } catch (InvocationTargetException ex) {
                    LOGGER.fatal("MageDialog error", ex);
                }
            }
        }
    }

    private synchronized void startModal() {
        // modal loop -- all mouse events must be ignored by other windows
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                EventQueue theQueue = getToolkit().getSystemEventQueue();
                while (isVisible()) {
                    AWTEvent event = theQueue.getNextEvent();
                    Object source = event.getSource();
                    boolean dispatch = true;

                    // https://github.com/magefree/mage/issues/584 - Let's hope this will fix the Linux window problem
                    if (event.getSource() != null && event.getSource() instanceof TrayIcon && !(event instanceof InvocationEvent)) {
                        dispatch = false;
                        //return; // JayDi85: users can move mouse over try icon to disable modal mode (it's a bug but can be used in the future)
                    }

                    // ignore mouse events outside from panel, only drag and move allowed -- as example:
                    // combobox's popup will be selectable outside
                    // cards and button hints will be works
                    Component popupComponent = null;
                    MouseEvent popupEvent = null;
                    if (event instanceof MouseEvent && event.getSource() instanceof Component) {
                        MouseEvent e = (MouseEvent) event;
                        MouseEvent m = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, this);

                        // disable all outer events (except some actions)
                        if (!this.contains(m.getPoint())) {
                            boolean allowedEvent = false;

                            // need any mouse move (for hints)
                            if (e.getID() == MouseEvent.MOUSE_DRAGGED || e.getID() == MouseEvent.MOUSE_MOVED) {
                                allowedEvent = true;
                            }

                            // need popup clicks and mouse wheel (for out of bound actions)
                            if (!allowedEvent) {
                                popupComponent = SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY()); // show root component (popups creates at root)
                                if (popupComponent != null && (popupComponent.getClass().getName().contains("BasicComboPopup")
                                        || popupComponent.getClass().getName().contains("JMenuItem"))) {
                                    popupEvent = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, popupComponent);
                                    allowedEvent = true;
                                }
                            }

                            dispatch = allowedEvent;
                        }
                    }

                    if (dispatch) {
                        if (popupEvent != null) {
                            // process outer popup events, it's must be FIRST check
                            popupComponent.dispatchEvent(popupEvent);
                        } else if (event instanceof ActiveEvent) {
                            ((ActiveEvent) event).dispatch();
                        } else if (source instanceof Component) {
                            ((Component) source).dispatchEvent(event);
                        } else if (source instanceof MenuComponent) {
                            ((MenuComponent) source).dispatchEvent(event);
                        } else {
                            LOGGER.warn("Unable to dispatch: " + event);
                        }
                    }
                }
            } else {
                while (isVisible()) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            LOGGER.fatal("MageDialog error", e);
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void stopModal() {
        notifyAll();
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    public boolean isModal() {
        return this.modal;
    }

    public void hideDialog() {
        this.setVisible(false);
    }

    public void removeDialog() {
        // avoid memory leak of javax.swing.plaf.nimbus.NimbusStyle$CacheKey
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
        //this.setVisible(false);
        // important to set close before removing the JInternalFrame to avoid memory leaks (http://bugs.java.com/view_bug.do?bug_id=7163808)
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            LOGGER.error("setClosed(false) failed", ex);
        }
        MageFrame.getDesktop().remove(this);
    }

    public void makeWindowCentered() {
        makeWindowCentered(this, this.getWidth(), this.getHeight());
    }

    public static void makeWindowCentered(Component component, int width, int height) {
        Point centered = SettingsManager.instance.getComponentPosition(width, height);
        component.setLocation(centered.x, centered.y);
        GuiDisplayUtil.keepComponentInsideScreen(centered.x, centered.y, component);
    }

    /**
     * Used to set a tooltip text on icon and titel bar
     *
     * @param text
     */
    public void setTitelBarToolTip(final String text) {
        desktopIcon.setToolTipText(text); //tooltip on icon
        Component[] children = getComponents();
        if (children != null) {
            for (Component children1 : children) {
                if (children1.getClass().getName().equalsIgnoreCase("javax.swing.plaf.synth.SynthInternalFrameTitlePane")) {
                    ((JComponent) children1).setToolTipText(text); //tooltip on title bar
                    break;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
