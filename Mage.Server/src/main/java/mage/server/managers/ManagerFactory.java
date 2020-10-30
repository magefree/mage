package mage.server.managers;

public interface ManagerFactory {
    IChatManager chatManager();

    IDraftManager draftManager();

    IGameManager gameManager();

    IGamesRoomManager gamesRoomManager();

    IMailClient mailClient();

    IMailClient mailgunClient();

    IReplayManager replayManager();

    ISessionManager sessionManager();

    ITableManager tableManager();

    IUserManager userManager();

    IConfigSettings configSettings();

    IThreadExecutor threadExecutor();

    ITournamentManager tournamentManager();
}
