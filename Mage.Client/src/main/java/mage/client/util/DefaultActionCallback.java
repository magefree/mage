package mage.client.util;

import java.awt.event.MouseEvent;
import java.util.UUID;

import mage.view.CardView;
import org.mage.network.Client;


public class DefaultActionCallback {

    private static final DefaultActionCallback INSTANCE = new DefaultActionCallback();

    private DefaultActionCallback() {}

    public static DefaultActionCallback getInstance() {
        return INSTANCE;
    }

    public void mouseClicked(MouseEvent e, UUID gameId, Client client, CardView card) {
        if (gameId != null) {
            if (card.isAbility() && card.getAbility() != null) {
                client.sendPlayerUUID(gameId, card.getAbility().getId());
            } else {
                client.sendPlayerUUID(gameId, card.getId());
            }
        }
    }
}
