package mage.client.game;

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
import static mage.constants.Constants.*;
import mage.constants.ManaType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.designations.DesignationType;
import mage.utils.timer.PriorityTimer;
import mage.view.*;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Game GUI: player panel with avatar and icons
 *
 * @author nantuko, JayDi85, Susucr
 */
public class PlayerPanelExt extends javax.swing.JPanel {

    // TODO: *.form file was lost, panel must be reworks in designer
    private UUID playerId;
    private UUID gameId;
    private PlayerView player;
    private boolean isMe;

    private BigCard bigCard;

    private static final String DEFAULT_AVATAR_PATH = "/avatars/" + DEFAULT_AVATAR_ID + ".jpg";

    private static final int PANEL_WIDTH = 94;
    private static final int PANEL_HEIGHT = 290;
    private static final int PANEL_HEIGHT_SMALL = 238;
    private static final int PANEL_HEIGHT_EXTRA_FOR_ME = 25;
    private static final int MANA_LABEL_SIZE_HORIZONTAL = 20;

    private static final Border GREEN_BORDER = new LineBorder(Color.green, 3);
    private static final Border RED_BORDER = new LineBorder(Color.red, 2);
    private static final Border YELLOW_BORDER = new LineBorder(Color.yellow, 3);
    private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(0, 0, 0, 0);

    private final Color activeValueColor = new Color(244, 9, 47);
    private final Font fontValuesZero = this.getFont().deriveFont(Font.PLAIN);
    private final Font fontValuesNonZero = this.getFont().deriveFont(Font.BOLD);

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

