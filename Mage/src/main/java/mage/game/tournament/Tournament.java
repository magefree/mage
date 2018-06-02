
package mage.game.tournament;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.game.draft.Draft;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.result.ResultProtos.TourneyProto;
import mage.players.Player;
import mage.players.PlayerType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Tournament {

    UUID getId();

    void addPlayer(Player player, PlayerType playerType);

    void removePlayer(UUID playerId);

    TournamentPlayer getPlayer(UUID playerId);

    Collection<TournamentPlayer> getPlayers();

    Collection<Round> getRounds();

    List<ExpansionSet> getSets();

    void updateResults();

    void setBoosterInfo(String setInfo);

    /**
     * Gives back a String that shows the included sets (e.g. "3xRTR" or "1xDGM
     * 1xGTC 1xRTR") or cube name
     *
     * @return String
     */
    String getBoosterInfo();

    void submitDeck(UUID playerId, Deck deck);

    boolean updateDeck(UUID playerId, Deck deck);

    void autoSubmit(UUID playerId, Deck deck);

    boolean allJoined();

    boolean isDoneConstructing();

    void quit(UUID playerId);

    void leave(UUID playerId);

    void nextStep();

    void addTableEventListener(Listener<TableEvent> listener);

    void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener);

    void fireConstructEvent(UUID playerId);

    TournamentOptions getOptions();

    // tournament times
    void setStartTime();

    Date getStartTime();

    Date getEndTime();

    Date getStepStartTime();

    void setStepStartTime(Date date);

    // tournament type
    TournamentType getTournamentType();

    void setTournamentType(TournamentType tournamentType);

    // tournamentState
    String getTournamentState();

    void setTournamentState(String tournamentState);

    int getNumberRounds();

    void cleanUpOnTournamentEnd();

    boolean isAbort();

    void setAbort(boolean abort);

    void clearDraft();

    Draft getDraft();

    TourneyProto toProto();
}
