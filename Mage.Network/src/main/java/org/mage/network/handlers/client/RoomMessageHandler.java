package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.view.RoomView;
import org.mage.network.model.RoomMessage;
import org.mage.network.model.RoomRequest;


/**
 *
 * @author BetaSteward
 */
public class RoomMessageHandler extends SimpleChannelInboundHandler<RoomMessage> {

    private ChannelHandlerContext ctx;
    private final BlockingQueue<RoomView> queue = new LinkedBlockingQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, RoomMessage msg) throws Exception {
        queue.offer(msg.getRoom());
    }
    
    public RoomView getRoom(UUID roomId) throws Exception {
        ctx.writeAndFlush(new RoomRequest(roomId));
        return queue.take();
    }
    
}
