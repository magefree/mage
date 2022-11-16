package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class SwapSeatRequest extends ServerRequest {
    
    private UUID roomId;
    private UUID tableId;
    private int seatNum1;
    private int seatNum2;
    
    public SwapSeatRequest(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
        this.roomId = roomId;
        this.tableId = tableId;
        this.seatNum1 = seatNum1;
        this.seatNum2 = seatNum2;
    }
            
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.swapSeats(getSessionId(ctx), roomId, tableId, seatNum1, seatNum2);
    }

}
