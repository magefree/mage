package org.mage.network.handlers.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.interfaces.ServerState;
import mage.remote.Connection;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;
import org.mage.network.messages.responses.ClientRegisteredResponse;
import org.mage.network.messages.requests.RegisterClientRequest;

/**
 *
 * @author BetaSteward
 */
public class ClientRegisteredMessageHandler extends SimpleChannelInboundHandler<ClientRegisteredResponse> {

    private static final Logger logger = Logger.getLogger(ClientRegisteredMessageHandler.class);
    
    private final BlockingQueue<ServerState> queue = new LinkedBlockingQueue<>();
    private Connection connection;
    private MageVersion version;
        
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new RegisterClientRequest(connection, version)).addListener(new ListenerImpl());
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ClientRegisteredResponse msg) throws Exception {
        queue.offer(msg.getServerState());
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void setVersion(MageVersion version) {
        this.version = version;
    }
    
    public ServerState registerClient() throws InterruptedException {
        return queue.take();
    }
    
    private final class ListenerImpl implements ChannelFutureListener {
        
        private final ServerState POISON_PILL = new ServerState();

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
                logger.error("Communication error", future.cause());
                queue.offer(POISON_PILL);
            }
        }
    }
}
