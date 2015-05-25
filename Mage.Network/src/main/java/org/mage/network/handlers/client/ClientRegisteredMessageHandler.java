package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.interfaces.ServerState;
import mage.utils.MageVersion;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.ClientRegisteredMessage;
import org.mage.network.model.RegisterClientMessage;

/**
 *
 * @author BetaSteward
 */
public class ClientRegisteredMessageHandler extends SimpleChannelInboundHandler<ClientRegisteredMessage> {

    private final MageClient client;    
//    private ChannelHandlerContext ctx;
    private final BlockingQueue<ServerState> queue = new LinkedBlockingQueue<>();
    private String userName;
    private MageVersion version;

    public ClientRegisteredMessageHandler (MageClient client) {
        this.client = client;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        this.ctx = ctx;
        ctx.writeAndFlush(new RegisterClientMessage(userName, version));
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ClientRegisteredMessage msg) throws Exception {
        queue.offer(msg.getServerState());
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setVersion(MageVersion version) {
        this.version = version;
    }
    
    public void registerClient() throws InterruptedException {
        client.clientRegistered(queue.take());
    }
    
}
