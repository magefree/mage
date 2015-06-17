package org.mage.network;

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
import java.util.UUID;
import mage.view.ChatMessage;
import org.apache.log4j.Logger;
import org.mage.network.handlers.ExceptionHandler;
import org.mage.network.handlers.MessageHandler;
import org.mage.network.handlers.server.HeartbeatHandler;
import org.mage.network.handlers.PingMessageHandler;
import org.mage.network.handlers.WriteListener;
//import org.mage.network.handlers.server.ChatMessageHandler;
import org.mage.network.handlers.server.ConnectionHandler;
//import org.mage.network.handlers.server.RegisterClientMessageHandler;
//import org.mage.network.handlers.server.RoomMessageHandler;
import org.mage.network.handlers.server.ServerRequestHandler;
//import org.mage.network.handlers.server.TableMessageHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ChatMessageMessage;
import org.mage.network.model.GameStartedMessage;
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
//    private final RegisterClientMessageHandler registerClientMessageHandler;

//    private final ChatMessageHandler chatMessageHandler;
    private final ServerRequestHandler serverMessageHandler;
//    private final RoomMessageHandler roomMessageHandler;
//    private final TableMessageHandler tableMessageHandler;
    
    private final ExceptionHandler exceptionHandler;
    
    public Server(MageServer server) {
        this.server = server;
//        h = new MessageHandler();
//        registerClientMessageHandler = new RegisterClientMessageHandler(server);
//        chatMessageHandler = new ChatMessageHandler(server);
        serverMessageHandler = new ServerRequestHandler(server);
//        roomMessageHandler = new RoomMessageHandler(server);
//        tableMessageHandler = new TableMessageHandler(server);
        
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
//            ch.pipeline().addLast(handlersExecutor, "registerClientMessageHandler", registerClientMessageHandler);

//            ch.pipeline().addLast(handlersExecutor, "chatMessageHandler", chatMessageHandler);
            ch.pipeline().addLast(handlersExecutor, "serverMessageHandler", serverMessageHandler);
//            ch.pipeline().addLast(handlersExecutor, "roomMessageHandler", roomMessageHandler);
//            ch.pipeline().addLast(handlersExecutor, "tableMessageHandler", tableMessageHandler);
            
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

    
}
