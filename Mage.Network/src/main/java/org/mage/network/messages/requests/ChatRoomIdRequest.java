package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.ChatRoomIdMessage;

/**
 *
 * @author BetaSteward
 */
public class ChatRoomIdRequest extends ServerRequest {
    
    private UUID roomId;
    
    public ChatRoomIdRequest(UUID roomId) {
        this.roomId = roomId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new ChatRoomIdMessage(server.getRoomChatId(roomId))).addListener(WriteListener.getInstance());
    }

}
