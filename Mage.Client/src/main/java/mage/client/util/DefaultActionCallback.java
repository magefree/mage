package mage.client.util;

import java.awt.event.MouseEvent;
import java.util.UUID;

import mage.client.remote.Session;
import mage.view.CardView;


public class DefaultActionCallback {
	public void mouseClicked(MouseEvent e, UUID gameId, Session session, CardView card) {
		System.out.println("gameId:" + gameId);
		if (gameId != null)
			session.sendPlayerUUID(gameId, card.getId());
	}
}
