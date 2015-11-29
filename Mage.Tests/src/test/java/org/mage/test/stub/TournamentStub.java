/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package org.mage.test.stub;

import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.game.draft.Draft;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.tournament.*;
import mage.players.Player;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public void addPlayer(Player player, String playerType) {

    }

    @Override
    public void removePlayer(UUID playerId) {

    }

    @Override
    public TournamentPlayer getPlayer(UUID playerId) {
        return null;
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
    public void updateDeck(UUID playerId, Deck deck) {

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
