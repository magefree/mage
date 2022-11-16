package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.Connection;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.BooleanResponse;

public class EmailAuthTokenRequest extends ServerRequest {
    
    final private Connection connection;
    
    public EmailAuthTokenRequest(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new BooleanResponse(server.emailAuthToken(connection))).addListener(WriteListener.getInstance());
    }
    
}
