package org.mage.network;

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
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.net.ssl.SSLException;
import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.ServerState;
import mage.remote.Connection;
import mage.utils.MageVersion;
import mage.view.DraftPickView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserDataView;
import mage.view.UserView;
import org.apache.log4j.Logger;
import org.mage.network.handlers.ExceptionHandler;
import org.mage.network.handlers.PingMessageHandler;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.handlers.client.ClientRegisteredMessageHandler;
import org.mage.network.handlers.client.HeartbeatHandler;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.MessageType;

/**
 *
 * @author BetaSteward
 */
public class Client {
    
    private static final Logger logger = Logger.getLogger(Client.class);
    
    private static final int IDLE_PING_TIME = 30;
    private static final int IDLE_TIMEOUT = 60;

    private final MageClient client;
//    private final MessageHandler h;
    private final ClientMessageHandler clientMessageHandler;
    private final ClientRegisteredMessageHandler clientRegisteredMessageHandler;
    
    private final ExceptionHandler exceptionHandler;
    
    private SslContext sslCtx;
    private Channel channel;
    private EventLoopGroup group;
    private String username;
    private String host;
    private int port;
    
    public Client(MageClient client) {
        this.client = client;
//        h = new MessageHandler();
        clientMessageHandler = new ClientMessageHandler(client);
        clientRegisteredMessageHandler = new ClientRegisteredMessageHandler();
        
        exceptionHandler = new ExceptionHandler();
    }
    
    public boolean connect(Connection connection, MageVersion version) {
        
        this.username = connection.getUsername();
        this.host = connection.getHost();
        this.port = connection.getPort();

        group = new NioEventLoopGroup();
        try {
            if (connection.isSSL()) {
                sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
            } else {
                sslCtx = null;
            }
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
            
            clientRegisteredMessageHandler.setConnection(connection);
            clientRegisteredMessageHandler.setVersion(version);
            channel = b.connect(host, port).sync().channel();
            ServerState state = clientRegisteredMessageHandler.registerClient();
            if (state.isValid()) {
                client.clientRegistered(state);
                client.connected(connection.getUsername() + "@" + host + ":" + port + " ");
                return true;
            }
            else {
                disconnect(false);
            }
        } catch (SSLException | InterruptedException ex) {
            logger.fatal("Error connecting", ex);
            client.inform("Error", "Error connecting", MessageType.ERROR);
            disconnect(false);
        }
        return false;
    }
        
    private class ClientInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        public void initChannel(SocketChannel ch) throws Exception {
            
            if (sslCtx != null) {
                ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), host, port));
            }
            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
            ch.pipeline().addLast(new ObjectEncoder());

            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(IDLE_TIMEOUT, IDLE_PING_TIME, 0));
            ch.pipeline().addLast("heartbeatHandler", new HeartbeatHandler());
            ch.pipeline().addLast("pingMessageHandler", new PingMessageHandler());

