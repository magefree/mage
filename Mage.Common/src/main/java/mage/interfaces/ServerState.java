

package mage.interfaces;

import mage.players.PlayerType;
import mage.utils.MageVersion;
import mage.view.GameTypeView;
import mage.view.TournamentTypeView;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ServerState implements Serializable {

    private final List<GameTypeView> gameTypes;
    private final List<TournamentTypeView> tournamentTypes;
    private final PlayerType[] playerTypes;
    private final String[] deckTypes;
    private final String[] draftCubes;
    private final boolean testMode;
    private final MageVersion version;
    private final long cardsContentVersion;
    private final long expansionsContentVersion;

    public ServerState(List<GameTypeView> gameTypes, List<TournamentTypeView> tournamentTypes,
                       PlayerType[] playerTypes, String[] deckTypes, String[] draftCubes, boolean testMode,
                       MageVersion version, long cardsContentVersion, long expansionsContentVersion) {
        this.gameTypes = gameTypes;
        this.tournamentTypes = tournamentTypes;
        this.playerTypes = playerTypes;
        this.deckTypes = deckTypes;
        this.draftCubes = draftCubes;
        this.testMode = testMode;
        this.version = version;
        this.cardsContentVersion = cardsContentVersion;
        this.expansionsContentVersion = expansionsContentVersion;

    }

    public List<GameTypeView> getGameTypes() {
        return gameTypes;
    }

    public List<GameTypeView> getTournamentGameTypes() {
        return gameTypes.stream()
                .filter(gameTypeView -> gameTypeView.getMinPlayers() == 2 && gameTypeView.getMaxPlayers() == 2)
                .collect(Collectors.toList());
    }

    public List<TournamentTypeView> getTournamentTypes() {
        return tournamentTypes;
    }

    public PlayerType[] getPlayerTypes() {
        return playerTypes;
    }

    public String[] getDeckTypes() {
        return deckTypes;
    }

    public String[] getDraftCubes() {
        return draftCubes;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public MageVersion getVersion() {
        return version;
    }

    public long getCardsContentVersion() {
        return cardsContentVersion;
    }

    public long getExpansionsContentVersion() {
        return expansionsContentVersion;
    }

}
