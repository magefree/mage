
package mage.game;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import mage.cards.decks.DeckValidator;
import mage.collectors.DataCollectorServices;
import mage.constants.TableState;
import mage.game.draft.Draft;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.events.TableEventSource;
import mage.game.match.Match;
import mage.game.result.ResultProtos.TableProto;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.players.PlayerType;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class Table implements Serializable {

    private final static AtomicInteger GLOBAL_INDEX = new AtomicInteger();

    private UUID tableId;
    private Integer tableIndex; // for better logs and history
    private UUID roomId;
    private String name;
    private String controllerName;
    private String gameType;
    private Date createTime;
    private Seat[] seats;
    private int numSeats;
    private boolean isTournament;

    private boolean tournamentSubTable; // must assign by setTournamentSubTable only
    private UUID parentTableId = null; // original tourney table

    private DeckValidator validator;
    private TableState state;
    private Match match;
    private Tournament tournament;
    private TableRecorder recorder;
    private Set<String> bannedUsernames;
    private boolean isPlaneChase;

    @FunctionalInterface
    public interface TableRecorder {

        void record(Table table);
    }

    protected TableEventSource tableEventSource = new TableEventSource();

    public Table(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator, List<PlayerType> playerTypes, TableRecorder recorder, Tournament tournament, Set<String> bannedUsernames, boolean isPlaneChase) {
        this(roomId, gameType, name, controllerName, validator, playerTypes, recorder, bannedUsernames, isPlaneChase);
        this.tournament = tournament;
        this.isTournament = true;
        setState(TableState.WAITING);

        DataCollectorServices.getInstance().onTableStart(this);
    }

    public Table(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator, List<PlayerType> playerTypes, TableRecorder recorder, Match match, Set<String> bannedUsernames, boolean isPlaneChase) {
        this(roomId, gameType, name, controllerName, validator, playerTypes, recorder, bannedUsernames, isPlaneChase);
        this.match = match;
        this.match.setTableId(this.getId());
        this.isTournament = false;
        setState(TableState.WAITING);

        DataCollectorServices.getInstance().onTableStart(this);
    }

    protected Table(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator, List<PlayerType> playerTypes, TableRecorder recorder, Set<String> bannedUsernames, boolean isPlaneChase) {
        this.tableId = UUID.randomUUID();
        this.tableIndex = GLOBAL_INDEX.incrementAndGet();
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
        this.isPlaneChase = isPlaneChase;
    }

    private void createSeats(List<PlayerType> playerTypes) {
        int i = 0;
        seats = new Seat[numSeats];
        for (PlayerType playerType : playerTypes) {
            seats[i] = new Seat(playerType);
            i++;
        }
    }

    public UUID getId() {
        return tableId;
    }

    public Integer getTableIndex() {
        return tableIndex;
    }

    public UUID getParentTableId() {
        return parentTableId;
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

    public void initDraft(Draft draft) {
        setState(TableState.DRAFTING);
        tournament.setStepStartTime(new Date());
        draft.setTableId(this.getId());
    }

    public void construct() {
        setState(TableState.CONSTRUCTING);
        tournament.setStepStartTime(new Date());
    }

    /**
     * All activities of the table end (only replay of games (if active) and
     * display tournament results)
     */
    public void closeTable() {
        if (getState() != TableState.WAITING && getState() != TableState.READY_TO_START) {
            setState(TableState.FINISHED); // otherwise the table can be removed completely
        }
        this.validator = null;

        DataCollectorServices.getInstance().onTableEnd(this);
    }

    /**
     * Complete remove of the table, release all objects
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

    public Seat getNextAvailableSeat(PlayerType playerType) {
        for (int i = 0; i < numSeats; i++) {
            if (seats[i].getPlayer() == null && seats[i].getPlayerType() == (playerType)) {
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

    public void setTournamentSubTable(UUID parentTableId) {
        this.tournamentSubTable = true;
        this.parentTableId = parentTableId;
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
                .setStartTimeMs(this.getStartTime() != null ? this.getStartTime().getTime() : 0L)
                .setEndTimeMs(this.getEndTime() != null ? this.getEndTime().getTime() : 0L)
                .build();
    }
}
