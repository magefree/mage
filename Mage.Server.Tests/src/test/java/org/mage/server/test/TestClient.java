package org.mage.server.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.choices.Choice;
import mage.game.match.MatchOptions;
import mage.interfaces.ServerState;
import mage.players.net.UserGroup;
import mage.players.net.UserSkipPrioritySteps;
import mage.remote.Connection;
import mage.utils.MageVersion;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.DeckView;
import mage.view.DraftPickView;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.UserDataView;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;
import org.mage.network.Client;
import org.mage.network.interfaces.MageClient;
import org.mage.network.messages.MessageType;

/**
 *
 * @author BetaSteward
 */
public class TestClient implements MageClient {

    protected static final Logger logger = Logger.getLogger(TestClient.class);
    
    private Client client;
    private ServerState serverState;
    private String userName;
    private boolean joinedTableFired = false;
    
    public TestClient() {
        client = new Client(this);
    }
    
    public boolean connect(String userName) {
        this.userName = userName;
        Connection connection = new Connection();
        connection.setHost("localhost");
        connection.setPort(17171);
        connection.setSSL(true);
        connection.setUsername(userName);
        connection.setForceDBComparison(false);
        connection.setUserData(new UserDataView(UserGroup.PLAYER, 51, false, false, false, new UserSkipPrioritySteps(), "world", false, false, false, false, false, false));
        return client.connect(connection, MageVersion.getCurrent());
    }
    
    public void disconnect(boolean error) {
        client.disconnect(error);
    }
    
    public boolean isConnected() {
        return client.isConnected();
    }
    
    @Override
    public void connected(String message) {
    }

    @Override
    public void disconnected(boolean error) {
        // do nothing
    }

    @Override
    public void inform(String title, String message, MessageType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void joinMainChat() {
        UUID mainRoomId = client.getServerState().getMainRoomId();
        client.joinChat(client.getRoomChatId(mainRoomId));
    }
    
    public void sendChatMessage(UUID chatId, String message) {
        client.sendChatMessage(chatId, message);
    }

    @Override
    public void receiveChatMessage(UUID chatId, ChatMessage message) {
        logger.info("Recieved message for " + userName + ": " + message.getUsername() + "-" + message.getTime() + "-" + message.getMessage());
    }

    @Override
    public void receiveBroadcastMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clientRegistered(ServerState state) {
        this.serverState = state;
    }

    @Override
    public ServerState getServerState() {
        return serverState;
    }

    @Override
    public void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
        joinedTableFired = true;
    }
    
    public boolean isJoinedTableFired() {
        return joinedTableFired;
    }

    @Override
    public void gameStarted(UUID gameId, UUID playerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initGame(UUID gameId, GameView gameView) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameAsk(UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameChooseAbility(UUID gameId, AbilityPickerView abilities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameChoosePile(UUID gameId, String message, CardsView pile1, CardsView pile2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameChooseChoice(UUID gameId, Choice choice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gamePlayMana(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gamePlayXMana(UUID gameId, GameView gameView, String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameSelectAmount(UUID gameId, String message, int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameEndInfo(UUID gameId, GameEndView view) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void userRequestDialog(UUID gameId, UserRequestMessage userRequestMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getServerMessages() {
        return client.getServerMessages();
    }
    
    public UUID getMainRoomId() {
        return serverState.getMainRoomId();
    }
    
    public RoomView getRoom(UUID roomId) {
        return client.getRoom(roomId);
    }
    
    public TableView createTable(UUID roomId, MatchOptions options) {
        return client.createTable(roomId, options);
    }
    
    public boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deck, String password) {
        return client.joinTable(roomId, tableId, playerName, playerType, skill, deck, password);
    }

    @Override
    public void gameUpdate(UUID gameId, GameView gameView) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameInform(UUID gameId, GameClientMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameInformPersonal(UUID gameId, GameClientMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameOver(UUID gameId, String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gameError(UUID gameId, String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sideboard(UUID tableId, DeckView deck, int time, boolean limited) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void construct(UUID tableId, DeckView deck, int time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startDraft(UUID draftId, UUID playerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draftInit(UUID draftId, DraftPickView draftPickView) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draftUpdate(UUID draftId, DraftView draftView) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draftPick(UUID draftId, DraftPickView draftPickView) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draftOver(UUID draftId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTournament(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tournamentStarted(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void watchGame(UUID gameId, UUID chatId, GameView game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
