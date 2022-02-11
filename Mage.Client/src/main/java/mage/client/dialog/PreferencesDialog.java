package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.components.KeyBindButton;
import mage.client.themes.ThemeType;
import mage.client.util.CardLanguage;
import mage.client.util.ClientDefaultSettings;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.players.net.UserSkipPrioritySteps;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static mage.client.constants.Constants.BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_MULTICOLOR;
import static mage.constants.Constants.*;

/**
 * @author nantuko, JayDi85, leemi
 */
public class PreferencesDialog extends javax.swing.JDialog {

    private static final Logger logger = Logger.getLogger(PreferencesDialog.class);

    public static final String KEY_SHOW_TOOLTIPS_DELAY = "showTooltipsDelay";
    public static final String KEY_SHOW_CARD_NAMES = "showCardNames";
    public static final String KEY_SHOW_FULL_IMAGE_PATH = "showFullImagePath";
    public static final String KEY_PERMANENTS_IN_ONE_PILE = "nonLandPermanentsInOnePile";
    public static final String KEY_SHOW_PLAYER_NAMES_PERMANENTLY = "showPlayerNamesPermanently";
    public static final String KEY_DISPLAY_LIVE_ON_AVATAR = "displayLiveOnAvatar";
    public static final String KEY_SHOW_ABILITY_PICKER_FORCED = "showAbilityPicker";
    public static final String KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS = "gameAllowRequestShowHandCards";
    public static final String KEY_GAME_SHOW_STORM_COUNTER = "gameShowStormCounter";
    public static final String KEY_GAME_CONFIRM_EMPTY_MANA_POOL = "gameConfirmEmptyManaPool";
    public static final String KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER = "gameAskMoveToGraveORder";
    public static final String KEY_GAME_USE_PROFANITY_FILTER = "gameUseProfanityFilter";

    public static final String KEY_BATTLEFIELD_FEEDBACK_COLORIZING_MODE = "battlefieldFeedbackColorizingMode";

    public static final String KEY_GUI_TABLE_FONT_SIZE = "guiTableFontSize";
    public static final String KEY_GUI_CHAT_FONT_SIZE = "guiChatFontSize";
    public static final String KEY_GUI_CARD_HAND_SIZE = "guiCardHandSize";
    public static final String KEY_GUI_CARD_EDITOR_SIZE = "guiCardEditorSize";
    public static final String KEY_GUI_CARD_OFFSET_SIZE = "guiCardOffsetSize";
    public static final String KEY_GUI_ENLARGED_IMAGE_SIZE = "guiEnlargedImageSize";

    public static final String KEY_GUI_STACK_WIDTH = "guiStackWidth";
    public static final String KEY_GUI_TOOLTIP_SIZE = "guiTooltipSize";
    public static final String KEY_GUI_DIALOG_FONT_SIZE = "guiDialogFontSize";
    public static final String KEY_GUI_FEEDBACK_AREA_SIZE = "guiFeedbackAreaSize";
    public static final String KEY_GUI_CARD_OTHER_ZONES_SIZE = "guiCardOtherZonesSize";
    public static final String KEY_GUI_CARD_BATTLEFIELD_MIN_SIZE = "guiCardBattlefieldMinSize";
    public static final String KEY_GUI_CARD_BATTLEFIELD_MAX_SIZE = "guiCardBattlefieldMaxSize";

    public static final String KEY_GAME_LOG_SHOW_TURN_INFO = "gameLogShowTurnInfo";
    public static final String KEY_GAME_LOG_AUTO_SAVE = "gameLogAutoSave";
    public static final String KEY_DRAFT_LOG_AUTO_SAVE = "draftLogAutoSave";
    public static final String KEY_JSON_GAME_LOG_AUTO_SAVE = "gameLogJsonAutoSave";

    public static final String KEY_CARD_IMAGES_USE_DEFAULT = "cardImagesUseDefault";
    public static final String KEY_CARD_IMAGES_PATH = "cardImagesPath";
    public static final String KEY_CARD_IMAGES_THREADS = "cardImagesThreads";
    public static final String KEY_CARD_IMAGES_THREADS_DEFAULT = "3";
    public static final String KEY_CARD_IMAGES_SAVE_TO_ZIP = "cardImagesSaveToZip";
    public static final String KEY_CARD_IMAGES_PREF_LANGUAGE = "cardImagesPreferredImageLaguage";

    public static final String KEY_CARD_RENDERING_FALLBACK = "cardRenderingFallback";
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
    public static final String KEY_SOUNDS_MATCH_MUSIC_PATH = "soundsMatchMusicPath";

    public static final String KEY_BIG_CARD_TOGGLED = "bigCardToggled";

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
    public static final String KEY_MAGE_PANEL_LAST_SIZE = "gamepanelLastSize";

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
    public static final String KEY_NEW_TABLE_SPECTATORS_ALLOWED = "newTableSpectatorsAllowed";
    public static final String KEY_NEW_TABLE_PLANECHASE = "newTablePlaneChase";
    public static final String KEY_NEW_TABLE_NUMBER_OF_FREE_MULLIGANS = "newTableNumberOfFreeMulligans";
    public static final String KEY_NEW_TABLE_MULLIGAN_TYPE = "newTableMulliganType";
    public static final String KEY_NEW_TABLE_DECK_FILE = "newTableDeckFile";
    public static final String KEY_NEW_TABLE_RANGE = "newTableRange";
    public static final String KEY_NEW_TABLE_ATTACK_OPTION = "newTableAttackOption";
    public static final String KEY_NEW_TABLE_SKILL_LEVEL = "newTableSkillLevel";
    public static final String KEY_NEW_TABLE_NUMBER_PLAYERS = "newTableNumberPlayers";
    public static final String KEY_NEW_TABLE_PLAYER_TYPES = "newTablePlayerTypes";
    public static final String KEY_NEW_TABLE_QUIT_RATIO = "newTableQuitRatio";
    public static final String KEY_NEW_TABLE_MINIMUM_RATING = "newTableMinimumRating";
    public static final String KEY_NEW_TABLE_RATED = "newTableRated";
    public static final String KEY_NEW_TABLE_EDH_POWER_LEVEL = "newTableEdhPowerLevel";

    // pref setting for new tournament dialog
    public static final String KEY_NEW_TOURNAMENT_NAME = "newTournamentName";
    public static final String KEY_NEW_TOURNAMENT_PASSWORD = "newTournamentPassword";
    public static final String KEY_NEW_TOURNAMENT_TIME_LIMIT = "newTournamentTimeLimit";
    public static final String KEY_NEW_TOURNAMENT_CONSTR_TIME = "newTournamentConstructionTime";
    public static final String KEY_NEW_TOURNAMENT_TYPE = "newTournamentType";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS = "newTournamentNumberOfFreeMulligans";
    public static final String KEY_NEW_TOURNAMENT_MULLIGUN_TYPE = "newTournamentMulliganType";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_WINS = "newTournamentNumberOfWins";
    public static final String KEY_NEW_TOURNAMENT_PACKS_SEALED = "newTournamentPacksSealed";
    public static final String KEY_NEW_TOURNAMENT_PACKS_DRAFT = "newTournamentPacksDraft";
    public static final String KEY_NEW_TOURNAMENT_PACKS_RANDOM_DRAFT = "newTournamentPacksRandomDraft";
    public static final String KEY_NEW_TOURNAMENT_PLAYERS_SEALED = "newTournamentPlayersSealed";
    public static final String KEY_NEW_TOURNAMENT_PLAYERS_DRAFT = "newTournamentPlayersDraft";
    public static final String KEY_NEW_TOURNAMENT_DRAFT_TIMING = "newTournamentDraftTiming";
    public static final String KEY_NEW_TOURNAMENT_ALLOW_SPECTATORS = "newTournamentAllowSpectators";
    public static final String KEY_NEW_TOURNAMENT_PLANE_CHASE = "newTournamentPlaneChase";
    public static final String KEY_NEW_TOURNAMENT_ALLOW_ROLLBACKS = "newTournamentAllowRollbacks";
    public static final String KEY_NEW_TOURNAMENT_DECK_FILE = "newTournamentDeckFile";
    public static final String KEY_NEW_TOURNAMENT_QUIT_RATIO = "newTournamentQuitRatio";
    public static final String KEY_NEW_TOURNAMENT_MINIMUM_RATING = "newTournamentMinimumRating";
    public static final String KEY_NEW_TOURNAMENT_RATED = "newTournamentRated";

    // pref setting for deck generator
    public static final String KEY_NEW_DECK_GENERATOR_DECK_SIZE = "newDeckGeneratorDeckSize";
    public static final String KEY_NEW_DECK_GENERATOR_SET = "newDeckGeneratorSet";
    public static final String KEY_NEW_DECK_GENERATOR_SINGLETON = "newDeckGeneratorSingleton";
    public static final String KEY_NEW_DECK_GENERATOR_ARTIFACTS = "newDeckGeneratorArtifacts";
    public static final String KEY_NEW_DECK_GENERATOR_NON_BASIC_LANDS = "newDeckGeneratorNonBasicLands";
    public static final String KEY_NEW_DECK_GENERATOR_COLORLESS = "newDeckGeneratorColorless";
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
    public static final String KEY_CONNECTION_URL_SERVER_LIST = "connectionURLServerList";

