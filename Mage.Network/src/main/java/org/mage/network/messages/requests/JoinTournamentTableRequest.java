package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.BooleanResponse;

/**
 *
 * @author BetaSteward
 */
public class JoinTournamentTableRequest extends ServerRequest {
    private final UUID roomId;
    private final UUID tableId;
    private final String name;
    private final String playerType;
    private final int skill;
    private final DeckCardLists deckList;
    private final String password;

    public JoinTournamentTableRequest(UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) {
        this.roomId = roomId;
        this.tableId = tableId;
        this.name = name;
        this.playerType = playerType;
        this.skill = skill;
        this.deckList = deckList;
        this.password = password;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new BooleanResponse(server.joinTournamentTable(ctx.channel().id().asLongText(), roomId, tableId, name, playerType, skill, deckList, password))).addListener(WriteListener.getInstance());
    }
    
}
