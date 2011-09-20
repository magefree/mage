/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * PlayerPanel.java
 *
 * Created on Nov 18, 2009, 3:01:31 PM
 */

package mage.client.game;

import mage.cards.MageCard;
import mage.cards.action.ActionCallback;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.components.HoverButton;
import mage.client.components.MageRoundPane;
import mage.client.dialog.ShowCardsDialog;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Command;
import mage.client.util.Config;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.components.ImagePanel;
import mage.remote.Session;
import mage.sets.Sets;
import mage.view.CardView;
import mage.view.ManaPoolView;
import mage.view.PlayerView;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enhanced player pane.
 *
 * @author nantuko
 */
public class PlayerPanelExt extends javax.swing.JPanel {

	private UUID playerId;
	private UUID gameId;
	private Session session;
	private PlayerView player;

	private ShowCardsDialog graveyard;
	private BigCard bigCard;

	private static final int AVATAR_COUNT = 77;

	private static final Border greenBorder = new LineBorder(Color.red, 3);
	private static final Border redBorder = new LineBorder(Color.red, 2);
	private static final Border emptyBorder = BorderFactory.createEmptyBorder(0,0,0,0);

	private static final Dimension topCardDimension = new Dimension(40, 56);

	private int avatarId = -1;

    /** Creates new form PlayerPanel */
    public PlayerPanelExt(boolean me) {
        initComponents(me);
    }

