package mage.client.util;

import mage.client.SessionHandler;
import mage.view.CardView;

import java.util.UUID;


public enum DefaultActionCallback {

    instance;

    public void mouseClicked(UUID gameId, CardView card) {
        if (gameId != null) {
            if (card.isAbility() && card.getAbility() != null) {
                SessionHandler.sendPlayerUUID(gameId, card.getAbility().getId());
            } else {
                SessionHandler.sendPlayerUUID(gameId, card.getId());
            }
        }
    }
}
