package mage.remote.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.choices.Choice;
import mage.interfaces.ServerState;
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
import mage.remote.messages.MessageType;

/**
 *
 * @author BetaSteward
 */
public interface MageClient {
    
    void connected(String message);
    void disconnected(boolean error);
    
    void inform(String title, String message, MessageType type);
    
    void receiveChatMessage(UUID chatId, ChatMessage message);

    void clientRegistered(ServerState state);
    ServerState getServerState();
    
    void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament);
    void gameStarted(UUID gameId, UUID playerId);

    void initGame(UUID gameId, GameView gameView);
    void gameUpdate(UUID gameId, GameView gameView);
    void gameInform(UUID gameId, GameClientMessage message);
    void gameInformPersonal(UUID gameId, GameClientMessage message);
    void gameOver(UUID gameId, GameView gameView, String message);
    void gameError(UUID gameId, String message);

    void gameAsk(UUID gameId, GameView gameView, String question, Map<String, Serializable> options);
    void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options);
    void gameChooseAbility(UUID gameId, GameView gameView, AbilityPickerView abilities);
    void gameChoosePile(UUID gameId, GameView gameView, String message, CardsView pile1, CardsView pile2);
    void gameChooseChoice(UUID gameId, GameView gameView, Choice choice);
    void gamePlayMana(UUID gameId, GameView gameView, String message, Map<String, Serializable> options);
    void gamePlayXMana(UUID gameId, GameView gameView, String message);
    void gameSelectAmount(UUID gameId, GameView gameView, String message, int min, int max);
    void gameMultiAmount(UUID gameId, GameView gameView, Map<String, Serializable> option, List<String> messages, int min, int max);
    void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options);

    void gameEndInfo(UUID gameId, GameEndView view);

    void userRequestDialog(UUID gameId, UserRequestMessage userRequestMessage);

    void sideboard(UUID tableId, DeckView deck, int time, boolean limited);
    void viewLimitedDeck(UUID tableId, DeckView deck, int time, boolean limited);
    void viewSideboard(UUID gameId, UUID targetPlayerId);
    void construct(UUID tableId, DeckView deck, int time);

    void startDraft(UUID draftId, UUID playerId);
    void draftInit(UUID draftId, DraftPickView draftPickView);
    void draftUpdate(UUID draftId, DraftView draftView);
    void draftPick(UUID draftId, DraftPickView draftPickView);
    void draftOver(UUID draftId);

    void showTournament(UUID tournamentId);
    void tournamentStarted(UUID tournamentId);
    
    void watchGame(UUID gameId, UUID chatId, GameView game);
    
    void replayGame(UUID gameId);
    void replayInit(UUID gameId, GameView gameView);
    void replayDone(UUID gameId, String result);
    void replayUpdate(UUID gameId, GameView gameView);
}
