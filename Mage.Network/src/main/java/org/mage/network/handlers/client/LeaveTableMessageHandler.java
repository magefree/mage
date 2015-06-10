package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.cards.decks.DeckCardLists;
import org.mage.network.model.JoinTableMessage;
import org.mage.network.model.JoinTableRequest;
import org.mage.network.model.LeaveTableRequest;
import org.mage.network.model.LeftTableMessage;


/**
 *
 * @author BetaSteward
 */
public class LeaveTableMessageHandler extends SimpleChannelInboundHandler<LeftTableMessage> {

    private ChannelHandlerContext ctx;
    private final BlockingQueue<Boolean> queue = new LinkedBlockingQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LeftTableMessage msg) throws Exception {
        queue.offer(msg.getSuccess());
    }
    
    public boolean leaveTable(UUID roomId, UUID tableId) throws Exception {
        queue.clear();
        ctx.writeAndFlush(new LeaveTableRequest(roomId, tableId));
        return queue.take();
    }
    
}
