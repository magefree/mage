/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.game.draft;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.game.draft.DraftCube.CardIdentity;

/**
 *
 * @author spjspj
 */
public class RichManCubeBoosterDraft extends DraftImpl {

    protected int[] richManTimes = {75, 70, 65, 60, 55, 50, 45, 40, 35, 35, 35, 35, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
    protected final Map<String, CardIdentity> cardsInCube = new LinkedHashMap<>();

    public RichManCubeBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
    }

    @Override
    public void start() {
        cardNum = 0;
        while (!isAbort() && cardNum < 36) {
            openBooster();
            cardNum = 0;
            while (!isAbort() && pickCards()) {
                passLeft();
                fireUpdatePlayersEvent();
            }
        }
        resetBufferedCards();
        this.fireEndDraftEvent();
    }

    @Override
    protected void passLeft() {
        synchronized (players) {
            UUID startId = table.get(0);
            UUID currentId = startId;
            UUID nextId = table.getNext();
            DraftPlayer next = players.get(nextId);
            draftCube.leftCubeCards.clear();
            draftCube.leftCubeCards.addAll(draftCube.getCubeCards());
            cardsInCube.clear();
            for (CardIdentity card : draftCube.leftCubeCards) {
                cardsInCube.put(card.getName(), card);
            }

            while (true) {
                for (DraftPlayer player : players.values()) {
                    if (player != null && player.getDeck() != null) {
                        for (Card card : player.getDeck().getSideboard()) {
                            if (cardsInCube.get(card.getName()) != null) {
                                draftCube.removeFromLeftCards(cardsInCube.get(card.getName()));
                            }
                        }
                    }
                }

                List<Card> nextBooster = draftCube.createBooster();
                next.setBooster(nextBooster);
                if (nextId == startId) {
                    break;
                }
                nextId = table.getNext();
                next = players.get(nextId);
            }
        }
    }

    @Override
    protected boolean pickCards() {
        cardNum++;
        for (DraftPlayer player : players.values()) {
            if (cardNum > 36) {
                return false;
            }
            player.setPicking();
            player.getPlayer().pickCard(player.getBooster(), player.getDeck(), this);
        }
        synchronized (this) {
            while (!donePicking()) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                }
            }
        }
        return true;
    }

    @Override
    public void firePickCardEvent(UUID playerId) {
        DraftPlayer player = players.get(playerId);
        if (cardNum > 36) {
            cardNum = 36;
        }
        if (cardNum <= 0) {
            cardNum = 1;
        }
        int time = richManTimes[cardNum - 1] * timing.getFactor();
        playerQueryEventSource.pickCard(playerId, "Pick card", player.getBooster(), time);
    }
}
