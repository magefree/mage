package com.xmage.core.entity.model;


/**
 * Class representing XMage server stats.
 *
 * @author noxx
 */
public class ServerStats implements EntityModel {

    private int numberOfGamesPlayed;

    private int numberOfUniquePlayers;

    private String top3Players;

    private int numberOfPlayersPlayedOnce;

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public int getNumberOfUniquePlayers() {
        return numberOfUniquePlayers;
    }

    public void setNumberOfUniquePlayers(int numberOfUniquePlayers) {
        this.numberOfUniquePlayers = numberOfUniquePlayers;
    }

    public int getNumberOfPlayersPlayedOnce() {
        return numberOfPlayersPlayedOnce;
    }

    public void setNumberOfPlayersPlayedOnce(int numberOfPlayersPlayedOnce) {
        this.numberOfPlayersPlayedOnce = numberOfPlayersPlayedOnce;
    }

    public String getTop3Players() {
        return top3Players;
    }

    public void setTop3Players(String top3Players) {
        this.top3Players = top3Players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerStats stats = (ServerStats) o;

        if (numberOfGamesPlayed != stats.numberOfGamesPlayed) return false;
        if (numberOfUniquePlayers != stats.numberOfUniquePlayers) return false;
        if (numberOfPlayersPlayedOnce != stats.numberOfPlayersPlayedOnce) return false;
        if (top3Players != null ? !top3Players.equals(stats.top3Players) : stats.top3Players != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfGamesPlayed;
        result = 31 * result + numberOfUniquePlayers;
        result = 31 * result + numberOfPlayersPlayedOnce;
        result = 31 * result + (top3Players != null ? top3Players.hashCode() : 0);

        return result;
    }


}
