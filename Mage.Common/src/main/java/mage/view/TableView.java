package mage.view;

import mage.cards.decks.DeckCardInfo;
import mage.constants.MatchBufferTime;
import mage.constants.SkillLevel;
import mage.constants.TableState;
import mage.game.Game;
import mage.game.Seat;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.draft.DraftOptions;
import mage.game.match.MatchOptions;
import mage.game.match.MatchPlayer;
import mage.game.mulligan.MulliganType;
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
    private final String additionalInfoShort;
    private final String additionalInfoFull;
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
            StringBuilder infoTextShort = new StringBuilder();
            StringBuilder infoTextLong = new StringBuilder();
            if (table.getMatch().getGames().isEmpty()) {
                infoTextShort.append("Wins: ").append(table.getMatch().getWinsNeeded());
                infoTextLong.append("Wins required: ").append(table.getMatch().getWinsNeeded())
                        .append(" (Best of ").append(table.getMatch().getWinsNeeded()*2-1).append(")");
                buildMatchOptionsTextShared(table.getMatch().getOptions(),infoTextShort,infoTextLong);
            } else {
                infoTextShort.append("Wins: ").append(table.getMatch().getWinsNeeded()).append(sbScore);
                infoTextLong.append("Wins required: ").append(table.getMatch().getWinsNeeded()).append(sbScore);
            }
            infoTextLong.append("<br>Seats: ").append(this.seatsInfo);
            if (table.getMatch().getOptions().isSpectatorsAllowed()) {
                infoTextShort.append(", SP");
                infoTextLong.append("<br>Spectators allowed (SP)");
            }
            infoTextLong.append("<br>Game type: ").append(table.getGameType());
            infoTextLong.append("<br>Deck type: ").append(table.getDeckType());
            if (table.getNumberOfSeats() > 3) {
                infoTextShort.append(", Rng: ").append(table.getMatch().getOptions().getRange().toString());
                infoTextLong.append("<br>Range of Influence: ").append(table.getMatch().getOptions().getRange().toString());
            }
            this.additionalInfoShort = infoTextShort.toString();
            this.additionalInfoFull = infoTextLong.toString();
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
                this.gameType = this.gameType + ' ' + table.getTournament().getOptions().getNumberRounds() + " Rounds";
            }
            StringBuilder sb1 = new StringBuilder();
            for (TournamentPlayer tp : table.getTournament().getPlayers()) {
                if (!tp.getPlayer().getName().equals(table.getControllerName())) {
                    sb1.append(", ").append(tp.getPlayer().getName());
                }
            }
            this.controllerName += sb1.toString();
            this.seatsInfo = "" + table.getTournament().getPlayers().size() + "/" + table.getNumberOfSeats();
            StringBuilder infoTextShort = new StringBuilder();
            StringBuilder infoTextLong = new StringBuilder();
            StringBuilder stateText = new StringBuilder(table.getState().toString());
            infoTextShort.append("Wins: ").append(table.getTournament().getOptions().getMatchOptions().getWinsNeeded());
            infoTextLong.append("Wins required: ").append(table.getTournament().getOptions().getMatchOptions().getWinsNeeded())
                    .append(" (Best of ").append(table.getTournament().getOptions().getMatchOptions().getWinsNeeded()*2-1).append(")");
            infoTextLong.append("<br>Seats: ").append(this.seatsInfo);
            switch (table.getState()) {
                case WAITING:
                case READY_TO_START:
                case STARTING:
                    if (TableState.WAITING.equals(table.getState())) {
                        stateText.append(" (").append(table.getTournament().getPlayers().size()).append('/').append(table.getNumberOfSeats()).append(')');
                    }
                    buildMatchOptionsTextShared(table.getTournament().getOptions().getMatchOptions(), infoTextShort, infoTextLong);
                    if (table.getTournament().getTournamentType().isLimited()) {
                        infoTextShort.append(", Constr.: ").append(table.getTournament().getOptions().getLimitedOptions().getConstructionTime() / 60).append("m");
                        infoTextLong.append("<br>Construction time: ").append(table.getTournament().getOptions().getLimitedOptions().getConstructionTime() / 60).append(" Minutes");
                    }
                    if (table.getTournament().getOptions().getLimitedOptions() instanceof DraftOptions) {
                        DraftOptions draftOptions = (DraftOptions) table.getTournament().getOptions().getLimitedOptions();
                        infoTextShort.append(", Pick time: ").append(draftOptions.getTiming().getShortName());
                        infoTextLong.append("<br>Pick time: ").append(draftOptions.getTiming().getName());
                    }
                    if (table.getTournament().getOptions().isWatchingAllowed()) {
                        infoTextShort.append(", SP");
                        infoTextLong.append("<br>Spectators allowed (SP)");
                    }
                    infoTextLong.append("<br>Game type: ").append(table.getGameType());
                    infoTextLong.append("<br>Deck type: ").append(table.getDeckType());
                    if (!table.getTournament().getBoosterInfo().isEmpty()){
                        infoTextLong.append("<br>Boosters: ").append(table.getTournament().getBoosterInfo());
                    }
                    break;
                case DUELING:
                    stateText.append(" Round: ").append(table.getTournament().getRounds().size());
                    break;
                case DRAFTING:
                    Draft draft = table.getTournament().getDraft();
                    if (draft != null) {
                        stateText.append(' ').append(draft.getBoosterNum()).append('/').append(draft.getCardNum());
                    }
                    break;
                default:
            }
            this.additionalInfoShort = infoTextShort.toString();
            this.additionalInfoFull = infoTextLong.toString();
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

    private void buildMatchOptionsTextShared(MatchOptions options, StringBuilder shortBuilder, StringBuilder longBuilder){
        longBuilder.append("<br>Time: ").append(options.getMatchTimeLimit().toString());
        shortBuilder.append(", Time: ").append(options.getMatchTimeLimit().getShortName());
        if (options.getMatchBufferTime() != MatchBufferTime.NONE){
            shortBuilder.append("(+").append(options.getMatchBufferTime().getShortName()).append(")");
            longBuilder.append("<br>Buffer time: ").append(options.getMatchBufferTime().toString());
        }
        int customOptions = 0;
        if (options.getMulliganType() != MulliganType.GAME_DEFAULT) {
            longBuilder.append("<br>Mulligan: \"").append(options.getMulliganType().toString()).append("\"");
            customOptions += 1;
        }
        if (options.getFreeMulligans() > 0) {
            longBuilder.append("<br>Free Mulligans: ").append(options.getFreeMulligans());
            customOptions += 1;
        }
        if (options.isCustomStartLifeEnabled()) {
            longBuilder.append("<br>Starting Life: ").append(options.getCustomStartLife());
            customOptions += 1;
        }
        if (options.isCustomStartHandSizeEnabled()) {
            longBuilder.append("<br>Starting Hand Size: ").append(options.getCustomStartHandSize());
            customOptions += 1;
        }
        if (options.isPlaneChase()) {
            longBuilder.append("<br>Planechase");
            customOptions += 1;
        }
        if (!(options.getPerPlayerEmblemCards().isEmpty())
                || !(options.getGlobalEmblemCards().isEmpty())) {
            longBuilder.append("<br>Emblem cards:");
            for(DeckCardInfo card: options.getPerPlayerEmblemCards()){
                longBuilder.append("<br>* <b>").append(card.getCardName()).append("</b> (per player)");
            }
            for(DeckCardInfo card: options.getGlobalEmblemCards()){
                longBuilder.append("<br>* <b>").append(card.getCardName()).append("</b> (global)");
            }
            customOptions += 1;
        }
        if (customOptions > 0){
            shortBuilder.append(", Custom options (").append(customOptions).append(")");
        }
        if (options.isRollbackTurnsAllowed()) {
            shortBuilder.append(", RB");
            longBuilder.append("<br>Rollbacks allowed (RB)");
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

    public String getAdditionalInfoShort() {
        return this.additionalInfoShort;
    }

    public String getAdditionalInfoFull() {
        return this.additionalInfoFull;
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
