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
package org.mage.test.commander.duel;

import java.io.FileNotFoundException;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */
public class KaradorGhostChieftainTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        // During each of your turns, you may cast one creature card from your graveyard.
        setDecknamePlayerA("CommanderDuel_Karador_BGW.dck"); // Commander = Karador, Ghost Chieftain {5}{B}{G}{W}
        setDecknamePlayerB("CMDNorinTheWary.dck"); // Need red

        return super.createNewGameAndPlayers();
    }

    @Test
    public void castKarador() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTappedCount("Island", false, 2);
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);
    }

    @Test
    public void castKaradorTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // Lightning Blast deals 4 damage to target creature or player.
        addCard(Zone.HAND, playerB, "Lightning Blast", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Blast", "Karador, Ghost Chieftain");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        assertGraveyardCount(playerB, "Lightning Blast", 1);
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);

        assertTappedCount("Island", true, 5);
    }

    @Test
    public void castKaradorFourTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 11);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // Lightning Blast deals 4 damage to target creature or player.
        addCard(Zone.HAND, playerB, "Lightning Blast", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Blast", "Karador, Ghost Chieftain");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Blast", "Karador, Ghost Chieftain");

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        castSpell(6, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Blast", "Karador, Ghost Chieftain");

        castSpell(7, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();
        assertGraveyardCount(playerB, "Lightning Blast", 3);
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);

        assertTappedCount("Island", true, 9);
    }

    @Test
    public void castKaradorFourTimes15Reduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 11);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 15);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // Lightning Blast deals 4 damage to target creature or player.
        addCard(Zone.HAND, playerB, "Lightning Blast", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Blast", "Karador, Ghost Chieftain");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Blast", "Karador, Ghost Chieftain");

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        castSpell(6, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Blast", "Karador, Ghost Chieftain");

        castSpell(7, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();
        assertGraveyardCount(playerB, "Lightning Blast", 3);
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);

        assertTappedCount("Island", true, 0);
    }
}
