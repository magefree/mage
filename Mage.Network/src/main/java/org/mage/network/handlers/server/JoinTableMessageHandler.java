package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.JoinTableMessage;
import org.mage.network.model.JoinTableRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class JoinTableMessageHandler extends SimpleChannelInboundHandler<JoinTableRequest> {
            
    private final MageServer server;
    
    public JoinTableMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, JoinTableRequest msg) {
        ctx.writeAndFlush(new JoinTableMessage(server.joinTable(ctx.channel().id().asLongText(), msg.getRoomId(), msg.getTableId(), msg.getName(), msg.getPlayerType(), msg.getSkill(), msg.getDeckCardLists(), msg.getPassword())));        
    }

}
