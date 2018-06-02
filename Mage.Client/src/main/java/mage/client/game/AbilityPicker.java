

package mage.client.game;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.util.gui.GuiDisplayUtil;
import mage.view.AbilityPickerView;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * ************************  U N U S E D   *********************************
 *
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AbilityPicker extends JPopupMenu implements PopupMenuListener {

    private UUID gameId;

    public AbilityPicker(String ThisIsnotUsedAnymore) {
        this.addPopupMenuListener(this);
    }

    public void init(UUID gameId) {
        this.gameId = gameId;
    }

    public void cleanUp() {
        this.removePopupMenuListener(this);
    }

    public void show(AbilityPickerView choices, Point p) {
        if (p == null) {
            return;
        }
        this.removeAll();
        for (Entry<UUID, String> choice: choices.getChoices().entrySet()) {
            this.add(new AbilityPickerAction(choice.getKey(), choice.getValue()));
        }
        this.show(MageFrame.getDesktop(), p.x, p.y);
        GuiDisplayUtil.keepComponentInsideScreen(p.x, p.y, this);
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {  }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        SessionHandler.sendPlayerBoolean(gameId, false);
    }

    private class AbilityPickerAction extends AbstractAction {

        private final UUID id;

        public AbilityPickerAction(UUID id, String choice) {
            this.id = id;
            putValue(Action.NAME, choice);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SessionHandler.sendPlayerUUID(gameId, id);
            setVisible(false);
        }

    }

}
