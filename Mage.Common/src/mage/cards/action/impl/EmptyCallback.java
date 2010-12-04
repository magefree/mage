package mage.cards.action.impl;

import java.awt.event.MouseEvent;

import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;

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
	public void mouseEntered(MouseEvent e, TransferData data) {
	}

	@Override
	public void mouseExited(MouseEvent e, TransferData data) {
	}

	@Override
	public void mouseClicked(MouseEvent e, TransferData data) {
	}

	@Override
	public void mousePressed(MouseEvent e, TransferData data) {
	}

}
