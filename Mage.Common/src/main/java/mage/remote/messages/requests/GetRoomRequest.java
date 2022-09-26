package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.GetRoomResponse;

/**
 *
 * @author BetaSteward
 */
public class GetRoomRequest extends ServerRequest {
    
    private UUID roomId;
    
    public GetRoomRequest(UUID roomId) {
        this.roomId = roomId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new GetRoomResponse(server.getRoom(roomId))).addListener(WriteListener.getInstance());
    }
    
}
