package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.components.KeyBindButton;
import mage.client.themes.ThemeType;
import mage.client.util.*;
import mage.client.util.audio.MusicPlayer;
import mage.client.util.gui.BufferedImageBuilder;
import mage.client.util.gui.GuiDisplayUtil;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.players.net.UserSkipPrioritySteps;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static mage.client.constants.Constants.AUTO_TARGET_NON_FEEL_BAD;
import static mage.constants.Constants.*;

/**
 * GUI: preferences dialog with all user settings
 *
 * @author nantuko, JayDi85, leemi
 */
public class PreferencesDialog extends javax.swing.JDialog {

    // TODO: fix card hand size (hand panel can't show full card on too big values - must use auto-height)

    private static final Logger logger = Logger.getLogger(PreferencesDialog.class);

    private static PreferencesDialog instance; // shared dialog instance

    public static final boolean NETWORK_ENABLE_PROXY_SUPPORT = false; // TODO: delete proxy at all after few releases, 2025-02-09

    // WARNING, do not change const values - it must be same for compatibility with user's saved settings
    public static final String KEY_SHOW_TOOLTIPS_DELAY = "showTooltipsDelay";
    public static final String KEY_SHOW_CARD_NAMES = "showCardNames";
    public static final String KEY_SHOW_FULL_IMAGE_PATH = "showFullImagePath";
    public static final String KEY_PERMANENTS_IN_ONE_PILE = "nonLandPermanentsInOnePile";
    public static final String KEY_SHOW_PLAYER_NAMES_PERMANENTLY = "showPlayerNamesPermanently";
    public static final String KEY_DISPLAY_LIVE_ON_AVATAR = "displayLiveOnAvatar";
    public static final String KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS = "gameAllowRequestShowHandCards";
    public static final String KEY_GAME_CONFIRM_EMPTY_MANA_POOL = "gameConfirmEmptyManaPool";
    public static final String KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER = "gameAskMoveToGraveORder";
    public static final String KEY_GAME_USE_PROFANITY_FILTER = "gameUseProfanityFilter";

    // size settings
    public static final String KEY_GUI_CARD_BATTLEFIELD_SIZE = "guiCardBattlefieldSize";
    public static final String KEY_GUI_CARD_HAND_SIZE = "guiCardHandSize";
    public static final String KEY_GUI_CARD_EDITOR_SIZE = "guiCardEditorSize";
    public static final String KEY_GUI_CARD_OTHER_ZONES_SIZE = "guiCardOtherZonesSize";
    public static final String KEY_GUI_DIALOG_FONT_SIZE = "guiDialogFontSize";
    public static final String KEY_GUI_CHAT_FONT_SIZE = "guiChatFontSize";
    public static final String KEY_GUI_PLAYER_PANEL_SIZE = "guiPlayerPanelSize";
    public static final String KEY_GUI_TOOLTIP_SIZE = "guiTooltipSize";

    public static final String KEY_GAME_LOG_AUTO_SAVE = "gameLogAutoSave";
    public static final String KEY_DRAFT_LOG_AUTO_SAVE = "draftLogAutoSave";
    public static final String KEY_LIMITED_DECK_AUTO_SAVE = "draftLimitedAutoSave";
    public static final String KEY_JSON_GAME_LOG_AUTO_SAVE = "gameLogJsonAutoSave";

    public static final String KEY_CARD_IMAGES_USE_DEFAULT = "cardImagesUseDefault";
    public static final String KEY_CARD_IMAGES_PATH = "cardImagesPath";
    public static final String KEY_CARD_IMAGES_SAVE_TO_ZIP = "cardImagesSaveToZip";
    public static final String KEY_CARD_IMAGES_PREF_LANGUAGE = "cardImagesPreferredImageLaguage";

    public static final String KEY_CARD_RENDERING_IMAGE_MODE = "cardRenderingMode";
    public static final String KEY_CARD_RENDERING_ICONS_FOR_ABILITIES = "cardRenderingIconsForAbilities";
    public static final String KEY_CARD_RENDERING_ICONS_FOR_PLAYABLE = "cardRenderingIconsForPlayable";
    public static final String KEY_CARD_RENDERING_REMINDER_TEXT = "cardRenderingReminderText";
    public static final String KEY_CARD_RENDERING_ABILITY_TEXT_OVERLAY = "cardRenderingAbilityTextOverlay";
    public static final String KEY_CARD_RENDERING_SET_SYMBOL = "cardRenderingSetSymbol";

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
    public static final String SOUNDS_MATCH_MUSIC_ENABLE_BY_DEFAULT = "true";
    public static final String KEY_SOUNDS_MATCH_MUSIC_PATH = "soundsMatchMusicPath";

    // Themes
    public static final String KEY_THEME = "themeSelection";

    // Phases
    public static final String UPKEEP_YOU = "upkeepYou";
    public static final String DRAW_YOU = "drawYou";
    public static final String MAIN_YOU = "mainYou";
    public static final String BEFORE_COMBAT_YOU = "beforeCombatYou";
    public static final String END_OF_COMBAT_YOU = "endOfCombatYou";
    public static final String MAIN_TWO_YOU = "main2You";
    public static final String END_OF_TURN_YOU = "endOfTurnYou";

    public static final String UPKEEP_OTHERS = "upkeepOthers";
    public static final String DRAW_OTHERS = "drawOthers";
    public static final String MAIN_OTHERS = "mainOthers";
    public static final String BEFORE_COMBAT_OTHERS = "beforeCombatOthers";
    public static final String END_OF_COMBAT_OTHERS = "endOfCombatOthers";
    public static final String MAIN_TWO_OTHERS = "main2Others";
    public static final String END_OF_TURN_OTHERS = "endOfTurnOthers";

    public static final String KEY_STOP_ATTACK = "stopDeclareAttacksStep";
    public static final String KEY_STOP_BLOCK_WITH_ANY = "stopDeclareBlockersStepWithAny";
    public static final String KEY_STOP_BLOCK_WITH_ZERO = "stopDeclareBlockersStepWithZero";
    public static final String KEY_STOP_ALL_MAIN_PHASES = "stopOnAllMainPhases";
    public static final String KEY_STOP_ALL_END_PHASES = "stopOnAllEndPhases";
    public static final String KEY_STOP_NEW_STACK_OBJECTS = "stopOnNewStackObjects";
    public static final String KEY_PASS_PRIORITY_CAST = "passPriorityCast";
    public static final String KEY_PASS_PRIORITY_ACTIVATION = "passPriorityActivation";
    public static final String KEY_AUTO_ORDER_TRIGGER = "autoOrderTrigger";
    public static final String KEY_USE_SAME_SETTINGS_FOR_SAME_REPLACEMENT_EFFECTS = "useSameSettingsForSameReplacementEffects";
    public static final String KEY_USE_FIRST_MANA_ABILITY = "useFirstManaAbility";

    // mana auto payment
    public static final String KEY_GAME_MANA_AUTOPAYMENT = "gameManaAutopayment";
    public static final String KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE = "gameManaAutopaymentOnlyOne";

    // Size of frame to check if divider locations should be used
    public static final String KEY_MAGE_PANEL_LAST_SIZE = "gamepanelLastSize"; // TODO: remove?

    // pref settings of table settings and filtering
    public static final String KEY_TABLES_FILTER_SETTINGS = "tablePanelFilterSettings";
    public static final String KEY_TABLES_COLUMNS_WIDTH = "tablePanelColumnWidth";
    public static final String KEY_TABLES_COLUMNS_ORDER = "tablePanelColumnSort";

    // last sort settings used in deck editor
    public static final String KEY_DECK_EDITOR_LAST_SORT = "deckEditorLastSort";
    public static final String KEY_DECK_EDITOR_LAST_SEPARATE_CREATURES = "deckEditorLastSeparateCreatures";

    public static final String KEY_DECK_EDITOR_SEARCH_NAMES = "deckEditorSearchNames";
    public static final String KEY_DECK_EDITOR_SEARCH_TYPES = "deckEditorSearchTypes";
    public static final String KEY_DECK_EDITOR_SEARCH_RULES = "deckEditorSearchRules";
    public static final String KEY_DECK_EDITOR_SEARCH_UNIQUE = "deckEditorSearchUnique";

    // positions of divider bars
    public static final String KEY_TABLES_DIVIDER_LOCATION_1 = "tablePanelDividerLocation1";
    public static final String KEY_TABLES_DIVIDER_LOCATION_2 = "tablePanelDividerLocation2";
    public static final String KEY_TABLES_DIVIDER_LOCATION_3 = "tablePanelDividerLocation3";
    public static final String KEY_TABLES_DIVIDER_LOCATION_4 = "tablePanelDividerLocation4";

    // Positions of deck editor divider bars
    public static final String KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION_NORMAL = "editorHorizontalDividerLocationNormal";
    public static final String KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION_LIMITED = "editorHorizontalDividerLocationLimited";
    public static final String KEY_EDITOR_DECKAREA_SETTINGS = "editorDeckAreaSettings";

    // user list
    public static final String KEY_USERS_COLUMNS_WIDTH = "userPanelColumnWidth";
    public static final String KEY_USERS_COLUMNS_ORDER = "userPanelColumnSort";
    // table waiting dialog
    public static final String KEY_TABLE_WAITING_WIDTH = "tableWaitingPanelWidth";
    public static final String KEY_TABLE_WAITING_HEIGHT = "tableWaitingPanelHeight";
    public static final String KEY_TABLE_WAITING_COLUMNS_WIDTH = "tableWaitingPanelColumnWidth";
    public static final String KEY_TABLE_WAITING_COLUMNS_ORDER = "tableWaitingPanelColumnSort";

    public static final String KEY_GAMEPANEL_DIVIDER_LOCATIONS_GAME_AND_BIG_CARD = "gamepanelDividerLocationsGameAndBigCard";
    public static final String KEY_GAMEPANEL_DIVIDER_LOCATIONS_BATTLEFIELD_AND_CHATS = "gamepanelDividerLocationsBattlefieldAndChats";
    public static final String KEY_GAMEPANEL_DIVIDER_LOCATIONS_HAND_STACK = "gamepanelDividerLocationsHandStack";
    public static final String KEY_GAMEPANEL_DIVIDER_LOCATIONS_CHAT_AND_LOGS = "gamepanelDividerLocationsChatAndLogs";

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
    public static final String KEY_NEW_TABLE_BUFFER_TIME = "newTableBufferTime";
    public static final String KEY_NEW_TABLE_GAME_TYPE = "newTableGameType";
    public static final String KEY_NEW_TABLE_NUMBER_OF_WINS = "newTableNumberOfWins";
    public static final String KEY_NEW_TABLE_ROLLBACK_TURNS_ALLOWED = "newTableRollbackTurnsAllowed";
    public static final String KEY_NEW_TABLE_SPECTATORS_ALLOWED = "newTableSpectatorsAllowed";
    public static final String KEY_NEW_TABLE_PLANECHASE = "newTablePlaneChase";
    public static final String KEY_NEW_TABLE_NUMBER_OF_FREE_MULLIGANS = "newTableNumberOfFreeMulligans";
    public static final String KEY_NEW_TABLE_MULLIGAN_TYPE = "newTableMulliganType";
    public static final String KEY_NEW_TABLE_CUSTOM_STARTING_LIFE = "newTableUseCustomLife";
    public static final String KEY_NEW_TABLE_NUMBER_OF_LIFE_AT_START = "newTableNumberOfLifeAtStart";
    public static final String KEY_NEW_TABLE_CUSTOM_STARTING_HAND_SIZE = "newTableUseCustomHandSize";
    public static final String KEY_NEW_TABLE_NUMBER_OF_HAND_SIZE_AT_START = "newTableNumberOfHandSizeAtStart";
    public static final String KEY_NEW_TABLE_DECK_FILE = "newTableDeckFile";
    public static final String KEY_NEW_TABLE_RANGE = "newTableRange";
    public static final String KEY_NEW_TABLE_ATTACK_OPTION = "newTableAttackOption";
    public static final String KEY_NEW_TABLE_SKILL_LEVEL = "newTableSkillLevel";
    public static final String KEY_NEW_TABLE_NUMBER_PLAYERS = "newTableNumberPlayers";
    public static final String KEY_NEW_TABLE_PLAYER_TYPES = "newTablePlayerTypes";
    public static final String KEY_NEW_TABLE_PLAYER_SKILLS = "newTablePlayerSkills";
    public static final String KEY_NEW_TABLE_PLAYER_DECKS = "newTablePlayerDecks";
    public static final String KEY_NEW_TABLE_QUIT_RATIO = "newTableQuitRatio";
    public static final String KEY_NEW_TABLE_MINIMUM_RATING = "newTableMinimumRating";
    public static final String KEY_NEW_TABLE_RATED = "newTableRated";
    public static final String KEY_NEW_TABLE_EDH_POWER_LEVEL = "newTableEdhPowerLevel";
    public static final String KEY_NEW_TABLE_EMBLEM_CARDS_ENABLED = "newTableEmblemCardsEnabled";
    public static final String KEY_NEW_TABLE_EMBLEM_CARDS_PER_PLAYER_FILE = "newTableEmblemCardsPerPlayerFile";
    public static final String KEY_NEW_TABLE_EMBLEM_CARDS_STARTING_PLAYER_FILE = "newTableEmblemCardsStartingPlayerFile";

    // pref setting for new tournament dialog
    public static final String KEY_NEW_TOURNAMENT_NAME = "newTournamentName";
    public static final String KEY_NEW_TOURNAMENT_PASSWORD = "newTournamentPassword";
    public static final String KEY_NEW_TOURNAMENT_TIME_LIMIT = "newTournamentTimeLimit";
    public static final String KEY_NEW_TOURNAMENT_BUFFER_TIME = "newTournamentBufferTime";
    public static final String KEY_NEW_TOURNAMENT_CONSTR_TIME = "newTournamentConstructionTime";
    public static final String KEY_NEW_TOURNAMENT_SKILL_LEVEL = "newTournamentSkillLevel";
    public static final String KEY_NEW_TOURNAMENT_TYPE = "newTournamentType";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS = "newTournamentNumberOfFreeMulligans";
    public static final String KEY_NEW_TOURNAMENT_MULLIGUN_TYPE = "newTournamentMulliganType";
    public static final String KEY_NEW_TOURNAMENT_CUSTOM_STARTING_LIFE = "newTournamentUseCustomLife";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_LIFE_AT_START = "newTournamentNumberOfLifeAtStart";
    public static final String KEY_NEW_TOURNAMENT_CUSTOM_STARTING_HAND_SIZE = "newTournamentUseCustomHandSize";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_HAND_SIZE_AT_START = "newTournamentNumberOfHandSizeAtStart";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_WINS = "newTournamentNumberOfWins";
    public static final String KEY_NEW_TOURNAMENT_PACKS_SEALED = "newTournamentPacksSealed";
    public static final String KEY_NEW_TOURNAMENT_PACKS_DRAFT = "newTournamentPacksDraft";
    public static final String KEY_NEW_TOURNAMENT_PACKS_RANDOM_DRAFT = "newTournamentPacksRandomDraft";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_PLAYERS = "newTournamentNumberPlayers";
    public static final String KEY_NEW_TOURNAMENT_SINGLE_MULTIPLAYER_GAME = "newTournamentSingleMultiplayerGame";
    public static final String KEY_NEW_TOURNAMENT_PLAYER_TYPES = "newTournamentPlayerTypes";
    public static final String KEY_NEW_TOURNAMENT_PLAYER_SKILLS = "newTournamentPlayerSkills";
    public static final String KEY_NEW_TOURNAMENT_DRAFT_TIMING = "newTournamentDraftTiming";
    public static final String KEY_NEW_TOURNAMENT_ALLOW_SPECTATORS = "newTournamentAllowSpectators";
    public static final String KEY_NEW_TOURNAMENT_PLANE_CHASE = "newTournamentPlaneChase";
    public static final String KEY_NEW_TOURNAMENT_ALLOW_ROLLBACKS = "newTournamentAllowRollbacks";
    public static final String KEY_NEW_TOURNAMENT_DECK_FILE = "newTournamentDeckFile";
    public static final String KEY_NEW_TOURNAMENT_QUIT_RATIO = "newTournamentQuitRatio";
    public static final String KEY_NEW_TOURNAMENT_MINIMUM_RATING = "newTournamentMinimumRating";
    public static final String KEY_NEW_TOURNAMENT_RATED = "newTournamentRated";
    public static final String KEY_NEW_TOURNAMENT_EMBLEM_CARDS_ENABLED = "newTournamentEmblemCardsEnabled";
    public static final String KEY_NEW_TOURNAMENT_EMBLEM_CARDS_PER_PLAYER_FILE = "newTournamentEmblemCardsPerPlayerFile";
    public static final String KEY_NEW_TOURNAMENT_EMBLEM_CARDS_STARTING_PLAYER_FILE = "newTournamentEmblemCardsStartingPlayerFile";

    // Settings for auto-choosing targets
    public static final String KEY_AUTO_TARGET_LEVEL = "autoTargetLevel";

