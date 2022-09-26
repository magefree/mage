package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.interfaces.MageServer;

public class MuteUserRequest extends ServerRequest {
    private final String userName;
    private final long durationMinutes;

    public MuteUserRequest(String userName, long durationMinutes) {
        this.userName = userName;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.muteUser(getSessionId(ctx), userName, durationMinutes);
    }
}