    public void init(UUID gameId, UUID playerId, boolean controlled, BigCard bigCard, int priorityTime) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.bigCard = bigCard;
        this.isMe = controlled;
        cheat.setVisible(SessionHandler.isTestMode() && this.isMe);
        cheat.setFocusable(false);
        toolHintsHelper.setVisible(this.isMe);
        toolHintsHelper.setFocusable(false);
        flagName = null;
        if (priorityTime > 0) {
            long delay = 1000L;

            timer = new PriorityTimer(priorityTime, delay, () -> {
                // do nothing
            });
            final PriorityTimer pt = timer;
            timer.setTaskOnTick(() -> {
                int priorityTimeValue = pt.getCount() + pt.getBufferCount();
                String text = getPriorityTimeLeftString(priorityTimeValue);
                // Set timer text colors (note, if you change it here, change it in update() as well)
                final Color textColor;  // use default in HoverButton
                final Color foregroundColor;
                if (pt.getBufferCount() > 0) {
                    textColor = Color.GREEN;
                    foregroundColor = Color.GREEN.darker().darker();
                } else if (pt.getCount() < 300) { // visual indication for under 5 minutes
                    textColor = Color.RED;
                    foregroundColor = Color.RED.darker().darker();
                } else {
                    textColor = null;
                    foregroundColor = Color.BLACK;
                }

                SwingUtilities.invokeLater(() -> {
                    PlayerPanelExt.this.avatar.setTopText(text);
                    PlayerPanelExt.this.avatar.setTopTextColor(textColor);
                    PlayerPanelExt.this.timerLabel.setText(text);
                    PlayerPanelExt.this.timerLabel.setForeground(foregroundColor);
                    PlayerPanelExt.this.avatar.repaint();
                });
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

    private void setTextForLabel(String category, JLabel label, JComponent relatedComponent, int amount, boolean alwaysBlack) {
        setTextForLabel(category, label, relatedComponent, amount, alwaysBlack, Color.BLACK);
    }

    private void setTextForLabel(String category, JLabel label, JComponent relatedComponent, int amount, boolean alwaysBlack, Color fontColor) {
        label.setText(Integer.toString(amount));
        label.setToolTipText(category + ": " + amount);
        if (relatedComponent != null) {
            relatedComponent.setToolTipText(category + ": " + amount);
        }

        if (amount != 0 || alwaysBlack) {
            label.setForeground(fontColor);
            label.setFont(fontValuesNonZero);
        } else {
            label.setForeground(new Color(100, 100, 100));
            label.setFont(fontValuesZero);
        }
    }

    private boolean isCardsPlayable(Collection<CardView> cards, GameView gameView, Set<UUID> possibleTargets) {
        if (cards == null || gameView == null) {
            return false;
        }

        // can play
        if (gameView.getCanPlayObjects() != null && !gameView.getCanPlayObjects().isEmpty()) {
            for (CardView card : cards) {
                if (gameView.getCanPlayObjects().containsObject(card.getId())) {
                    return true;
                }
            }
        }

        // can select
        if (possibleTargets != null && !possibleTargets.isEmpty()) {
            for (CardView card : cards) {
                if (possibleTargets.contains(card.getId())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void update(GameView game, PlayerView player, Set<UUID> possibleTargets) {
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
                } else {
                    avatar.loseLifeDisplay();
                }
            } else {
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
        setTextForLabel("life", lifeLabel, life, playerLife, true);
        setTextForLabel("poison", poisonLabel, poison, player.getCounters().getCount(CounterType.POISON), false);
        setTextForLabel("energy", energyLabel, energy, player.getCounters().getCount(CounterType.ENERGY), false);
        setTextForLabel("experience", experienceLabel, experience, player.getCounters().getCount(CounterType.EXPERIENCE), false);
        setTextForLabel("rad", radLabel, rad, player.getCounters().getCount(CounterType.RAD), false);
        setTextForLabel("hand zone", handLabel, hand, player.getHandCount(), true);
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
        setTextForLabel("library zone", libraryLabel, library, libraryCards, true);

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

        Color graveColor = isCardsPlayable(player.getGraveyard().values(), game, possibleTargets) ? activeValueColor : Color.BLACK;
        setTextForLabel("graveyard zone", graveLabel, grave, graveCards, false, graveColor);
        graveLabel.setToolTipText("Card Types: " + qtyCardTypes(player.getGraveyard()));

        Color commandColor = Color.BLACK;
        for (CommandObjectView com : player.getCommandObjectList()) {
            if (game != null && game.getCanPlayObjects() != null && game.getCanPlayObjects().containsObject(com.getId())) {
                commandColor = activeValueColor;
                break;
            }
            if (possibleTargets != null && possibleTargets.contains(com.getId())) {
                commandColor = activeValueColor;
                break;
            }
        }
        setTextForLabel("command zone", commandLabel, commandZone, player.getCommandObjectList().size(), false, commandColor);

        int exileCards = player.getExile().size();
        Color excileColor = isCardsPlayable(player.getExile().values(), game, possibleTargets) ? activeValueColor : Color.BLACK;
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
        setTextForLabel("exile zone", exileLabel, exileZone, exileCards, false, excileColor);

        if (!MageFrame.isLite()) {
            int id = player.getUserData().getAvatarId();
            if (!(id > 1000) && (id != 64) && (id < MIN_AVATAR_ID || id > MAX_AVATAR_ID)) {
                id = DEFAULT_AVATAR_ID;
            }
            if (id != avatarId) {
                avatarId = id;
                String path = "/avatars/" + avatarId + ".jpg";
                if (avatarId == 64) {
                    path = "/avatars/i64.jpg";
                } else if (avatarId >= 1000) {
                    avatarId = avatarId - 1000;
                    path = "/avatars/special/" + avatarId + ".gif";
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
                this.timer.setBufferCount(player.getBufferTimeLeft());
                this.avatar.setTopText(priorityTimeValue);
                this.timerLabel.setText(priorityTimeValue);
                // Set timer text colors (note, if you change it here, change it in init()::timer.setTaskOnTick() as well)
                final Color textColor; // use default in HoverButton
                final Color foregroundColor;
                if (player.getBufferTimeLeft() > 0) {
                    textColor = Color.GREEN;
                    foregroundColor = Color.GREEN.darker().darker();
                } else if (player.getPriorityTimeLeft() < 300) { // visual indication for under 5 minutes
                    textColor = Color.RED;
                    foregroundColor = Color.RED.darker().darker();
                } else {
                    textColor = null;
                    foregroundColor = Color.BLACK;
                }
                this.avatar.setTopTextColor(textColor);
                this.timerLabel.setForeground(foregroundColor);
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

        // possible targeting
        if (possibleTargets != null && possibleTargets.contains(this.playerId)) {
            this.avatar.setBorder(YELLOW_BORDER);
            this.btnPlayer.setBorder(YELLOW_BORDER);
        }

        update(player.getManaPool());
    }

    private void resetBackgroundColor() {
        panelBackground.setBackgroundColor(PreferencesDialog.getCurrentTheme().getPlayerPanel_inactiveBackgroundColor());
    }

    private void setGreenBackgroundColor() {
        panelBackground.setBackgroundColor(PreferencesDialog.getCurrentTheme().getPlayerPanel_activeBackgroundColor());
    }

    private void setDeadBackgroundColor() {
        panelBackground.setBackgroundColor(PreferencesDialog.getCurrentTheme().getPlayerPanel_deadBackgroundColor());
    }

    /**
     * Updates the avatar image and tooltip text
     */
    private void updateAvatar() {
        if (flagName == null) { // do only once
            avatar.setText(this.player.getName());
            flagName = player.getUserData().getFlagName();
            this.avatar.setTopTextImage(CountryUtil.getCountryFlagIconSize(flagName, 11).getImage());
            String countryName = CountryUtil.getCountryName(flagName);
            basicTooltipText = "<HTML>Name: " + player.getName()
                    + "<br/>Flag: " + (countryName == null ? "Unknown" : countryName)
                    + "<br/>This match wins: " + player.getWins() + " of " + player.getWinsNeeded() + " (to win the match)";
        }

        // extend tooltip
        StringBuilder tooltipText = new StringBuilder(basicTooltipText);
        tooltipText.append("<br/>Match time remaining: ").append(getPriorityTimeLeftString(player));

        // designations
        this.avatar.clearTopTextImagesRight();
        for (String name : player.getDesignationNames()) {
            tooltipText.append("<br/>").append(name);
            if (DesignationType.CITYS_BLESSING.toString().equals(name)) {
                this.avatar.addTopTextImageRight(ImageHelper.getImageFromResources("/info/city_blessing.png"));
            }
        }
        if (player.isMonarch()) {
            tooltipText.append("<br/>").append("The Monarch");
            this.avatar.addTopTextImageRight(ImageHelper.getImageFromResources("/info/crown.png"));
        }
        if (player.isInitiative()) {
            tooltipText.append("<br/>").append("Have the Initiative");
            this.avatar.addTopTextImageRight(ImageHelper.getImageFromResources("/info/initiative.png"));
        }

        // counters
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
        int priorityTimeLeft = player.getPriorityTimeLeft() + player.getBufferTimeLeft();
        return getPriorityTimeLeftString(priorityTimeLeft);
    }

    private String getPriorityTimeLeftString(int priorityTimeLeft) {
        int h = priorityTimeLeft / 3600;
        int m = (priorityTimeLeft % 3600) / 60;
        int s = priorityTimeLeft % 60;
        return (h < 10 ? "0" : "") + h + ':' + (m < 10 ? "0" : "") + m + ':' + (s < 10 ? "0" : "") + s;
    }

    protected void update(ManaPoolView pool) {
        for (Map.Entry<JLabel, ManaType> mana : manaLabels.entrySet()) {
            String category = mana.getValue().toString() + " mana";
            switch (mana.getValue()) {
                case BLACK:
                    setTextForLabel(category, mana.getKey(), manaButtons.get(mana.getKey()), pool.getBlack(), false, activeValueColor);
                    break;
                case RED:
                    setTextForLabel(category, mana.getKey(), manaButtons.get(mana.getKey()), pool.getRed(), false, activeValueColor);
                    break;
                case WHITE:
                    setTextForLabel(category, mana.getKey(), manaButtons.get(mana.getKey()), pool.getWhite(), false, activeValueColor);
                    break;
                case GREEN:
                    setTextForLabel(category, mana.getKey(), manaButtons.get(mana.getKey()), pool.getGreen(), false, activeValueColor);
                    break;
                case BLUE:
                    setTextForLabel(category, mana.getKey(), manaButtons.get(mana.getKey()), pool.getBlue(), false, activeValueColor);
                    break;
                case COLORLESS:
                    setTextForLabel(category, mana.getKey(), manaButtons.get(mana.getKey()), pool.getColorless(), false, activeValueColor);
                    break;
            }

            //HoverButton btn = manaButtons.get(mana.getKey());
            //mana.getKey().setOpaque(true);
            //mana.getKey().setBackground(Color.green);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        radLabel = new JLabel();
        graveLabel = new JLabel();
        commandLabel = new JLabel();
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
        lifeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Image imageLife = ImageHelper.getImageFromResources("/info/life.png");
        BufferedImage resizedLife = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageLife, BufferedImage.TYPE_INT_ARGB), r);
        life = new ImagePanel(resizedLife, ImagePanelStyle.ACTUAL);
        life.setToolTipText("Life");
        life.setOpaque(false);

        // hand area
        r = new Rectangle(18, 18);
        handLabel.setToolTipText("Hand");
        handLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Image imageHand = ImageHelper.getImageFromResources("/info/hand.png");
        BufferedImage resizedHand = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageHand, BufferedImage.TYPE_INT_ARGB), r);
        hand = new ImagePanel(resizedHand, ImagePanelStyle.ACTUAL);
        hand.setToolTipText("Hand");
        hand.setOpaque(false);

        // Poison count
        r = new Rectangle(18, 18);
        Image imagePoison = ImageHelper.getImageFromResources("/info/poison.png");
        BufferedImage resizedPoison = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imagePoison, BufferedImage.TYPE_INT_ARGB), r);
        poison = new ImagePanel(resizedPoison, ImagePanelStyle.ACTUAL);
        poison.setOpaque(false);
        setTextForLabel("poison", poisonLabel, poison, 0, false);
        poisonLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Library
        r = new Rectangle(19, 19);
        libraryLabel.setToolTipText("Library");
        libraryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Image imageLibrary = ImageHelper.getImageFromResources("/info/library.png");
        BufferedImage resizedLibrary = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageLibrary, BufferedImage.TYPE_INT_ARGB), r);

        library = new HoverButton(null, resizedLibrary, resizedLibrary, resizedLibrary, r);
        library.setToolTipText("Library");
        library.setOpaque(false);
        library.setObserver(() -> btnLibraryActionPerformed(null));

        // Grave count and open graveyard button
        r = new Rectangle(21, 21);
        graveLabel.setToolTipText("Card Types: 0");
        graveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Image imageGrave = ImageHelper.getImageFromResources("/info/grave.png");
        BufferedImage resizedGrave = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageGrave, BufferedImage.TYPE_INT_ARGB), r);

        grave = new HoverButton(null, resizedGrave, resizedGrave, resizedGrave, r);
        grave.setToolTipText("Graveyard");
        grave.setOpaque(false);
        grave.setObserver(() -> btnGraveActionPerformed(null));

        exileLabel = new JLabel();
        exileLabel.setToolTipText("Exile");
        exileLabel.setHorizontalAlignment(SwingConstants.CENTER);
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
        cheat.setToolTipText("Cheat button (activate it on your priority only)");
        cheat.addActionListener(e -> btnCheatActionPerformed(e));

        // tools button like hints
        toolHintsHelper = new JButton();
        toolHintsHelper.setText("hints");
        toolHintsHelper.setToolTipText("Open new card hints helper window");
        toolHintsHelper.addActionListener(e -> btnToolHintsHelperActionPerformed(e));

        zonesPanel = new JPanel();
        zonesPanel.setPreferredSize(new Dimension(100, 60));
        zonesPanel.setSize(100, 60);
        zonesPanel.setLayout(null);
        zonesPanel.setOpaque(false);

        image = ImageHelper.getImageFromResources("/info/command_zone.png");
        r = new Rectangle(21, 21);
        resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        commandZone = new HoverButton(null, resized, resized, resized, r);
        commandZone.setToolTipText("Command Zone (Commanders, Emblems and Planes)");
        commandZone.setOpaque(false);
        commandZone.setObserver(() -> btnCommandZoneActionPerformed(null));
        commandZone.setBounds(3, 0, 21, 21);
        zonesPanel.add(commandZone);

        commandLabel.setToolTipText("Command zone");
        commandLabel.setBounds(25, 0, 21, 21);
        zonesPanel.add(commandLabel);

        cheat.setBounds(40, 2, 25, 21);
        zonesPanel.add(cheat);

        toolHintsHelper.setBounds(3, 2 + 21 + 2, 73, 21);
        zonesPanel.add(toolHintsHelper);

        // Energy count
        r = new Rectangle(18, 18);
        Image imageEnergy = ImageHelper.getImageFromResources("/info/energy.png");
        BufferedImage resizedEnergy = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageEnergy, BufferedImage.TYPE_INT_ARGB), r);
        energy = new ImagePanel(resizedEnergy, ImagePanelStyle.ACTUAL);
        energy.setToolTipText("Energy");
        energy.setOpaque(false);
        setTextForLabel("energy", energyLabel, energy, 0, false);
        energyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Experience count
        r = new Rectangle(18, 18);
        Image imageExperience = ImageHelper.getImageFromResources("/info/experience.png");
        BufferedImage resizedExperience = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageExperience, BufferedImage.TYPE_INT_ARGB), r);
        experience = new ImagePanel(resizedExperience, ImagePanelStyle.ACTUAL);
        experience.setToolTipText("Experience");
        experience.setOpaque(false);
        setTextForLabel("experience", experienceLabel, experience, 0, false);
        experienceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Rad count
        r = new Rectangle(16, 16);
        Image imageRad = ImageHelper.getImageFromResources("/info/rad.png");
        BufferedImage resizedRad = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(imageRad, BufferedImage.TYPE_INT_ARGB), r);
        rad = new ImagePanel(resizedRad, ImagePanelStyle.ACTUAL);
        rad.setToolTipText("Rad");
        rad.setOpaque(false);
        setTextForLabel("rad", radLabel, rad, 0, false);
        radLabel.setHorizontalAlignment(SwingConstants.CENTER);

        btnPlayer = new JButton();
        btnPlayer.setText("Player");
        btnPlayer.setVisible(false);
        btnPlayer.setToolTipText("Player");
        btnPlayer.setPreferredSize(new Dimension(20, 40));
        btnPlayer.addActionListener(e -> SessionHandler.sendPlayerUUID(gameId, playerId));

        // Add mana symbols
        // TODO: replace "button + label" to label on rework
        /*
        MouseAdapter manaMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JLabel label =  (JLabel) evt.getSource();
                if (manaLabels.containsKey(label)) {
                    btnManaActionPerformed(manaLabels.get(label));
                }
            }
        };

        JLabel manaCountLabelW = new JLabel();
        manaCountLabelW.setToolTipText("White mana");
        setTextForLabel(manaCountLabelW, 0, false);
        manaCountLabelW.setIcon(new ImageIcon(ManaSymbols.getSizedManaSymbol("W", 15)));
        manaCountLabelW.addMouseListener(manaMouseAdapter);
        manaLabels.put(manaCountLabelW, ManaType.WHITE);l
        //*/
        ///*
        JLabel manaCountLabelW = new JLabel();
        manaCountLabelW.setHorizontalAlignment(SwingConstants.CENTER);
        manaLabels.put(manaCountLabelW, ManaType.WHITE);
        r = new Rectangle(15, 15);
        BufferedImage imageManaW = ManaSymbols.getSizedManaSymbol("W", 15);
        HoverButton btnWhiteMana = new HoverButton(null, imageManaW, imageManaW, imageManaW, r);
        btnWhiteMana.setOpaque(false);
        btnWhiteMana.setObserver(() -> btnManaActionPerformed(ManaType.WHITE));
        manaButtons.put(manaCountLabelW, btnWhiteMana);
        setTextForLabel(ManaType.WHITE.toString() + " mana", manaCountLabelW, btnWhiteMana, 0, false);
        //*/

        JLabel manaCountLabelU = new JLabel();
        manaLabels.put(manaCountLabelU, ManaType.BLUE);
        manaCountLabelU.setHorizontalAlignment(SwingConstants.CENTER);
        r = new Rectangle(15, 15);
        BufferedImage imageManaU = ManaSymbols.getSizedManaSymbol("U", 15);
        HoverButton btnBlueMana = new HoverButton(null, imageManaU, imageManaU, imageManaU, r);
        btnBlueMana.setOpaque(false);
        btnBlueMana.setObserver(() -> btnManaActionPerformed(ManaType.BLUE));
        manaButtons.put(manaCountLabelU, btnBlueMana);
        setTextForLabel(ManaType.BLUE.toString() + " mana", manaCountLabelU, btnBlueMana, 0, false);

        JLabel manaCountLabelB = new JLabel();
        manaLabels.put(manaCountLabelB, ManaType.BLACK);
        manaCountLabelB.setHorizontalAlignment(SwingConstants.CENTER);
        r = new Rectangle(15, 15);
        BufferedImage imageManaB = ManaSymbols.getSizedManaSymbol("B", 15);
        HoverButton btnBlackMana = new HoverButton(null, imageManaB, imageManaB, imageManaB, r);
        btnBlackMana.setOpaque(false);
        btnBlackMana.setObserver(() -> btnManaActionPerformed(ManaType.BLACK));
        manaButtons.put(manaCountLabelB, btnBlackMana);
        setTextForLabel(ManaType.BLACK.toString() + " mana", manaCountLabelB, btnBlackMana, 0, false);

        JLabel manaCountLabelR = new JLabel();
        manaLabels.put(manaCountLabelR, ManaType.RED);
        manaCountLabelR.setHorizontalAlignment(SwingConstants.CENTER);
        r = new Rectangle(15, 15);
        BufferedImage imageManaR = ManaSymbols.getSizedManaSymbol("R", 15);
        HoverButton btnRedMana = new HoverButton(null, imageManaR, imageManaR, imageManaR, r);
        btnRedMana.setOpaque(false);
        btnRedMana.setObserver(() -> btnManaActionPerformed(ManaType.RED));
        manaButtons.put(manaCountLabelR, btnRedMana);
        setTextForLabel(ManaType.RED.toString() + " mana", manaCountLabelR, btnRedMana, 0, false);

        JLabel manaCountLabelG = new JLabel();
        manaLabels.put(manaCountLabelG, ManaType.GREEN);
        manaCountLabelG.setHorizontalAlignment(SwingConstants.CENTER);
        r = new Rectangle(15, 15);
        BufferedImage imageManaG = ManaSymbols.getSizedManaSymbol("G", 15);
        HoverButton btnGreenMana = new HoverButton(null, imageManaG, imageManaG, imageManaG, r);
        btnGreenMana.setOpaque(false);
        btnGreenMana.setObserver(() -> btnManaActionPerformed(ManaType.GREEN));
        manaButtons.put(manaCountLabelG, btnGreenMana);
        setTextForLabel(ManaType.GREEN.toString() + " mana", manaCountLabelG, btnGreenMana, 0, false);

        JLabel manaCountLabelX = new JLabel();
        manaLabels.put(manaCountLabelX, ManaType.COLORLESS);
        manaCountLabelX.setHorizontalAlignment(SwingConstants.CENTER);
        r = new Rectangle(15, 15);
        BufferedImage imageManaX = ManaSymbols.getSizedManaSymbol("C", 15);
        HoverButton btnColorlessMana = new HoverButton(null, imageManaX, imageManaX, imageManaX, r);
        btnColorlessMana.setOpaque(false);
        btnColorlessMana.setObserver(() -> btnManaActionPerformed(ManaType.COLORLESS));
        manaButtons.put(manaCountLabelX, btnColorlessMana);
        setTextForLabel(ManaType.COLORLESS.toString() + " mana", manaCountLabelX, btnColorlessMana, 0, false);

        GroupLayout gl_panelBackground = new GroupLayout(panelBackground);
        gl_panelBackground.setHorizontalGroup(
                gl_panelBackground.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(7)
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnPlayer, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(timerLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(avatar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                                .addGap(6))
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(9)
                                // The left column of icon+label
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(life, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lifeLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(poison, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(energy, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(energyLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(rad, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(radLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(btnWhiteMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(manaCountLabelW, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(btnBlueMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(manaCountLabelU, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(btnBlackMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(manaCountLabelB, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(grave, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(graveLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
                                // The right column of icon+label
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(hand, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(handLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(library, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(experience, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(experienceLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(btnRedMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(manaCountLabelR, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(btnGreenMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(manaCountLabelG, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(btnColorlessMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(manaCountLabelX, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(exileZone, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(exileLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
                                .addGap(4))
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(6)
                                .addComponent(zonesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(6)));
        gl_panelBackground.setVerticalGroup(
                gl_panelBackground.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelBackground.createSequentialGroup()
                                .addGap(6)
                                .addComponent(avatar, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnPlayer, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(timerLabel)
                                .addGap(2)
                                // Life & Hand
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(life, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lifeLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(hand, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(handLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                // Poison & Library
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(poison, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(poisonLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(library, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(libraryLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                // Energy & Experience
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(energy, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(energyLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(experience, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(experienceLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                // Rad & <empty>
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(rad, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(radLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                // W & R
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(btnWhiteMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(manaCountLabelW, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(btnRedMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(manaCountLabelR, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
                                // U & G
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(btnBlueMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(manaCountLabelU, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(btnGreenMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(manaCountLabelG, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
                                // B & X
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(btnBlackMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(manaCountLabelB, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addGap(1)
                                                .addComponent(btnColorlessMana, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(manaCountLabelX, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
                                // grave & exile
                                .addGroup(gl_panelBackground.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(grave, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(graveLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panelBackground.createSequentialGroup()
                                                .addComponent(exileZone, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(exileLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addGap(2)
                                .addComponent(zonesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)));
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

    }// </editor-fold>//GEN-END:initComponents

    protected void sizePlayerPanel(boolean smallMode) {
        int extraForMe = this.isMe ? PANEL_HEIGHT_EXTRA_FOR_ME : 0;
        if (smallMode) {
            avatar.setVisible(false);
            btnPlayer.setVisible(true);
            timerLabel.setVisible(true);
            panelBackground.setPreferredSize(new Dimension(PANEL_WIDTH - 2, PANEL_HEIGHT_SMALL + extraForMe));
            panelBackground.setBounds(0, 0, PANEL_WIDTH - 2, PANEL_HEIGHT_SMALL + extraForMe);
        } else {
            avatar.setVisible(true);
            btnPlayer.setVisible(false);
            timerLabel.setVisible(false);
            panelBackground.setPreferredSize(new Dimension(PANEL_WIDTH - 2, PANEL_HEIGHT + extraForMe));
            panelBackground.setBounds(0, 0, PANEL_WIDTH - 2, PANEL_HEIGHT + extraForMe);
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

    private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.cheatShow(gameId, playerId);
    }

    private void btnToolHintsHelperActionPerformed(java.awt.event.ActionEvent evt) {
        MageFrame.getGame(gameId).openCardHintsWindow("main");
    }

    public PlayerView getPlayer() {
        return player;
    }

    private int qtyCardTypes(mage.view.CardsView cardsView) {
        Set<String> cardTypesPresent = new LinkedHashSet<String>() {
        };
        for (CardView card : cardsView.values()) {
            Set<CardType> cardTypes = EnumSet.noneOf(CardType.class);
            cardTypes.addAll(card.getCardTypes());
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private HoverButton avatar;
    private JButton btnPlayer;
    private ImagePanel life;
    private ImagePanel poison;
    private ImagePanel energy;
    private ImagePanel experience;
    private ImagePanel rad;
    private ImagePanel hand;
    private HoverButton grave;
    private HoverButton library;
    private JButton cheat;
    private JButton toolHintsHelper;
    private MageRoundPane panelBackground;

    private JLabel timerLabel;
    private JLabel lifeLabel;
    private JLabel handLabel;
    private JLabel libraryLabel;
    private JLabel poisonLabel;
    private JLabel energyLabel;
    private JLabel experienceLabel;
    private JLabel radLabel;
    private JLabel graveLabel;
    private JLabel commandLabel;
    private JLabel exileLabel;

    private JPanel zonesPanel;
    private HoverButton exileZone;
    private HoverButton commandZone;
    // End of variables declaration//GEN-END:variables

    private boolean changedFontLibrary;
    private boolean changedFontLife;
    private boolean changedFontGrave;
    private boolean changedFontExile;
    private final Map<JLabel, ManaType> manaLabels = new HashMap<>();
    private final Map<JLabel, HoverButton> manaButtons = new HashMap<>();

}
