package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.interfaces.MageServer;

public class ToggleActivationRequest extends ServerRequest {
    private final String userName;

    public ToggleActivationRequest(String userName) {
        this.userName = userName;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.toggleActivation(getSessionId(ctx), userName);
    }
    
}
