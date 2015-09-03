/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
 * PreferencesDialog.java
 *
 * Created on 26.06.2011, 16:35:40
 */
package mage.client.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import mage.client.MageFrame;
import mage.client.util.Config;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.players.net.UserSkipPrioritySteps;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import org.apache.log4j.Logger;

/**
 * Preferences dialog.
 *
 * @author nantuko
 */
public class PreferencesDialog extends javax.swing.JDialog {

    private static final transient Logger log = Logger.getLogger(PreferencesDialog.class);

    public static final String KEY_HAND_USE_BIG_CARDS = "handUseBigCards";
    public static final String KEY_SHOW_TOOLTIPS_ANY_ZONE = "showTooltipsInAnyZone";
    public static final String KEY_SHOW_CARD_NAMES = "showCardNames";
    public static final String KEY_PERMANENTS_IN_ONE_PILE = "nonLandPermanentsInOnePile";
    public static final String KEY_SHOW_PLAYER_NAMES_PERMANENTLY = "showPlayerNamesPermanently";
    public static final String KEY_SHOW_ABILITY_PICKER_FORCED = "showAbilityPicker";
    public static final String KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS = "gameAllowRequestShowHandCards";
    public static final String KEY_GAME_SHOW_STORM_COUNTER = "gameShowStormCounter";
    public static final String KEY_GAME_CONFIRM_EMPTY_MANA_POOL = "gameConfirmEmptyManaPool";
    public static final String KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER = "gameAskMoveToGraveORder";

    public static final String KEY_GAME_LOG_AUTO_SAVE = "gameLogAutoSave";
    public static final String KEY_DRAFT_LOG_AUTO_SAVE = "draftLogAutoSave";

    public static final String KEY_CARD_IMAGES_USE_DEFAULT = "cardImagesUseDefault";
    public static final String KEY_CARD_IMAGES_PATH = "cardImagesPath";
    public static final String KEY_CARD_IMAGES_CHECK = "cardImagesCheck";
    public static final String KEY_CARD_IMAGES_SAVE_TO_ZIP = "cardImagesSaveToZip";
    public static final String KEY_CARD_IMAGES_PREF_LANGUAGE = "cardImagesPreferedImageLaguage";

    public static final String KEY_BACKGROUND_IMAGE = "backgroundImage";
    public static final String KEY_BATTLEFIELD_IMAGE = "battlefieldImage";
    public static final String KEY_BACKGROUND_IMAGE_DEFAULT = "backgroundImagedDefault";
    public static final String KEY_BATTLEFIELD_IMAGE_RANDOM = "battlefieldImagerandom";
    public static final String KEY_BATTLEFIELD_IMAGE_DEFAULT = "battlefieldImageDefault";

    public static final String KEY_SOUNDS_GAME_ON = "soundsOn";
    public static final String KEY_SOUNDS_DRAFT_ON = "soundsDraftOn";
    public static final String KEY_SOUNDS_SKIP_BUTTONS_ON = "soundsSkipButtonsOn";
    public static final String KEY_SOUNDS_OTHER_ON = "soundsOtherOn";
    public static final String KEY_SOUNDS_MATCH_MUSIC_ON = "soundsMatchMusicOn";
    public static final String KEY_SOUNDS_MATCH_MUSIC_PATH = "soundsMatchMusicPath";

    public static final String KEY_BIG_CARD_TOGGLED = "bigCardToggled";

    // Phases
    public static final String UPKEEP_YOU = "upkeepYou";
    public static final String DRAW_YOU = "drawYou";
    public static final String MAIN_YOU = "mainYou";
    public static final String BEFORE_COMBAT_YOU = "beforeCombatYou";
    public static final String END_OF_COMBAT_YOU = "endOfCombatYou";
    public static final String MAIN_2_YOU = "main2You";
    public static final String END_OF_TURN_YOU = "endOfTurnYou";

    public static final String UPKEEP_OTHERS = "upkeepOthers";
    public static final String DRAW_OTHERS = "drawOthers";
    public static final String MAIN_OTHERS = "mainOthers";
    public static final String BEFORE_COMBAT_OTHERS = "beforeCombatOthers";
    public static final String END_OF_COMBAT_OTHERS = "endOfCombatOthers";
    public static final String MAIN_2_OTHERS = "main2Others";
    public static final String END_OF_TURN_OTHERS = "endOfTurnOthers";

    public static final String KEY_STOP_ATTACK = "stopDeclareAttacksStep";
    public static final String KEY_STOP_BLOCK = "stopDeclareBlockersStep";
    public static final String KEY_STOP_ALL_MAIN_PHASES = "stopOnAllMainPhases";
    public static final String KEY_STOP_ALL_END_PHASES = "stopOnAllEndPhases";
    public static final String KEY_PASS_PRIORITY_CAST = "passPriorityCast";
    public static final String KEY_PASS_PRIORITY_ACTIVATION = "passPriorityActivation";
    public static final String KEY_AUTO_ORDER_TRIGGER = "autoOrderTrigger";

    // mana auto payment
    public static final String KEY_GAME_MANA_AUTOPAYMENT = "gameManaAutopayment";
    public static final String KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE = "gameManaAutopaymentOnlyOne";

    // Size of frame to check if divider locations should be used
    public static final String KEY_MAGE_PANEL_LAST_SIZE = "gamepanelLastSize";

    // pref settings of table settings and filtering
    public static final String KEY_TABLES_FILTER_SETTINGS = "tablePanelFilterSettings";
    public static final String KEY_TABLES_COLUMNS_WIDTH = "tablePanelColumnWidth";
    public static final String KEY_TABLES_COLUMNS_ORDER = "tablePanelColumnSort";

    // positions of divider bars
    public static final String KEY_TABLES_DIVIDER_LOCATION_1 = "tablePanelDividerLocation1";
    public static final String KEY_TABLES_DIVIDER_LOCATION_2 = "tablePanelDividerLocation2";
    public static final String KEY_TABLES_DIVIDER_LOCATION_3 = "tablePanelDividerLocation3";

    // user list
    public static final String KEY_USERS_COLUMNS_WIDTH = "userPanelColumnWidth";
    public static final String KEY_USERS_COLUMNS_ORDER = "userPanelColumnSort";
    // table waiting dialog
    public static final String KEY_TABLE_WAITING_WIDTH = "tableWaitingPanelWidth";
    public static final String KEY_TABLE_WAITING_HEIGHT = "tableWaitingPanelHeight";
    public static final String KEY_TABLE_WAITING_COLUMNS_WIDTH = "tableWaitingPanelColumnWidth";
    public static final String KEY_TABLE_WAITING_COLUMNS_ORDER = "tableWaitingPanelColumnSort";

    public static final String KEY_GAMEPANEL_DIVIDER_LOCATION_0 = "gamepanelDividerLocation0";
    public static final String KEY_GAMEPANEL_DIVIDER_LOCATION_1 = "gamepanelDividerLocation1";
    public static final String KEY_GAMEPANEL_DIVIDER_LOCATION_2 = "gamepanelDividerLocation2";

    public static final String KEY_TOURNAMENT_PLAYER_COLUMNS_WIDTH = "tournamentPlayerPanelColumnWidth";
    public static final String KEY_TOURNAMENT_PLAYER_COLUMNS_ORDER = "tournamentPlayerPanelColumnSort";
    public static final String KEY_TOURNAMENT_MATCH_COLUMNS_WIDTH = "tournamentMatchPanelColumnWidth";
    public static final String KEY_TOURNAMENT_MATCH_COLUMNS_ORDER = "tournamentMatchPanelColumnSort";
    public static final String KEY_TOURNAMENT_DIVIDER_LOCATION_1 = "tournamentPanelDividerLocation1";
    public static final String KEY_TOURNAMENT_DIVIDER_LOCATION_2 = "tournamentPanelDividerLocation2";

    // pref setting for new table dialog
    public static final String KEY_NEW_TABLE_NAME = "newTableName";
    public static final String KEY_NEW_TABLE_PASSWORD = "newTablePassword";
    public static final String KEY_NEW_TABLE_PASSWORD_JOIN = "newTablePasswordJoin";
    public static final String KEY_NEW_TABLE_DECK_TYPE = "newTableDeckType";
    public static final String KEY_NEW_TABLE_TIME_LIMIT = "newTableTimeLimit";
    public static final String KEY_NEW_TABLE_GAME_TYPE = "newTableGameType";
    public static final String KEY_NEW_TABLE_NUMBER_OF_WINS = "newTableNumberOfWins";
    public static final String KEY_NEW_TABLE_ROLLBACK_TURNS_ALLOWED = "newTableRollbackTurnsAllowed";
    public static final String KEY_NEW_TABLE_NUMBER_OF_FREE_MULLIGANS = "newTableNumberOfFreeMulligans";
    public static final String KEY_NEW_TABLE_DECK_FILE = "newTableDeckFile";
    public static final String KEY_NEW_TABLE_RANGE = "newTableRange";
    public static final String KEY_NEW_TABLE_ATTACK_OPTION = "newTableAttackOption";
    public static final String KEY_NEW_TABLE_SKILL_LEVEL = "newTableSkillLevel";
    public static final String KEY_NEW_TABLE_NUMBER_PLAYERS = "newTableNumberPlayers";
    public static final String KEY_NEW_TABLE_PLAYER_TYPES = "newTablePlayerTypes";

    // pref setting for new tournament dialog
    public static final String KEY_NEW_TOURNAMENT_NAME = "newTournamentName";
    public static final String KEY_NEW_TOURNAMENT_PASSWORD = "newTournamentPassword";
    public static final String KEY_NEW_TOURNAMENT_TIME_LIMIT = "newTournamentTimeLimit";
    public static final String KEY_NEW_TOURNAMENT_CONSTR_TIME = "newTournamentConstructionTime";
    public static final String KEY_NEW_TOURNAMENT_TYPE = "newTournamentType";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS = "newTournamentNumberOfFreeMulligans";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_WINS = "newTournamentNumberOfWins";
    public static final String KEY_NEW_TOURNAMENT_PACKS_SEALED = "newTournamentPacksSealed";
    public static final String KEY_NEW_TOURNAMENT_PACKS_DRAFT = "newTournamentPacksDraft";
    public static final String KEY_NEW_TOURNAMENT_PACKS_RANDOM_DRAFT = "newTournamentPacksRandomDraft";
    public static final String KEY_NEW_TOURNAMENT_PLAYERS_SEALED = "newTournamentPlayersSealed";
    public static final String KEY_NEW_TOURNAMENT_PLAYERS_DRAFT = "newTournamentPlayersDraft";
    public static final String KEY_NEW_TOURNAMENT_DRAFT_TIMING = "newTournamentDraftTiming";
    public static final String KEY_NEW_TOURNAMENT_ALLOW_SPECTATORS = "newTournamentAllowSpectators";
    public static final String KEY_NEW_TOURNAMENT_ALLOW_ROLLBACKS = "newTournamentAllowRollbacks";
    public static final String KEY_NEW_TOURNAMENT_DECK_FILE = "newTournamentDeckFile";

