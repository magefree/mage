/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.cards.decks.DeckValidator;
import mage.constants.TableState;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.events.TableEventSource;
import mage.game.match.Match;
import mage.game.result.ResultProtos.TableProto;
import mage.game.tournament.Tournament;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Table implements Serializable {

    private UUID tableId;
    private UUID roomId;
    private String name;
    private String controllerName;
    private String gameType;
    private Date createTime;
    private Seat[] seats;
    private int numSeats;
    private boolean isTournament;
    private boolean tournamentSubTable;
    private DeckValidator validator;
    private TableState state;
    private Match match;
    private Tournament tournament;
    private TableRecorder recorder;
    private Set<String> bannedUsernames;

    @FunctionalInterface
    public interface TableRecorder {
        void record(Table table);
    }

    protected TableEventSource tableEventSource = new TableEventSource();

    public Table(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator, List<String> playerTypes, TableRecorder recorder, Tournament tournament, Set<String> bannedUsernames) {
        this(roomId, gameType, name, controllerName, validator, playerTypes, recorder, bannedUsernames);
        this.tournament = tournament;
        this.isTournament = true;
        setState(TableState.WAITING);
    }

    public Table(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator, List<String> playerTypes, TableRecorder recorder, Match match, Set<String> bannedUsernames) {
        this(roomId, gameType, name, controllerName, validator, playerTypes, recorder, bannedUsernames);
        this.match = match;
        this.isTournament = false;
        setState(TableState.WAITING);
    }

    protected Table(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator, List<String> playerTypes, TableRecorder recorder, Set<String> bannedUsernames) {
        tableId = UUID.randomUUID();
        this.roomId = roomId;
        this.numSeats = playerTypes.size();
        this.gameType = gameType;
        this.name = name;
        this.controllerName = controllerName;
        this.createTime = new Date();
        createSeats(playerTypes);
        this.validator = validator;
        this.recorder = recorder;
        this.bannedUsernames = new HashSet<>(bannedUsernames);
    }

    private void createSeats(List<String> playerTypes) {
        int i = 0;
        seats = new Seat[numSeats];
        for (String playerType : playerTypes) {
            seats[i] = new Seat(playerType);
            i++;
        }
    }

    public UUID getId() {
        return tableId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void initGame() {
        setState(TableState.DUELING);
    }

    public void initTournament() {
        setState(TableState.DUELING);
        tournament.setStepStartTime(new Date());
    }

    public void endTournament() {
        setState(TableState.FINISHED);
    }

    public void initDraft() {
        setState(TableState.DRAFTING);
        tournament.setStepStartTime(new Date());
    }

    public void construct() {
        setState(TableState.CONSTRUCTING);
        tournament.setStepStartTime(new Date());
    }

    /**
     * All activities of the table end (only replay of games (if active) and
     * display tournament results)
     *
     */
    public void closeTable() {
        if (getState() != TableState.WAITING && getState() != TableState.READY_TO_START) {
            setState(TableState.FINISHED); // otherwise the table can be removed completely
        }
        this.validator = null;
    }

    /**
     * Complete remove of the table, release all objects
     *
     */
    public void cleanUp() {
        if (match != null) {
            match.cleanUpOnMatchEnd(false, false);
        }
    }

    public String getGameType() {
        return gameType;
    }

    public String getDeckType() {
        if (validator != null) {
            return validator.getName();
        }
        return "<deck type missing>";
    }

    public Date getCreateTime() {
        return new Date(createTime.getTime());
    }

    public boolean isTournament() {
        return this.isTournament;
    }

    public UUID joinTable(Player player, Seat seat) throws GameException {
        if (seat.getPlayer() != null) {
            throw new GameException("Seat is occupied.");
        }
        seat.setPlayer(player);
        if (isReady()) {
            setState(TableState.READY_TO_START);
        }
        return seat.getPlayer().getId();
    }

    private boolean isReady() {
        for (int i = 0; i < numSeats; i++) {
            if (seats[i].getPlayer() == null) {
                return false;
            }
        }
        return true;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public int getNumberOfSeats() {
        return numSeats;
    }

    public Seat getNextAvailableSeat(String playerType) {
        for (int i = 0; i < numSeats; i++) {
            if (seats[i].getPlayer() == null && seats[i].getPlayerType().equals(playerType)) {
                return seats[i];
            }
        }
        return null;
    }

    public boolean allSeatsAreOccupied() {
        for (int i = 0; i < numSeats; i++) {
            if (seats[i].getPlayer() == null) {
                return false;
            }
        }
        return true;
    }

    public void leaveNotStartedTable(UUID playerId) {
        for (int i = 0; i < numSeats; i++) {
            Player player = seats[i].getPlayer();
            if (player != null && player.getId().equals(playerId)) {
                seats[i].setPlayer(null);
                if (getState() == TableState.READY_TO_START) {
                    setState(TableState.WAITING);
                }
                break;
            }
        }
    }

    final public void setState(TableState state) {
        this.state = state;
        if (isTournament()) {
            getTournament().setTournamentState(state.toString());
        }
        if (state == TableState.FINISHED) {
            this.recorder.record(this);
        }
    }

    public TableState getState() {
        return state;
    }

    public DeckValidator getValidator() {
        return this.validator;
    }

    public void sideboard() {
        setState(TableState.SIDEBOARDING);
    }

    public String getName() {
        return this.name;
    }

    public void addTableEventListener(Listener<TableEvent> listener) {
        tableEventSource.addListener(listener);
    }

    public Match getMatch() {
        return match;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getControllerName() {
        return controllerName;
    }

    public boolean isTournamentSubTable() {
        return tournamentSubTable;
    }

    public void setTournamentSubTable(boolean tournamentSubTable) {
        this.tournamentSubTable = tournamentSubTable;
    }

    public Date getStartTime() {
        if (isTournament) {
            return tournament.getStartTime();
        } else {
            return match.getStartTime();
        }
    }

    public Date getEndTime() {
        if (isTournament) {
            return tournament.getEndTime();
        } else {
            return match.getEndTime();
        }
    }

    public boolean userIsBanned(String username) {
        return bannedUsernames.contains(username);
    }

    public TableProto toProto() {
        TableProto.Builder builder = TableProto.newBuilder();
        if (this.isTournament()) {
            builder.getTourneyBuilder().mergeFrom(this.getTournament().toProto());
        } else {
            builder.getMatchBuilder().mergeFrom(this.getMatch().toProto());
        }
        return builder.setGameType(this.getGameType())
                .setName(this.getName())
                .setGameType(this.getGameType())
                .setDeckType(this.getDeckType())
                .setControllerName(this.getControllerName())
                .setStartTimeMs(this.getStartTime().getTime())
                .setEndTimeMs(this.getEndTime().getTime())
                .build();
    }
}
