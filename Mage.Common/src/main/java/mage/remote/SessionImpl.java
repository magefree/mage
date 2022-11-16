package mage.remote;

import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionInfo;
import mage.players.net.UserData;
import mage.remote.handlers.PingMessageHandler;
import mage.remote.handlers.client.ClientExceptionHandler;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.handlers.client.HeartbeatHandler;
import mage.remote.messages.MessageType;
import mage.utils.MageVersion;
import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.remote.interfaces.MageClient;
import mage.interfaces.ServerState;
import mage.players.PlayerType;
import mage.view.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Allows client to make requests to server
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class SessionImpl implements Session {

    private static final Logger logger = Logger.getLogger(SessionImpl.class);

    private static final int IDLE_PING_TIME = 30;
    private static final int IDLE_TIMEOUT = 60;

    private final MageClient client;
    private ClientMessageHandler clientMessageHandler;
    private ClientExceptionHandler exceptionHandler;

    private SslContext sslCtx;
    private Channel channel;
    private String username;
    private String host;
    private int port;

    private static final EventLoopGroup group = new NioEventLoopGroup();
    
    public SessionImpl(MageClient client) {
        this.client = client;
    }

    @Override
    public String getSessionId() {
        return channel.id().asLongText();
    }

    private class SessionInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        public void initChannel(SocketChannel ch) throws Exception {
            if (sslCtx != null) {
                ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), host, port));
            }
            ch.pipeline().addLast(new ObjectDecoder(10 * 1024 * 1024, ClassResolvers.cacheDisabled(null)));
            ch.pipeline().addLast(new ObjectEncoder());
            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(IDLE_TIMEOUT, IDLE_PING_TIME, 0));
            ch.pipeline().addLast("heartbeatHandler", new HeartbeatHandler());
            ch.pipeline().addLast("pingMessageHandler", new PingMessageHandler());
            ch.pipeline().addLast("clientMessageHandler", clientMessageHandler);
            ch.pipeline().addLast("exceptionHandler", exceptionHandler);
        }
    }

    @Override
    public boolean register(Connection connection, MageVersion version) {
        this.username = connection.getUsername();
        this.host = connection.getHost();
        this.port = connection.getPort();
        
        try {
            if (connection.isSSL()) {
                sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
            } else {
                sslCtx = null;
            }

            clientMessageHandler = new ClientMessageHandler(client, true);
            exceptionHandler = new ClientExceptionHandler(this);

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SessionInitializer());

            clientMessageHandler.setConnection(connection);
            clientMessageHandler.setVersion(version);
            channel = bootstrap.connect(host, port).sync().channel();
            boolean ret = clientMessageHandler.registerUser();
            disconnect(false);
            return ret;
        } catch (Exception ex) {
            logger.fatal("Error connecting", ex);
            client.inform("Error", "Error connecting", MessageType.ERROR);
        }
        return false;
    }

    @Override
    public boolean emailAuthToken(Connection connection) {
        try {
            return clientMessageHandler.emailAuthToken(connection);
        } catch (Exception ex) {
            logger.error("Error quitting draft", ex);
        }
        return false;
    }
    
    @Override
    public boolean resetPassword(Connection connection) {
        try {
            return clientMessageHandler.resetPassword(connection);
        } catch (Exception ex) {
            logger.error("Error quitting draft", ex);
        }
        return false;
    }

    @Override
    public boolean connect(Connection connection, MageVersion version) {
        this.username = connection.getUsername();
        this.host = connection.getHost();
        this.port = connection.getPort();

        try {
            if (connection.isSSL()) {
                sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
            } else {
                sslCtx = null;
            }
            
            clientMessageHandler = new ClientMessageHandler(client, false);
            exceptionHandler = new ClientExceptionHandler(this);
            
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SessionInitializer());
            
            clientMessageHandler.setConnection(connection);
            clientMessageHandler.setVersion(version);
            channel = bootstrap.connect(host, port).sync().channel();
            Optional<ServerState> state = clientMessageHandler.connectClient();
            if ((state.isPresent())&&state.get().isValid()) {
                client.clientRegistered(state.get());
                client.connected(connection.getUsername() + "@" + host + ":" + port + " ");
                return true;
            } else {
                disconnect(false);
            }
        } catch (Exception ex) {
            logger.fatal("Error connecting", ex);
            client.inform("Error", "Error connecting", MessageType.ERROR);
        }
        return false;
    }

    @Override
    public Optional<String> getServerHostname() {
        return isConnected() ? Optional.of(host) : Optional.empty();
    }
    
    /**
     * @param error - true = connection was lost because of error and
     *                        ask the user if they want to try to reconnect
     */
    @Override
    public void disconnect(boolean error) {
        try {
            channel.disconnect().sync();
            client.disconnected(error);
        } catch (InterruptedException ex) {
            logger.fatal("Error disconnecting", ex);
        }
    }

    @Override
    public boolean sendFeedback(String title, String type, String message, String email) {
        try {
            clientMessageHandler.sendFeedback(title, type, message, email);
            return true;
        } catch (Exception ex) {
            logger.error("Error sending feedback", ex);
        }
        return false;
    }

    @Override
    public boolean isConnected() {
        if (channel != null) {
            return channel.isActive();
        }
        return false;
    }

    @Override
    public UUID getMainRoomId(){
        try {
            return clientMessageHandler.getMainRoomId();
        } catch (Exception ex) {
            logger.error("Error getting main room id", ex);
        }
        return null;
    }

    @Override
    public Optional<UUID> getRoomChatId(UUID roomId) {
        try {
            return Optional.of(clientMessageHandler.getChatRoomId(roomId));
        } catch (Exception ex) {
            logger.error("Error getting chat room id", ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<TableView> getTable(UUID roomId, UUID tableId) {
        try {
            return Optional.of(clientMessageHandler.getTable(roomId, tableId));
        } catch (Exception ex) {
            logger.error("Error getting table", ex);
        }
        return Optional.empty();
    }

    @Override
    public void watchTable(UUID roomId, UUID tableId) {
        try {
            clientMessageHandler.watchTable(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error watching table", ex);
        }
    }

    @Override
    public void watchTournamentTable(UUID tableId) {
        try {
            clientMessageHandler.watchTournamentTable(tableId);
        } catch (Exception ex) {
            logger.error("Error watching tournament table", ex);
        }
    }

    @Override
    public boolean joinTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deck, String password) {
        try {
            return clientMessageHandler.joinTable(roomId, tableId, playerName, playerType, skill, deck, password);
        } catch (Exception ex) {
            logger.error("Error joining table", ex);
        }
        return false;
    }

    @Override
    public boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deck, String password) {
        try {
            return clientMessageHandler.joinTournamentTable(roomId, tableId, playerName, playerType, skill, deck, password);
        } catch (Exception ex) {
            logger.error("Error joining tournament table", ex);
        }
        return false;
    }

    @Override
    public List<TableView> getTables(UUID roomId) {
        try {
            return clientMessageHandler.getTables(roomId);
        } catch (Exception ex) {
            logger.error("Error getting tables", ex);
        }
        return null;
    }

    @Override
    public TournamentView getTournament(UUID tournamentId) {
        try {
            return clientMessageHandler.getTournament(tournamentId);
        } catch (Exception ex) {
            logger.error("Error getting tournament", ex);
        }
        return null;
    }

    @Override
    public Optional<UUID> getTournamentChatId(UUID tournamentId) {
        try {
            return Optional.of(clientMessageHandler.getTournamentChatId(tournamentId));
        } catch (Exception ex) {
            logger.error("Error getting tournament chat room id", ex);
        }
        return Optional.empty();
    }

    @Override
    public void sendPlayerUUID(UUID gameId, UUID id) {
        clientMessageHandler.sendPlayerUUID(gameId, id);
    }

    @Override
    public void sendPlayerBoolean(UUID gameId, boolean b) {
        clientMessageHandler.sendPlayerBoolean(gameId, b);
    }

    @Override
    public void sendPlayerInteger(UUID gameId, int i) {
        clientMessageHandler.sendPlayerInteger(gameId, i);
    }

    @Override
    public void sendPlayerString(UUID gameId, String string) {
        clientMessageHandler.sendPlayerString(gameId, string);
    }

    @Override
    public void sendPlayerManaType(UUID gameId, UUID playerId, ManaType manaType) {
        clientMessageHandler.sendPlayerManaType(gameId, playerId, manaType);
    }

    @Override
    public DraftPickView pickCard(UUID draftId, UUID cardId, Set<UUID> cardsHidden) {
        try {
            return clientMessageHandler.pickCard(draftId, cardId, cardsHidden);
        } catch (Exception ex) {
            logger.error("Error sending card pick", ex);
        }
        return null;
    }

    @Override
    public void markCard(UUID draftId, UUID cardId) {
        try {
            clientMessageHandler.markCard(draftId, cardId);
        } catch (Exception ex) {
            logger.error("Error marking card", ex);
        }
    }
    

    @Override
    public void setBoosterLoaded(UUID draftId) {
        try {
            clientMessageHandler.setBoosterLoaded(draftId);
        } catch (Exception ex) {
            logger.error("Error marking card", ex);
        }
    }

    @Override
    public void joinChat(UUID chatId) {
        clientMessageHandler.joinChat(chatId);
    }

    @Override
    public void leaveChat(UUID chatId) {
        clientMessageHandler.leaveChat(chatId);
    }

    @Override
    public void sendChatMessage(UUID chatId, String message) {
        clientMessageHandler.sendMessage(chatId, message);
    }

    @Override
    public void sendBroadcastMessage(String message) {
        try {
            clientMessageHandler.sendBroadcastMessage(message);
        } catch (Exception ex) {
            logger.error("Error sending broadcast message", ex);
        }
    }

    @Override
    public Optional<UUID> joinGame(UUID gameId) {
        try {
            return Optional.of(clientMessageHandler.joinGame(gameId));
        } catch (Exception ex) {
            logger.error("Error joining game", ex);
        }
        return Optional.empty();
    }

    @Override
    public void joinDraft(UUID draftId) {
        try {
            clientMessageHandler.joinDraft(draftId);
        } catch (Exception ex) {
            logger.error("Error joining draft", ex);
        }
    }

    @Override
    public void joinTournament(UUID tournamentId) {
        try {
            clientMessageHandler.joinTournament(tournamentId);
        } catch (Exception ex) {
            logger.error("Error joining tournament", ex);
        }
    }

    @Override
    public boolean watchGame(UUID gameId){
        try {
            return clientMessageHandler.watchGame(gameId);
        } catch (Exception ex) {
            logger.error("Error watching game", ex);
        }
        return false;
    }

    @Override
    public void replayGame(UUID gameId) {
        try {
            clientMessageHandler.replayGame(gameId);
        } catch (Exception ex) {
            logger.error("Error replaying game", ex);
        }
    }

    @Override
    public TableView createTable(UUID roomId, MatchOptions options) {
        try {
            return clientMessageHandler.createTable(roomId, options);
        } catch (Exception ex) {
            logger.error("Error creating table", ex);
        }
        return null;
    }

    @Override
    public TableView createTournamentTable(UUID roomId, TournamentOptions options) {
        try {
            return clientMessageHandler.createTournamentTable(roomId, options);
        } catch (Exception ex) {
            logger.error("Error creating tournament table", ex);
        }
        return null;
    }

    /**
     * Remove table - called from admin console
     *
     * @param tableId
     * @return
     */
    @Override
    public void removeTable(UUID uuid) {
        try {
            clientMessageHandler.removeTable(uuid);
        } catch (Exception ex) {
            logger.error("Error removing table", ex);
        }
    }

    @Override
    public void swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
        try {
            clientMessageHandler.swapSeats(roomId, tableId, seatNum1, seatNum2);
        } catch (Exception ex) {
            logger.error("Error swaping seats", ex);
        }
    }

    @Override
    public boolean leaveTable(UUID roomId, UUID tableId) {
        try {
            return clientMessageHandler.leaveTable(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error leaving table", ex);
        }
        return false;
    }

    @Override
    public boolean startMatch(UUID roomId, UUID tableId) {
        try {
            return clientMessageHandler.startMatch(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error starting match", ex);
        }
        return false;
    }

    @Override
    public boolean startTournament(UUID roomId, UUID tableId) {
        try {
            return clientMessageHandler.startTournament(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error starting tournament", ex);
        }
        return false;
    }

    @Override
    public boolean submitDeck(UUID tableId, DeckCardLists deckCardLists) {
        try {
            return clientMessageHandler.submitDeck(tableId, deckCardLists);
        } catch (Exception ex) {
            logger.error("Error submitting deck", ex);
        }
        return false;
    }

    @Override
    public void updateDeck(UUID tableId, DeckCardLists deckCardLists) {
        try {
            clientMessageHandler.updateDeck(tableId, deckCardLists);
        } catch (Exception ex) {
            logger.error("Error updating deck", ex);
        }
    }

    @Override
    public void quitMatch(UUID gameId) {
        try {
            clientMessageHandler.quitMatch(gameId);
        } catch (Exception ex) {
            logger.error("Error quitting match", ex);
        }
    }

    @Override
    public void quitTournament(UUID tournamentId) {
        try {
            clientMessageHandler.quitTournament(tournamentId);
        } catch (Exception ex) {
            logger.error("Error quitting tournament", ex);
        }
    }

    @Override
    public void quitDraft(UUID draftId) {
        try {
            clientMessageHandler.quitDraft(draftId);
        } catch (Exception ex) {
            logger.error("Error quitting draft", ex);
        }
    }

    @Override
    public void sendPlayerAction(PlayerAction passPriorityAction, UUID gameId, Serializable data) {
        try {
            clientMessageHandler.sendPlayerAction(passPriorityAction, gameId, data);
        } catch (Exception ex) {
            logger.error("Error sending player action", ex);
        }
    }

    @Override
    public void stopWatching(UUID gameId) {
        try {
            clientMessageHandler.stopWatching(gameId);
        } catch (Exception ex) {
            logger.error("Error stopping watching game", ex);
        }
    }

    @Override
    public boolean startReplay(UUID gameId) {
        try {
            clientMessageHandler.startReplay(gameId);
            return true;
        } catch (Exception ex) {
            logger.error("Error starting replay", ex);
            return false;
        }
    }

    @Override
    public void stopReplay(UUID gameId) {
        clientMessageHandler.stopReplay(gameId);
    }

    @Override
    public void nextPlay(UUID gameId) {
        clientMessageHandler.nextPlay(gameId);
    }

    @Override
    public void previousPlay(UUID gameId) {
        clientMessageHandler.previousPlay(gameId);
    }

    @Override
    public void skipForward(UUID gameId, int i) {
        clientMessageHandler.skipForward(gameId, i);
    }

    @Override
    public void cheat(UUID gameId, UUID playerId, DeckCardLists importDeck) {
        try {
            clientMessageHandler.cheat(gameId, playerId, importDeck);
        } catch (Exception ex) {
            logger.error("Error cheating", ex);
        }
    }

    @Override
    public List<UserView> getUsers() {
        try {
            return clientMessageHandler.getUsers();
        } catch (Exception ex) {
            logger.error("Error getting users", ex);
        }
        return null;
    }

    @Override
    public Object getServerMessages() {
        try {
            return clientMessageHandler.getServerMessages();
        } catch (Exception ex) {
            logger.error("Error getting server messages", ex);
        }
        return null;
    }

    @Override
    public void disconnectUser(String string) {
        try {
            clientMessageHandler.disconnectUser(string);
        } catch (Exception ex) {
            logger.error("Error disconnecting user", ex);
        }
    }

    @Override
    public void endUserSession(String string) {
        try {
            clientMessageHandler.endUserSession(string);
        } catch (Exception ex) {
            logger.error("Error ending user session", ex);
        }
    }

    @Override
    public void muteUser(String userName, long durationMinutes){
        try {
            clientMessageHandler.muteUser(userName, durationMinutes);
        } catch (Exception ex) {
            logger.error("Error muting user", ex);
        }
    }

    @Override
    public void setActivation(String userName, boolean active) {
        try {
            clientMessageHandler.setActivation(userName, active);
        } catch (Exception ex) {
            logger.error("Error setting activation", ex);
        }
    }
    
    @Override
    public void toggleActivation(String userName) {
        try {
            clientMessageHandler.toggleActivation(userName);
        } catch (Exception ex) {
            logger.error("Error toggling activation", ex);
        }
    }
    
    @Override
    public void lockUser(String userName, long durationMinutes) {
        try {
            clientMessageHandler.lockUser(userName, durationMinutes);
        } catch (Exception ex) {
            logger.error("Error locking user", ex);
        }
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public boolean ping(Connection connection) {
        try {
            return clientMessageHandler.ping(connection);
        } catch (Exception ex) {
            logger.error("Error pinging", ex);
        }
        return false;
    }
    
    @Override
    public ServerState getServerState() {
        return client.getServerState();
    }
    
    @Override
    public RoomView getRoom(UUID roomId) {
        try {
            return clientMessageHandler.getRoom(roomId);
        } catch (Exception ex) {
            logger.error("Error getting tables", ex);
        }
        return null;
    }
    
    @Override
    public List<ExpansionInfo> getMissingExpansionsData(List<String> setCodes) {
        try {
            return clientMessageHandler.getMissingExpansionData(setCodes);
        } catch (Exception ex) {
            logger.error("Error getting missing expansion set data", ex);
        }
        return null;
    }
    
    @Override
    public List<CardInfo> getMissingCardsData(List<String> cards) {
        try {
            return clientMessageHandler.getMissingCardsData(cards);
        } catch (Exception ex) {
            logger.error("Error getting missing card data from server", ex);
        }
        return null;
    }
    
    @Override
    public List<String> getCards() {
        try {
            return clientMessageHandler.getCards();
        } catch (Exception ex) {
            logger.error("Error getting cards", ex);
        }
        return null;
    }
    
    @Override
    public void setPreferences(UserData userData) {
        try {
            clientMessageHandler.setPreferences(userData);
        } catch (Exception ex) {
            logger.error("Error updating preferences", ex);
        }
    }

}
