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
public class CastBRGCommanderTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Flying
        // When you cast Prossh, Skyraider of Kher, put X 0/1 red Kobold creature tokens named Kobolds of Kher Keep onto the battlefield, where X is the amount of mana spent to cast Prossh.
        // Sacrifice another creature: Prossh gets +1/+0 until end of turn.
        setDecknamePlayerA("Power Hungry.dck"); // Commander = Prosssh, Skyrider of Kher {3}{B}{R}{G}
        setDecknamePlayerB("CommanderDuel_UW.dck"); //  Daxos of Meletis {1}{W}{U}
        return super.createNewGameAndPlayers();
    }

    @Test
    public void castCommanderWithFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        // The next creature card you cast this turn can be cast as though it had flash.
        // That spell can't be countered. That creature enters the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Savage Summoning");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Savage Summoning");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Prossh, Skyraider of Kher"); // 5/5
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Savage Summoning", 1);
        assertPermanentCount(playerA, "Prossh, Skyraider of Kher", 1);
        assertPowerToughness(playerA, "Prossh, Skyraider of Kher", 6, 6); // +1/+1 by Savage Summoning
        assertPermanentCount(playerA, "Kobolds of Kher Keep", 6);

    }

    /**
     * Activating Karn Liberated 's ultimate in an edh game (human OR ai) causes
     * all the command zones to lose their generals upon the new game restart
     */
    @Test
    public void castCommanderAfterKarnUltimate() {
        // +4: Target player exiles a card from his or her hand.
        // -3: Exile target permanent.
        // -14: Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated. Then put those cards onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Karn Liberated", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+4: Target player", playerA);
        addTarget(playerA, "Silvercoat Lion");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+4: Target player", playerA);
        addTarget(playerA, "Silvercoat Lion");
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "-14: Restart");

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Karn Liberated", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertCommandZoneCount(playerA, "Prossh, Skyraider of Kher", 1);
        assertCommandZoneCount(playerB, "Daxos of Meletis", 1);

    }

    /**
     * Mogg infestation creates tokens "for each creature that died this way".
     * When a commander is moved to a command zone, it doesn't "die", and thus
     * should not create tokens.
     */
    @Test
    public void castMoggInfestation() {
        // Destroy all creatures target player controls. For each creature that died this way, create two 1/1 red Goblin creature tokens under that player's control.
        addCard(Zone.HAND, playerA, "Mogg Infestation", 1); // Sorcery {3}{R}{R}

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Daxos of Meletis");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mogg Infestation");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Mogg Infestation", 1);
        assertCommandZoneCount(playerB, "Daxos of Meletis", 1);

        assertPermanentCount(playerB, "Goblin", 0);

    }

}