    // pref setting for deck generator
    public static final String KEY_NEW_DECK_GENERATOR_DECK_SIZE = "newDeckGeneratorDeckSize";
    public static final String KEY_NEW_DECK_GENERATOR_SET = "newDeckGeneratorSet";
    public static final String KEY_NEW_DECK_GENERATOR_SINGLETON = "newDeckGeneratorSingleton";
    public static final String KEY_NEW_DECK_GENERATOR_ARTIFACTS = "newDeckGeneratorArtifacts";
    public static final String KEY_NEW_DECK_GENERATOR_NON_BASIC_LANDS = "newDeckGeneratorNonBasicLands";

    // used to save and restore the settings for the cardArea (draft, sideboarding, deck builder)
    public static final String KEY_DRAFT_VIEW = "draftView";

    public static final String KEY_DRAFT_SORT_BY = "draftSortBy";
    public static final String KEY_DRAFT_SORT_INDEX = "draftSortIndex";
    public static final String KEY_DRAFT_SORT_ASCENDING = "draftSortAscending";
    public static final String KEY_DRAFT_PILES_TOGGLE = "draftPilesToggle";

    public static final String KEY_BASE_SORT_BY = "baseSortBy";
    public static final String KEY_BASE_SORT_INDEX = "baseSortIndex";
    public static final String KEY_BASE_SORT_ASCENDING = "baseSortAscending";
    public static final String KEY_BASE_PILES_TOGGLE = "basePilesToggle";

    public static final String KEY_SIDEBOARD_SORT_BY = "sideboardSortBy";
    public static final String KEY_SIDEBOARD_SORT_INDEX = "sideboardSortIndex";
    public static final String KEY_SIDEBOARD_SORT_ASCENDING = "sideboardSortAscending";
    public static final String KEY_SIDEBOARD_PILES_TOGGLE = "sideboardPilesToggle";

    public static final String KEY_DECK_SORT_BY = "deckSortBy";
    public static final String KEY_DECK_SORT_INDEX = "deckSortIndex";
    public static final String KEY_DECK_SORT_ASCENDING = "deckSortAscending";
    public static final String KEY_DECK_PILES_TOGGLE = "deckPilesToggle";

    public static final String KEY_PROXY_ADDRESS = "proxyAddress";
    public static final String KEY_PROXY_PORT = "proxyPort";
    public static final String KEY_PROXY_USERNAME = "proxyUsername";
    public static final String KEY_PROXY_REMEMBER = "proxyRemember";
    public static final String KEY_PROXY_TYPE = "proxyType";
    public static final String KEY_PROXY_PSWD = "proxyPassword";
    public static final String KEY_CONNECTION_URL_SERVER_LIST = "connectionURLServerList";

    public static final String KEY_AVATAR = "selectedId";

    public static final String KEY_CONNECT_AUTO_CONNECT = "autoConnect";
    public static final String KEY_CONNECT_FLAG = "connectFlag";

    private static final Map<String, String> cache = new HashMap<>();

    private static final Boolean UPDATE_CACHE_POLICY = Boolean.TRUE;

    public static final String OPEN_CONNECTION_TAB = "Open-Connection-Tab";
    public static final String OPEN_PHASES_TAB = "Open-Phases-Tab";

    public static String PHASE_ON = "on";
    public static String PHASE_OFF = "off";

    public static final int DEFAULT_AVATAR_ID = 51;
    private static int selectedAvatarId = DEFAULT_AVATAR_ID;
    private static final Set<Integer> available_avatars = new HashSet<>();
    private static final Map<Integer, JPanel> panels = new HashMap<>();

    private static final Border GREEN_BORDER = BorderFactory.createLineBorder(Color.GREEN, 3);
    private static final Border BLACK_BORDER = BorderFactory.createLineBorder(Color.BLACK, 3);

    static {
        available_avatars.add(51);
        available_avatars.add(13);
        available_avatars.add(9);
        available_avatars.add(53);
        available_avatars.add(10);
        available_avatars.add(39);
        available_avatars.add(19);
        available_avatars.add(30);
        available_avatars.add(25);

        available_avatars.add(22);
        available_avatars.add(77);
        available_avatars.add(62);
    }

    private final JFileChooser fc = new JFileChooser();

