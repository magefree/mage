package mage.client.game;

import mage.client.components.KeyboundButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class FirstButtonMousePressedAction extends MouseAdapter {

    private final Consumer<MouseEvent> callback;
    private boolean inside = false;

    public FirstButtonMousePressedAction(Consumer<MouseEvent> callback) {
        this.callback = callback;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof KeyboundButton) {
            KeyboundButton button = (KeyboundButton) e.getSource();
            button.setPressing(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof KeyboundButton) {
            KeyboundButton button = (KeyboundButton) e.getSource();
            button.setPressing(false);
        }
        if (e.getButton() == MouseEvent.BUTTON1 && inside) {
            callback.accept(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        inside = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        inside = false;
    }

}
