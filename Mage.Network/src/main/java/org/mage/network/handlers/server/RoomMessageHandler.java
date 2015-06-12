package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ChatRoomIdMessage;
import org.mage.network.model.ChatRoomIdRequest;
import org.mage.network.model.CreateTableMessage;
import org.mage.network.model.CreateTableRequest;
import org.mage.network.model.GetRoomRequest;
import org.mage.network.model.RoomMessage;
import org.mage.network.model.RoomRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class RoomMessageHandler extends SimpleChannelInboundHandler<RoomRequest> {
            
    private final MageServer server;
    
    public RoomMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, RoomRequest msg) {
        if (msg instanceof CreateTableRequest) {
            CreateTableRequest r = (CreateTableRequest)msg;
            ctx.writeAndFlush(new CreateTableMessage(server.createTable(ctx.channel().id().asLongText(), r.getRoomId(), r.getMatchOptions())));        
        }
        else if (msg instanceof ChatRoomIdRequest) {
            ctx.writeAndFlush(new ChatRoomIdMessage(server.getRoomChatId(msg.getRoomId())));
        }
        else if (msg instanceof GetRoomRequest) {
            ctx.writeAndFlush(new RoomMessage(server.getRoom(msg.getRoomId())));
        }
    }

}
