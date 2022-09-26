package mage.remote;


import mage.choices.Choice;
import mage.remote.messages.MessageType;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.DeckView;
import mage.view.DraftPickView;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.UserRequestMessage;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ClientCallback {
    
    ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    void construct(String sessionId, UUID tableId, DeckView deck, int time);

    void draftInit(String sessionId, UUID draftId, DraftPickView draftPickView);

    void draftOver(String sessionId, UUID draftId);

    void draftPick(String sessionId, UUID draftId, DraftPickView draftPickView);

    void draftUpdate(String sessionId, UUID draftId, DraftView draftView);

    void endGameInfo(String sessionId, UUID gameId, GameEndView view);

    void gameAsk(String sessionId, UUID gameId, GameView gameView, String question, Map<String, Serializable> options);

    void gameChooseAbility(String sessionId, UUID gameId, GameView gameView, AbilityPickerView abilities);

    void gameChooseChoice(String sessionId, UUID gameId, GameView gameView, Choice choice);

    void gameChoosePile(String sessionId, UUID gameId, GameView gameView, String message, CardsView pile1, CardsView pile2);

    void gameError(String sessionId, UUID gameId, String message);

    void gameInform(String sessionId, UUID gameId, GameClientMessage message);

    void gameInformPersonal(String sessionId, UUID gameId, GameClientMessage message);

    void gameMultiAmount(String sessionId, UUID gameId, GameView gameView, Map<String, Serializable> option, List<String> messages, int min, int max);

    void gameOver(String sessionId, UUID gameId, GameView gameView, String message);

    void gamePlayMana(String sessionId, UUID gameId, GameView gameView, String message, Map<String, Serializable> options);

    void gamePlayXMana(String sessionId, UUID gameId, GameView gameView, String message);

    void gameSelect(String sessionId, UUID gameId, GameView gameView, String message, Map<String, Serializable> options);

    void gameSelectAmount(String sessionId, UUID gameId, GameView gameView, String message, int min, int max);

    void gameStarted(String sessionId, UUID gameId, UUID playerId);

    void gameTarget(String sessionId, UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options);

    void gameUpdate(String sessionId, UUID gameId, GameView view);

    void informClient(String sessionId, String title, String message, MessageType type);

    void informClients(String title, String message, MessageType type);

    void initGame(String sessionId, UUID gameId, GameView gameView);

    void joinedTable(String sessionId, UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament);

    void pingClient(String sessionId);

    void replayDone(String sessionId, UUID gameId, String result);

    void replayGame(String sessionId, UUID gameId);

    void replayInit(String sessionId, UUID gameId, GameView gameView);

    void replayUpdate(String sessionId, UUID gameId, GameView gameView);

    void sendChatMessage(String sessionId, UUID chatId, ChatMessage message);

    void showTournament(String sessionId, UUID tournamentId);

    void sideboard(String sessionId, UUID tableId, DeckView deck, int time, boolean limited);

    void start(int port, boolean ssl) throws Exception;

    void startDraft(String sessionId, UUID draftId, UUID playerId);

    void tournamentStarted(String sessionId, UUID tournamentId, UUID playerId);

    void userRequestDialog(String sessionId, UUID gameId, UserRequestMessage userRequestMessage);

    void viewLimitedDeck(String sessionId, UUID tableId, DeckView deck, int time, boolean limited);

    void viewSideboard(String sessionId, UUID gameId, UUID targetPlayerId);

    void watchGame(String sessionId, UUID gameId, UUID chatId, GameView game);
    
}
