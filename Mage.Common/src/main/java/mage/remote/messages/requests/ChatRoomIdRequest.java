package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.UUIDResponse;

/**
 *
 * @author BetaSteward
 */
public class ChatRoomIdRequest extends ServerRequest {

    private final UUID roomId;

    public ChatRoomIdRequest(UUID roomId) {
        this.roomId = roomId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new UUIDResponse(server.getRoomChatId(getSessionId(ctx), roomId))).addListener(WriteListener.getInstance());
    }

}
