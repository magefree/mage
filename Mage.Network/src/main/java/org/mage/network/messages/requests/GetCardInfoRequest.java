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
import org.mage.network.messages.responses.GetCardInfoResponse;

/**
 *
 * @author LevelX2
 */
public class GetCardInfoRequest extends ServerRequest {

    List<String> cards;

    public GetCardInfoRequest(List<String> cards) {
        this.cards = cards;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new GetCardInfoResponse(server.getMissingCardData(cards))).addListener(WriteListener.getInstance());
    }

}
