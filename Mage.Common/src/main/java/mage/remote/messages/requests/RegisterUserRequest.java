package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.BooleanResponse;
import mage.remote.messages.responses.UserRegisteredResponse;
import mage.utils.MageVersion;

/**
 *
 * @author BetaSteward
 */
public class RegisterUserRequest extends ServerRequest {
    private final Connection connection;
    private final MageVersion version;
    
    public RegisterUserRequest(Connection connection, MageVersion version) {
        this.connection = connection;
        this.version = version;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        String host = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
        String result = server.registerUser(connection, getSessionId(ctx), version, host);
        if (result == null) {
            ctx.writeAndFlush(new UserRegisteredResponse(null)).addListener(WriteListener.getInstance());
            server.disconnect(getSessionId(ctx), DisconnectReason.CleaningUp);
        }
        else {
            ctx.writeAndFlush(new UserRegisteredResponse(result)).addListener(WriteListener.getInstance());
            server.disconnect(getSessionId(ctx), DisconnectReason.ValidationError);
        }
    }
    
}
