package mage.client.plugins.adapters;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

import mage.cards.MagePermanent;
import mage.client.MageFrame;
import mage.client.plugins.impl.Plugins;
import mage.client.util.DefaultActionCallback;

public class MageMouseAdapter extends MouseAdapter {
	
	private Component parent;
	private UUID gameId;
	protected static DefaultActionCallback defaultCallback = DefaultActionCallback.getInstance();	
	
	public MageMouseAdapter(Component parent, UUID gameId) {
		this.parent = parent;
		this.gameId = gameId;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (!Plugins.getInstance().isCardPluginLoaded())
			return;
		if (e.getButton() == MouseEvent.BUTTON1) {
			int count = e.getClickCount();
			if (count > 0) {
				Object o = parent.getComponentAt(e.getPoint());
				if (o instanceof MagePermanent) {
					MagePermanent selectedCard = (MagePermanent) o;
					// TODO: uncomment when attached cards works in plugin
					/*
					 * int x = e.getX() - selectedCard.getX(); int y = e.getY()
					 * - selectedCard.getY(); CardView card =
					 * selectedCard.getCardByPosition(x, y);
					 */
					defaultCallback.mouseClicked(e, gameId, MageFrame.getSession(), selectedCard.getOriginal());
				}
			}
		}
	}
}
