package org.mage.plugins.rating;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import mage.cards.Card;
import mage.cards.MageCard;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;

import org.jdesktop.swingx.JXPanel;
import org.mage.plugins.rating.ui.BigCard;
import org.mage.plugins.rating.ui.GuiDisplayUtil;
import org.mage.plugins.rating.ui.ImageHelper;

public class RateCallback implements ActionCallback {

	private Card card1;
	private Card card2;
	private RateThread callback;
	private BigCard bigCard;
	
	public RateCallback(Card card1, Card card2, RateThread callback, BigCard bigCard) {
		this.card1 = card1;
		this.card2 = card2;
		this.callback = callback;
		this.bigCard = bigCard;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0, TransferData arg1) {
	}
	
	@Override
	public void mousePressed(MouseEvent arg0, TransferData arg1) {
		this.callback.reportResult(card1, card2);
	}

	@Override
	public void mouseEntered(MouseEvent arg0, TransferData arg1) {
		MageCard card = (MageCard)arg1.component;
		Image image = card.getImage();
		if (image != null && image instanceof BufferedImage) {
			image = ImageHelper.getResizedImage((BufferedImage) image, bigCard.getWidth(), bigCard.getHeight());
			bigCard.setCard(card.getOriginal().getId(), image, card.getOriginal().getRules());
			bigCard.showTextComponent();
			if (card.getOriginal().isAbility()) {
				bigCard.showTextComponent();
			} else {
				bigCard.hideTextComponent();
			};  
		} else {
			JXPanel panel = GuiDisplayUtil.getDescription(card.getOriginal(), bigCard.getWidth(), bigCard.getHeight());
			panel.setVisible(true);
			bigCard.hideTextComponent();
			bigCard.addJXPanel(card.getOriginal().getId(), panel);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0, TransferData arg1) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0, TransferData arg1) {
	}

}
