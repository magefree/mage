package org.mage.test.load;

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
import mage.view.UserRequestMessage;
import mage.remote.messages.MessageType;
import mage.remote.interfaces.MageClient;
import mage.remote.Session;
import mage.utils.MageVersion;
import mage.view.GameView;
import org.apache.log4j.Logger;

import java.util.UUID;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * For tests only
 *
 * @author noxx, JayDi85
 */
public class SimpleMageClient implements MageClient {

    private final UUID clientId;
    private static final MageVersion version = new MageVersion(MageClient.class);

    private static final Logger log = Logger.getLogger(SimpleMageClient.class);

    private final LoadCallbackClient callbackClient;

    public SimpleMageClient(boolean joinGameChat) {
        clientId = UUID.randomUUID();
        callbackClient = new LoadCallbackClient(joinGameChat);
    }

    
    public MageVersion getVersion() {
        return version;
    }

    @Override
    public void connected(String message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void disconnected(boolean askToReconnect) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public void showMessage(String message) {
        log.info(message);
    }

    
    public void showError(String message) {
        log.error(message);
    }

    public void setSession(Session session) {
        callbackClient.setSession(session);
    }

    public boolean isGameOver() {
        return callbackClient.isGameOver();
    }

    public void setConcede(boolean needToConcede) {
        this.callbackClient.setConcede(needToConcede);
    }

    public String getLastGameResult() {
        return this.callbackClient.getLastGameResult();
    }

    public GameView getLastGameView() {
        return this.callbackClient.getLastGameView();
    }

    @Override
    public void inform(String title, String message, MessageType type) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void receiveChatMessage(UUID chatId, ChatMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void clientRegistered(ServerState state) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ServerState getServerState() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameStarted(UUID gameId, UUID playerId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void initGame(UUID gameId, GameView gameView) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameUpdate(UUID gameId, GameView gameView) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameInform(UUID gameId, GameClientMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameInformPersonal(UUID gameId, GameClientMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameOver(UUID gameId, String message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameError(UUID gameId, String message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameAsk(UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameChooseAbility(UUID gameId, AbilityPickerView abilities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameChoosePile(UUID gameId, String message, CardsView pile1, CardsView pile2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameChooseChoice(UUID gameId, Choice choice) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gamePlayMana(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gamePlayXMana(UUID gameId, GameView gameView, String message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameSelectAmount(UUID gameId, GameView gameView, String message, int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameMultiAmount(UUID gameId, GameView gameView, Map<String, Serializable> option, List<String> messages, int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void gameEndInfo(UUID gameId, GameEndView view) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void userRequestDialog(UUID gameId, UserRequestMessage userRequestMessage) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void sideboard(UUID tableId, DeckView deck, int time, boolean limited) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void viewLimitedDeck(UUID tableId, DeckView deck, int time, boolean limited) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void viewSideboard(UUID gameId, UUID targetPlayerId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void construct(UUID tableId, DeckView deck, int time) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void startDraft(UUID draftId, UUID playerId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void draftInit(UUID draftId, DraftPickView draftPickView) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void draftUpdate(UUID draftId, DraftView draftView) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void draftPick(UUID draftId, DraftPickView draftPickView) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void draftOver(UUID draftId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void showTournament(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void tournamentStarted(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void watchGame(UUID gameId, UUID chatId, GameView game) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void replayGame(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void replayInit(UUID gameId, GameView gameView) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void replayDone(UUID gameId, String result) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void replayUpdate(UUID gameId, GameView gameView) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