    // news
    public static final String KEY_NEWS_PAGE_LAST_VERSION = "newsPageLastVersion";
    public static final String KEY_NEWS_PAGE_COOKIES = "newsPageCookies";

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

    private static final Map<String, String> CACHE = new HashMap<>();

    private static final Boolean UPDATE_CACHE_POLICY = Boolean.TRUE;

    public static final String OPEN_CONNECTION_TAB = "Open-Connection-Tab";
    public static final String OPEN_PHASES_TAB = "Open-Phases-Tab";

    public static final String PHASE_ON = "on";
    public static final String PHASE_OFF = "off";

    private static final Map<Integer, JPanel> PANELS = new HashMap<>();

    private static final Border GREEN_BORDER = BorderFactory.createLineBorder(Color.GREEN, 3);
    private static final Border BLACK_BORDER = BorderFactory.createLineBorder(Color.BLACK, 3);

    private static int selectedAvatarId;

    private static ThemeType currentTheme = null;
    
    private static boolean ignoreGUISizeSliderStateChangedEvent = false;
    
    public static ThemeType getCurrentTheme() {
        if (currentTheme == null) {
            currentTheme = ThemeType.valueByName(getCachedValue(KEY_THEME, "Default"));
            logger.info("Using GUI theme: " + currentTheme.getName());
            currentTheme.reload();
        }

        return currentTheme;
    }

