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
import mage.client.util.PhaseManager;
import mage.client.util.gui.BufferedImageBuilder;
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
    
    public static final String KEY_SHOW_TOOLTIPS_ANY_ZONE = "showTooltipsInAnyZone";
    public static final String KEY_HAND_USE_BIG_CARDS = "handUseBigCards";
    public static final String KEY_PERMANENTS_IN_ONE_PILE = "nonLandPermanentsInOnePile";
    public static final String KEY_SHOW_PLAYER_NAMES_PERMANENTLY = "showPlayerNamesPermanently";
    public static final String KEY_SHOW_ABILITY_PICKER_FORCED = "showAbilityPicker";
    public static final String KEY_GAME_LOG_AUTO_SAVE = "gameLogAutoSave";

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
    public static final String KEY_SOUNDS_OTHER_ON = "soundsOtherOn";
    public static final String KEY_SOUNDS_MATCH_MUSIC_ON = "soundsMatchMusicOn";
    public static final String KEY_SOUNDS_MATCH_MUSIC_PATH = "soundsMatchMusicPath";

    public static final String KEY_BIG_CARD_TOGGLED = "bigCardToggled";


    // Size of frame to check if divider locations should be used 
    public static final String KEY_MAGE_PANEL_LAST_SIZE = "gamepanelLastSize";

    // positions of divider bars
    public static final String KEY_TABLES_DIVIDER_LOCATION_1 = "tablePanelDividerLocation1";
    public static final String KEY_TABLES_DIVIDER_LOCATION_2 = "tablePanelDividerLocation2";
    public static final String KEY_TABLES_DIVIDER_LOCATION_3 = "tablePanelDividerLocation3";

    public static final String KEY_GAMEPANEL_DIVIDER_LOCATION_0 = "gamepanelDividerLocation0";
    public static final String KEY_GAMEPANEL_DIVIDER_LOCATION_1 = "gamepanelDividerLocation1";
    public static final String KEY_GAMEPANEL_DIVIDER_LOCATION_2 = "gamepanelDividerLocation2";

    public static final String KEY_TOURNAMENT_DIVIDER_LOCATION_1 = "tournamentPanelDividerLocation1";
    public static final String KEY_TOURNAMENT_DIVIDER_LOCATION_2 = "tournamentPanelDividerLocation2";

    // pref setting for new table dialog
    public static final String KEY_NEW_TABLE_NAME = "newTableName";
    public static final String KEY_NEW_TABLE_DECK_TYPE = "newTableDeckType";
    public static final String KEY_NEW_TABLE_TIME_LIMIT = "newTableTimeLimit";
    public static final String KEY_NEW_TABLE_GAME_TYPE = "newTableGameType";
    public static final String KEY_NEW_TABLE_NUMBER_OF_WINS = "newTableNumberOfWins";
    public static final String KEY_NEW_TABLE_NUMBER_OF_FREE_MULLIGANS = "newTableNumberOfFreeMulligans";
    public static final String KEY_NEW_TABLE_DECK_FILE = "newTableDeckFile";
    public static final String KEY_NEW_TABLE_RANGE = "newTableRange";
    public static final String KEY_NEW_TABLE_ATTACK_OPTION = "newTableAttackOption";
    public static final String KEY_NEW_TABLE_NUMBER_PLAYERS = "newTableNumberPlayers";
    public static final String KEY_NEW_TABLE_PLAYER_TYPES = "newTablePlayerTypes";

    // pref setting for new tournament dialog
    public static final String KEY_NEW_TOURNAMENT_NAME = "newTournamentName";
    public static final String KEY_NEW_TOURNAMENT_TIME_LIMIT = "newTournamentTimeLimit";
    public static final String KEY_NEW_TOURNAMENT_CONSTR_TIME = "newTournamentConstructionTime";
    public static final String KEY_NEW_TOURNAMENT_TYPE = "newTournamentType";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_FREE_MULLIGANS = "newTournamentNumberOfFreeMulligans";
    public static final String KEY_NEW_TOURNAMENT_NUMBER_OF_WINS = "newTournamentNumberOfWins";
    public static final String KEY_NEW_TOURNAMENT_PACKS_SEALED = "newTournamentPacksSealed";
    public static final String KEY_NEW_TOURNAMENT_PACKS_DRAFT = "newTournamentPacksDraft";
    public static final String KEY_NEW_TOURNAMENT_PLAYERS_SEALED = "newTournamentPlayersSealed";
    public static final String KEY_NEW_TOURNAMENT_PLAYERS_DRAFT = "newTournamentPlayersDraft";
    public static final String KEY_NEW_TOURNAMENT_DRAFT_TIMING = "newTournamentDraftTiming";
    public static final String KEY_NEW_TOURNAMENT_ALLOW_SPECTATORS = "newTournamentAllowSpectators";

    // pref setting for deck generator
    public static final String KEY_NEW_DECK_GENERATOR_DECK_SIZE = "newDeckGeneratorDeckSize";
    public static final String KEY_NEW_DECK_GENERATOR_SET = "newDeckGeneratorSet";

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

    public static final String KEY_AVATAR = "selectedId";

    private static final Map<String, String> cache = new HashMap<>();

    private static final Boolean UPDATE_CACHE_POLICY = Boolean.TRUE;

    public static final String OPEN_CONNECTION_TAB = "Open-Connection-Tab";

    

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

    private static class ImageFileFilter extends FileFilter{
        
        @Override
        public boolean accept(File f) {
            String filename = f.getName();
            if(f.isDirectory()){
                return true;
            }
            if(filename != null){
                if(filename.endsWith(".jpg") || filename.endsWith(".jpeg") ||
                        filename.endsWith(".png") || filename.endsWith(".bmp")){
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
        
        cbPreferedImageLanguage.setModel(new DefaultComboBoxModel<>(new String[] {"en","de","fr","it","es","pt","jp","cn","ru","tw","ko"}));
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabsPanel = new javax.swing.JTabbedPane();
        tabMain = new javax.swing.JPanel();
        main_card = new javax.swing.JPanel();
        displayBigCardsInHand = new javax.swing.JCheckBox();
        showToolTipsInAnyZone = new javax.swing.JCheckBox();
        main_game = new javax.swing.JPanel();
        nonLandPermanentsInOnePile = new javax.swing.JCheckBox();
        showPlayerNamesPermanently = new javax.swing.JCheckBox();
        showAbilityPickerForced = new javax.swing.JCheckBox();
        main_gamelog = new javax.swing.JPanel();
        cbGameLogAutoSave = new javax.swing.JCheckBox();
        tabPhases = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        checkBoxUpkeepYou = new javax.swing.JCheckBox();
        checkBoxDrawYou = new javax.swing.JCheckBox();
        checkBoxMainYou = new javax.swing.JCheckBox();
        checkBoxBeforeCYou = new javax.swing.JCheckBox();
        checkBoxEndOfCYou = new javax.swing.JCheckBox();
        checkBoxMain2You = new javax.swing.JCheckBox();
        checkBoxEndTurnYou = new javax.swing.JCheckBox();
        checkBoxUpkeepOthers = new javax.swing.JCheckBox();
        checkBoxDrawOthers = new javax.swing.JCheckBox();
        checkBoxMainOthers = new javax.swing.JCheckBox();
        checkBoxBeforeCOthers = new javax.swing.JCheckBox();
        checkBoxEndOfCOthers = new javax.swing.JCheckBox();
        checkBoxMain2Others = new javax.swing.JCheckBox();
        checkBoxEndTurnOthers = new javax.swing.JCheckBox();
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
        cbEnableOtherSounds = new javax.swing.JCheckBox();
        sounds_backgroundMusic = new javax.swing.JPanel();
        cbEnableBattlefieldBGM = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        txtBattlefieldIBGMPath = new javax.swing.JTextField();
        btnBattlefieldBGMBrowse = new javax.swing.JButton();
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
        tabAvatars = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
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
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");

        main_card.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card"));
        main_card.setLayout(new java.awt.BorderLayout());

        displayBigCardsInHand.setText("Use big images (for high resolution screens)");
        displayBigCardsInHand.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        displayBigCardsInHand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayBigCardsInHandActionPerformed(evt);
            }
        });
        main_card.add(displayBigCardsInHand, java.awt.BorderLayout.PAGE_START);

        showToolTipsInAnyZone.setSelected(true);
        showToolTipsInAnyZone.setText("Show card tooltips while hoovering with the mouse pointer over a card");
        showToolTipsInAnyZone.setToolTipText("");
        showToolTipsInAnyZone.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        showToolTipsInAnyZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showToolTipsInAnyZoneActionPerformed(evt);
            }
        });
        main_card.add(showToolTipsInAnyZone, java.awt.BorderLayout.CENTER);

        main_game.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game"));
        main_game.setLayout(new java.awt.BorderLayout());

        nonLandPermanentsInOnePile.setSelected(true);
        nonLandPermanentsInOnePile.setLabel("Put non-land permanents in one pile");
        nonLandPermanentsInOnePile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonLandPermanentsInOnePileActionPerformed(evt);
            }
        });
        main_game.add(nonLandPermanentsInOnePile, java.awt.BorderLayout.PAGE_START);
        nonLandPermanentsInOnePile.getAccessibleContext().setAccessibleName("nonLandPermanentsInOnePile");

        showPlayerNamesPermanently.setSelected(true);
        showPlayerNamesPermanently.setText("Show player names on avatar permanently");
        showPlayerNamesPermanently.setToolTipText("Instead showing the names only if you hoover over the avatar with the mouse, the name is shown all the time.");
        showPlayerNamesPermanently.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showPlayerNamesPermanently.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPlayerNamesPermanentlyActionPerformed(evt);
            }
        });
        main_game.add(showPlayerNamesPermanently, java.awt.BorderLayout.LINE_START);

        showAbilityPickerForced.setSelected(true);
        showAbilityPickerForced.setText("Show ability picker for abilities or spells without costs");
        showAbilityPickerForced.setToolTipText("This prevents you from accidently activating abilities without other costs than tapping or casting spells with 0 mana costs.");
        showAbilityPickerForced.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showAbilityPickerForced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAbilityPickerForcedActionPerformed(evt);
            }
        });
        main_game.add(showAbilityPickerForced, java.awt.BorderLayout.PAGE_END);

        main_gamelog.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game log"));
        main_gamelog.setLayout(new java.awt.BorderLayout());

        cbGameLogAutoSave.setSelected(true);
        cbGameLogAutoSave.setText("Auto save game logs     (to \"../Mage.Client/gamelogs/\" directory)");
        cbGameLogAutoSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGameLogAutoSaveActionPerformed(evt);
            }
        });
        main_gamelog.add(cbGameLogAutoSave, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout tabMainLayout = new javax.swing.GroupLayout(tabMain);
        tabMain.setLayout(tabMainLayout);
        tabMainLayout.setHorizontalGroup(
            tabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(main_card, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(main_game, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                    .addComponent(main_gamelog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabMainLayout.setVerticalGroup(
            tabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(main_card, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(main_game, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(main_gamelog, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        main_card.getAccessibleContext().setAccessibleName("Game panel");

        tabsPanel.addTab("Main", tabMain);

        jLabel1.setText("Choose phases MAGE will stop on:");

        jLabel2.setText("Upkeep:");

        jLabel3.setText("Draw:");

        jLabel4.setText("Main:");

        jLabel5.setText("Before combat:");

        jLabel6.setText("End of combat:");

        jLabel7.setText("Main 2:");

        jLabel8.setText("End of turn:");

        jLabel9.setText("Your turn");

        jLabel10.setText("Opponent(s) turn");

        javax.swing.GroupLayout tabPhasesLayout = new javax.swing.GroupLayout(tabPhases);
        tabPhases.setLayout(tabPhasesLayout);
        tabPhasesLayout.setHorizontalGroup(
            tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPhasesLayout.createSequentialGroup()
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPhasesLayout.createSequentialGroup()
                                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(77, 77, 77)
                                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabPhasesLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel9)
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel10))
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
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        tabPhasesLayout.setVerticalGroup(
            tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPhasesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkBoxUpkeepOthers))
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(tabPhasesLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(20, 20, 20))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkBoxUpkeepYou)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(checkBoxDrawYou)
                    .addComponent(checkBoxDrawOthers))
                .addGap(4, 4, 4)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(checkBoxMainYou)
                    .addComponent(checkBoxMainOthers))
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkBoxBeforeCYou, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(tabPhasesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(checkBoxBeforeCOthers)))
                .addGap(7, 7, 7)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(checkBoxEndOfCYou)
                    .addComponent(checkBoxEndOfCOthers))
                .addGap(9, 9, 9)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(checkBoxMain2You)
                    .addComponent(checkBoxMain2Others))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabPhasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkBoxEndTurnYou)
                    .addComponent(jLabel8)
                    .addComponent(checkBoxEndTurnOthers))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        tabsPanel.addTab("Phases", tabPhases);

        panelCardImages.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card images:"));

        cbUseDefaultImageFolder.setText("Use default location to save images");
        cbUseDefaultImageFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUseDefaultImageFolderActionPerformed(evt);
            }
        });

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
                .addGap(0, 21, Short.MAX_VALUE))
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
                .addContainerGap(54, Short.MAX_VALUE))
        );

        tabsPanel.addTab("Images", tabImages);

        sounds_clips.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Clips"));
        sounds_clips.setLayout(new java.awt.BorderLayout());

        cbEnableGameSounds.setText("Enable game sounds");
        cbEnableGameSounds.setToolTipText("Sounds that will be played for certain actions (e.g. play land, attack, etc.) during the game.");
        cbEnableGameSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEnableGameSoundsActionPerformed(evt);
            }
        });
        sounds_clips.add(cbEnableGameSounds, java.awt.BorderLayout.PAGE_START);

        cbEnableOtherSounds.setText("Enable other sounds");
        cbEnableOtherSounds.setToolTipText("Sounds that will be played for actions outside of games (e.g. whisper, player joins your game, player submits a deck ...).");
        cbEnableOtherSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEnableOtherSoundsActionPerformed(evt);
            }
        });
        sounds_clips.add(cbEnableOtherSounds, java.awt.BorderLayout.PAGE_END);

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
                .addComponent(txtBattlefieldIBGMPath, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
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
                    .addComponent(sounds_clips, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                    .addComponent(sounds_backgroundMusic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabSoundsLayout.setVerticalGroup(
            tabSoundsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSoundsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sounds_clips, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sounds_backgroundMusic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
        );

        sounds_clips.getAccessibleContext().setAccessibleDescription("");

        tabsPanel.addTab("Sounds", tabSounds);

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
                .addGap(29, 29, 29)
                .addComponent(rememberPswd)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(34, 34, 34))
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addContainerGap()
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
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
                .addContainerGap())
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

        javax.swing.GroupLayout tabConnectionLayout = new javax.swing.GroupLayout(tabConnection);
        tabConnection.setLayout(tabConnectionLayout);
        tabConnectionLayout.setHorizontalGroup(
            tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabConnectionLayout.createSequentialGroup()
                .addGroup(tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabConnectionLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblProxyType)
                        .addGap(18, 18, 18)
                        .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabConnectionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlProxySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabConnectionLayout.setVerticalGroup(
            tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabConnectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabConnectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProxyType)
                    .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlProxySettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        pnlProxySettings.getAccessibleContext().setAccessibleDescription("");

        tabsPanel.addTab("Connection", tabConnection);

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

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Choose your avatar:");

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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13))))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jScrollPane1.setViewportView(jPanel9);

        javax.swing.GroupLayout tabAvatarsLayout = new javax.swing.GroupLayout(tabAvatars);
        tabAvatars.setLayout(tabAvatarsLayout);
        tabAvatarsLayout.setHorizontalGroup(
            tabAvatarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
        );
        tabAvatarsLayout.setVerticalGroup(
            tabAvatarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        tabsPanel.addTab("Avatars", tabAvatars);

        saveButton.setLabel("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        exitButton.setLabel("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsPanel)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(saveButton))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        Preferences prefs = MageFrame.getPreferences();

        // main
        save(prefs, dialog.showToolTipsInAnyZone, KEY_SHOW_TOOLTIPS_ANY_ZONE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.displayBigCardsInHand, KEY_HAND_USE_BIG_CARDS, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showAbilityPickerForced, KEY_SHOW_ABILITY_PICKER_FORCED, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true", "false", UPDATE_CACHE_POLICY);

        // Phases
        save(prefs, dialog.checkBoxUpkeepYou, PhaseManager.UPKEEP_YOU);
        save(prefs, dialog.checkBoxDrawYou, PhaseManager.DRAW_YOU);
        save(prefs, dialog.checkBoxMainYou, PhaseManager.MAIN_YOU);
        save(prefs, dialog.checkBoxBeforeCYou, PhaseManager.BEFORE_COMBAT_YOU);
        save(prefs, dialog.checkBoxEndOfCYou, PhaseManager.END_OF_COMBAT_YOU);
        save(prefs, dialog.checkBoxMain2You, PhaseManager.MAIN_2_YOU);
        save(prefs, dialog.checkBoxEndTurnYou, PhaseManager.END_OF_TURN_YOU);

        save(prefs, dialog.checkBoxUpkeepOthers, PhaseManager.UPKEEP_OTHERS);
        save(prefs, dialog.checkBoxDrawOthers, PhaseManager.DRAW_OTHERS);
        save(prefs, dialog.checkBoxMainOthers, PhaseManager.MAIN_OTHERS);
        save(prefs, dialog.checkBoxBeforeCOthers, PhaseManager.BEFORE_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxEndOfCOthers, PhaseManager.END_OF_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxMain2Others, PhaseManager.MAIN_2_OTHERS);
        save(prefs, dialog.checkBoxEndTurnOthers, PhaseManager.END_OF_TURN_OTHERS);

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

        // Avatar
        if (available_avatars.contains(selectedAvatarId)) {
            prefs.put(KEY_AVATAR, String.valueOf(selectedAvatarId));
            updateCache(KEY_AVATAR, String.valueOf(selectedAvatarId));
        }

        try {
            MageFrame.getSession().updatePreferencesForServer(
                        getSelectedAvatar(),
                        dialog.showAbilityPickerForced.isSelected());

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
        if(cbEnableBattlefieldBGM.isSelected()){
            txtBattlefieldIBGMPath.setEnabled(true);
            btnBattlefieldBGMBrowse.setEnabled(true);
        }else{
            txtBattlefieldIBGMPath.setEnabled(false);
            btnBattlefieldBGMBrowse.setEnabled(false);
        }
    }//GEN-LAST:event_cbEnableBattlefieldBGMActionPerformed
    
    private void cbUseDefaultBackgroundActionPerformed(java.awt.event.ActionEvent evt) {
        if(cbUseDefaultBackground.isSelected()){
            useDefaultBackgroundImage();
        }else{
            useSelectBackgroundImage();
        }
    }                                                    

    private void useDefaultBackgroundImage(){
        txtBackgroundImagePath.setEnabled(false);
        btnBrowseBackgroundImage.setEnabled(false);
        txtBackgroundImagePath.setText("");
    }
    
    private void useSelectBackgroundImage(){
        String path = cache.get(KEY_BACKGROUND_IMAGE);
        dialog.txtBackgroundImagePath.setText(path);
        txtBackgroundImagePath.setEnabled(true);
        btnBrowseBackgroundImage.setEnabled(true);
    }
    
    private void cbUseDefaultBattleImageActionPerformed(java.awt.event.ActionEvent evt) {
        if(cbUseDefaultBattleImage.isSelected()){
            useDefaultBattlefield();
        }else{
            useSelectedOrRandom();
        }
    }                                                     
    
    private void useDefaultBattlefield(){
        cbUseRandomBattleImage.setEnabled(false);
        txtBattlefieldImagePath.setEnabled(false);
        btnBrowseBattlefieldImage.setEnabled(false);
    }
    
    private void useSelectedOrRandom(){
        cbUseRandomBattleImage.setEnabled(true);
        String temp = cache.get(KEY_BATTLEFIELD_IMAGE_RANDOM);
        if(temp != null){
            if(temp.equals("true")){
                useRandomBattleField();
                cbUseRandomBattleImage.setSelected(true);
            }else{
                useSelectedBattleField();
                cbUseRandomBattleImage.setSelected(false);
            }
        }else{
             useSelectedBattleField();
             cbUseRandomBattleImage.setSelected(false);
        }
    }
    
    private void cbUseRandomBattleImageActionPerformed(java.awt.event.ActionEvent evt) {
        if(cbUseRandomBattleImage.isSelected()){
            useRandomBattleField();
        }else{
            useSelectedBattleField();
        }
    }                                                    

    private void useRandomBattleField(){
        txtBattlefieldImagePath.setEnabled(false);
        btnBrowseBattlefieldImage.setEnabled(false);
    }
    
    private void useSelectedBattleField(){
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

    private void showToolTipsInAnyZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToolTipsInAnyZoneActionPerformed

    }//GEN-LAST:event_showToolTipsInAnyZoneActionPerformed

    private void showAbilityPickerForcedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAbilityPickerForcedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showAbilityPickerForcedActionPerformed

    private void cbEnableOtherSoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEnableOtherSoundsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEnableOtherSoundsActionPerformed

    private void showProxySettings() {
        if (cbProxyType.getSelectedItem() == Connection.ProxyType.SOCKS) {
            this.pnlProxy.setVisible(true);
            this.pnlProxySettings.setVisible(true);
        }
        else if (cbProxyType.getSelectedItem() == Connection.ProxyType.HTTP) {
            this.pnlProxy.setVisible(true);
            this.pnlProxySettings.setVisible(true);
        }
        else if (cbProxyType.getSelectedItem() == Connection.ProxyType.NONE) {
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
                param = 3;
            }
        }
        final int openedTab = param;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!dialog.isVisible()) {
                    Preferences prefs = MageFrame.getPreferences();

                    // Phases
                    loadPhases(prefs);

                    // Images
                    loadImagesSettings(prefs);

                    // Sounds
                    loadSoundSettings(prefs);

                    // Connection
                    loadProxySettings(prefs);

                    // Selected avatar
                    loadSelectedAvatar(prefs);

                    // open specified tab before displaying
                    openTab(openedTab);

                    dialog.setLocation(300, 200);
                    dialog.reset();

                    dialog.setVisible(true);
                } else {
                    dialog.requestFocus();
                }
            }
        });
    }

    private static void loadPhases(Preferences prefs) {
        load(prefs, dialog.checkBoxUpkeepYou, PhaseManager.UPKEEP_YOU);
        load(prefs, dialog.checkBoxDrawYou, PhaseManager.DRAW_YOU);
        load(prefs, dialog.checkBoxMainYou, PhaseManager.MAIN_YOU);
        load(prefs, dialog.checkBoxBeforeCYou, PhaseManager.BEFORE_COMBAT_YOU);
        load(prefs, dialog.checkBoxEndOfCYou, PhaseManager.END_OF_COMBAT_YOU);
        load(prefs, dialog.checkBoxMain2You, PhaseManager.MAIN_2_YOU);
        load(prefs, dialog.checkBoxEndTurnYou, PhaseManager.END_OF_TURN_YOU);

        load(prefs, dialog.checkBoxUpkeepOthers, PhaseManager.UPKEEP_OTHERS);
        load(prefs, dialog.checkBoxDrawOthers, PhaseManager.DRAW_OTHERS);
        load(prefs, dialog.checkBoxMainOthers, PhaseManager.MAIN_OTHERS);
        load(prefs, dialog.checkBoxBeforeCOthers, PhaseManager.BEFORE_COMBAT_OTHERS);
        load(prefs, dialog.checkBoxEndOfCOthers, PhaseManager.END_OF_COMBAT_OTHERS);
        load(prefs, dialog.checkBoxMain2Others, PhaseManager.MAIN_2_OTHERS);
        load(prefs, dialog.checkBoxEndTurnOthers, PhaseManager.END_OF_TURN_OTHERS);

        load(prefs, dialog.displayBigCardsInHand, KEY_HAND_USE_BIG_CARDS, "true");
        load(prefs, dialog.showToolTipsInAnyZone, KEY_SHOW_TOOLTIPS_ANY_ZONE, "true");
        load(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true");
        load(prefs, dialog.showPlayerNamesPermanently, KEY_SHOW_PLAYER_NAMES_PERMANENTLY, "true");
        load(prefs, dialog.showAbilityPickerForced, KEY_SHOW_ABILITY_PICKER_FORCED, "true");
        load(prefs, dialog.cbGameLogAutoSave, KEY_GAME_LOG_AUTO_SAVE, "true");
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
        if(prop.equals("true")){
            dialog.cbUseDefaultBackground.setSelected(true);
            dialog.useDefaultBackgroundImage();
        }else{
            dialog.cbUseDefaultBackground.setSelected(false);
            dialog.useSelectBackgroundImage();
            String path = prefs.get(KEY_BACKGROUND_IMAGE, "");
            dialog.txtBackgroundImagePath.setText(path);
            updateCache(KEY_BACKGROUND_IMAGE, path);
        }
        prop = prefs.get(KEY_BATTLEFIELD_IMAGE_DEFAULT, "true");
        if(prop.equals("true")){
            dialog.cbUseDefaultBattleImage.setSelected(true);
            dialog.useDefaultBattlefield();        
        }else{
            dialog.cbUseDefaultBattleImage.setSelected(false);
            dialog.useSelectedOrRandom();
        }
        prop = prefs.get(KEY_BATTLEFIELD_IMAGE_RANDOM, "true");
        
        if(dialog.cbUseRandomBattleImage.isEnabled()) {
            if(prop.equals("true")){
                dialog.cbUseRandomBattleImage.setSelected(true);
                dialog.useRandomBattleField();
            }else{
                dialog.cbUseRandomBattleImage.setSelected(false);
                dialog.useSelectedBattleField();
                String path = prefs.get(KEY_BATTLEFIELD_IMAGE, "");
                dialog.txtBattlefieldImagePath.setText(path);
                updateCache(KEY_BATTLEFIELD_IMAGE, path);
            }
        }   
    }

    private static void loadSoundSettings(Preferences prefs) {
        String prop = prefs.get(KEY_SOUNDS_GAME_ON, "true");
        if (prop.equals("true")) {
            dialog.cbEnableGameSounds.setSelected(true);
        } else {
            dialog.cbEnableGameSounds.setSelected(false);
        }
        prop = prefs.get(KEY_SOUNDS_OTHER_ON, "true");
        if (prop.equals("true")) {
            dialog.cbEnableOtherSounds.setSelected(true);
        } else {
            dialog.cbEnableOtherSounds.setSelected(false);
        }

        // Match music
        prop = prefs.get(KEY_SOUNDS_MATCH_MUSIC_ON, "true");
        if (prop.equals("true")) {
            dialog.cbEnableBattlefieldBGM.setSelected(true);
        } else {
            dialog.cbEnableBattlefieldBGM.setSelected(false);
        }
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

    private static void openTab(int index) {
        try {
            if (index > 0) {
                dialog.tabsPanel.setSelectedIndex(3);
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
        if(!dialog.cbUseDefaultBackground.isSelected()){
            String path = dialog.txtBackgroundImagePath.getText();
            prefs.put(KEY_BACKGROUND_IMAGE, path);
            updateCache(KEY_BACKGROUND_IMAGE, path);
        }
        if(!dialog.cbUseDefaultBattleImage.isSelected() && !dialog.cbUseRandomBattleImage.isSelected()){
            String path = dialog.txtBattlefieldImagePath.getText();
            prefs.put(KEY_BATTLEFIELD_IMAGE, path);
            updateCache(KEY_BATTLEFIELD_IMAGE, path);
        }
    }
    
    private static void saveSoundPath(Preferences prefs){
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
        load(prefs, checkBox, propName, PhaseManager.PHASE_ON);
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName) {
        save(prefs, checkBox, propName, PhaseManager.PHASE_ON, PhaseManager.PHASE_OFF, false);
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
            addAvatar(jPanel11, 9,  false, false);
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
                        MageFrame.getSession().updatePreferencesForServer(id, PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_TOOLTIPS_ANY_ZONE, "true").equals("true"));
                    }
                }
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBattlefieldBGMBrowse;
    private javax.swing.JButton btnBrowseBackgroundImage;
    private javax.swing.JButton btnBrowseBattlefieldImage;
    private javax.swing.JButton btnBrowseImageLocation;
    private javax.swing.JCheckBox cbCheckForNewImages;
    private javax.swing.JCheckBox cbEnableBattlefieldBGM;
    private javax.swing.JCheckBox cbEnableGameSounds;
    private javax.swing.JCheckBox cbEnableOtherSounds;
    private javax.swing.JCheckBox cbGameLogAutoSave;
    private javax.swing.JComboBox<String> cbPreferedImageLanguage;
    private javax.swing.JComboBox<ProxyType> cbProxyType;
    private javax.swing.JCheckBox cbSaveToZipFiles;
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
    private javax.swing.JCheckBox displayBigCardsInHand;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelPreferedImageLanguage;
    private javax.swing.JLabel lblProxyPassword;
    private javax.swing.JLabel lblProxyPort;
    private javax.swing.JLabel lblProxyServer;
    private javax.swing.JLabel lblProxyType;
    private javax.swing.JLabel lblProxyUserName;
    private javax.swing.JPanel main_card;
    private javax.swing.JPanel main_game;
    private javax.swing.JPanel main_gamelog;
    private javax.swing.JCheckBox nonLandPermanentsInOnePile;
    private javax.swing.JPanel panelBackgroundImages;
    private javax.swing.JPanel panelCardImages;
    private javax.swing.JPanel pnlProxy;
    private javax.swing.JPanel pnlProxySettings;
    private javax.swing.JCheckBox rememberPswd;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox showAbilityPickerForced;
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
    // End of variables declaration//GEN-END:variables

    private static final PreferencesDialog dialog = new PreferencesDialog(new javax.swing.JFrame(), true);

    static {
        dialog.setResizable(false);
    }
}