//            ch.pipeline().addLast("h", h);
            ch.pipeline().addLast("clientRegisteredMessageHandler", clientRegisteredMessageHandler);
            ch.pipeline().addLast("clientMessageHandler", clientMessageHandler);

            ch.pipeline().addLast("exceptionHandler", exceptionHandler);
        }
    }
    
    public void disconnect(boolean error) {
        
        try {
            channel.disconnect().sync();
            client.disconnected(error);
        } catch (InterruptedException ex) {
            logger.fatal("Error disconnecting", ex);
        } finally {
            group.shutdownGracefully();
        }
    }
    
    public boolean isConnected() {
        if (channel != null)
            return channel.isActive();
        return false;
    }
    
    public void sendChatMessage(UUID chatId, String message) {
        clientMessageHandler.sendMessage(chatId, message);
    }

    public void joinChat(UUID chatId) {
        clientMessageHandler.joinChat(chatId);
    }
    
    public void leaveChat(UUID chatId) {
        clientMessageHandler.leaveChat(chatId);
    }

    public void sendPlayerUUID(UUID gameId, UUID id) {
        clientMessageHandler.sendPlayerUUID(gameId, id);
    }

    public void sendPlayerBoolean(UUID gameId, boolean b) {
        clientMessageHandler.sendPlayerBoolean(gameId, b);
    }

    public void sendPlayerInteger(UUID gameId, int i) {
        clientMessageHandler.sendPlayerInteger(gameId, i);
    }

    public void sendPlayerString(UUID gameId, String string) {
        clientMessageHandler.sendPlayerString(gameId, string);
    }

    public void sendPlayerManaType(UUID gameId, UUID playerId, ManaType manaType) {
        clientMessageHandler.sendPlayerManaType(gameId, playerId, manaType);
    }

    public String getUserName() {
        return username;
    }

    public ServerState getServerState() {
        return client.getServerState();
    }

    public boolean submitDeck(UUID tableId, DeckCardLists deckCardLists) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateDeck(UUID tableId, DeckCardLists deckCardLists) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean sendFeedback(String title, String type, String message, String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, String human, int i, DeckCardLists importDeck, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deck, String password) {
        try {
            return clientMessageHandler.joinTable(roomId, tableId, playerName, playerType, skill, deck, password);
        } catch (Exception ex) {
            logger.error("Error joining table", ex);
        }
        return false;
    }

    public TableView createTable(UUID roomId, MatchOptions options) {
        try {
            return clientMessageHandler.createTable(roomId, options);
        } catch (Exception ex) {
            logger.error("Error creating table", ex);
        }
        return null;
    }

    public void removeTable(UUID roomId, UUID tableId) {
        try {
            clientMessageHandler.removeTable(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error removing table", ex);
        }
    }

    public String getSessionId() {
        return channel.id().asLongText();
    }

    public TableView createTournamentTable(UUID roomId, TournamentOptions tOptions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updatePreferencesForServer(UserDataView view) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean startMatch(UUID roomId, UUID tableId) {
        try {
            return clientMessageHandler.startMatch(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error starting match", ex);
        }
        return false;
    }

    public boolean startTournament(UUID roomId, UUID tableId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean leaveTable(UUID roomId, UUID tableId) {
        try {
            return clientMessageHandler.leaveTable(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error leaving table", ex);
        }
        return false;
    }

    public void swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
        try {
            clientMessageHandler.swapSeats(roomId, tableId, seatNum1, seatNum2);
        } catch (Exception ex) {
            logger.error("Error swaping seats", ex);
        }
    }

    public void sendPlayerAction(PlayerAction passPriorityAction, UUID gameId, Serializable data) {
        try {
            clientMessageHandler.sendPlayerAction(passPriorityAction, gameId, data);
        } catch (Exception ex) {
            logger.error("Error swaping seats", ex);
        }
    }

    public TableView getTable(UUID roomId, UUID tableId) {
        try {
            return clientMessageHandler.getTable(roomId, tableId);
        } catch (Exception ex) {
            logger.error("Error getting table", ex);
        }
        return null;
    }

    public void watchTournamentTable(UUID tableId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UUID getTournamentChatId(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean joinTournament(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void quitTournament(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public TournamentView getTournament(UUID tournamentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void watchTable(UUID roomId, UUID tableId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void replayGame(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UUID getRoomChatId(UUID roomId) {
        try {
            return clientMessageHandler.getChatRoomId(roomId);
        } catch (Exception ex) {
            logger.error("Error getting chat room id", ex);
        }
        return null;
    }

    public List<String> getServerMessages() {
        try {
            return clientMessageHandler.getServerMessages();
        } catch (Exception ex) {
            logger.error("Error getting server messages", ex);
        }
        return null;
    }

    public RoomView getRoom(UUID roomId) {
        try {
            return clientMessageHandler.getRoom(roomId);
        } catch (Exception ex) {
            logger.error("Error getting tables", ex);
        }
        return null;
    }

    public void cheat(UUID gameId, UUID playerId, DeckCardLists importDeck) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UUID joinGame(UUID gameId) {
        try {
            return clientMessageHandler.joinGame(gameId);
        } catch (Exception ex) {
            logger.error("Error joining game", ex);
        }
        return null;
    }

    public UUID watchGame(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean startReplay(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void stopWatching(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void stopReplay(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void nextPlay(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void previousPlay(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void skipForward(UUID gameId, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void quitMatch(UUID gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean joinDraft(UUID draftId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DraftPickView sendCardPick(UUID draftId, UUID id, Set<UUID> cardsHidden) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void sendCardMark(UUID draftId, UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void quitDraft(UUID draftId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void sendBroadcastMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void disconnectUser(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeTable(UUID uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void endUserSession(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<UserView> getUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
