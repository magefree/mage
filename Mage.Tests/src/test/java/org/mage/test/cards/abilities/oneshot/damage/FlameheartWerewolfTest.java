/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author goblin
 */
public class FlameheartWerewolfTest extends CardTestPlayerBase {

    /**
     * https://github.com/magefree/mage/issues/2816
     */
    @Test
    public void testBlockingKalitas() {
        // this card is the second face of double-faced card

        // Flameheart Werewolf is a 3/2 with:
        // Whenever Flameheart Werewolf blocks or becomes blocked by a creature, Flameheart Werewolf deals 2 damage to that creature.

        // Kalitas, Traitor of Ghet is a 3/4 with:
        // Lifelink
        // If a nontoken creature an opponent controls would die, instead exile that card and put a 2/2 black Zombie creature token onto the battlefield

        addCard(Zone.BATTLEFIELD, playerA, "Flameheart Werewolf");
        addCard(Zone.BATTLEFIELD, playerB, "Kalitas, Traitor of Ghet");

        attack(2, playerB, "Kalitas, Traitor of Ghet");
        block(2, playerA, "Flameheart Werewolf", "Kalitas, Traitor of Ghet");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 23); // lifelinked

        // both should die
        assertPermanentCount(playerA, "Flameheart Werewolf", 0);
        assertExileCount("Flameheart Werewolf", 1); // exiled by Kalitas
        assertPermanentCount(playerB, "Kalitas, Traitor of Ghet", 0);
        assertGraveyardCount(playerB, "Kalitas, Traitor of Ghet", 1);
    }

    @Test
    public void testBlockedByTwo22s() {
        addCard(Zone.BATTLEFIELD, playerA, "Flameheart Werewolf");
        // Both 2/2 creatures should die before the combat starts
        addCard(Zone.BATTLEFIELD, playerB, "Falkenrath Reaver");
        addCard(Zone.BATTLEFIELD, playerB, "Wind Drake");

        attack(3, playerA, "Flameheart Werewolf");
        block(3, playerB, "Falkenrath Reaver", "Flameheart Werewolf");
        block(3, playerB, "Wind Drake", "Flameheart Werewolf");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Flameheart Werewolf was blocked, no trample
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // both 2/2s should die before they had a chance to deal damage
        // to Flameheart Werewolf
        assertPermanentCount(playerA, "Flameheart Werewolf", 1);
        assertGraveyardCount(playerA, "Flameheart Werewolf", 0);
        assertPermanentCount(playerB, "Falkenrath Reaver", 0);
        assertGraveyardCount(playerB, "Falkenrath Reaver", 1);
        assertPermanentCount(playerB, "Wind Drake", 0);
        assertGraveyardCount(playerB, "Wind Drake", 1);
    }

    @Test
    public void testKessigForgemaster() {
        addCard(Zone.BATTLEFIELD, playerA, "Kessig Forgemaster");
        // Both 1/1 creatures should die before the combat starts
        addCard(Zone.BATTLEFIELD, playerB, "Wily Bandar");
        addCard(Zone.BATTLEFIELD, playerB, "Stern Constable");

        attack(3, playerA, "Kessig Forgemaster");
        block(3, playerB, "Wily Bandar", "Kessig Forgemaster");
        block(3, playerB, "Stern Constable", "Kessig Forgemaster");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Kessig Forgemaster was blocked, no trample
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // both 1/1s should die before they had a chance to deal damage
        // to Kessig Forgemaster
        assertPermanentCount(playerA, "Kessig Forgemaster", 1);
        assertGraveyardCount(playerA, "Kessig Forgemaster", 0);
        assertPermanentCount(playerB, "Wily Bandar", 0);
        assertGraveyardCount(playerB, "Wily Bandar", 1);
        assertPermanentCount(playerB, "Stern Constable", 0);
        assertGraveyardCount(playerB, "Stern Constable", 1);
    }
}
