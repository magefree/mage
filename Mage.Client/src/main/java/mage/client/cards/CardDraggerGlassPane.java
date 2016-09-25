package mage.client.cards;

import mage.cards.MageCard;
import mage.client.plugins.impl.Plugins;
import mage.view.CardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by StravantUser on 2016-09-22.
 */
public class CardDraggerGlassPane extends JPanel implements MouseListener, MouseMotionListener {
    private DragCardSource source;
    private Component dragComponent;
    private JRootPane currentRoot;
    private Component oldGlassPane;
    private ArrayList<CardView> currentCards;
    private MageCard dragView;
    private DragCardTarget currentDragTarget;
    private boolean isDragging;

    public CardDraggerGlassPane(DragCardSource source) {
        this.source = source;

        // Listen on self
        setLayout(null);
        setOpaque(false);
        setVisible(true);
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

        // Hook up events
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

        // Switch glass pane to us
        oldGlassPane = currentRoot.getGlassPane();
        setVisible(false);
        currentRoot.setGlassPane(this);
        setVisible(true);
        revalidate();

        // Event to local space
        e = SwingUtilities.convertMouseEvent(c, e, this);

        // Get the cards to drag
        currentCards = new ArrayList<>(source.dragCardList());

        // Make a view for the first one and add it to us
        dragView = Plugins.getInstance().getMageCard(currentCards.get(0), null, new Dimension(100, 140), null, true);
        for (MouseListener l: dragView.getMouseListeners()) {
            dragView.removeMouseListener(l);
        }
        for (MouseMotionListener l : dragView.getMouseMotionListeners()) {
            dragView.removeMouseMotionListener(l);
        }
        this.add(dragView);
        dragView.setLocation(e.getX(), e.getY());

        // Notify the sounce
        source.dragCardBegin();

        // Update the target
        currentDragTarget = null;
        updateCurrentTarget(SwingUtilities.convertMouseEvent(c, e, currentRoot), false);
    }

    // e is relative to currentRoot
    private void updateCurrentTarget(MouseEvent e, boolean isEnding) {
        Component mouseOver = SwingUtilities.getDeepestComponentAt(currentRoot.getContentPane(), e.getX(), e.getY());
        while (mouseOver != null) {
            if (mouseOver instanceof DragCardTarget) {
                DragCardTarget target = (DragCardTarget)mouseOver;
                MouseEvent targetEvent = SwingUtilities.convertMouseEvent(currentRoot, e, mouseOver);
                if (target != currentDragTarget) {
                    if (currentDragTarget != null) {
                        MouseEvent oldTargetEvent = SwingUtilities.convertMouseEvent(currentRoot, e, (Component) currentDragTarget);
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
            MouseEvent oldTargetEvent = SwingUtilities.convertMouseEvent(currentRoot, e, (Component)currentDragTarget);
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
        e = SwingUtilities.convertMouseEvent(dragComponent, e, currentRoot);

        // Switch back glass pane
        currentRoot.setGlassPane(oldGlassPane);

        // Remove the drag card
        this.remove(dragView);

        // Update the target, and do the drop
        updateCurrentTarget(e, true);

        // Let the drag source know
        source.dragCardEnd(currentDragTarget);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Update the view
        MouseEvent glassE = SwingUtilities.convertMouseEvent(dragComponent, e, this);
        dragView.setLocation(glassE.getX(), glassE.getY());
        dragView.repaint();

        // Convert the event into root coords and update target
        e = SwingUtilities.convertMouseEvent(dragComponent, e, currentRoot);
        updateCurrentTarget(e, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}
