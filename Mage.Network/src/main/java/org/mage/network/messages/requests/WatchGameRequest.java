//package org.mage.network.messages.requests;
//
//import io.netty.channel.ChannelHandlerContext;
//import java.util.UUID;
//import org.mage.network.handlers.WriteListener;
//import org.mage.network.interfaces.MageServer;
//import org.mage.network.messages.responses.UUIDResponse;
//
///**
// *
// * @author BetaSteward
// */
//public class WatchGameRequest extends ServerRequest {
//    private final UUID gameId;
//
//    public WatchGameRequest(UUID gameId) {
//        this.gameId = gameId;
//    }
//
//    @Override
//    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
//        ctx.writeAndFlush(new UUIDResponse(server.watchGame(gameId, getSessionId(ctx)))).addListener(WriteListener.getInstance());
//    }
//    
//}
