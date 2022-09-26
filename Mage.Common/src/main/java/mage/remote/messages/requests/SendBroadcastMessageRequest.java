package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.interfaces.MageServer;

public class SendBroadcastMessageRequest extends ServerRequest {
    private final String message;
    
    public SendBroadcastMessageRequest(String message){
        this.message = message;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.sendBroadcastMessage(getSessionId(ctx), message);
    }
}
