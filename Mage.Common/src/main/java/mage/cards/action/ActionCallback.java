package mage.cards.action;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface ActionCallback {

    void mouseClicked(MouseEvent e, TransferData data, boolean doubleClick);

    void mousePressed(MouseEvent e, TransferData data);

    void mouseReleased(MouseEvent e, TransferData data);

    void mouseMoved(MouseEvent e, TransferData data);

    void mouseDragged(MouseEvent e, TransferData data);

    void mouseEntered(MouseEvent e, TransferData data);

    void mouseExited(MouseEvent e, TransferData data);

    void mouseWheelMoved(int mouseWheelRotation, TransferData data);

    void hideOpenComponents();

    void popupMenuCard(MouseEvent e, TransferData data);

    /**
     * Show popup menu
     *
     * @param e
     * @param sourceComponent custom source component for the event, must support CardEventProducer
     */
    void popupMenuPanel(MouseEvent e, Component sourceComponent);
}
