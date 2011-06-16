package mage.client.util;

import java.awt.event.MouseEvent;
import java.util.UUID;

import mage.remote.Session;
import mage.view.CardView;


public class DefaultActionCallback {
	
	private static final DefaultActionCallback INSTANCE = new DefaultActionCallback();
	
	private DefaultActionCallback() {}
	
	public static DefaultActionCallback getInstance() {
		return INSTANCE;
	}
	
	public void mouseClicked(MouseEvent e, UUID gameId, Session session, CardView card) {
		if (gameId != null) {
			if (card.isAbility() && card.getAbility() != null) {
				session.sendPlayerUUID(gameId, card.getAbility().getId());
			} else {
				session.sendPlayerUUID(gameId, card.getId());
			}
		}
	}
}
