/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JToggleButton;

/**
 *
 * @author Campbell Suter <znix@znix.xyz>
 */
public class KeyBindButton extends JToggleButton implements KeyListener {

	private int keyCode;
	private String text;

	public KeyBindButton() {
		fixText();
		addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!isSelected()) {
			return;
		}
		keyCode = e.getKeyCode();
		fixText();
		setSelected(false);
		System.out.println("text: " + text);
	}

	private void fixText() {
		if (keyCode == 0) {
			text = "<None>";
		} else {
			text = KeyEvent.getKeyText(keyCode);
		}
		repaint();
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
		fixText();
	}

	public int getKeyCode() {
		return keyCode;
	}

	@Override
	public String getText() {
		return text;
	}

}