    /**
     * Set and reload current theme. App need restart to apply all new settings.
     *
     * @param newTheme
     */
    public static void setCurrentTheme(ThemeType newTheme) {
        currentTheme = newTheme;
        currentTheme.reload();
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

    public static Integer getPrefDownloadThreads() {
        return Integer.parseInt(getCachedValue(KEY_CARD_IMAGES_THREADS, KEY_CARD_IMAGES_THREADS_DEFAULT));
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
        cbTheme.setModel(new DefaultComboBoxModel<>(ThemeType.values()));
        addAvatars();

        cbPreferredImageLanguage.setModel(new DefaultComboBoxModel<>(CardLanguage.toList()));
        cbNumberOfDownloadThreads.setModel(new DefaultComboBoxModel<>(new String[]{"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"}));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tabsPanel = new javax.swing.JTabbedPane();
        tabMain = new javax.swing.JPanel();
        main_gamelog = new javax.swing.JPanel();
        cbGameLogShowTurnInfo = new javax.swing.JCheckBox();
        cbGameLogAutoSave = new javax.swing.JCheckBox();
        cbDraftLogAutoSave = new javax.swing.JCheckBox();
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
        showAbilityPickerForced = new javax.swing.JCheckBox();
        cbAllowRequestToShowHandCards = new javax.swing.JCheckBox();
        cbShowStormCounter = new javax.swing.JCheckBox();
        cbConfirmEmptyManaPool = new javax.swing.JCheckBox();
        cbAskMoveToGraveOrder = new javax.swing.JCheckBox();
        main_battlefield = new javax.swing.JPanel();
        cbBattlefieldFeedbackColorizingMode = new javax.swing.JComboBox();
        lblBattlefieldFeedbackColorizingMode = new javax.swing.JLabel();
        tabGuiSize = new javax.swing.JPanel();
        guiSizeBasic = new javax.swing.JPanel();
        sliderFontSize = new javax.swing.JSlider();
        fontSizeLabel = new javax.swing.JLabel();
        sliderChatFontSize = new javax.swing.JSlider();
        chatFontSizeLabel = new javax.swing.JLabel();
        sliderDialogFont = new javax.swing.JSlider();
        labelDialogFont = new javax.swing.JLabel();
        sliderEditorCardSize = new javax.swing.JSlider();
        labelEditorCardSize = new javax.swing.JLabel();
        sliderEditorCardOffset = new javax.swing.JSlider();
        labelEditorCardOffset = new javax.swing.JLabel();
        sliderEnlargedImageSize = new javax.swing.JSlider();
        labelEnlargedImageSize = new javax.swing.JLabel();
        guiSizeGame = new javax.swing.JPanel();
        sliderCardSizeHand = new javax.swing.JSlider();
        labelCardSizeHand = new javax.swing.JLabel();
        sliderCardSizeOtherZones = new javax.swing.JSlider();
        labelCardSizeOtherZones = new javax.swing.JLabel();
        sliderCardSizeMinBattlefield = new javax.swing.JSlider();
        labelCardSizeMinBattlefield = new javax.swing.JLabel();
        sliderCardSizeMaxBattlefield = new javax.swing.JSlider();
        labelCardSizeMaxBattlefield = new javax.swing.JLabel();
        sliderStackWidth = new javax.swing.JSlider();
        labelStackWidth = new javax.swing.JLabel();
        sliderGameFeedbackArea = new javax.swing.JSlider();
        labelGameFeedback = new javax.swing.JLabel();
        sliderTooltipSize = new javax.swing.JSlider();
        labelTooltipSize = new javax.swing.JLabel();
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
        tabImages = new javax.swing.JPanel();
        panelCardImages = new javax.swing.JPanel();
        cbUseDefaultImageFolder = new javax.swing.JCheckBox();
        txtImageFolderPath = new javax.swing.JTextField();
        btnBrowseImageLocation = new javax.swing.JButton();
        cbSaveToZipFiles = new javax.swing.JCheckBox();
        cbPreferredImageLanguage = new javax.swing.JComboBox<>();
        labelPreferredImageLanguage = new javax.swing.JLabel();
        labelNumberOfDownloadThreads = new javax.swing.JLabel();
        cbNumberOfDownloadThreads = new javax.swing.JComboBox();
        labelHint1 = new javax.swing.JLabel();
        panelCardStyles = new javax.swing.JPanel();
        cbCardRenderImageFallback = new javax.swing.JCheckBox();
        cbCardRenderIconsForAbilities = new javax.swing.JCheckBox();
        cbCardRenderIconsForPlayable = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        cbCardRenderShowReminderText = new javax.swing.JCheckBox();
        cbCardRenderHideSetSymbol = new javax.swing.JCheckBox();
        cbCardRenderShowAbilityTextOverlay = new javax.swing.JCheckBox();
        panelBackgroundImages = new javax.swing.JPanel();
        cbUseDefaultBackground = new javax.swing.JCheckBox();
        txtBackgroundImagePath = new javax.swing.JTextField();
        btnBrowseBackgroundImage = new javax.swing.JButton();
        txtBattlefieldImagePath = new javax.swing.JTextField();
        btnBrowseBattlefieldImage = new javax.swing.JButton();
        cbUseDefaultBattleImage = new javax.swing.JCheckBox();
        cbUseRandomBattleImage = new javax.swing.JCheckBox();
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
        tabConnection = new javax.swing.JPanel();
        connection_servers = new javax.swing.JPanel();
        lblURLServerList = new javax.swing.JLabel();
        txtURLServerList = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        lblProxyType = new javax.swing.JLabel();
        cbProxyType = new javax.swing.JComboBox<>();
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
        tabThemes = new javax.swing.JPanel();
        themesCategory = new javax.swing.JPanel();
        lbSelectLabel = new javax.swing.JLabel();
        cbTheme = new javax.swing.JComboBox<>();
        lbThemeHint = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");

        tabsPanel.setMinimumSize(new java.awt.Dimension(532, 451));

        main_gamelog.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game log"));
        main_gamelog.setLayout(new javax.swing.BoxLayout(main_gamelog, javax.swing.BoxLayout.PAGE_AXIS));

        cbGameLogShowTurnInfo.setSelected(true);
        cbGameLogShowTurnInfo.setText("Show turn info in game logs (19:01 T2.DA: message)");
        cbGameLogShowTurnInfo.setToolTipText("Add turn number and step info after time in game logs");
        main_gamelog.add(cbGameLogShowTurnInfo);

        cbGameLogAutoSave.setSelected(true);
        cbGameLogAutoSave.setText("Save game logs     (to \"../Mage.Client/gamelogs/\" directory)");
        cbGameLogAutoSave.setToolTipText("The logs of all your games will be saved to the mentioned folder if this option is switched on.");
        main_gamelog.add(cbGameLogAutoSave);

        cbDraftLogAutoSave.setSelected(true);
        cbDraftLogAutoSave.setText("Save draft logs     (to \"../Mage.Client/gamelogs/\" directory)");
        cbDraftLogAutoSave.setToolTipText("The logs of all your games will be saved to the mentioned folder if this option is switched on.");
        main_gamelog.add(cbDraftLogAutoSave);

        cbGameJsonLogAutoSave.setText("Save JSON game logs     (to \"../Mage.Client/gamelogsJson/\" directory)");
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

        tooltipDelay.setMajorTickSpacing(1000);
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
                .add(main_cardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(main_cardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(tooltipDelayLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(tooltipDelay, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(main_cardLayout.createSequentialGroup()
                        .add(showCardName)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(showFullImagePath)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        displayLifeOnAvatar.setSelected(true);
        displayLifeOnAvatar.setText("Display life on avatar image");
        displayLifeOnAvatar.setToolTipText("Display the player's life over its avatar image.");
        displayLifeOnAvatar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        displayLifeOnAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayLifeOnAvatarActionPerformed(evt);
            }
        });

        showAbilityPickerForced.setSelected(true);
        showAbilityPickerForced.setText("Show ability picker for 1 available option (spells without costs, mdf/split side, adventure)");
        showAbilityPickerForced.setToolTipText("This prevents you from accidently activating abilities what you don't want (example: if you haven't mana to cast main side, but clicks on mdf card and play land instead)");
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

        org.jdesktop.layout.GroupLayout main_gameLayout = new org.jdesktop.layout.GroupLayout(main_game);
        main_game.setLayout(main_gameLayout);
        main_gameLayout.setHorizontalGroup(
            main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(main_gameLayout.createSequentialGroup()
                .addContainerGap()
                .add(main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(main_gameLayout.createSequentialGroup()
                        .add(main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(showPlayerNamesPermanently, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(nonLandPermanentsInOnePile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(cbConfirmEmptyManaPool, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(cbAllowRequestToShowHandCards, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(cbShowStormCounter, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(cbAskMoveToGraveOrder, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(showAbilityPickerForced, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(displayLifeOnAvatar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        main_gameLayout.setVerticalGroup(
            main_gameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(main_gameLayout.createSequentialGroup()
                .add(nonLandPermanentsInOnePile)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(showPlayerNamesPermanently)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(displayLifeOnAvatar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(showAbilityPickerForced)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbAllowRequestToShowHandCards)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbShowStormCounter)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbConfirmEmptyManaPool)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbAskMoveToGraveOrder))
        );

        nonLandPermanentsInOnePile.getAccessibleContext().setAccessibleName("nonLandPermanentsInOnePile");

        main_battlefield.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Battlefield"));

        cbBattlefieldFeedbackColorizingMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disable colorizing", "Enable one color for all phases", "Enable multicolor for different phases" }));
        cbBattlefieldFeedbackColorizingMode.setToolTipText("Battlefield feedback panel colorizing on your turn (e.g. use green color if you must select card or answer to request)");
        cbBattlefieldFeedbackColorizingMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBattlefieldFeedbackColorizingModeActionPerformed(evt);
            }
        });

        lblBattlefieldFeedbackColorizingMode.setLabelFor(cbBattlefieldFeedbackColorizingMode);
        lblBattlefieldFeedbackColorizingMode.setText("Feedback panel colorizing:");

        org.jdesktop.layout.GroupLayout main_battlefieldLayout = new org.jdesktop.layout.GroupLayout(main_battlefield);
        main_battlefield.setLayout(main_battlefieldLayout);
        main_battlefieldLayout.setHorizontalGroup(
            main_battlefieldLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(main_battlefieldLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblBattlefieldFeedbackColorizingMode)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbBattlefieldFeedbackColorizingMode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 278, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        main_battlefieldLayout.setVerticalGroup(
            main_battlefieldLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(main_battlefieldLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(lblBattlefieldFeedbackColorizingMode)
                .add(cbBattlefieldFeedbackColorizingMode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        org.jdesktop.layout.GroupLayout tabMainLayout = new org.jdesktop.layout.GroupLayout(tabMain);
        tabMain.setLayout(tabMainLayout);
        tabMainLayout.setHorizontalGroup(
            tabMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, main_card, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, main_gamelog, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(main_game, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, main_battlefield, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(main_battlefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        main_card.getAccessibleContext().setAccessibleName("Game panel");

        tabsPanel.addTab("Main", tabMain);

        tabGuiSize.setMaximumSize(new java.awt.Dimension(527, 423));
        tabGuiSize.setMinimumSize(new java.awt.Dimension(527, 423));
        java.awt.GridBagLayout tabGuiSizeLayout = new java.awt.GridBagLayout();
        tabGuiSizeLayout.columnWidths = new int[] {0};
        tabGuiSizeLayout.rowHeights = new int[] {0, 20, 0};
        tabGuiSizeLayout.columnWeights = new double[] {1.0};
        tabGuiSizeLayout.rowWeights = new double[] {1.0, 0.0, 1.0};
        tabGuiSize.setLayout(tabGuiSizeLayout);

        guiSizeBasic.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Size basic elements"));
        guiSizeBasic.setMinimumSize(new java.awt.Dimension(600, 180));
        guiSizeBasic.setPreferredSize(new java.awt.Dimension(600, 180));
        java.awt.GridBagLayout guiSizeBasicLayout = new java.awt.GridBagLayout();
        guiSizeBasicLayout.columnWeights = new double[] {1.0, 1.0, 1.0};
        guiSizeBasicLayout.rowWeights = new double[] {1.0, 0.2, 1.0, 0.2};
        guiSizeBasic.setLayout(guiSizeBasicLayout);

        sliderFontSize.setMajorTickSpacing(5);
        sliderFontSize.setMaximum(50);
        sliderFontSize.setMinimum(10);
        sliderFontSize.setMinorTickSpacing(1);
        sliderFontSize.setPaintLabels(true);
        sliderFontSize.setPaintTicks(true);
        sliderFontSize.setSnapToTicks(true);
        sliderFontSize.setToolTipText("<HTML>The size of the font used to display table text.");
        sliderFontSize.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderFontSize.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderFontSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeBasic.add(sliderFontSize, gridBagConstraints);

        fontSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontSizeLabel.setText("Tables");
        fontSizeLabel.setToolTipText("<HTML>The size of the font used to display table text.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeBasic.add(fontSizeLabel, gridBagConstraints);

        sliderChatFontSize.setMajorTickSpacing(5);
        sliderChatFontSize.setMaximum(50);
        sliderChatFontSize.setMinimum(10);
        sliderChatFontSize.setMinorTickSpacing(1);
        sliderChatFontSize.setPaintLabels(true);
        sliderChatFontSize.setPaintTicks(true);
        sliderChatFontSize.setSnapToTicks(true);
        sliderChatFontSize.setToolTipText("<HTML>The size of the font used to display the chat text");
        sliderChatFontSize.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderChatFontSize.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderChatFontSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeBasic.add(sliderChatFontSize, gridBagConstraints);

        chatFontSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chatFontSizeLabel.setText("Chat");
        chatFontSizeLabel.setToolTipText("<HTML>The size of the font used to display the chat text");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeBasic.add(chatFontSizeLabel, gridBagConstraints);

        sliderDialogFont.setMajorTickSpacing(5);
        sliderDialogFont.setMaximum(50);
        sliderDialogFont.setMinimum(10);
        sliderDialogFont.setMinorTickSpacing(1);
        sliderDialogFont.setPaintLabels(true);
        sliderDialogFont.setPaintTicks(true);
        sliderDialogFont.setSnapToTicks(true);
        sliderDialogFont.setToolTipText("<HTML>The size of the font of messages and menues");
        sliderDialogFont.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderDialogFont.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderDialogFont.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeBasic.add(sliderDialogFont, gridBagConstraints);

        labelDialogFont.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDialogFont.setText("Messages and menus");
        labelDialogFont.setToolTipText("<HTML>The size of the font of messages and menus");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeBasic.add(labelDialogFont, gridBagConstraints);
        labelDialogFont.getAccessibleContext().setAccessibleDescription("<HTML>The size of the font used to display messages or menus.");

        sliderEditorCardSize.setMajorTickSpacing(5);
        sliderEditorCardSize.setMaximum(50);
        sliderEditorCardSize.setMinimum(10);
        sliderEditorCardSize.setMinorTickSpacing(1);
        sliderEditorCardSize.setPaintLabels(true);
        sliderEditorCardSize.setPaintTicks(true);
        sliderEditorCardSize.setSnapToTicks(true);
        sliderEditorCardSize.setToolTipText("<HTML>The size of the card in editor and the picked zone of the draft panel");
        sliderEditorCardSize.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderEditorCardSize.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderEditorCardSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeBasic.add(sliderEditorCardSize, gridBagConstraints);

        labelEditorCardSize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEditorCardSize.setText("Cards in editor and draft panel");
        labelEditorCardSize.setToolTipText("<HTML>The size of the card in editor and the picked zone of the draft panel\n");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeBasic.add(labelEditorCardSize, gridBagConstraints);

        sliderEditorCardOffset.setMajorTickSpacing(5);
        sliderEditorCardOffset.setMaximum(50);
        sliderEditorCardOffset.setMinimum(10);
        sliderEditorCardOffset.setMinorTickSpacing(1);
        sliderEditorCardOffset.setPaintLabels(true);
        sliderEditorCardOffset.setPaintTicks(true);
        sliderEditorCardOffset.setSnapToTicks(true);
        sliderEditorCardOffset.setToolTipText("<HTML>The size of the card in editor and the picked zone of the draft panel");
        sliderEditorCardOffset.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderEditorCardOffset.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderEditorCardOffset.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeBasic.add(sliderEditorCardOffset, gridBagConstraints);

        labelEditorCardOffset.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEditorCardOffset.setText("Card offset editor and draft");
        labelEditorCardOffset.setToolTipText("<HTML>The vertical offset of card images in editor areas\n");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeBasic.add(labelEditorCardOffset, gridBagConstraints);

        sliderEnlargedImageSize.setMajorTickSpacing(5);
        sliderEnlargedImageSize.setMaximum(50);
        sliderEnlargedImageSize.setMinimum(10);
        sliderEnlargedImageSize.setMinorTickSpacing(1);
        sliderEnlargedImageSize.setPaintLabels(true);
        sliderEnlargedImageSize.setPaintTicks(true);
        sliderEnlargedImageSize.setSnapToTicks(true);
        sliderEnlargedImageSize.setToolTipText("<HTML>The size of the image shown for the card your mouse pointer<br>is located over while you turn the mouse wheel ");
        sliderEnlargedImageSize.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderEnlargedImageSize.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderEnlargedImageSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeBasic.add(sliderEnlargedImageSize, gridBagConstraints);

        labelEnlargedImageSize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEnlargedImageSize.setText("Enlarged image (mouse wheel)");
        labelEnlargedImageSize.setToolTipText("<HTML>The size of the image shown for the card your mouse pointer<br>is located over while you turn the mouse wheel\n");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeBasic.add(labelEnlargedImageSize, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        tabGuiSize.add(guiSizeBasic, gridBagConstraints);

        guiSizeGame.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Size game elements"));
        guiSizeGame.setMinimumSize(new java.awt.Dimension(600, 180));
        guiSizeGame.setPreferredSize(new java.awt.Dimension(600, 180));
        java.awt.GridBagLayout guiSizeGameLayout = new java.awt.GridBagLayout();
        guiSizeGameLayout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        guiSizeGameLayout.rowWeights = new double[] {1.0, 0.2, 1.0, 0.2};
        guiSizeGame.setLayout(guiSizeGameLayout);

        sliderCardSizeHand.setMajorTickSpacing(5);
        sliderCardSizeHand.setMaximum(50);
        sliderCardSizeHand.setMinimum(10);
        sliderCardSizeHand.setMinorTickSpacing(1);
        sliderCardSizeHand.setPaintLabels(true);
        sliderCardSizeHand.setPaintTicks(true);
        sliderCardSizeHand.setSnapToTicks(true);
        sliderCardSizeHand.setToolTipText("<HTML>The size of the card images in hand and on the stack");
        sliderCardSizeHand.setValue(14);
        sliderCardSizeHand.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderCardSizeHand.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderCardSizeHand.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeGame.add(sliderCardSizeHand, gridBagConstraints);

        labelCardSizeHand.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCardSizeHand.setText("Hand cards and stack objects");
        labelCardSizeHand.setToolTipText("<HTML>The size of the card images in hand and on the stack");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeGame.add(labelCardSizeHand, gridBagConstraints);

        sliderCardSizeOtherZones.setMajorTickSpacing(5);
        sliderCardSizeOtherZones.setMaximum(50);
        sliderCardSizeOtherZones.setMinimum(10);
        sliderCardSizeOtherZones.setMinorTickSpacing(1);
        sliderCardSizeOtherZones.setPaintLabels(true);
        sliderCardSizeOtherZones.setPaintTicks(true);
        sliderCardSizeOtherZones.setSnapToTicks(true);
        sliderCardSizeOtherZones.setToolTipText("<HTML>The size of card in other game zone (e.g. graveyard, revealed cards etc.)");
        sliderCardSizeOtherZones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderCardSizeOtherZones.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderCardSizeOtherZones.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeGame.add(sliderCardSizeOtherZones, gridBagConstraints);

        labelCardSizeOtherZones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCardSizeOtherZones.setText("Cards other zones");
        labelCardSizeOtherZones.setToolTipText("<HTML>The size of card in other game zone (e.g. graveyard, revealed cards etc.)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeGame.add(labelCardSizeOtherZones, gridBagConstraints);

        sliderCardSizeMinBattlefield.setMajorTickSpacing(5);
        sliderCardSizeMinBattlefield.setMaximum(50);
        sliderCardSizeMinBattlefield.setMinimum(10);
        sliderCardSizeMinBattlefield.setMinorTickSpacing(1);
        sliderCardSizeMinBattlefield.setPaintLabels(true);
        sliderCardSizeMinBattlefield.setPaintTicks(true);
        sliderCardSizeMinBattlefield.setSnapToTicks(true);
        sliderCardSizeMinBattlefield.setToolTipText("<HTML>The minimum size of permanents on the battlefield");
        sliderCardSizeMinBattlefield.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderCardSizeMinBattlefield.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderCardSizeMinBattlefield.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeGame.add(sliderCardSizeMinBattlefield, gridBagConstraints);

        labelCardSizeMinBattlefield.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCardSizeMinBattlefield.setText("Permanents min size");
        labelCardSizeMinBattlefield.setToolTipText("<HTML>The minimum size of permanents on the battlefield");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeGame.add(labelCardSizeMinBattlefield, gridBagConstraints);

        sliderCardSizeMaxBattlefield.setMajorTickSpacing(5);
        sliderCardSizeMaxBattlefield.setMaximum(50);
        sliderCardSizeMaxBattlefield.setMinimum(10);
        sliderCardSizeMaxBattlefield.setMinorTickSpacing(1);
        sliderCardSizeMaxBattlefield.setPaintLabels(true);
        sliderCardSizeMaxBattlefield.setPaintTicks(true);
        sliderCardSizeMaxBattlefield.setSnapToTicks(true);
        sliderCardSizeMaxBattlefield.setToolTipText("<HTML>The maximum size of permanents on the battlefield");
        sliderCardSizeMaxBattlefield.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderCardSizeMaxBattlefield.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderCardSizeMaxBattlefield.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeGame.add(sliderCardSizeMaxBattlefield, gridBagConstraints);

        labelCardSizeMaxBattlefield.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCardSizeMaxBattlefield.setText("Permanents max size");
        labelCardSizeMaxBattlefield.setToolTipText("<HTML>The maximum size of permanents on the battlefield");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeGame.add(labelCardSizeMaxBattlefield, gridBagConstraints);

        sliderStackWidth.setMajorTickSpacing(20);
        sliderStackWidth.setMaximum(90);
        sliderStackWidth.setMinimum(10);
        sliderStackWidth.setMinorTickSpacing(5);
        sliderStackWidth.setPaintLabels(true);
        sliderStackWidth.setPaintTicks(true);
        sliderStackWidth.setSnapToTicks(true);
        sliderStackWidth.setToolTipText("<HTML>The % size of the stack object area in relation to the hand card area size.");
        sliderStackWidth.setValue(30);
        sliderStackWidth.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderStackWidth.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderStackWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeGame.add(sliderStackWidth, gridBagConstraints);
        sliderStackWidth.getAccessibleContext().setAccessibleDescription("<HTML>The stack width in relation to the hand area width");

        labelStackWidth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelStackWidth.setText("Stack %width related to hand");
        labelStackWidth.setToolTipText("<HTML>The % size of the stack object area in relation to the hand card area size.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeGame.add(labelStackWidth, gridBagConstraints);
        labelStackWidth.getAccessibleContext().setAccessibleDescription("<HTML>The stack width in relation to the hand area width");

        sliderGameFeedbackArea.setMajorTickSpacing(5);
        sliderGameFeedbackArea.setMaximum(50);
        sliderGameFeedbackArea.setMinimum(10);
        sliderGameFeedbackArea.setMinorTickSpacing(1);
        sliderGameFeedbackArea.setPaintLabels(true);
        sliderGameFeedbackArea.setPaintTicks(true);
        sliderGameFeedbackArea.setSnapToTicks(true);
        sliderGameFeedbackArea.setToolTipText("<HTML>The size of the game feedback area (buttons and messages above the hand area)");
        sliderGameFeedbackArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderGameFeedbackArea.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderGameFeedbackArea.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeGame.add(sliderGameFeedbackArea, gridBagConstraints);

        labelGameFeedback.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGameFeedback.setText("Dialog area");
        labelGameFeedback.setToolTipText("<HTML>The size of the game feedback area (buttons and messages above the hand area)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeGame.add(labelGameFeedback, gridBagConstraints);
        labelGameFeedback.getAccessibleContext().setAccessibleName("");

        sliderTooltipSize.setMajorTickSpacing(5);
        sliderTooltipSize.setMaximum(50);
        sliderTooltipSize.setMinimum(10);
        sliderTooltipSize.setMinorTickSpacing(1);
        sliderTooltipSize.setPaintLabels(true);
        sliderTooltipSize.setPaintTicks(true);
        sliderTooltipSize.setSnapToTicks(true);
        sliderTooltipSize.setToolTipText("<HTML>The size of the tooltip window for cards or permanents");
        sliderTooltipSize.setValue(14);
        sliderTooltipSize.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sliderTooltipSize.setMinimumSize(new java.awt.Dimension(150, 40));
        sliderTooltipSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGUISizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        guiSizeGame.add(sliderTooltipSize, gridBagConstraints);

        labelTooltipSize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTooltipSize.setText("Tooltip window");
        labelTooltipSize.setToolTipText("<HTML>The size of the tooltip window for cards or permanents");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        guiSizeGame.add(labelTooltipSize, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        tabGuiSize.add(guiSizeGame, gridBagConstraints);

        tabsPanel.addTab("GUI Size", tabGuiSize);

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
        cbStopAttack.setToolTipText("");
        cbStopAttack.setActionCommand("");
        cbStopAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopAttackActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopAttack);

        cbStopBlockWithAny.setSelected(true);
        cbStopBlockWithAny.setText("STOP skips on declare blockers if ANY blockers are available");
        cbStopBlockWithAny.setToolTipText("");
        cbStopBlockWithAny.setActionCommand("");
        cbStopBlockWithAny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopBlockWithAnyActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopBlockWithAny);

        cbStopBlockWithZero.setText("STOP skips on declare blockers if ZERO blockers are available");
        cbStopBlockWithZero.setToolTipText("");
        cbStopBlockWithZero.setActionCommand("");
        cbStopBlockWithZero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopBlockWithZeroActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopBlockWithZero);

        cbStopOnNewStackObjects.setText("Skip to STACK resolved (F10): stop on new objects added (on) or stop until empty (off)");
        cbStopOnNewStackObjects.setToolTipText("");
        cbStopOnNewStackObjects.setActionCommand("");
        cbStopOnNewStackObjects.setPreferredSize(new java.awt.Dimension(300, 25));
        cbStopOnNewStackObjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopOnNewStackObjectsActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopOnNewStackObjects);

        cbStopOnAllMain.setText("Skip to MAIN step (F7): stop on any main steps (on) or stop on your main step (off)");
        cbStopOnAllMain.setToolTipText("");
        cbStopOnAllMain.setActionCommand("");
        cbStopOnAllMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStopOnAllMainActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbStopOnAllMain);

        cbStopOnAllEnd.setText("Skip to END step (F5): stop on any end steps (on) or stop on opponents end step (off)");
        cbStopOnAllEnd.setToolTipText("");
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

        cbAutoOrderTrigger.setText("TRIGGERS: auto-choose triggers order for same rule texts (put same triggers to the stack at default order)");
        cbAutoOrderTrigger.setToolTipText("<HTML>If you put same triggers with same texts on the stack then auto-choose their order.<br/>\nYou can change that settings anytime at the game.");
        cbAutoOrderTrigger.setActionCommand("");
        cbAutoOrderTrigger.setPreferredSize(new java.awt.Dimension(300, 25));
        cbAutoOrderTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutoOrderTriggerActionPerformed(evt);
            }
        });
        phases_stopSettings.add(cbAutoOrderTrigger);

        cbUseSameSettingsForReplacementEffect.setText("REPLACEMENT EFFECTS: use same auto-choose settings for same cards (choose replacement effects order dialog)");
        cbUseSameSettingsForReplacementEffect.setToolTipText("<HTML>If you setup auto-choose for one object/card then it will be applied for all other objects with same name.<br/>\nYou can change that settings anytime at the game.");
        cbUseSameSettingsForReplacementEffect.setActionCommand("");
        cbUseSameSettingsForReplacementEffect.setPreferredSize(new java.awt.Dimension(300, 25));
        cbUseSameSettingsForReplacementEffect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseSameSettingsForReplacementEffectActionPerformed(evt);
            }
        });
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
                .add(phases_stopSettings, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsPanel.addTab("Phases & Priority", tabPhases);

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

        cbPreferredImageLanguage.setMaximumRowCount(20);
        cbPreferredImageLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelPreferredImageLanguage.setText("Default images language:");
        labelPreferredImageLanguage.setFocusable(false);

        labelNumberOfDownloadThreads.setText("Default download threads:");

        cbNumberOfDownloadThreads.setMaximumRowCount(20);
        cbNumberOfDownloadThreads.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelHint1.setText("(change it to 1-3 if image source bans your IP for too many connections)");

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
                                .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(labelNumberOfDownloadThreads)
                                    .add(labelPreferredImageLanguage))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(cbPreferredImageLanguage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 153, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(panelCardImagesLayout.createSequentialGroup()
                                        .add(cbNumberOfDownloadThreads, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 153, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(labelHint1)))))
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
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelNumberOfDownloadThreads)
                    .add(cbNumberOfDownloadThreads, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(labelHint1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelCardImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelPreferredImageLanguage)
                    .add(cbPreferredImageLanguage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        panelCardStyles.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card styles (restart xmage to apply new settings)"));
        panelCardStyles.setLayout(new javax.swing.BoxLayout(panelCardStyles, javax.swing.BoxLayout.Y_AXIS));

        cbCardRenderImageFallback.setText("Render mode: MTGO style (off) or IMAGE style (on)");
        panelCardStyles.add(cbCardRenderImageFallback);

        cbCardRenderIconsForAbilities.setText("Enable card icons for abilities (example: flying, deathtouch)");
        panelCardStyles.add(cbCardRenderIconsForAbilities);

        cbCardRenderIconsForPlayable.setText("Enable card icons for playable abilities (example: if you can activate card's ability then show a special icon in the corner)");
        panelCardStyles.add(cbCardRenderIconsForPlayable);
        panelCardStyles.add(jSeparator1);

        cbCardRenderShowReminderText.setText("Show reminder text in rendered card textboxes");
        panelCardStyles.add(cbCardRenderShowReminderText);

        cbCardRenderHideSetSymbol.setText("Hide set symbols on cards (more space on the type line for card types)");
        panelCardStyles.add(cbCardRenderHideSetSymbol);

        cbCardRenderShowAbilityTextOverlay.setText("Show ability text as overlay in big card view");
        panelCardStyles.add(cbCardRenderShowAbilityTextOverlay);

        panelBackgroundImages.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Background images"));

        cbUseDefaultBackground.setText("Use default location for backgrounds");
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

        cbUseRandomBattleImage.setText("Use random background");
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
                        .add(cbUseDefaultBackground)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(txtBackgroundImagePath)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnBrowseBackgroundImage))
                    .add(panelBackgroundImagesLayout.createSequentialGroup()
                        .add(cbUseRandomBattleImage)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(txtBattlefieldImagePath)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnBrowseBattlefieldImage))
                    .add(panelBackgroundImagesLayout.createSequentialGroup()
                        .add(cbUseDefaultBattleImage)
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBackgroundImagesLayout.setVerticalGroup(
            panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelBackgroundImagesLayout.createSequentialGroup()
                .add(cbUseDefaultBattleImage)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbUseDefaultBackground)
                    .add(txtBackgroundImagePath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBrowseBackgroundImage))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelBackgroundImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbUseRandomBattleImage)
                    .add(txtBattlefieldImagePath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBrowseBattlefieldImage))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout tabImagesLayout = new org.jdesktop.layout.GroupLayout(tabImages);
        tabImages.setLayout(tabImagesLayout);
        tabImagesLayout.setHorizontalGroup(
            tabImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabImagesLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelCardImages, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelCardStyles, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelBackgroundImages, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabImagesLayout.setVerticalGroup(
            tabImagesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabImagesLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelCardStyles, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelCardImages, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelBackgroundImages, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
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
                .add(avatarPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsPanel.addTab("Avatars", tabAvatars);

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

        org.jdesktop.layout.GroupLayout connection_serversLayout = new org.jdesktop.layout.GroupLayout(connection_servers);
        connection_servers.setLayout(connection_serversLayout);
        connection_serversLayout.setHorizontalGroup(
            connection_serversLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(connection_serversLayout.createSequentialGroup()
                .add(connection_serversLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(connection_serversLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(lblURLServerList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtURLServerList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 370, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(connection_serversLayout.createSequentialGroup()
                        .add(141, 141, 141)
                        .add(jLabel17)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        connection_serversLayout.setVerticalGroup(
            connection_serversLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(connection_serversLayout.createSequentialGroup()
                .add(connection_serversLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lblURLServerList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(txtURLServerList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel17))
        );

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

        org.jdesktop.layout.GroupLayout pnlProxyLayout = new org.jdesktop.layout.GroupLayout(pnlProxy);
        pnlProxy.setLayout(pnlProxyLayout);
        pnlProxyLayout.setHorizontalGroup(
            pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlProxyLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlProxyLayout.createSequentialGroup()
                        .add(rememberPswd)
                        .add(47, 47, 47)
                        .add(jLabel11)
                        .add(34, 34, 34))
                    .add(pnlProxyLayout.createSequentialGroup()
                        .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblProxyPort)
                            .add(lblProxyPassword)
                            .add(lblProxyServer)
                            .add(lblProxyUserName))
                        .add(19, 19, 19)
                        .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(txtProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, txtPasswordField)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, txtProxyUserName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(txtProxyServer))
                        .addContainerGap())))
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
                .add(18, 18, 18)
                .add(pnlProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(rememberPswd)
                    .add(jLabel11))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout pnlProxySettingsLayout = new org.jdesktop.layout.GroupLayout(pnlProxySettings);
        pnlProxySettings.setLayout(pnlProxySettingsLayout);
        pnlProxySettingsLayout.setHorizontalGroup(
            pnlProxySettingsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlProxySettingsLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlProxy, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlProxySettingsLayout.setVerticalGroup(
            pnlProxySettingsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlProxySettingsLayout.createSequentialGroup()
                .add(pnlProxy, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout tabConnectionLayout = new org.jdesktop.layout.GroupLayout(tabConnection);
        tabConnection.setLayout(tabConnectionLayout);
        tabConnectionLayout.setHorizontalGroup(
            tabConnectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, tabConnectionLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabConnectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(pnlProxySettings, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, tabConnectionLayout.createSequentialGroup()
                        .add(lblProxyType)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(cbProxyType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(connection_servers, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabConnectionLayout.setVerticalGroup(
            tabConnectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabConnectionLayout.createSequentialGroup()
                .addContainerGap()
                .add(connection_servers, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabConnectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblProxyType)
                    .add(cbProxyType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(pnlProxySettings, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlProxySettings.getAccessibleContext().setAccessibleDescription("");

        tabsPanel.addTab("Connection", tabConnection);

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

        tabsPanel.addTab("Controls", tabControls);

        themesCategory.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Themes"));

        lbSelectLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSelectLabel.setText("Select a theme:");
        lbSelectLabel.setToolTipText("");
        lbSelectLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lbSelectLabel.setPreferredSize(new java.awt.Dimension(110, 16));
        lbSelectLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        cbTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbThemeActionPerformed(evt);
            }
        });

        lbThemeHint.setText("Requires a restart to apply new theme.");

        org.jdesktop.layout.GroupLayout themesCategoryLayout = new org.jdesktop.layout.GroupLayout(themesCategory);
        themesCategory.setLayout(themesCategoryLayout);
        themesCategoryLayout.setHorizontalGroup(
            themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(themesCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbSelectLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lbThemeHint)
                    .add(cbTheme, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 303, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(303, Short.MAX_VALUE))
        );
        themesCategoryLayout.setVerticalGroup(
            themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(themesCategoryLayout.createSequentialGroup()
                .add(themesCategoryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(cbTheme, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lbSelectLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lbThemeHint)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout tabThemesLayout = new org.jdesktop.layout.GroupLayout(tabThemes);
        tabThemes.setLayout(tabThemesLayout);
        tabThemesLayout.setHorizontalGroup(
            tabThemesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 750, Short.MAX_VALUE)
            .add(tabThemesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(tabThemesLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(themesCategory, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        tabThemesLayout.setVerticalGroup(
            tabThemesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 526, Short.MAX_VALUE)
            .add(tabThemesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(tabThemesLayout.createSequentialGroup()
                    .add(21, 21, 21)
                    .add(themesCategory, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(430, Short.MAX_VALUE)))
        );

        tabsPanel.addTab("Themes", tabThemes);

        saveButton.setLabel("Save");
        saveButton.setMaximumSize(new java.awt.Dimension(100, 30));
        saveButton.setMinimumSize(new java.awt.Dimension(100, 30));
        saveButton.setPreferredSize(new java.awt.Dimension(100, 30));
        saveButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        exitButton.setLabel("Exit");
        exitButton.setMaximumSize(new java.awt.Dimension(100, 30));
        exitButton.setMinimumSize(new java.awt.Dimension(100, 30));
        exitButton.setPreferredSize(new java.awt.Dimension(100, 30));
        exitButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
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
                .add(tabsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 554, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(exitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        Preferences prefs = MageFrame.getPreferences();

        // main
        save(prefs, dialog.tooltipDelay, KEY_SHOW_TOOLTIPS_DELAY, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showCardName, KEY_SHOW_CARD_NAMES, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showFullImagePath, KEY_SHOW_FULL_IMAGE_PATH, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.displayLifeOnAvatar, KEY_DISPLAY_LIVE_ON_AVATAR, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showAbilityPickerForced, KEY_SHOW_ABILITY_PICKER_FORCED, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbShowStormCounter, KEY_GAME_SHOW_STORM_COUNTER, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbConfirmEmptyManaPool, KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbAskMoveToGraveOrder, KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbGameLogShowTurnInfo, KEY_GAME_LOG_SHOW_TURN_INFO, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbDraftLogAutoSave, KEY_DRAFT_LOG_AUTO_SAVE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbGameJsonLogAutoSave, KEY_JSON_GAME_LOG_AUTO_SAVE, "true", "false", UPDATE_CACHE_POLICY);

        String paramName = KEY_BATTLEFIELD_FEEDBACK_COLORIZING_MODE;
        int paramValue = dialog.cbBattlefieldFeedbackColorizingMode.getSelectedIndex();
        int paramDefault = BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_MULTICOLOR;
        if (getCachedValue(paramName, paramDefault) != paramValue) {
            prefs.putInt(paramName, paramValue);
            if (UPDATE_CACHE_POLICY) {
                updateCache(paramName, Integer.toString(paramValue));
            }
        }

        saveGUISize();

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

        save(prefs, dialog.cbStopAttack, KEY_STOP_ATTACK, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopBlockWithAny, KEY_STOP_BLOCK_WITH_ANY, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopBlockWithZero, KEY_STOP_BLOCK_WITH_ZERO, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopOnAllMain, KEY_STOP_ALL_MAIN_PHASES, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopOnAllEnd, KEY_STOP_ALL_END_PHASES, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbStopOnNewStackObjects, KEY_STOP_NEW_STACK_OBJECTS, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbPassPriorityCast, KEY_PASS_PRIORITY_CAST, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbPassPriorityActivation, KEY_PASS_PRIORITY_ACTIVATION, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbAutoOrderTrigger, KEY_AUTO_ORDER_TRIGGER, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbUseSameSettingsForReplacementEffect, KEY_USE_SAME_SETTINGS_FOR_SAME_REPLACEMENT_EFFECTS, "true", "false", UPDATE_CACHE_POLICY);

        // images
        save(prefs, dialog.cbUseDefaultImageFolder, KEY_CARD_IMAGES_USE_DEFAULT, "true", "false", UPDATE_CACHE_POLICY);
        saveImagesPath(prefs);
        save(prefs, dialog.cbSaveToZipFiles, KEY_CARD_IMAGES_SAVE_TO_ZIP, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbNumberOfDownloadThreads, KEY_CARD_IMAGES_THREADS);
        save(prefs, dialog.cbPreferredImageLanguage, KEY_CARD_IMAGES_PREF_LANGUAGE);

        save(prefs, dialog.cbUseDefaultBackground, KEY_BACKGROUND_IMAGE_DEFAULT, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbUseDefaultBattleImage, KEY_BATTLEFIELD_IMAGE_DEFAULT, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbUseRandomBattleImage, KEY_BATTLEFIELD_IMAGE_RANDOM, "true", "false", UPDATE_CACHE_POLICY);

        // rendering
        save(prefs, dialog.cbCardRenderImageFallback, KEY_CARD_RENDERING_FALLBACK, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbCardRenderIconsForAbilities, KEY_CARD_RENDERING_ICONS_FOR_ABILITIES, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbCardRenderIconsForPlayable, KEY_CARD_RENDERING_ICONS_FOR_PLAYABLE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbCardRenderHideSetSymbol, KEY_CARD_RENDERING_SET_SYMBOL, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbCardRenderShowReminderText, KEY_CARD_RENDERING_REMINDER_TEXT, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbCardRenderShowAbilityTextOverlay, KEY_CARD_RENDERING_ABILITY_TEXT_OVERLAY, "true", "false", UPDATE_CACHE_POLICY);

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
        save(prefs, dialog.cbTheme, KEY_THEME);

        // Avatar
        if (selectedAvatarId < MIN_AVATAR_ID || selectedAvatarId > MAX_AVATAR_ID) {
            selectedAvatarId = DEFAULT_AVATAR_ID;
        }
        prefs.put(KEY_AVATAR, String.valueOf(selectedAvatarId));
        updateCache(KEY_AVATAR, String.valueOf(selectedAvatarId));

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

    private void saveGUISize() {
        Preferences prefs = MageFrame.getPreferences();
        
        // GUI Size
        save(prefs, dialog.sliderFontSize, KEY_GUI_TABLE_FONT_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderChatFontSize, KEY_GUI_CHAT_FONT_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderCardSizeHand, KEY_GUI_CARD_HAND_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderEditorCardSize, KEY_GUI_CARD_EDITOR_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderEditorCardOffset, KEY_GUI_CARD_OFFSET_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderEnlargedImageSize, KEY_GUI_ENLARGED_IMAGE_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderStackWidth, KEY_GUI_STACK_WIDTH, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderTooltipSize, KEY_GUI_TOOLTIP_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderDialogFont, KEY_GUI_DIALOG_FONT_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderGameFeedbackArea, KEY_GUI_FEEDBACK_AREA_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderCardSizeOtherZones, KEY_GUI_CARD_OTHER_ZONES_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderCardSizeMinBattlefield, KEY_GUI_CARD_BATTLEFIELD_MIN_SIZE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.sliderCardSizeMaxBattlefield, KEY_GUI_CARD_BATTLEFIELD_MAX_SIZE, "true", "false", UPDATE_CACHE_POLICY);

        // do as worker job
        GUISizeHelper.changeGUISize();
    }
    
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        dialog.setVisible(false);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void useDefaultPath() {
        txtImageFolderPath.setText("./plugins/images/");
        txtImageFolderPath.setEnabled(false);
        btnBrowseImageLocation.setEnabled(false);
    }

    private void useConfigurablePath() {
        String path = CACHE.get(KEY_CARD_IMAGES_PATH);
        dialog.txtImageFolderPath.setText(path);
        txtImageFolderPath.setEnabled(true);
        btnBrowseImageLocation.setEnabled(true);
    }

    private void cbProxyTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProxyTypeActionPerformed
        this.showProxySettings();
    }//GEN-LAST:event_cbProxyTypeActionPerformed

    private void txtPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordFieldActionPerformed
    }//GEN-LAST:event_txtPasswordFieldActionPerformed

    private void txtProxyPortkeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProxyPortkeyTyped
    }//GEN-LAST:event_txtProxyPortkeyTyped

    private void rememberPswdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rememberPswdActionPerformed
    }//GEN-LAST:event_rememberPswdActionPerformed

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
        String path = CACHE.get(KEY_BACKGROUND_IMAGE);
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

    private void nonLandPermanentsInOnePileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonLandPermanentsInOnePileActionPerformed

    }//GEN-LAST:event_nonLandPermanentsInOnePileActionPerformed

    private void showPlayerNamesPermanentlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPlayerNamesPermanentlyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showPlayerNamesPermanentlyActionPerformed

    private void showCardNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCardNameActionPerformed

    }//GEN-LAST:event_showCardNameActionPerformed

    private void showAbilityPickerForcedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAbilityPickerForcedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showAbilityPickerForcedActionPerformed

    private void cbEnableOtherSoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEnableOtherSoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEnableOtherSoundsActionPerformed

    private void cbStopAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopAttackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopAttackActionPerformed

    private void cbStopBlockWithAnyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopBlockWithAnyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopBlockWithAnyActionPerformed

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

    private void cbPassPriorityCastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPassPriorityCastActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPassPriorityCastActionPerformed

    private void cbPassPriorityActivationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPassPriorityActivationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPassPriorityActivationActionPerformed

    private void cbAutoOrderTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutoOrderTriggerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAutoOrderTriggerActionPerformed

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

    private void cbBattlefieldFeedbackColorizingModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBattlefieldFeedbackColorizingModeActionPerformed

    }//GEN-LAST:event_cbBattlefieldFeedbackColorizingModeActionPerformed

    private void displayLifeOnAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayLifeOnAvatarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_displayLifeOnAvatarActionPerformed

    private void cbStopOnNewStackObjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopOnNewStackObjectsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopOnNewStackObjectsActionPerformed

    private void cbStopBlockWithZeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStopBlockWithZeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStopBlockWithZeroActionPerformed

    private void cbSaveToZipFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSaveToZipFilesActionPerformed
        // TODO add your handling code here:
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
        // TODO add your handling code here:
    }//GEN-LAST:event_cbThemeActionPerformed

    private void sliderGUISizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGUISizeStateChanged
        // This prevents this event from firing during the initial
        // setting of the sliders from pref values
        if (!ignoreGUISizeSliderStateChangedEvent)
        {
            saveGUISize();
        }
    }//GEN-LAST:event_sliderGUISizeStateChanged

    private void cbUseSameSettingsForReplacementEffectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUseSameSettingsForReplacementEffectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbUseSameSettingsForReplacementEffectActionPerformed

    private void showProxySettings() {
        Connection.ProxyType proxyType = (Connection.ProxyType) cbProxyType.getSelectedItem();
        switch (proxyType) {
            case SOCKS:
                this.pnlProxy.setVisible(true);
                this.pnlProxySettings.setVisible(true);
                break;
            case HTTP:
                this.pnlProxy.setVisible(true);
                this.pnlProxySettings.setVisible(true);
                break;
            case NONE:
                this.pnlProxy.setVisible(false);
                this.pnlProxySettings.setVisible(false);
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

        connection.setProxyType(configProxyType);
        if (configProxyType != ProxyType.NONE) {
            String host = getCachedValue(KEY_PROXY_ADDRESS, "");
            String port = getCachedValue(KEY_PROXY_PORT, "");
            if (!host.isEmpty() && !port.isEmpty()) {
                connection.setProxyHost(host);
                connection.setProxyPort(Integer.valueOf(port));
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
            if (param1.equals(OPEN_CONNECTION_TAB)) {
                param = 6;
            }
            if (param1.equals(OPEN_PHASES_TAB)) {
                param = 2;
            }
        }
        final int openedTab = param;
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

                // Connection
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
        load(prefs, dialog.tooltipDelay, KEY_SHOW_TOOLTIPS_DELAY, "300");
        load(prefs, dialog.showCardName, KEY_SHOW_CARD_NAMES, "true");
        load(prefs, dialog.showFullImagePath, KEY_SHOW_FULL_IMAGE_PATH, "true");
        load(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true");
        load(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true");
        load(prefs, dialog.displayLifeOnAvatar, KEY_DISPLAY_LIVE_ON_AVATAR, "true");
        load(prefs, dialog.showAbilityPickerForced, KEY_SHOW_ABILITY_PICKER_FORCED, "true");
        load(prefs, dialog.cbAllowRequestToShowHandCards, KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, "true");
        load(prefs, dialog.cbShowStormCounter, KEY_GAME_SHOW_STORM_COUNTER, "true");
        load(prefs, dialog.cbConfirmEmptyManaPool, KEY_GAME_CONFIRM_EMPTY_MANA_POOL, "true");
        load(prefs, dialog.cbAskMoveToGraveOrder, KEY_GAME_ASK_MOVE_TO_GRAVE_ORDER, "true");

        load(prefs, dialog.cbGameLogShowTurnInfo, KEY_GAME_LOG_SHOW_TURN_INFO, "true");
        load(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true");
        load(prefs, dialog.cbDraftLogAutoSave, KEY_DRAFT_LOG_AUTO_SAVE, "true");
        load(prefs, dialog.cbGameJsonLogAutoSave, KEY_JSON_GAME_LOG_AUTO_SAVE, "true", "false");

        String feedbackParam = "";
        try {
            feedbackParam = MageFrame.getPreferences().get(KEY_BATTLEFIELD_FEEDBACK_COLORIZING_MODE, "2");
            int feedbackMode = Integer.parseInt(feedbackParam);
            dialog.cbBattlefieldFeedbackColorizingMode.setSelectedIndex(feedbackMode);
        } catch (Throwable e) {
            logger.error("Can't parse and setup param " + KEY_BATTLEFIELD_FEEDBACK_COLORIZING_MODE + " = " + feedbackParam, e);
            dialog.cbBattlefieldFeedbackColorizingMode.setSelectedIndex(BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_MULTICOLOR);
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
        ignoreGUISizeSliderStateChangedEvent = true;
        load(prefs, dialog.sliderFontSize, KEY_GUI_TABLE_FONT_SIZE, "14");
        load(prefs, dialog.sliderChatFontSize, KEY_GUI_CHAT_FONT_SIZE, "14");
        load(prefs, dialog.sliderCardSizeHand, KEY_GUI_CARD_HAND_SIZE, "14");
        load(prefs, dialog.sliderEditorCardSize, KEY_GUI_CARD_EDITOR_SIZE, "14");
        load(prefs, dialog.sliderEditorCardOffset, KEY_GUI_CARD_OFFSET_SIZE, "14");
        load(prefs, dialog.sliderEnlargedImageSize, KEY_GUI_ENLARGED_IMAGE_SIZE, "20");
        load(prefs, dialog.sliderStackWidth, KEY_GUI_STACK_WIDTH, "14");
        load(prefs, dialog.sliderDialogFont, KEY_GUI_DIALOG_FONT_SIZE, "14");
        load(prefs, dialog.sliderTooltipSize, KEY_GUI_TOOLTIP_SIZE, "14");
        load(prefs, dialog.sliderGameFeedbackArea, KEY_GUI_FEEDBACK_AREA_SIZE, "14");
        load(prefs, dialog.sliderCardSizeOtherZones, KEY_GUI_CARD_OTHER_ZONES_SIZE, "14");
        load(prefs, dialog.sliderCardSizeMinBattlefield, KEY_GUI_CARD_BATTLEFIELD_MIN_SIZE, "10");
        load(prefs, dialog.sliderCardSizeMaxBattlefield, KEY_GUI_CARD_BATTLEFIELD_MAX_SIZE, "14");
        ignoreGUISizeSliderStateChangedEvent = false;
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
        load(prefs, dialog.cbSaveToZipFiles, KEY_CARD_IMAGES_SAVE_TO_ZIP, "true");
        dialog.cbNumberOfDownloadThreads.setSelectedItem(MageFrame.getPreferences().get(KEY_CARD_IMAGES_THREADS, KEY_CARD_IMAGES_THREADS_DEFAULT));
        dialog.cbPreferredImageLanguage.setSelectedItem(MageFrame.getPreferences().get(KEY_CARD_IMAGES_PREF_LANGUAGE, CardLanguage.ENGLISH.getCode()));

        // rendering settings
        load(prefs, dialog.cbCardRenderImageFallback, KEY_CARD_RENDERING_FALLBACK, "true", "false");
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
        dialog.cbProxyType.setSelectedItem(Connection.ProxyType.valueOf(MageFrame.getPreferences().get(KEY_PROXY_TYPE, "NONE").toUpperCase(Locale.ENGLISH)));

        load(prefs, dialog.txtProxyServer, KEY_PROXY_ADDRESS, ClientDefaultSettings.serverName);
        load(prefs, dialog.txtProxyPort, KEY_PROXY_PORT, Integer.toString(ClientDefaultSettings.port));
        load(prefs, dialog.txtProxyUserName, KEY_PROXY_USERNAME, "");
        load(prefs, dialog.rememberPswd, KEY_PROXY_REMEMBER, "true", "false");
        if (dialog.rememberPswd.isSelected()) {
            load(prefs, dialog.txtPasswordField, KEY_PROXY_PSWD, "");
        }
        load(prefs, dialog.txtURLServerList, KEY_CONNECTION_URL_SERVER_LIST, "http://XMage.de/files/server-list.txt");
    }

    private static void loadControlSettings(Preferences prefs) {
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
        dialog.cbTheme.setSelectedItem(PreferencesDialog.getCurrentTheme());
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
            if (selectedAvatarId < MIN_AVATAR_ID || selectedAvatarId > MAX_AVATAR_ID) {
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
                dialog.tabsPanel.setSelectedIndex(index);
            }
        } catch (Exception e) {
            logger.error("Error during open tab", e);
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
        return PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_SAVE_TO_ZIP, "true").equals("true");
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

    private static void save(Preferences prefs, JSlider slider, String propName, String yesValue, String noValue, boolean updateCache) {
        prefs.put(propName, Integer.toString(slider.getValue()));
        if (updateCache) {
            updateCache(propName, Integer.toString(slider.getValue()));
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
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        } else {
            String value = MageFrame.getPreferences().get(key, def);
            if (value == null) {
                return def;
            }
            CACHE.put(key, value);
            return value;
        }
    }

    public static int getRenderMode() {
        if (getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_FALLBACK, "false").equals("false")) {
            return 0; // mtgo
        } else {
            return 1; // image
        }
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
        } catch (BackingStoreException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: couldn't save preferences. Please try once again.");
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
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_ABILITY_PICKER_FORCED, "true").equals("true"),
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
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_USE_SAME_SETTINGS_FOR_SAME_REPLACEMENT_EFFECTS, "true").equals("true"),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                userStrId
        );
    }

    public static int getBattlefieldFeedbackColorizingMode() {
        return PreferencesDialog.getCachedValue(
                PreferencesDialog.KEY_BATTLEFIELD_FEEDBACK_COLORIZING_MODE,
                BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_MULTICOLOR);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane avatarPane;
    private javax.swing.JPanel avatarPanel;
    private javax.swing.JButton btnBattlefieldBGMBrowse;
    private javax.swing.JButton btnBrowseBackgroundImage;
    private javax.swing.JButton btnBrowseBattlefieldImage;
    private javax.swing.JButton btnBrowseImageLocation;
    private javax.swing.JButton bttnResetControls;
    private javax.swing.JCheckBox cbAllowRequestToShowHandCards;
    private javax.swing.JCheckBox cbAskMoveToGraveOrder;
    private javax.swing.JCheckBox cbAutoOrderTrigger;
    private javax.swing.JComboBox cbBattlefieldFeedbackColorizingMode;
    private javax.swing.JCheckBox cbCardRenderHideSetSymbol;
    private javax.swing.JCheckBox cbCardRenderIconsForAbilities;
    private javax.swing.JCheckBox cbCardRenderIconsForPlayable;
    private javax.swing.JCheckBox cbCardRenderImageFallback;
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
    private javax.swing.JCheckBox cbGameLogShowTurnInfo;
    private javax.swing.JComboBox cbNumberOfDownloadThreads;
    private javax.swing.JCheckBox cbPassPriorityActivation;
    private javax.swing.JCheckBox cbPassPriorityCast;
    private javax.swing.JComboBox<String> cbPreferredImageLanguage;
    private javax.swing.JComboBox<ProxyType> cbProxyType;
    private javax.swing.JCheckBox cbSaveToZipFiles;
    private javax.swing.JCheckBox cbShowStormCounter;
    private javax.swing.JCheckBox cbStopAttack;
    private javax.swing.JCheckBox cbStopBlockWithAny;
    private javax.swing.JCheckBox cbStopBlockWithZero;
    private javax.swing.JCheckBox cbStopOnAllEnd;
    private javax.swing.JCheckBox cbStopOnAllMain;
    private javax.swing.JCheckBox cbStopOnNewStackObjects;
    private javax.swing.JComboBox<ThemeType> cbTheme;
    private javax.swing.JCheckBox cbUseDefaultBackground;
    private javax.swing.JCheckBox cbUseDefaultBattleImage;
    private javax.swing.JCheckBox cbUseDefaultImageFolder;
    private javax.swing.JCheckBox cbUseRandomBattleImage;
    private javax.swing.JCheckBox cbUseSameSettingsForReplacementEffect;
    private javax.swing.JLabel chatFontSizeLabel;
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
    private javax.swing.JLabel controlsDescriptionLabel;
    private javax.swing.JCheckBox displayLifeOnAvatar;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel fontSizeLabel;
    private javax.swing.JPanel guiSizeBasic;
    private javax.swing.JPanel guiSizeGame;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel labelCardSizeHand;
    private javax.swing.JLabel labelCardSizeMaxBattlefield;
    private javax.swing.JLabel labelCardSizeMinBattlefield;
    private javax.swing.JLabel labelCardSizeOtherZones;
    private javax.swing.JLabel labelConfirm;
    private javax.swing.JLabel labelDialogFont;
    private javax.swing.JLabel labelEditorCardOffset;
    private javax.swing.JLabel labelEditorCardSize;
    private javax.swing.JLabel labelEndStep;
    private javax.swing.JLabel labelEnlargedImageSize;
    private javax.swing.JLabel labelGameFeedback;
    private javax.swing.JLabel labelHint1;
    private javax.swing.JLabel labelMainStep;
    private javax.swing.JLabel labelNextTurn;
    private javax.swing.JLabel labelNumberOfDownloadThreads;
    private javax.swing.JLabel labelPreferredImageLanguage;
    private javax.swing.JLabel labelPriorEnd;
    private javax.swing.JLabel labelSkipStep;
    private javax.swing.JLabel labelStackWidth;
    private javax.swing.JLabel labelSwitchChat;
    private javax.swing.JLabel labelToggleRecordMacro;
    private javax.swing.JLabel labelTooltipSize;
    private javax.swing.JLabel labelYourTurn;
    private javax.swing.JLabel lbSelectLabel;
    private javax.swing.JLabel lbThemeHint;
    private javax.swing.JLabel lblBattlefieldFeedbackColorizingMode;
    private javax.swing.JLabel lblProxyPassword;
    private javax.swing.JLabel lblProxyPort;
    private javax.swing.JLabel lblProxyServer;
    private javax.swing.JLabel lblProxyType;
    private javax.swing.JLabel lblProxyUserName;
    private javax.swing.JLabel lblURLServerList;
    private javax.swing.JLabel lebelSkip;
    private javax.swing.JPanel main_battlefield;
    private javax.swing.JPanel main_card;
    private javax.swing.JPanel main_game;
    private javax.swing.JPanel main_gamelog;
    private javax.swing.JCheckBox nonLandPermanentsInOnePile;
    private javax.swing.JPanel panelBackgroundImages;
    private javax.swing.JPanel panelCardImages;
    private javax.swing.JPanel panelCardStyles;
    private javax.swing.JPanel phases_stopSettings;
    private javax.swing.JPanel pnlProxy;
    private javax.swing.JPanel pnlProxySettings;
    private javax.swing.JCheckBox rememberPswd;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox showAbilityPickerForced;
    private javax.swing.JCheckBox showCardName;
    private javax.swing.JCheckBox showFullImagePath;
    private javax.swing.JCheckBox showPlayerNamesPermanently;
    private javax.swing.JSlider sliderCardSizeHand;
    private javax.swing.JSlider sliderCardSizeMaxBattlefield;
    private javax.swing.JSlider sliderCardSizeMinBattlefield;
    private javax.swing.JSlider sliderCardSizeOtherZones;
    private javax.swing.JSlider sliderChatFontSize;
    private javax.swing.JSlider sliderDialogFont;
    private javax.swing.JSlider sliderEditorCardOffset;
    private javax.swing.JSlider sliderEditorCardSize;
    private javax.swing.JSlider sliderEnlargedImageSize;
    private javax.swing.JSlider sliderFontSize;
    private javax.swing.JSlider sliderGameFeedbackArea;
    private javax.swing.JSlider sliderStackWidth;
    private javax.swing.JSlider sliderTooltipSize;
    private javax.swing.JPanel sounds_backgroundMusic;
    private javax.swing.JPanel sounds_clips;
    private javax.swing.JPanel tabAvatars;
    private javax.swing.JPanel tabConnection;
    private javax.swing.JPanel tabControls;
    private javax.swing.JPanel tabGuiSize;
    private javax.swing.JPanel tabImages;
    private javax.swing.JPanel tabMain;
    private javax.swing.JPanel tabPhases;
    private javax.swing.JPanel tabSounds;
    private javax.swing.JPanel tabThemes;
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
    private javax.swing.JTextField txtURLServerList;
    // End of variables declaration//GEN-END:variables

    private static final PreferencesDialog dialog = new PreferencesDialog(new javax.swing.JFrame(), true);

    static {
        dialog.setResizable(false);
    }
}
