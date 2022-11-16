/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.GetExpansionInfoResponse;

/**
 *
 * @author LevelX2
 */
public class GetExpansionInfoRequest extends ServerRequest {

    List<String> setCodes;

    public GetExpansionInfoRequest(List<String> setCodes) {
        this.setCodes = setCodes;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new GetExpansionInfoResponse(server.getMissingExpansionData(setCodes))).addListener(WriteListener.getInstance());
    }

}
