package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.interfaces.MageServer;

public class EndUserSessionRequest extends ServerRequest{
    private final String string;
    
    public EndUserSessionRequest(String string){
        this.string = string;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.endUserSession(getSessionId(ctx), string);
    }
}
