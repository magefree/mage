package mage.client.util;

import java.awt.event.MouseEvent;
import java.util.UUID;

import mage.client.SessionHandler;
import mage.remote.Session;
import mage.view.CardView;


public class DefaultActionCallback {

    private static final DefaultActionCallback INSTANCE = new DefaultActionCallback();

    private DefaultActionCallback() {}

    public static DefaultActionCallback getInstance() {
        return INSTANCE;
    }

    public void mouseClicked(MouseEvent e, UUID gameId,  CardView card) {
        if (gameId != null) {
            if (card.isAbility() && card.getAbility() != null) {
                SessionHandler.sendPlayerUUID(gameId, card.getAbility().getId());
            } else {
                SessionHandler.sendPlayerUUID(gameId, card.getId());
            }
        }
    }
}
