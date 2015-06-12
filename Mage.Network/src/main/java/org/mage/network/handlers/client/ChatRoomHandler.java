package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.mage.network.model.ChatRoomIdMessage;
import org.mage.network.model.ChatRoomIdRequest;
import org.mage.network.model.JoinChatRequest;
import org.mage.network.model.LeaveChatRequest;

/**
 *
 * @author BetaSteward
 */
public class ChatRoomHandler extends SimpleChannelInboundHandler<ChatRoomIdMessage> {
    
    private ChannelHandlerContext ctx;
    private final BlockingQueue<UUID> queue = new LinkedBlockingQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, ChatRoomIdMessage msg) {
        queue.offer(msg.getId());
    }
        
    public UUID getChatRoomId(UUID roomId) throws Exception {
        queue.clear();
        ctx.writeAndFlush(new ChatRoomIdRequest(roomId));
        return queue.take();
    }

    public void joinChat(UUID chatId) {
        ctx.writeAndFlush(new JoinChatRequest(chatId));
    }
    
    public void leaveChat(UUID chatId) {
        ctx.writeAndFlush(new LeaveChatRequest(chatId));
    }

}
