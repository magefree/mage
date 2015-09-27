/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.GetExpansionInfoResponse;

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
