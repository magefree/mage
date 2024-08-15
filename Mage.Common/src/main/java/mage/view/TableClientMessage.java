

package mage.view;

import java.io.Serializable;
import java.util.UUID;

import mage.cards.decks.Deck;

/**
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class TableClientMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private DeckView deck;
    private UUID roomId;
    private UUID currentTableId;
    private UUID parentTableId; // tourney uses sub-tables for matches
    private UUID gameId;
    private UUID playerId;
    private int time;
    private boolean flag = false;

    public TableClientMessage() {
    }

    public TableClientMessage withDeck(Deck deck) {
        this.deck = new DeckView(deck);
        return this;
    }

    public TableClientMessage withRoom(UUID roomId) {
        this.roomId = roomId;
        return this;
    }

    public TableClientMessage withTime(int time) {
        this.time = time;
        return this;
    }

    public TableClientMessage withFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public TableClientMessage withPlayer(UUID playerId) {
        this.playerId = playerId;
        return this;
    }

    public TableClientMessage withTable(UUID currentTableId, UUID parentTableId) {
        this.currentTableId = currentTableId;
        this.parentTableId = parentTableId;
        return this;
    }

    public TableClientMessage withGame(UUID gameId) {
        this.gameId = gameId;
        return this;
    }

    public DeckView getDeck() {
        return deck;
    }

    public UUID getCurrentTableId() {
        return currentTableId;
    }

    public UUID getParentTableId() {
        return parentTableId;
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
