package mage.client;

import mage.MageException;
import mage.cards.action.ActionCallback;
import mage.cards.decks.Deck;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.cards.repository.RepositoryUtil;
import mage.client.cards.BigCard;
import mage.client.chat.ChatPanelBasic;
import mage.client.components.*;
import mage.client.components.ext.dlg.DialogManager;
import mage.client.components.tray.MageTray;
import mage.client.constants.Constants;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.deckeditor.DeckEditorPane;
import mage.client.deckeditor.collection.viewer.CollectionViewerPane;
import mage.client.dialog.*;
import mage.client.draft.DraftPane;
import mage.client.draft.DraftPanel;
import mage.client.game.GamePane;
import mage.client.game.GamePanel;
import mage.client.game.PlayAreaPanel;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.preference.MagePreferences;
import mage.client.remote.CallbackClientImpl;
import mage.client.table.TablesPane;
import mage.client.table.TablesPanel;
import mage.client.tournament.TournamentPane;
import mage.client.util.*;
import mage.client.util.audio.MusicPlayer;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.GuiDisplayUtil;
import mage.client.util.gui.countryBox.CountryUtil;
import mage.client.util.sets.ConstructedFormats;
import mage.client.util.stats.UpdateMemUsageTask;
import mage.components.ImagePanel;
import mage.components.ImagePanelStyle;
import mage.constants.PlayerAction;
import mage.cards.RateCard;
import mage.interfaces.MageClient;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import mage.util.DebugUtil;
import mage.util.ThreadUtils;
import mage.util.XMageThreadFactory;
import mage.utils.MageVersion;
import mage.view.GameEndView;
import mage.view.UserRequestMessage;
import net.java.truevfs.access.TArchiveDetector;
import net.java.truevfs.access.TConfig;
import net.java.truevfs.kernel.spec.FsAccessOption;
import org.apache.log4j.Logger;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.SvgUtils;
import org.mage.plugins.card.images.DownloadPicturesService;
import org.mage.plugins.card.info.CardInfoPaneImpl;
import org.mage.plugins.card.utils.CardImageUtils;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

