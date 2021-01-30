package mage.client.game;

import mage.client.components.KeyboundButton;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class FirstButtonMousePressedAction extends MouseAdapter {

    private final Consumer<MouseEvent> callback;
    private boolean pressed = false;
    private boolean inside = false;

    public FirstButtonMousePressedAction(Consumer<MouseEvent> callback) {
        this.callback = callback;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        if (e.getSource() instanceof KeyboundButton) {
            KeyboundButton button = (KeyboundButton) e.getSource();
            button.setTint(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        if (e.getSource() instanceof KeyboundButton) {
            KeyboundButton button = (KeyboundButton) e.getSource();
            button.setTint(false);
        }
        if (SwingUtilities.isLeftMouseButton(e) && inside) {
            callback.accept(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        inside = true;
        if (pressed && e.getSource() instanceof KeyboundButton) {
            KeyboundButton button = (KeyboundButton) e.getSource();
            button.setTint(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        inside = false;
        if (e.getSource() instanceof KeyboundButton) {
            KeyboundButton button = (KeyboundButton) e.getSource();
            button.setTint(false);
        }
    }

}
