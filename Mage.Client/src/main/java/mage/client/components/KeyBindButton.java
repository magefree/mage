package mage.client.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Campbell Suter <znix@znix.xyz>
 */
public class KeyBindButton extends JButton implements ActionListener {

    private final JPopupMenu menu;
    private final PopupItem item;
    private int keyCode;
    private String text;

    public KeyBindButton() {
        menu = new JPopupMenu();
        menu.add(item = new PopupItem());
        addActionListener(this);

        fixText();
    }

    private void applyNewKeycode(int code) {
        keyCode = code;
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_SPACE:
                keyCode = 0;
        }
        fixText();
        menu.setVisible(false);
        setSize(getPreferredSize());
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

    @Override
    public void actionPerformed(ActionEvent e) {
        menu.show(this, 0, 0);
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
