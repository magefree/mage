package org.mage.network;

import org.mage.network.model.UserRequestDialogMessage;
import org.mage.network.model.GameEndInfoMessage;
import org.mage.network.model.GameSelectAmountMessage;
import org.mage.network.model.GamePlayXManaMessage;
import org.mage.network.model.GamePlayManaMessage;
import org.mage.network.model.GameChooseChoiceMessage;
import org.mage.network.model.GameChoosePileMessage;
import org.mage.network.model.GameChooseAbilityMessage;
import org.mage.network.model.GameSelectMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.choices.Choice;
import mage.game.Table;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;
import org.mage.network.handlers.ExceptionHandler;
import org.mage.network.handlers.MessageHandler;
import org.mage.network.handlers.server.HeartbeatHandler;
import org.mage.network.handlers.PingMessageHandler;
import org.mage.network.handlers.WriteListener;
import org.mage.network.handlers.server.ConnectionHandler;
import org.mage.network.handlers.server.ServerRequestHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ChatMessageMessage;
import org.mage.network.model.GameAskMessage;
import org.mage.network.model.GameInitMessage;
import org.mage.network.model.GameStartedMessage;
import org.mage.network.model.GameTargetMessage;
import org.mage.network.model.InformClientMessage;
import org.mage.network.model.JoinedTableMessage;
import org.mage.network.model.MessageType;
import org.mage.network.model.PingMessage;

/**
 *
 * @author BetaSteward
 */
public class Server {
    
    private static final Logger logger = Logger.getLogger(Server.class);

    private static final int IDLE_PING_TIME = 30;
    private static final int IDLE_TIMEOUT = 60;
    private static final PingMessage ping = new PingMessage();
    
    public static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    
    private SslContext sslCtx;

    private final MageServer server;
//    private final MessageHandler h;
    private final PingMessageHandler pingMessageHandler = new PingMessageHandler();
    private final EventExecutorGroup handlersExecutor = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() * 2);
    private final ServerRequestHandler serverMessageHandler;
    
    private final ExceptionHandler exceptionHandler;
    
    public Server(MageServer server) {
        this.server = server;
//        h = new MessageHandler();
        serverMessageHandler = new ServerRequestHandler(server);
        exceptionHandler = new ExceptionHandler();
    }
    
    public void start(int port, boolean ssl) throws Exception {
        
        if (ssl) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }
        
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ServerInitializer());

            b.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            logger.fatal("Error starting server", ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        
    }

    private class ServerInitializer extends ChannelInitializer<SocketChannel> {
        
        @Override
        public void initChannel(SocketChannel ch) throws Exception {
            
            if (sslCtx != null) {
               ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
            }
            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
            ch.pipeline().addLast(new ObjectEncoder());

//            ch.pipeline().addLast("h", h);
            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(IDLE_TIMEOUT, IDLE_PING_TIME, 0));
            ch.pipeline().addLast(handlersExecutor, "heartbeatHandler", new HeartbeatHandler(server));
            ch.pipeline().addLast("pingMessageHandler", pingMessageHandler);

            ch.pipeline().addLast("connectionHandler", new ConnectionHandler());
            ch.pipeline().addLast(handlersExecutor, "serverMessageHandler", serverMessageHandler);
            ch.pipeline().addLast("exceptionHandler", exceptionHandler);
        }

    }    
    
    private Channel findChannel(String sessionId) {
        for (Channel channel: clients) {
            if (channel.id().asLongText().equals(sessionId)) {
                return channel;
            }
        }
        return null;
    }

    public void sendChatMessage(String sessionId, UUID chatId, ChatMessage message) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new ChatMessageMessage(chatId, message)).addListener(WriteListener.getInstance());
    }

    public void informClient(String sessionId, String title, String message, MessageType type) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new InformClientMessage(title, message, type)).addListener(WriteListener.getInstance());
    }

    public void informClients(String title, String message, MessageType type) {
        clients.writeAndFlush(new InformClientMessage(title, message, type)).addListener(WriteListener.getInstance());
    }

    public void pingClient(String sessionId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            HeartbeatHandler heartbeatHandler = (HeartbeatHandler)ch.pipeline().get("heartbeatHandler");
            heartbeatHandler.pingClient();
        }
    }

    public void joinedTable(String sessionId, UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new JoinedTableMessage(roomId, tableId, chatId, owner, tournament)).addListener(WriteListener.getInstance());
    }
    
    public void gameStarted(String sessionId, UUID gameId, UUID playerId) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameStartedMessage(gameId, playerId)).addListener(WriteListener.getInstance());
    }

    public void initGame(String sessionId, UUID gameId, GameView gameView) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameInitMessage(gameId, gameView)).addListener(WriteListener.getInstance());
    }
    
    public void gameAsk(String sessionId, UUID gameId, GameView gameView, String question) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameAskMessage(gameId, gameView, question)).addListener(WriteListener.getInstance());
    }

    public void gameTarget(String sessionId, UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameTargetMessage(gameId, gameView, question, cardView, targets, required, options)).addListener(WriteListener.getInstance());
    }

    public void gameSelect(String sessionId, UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameSelectMessage(gameId, gameView, message, options)).addListener(WriteListener.getInstance());
    }

    public void gameChooseAbility(String sessionId, UUID gameId, AbilityPickerView abilities) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameChooseAbilityMessage(gameId, abilities)).addListener(WriteListener.getInstance());
    }

    public void gameChoosePile(String sessionId, UUID gameId, String message, CardsView pile1, CardsView pile2) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameChoosePileMessage(gameId, message, pile1, pile2)).addListener(WriteListener.getInstance());
    }

    public void gameChooseChoice(String sessionId, UUID gameId, Choice choice) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameChooseChoiceMessage(gameId, choice)).addListener(WriteListener.getInstance());
    }

    public void gamePlayMana(String sessionId, UUID gameId, GameView gameView, String message) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GamePlayManaMessage(gameId, gameView, message)).addListener(WriteListener.getInstance());
    }

    public void gamePlayXMana(String sessionId, UUID gameId, GameView gameView, String message) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GamePlayXManaMessage(gameId, gameView, message)).addListener(WriteListener.getInstance());
    }

    public void gameSelectAmount(String sessionId, UUID gameId, String message, int min, int max) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameSelectAmountMessage(gameId, message, min, max)).addListener(WriteListener.getInstance());
    }

    public void endGameInfo(String sessionId, UUID gameId, GameEndView view) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new GameEndInfoMessage(gameId, view)).addListener(WriteListener.getInstance());
    }

    public void userRequestDialog(String sessionId, UUID gameId, UserRequestMessage userRequestMessage) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new UserRequestDialogMessage(gameId, userRequestMessage)).addListener(WriteListener.getInstance());
    }

    
}
