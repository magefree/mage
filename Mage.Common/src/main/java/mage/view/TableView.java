package mage.view;

import mage.constants.SkillLevel;
import mage.constants.TableState;
import mage.game.Game;
import mage.game.Seat;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.draft.DraftOptions;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.match.MatchPlayer;
import mage.game.mulligan.MulliganType;
import mage.game.tournament.Tournament;
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
                this.createTime = table.getTournament().get().getStartTime();
            } else {
                this.createTime = table.getMatch().get().getStartTime();
            }
        }
        this.isTournament = table.isTournament();
        for (Seat seat : table.getSeats()) {
            seats.add(new SeatView(seat));
        }
        if (!table.isTournament()) {
            // MATCH
            Match match = table.getMatch().get();
            seatsInfo = "" + match.getPlayers().size() + '/' + table.getSeats().length;
            if (table.getState() == TableState.WAITING || table.getState() == TableState.READY_TO_START) {
                tableStateText = table.getState().toString() + " (" + seatsInfo + ')';
            } else {
                tableStateText = table.getState().toString();
            }
            for (Game game : match.getGames()) {
                games.add(game.getId());
            }
            StringBuilder sb = new StringBuilder();
            StringBuilder sbScore = new StringBuilder();
            for (MatchPlayer matchPlayer : match.getPlayers()) {
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
            if (match.getDraws() > 0) {
                sbScore.append(" Draws: ").append(match.getDraws());
            }
            this.controllerName += sb.toString();
            this.deckType = table.getDeckType();
            StringBuilder addInfo = new StringBuilder();
            if (match.getGames().isEmpty()) {
                addInfo.append("Wins:").append(match.getWinsNeeded());
                addInfo.append(" Time: ").append(match.getOptions().getMatchTimeLimit().toString());
                addInfo.append(" Buffer: ").append(match.getOptions().getMatchBufferTime().toString());
                if (match.getOptions().getMulliganType() != MulliganType.GAME_DEFAULT) {
                    addInfo.append(" Mulligan: \"").append(match.getOptions().getMulliganType().toString()).append("\"");
                }
                if (match.getFreeMulligans() > 0) {
                    addInfo.append(" FM: ").append(match.getFreeMulligans());
                }
                if (match.getOptions().isCustomStartLifeEnabled()) {
                    addInfo.append(" StartLife: ").append(match.getOptions().getCustomStartLife());
                }
                if (match.getOptions().isCustomStartHandSizeEnabled()) {
                    addInfo.append(" StartHandSize: ").append(match.getOptions().getCustomStartHandSize());
                }
            } else {
                addInfo.append("Wins:").append(match.getWinsNeeded());
                addInfo.append(sbScore.toString());
            }
            if (match.getOptions().isRollbackTurnsAllowed()) {
                addInfo.append(" RB");
            }
            if (match.getOptions().isPlaneChase()) {
                addInfo.append(" PC");
            }
            if (!(match.getOptions().getPerPlayerEmblemCards().isEmpty())
                    || !(match.getOptions().getGlobalEmblemCards().isEmpty())) {
                addInfo.append(" EC");
            }
            if (match.getOptions().isSpectatorsAllowed()) {
                addInfo.append(" SP");
            }
            if (table.getNumberOfSeats() > 3) {
                addInfo.append(" Rng: ").append(match.getOptions().getRange().toString());
            }
            this.additionalInfo = addInfo.toString();
            this.skillLevel = match.getOptions().getSkillLevel();
            this.quitRatio = Integer.toString(match.getOptions().getQuitRatio());
            this.minimumRating = Integer.toString(match.getOptions().getMinimumRating());
            this.limited = match.getOptions().isLimited();
            this.rated = match.getOptions().isRated();
            this.passworded = !match.getOptions().getPassword().isEmpty();
            this.spectatorsAllowed = match.getOptions().isSpectatorsAllowed();
        } else {
            // TOURNAMENT
            Tournament tournament = table.getTournament().get();
            if (tournament.getOptions().getNumberRounds() > 0) {
                this.gameType = new StringBuilder(this.gameType).append(' ').append(tournament.getOptions().getNumberRounds()).append(" Rounds").toString();
            }
            StringBuilder sb1 = new StringBuilder();
            for (TournamentPlayer tp : tournament.getPlayers()) {
                if (!tp.getPlayer().getName().equals(table.getControllerName())) {
                    sb1.append(", ").append(tp.getPlayer().getName());
                }
            }
            this.controllerName += sb1.toString();
            this.seatsInfo = "" + tournament.getPlayers().size() + "/" + table.getNumberOfSeats();
            StringBuilder infoText = new StringBuilder();
            StringBuilder stateText = new StringBuilder(table.getState().toString());
            infoText.append("Wins:").append(tournament.getOptions().getMatchOptions().getWinsNeeded());
            infoText.append(" Seats: ").append(this.seatsInfo);
            switch (table.getState()) {
                case WAITING:
                case READY_TO_START:
                case STARTING:
                    if (TableState.WAITING.equals(table.getState())) {
                        stateText.append(" (").append(tournament.getPlayers().size()).append('/').append(table.getNumberOfSeats()).append(')');
                    }
                    MatchOptions tourneyMatchOptions = tournament.getOptions().getMatchOptions();
                    infoText.append(" Time: ").append(tourneyMatchOptions.getMatchTimeLimit().toString());
                    infoText.append(" Buffer: ").append(tourneyMatchOptions.getMatchBufferTime().toString());
                    if (tourneyMatchOptions.getMulliganType() != MulliganType.GAME_DEFAULT) {
                        infoText.append(" Mulligan: \"").append(tourneyMatchOptions.getMulliganType().toString()).append("\"");
                    }
                    if (tourneyMatchOptions.getFreeMulligans() > 0) {
                        infoText.append(" FM: ").append(tourneyMatchOptions.getFreeMulligans());
                    }
                    if (tournament.getOptions().getMatchOptions().isCustomStartLifeEnabled()) {
                        infoText.append(" StartLife: ").append(tournament.getOptions().getMatchOptions().getCustomStartLife());
                    }
                    if (tournament.getOptions().getMatchOptions().isCustomStartHandSizeEnabled()) {
                        infoText.append(" StartHandSize: ").append(tournament.getOptions().getMatchOptions().getCustomStartHandSize());
                    }
                    if (tournament.getTournamentType().isLimited()) {
                        infoText.append(" Constr.: ").append(tournament.getOptions().getLimitedOptions().getConstructionTime() / 60).append(" Min.");
                    }
                    if (tournament.getOptions().getLimitedOptions() instanceof DraftOptions) {
                        DraftOptions draftOptions = (DraftOptions) tournament.getOptions().getLimitedOptions();
                        infoText.append(" Pick time: ").append(draftOptions.getTiming().getShortName());
                    }
                    if (tourneyMatchOptions.isRollbackTurnsAllowed()) {
                        infoText.append(" RB");
                    }
                    if (tourneyMatchOptions.isPlaneChase()) {
                        infoText.append(" PC");
                    }
                    if (!(tournament.getOptions().getMatchOptions().getPerPlayerEmblemCards().isEmpty())
                            || !(tournament.getOptions().getMatchOptions().getGlobalEmblemCards().isEmpty())) {
                        infoText.append(" EC");
                    }
                    if (tournament.getOptions().isWatchingAllowed()) {
                        infoText.append(" SP");
                    }

                    break;
                case DUELING:
                    stateText.append(" Round: ").append(tournament.getRounds().size());
                    break;
                case DRAFTING:
                    Draft draft = tournament.getDraft();
                    if (draft != null) {
                        stateText.append(' ').append(draft.getBoosterNum()).append('/').append(draft.getCardNum());
                    }
                    break;
                default:
            }
            this.additionalInfo = infoText.toString();
            this.tableStateText = stateText.toString();
            this.deckType = table.getDeckType() + ' ' + tournament.getBoosterInfo();
            this.skillLevel = tournament.getOptions().getMatchOptions().getSkillLevel();
            this.quitRatio = Integer.toString(tournament.getOptions().getQuitRatio());
            this.minimumRating = Integer.toString(tournament.getOptions().getMinimumRating());
            this.limited = tournament.getOptions().getMatchOptions().isLimited();
            this.rated = tournament.getOptions().getMatchOptions().isRated();
            this.passworded = !tournament.getOptions().getPassword().isEmpty();
            this.spectatorsAllowed = tournament.getOptions().isWatchingAllowed();
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
