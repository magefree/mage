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
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.UUID;
import mage.view.ChatMessage;
import org.apache.log4j.Logger;
import org.mage.network.handlers.HeartbeatHandler;
import org.mage.network.handlers.PingMessageHandler;
import org.mage.network.handlers.server.ChatMessageHandler;
import org.mage.network.handlers.server.ChatRoomIdHandler;
import org.mage.network.handlers.server.ConnectionHandler;
import org.mage.network.handlers.server.JoinChatMessageHandler;
import org.mage.network.handlers.server.LeaveChatMessageHandler;
import org.mage.network.handlers.server.RegisterClientMessageHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.InformClientMessage;
import org.mage.network.model.MessageType;
import org.mage.network.model.ReceiveChatMessage;

/**
 *
 * @author BetaSteward
 */
public class Server {
    
    private static final Logger logger = Logger.getLogger(Server.class);

    private static final int IDLE_PING_TIME = 30;
    private static final int IDLE_TIMEOUT = 60;
    
    public static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final HeartbeatHandler heartbeatHandler = new HeartbeatHandler();
    private final PingMessageHandler pingMessageHandler = new PingMessageHandler();
    private final EventExecutorGroup handlersExecutor = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() * 2);
    private final RegisterClientMessageHandler registerClientMessageHandler;

    private final ChatRoomIdHandler chatRoomIdHandler;
    private final ChatMessageHandler chatMessageHandler;
    private final JoinChatMessageHandler joinChatMessageHandler;
    private final LeaveChatMessageHandler leaveChatMessageHandler;
    
    public Server(MageServer server) {
        registerClientMessageHandler = new RegisterClientMessageHandler(server);
        chatMessageHandler = new ChatMessageHandler(server);
        joinChatMessageHandler = new JoinChatMessageHandler(server);
        leaveChatMessageHandler = new LeaveChatMessageHandler(server);
        chatRoomIdHandler = new ChatRoomIdHandler(server);
    }
    
    public void start(int port) {
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
            
            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
            ch.pipeline().addLast(new ObjectEncoder());

            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(IDLE_TIMEOUT, IDLE_PING_TIME, 0));
            ch.pipeline().addLast("heartbeatHandler", heartbeatHandler);
            ch.pipeline().addLast("pingMessageHandler", pingMessageHandler);

            ch.pipeline().addLast("connectionHandler", new ConnectionHandler());
            ch.pipeline().addLast(handlersExecutor, registerClientMessageHandler);

            ch.pipeline().addLast(handlersExecutor, chatRoomIdHandler);
            ch.pipeline().addLast(handlersExecutor, chatMessageHandler);
            ch.pipeline().addLast(handlersExecutor, joinChatMessageHandler);
            ch.pipeline().addLast(handlersExecutor, leaveChatMessageHandler);
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
            ch.writeAndFlush(new ReceiveChatMessage(chatId, message));
    }

    public void informClient(String sessionId, String message, MessageType type) {
        Channel ch = findChannel(sessionId);
        if (ch != null)
            ch.writeAndFlush(new InformClientMessage(message, type));
    }

    public void informClients(String message, MessageType type) {
        clients.writeAndFlush(new InformClientMessage(message, type));
    }

}
