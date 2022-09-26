/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.game.tournament.TournamentOptions;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.GetTablesResponse;
import mage.remote.messages.responses.TableViewResponse;

/**
 *
 * @author dev
 */
public class GetTablesRequest extends ServerRequest {
    private final UUID roomId;

    public GetTablesRequest(UUID roomId) {
        this.roomId = roomId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new GetTablesResponse(server.getTables(getSessionId(ctx), roomId))).addListener(WriteListener.getInstance());
    }
    
}