    // pref setting for deck generator
    public static final String KEY_NEW_DECK_GENERATOR_COLORS = "newDeckGeneratorDeckColors";
    public static final String KEY_NEW_DECK_GENERATOR_DECK_SIZE = "newDeckGeneratorDeckSize";
    public static final String KEY_NEW_DECK_GENERATOR_SET = "newDeckGeneratorSet";
    public static final String KEY_NEW_DECK_GENERATOR_SINGLETON = "newDeckGeneratorSingleton";
    public static final String KEY_NEW_DECK_GENERATOR_ARTIFACTS = "newDeckGeneratorArtifacts";
    public static final String KEY_NEW_DECK_GENERATOR_NON_BASIC_LANDS = "newDeckGeneratorNonBasicLands";
    public static final String KEY_NEW_DECK_GENERATOR_COLORLESS = "newDeckGeneratorColorless";
    public static final String KEY_NEW_DECK_GENERATOR_COMMANDER = "newDeckGeneratorCommander";
    public static final String KEY_NEW_DECK_GENERATOR_ADVANCED = "newDeckGeneratorAdvanced";
    public static final String KEY_NEW_DECK_GENERATOR_CREATURE_PERCENTAGE = "newDeckGeneratorCreaturePercentage";
    public static final String KEY_NEW_DECK_GENERATOR_NON_CREATURE_PERCENTAGE = "newDeckGeneratorNonCreaturePercentage";
    public static final String KEY_NEW_DECK_GENERATOR_LAND_PERCENTAGE = "newDeckGeneratorLandPercentage";
    public static final String KEY_NEW_DECK_GENERATOR_ADVANCED_CMC = "newDeckGeneratorAdvancedCMC";

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

    // news
    public static final String KEY_NEWS_PAGE_LAST_VERSION = "newsPageLastVersion";
    public static final String KEY_NEWS_PAGE_COOKIES = "newsPageCookiesV2";

    // controls
    public static final String KEY_CONTROL_MODIFIER_POSTFIX = "_modifier";
    public static final String KEY_CONTROL_TOGGLE_MACRO = "controlToggleMacro";
    public static final String KEY_CONTROL_SWITCH_CHAT = "controlSwitchChat";
    public static final String KEY_CONTROL_CONFIRM = "controlConfirm";
    public static final String KEY_CONTROL_CANCEL_SKIP = "controlCancelSkip";
    public static final String KEY_CONTROL_NEXT_TURN = "controlNextTurn";
    public static final String KEY_CONTROL_END_STEP = "controlEndStep";
    public static final String KEY_CONTROL_SKIP_STEP = "controlSkipTurn";
    public static final String KEY_CONTROL_MAIN_STEP = "controlMainStep";
    public static final String KEY_CONTROL_YOUR_TURN = "controlYourTurn";
    public static final String KEY_CONTROL_SKIP_STACK = "controlSkipStack";
    public static final String KEY_CONTROL_PRIOR_END = "controlPriorEnd";

    public static final String KEY_AVATAR = "selectedId";

    public static final String KEY_CONNECT_AUTO_CONNECT = "autoConnect";
    public static final String KEY_CONNECT_FLAG = "connectFlag";

    // auto-update settings on first run
    public static final String KEY_SETTINGS_VERSION = "settingsVersion";

    private static final ReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private static final Map<String, String> CACHE = new HashMap<>();

    public static final String OPEN_CONNECTION_TAB = "Open-Connection-Tab";
    public static final String OPEN_PHASES_TAB = "Open-Phases-Tab";

    public static final String PHASE_ON = "on";
    public static final String PHASE_OFF = "off";

    private static final Map<Integer, JPanel> PANELS = new HashMap<>();

    private static final Border GREEN_BORDER = BorderFactory.createLineBorder(Color.GREEN, 3);
    private static final Border BLACK_BORDER = BorderFactory.createLineBorder(Color.BLACK, 3);

    private static int selectedAvatarId;

    private static ThemeType currentTheme = null;

    // prevent fast settings apply on form loading
    private static boolean isLoadingSizes = false;
    private static boolean isLoadingTheme = false;

    // GUI default size settings
    private static final DefaultSizeSettings defaultSizeSettings = new DefaultSizeSettings();

    public static class DefaultSizeSettings {

        private final List<String> settingKeys = new ArrayList<>();
        private final Map<String, List<Integer>> presetValues = new LinkedHashMap<>();
        private final Map<String, Integer> presetMinHeights = new LinkedHashMap<>(); // preset name, minimum screen height

        public DefaultSizeSettings() {
            // prepare default size settings
            // warning, make sure it use same order as createSizeSetting below
            settingKeys.add(KEY_GUI_DIALOG_FONT_SIZE);
            settingKeys.add(KEY_GUI_CHAT_FONT_SIZE);
            settingKeys.add(KEY_GUI_CARD_EDITOR_SIZE);
            settingKeys.add(KEY_GUI_TOOLTIP_SIZE);
            //
            settingKeys.add(KEY_GUI_PLAYER_PANEL_SIZE);
            settingKeys.add(KEY_GUI_CARD_BATTLEFIELD_SIZE);
            settingKeys.add(KEY_GUI_CARD_HAND_SIZE);
            settingKeys.add(KEY_GUI_CARD_OTHER_ZONES_SIZE);

            // x6 groups allowed here
            // minimum system requirements: screen height > 750
            // lower settings possible, but it's hard to use due low text and image quality
            presetMinHeights.put("1366 x 768", 768);
            presetValues.put("1366 x 768", Arrays.asList(
                    10, 15, 17, 15,
                    10, 22, 14, 13
            ));
            presetMinHeights.put("1920 x 1080", 1080);
            presetValues.put("1920 x 1080", Arrays.asList(
                    17, 18, 23, 20,
                    14, 30, 22, 21
            ));
            presetMinHeights.put("2560 x 1440", 1440);
            presetValues.put("2560 x 1440", Arrays.asList(
                    23, 25, 35, 31,
                    18, 42, 31, 28
            ));
            presetMinHeights.put("3840 x 2160", 2160);
            presetValues.put("3840 x 2160", Arrays.asList(
                    34, 37, 50, 55,
                    27, 64, 50, 44
            ));
        }

        public List<String> getAllPresets() {
            return new ArrayList<>(presetValues.keySet());
        }

        public List<Integer> getPresetValues(String presetName) {
            List<Integer> res = presetValues.getOrDefault(presetName, null);
            if (res == null) {
                throw new IllegalArgumentException("Wrong code usage: unknown size settings preset name " + presetName);
            }
            return res;
        }

        public String findBestPreset() {
            int screenDPI = Toolkit.getDefaultToolkit().getScreenResolution();
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            return findBestPreset(screenDPI, screenHeight);
        }

        public String findBestPreset(int screenDPI, int screenHeight) {
            // Possible use cases with DPI:
            //
            // 1. Normal mode, user setup DPI settings by OS (example: 120%):
            // * DPI - bigger, resolution - lower
            // * GUI components: safe to use screen coordinates and sizes
            // * GUI graphics: must use better images quality to fix pixelated drawing on big DPI values
            // - bug: pixelated images and text (possible fix: use smooth rendering hints on final stage with card image)
            //
            // 2. Java 9+ gui scale mode by -Dsun.java2d.uiScale=2
            // * TODO: test with java 9 scale command line params https://github.com/magefree/mage/issues/969#issuecomment-671055642
            //     looks like it works for linux systems only (can't reproduce it with Windows)
            //
            // 3. Windows compatibility mode with Override high DPI scaling behavior to System, see here https://github.com/magefree/mage/issues/969#issuecomment-2016809163
            // * work with increased DPI settings or HiDPI monitors (?)
            // * DPI - same (native?), resolution - lower
            // * GUI components: safe to use screen coordinates and sizes
            // * GUI graphics: safe to use default images - Windows uses smooth scaling for it, so no pixelated drawing
            // - bug: smoothed images and text (nothing to fix?)

            // find min preset (for too small screens)
            String minPossiblePreset = null;
            int minPossibleRes = Integer.MAX_VALUE;
            for (String preset : presetMinHeights.keySet()) {
                int res = presetMinHeights.get(preset);
                if (res < minPossibleRes) {
                    minPossibleRes = res;
                    minPossiblePreset = preset;
                }
            }
            if (minPossiblePreset == null) {
                throw new IllegalArgumentException("must found min preset all the time");
            }

            // find max preset
            String maxPossiblePreset = null;
            int maxPossibleRes = Integer.MIN_VALUE;
            for (String preset : presetMinHeights.keySet()) {
                int res = presetMinHeights.get(preset);
                if (res <= screenHeight && res > maxPossibleRes) {
                    maxPossibleRes = res;
                    maxPossiblePreset = preset;
                }
            }

            return maxPossiblePreset != null ? maxPossiblePreset : minPossiblePreset;
        }

        public String getSettingsKeyByIndex(int index) {
            return settingKeys.get(index);
        }

        public void applyPreset(String presetName) {
            // WARNING, it's apply settings directly to storage and cache, so opened preferences dialog will be outdated
            // so usage example: app's starting routine
            List<Integer> values = getPresetValues(presetName);
            for (int i = 0; i < values.size(); i++) {
                String settingsKey = getSettingsKeyByIndex(i);
                int settingsValue = values.get(i);
                saveValue(settingsKey, String.valueOf(settingsValue));
            }
        }
    }

    // GUI size settings
    private final Map<String, SizeSetting> sizeSettings = new LinkedHashMap<>();

    static class SizeSetting {
        Integer position;
        JSlider slider;
        JTextField editor;
        JLabel label;
        JButton sample;
        String key;
        Integer defaultValue;
        String originalName;
        String originalHint;
        boolean useExample;

