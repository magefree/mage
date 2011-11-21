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
import javax.swing.GroupLayout.Alignment;

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
    public PlayerPanelExt() {
        initComponents();
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

        if (!MageFrame.isLite()) {
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
    private void initComponents() {
		
    	panelBackground = new MageRoundPane();
		Rectangle r = new Rectangle(80, 80);
		lifeLabel = new JLabel();
		handLabel = new JLabel();
		poisonLabel = new JLabel();
		libraryLabel = new JLabel();
		setOpaque(false);

		panelBackground.setXOffset(3);
		panelBackground.setYOffset(3);
        panelBackground.setVisible(true);

		// Avatar
		Image image = ImageHelper.getImageFromResources("/avatars/unknown.jpg");
		BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
		avatar = new HoverButton("player", resized, resized, resized, r);
		avatar.setObserver(new Command() {
			@Override
			public void execute() {
				session.sendPlayerUUID(gameId, playerId);
			}
		});
		r = new Rectangle(18, 18);
		lifeLabel.setToolTipText("Life");
		Image imageLife = ImageHelper.getImageFromResources("/info/life.png");
		BufferedImage resizedLife = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageLife, BufferedImage.TYPE_INT_ARGB), r);
		life = new ImagePanel(resizedLife, ImagePanel.ACTUAL);
		life.setToolTipText("Life");
		life.setOpaque(false);
		r = new Rectangle(18, 18);
		handLabel.setToolTipText("Hand");
		Image imageHand = ImageHelper.getImageFromResources("/info/hand.png");
		BufferedImage resizedHand = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageHand, BufferedImage.TYPE_INT_ARGB), r);
		hand = new ImagePanel(resizedHand, ImagePanel.ACTUAL);
		hand.setToolTipText("Hand");
		hand.setOpaque(false);

		// Poison count
		poisonLabel.setText("0");
		r = new Rectangle(14, 14);
		poisonLabel.setToolTipText("Poison");
		Image imagePoison = ImageHelper.getImageFromResources("/info/poison.png");
		BufferedImage resizedPoison = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imagePoison, BufferedImage.TYPE_INT_ARGB), r);
		poison = new ImagePanel(resizedPoison, ImagePanel.ACTUAL);
		poison.setToolTipText("Poison");
		poison.setOpaque(false);
		r = new Rectangle(19, 19);
		libraryLabel.setToolTipText("Library");
		Image imageLibrary = ImageHelper.getImageFromResources("/info/library.png");
		BufferedImage resizedLibrary = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageLibrary, BufferedImage.TYPE_INT_ARGB), r);
		library = new ImagePanel(resizedLibrary, ImagePanel.ACTUAL);
		library.setToolTipText("Library");
		library.setOpaque(false);

		// Grave count and open graveyard button
		graveLabel = new JLabel();
		r = new Rectangle(21, 21);
		graveLabel.setToolTipText("Graveyard");
		Image imageGrave = ImageHelper.getImageFromResources("/info/grave.png");
		BufferedImage resizedGrave = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageGrave, BufferedImage.TYPE_INT_ARGB), r);
		grave = new HoverButton(null, resizedGrave, resizedGrave, resizedGrave, r);
		grave.setToolTipText("Graveyard");
		grave.setOpaque(false);
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
		cheat.setToolTipText("Cheat button");
		cheat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCheatActionPerformed(e);
			}
		});

		// Add mana symbols
		BufferedImage imageManaW = ManaSymbols.getManaSymbolImageSmall("W");
		ImagePanel manaW = new ImagePanel(imageManaW, ImagePanel.ACTUAL);
		manaW.setOpaque(false);
		JLabel manaCountLabelW = new JLabel();
		manaCountLabelW.setText("0");
		manaLabels.put("W", manaCountLabelW);

		BufferedImage imageManaU = ManaSymbols.getManaSymbolImageSmall("U");
		ImagePanel manaU = new ImagePanel(imageManaU, ImagePanel.ACTUAL);
		manaU.setOpaque(false);
		JLabel manaCountLabelU = new JLabel();
		manaCountLabelU.setText("0");
		manaLabels.put("U", manaCountLabelU);

		BufferedImage imageManaB = ManaSymbols.getManaSymbolImageSmall("B");
		ImagePanel manaB = new ImagePanel(imageManaB, ImagePanel.ACTUAL);
		manaB.setOpaque(false);
		JLabel manaCountLabelB = new JLabel();
		manaCountLabelB.setText("0");
		manaLabels.put("B", manaCountLabelB);

		BufferedImage imageManaR = ManaSymbols.getManaSymbolImageSmall("R");
		ImagePanel manaR = new ImagePanel(imageManaR, ImagePanel.ACTUAL);
		manaR.setOpaque(false);
		JLabel manaCountLabelR = new JLabel();
		manaCountLabelR.setText("0");
		manaLabels.put("R", manaCountLabelR);

		BufferedImage imageManaG = ManaSymbols.getManaSymbolImageSmall("G");
		ImagePanel manaG = new ImagePanel(imageManaG, ImagePanel.ACTUAL);
		manaG.setOpaque(false);
		JLabel manaCountLabelG = new JLabel();
		manaCountLabelG.setText("0");
		manaLabels.put("G", manaCountLabelG);

		BufferedImage imageManaX = ManaSymbols.getManaSymbolImageSmall("X");
		ImagePanel manaX = new ImagePanel(imageManaX, ImagePanel.ACTUAL);
		manaX.setOpaque(false);
		JLabel manaCountLabelX = new JLabel();
		manaCountLabelX.setText("0");
		manaLabels.put("X", manaCountLabelX);
		GroupLayout gl_panelBackground = new GroupLayout(panelBackground);
		gl_panelBackground.setHorizontalGroup(
			gl_panelBackground.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBackground.createSequentialGroup()
					.addGap(6)
					.addComponent(avatar, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panelBackground.createSequentialGroup()
					.addGap(9)
					.addComponent(life, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(18)
							.addComponent(hand, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(lifeLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(4)
					.addComponent(handLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panelBackground.createSequentialGroup()
					.addGap(9)
					.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(3)
							.addComponent(poison, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(2)
							.addComponent(manaW, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(2)
							.addComponent(manaU, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(2)
							.addComponent(manaB, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addComponent(grave, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGap(18)
									.addComponent(library, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
								.addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGap(20)
									.addComponent(manaR, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGap(1)
									.addComponent(manaCountLabelW, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
							.addGap(3)
							.addComponent(manaCountLabelR, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
								.addComponent(manaCountLabelB, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGap(19)
									.addComponent(manaX, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
							.addGap(5)
							.addComponent(manaCountLabelX, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(20)
							.addComponent(manaG, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(40)
							.addComponent(manaCountLabelG, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(40)
							.addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(18)
							.addComponent(cheat, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(5)
							.addComponent(graveLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(1)
							.addComponent(manaCountLabelU, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
		);
		gl_panelBackground.setVerticalGroup(
			gl_panelBackground.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBackground.createSequentialGroup()
					.addGap(6)
					.addComponent(avatar, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(1)
							.addComponent(life, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(1)
							.addComponent(hand, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(lifeLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(handLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(1)
					.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(4)
							.addComponent(poison, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(manaW, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(manaU, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(manaB, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(grave, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelBackground.createSequentialGroup()
											.addGap(1)
											.addComponent(library, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
										.addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
									.addGap(2)
									.addComponent(manaR, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGap(14)
									.addComponent(manaCountLabelW, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGap(14)
									.addComponent(manaCountLabelR, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
							.addGap(4)
							.addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
								.addComponent(manaCountLabelB, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelBackground.createSequentialGroup()
									.addGap(8)
									.addComponent(manaX, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
								.addComponent(manaCountLabelX, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(39)
							.addComponent(manaG, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(31)
							.addComponent(manaCountLabelG, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(76)
							.addComponent(cheat, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(76)
							.addComponent(graveLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBackground.createSequentialGroup()
							.addGap(31)
							.addComponent(manaCountLabelU, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
		);
		panelBackground.setLayout(gl_panelBackground);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelBackground, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelBackground, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
		);
		setLayout(groupLayout);

    }

    public void sizePlayerPanel() {
    	Dimension r = new Dimension(60, 60);
    	this.avatar.setSize(r);
    	this.avatar.setPreferredSize(r);
    }
    
//	private void addManaImagePanel(String mana, Rectangle rect, JPanel container) {
//		BufferedImage imageMana = ManaSymbols.getManaSymbolImageSmall(mana);
//		if (imageMana != null) {
//			ImagePanel manaB = new ImagePanel(imageMana, ImagePanel.ACTUAL);
//			manaB.setBounds(rect.x, rect.y, rect.width, rect.height);
//			manaB.setOpaque(false);
//			container.add(manaB);
//		}
//		JLabel manaCountLabel = new JLabel();
//		manaCountLabel.setText("0");
//		manaCountLabel.setBounds(rect.x + rect.width + 5, rect.y - 8, 30, 30);
//		container.add(manaCountLabel);
//		manaLabels.put(mana, manaCountLabel);
//	}

	/**
	 * Adds image panel and label to the container panel.
	 *
	 * @param containerPanel
	 * @param text
	 * @param r
	 * @param imagePath
	 * @return
	 */
//	private JComponent addParam(JPanel containerPanel, String tooltip, JLabel text, Rectangle r, String imagePath, boolean isButton) {
//		if (text != null) {
//			text.setForeground(Color.black);
//			containerPanel.add(text);
//			text.setToolTipText(tooltip);
//		}
//
//		Image image = ImageHelper.getImageFromResources(imagePath);
//		BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
//		JComponent component = null;
//		if (isButton) {
//		 	component = new HoverButton(null, resized, resized, resized, r);
//		} else {
//			component = new ImagePanel(resized, ImagePanel.ACTUAL);
//		}
//		component.setToolTipText(tooltip);
//		component.setOpaque(false);
//		containerPanel.add(component);
//
//		return component;
//	}

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
