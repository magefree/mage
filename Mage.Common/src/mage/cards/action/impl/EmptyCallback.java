package mage.cards.action.impl;

import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Callback that does nothing on any action 
 * 
 * @author nantuko84
 */
public class EmptyCallback implements ActionCallback {

    @Override
    public void mouseMoved(MouseEvent e, TransferData data) {
    }

    @Override
    public void mouseDragged(MouseEvent e, TransferData data) {

    }

    @Override
    public void mouseEntered(MouseEvent e, TransferData data) {
    }

    @Override
    public void mouseExited(MouseEvent e, TransferData data) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, TransferData data) {
    }

    @Override
    public void hideTooltipPopup() {
    }

    @Override
    public void mouseClicked(MouseEvent e, TransferData data) {
    }

    @Override
    public void mousePressed(MouseEvent e, TransferData data) {
    }

    @Override
    public void mouseReleased(MouseEvent e, TransferData data) {
    }

}
