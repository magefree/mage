package mage.cards.interfaces;

import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import mage.view.PermanentView;

public interface PermanentInterface extends MouseMotionListener, MouseListener, FocusListener, ComponentListener {
	void updateCard(PermanentView card);
}
