
package mage.game;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class GameInfo {

    
    private final UUID matchId;
    private final UUID gameId;
    private final String state;
    private final String result;
    private final String players;
    private int roundNum;
    private UUID tableId;

    public GameInfo(int roundNum, UUID matchId, UUID gameId, String state, String result, String players, UUID tableId) {
        this.roundNum = roundNum;
        this.matchId = matchId;
        this.gameId = gameId;
        this.state = state;
        this.result = result;
        this.players = players;
        this.tableId = tableId;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getState() {
        return state;
    }

    public String getResult() {
        return result;
    }

    public String getPlayers() {
        return players;
    }

    public UUID getTableId() {
        return tableId;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }
    
}
