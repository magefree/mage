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

import mage.MageException;
import mage.cards.MageCard;
import mage.cards.action.ActionCallback;
import mage.cards.decks.importer.DckDeckImporter;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.components.HoverButton;
import mage.client.components.MageRoundPane;
import mage.client.components.ext.dlg.DialogManager;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.CardsViewUtil;
import mage.client.util.Command;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.components.ImagePanel;
import mage.constants.ManaType;
import mage.remote.Session;
import mage.utils.timer.PriorityTimer;
import mage.view.CardView;
import mage.view.ManaPoolView;
import mage.view.PlayerView;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

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

    private BigCard bigCard;

    private static final int AVATAR_COUNT = 77;
    
    private static final String DEFAULT_AVATAR_PATH = "/avatars/51.jpg";
    
    private static final int PANEL_WIDTH = 94;
    private static final int PANEL_HEIGHT = 242;
    private static final int PANEL_HEIGHT_SMALL = 212;
    private static final int MANA_LABEL_SIZE_HORIZONTAL = 20;


    private static final Border greenBorder = new LineBorder(Color.green, 3);
    private static final Border redBorder = new LineBorder(Color.red, 2);
    private static final Border emptyBorder = BorderFactory.createEmptyBorder(0,0,0,0);

    private static final Dimension topCardDimension = new Dimension(40, 56);
    
    private int avatarId = -1;

    private PriorityTimer timer;

    /** Creates new form PlayerPanel */
    public PlayerPanelExt() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        initComponents();
    }

    public void init(UUID gameId, UUID playerId, BigCard bigCard, int priorityTime) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.bigCard = bigCard;
        session = MageFrame.getSession();
        cheat.setVisible(session.isTestMode());
        cheat.setFocusable(false);

        if (priorityTime > 0) {
            long delay = 1000L;

            timer = new PriorityTimer(priorityTime, delay, new mage.interfaces.Action() {
                @Override
                public void execute() throws MageException {
                    // do nothing
                }
            });
            final PriorityTimer pt = timer;
            timer.setTaskOnTick(new mage.interfaces.Action() {
                @Override
                public void execute() throws MageException {
                    int priorityTimeValue = pt.getCount();
                    String text = getPriorityTimeLeftString(priorityTimeValue);
                    PlayerPanelExt.this.avatar.setTopText(text);
                    PlayerPanelExt.this.timerLabel.setText(text);
                    PlayerPanelExt.this.avatar.repaint();
                }
            });
            timer.init(gameId);
        }
    }

    public void cleanUp() {
        if (timer != null) {
            this.timer.cancel();
        }
        topCardPanel.updateCallback(null, gameId);
    }

    public void update(PlayerView player) {
        this.player = player;
        int playerLife = player.getLife();
        if (playerLife > 99) {
            Font font = lifeLabel.getFont();
            font = font.deriveFont(9f);
            lifeLabel.setFont(font);
            changedFontLife = true;
        } else if (changedFontLife) {
            Font font = lifeLabel.getFont();
            font = font.deriveFont(12f);
            lifeLabel.setFont(font);
            changedFontLife = false;
        }
        lifeLabel.setText(Integer.toString(playerLife));
        poisonLabel.setText(Integer.toString(player.getPoison()));
        handLabel.setText(Integer.toString(player.getHandCount()));
        int libraryCards = player.getLibraryCount();
        if (libraryCards > 99) {
            Font font = libraryLabel.getFont();
            font = font.deriveFont(9f);
            libraryLabel.setFont(font);
            changedFontLibrary = true;
        } else if (changedFontLibrary) {
            Font font = libraryLabel.getFont();
            font = font.deriveFont(12f);
            libraryLabel.setFont(font);
            changedFontLibrary = false;
        }
        libraryLabel.setText(Integer.toString(libraryCards));

        int graveCards = player.getGraveyard().size();
        if (graveCards > 99) {
            if (!changedFontGrave) {
                Font font = graveLabel.getFont();
                font = font.deriveFont(9f);
                graveLabel.setFont(font);
                changedFontGrave = true;
            }
        } else if (changedFontGrave) {
            Font font = lifeLabel.getFont();
            font = font.deriveFont(12f);
            graveLabel.setFont(font);
            changedFontGrave = false;
        }
        graveLabel.setText(Integer.toString(graveCards));

        int exileCards = player.getExile().size();
        if (exileCards > 99) {
            if (!changedFontExile) {
                Font font = exileLabel.getFont();
                font = font.deriveFont(9f);
                exileLabel.setFont(font);
                changedFontExile = true;
            }
        } else if (changedFontExile) {
            Font font = lifeLabel.getFont();
            font = font.deriveFont(12f);
            exileLabel.setFont(font);
            changedFontExile = false;
        }
        exileLabel.setText(Integer.toString(exileCards));

        if (!MageFrame.isLite()) {
            int id = player.getUserData().getAvatarId();
            if (id <= 0) {
                id = PreferencesDialog.DEFAULT_AVATAR_ID;
            }
            if (id != avatarId) {
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
        if (this.timer != null) {
            if (player.getPriorityTimeLeft() != Integer.MAX_VALUE) {
                String priorityTimeValue = getPriorityTimeLeftString(player);
                this.timer.setCount(player.getPriorityTimeLeft());
                this.avatar.setTopText(priorityTimeValue);
                this.timerLabel.setText(priorityTimeValue);
            }
            if (player.isTimerActive()) {
                this.timer.resume();
            } else {
                this.timer.pause();
            }
        }

        this.btnPlayer.setText(player.getName());
        this.btnPlayer.setToolTipText("Life: " + player.getLife());
        if (player.isActive()) {
            this.avatar.setBorder(greenBorder);
            this.btnPlayer.setBorder(greenBorder);
        } else if (player.hasLeft()) {
            this.avatar.setBorder(redBorder);
            this.btnPlayer.setBorder(redBorder);
        } else {
            this.avatar.setBorder(emptyBorder);
            this.btnPlayer.setBorder(emptyBorder);
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

    private String getPriorityTimeLeftString(PlayerView player) {
        int priorityTimeLeft = player.getPriorityTimeLeft();
        return getPriorityTimeLeftString(priorityTimeLeft);
    }

    private String getPriorityTimeLeftString(int priorityTimeLeft) {
        int h = priorityTimeLeft / 3600;
        int m = (priorityTimeLeft % 3600) / 60;
        int s = priorityTimeLeft % 60;
        return (h < 10 ? "0" : "") + h + ":" + (m < 10 ? "0" : "") + m  + ":" + (s < 10 ? "0" : "") + s;
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
        panelBackground.setPreferredSize(new Dimension(PANEL_WIDTH - 2, PANEL_HEIGHT));
        Rectangle r = new Rectangle(80, 80);
        timerLabel = new JLabel();
        lifeLabel = new JLabel();
        handLabel = new JLabel();
        poisonLabel = new JLabel();
        libraryLabel = new JLabel();
        setOpaque(false);

        panelBackground.setXOffset(3);
        panelBackground.setYOffset(3);
        panelBackground.setVisible(true);

        // Avatar
        Image image = ImageHelper.getImageFromResources(DEFAULT_AVATAR_PATH);

        topCardPanel = Plugins.getInstance().getMageCard(new CardView(CardRepository.instance.findCard("Forest").getMockCard()), bigCard, topCardDimension, gameId, true);
        topCardPanel.setVisible(false);
        panelBackground.add(topCardPanel);

        BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        avatar = new HoverButton("player", resized, resized, resized, r);
        String showPlayerNamePermanently = MageFrame.getPreferences().get(PreferencesDialog.KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true");
        if (showPlayerNamePermanently.equals("true")) {
            avatar.setTextAlwaysVisible(true);
        }
        avatar.setTextOffsetButtonY(10);
        avatar.setObserver(new Command() {
            @Override
            public void execute() {
                session.sendPlayerUUID(gameId, playerId);
            }
        });
        // timer area /small layout)
        timerLabel.setToolTipText("Time left");
        timerLabel.setSize(80,12);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // life area
        r = new Rectangle(18, 18);
        lifeLabel.setToolTipText("Life");
        Image imageLife = ImageHelper.getImageFromResources("/info/life.png");
        BufferedImage resizedLife = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageLife, BufferedImage.TYPE_INT_ARGB), r);
        life = new ImagePanel(resizedLife, ImagePanel.ACTUAL);
        life.setToolTipText("Life");
        life.setOpaque(false);
        // hand area
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
        
        // Library
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

        exileLabel = new JLabel();
        exileLabel.setToolTipText("Exile");
        image = ImageHelper.getImageFromResources("/info/exile.png");
        r = new Rectangle(21, 21);
        resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        exileZone = new HoverButton(null, resized, resized, resized, r);
        exileZone.setToolTipText("Exile");
        exileZone.setOpaque(false);
        exileZone.setObserver(new Command() {
            @Override
            public void execute() {
                btnExileZoneActionPerformed(null);
            }
        });
        exileZone.setBounds(25, 0, 21, 21);

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

        zonesPanel = new JPanel();
        zonesPanel.setPreferredSize(new Dimension(100, 20));
        zonesPanel.setSize(100, 20);
        zonesPanel.setLayout(null);

        image = ImageHelper.getImageFromResources("/info/command_zone.png");
        r = new Rectangle(21, 21);
        resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        commandZone = new HoverButton(null, resized, resized, resized, r);
        commandZone.setToolTipText("Command Zone (Commander and Emblems)");
        commandZone.setOpaque(false);
        commandZone.setObserver(new Command() {
            @Override
            public void execute() {
                btnCommandZoneActionPerformed(null);
            }
        });
        commandZone.setBounds(5, 0, 21, 21);
        zonesPanel.add(commandZone);

        cheat.setBounds(28, 0, 25, 21);
        zonesPanel.add(cheat);

        btnPlayer = new JButton();
        btnPlayer.setText("Player");
        btnPlayer.setVisible(false);
        btnPlayer.setToolTipText("Player");
        btnPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                session.sendPlayerUUID(gameId, playerId);
            }
        });

        // Add mana symbols
        JLabel manaCountLabelW = new JLabel();
        manaCountLabelW.setToolTipText("White mana");
        manaCountLabelW.setText("0");
        manaLabels.put("W", manaCountLabelW);
        r = new Rectangle(12, 12);
        BufferedImage imageManaW = ManaSymbols.getManaSymbolImageSmall("W");
        HoverButton btnWhiteMana = new HoverButton(null, imageManaW, imageManaW, imageManaW, r);
        btnWhiteMana.setToolTipText("White mana");
        btnWhiteMana.setOpaque(false);
        btnWhiteMana.setObserver(new Command() {
            @Override
            public void execute() {
                btnManaActionPerformed(ManaType.WHITE);
            }
        });

        JLabel manaCountLabelU = new JLabel();
        manaCountLabelU.setToolTipText("Blue mana");
        manaCountLabelU.setText("0");
        manaLabels.put("U", manaCountLabelU);
        r = new Rectangle(12, 12);
        BufferedImage imageManaU = ManaSymbols.getManaSymbolImageSmall("U");
        HoverButton btnBlueMana = new HoverButton(null, imageManaU, imageManaU, imageManaU, r);
        btnBlueMana.setToolTipText("Blue mana");
        btnBlueMana.setOpaque(false);
        btnBlueMana.setObserver(new Command() {
            @Override
            public void execute() {
                btnManaActionPerformed(ManaType.BLUE);
            }
        });

        JLabel manaCountLabelB = new JLabel();
        manaCountLabelB.setToolTipText("Black mana");
        manaCountLabelB.setText("0");
        manaLabels.put("B", manaCountLabelB);
        r = new Rectangle(12, 12);
        BufferedImage imageManaB = ManaSymbols.getManaSymbolImageSmall("B");
        HoverButton btnBlackMana = new HoverButton(null, imageManaB, imageManaB, imageManaB, r);
        btnBlackMana.setToolTipText("Black mana");
        btnBlackMana.setOpaque(false);
        btnBlackMana.setObserver(new Command() {
            @Override
            public void execute() {
                btnManaActionPerformed(ManaType.BLACK);
            }
        });

        JLabel manaCountLabelR = new JLabel();
        manaCountLabelR.setToolTipText("Red mana");
        manaCountLabelR.setText("0");
        manaLabels.put("R", manaCountLabelR);
        r = new Rectangle(12, 12);
        BufferedImage imageManaR = ManaSymbols.getManaSymbolImageSmall("R");
        HoverButton btnRedMana = new HoverButton(null, imageManaR, imageManaR, imageManaR, r);
        btnRedMana.setToolTipText("Red mana");
        btnRedMana.setOpaque(false);
        btnRedMana.setObserver(new Command() {
            @Override
            public void execute() {
                btnManaActionPerformed(ManaType.RED);
            }
        });

        JLabel manaCountLabelG = new JLabel();
        manaCountLabelG.setToolTipText("Green mana");
        manaCountLabelG.setText("0");
        manaLabels.put("G", manaCountLabelG);
        r = new Rectangle(12, 12);
        BufferedImage imageManaG = ManaSymbols.getManaSymbolImageSmall("G");
        HoverButton btnGreenMana = new HoverButton(null, imageManaG, imageManaG, imageManaG, r);
        btnGreenMana.setToolTipText("Green mana");
        btnGreenMana.setOpaque(false);
        btnGreenMana.setObserver(new Command() {
            @Override
            public void execute() {
                btnManaActionPerformed(ManaType.GREEN);
            }
        });

        JLabel manaCountLabelX = new JLabel();
        manaCountLabelX.setToolTipText("Colorless mana");
        manaCountLabelX.setText("0");
        manaLabels.put("X", manaCountLabelX);
        r = new Rectangle(12, 12);
        BufferedImage imageManaX = ManaSymbols.getManaSymbolImageSmall("X");
        HoverButton btnColorlessMana = new HoverButton(null, imageManaX, imageManaX, imageManaX, r);
        btnColorlessMana.setToolTipText("Colorless mana");
        btnColorlessMana.setOpaque(false);
        btnColorlessMana.setObserver(new Command() {
            @Override
            public void execute() {
                btnManaActionPerformed(ManaType.COLORLESS);
            }
        });

        GroupLayout gl_panelBackground = new GroupLayout(panelBackground);
        gl_panelBackground.setHorizontalGroup(
                gl_panelBackground.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(9)
                                .addComponent(life, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                .addGap(3)
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(18)
                                                .addComponent(hand, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lifeLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
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
                                                        .addComponent(btnWhiteMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(gl_panelBackground.createSequentialGroup()
                                                        .addGap(2)
                                                        .addComponent(btnBlueMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(gl_panelBackground.createSequentialGroup()
                                                        .addGap(2)
                                                        .addComponent(btnBlackMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                                .addComponent(grave, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                )
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                .addGap(18)
                                                                .addComponent(library, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                .addGap(20)
                                                                .addComponent(btnRedMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                .addGap(1)
                                                                .addComponent(manaCountLabelW, GroupLayout.PREFERRED_SIZE, MANA_LABEL_SIZE_HORIZONTAL, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(3)
                                                .addComponent(manaCountLabelR, GroupLayout.PREFERRED_SIZE, MANA_LABEL_SIZE_HORIZONTAL, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(manaCountLabelB, GroupLayout.PREFERRED_SIZE, MANA_LABEL_SIZE_HORIZONTAL, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                .addGap(19)
                                                                .addComponent(btnColorlessMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(5)
                                                .addComponent(manaCountLabelX, GroupLayout.PREFERRED_SIZE, MANA_LABEL_SIZE_HORIZONTAL, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(20)
                                                .addComponent(btnGreenMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(40)
                                                .addComponent(manaCountLabelG, GroupLayout.PREFERRED_SIZE, MANA_LABEL_SIZE_HORIZONTAL, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(40)
                                                .addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        /*.addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(18)
                                                .addComponent(cheat, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))*/
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(18)
                                                .addComponent(exileZone, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                                )
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(5)
                                                .addComponent(graveLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(40)
                                                .addComponent(exileLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(manaCountLabelU, GroupLayout.PREFERRED_SIZE, MANA_LABEL_SIZE_HORIZONTAL, GroupLayout.PREFERRED_SIZE))))
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(6)
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnPlayer, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(timerLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(avatar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                                .addGap(14))

                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(6)
                                .addComponent(zonesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(14))
        );
        gl_panelBackground.setVerticalGroup(                
                gl_panelBackground.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                
                                .addGap(6)
                                .addComponent(avatar, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnPlayer)
                                .addComponent(timerLabel)
                                .addGap(1)
                                // Life & Hand
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
                                // Poison
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                        .addGap(4)
                                                        .addComponent(poison, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(4)
                                                        .addComponent(btnWhiteMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(2)
                                                        .addComponent(btnBlueMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(2)
                                                        .addComponent(btnBlackMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(5)
                                                        .addComponent(grave, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                        )
                                        
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                                .addGap(1)
                                                                                .addComponent(library, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                                                .addGap(2)
                                                                .addComponent(btnRedMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
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
                                                                .addComponent(btnColorlessMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(manaCountLabelX, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(39)
                                                .addComponent(btnGreenMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(31)
                                                .addComponent(manaCountLabelG, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        /*.addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(76)
                                                .addComponent(cheat, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))*/
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(76)
                                                .addComponent(exileZone, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                                )
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(76)
                                                .addComponent(graveLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(76)
                                                .addComponent(exileLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(31)
                                                .addComponent(manaCountLabelU, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        )
                                )
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(zonesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
        );
        panelBackground.setLayout(gl_panelBackground);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(panelBackground, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(panelBackground, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        setLayout(groupLayout);

    }

    protected void sizePlayerPanel(boolean smallMode) {
        if (smallMode) {
            avatar.setVisible(false);
            btnPlayer.setVisible(true);
            timerLabel.setVisible(true);
            panelBackground.setPreferredSize(new Dimension(PANEL_WIDTH - 2, PANEL_HEIGHT_SMALL));
            panelBackground.setBounds(0, 0, PANEL_WIDTH - 2, PANEL_HEIGHT_SMALL);
        }
        else {
            avatar.setVisible(true);
            btnPlayer.setVisible(false);
            timerLabel.setVisible(false);
            panelBackground.setPreferredSize(new Dimension(PANEL_WIDTH - 2, PANEL_HEIGHT));
            panelBackground.setBounds(0, 0, PANEL_WIDTH - 2, PANEL_HEIGHT);
        }
    }

    private void btnManaActionPerformed(ManaType manaType) {
        session.sendPlayerManaType(gameId, player.getPlayerId(), manaType);
    }

    private void btnGraveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraveActionPerformed
        /*if (graveyard == null) {
            graveyard = new ShowCardsDialog();
        }*/
        //graveyard.loadCards(player.getName() + " graveyard", player.getGraveyard(), bigCard, Config.dimensions, gameId, false);
        DialogManager.getManager(gameId).showGraveyardDialog(player.getGraveyard(), bigCard, gameId);
    }

    private void btnCommandZoneActionPerformed(java.awt.event.ActionEvent evt) {
        DialogManager.getManager(gameId).showEmblemsDialog(CardsViewUtil.convertCommandObject(player.getCommadObjectList()), bigCard, gameId);
    }

    private void btnExileZoneActionPerformed(java.awt.event.ActionEvent evt) {
        DialogManager.getManager(gameId).showExileDialog(player.getExile(), bigCard, gameId);
    }

    private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheatActionPerformed
        DckDeckImporter deckImporter = new DckDeckImporter();
        session.cheat(gameId, playerId, deckImporter.importDeck("cheat.dck"));
    }

    public PlayerView getPlayer() {
        return player;
    }

    private HoverButton avatar;
    private JButton btnPlayer;
    private ImagePanel life;
    private ImagePanel poison;
    private ImagePanel hand;
    private HoverButton grave;
    private ImagePanel library;
    private CardView topCard;
    private MageCard topCardPanel;
    private JButton cheat;
    private MageRoundPane panelBackground;

    private JLabel timerLabel;
    private JLabel lifeLabel;
    private JLabel handLabel;
    private JLabel libraryLabel;
    private JLabel poisonLabel;
    private JLabel graveLabel;
    private JLabel exileLabel;
    private boolean changedFontLibrary;
    private boolean changedFontLife;
    private boolean changedFontGrave;
    private boolean changedFontExile;

    private JPanel zonesPanel;
    private HoverButton exileZone;
    private HoverButton commandZone;
    private HoverButton enchantPlayerViewZone;

    private final Map<String, JLabel> manaLabels = new HashMap<>();
}
