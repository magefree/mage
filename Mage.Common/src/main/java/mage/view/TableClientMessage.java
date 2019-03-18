

package mage.view;

import java.io.Serializable;
import java.util.UUID;

import mage.cards.decks.Deck;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableClientMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private DeckView deck;
    private UUID roomId;
    private UUID tableId;
    private UUID gameId;
    private UUID playerId;
    private int time;
    private boolean flag = false;

    public TableClientMessage(Deck deck, UUID tableId, int time) {
        this.deck = new DeckView(deck);
        this.tableId = tableId;
        this.time = time;
    }

    public TableClientMessage(Deck deck, UUID tableId, int time, boolean flag) {
        this.deck = new DeckView(deck);
        this.tableId = tableId;
        this.time = time;
        this.flag = flag;
    }

    public TableClientMessage(UUID gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public TableClientMessage(UUID roomId, UUID tableId, boolean flag) {
        this.roomId = roomId;
        this.tableId = tableId;
        this.flag = flag;
    }

    public DeckView getDeck() {
        return deck;
    }

    public UUID getTableId() {
        return tableId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getTime() {
        return time;
    }

    public boolean getFlag() {
        return flag;
    }

    public void cleanUp() {

    }
}
