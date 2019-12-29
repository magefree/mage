package mage.client.remote;

import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.chat.ChatPanelBasic;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.dialog.PreferencesDialog;
import mage.client.draft.DraftPanel;
import mage.client.game.GamePanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.DeckUtil;
import mage.client.util.GameManager;
import mage.client.util.IgnoreList;
import mage.client.util.audio.AudioManager;
import mage.client.util.object.SaveObjectUtil;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.ActionData;
import mage.remote.Session;
import mage.utils.CompressUtil;
import mage.view.*;
import mage.view.ChatMessage.MessageType;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CallbackClientImpl implements CallbackClient {

    private static final Logger logger = Logger.getLogger(CallbackClientImpl.class);
    private final MageFrame frame;
    private int messageId = 0;
    private int gameInformMessageId = 0;

    public CallbackClientImpl(MageFrame frame) {
        this.frame = frame;
    }

    @Override
    public synchronized void processCallback(final ClientCallback callback) {
        SaveObjectUtil.saveObject(callback.getData(), callback.getMethod().toString());
        callback.setData(CompressUtil.decompress(callback.getData()));
        SwingUtilities.invokeLater(() -> {
            try {
                logger.debug(callback.getMessageId() + " -- " + callback.getMethod());
                switch (callback.getMethod()) {
                    case START_GAME: {
                        TableClientMessage message = (TableClientMessage) callback.getData();
                        GameManager.instance.setCurrentPlayerUUID(message.getPlayerId());
                        gameStarted(message.getGameId(), message.getPlayerId());
                        break;
                    }
                    case START_TOURNAMENT: {
                        TableClientMessage message = (TableClientMessage) callback.getData();
                        tournamentStarted(message.getGameId(), message.getPlayerId());
                        break;
                    }
                    case START_DRAFT: {
                        TableClientMessage message = (TableClientMessage) callback.getData();
                        draftStarted(message.getGameId(), message.getPlayerId());
                        break;
                    }
                    case REPLAY_GAME:
                        replayGame(callback.getObjectId());
                        break;
                    case SHOW_TOURNAMENT:
                        showTournament(callback.getObjectId());
                        break;
                    case WATCHGAME:
                        watchGame(callback.getObjectId());
                        break;
                    case CHATMESSAGE: {
                        ChatMessage message = (ChatMessage) callback.getData();
                        // Drop messages from ignored users
                        if (message.getUsername() != null && IgnoreList.IGNORED_MESSAGE_TYPES.contains(message.getMessageType())) {
                            final String serverAddress = SessionHandler.getSession().getServerHostname().orElseGet(() -> "");
                            if (IgnoreList.userIsIgnored(serverAddress, message.getUsername())) {
                                break;
                            }
                        }

                        ChatPanelBasic panel = MageFrame.getChat(callback.getObjectId());
                        if (panel != null) {
                            // play the sound related to the message
                            if (message.getSoundToPlay() != null) {
                                switch (message.getSoundToPlay()) {
                                    case PlayerLeft:
                                        AudioManager.playPlayerLeft();
                                        break;
                                    case PlayerQuitTournament:
                                        AudioManager.playPlayerQuitTournament();
                                        break;
                                    case PlayerSubmittedDeck:
                                        AudioManager.playPlayerSubmittedDeck();
                                        break;
                                    case PlayerWhispered:
                                        AudioManager.playPlayerWhispered();
                                        break;
                                }
                            }
                            // send start message to chat if not done yet
                            if (!panel.isStartMessageDone()) {
                                createChatStartMessage(panel);
                            }
                            // send the message to subchat if exists and it's not a game message
                            if (message.getMessageType() != MessageType.GAME && panel.getConnectedChat() != null) {
                                panel.getConnectedChat().receiveMessage(message.getUsername(), message.getMessage(), message.getTime(), message.getMessageType(), ChatMessage.MessageColor.BLACK);
                            } else {
                                panel.receiveMessage(message.getUsername(), message.getMessage(), message.getTime(), message.getMessageType(), message.getColor());
                            }

                        }
                        break;
                    }
                    case SERVER_MESSAGE:
                        if (callback.getData() != null) {
                            ChatMessage message = (ChatMessage) callback.getData();
                            if (message.getColor() == ChatMessage.MessageColor.RED) {
                                JOptionPane.showMessageDialog(null, message.getMessage(), "Server message", JOptionPane.WARNING_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, message.getMessage(), "Server message", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;
                    case JOINED_TABLE: {
                        TableClientMessage message = (TableClientMessage) callback.getData();
                        joinedTable(message.getRoomId(), message.getTableId(), message.getFlag());
                        break;
                    }
                    case REPLAY_INIT: {
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            panel.init((GameView) callback.getData());
                        }
                        break;
                    }
                    case REPLAY_DONE: {
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            panel.endMessage((String) callback.getData(), callback.getMessageId());
                        }
                        break;
                    }
                    case REPLAY_UPDATE: {
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            panel.updateGame((GameView) callback.getData());
                        }
                        break;
                    }
                    case GAME_INIT: {
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_INIT", callback.getObjectId(), callback.getData());
                            panel.init((GameView) callback.getData());
                        }
                        break;
                    }
                    case GAME_OVER: {
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            Session session = SessionHandler.getSession();
                            if (session.isJsonLogActive()) {
                                appendJsonEvent("GAME_OVER", callback.getObjectId(), callback.getData());
                                ActionData actionData = appendJsonEvent("GAME_OVER", callback.getObjectId(), callback.getData());
                                String logFileName = "game-" + actionData.gameId + ".json";
                                S3Uploader.upload(logFileName, actionData.gameId.toString());
                            }
                            panel.endMessage((String) callback.getData(), callback.getMessageId());
                        }
                        break;
                    }
                    case GAME_ERROR:
                        frame.showErrorDialog("Game Error", (String) callback.getData());
                        break;
                    case GAME_ASK: {
                        GameClientMessage message = (GameClientMessage) callback.getData();
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_ASK", callback.getObjectId(), message);
                            panel.ask(message.getMessage(), message.getGameView(), callback.getMessageId(), message.getOptions());
                        }
                        break;
                    }
                    case GAME_TARGET: // e.g. Pick triggered ability
                    {
                        GameClientMessage message = (GameClientMessage) callback.getData();

                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_TARGET", callback.getObjectId(), message);
                            panel.pickTarget(message.getMessage(), message.getCardsView(), message.getGameView(),
                                    message.getTargets(), message.isFlag(), message.getOptions(), callback.getMessageId());
                        }
                        break;
                    }
                    case GAME_SELECT: {
                        GameClientMessage message = (GameClientMessage) callback.getData();

                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_SELECT", callback.getObjectId(), message);
                            panel.select(message.getMessage(), message.getGameView(), callback.getMessageId(), message.getOptions());
                        }
                        break;
                    }
                    case GAME_CHOOSE_ABILITY: {
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_CHOOSE_ABILITY", callback.getObjectId(), callback.getData());
                            panel.pickAbility((AbilityPickerView) callback.getData());
                        }
                        break;
                    }
                    case GAME_CHOOSE_PILE: {
                        GameClientMessage message = (GameClientMessage) callback.getData();
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_CHOOSE_PILE", callback.getObjectId(), message);
                            panel.pickPile(message.getMessage(), message.getPile1(), message.getPile2());
                        }
                        break;
                    }
                    case GAME_CHOOSE_CHOICE: {
                        GameClientMessage message = (GameClientMessage) callback.getData();

                        GamePanel panel = MageFrame.getGame(callback.getObjectId());

                        if (panel != null) {
                            appendJsonEvent("GAME_CHOOSE_CHOICE", callback.getObjectId(), message);
                            panel.getChoice(message.getChoice(), callback.getObjectId());
                        }
                        break;
                    }
                    case GAME_PLAY_MANA: {
                        GameClientMessage message = (GameClientMessage) callback.getData();
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_PLAY_MANA", callback.getObjectId(), message);
                            panel.playMana(message.getMessage(), message.getGameView(), message.getOptions(), callback.getMessageId());
                        }
                        break;
                    }
                    case GAME_PLAY_XMANA: {
                        GameClientMessage message = (GameClientMessage) callback.getData();

                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_PLAY_XMANA", callback.getObjectId(), message);
                            panel.playXMana(message.getMessage(), message.getGameView(), callback.getMessageId());
                        }
                        break;
                    }
                    case GAME_GET_AMOUNT: {
                        GameClientMessage message = (GameClientMessage) callback.getData();

                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            appendJsonEvent("GAME_GET_AMOUNT", callback.getObjectId(), message);

                            panel.getAmount(message.getMin(), message.getMax(), message.getMessage());
                        }
                        break;
                    }
                    case GAME_UPDATE: {
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());

                        if (panel != null) {
                            appendJsonEvent("GAME_UPDATE", callback.getObjectId(), callback.getData());

                            panel.updateGame((GameView) callback.getData(), true, null, null); // update after undo
                        }
                        break;
                    }
                    case END_GAME_INFO:
                        MageFrame.getInstance().showGameEndDialog((GameEndView) callback.getData());

                        break;
                    case SHOW_USERMESSAGE:
                        List<String> messageData = (List<String>) callback.getData();
                        if (messageData.size() == 2) {
                            JOptionPane.showMessageDialog(null, messageData.get(1), messageData.get(0), JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case GAME_INFORM:
                        if (callback.getMessageId() > gameInformMessageId) {
                            {
                                GameClientMessage message = (GameClientMessage) callback.getData();
                                GamePanel panel = MageFrame.getGame(callback.getObjectId());
                                if (panel != null) {
                                    appendJsonEvent("GAME_INFORM", callback.getObjectId(), message);
                                    panel.inform(message.getMessage(), message.getGameView(), callback.getMessageId());
                                }
                            }
// no longer needed because phase skip handling on server side now
                        } else {
                            logger.warn(new StringBuilder("message out of sequence - ignoring").append("MessageId = ").append(callback.getMessageId()).append(" method = ").append(callback.getMethod()));
                            //logger.warn("message out of sequence - ignoring");
                        }
                        gameInformMessageId = messageId;
                        break;
                    case GAME_INFORM_PERSONAL: {
                        GameClientMessage message = (GameClientMessage) callback.getData();
                        GamePanel panel = MageFrame.getGame(callback.getObjectId());
                        if (panel != null) {
                            JOptionPane.showMessageDialog(panel, message.getMessage(), "Game message",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    }
                    case SIDEBOARD: {
                        TableClientMessage message = (TableClientMessage) callback.getData();
                        DeckView deckView = message.getDeck();
                        Deck deck = DeckUtil.construct(deckView);
                        if (message.getFlag()) {
                            construct(deck, message.getTableId(), message.getTime());
                        } else {
                            sideboard(deck, message.getTableId(), message.getTime());
                        }
                        break;
                    }
                    case VIEW_LIMITED_DECK: {
                        TableClientMessage message = (TableClientMessage) callback.getData();
                        DeckView deckView = message.getDeck();
                        Deck deck = DeckUtil.construct(deckView);
                        viewLimitedDeck(deck, message.getTableId(), message.getTime());
                        break;
                    }
                    case CONSTRUCT: {
                        TableClientMessage message = (TableClientMessage) callback.getData();
                        DeckView deckView = message.getDeck();
                        Deck deck = DeckUtil.construct(deckView);
                        construct(deck, message.getTableId(), message.getTime());
                        break;
                    }
                    case DRAFT_OVER:
                        MageFrame.removeDraft(callback.getObjectId());
                        break;
                    case DRAFT_PICK: {
                        DraftClientMessage message = (DraftClientMessage) callback.getData();
                        DraftPanel panel = MageFrame.getDraft(callback.getObjectId());
                        if (panel != null) {
                            panel.loadBooster(message.getDraftPickView());
                        }
                        break;
                    }
                    case DRAFT_UPDATE: {
                        DraftPanel panel = MageFrame.getDraft(callback.getObjectId());
                        DraftClientMessage message = (DraftClientMessage) callback.getData();
                        if (panel != null) {
                            panel.updateDraft(message.getDraftView());
                        }
                        break;
                    }
                    case DRAFT_INIT: {
                        DraftClientMessage message = (DraftClientMessage) callback.getData();
                        DraftPanel panel = MageFrame.getDraft(callback.getObjectId());
                        if (panel != null) {
                            panel.loadBooster(message.getDraftPickView());
                        }
                        break;
                    }
                    case TOURNAMENT_INIT:
                        break;
                    case USER_REQUEST_DIALOG:
                        frame.showUserRequestDialog((UserRequestMessage) callback.getData());
                        break;
                    default:
                        break;
                }
                messageId = callback.getMessageId();
            } catch (Exception ex) {
                handleException(ex);
            }
        });
    }

    private ActionData appendJsonEvent(String name, UUID gameId, Object value) {
        Session session = SessionHandler.getSession();
        if (session.isJsonLogActive()) {
            ActionData actionData = new ActionData(name, gameId);
            actionData.value = value;
            session.appendJsonLog(actionData);
            return actionData;
        }
        return null;
    }

    private void createChatStartMessage(ChatPanelBasic chatPanel) {
        chatPanel.setStartMessageDone(true);
        ChatPanelBasic usedPanel = chatPanel;
        if (chatPanel.getConnectedChat() != null) {
            usedPanel = chatPanel.getConnectedChat();
        }
        switch (usedPanel.getChatType()) {
            case GAME:
                usedPanel.receiveMessage("", new StringBuilder("You may use hot keys to play faster:")
                                .append("<br/>Turn mousewheel up (ALT-e) - enlarge image of card the mousepointer hovers over")
                                .append("<br/>Turn mousewheel down (ALT-s) - enlarge original/alternate image of card the mousepointer hovers over")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_CONFIRM)))
                                .append("</b> - Confirm \"Ok\", \"Yes\" or \"Done\" button")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_NEXT_TURN)))
                                .append("</b> - Skip current turn but stop on declare attackers/blockers and something on the stack")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_END_STEP)))
                                .append("</b> - Skip to next end step but stop on declare attackers/blockers and something on the stack")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_SKIP_STEP)))
                                .append("</b> - Skip current turn but stop on declare attackers/blockers")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_MAIN_STEP)))
                                .append("</b> - Skip to next main phase but stop on declare attackers/blockers and something on the stack")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_YOUR_TURN)))
                                .append("</b> - Skip everything until your next turn")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_PRIOR_END)))
                                .append("</b> - Skip everything until the end step just prior to your turn")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_CANCEL_SKIP)))
                                .append("</b> - Undo F4/F5/F7/F9/F11")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_SWITCH_CHAT)))
                                .append("</b> - Switth in/out to chat text field")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_TOGGLE_MACRO)))
                                .append("</b> - Toggle recording a sequence of actions to repeat. Will not pause if interrupted and can fail if a selected card changes such as when scrying top card to bottom.")
                                .append("<br/><b>").append(System.getProperty("os.name").contains("Mac OS X") ? "Cmd" : "Ctrl").append(" + click</b> - Hold priority while casting a spell or activating an ability")
                                .append("<br/>").append("Type <b>/FIX</b> message in chat to fix freezed game")
                                .toString(),
                        null, MessageType.USER_INFO, ChatMessage.MessageColor.BLUE);
                break;
            case TOURNAMENT:
                usedPanel.receiveMessage("", "On this panel you can see the players, their state and the results of the games of the tournament. Also you can chat with the competitors of the tournament.",
                        null, MessageType.USER_INFO, ChatMessage.MessageColor.BLUE);
                break;
            case TABLES:
                usedPanel.receiveMessage("", new StringBuilder("Download card images by using the \"Images\" menu to the top right .")
                                .append("<br/>Download icons and symbols by using the \"Symbols\" menu to the top right.")
                                .append("<br/>\\list - Show a list of available chat commands.")
                                .append("<br/>").append(IgnoreList.usage())
                                .append("<br/>Type <font color=green>\\w yourUserName profanity 0 (or 1 or 2)</font> to turn off/on the profanity filter").toString(),
                        null, MessageType.USER_INFO, ChatMessage.MessageColor.BLUE);
                break;
            default:
                break;

        }
    }

    private void joinedTable(UUID roomId, UUID tableId, boolean isTournament) {
        try {
            frame.showTableWaitingDialog(roomId, tableId, isTournament);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    protected void gameStarted(final UUID gameId, final UUID playerId) {
        try {
            frame.showGame(gameId, playerId);
            logger.info("Game " + gameId + " started for player " + playerId);
        } catch (Exception ex) {
            handleException(ex);
        }

        if (Plugins.instance.isCounterPluginLoaded()) {
            Plugins.instance.addGamesPlayed();
        }
    }

    protected void draftStarted(UUID draftId, UUID playerId) {
        try {
            frame.showDraft(draftId);
            logger.info("Draft " + draftId + " started for player " + playerId);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    protected void tournamentStarted(UUID tournamentId, UUID playerId) {
        try {
            frame.showTournament(tournamentId);
            AudioManager.playTournamentStarted();
            logger.info("Tournament " + tournamentId + " started for player " + playerId);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    /**
     * Shows the tournament info panel for a tournament
     *
     * @param tournamentId
     */
    protected void showTournament(UUID tournamentId) {
        try {
            frame.showTournament(tournamentId);
            logger.info("Showing tournament " + tournamentId);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    protected void watchGame(UUID gameId) {
        try {
            frame.watchGame(gameId);
            logger.info("Watching game " + gameId);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    protected void replayGame(UUID gameId) {
        try {
            frame.replayGame(gameId);
            logger.info("Replaying game");
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    protected void sideboard(Deck deck, UUID tableId, int time) {
        frame.showDeckEditor(DeckEditorMode.SIDEBOARDING, deck, tableId, time);
    }

    protected void construct(Deck deck, UUID tableId, int time) {
        frame.showDeckEditor(DeckEditorMode.LIMITED_BUILDING, deck, tableId, time);
    }

    protected void viewLimitedDeck(Deck deck, UUID tableId, int time) {
        frame.showDeckEditor(DeckEditorMode.VIEW_LIMITED_DECK, deck, tableId, time);
    }

    private void handleException(Exception ex) {
        logger.fatal("Client error\n", ex);
        frame.showError("Error: " + ex.getMessage());
    }
}
