package mage.server;

import mage.server.draft.DraftManager;
import mage.server.game.GameManager;
import mage.server.game.GamesRoomManager;
import mage.server.game.ReplayManager;
import mage.server.managers.*;
import mage.server.tournament.TournamentManager;
import mage.server.util.ThreadExecutor;

public class MainManagerFactory implements ManagerFactory {

    private final IConfigSettings configSettings;
    private final IThreadExecutor threadExecutor;
    private final IChatManager chatManager;
    private final IDraftManager draftManager;
    private final IGameManager gameManager;
    private final IGamesRoomManager gamesRoomManager;
    private final IMailClient mailClient;
    private final IMailClient mailgunClient;
    private final IReplayManager replayManager;
    private final ISessionManager sessionManager;
    private final ITableManager tableManager;
    private final IUserManager userManager;
    private final ITournamentManager tournamentManager;


    public MainManagerFactory(IConfigSettings configSettings) {
        this.configSettings = configSettings;
        this.threadExecutor = new ThreadExecutor(configSettings);
        this.mailClient = new MailClient(configSettings);
        this.mailgunClient = new MailgunClient(configSettings);
        this.chatManager = new ChatManager(this);
        this.draftManager = new DraftManager(this);
        this.gameManager = new GameManager(this);
        this.replayManager = new ReplayManager(this);
        this.sessionManager = new SessionManager(this);
        this.tournamentManager = new TournamentManager(this);
        final GamesRoomManager gamesRoomManager = new GamesRoomManager(this);
        final TableManager tableManager = new TableManager(this);
        final UserManager userManager = new UserManager(this);
        this.gamesRoomManager = gamesRoomManager;
        this.tableManager = tableManager;
        this.userManager = userManager;
        startThreads(gamesRoomManager, tableManager, userManager);
    }

    private void startThreads(GamesRoomManager gamesRoomManager, TableManager tableManager, UserManager userManager) {
        userManager.init();
        tableManager.init();
        gamesRoomManager.init();
    }

    @Override
    public IChatManager chatManager() {
        return chatManager;
    }

    @Override
    public IDraftManager draftManager() {
        return draftManager;
    }

    @Override
    public IGameManager gameManager() {
        return gameManager;
    }

    @Override
    public IGamesRoomManager gamesRoomManager() {
        return gamesRoomManager;
    }

    @Override
    public IMailClient mailClient() {
        return mailClient;
    }

    @Override
    public IMailClient mailgunClient() {
        return mailgunClient;
    }

    @Override
    public IReplayManager replayManager() {
        return replayManager;
    }

    @Override
    public ISessionManager sessionManager() {
        return sessionManager;
    }

    @Override
    public ITableManager tableManager() {
        return tableManager;
    }

    @Override
    public IUserManager userManager() {
        return userManager;
    }

    @Override
    public IConfigSettings configSettings() {
        return configSettings;
    }

    @Override
    public IThreadExecutor threadExecutor() {
        return threadExecutor;
    }

    @Override
    public ITournamentManager tournamentManager() {
        return tournamentManager;
    }
}