        public SizeSetting(Integer position, JSlider slider, JTextField editor, JLabel label, JButton sample, String key, Integer defaultValue) {
            if (slider == null || editor == null || label == null) {
                // how-to fix: must use components with xxx1, xxx12 naming - see createSizeSetting
                throw new IllegalArgumentException("Wrong code usage: can't find size settings component in Preferences - make sure it uses good naming");
            }

            this.position = position;
            this.slider = slider;
            this.editor = editor;
            this.label = label;
            this.sample = sample;
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public void init(String name, String hint, boolean useExample) {
            this.originalName = name;
            this.originalHint = hint;
            this.useExample = useExample;
            updateLabelAndSample();

            this.editor.setText("99");

            // TODO: sample text is useless for UX, it's better to have popup screenshot with selected element
            this.sample.setText("Move mouse to view sample");

            // repeat all IDE settings, so no need to change in each instance by IDE
            // only component size must be changed by IDE
            this.slider.setMajorTickSpacing(15);
            this.slider.setMaximum(99);
            this.slider.setMinimum(7);
            this.slider.setMinorTickSpacing(5);
            this.slider.setPaintLabels(false);
            this.slider.setPaintTicks(true);
            this.slider.setSnapToTicks(false);
        }

        private void updateLabelAndSample() {
            String text = this.originalName;
            if (this.useExample) {
                String sampleText = String.format("<span style=\"font-size: %dpt;\">Sample Text</span>",
                        this.slider.getValue()
                );
                this.sample.setToolTipText("<html>" + sampleText);
                this.sample.setVisible(true);
            } else {
                this.sample.setVisible(false);
            }
            this.label.setText("<html>" + text);
            this.label.setToolTipText("<html>" + this.originalHint);
        }

        public void enableSyncValues() {
            // slider is main source of the value

            // from slider to editor
            this.slider.addChangeListener(e -> {
                syncFromSliderToEditor();
                updateLabelAndSample(); // sync font examples

                if (!isLoadingSizes && !this.slider.getValueIsAdjusting()) {
                    // first loading - ignore
                    // mouse drag - allow, e.g. update GUI immediately (user will see all fonts changes in real time)
                    // TODO: mouse drag update disabled due slow performance - need GUI rendering research and optimize before enable
                    saveGUISize(true, false); // do not refresh theme cause it heavy and stop active slider move on dragging
                }
            });

            // from editor to slider
            this.editor.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    syncFromEditorToSlider();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    syncFromEditorToSlider();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    syncFromEditorToSlider();
                }
            });
        }

        private void syncFromSliderToEditor() {
            // slider is primary source so sync immediately
            this.editor.setText(String.valueOf(this.slider.getValue()));
        }

        private void syncFromEditorToSlider() {
            if (isLoadingSizes) {
                return;
            }
            SwingUtilities.invokeLater(() -> {
                int currentValue = slider.getValue();
                int needValue;
                try {
                    needValue = Integer.parseInt(editor.getText());
                } catch (NumberFormatException e) {
                    needValue = -1;
                }
                if (currentValue != needValue && needValue >= 10 && needValue <= 99) {
                    slider.setValue(needValue);
                }
            });
        }
    }

    public static PreferencesDialog getInstance() {
        if (instance == null) {
            instance = new PreferencesDialog(new javax.swing.JFrame(), true);
        }
        return instance;
    }

    public static ThemeType getCurrentTheme() {
        if (currentTheme == null) {
            // first init
            loadTheme();
        }

        return currentTheme;
    }

    private static void loadTheme() {
        currentTheme = ThemeType.valueByName(getCachedValue(KEY_THEME, "Default"));
        logger.info("Using GUI theme: " + currentTheme.getName());
        currentTheme.reload();
    }

    /**
     * Set and reload current theme. App need restart to apply all new settings.
     *
     * @param newTheme
     */
    public static void setCurrentTheme(ThemeType newTheme) {
        boolean needReload = currentTheme != newTheme;
        currentTheme = newTheme;
        if (needReload) {
            currentTheme.reload();
        }
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

    public static CardLanguage getPrefImagesLanguage() {
        return CardLanguage.valueByCode(getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_PREF_LANGUAGE, CardLanguage.ENGLISH.getCode()));
    }

    private static class ImageFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            String filename = f.getName();
            if (f.isDirectory()) {
                return true;
            }
            if (filename != null) {
                return filename.endsWith(".jpg") || filename.endsWith(".jpeg")
                        || filename.endsWith(".png") || filename.endsWith(".bmp");
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "*.png | *.bmp |*.jpg | *.jpeg";
        }
    }

    public PreferencesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setResizable(false);

        txtImageFolderPath.setEditable(false);
        cbProxyType.setModel(new DefaultComboBoxModel<>(Connection.ProxyType.values()));
        cbTheme.setModel(new DefaultComboBoxModel<>(ThemeType.values()));
        addAvatars();

        // prepare size table (you can change settings order by new position index)
        // WARNING, if you change default values then make sure calculateGUISizes uses same
        // WARNING, make sure DefaultSizeSettings uses same settings keys
        // App's elements (from position 1)
        createSizeSetting(1, KEY_GUI_DIALOG_FONT_SIZE, 14, false, "Font in dialogs and menu", "The size of the font of messages, menu, dialogs and other windows");
        createSizeSetting(2, KEY_GUI_CHAT_FONT_SIZE, 14, false, "Font in logs and chats", "The size of the font used to display the chat text");
        createSizeSetting(3, KEY_GUI_CARD_EDITOR_SIZE, 14, false, "Size of cards in editor and draft panels", "The size of the card in editor and the picked zone of the draft panel ");
        createSizeSetting(4, KEY_GUI_TOOLTIP_SIZE, 17, false, "Size of popup card hint", "The size of the tooltip window for cards or permanents (use mouse wheel to switch text/image mode)");
        // Game's elements (from position 8)
        createSizeSetting(8, KEY_GUI_PLAYER_PANEL_SIZE, 14, false, "Size of player panel", "The size of the player panels on battlefield");
        createSizeSetting(9, KEY_GUI_CARD_BATTLEFIELD_SIZE, 14, false, "Size of permanents in battlefield", "Average permanents size on battlefield (app will auto-size it depends on free space");
        createSizeSetting(10, KEY_GUI_CARD_HAND_SIZE, 14, false, "Size of cards in hand and stack", "The size of the card images in hand and on the stack");
        createSizeSetting(11, KEY_GUI_CARD_OTHER_ZONES_SIZE, 14, false, "Size of cards in other zones", "The size of card in other game zone (e.g. graveyard, revealed cards etc.)");

        // protection from wrong keys amount
        if (sizeSettings.size() != defaultSizeSettings.presetValues.values().stream().findFirst().get().size()) {
            throw new IllegalArgumentException("Wrong code usage: size and default size settings must contains same records");
        } else {
            // protection from wrong keys order
            List<String> keys = new ArrayList<>(sizeSettings.keySet());
            for (int i = 0; i < keys.size(); i++) {
                if (!defaultSizeSettings.getSettingsKeyByIndex(i).equals(keys.get(i))) {
                    throw new IllegalArgumentException("Wrong code usage: size and default size settings must use same ordered keys");
                }
            }
        }

        // hide unused controls
        hideUnusedSizeSettings();

        // prepare default size settings
        // set new settings on button clicks
        int position = 0;
        String recommendedPreset = defaultSizeSettings.findBestPreset();
        for (String presetName : defaultSizeSettings.getAllPresets()) {
            position++;
            JButton button = GUISizeHelper.getComponentByFieldName(this, "buttonSizeDefault" + position);
            String buttonName = presetName;
            if (presetName.equals(recommendedPreset)) {
                buttonName += " (recommended)";
            }
            button.setText(buttonName);
            button.addActionListener(e -> {
                isLoadingSizes = true;
                try {
                    List<Integer> values = defaultSizeSettings.getPresetValues(presetName);
                    for (int i = 0; i < values.size(); i++) {
                        sizeSettings.get(defaultSizeSettings.getSettingsKeyByIndex(i)).slider.setValue(values.get(i));
                    }
                } finally {
                    isLoadingSizes = false;
                }
                saveGUISize(true, false);
            });
        }

        // hide unused buttons
        for (int i = panelSizeDefaultSettings.getComponentCount() - 1; i >= 0; i--) {
            JButton button = (JButton) panelSizeDefaultSettings.getComponent(i);
            if (button.getText().startsWith("set to default")) {
                panelSizeDefaultSettings.remove(button);
            }
        }

        cbPreferredImageLanguage.setModel(new DefaultComboBoxModel<>(CardLanguage.toList()));
        cbCardRenderImageFallback.setModel(new DefaultComboBoxModel<>(CardRenderMode.toList()));
    }

    private void createSizeSetting(Integer position, String key, Integer defaultValue, boolean useExample, String name, String hint) {
        SizeSetting setting = new SizeSetting(
                position,
                GUISizeHelper.getComponentByFieldName(this, "sliderSize" + position),
                GUISizeHelper.getComponentByFieldName(this, "editSize" + position),
                GUISizeHelper.getComponentByFieldName(this, "hintSize" + position),
                GUISizeHelper.getComponentByFieldName(this, "sampleSize" + position),
                key,
                defaultValue
        );
        setting.init(name, hint, useExample);
        setting.enableSyncValues();
        this.sizeSettings.put(key, setting);
    }

    private void hideUnusedSizeSettings() {
        // find all panels
        Map<Integer, JPanel> panels = new HashMap<>();
        int position = 1;
        JPanel panel;
        while (true) {
            panel = GUISizeHelper.getComponentByFieldName(this, "panelSize" + position);
            if (panel != null) {
                panels.put(position, panel);
                position++;
            } else {
                break;
            }
        }

        // hide unused panels
        panels.forEach((panelPosition, panelControl) -> {
            if (this.sizeSettings.values().stream().noneMatch(s -> s.position.equals(panelPosition))) {
                // must remove, so layout fill an empty space with other panels
                panelControl.getParent().remove(panelControl);
            }
        });
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
        main_gamelog = new javax.swing.JPanel();
        cbGameLogAutoSave = new javax.swing.JCheckBox();
        cbDraftLogAutoSave = new javax.swing.JCheckBox();
        cbLimitedDeckAutoSave = new javax.swing.JCheckBox();
        cbGameJsonLogAutoSave = new javax.swing.JCheckBox();
        main_card = new javax.swing.JPanel();
        showCardName = new javax.swing.JCheckBox();
        tooltipDelayLabel = new javax.swing.JLabel();
        tooltipDelay = new javax.swing.JSlider();
        showFullImagePath = new javax.swing.JCheckBox();
        main_game = new javax.swing.JPanel();
        nonLandPermanentsInOnePile = new javax.swing.JCheckBox();
        showPlayerNamesPermanently = new javax.swing.JCheckBox();
        displayLifeOnAvatar = new javax.swing.JCheckBox();
        cbAllowRequestToShowHandCards = new javax.swing.JCheckBox();
        cbConfirmEmptyManaPool = new javax.swing.JCheckBox();
        cbAskMoveToGraveOrder = new javax.swing.JCheckBox();
        lblTargetAutoChoose = new javax.swing.JLabel();
        cbTargetAutoChooseLevel = new javax.swing.JComboBox<>();
        tabAvatars = new javax.swing.JPanel();
        avatarPane = new javax.swing.JScrollPane();
        avatarPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        tabGuiSize = new javax.swing.JPanel();
        panelSizeDefaultSettings = new javax.swing.JPanel();
        buttonSizeDefault1 = new javax.swing.JButton();
        buttonSizeDefault2 = new javax.swing.JButton();
        buttonSizeDefault3 = new javax.swing.JButton();
        buttonSizeDefault4 = new javax.swing.JButton();
        buttonSizeDefault5 = new javax.swing.JButton();
        buttonSizeDefault6 = new javax.swing.JButton();
        panelSizeDetailedSettings = new javax.swing.JPanel();
        labelSizeGroup1 = new javax.swing.JLabel();
        panelSize1 = new javax.swing.JPanel();
        sliderSize1 = new javax.swing.JSlider();
        editSize1 = new javax.swing.JTextField();
        hintSize1 = new javax.swing.JLabel();
        sampleSize1 = new javax.swing.JButton();
        panelSize2 = new javax.swing.JPanel();
        sliderSize2 = new javax.swing.JSlider();
        editSize2 = new javax.swing.JTextField();
        hintSize2 = new javax.swing.JLabel();
        sampleSize2 = new javax.swing.JButton();
        panelSize3 = new javax.swing.JPanel();
        sliderSize3 = new javax.swing.JSlider();
        editSize3 = new javax.swing.JTextField();
        hintSize3 = new javax.swing.JLabel();
        sampleSize3 = new javax.swing.JButton();
        panelSize4 = new javax.swing.JPanel();
        sliderSize4 = new javax.swing.JSlider();
        editSize4 = new javax.swing.JTextField();
        hintSize4 = new javax.swing.JLabel();
        sampleSize4 = new javax.swing.JButton();
        panelSize5 = new javax.swing.JPanel();
        sliderSize5 = new javax.swing.JSlider();
        editSize5 = new javax.swing.JTextField();
        hintSize5 = new javax.swing.JLabel();
        sampleSize5 = new javax.swing.JButton();
        panelSize6 = new javax.swing.JPanel();
        sliderSize6 = new javax.swing.JSlider();
        editSize6 = new javax.swing.JTextField();
        hintSize6 = new javax.swing.JLabel();
        sampleSize6 = new javax.swing.JButton();
        panelSize7 = new javax.swing.JPanel();
        sliderSize7 = new javax.swing.JSlider();
        editSize7 = new javax.swing.JTextField();
        hintSize7 = new javax.swing.JLabel();
        sampleSize7 = new javax.swing.JButton();
        labelSizeGroup2 = new javax.swing.JLabel();
        panelSize8 = new javax.swing.JPanel();
        sliderSize8 = new javax.swing.JSlider();
        editSize8 = new javax.swing.JTextField();
        hintSize8 = new javax.swing.JLabel();
        sampleSize8 = new javax.swing.JButton();
        panelSize9 = new javax.swing.JPanel();
        sliderSize9 = new javax.swing.JSlider();
        editSize9 = new javax.swing.JTextField();
        hintSize9 = new javax.swing.JLabel();
        sampleSize9 = new javax.swing.JButton();
        panelSize10 = new javax.swing.JPanel();
        sliderSize10 = new javax.swing.JSlider();
        editSize10 = new javax.swing.JTextField();
        hintSize10 = new javax.swing.JLabel();
        sampleSize10 = new javax.swing.JButton();
        panelSize11 = new javax.swing.JPanel();
        sliderSize11 = new javax.swing.JSlider();
        editSize11 = new javax.swing.JTextField();
        hintSize11 = new javax.swing.JLabel();
        sampleSize11 = new javax.swing.JButton();
        panelSize12 = new javax.swing.JPanel();
        sliderSize12 = new javax.swing.JSlider();
        editSize12 = new javax.swing.JTextField();
        hintSize12 = new javax.swing.JLabel();
        sampleSize12 = new javax.swing.JButton();
        panelSize13 = new javax.swing.JPanel();
        sliderSize13 = new javax.swing.JSlider();
        editSize13 = new javax.swing.JTextField();
        hintSize13 = new javax.swing.JLabel();
        sampleSize13 = new javax.swing.JButton();
        panelSize14 = new javax.swing.JPanel();
        sliderSize14 = new javax.swing.JSlider();
        editSize14 = new javax.swing.JTextField();
        hintSize14 = new javax.swing.JLabel();
        sampleSize14 = new javax.swing.JButton();
        tabGuiTheme = new javax.swing.JPanel();
        themesCategory = new javax.swing.JPanel();
        lbSelectLabel = new javax.swing.JLabel();
        cbTheme = new javax.swing.JComboBox<>();
        lbThemeHint = new javax.swing.JLabel();
        panelBackgroundImages = new javax.swing.JPanel();
        cbUseDefaultBackground = new javax.swing.JCheckBox();
        txtBackgroundImagePath = new javax.swing.JTextField();
        btnBrowseBackgroundImage = new javax.swing.JButton();
        txtBattlefieldImagePath = new javax.swing.JTextField();
        btnBrowseBattlefieldImage = new javax.swing.JButton();
        cbUseDefaultBattleImage = new javax.swing.JCheckBox();
        cbUseRandomBattleImage = new javax.swing.JCheckBox();
        tabGuiImages = new javax.swing.JPanel();
        panelCardImages = new javax.swing.JPanel();
        cbUseDefaultImageFolder = new javax.swing.JCheckBox();
        txtImageFolderPath = new javax.swing.JTextField();
        btnBrowseImageLocation = new javax.swing.JButton();
        cbSaveToZipFiles = new javax.swing.JCheckBox();
        cbPreferredImageLanguage = new javax.swing.JComboBox<>();
        labelPreferredImageLanguage = new javax.swing.JLabel();
        panelCardStyles = new javax.swing.JPanel();
        cbCardRenderIconsForAbilities = new javax.swing.JCheckBox();
        cbCardRenderIconsForPlayable = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        cbCardRenderShowReminderText = new javax.swing.JCheckBox();
        cbCardRenderHideSetSymbol = new javax.swing.JCheckBox();
        cbCardRenderShowAbilityTextOverlay = new javax.swing.JCheckBox();
        labelRenderMode = new javax.swing.JLabel();
        cbCardRenderImageFallback = new javax.swing.JComboBox<>();
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
        cbStopBlockWithAny = new javax.swing.JCheckBox();
        cbStopBlockWithZero = new javax.swing.JCheckBox();
        cbStopOnNewStackObjects = new javax.swing.JCheckBox();
        cbStopOnAllMain = new javax.swing.JCheckBox();
        cbStopOnAllEnd = new javax.swing.JCheckBox();
        cbPassPriorityCast = new javax.swing.JCheckBox();
        cbPassPriorityActivation = new javax.swing.JCheckBox();
        cbAutoOrderTrigger = new javax.swing.JCheckBox();
        cbUseSameSettingsForReplacementEffect = new javax.swing.JCheckBox();
        tabControls = new javax.swing.JPanel();
        labelNextTurn = new javax.swing.JLabel();
        labelEndStep = new javax.swing.JLabel();
        labelMainStep = new javax.swing.JLabel();
        labelYourTurn = new javax.swing.JLabel();
        lebelSkip = new javax.swing.JLabel();
        labelPriorEnd = new javax.swing.JLabel();
        labelCancel = new javax.swing.JLabel();
        keyCancelSkip = new KeyBindButton(this, KEY_CONTROL_CANCEL_SKIP);
        keyNextTurn = new KeyBindButton(this, KEY_CONTROL_NEXT_TURN);
        keyMainStep = new KeyBindButton(this, KEY_CONTROL_MAIN_STEP);
        keyEndStep = new KeyBindButton(this, KEY_CONTROL_END_STEP);
        keyYourTurn = new KeyBindButton(this, KEY_CONTROL_YOUR_TURN);
        keySkipStack = new KeyBindButton(this, KEY_CONTROL_SKIP_STACK);
        keyPriorEnd = new KeyBindButton(this, KEY_CONTROL_PRIOR_END);
        keySkipStep = new KeyBindButton(this, KEY_CONTROL_SKIP_STEP);
        labelSkipStep = new javax.swing.JLabel();
        keyConfirm = new KeyBindButton(this, KEY_CONTROL_CONFIRM);
        labelConfirm = new javax.swing.JLabel();
        controlsDescriptionLabel = new javax.swing.JLabel();
        bttnResetControls = new javax.swing.JButton();
        labelToggleRecordMacro = new javax.swing.JLabel();
        keyToggleRecordMacro = new KeyBindButton(this, KEY_CONTROL_TOGGLE_MACRO);
        labelSwitchChat = new javax.swing.JLabel();
        keySwitchChat = new KeyBindButton(this, KEY_CONTROL_SWITCH_CHAT);
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
        tabConnection = new javax.swing.JPanel();
        connection_Proxy = new javax.swing.JPanel();
        cbProxyType = new javax.swing.JComboBox<>();
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
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tabsPanel.setMinimumSize(new java.awt.Dimension(532, 451));

        main_gamelog.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game log"));
        main_gamelog.setLayout(new javax.swing.BoxLayout(main_gamelog, javax.swing.BoxLayout.PAGE_AXIS));

        cbGameLogAutoSave.setSelected(true);
        cbGameLogAutoSave.setText("Save game logs (dest folder: \"..\\xmage\\mage-client\\gamelogs\")");
        cbGameLogAutoSave.setToolTipText("The logs of all your games will be saved to the mentioned folder if this option is switched on.");
        main_gamelog.add(cbGameLogAutoSave);

        cbDraftLogAutoSave.setSelected(true);
        cbDraftLogAutoSave.setText("Save draft logs (dest folder: \"..\\xmage\\mage-client\\gamelogs\")");
        cbDraftLogAutoSave.setToolTipText("The logs of all your games will be saved to the mentioned folder if this option is switched on.");
        cbDraftLogAutoSave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        main_gamelog.add(cbDraftLogAutoSave);

        cbLimitedDeckAutoSave.setSelected(true);
        cbLimitedDeckAutoSave.setText("Save limited decks on submit (dest folder: \"..\\xmage\\mage-client\\gamelogs\")");
        cbLimitedDeckAutoSave.setToolTipText("A .dck file for each limited tournament will be saved to the mentioned folder if this option is switched on.");
        main_gamelog.add(cbLimitedDeckAutoSave);

        cbGameJsonLogAutoSave.setText("Save JSON game logs (dest folder: \"..\\xmage\\mage-client\\gamelogs\")");
        cbGameJsonLogAutoSave.setToolTipText("The JSON logs of all your games will be saved to the mentioned folder if this option is switched on.");
        main_gamelog.add(cbGameJsonLogAutoSave);

        main_card.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card"));

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

        tooltipDelayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tooltipDelayLabel.setText("Delay in milliseconds for showing the card tooltip text (0 value will disable tooltip)");
        tooltipDelayLabel.setToolTipText("<HTML>The time the appearance of the tooltip window for a card is delayed.<br>\nIf set to zero, the tooltip window won't be shown at all.");

        tooltipDelay.setMajorTickSpacing(500);
        tooltipDelay.setMaximum(5000);
        tooltipDelay.setMinorTickSpacing(100);
        tooltipDelay.setPaintLabels(true);
        tooltipDelay.setPaintTicks(true);
        tooltipDelay.setSnapToTicks(true);
        tooltipDelay.setToolTipText("<HTML>The time the appearance of the tooltip window for a card is delayed.<br>\nIf set to zero, the tooltip window won't be shown at all.");
        tooltipDelay.setValue(300);

        showFullImagePath.setSelected(true);
        showFullImagePath.setToolTipText("Show the path Xmage is expecting for this card's image (only displays if missing)");
        showFullImagePath.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        showFullImagePath.setLabel("Display image path for missing images");
        showFullImagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFullImagePathActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout main_cardLayout = new org.jdesktop.layout.GroupLayout(main_card);
        main_card.setLayout(main_cardLayout);
        main_cardLayout.setHorizontalGroup(
            main_cardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(main_cardLayout.createSequentialGroup()
                .add(6, 6, 6)
                .add(tooltipDelayLabel)
                .addContainerGap(383, Short.MAX_VALUE))
            .add(main_cardLayout.createSequentialGroup()
                .add(main_cardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(main_cardLayout.createSequentialGroup()
                        .add(showCardName)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(showFullImagePath))
                    .add(tooltipDelay, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 522, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, Short.MAX_VALUE))
        );
        main_cardLayout.setVerticalGroup(
            main_cardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(main_cardLayout.createSequentialGroup()
                .add(main_cardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(showCardName)
                    .add(showFullImagePath))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tooltipDelayLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tooltipDelay, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        main_game.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game"));

        nonLandPermanentsInOnePile.setSelected(true);
        nonLandPermanentsInOnePile.setText("Put non-land permanents in same row as creatures");
        nonLandPermanentsInOnePile.setToolTipText("<html>If activated, all non land permanents are shown in one row.<br>\nFirst creatures than other permanents. If not activated, creatures are<br>\nshown in a separate row.");

        showPlayerNamesPermanently.setSelected(true);
        showPlayerNamesPermanently.setText("Show player names on avatar permanently");
        showPlayerNamesPermanently.setToolTipText("Instead showing the names only if you hover over the avatar with the mouse, the name is shown all the time.");

        displayLifeOnAvatar.setSelected(true);
        displayLifeOnAvatar.setText("Display life on avatar image");
        displayLifeOnAvatar.setToolTipText("Display the player's life over its avatar image.");

        cbAllowRequestToShowHandCards.setSelected(true);
        cbAllowRequestToShowHandCards.setText("Allow requests from players and spectators to show your hand cards");
        cbAllowRequestToShowHandCards.setToolTipText("<html>This is the default setting used for your matches. If activated other players or spectators<br>\nof your match can send a request so you can allow them to see your hand cards.");

        cbConfirmEmptyManaPool.setSelected(true);
        cbConfirmEmptyManaPool.setText("Confirm if you want to pass a phase/step but there is still mana in your mana pool");
        cbConfirmEmptyManaPool.setToolTipText("<html>If activated you get a confirm message if you pass priority while stack is empty<br>\n and you still have mana in your mana pool.");

        cbAskMoveToGraveOrder.setSelected(true);
        cbAskMoveToGraveOrder.setText("Ask player for setting order cards go to graveyard");
        cbAskMoveToGraveOrder.setToolTipText("<html>If activated and multiple cards go to the graveyard at the same time<br>\nthe player is asked to set the order of the cards.");

        lblTargetAutoChoose.setText("Auto-choose targets for player:");
        lblTargetAutoChoose.setToolTipText("<html>\nWhen there is only one possible outcome for targeting, the targets can be chosen for you.\n<br>\n<b>None:</b> All targeting must be done by the player.\n<br>\n<b>Most:</b> All targeting other than feel-bad effects (discarding, destroy, sacrifice, exile) that target you, a card you own, or a permanent/spell you control.\n<br>\n<b>All:</b> All targeting that can be automated will be.");

        cbTargetAutoChooseLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Off", "Most", "All" }));
        cbTargetAutoChooseLevel.setSelectedIndex(1);
        cbTargetAutoChooseLevel.setToolTipText(lblTargetAutoChoose.getToolTipText());

        org.jdesktop.layout.GroupLayout main_gameLayout = new org.jdesktop.layout.GroupLayout(main_game);
        main_game.setLayout(main_gameLayout);
        main_gameLayout.setHorizontalGroup(
            main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(displayLifeOnAvatar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(main_gameLayout.createSequentialGroup()
                .add(main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(main_gameLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(lblTargetAutoChoose)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cbTargetAutoChooseLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(cbAskMoveToGraveOrder, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 596, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(showPlayerNamesPermanently, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(nonLandPermanentsInOnePile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(cbConfirmEmptyManaPool, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                        .add(cbAllowRequestToShowHandCards, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        main_gameLayout.setVerticalGroup(
            main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(main_gameLayout.createSequentialGroup()
                .add(nonLandPermanentsInOnePile)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(showPlayerNamesPermanently)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayLifeOnAvatar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(cbAllowRequestToShowHandCards)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbConfirmEmptyManaPool)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbAskMoveToGraveOrder)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblTargetAutoChoose)
                    .add(cbTargetAutoChooseLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        nonLandPermanentsInOnePile.getAccessibleContext().setAccessibleName("nonLandPermanentsInOnePile");
        cbTargetAutoChooseLevel.getAccessibleContext().setAccessibleName("Auto-choose targets for player combo box");

        org.jdesktop.layout.GroupLayout tabMainLayout = new org.jdesktop.layout.GroupLayout(tabMain);
        tabMain.setLayout(tabMainLayout);
        tabMainLayout.setHorizontalGroup(
            tabMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, main_card, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, main_gamelog, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(main_game, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabMainLayout.setVerticalGroup(
            tabMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(main_card, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(main_game, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(main_gamelog, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        main_card.getAccessibleContext().setAccessibleName("Game panel");

        tabsPanel.addTab("Main", tabMain);

        avatarPane.setMaximumSize(new java.awt.Dimension(508, 772));
        avatarPane.setMinimumSize(new java.awt.Dimension(508, 772));
        avatarPane.setPreferredSize(new java.awt.Dimension(508, 772));

        avatarPanel.setMaximumSize(new java.awt.Dimension(508, 772));
        avatarPanel.setMinimumSize(new java.awt.Dimension(508, 772));
        avatarPanel.setPreferredSize(new java.awt.Dimension(508, 772));
        avatarPanel.setLayout(new java.awt.GridLayout(6, 4, 20, 20));

        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel10.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel10.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel10.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel10);

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel11.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel11.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel11.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel11);

        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel12.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel12.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel12.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel12);

        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel13.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel13.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel13.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel13);

        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel14.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel14.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel14.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel14Layout = new org.jdesktop.layout.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel14);

        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel15.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel15.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel15.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel15);

        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel16.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel16.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel16.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel16);

        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel17.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel17.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel17.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel17Layout = new org.jdesktop.layout.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel17);

        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel18.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel18.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel18.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel18Layout = new org.jdesktop.layout.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel18);

        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel19.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel19.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel19.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel19Layout = new org.jdesktop.layout.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel19);

        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel20.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel20.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel20.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel20Layout = new org.jdesktop.layout.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel20);

        jPanel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel21.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel21.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel21.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel21Layout = new org.jdesktop.layout.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel21);

        jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel22.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel22.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel22.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel22Layout = new org.jdesktop.layout.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel22);

        jPanel23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel23.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel23.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel23.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel23Layout = new org.jdesktop.layout.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel23);

        jPanel24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel24.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel24.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel24.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel24Layout = new org.jdesktop.layout.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel24);

        jPanel25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel25.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel25.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel25.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel25Layout = new org.jdesktop.layout.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel25);

        jPanel26.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel26.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel26.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel26.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel26Layout = new org.jdesktop.layout.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel26);

        jPanel27.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel27.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel27.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel27.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel27Layout = new org.jdesktop.layout.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel27);

        jPanel28.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel28.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel28.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel28.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel28Layout = new org.jdesktop.layout.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel28);

        jPanel29.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel29.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel29.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel29.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel29Layout = new org.jdesktop.layout.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel29);

        jPanel30.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel30.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel30.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel30.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel30Layout = new org.jdesktop.layout.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel30);

        jPanel31.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel31.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel31.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel31.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel31Layout = new org.jdesktop.layout.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel31);

        jPanel32.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel32.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel32.setMinimumSize(new java.awt.Dimension(102, 102));
        jPanel32.setPreferredSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel32Layout = new org.jdesktop.layout.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel32);

        jPanel33.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel33.setMaximumSize(new java.awt.Dimension(102, 102));
        jPanel33.setMinimumSize(new java.awt.Dimension(102, 102));

        org.jdesktop.layout.GroupLayout jPanel33Layout = new org.jdesktop.layout.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        avatarPanel.add(jPanel33);

        avatarPane.setViewportView(avatarPanel);

        org.jdesktop.layout.GroupLayout tabAvatarsLayout = new org.jdesktop.layout.GroupLayout(tabAvatars);
        tabAvatars.setLayout(tabAvatarsLayout);
        tabAvatarsLayout.setHorizontalGroup(
            tabAvatarsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabAvatarsLayout.createSequentialGroup()
                .add(avatarPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 528, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        tabAvatarsLayout.setVerticalGroup(
            tabAvatarsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabAvatarsLayout.createSequentialGroup()
                .add(avatarPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 504, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 52, Short.MAX_VALUE))
        );

        tabsPanel.addTab("My Avatar", tabAvatars);

        panelSizeDefaultSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Choose predefined settings due your screen size"));
        panelSizeDefaultSettings.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        buttonSizeDefault1.setText("set to default");
        panelSizeDefaultSettings.add(buttonSizeDefault1);

        buttonSizeDefault2.setText("set to default");
        panelSizeDefaultSettings.add(buttonSizeDefault2);

        buttonSizeDefault3.setText("set to default");
        panelSizeDefaultSettings.add(buttonSizeDefault3);

        buttonSizeDefault4.setText("set to default");
        panelSizeDefaultSettings.add(buttonSizeDefault4);

        buttonSizeDefault5.setText("set to default");
        panelSizeDefaultSettings.add(buttonSizeDefault5);

        buttonSizeDefault6.setText("set to default");
        panelSizeDefaultSettings.add(buttonSizeDefault6);

        panelSizeDetailedSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detailed settings"));
        panelSizeDetailedSettings.setLayout(new java.awt.GridLayout(16, 1));

        labelSizeGroup1.setText("App's elements:");
        labelSizeGroup1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        panelSizeDetailedSettings.add(labelSizeGroup1);

        panelSize1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize1.setMajorTickSpacing(5);
        sliderSize1.setMaximum(50);
        sliderSize1.setMinimum(10);
        sliderSize1.setMinorTickSpacing(1);
        sliderSize1.setPaintTicks(true);
        sliderSize1.setSnapToTicks(true);
        sliderSize1.setValue(25);
        sliderSize1.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize1.add(sliderSize1);

        editSize1.setText("value");
        panelSize1.add(editSize1);

        hintSize1.setText("name and hint");
        panelSize1.add(hintSize1);

        sampleSize1.setText("sample");
        sampleSize1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize1.add(sampleSize1);

        panelSizeDetailedSettings.add(panelSize1);

        panelSize2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize2.setMajorTickSpacing(5);
        sliderSize2.setMaximum(50);
        sliderSize2.setMinimum(10);
        sliderSize2.setMinorTickSpacing(1);
        sliderSize2.setPaintTicks(true);
        sliderSize2.setSnapToTicks(true);
        sliderSize2.setValue(25);
        sliderSize2.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize2.add(sliderSize2);

        editSize2.setText("value");
        panelSize2.add(editSize2);

        hintSize2.setText("name and hint");
        panelSize2.add(hintSize2);

        sampleSize2.setText("sample");
        sampleSize2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize2.add(sampleSize2);

        panelSizeDetailedSettings.add(panelSize2);

        panelSize3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize3.setMajorTickSpacing(5);
        sliderSize3.setMaximum(50);
        sliderSize3.setMinimum(10);
        sliderSize3.setMinorTickSpacing(1);
        sliderSize3.setPaintTicks(true);
        sliderSize3.setSnapToTicks(true);
        sliderSize3.setValue(25);
        sliderSize3.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize3.add(sliderSize3);

        editSize3.setText("value");
        panelSize3.add(editSize3);

        hintSize3.setText("name and hint");
        panelSize3.add(hintSize3);

        sampleSize3.setText("sample");
        sampleSize3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize3.add(sampleSize3);

        panelSizeDetailedSettings.add(panelSize3);

        panelSize4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize4.setMajorTickSpacing(5);
        sliderSize4.setMaximum(50);
        sliderSize4.setMinimum(10);
        sliderSize4.setMinorTickSpacing(1);
        sliderSize4.setPaintTicks(true);
        sliderSize4.setSnapToTicks(true);
        sliderSize4.setValue(25);
        sliderSize4.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize4.add(sliderSize4);

        editSize4.setText("value");
        panelSize4.add(editSize4);

        hintSize4.setText("name and hint");
        panelSize4.add(hintSize4);

        sampleSize4.setText("sample");
        sampleSize4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize4.add(sampleSize4);

        panelSizeDetailedSettings.add(panelSize4);

        panelSize5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize5.setMajorTickSpacing(5);
        sliderSize5.setMaximum(50);
        sliderSize5.setMinimum(10);
        sliderSize5.setMinorTickSpacing(1);
        sliderSize5.setPaintTicks(true);
        sliderSize5.setSnapToTicks(true);
        sliderSize5.setValue(25);
        sliderSize5.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize5.add(sliderSize5);

        editSize5.setText("value");
        panelSize5.add(editSize5);

        hintSize5.setText("name and hint");
        panelSize5.add(hintSize5);

        sampleSize5.setText("sample");
        sampleSize5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize5.add(sampleSize5);

        panelSizeDetailedSettings.add(panelSize5);

        panelSize6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize6.setMajorTickSpacing(5);
        sliderSize6.setMaximum(50);
        sliderSize6.setMinimum(10);
        sliderSize6.setMinorTickSpacing(1);
        sliderSize6.setPaintTicks(true);
        sliderSize6.setSnapToTicks(true);
        sliderSize6.setValue(25);
        sliderSize6.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize6.add(sliderSize6);

        editSize6.setText("value");
        panelSize6.add(editSize6);

        hintSize6.setText("name and hint");
        panelSize6.add(hintSize6);

        sampleSize6.setText("sample");
        sampleSize6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize6.add(sampleSize6);

        panelSizeDetailedSettings.add(panelSize6);

        panelSize7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize7.setMajorTickSpacing(5);
        sliderSize7.setMaximum(50);
        sliderSize7.setMinimum(10);
        sliderSize7.setMinorTickSpacing(1);
        sliderSize7.setPaintTicks(true);
        sliderSize7.setSnapToTicks(true);
        sliderSize7.setValue(25);
        sliderSize7.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize7.add(sliderSize7);

        editSize7.setText("value");
        panelSize7.add(editSize7);

        hintSize7.setText("name and hint");
        panelSize7.add(hintSize7);

        sampleSize7.setText("sample");
        sampleSize7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize7.add(sampleSize7);

        panelSizeDetailedSettings.add(panelSize7);

        labelSizeGroup2.setText("Game's elements:");
        labelSizeGroup2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        panelSizeDetailedSettings.add(labelSizeGroup2);

        panelSize8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize8.setMajorTickSpacing(5);
        sliderSize8.setMaximum(50);
        sliderSize8.setMinimum(10);
        sliderSize8.setMinorTickSpacing(1);
        sliderSize8.setPaintTicks(true);
        sliderSize8.setSnapToTicks(true);
        sliderSize8.setValue(25);
        sliderSize8.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize8.add(sliderSize8);

        editSize8.setText("value");
        panelSize8.add(editSize8);

        hintSize8.setText("name and hint");
        panelSize8.add(hintSize8);

        sampleSize8.setText("sample");
        sampleSize8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize8.add(sampleSize8);

        panelSizeDetailedSettings.add(panelSize8);

        panelSize9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize9.setMajorTickSpacing(5);
        sliderSize9.setMaximum(50);
        sliderSize9.setMinimum(10);
        sliderSize9.setMinorTickSpacing(1);
        sliderSize9.setPaintTicks(true);
        sliderSize9.setSnapToTicks(true);
        sliderSize9.setValue(25);
        sliderSize9.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize9.add(sliderSize9);

        editSize9.setText("value");
        panelSize9.add(editSize9);

        hintSize9.setText("name and hint");
        panelSize9.add(hintSize9);

        sampleSize9.setText("sample");
        sampleSize9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize9.add(sampleSize9);

        panelSizeDetailedSettings.add(panelSize9);

        panelSize10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize10.setMajorTickSpacing(5);
        sliderSize10.setMaximum(50);
        sliderSize10.setMinimum(10);
        sliderSize10.setMinorTickSpacing(1);
        sliderSize10.setPaintTicks(true);
        sliderSize10.setSnapToTicks(true);
        sliderSize10.setValue(25);
        sliderSize10.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize10.add(sliderSize10);

        editSize10.setText("value");
        panelSize10.add(editSize10);

        hintSize10.setText("name and hint");
        panelSize10.add(hintSize10);

        sampleSize10.setText("sample");
        sampleSize10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize10.add(sampleSize10);

        panelSizeDetailedSettings.add(panelSize10);

        panelSize11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize11.setMajorTickSpacing(5);
        sliderSize11.setMaximum(50);
        sliderSize11.setMinimum(10);
        sliderSize11.setMinorTickSpacing(1);
        sliderSize11.setPaintTicks(true);
        sliderSize11.setSnapToTicks(true);
        sliderSize11.setValue(25);
        sliderSize11.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize11.add(sliderSize11);

        editSize11.setText("value");
        panelSize11.add(editSize11);

        hintSize11.setText("name and hint");
        panelSize11.add(hintSize11);

        sampleSize11.setText("sample");
        sampleSize11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize11.add(sampleSize11);

        panelSizeDetailedSettings.add(panelSize11);

        panelSize12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize12.setMajorTickSpacing(5);
        sliderSize12.setMaximum(50);
        sliderSize12.setMinimum(10);
        sliderSize12.setMinorTickSpacing(1);
        sliderSize12.setPaintTicks(true);
        sliderSize12.setSnapToTicks(true);
        sliderSize12.setValue(25);
        sliderSize12.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize12.add(sliderSize12);

        editSize12.setText("value");
        panelSize12.add(editSize12);

        hintSize12.setText("name and hint");
        panelSize12.add(hintSize12);

        sampleSize12.setText("sample");
        sampleSize12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize12.add(sampleSize12);

        panelSizeDetailedSettings.add(panelSize12);

        panelSize13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize13.setMajorTickSpacing(5);
        sliderSize13.setMaximum(50);
        sliderSize13.setMinimum(10);
        sliderSize13.setMinorTickSpacing(1);
        sliderSize13.setPaintTicks(true);
        sliderSize13.setSnapToTicks(true);
        sliderSize13.setValue(25);
        sliderSize13.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize13.add(sliderSize13);

        editSize13.setText("value");
        panelSize13.add(editSize13);

        hintSize13.setText("name and hint");
        panelSize13.add(hintSize13);

        sampleSize13.setText("sample");
        sampleSize13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize13.add(sampleSize13);

        panelSizeDetailedSettings.add(panelSize13);

        panelSize14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        sliderSize14.setMajorTickSpacing(5);
        sliderSize14.setMaximum(50);
        sliderSize14.setMinimum(10);
        sliderSize14.setMinorTickSpacing(1);
        sliderSize14.setPaintTicks(true);
        sliderSize14.setSnapToTicks(true);
        sliderSize14.setValue(25);
        sliderSize14.setPreferredSize(new java.awt.Dimension(300, 31));
        panelSize14.add(sliderSize14);

        editSize14.setText("value");
        panelSize14.add(editSize14);

        hintSize14.setText("name and hint");
        panelSize14.add(hintSize14);

        sampleSize14.setText("sample");
        sampleSize14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSize14.add(sampleSize14);

        panelSizeDetailedSettings.add(panelSize14);

        org.jdesktop.layout.GroupLayout tabGuiSizeLayout = new org.jdesktop.layout.GroupLayout(tabGuiSize);
        tabGuiSize.setLayout(tabGuiSizeLayout);
        tabGuiSizeLayout.setHorizontalGroup(
            tabGuiSizeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabGuiSizeLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabGuiSizeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelSizeDetailedSettings, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
                    .add(panelSizeDefaultSettings, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabGuiSizeLayout.setVerticalGroup(
            tabGuiSizeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabGuiSizeLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelSizeDefaultSettings, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(panelSizeDetailedSettings, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 510, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabsPanel.addTab("GUI Size", tabGuiSize);

        themesCategory.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Themes"));

        lbSelectLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSelectLabel.setText("GUI color style:");
        lbSelectLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lbSelectLabel.setPreferredSize(new java.awt.Dimension(110, 16));
        lbSelectLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        cbTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbThemeActionPerformed(evt);
            }
        });

        lbThemeHint.setText("<html><b>WARNING</b>, some color settings and images will be applied after app RESTART");

        org.jdesktop.layout.GroupLayout themesCategoryLayout = new org.jdesktop.layout.GroupLayout(themesCategory);
        themesCategory.setLayout(themesCategoryLayout);
        themesCategoryLayout.setHorizontalGroup(
            themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(themesCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbSelectLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lbThemeHint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cbTheme, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 313, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        themesCategoryLayout.setVerticalGroup(
            themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(themesCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .add(themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(cbTheme, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lbSelectLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lbThemeHint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        panelBackgroundImages.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Background images"));

        cbUseDefaultBackground.setText("Use default app's background");
        cbUseDefaultBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseDefaultBackgroundActionPerformed(evt);
            }
        });

        btnBrowseBackgroundImage.setText("Browse...");
        btnBrowseBackgroundImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseBackgroundImageActionPerformed(evt);
            }
        });

        btnBrowseBattlefieldImage.setText("Browse...");
        btnBrowseBattlefieldImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseBattlefieldImageActionPerformed(evt);
            }
        });

        cbUseDefaultBattleImage.setText("Use default battlefield's background (from theme)");
        cbUseDefaultBattleImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseDefaultBattleImageActionPerformed(evt);
            }
        });

        cbUseRandomBattleImage.setText("Use random background from path .\\xmage\\mage-client\\backgrounds");
        cbUseRandomBattleImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseRandomBattleImageActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panelBackgroundImagesLayout = new org.jdesktop.layout.GroupLayout(panelBackgroundImages);
        panelBackgroundImages.setLayout(panelBackgroundImagesLayout);
        panelBackgroundImagesLayout.setHorizontalGroup(
            panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelBackgroundImagesLayout.createSequentialGroup()
                .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelBackgroundImagesLayout.createSequentialGroup()
                        .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cbUseDefaultBackground)
                            .add(panelBackgroundImagesLayout.createSequentialGroup()
                                .add(cbUseDefaultBattleImage)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cbUseRandomBattleImage)))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(panelBackgroundImagesLayout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(panelBackgroundImagesLayout.createSequentialGroup()
                                .add(txtBattlefieldImagePath)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(btnBrowseBattlefieldImage))
                            .add(panelBackgroundImagesLayout.createSequentialGroup()
                                .add(txtBackgroundImagePath)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(btnBrowseBackgroundImage)))))
                .addContainerGap())
        );
        panelBackgroundImagesLayout.setVerticalGroup(
            panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelBackgroundImagesLayout.createSequentialGroup()
                .add(cbUseDefaultBackground)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtBackgroundImagePath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBrowseBackgroundImage))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbUseDefaultBattleImage)
                    .add(cbUseRandomBattleImage))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtBattlefieldImagePath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBrowseBattlefieldImage))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout tabGuiThemeLayout = new org.jdesktop.layout.GroupLayout(tabGuiTheme);
        tabGuiTheme.setLayout(tabGuiThemeLayout);
        tabGuiThemeLayout.setHorizontalGroup(
            tabGuiThemeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabGuiThemeLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabGuiThemeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, themesCategory, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelBackgroundImages, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabGuiThemeLayout.setVerticalGroup(
            tabGuiThemeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabGuiThemeLayout.createSequentialGroup()
                .addContainerGap()
                .add(themesCategory, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelBackgroundImages, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabsPanel.addTab("GUI Theme", tabGuiTheme);

        panelCardImages.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card images"));

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

        cbSaveToZipFiles.setText("Store images in zip files");
        cbSaveToZipFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSaveToZipFilesActionPerformed(evt);
            }
        });

        cbPreferredImageLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelPreferredImageLanguage.setText("Default images language:");
        labelPreferredImageLanguage.setFocusable(false);

        org.jdesktop.layout.GroupLayout panelCardImagesLayout = new org.jdesktop.layout.GroupLayout(panelCardImages);
        panelCardImages.setLayout(panelCardImagesLayout);
        panelCardImagesLayout.setHorizontalGroup(
            panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelCardImagesLayout.createSequentialGroup()
                .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelCardImagesLayout.createSequentialGroup()
                        .add(cbUseDefaultImageFolder)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtImageFolderPath)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnBrowseImageLocation))
                    .add(panelCardImagesLayout.createSequentialGroup()
                        .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cbSaveToZipFiles)
                            .add(panelCardImagesLayout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(labelPreferredImageLanguage)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cbPreferredImageLanguage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 153, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCardImagesLayout.setVerticalGroup(
            panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelCardImagesLayout.createSequentialGroup()
                .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbUseDefaultImageFolder)
                    .add(txtImageFolderPath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBrowseImageLocation))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(cbSaveToZipFiles)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelPreferredImageLanguage)
                    .add(cbPreferredImageLanguage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelCardStyles.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card styles (restart xmage to apply new settings)"));

        cbCardRenderIconsForAbilities.setText("Enable card icons for abilities (example: flying, deathtouch)");

        cbCardRenderIconsForPlayable.setText("Enable card icons for playable abilities (example: if you can activate card's ability then show a special icon in the corner)");

        cbCardRenderShowReminderText.setText("Show reminder text in rendered card textboxes");

        cbCardRenderHideSetSymbol.setText("Hide set symbols on cards (more space on the type line for card types)");

        cbCardRenderShowAbilityTextOverlay.setText("Show ability text as overlay in big card view");

        labelRenderMode.setText("Render Mode:");
        labelRenderMode.setToolTipText("<HTML>Image - Renders card image with text overlay<br> MTGO - Renders card frame around card art<br> Forced M15 - Renders all cards in the modern frame<br> Forced Retro - Renders all cards in the retro frame");

        cbCardRenderImageFallback.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCardRenderImageFallback.setToolTipText("<HTML>Image - Renders card image with text overlay<br> MTGO - Renders card frame around card art<br> Forced M15 - Renders all cards in the MTGO style with the modern frame<br> Forced Retro - Renders all cards in the MTGO style with the retro frame");

        org.jdesktop.layout.GroupLayout panelCardStylesLayout = new org.jdesktop.layout.GroupLayout(panelCardStyles);
        panelCardStyles.setLayout(panelCardStylesLayout);
        panelCardStylesLayout.setHorizontalGroup(
            panelCardStylesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(cbCardRenderIconsForAbilities)
            .add(cbCardRenderIconsForPlayable)
            .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 775, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(cbCardRenderShowReminderText)
            .add(cbCardRenderHideSetSymbol)
            .add(cbCardRenderShowAbilityTextOverlay)
            .add(panelCardStylesLayout.createSequentialGroup()
                .add(6, 6, 6)
                .add(labelRenderMode)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbCardRenderImageFallback, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        panelCardStylesLayout.setVerticalGroup(
            panelCardStylesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelCardStylesLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(panelCardStylesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelRenderMode)
                    .add(cbCardRenderImageFallback, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, 0)
                .add(cbCardRenderIconsForAbilities)
                .add(0, 0, 0)
                .add(cbCardRenderIconsForPlayable)
                .add(0, 0, 0)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(cbCardRenderShowReminderText)
                .add(0, 0, 0)
                .add(cbCardRenderHideSetSymbol)
                .add(0, 0, 0)
                .add(cbCardRenderShowAbilityTextOverlay))
        );

        org.jdesktop.layout.GroupLayout tabGuiImagesLayout = new org.jdesktop.layout.GroupLayout(tabGuiImages);
        tabGuiImages.setLayout(tabGuiImagesLayout);
        tabGuiImagesLayout.setHorizontalGroup(
            tabGuiImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabGuiImagesLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabGuiImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelCardImages, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelCardStyles, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabGuiImagesLayout.setVerticalGroup(
            tabGuiImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabGuiImagesLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelCardStyles, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelCardImages, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(309, Short.MAX_VALUE))
        );

        tabsPanel.addTab("GUI Images", tabGuiImages);

        jLabelHeadLine.setText("Default stop steps if not skip buttons activated (e.g. F6):");

        jLabelYourTurn.setText("Your turn");

        jLabelOpponentsTurn.setText("Opponent(s) turn");

        jLabelUpkeep.setText("Upkeep:");

        jLabelDraw.setText("Draw:");

        jLabelMain1.setText("Main 1:");

        jLabelBeforeCombat.setText("Before combat:");

        jLabelEndofCombat.setText("End of combat:");

        jLabelMain2.setText("Main 2:");

        jLabelEndOfTurn.setText("End of turn:");

        phases_stopSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "SKIP settings"));
        phases_stopSettings.setLayout(new java.awt.GridLayout(10, 1));

        cbStopAttack.setSelected(true);
        cbStopAttack.setText("STOP skips on declare attackers if attackers are available");
        cbStopAttack.setActionCommand("");
        phases_stopSettings.add(cbStopAttack);

        cbStopBlockWithAny.setSelected(true);
        cbStopBlockWithAny.setText("STOP skips when attacked and on declare blockers if ANY blockers are available");
        cbStopBlockWithAny.setActionCommand("");
        phases_stopSettings.add(cbStopBlockWithAny);

        cbStopBlockWithZero.setText("STOP skips when attacked if ZERO blockers are available");
        cbStopBlockWithZero.setActionCommand("");
        phases_stopSettings.add(cbStopBlockWithZero);

        cbStopOnNewStackObjects.setText("Skip to STACK resolved (F10): stop on new objects added (on) or stop when stack empty (off)");
        cbStopOnNewStackObjects.setActionCommand("");
        cbStopOnNewStackObjects.setPreferredSize(new java.awt.Dimension(300, 25));
        phases_stopSettings.add(cbStopOnNewStackObjects);

        cbStopOnAllMain.setText("Skip to MAIN step (F7): stop on any main steps (on) or stop on your main step (off)");
        cbStopOnAllMain.setActionCommand("");
        phases_stopSettings.add(cbStopOnAllMain);

        cbStopOnAllEnd.setText("Skip to END step (F5): stop on any end steps (on) or stop on opponents end step (off)");
        cbStopOnAllEnd.setActionCommand("");
        cbStopOnAllEnd.setPreferredSize(new java.awt.Dimension(300, 25));
        phases_stopSettings.add(cbStopOnAllEnd);

        cbPassPriorityCast.setText("Pass priority automatically after you have put a spell on the stack");
        cbPassPriorityCast.setToolTipText("If activated the system passes priority automatically for you if you have put a spell on the stack.");
        cbPassPriorityCast.setActionCommand("");
        cbPassPriorityCast.setPreferredSize(new java.awt.Dimension(300, 25));
        phases_stopSettings.add(cbPassPriorityCast);

        cbPassPriorityActivation.setText("Pass priority automatically after you have put an activated ability on the stack");
        cbPassPriorityActivation.setToolTipText("If activated the system passes priority for you automatically after you have put an activated ability on the stack.");
        cbPassPriorityActivation.setActionCommand("");
        cbPassPriorityActivation.setPreferredSize(new java.awt.Dimension(300, 25));
        phases_stopSettings.add(cbPassPriorityActivation);

        cbAutoOrderTrigger.setText("TRIGGERS: auto-choose triggers order for same rule texts (put same triggers to the stack at default order)");
        cbAutoOrderTrigger.setToolTipText("<HTML>If you put same triggers with same texts on the stack then auto-choose their order.<br/>\nYou can change that settings anytime at the game.");
        cbAutoOrderTrigger.setActionCommand("");
        cbAutoOrderTrigger.setPreferredSize(new java.awt.Dimension(300, 25));
        phases_stopSettings.add(cbAutoOrderTrigger);

        cbUseSameSettingsForReplacementEffect.setText("REPLACEMENT EFFECTS: use same auto-choose settings for same cards (choose replacement effects order dialog)");
        cbUseSameSettingsForReplacementEffect.setToolTipText("<HTML>If you setup auto-choose for one object/card then it will be applied for all other objects with same name.<br/>\nYou can change that settings anytime at the game.");
        cbUseSameSettingsForReplacementEffect.setActionCommand("");
        cbUseSameSettingsForReplacementEffect.setPreferredSize(new java.awt.Dimension(300, 25));
        phases_stopSettings.add(cbUseSameSettingsForReplacementEffect);

        org.jdesktop.layout.GroupLayout tabPhasesLayout = new org.jdesktop.layout.GroupLayout(tabPhases);
        tabPhases.setLayout(tabPhasesLayout);
        tabPhasesLayout.setHorizontalGroup(
            tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabPhasesLayout.createSequentialGroup()
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tabPhasesLayout.createSequentialGroup()
                        .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(tabPhasesLayout.createSequentialGroup()
                                .add(20, 20, 20)
                                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(tabPhasesLayout.createSequentialGroup()
                                        .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabelUpkeep)
                                            .add(jLabelBeforeCombat)
                                            .add(jLabelEndofCombat)
                                            .add(jLabelMain2)
                                            .add(jLabelEndOfTurn))
                                        .add(77, 77, 77)
                                        .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(tabPhasesLayout.createSequentialGroup()
                                                .add(2, 2, 2)
                                                .add(jLabelYourTurn)
                                                .add(32, 32, 32)
                                                .add(jLabelOpponentsTurn))
                                            .add(tabPhasesLayout.createSequentialGroup()
                                                .add(13, 13, 13)
                                                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                                    .add(checkBoxDrawYou)
                                                    .add(checkBoxUpkeepYou)
                                                    .add(checkBoxMainYou)
                                                    .add(checkBoxBeforeCYou)
                                                    .add(checkBoxEndOfCYou)
                                                    .add(checkBoxMain2You)
                                                    .add(checkBoxEndTurnYou))
                                                .add(78, 78, 78)
                                                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                                    .add(checkBoxUpkeepOthers)
                                                    .add(checkBoxBeforeCOthers)
                                                    .add(checkBoxMainOthers)
                                                    .add(checkBoxEndOfCOthers)
                                                    .add(checkBoxDrawOthers)
                                                    .add(checkBoxMain2Others)
                                                    .add(checkBoxEndTurnOthers)))))
                                    .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelMain1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelDraw, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .add(tabPhasesLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(jLabelHeadLine)))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(tabPhasesLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(phases_stopSettings, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabPhasesLayout.setVerticalGroup(
            tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabPhasesLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(tabPhasesLayout.createSequentialGroup()
                        .add(jLabelOpponentsTurn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(checkBoxUpkeepOthers))
                    .add(tabPhasesLayout.createSequentialGroup()
                        .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(tabPhasesLayout.createSequentialGroup()
                                .add(jLabelHeadLine)
                                .add(20, 20, 20))
                            .add(jLabelYourTurn))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(checkBoxUpkeepYou)
                            .add(jLabelUpkeep))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelDraw)
                    .add(checkBoxDrawYou)
                    .add(checkBoxDrawOthers))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelMain1)
                    .add(checkBoxMainYou)
                    .add(checkBoxMainOthers))
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tabPhasesLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelBeforeCombat)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, checkBoxBeforeCYou)))
                    .add(tabPhasesLayout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(checkBoxBeforeCOthers)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelEndofCombat)
                    .add(checkBoxEndOfCYou)
                    .add(checkBoxEndOfCOthers))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelMain2)
                    .add(checkBoxMain2You)
                    .add(checkBoxMain2Others))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabPhasesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(checkBoxEndTurnYou)
                    .add(jLabelEndOfTurn)
                    .add(checkBoxEndTurnOthers))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(phases_stopSettings, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsPanel.addTab("Game - Phases & Priority", tabPhases);

        labelNextTurn.setText("Next Turn:");

        labelEndStep.setText("End Step:");

        labelMainStep.setText("Main Step:");

        labelYourTurn.setText("Your Turn:");

        lebelSkip.setText("Skip Stack:");

        labelPriorEnd.setText("Prior End:");

        labelCancel.setText("Cancel Skip:");

        keyCancelSkip.setText("keyBindButton1");

        keyNextTurn.setText("keyBindButton1");

        keyMainStep.setText("keyBindButton1");

        keyEndStep.setText("keyBindButton1");

        keyYourTurn.setText("keyBindButton1");

        keySkipStack.setText("keyBindButton1");

        keyPriorEnd.setText("keyBindButton1");

        keySkipStep.setText("keyBindButton1");

        labelSkipStep.setText("Skip Step:");

        keyConfirm.setText("keyBindButton1");

        labelConfirm.setText("Confirm:");

        controlsDescriptionLabel.setText("<html>Click on a button and press a KEY or a combination of CTRL/ALT/SHIF + KEY to change a keybind.\n<br>\nPress SPACE to clear binging.\n<br>\nPress ESC to cancel binding.\n<br>\nNew changes will be applied after new game start.");
        controlsDescriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        bttnResetControls.setText("Reset to default");
        bttnResetControls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnResetControlsActionPerformed(evt);
            }
        });

        labelToggleRecordMacro.setText("Record Macro (unsupported):");

        keyToggleRecordMacro.setText("keyBindButton1");

        labelSwitchChat.setText("Go in/out to chat:");

        keySwitchChat.setText("keyBindButton1");

        org.jdesktop.layout.GroupLayout tabControlsLayout = new org.jdesktop.layout.GroupLayout(tabControls);
        tabControls.setLayout(tabControlsLayout);
        tabControlsLayout.setHorizontalGroup(
            tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabControlsLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(bttnResetControls, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(tabControlsLayout.createSequentialGroup()
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(labelCancel)
                            .add(labelNextTurn)
                            .add(labelEndStep)
                            .add(labelMainStep)
                            .add(labelYourTurn)
                            .add(lebelSkip)
                            .add(labelPriorEnd)
                            .add(labelSkipStep)
                            .add(labelConfirm)
                            .add(labelToggleRecordMacro)
                            .add(labelSwitchChat))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(keyConfirm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keyCancelSkip, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keyNextTurn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keySkipStack, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keyYourTurn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keyMainStep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keyPriorEnd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keySkipStep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keyEndStep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keyToggleRecordMacro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(keySwitchChat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(controlsDescriptionLabel)
                .addContainerGap())
        );
        tabControlsLayout.setVerticalGroup(
            tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabControlsLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(tabControlsLayout.createSequentialGroup()
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelConfirm)
                            .add(keyConfirm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelCancel)
                            .add(keyCancelSkip, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelNextTurn)
                            .add(keyNextTurn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelEndStep)
                            .add(keyEndStep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelSkipStep)
                            .add(keySkipStep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelMainStep)
                            .add(keyMainStep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelYourTurn)
                            .add(keyYourTurn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lebelSkip)
                            .add(keySkipStack, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelPriorEnd)
                            .add(keyPriorEnd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelToggleRecordMacro)
                            .add(keyToggleRecordMacro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tabControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(labelSwitchChat)
                            .add(keySwitchChat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(bttnResetControls))
                    .add(controlsDescriptionLabel))
                .addContainerGap())
        );

        tabsPanel.addTab("Game - Hotkeys", tabControls);

        sounds_clips.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Clips"));
        sounds_clips.setLayout(new java.awt.GridLayout(4, 0));

        cbEnableGameSounds.setText("Enable game sounds");
        cbEnableGameSounds.setToolTipText("Sounds that will be played for certain actions (e.g. play land, attack, etc.) during the game.");
        sounds_clips.add(cbEnableGameSounds);

        cbEnableDraftSounds.setText("Enable draft sounds");
        cbEnableDraftSounds.setToolTipText("Sounds that will be played during drafting for card picking or warining if time runs out.");
        sounds_clips.add(cbEnableDraftSounds);

        cbEnableSkipButtonsSounds.setText("Enable skip button sounds");
        cbEnableSkipButtonsSounds.setToolTipText("Sounds that will be played if a priority skip action (F4/F5/F7/F9) or cancel skip action (F3) is used.");
        sounds_clips.add(cbEnableSkipButtonsSounds);

        cbEnableOtherSounds.setText("Enable other sounds");
        cbEnableOtherSounds.setToolTipText("Sounds that will be played for actions outside of games (e.g. whisper, player joins your game, player submits a deck ...).");
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

        btnBattlefieldBGMBrowse.setText("Browse...");
        btnBattlefieldBGMBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBattlefieldBGMBrowseActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sounds_backgroundMusicLayout = new org.jdesktop.layout.GroupLayout(sounds_backgroundMusic);
        sounds_backgroundMusic.setLayout(sounds_backgroundMusicLayout);
        sounds_backgroundMusicLayout.setHorizontalGroup(
            sounds_backgroundMusicLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sounds_backgroundMusicLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel16)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtBattlefieldIBGMPath)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnBattlefieldBGMBrowse))
            .add(sounds_backgroundMusicLayout.createSequentialGroup()
                .add(cbEnableBattlefieldBGM)
                .add(0, 0, Short.MAX_VALUE))
        );
        sounds_backgroundMusicLayout.setVerticalGroup(
            sounds_backgroundMusicLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sounds_backgroundMusicLayout.createSequentialGroup()
                .add(cbEnableBattlefieldBGM)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sounds_backgroundMusicLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtBattlefieldIBGMPath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBattlefieldBGMBrowse)
                    .add(jLabel16)))
        );

        org.jdesktop.layout.GroupLayout tabSoundsLayout = new org.jdesktop.layout.GroupLayout(tabSounds);
        tabSounds.setLayout(tabSoundsLayout);
        tabSoundsLayout.setHorizontalGroup(
            tabSoundsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabSoundsLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabSoundsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sounds_clips, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, sounds_backgroundMusic, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabSoundsLayout.setVerticalGroup(
            tabSoundsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabSoundsLayout.createSequentialGroup()
                .addContainerGap()
                .add(sounds_clips, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sounds_backgroundMusic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sounds_clips.getAccessibleContext().setAccessibleDescription("");

        tabsPanel.addTab("Sounds", tabSounds);

        connection_Proxy.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Proxy for server connection and images download (DO NOT SUPPORTED)"));

        cbProxyType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProxyTypeActionPerformed(evt);
            }
        });

        lblProxyServer.setText("Server:");

        lblProxyPort.setText("Port:");

        lblProxyUserName.setText("User Name:");

        lblProxyPassword.setText("Password:");

        rememberPswd.setText("Remember Password");

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel11.setText("Note: password won't be encrypted!");

        org.jdesktop.layout.GroupLayout pnlProxyLayout = new org.jdesktop.layout.GroupLayout(pnlProxy);
        pnlProxy.setLayout(pnlProxyLayout);
        pnlProxyLayout.setHorizontalGroup(
            pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlProxyLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlProxyLayout.createSequentialGroup()
                        .add(lblProxyServer)
                        .add(39, 39, 39)
                        .add(txtProxyServer))
                    .add(pnlProxyLayout.createSequentialGroup()
                        .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(pnlProxyLayout.createSequentialGroup()
                                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(lblProxyPort)
                                    .add(lblProxyPassword)
                                    .add(lblProxyUserName))
                                .add(19, 19, 19)
                                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(txtProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, txtPasswordField)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, txtProxyUserName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(pnlProxyLayout.createSequentialGroup()
                                .add(rememberPswd)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jLabel11)))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlProxyLayout.setVerticalGroup(
            pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlProxyLayout.createSequentialGroup()
                .add(6, 6, 6)
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtProxyServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblProxyServer))
                .add(8, 8, 8)
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblProxyPort)
                    .add(txtProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtProxyUserName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblProxyUserName))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtPasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblProxyPassword))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(rememberPswd)
                    .add(jLabel11))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout connection_ProxyLayout = new org.jdesktop.layout.GroupLayout(connection_Proxy);
        connection_Proxy.setLayout(connection_ProxyLayout);
        connection_ProxyLayout.setHorizontalGroup(
            connection_ProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(connection_ProxyLayout.createSequentialGroup()
                .addContainerGap()
                .add(connection_ProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(connection_ProxyLayout.createSequentialGroup()
                        .add(cbProxyType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 629, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlProxy, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        connection_ProxyLayout.setVerticalGroup(
            connection_ProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(connection_ProxyLayout.createSequentialGroup()
                .addContainerGap()
                .add(cbProxyType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnlProxy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout tabConnectionLayout = new org.jdesktop.layout.GroupLayout(tabConnection);
        tabConnection.setLayout(tabConnectionLayout);
        tabConnectionLayout.setHorizontalGroup(
            tabConnectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabConnectionLayout.createSequentialGroup()
                .addContainerGap()
                .add(connection_Proxy, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabConnectionLayout.setVerticalGroup(
            tabConnectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabConnectionLayout.createSequentialGroup()
                .add(connection_Proxy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabsPanel.addTab("Network", tabConnection);

        saveButton.setLabel("Save");
        saveButton.setMaximumSize(new java.awt.Dimension(100, 30));
        saveButton.setMinimumSize(new java.awt.Dimension(100, 30));
        saveButton.setPreferredSize(new java.awt.Dimension(100, 30));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        exitButton.setLabel("Exit");
        exitButton.setMaximumSize(new java.awt.Dimension(100, 30));
        exitButton.setMinimumSize(new java.awt.Dimension(100, 30));
        exitButton.setPreferredSize(new java.awt.Dimension(100, 30));
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, tabsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(exitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(tabsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(exitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        Preferences prefs = MageFrame.getPreferences();

        // main
        save(prefs, dialog.tooltipDelay, KEY_SHOW_TOOLTIPS_DELAY);
        save(prefs, dialog.showCardName, KEY_SHOW_CARD_NAMES, "true", "false");
        save(prefs, dialog.showFullImagePath, KEY_SHOW_FULL_IMAGE_PATH, "true", "false");
        save(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true", "false");
        save(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true", "false");
        save(prefs, dialog.displayLifeOnAvatar, KEY_DISPLAY_LIVE_ON_AVATAR, "true", "false");
        save(prefs, dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true", "false");
        save(prefs, dialog.cbConfirmEmptyManaPool, KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true", "false");
        save(prefs, dialog.cbAskMoveToGraveOrder, KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "true", "false");
        save(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true", "false");
        save(prefs, dialog.cbDraftLogAutoSave, KEY_DRAFT_LOG_AUTO_SAVE, "true", "false");
        save(prefs, dialog.cbLimitedDeckAutoSave, KEY_LIMITED_DECK_AUTO_SAVE, "true", "false");
        save(prefs, dialog.cbGameJsonLogAutoSave, KEY_JSON_GAME_LOG_AUTO_SAVE, "true", "false");

        String paramName = KEY_AUTO_TARGET_LEVEL;
        int paramValue = dialog.cbTargetAutoChooseLevel.getSelectedIndex();
        int paramDefault = AUTO_TARGET_NON_FEEL_BAD;
        if (getCachedValue(paramName, paramDefault) != paramValue) {
            prefs.putInt(paramName, paramValue);
            updateCache(paramName, Integer.toString(paramValue));
        }

        saveGUISize(false, false);

        // Phases & Priority
        save(prefs, dialog.checkBoxUpkeepYou, UPKEEP_YOU);
        save(prefs, dialog.checkBoxDrawYou, DRAW_YOU);
        save(prefs, dialog.checkBoxMainYou, MAIN_YOU);
        save(prefs, dialog.checkBoxBeforeCYou, BEFORE_COMBAT_YOU);
        save(prefs, dialog.checkBoxEndOfCYou, END_OF_COMBAT_YOU);
        save(prefs, dialog.checkBoxMain2You, MAIN_TWO_YOU);
        save(prefs, dialog.checkBoxEndTurnYou, END_OF_TURN_YOU);

        save(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS);
        save(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS);
        save(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS);
        save(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxEndOfCOthers, END_OF_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxMain2Others, MAIN_TWO_OTHERS);
        save(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS);

        save(prefs, dialog.cbStopAttack, KEY_STOP_ATTACK, "true", "false");
        save(prefs, dialog.cbStopBlockWithAny, KEY_STOP_BLOCK_WITH_ANY, "true", "false");
        save(prefs, dialog.cbStopBlockWithZero, KEY_STOP_BLOCK_WITH_ZERO, "true", "false");
        save(prefs, dialog.cbStopOnAllMain, KEY_STOP_ALL_MAIN_PHASES, "true", "false");
        save(prefs, dialog.cbStopOnAllEnd, KEY_STOP_ALL_END_PHASES, "true", "false");
        save(prefs, dialog.cbStopOnNewStackObjects, KEY_STOP_NEW_STACK_OBJECTS, "true", "false");
        save(prefs, dialog.cbPassPriorityCast, KEY_PASS_PRIORITY_CAST, "true", "false");
        save(prefs, dialog.cbPassPriorityActivation, KEY_PASS_PRIORITY_ACTIVATION, "true", "false");
        save(prefs, dialog.cbAutoOrderTrigger, KEY_AUTO_ORDER_TRIGGER, "true", "false");
        save(prefs, dialog.cbUseSameSettingsForReplacementEffect, KEY_USE_SAME_SETTINGS_FOR_SAME_REPLACEMENT_EFFECTS, "true", "false");

        // images
        save(prefs, dialog.cbUseDefaultImageFolder, KEY_CARD_IMAGES_USE_DEFAULT, "true", "false");
        saveImagesPath(prefs);
        save(prefs, dialog.cbSaveToZipFiles, KEY_CARD_IMAGES_SAVE_TO_ZIP, "true", "false");
        save(prefs, dialog.cbPreferredImageLanguage, KEY_CARD_IMAGES_PREF_LANGUAGE);

        save(prefs, dialog.cbUseDefaultBackground, KEY_BACKGROUND_IMAGE_DEFAULT, "true", "false");
        save(prefs, dialog.cbUseDefaultBattleImage, KEY_BATTLEFIELD_IMAGE_DEFAULT, "true", "false");
        save(prefs, dialog.cbUseRandomBattleImage, KEY_BATTLEFIELD_IMAGE_RANDOM, "true", "false");

        // rendering
        save(prefs, dialog.cbCardRenderImageFallback, KEY_CARD_RENDERING_IMAGE_MODE);
        save(prefs, dialog.cbCardRenderIconsForAbilities, KEY_CARD_RENDERING_ICONS_FOR_ABILITIES, "true", "false");
        save(prefs, dialog.cbCardRenderIconsForPlayable, KEY_CARD_RENDERING_ICONS_FOR_PLAYABLE, "true", "false");
        save(prefs, dialog.cbCardRenderHideSetSymbol, KEY_CARD_RENDERING_SET_SYMBOL, "true", "false");
        save(prefs, dialog.cbCardRenderShowReminderText, KEY_CARD_RENDERING_REMINDER_TEXT, "true", "false");
        save(prefs, dialog.cbCardRenderShowAbilityTextOverlay, KEY_CARD_RENDERING_ABILITY_TEXT_OVERLAY, "true", "false");

        // sounds
        save(prefs, dialog.cbEnableGameSounds, KEY_SOUNDS_GAME_ON, "true", "false");
        save(prefs, dialog.cbEnableDraftSounds, KEY_SOUNDS_DRAFT_ON, "true", "false");
        save(prefs, dialog.cbEnableSkipButtonsSounds, KEY_SOUNDS_SKIP_BUTTONS_ON, "true", "false");
        save(prefs, dialog.cbEnableOtherSounds, KEY_SOUNDS_OTHER_ON, "true", "false");
        save(prefs, dialog.cbEnableBattlefieldBGM, KEY_SOUNDS_MATCH_MUSIC_ON, "true", "false");
        saveSoundPath(prefs);
        if (prefs.get(KEY_SOUNDS_MATCH_MUSIC_ON, SOUNDS_MATCH_MUSIC_ENABLE_BY_DEFAULT).equals("true")) {
            if (MageFrame.isGameActive()) {
                MusicPlayer.playBGM();
            }
        } else {
            MusicPlayer.stopBGM();
        }

        // proxy
        save(prefs, dialog.cbProxyType, KEY_PROXY_TYPE);
        save(prefs, dialog.txtProxyServer, KEY_PROXY_ADDRESS);
        save(prefs, dialog.txtProxyPort, KEY_PROXY_PORT);
        save(prefs, dialog.txtProxyUserName, KEY_PROXY_USERNAME);
        save(prefs, dialog.rememberPswd, KEY_PROXY_REMEMBER, "true", "false");
        if (dialog.rememberPswd.isSelected()) {
            char[] input = txtPasswordField.getPassword();
            prefs.put(KEY_PROXY_PSWD, new String(input));
        }

        // controls
        save(prefs, dialog.keyConfirm);
        save(prefs, dialog.keyCancelSkip);
        save(prefs, dialog.keyNextTurn);
        save(prefs, dialog.keyEndStep);
        save(prefs, dialog.keySkipStep);
        save(prefs, dialog.keyMainStep);
        save(prefs, dialog.keyYourTurn);
        save(prefs, dialog.keySkipStack);
        save(prefs, dialog.keyPriorEnd);
        save(prefs, dialog.keyToggleRecordMacro);
        save(prefs, dialog.keySwitchChat);

        // Themes
        saveTheme(false);

        // Avatar
        if (selectedAvatarId < MIN_AVATAR_ID || selectedAvatarId > MAX_AVATAR_ID) {
            selectedAvatarId = DEFAULT_AVATAR_ID;
        }
        prefs.put(KEY_AVATAR, String.valueOf(selectedAvatarId));
        updateCache(KEY_AVATAR, String.valueOf(selectedAvatarId));

        // refresh full GUI
        GUISizeHelper.refreshGUIAndCards(true);

        // send server side settings
        try {
            SessionHandler.updatePreferencesForServer(getUserData());
            prefs.flush();
        } catch (BackingStoreException ex) {
            logger.error("Error: couldn't save preferences", ex);
            UserRequestMessage message = new UserRequestMessage("Error", "Error: couldn't save preferences. Please try once again.");
            message.setButton1("OK", null);
            MageFrame.getInstance().showUserRequestDialog(message);
        }

        dialog.setVisible(false);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void saveTheme(boolean refreshTheme) {
        Preferences prefs = MageFrame.getPreferences();
        save(prefs, getInstance().cbTheme, KEY_THEME);

        if (refreshTheme) {
            loadTheme();
        }
    }

    private static void saveGUISize(boolean refreshGUI, boolean refreshTheme) {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        Preferences prefs = MageFrame.getPreferences();
        dialog.sizeSettings.values().forEach(setting -> {
            save(prefs, setting.slider, setting.key);
        });

        // refresh full GUI with new settings (except theme)
        if (refreshGUI) {
            GUISizeHelper.refreshGUIAndCards(refreshTheme);
        }
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void useDefaultPath() {
        txtImageFolderPath.setText("./plugins/images/");
        txtImageFolderPath.setEnabled(false);
        btnBrowseImageLocation.setEnabled(false);
    }

    private void useConfigurablePath() {
        String path = CACHE.get(KEY_CARD_IMAGES_PATH);
        txtImageFolderPath.setText(path);
        txtImageFolderPath.setEnabled(true);
        btnBrowseImageLocation.setEnabled(true);
    }

    private void cbProxyTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProxyTypeActionPerformed
        this.showProxySettings();
    }//GEN-LAST:event_cbProxyTypeActionPerformed

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
        String path = CACHE.get(KEY_BACKGROUND_IMAGE);
        txtBackgroundImagePath.setText(path);
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
        String temp = CACHE.get(KEY_BATTLEFIELD_IMAGE_RANDOM);
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

    private void btnBattlefieldBGMBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBattlefieldBGMBrowseActionPerformed
        int returnVal = fc.showOpenDialog(PreferencesDialog.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtBattlefieldIBGMPath.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_btnBattlefieldBGMBrowseActionPerformed

    private void showCardNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCardNameActionPerformed

    }//GEN-LAST:event_showCardNameActionPerformed

    private void bttnResetControlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnResetControlsActionPerformed
        getKeybindButtons().forEach((bttn) -> {
            String id = bttn.getKey();
            int keyCode = getDefaultControlKey(id);
            int modCode = getDefaultControlMofier(id);
            bttn.setKeyCode(keyCode);
            bttn.setModifierCode(modCode);
        });
    }//GEN-LAST:event_bttnResetControlsActionPerformed

    private void showFullImagePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFullImagePathActionPerformed
    }//GEN-LAST:event_showFullImagePathActionPerformed

    private void cbSaveToZipFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSaveToZipFilesActionPerformed
    }//GEN-LAST:event_cbSaveToZipFilesActionPerformed

    private void btnBrowseImageLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseImageLocationActionPerformed
        int returnVal = fc.showOpenDialog(PreferencesDialog.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtImageFolderPath.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_btnBrowseImageLocationActionPerformed

    private void cbUseDefaultImageFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUseDefaultImageFolderActionPerformed
        if (cbUseDefaultImageFolder.isSelected()) {
            useDefaultPath();
        } else {
            useConfigurablePath();
        }
    }//GEN-LAST:event_cbUseDefaultImageFolderActionPerformed

    private void cbThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbThemeActionPerformed
        if (!isLoadingTheme) {
            saveTheme(true);
        }
    }//GEN-LAST:event_cbThemeActionPerformed

    private void showProxySettings() {
        Connection.ProxyType proxyType = (Connection.ProxyType) cbProxyType.getSelectedItem();
        switch (proxyType) {
            case HTTP:
            case SOCKS:
                GuiDisplayUtil.setPanelEnabled(this.pnlProxy, true);
                break;
            case NONE:
                GuiDisplayUtil.setPanelEnabled(this.pnlProxy, false);
                break;
            default:
                break;
        }
        this.pack();
        this.repaint();
    }

    public static void setProxyInformation(Connection connection) {
        ProxyType configProxyType = Connection.ProxyType.valueByText(getCachedValue(KEY_PROXY_TYPE, "None"));
        if (configProxyType == null) {
            return;
        }

        if (!NETWORK_ENABLE_PROXY_SUPPORT) {
            connection.setProxyType(ProxyType.NONE);
            return;
        }

        connection.setProxyType(configProxyType);
        if (configProxyType != ProxyType.NONE) {
            String host = getCachedValue(KEY_PROXY_ADDRESS, "");
            String port = getCachedValue(KEY_PROXY_PORT, "");
            if (!host.isEmpty() && !port.isEmpty()) {
                connection.setProxyHost(host);
                connection.setProxyPort(Integer.parseInt(port));
                String username = getCachedValue(KEY_PROXY_USERNAME, "");
                connection.setProxyUsername(username);
                if (getCachedValue(KEY_PROXY_REMEMBER, "false").equals("true")) {
                    String password = getCachedValue(KEY_PROXY_PSWD, "");
                    connection.setProxyPassword(password);
                }
            } else {
                logger.warn("host or\\and port are empty: host=" + host + ", port=" + port);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int param = 0;
        if (args.length > 0) {
            String param1 = args[0];
            if (param1.equals(OPEN_PHASES_TAB)) {
                param = 5;
            }
            if (param1.equals(OPEN_CONNECTION_TAB)) {
                param = 5 + 3;
            }
        }
        final int openedTab = param;
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        java.awt.EventQueue.invokeLater(() -> {
            if (!dialog.isVisible()) {
                Preferences prefs = MageFrame.getPreferences();

                // Main & Phases
                loadPhases(prefs);

                // Gui Size
                loadGuiSize(prefs);

                // Images
                loadImagesSettings(prefs);

                // Sounds
                loadSoundSettings(prefs);

                // Proxy
                loadProxySettings(prefs);

                // Controls
                loadControlSettings(prefs);

                // Themes
                loadThemeSettings(prefs);

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
        });
    }

    private static void loadPhases(Preferences prefs) {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        load(prefs, dialog.tooltipDelay, KEY_SHOW_TOOLTIPS_DELAY, "300");
        load(prefs, dialog.showCardName, KEY_SHOW_CARD_NAMES, "true");
        load(prefs, dialog.showFullImagePath, KEY_SHOW_FULL_IMAGE_PATH, "true");
        load(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true");
        load(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true");
        load(prefs, dialog.displayLifeOnAvatar, KEY_DISPLAY_LIVE_ON_AVATAR, "true");
        load(prefs, dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true");
        load(prefs, dialog.cbConfirmEmptyManaPool, KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true");
        load(prefs, dialog.cbAskMoveToGraveOrder, KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "true");

        load(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true");
        load(prefs, dialog.cbDraftLogAutoSave, KEY_DRAFT_LOG_AUTO_SAVE, "true");
        load(prefs, dialog.cbLimitedDeckAutoSave, KEY_LIMITED_DECK_AUTO_SAVE, "true");
        load(prefs, dialog.cbGameJsonLogAutoSave, KEY_JSON_GAME_LOG_AUTO_SAVE, "true", "false");

        String autoTargetParam;
        try {
            autoTargetParam = MageFrame.getPreferences().get(KEY_AUTO_TARGET_LEVEL, "1");
            int autoTargetMode = Integer.parseInt(autoTargetParam);
            dialog.cbTargetAutoChooseLevel.setSelectedIndex(autoTargetMode);
        } catch (Throwable e) {
            autoTargetParam = "";
            dialog.cbTargetAutoChooseLevel.setSelectedIndex(AUTO_TARGET_NON_FEEL_BAD);
            logger.error("Can't Parse and setup param " + KEY_AUTO_TARGET_LEVEL + " = " + autoTargetParam, e);
        }

        load(prefs, dialog.checkBoxUpkeepYou, UPKEEP_YOU, "on", "on");
        load(prefs, dialog.checkBoxDrawYou, DRAW_YOU, "on", "on");
        load(prefs, dialog.checkBoxMainYou, MAIN_YOU, "on", "on");
        load(prefs, dialog.checkBoxBeforeCYou, BEFORE_COMBAT_YOU, "on", "on");
        load(prefs, dialog.checkBoxEndOfCYou, END_OF_COMBAT_YOU, "on", "on");
        load(prefs, dialog.checkBoxMain2You, MAIN_TWO_YOU, "on", "on");
        load(prefs, dialog.checkBoxEndTurnYou, END_OF_TURN_YOU, "on", "on");

        load(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxEndOfCOthers, END_OF_COMBAT_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxMain2Others, MAIN_TWO_OTHERS, "on", "on");
        load(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS, "on", "on");

        load(prefs, dialog.cbStopAttack, KEY_STOP_ATTACK, "true", "true");
        load(prefs, dialog.cbStopBlockWithAny, KEY_STOP_BLOCK_WITH_ANY, "true", "true");
        load(prefs, dialog.cbStopBlockWithZero, KEY_STOP_BLOCK_WITH_ZERO, "true", "false");
        load(prefs, dialog.cbStopOnAllMain, KEY_STOP_ALL_MAIN_PHASES, "true", "false");
        load(prefs, dialog.cbStopOnAllEnd, KEY_STOP_ALL_END_PHASES, "true", "false");
        load(prefs, dialog.cbStopOnNewStackObjects, KEY_STOP_NEW_STACK_OBJECTS, "true", "false");
        load(prefs, dialog.cbPassPriorityCast, KEY_PASS_PRIORITY_CAST, "true", "false");
        load(prefs, dialog.cbPassPriorityActivation, KEY_PASS_PRIORITY_ACTIVATION, "true", "false");
        load(prefs, dialog.cbAutoOrderTrigger, KEY_AUTO_ORDER_TRIGGER, "true", "true");
        load(prefs, dialog.cbUseSameSettingsForReplacementEffect, KEY_USE_SAME_SETTINGS_FOR_SAME_REPLACEMENT_EFFECTS, "true", "true");
    }

    private static void loadGuiSize(Preferences prefs) {
        isLoadingSizes = true;
        try {
            getInstance().sizeSettings.values().forEach(setting -> {
                load(prefs, setting.slider, setting.key, setting.defaultValue.toString());
            });
        } finally {
            isLoadingSizes = false;
        }
    }

    private static void loadImagesSettings(Preferences prefs) {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
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
        load(prefs, dialog.cbSaveToZipFiles, KEY_CARD_IMAGES_SAVE_TO_ZIP, "true", "false");
        dialog.cbPreferredImageLanguage.setSelectedItem(MageFrame.getPreferences().get(KEY_CARD_IMAGES_PREF_LANGUAGE, CardLanguage.ENGLISH.getCode()));

        // rendering settings
        dialog.cbCardRenderImageFallback.setSelectedItem(MageFrame.getPreferences().get(KEY_CARD_RENDERING_IMAGE_MODE, CardRenderMode.MTGO.toString()));
        load(prefs, dialog.cbCardRenderIconsForAbilities, KEY_CARD_RENDERING_ICONS_FOR_ABILITIES, "true", "true");
        load(prefs, dialog.cbCardRenderIconsForPlayable, KEY_CARD_RENDERING_ICONS_FOR_PLAYABLE, "true", "true");
        load(prefs, dialog.cbCardRenderHideSetSymbol, KEY_CARD_RENDERING_SET_SYMBOL, "true");
        load(prefs, dialog.cbCardRenderShowReminderText, KEY_CARD_RENDERING_REMINDER_TEXT, "true");
        load(prefs, dialog.cbCardRenderShowAbilityTextOverlay, KEY_CARD_RENDERING_ABILITY_TEXT_OVERLAY, "true");

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
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        dialog.cbEnableGameSounds.setSelected(prefs.get(KEY_SOUNDS_GAME_ON, "true").equals("true"));
        dialog.cbEnableDraftSounds.setSelected(prefs.get(KEY_SOUNDS_DRAFT_ON, "true").equals("true"));
        dialog.cbEnableSkipButtonsSounds.setSelected(prefs.get(KEY_SOUNDS_SKIP_BUTTONS_ON, "true").equals("true"));
        dialog.cbEnableOtherSounds.setSelected(prefs.get(KEY_SOUNDS_OTHER_ON, "true").equals("true"));

        // Match music
        dialog.cbEnableBattlefieldBGM.setSelected(prefs.get(KEY_SOUNDS_MATCH_MUSIC_ON, SOUNDS_MATCH_MUSIC_ENABLE_BY_DEFAULT).equals("true"));
        dialog.txtBattlefieldIBGMPath.setEnabled(dialog.cbEnableBattlefieldBGM.isSelected());
        dialog.btnBattlefieldBGMBrowse.setEnabled(dialog.cbEnableBattlefieldBGM.isSelected());
        // load and save the path always, so you can reactivate music without selecting path again
        String path = prefs.get(KEY_SOUNDS_MATCH_MUSIC_PATH, "");
        dialog.txtBattlefieldIBGMPath.setText(path);

        updateCache(KEY_SOUNDS_MATCH_MUSIC_PATH, path);
    }

    private static void loadProxySettings(Preferences prefs) {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        dialog.cbProxyType.setSelectedItem(Connection.ProxyType.valueOf(MageFrame.getPreferences().get(KEY_PROXY_TYPE, "NONE").toUpperCase(Locale.ENGLISH)));

        load(prefs, dialog.txtProxyServer, KEY_PROXY_ADDRESS, ClientDefaultSettings.serverName);
        load(prefs, dialog.txtProxyPort, KEY_PROXY_PORT, Integer.toString(ClientDefaultSettings.port));
        load(prefs, dialog.txtProxyUserName, KEY_PROXY_USERNAME, "");
        load(prefs, dialog.rememberPswd, KEY_PROXY_REMEMBER, "true", "false");
        if (dialog.rememberPswd.isSelected()) {
            load(prefs, dialog.txtPasswordField, KEY_PROXY_PSWD, "");
        }
    }

    private static void loadControlSettings(Preferences prefs) {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        load(prefs, dialog.keyConfirm);
        load(prefs, dialog.keyCancelSkip);
        load(prefs, dialog.keyNextTurn);
        load(prefs, dialog.keyEndStep);
        load(prefs, dialog.keySkipStep);
        load(prefs, dialog.keyMainStep);
        load(prefs, dialog.keyYourTurn);
        load(prefs, dialog.keySkipStack);
        load(prefs, dialog.keyPriorEnd);
        load(prefs, dialog.keyToggleRecordMacro);
        load(prefs, dialog.keySwitchChat);
    }

    private static void loadThemeSettings(Preferences prefs) {
        isLoadingTheme = true;
        try {
            getInstance().cbTheme.setSelectedItem(PreferencesDialog.getCurrentTheme());
        } finally {
            isLoadingTheme = false;
        }
    }

    private static void loadSelectedAvatar(Preferences prefs) {
        getSelectedAvatar();
        getInstance().setSelectedId(selectedAvatarId);
    }

    public static int getSelectedAvatar() {
        try {
            selectedAvatarId = Integer.parseInt(MageFrame.getPreferences().get(KEY_AVATAR, String.valueOf(DEFAULT_AVATAR_ID)));
        } catch (NumberFormatException n) {
            selectedAvatarId = DEFAULT_AVATAR_ID;
        } finally {
            if (selectedAvatarId < MIN_AVATAR_ID || selectedAvatarId > MAX_AVATAR_ID) {
                selectedAvatarId = DEFAULT_AVATAR_ID;
            }
        }
        return selectedAvatarId;
    }

    public static UserSkipPrioritySteps getUserSkipPrioritySteps() {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
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
        userSkipPrioritySteps.setStopOnDeclareBlockersWithAnyPermanents(dialog.cbStopBlockWithAny.isSelected());
        userSkipPrioritySteps.setStopOnDeclareBlockersWithZeroPermanents(dialog.cbStopBlockWithZero.isSelected());
        userSkipPrioritySteps.setStopOnAllEndPhases(dialog.cbStopOnAllEnd.isSelected());
        userSkipPrioritySteps.setStopOnAllMainPhases(dialog.cbStopOnAllMain.isSelected());
        userSkipPrioritySteps.setStopOnStackNewObjects(dialog.cbStopOnNewStackObjects.isSelected());

        return userSkipPrioritySteps;
    }

    private static void openTab(int index) {
        try {
            if (index > 0) {
                getInstance().tabsPanel.setSelectedIndex(index);
            }
        } catch (Exception e) {
            logger.error("Error during open tab", e);
        }
    }

    private static void saveImagesPath(Preferences prefs) {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
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
        String path = getInstance().txtBattlefieldIBGMPath.getText();
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
        updateCache(propName, prop);
    }

    private static void load(Preferences prefs, JTextField field, String propName, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        field.setText(prop);
    }

    private static void load(Preferences prefs, JSlider field, String propName, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        int value;
        try {
            value = Integer.parseInt(prop);
        } catch (NumberFormatException e) {
            // It's OK to ignore "e" here because returning a default value is the documented behaviour on invalid input.
            value = Integer.parseInt(defaultValue);
        }
        field.setValue(value);
    }

    private static void load(Preferences prefs, JComboBox field, String propName, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        field.setSelectedItem(prop);
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName) {
        load(prefs, checkBox, propName, PHASE_ON);
    }

    private static void load(Preferences prefs, KeyBindButton button) {
        String key = button.getKey();

        int code = prefs.getInt(key, getDefaultControlKey(key));
        int mod = prefs.getInt(key + KEY_CONTROL_MODIFIER_POSTFIX, getDefaultControlMofier(key));
        button.setKeyCode(code);
        button.setModifierCode(mod);
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName) {
        save(prefs, checkBox, propName, PHASE_ON, PHASE_OFF);
    }

    public static void setPrefValue(String key, boolean value) {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        switch (key) {
            case KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS:
                dialog.cbAllowRequestToShowHandCards.setSelected(value);
                save(MageFrame.getPreferences(), dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true", "false");
                break;
        }
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName, String yesValue, String noValue) {
        prefs.put(propName, checkBox.isSelected() ? yesValue : noValue);
        updateCache(propName, checkBox.isSelected() ? yesValue : noValue);
    }

    private static void save(Preferences prefs, JSlider slider, String propName) {
        prefs.put(propName, Integer.toString(slider.getValue()));
        updateCache(propName, Integer.toString(slider.getValue()));
    }

    private static void save(Preferences prefs, JTextField textField, String propName) {
        prefs.put(propName, textField.getText().trim());
        updateCache(propName, textField.getText().trim());
    }

    private static void save(Preferences prefs, JComboBox comboBox, String propName) {
        prefs.put(propName, comboBox.getSelectedItem().toString().trim());
        updateCache(propName, comboBox.getSelectedItem().toString().trim());
    }

    private static void save(Preferences prefs, KeyBindButton button) {
        int code = button.getKeyCode();
        int mod = button.getModifierCode();

        String key = button.getKey();

        prefs.putInt(key, code);
        updateCache(key, Integer.toString(code));

        prefs.putInt(key + KEY_CONTROL_MODIFIER_POSTFIX, mod);
        updateCache(key + KEY_CONTROL_MODIFIER_POSTFIX, Integer.toString(mod));
    }

    public void reset() {
        tabsPanel.setSelectedIndex(0);
    }

    public static int getCachedValue(String key, int def) {
        String stringValue = getCachedValue(key, String.valueOf(def));
        int value;
        try {
            value = Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            // It's OK to ignore "e" here because returning a default value is the documented behaviour on invalid input.
            value = def;
        }
        return value;
    }

    public static String getCachedValue(String key, String def) {
        final Lock r = cacheLock.readLock();
        r.lock();
        try {
            if (CACHE.containsKey(key)) {
                return CACHE.get(key);
            }
        } finally {
            r.unlock();
        }

        final Lock w = cacheLock.writeLock();
        w.lock();
        try {
            String value = MageFrame.getPreferences().get(key, def);
            if (value == null) {
                value = def;
            }
            CACHE.put(key, value);
            return value;
        } finally {
            w.unlock();
        }
    }

    public static int getRenderMode() {
        return CardRenderMode.fromString(getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_IMAGE_MODE, CardRenderMode.MTGO.toString())).getId();
    }

    public static boolean getRenderIconsForAbilities() {
        return (getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_ICONS_FOR_ABILITIES, "true").equals("true"));
    }

    public static boolean getRenderIconsForPlayable() {
        return (getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_ICONS_FOR_PLAYABLE, "true").equals("true"));
    }

    private static int getDefaultControlMofier(String key) {
        switch (key) {
            default:
                return 0;
        }
    }

    private static int getDefaultControlKey(String key) {
        switch (key) {
            case KEY_CONTROL_CONFIRM:
                return KeyEvent.VK_F2;
            case KEY_CONTROL_CANCEL_SKIP:
                return KeyEvent.VK_F3;
            case KEY_CONTROL_NEXT_TURN:
                return KeyEvent.VK_F4;
            case KEY_CONTROL_END_STEP:
                return KeyEvent.VK_F5;
            case KEY_CONTROL_SKIP_STEP:
                return KeyEvent.VK_F6;
            case KEY_CONTROL_MAIN_STEP:
                return KeyEvent.VK_F7;
            case KEY_CONTROL_TOGGLE_MACRO:
                return KeyEvent.VK_F8;
            case KEY_CONTROL_YOUR_TURN:
                return KeyEvent.VK_F9;
            case KEY_CONTROL_SKIP_STACK:
                return KeyEvent.VK_F10;
            case KEY_CONTROL_PRIOR_END:
                return KeyEvent.VK_F11;
            case KEY_CONTROL_SWITCH_CHAT:
                return KeyEvent.VK_F12;
            default:
                return 0;
        }
    }

    public static int getCurrentControlKey(String key) {
        return getCachedValue(key, getDefaultControlKey(key));
    }

    public static int getCurrentControlModifier(String key) {
        return getCachedValue(key + KEY_CONTROL_MODIFIER_POSTFIX, getDefaultControlMofier(key));
    }

    public static KeyStroke getCachedKeystroke(String key) {
        int code = getCachedValue(key, getDefaultControlKey(key));
        int mod = getCachedValue(key + KEY_CONTROL_MODIFIER_POSTFIX, getDefaultControlMofier(key));

        return KeyStroke.getKeyStroke(code, mod);
    }

    public static String getCachedKeyText(String key) {
        int code = getCachedValue(key, getDefaultControlKey(key));
        String codeStr = KeyEvent.getKeyText(code);

        int mod = getCachedValue(key + KEY_CONTROL_MODIFIER_POSTFIX, getDefaultControlMofier(key));
        String modStr = KeyEvent.getKeyModifiersText(mod);

        return (modStr.isEmpty() ? "" : modStr + " + ") + codeStr;
    }

    private static void updateCache(String key, String value) {
        CACHE.put(key, value);
    }

    public static void saveValue(String key, String value) {
        Preferences prefs = MageFrame.getPreferences();
        prefs.put(key, value);
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            logger.error("Can't save preferences " + key + " due " + e, e);
        }
        updateCache(key, value);
    }

    private void addAvatars() {
        try {
            addAvatar(jPanel10, 10, true, false);
            addAvatar(jPanel11, 11, false, false);
            addAvatar(jPanel12, 12, false, false);
            addAvatar(jPanel13, 13, false, false);
            addAvatar(jPanel14, 14, false, false);
            addAvatar(jPanel15, 15, false, false);
            addAvatar(jPanel16, 16, false, false);
            addAvatar(jPanel17, 17, false, false);
            addAvatar(jPanel18, 18, false, false);
            addAvatar(jPanel19, 19, false, false);
            addAvatar(jPanel20, 20, false, false);
            addAvatar(jPanel21, 21, false, false);
            addAvatar(jPanel22, 22, false, false);
            addAvatar(jPanel23, 23, false, false);
            addAvatar(jPanel24, 24, false, false);
            addAvatar(jPanel25, 25, false, false);
            addAvatar(jPanel26, 26, false, false);
            addAvatar(jPanel27, 27, false, false);
            addAvatar(jPanel28, 28, false, false);
            addAvatar(jPanel29, 29, false, false);
            addAvatar(jPanel30, 30, false, false);
            addAvatar(jPanel31, 31, false, false);
            addAvatar(jPanel32, 32, false, false);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setSelectedId(int id) {
        if (id >= MIN_AVATAR_ID && id <= MAX_AVATAR_ID) {
            for (JPanel panel : PANELS.values()) {
                panel.setBorder(BLACK_BORDER);
            }
            PreferencesDialog.selectedAvatarId = id;
            PANELS.get(PreferencesDialog.selectedAvatarId).setBorder(GREEN_BORDER);
        }
    }

    private void addAvatar(JPanel jPanel, final int id, boolean selected, boolean locked) {
        String path = "/avatars/" + id + ".jpg";
        PANELS.put(id, jPanel);
        Image image = ImageHelper.getImageFromResources(path);

        Rectangle r = new Rectangle(jPanel.getWidth() - 5, jPanel.getHeight() - 5);
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
                    if (!SwingUtilities.isLeftMouseButton(e)) {
                        return;
                    }
                    if (selectedAvatarId != id) {
                        setSelectedId(id);
                        SessionHandler.updatePreferencesForServer(getUserData());
                    }
                }
            });
        }
    }

    public static UserData getUserData() {
        if (selectedAvatarId == 0) {
            getSelectedAvatar();
        }
        String userStrId = System.getProperty("user.name");
        return new UserData(UserGroup.PLAYER,
                PreferencesDialog.selectedAvatarId,
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true").equals("true"),
                getUserSkipPrioritySteps(),
                MageFrame.getPreferences().get(KEY_CONNECT_FLAG, "world"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "false").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PASS_PRIORITY_CAST, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PASS_PRIORITY_ACTIVATION, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_AUTO_ORDER_TRIGGER, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_AUTO_TARGET_LEVEL, 1),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_USE_SAME_SETTINGS_FOR_SAME_REPLACEMENT_EFFECTS, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                userStrId
        );
    }

    public List<KeyBindButton> getKeybindButtons() {
        return Arrays.asList(
                keyCancelSkip,
                keyConfirm,
                keyEndStep,
                keyMainStep,
                keyNextTurn,
                keyPriorEnd,
                keySkipStack,
                keySkipStep,
                keyYourTurn,
                keyToggleRecordMacro,
                keySwitchChat
        );
    }

    public static DefaultSizeSettings getDefaultSizeSettings() {
        return defaultSizeSettings;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane avatarPane;
    private javax.swing.JPanel avatarPanel;
    private javax.swing.JButton btnBattlefieldBGMBrowse;
    private javax.swing.JButton btnBrowseBackgroundImage;
    private javax.swing.JButton btnBrowseBattlefieldImage;
    private javax.swing.JButton btnBrowseImageLocation;
    private javax.swing.JButton bttnResetControls;
    private javax.swing.JButton buttonSizeDefault1;
    private javax.swing.JButton buttonSizeDefault2;
    private javax.swing.JButton buttonSizeDefault3;
    private javax.swing.JButton buttonSizeDefault4;
    private javax.swing.JButton buttonSizeDefault5;
    private javax.swing.JButton buttonSizeDefault6;
    private javax.swing.JCheckBox cbAllowRequestToShowHandCards;
    private javax.swing.JCheckBox cbAskMoveToGraveOrder;
    private javax.swing.JCheckBox cbAutoOrderTrigger;
    private javax.swing.JCheckBox cbCardRenderHideSetSymbol;
    private javax.swing.JCheckBox cbCardRenderIconsForAbilities;
    private javax.swing.JCheckBox cbCardRenderIconsForPlayable;
    private javax.swing.JComboBox<String> cbCardRenderImageFallback;
    private javax.swing.JCheckBox cbCardRenderShowAbilityTextOverlay;
    private javax.swing.JCheckBox cbCardRenderShowReminderText;
    private javax.swing.JCheckBox cbConfirmEmptyManaPool;
    private javax.swing.JCheckBox cbDraftLogAutoSave;
    private javax.swing.JCheckBox cbEnableBattlefieldBGM;
    private javax.swing.JCheckBox cbEnableDraftSounds;
    private javax.swing.JCheckBox cbEnableGameSounds;
    private javax.swing.JCheckBox cbEnableOtherSounds;
    private javax.swing.JCheckBox cbEnableSkipButtonsSounds;
    private javax.swing.JCheckBox cbGameJsonLogAutoSave;
    private javax.swing.JCheckBox cbGameLogAutoSave;
    private javax.swing.JCheckBox cbLimitedDeckAutoSave;
    private javax.swing.JCheckBox cbPassPriorityActivation;
    private javax.swing.JCheckBox cbPassPriorityCast;
    private javax.swing.JComboBox<String> cbPreferredImageLanguage;
    private javax.swing.JComboBox<ProxyType> cbProxyType;
    private javax.swing.JCheckBox cbSaveToZipFiles;
    private javax.swing.JCheckBox cbStopAttack;
    private javax.swing.JCheckBox cbStopBlockWithAny;
    private javax.swing.JCheckBox cbStopBlockWithZero;
    private javax.swing.JCheckBox cbStopOnAllEnd;
    private javax.swing.JCheckBox cbStopOnAllMain;
    private javax.swing.JCheckBox cbStopOnNewStackObjects;
    private javax.swing.JComboBox<String> cbTargetAutoChooseLevel;
    private javax.swing.JComboBox<ThemeType> cbTheme;
    private javax.swing.JCheckBox cbUseDefaultBackground;
    private javax.swing.JCheckBox cbUseDefaultBattleImage;
    private javax.swing.JCheckBox cbUseDefaultImageFolder;
    private javax.swing.JCheckBox cbUseRandomBattleImage;
    private javax.swing.JCheckBox cbUseSameSettingsForReplacementEffect;
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
    private javax.swing.JPanel connection_Proxy;
    private javax.swing.JLabel controlsDescriptionLabel;
    private javax.swing.JCheckBox displayLifeOnAvatar;
    private javax.swing.JTextField editSize1;
    private javax.swing.JTextField editSize10;
    private javax.swing.JTextField editSize11;
    private javax.swing.JTextField editSize12;
    private javax.swing.JTextField editSize13;
    private javax.swing.JTextField editSize14;
    private javax.swing.JTextField editSize2;
    private javax.swing.JTextField editSize3;
    private javax.swing.JTextField editSize4;
    private javax.swing.JTextField editSize5;
    private javax.swing.JTextField editSize6;
    private javax.swing.JTextField editSize7;
    private javax.swing.JTextField editSize8;
    private javax.swing.JTextField editSize9;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel hintSize1;
    private javax.swing.JLabel hintSize10;
    private javax.swing.JLabel hintSize11;
    private javax.swing.JLabel hintSize12;
    private javax.swing.JLabel hintSize13;
    private javax.swing.JLabel hintSize14;
    private javax.swing.JLabel hintSize2;
    private javax.swing.JLabel hintSize3;
    private javax.swing.JLabel hintSize4;
    private javax.swing.JLabel hintSize5;
    private javax.swing.JLabel hintSize6;
    private javax.swing.JLabel hintSize7;
    private javax.swing.JLabel hintSize8;
    private javax.swing.JLabel hintSize9;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JSeparator jSeparator1;
    private mage.client.components.KeyBindButton keyCancelSkip;
    private mage.client.components.KeyBindButton keyConfirm;
    private mage.client.components.KeyBindButton keyEndStep;
    private mage.client.components.KeyBindButton keyMainStep;
    private mage.client.components.KeyBindButton keyNextTurn;
    private mage.client.components.KeyBindButton keyPriorEnd;
    private mage.client.components.KeyBindButton keySkipStack;
    private mage.client.components.KeyBindButton keySkipStep;
    private mage.client.components.KeyBindButton keySwitchChat;
    private mage.client.components.KeyBindButton keyToggleRecordMacro;
    private mage.client.components.KeyBindButton keyYourTurn;
    private javax.swing.JLabel labelCancel;
    private javax.swing.JLabel labelConfirm;
    private javax.swing.JLabel labelEndStep;
    private javax.swing.JLabel labelMainStep;
    private javax.swing.JLabel labelNextTurn;
    private javax.swing.JLabel labelPreferredImageLanguage;
    private javax.swing.JLabel labelPriorEnd;
    private javax.swing.JLabel labelRenderMode;
    private javax.swing.JLabel labelSizeGroup1;
    private javax.swing.JLabel labelSizeGroup2;
    private javax.swing.JLabel labelSkipStep;
    private javax.swing.JLabel labelSwitchChat;
    private javax.swing.JLabel labelToggleRecordMacro;
    private javax.swing.JLabel labelYourTurn;
    private javax.swing.JLabel lbSelectLabel;
    private javax.swing.JLabel lbThemeHint;
    private javax.swing.JLabel lblProxyPassword;
    private javax.swing.JLabel lblProxyPort;
    private javax.swing.JLabel lblProxyServer;
    private javax.swing.JLabel lblProxyUserName;
    private javax.swing.JLabel lblTargetAutoChoose;
    private javax.swing.JLabel lebelSkip;
    private javax.swing.JPanel main_card;
    private javax.swing.JPanel main_game;
    private javax.swing.JPanel main_gamelog;
    private javax.swing.JCheckBox nonLandPermanentsInOnePile;
    private javax.swing.JPanel panelBackgroundImages;
    private javax.swing.JPanel panelCardImages;
    private javax.swing.JPanel panelCardStyles;
    private javax.swing.JPanel panelSize1;
    private javax.swing.JPanel panelSize10;
    private javax.swing.JPanel panelSize11;
    private javax.swing.JPanel panelSize12;
    private javax.swing.JPanel panelSize13;
    private javax.swing.JPanel panelSize14;
    private javax.swing.JPanel panelSize2;
    private javax.swing.JPanel panelSize3;
    private javax.swing.JPanel panelSize4;
    private javax.swing.JPanel panelSize5;
    private javax.swing.JPanel panelSize6;
    private javax.swing.JPanel panelSize7;
    private javax.swing.JPanel panelSize8;
    private javax.swing.JPanel panelSize9;
    private javax.swing.JPanel panelSizeDefaultSettings;
    private javax.swing.JPanel panelSizeDetailedSettings;
    private javax.swing.JPanel phases_stopSettings;
    private javax.swing.JPanel pnlProxy;
    private javax.swing.JCheckBox rememberPswd;
    private javax.swing.JButton sampleSize1;
    private javax.swing.JButton sampleSize10;
    private javax.swing.JButton sampleSize11;
    private javax.swing.JButton sampleSize12;
    private javax.swing.JButton sampleSize13;
    private javax.swing.JButton sampleSize14;
    private javax.swing.JButton sampleSize2;
    private javax.swing.JButton sampleSize3;
    private javax.swing.JButton sampleSize4;
    private javax.swing.JButton sampleSize5;
    private javax.swing.JButton sampleSize6;
    private javax.swing.JButton sampleSize7;
    private javax.swing.JButton sampleSize8;
    private javax.swing.JButton sampleSize9;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox showCardName;
    private javax.swing.JCheckBox showFullImagePath;
    private javax.swing.JCheckBox showPlayerNamesPermanently;
    private javax.swing.JSlider sliderSize1;
    private javax.swing.JSlider sliderSize10;
    private javax.swing.JSlider sliderSize11;
    private javax.swing.JSlider sliderSize12;
    private javax.swing.JSlider sliderSize13;
    private javax.swing.JSlider sliderSize14;
    private javax.swing.JSlider sliderSize2;
    private javax.swing.JSlider sliderSize3;
    private javax.swing.JSlider sliderSize4;
    private javax.swing.JSlider sliderSize5;
    private javax.swing.JSlider sliderSize6;
    private javax.swing.JSlider sliderSize7;
    private javax.swing.JSlider sliderSize8;
    private javax.swing.JSlider sliderSize9;
    private javax.swing.JPanel sounds_backgroundMusic;
    private javax.swing.JPanel sounds_clips;
    private javax.swing.JPanel tabAvatars;
    private javax.swing.JPanel tabConnection;
    private javax.swing.JPanel tabControls;
    private javax.swing.JPanel tabGuiImages;
    private javax.swing.JPanel tabGuiSize;
    private javax.swing.JPanel tabGuiTheme;
    private javax.swing.JPanel tabMain;
    private javax.swing.JPanel tabPhases;
    private javax.swing.JPanel tabSounds;
    private javax.swing.JTabbedPane tabsPanel;
    private javax.swing.JPanel themesCategory;
    private javax.swing.JSlider tooltipDelay;
    private javax.swing.JLabel tooltipDelayLabel;
    private javax.swing.JTextField txtBackgroundImagePath;
    private javax.swing.JTextField txtBattlefieldIBGMPath;
    private javax.swing.JTextField txtBattlefieldImagePath;
    private javax.swing.JTextField txtImageFolderPath;
    private javax.swing.JPasswordField txtPasswordField;
    private javax.swing.JTextField txtProxyPort;
    private javax.swing.JTextField txtProxyServer;
    private javax.swing.JTextField txtProxyUserName;
    // End of variables declaration//GEN-END:variables
}
