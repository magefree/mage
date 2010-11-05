package mage.cards.action.impl;

import java.awt.event.MouseEvent;

import mage.cards.action.ActionCallback;

/**
 * Callback that does nothing on any action 
 * 
 * @author nantuko84
 */
public class EmptyCallback implements ActionCallback {

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
