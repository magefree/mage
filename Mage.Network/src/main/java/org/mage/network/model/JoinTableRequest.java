package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;

/**
 *
 * @author BetaSteward
 */
public class JoinTableRequest implements Serializable {
    
    private UUID roomId;
    private UUID tableId;
    private String name;
    private String playerType;
    private int skill;
    private DeckCardLists deckList;
    private String password;
    
    public JoinTableRequest(UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) {
        this.roomId = roomId;
        this.tableId = tableId;
        this.name = name;
        this.playerType = playerType;
        this.skill = skill;
        this.deckList = deckList;
        this.password = password;
    }
    
    public UUID getRoomId() {
        return roomId;
    }

    public UUID gettableId() {
        return tableId;
    }
    
    public String getName() {
        return name;
    }

    public String getPlayerType() {
        return playerType;
    }
    
    public int getSkill() {
        return skill;
    }
    
    public DeckCardLists getDeckCardLists() {
        return deckList;
    }

    public String getPassword() {
        return password;
    }
    
}
