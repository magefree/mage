package mage.client.cards;

import mage.abilities.icon.CardIconRenderSettings;
import mage.cards.MageCard;
import mage.client.MagePane;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientDefaultSettings;
import mage.util.DebugUtil;
import mage.view.CardView;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Popup panel for dragging cards drawing
 *
 * @author StravantUser, JayDi85
 */
public class CardDraggerGlassPane {

    private static final Logger logger = Logger.getLogger(CardDraggerGlassPane.class);

    private final DragCardSource source;
    private DragCardTarget currentTarget;

    private MageCard draggingCard; // original card that starting the dragging (full dragging cards keeps in currentCards)
    private ArrayList<CardView> currentCards;
    private JComponent draggingGlassPane;
    private MageCard draggingDrawView; // fake card for drawing on glass pane

    // processing drag events (moving and the end)
    private MouseListener draggingMouseListener;
    private MouseMotionListener draggingMouseMotionListener;

    private boolean isDragging;
    private Dimension cardDimension;

    // This should not be strictly needed, but for some reason I can't figure out getDeepestComponentAt and
    // getComponentAt do not seem to work correctly for our setup if called on the root MageFrame.
    private MagePane eventRootPane; // example: deck editor pane

    public CardDraggerGlassPane(DragCardSource source) {
        this.source = source;
    }

    public void handleDragStart(MageCard card, MouseEvent cardEvent) {
        // Start drag
        if (isDragging) {
            return;
        }
        isDragging = true;

        // Record what we are dragging on
        draggingCard = card;

        // Pane for dragging drawing (fake card)
        JRootPane currentRoot = SwingUtilities.getRootPane(draggingCard);
        draggingGlassPane = (JComponent) currentRoot.getGlassPane();
        draggingGlassPane.setLayout(null);
        draggingGlassPane.setOpaque(false);
        draggingGlassPane.setVisible(true);
        if (DebugUtil.GUI_DECK_EDITOR_DRAW_DRAGGING_PANE_BORDER) {
            draggingGlassPane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        }

        // Get root mage pane to handle drag targeting in
        Component rootMagePane = draggingCard;
        while (rootMagePane != null && !(rootMagePane instanceof MagePane)) {
            rootMagePane = rootMagePane.getParent();
        }
        if (rootMagePane == null) {
            throw new RuntimeException("CardDraggerGlassPane::beginDrag not in a MagePane?");
        } else {
            eventRootPane = (MagePane) rootMagePane;
        }

        // ENABLE drag moving and drag ending processing
        if (this.draggingMouseMotionListener == null) {
            this.draggingMouseMotionListener = new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    handleDragging(e);
                }
            };
        }
        if (this.draggingMouseListener == null) {
            this.draggingMouseListener = new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    handleDragEnd(e);
                }
            };
        }
        draggingCard.addMouseMotionListener(this.draggingMouseMotionListener);
        draggingCard.addMouseListener(this.draggingMouseListener);

        // Event to local space
        MouseEvent glassEvent = SwingUtilities.convertMouseEvent(draggingCard.getMainPanel(), cardEvent, draggingGlassPane);

        // Get the cards to drag
        currentCards = new ArrayList<>(source.dragCardList());

        // make a fake card to drawing (card's top left corner under the cursor)
        Rectangle rectangle = new Rectangle(glassEvent.getX(), glassEvent.getY(), getCardDimension().width, getCardDimension().height);
        draggingDrawView = Plugins.instance.getMageCard(currentCards.get(0), null, new CardIconRenderSettings(), getCardDimension(), null, true, false, PreferencesDialog.getRenderMode(), true);
        draggingDrawView.setCardContainerRef(null); // no feedback events
        draggingDrawView.update(currentCards.get(0));
        draggingDrawView.setCardBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        // disable mouse events from fake card
        for (MouseListener l : draggingDrawView.getMouseListeners()) {
            draggingDrawView.removeMouseListener(l);
        }
        for (MouseMotionListener l : draggingDrawView.getMouseMotionListeners()) {
            draggingDrawView.removeMouseMotionListener(l);
        }
        draggingGlassPane.add(draggingDrawView);

        // Notify the sounce
        source.dragCardBegin();

        // Update the target
        currentTarget = null;
        MouseEvent rootEvent = SwingUtilities.convertMouseEvent(draggingGlassPane, glassEvent, eventRootPane);
        updateCurrentTarget(rootEvent, false);
    }

    private void handleDragging(MouseEvent e) {
        // redraw fake card near the cursor
        MouseEvent glassEvent = SwingUtilities.convertMouseEvent(draggingCard.getMainPanel(), e, draggingGlassPane);
        draggingDrawView.setCardLocation(glassEvent.getX(), glassEvent.getY());
        draggingDrawView.repaint();

        // Convert the event into root coords and update target
        MouseEvent rootEvent = SwingUtilities.convertMouseEvent(draggingCard.getMainPanel(), e, eventRootPane);
        updateCurrentTarget(rootEvent, false);
    }

    private void handleDragEnd(MouseEvent e) {
        // No longer dragging
        isDragging = false;

        // Remove custom listeners
        draggingCard.removeMouseListener(this.draggingMouseListener);
        draggingCard.removeMouseMotionListener(this.draggingMouseMotionListener);

        // Convert the event into root coords
        MouseEvent rootEvent = SwingUtilities.convertMouseEvent(draggingCard.getMainPanel(), e, eventRootPane);

        // Remove the drag card
        draggingGlassPane.remove(draggingDrawView);
        draggingGlassPane.repaint();

        // Let the drag source know
        source.dragCardEnd(currentTarget);

        // Update the target, and do the drop
        updateCurrentTarget(rootEvent, true);
    }

    private void updateCurrentTarget(MouseEvent rootEvent, boolean isEnding) {
        // event related to eventRootPane
        Component mouseOver = SwingUtilities.getDeepestComponentAt(eventRootPane, rootEvent.getX(), rootEvent.getY());
        while (mouseOver != null) {
            if (mouseOver instanceof DragCardTarget) {
                DragCardTarget target = (DragCardTarget) mouseOver;
                MouseEvent targetEvent = SwingUtilities.convertMouseEvent(eventRootPane, rootEvent, mouseOver);
                if (target != currentTarget) {
                    if (currentTarget != null) {
                        MouseEvent oldTargetEvent = SwingUtilities.convertMouseEvent(eventRootPane, rootEvent, (Component) currentTarget);
                        currentTarget.dragCardExit(oldTargetEvent);
                    }
                    currentTarget = target;
                    currentTarget.dragCardEnter(targetEvent);
                }
                if (isEnding) {
                    currentTarget.dragCardExit(targetEvent);
                    currentTarget.dragCardDrop(targetEvent, source, currentCards);
                } else {
                    currentTarget.dragCardMove(targetEvent);
                }
                return;
            }
            mouseOver = mouseOver.getParent();
        }
        if (currentTarget != null) {
            MouseEvent oldTargetEvent = SwingUtilities.convertMouseEvent(eventRootPane, rootEvent, (Component) currentTarget);
            currentTarget.dragCardExit(oldTargetEvent);
        }
        currentTarget = null;
    }

    protected Dimension getCardDimension() {
        if (cardDimension == null) {
            cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(), ClientDefaultSettings.dimensions.getFrameHeight());
        }
        return cardDimension;
    }

    public boolean isDragging() {
        return isDragging;
    }
}
