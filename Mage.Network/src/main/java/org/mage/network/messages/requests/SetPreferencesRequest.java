package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.players.net.UserData;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class SetPreferencesRequest extends ServerRequest {

    private final UserData userData;

    public SetPreferencesRequest(UserData userData) {
        this.userData = userData;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.setPreferences(getSessionId(ctx), userData);
    }

}
