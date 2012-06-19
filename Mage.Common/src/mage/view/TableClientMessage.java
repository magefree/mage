/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

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
