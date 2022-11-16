package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.utils.MageVersion;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.UserConnectedResponse;

public class ConnectUserRequest extends ServerRequest {
    
    private final Connection connection;
    private final MageVersion version;
    
    public ConnectUserRequest(Connection connection, MageVersion version) {
        this.connection = connection;
        this.version = version;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        String host = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
        String message = server.connectUser(connection, getSessionId(ctx), version, host);
        if (message==null) {
            ctx.writeAndFlush(new UserConnectedResponse(server.getServerState(),null)).addListener(WriteListener.getInstance());
        }
        else {
            ctx.writeAndFlush(new UserConnectedResponse(null,message)).addListener(WriteListener.getInstance());
            server.disconnect(getSessionId(ctx), DisconnectReason.ValidationError);
        }
    }
    
}
