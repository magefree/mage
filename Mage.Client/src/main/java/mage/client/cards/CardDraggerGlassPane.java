package mage.client.cards;

import mage.cards.MageCard;
import mage.client.MagePane;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.view.CardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by StravantUser on 2016-09-22.
 */
public class CardDraggerGlassPane implements MouseListener, MouseMotionListener {
    private final DragCardSource source;
    private Component dragComponent;
    private JRootPane currentRoot;
    private JComponent glassPane;
    private ArrayList<CardView> currentCards;
    private MageCard dragView;
    private DragCardTarget currentDragTarget;
    private boolean isDragging;

    // This should not be strictly needed, but for some reason I can't figure out getDeepestComponentAt and
    // getComponentAt do not seem to work correctly for our setup if called on the root MageFrame.
    private MagePane currentEventRootMagePane;

    public CardDraggerGlassPane(DragCardSource source) {
        this.source = source;
    }

    public void beginDrag(Component c, MouseEvent e) {
        // Start drag
        if (isDragging) {
            return;
        }
        isDragging = true;

        // Record what we are dragging on
        dragComponent = c;
        currentRoot = SwingUtilities.getRootPane(c);

        // Pane
        glassPane = (JComponent) currentRoot.getGlassPane();
        glassPane.setLayout(null);
        glassPane.setOpaque(false);
        glassPane.setVisible(true);

        // Get root mage pane to handle drag targeting in
        Component rootMagePane = c;
        while (rootMagePane != null && !(rootMagePane instanceof MagePane)) {
            rootMagePane = rootMagePane.getParent();
        }
        if (rootMagePane == null) {
            throw new RuntimeException("CardDraggerGlassPane::beginDrag not in a MagePane?");
        } else {
            currentEventRootMagePane = (MagePane) rootMagePane;
        }

        // Hook up events
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

        // Event to local space
        e = SwingUtilities.convertMouseEvent(c, e, glassPane);

        // Get the cards to drag
        currentCards = new ArrayList<>(source.dragCardList());

        // Make a view for the first one and add it to us
        dragView = Plugins.instance.getMageCard(currentCards.get(0), null, new Dimension(100, 140), null, true, false, PreferencesDialog.getRenderMode());
        for (MouseListener l : dragView.getMouseListeners()) {
            dragView.removeMouseListener(l);
        }
        for (MouseMotionListener l : dragView.getMouseMotionListeners()) {
            dragView.removeMouseMotionListener(l);
        }
        dragView.setLocation(e.getX(), e.getY());
        glassPane.add(dragView);

        // Notify the sounce
        source.dragCardBegin();

        // Update the target
        currentDragTarget = null;
        updateCurrentTarget(SwingUtilities.convertMouseEvent(glassPane, e, currentEventRootMagePane), false);
    }

    // e is relative to currentRoot
    private void updateCurrentTarget(MouseEvent e, boolean isEnding) {
        Component mouseOver = SwingUtilities.getDeepestComponentAt(currentEventRootMagePane, e.getX(), e.getY());
        while (mouseOver != null) {
            if (mouseOver instanceof DragCardTarget) {
                DragCardTarget target = (DragCardTarget) mouseOver;
                MouseEvent targetEvent = SwingUtilities.convertMouseEvent(currentEventRootMagePane, e, mouseOver);
                if (target != currentDragTarget) {
                    if (currentDragTarget != null) {
                        MouseEvent oldTargetEvent = SwingUtilities.convertMouseEvent(currentEventRootMagePane, e, (Component) currentDragTarget);
                        currentDragTarget.dragCardExit(oldTargetEvent);
                    }
                    currentDragTarget = target;
                    currentDragTarget.dragCardEnter(targetEvent);
                }
                if (isEnding) {
                    currentDragTarget.dragCardExit(targetEvent);
                    currentDragTarget.dragCardDrop(targetEvent, source, currentCards);
                } else {
                    currentDragTarget.dragCardMove(targetEvent);
                }
                return;
            }
            mouseOver = mouseOver.getParent();
        }
        if (currentDragTarget != null) {
            MouseEvent oldTargetEvent = SwingUtilities.convertMouseEvent(currentEventRootMagePane, e, (Component) currentDragTarget);
            currentDragTarget.dragCardExit(oldTargetEvent);
        }
        currentDragTarget = null;
    }

    public boolean isDragging() {
        return isDragging;
    }

    /**
     * Mouse released -> we are done the drag
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No longer dragging
        isDragging = false;

        // Remove listeners
        dragComponent.removeMouseListener(this);
        dragComponent.removeMouseMotionListener(this);

        // Convert the event into root coords
        e = SwingUtilities.convertMouseEvent(dragComponent, e, currentEventRootMagePane);

        // Remove the drag card
        glassPane.remove(dragView);
        glassPane.repaint();

        // Let the drag source know
        source.dragCardEnd(currentDragTarget);

        // Update the target, and do the drop
        updateCurrentTarget(e, true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Update the view
        MouseEvent glassE = SwingUtilities.convertMouseEvent(dragComponent, e, glassPane);
        dragView.setLocation(glassE.getX(), glassE.getY());
        dragView.repaint();
        // Convert the event into root coords and update target
        e = SwingUtilities.convertMouseEvent(dragComponent, e, currentEventRootMagePane);
        updateCurrentTarget(e, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