	public void init(UUID gameId, UUID playerId, BigCard bigCard) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.bigCard = bigCard;
		session = MageFrame.getSession();
		cheat.setVisible(session.isTestMode());
	}

	public void update(PlayerView player) {
		this.player = player;
		lifeLabel.setText(Integer.toString(player.getLife()));
		poisonLabel.setText(Integer.toString(player.getPoison()));
		handLabel.setText(Integer.toString(player.getHandCount()));
		libraryLabel.setText(Integer.toString(player.getLibraryCount()));
		graveLabel.setText(Integer.toString(player.getGraveyard().size()));

		int id = player.getUserData().getAvatarId();
		if (id > 0 && id != avatarId) {
			avatarId = id;
			String path = "/avatars/" + String.valueOf(avatarId) + ".jpg";
			if (avatarId == 64) {
				path = "/avatars/i64.jpg";
			} else if (avatarId >= 1000) {
				avatarId = avatarId - 1000;
				path = "/avatars/special/" + String.valueOf(avatarId) + ".gif";
			}
			Image image = ImageHelper.getImageFromResources(path);
			Rectangle r = new Rectangle(80, 80);
			BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
			this.avatar.update("player", resized, resized, resized, resized, r);
		}

		this.avatar.setText(player.getName());
		if (player.isActive()) {
			this.avatar.setBorder(greenBorder);
		} else if (player.hasLeft()) {
			this.avatar.setBorder(redBorder);
		} else {
			this.avatar.setBorder(emptyBorder);
		}

		synchronized (this) {
			if (player.getTopCard() != null) {
				if (topCard == null || !topCard.getId().equals(player.getTopCard().getId())) {
					if (topCard == null) {
						topCardPanel.setVisible(true);
					}
					topCard = player.getTopCard();
					topCardPanel.update(topCard);
					topCardPanel.updateImage();
					ActionCallback callback = Plugins.getInstance().getActionCallback();
					((MageActionCallback)callback).refreshSession();
					topCardPanel.updateCallback(callback, gameId);
				}
			} else if (topCard != null) {
				topCard = null;
				topCardPanel.setVisible(false);
			}
		}

		update(player.getManaPool());
	}

	protected void update(ManaPoolView pool) {
		manaLabels.get("B").setText(Integer.toString(pool.getBlack()));
		manaLabels.get("R").setText(Integer.toString(pool.getRed()));
		manaLabels.get("W").setText(Integer.toString(pool.getWhite()));
		manaLabels.get("G").setText(Integer.toString(pool.getGreen()));
		manaLabels.get("U").setText(Integer.toString(pool.getBlue()));
		manaLabels.get("X").setText(Integer.toString(pool.getColorless()));
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(boolean me) {
		setLayout(null);
		setOpaque(false);

		panelBackground = new MageRoundPane();
		panelBackground.setXOffset(3);
		panelBackground.setYOffset(3);
        panelBackground.setLayout(null);
        panelBackground.setVisible(true);
        panelBackground.setBounds(0, 0, 92, 250);
		add(panelBackground);

		Rectangle r = new Rectangle(80, 80);
		Image image = ImageHelper.getImageFromResources("/avatars/unknown.jpg");

		topCardPanel = Plugins.getInstance().getMageCard(new CardView(Sets.findCard("Forest")), bigCard, topCardDimension, gameId, true);
		topCardPanel.setVisible(false);
		panelBackground.add(topCardPanel);

		// Avatar
		BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
		avatar = new HoverButton("player", resized, resized, resized, r);
		avatar.setBounds(6, 6, r.width, r.height);
		panelBackground.add(avatar);
		avatar.setObserver(new Command() {
			@Override
			public void execute() {
				session.sendPlayerUUID(gameId, playerId);
			}
		});

		// Life count
		lifeLabel = new JLabel();
		lifeLabel.setBounds(30, 82, 30, 30);
		r = new Rectangle(18, 18);
		life = (ImagePanel)addParam(panelBackground, "Life", lifeLabel, r, "/info/life.png", false);
		life.setBounds(9, 90, r.width, r.height);

		// Hand count
		handLabel = new JLabel();
		handLabel.setBounds(70, 82, 50, 30);
		r = new Rectangle(18, 18);
		hand = (ImagePanel)addParam(panelBackground, "Hand", handLabel, r, "/info/hand.png", false);
		hand.setBounds(48, 90, r.width, r.height);

		// Poison count
		poisonLabel = new JLabel();
		poisonLabel.setText("0");
		poisonLabel.setBounds(30, 112, 20, 20);
		//poisonLabel.setBorder(greenBorder);
		r = new Rectangle(14, 14);
		poison = (ImagePanel)addParam(panelBackground, "Poison", poisonLabel, r, "/info/poison.png", false);
		poison.setBounds(12, 116, r.width, r.height);

		// Library count
		libraryLabel = new JLabel();
		libraryLabel.setBounds(70, 107, 30, 30);
		r = new Rectangle(19, 19);
		library = (ImagePanel)addParam(panelBackground, "Library", libraryLabel, r, "/info/library.png", false);
		library.setBounds(48, 113, r.width, r.height);

		// Grave count and open graveyard button
		graveLabel = new JLabel();
		r = new Rectangle(21, 21);
		graveLabel.setBounds(35, 250 - r.height - 5, r.width, r.height);
		grave = (HoverButton)addParam(panelBackground, "Graveyard", graveLabel, r, "/info/grave.png", true);
		grave.setBounds(9, 250 - r.height - 5, r.width, r.height);
		grave.setObserver(new Command() {
			@Override
			public void execute() {
				btnGraveActionPerformed(null);
			}
		});

		// Cheat button
		r = new Rectangle(25, 21);
		image = ImageHelper.getImageFromResources("/info/cheat.png");
		resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
		cheat = new JButton();
		cheat.setIcon(new ImageIcon(resized));
		panelBackground.add(cheat);
		cheat.setBounds(55, 250 - r.height - 5, r.width, r.height);
		cheat.setToolTipText("Cheat button");
		cheat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCheatActionPerformed(e);
			}
		});

		// Add mana symbols
		addManaImagePanel("W", new Rectangle(11, 140, 15, 15), panelBackground);
		addManaImagePanel("U", new Rectangle(11, 160, 15, 15), panelBackground);
		addManaImagePanel("B", new Rectangle(11, 180, 15, 15), panelBackground);
		addManaImagePanel("R", new Rectangle(50, 140, 15, 15), panelBackground);
		addManaImagePanel("G", new Rectangle(50, 160, 15, 15), panelBackground);
		addManaImagePanel("X", new Rectangle(50, 180, 15, 15), panelBackground);
    }

	private void addManaImagePanel(String mana, Rectangle rect, JPanel container) {
		BufferedImage imageMana = ManaSymbols.getManaSymbolImageSmall(mana);
		if (imageMana != null) {
			ImagePanel manaB = new ImagePanel(imageMana, ImagePanel.ACTUAL);
			manaB.setBounds(rect.x, rect.y, rect.width, rect.height);
			manaB.setOpaque(false);
			container.add(manaB);
		}
		JLabel manaCountLabel = new JLabel();
		manaCountLabel.setText("0");
		manaCountLabel.setBounds(rect.x + rect.width + 5, rect.y - 8, 30, 30);
		container.add(manaCountLabel);
		manaLabels.put(mana, manaCountLabel);
	}

	/**
	 * Adds image panel and label to the container panel.
	 *
	 * @param containerPanel
	 * @param text
	 * @param r
	 * @param imagePath
	 * @return
	 */
	private JComponent addParam(JPanel containerPanel, String tooltip, JLabel text, Rectangle r, String imagePath, boolean isButton) {
		if (text != null) {
			text.setForeground(Color.black);
			containerPanel.add(text);
			text.setToolTipText(tooltip);
		}

		Image image = ImageHelper.getImageFromResources(imagePath);
		BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
		JComponent component = null;
		if (isButton) {
		 	component = new HoverButton(null, resized, resized, resized, r);
		} else {
			component = new ImagePanel(resized, ImagePanel.ACTUAL);
		}
		component.setToolTipText(tooltip);
		component.setOpaque(false);
		containerPanel.add(component);

		return component;
	}

	private void btnGraveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraveActionPerformed
		if (graveyard == null) {
			graveyard = new ShowCardsDialog();
		}
		graveyard.loadCards(player.getName() + " graveyard", player.getGraveyard(), bigCard, Config.dimensions, gameId, false);
	}

	private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheatActionPerformed
		try {
			session.cheat(gameId, playerId, Sets.loadDeck("cheat.dck"));
		} catch (FileNotFoundException ex) {
			Logger.getLogger(PlayAreaPanel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(PlayAreaPanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private HoverButton avatar;
	private ImagePanel life;
	private ImagePanel poison;
	private ImagePanel hand;
	private HoverButton grave;
	private ImagePanel library;
	private CardView topCard;
	private MageCard topCardPanel;
	private JButton cheat;
	private MageRoundPane panelBackground;

	private JLabel lifeLabel;
	private JLabel handLabel;
	private JLabel libraryLabel;
	private JLabel poisonLabel;
	private JLabel graveLabel;

	private Map<String, JLabel> manaLabels = new HashMap<String, JLabel>();
}