    {
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    private final JFileChooser fc_i = new JFileChooser();

    {
        fc_i.setAcceptAllFileFilterUsed(false);
        fc_i.addChoosableFileFilter(new ImageFileFilter());
    }

    private static class ImageFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            String filename = f.getName();
            if (f.isDirectory()) {
                return true;
            }
            if (filename != null) {
                if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")
                        || filename.endsWith(".png") || filename.endsWith(".bmp")) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "*.png | *.bmp |*.jpg | *.jpeg";
        }
    }

    /**
     * Creates new form PreferencesDialog
     *
     * @param parent
     * @param modal
     */
    public PreferencesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtImageFolderPath.setEditable(false);
        cbProxyType.setModel(new DefaultComboBoxModel<>(Connection.ProxyType.values()));
        addAvatars();

        cbPreferedImageLanguage.setModel(new DefaultComboBoxModel<>(new String[]{"en", "de", "fr", "it", "es", "pt", "jp", "cn", "ru", "tw", "ko"}));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabsPanel = new javax.swing.JTabbedPane();
        tabMain = new javax.swing.JPanel();
        main_card = new javax.swing.JPanel();
        displayBigCardsInHand = new javax.swing.JCheckBox();
        showToolTipsInAnyZone = new javax.swing.JCheckBox();
        showCardName = new javax.swing.JCheckBox();
        main_game = new javax.swing.JPanel();
        nonLandPermanentsInOnePile = new javax.swing.JCheckBox();
        showPlayerNamesPermanently = new javax.swing.JCheckBox();
        showAbilityPickerForced = new javax.swing.JCheckBox();
        cbAllowRequestToShowHandCards = new javax.swing.JCheckBox();
        cbShowStormCounter = new javax.swing.JCheckBox();
        cbConfirmEmptyManaPool = new javax.swing.JCheckBox();
        cbAskMoveToGraveOrder = new javax.swing.JCheckBox();
        main_gamelog = new javax.swing.JPanel();
        cbGameLogAutoSave = new javax.swing.JCheckBox();
        cbDraftLogAutoSave = new javax.swing.JCheckBox();
        tabPhases = new javax.swing.JPanel();
        jLabelHeadLine = new javax.swing.JLabel();
        jLabelYourTurn = new javax.swing.JLabel();
        jLabelOpponentsTurn = new javax.swing.JLabel();
        jLabelUpkeep = new javax.swing.JLabel();
        checkBoxUpkeepYou = new javax.swing.JCheckBox();
        checkBoxUpkeepOthers = new javax.swing.JCheckBox();
        jLabelDraw = new javax.swing.JLabel();
        checkBoxDrawYou = new javax.swing.JCheckBox();
        checkBoxDrawOthers = new javax.swing.JCheckBox();
        jLabelMain1 = new javax.swing.JLabel();
        checkBoxMainYou = new javax.swing.JCheckBox();
        checkBoxMainOthers = new javax.swing.JCheckBox();
        jLabelBeforeCombat = new javax.swing.JLabel();
        checkBoxBeforeCYou = new javax.swing.JCheckBox();
        checkBoxBeforeCOthers = new javax.swing.JCheckBox();
        jLabelEndofCombat = new javax.swing.JLabel();
        checkBoxEndOfCYou = new javax.swing.JCheckBox();
        checkBoxEndOfCOthers = new javax.swing.JCheckBox();
        jLabelMain2 = new javax.swing.JLabel();
        checkBoxMain2You = new javax.swing.JCheckBox();
        checkBoxMain2Others = new javax.swing.JCheckBox();
        jLabelEndOfTurn = new javax.swing.JLabel();
        checkBoxEndTurnYou = new javax.swing.JCheckBox();
        checkBoxEndTurnOthers = new javax.swing.JCheckBox();
        phases_stopSettings = new javax.swing.JPanel();
        cbStopAttack = new javax.swing.JCheckBox();
        cbStopBlock = new javax.swing.JCheckBox();
        cbStopOnAllMain = new javax.swing.JCheckBox();
        cbStopOnAllEnd = new javax.swing.JCheckBox();
        cbPassPriorityCast = new javax.swing.JCheckBox();
        cbPassPriorityActivation = new javax.swing.JCheckBox();
        cbAutoOrderTrigger = new javax.swing.JCheckBox();
        tabImages = new javax.swing.JPanel();
        panelCardImages = new javax.swing.JPanel();
        cbUseDefaultImageFolder = new javax.swing.JCheckBox();
        txtImageFolderPath = new javax.swing.JTextField();
        btnBrowseImageLocation = new javax.swing.JButton();
        cbCheckForNewImages = new javax.swing.JCheckBox();
        cbSaveToZipFiles = new javax.swing.JCheckBox();
        cbPreferedImageLanguage = new javax.swing.JComboBox<String>();
        labelPreferedImageLanguage = new javax.swing.JLabel();
        panelBackgroundImages = new javax.swing.JPanel();
        cbUseDefaultBackground = new javax.swing.JCheckBox();
        txtBackgroundImagePath = new javax.swing.JTextField();
        btnBrowseBackgroundImage = new javax.swing.JButton();
        txtBattlefieldImagePath = new javax.swing.JTextField();
        btnBrowseBattlefieldImage = new javax.swing.JButton();
        cbUseDefaultBattleImage = new javax.swing.JCheckBox();
        cbUseRandomBattleImage = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tabSounds = new javax.swing.JPanel();
        sounds_clips = new javax.swing.JPanel();
        cbEnableGameSounds = new javax.swing.JCheckBox();
        cbEnableDraftSounds = new javax.swing.JCheckBox();
        cbEnableSkipButtonsSounds = new javax.swing.JCheckBox();
        cbEnableOtherSounds = new javax.swing.JCheckBox();
        sounds_backgroundMusic = new javax.swing.JPanel();
        cbEnableBattlefieldBGM = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        txtBattlefieldIBGMPath = new javax.swing.JTextField();
        btnBattlefieldBGMBrowse = new javax.swing.JButton();
        tabAvatars = new javax.swing.JPanel();
        avatarPane = new javax.swing.JScrollPane();
        avatarPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        tabConnection = new javax.swing.JPanel();
        lblProxyType = new javax.swing.JLabel();
        cbProxyType = new javax.swing.JComboBox<ProxyType>();
        pnlProxySettings = new javax.swing.JPanel();
        pnlProxy = new javax.swing.JPanel();
        lblProxyServer = new javax.swing.JLabel();
        txtProxyServer = new javax.swing.JTextField();
        lblProxyPort = new javax.swing.JLabel();
        txtProxyPort = new javax.swing.JTextField();
        lblProxyUserName = new javax.swing.JLabel();
        txtProxyUserName = new javax.swing.JTextField();
        lblProxyPassword = new javax.swing.JLabel();
        txtPasswordField = new javax.swing.JPasswordField();
        rememberPswd = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        connection_servers = new javax.swing.JPanel();
        lblURLServerList = new javax.swing.JLabel();
        txtURLServerList = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");

        main_card.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card"));

        displayBigCardsInHand.setText("Use big images (for high resolution screens)");
        displayBigCardsInHand.setToolTipText("Changes the size of the cards shown in hand. Switch this option off if you have a small screen size.");
        displayBigCardsInHand.setActionCommand("");
        displayBigCardsInHand.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        displayBigCardsInHand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayBigCardsInHandActionPerformed(evt);
            }
        });

        showToolTipsInAnyZone.setSelected(true);
        showToolTipsInAnyZone.setText("Show card tooltips while hovering with the mouse pointer over a card");
        showToolTipsInAnyZone.setToolTipText("");
        showToolTipsInAnyZone.setActionCommand("");
        showToolTipsInAnyZone.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        showToolTipsInAnyZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showToolTipsInAnyZoneActionPerformed(evt);
            }
        });

        showCardName.setSelected(true);
        showCardName.setText("Show card name on card panel");
        showCardName.setToolTipText("Write the card's name on the card to make the card name more recognizable.");
        showCardName.setActionCommand("");
        showCardName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        showCardName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showCardNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_cardLayout = new javax.swing.GroupLayout(main_card);
        main_card.setLayout(main_cardLayout);
        main_cardLayout.setHorizontalGroup(
            main_cardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_cardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(main_cardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(main_cardLayout.createSequentialGroup()
                        .addComponent(displayBigCardsInHand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(main_cardLayout.createSequentialGroup()
                        .addGroup(main_cardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showToolTipsInAnyZone)
                            .addComponent(showCardName))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        main_cardLayout.setVerticalGroup(
            main_cardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_cardLayout.createSequentialGroup()
                .addComponent(displayBigCardsInHand)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showToolTipsInAnyZone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showCardName))
        );

        main_game.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game"));

        nonLandPermanentsInOnePile.setSelected(true);
        nonLandPermanentsInOnePile.setLabel("Put non-land permanents in one pile");
        nonLandPermanentsInOnePile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonLandPermanentsInOnePileActionPerformed(evt);
            }
        });

        showPlayerNamesPermanently.setSelected(true);
        showPlayerNamesPermanently.setText("Show player names on avatar permanently");
        showPlayerNamesPermanently.setToolTipText("Instead showing the names only if you hover over the avatar with the mouse, the name is shown all the time.");
        showPlayerNamesPermanently.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showPlayerNamesPermanently.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPlayerNamesPermanentlyActionPerformed(evt);
            }
        });

        showAbilityPickerForced.setSelected(true);
        showAbilityPickerForced.setText("Show ability picker for abilities or spells without costs");
        showAbilityPickerForced.setToolTipText("This prevents you from accidently activating abilities without other costs than tapping or casting spells with 0 mana costs.");
        showAbilityPickerForced.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showAbilityPickerForced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAbilityPickerForcedActionPerformed(evt);
            }
        });

        cbAllowRequestToShowHandCards.setSelected(true);
        cbAllowRequestToShowHandCards.setText("Allow requests from players and spectators to show your hand cards");
        cbAllowRequestToShowHandCards.setToolTipText("<html>This is the default setting used for your matches. If activated other players or spectators<br>\nof your match can send a request so you can allow them to see your hand cards.");
        cbAllowRequestToShowHandCards.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbAllowRequestToShowHandCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAllowRequestToShowHandCardsActionPerformed(evt);
            }
        });

        cbShowStormCounter.setSelected(true);
        cbShowStormCounter.setText("Show the number of spell casts during the current turn");
        cbShowStormCounter.setToolTipText("<html>Adds a little box left to the short keys line with the number<br>\nof spells already cast during the current turn (storm counter).");
        cbShowStormCounter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbShowStormCounter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbShowStormCounterActionPerformed(evt);
            }
        });

        cbConfirmEmptyManaPool.setSelected(true);
        cbConfirmEmptyManaPool.setText("Confirm if you want to pass a phase/step but there is still mana in your mana pool");
        cbConfirmEmptyManaPool.setToolTipText("<html>If activated you get a confirm message if you pass priority while stack is empty<br>\n and you still have mana in your mana pool.");
        cbConfirmEmptyManaPool.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbConfirmEmptyManaPool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbConfirmEmptyManaPoolActionPerformed(evt);
            }
        });

        cbAskMoveToGraveOrder.setSelected(true);
        cbAskMoveToGraveOrder.setText("Ask player for setting order cards go to graveyard");
        cbAskMoveToGraveOrder.setToolTipText("<html>If activated and multiple cards go to the graveyard at the same time<br>\nthe player is asked to set the order of the cards.");
        cbAskMoveToGraveOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbAskMoveToGraveOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAskMoveToGraveOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_gameLayout = new javax.swing.GroupLayout(main_game);
        main_game.setLayout(main_gameLayout);
        main_gameLayout.setHorizontalGroup(
            main_gameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_gameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(main_gameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbAllowRequestToShowHandCards, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(main_gameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(showPlayerNamesPermanently, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nonLandPermanentsInOnePile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(showAbilityPickerForced, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(cbShowStormCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbConfirmEmptyManaPool, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAskMoveToGraveOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        main_gameLayout.setVerticalGroup(
            main_gameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_gameLayout.createSequentialGroup()
                .addComponent(nonLandPermanentsInOnePile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showPlayerNamesPermanently)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showAbilityPickerForced)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbAllowRequestToShowHandCards)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbShowStormCounter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbConfirmEmptyManaPool)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbAskMoveToGraveOrder)
                .addContainerGap())
        );

        nonLandPermanentsInOnePile.getAccessibleContext().setAccessibleName("nonLandPermanentsInOnePile");

        main_gamelog.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game log"));

        cbGameLogAutoSave.setSelected(true);
        cbGameLogAutoSave.setText("Auto save game logs     (to \"../Mage.Client/gamelogs/\" directory)");
        cbGameLogAutoSave.setToolTipText("The logs of all your games will be saved to the mentioned folder if this option is switched on.");
        cbGameLogAutoSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGameLogAutoSaveActionPerformed(evt);
            }
        });

        cbDraftLogAutoSave.setSelected(true);
        cbDraftLogAutoSave.setText("Auto save draft logs     (to \"../Mage.Client/gamelogs/\" directory)");
        cbDraftLogAutoSave.setToolTipText("The logs of all your games will be saved to the mentioned folder if this option is switched on.");
        cbDraftLogAutoSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDraftLogAutoSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_gamelogLayout = new javax.swing.GroupLayout(main_gamelog);
        main_gamelog.setLayout(main_gamelogLayout);
        main_gamelogLayout.setHorizontalGroup(
            main_gamelogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_gamelogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(main_gamelogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGameLogAutoSave, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDraftLogAutoSave, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        main_gamelogLayout.setVerticalGroup(
            main_gamelogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_gamelogLayout.createSequentialGroup()
                .addComponent(cbGameLogAutoSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDraftLogAutoSave))
        );

        javax.swing.GroupLayout tabMainLayout = new javax.swing.GroupLayout(tabMain);
        tabMain.setLayout(tabMainLayout);
        tabMainLayout.setHorizontalGroup(
            tabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(main_card, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(main_game, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(main_gamelog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabMainLayout.setVerticalGroup(
            tabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(main_card, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(main_game, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(main_gamelog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        main_card.getAccessibleContext().setAccessibleName("Game panel");

        tabsPanel.addTab("Main", tabMain);

        jLabelHeadLine.setText("Choose phases your game will stop on if not skipped by a skip action (e.g. F6):");

        jLabelYourTurn.setText("Your turn");

        jLabelOpponentsTurn.setText("Opponent(s) turn");

        jLabelUpkeep.setText("Upkeep:");

        jLabelDraw.setText("Draw:");

        jLabelMain1.setText("Main:");

        jLabelBeforeCombat.setText("Before combat:");

        jLabelEndofCombat.setText("End of combat:");

        jLabelMain2.setText("Main 2:");

        jLabelEndOfTurn.setText("End of turn:");

        phases_stopSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Stop settings"));
        phases_stopSettings.setLayout(new java.awt.GridLayout(7, 1));

        cbStopAttack.setSelected(true);
        cbStopAttack.setText("Stop on declare attackers step if you skip steps (F4/F5/F7) and attackers are available");
        cbStopAttack.setToolTipText("If you use F4, F5 or F7 to skip steps, you stop on declare attackers step if attackers are available. If this option is not activated, you also skip the declare attackers step with this actions. F9 does always skip the declare attackers step.");
        cbStopAttack.setActionCommand("");
        cbStopAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopAttackActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopAttack);

        cbStopBlock.setText("Stop on your declare blockers step also if no blockers available");
        cbStopBlock.setToolTipText("Also if you have no blockers to declare, the game stops at the declare blockers step.");
        cbStopBlock.setActionCommand("");
        cbStopBlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopBlockActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopBlock);

        cbStopOnAllMain.setText("Skip with F7 to next main phase (if not activated skip always to your next main phase)");
        cbStopOnAllMain.setToolTipText("If activated F7 skips to next main phases (regardless of the active players).");
        cbStopOnAllMain.setActionCommand("");
        cbStopOnAllMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopOnAllMainActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopOnAllMain);

        cbStopOnAllEnd.setText("Skip with F5 to next end step (if not activated only to end steps of opponents)");
        cbStopOnAllEnd.setToolTipText("If activated - F5 skips to the next end step (regardless of the current player)");
        cbStopOnAllEnd.setActionCommand("");
        cbStopOnAllEnd.setPreferredSize(new java.awt.Dimension(300, 25));
        cbStopOnAllEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopOnAllEndActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopOnAllEnd);

        cbPassPriorityCast.setText("Pass priority automatically after you have put a spell on the stack");
        cbPassPriorityCast.setToolTipText("If activated the system passes priority automatically for you if you have put a spell on the stack.");
        cbPassPriorityCast.setActionCommand("");
        cbPassPriorityCast.setPreferredSize(new java.awt.Dimension(300, 25));
        cbPassPriorityCast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPassPriorityCastActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbPassPriorityCast);

        cbPassPriorityActivation.setText("Pass priority automatically after you have put an activated ability on the stack");
        cbPassPriorityActivation.setToolTipText("If activated the system passes priority for you automatically after you have put an activated ability on the stack.");
        cbPassPriorityActivation.setActionCommand("");
        cbPassPriorityActivation.setPreferredSize(new java.awt.Dimension(300, 25));
        cbPassPriorityActivation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPassPriorityActivationActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbPassPriorityActivation);

        cbAutoOrderTrigger.setText("Set order for your triggers automatically if all have the same text");
        cbAutoOrderTrigger.setToolTipText("<HTML>If activated the order to put on the stack your triggers that trigger at the same time<br/>\nis set automatically if all have the same text.");
        cbAutoOrderTrigger.setActionCommand("");
        cbAutoOrderTrigger.setPreferredSize(new java.awt.Dimension(300, 25));
        cbAutoOrderTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutoOrderTriggerActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbAutoOrderTrigger);

        javax.swing.GroupLayout tabPhasesLayout = new javax.swing.GroupLayout(tabPhases);
        tabPhases.setLayout(tabPhasesLayout);
        tabPhasesLayout.setHorizontalGroup(
            tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPhasesLayout.createSequentialGroup()
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPhasesLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabPhasesLayout.createSequentialGroup()
                                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelUpkeep)
                                            .addComponent(jLabelBeforeCombat)
                                            .addComponent(jLabelEndofCombat)
                                            .addComponent(jLabelMain2)
                                            .addComponent(jLabelEndOfTurn))
                                        .addGap(77, 77, 77)
                                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(tabPhasesLayout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabelYourTurn)
                                                .addGap(32, 32, 32)
                                                .addComponent(jLabelOpponentsTurn))
                                            .addGroup(tabPhasesLayout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(checkBoxDrawYou)
                                                    .addComponent(checkBoxUpkeepYou)
                                                    .addComponent(checkBoxMainYou)
                                                    .addComponent(checkBoxBeforeCYou)
                                                    .addComponent(checkBoxEndOfCYou)
                                                    .addComponent(checkBoxMain2You)
                                                    .addComponent(checkBoxEndTurnYou))
                                                .addGap(78, 78, 78)
                                                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(checkBoxUpkeepOthers)
                                                    .addComponent(checkBoxBeforeCOthers)
                                                    .addComponent(checkBoxMainOthers)
                                                    .addComponent(checkBoxEndOfCOthers)
                                                    .addComponent(checkBoxDrawOthers)
                                                    .addComponent(checkBoxMain2Others)
                                                    .addComponent(checkBoxEndTurnOthers)))))
                                    .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabelMain1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelDraw, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(tabPhasesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelHeadLine)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(phases_stopSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabPhasesLayout.setVerticalGroup(
            tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPhasesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addComponent(jLabelOpponentsTurn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkBoxUpkeepOthers))
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(tabPhasesLayout.createSequentialGroup()
                                .addComponent(jLabelHeadLine)
                                .addGap(20, 20, 20))
                            .addComponent(jLabelYourTurn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkBoxUpkeepYou)
                            .addComponent(jLabelUpkeep))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelDraw)
                    .addComponent(checkBoxDrawYou)
                    .addComponent(checkBoxDrawOthers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelMain1)
                    .addComponent(checkBoxMainYou)
                    .addComponent(checkBoxMainOthers))
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelBeforeCombat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkBoxBeforeCYou, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(checkBoxBeforeCOthers)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelEndofCombat)
                    .addComponent(checkBoxEndOfCYou)
                    .addComponent(checkBoxEndOfCOthers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelMain2)
                    .addComponent(checkBoxMain2You)
                    .addComponent(checkBoxMain2Others))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkBoxEndTurnYou)
                    .addComponent(jLabelEndOfTurn)
                    .addComponent(checkBoxEndTurnOthers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phases_stopSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsPanel.addTab("Phases & Priority", tabPhases);

        panelCardImages.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card images:"));

        cbUseDefaultImageFolder.setText("Use default location to save images");
        cbUseDefaultImageFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseDefaultImageFolderActionPerformed(evt);
            }
        });

        txtImageFolderPath.setToolTipText("The selected image will be used as background picture. You have to restart MAGE to view a changed background image.");

        btnBrowseImageLocation.setText("Browse...");
        btnBrowseImageLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseImageLocationActionPerformed(evt);
            }
        });

        cbCheckForNewImages.setText("Check for new images on startup");
        cbCheckForNewImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCheckForNewImagesActionPerformed(evt);
            }
        });

        cbSaveToZipFiles.setText("Store images in zip files");
        cbSaveToZipFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSaveToZipFilesActionPerformed(evt);
            }
        });

        cbPreferedImageLanguage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelPreferedImageLanguage.setText("Prefered image language:");
        labelPreferedImageLanguage.setFocusable(false);

        javax.swing.GroupLayout panelCardImagesLayout = new javax.swing.GroupLayout(panelCardImages);
        panelCardImages.setLayout(panelCardImagesLayout);
        panelCardImagesLayout.setHorizontalGroup(
            panelCardImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCardImagesLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtImageFolderPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowseImageLocation))
            .addGroup(panelCardImagesLayout.createSequentialGroup()
                .addGroup(panelCardImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbUseDefaultImageFolder)
                    .addComponent(cbCheckForNewImages)
                    .addGroup(panelCardImagesLayout.createSequentialGroup()
                        .addGroup(panelCardImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCardImagesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelPreferedImageLanguage))
                            .addComponent(cbSaveToZipFiles, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbPreferedImageLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelCardImagesLayout.setVerticalGroup(
            panelCardImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCardImagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbUseDefaultImageFolder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCardImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtImageFolderPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseImageLocation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbCheckForNewImages)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbSaveToZipFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCardImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPreferedImageLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPreferedImageLanguage))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBackgroundImages.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Background images setting:"));

        cbUseDefaultBackground.setText("Use default image");
        cbUseDefaultBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseDefaultBackgroundActionPerformed(evt);
            }
        });

        txtBackgroundImagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBackgroundImagePathActionPerformed(evt);
            }
        });

        btnBrowseBackgroundImage.setText("Browse...");
        btnBrowseBackgroundImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseBackgroundImageActionPerformed(evt);
            }
        });

        txtBattlefieldImagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBattlefieldImagePathActionPerformed(evt);
            }
        });

        btnBrowseBattlefieldImage.setText("Browse...");
        btnBrowseBattlefieldImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseBattlefieldImageActionPerformed(evt);
            }
        });

        cbUseDefaultBattleImage.setText("Use default battlefield image");
        cbUseDefaultBattleImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseDefaultBattleImageActionPerformed(evt);
            }
        });

        cbUseRandomBattleImage.setText("Select random battlefield image");
        cbUseRandomBattleImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseRandomBattleImageActionPerformed(evt);
            }
        });

        jLabel14.setText("Background:");

        jLabel15.setText("Battlefield:");

        javax.swing.GroupLayout panelBackgroundImagesLayout = new javax.swing.GroupLayout(panelBackgroundImages);
        panelBackgroundImages.setLayout(panelBackgroundImagesLayout);
        panelBackgroundImagesLayout.setHorizontalGroup(
            panelBackgroundImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundImagesLayout.createSequentialGroup()
                .addGroup(panelBackgroundImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackgroundImagesLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel14))
                    .addGroup(panelBackgroundImagesLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBackgroundImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackgroundImagesLayout.createSequentialGroup()
                        .addComponent(txtBattlefieldImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowseBattlefieldImage))
                    .addGroup(panelBackgroundImagesLayout.createSequentialGroup()
                        .addComponent(txtBackgroundImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowseBackgroundImage)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelBackgroundImagesLayout.createSequentialGroup()
                .addGroup(panelBackgroundImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbUseRandomBattleImage)
                    .addComponent(cbUseDefaultBattleImage)
                    .addComponent(cbUseDefaultBackground))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBackgroundImagesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtBackgroundImagePath, txtBattlefieldImagePath});

        panelBackgroundImagesLayout.setVerticalGroup(
            panelBackgroundImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundImagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbUseDefaultBackground)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBackgroundImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBackgroundImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseBackgroundImage)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbUseDefaultBattleImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbUseRandomBattleImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBackgroundImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBattlefieldImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseBattlefieldImage)
                    .addComponent(jLabel15)))
        );

        javax.swing.GroupLayout tabImagesLayout = new javax.swing.GroupLayout(tabImages);
        tabImages.setLayout(tabImagesLayout);
        tabImagesLayout.setHorizontalGroup(
            tabImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabImagesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCardImages, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBackgroundImages, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabImagesLayout.setVerticalGroup(
            tabImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabImagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCardImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBackgroundImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabsPanel.addTab("Images", tabImages);

        sounds_clips.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Clips"));
        sounds_clips.setLayout(new java.awt.GridLayout(4, 0));

        cbEnableGameSounds.setText("Enable game sounds");
        cbEnableGameSounds.setToolTipText("Sounds that will be played for certain actions (e.g. play land, attack, etc.) during the game.");
        cbEnableGameSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEnableGameSoundsActionPerformed(evt);
            }
        });
        sounds_clips.add(cbEnableGameSounds);

        cbEnableDraftSounds.setText("Enable draft sounds");
        cbEnableDraftSounds.setToolTipText("Sounds that will be played during drafting for card picking or warining if time runs out.");
        cbEnableDraftSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEnableDraftSoundsActionPerformed(evt);
            }
        });
        sounds_clips.add(cbEnableDraftSounds);

        cbEnableSkipButtonsSounds.setText("Enable skip button sounds");
        cbEnableSkipButtonsSounds.setToolTipText("Sounds that will be played if a priority skip action (F4/F5/F7/F9) or cancel skip action (F3) is used.");
        cbEnableSkipButtonsSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEnableSkipButtonsSoundsActionPerformed(evt);
            }
        });
        sounds_clips.add(cbEnableSkipButtonsSounds);

        cbEnableOtherSounds.setText("Enable other sounds");
        cbEnableOtherSounds.setToolTipText("Sounds that will be played for actions outside of games (e.g. whisper, player joins your game, player submits a deck ...).");
        cbEnableOtherSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEnableOtherSoundsActionPerformed(evt);
            }
        });
        sounds_clips.add(cbEnableOtherSounds);

        sounds_backgroundMusic.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Music"));

        cbEnableBattlefieldBGM.setText("Play music during match");
        cbEnableBattlefieldBGM.setToolTipText("During your matches music will be played from the seleced folder.");
        cbEnableBattlefieldBGM.setActionCommand("Play automatically during matches");
        cbEnableBattlefieldBGM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEnableBattlefieldBGMActionPerformed(evt);
            }
        });

        jLabel16.setText("Playing from folder:");
        jLabel16.setToolTipText("");

        txtBattlefieldIBGMPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBattlefieldIBGMPathActionPerformed(evt);
            }
        });

        btnBattlefieldBGMBrowse.setText("Browse...");
        btnBattlefieldBGMBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBattlefieldBGMBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sounds_backgroundMusicLayout = new javax.swing.GroupLayout(sounds_backgroundMusic);
        sounds_backgroundMusic.setLayout(sounds_backgroundMusicLayout);
        sounds_backgroundMusicLayout.setHorizontalGroup(
            sounds_backgroundMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sounds_backgroundMusicLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBattlefieldIBGMPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBattlefieldBGMBrowse))
            .addGroup(sounds_backgroundMusicLayout.createSequentialGroup()
                .addComponent(cbEnableBattlefieldBGM)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        sounds_backgroundMusicLayout.setVerticalGroup(
            sounds_backgroundMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sounds_backgroundMusicLayout.createSequentialGroup()
                .addComponent(cbEnableBattlefieldBGM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sounds_backgroundMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBattlefieldIBGMPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBattlefieldBGMBrowse)
                    .addComponent(jLabel16)))
        );

        javax.swing.GroupLayout tabSoundsLayout = new javax.swing.GroupLayout(tabSounds);
        tabSounds.setLayout(tabSoundsLayout);
        tabSoundsLayout.setHorizontalGroup(
            tabSoundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSoundsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabSoundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sounds_clips, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sounds_backgroundMusic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabSoundsLayout.setVerticalGroup(
            tabSoundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSoundsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sounds_clips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sounds_backgroundMusic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sounds_clips.getAccessibleContext().setAccessibleDescription("");

        tabsPanel.addTab("Sounds", tabSounds);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Choose your avatar:");

        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("New avatars:");

        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout avatarPanelLayout = new javax.swing.GroupLayout(avatarPanel);
        avatarPanel.setLayout(avatarPanelLayout);
        avatarPanelLayout.setHorizontalGroup(
            avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(avatarPanelLayout.createSequentialGroup()
                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(avatarPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(avatarPanelLayout.createSequentialGroup()
                        .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(avatarPanelLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(avatarPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(avatarPanelLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13))))
                        .addGap(32, 32, 32)
                        .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        avatarPanelLayout.setVerticalGroup(
            avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(avatarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(avatarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(avatarPanelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        avatarPane.setViewportView(avatarPanel);

        javax.swing.GroupLayout tabAvatarsLayout = new javax.swing.GroupLayout(tabAvatars);
        tabAvatars.setLayout(tabAvatarsLayout);
        tabAvatarsLayout.setHorizontalGroup(
            tabAvatarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(avatarPane, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        tabAvatarsLayout.setVerticalGroup(
            tabAvatarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(avatarPane, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );

        tabsPanel.addTab("Avatars", tabAvatars);

        lblProxyType.setText("Proxy:");

        cbProxyType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProxyTypeActionPerformed(evt);
            }
        });

        pnlProxySettings.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblProxyServer.setText("Server:");

        lblProxyPort.setText("Port:");

        txtProxyPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProxyPortkeyTyped(evt);
            }
        });

        lblProxyUserName.setText("User Name:");

        lblProxyPassword.setText("Password:");

        txtPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordFieldActionPerformed(evt);
            }
        });

        rememberPswd.setText("Remember Password");
        rememberPswd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rememberPswdActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel11.setText("Note: password won't be encrypted!");

        javax.swing.GroupLayout pnlProxyLayout = new javax.swing.GroupLayout(pnlProxy);
        pnlProxy.setLayout(pnlProxyLayout);
        pnlProxyLayout.setHorizontalGroup(
            pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProxyLayout.createSequentialGroup()
                        .addComponent(rememberPswd)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel11)
                        .addGap(34, 34, 34))
                    .addGroup(pnlProxyLayout.createSequentialGroup()
                        .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProxyPort)
                            .addComponent(lblProxyPassword)
                            .addComponent(lblProxyServer)
                            .addComponent(lblProxyUserName))
                        .addGap(19, 19, 19)
                        .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtProxyUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtProxyServer))
                        .addContainerGap())))
        );
        pnlProxyLayout.setVerticalGroup(
            pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyServer))
                .addGap(8, 8, 8)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProxyPort)
                    .addComponent(txtProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProxyUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyPassword))
                .addGap(18, 18, 18)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rememberPswd)
                    .addComponent(jLabel11))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlProxySettingsLayout = new javax.swing.GroupLayout(pnlProxySettings);
        pnlProxySettings.setLayout(pnlProxySettingsLayout);
        pnlProxySettingsLayout.setHorizontalGroup(
            pnlProxySettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxySettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProxy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlProxySettingsLayout.setVerticalGroup(
            pnlProxySettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxySettingsLayout.createSequentialGroup()
                .addComponent(pnlProxy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        connection_servers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Servers"));

        lblURLServerList.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblURLServerList.setText("URL server list:");
        lblURLServerList.setToolTipText("");
        lblURLServerList.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lblURLServerList.setPreferredSize(new java.awt.Dimension(110, 16));
        lblURLServerList.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        txtURLServerList.setToolTipText("The URL XMage tries to read a server list from.");
        txtURLServerList.setPreferredSize(new java.awt.Dimension(300, 22));

        jLabel17.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel17.setText("e.g.: http://XMage.de/files/server-list.txt");

        javax.swing.GroupLayout connection_serversLayout = new javax.swing.GroupLayout(connection_servers);
        connection_servers.setLayout(connection_serversLayout);
        connection_serversLayout.setHorizontalGroup(
            connection_serversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connection_serversLayout.createSequentialGroup()
                .addGroup(connection_serversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(connection_serversLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblURLServerList, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtURLServerList, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(connection_serversLayout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel17)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        connection_serversLayout.setVerticalGroup(
            connection_serversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connection_serversLayout.createSequentialGroup()
                .addGroup(connection_serversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblURLServerList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtURLServerList, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17))
        );

        javax.swing.GroupLayout tabConnectionLayout = new javax.swing.GroupLayout(tabConnection);
        tabConnection.setLayout(tabConnectionLayout);
        tabConnectionLayout.setHorizontalGroup(
            tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabConnectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabConnectionLayout.createSequentialGroup()
                        .addComponent(lblProxyType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlProxySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(connection_servers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabConnectionLayout.setVerticalGroup(
            tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabConnectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(connection_servers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProxyType)
                    .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlProxySettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pnlProxySettings.getAccessibleContext().setAccessibleDescription("");

        tabsPanel.addTab("Connection", tabConnection);

        saveButton.setLabel("Save");
        saveButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        exitButton.setLabel("Exit");
        exitButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(tabsPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabsPanel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(saveButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        Preferences prefs = MageFrame.getPreferences();

        // main
        save(prefs, dialog.displayBigCardsInHand, KEY_HAND_USE_BIG_CARDS, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showToolTipsInAnyZone, KEY_SHOW_TOOLTIPS_ANY_ZONE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showCardName, KEY_SHOW_CARD_NAMES, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showAbilityPickerForced, KEY_SHOW_ABILITY_PICKER_FORCED, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbShowStormCounter, KEY_GAME_SHOW_STORM_COUNTER, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbConfirmEmptyManaPool, KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbAskMoveToGraveOrder, KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbDraftLogAutoSave, KEY_DRAFT_LOG_AUTO_SAVE, "true", "false", UPDATE_CACHE_POLICY);

        // Phases & Priority
        save(prefs, dialog.checkBoxUpkeepYou, UPKEEP_YOU);
        save(prefs, dialog.checkBoxDrawYou, DRAW_YOU);
        save(prefs, dialog.checkBoxMainYou, MAIN_YOU);
        save(prefs, dialog.checkBoxBeforeCYou, BEFORE_COMBAT_YOU);
        save(prefs, dialog.checkBoxEndOfCYou, END_OF_COMBAT_YOU);
        save(prefs, dialog.checkBoxMain2You, MAIN_2_YOU);
        save(prefs, dialog.checkBoxEndTurnYou, END_OF_TURN_YOU);

        save(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS);
        save(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS);
        save(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS);
        save(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxEndOfCOthers, END_OF_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxMain2Others, MAIN_2_OTHERS);
        save(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS);

        save(prefs, dialog.cbStopAttack, KEY_STOP_ATTACK, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopBlock, KEY_STOP_BLOCK, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopOnAllMain, KEY_STOP_ALL_MAIN_PHASES, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopOnAllEnd, KEY_STOP_ALL_END_PHASES, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbPassPriorityCast, KEY_PASS_PRIORITY_CAST, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbPassPriorityActivation, KEY_PASS_PRIORITY_ACTIVATION, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbAutoOrderTrigger, KEY_AUTO_ORDER_TRIGGER, "true", "false", UPDATE_CACHE_POLICY);

        // images
        save(prefs, dialog.cbUseDefaultImageFolder, KEY_CARD_IMAGES_USE_DEFAULT, "true", "false", UPDATE_CACHE_POLICY);
        saveImagesPath(prefs);
        save(prefs, dialog.cbCheckForNewImages, KEY_CARD_IMAGES_CHECK, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbSaveToZipFiles, KEY_CARD_IMAGES_SAVE_TO_ZIP, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbPreferedImageLanguage, KEY_CARD_IMAGES_PREF_LANGUAGE);

        save(prefs, dialog.cbUseDefaultBackground, KEY_BACKGROUND_IMAGE_DEFAULT, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbUseDefaultBattleImage, KEY_BATTLEFIELD_IMAGE_DEFAULT, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbUseRandomBattleImage, KEY_BATTLEFIELD_IMAGE_RANDOM, "true", "false", UPDATE_CACHE_POLICY);

        // sounds
        save(prefs, dialog.cbEnableGameSounds, KEY_SOUNDS_GAME_ON, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbEnableDraftSounds, KEY_SOUNDS_DRAFT_ON, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbEnableSkipButtonsSounds, KEY_SOUNDS_SKIP_BUTTONS_ON, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbEnableOtherSounds, KEY_SOUNDS_OTHER_ON, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbEnableBattlefieldBGM, KEY_SOUNDS_MATCH_MUSIC_ON, "true", "false", UPDATE_CACHE_POLICY);
        saveSoundPath(prefs);

        // connection
        save(prefs, dialog.cbProxyType, KEY_PROXY_TYPE);
        save(prefs, dialog.txtProxyServer, KEY_PROXY_ADDRESS);
        save(prefs, dialog.txtProxyPort, KEY_PROXY_PORT);
        save(prefs, dialog.txtProxyUserName, KEY_PROXY_USERNAME);
        save(prefs, dialog.rememberPswd, KEY_PROXY_REMEMBER, "true", "false", UPDATE_CACHE_POLICY);
        if (dialog.rememberPswd.isSelected()) {
            char[] input = txtPasswordField.getPassword();
            prefs.put(KEY_PROXY_PSWD, new String(input));
        }
        save(prefs, dialog.txtURLServerList, KEY_CONNECTION_URL_SERVER_LIST);

        // Avatar
        if (available_avatars.contains(selectedAvatarId)) {
            prefs.put(KEY_AVATAR, String.valueOf(selectedAvatarId));
            updateCache(KEY_AVATAR, String.valueOf(selectedAvatarId));
        }

        try {
            MageFrame.getSession().updatePreferencesForServer(getUserData());

            prefs.flush();
        } catch (BackingStoreException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: couldn't save preferences. Please try once again.");
        }

        dialog.setVisible(false);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        dialog.setVisible(false);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void cbUseDefaultImageFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUseDefaultImageFolderActionPerformed
        if (cbUseDefaultImageFolder.isSelected()) {
            useDefaultPath();
        } else {
            useConfigurablePath();
        }
    }//GEN-LAST:event_cbUseDefaultImageFolderActionPerformed

    private void useDefaultPath() {
        txtImageFolderPath.setText("./plugins/images/");
        txtImageFolderPath.setEnabled(false);
        btnBrowseImageLocation.setEnabled(false);
    }

    private void useConfigurablePath() {
        String path = cache.get(KEY_CARD_IMAGES_PATH);
        dialog.txtImageFolderPath.setText(path);
        txtImageFolderPath.setEnabled(true);
        btnBrowseImageLocation.setEnabled(true);
    }

    private void btnBrowseImageLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseImageLocationActionPerformed
        int returnVal = fc.showOpenDialog(PreferencesDialog.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtImageFolderPath.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_btnBrowseImageLocationActionPerformed

    private void cbProxyTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProxyTypeActionPerformed
        this.showProxySettings();
    }//GEN-LAST:event_cbProxyTypeActionPerformed

    private void txtPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordFieldActionPerformed
    }//GEN-LAST:event_txtPasswordFieldActionPerformed

    private void txtProxyPortkeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProxyPortkeyTyped
    }//GEN-LAST:event_txtProxyPortkeyTyped

    private void rememberPswdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rememberPswdActionPerformed
    }//GEN-LAST:event_rememberPswdActionPerformed

    private void cbSaveToZipFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSaveToZipFilesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSaveToZipFilesActionPerformed

    private void cbCheckForNewImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCheckForNewImagesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCheckForNewImagesActionPerformed

    private void cbEnableGameSoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEnableGameSoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEnableGameSoundsActionPerformed

    private void cbEnableBattlefieldBGMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEnableBattlefieldBGMActionPerformed
        if (cbEnableBattlefieldBGM.isSelected()) {
            txtBattlefieldIBGMPath.setEnabled(true);
            btnBattlefieldBGMBrowse.setEnabled(true);
        } else {
            txtBattlefieldIBGMPath.setEnabled(false);
            btnBattlefieldBGMBrowse.setEnabled(false);
        }
    }//GEN-LAST:event_cbEnableBattlefieldBGMActionPerformed

    private void cbUseDefaultBackgroundActionPerformed(java.awt.event.ActionEvent evt) {
        if (cbUseDefaultBackground.isSelected()) {
            useDefaultBackgroundImage();
        } else {
            useSelectBackgroundImage();
        }
    }

    private void useDefaultBackgroundImage() {
        txtBackgroundImagePath.setEnabled(false);
        btnBrowseBackgroundImage.setEnabled(false);
        txtBackgroundImagePath.setText("");
    }

    private void useSelectBackgroundImage() {
        String path = cache.get(KEY_BACKGROUND_IMAGE);
        dialog.txtBackgroundImagePath.setText(path);
        txtBackgroundImagePath.setEnabled(true);
        btnBrowseBackgroundImage.setEnabled(true);
    }

    private void cbUseDefaultBattleImageActionPerformed(java.awt.event.ActionEvent evt) {
        if (cbUseDefaultBattleImage.isSelected()) {
            useDefaultBattlefield();
        } else {
            useSelectedOrRandom();
        }
    }

    private void useDefaultBattlefield() {
        cbUseRandomBattleImage.setEnabled(false);
        txtBattlefieldImagePath.setEnabled(false);
        btnBrowseBattlefieldImage.setEnabled(false);
    }

    private void useSelectedOrRandom() {
        cbUseRandomBattleImage.setEnabled(true);
        String temp = cache.get(KEY_BATTLEFIELD_IMAGE_RANDOM);
        if (temp != null) {
            if (temp.equals("true")) {
                useRandomBattleField();
                cbUseRandomBattleImage.setSelected(true);
            } else {
                useSelectedBattleField();
                cbUseRandomBattleImage.setSelected(false);
            }
        } else {
            useSelectedBattleField();
            cbUseRandomBattleImage.setSelected(false);
        }
    }

    private void cbUseRandomBattleImageActionPerformed(java.awt.event.ActionEvent evt) {
        if (cbUseRandomBattleImage.isSelected()) {
            useRandomBattleField();
        } else {
            useSelectedBattleField();
        }
    }

    private void useRandomBattleField() {
        txtBattlefieldImagePath.setEnabled(false);
        btnBrowseBattlefieldImage.setEnabled(false);
    }

    private void useSelectedBattleField() {
        txtBattlefieldImagePath.setEnabled(true);
        btnBrowseBattlefieldImage.setEnabled(true);
    }

    private void btnBrowseBackgroundImageActionPerformed(java.awt.event.ActionEvent evt) {
        int returnVal = fc_i.showOpenDialog(PreferencesDialog.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc_i.getSelectedFile();
            txtBackgroundImagePath.setText(file.getAbsolutePath());
        }
    }

    private void btnBrowseBattlefieldImageActionPerformed(java.awt.event.ActionEvent evt) {
        int returnVal = fc_i.showOpenDialog(PreferencesDialog.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc_i.getSelectedFile();
            txtBattlefieldImagePath.setText(file.getAbsolutePath());
        }
    }

    private void txtBackgroundImagePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBackgroundImagePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBackgroundImagePathActionPerformed

    private void txtBattlefieldImagePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBattlefieldImagePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBattlefieldImagePathActionPerformed

    private void txtBattlefieldIBGMPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBattlefieldIBGMPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBattlefieldIBGMPathActionPerformed

    private void btnBattlefieldBGMBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBattlefieldBGMBrowseActionPerformed
        // TODO add your handling code here:
        int returnVal = fc.showOpenDialog(PreferencesDialog.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtBattlefieldIBGMPath.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_btnBattlefieldBGMBrowseActionPerformed

    private void cbGameLogAutoSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGameLogAutoSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGameLogAutoSaveActionPerformed

    private void nonLandPermanentsInOnePileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonLandPermanentsInOnePileActionPerformed

    }//GEN-LAST:event_nonLandPermanentsInOnePileActionPerformed

    private void showPlayerNamesPermanentlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPlayerNamesPermanentlyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showPlayerNamesPermanentlyActionPerformed

    private void displayBigCardsInHandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayBigCardsInHandActionPerformed

    }//GEN-LAST:event_displayBigCardsInHandActionPerformed

    private void showCardNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCardNameActionPerformed

    }//GEN-LAST:event_showCardNameActionPerformed

    private void showAbilityPickerForcedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAbilityPickerForcedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showAbilityPickerForcedActionPerformed

    private void cbEnableOtherSoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEnableOtherSoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEnableOtherSoundsActionPerformed

    private void showToolTipsInAnyZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToolTipsInAnyZoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showToolTipsInAnyZoneActionPerformed

    private void cbStopAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopAttackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopAttackActionPerformed

    private void cbStopBlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopBlockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopBlockActionPerformed

    private void cbStopOnAllMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopOnAllMainActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopOnAllMainActionPerformed

    private void cbStopOnAllEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopOnAllEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopOnAllEndActionPerformed

    private void cbEnableDraftSoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEnableDraftSoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEnableDraftSoundsActionPerformed

    private void cbEnableSkipButtonsSoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEnableSkipButtonsSoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEnableSkipButtonsSoundsActionPerformed

    private void cbAllowRequestToShowHandCardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAllowRequestToShowHandCardsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAllowRequestToShowHandCardsActionPerformed

    private void cbShowStormCounterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbShowStormCounterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbShowStormCounterActionPerformed

    private void cbConfirmEmptyManaPoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbConfirmEmptyManaPoolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbConfirmEmptyManaPoolActionPerformed

    private void cbAskMoveToGraveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAskMoveToGraveOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAskMoveToGraveOrderActionPerformed

    private void cbDraftLogAutoSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDraftLogAutoSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDraftLogAutoSaveActionPerformed

    private void cbPassPriorityCastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPassPriorityCastActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPassPriorityCastActionPerformed

    private void cbPassPriorityActivationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPassPriorityActivationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPassPriorityActivationActionPerformed

    private void cbAutoOrderTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutoOrderTriggerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAutoOrderTriggerActionPerformed

    private void showProxySettings() {
        if (cbProxyType.getSelectedItem() == Connection.ProxyType.SOCKS) {
            this.pnlProxy.setVisible(true);
            this.pnlProxySettings.setVisible(true);
        } else if (cbProxyType.getSelectedItem() == Connection.ProxyType.HTTP) {
            this.pnlProxy.setVisible(true);
            this.pnlProxySettings.setVisible(true);
        } else if (cbProxyType.getSelectedItem() == Connection.ProxyType.NONE) {
            this.pnlProxy.setVisible(false);
            this.pnlProxySettings.setVisible(false);
        }
        this.pack();
        this.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        int param = 0;
        if (args.length > 0) {
            String param1 = args[0];
            if (param1.equals(OPEN_CONNECTION_TAB)) {
                param = 4;
            }
            if (param1.equals(OPEN_PHASES_TAB)) {
                param = 1;
            }
        }
        final int openedTab = param;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!dialog.isVisible()) {
                    Preferences prefs = MageFrame.getPreferences();

                    // Main & Phases
                    loadPhases(prefs);

                    // Images
                    loadImagesSettings(prefs);

                    // Sounds
                    loadSoundSettings(prefs);

                    // Connection
                    loadProxySettings(prefs);

                    // Selected avatar
                    loadSelectedAvatar(prefs);

                    dialog.reset();
                    // open specified tab before displaying
                    openTab(openedTab);

                    dialog.setLocation(300, 200);

                    dialog.setVisible(true);
                } else {
                    dialog.requestFocus();
                }
            }
        });
    }

    private static void loadPhases(Preferences prefs) {
        load(prefs, dialog.displayBigCardsInHand, KEY_HAND_USE_BIG_CARDS, "true", "true");
        load(prefs, dialog.showToolTipsInAnyZone, KEY_SHOW_TOOLTIPS_ANY_ZONE, "true");
        load(prefs, dialog.showCardName, KEY_SHOW_CARD_NAMES, "true");
        load(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true");
        load(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true");
        load(prefs, dialog.showAbilityPickerForced, KEY_SHOW_ABILITY_PICKER_FORCED, "true");
        load(prefs, dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true");
        load(prefs, dialog.cbShowStormCounter, KEY_GAME_SHOW_STORM_COUNTER, "true");
        load(prefs, dialog.cbConfirmEmptyManaPool, KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true");
        load(prefs, dialog.cbAskMoveToGraveOrder, KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "true");

        load(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true");
        load(prefs, dialog.cbDraftLogAutoSave, KEY_DRAFT_LOG_AUTO_SAVE, "true");

        load(prefs, dialog.checkBoxUpkeepYou, UPKEEP_YOU, "on", "on");
        load(prefs, dialog.checkBoxDrawYou, DRAW_YOU, "on", "on");
        load(prefs, dialog.checkBoxMainYou, MAIN_YOU, "on", "on");
        load(prefs, dialog.checkBoxBeforeCYou, BEFORE_COMBAT_YOU, "on", "on");
        load(prefs, dialog.checkBoxEndOfCYou, END_OF_COMBAT_YOU, "on", "on");
        load(prefs, dialog.checkBoxMain2You, MAIN_2_YOU, "on", "on");
        load(prefs, dialog.checkBoxEndTurnYou, END_OF_TURN_YOU, "on", "on");

        load(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxEndOfCOthers, END_OF_COMBAT_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxMain2Others, MAIN_2_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS, "on", "on");

        load(prefs, dialog.cbStopAttack, KEY_STOP_ATTACK, "true", "true");
        load(prefs, dialog.cbStopBlock, KEY_STOP_BLOCK, "true", "true");
        load(prefs, dialog.cbStopOnAllMain, KEY_STOP_ALL_MAIN_PHASES, "true", "false");
        load(prefs, dialog.cbStopOnAllEnd, KEY_STOP_ALL_END_PHASES, "true", "false");
        load(prefs, dialog.cbPassPriorityCast, KEY_PASS_PRIORITY_CAST, "true", "false");
        load(prefs, dialog.cbPassPriorityActivation, KEY_PASS_PRIORITY_ACTIVATION, "true", "false");
        load(prefs, dialog.cbAutoOrderTrigger, KEY_AUTO_ORDER_TRIGGER, "true", "true");

    }

    private static void loadImagesSettings(Preferences prefs) {
        String prop = prefs.get(KEY_CARD_IMAGES_USE_DEFAULT, "true");
        if (prop.equals("true")) {
            dialog.cbUseDefaultImageFolder.setSelected(true);
            dialog.useDefaultPath();
        } else {
            dialog.cbUseDefaultImageFolder.setSelected(false);
            dialog.useConfigurablePath();
            String path = prefs.get(KEY_CARD_IMAGES_PATH, "");
            dialog.txtImageFolderPath.setText(path);
            updateCache(KEY_CARD_IMAGES_PATH, path);
        }
        load(prefs, dialog.cbCheckForNewImages, KEY_CARD_IMAGES_CHECK, "true");
        load(prefs, dialog.cbSaveToZipFiles, KEY_CARD_IMAGES_SAVE_TO_ZIP, "true");
        dialog.cbPreferedImageLanguage.setSelectedItem(MageFrame.getPreferences().get(KEY_CARD_IMAGES_PREF_LANGUAGE, "en"));

        //add background load precedure
        prop = prefs.get(KEY_BACKGROUND_IMAGE_DEFAULT, "true");
        if (prop.equals("true")) {
            dialog.cbUseDefaultBackground.setSelected(true);
            dialog.useDefaultBackgroundImage();
        } else {
            dialog.cbUseDefaultBackground.setSelected(false);
            dialog.useSelectBackgroundImage();
            String path = prefs.get(KEY_BACKGROUND_IMAGE, "");
            dialog.txtBackgroundImagePath.setText(path);
            updateCache(KEY_BACKGROUND_IMAGE, path);
        }
        prop = prefs.get(KEY_BATTLEFIELD_IMAGE_DEFAULT, "true");
        if (prop.equals("true")) {
            dialog.cbUseDefaultBattleImage.setSelected(true);
            dialog.useDefaultBattlefield();
        } else {
            dialog.cbUseDefaultBattleImage.setSelected(false);
            dialog.useSelectedOrRandom();
        }
        prop = prefs.get(KEY_BATTLEFIELD_IMAGE_RANDOM, "true");

        if (dialog.cbUseRandomBattleImage.isEnabled()) {
            if (prop.equals("true")) {
                dialog.cbUseRandomBattleImage.setSelected(true);
                dialog.useRandomBattleField();
            } else {
                dialog.cbUseRandomBattleImage.setSelected(false);
                dialog.useSelectedBattleField();
                String path = prefs.get(KEY_BATTLEFIELD_IMAGE, "");
                dialog.txtBattlefieldImagePath.setText(path);
                updateCache(KEY_BATTLEFIELD_IMAGE, path);
            }
        }
    }

    private static void loadSoundSettings(Preferences prefs) {
        dialog.cbEnableGameSounds.setSelected(prefs.get(KEY_SOUNDS_GAME_ON, "true").equals("true"));
        dialog.cbEnableDraftSounds.setSelected(prefs.get(KEY_SOUNDS_DRAFT_ON, "true").equals("true"));
        dialog.cbEnableSkipButtonsSounds.setSelected(prefs.get(KEY_SOUNDS_SKIP_BUTTONS_ON, "true").equals("true"));
        dialog.cbEnableOtherSounds.setSelected(prefs.get(KEY_SOUNDS_OTHER_ON, "true").equals("true"));

        // Match music
        dialog.cbEnableBattlefieldBGM.setSelected(prefs.get(KEY_SOUNDS_MATCH_MUSIC_ON, "true").equals("true"));
        dialog.txtBattlefieldIBGMPath.setEnabled(dialog.cbEnableBattlefieldBGM.isSelected());
        dialog.btnBattlefieldBGMBrowse.setEnabled(dialog.cbEnableBattlefieldBGM.isSelected());
        // load and save the path always, so you can reactivate music without selecting path again
        String path = prefs.get(KEY_SOUNDS_MATCH_MUSIC_PATH, "");
        dialog.txtBattlefieldIBGMPath.setText(path);

        updateCache(KEY_SOUNDS_MATCH_MUSIC_PATH, path);
    }

    private static void loadProxySettings(Preferences prefs) {
        dialog.cbProxyType.setSelectedItem(Connection.ProxyType.valueOf(MageFrame.getPreferences().get(KEY_PROXY_TYPE, "NONE").toUpperCase()));

        load(prefs, dialog.txtProxyServer, KEY_PROXY_ADDRESS, Config.serverName);
        load(prefs, dialog.txtProxyPort, KEY_PROXY_PORT, Integer.toString(Config.port));
        load(prefs, dialog.txtProxyUserName, KEY_PROXY_USERNAME, "");
        load(prefs, dialog.rememberPswd, KEY_PROXY_REMEMBER, "true", "false");
        if (dialog.rememberPswd.isSelected()) {
            load(prefs, dialog.txtPasswordField, KEY_PROXY_PSWD, "");
        }
        load(prefs, dialog.txtURLServerList, KEY_CONNECTION_URL_SERVER_LIST, "http://XMage.de/files/server-list.txt");
    }

    private static void loadSelectedAvatar(Preferences prefs) {
        getSelectedAvatar();
        dialog.setSelectedId(selectedAvatarId);
    }

    public static int getSelectedAvatar() {
        try {
            selectedAvatarId = Integer.valueOf(MageFrame.getPreferences().get(KEY_AVATAR, String.valueOf(DEFAULT_AVATAR_ID)));
        } catch (NumberFormatException n) {
            selectedAvatarId = DEFAULT_AVATAR_ID;
        } finally {
            if (!available_avatars.contains(selectedAvatarId)) {
                selectedAvatarId = DEFAULT_AVATAR_ID;
            }
        }
        return selectedAvatarId;
    }

    public static UserSkipPrioritySteps getUserSkipPrioritySteps() {
        if (!dialog.isVisible()) {
            loadPhases(MageFrame.getPreferences());
        }
        UserSkipPrioritySteps userSkipPrioritySteps = new UserSkipPrioritySteps();

        userSkipPrioritySteps.getYourTurn().setUpkeep(dialog.checkBoxUpkeepYou.isSelected());
        userSkipPrioritySteps.getYourTurn().setDraw(dialog.checkBoxDrawYou.isSelected());
        userSkipPrioritySteps.getYourTurn().setMain1(dialog.checkBoxMainYou.isSelected());
        userSkipPrioritySteps.getYourTurn().setBeforeCombat(dialog.checkBoxBeforeCYou.isSelected());
        userSkipPrioritySteps.getYourTurn().setEndOfCombat(dialog.checkBoxEndOfCYou.isSelected());
        userSkipPrioritySteps.getYourTurn().setMain2(dialog.checkBoxMain2You.isSelected());
        userSkipPrioritySteps.getYourTurn().setEndOfTurn(dialog.checkBoxEndTurnYou.isSelected());

        userSkipPrioritySteps.getOpponentTurn().setUpkeep(dialog.checkBoxUpkeepOthers.isSelected());
        userSkipPrioritySteps.getOpponentTurn().setDraw(dialog.checkBoxDrawOthers.isSelected());
        userSkipPrioritySteps.getOpponentTurn().setMain1(dialog.checkBoxMainOthers.isSelected());
        userSkipPrioritySteps.getOpponentTurn().setBeforeCombat(dialog.checkBoxBeforeCOthers.isSelected());
        userSkipPrioritySteps.getOpponentTurn().setEndOfCombat(dialog.checkBoxEndOfCOthers.isSelected());
        userSkipPrioritySteps.getOpponentTurn().setMain2(dialog.checkBoxMain2Others.isSelected());
        userSkipPrioritySteps.getOpponentTurn().setEndOfTurn(dialog.checkBoxEndTurnOthers.isSelected());

        userSkipPrioritySteps.setStopOnDeclareAttackersDuringSkipActions(dialog.cbStopAttack.isSelected());
        userSkipPrioritySteps.setStopOnDeclareBlockerIfNoneAvailable(dialog.cbStopBlock.isSelected());
        userSkipPrioritySteps.setStopOnAllEndPhases(dialog.cbStopOnAllEnd.isSelected());
        userSkipPrioritySteps.setStopOnAllMainPhases(dialog.cbStopOnAllMain.isSelected());

        return userSkipPrioritySteps;
    }

    private static void openTab(int index) {
        try {
            if (index > 0) {
                dialog.tabsPanel.setSelectedIndex(index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveImagesPath(Preferences prefs) {
        if (!dialog.cbUseDefaultImageFolder.isSelected()) {
            String path = dialog.txtImageFolderPath.getText();
            prefs.put(KEY_CARD_IMAGES_PATH, path);
            updateCache(KEY_CARD_IMAGES_PATH, path);
        }
        // background path save precedure
        if (!dialog.cbUseDefaultBackground.isSelected()) {
            String path = dialog.txtBackgroundImagePath.getText();
            prefs.put(KEY_BACKGROUND_IMAGE, path);
            updateCache(KEY_BACKGROUND_IMAGE, path);
        }
        if (!dialog.cbUseDefaultBattleImage.isSelected() && !dialog.cbUseRandomBattleImage.isSelected()) {
            String path = dialog.txtBattlefieldImagePath.getText();
            prefs.put(KEY_BATTLEFIELD_IMAGE, path);
            updateCache(KEY_BATTLEFIELD_IMAGE, path);
        }
    }

    private static void saveSoundPath(Preferences prefs) {
        String path = dialog.txtBattlefieldIBGMPath.getText();
        prefs.put(KEY_SOUNDS_MATCH_MUSIC_PATH, path);
        updateCache(KEY_SOUNDS_MATCH_MUSIC_PATH, path);
    }

    public static boolean isSaveImagesToZip() {
        return PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_SAVE_TO_ZIP, "false").equals("true");
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName, String yesValue) {
        String prop = prefs.get(propName, yesValue);
        checkBox.setSelected(prop.equals(yesValue));
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName, String yesValue, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        checkBox.setSelected(prop.equals(yesValue));
    }

    private static void load(Preferences prefs, JTextField field, String propName, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        field.setText(prop);
    }

    private static void load(Preferences prefs, JComboBox field, String propName, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        field.setSelectedItem(prop);
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName) {
        load(prefs, checkBox, propName, PHASE_ON);
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName) {
        save(prefs, checkBox, propName, PHASE_ON, PHASE_OFF, false);
    }

    public static void setPrefValue(String key, boolean value) {
        switch (key) {
            case KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS:
                dialog.cbAllowRequestToShowHandCards.setSelected(value);
                save(MageFrame.getPreferences(), dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true", "false", UPDATE_CACHE_POLICY);
                break;
        }
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName, String yesValue, String noValue, boolean updateCache) {
        prefs.put(propName, checkBox.isSelected() ? yesValue : noValue);
        if (updateCache) {
            updateCache(propName, checkBox.isSelected() ? yesValue : noValue);
        }
    }

    private static void save(Preferences prefs, JTextField textField, String propName) {
        prefs.put(propName, textField.getText().trim());
        updateCache(propName, textField.getText().trim());
    }

    private static void save(Preferences prefs, JComboBox comboBox, String propName) {
        prefs.put(propName, comboBox.getSelectedItem().toString().trim());
        updateCache(propName, comboBox.getSelectedItem().toString().trim());
    }

    public void reset() {
        tabsPanel.setSelectedIndex(0);
    }

    public static String getCachedValue(String key, String def) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            Preferences prefs = MageFrame.getPreferences();
            String value = prefs.get(key, def);
            if (value == null) {
                return null;
            }
            cache.put(key, value);
            return value;
        }
    }

    private static void updateCache(String key, String value) {
        cache.put(key, value);
    }

    public static void saveValue(String key, String value) {
        Preferences prefs = MageFrame.getPreferences();
        prefs.put(key, value);
        try {
            prefs.flush();
        } catch (BackingStoreException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: couldn't save preferences. Please try once again.");
        }
        updateCache(key, value);
    }

    private void addAvatars() {
        try {
            addAvatar(jPanel10, 51, true, false);
            addAvatar(jPanel13, 13, false, false);
            addAvatar(jPanel11, 9, false, false);
            addAvatar(jPanel12, 53, false, false);
            addAvatar(jPanel14, 10, false, false);
            addAvatar(jPanel15, 39, false, false);
            addAvatar(jPanel19, 19, false, false);
            addAvatar(jPanel20, 30, false, false);
            addAvatar(jPanel21, 25, false, false);

            addAvatar(jPanel16, 22, false, false);
            addAvatar(jPanel17, 77, false, false);
            addAvatar(jPanel18, 62, false, false);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    public void setSelectedId(int id) {
        if (available_avatars.contains(id)) {
            for (JPanel panel : panels.values()) {
                panel.setBorder(BLACK_BORDER);
            }
            PreferencesDialog.selectedAvatarId = id;
            panels.get(PreferencesDialog.selectedAvatarId).setBorder(GREEN_BORDER);
        }
    }

    private void addAvatar(JPanel jPanel, final int id, boolean selected, boolean locked) {
        String path = "/avatars/" + String.valueOf(id) + ".jpg";
        panels.put(id, jPanel);
        Image image = ImageHelper.getImageFromResources(path);
        Rectangle r = new Rectangle(90, 90);
        BufferedImage bufferedImage;
        if (!locked) {
            bufferedImage = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        } else {
            bufferedImage = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB, new Color(150, 150, 150, 170));
        }
        BufferedImage resized = ImageHelper.getResizedImage(bufferedImage, r);
        final JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(resized));
        if (selected) {
            jPanel.setBorder(GREEN_BORDER);
        } else {
            jPanel.setBorder(BLACK_BORDER);
        }
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jLabel);
        if (!locked) {
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (selectedAvatarId != id) {
                        setSelectedId(id);
                        MageFrame.getSession().updatePreferencesForServer(getUserData());
                    }
                }
            });
        }
    }

    public static UserData getUserData() {
        return new UserData(UserGroup.PLAYER,
                PreferencesDialog.selectedAvatarId,
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_ABILITY_PICKER_FORCED, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true").equals("true"),
                getUserSkipPrioritySteps(),
                MageFrame.getPreferences().get(KEY_CONNECT_FLAG, "world"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PASS_PRIORITY_CAST, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PASS_PRIORITY_ACTIVATION, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_AUTO_ORDER_TRIGGER, "true").equals("true")
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane avatarPane;
    private javax.swing.JPanel avatarPanel;
    private javax.swing.JButton btnBattlefieldBGMBrowse;
    private javax.swing.JButton btnBrowseBackgroundImage;
    private javax.swing.JButton btnBrowseBattlefieldImage;
    private javax.swing.JButton btnBrowseImageLocation;
    private javax.swing.JCheckBox cbAllowRequestToShowHandCards;
    private javax.swing.JCheckBox cbAskMoveToGraveOrder;
    private javax.swing.JCheckBox cbAutoOrderTrigger;
    private javax.swing.JCheckBox cbCheckForNewImages;
    private javax.swing.JCheckBox cbConfirmEmptyManaPool;
    private javax.swing.JCheckBox cbDraftLogAutoSave;
    private javax.swing.JCheckBox cbEnableBattlefieldBGM;
    private javax.swing.JCheckBox cbEnableDraftSounds;
    private javax.swing.JCheckBox cbEnableGameSounds;
    private javax.swing.JCheckBox cbEnableOtherSounds;
    private javax.swing.JCheckBox cbEnableSkipButtonsSounds;
    private javax.swing.JCheckBox cbGameLogAutoSave;
    private javax.swing.JCheckBox cbPassPriorityActivation;
    private javax.swing.JCheckBox cbPassPriorityCast;
    private javax.swing.JComboBox<String> cbPreferedImageLanguage;
    private javax.swing.JComboBox<ProxyType> cbProxyType;
    private javax.swing.JCheckBox cbSaveToZipFiles;
    private javax.swing.JCheckBox cbShowStormCounter;
    private javax.swing.JCheckBox cbStopAttack;
    private javax.swing.JCheckBox cbStopBlock;
    private javax.swing.JCheckBox cbStopOnAllEnd;
    private javax.swing.JCheckBox cbStopOnAllMain;
    private javax.swing.JCheckBox cbUseDefaultBackground;
    private javax.swing.JCheckBox cbUseDefaultBattleImage;
    private javax.swing.JCheckBox cbUseDefaultImageFolder;
    private javax.swing.JCheckBox cbUseRandomBattleImage;
    private javax.swing.JCheckBox checkBoxBeforeCOthers;
    private javax.swing.JCheckBox checkBoxBeforeCYou;
    private javax.swing.JCheckBox checkBoxDrawOthers;
    private javax.swing.JCheckBox checkBoxDrawYou;
    private javax.swing.JCheckBox checkBoxEndOfCOthers;
    private javax.swing.JCheckBox checkBoxEndOfCYou;
    private javax.swing.JCheckBox checkBoxEndTurnOthers;
    private javax.swing.JCheckBox checkBoxEndTurnYou;
    private javax.swing.JCheckBox checkBoxMain2Others;
    private javax.swing.JCheckBox checkBoxMain2You;
    private javax.swing.JCheckBox checkBoxMainOthers;
    private javax.swing.JCheckBox checkBoxMainYou;
    private javax.swing.JCheckBox checkBoxUpkeepOthers;
    private javax.swing.JCheckBox checkBoxUpkeepYou;
    private javax.swing.JPanel connection_servers;
    private javax.swing.JCheckBox displayBigCardsInHand;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabelBeforeCombat;
    private javax.swing.JLabel jLabelDraw;
    private javax.swing.JLabel jLabelEndOfTurn;
    private javax.swing.JLabel jLabelEndofCombat;
    private javax.swing.JLabel jLabelHeadLine;
    private javax.swing.JLabel jLabelMain1;
    private javax.swing.JLabel jLabelMain2;
    private javax.swing.JLabel jLabelOpponentsTurn;
    private javax.swing.JLabel jLabelUpkeep;
    private javax.swing.JLabel jLabelYourTurn;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JLabel labelPreferedImageLanguage;
    private javax.swing.JLabel lblProxyPassword;
    private javax.swing.JLabel lblProxyPort;
    private javax.swing.JLabel lblProxyServer;
    private javax.swing.JLabel lblProxyType;
    private javax.swing.JLabel lblProxyUserName;
    private javax.swing.JLabel lblURLServerList;
    private javax.swing.JPanel main_card;
    private javax.swing.JPanel main_game;
    private javax.swing.JPanel main_gamelog;
    private javax.swing.JCheckBox nonLandPermanentsInOnePile;
    private javax.swing.JPanel panelBackgroundImages;
    private javax.swing.JPanel panelCardImages;
    private javax.swing.JPanel phases_stopSettings;
    private javax.swing.JPanel pnlProxy;
    private javax.swing.JPanel pnlProxySettings;
    private javax.swing.JCheckBox rememberPswd;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox showAbilityPickerForced;
    private javax.swing.JCheckBox showCardName;
    private javax.swing.JCheckBox showPlayerNamesPermanently;
    private javax.swing.JCheckBox showToolTipsInAnyZone;
    private javax.swing.JPanel sounds_backgroundMusic;
    private javax.swing.JPanel sounds_clips;
    private javax.swing.JPanel tabAvatars;
    private javax.swing.JPanel tabConnection;
    private javax.swing.JPanel tabImages;
    private javax.swing.JPanel tabMain;
    private javax.swing.JPanel tabPhases;
    private javax.swing.JPanel tabSounds;
    private javax.swing.JTabbedPane tabsPanel;
    private javax.swing.JTextField txtBackgroundImagePath;
    private javax.swing.JTextField txtBattlefieldIBGMPath;
    private javax.swing.JTextField txtBattlefieldImagePath;
    private javax.swing.JTextField txtImageFolderPath;
    private javax.swing.JPasswordField txtPasswordField;
    private javax.swing.JTextField txtProxyPort;
    private javax.swing.JTextField txtProxyServer;
    private javax.swing.JTextField txtProxyUserName;
    private javax.swing.JTextField txtURLServerList;
    // End of variables declaration//GEN-END:variables

    private static final PreferencesDialog dialog = new PreferencesDialog(new javax.swing.JFrame(), true);

    static {
        dialog.setResizable(false);
    }
}