/**
 * Client app
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class MageFrame extends javax.swing.JFrame implements MageClient {

    private static final String TITLE_NAME = "XMage";

    private static final Logger LOGGER = Logger.getLogger(MageFrame.class);
    private static final String LITE_MODE_ARG = "-lite";
    private static final String GRAY_MODE_ARG = "-gray";
    private static final String FULL_SCREEN_PROP = "xmage.fullScreen"; // -Dxmage.fullScreen=false
    private static final String SKIP_DONE_SYMBOLS = "-skipDoneSymbols";
    private static final String USER_ARG = "-user";
    private static final String PASSWORD_ARG = "-pw";
    private static final String SERVER_ARG = "-server";
    private static final String PORT_ARG = "-port";
    private static final String DEBUG_ARG = "-debug"; // enable debug button in main menu

    private static final String NOT_CONNECTED_TEXT = "<not connected>";
    private static final String NOT_CONNECTED_BUTTON = "CONNECT TO SERVER";
    private static MageFrame instance;

    private final ConnectDialog connectDialog;
    private final ErrorDialog errorDialog;
    private static CallbackClient callbackClient;
    private static final Preferences PREFS = Preferences.userNodeForPackage(MageFrame.class);
    private final JPanel fakeTopPanel;
    private JLabel title;
    private Rectangle titleRectangle;
    private static final MageVersion VERSION = new MageVersion(MageFrame.class);
    private Connection currentConnection;
    private static MagePane activeFrame;
    private static boolean liteMode = false;
    //TODO: make gray theme, implement theme selector in preferences dialog
    private static boolean grayMode = false;
    private static boolean macOsFullScreenEnabled = true;
    private static boolean skipSmallSymbolGenerationForExisting = false;
    private static String startUser = null;
    private static String startPassword = "";
    private static String startServer = "localhost";
    private static int startPort = -1;
    private static boolean debugMode = false;

    private JToggleButton switchPanelsButton = null; // from main menu
    private static String SWITCH_PANELS_BUTTON_NAME = "Switch panels";

    private static final Map<UUID, ChatPanelBasic> CHATS = new HashMap<>();
    private static final Map<UUID, GamePanel> GAMES = new HashMap<>();
    private static final Map<UUID, DraftPanel> DRAFTS = new HashMap<>();
    private static final MageUI UI = new MageUI();

    private static final ScheduledExecutorService PING_SENDER_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            new XMageThreadFactory(ThreadUtils.THREAD_PREFIX_CLIENT_PING_SENDER)
    );
    private static UpdateMemUsageTask updateMemUsageTask;

    private static long startTime;

    /**
     * @return the session
     */
    public static JDesktopPane getDesktop() {
        return desktopPane;
    }

    public static Preferences getPreferences() {
        return PREFS;
    }

    public static boolean isLite() {
        return liteMode;
    }

    public static boolean isGray() {
        return grayMode;
    }

    public static boolean isSkipSmallSymbolGenerationForExisting() {
        return skipSmallSymbolGenerationForExisting;
    }

    @Override
    public MageVersion getVersion() {
        return VERSION;
    }

    public static MageFrame getInstance() {
        return instance;
    }

    private void handleEvent(AWTEvent event) {
        MagePane frame = activeFrame;

        // support multiple mage panes
        Object source = event.getSource();
        if (source instanceof Component) {
            Component component = (Component) source;
            while (component != null) {
                if (component instanceof MagePane) {
                    frame = (MagePane) component;
                    break;
                }
                component = component.getParent();
            }
        }

        if (frame != null) {
            frame.handleEvent(event);
        }
    }

    public MageFrame() throws MageException {
        File cacertsFile = new File(System.getProperty("user.dir") + "/release/cacerts").getAbsoluteFile();
        if (!cacertsFile.exists()) { // When running from the jar file the contents of the /release folder will have been expanded into the home folder as part of packaging
            cacertsFile = new File(System.getProperty("user.dir") + "/cacerts").getAbsoluteFile();
        }
        if (cacertsFile.exists()) {
            LOGGER.info("Custom (or bundled) Java certificate file (cacerts) file found");
            String cacertsPath = cacertsFile.getPath();
            System.setProperty("javax.net.ssl.trustStore", cacertsPath);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        } else {
            LOGGER.info("custom Java certificate file not found at: " + cacertsFile.getAbsolutePath());
        }

        setWindowTitle();

        // mac os only: enable full screen support in java 8 (java 11+ try to use it all the time)
        if (MacFullscreenUtil.isMacOSX() && macOsFullScreenEnabled) {
            MacFullscreenUtil.enableMacOSFullScreenMode(this);
            MacFullscreenUtil.toggleMacOSFullScreenMode(this);
        }

        EDTExceptionHandler.registerExceptionHandler();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> handleEvent(event), AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);

        TConfig config = TConfig.current();
        config.setArchiveDetector(new TArchiveDetector("zip"));
        config.setAccessPreference(FsAccessOption.STORE, true);

        // apply current theme
        GUISizeHelper.calculateGUISizes();
        GuiDisplayUtil.refreshThemeSettings();

        // workaround to stop JSplitPane from eating F6 and F8 or any other function keys
        Object value = UIManager.get("SplitPane.ancestorInputMap");
        if (value instanceof InputMap) {
            InputMap map = (InputMap) value;
            for (int vk = KeyEvent.VK_F2; vk <= KeyEvent.VK_F12; ++vk) {
                map.remove(KeyStroke.getKeyStroke(vk, 0));
            }
        }

        // other settings
        if (ClientCallback.SIMULATE_BAD_CONNECTION) {
            LOGGER.info("Network: bad connection mode enabled");
        }

        // DATA PREPARE
        RepositoryUtil.bootstrapLocalDb();
        // re-create database on empty (e.g. after new build cleaned db on startup)
        if (RepositoryUtil.CARD_DB_RECREATE_BY_CLIENT_SIDE && RepositoryUtil.isDatabaseEmpty()) {
            LOGGER.info("DB: creating cards database (it can take few minutes)...");
            CardScanner.scan();
            LOGGER.info("Done.");
        }

        // IMAGES CHECK
        LOGGER.info("Images: search broken files...");
        CardImageUtils.checkAndFixImageFiles();

        bootstrapSetsAndFormats();

        if (RateCard.PRELOAD_CARD_RATINGS_ON_STARTUP) {
            RateCard.bootstrapCardsAndRatings();
        }
        SvgUtils.checkSvgSupport();
        ManaSymbols.loadImages();
        Plugins.instance.loadPlugins();
        if (!Plugins.instance.isCardPluginLoaded()) {
            throw new MageException("can't load card plugin");
        }

        initComponents();

        // auto-update switch panels button with actual stats
        desktopPane.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                if (desktopPane.getLayer(e.getComponent()) == JLayeredPane.DEFAULT_LAYER) {
                    updateSwitchPanelsButton();
                }
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                if (desktopPane.getLayer(e.getComponent()) == JLayeredPane.DEFAULT_LAYER) {
                    updateSwitchPanelsButton();
                }
            }
        });

        desktopPane.setDesktopManager(new MageDesktopManager());

        setSize(1024, 768);
        SettingsManager.instance.setScreenWidthAndHeight(1024, 768);
        DialogManager.updateParams(768, 1024, false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        SessionHandler.startSession(this);
        callbackClient = new CallbackClientImpl(this);
        connectDialog = new ConnectDialog();
        desktopPane.add(connectDialog, JLayeredPane.MODAL_LAYER);
        errorDialog = new ErrorDialog();
        errorDialog.setLocation(100, 100);
        desktopPane.add(errorDialog, JLayeredPane.MODAL_LAYER);

        PING_SENDER_EXECUTOR.scheduleAtFixedRate(SessionHandler::ping, TablesPanel.PING_SERVER_SECS, TablesPanel.PING_SERVER_SECS, TimeUnit.SECONDS);

        updateMemUsageTask = new UpdateMemUsageTask(jMemUsageLabel);

        // create default server lobby and hide it until connect
        tablesPane = new TablesPane();
        desktopPane.add(tablesPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        SwingUtilities.invokeLater(this::hideServerLobby);

        // save links for global/shared components
        UI.addComponent(MageComponents.DESKTOP_PANE, desktopPane);
        UI.addComponent(MageComponents.DESKTOP_TOOLBAR, mageToolbar);

        addTooltipContainer();
        setBackground();
        addMageLabel();
        setAppIcon();
        MageTray.instance.install();

        // transparent top panel to fix swing bugs with other panel drawing and events processing on some systems like macOS
        fakeTopPanel = new JPanel();
        fakeTopPanel.setVisible(true);
        fakeTopPanel.setOpaque(false);
        fakeTopPanel.setLayout(null);
        desktopPane.add(fakeTopPanel, JLayeredPane.DRAG_LAYER);

        desktopPane.add(ArrowBuilder.getBuilder().getArrowsManagerPanel(), JLayeredPane.PALETTE_LAYER);


        desktopPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = ((JComponent) e.getSource()).getWidth();
                int height = ((JComponent) e.getSource()).getHeight();
                SettingsManager.instance.setScreenWidthAndHeight(width, height);
                if (!liteMode && !grayMode) {
                    backgroundPane.setSize(width, height);
                }

                updateCurrentFrameSize();

                ArrowBuilder.getBuilder().setSize(width, height);
                fakeTopPanel.setSize(width, height);

                if (title != null) {
                    title.setBounds((int) (width - titleRectangle.getWidth()) / 2, (int) (height - titleRectangle.getHeight()) / 2, titleRectangle.width, titleRectangle.height);
                }
            }
        });

        // tooltips delay in ms
        ToolTipManager.sharedInstance().setDismissDelay(Constants.TOOLTIPS_DELAY_MS);

        mageToolbar.add(createSwitchPanelsButton(), 0);
        mageToolbar.add(new javax.swing.JToolBar.Separator(), 1);

        if (Plugins.instance.isCounterPluginLoaded()) {
            int i = Plugins.instance.getGamesPlayed();
            JLabel label = new JLabel("  Games played: " + i);
            desktopPane.add(label, JLayeredPane.DEFAULT_LAYER + 1);
            label.setVisible(true);
            label.setForeground(Color.white);
            label.setBounds(0, 0, 180, 30);
        }

        setGUISize();
        setConnectButtonText(NOT_CONNECTED_BUTTON);
        SwingUtilities.invokeLater(() -> {
            disableButtons();
            updateMemUsageTask.execute();
            LOGGER.info("Client start up time: " + ((System.currentTimeMillis() - startTime) / 1000 + " seconds"));
            if (autoConnect()) {
                enableButtons();
            } else {
                connectDialog.showDialog();
            }
            setWindowTitle();
        });
    }

    private void bootstrapSetsAndFormats() {
        LOGGER.info("Loading sets and formats...");
        ConstructedFormats.ensureLists();
    }

    private void setWindowTitle() {
        setTitle(TITLE_NAME + "  Client: "
                + (VERSION == null ? "<not available>" : VERSION.toString()) + "  Server: "
                + ((SessionHandler.getSession() != null && SessionHandler.isConnected()) ? SessionHandler.getVersionInfo() : NOT_CONNECTED_TEXT));
    }

    private void updateTooltipContainerSizes() {
        JPanel cardPreviewContainer;
        BigCard bigCard;
        JPanel cardPreviewContainerRotated;
        BigCard bigCardRotated;
        try {
            cardPreviewContainer = (JPanel) UI.getComponent(MageComponents.CARD_PREVIEW_CONTAINER);
            bigCard = (BigCard) UI.getComponent(MageComponents.CARD_PREVIEW_PANE);
            cardPreviewContainerRotated = (JPanel) UI.getComponent(MageComponents.CARD_PREVIEW_CONTAINER_ROTATED);
            bigCardRotated = (BigCard) UI.getComponent(MageComponents.CARD_PREVIEW_PANE_ROTATED);
        } catch (InterruptedException e) {
            LOGGER.fatal("Can't update tooltip panel sizes");
            Thread.currentThread().interrupt();
            return;
        }

        int height = GUISizeHelper.enlargedImageHeight;
        int width = (int) ((float) height * (float) 0.64);
        bigCard.setSize(width, height);
        cardPreviewContainer.setBounds(0, 0, width + 80, height + 30);
        bigCardRotated.setSize(height, width + 30);
        cardPreviewContainerRotated.setBounds(0, 0, height + 80, width + 100 + 30);
    }

    private void addTooltipContainer() {
        JEditorPane cardInfoPane = (JEditorPane) Plugins.instance.getCardInfoPane();
        if (cardInfoPane == null) {
            LOGGER.fatal("Can't find card tooltip plugin");
            return;
        }
        cardInfoPane.setLocation(40, 40);
        cardInfoPane.setBackground(new Color(0, 0, 0, 255)); // use non-transparent background to full draw, see bug example in #12261
        UI.addComponent(MageComponents.CARD_INFO_PANE, cardInfoPane);

        MageRoundPane popupContainer = new MageRoundPane();
        popupContainer.setLayout(null);
        popupContainer.add(cardInfoPane);
        popupContainer.setVisible(false);
        if (DebugUtil.GUI_POPUP_CONTAINER_DRAW_DEBUG_BORDER) {
            popupContainer.setBorder(BorderFactory.createLineBorder(Color.red));
        }
        desktopPane.add(popupContainer, JLayeredPane.POPUP_LAYER);
        UI.addComponent(MageComponents.POPUP_CONTAINER, popupContainer);


        JPanel cardPreviewContainer = new JPanel();
        cardPreviewContainer.setOpaque(false);
        cardPreviewContainer.setLayout(null);
        cardPreviewContainer.setVisible(false);
        desktopPane.add(cardPreviewContainer, JLayeredPane.POPUP_LAYER);
        UI.addComponent(MageComponents.CARD_PREVIEW_CONTAINER, cardPreviewContainer);

        BigCard bigCard = new BigCard();
        bigCard.setLocation(40, 40);
        bigCard.setBackground(new Color(0, 0, 0, 0));
        cardPreviewContainer.add(bigCard);
        UI.addComponent(MageComponents.CARD_PREVIEW_PANE, bigCard);

        JPanel cardPreviewContainerRotated = new JPanel();
        cardPreviewContainerRotated.setOpaque(false);
        cardPreviewContainerRotated.setLayout(null);
        cardPreviewContainerRotated.setVisible(false);
        desktopPane.add(cardPreviewContainerRotated, JLayeredPane.POPUP_LAYER);
        UI.addComponent(MageComponents.CARD_PREVIEW_CONTAINER_ROTATED, cardPreviewContainerRotated);


        BigCard bigCardRotated = new BigCard(true);
        bigCardRotated.setLocation(40, 40);
        bigCardRotated.setBackground(new Color(0, 0, 0, 0));
        cardPreviewContainerRotated.add(bigCardRotated);
        UI.addComponent(MageComponents.CARD_PREVIEW_PANE_ROTATED, bigCardRotated);

        updateTooltipContainerSizes();
    }

    private void setGUISizeTooltipContainer() {
        try {
            int height = GUISizeHelper.enlargedImageHeight;
            int width = (int) ((float) height * (float) 0.64);

            JPanel cardPreviewContainer = (JPanel) UI.getComponent(MageComponents.CARD_PREVIEW_CONTAINER);
            cardPreviewContainer.setBounds(0, 0, width + 80, height + 30);

            BigCard bigCard = (BigCard) UI.getComponent(MageComponents.CARD_PREVIEW_PANE);
            bigCard.setSize(width, height);

            JPanel cardPreviewContainerRotated = (JPanel) UI.getComponent(MageComponents.CARD_PREVIEW_CONTAINER_ROTATED);
            cardPreviewContainerRotated.setBounds(0, 0, height + 80, width + 100 + 30);

            BigCard bigCardRotated = (BigCard) UI.getComponent(MageComponents.CARD_PREVIEW_PANE_ROTATED);
            bigCardRotated.setSize(height, width + 30);

        } catch (Exception e) {
            LOGGER.warn("Error while changing tooltip container size.", e);
        }
    }

    // Sets background for login screen
    private void setBackground() {
        if (liteMode || grayMode) {
            return;
        }

        try {
            // If user has custom background, use that, otherwise, use theme background
            if (Plugins.instance.isThemePluginLoaded() &&
                    !PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BACKGROUND_IMAGE_DEFAULT, "true").equals("true")) {
                backgroundPane = (ImagePanel) Plugins.instance.updateTablePanel(new HashMap<>());
            } else {
                InputStream is = this.getClass().getResourceAsStream(PreferencesDialog.getCurrentTheme().getLoginBackgroundPath());
                BufferedImage background = ImageIO.read(is);
                backgroundPane = new ImagePanel(background, ImagePanelStyle.SCALED);
            }
            backgroundPane.setSize(1024, 768);
            desktopPane.add(backgroundPane, JLayeredPane.DEFAULT_LAYER);
        } catch (IOException e) {
            LOGGER.fatal("Error while setting background.", e);
        }
    }

    public static boolean isChristmasTime(Date currentTime) {
        // from december 15 to january 15
        Calendar cal = new GregorianCalendar();
        cal.setTime(currentTime);

        int currentYear = cal.get(Calendar.YEAR);
        if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
            currentYear = currentYear - 1;
        }

        Date chrisFrom = new GregorianCalendar(currentYear, Calendar.DECEMBER, 15).getTime();
        Date chrisTo = new GregorianCalendar(currentYear + 1, Calendar.JANUARY, 15 + 1).getTime(); // end of the 15 day

        return ((currentTime.equals(chrisFrom) || currentTime.after(chrisFrom))
                && currentTime.before(chrisTo));
    }

    private void addMageLabel() {
        if (liteMode || grayMode) {
            return;
        }

        String filename;
        float ratio;
        if (isChristmasTime(Calendar.getInstance().getTime())) {
            // Christmas logo
            LOGGER.info("Ho Ho Ho, Merry Christmas and a Happy New Year");
            filename = "/label-xmage-christmas.png";
            ratio = 539.0f / 318.0f;
        } else {
            // standard logo
            filename = "/label-xmage.png";
            ratio = 509.0f / 288.0f;
        }

        try {
            InputStream is = this.getClass().getResourceAsStream(filename);
            if (is != null) {
                titleRectangle = new Rectangle(540, (int) (640 / ratio));

                BufferedImage image = ImageIO.read(is);
                //ImageIcon resized = new ImageIcon(image.getScaledInstance(titleRectangle.width, titleRectangle.height, java.awt.Image.SCALE_SMOOTH));
                title = new JLabel();
                title.setIcon(new ImageIcon(image));
                backgroundPane.setLayout(null);
                backgroundPane.add(title);
            }
        } catch (IOException e) {
            LOGGER.fatal("Error while adding mage label.", e);
        }
    }

    private void setAppIcon() {
        Image image = ImageManagerImpl.instance.getAppImage();
        setIconImage(image);
    }

    private AbstractButton createSwitchPanelsButton() {
        this.switchPanelsButton = new JToggleButton(SWITCH_PANELS_BUTTON_NAME);
        this.switchPanelsButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                createAndShowSwitchPanelsMenu((JComponent) e.getSource(), this.switchPanelsButton);
            }
        });
        this.switchPanelsButton.setFocusable(false);
        this.switchPanelsButton.setHorizontalTextPosition(SwingConstants.LEADING);
        return this.switchPanelsButton;
    }

    private void updateSwitchPanelsButton() {
        if (this.switchPanelsButton != null) {
            int totalCount = getPanelsCount(false);
            int activeCount = getPanelsCount(true);
            this.switchPanelsButton.setText(SWITCH_PANELS_BUTTON_NAME + String.format(" (%d)", totalCount));
            this.switchPanelsButton.setToolTipText(String.format("Click to switch between panels (active panels: %d of %d)",
                    activeCount,
                    totalCount
            ));
        }
    }

    private void createAndShowSwitchPanelsMenu(final JComponent component, final AbstractButton windowButton) {
        JPopupMenu menu = new JPopupMenu();
        Component[] windows = desktopPane.getComponentsInLayer(javax.swing.JLayeredPane.DEFAULT_LAYER);
        MagePaneMenuItem menuItem;

        // TODO: sort menu by games, not current component order
        //  lobby -> table 1 tourny, table 1 draft, table 1 game, table 2...
        for (int i = 0; i < windows.length; i++) {
            if (windows[i] instanceof MagePane) {
                MagePane window = (MagePane) windows[i];
                if (window.isVisible()) {
                    menuItem = new MagePaneMenuItem(window);
                    menuItem.setFont(GUISizeHelper.menuFont);
                    menuItem.setState(i == 0);
                    menuItem.addActionListener(ae -> {
                        MagePane frame = ((MagePaneMenuItem) ae.getSource()).getFrame();
                        setActive(frame);
                    });
                    //menuItem.setIcon(window.getFrameIcon());
                    menu.add(menuItem);
                }
            }
        }

        menu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                windowButton.setSelected(false);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                windowButton.setSelected(false);
            }
        });

        menu.show(component, 0, component.getHeight());
    }

    public static boolean isGameActive() {
        return activeFrame instanceof GamePane;
    }

    public static void setActive(MagePane frame) {
        // Always hide not hidden popup window or enlarged card view if a frame is set to active
        try {
            ActionCallback callback = Plugins.instance.getActionCallback();
            if (callback instanceof MageActionCallback) {
                ((MageActionCallback) callback).hideEnlargedCard();
            }
            Component container = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
            if (container.isVisible()) {
                container.setVisible(false);
                container.repaint();
            }
        } catch (InterruptedException e) {
            LOGGER.fatal("MageFrame error", e);
            Thread.currentThread().interrupt();
        }

        // Nothing to do
        if (activeFrame == frame) {
            return;
        }

        // Deactivate current frame if there is one
        if (activeFrame != null) {
            activeFrame.deactivated();
        }
        activeFrame = null;

        // clean resources
        ArrowBuilder.getBuilder().hideAllPanels();
        MusicPlayer.stopBGM();

        // if no new frame to activate (example: disconnection)
        if (frame == null) {
            return;
        }

        LOGGER.debug("Setting " + frame.getTitle() + " active");

        activeFrame = frame;
        desktopPane.moveToFront(activeFrame);
        activeFrame.setBounds(0, 0, desktopPane.getWidth(), desktopPane.getHeight());
        activeFrame.revalidate();
        activeFrame.activated();
        activeFrame.setVisible(true);

        if (activeFrame instanceof GamePane) {
            ArrowBuilder.getBuilder().showPanel(((GamePane) activeFrame).getGameId());
            MusicPlayer.playBGM();
        }
    }

    private void updateCurrentFrameSize() {
        if (activeFrame != null) {
            activeFrame.setBounds(0, 0, desktopPane.getWidth(), desktopPane.getHeight());
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();

        updateCurrentFrameSize();
    }

    public static void deactivate(MagePane frame) {
        frame.setVisible(false);
        setActive(getTopMost(frame));
        if (activeFrame != frame) {
            frame.deactivated();
        }
    }

    public static MagePane getTopMost(MagePane exclude) {
        MagePane topmost = null;
        int best = Integer.MAX_VALUE;
        for (Component frame : desktopPane.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER)) {
            if (frame.isVisible()) {
                int z = desktopPane.getComponentZOrder(frame);
                if (z < best) {
                    // Exclude the tables pane if not connected, we never want to show it when not connected
                    if (frame instanceof MagePane && (SessionHandler.isConnected() || !(frame instanceof TablesPane))) {
                        best = z;
                        if (!frame.equals(exclude)) {
                            topmost = (MagePane) frame;
                        }
                    }
                }
            }
        }
        return topmost;
    }

    /**
     * Shows a game for a player of the game
     *
     * @param gameId
     * @param playerId
     */
    public void showGame(UUID gameId, UUID playerId) {
        GamePane gamePane = new GamePane();
        desktopPane.add(gamePane, JLayeredPane.DEFAULT_LAYER);
        gamePane.setVisible(true);
        gamePane.showGame(gameId, playerId);
        setActive(gamePane);
    }

    public void watchGame(UUID gameId) {
        for (Component component : desktopPane.getComponents()) {
            if (component instanceof GamePane
                    && ((GamePane) component).getGameId().equals(gameId)) {
                setActive((GamePane) component);
                return;
            }
        }
        GamePane gamePane = new GamePane();
        desktopPane.add(gamePane, JLayeredPane.DEFAULT_LAYER);
        gamePane.setVisible(true);
        gamePane.watchGame(gameId);
        setActive(gamePane);
    }

    public void replayGame(UUID gameId) {
        GamePane gamePane = new GamePane();
        desktopPane.add(gamePane, JLayeredPane.DEFAULT_LAYER);
        gamePane.setVisible(true);
        gamePane.replayGame(gameId);
        setActive(gamePane);
    }

    public void showDraft(UUID draftId) {
        DraftPane draftPane = new DraftPane();
        desktopPane.add(draftPane, JLayeredPane.DEFAULT_LAYER);
        draftPane.setVisible(true);
        draftPane.showDraft(draftId);
        setActive(draftPane);
    }

    public void endDraft(UUID draftId) {
        // inform all open draft panes about
        for (Component window : desktopPane.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER)) {
            if (window instanceof DraftPane) {
                DraftPane draftPane = (DraftPane) window;
                draftPane.removeDraft();
            }
        }
    }

    public void showTournament(UUID tournamentId) {
        // existing tourney
        TournamentPane tournamentPane = null;
        for (Component component : desktopPane.getComponents()) {
            if (component instanceof TournamentPane
                    && ((TournamentPane) component).getTournamentId().equals(tournamentId)) {
                tournamentPane = (TournamentPane) component;
            }
        }

        // new tourney
        if (tournamentPane == null) {
            tournamentPane = new TournamentPane();
            desktopPane.add(tournamentPane, JLayeredPane.DEFAULT_LAYER);
            tournamentPane.setVisible(true);
            tournamentPane.showTournament(tournamentId);
        }

        // if user connects on startup then there are possible multiple tables open, so keep only actual
        // priority: game > constructing > draft > tourney
        // TODO: activate panel by priority

        setActive(tournamentPane);
    }

    public void showGameEndDialog(GameEndView gameEndView) {
        GameEndDialog gameEndDialog = new GameEndDialog(gameEndView);
        desktopPane.add(gameEndDialog, JLayeredPane.MODAL_LAYER);
        gameEndDialog.showDialog();
    }

    public void showTableWaitingDialog(UUID roomId, UUID tableId, boolean isTournament) {
        TableWaitingDialog tableWaitingDialog = new TableWaitingDialog();
        desktopPane.add(tableWaitingDialog, JLayeredPane.MODAL_LAYER);
        tableWaitingDialog.showDialog(roomId, tableId, isTournament);
    }

    public static boolean connect(Connection connection) {
        boolean result = SessionHandler.connect(connection);
        MageFrame.getInstance().setWindowTitle();
        return result;
    }

    public static boolean stopConnecting() {
        return SessionHandler.stopConnecting();
    }

    public boolean autoConnect() {
        boolean autoConnectParamValue = startUser != null || Boolean.parseBoolean(PREFS.get("autoConnect", "false"));
        boolean status = false;
        if (autoConnectParamValue) {
            LOGGER.info("Auto-connecting to " + MagePreferences.getServerAddress());
            status = performConnect(false);
        }
        return status;
    }

    private boolean performConnect(boolean reconnect) {
        if (currentConnection == null || !reconnect) {
            String server = MagePreferences.getLastServerAddress();
            int port = MagePreferences.getLastServerPort();
            String userName = MagePreferences.getLastServerUser();
            String password = MagePreferences.getLastServerPassword();
            String proxyServer = PREFS.get("proxyAddress", "");
            int proxyPort = Integer.parseInt(PREFS.get("proxyPort", "0"));
            ProxyType proxyType = ProxyType.valueByText(PREFS.get("proxyType", "None"));
            String proxyUsername = PREFS.get("proxyUsername", "");
            String proxyPassword = PREFS.get("proxyPassword", "");
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            currentConnection = new Connection();
            currentConnection.setUsername(userName);
            currentConnection.setPassword(password);
            currentConnection.setHost(server);
            currentConnection.setPort(port);
            String allMAC = "";
            try {
                allMAC = Connection.getMAC();
            } catch (SocketException ex) {
            }
            currentConnection.setUserIdStr(System.getProperty("user.name") + ":" + System.getProperty("os.name") + ":" + MagePreferences.getUserNames() + ":" + allMAC);
            currentConnection.setProxyType(proxyType);
            currentConnection.setProxyHost(proxyServer);
            currentConnection.setProxyPort(proxyPort);
            currentConnection.setProxyUsername(proxyUsername);
            currentConnection.setProxyPassword(proxyPassword);
            setUserPrefsToConnection(currentConnection);
        }

        try {
            LOGGER.debug("connecting (auto): " + currentConnection.getProxyType().toString()
                    + ' ' + currentConnection.getProxyHost() + ' ' + currentConnection.getProxyPort() + ' ' + currentConnection.getProxyUsername());
            if (MageFrame.connect(currentConnection)) {
                prepareAndShowServerLobby();
                return true;
            } else {
                showMessage("Unable connect to server: " + SessionHandler.getLastConnectError());
            }
        } finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        return false;
    }

    public void setUserPrefsToConnection(Connection connection) {
        connection.setUserData(PreferencesDialog.getUserData());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupDebug = new javax.swing.JPopupMenu();
        menuDebugTestModalDialog = new javax.swing.JMenuItem();
        menuDebugTestCardRenderModesDialog = new javax.swing.JMenuItem();
        desktopPane = new MageJDesktop();
        mageToolbar = new javax.swing.JToolBar();
        btnPreferences = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnConnect = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnDeckEditor = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnCollectionViewer = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnSendFeedback = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnSymbols = new javax.swing.JButton();
        jSeparatorSymbols = new javax.swing.JToolBar.Separator();
        btnImages = new javax.swing.JButton();
        jSeparatorImages = new javax.swing.JToolBar.Separator();
        btnAbout = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnDebug = new javax.swing.JButton();
        separatorDebug = new javax.swing.JToolBar.Separator();
        jMemUsageLabel = new javax.swing.JLabel();

        menuDebugTestModalDialog.setText("Test Modal Dialogs");
        menuDebugTestModalDialog.setFont(GUISizeHelper.menuFont);
        menuDebugTestModalDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDebugTestModalDialogActionPerformed(evt);
            }
        });
        popupDebug.add(menuDebugTestModalDialog);

        menuDebugTestCardRenderModesDialog.setText("Test Card Render Modes");
        menuDebugTestCardRenderModesDialog.setFont(GUISizeHelper.menuFont);
        menuDebugTestCardRenderModesDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDebugTestCardRenderModesDialogActionPerformed(evt);
            }
        });
        popupDebug.add(menuDebugTestCardRenderModesDialog);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 500));

        desktopPane.setBackground(new java.awt.Color(204, 204, 204));

        mageToolbar.setFloatable(false);
        mageToolbar.setRollover(true);
        mageToolbar.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        mageToolbar.setMaximumSize(new java.awt.Dimension(614, 60));
        mageToolbar.setMinimumSize(new java.awt.Dimension(566, 60));
        mageToolbar.setPreferredSize(new java.awt.Dimension(614, 60));

        btnPreferences.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/preferences.png"))); // NOI18N
        btnPreferences.setText("Preferences");
        btnPreferences.setToolTipText("By changing the settings in the preferences window you can adjust the look and behaviour of xmage.");
        btnPreferences.setFocusable(false);
        btnPreferences.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreferencesActionPerformed(evt);
            }
        });
        mageToolbar.add(btnPreferences);
        mageToolbar.add(jSeparator4);

        btnConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/connect.png"))); // NOI18N
        btnConnect.setToolTipText("Connect to or disconnect from a XMage server.");
        btnConnect.setFocusable(false);
        btnConnect.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        mageToolbar.add(btnConnect);
        mageToolbar.add(jSeparator1);

        btnDeckEditor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/deck_editor.png"))); // NOI18N
        btnDeckEditor.setText("Deck Editor");
        btnDeckEditor.setToolTipText("Start the deck editor to create or modify decks.");
        btnDeckEditor.setFocusable(false);
        btnDeckEditor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDeckEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeckEditorActionPerformed(evt);
            }
        });
        mageToolbar.add(btnDeckEditor);
        mageToolbar.add(jSeparator2);

        btnCollectionViewer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/collection.png"))); // NOI18N
        btnCollectionViewer.setText("Card Viewer");
        btnCollectionViewer.setToolTipText("Card viewer to show the cards of sets. ");
        btnCollectionViewer.setFocusable(false);
        btnCollectionViewer.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCollectionViewer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCollectionViewerActionPerformed(evt);
            }
        });
        mageToolbar.add(btnCollectionViewer);
        mageToolbar.add(jSeparator5);

        btnSendFeedback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/feedback.png"))); // NOI18N
        btnSendFeedback.setText("Feedback");
        btnSendFeedback.setToolTipText("Send some feedback to the developers.");
        btnSendFeedback.setFocusable(false);
        btnSendFeedback.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSendFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendFeedbackActionPerformed(evt);
            }
        });
        mageToolbar.add(btnSendFeedback);
        mageToolbar.add(jSeparator6);

        btnSymbols.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/symbol.png"))); // NOI18N
        btnSymbols.setText("Symbols");
        btnSymbols.setToolTipText("<HTML>Load the mana and other card symbols from the internet.<br>\nOtherwise you only see the replacement sequence like {U} for blue mana symbol.<br>\nYou need to do that only once.");
        btnSymbols.setFocusable(false);
        btnSymbols.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSymbols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSymbolsActionPerformed(evt);
            }
        });
        mageToolbar.add(btnSymbols);
        mageToolbar.add(jSeparatorSymbols);

        btnImages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/images.png"))); // NOI18N
        btnImages.setText("Images");
        btnImages.setToolTipText("<HTML>Load card images from external sources.");
        btnImages.setFocusable(false);
        btnImages.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagesActionPerformed(evt);
            }
        });
        mageToolbar.add(btnImages);
        mageToolbar.add(jSeparatorImages);

        btnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/about.png"))); // NOI18N
        btnAbout.setText("About");
        btnAbout.setToolTipText("About app");
        btnAbout.setFocusable(false);
        btnAbout.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        mageToolbar.add(btnAbout);
        mageToolbar.add(jSeparator7);

        btnDebug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/connect.png"))); // NOI18N
        btnDebug.setText("Debug");
        btnDebug.setToolTipText("Show debug tools");
        btnDebug.setFocusable(false);
        btnDebug.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDebug.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                btnDebugMouseClicked(e);
            }
        });
        mageToolbar.add(btnDebug);
        mageToolbar.add(separatorDebug);

        jMemUsageLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMemUsageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/memory.png"))); // NOI18N
        jMemUsageLabel.setText("100% Free mem");
        jMemUsageLabel.setFocusable(false);
        jMemUsageLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mageToolbar.add(jMemUsageLabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                        .addComponent(mageToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mageToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
        );

        if (PreferencesDialog.getCurrentTheme().getMageToolbar() != null) {
            mageToolbar.getParent().setBackground(PreferencesDialog.getCurrentTheme().getMageToolbar());
        }

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeckEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeckEditorActionPerformed
        showDeckEditor(DeckEditorMode.FREE_BUILDING, null, null, 0);
    }//GEN-LAST:event_btnDeckEditorActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        if (SessionHandler.isConnected()) {
            tryDisconnectOrExit(false);
        } else {
            connectDialog.showDialog();
            setWindowTitle();
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    public void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        JInternalFrame[] windows = desktopPane.getAllFramesInLayer(JLayeredPane.MODAL_LAYER);
        for (JInternalFrame window : windows) {
            if (window instanceof AboutDialog) {
                // don't open the window twice.
                return;
            }
        }
        AboutDialog aboutDialog = new AboutDialog();
        desktopPane.add(aboutDialog, JLayeredPane.MODAL_LAYER);
        aboutDialog.showDialog(VERSION);
    }//GEN-LAST:event_btnAboutActionPerformed

    private void btnCollectionViewerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCollectionViewerActionPerformed
        showCollectionViewer();
    }//GEN-LAST:event_btnCollectionViewerActionPerformed

    public void btnPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreferencesActionPerformed
        PreferencesDialog.main(new String[]{});
    }//GEN-LAST:event_btnPreferencesActionPerformed

    public void btnSendFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendFeedbackActionPerformed
        if (!SessionHandler.isConnected()) {
            JOptionPane.showMessageDialog(null, "You may send us feedback only when connected to server.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        FeedbackDialog.main(new String[]{});
    }//GEN-LAST:event_btnSendFeedbackActionPerformed

    private void btnSymbolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSymbolsActionPerformed
        downloadAdditionalResources();
    }//GEN-LAST:event_btnSymbolsActionPerformed

    public void downloadAdditionalResources() {
        UserRequestMessage message = new UserRequestMessage("Download additional resources", "Do you want to download game symbols and additional image files?");
        message.setButton1("No", null);
        message.setButton2("Yes", PlayerAction.CLIENT_DOWNLOAD_SYMBOLS);
        showUserRequestDialog(message);
    }

    private void btnImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagesActionPerformed
        downloadImages();
    }//GEN-LAST:event_btnImagesActionPerformed

    private void menuDebugTestModalDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDebugTestModalDialogActionPerformed
        final TestModalDialog dialog = new TestModalDialog();
        dialog.showDialog();
    }//GEN-LAST:event_menuDebugTestModalDialogActionPerformed

    private void btnDebugMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDebugMouseClicked
        popupDebug.show(evt.getComponent(), 0, evt.getComponent().getHeight());
    }//GEN-LAST:event_btnDebugMouseClicked

    private void menuDebugTestCardRenderModesDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDebugTestCardRenderModesDialogActionPerformed
        final TestCardRenderDialog dialog = new TestCardRenderDialog();
        dialog.showDialog();
    }//GEN-LAST:event_menuDebugTestCardRenderModesDialogActionPerformed

    public void downloadImages() {
        DownloadPicturesService.startDownload();
    }

    public void exitApp() {
        tryDisconnectOrExit(true);
    }

    private void tryDisconnectOrExit(Boolean needExit) {
        String actionName = needExit ? "exit" : "disconnect";
        PlayerAction actionFull = needExit ? PlayerAction.CLIENT_EXIT_FULL : PlayerAction.CLIENT_DISCONNECT_FULL;
        PlayerAction actionKeepTables = needExit ? PlayerAction.CLIENT_EXIT_KEEP_GAMES : PlayerAction.CLIENT_DISCONNECT_KEEP_GAMES;
        double windowSizeRatio = 1.3;
        if (SessionHandler.isConnected()) {
            int activeTables = MageFrame.getInstance().getPanelsCount(true);
            UserRequestMessage message = new UserRequestMessage(
                    "Confirm " + actionName,
                    "You are connected and has " + activeTables + " active table(s). You can quit from all your tables (concede) or ask server to wait a few minutes for reconnect. What to do?"
            );
            String totalInfo = (activeTables == 0 ? "" : String.format(" from %d table%s", activeTables, (activeTables > 1 ? "s" : "")));
            message.setButton1("Cancel", null);
            message.setButton2("Wait for me", actionKeepTables);
            message.setButton3("Quit" + totalInfo, actionFull);
            message.setWindowSizeRatio(windowSizeRatio);
            MageFrame.getInstance().showUserRequestDialog(message);
        } else {
            UserRequestMessage message = new UserRequestMessage(
                    "Confirm " + actionName,
                    "Are you sure you want to " + actionName + "?"
            );
            message.setButton1("Cancel", null);
            message.setButton2("Yes", actionFull);
            message.setWindowSizeRatio(windowSizeRatio);
            MageFrame.getInstance().showUserRequestDialog(message);
        }
    }

    public void enableButtons() {
        btnConnect.setEnabled(true);
        btnDeckEditor.setEnabled(true);
    }

    public void disableButtons() {
        btnConnect.setEnabled(true);
        btnDeckEditor.setEnabled(true);
    }

    public void hideServerLobby() {
        this.tablesPane.hideTables();
        updateSwitchPanelsButton();
    }

    public void setServerLobbyTablesFilter() {
        if (this.tablesPane != null) {
            this.tablesPane.setTableFilter();
        }
    }

    public void prepareAndShowServerLobby() {
        // Update the tables pane with the new session
        this.tablesPane.showTables();

        // Show the tables pane if there wasn't already an active pane
        MagePane topPanebefore = getTopMost(tablesPane);
        setActive(tablesPane);
        if (topPanebefore != null && topPanebefore != tablesPane) {
            setActive(topPanebefore);
        }

        updateSwitchPanelsButton();
    }

    public void hideGames() {
        Component[] windows = desktopPane.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER);
        for (Component window : windows) {
            if (window instanceof GamePane) {
                GamePane gamePane = (GamePane) window;
                gamePane.removeGame();
            }
            if (window instanceof DraftPane) {
                DraftPane draftPane = (DraftPane) window;
                draftPane.removeDraft();
            }
            if (window instanceof TournamentPane) {
                TournamentPane tournamentPane = (TournamentPane) window;
                tournamentPane.removeTournament();
            }
            // close & remove sideboarding or construction pane if open
            if (window instanceof DeckEditorPane) {
                DeckEditorPane deckEditorPane = (DeckEditorPane) window;
                if (deckEditorPane.getDeckEditorMode() == DeckEditorMode.LIMITED_BUILDING
                        || deckEditorPane.getDeckEditorMode() == DeckEditorMode.SIDEBOARDING
                        || deckEditorPane.getDeckEditorMode() == DeckEditorMode.LIMITED_SIDEBOARD_BUILDING
                        || deckEditorPane.getDeckEditorMode() == DeckEditorMode.VIEW_LIMITED_DECK) {
                    deckEditorPane.removeFrame();
                }
            }

        }
    }

    private String prepareDeckEditorName(DeckEditorMode mode, Deck deck, UUID tableId) {
        // GUI searching frame name for duplicates, so:
        // - online editors must be unique;
        // - offline editor must be single;
        String name;
        switch (mode) {
            case FREE_BUILDING:
                // offline
                name = "Deck Editor";
                break;
            case LIMITED_BUILDING:
            case LIMITED_SIDEBOARD_BUILDING:
            case SIDEBOARDING:
            case VIEW_LIMITED_DECK:
                // online
                name = "Deck Editor - " + mode.getTitle();
                break;
            default:
                throw new IllegalArgumentException("Unknown deck editor mode: " + mode);
        }

        // additional info about deck/player
        if (deck != null && deck.getName() != null && !deck.getName().isEmpty()) {
            name += " - " + deck.getName();
        }

        // additional info about game
        if (tableId != null) {
            name += " - table " + tableId;
        }

        return name;
    }

    public void showDeckEditor(DeckEditorMode mode, Deck deck, UUID tableId, int visibleTimer) {
        // create or open new editor
        String name = prepareDeckEditorName(mode, deck, tableId);

        // already exists
        Component[] windows = desktopPane.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER);
        for (Component window : windows) {
            if (window instanceof DeckEditorPane && ((MagePane) window).getTitle().equals(name)) {
                setActive((MagePane) window);
                return;
            }
        }

        // new editor
        DeckEditorPane deckEditor = new DeckEditorPane();
        desktopPane.add(deckEditor, JLayeredPane.DEFAULT_LAYER);
        deckEditor.setVisible(false);
        deckEditor.show(mode, deck, name, tableId, visibleTimer);
        setActive(deckEditor);
    }

    public void showUserRequestDialog(final UserRequestMessage userRequestMessage) {
        if (SwingUtilities.isEventDispatchThread()) {
            innerShowUserRequestDialog(userRequestMessage);
        } else {
            SwingUtilities.invokeLater(() -> innerShowUserRequestDialog(userRequestMessage));
        }
    }

    private void innerShowUserRequestDialog(final UserRequestMessage userRequestMessage) {
        UserRequestDialog userRequestDialog = new UserRequestDialog();
        userRequestDialog.setLocation(100, 100);
        desktopPane.add(userRequestDialog, JLayeredPane.MODAL_LAYER);
        userRequestDialog.showDialog(userRequestMessage);
    }

    public void showErrorDialog(final String title, final String message) {
        if (SwingUtilities.isEventDispatchThread()) {
            errorDialog.showDialog(title, message);
        } else {
            SwingUtilities.invokeLater(() -> errorDialog.showDialog(title, message));
        }
    }

    public void showCollectionViewer() {
        Component[] windows = desktopPane.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER);
        for (Component window : windows) {
            if (window instanceof CollectionViewerPane) {
                setActive((MagePane) window);
                return;
            }
        }
        CollectionViewerPane collectionViewerPane = new CollectionViewerPane();
        desktopPane.add(collectionViewerPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        collectionViewerPane.setVisible(true);
        setActive(collectionViewerPane);
    }

    static void renderSplashFrame(Graphics2D g) {
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120, 140, 200, 40);
        g.setPaintMode();
        g.setColor(Color.white);
        g.drawString("Version 0.6.1", 560, 460);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        // Workaround for #451
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        LOGGER.info("Starting MAGE CLIENT version: " + VERSION);
        LOGGER.info("Java version: " + System.getProperty("java.version"));
        LOGGER.info("Logging level: " + LOGGER.getEffectiveLevel());
        LOGGER.info("Default charset: " + Charset.defaultCharset());
        if (!Charset.defaultCharset().toString().equals("UTF-8")) {
            LOGGER.warn("WARNING, bad charset. Some images will not be downloaded. You must:");
            LOGGER.warn("* Open launcher -> settings -> java -> client java options");
            LOGGER.warn("* Insert additional command at the the end: -Dfile.encoding=UTF-8");
        }

        startTime = System.currentTimeMillis();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> LOGGER.fatal(null, e));

        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg.startsWith(LITE_MODE_ARG)) {
                    liteMode = true;
                }
                if (arg.startsWith(GRAY_MODE_ARG)) {
                    grayMode = true;
                }
                if (System.getProperty(FULL_SCREEN_PROP) != null) {
                    macOsFullScreenEnabled = Boolean.parseBoolean(System.getProperty(FULL_SCREEN_PROP));
                }
                if (arg.startsWith(SKIP_DONE_SYMBOLS)) {
                    skipSmallSymbolGenerationForExisting = true;
                }
                if (arg.startsWith(USER_ARG)) {
                    startUser = args[i + 1];
                    i++;
                }
                if (arg.startsWith(PASSWORD_ARG)) {
                    startPassword = args[i + 1];
                    i++;
                }
                if (arg.startsWith(SERVER_ARG)) {
                    startServer = args[i + 1];
                    i++;
                }
                if (arg.startsWith(PORT_ARG)) {
                    startPort = Integer.parseInt(args[i + 1]);
                    i++;
                }
                if (arg.startsWith(DEBUG_ARG)) {
                    debugMode = true;
                }
            }

            // enable debug menu by default for developer build (if you run it from source code)
            debugMode |= VERSION.isDeveloperBuild();

            if (!liteMode) {
                final SplashScreen splash = SplashScreen.getSplashScreen();
                if (splash != null) {
                    Graphics2D g = splash.createGraphics();
                    if (g != null) {
                        renderSplashFrame(g);
                    }
                    splash.update();
                }
            }
            try {
                instance = new MageFrame();
            } catch (Throwable e) {
                LOGGER.fatal("Critical error on start up, app will be closed: " + e.getMessage(), e);
                System.exit(1);
            }

            // debug menu
            instance.separatorDebug.setVisible(debugMode);
            instance.btnDebug.setVisible(debugMode);

            if (startUser != null) {
                instance.currentConnection = new Connection();
                instance.currentConnection.setUsername(startUser);
                instance.currentConnection.setHost(startServer);
                if (startPort > 0) {
                    instance.currentConnection.setPort(startPort);
                } else {
                    instance.currentConnection.setPort(MagePreferences.getServerPortWithDefault(ClientDefaultSettings.port));
                }
                PreferencesDialog.setProxyInformation(instance.currentConnection);
                instance.currentConnection.setPassword(startPassword);
            }
            instance.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnCollectionViewer;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDebug;
    private javax.swing.JButton btnDeckEditor;
    private javax.swing.JButton btnImages;
    private javax.swing.JButton btnPreferences;
    private javax.swing.JButton btnSendFeedback;
    private javax.swing.JButton btnSymbols;
    private static javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jMemUsageLabel;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparatorImages;
    private javax.swing.JToolBar.Separator jSeparatorSymbols;
    private javax.swing.JToolBar mageToolbar;
    private javax.swing.JMenuItem menuDebugTestCardRenderModesDialog;
    private javax.swing.JMenuItem menuDebugTestModalDialog;
    private javax.swing.JPopupMenu popupDebug;
    private javax.swing.JToolBar.Separator separatorDebug;
    // End of variables declaration//GEN-END:variables

    private static final long serialVersionUID = -9104885239063142218L;
    private ImagePanel backgroundPane;
    private final TablesPane tablesPane;

    public void setConnectButtonText(String status) {
        this.btnConnect.setText(status);

        // Needed to layout the tooltbar after text length change
        // TODO: need research, is it actual?
        GUISizeHelper.refreshGUIAndCards(false);

        this.btnConnect.repaint();
        this.btnConnect.revalidate();
    }

    public static MageUI getUI() {
        return UI;
    }

    public static ChatPanelBasic getChat(UUID chatId) {
        return CHATS.get(chatId);
    }

    public static void addChat(UUID chatId, ChatPanelBasic chatPanel) {
        CHATS.put(chatId, chatPanel);
    }

    public static void removeChat(UUID chatId) {
        CHATS.remove(chatId);
    }

    public static Map<UUID, ChatPanelBasic> getChatPanels() {
        return CHATS;
    }

    public static void addGame(UUID gameId, GamePanel gamePanel) {
        GAMES.put(gameId, gamePanel);
    }

    public static GamePanel getGame(UUID gameId) {
        return GAMES.get(gameId);
    }

    public static Map<UUID, PlayAreaPanel> getGamePlayers(UUID gameId) {
        GamePanel p = GAMES.get(gameId);
        return p != null ? p.getPlayers() : new HashMap<>();
    }

    public static void removeGame(UUID gameId) {
        GAMES.remove(gameId);
    }

    public static DraftPanel getDraft(UUID draftId) {
        return DRAFTS.get(draftId);
    }

    public static void removeDraft(UUID draftId) {
        DraftPanel draftPanel = DRAFTS.get(draftId);
        if (draftPanel != null) {
            DRAFTS.remove(draftId);
            draftPanel.hideDraft();
        }
    }

    public static void addDraft(UUID draftId, DraftPanel draftPanel) {
        DRAFTS.put(draftId, draftPanel);
    }

    /**
     * Return total number of panels/frames (game panel, deck editor panel, etc)
     *
     * @param onlyActive return only active panels (related to online like game panel, but not game viewer)
     * @return
     */
    public int getPanelsCount(boolean onlyActive) {
        return (int) Arrays.stream(this.desktopPane.getComponentsInLayer(javax.swing.JLayeredPane.DEFAULT_LAYER))
                .filter(Component::isVisible)
                .filter(p -> p instanceof MagePane)
                .map(p -> (MagePane) p)
                .filter(p-> !onlyActive || p.isActiveTable())
                .count();
    }

    @Override
    public void connected(final String message) {
        SwingUtilities.invokeLater(() -> {
            setConnectButtonText(message);
            enableButtons();
        });
    }

    @Override
    public void disconnected(boolean askToReconnect, boolean keepMySessionActive) {
        if (SwingUtilities.isEventDispatchThread()) {
            // TODO: need research, it can generate wrong logs due diff threads source (doInBackground, swing, server events, etc)
            // REMOTE task, e.g. connecting
            LOGGER.info("Disconnected from server side");
        } else {
            // USER mode, e.g. user plays and got disconnect
            LOGGER.info("Disconnected from client side");
        }

        Runnable runOnExit = () -> {
            // user already disconnected, can't do any online actions like quite chat
            // but try to keep session
            // TODO: why it ignore askToReconnect here, but use custom reconnect dialog later?! Need research
            SessionHandler.disconnect(false, keepMySessionActive);
            setConnectButtonText(NOT_CONNECTED_BUTTON);
            disableButtons();
            hideGames();
            hideServerLobby();
            if (askToReconnect) {
                UserRequestMessage message = new UserRequestMessage("Connection lost", "The connection to server was lost. Reconnect to " + MagePreferences.getLastServerAddress() + "?");
                message.setButton1("No", null);
                message.setButton2("Yes", PlayerAction.CLIENT_RECONNECT);
                showUserRequestDialog(message);
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            runOnExit.run();
        } else {
            SwingUtilities.invokeLater(runOnExit);
        }
    }

    @Override
    public void showMessage(String message) {
        final UserRequestMessage requestMessage = new UserRequestMessage("Message", message);
        requestMessage.setButton1("OK", null);
        showUserRequestDialog(requestMessage);
    }

    @Override
    public void showError(final String message) {
        final UserRequestMessage requestMessage = new UserRequestMessage("Error", message);
        requestMessage.setButton1("OK", null);
        showUserRequestDialog(requestMessage);
    }

    @Override
    public void onCallback(ClientCallback callback) {
        callbackClient.onCallback(callback);
    }

    @Override
    public void onNewConnection() {
        callbackClient.onNewConnection();
    }

    public void sendUserReplay(PlayerAction playerAction, UserRequestMessage userRequestMessage) {
        switch (playerAction) {
            case CLIENT_DOWNLOAD_SYMBOLS:
                Plugins.instance.downloadSymbols();
                break;
            case CLIENT_DOWNLOAD_CARD_IMAGES:
                DownloadPicturesService.startDownload();
                break;
            case CLIENT_DISCONNECT_FULL:
                doClientDisconnect(false, "You have disconnected");
                break;
            case CLIENT_DISCONNECT_KEEP_GAMES:
                doClientDisconnect(true, "You have disconnected and have few minutes to reconnect");
                break;
            case CLIENT_QUIT_TOURNAMENT:
                SessionHandler.quitTournament(userRequestMessage.getTournamentId());
                break;
            case CLIENT_QUIT_DRAFT_TOURNAMENT:
                SessionHandler.quitDraft(userRequestMessage.getTournamentId());
                MageFrame.removeDraft(userRequestMessage.getTournamentId());
                break;
            case CLIENT_CONCEDE_GAME:
                SessionHandler.sendPlayerAction(PlayerAction.CONCEDE, userRequestMessage.getGameId(), null);
                break;
            case CLIENT_CONCEDE_MATCH:
                SessionHandler.quitMatch(userRequestMessage.getGameId());
                break;
            case CLIENT_STOP_WATCHING:
                SessionHandler.stopWatching(userRequestMessage.getGameId());
                GamePanel gamePanel = getGame(userRequestMessage.getGameId());
                if (gamePanel != null) {
                    gamePanel.removeGame();
                }
                removeGame(userRequestMessage.getGameId());
                break;
            case CLIENT_EXIT_FULL:
                doClientDisconnect(false, "");
                doClientShutdownAndExit();
                break;
            case CLIENT_EXIT_KEEP_GAMES:
                doClientDisconnect(true, "");
                doClientShutdownAndExit();
                break;
            case CLIENT_REMOVE_TABLE:
                SessionHandler.removeTable(userRequestMessage.getRoomId(), userRequestMessage.getTableId());
                break;
            case CLIENT_RECONNECT:
                if (performConnect(true)) {
                    enableButtons();
                }
                break;
            case CLIENT_REPLAY_ACTION:
                SessionHandler.stopReplay(userRequestMessage.getGameId());
                break;
            default:
                if (SessionHandler.getSession() != null && playerAction != null) {
                    SessionHandler.sendPlayerAction(playerAction, userRequestMessage.getGameId(), userRequestMessage.getRelatedUserId());
                }

        }
    }

    private void doClientDisconnect(boolean keepMySessionActive, String afterMessage) {
        if (SessionHandler.isConnected()) {
            SessionHandler.disconnect(false, keepMySessionActive);
        }
        tablesPane.clearChat();
        setWindowTitle();

        if (!afterMessage.isEmpty()) {
            showMessage(afterMessage);
        }
    }

    private void doClientShutdownAndExit() {
        tablesPane.cleanUp();
        CardRepository.instance.closeDB();
        Plugins.instance.shutdown();
        dispose();
        System.exit(0);
    }

    private void endTables() {
        for (UUID gameId : GAMES.keySet()) {
            SessionHandler.quitMatch(gameId);
        }
        for (UUID draftId : DRAFTS.keySet()) {
            SessionHandler.quitDraft(draftId);
        }
    }

    /**
     * Refresh whole GUI including cards and card images.
     * Use it after new images downloaded, new fonts or theme settings selected.
     */
    public void refreshGUIAndCards() {
        ImageCaches.clearAll();

        setGUISize();

        setGUISizeTooltipContainer();

        Plugins.instance.changeGUISize();
        CountryUtil.changeGUISize();
        for (Component component : desktopPane.getComponents()) {
            if (component instanceof MageDialog) {
                ((MageDialog) component).changeGUISize();
            }
            if (component instanceof MagePane) {
                ((MagePane) component).changeGUISize();
            }
        }
        for (ChatPanelBasic chatPanel : CHATS.values()) {
            chatPanel.changeGUISize(GUISizeHelper.chatFont);
        }
        try {
            CardInfoPaneImpl cardInfoPane = (CardInfoPaneImpl) UI.getComponent(MageComponents.CARD_INFO_PANE);
            if (cardInfoPane != null) {
                cardInfoPane.changeGUISize();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.revalidate();
        this.repaint();
    }

    private void setGUISize() {
        Font font = GUISizeHelper.menuFont;
        mageToolbar.setFont(font);
        int newHeight = font.getSize() + 6;
        Dimension mageToolbarDimension = mageToolbar.getPreferredSize();
        mageToolbarDimension.height = newHeight + 6;
        mageToolbar.setMinimumSize(mageToolbarDimension);
        mageToolbar.setMaximumSize(mageToolbarDimension);
        mageToolbar.setPreferredSize(mageToolbarDimension);
        for (Component component : mageToolbar.getComponents()) {
            if (component instanceof JButton || component instanceof JLabel || component instanceof JToggleButton) {
                component.setFont(font);
                Dimension d = component.getPreferredSize();
                d.height = newHeight;
                component.setMinimumSize(d);
                component.setMaximumSize(d);

            }
            if (component instanceof javax.swing.JToolBar.Separator) {
                Dimension d = component.getPreferredSize();
                d.height = newHeight;
                component.setMinimumSize(d);
                component.setMaximumSize(d);
            }
        }

        updateTooltipContainerSizes();
    }

    public static void showWhatsNewDialog() {
        try {
            URI newsURI = new URI("https://jaydi85.github.io/xmage-web-news/news.html");
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(newsURI);
            }
        } catch (URISyntaxException e) {
            LOGGER.error("URI Syntax error when creating news link", e);
        } catch (IOException e) {
            LOGGER.error("IOException while loading news page", e);
        }
    }

    public boolean isGameFrameActive(UUID gameId) {
        if (activeFrame != null && activeFrame instanceof GamePane) {
            return ((GamePane) activeFrame).getGameId().equals(gameId);
        }
        return false;
    }
}

class MagePaneMenuItem extends JCheckBoxMenuItem {

    private final MagePane frame;

    public MagePaneMenuItem(MagePane frame) {
        super(frame.getTitle());
        this.frame = frame;
    }

    public MagePane getFrame() {
        return frame;
    }
}
