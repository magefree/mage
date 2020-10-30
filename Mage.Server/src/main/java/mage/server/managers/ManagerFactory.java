package mage.server.managers;

public interface ManagerFactory {
    ChatManager chatManager();

    DraftManager draftManager();

    GameManager gameManager();

    GamesRoomManager gamesRoomManager();

    MailClient mailClient();

    MailClient mailgunClient();

    ReplayManager replayManager();

    SessionManager sessionManager();

    TableManager tableManager();

    UserManager userManager();

    ConfigSettings configSettings();

    ThreadExecutor threadExecutor();

    TournamentManager tournamentManager();
}
