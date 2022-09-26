package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.interfaces.MageServer;

public class SetActivationRequest extends ServerRequest {
    private final String userName;
    private final boolean active;

    public SetActivationRequest(String userName, boolean active) {
        this.userName = userName;
        this.active = active;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.setActivation(getSessionId(ctx), userName, active);
    }
    
}
