

 /*
 * PlayerPanel.java
 *
 * Created on Nov 18, 2009, 3:01:31 PM
 */
package mage.client.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import mage.cards.decks.importer.DckDeckImporter;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.components.HoverButton;
import mage.client.components.MageRoundPane;
import mage.client.components.ext.dlg.DialogManager;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.CardsViewUtil;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.client.util.gui.countryBox.CountryUtil;
import mage.components.ImagePanel;
import mage.components.ImagePanelStyle;
import mage.constants.CardType;
import static mage.constants.Constants.DEFAULT_AVATAR_ID;
import static mage.constants.Constants.MAX_AVATAR_ID;
import static mage.constants.Constants.MIN_AVATAR_ID;
import mage.constants.ManaType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.designations.DesignationType;
import mage.utils.timer.PriorityTimer;
import mage.view.CardView;
import mage.view.ManaPoolView;
import mage.view.PlayerView;
import org.mage.card.arcane.ManaSymbols;

/**
 * Enhanced player pane.
 *
 * @author nantuko
 */
public class PlayerPanelExt extends javax.swing.JPanel {

    private UUID playerId;
    private UUID gameId;
    private PlayerView player;

    private BigCard bigCard;

    private static final String DEFAULT_AVATAR_PATH = "/avatars/" + DEFAULT_AVATAR_ID + ".jpg";

    private static final int PANEL_WIDTH = 94;
    private static final int PANEL_HEIGHT = 262;
    private static final int PANEL_HEIGHT_SMALL = 242;
    private static final int MANA_LABEL_SIZE_HORIZONTAL = 20;

    private static final Border GREEN_BORDER = new LineBorder(Color.green, 3);
    private static final Border RED_BORDER = new LineBorder(Color.red, 2);
    private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    private final Color inactiveBackgroundColor = new Color(200, 200, 180, 200);
    private final Color activeBackgroundColor = new Color(200, 255, 200, 200);
    private final Color deadBackgroundColor = new Color(131, 94, 83, 200);

    private int avatarId = -1;
    private String flagName;
    private String basicTooltipText;
    private static final Map<UUID, Integer> playerLives = new HashMap<>();

    private PriorityTimer timer;

