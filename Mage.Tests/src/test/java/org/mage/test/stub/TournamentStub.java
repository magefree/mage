
package org.mage.test.stub;

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
import mage.game.result.ResultProtos;
import mage.game.tournament.*;
import mage.players.Player;
import mage.players.PlayerType;

/**
 *
 * @author Quercitron
 */
public class TournamentStub implements Tournament {

    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void addPlayer(Player player, PlayerType playerType) {

    }

    @Override
    public void removePlayer(UUID playerId) {

    }

    @Override
    public TournamentPlayer getPlayer(UUID playerId) {
        return null;
    }

    @Override
    public ResultProtos.TourneyProto toProto() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<TournamentPlayer> getPlayers() {
        return null;
    }

    @Override
    public Collection<Round> getRounds() {
        return null;
    }

    @Override
    public List<ExpansionSet> getSets() {
        return null;
    }

    @Override
    public void updateResults() {

    }

    @Override
    public void setBoosterInfo(String setInfo) {

    }

    @Override
    public String getBoosterInfo() {
        return null;
    }

    @Override
    public void submitDeck(UUID playerId, Deck deck) {

    }

    @Override
    public boolean updateDeck(UUID playerId, Deck deck) {
        return true;
    }

    @Override
    public void autoSubmit(UUID playerId, Deck deck) {

    }

    @Override
    public boolean allJoined() {
        return false;
    }

    @Override
    public boolean isDoneConstructing() {
        return false;
    }

    @Override
    public void quit(UUID playerId) {

    }

    @Override
    public void leave(UUID playerId) {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void addTableEventListener(Listener<TableEvent> listener) {

    }

    @Override
    public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {

    }

    @Override
    public void fireConstructEvent(UUID playerId) {

    }

    @Override
    public TournamentOptions getOptions() {
        return null;
    }

    @Override
    public void setStartTime() {

    }

    @Override
    public Date getStartTime() {
        return null;
    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public Date getStepStartTime() {
        return null;
    }

    @Override
    public void setStepStartTime(Date date) {

    }

    @Override
    public TournamentType getTournamentType() {
        return null;
    }

    @Override
    public void setTournamentType(TournamentType tournamentType) {

    }

    @Override
    public String getTournamentState() {
        return null;
    }

    @Override
    public void setTournamentState(String tournamentState) {

    }

    @Override
    public int getNumberRounds() {
        return 0;
    }

    @Override
    public void cleanUpOnTournamentEnd() {

    }

    @Override
    public boolean isAbort() {
        return false;
    }

    @Override
    public void setAbort(boolean abort) {

    }

    @Override
    public void clearDraft() {

    }

    @Override
    public Draft getDraft() {
        return null;
    }
}
