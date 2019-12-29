package mage.view;

import mage.constants.SkillLevel;
import mage.constants.TableState;
import mage.game.Game;
import mage.game.Seat;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.match.MatchPlayer;
import mage.game.tournament.TournamentPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TableView implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID tableId;
    private String gameType;
    private final String deckType;
    private String tableName;
    private String controllerName;
    private final String additionalInfo;
    private Date createTime;
    private TableState tableState;
    private final SkillLevel skillLevel;
    private final String tableStateText;
    private final String seatsInfo;
    private boolean isTournament;
    private List<SeatView> seats = new ArrayList<>();
    private List<UUID> games = new ArrayList<>();
    private final String quitRatio;
    private final String minimumRating;
    private final boolean limited;
    private final boolean rated;
    private final boolean passworded;
    private final boolean spectatorsAllowed;

    public TableView(Table table) {
        this.tableId = table.getId();
        this.gameType = table.getGameType();
        this.tableName = table.getName();
        this.controllerName = table.getControllerName();
        this.tableState = table.getState();
        if (table.getState() == TableState.WAITING
                || table.getState() == TableState.READY_TO_START
                || table.getState() == TableState.STARTING) {
            this.createTime = table.getCreateTime();
        } else {
            if (table.isTournament()) {
                this.createTime = table.getTournament().getStartTime();
            } else {
                this.createTime = table.getMatch().getStartTime();
            }
        }
        this.isTournament = table.isTournament();
        for (Seat seat : table.getSeats()) {
            seats.add(new SeatView(seat));
        }
        if (!table.isTournament()) {
            // MATCH
            seatsInfo = "" + table.getMatch().getPlayers().size() + '/' + table.getSeats().length;
            if (table.getState() == TableState.WAITING || table.getState() == TableState.READY_TO_START) {
                tableStateText = table.getState().toString() + " (" + seatsInfo + ')';
            } else {
                tableStateText = table.getState().toString();
            }
            for (Game game : table.getMatch().getGames()) {
                games.add(game.getId());
            }
            StringBuilder sb = new StringBuilder();
            StringBuilder sbScore = new StringBuilder();
            for (MatchPlayer matchPlayer : table.getMatch().getPlayers()) {
                if (matchPlayer.getPlayer() == null) {
                    sb.append(", ").append("[unknown]");
                    sbScore.append('-').append(matchPlayer.getWins());
                } else if (!matchPlayer.getName().equals(table.getControllerName())) {
                    sb.append(", ").append(matchPlayer.getName());
                    sbScore.append('-').append(matchPlayer.getWins());
                } else {
                    sbScore.insert(0, matchPlayer.getWins()).insert(0, " Score: ");
                }
            }
            if (table.getMatch().getDraws() > 0) {
                sbScore.append(" Draws: ").append(table.getMatch().getDraws());
            }
            this.controllerName += sb.toString();
            this.deckType = table.getDeckType();
            StringBuilder addInfo = new StringBuilder();
            if (table.getMatch().getGames().isEmpty()) {
                addInfo.append("Wins:").append(table.getMatch().getWinsNeeded());
                addInfo.append(" Time: ").append(table.getMatch().getOptions().getMatchTimeLimit().toString());
                if (table.getMatch().getFreeMulligans() > 0) {
                    addInfo.append(" Free Mul.: ").append(table.getMatch().getFreeMulligans());
                }
            } else {
                addInfo.append("Wins:").append(table.getMatch().getWinsNeeded());
                addInfo.append(sbScore.toString());
            }
            if (table.getNumberOfSeats() > 3) {
                addInfo.append(" Rng: ").append(table.getMatch().getOptions().getRange().toString());
            }
            this.additionalInfo = addInfo.toString();
            this.skillLevel = table.getMatch().getOptions().getSkillLevel();
            this.quitRatio = Integer.toString(table.getMatch().getOptions().getQuitRatio());
            this.minimumRating = Integer.toString(table.getMatch().getOptions().getMinimumRating());
            this.limited = table.getMatch().getOptions().isLimited();
            this.rated = table.getMatch().getOptions().isRated();
            this.passworded = !table.getMatch().getOptions().getPassword().isEmpty();
            this.spectatorsAllowed = table.getMatch().getOptions().isSpectatorsAllowed();
        } else {
            // TOURNAMENT
            if (table.getTournament().getOptions().getNumberRounds() > 0) {
                this.gameType = new StringBuilder(this.gameType).append(' ').append(table.getTournament().getOptions().getNumberRounds()).append(" Rounds").toString();
            }
            StringBuilder sb1 = new StringBuilder();
            for (TournamentPlayer tp : table.getTournament().getPlayers()) {
                if (!tp.getPlayer().getName().equals(table.getControllerName())) {
                    sb1.append(", ").append(tp.getPlayer().getName());
                }
            }
            this.controllerName += sb1.toString();
            this.seatsInfo = "" + table.getTournament().getPlayers().size() + "/" + table.getNumberOfSeats();
            StringBuilder infoText = new StringBuilder();
            StringBuilder stateText = new StringBuilder(table.getState().toString());
            infoText.append("Wins:").append(table.getTournament().getOptions().getMatchOptions().getWinsNeeded());
            infoText.append(" Seats: ").append(this.seatsInfo);
            switch (table.getState()) {
                case WAITING:
                    stateText.append(" (").append(table.getTournament().getPlayers().size()).append('/').append(table.getNumberOfSeats()).append(')');
                    break;
                case READY_TO_START:
                case STARTING:
                    infoText.append(" Time: ").append(table.getTournament().getOptions().getMatchOptions().getMatchTimeLimit().toString());
                    if (table.getTournament().getOptions().getMatchOptions().getFreeMulligans() > 0) {
                        infoText.append(" Fr.Mul: ").append(table.getTournament().getOptions().getMatchOptions().getFreeMulligans());
                    }
                    if (table.getTournament().getTournamentType().isLimited()) {
                        infoText.append(" Constr.: ").append(table.getTournament().getOptions().getLimitedOptions().getConstructionTime() / 60).append(" Min.");
                    }
                    break;
                case DUELING:
                    stateText.append(" Round: ").append(table.getTournament().getRounds().size());
                    break;
                case DRAFTING:
                    Draft draft = table.getTournament().getDraft();
                    if (draft != null) {
                        stateText.append(' ').append(draft.getBoosterNum()).append('/').append(draft.getCardNum() - 1);
                    }
                    break;
                default:
            }
            this.additionalInfo = infoText.toString();
            this.tableStateText = stateText.toString();
            this.deckType = table.getDeckType() + ' ' + table.getTournament().getBoosterInfo();
            this.skillLevel = table.getTournament().getOptions().getMatchOptions().getSkillLevel();
            this.quitRatio = Integer.toString(table.getTournament().getOptions().getQuitRatio());
            this.minimumRating = Integer.toString(table.getTournament().getOptions().getMinimumRating());
            this.limited = table.getTournament().getOptions().getMatchOptions().isLimited();
            this.rated = table.getTournament().getOptions().getMatchOptions().isRated();
            this.passworded = !table.getTournament().getOptions().getPassword().isEmpty();
            this.spectatorsAllowed = table.getTournament().getOptions().isWatchingAllowed();
        }
    }

    public UUID getTableId() {
        return tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public boolean getSpectatorsAllowed() {
        return spectatorsAllowed;
    }


    public String getGameType() {
        return gameType;
    }

    public String getDeckType() {
        return deckType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TableState getTableState() {
        return tableState;
    }

    public List<SeatView> getSeats() {
        return seats;
    }

    public List<UUID> getGames() {
        return games;
    }

    public String getSeatsInfo() {
        return seatsInfo;
    }

    public boolean isTournament() {
        return this.isTournament;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public String getTableStateText() {
        return tableStateText;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public String getQuitRatio() {
        return quitRatio;
    }

    public String getMinimumRating() {
        return minimumRating;
    }

    public boolean isLimited() {
        return limited;
    }

    public boolean isRated() {
        return rated;
    }

    public boolean isPassworded() {
        return passworded;
    }
}
