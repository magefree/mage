package mage.interfaces.callback;

/**
 * Server's commands to process on client side. Commands can come in un-synced state due bad/slow network
 * <p>
 * Can be:
 * - critical events (messages, game events, choose dialogs, etc)
 * - non-critical events (game updates, messages)
 */
public enum ClientCallbackMethod {

    // TODO: rename events due place/action like GAME_STARTED, GAME_ASK_DIALOG, GAME_TARGET_DIALOG

    // messages
    CHATMESSAGE(ClientCallbackType.MESSAGE, "chatMessage"),
    SHOW_USERMESSAGE(ClientCallbackType.MESSAGE, "showUserMessage"),
    SERVER_MESSAGE(ClientCallbackType.MESSAGE, "serverMessage"),

    // table
    JOINED_TABLE(ClientCallbackType.TABLE_CHANGE, "joinedTable"),

    // tournament
    START_TOURNAMENT(ClientCallbackType.TABLE_CHANGE, "startTournament"),
    TOURNAMENT_INIT(ClientCallbackType.TABLE_CHANGE, "tournamentInit"), // TODO: unused on client
    TOURNAMENT_UPDATE(ClientCallbackType.UPDATE, "tournamentUpdate"), // TODO: unused on client
    TOURNAMENT_OVER(ClientCallbackType.TABLE_CHANGE, "tournamentOver"), // TODO: unused on client

    // draft/sideboard
    START_DRAFT(ClientCallbackType.TABLE_CHANGE, "startDraft"),
    SIDEBOARD(ClientCallbackType.TABLE_CHANGE, "sideboard"),
    CONSTRUCT(ClientCallbackType.TABLE_CHANGE, "construct"),
    DRAFT_OVER(ClientCallbackType.TABLE_CHANGE, "draftOver"),
    DRAFT_INIT(ClientCallbackType.TABLE_CHANGE, "draftInit"),
    DRAFT_PICK(ClientCallbackType.TABLE_CHANGE, "draftPick"),
    DRAFT_UPDATE(ClientCallbackType.UPDATE, "draftUpdate"),

    // watch
    SHOW_TOURNAMENT(ClientCallbackType.TABLE_CHANGE, "showTournament"),
    WATCHGAME(ClientCallbackType.TABLE_CHANGE, "watchGame"),

    // in-game actions
    VIEW_LIMITED_DECK(ClientCallbackType.MESSAGE, "viewLimitedDeck"),
    VIEW_SIDEBOARD(ClientCallbackType.MESSAGE, "viewSideboard"),

    // other
    USER_REQUEST_DIALOG(ClientCallbackType.DIALOG, "userRequestDialog"),
    GAME_REDRAW_GUI(ClientCallbackType.CLIENT_SIDE_EVENT, "gameRedrawGUI"),

    // game
    START_GAME(ClientCallbackType.TABLE_CHANGE, "startGame"),
    GAME_INIT(ClientCallbackType.TABLE_CHANGE, "gameInit"),
    GAME_UPDATE_AND_INFORM(ClientCallbackType.UPDATE, "gameInform"), // update game and feedback panel with current status (e.g. on non our priority)
    GAME_INFORM_PERSONAL(ClientCallbackType.MESSAGE, "gameInformPersonal"),
    GAME_ERROR(ClientCallbackType.MESSAGE, "gameError"),
    GAME_UPDATE(ClientCallbackType.UPDATE, "gameUpdate"),
    GAME_TARGET(ClientCallbackType.DIALOG, "gameTarget"),
    GAME_CHOOSE_ABILITY(ClientCallbackType.DIALOG, "gameChooseAbility"),
    GAME_CHOOSE_PILE(ClientCallbackType.DIALOG, "gameChoosePile"),
    GAME_CHOOSE_CHOICE(ClientCallbackType.DIALOG, "gameChooseChoice"),
    GAME_ASK(ClientCallbackType.DIALOG, "gameAsk"),
    GAME_SELECT(ClientCallbackType.DIALOG, "gameSelect"),
    GAME_PLAY_MANA(ClientCallbackType.DIALOG, "gamePlayMana"),
    GAME_PLAY_XMANA(ClientCallbackType.DIALOG, "gamePlayXMana"),
    GAME_GET_AMOUNT(ClientCallbackType.DIALOG, "gameSelectAmount"),
    GAME_GET_MULTI_AMOUNT(ClientCallbackType.DIALOG, "gameSelectMultiAmount"),
    GAME_OVER(ClientCallbackType.TABLE_CHANGE, "gameOver"),
    END_GAME_INFO(ClientCallbackType.TABLE_CHANGE, "endGameInfo"),

    // replay (unsupported)
    REPLAY_GAME(ClientCallbackType.TABLE_CHANGE, "replayGame"),
    REPLAY_INIT(ClientCallbackType.TABLE_CHANGE, "replayInit"),
    REPLAY_UPDATE(ClientCallbackType.UPDATE, "replayUpdate"),
    REPLAY_DONE(ClientCallbackType.TABLE_CHANGE, "replayDone");

    final ClientCallbackType type;
    final String code;

    ClientCallbackMethod(ClientCallbackType type, String code) {
        this.type = type;
        this.code = code;
    }

    public ClientCallbackType getType() {
        return this.type;
    }
}
