/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.UUIDResponse;

/**
 *
 * @author dev
 */
public class GetMainRoomIdRequest extends ServerRequest {
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new UUIDResponse(server.getMainRoomId(getSessionId(ctx)))).addListener(WriteListener.getInstance());
    }
    
}
