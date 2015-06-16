package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;

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
        ctx.writeAndFlush(new RoomMessage(server.getRoom(roomId))).addListener(WriteListener.getInstance());
    }
    
}
