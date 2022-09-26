/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author dev
 */
public class LockUserRequest extends ServerRequest {
    private final String userName;
    private final long durationMinutes;

    public LockUserRequest(String userName, long durationMinutes) {
        this.userName = userName;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.lockUser(getSessionId(ctx), userName, durationMinutes);
    }
    
}
