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
        return super.createNewGameAndPlayers();
    }

    @Test
    public void castCommanderWithFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addCard(Zone.HAND, playerA, "Savage Summoning");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Savage Summoning");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Prossh, Skyraider of Kher");
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Savage Summoning", 1);
        assertPermanentCount(playerA, "Prossh, Skyraider of Kher", 1);
        assertPermanentCount(playerA, "Kobolds of Kher Keep", 6);

    }

}
