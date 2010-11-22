package mage.client.plugins.adapters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;

import javax.swing.Popup;
import javax.swing.PopupFactory;

import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.game.PlayAreaPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.remote.Session;
import mage.client.util.DefaultActionCallback;
import mage.client.util.ImageHelper;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.GuiDisplayUtil;

import org.jdesktop.swingx.JXPanel;

public class MageActionCallback implements ActionCallback {

	private Popup popup;
	private Component parent;
	private BigCard bigCard; 
	protected static DefaultActionCallback defaultCallback = DefaultActionCallback.getInstance();
	protected static Session session = MageFrame.getSession();
	
	public MageActionCallback() {
	}
	
	public void setCardPreviewComponent(BigCard bigCard) {
		this.bigCard = bigCard;
	}
	
	public void refreshSession() {
		if (session == null) {
			session = MageFrame.getSession();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e, TransferData data) {
		data.component.requestFocusInWindow();
		defaultCallback.mouseClicked(e, data.gameId, session, data.card);
	}

	@Override
	public void mouseEntered(MouseEvent e, TransferData data) {
		if (popup != null)
			popup.hide();
		PopupFactory factory = PopupFactory.getSharedInstance();
		popup = factory.getPopup(data.component, data.popupText, (int) data.locationOnScreen.getX() + data.popupOffsetX, (int) data.locationOnScreen.getY() + data.popupOffsetY + 40);
		popup.show();
		//hack to get popup to resize to fit text
		popup.hide();
		popup = factory.getPopup(data.component, data.popupText, (int) data.locationOnScreen.getX() + data.popupOffsetX, (int) data.locationOnScreen.getY() + data.popupOffsetY + 40);
		popup.show();
		
		// Draw Arrows for targets
		List<UUID> targets = data.card.getTargets();
		if (targets != null) {
			for (UUID uuid : targets) {
				//System.out.println("Getting play area panel for uuid: " + uuid);
				
				PlayAreaPanel p = session.getGame().getPlayers().get(uuid);
				if (p != null) {
					Point target = p.getLocationOnScreen();
					Point me = data.locationOnScreen;
					ArrowBuilder.addArrow((int)me.getX() + 35, (int)me.getY(), (int)target.getX() + 40, (int)target.getY() - 40, Color.red);
				} else {
					for (PlayAreaPanel pa : session.getGame().getPlayers().values()) {
						MagePermanent permanent = pa.getBattlefieldPanel().getPermanents().get(uuid);
						if (permanent != null) {
							Point target = permanent.getLocationOnScreen();
							Point me = data.locationOnScreen;
							ArrowBuilder.addArrow((int)me.getX() + 35, (int)me.getY(), (int)target.getX() + 40, (int)target.getY() + 10, Color.red);
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e, TransferData data) {
		if (!Plugins.getInstance().isCardPluginLoaded()) {return;}
		if (bigCard == null) {return;}

		MageCard card = (MageCard) data.component;
		if (card.getOriginal().getId() != bigCard.getCardId()) {
			synchronized (MageMouseAdapter.class) {
				if (card.getOriginal().getId() != bigCard.getCardId()) {
					Image image = card.getImage();
					if (image != null && image instanceof BufferedImage) {
						image = ImageHelper.getResizedImage((BufferedImage) image, bigCard.getWidth(), bigCard.getHeight());
						bigCard.setCard(card.getOriginal().getId(), image, card.getOriginal().getRules());
						bigCard.hideTextComponent();
					} else {
						JXPanel panel = GuiDisplayUtil.getDescription(card.getOriginal(), bigCard.getWidth(), bigCard.getHeight());
						panel.setVisible(true);
						bigCard.hideTextComponent();
						bigCard.addJXPanel(card.getOriginal().getId(), panel);
					}
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (popup != null) {
			popup.hide();
			ArrowBuilder.removeAllArrows();
		}
	}

}
