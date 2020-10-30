package mage.server;

import mage.server.draft.DraftManagerImpl;
import mage.server.game.GameManagerImpl;
import mage.server.game.GamesRoomManagerImpl;
import mage.server.game.ReplayManagerImpl;
import mage.server.managers.*;
import mage.server.tournament.TournamentManagerImpl;
import mage.server.util.ThreadExecutorImpl;

public class MainManagerFactory implements ManagerFactory {

    private final ConfigSettings configSettings;
    private final ThreadExecutor threadExecutor;
    private final ChatManager chatManager;
    private final DraftManager draftManager;
    private final GameManager gameManager;
    private final GamesRoomManager gamesRoomManager;
    private final MailClient mailClient;
    private final MailClient mailgunClient;
    private final ReplayManager replayManager;
    private final SessionManager sessionManager;
    private final TableManager tableManager;
    private final UserManager userManager;
    private final TournamentManager tournamentManager;


    public MainManagerFactory(ConfigSettings configSettings) {
        this.configSettings = configSettings;
        // ThreadExecutorImpl, MailClientImpl and MailGunClient depend only on the config, so they are initialised first
        this.threadExecutor = new ThreadExecutorImpl(configSettings);
        this.mailClient = new MailClientImpl(configSettings);
        this.mailgunClient = new MailgunClientImpl(configSettings);
        // Chat, Draft, Game, Replay, Session and Tournament managers only require access to the ManagerFactory
        // but do not use them in initialisation
        this.chatManager = new ChatManagerImpl(this);
        this.draftManager = new DraftManagerImpl(this);
        this.gameManager = new GameManagerImpl(this);
        this.replayManager = new ReplayManagerImpl(this);
        this.sessionManager = new SessionManagerImpl(this);
        this.tournamentManager = new TournamentManagerImpl(this);
        // GamesRoom, Table, User managers depend on the ManagerFactory and have an initialisation block which is delayed
        // to the end of the construction
        final GamesRoomManagerImpl gamesRoomManager = new GamesRoomManagerImpl(this);
        final TableManagerImpl tableManager = new TableManagerImpl(this);
        final UserManagerImpl userManager = new UserManagerImpl(this);
        this.gamesRoomManager = gamesRoomManager;
        this.tableManager = tableManager;
        this.userManager = userManager;
        // execute the initialisation block of the relevant manager (they start the executor services)
        startThreads(gamesRoomManager, tableManager, userManager);
    }

    private void startThreads(GamesRoomManagerImpl gamesRoomManager, TableManagerImpl tableManager, UserManagerImpl userManager) {
        userManager.init();
        tableManager.init();
        gamesRoomManager.init();
    }

    @Override
    public ChatManager chatManager() {
        return chatManager;
    }

    @Override
    public DraftManager draftManager() {
        return draftManager;
    }

    @Override
    public GameManager gameManager() {
        return gameManager;
    }

    @Override
    public GamesRoomManager gamesRoomManager() {
        return gamesRoomManager;
    }

    @Override
    public MailClient mailClient() {
        return mailClient;
    }

    @Override
    public MailClient mailgunClient() {
        return mailgunClient;
    }

    @Override
    public ReplayManager replayManager() {
        return replayManager;
    }

    @Override
    public SessionManager sessionManager() {
        return sessionManager;
    }

    @Override
    public TableManager tableManager() {
        return tableManager;
    }

    @Override
    public UserManager userManager() {
        return userManager;
    }

    @Override
    public ConfigSettings configSettings() {
        return configSettings;
    }

    @Override
    public ThreadExecutor threadExecutor() {
        return threadExecutor;
    }

    @Override
    public TournamentManager tournamentManager() {
        return tournamentManager;
    }
}
