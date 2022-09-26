package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.players.PlayerType;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.BooleanResponse;

/**
 *
 * @author BetaSteward
 */
public class JoinTableRequest extends ServerRequest {
    
    private UUID roomId;
    private UUID tableId;
    private String name;
    private PlayerType playerType;
    private int skill;
    private DeckCardLists deckList;
    private String password;
    
    public JoinTableRequest(UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) {
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
        ctx.writeAndFlush(new BooleanResponse(server.joinTable(getSessionId(ctx), roomId, tableId, name, playerType, skill, deckList, password))).addListener(WriteListener.getInstance());
    }
    
}
