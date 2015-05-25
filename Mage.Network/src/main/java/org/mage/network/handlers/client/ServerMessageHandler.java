package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.mage.network.model.ServerMessagesMessage;
import org.mage.network.model.ServerMessagesRequest;


/**
 *
 * @author BetaSteward
 */
public class ServerMessageHandler extends SimpleChannelInboundHandler<ServerMessagesMessage> {

    private ChannelHandlerContext ctx;
    private final BlockingQueue<List<String>> queue = new LinkedBlockingQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ServerMessagesMessage msg) throws Exception {
        queue.offer(msg.getMessages());
    }
    
    public List<String> getServerMessages() throws Exception {
        ctx.writeAndFlush(new ServerMessagesRequest());
        return queue.take();
    }
    
}
