package mage.interfaces.callback;

/**
 * Created by IGOUDT on 4-4-2017.
 */
public enum ClientCallbackMethod {

    CHATMESSAGE("chatMessage"),
    TOURNAMENT_INIT("tournamentInit"),
    TOURNAMENT_UPDATE("tournamentUpdate"),
    TOURNAMENT_OVER("tournamentOver"),
    JOINED_TABLE("joinedTable"),
    START_DRAFT("startDraft"),
    START_TOURNAMENT("startTournament"),
    SIDEBOARD("sideboard"),
    VIEW_LIMITED_DECK("viewLimitedDeck"),
    CONSTRUCT("construct"),
    SHOW_USERMESSAGE("showUserMessage"),
    WATCHGAME("watchGame"),
    REPLAY_GAME("replayGame"),
    START_GAME("startGame"),
    SHOW_TOURNAMENT("showTournament"),
    SHOW_GAME_END_DIALOG("showGameEndDialog"),
    SERVER_MESSAGE("serverMessage"),
    GAME_INIT("gameInit"),
    GAME_OVER("gameOver"),
    GAME_INFORM("gameInform"),
    GAME_INFORM_PERSONAL("gameInformPersonal"),
    GAME_ERROR("gameError"),
    GAME_UPDATE("gameUpdate"),
    DRAFT_OVER("draftOver"),
    REPLAY_DONE("replayDone"),
    USER_REQUEST_DIALOG("userRequestDialog"),
    REPLAY_UPDATE("replayUpdate"),
    REPLAY_INIT("replayInit"),
    END_GAME_INFO("endGameInfo"),
    GAME_TARGET("gameTarget"),
    GAME_CHOOSE_ABILITY("gameChooseAbility"),
    GAME_CHOOSE_PILE("gameChoosePile"),
    GAME_CHOOSE_CHOICE("gameChooseChoice"),
    GAME_ASK("gameAsk"),
    GAME_SELECT("gameSelect"),
    GAME_PLAY_MANA("gamePlayMana"),
    GAME_PLAY_XMANA("gamePlayXMana"),
    GAME_GET_AMOUNT("gameSelectAmount"),
    DRAFT_INIT("draftInit"),
    // DRAFT_INFORM("draftInform"),
    DRAFT_PICK("draftPick"),
    DRAFT_UPDATE("draftUpdate");

    String value;

    ClientCallbackMethod(String value) {
        this.value = value;
    }
}
