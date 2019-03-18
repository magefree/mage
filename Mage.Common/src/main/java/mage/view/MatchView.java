
package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import mage.game.Game;
import mage.game.Table;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MatchView implements Serializable {

    private final UUID tableId;
    private UUID matchId;
    private String matchName;
    private String gameType;
    private String deckType;

    private final List<UUID> games = new ArrayList<>();
    private String result;
    private String players;
    
    private Date startTime;
    private Date endTime;
    private boolean replayAvailable;
    private final boolean isTournament;
    private boolean rated;

    public MatchView(Table table) {
        this.tableId = table.getId();
        this.isTournament = table.isTournament();
        if (table.isTournament()) {
            initTournamentTable(table);
        } else {
            initMatchTable(table);
        }
    }

    // used for matches
    private void initMatchTable(Table table) {
        Match match = table.getMatch();
        this.matchId = match.getId();
        this.matchName = match.getName();
        this.gameType = match.getOptions().getGameType();

        if (table.getName() != null && !table.getName().isEmpty()) {
            this.deckType = match.getOptions().getDeckType() + " [" +  table.getName() + ']';
        }  else {
            this.deckType = match.getOptions().getDeckType();
        }
        
        for (Game game: match.getGames()) {
            games.add(game.getId());
        }
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (MatchPlayer matchPlayer: match.getPlayers()) {
            sb1.append(matchPlayer.getName());
            if(matchPlayer.hasQuit()) {
                if (matchPlayer.getPlayer().hasTimerTimeout()) {
                    sb1.append(" [timer] ");
                } else if (matchPlayer.getPlayer().hasIdleTimeout()) {
                    sb1.append(" [idle] ");
                } else {
                    sb1.append(" [quit] ");
                }
            }
            int lostGames = match.getNumGames() - (matchPlayer.getWins() + match.getDraws());
            sb1.append(", ");
            sb2.append(matchPlayer.getName()).append(" [");
            sb2.append(matchPlayer.getWins()).append('-');
            if (match.getDraws() > 0) {
                sb2.append(match.getDraws()).append('-');
            }
            sb2.append(lostGames).append("], ");
        }
        if (sb1.length() > 2) {
            players = sb1.substring(0, sb1.length() - 2);
            result = sb2.substring(0, sb2.length() - 2);
        } else {
            players = "[no players]";
            result = "";
        }
        this.startTime = match.getStartTime();
        this.endTime = match.getEndTime();
        this.replayAvailable = match.isReplayAvailable();
        this.rated = match.getOptions().isRated();
    }

    // used for tournaments
    private void initTournamentTable(Table table) {
        this.matchId = table.getTournament().getId();
        this.matchName = table.getName();
        this.gameType = table.getGameType();
        if (table.getTournament().getOptions().getNumberRounds() > 0) {
            this.gameType = new StringBuilder(this.gameType).append(' ').append(table.getTournament().getOptions().getNumberRounds()).append(" Rounds").toString();
        }
        StringBuilder sbDeckType = new StringBuilder(table.getDeckType());
        if (!table.getTournament().getBoosterInfo().isEmpty()) {
            sbDeckType.append(' ').append(table.getTournament().getBoosterInfo());
        }
        if (table.getName() != null && !table.getName().isEmpty()) {
            sbDeckType.append(table.getDeckType()).append(" [").append(table.getName()).append(']');
        }
        this.deckType = sbDeckType.toString();
        StringBuilder sb1 = new StringBuilder();
        for (TournamentPlayer tPlayer : table.getTournament().getPlayers()) {
            sb1.append(tPlayer.getPlayer().getName()).append(" (").append(tPlayer.getPoints()).append(" P.) ");
        }
        this.players = sb1.toString();
        StringBuilder sb2 = new StringBuilder();
        if (!table.getTournament().getRounds().isEmpty()) {
            for (TournamentPlayer tPlayer : table.getTournament().getPlayers()) {
                sb2.append(tPlayer.getPlayer().getName()).append(": ").append(tPlayer.getResults()).append(' ');
            }
        } else {
          sb2.append("Canceled");
        }
        this.result = sb2.toString();
        this.startTime = table.getTournament().getStartTime();
        this.endTime = table.getTournament().getEndTime();
        this.replayAvailable = false;
        this.rated = table.getTournament().getOptions().getMatchOptions().isRated();
    }

    public UUID getMatchId() {
        return matchId;
    }

    public String getName() {
        return matchName;
    }

    public String getGameType() {
        return gameType;
    }

    public String getDeckType() {
        return deckType;
    }

    public List<UUID> getGames() {
        return games;
    }

    public String getResult() {
        return result;
    }

    public String getPlayers() {
        return players;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getMatchName() {
        return matchName;
    }

    public boolean isReplayAvailable() {
        return replayAvailable;
    }

    public boolean isTournament() {
        return isTournament;
    }

    public UUID getTableId() {
        return tableId;
    }

    public boolean isRated() {
        return rated;
    }
}