    /**
     * Creates new form PlayerPanel
     */
    public PlayerPanelExt() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        initComponents();
        setGUISize();
    }

    public void init(UUID gameId, UUID playerId, BigCard bigCard, int priorityTime) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.bigCard = bigCard;
        cheat.setVisible(SessionHandler.isTestMode());
        cheat.setFocusable(false);
        flagName = null;
        if (priorityTime > 0) {
            long delay = 1000L;

            timer = new PriorityTimer(priorityTime, delay, () -> {
                // do nothing
            });
            final PriorityTimer pt = timer;
            timer.setTaskOnTick(() -> {
                int priorityTimeValue = pt.getCount();
                String text = getPriorityTimeLeftString(priorityTimeValue);
                PlayerPanelExt.this.avatar.setTopText(text);
                PlayerPanelExt.this.timerLabel.setText(text);
                PlayerPanelExt.this.avatar.repaint();
            });
            timer.init(gameId);
        }
    }

    public void cleanUp() {
        if (timer != null) {
            this.timer.cancel();
        }
    }

    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {

    }

    private void setTextForLabel(JLabel label, int amount, boolean alwaysBlack) {
        label.setText(Integer.toString(amount));
        if (amount != 0 || alwaysBlack) {
            label.setForeground(Color.BLACK);
        } else {
            label.setForeground(new Color(100, 100, 100));
        }
    }

    public void update(PlayerView player) {
        this.player = player;
        int pastLife = player.getLife();
        if (playerLives != null) {
            if (playerLives.containsKey(player.getPlayerId())) {
                pastLife = playerLives.get(player.getPlayerId());
            }
            playerLives.put(player.getPlayerId(), player.getLife());
        }
        int playerLife = player.getLife();

        boolean displayLife = "true".equals(MageFrame.getPreferences().get(PreferencesDialog.KEY_DISPLAY_LIVE_ON_AVATAR, "true"));
        avatar.setCenterText(displayLife ? String.valueOf(playerLife) : null);

        if (displayLife) {
            if (playerLife != pastLife) {
                if (playerLife > pastLife) {
                    avatar.gainLifeDisplay();
                } else if (playerLife < pastLife) {
                    avatar.loseLifeDisplay();
                }
            } else if (playerLife == pastLife) {
                avatar.stopLifeDisplay();
            }
        }

        updateAvatar();

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
        setTextForLabel(lifeLabel, playerLife, true);
        setTextForLabel(poisonLabel, player.getCounters().getCount(CounterType.POISON), false);
        setTextForLabel(energyLabel, player.getCounters().getCount(CounterType.ENERGY), false);
        setTextForLabel(experienceLabel, player.getCounters().getCount(CounterType.EXPERIENCE), false);
        setTextForLabel(handLabel, player.getHandCount(), true);
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
        setTextForLabel(libraryLabel, libraryCards, true);

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
        setTextForLabel(graveLabel, graveCards, false);
        graveLabel.setToolTipText("Card Types: " + qtyCardTypes(player.getGraveyard()));

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
        setTextForLabel(exileLabel, exileCards, false);

        if (!MageFrame.isLite()) {
            int id = player.getUserData().getAvatarId();
            if (!(id >= 1000) && (id <= 0 || (id <= MIN_AVATAR_ID && id > MAX_AVATAR_ID))) {
                id = DEFAULT_AVATAR_ID;
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
                this.avatar.update(this.player.getName(), resized, resized, resized, resized, r);
            }
        }
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

        if (player.isActive()) {
            this.avatar.setBorder(GREEN_BORDER);
            this.btnPlayer.setBorder(GREEN_BORDER);
            setGreenBackgroundColor();
        } else {
            resetBackgroundColor();
            if (player.hasLeft()) {
                this.avatar.setBorder(RED_BORDER);
                this.btnPlayer.setBorder(RED_BORDER);
                setDeadBackgroundColor();
            } else {
                this.avatar.setBorder(EMPTY_BORDER);
                this.btnPlayer.setBorder(EMPTY_BORDER);
            }
        }

        update(player.getManaPool());
    }

    private void resetBackgroundColor() {
        panelBackground.setBackgroundColor(inactiveBackgroundColor);
    }

    private void setGreenBackgroundColor() {
        panelBackground.setBackgroundColor(activeBackgroundColor);
    }

    private void setDeadBackgroundColor() {
        panelBackground.setBackgroundColor(deadBackgroundColor);
    }

    /**
     * Updates the avatar image and tooltip text
     */
    private void updateAvatar() {
        if (flagName == null) { // do only once
            avatar.setText(this.player.getName());
            if (!player.getUserData().getFlagName().equals(flagName)) {
                flagName = player.getUserData().getFlagName();
                this.avatar.setTopTextImage(CountryUtil.getCountryFlagIconSize(flagName, 11).getImage());
            }
            // TODO: Add the wins to the tooltiptext of the avatar
            String countryname = CountryUtil.getCountryName(flagName);
            if (countryname == null) {
                countryname = "Unknown";
            }
            basicTooltipText = "<HTML>Name: " + player.getName()
                    + "<br/>Country: " + countryname
                    + "<br/>Constructed rating: " + player.getUserData().getConstructedRating()
                    + "<br/>Limited rating: " + player.getUserData().getLimitedRating()
                    + "<br/>Deck hash code: " + player.getDeckHashCode()
                    + "<br/>This match wins: " + player.getWins() + " of " + player.getWinsNeeded() + " (to win the match)"
                    + (player.getUserData() == null ? "" : "<br/>History: " + player.getUserData().getHistory());
        }
        // Extend tooltip
        StringBuilder tooltipText = new StringBuilder(basicTooltipText);
        this.avatar.setTopTextImageRight(null);
        for (String name : player.getDesignationNames()) {
            tooltipText.append("<br/>").append(name);
            if (DesignationType.CITYS_BLESSING.toString().equals(name)) {
                this.avatar.setTopTextImageRight(ImageHelper.getImageFromResources("/info/city_blessing.png"));
            }
        }
        if (player.isMonarch()) {
            this.avatar.setTopTextImageRight(ImageHelper.getImageFromResources("/info/crown.png"));
        }
        for (Counter counter : player.getCounters().values()) {
            tooltipText.append("<br/>").append(counter.getName()).append(" counters: ").append(counter.getCount());
        }

        avatar.setToolTipText(tooltipText.toString());
        avatar.repaint();

        // used if avatar image can't be used
        this.btnPlayer.setText(player.getName());
        this.btnPlayer.setToolTipText(tooltipText.toString());
    }

    private String getPriorityTimeLeftString(PlayerView player) {
        int priorityTimeLeft = player.getPriorityTimeLeft();
        return getPriorityTimeLeftString(priorityTimeLeft);
    }

    private String getPriorityTimeLeftString(int priorityTimeLeft) {
        int h = priorityTimeLeft / 3600;
        int m = (priorityTimeLeft % 3600) / 60;
        int s = priorityTimeLeft % 60;
        return (h < 10 ? "0" : "") + h + ':' + (m < 10 ? "0" : "") + m + ':' + (s < 10 ? "0" : "") + s;
    }

    protected void update(ManaPoolView pool) {
        setTextForLabel(manaLabels.get("B"), pool.getBlack(), false);
        setTextForLabel(manaLabels.get("R"), pool.getRed(), false);
        setTextForLabel(manaLabels.get("W"), pool.getWhite(), false);
        setTextForLabel(manaLabels.get("G"), pool.getGreen(), false);
        setTextForLabel(manaLabels.get("U"), pool.getBlue(), false);
        setTextForLabel(manaLabels.get("X"), pool.getColorless(), false);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        panelBackground = new MageRoundPane();
        panelBackground.setPreferredSize(new Dimension(PANEL_WIDTH - 2, PANEL_HEIGHT));
        Rectangle r = new Rectangle(80, 80);
//        avatarFlag = new JLabel();
//        monarchIcon = new JLabel();
        timerLabel = new JLabel();
        lifeLabel = new JLabel();
        handLabel = new JLabel();
        poisonLabel = new JLabel();
        energyLabel = new JLabel();
        experienceLabel = new JLabel();
        graveLabel = new JLabel();
        libraryLabel = new JLabel();
        setOpaque(false);

        panelBackground.setXOffset(3);
        panelBackground.setYOffset(3);
        panelBackground.setVisible(true);

        // Avatar
        Image image = ImageHelper.getImageFromResources(DEFAULT_AVATAR_PATH);

        BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        avatar = new HoverButton("", resized, resized, resized, r);

        String showPlayerNamePermanently = MageFrame.getPreferences().get(PreferencesDialog.KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true");
        if (showPlayerNamePermanently.equals("true")) {
            avatar.setTextAlwaysVisible(true);
        }
        avatar.setTextOffsetButtonY(10);
        avatar.setObserver(() -> SessionHandler.sendPlayerUUID(gameId, playerId));

        // timer area /small layout)
        timerLabel.setToolTipText("Time left");
        timerLabel.setSize(80, 12);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // life area
        r = new Rectangle(18, 18);
        lifeLabel.setToolTipText("Life");
        Image imageLife = ImageHelper.getImageFromResources("/info/life.png");
        BufferedImage resizedLife = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageLife, BufferedImage.TYPE_INT_ARGB), r);
        life = new ImagePanel(resizedLife, ImagePanelStyle.ACTUAL);
        life.setToolTipText("Life");
        life.setOpaque(false);
        // hand area
        r = new Rectangle(18, 18);
        handLabel.setToolTipText("Hand");
        Image imageHand = ImageHelper.getImageFromResources("/info/hand.png");
        BufferedImage resizedHand = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageHand, BufferedImage.TYPE_INT_ARGB), r);
        hand = new ImagePanel(resizedHand, ImagePanelStyle.ACTUAL);
        hand.setToolTipText("Hand");
        hand.setOpaque(false);

        // Poison count
        setTextForLabel(poisonLabel, 0, false);
        r = new Rectangle(18, 18);
        poisonLabel.setToolTipText("Poison");
        Image imagePoison = ImageHelper.getImageFromResources("/info/poison.png");
        BufferedImage resizedPoison = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imagePoison, BufferedImage.TYPE_INT_ARGB), r);
        poison = new ImagePanel(resizedPoison, ImagePanelStyle.ACTUAL);
        poison.setToolTipText("Poison");
        poison.setOpaque(false);

        // Library
        r = new Rectangle(19, 19);
        libraryLabel.setToolTipText("Library");
        Image imageLibrary = ImageHelper.getImageFromResources("/info/library.png");
        BufferedImage resizedLibrary = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageLibrary, BufferedImage.TYPE_INT_ARGB), r);

        library = new HoverButton(null, resizedLibrary, resizedLibrary, resizedLibrary, r);
        library.setToolTipText("Library");
        library.setOpaque(false);
        library.setObserver(() -> btnLibraryActionPerformed(null));

        // Grave count and open graveyard button
        r = new Rectangle(21, 21);
        graveLabel.setToolTipText("Card Types: 0");
        Image imageGrave = ImageHelper.getImageFromResources("/info/grave.png");
        BufferedImage resizedGrave = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageGrave, BufferedImage.TYPE_INT_ARGB), r);

        grave = new HoverButton(null, resizedGrave, resizedGrave, resizedGrave, r);
        grave.setToolTipText("Graveyard");
        grave.setOpaque(false);
        grave.setObserver(() -> btnGraveActionPerformed(null));

        exileLabel = new JLabel();
        exileLabel.setToolTipText("Exile");
        image = ImageHelper.getImageFromResources("/info/exile.png");
        r = new Rectangle(21, 21);
        resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        exileZone = new HoverButton(null, resized, resized, resized, r);
        exileZone.setToolTipText("Exile");
        exileZone.setOpaque(false);
        exileZone.setObserver(() -> btnExileZoneActionPerformed(null));
        exileZone.setBounds(25, 0, 21, 21);

        // Cheat button
        r = new Rectangle(25, 21);
        image = ImageHelper.getImageFromResources("/info/cheat.png");
        resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        cheat = new JButton();
        cheat.setIcon(new ImageIcon(resized));
        cheat.setToolTipText("Cheat button");
        cheat.addActionListener(e -> btnCheatActionPerformed(e));

        zonesPanel = new JPanel();
        zonesPanel.setPreferredSize(new Dimension(100, 60));
        zonesPanel.setSize(100, 60);
        zonesPanel.setLayout(null);
        zonesPanel.setOpaque(false);

        image = ImageHelper.getImageFromResources("/info/command_zone.png");
        r = new Rectangle(21, 21);
        resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        commandZone = new HoverButton(null, resized, resized, resized, r);
        commandZone.setToolTipText("Command Zone (Commander and Emblems)");
        commandZone.setOpaque(false);
        commandZone.setObserver(() -> btnCommandZoneActionPerformed(null));
        commandZone.setBounds(5, 0, 21, 21);
        zonesPanel.add(commandZone);

        cheat.setBounds(28, 0, 25, 21);
        zonesPanel.add(cheat);

        energyExperiencePanel = new JPanel();
        energyExperiencePanel.setPreferredSize(new Dimension(100, 20));
        energyExperiencePanel.setSize(100, 20);
        energyExperiencePanel.setLayout(null);
        energyExperiencePanel.setOpaque(false);

        // Energy count
        setTextForLabel(energyLabel, 0, false);
        r = new Rectangle(18, 18);
        energyLabel.setToolTipText("Energy");
        Image imageEnergy = ImageHelper.getImageFromResources("/info/energy.png");
        BufferedImage resizedEnergy = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageEnergy, BufferedImage.TYPE_INT_ARGB), r);
        energy = new ImagePanel(resizedEnergy, ImagePanelStyle.ACTUAL);
        energy.setToolTipText("Energy");
        energy.setOpaque(false);

        // Experience count
        setTextForLabel(experienceLabel, 0, false);
        r = new Rectangle(18, 18);
        experienceLabel.setToolTipText("Experience");
        Image imageExperience = ImageHelper.getImageFromResources("/info/experience.png");
        BufferedImage resizedExperience = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageExperience, BufferedImage.TYPE_INT_ARGB), r);
        experience = new ImagePanel(resizedExperience, ImagePanelStyle.ACTUAL);
        experience.setToolTipText("Experience");
        experience.setOpaque(false);

        btnPlayer = new JButton();
        btnPlayer.setText("Player");
        btnPlayer.setVisible(false);
        btnPlayer.setToolTipText("Player");
        btnPlayer.addActionListener(e -> SessionHandler.sendPlayerUUID(gameId, playerId));

        // Add mana symbols
        JLabel manaCountLabelW = new JLabel();
        manaCountLabelW.setToolTipText("White mana");
        setTextForLabel(manaCountLabelW, 0, false);
        manaLabels.put("W", manaCountLabelW);
        r = new Rectangle(15, 15);
        BufferedImage imageManaW = ManaSymbols.getSizedManaSymbol("W", 15);
        HoverButton btnWhiteMana = new HoverButton(null, imageManaW, imageManaW, imageManaW, r);

        btnWhiteMana.setToolTipText("White mana");
        btnWhiteMana.setOpaque(false);
        btnWhiteMana.setObserver(() -> btnManaActionPerformed(ManaType.WHITE));

        JLabel manaCountLabelU = new JLabel();
        manaCountLabelU.setToolTipText("Blue mana");
        setTextForLabel(manaCountLabelU, 0, false);
        manaLabels.put("U", manaCountLabelU);
        r = new Rectangle(15, 15);
        BufferedImage imageManaU = ManaSymbols.getSizedManaSymbol("U", 15);
        HoverButton btnBlueMana = new HoverButton(null, imageManaU, imageManaU, imageManaU, r);
        btnBlueMana.setToolTipText("Blue mana");
        btnBlueMana.setOpaque(false);
        btnBlueMana.setObserver(() -> btnManaActionPerformed(ManaType.BLUE));

        JLabel manaCountLabelB = new JLabel();
        manaCountLabelB.setToolTipText("Black mana");
        setTextForLabel(manaCountLabelB, 0, false);
        manaLabels.put("B", manaCountLabelB);
        r = new Rectangle(15, 15);
        BufferedImage imageManaB = ManaSymbols.getSizedManaSymbol("B", 15);
        HoverButton btnBlackMana = new HoverButton(null, imageManaB, imageManaB, imageManaB, r);
        btnBlackMana.setToolTipText("Black mana");
        btnBlackMana.setOpaque(false);
        btnBlackMana.setObserver(() -> btnManaActionPerformed(ManaType.BLACK));

        JLabel manaCountLabelR = new JLabel();
        manaCountLabelR.setToolTipText("Red mana");
        setTextForLabel(manaCountLabelR, 0, false);
        manaLabels.put("R", manaCountLabelR);
        r = new Rectangle(15, 15);
        BufferedImage imageManaR = ManaSymbols.getSizedManaSymbol("R", 15);
        HoverButton btnRedMana = new HoverButton(null, imageManaR, imageManaR, imageManaR, r);
        btnRedMana.setToolTipText("Red mana");
        btnRedMana.setOpaque(false);
        btnRedMana.setObserver(() -> btnManaActionPerformed(ManaType.RED));

        JLabel manaCountLabelG = new JLabel();
        manaCountLabelG.setToolTipText("Green mana");
        setTextForLabel(manaCountLabelG, 0, false);
        manaLabels.put("G", manaCountLabelG);
        r = new Rectangle(15, 15);
        BufferedImage imageManaG = ManaSymbols.getSizedManaSymbol("G", 15);
        HoverButton btnGreenMana = new HoverButton(null, imageManaG, imageManaG, imageManaG, r);
        btnGreenMana.setToolTipText("Green mana");
        btnGreenMana.setOpaque(false);
        btnGreenMana.setObserver(() -> btnManaActionPerformed(ManaType.GREEN));

        JLabel manaCountLabelX = new JLabel();
        manaCountLabelX.setToolTipText("Colorless mana");
        setTextForLabel(manaCountLabelX, 0, false);
        manaLabels.put("X", manaCountLabelX);
        r = new Rectangle(15, 15);
        BufferedImage imageManaX = ManaSymbols.getSizedManaSymbol("C", 15);
        HoverButton btnColorlessMana = new HoverButton(null, imageManaX, imageManaX, imageManaX, r);
        btnColorlessMana.setToolTipText("Colorless mana");
        btnColorlessMana.setOpaque(false);
        btnColorlessMana.setObserver(() -> btnManaActionPerformed(ManaType.COLORLESS));

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
                                .addComponent(poison, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                .addGap(3)
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(18)
                                                .addComponent(library, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addGap(4)
                                .addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(9)
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(energy, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
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
                                                                .addComponent(experience, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(energyLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
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
                                                .addComponent(experienceLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
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
                                .addGap(8))
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
                                .addGap(2)
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
                                // Poison & Library
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(poison, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(library, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addGap(1)
                                // Poison
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(energy, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addGap(2)
                                                .addComponent(btnWhiteMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addGap(2)
                                                .addComponent(btnBlueMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addGap(2)
                                                .addComponent(btnBlackMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addGap(3)
                                                .addComponent(grave, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                                                .addGap(1)
                                                                                .addComponent(experience, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(energyLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
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
                                        .addComponent(experienceLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
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
                                .addGap(2)
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
        } else {
            avatar.setVisible(true);
            btnPlayer.setVisible(false);
            timerLabel.setVisible(false);
            panelBackground.setPreferredSize(new Dimension(PANEL_WIDTH - 2, PANEL_HEIGHT));
            panelBackground.setBounds(0, 0, PANEL_WIDTH - 2, PANEL_HEIGHT);
        }
    }

    private void btnManaActionPerformed(ManaType manaType) {
        SessionHandler.sendPlayerManaType(gameId, player.getPlayerId(), manaType);
    }

    private void btnGraveActionPerformed(java.awt.event.ActionEvent evt) {
        MageFrame.getGame(gameId).openGraveyardWindow(player.getName());
    }

    private void btnLibraryActionPerformed(java.awt.event.ActionEvent evt) {
        MageFrame.getGame(gameId).openTopLibraryWindow(player.getName());
    }

    private void btnCommandZoneActionPerformed(java.awt.event.ActionEvent evt) {
        DialogManager.getManager(gameId).showEmblemsDialog(CardsViewUtil.convertCommandObject(player.getCommandObjectList()), bigCard, gameId);
    }

    private void btnExileZoneActionPerformed(java.awt.event.ActionEvent evt) {
        DialogManager.getManager(gameId).showExileDialog(player.getExile(), bigCard, gameId);
    }

    private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheatActionPerformed
        DckDeckImporter deckImporter = new DckDeckImporter();
        SessionHandler.cheat(gameId, playerId, deckImporter.importDeck("cheat.dck"));
    }

    public PlayerView getPlayer() {
        return player;
    }

    private int qtyCardTypes(mage.view.CardsView cardsView) {
        Set<String> cardTypesPresent = new LinkedHashSet<String>() {
        };
        for (CardView card : cardsView.values()) {
            Set<CardType> cardTypes = card.getCardTypes();
            for (CardType cardType : cardTypes) {
                cardTypesPresent.add(cardType.toString());
            }
        }
        if (cardTypesPresent.isEmpty()) {
            return 0;
        } else {
            return cardTypesPresent.size();
        }
    }

    private HoverButton avatar;
    private JButton btnPlayer;
    private ImagePanel life;
    private ImagePanel poison;
    private ImagePanel energy;
    private ImagePanel experience;
    private ImagePanel hand;
    private HoverButton grave;
    private HoverButton library;
    private JButton cheat;
    private MageRoundPane panelBackground;

    private JLabel timerLabel;
    private JLabel lifeLabel;
    private JLabel handLabel;
    private JLabel libraryLabel;
    private JLabel poisonLabel;
    private JLabel energyLabel;
    private JLabel experienceLabel;
    private JLabel graveLabel;
    private JLabel exileLabel;
    private boolean changedFontLibrary;
    private boolean changedFontLife;
    private boolean changedFontGrave;
    private boolean changedFontExile;

    private JPanel zonesPanel;
    private JPanel energyExperiencePanel;
    private HoverButton exileZone;
    private HoverButton commandZone;

    private final Map<String, JLabel> manaLabels = new HashMap<>();
}
