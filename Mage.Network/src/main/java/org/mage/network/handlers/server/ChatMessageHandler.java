package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ChatMessageRequest;
import org.mage.network.model.ChatRequest;
import org.mage.network.model.ChatRoomIdMessage;
import org.mage.network.model.ChatRoomIdRequest;
import org.mage.network.model.JoinChatRequest;
import org.mage.network.model.LeaveChatRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ChatMessageHandler extends SimpleChannelInboundHandler<ChatRequest> {

    private final MageServer server;
    
    public ChatMessageHandler(MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, ChatRequest msg) {
        if (msg instanceof JoinChatRequest) {
            server.joinChat(msg.getChatId(), ctx.channel().id().asLongText());
        }
        if (msg instanceof ChatMessageRequest) {
            ChatMessageRequest r = (ChatMessageRequest)msg;
            server.receiveChatMessage(r.getChatId(), ctx.channel().id().asLongText(), r.getMessage());
        }
        if (msg instanceof LeaveChatRequest) {
            server.leaveChat(msg.getChatId(), ctx.channel().id().asLongText());
        }
    }
    
}
