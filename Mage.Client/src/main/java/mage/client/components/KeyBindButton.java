package mage.client.components;

import mage.client.dialog.PreferencesDialog;

import javax.swing.*;
import java.awt.event.*;

/**
 * @author Campbell Suter <znix@znix.xyz>, JayDi85
 */
public class KeyBindButton extends JButton implements ActionListener {

    private final PreferencesDialog preferences;
    private final String key;
    private PopupItem item;
    private JPopupMenu menu;
    private int keyCode;
    private int modifierCode;
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

    private JPopupMenu createPopupMenu() {
        menu = new JPopupMenu();
        menu.add(item = new PopupItem());
        return menu;
    }

    private void applyNewKeycode(int code, int modifier) {
        // clear used keys
        preferences.getKeybindButtons().stream()
                .filter(b -> b != KeyBindButton.this)
                .filter(b -> {
                    return b.keyCode == code && b.modifierCode == modifier;
                })
                .forEach(b -> {
                    b.setKeyCode(0);
                    b.setModifierCode(0);
                });

        // set new
        setKeyCode(code);
        setModifierCode(modifier);
        menu.setVisible(false);
    }

    private void fixText() {
        if (keyCode == 0) {
            text = "<none>";
        } else {
            String codeStr = KeyEvent.getKeyText(keyCode);
            String modStr = KeyEvent.getKeyModifiersText(modifierCode);
            text = (modStr.isEmpty() ? "" : modStr + " + ") + codeStr;
        }
        repaint();
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_SPACE:
                this.keyCode = 0;
        }
        fixText();
        //setSize(getPreferredSize());
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setModifierCode(int modifierCode) {
        this.modifierCode = modifierCode;

        // only single modifier allowed
        if (!(modifierCode == InputEvent.ALT_MASK
                || modifierCode == InputEvent.CTRL_MASK
                || modifierCode == InputEvent.SHIFT_MASK)) {
            this.modifierCode = 0;
        }
        fixText();
        //setSize(getPreferredSize());
    }

    public int getModifierCode() {
        return modifierCode;
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
        JPopupMenu m = createPopupMenu();
        m.setPopupSize(this.getWidth(), this.getHeight());
        m.show(this, 0, 0);
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

            // cancel on ESC
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                menu.setVisible(false);
                return;
            }

            // clear on SPACE
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                setKeyCode(0);
                setModifierCode(0);
                menu.setVisible(false);
                return;
            }

            // ignore multiple mod keys
            switch (e.getModifiers()) {
                case KeyEvent.CTRL_MASK:
                case KeyEvent.SHIFT_MASK:
                case KeyEvent.ALT_MASK:
                case 0:
                    break;
                default:
                    return;
            }

            // skip single mod keys without chars
            switch (e.getKeyCode()) {
                case KeyEvent.VK_CONTROL:
                case KeyEvent.VK_SHIFT:
                case KeyEvent.VK_ALT:
                    return;
            }

            // all done, can save
            applyNewKeycode(e.getKeyCode(), e.getModifiers());
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
}
