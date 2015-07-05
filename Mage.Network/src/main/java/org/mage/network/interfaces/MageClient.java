package org.mage.network.interfaces;

import java.io.Serializable;
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
import org.mage.network.messages.MessageType;

/**
 *
 * @author BetaSteward
 */
public interface MageClient {
    
    void connected(String message);
    void disconnected(boolean error);
    
    void inform(String title, String message, MessageType type);
    
    void receiveChatMessage(UUID chatId, ChatMessage message);
    void receiveBroadcastMessage(String message);

    void clientRegistered(ServerState state);
    ServerState getServerState();
    
    void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament);
    void gameStarted(UUID gameId, UUID playerId);

    void initGame(UUID gameId, GameView gameView);
    void gameUpdate(UUID gameId, GameView gameView);
    void gameInform(UUID gameId, GameClientMessage message);
    void gameInformPersonal(UUID gameId, GameClientMessage message);
    void gameOver(UUID gameId, String message);
    void gameError(UUID gameId, String message);

    void gameAsk(UUID gameId, GameView gameView, String question);
    void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options);
    void gameChooseAbility(UUID gameId, AbilityPickerView abilities);
    void gameChoosePile(UUID gameId, String message, CardsView pile1, CardsView pile2);
    void gameChooseChoice(UUID gameId, Choice choice);
    void gamePlayMana(UUID gameId, GameView gameView, String message);
    void gamePlayXMana(UUID gameId, GameView gameView, String message);
    void gameSelectAmount(UUID gameId, String message, int min, int max);
    void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options);

    void gameEndInfo(UUID gameId, GameEndView view);

    void userRequestDialog(UUID gameId, UserRequestMessage userRequestMessage);

    void sideboard(UUID tableId, DeckView deck, int time, boolean limited);
    void construct(UUID tableId, DeckView deck, int time);

    void startDraft(UUID draftId, UUID playerId);
    void draftInit(UUID draftId, DraftPickView draftPickView);
    void draftUpdate(UUID draftId, DraftView draftView);
    void draftPick(UUID draftId, DraftPickView draftPickView);
    void draftOver(UUID draftId);

    void showTournament(UUID tournamentId);
    void tournamentStarted(UUID tournamentId);
    
}
