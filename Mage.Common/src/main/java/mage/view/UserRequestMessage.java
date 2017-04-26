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
import mage.constants.PlayerAction;

/**
 *
 * @author LevelX2
 */
public class UserRequestMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String titel;
    private final String message;
    private UUID relatedUserId;
    private String relatedUserName;
    private UUID matchId;
    private UUID tournamentId;
    private UUID gameId;
    private UUID roomId;
    private UUID tableId;

    private String button1Text;
    private PlayerAction button1Action;

    private String button2Text;
    private PlayerAction button2Action;

    private String button3Text;
    private PlayerAction button3Action;

    public UserRequestMessage(String titel, String message) {
        this.titel = titel;
        this.message = message;
        this.button1Action = null;
        this.button2Action = null;
        this.button3Action = null;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public void setRelatedUser(UUID userId, String name) {
        this.relatedUserId = userId;
        this.relatedUserName = name;
    }

    public void setButton1(String text, PlayerAction buttonAction) {
        this.button1Text = text;
        this.button1Action = buttonAction;
    }

    public void setButton2(String text, PlayerAction buttonAction) {
        this.button2Text = text;
        this.button2Action = buttonAction;
    }

    public void setButton3(String text, PlayerAction buttonAction) {
        this.button3Text = text;
        this.button3Action = buttonAction;
    }

    public String getTitel() {
        return titel;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage() {
        return message;
    }

    public UUID getRelatedUserId() {
        return relatedUserId;
    }

    public String getRelatedUserName() {
        return relatedUserName;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(UUID tournamentId) {
        this.tournamentId = tournamentId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public String getButton1Text() {
        return button1Text;
    }

    public PlayerAction getButton1Action() {
        return button1Action;
    }

    public String getButton2Text() {
        return button2Text;
    }

    public PlayerAction getButton2Action() {
        return button2Action;
    }

    public String getButton3Text() {
        return button3Text;
    }

    public PlayerAction getButton3Action() {
        return button3Action;
    }

}
