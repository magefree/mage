package mage.client.plugins.adapters;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import mage.cards.MagePermanent;
import mage.client.cards.BigCard;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ImageHelper;

public class MageMouseMotionAdapter extends MouseMotionAdapter {
	
	private Component parent;
	private BigCard bigCard; 
	
	public MageMouseMotionAdapter(Component parent, BigCard bigCard) {
		this.parent = parent;
		this.bigCard = bigCard;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!Plugins.getInstance().isCardPluginLoaded()) return;
		if (bigCard == null) return;
		Object o = parent.getComponentAt(e.getPoint());
		if (o instanceof MagePermanent) {
			MagePermanent card = (MagePermanent) o;
			if (card.getOriginal().getId() != bigCard.getCardId()) {
				Image image = card.getImage();
				if (image != null && image instanceof BufferedImage) {
					image = ImageHelper.getResizedImage((BufferedImage) image, bigCard.getWidth(), bigCard.getHeight());
					bigCard.setCard(card.getOriginal().getId(), image, card.getOriginal().getRules());
					bigCard.hideTextComponent();
				} else {
					//TODO: add description panel
				}
			}
		}
	}
}
