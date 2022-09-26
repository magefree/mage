package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.GetUsersResponse;

public class GetUsersRequest extends ServerRequest {
    
    public GetUsersRequest() {
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new GetUsersResponse(server.getUsers(getSessionId(ctx)))).addListener(WriteListener.getInstance());
    }
}
