
package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.interfaces.MageServer;


public class DisconnectUserRequest extends ServerRequest {
    private final String string;
    
    public DisconnectUserRequest(String string){
        this.string = string;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.disconnectUser(getSessionId(ctx), string);
    }
    
}
