package mage.client.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author Campbell Suter <znix@znix.xyz>
 */
public class KeyBindButton extends JButton implements ActionListener {

	private final PreferencesDialog preferences;
	private final String key;
	private PopupItem item;
	private JPopupMenu menu;
	private int keyCode;
	private String text;

	/**
	 * For the IDE only, do not use!
	 */
	public KeyBindButton() {
		this(null, null);
	}

	public KeyBindButton(PreferencesDialog preferences, String key) {
		this.preferences = preferences;
		this.key = key;
		addActionListener(this);
		fixText();
	}

	private JPopupMenu getMenu() {
		menu = new JPopupMenu();
		menu.add(item = new PopupItem());
		return menu;
	}

	private void applyNewKeycode(int code) {
		preferences.getKeybindButtons().stream()
				.filter(b -> b != KeyBindButton.this)
				.filter(b -> {
                    return b.keyCode == code;
                })
				.forEach(b -> b.setKeyCode(0));

		setKeyCode(code);
		menu.setVisible(false);
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
		switch (keyCode) {
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_SPACE:
				keyCode = 0;
		}
		fixText();
		setSize(getPreferredSize());
	}

	public int getKeyCode() {
		return keyCode;
	}

	@Override
	public String getText() {
		return text;
	}

	public String getKey() {
		return key;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getMenu().show(this, 0, 0);
		item.requestFocusInWindow();
	}

	private class PopupItem extends JLabel implements KeyListener {

		public PopupItem() {
			super("Press a key");
			addKeyListener(this);
			setFocusable(true);
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			applyNewKeycode(e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

	}
}
