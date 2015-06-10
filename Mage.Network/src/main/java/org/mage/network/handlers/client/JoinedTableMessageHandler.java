package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.JoinedTableMessage;


/**
 *
 * @author BetaSteward
 */
public class JoinedTableMessageHandler extends SimpleChannelInboundHandler<JoinedTableMessage> {


    private final MageClient client;
    
    public JoinedTableMessageHandler (MageClient client) {
        this.client = client;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, JoinedTableMessage msg) throws Exception {
        client.joinedTable(msg.getRoomId(), msg.getTableId(), msg.getChatId(), msg.isOwner(), msg.isTournament());
    }
    
    
}
