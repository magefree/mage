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
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CastOtherPlayersCardFromExileTest extends CardTestPlayerBase {

    /**
     * https://github.com/magefree/mage/issues/2721 Praetor's Grasp and Thada
     * Adel, Acquisitor, and possibly other cards with similar effects cause the
     * owner of stolen cards to be unable to cast those cards if they somehow
     * reacquire them.
     *
     * Specific examples I've encountered:
     *
     * In two separate games with Thada Adel, Acquisitor, I stole a Sol Ring and
     * cast it. After some board wipes and graveyard digging, the Sol Rings
     * found their way back to their owners' hands. They were unable to cast
     * them.
     *
     * I stole a Spine of Ish Sah with Praetor's Grasp. After a board wipe, the
     * Spine returned to its owner's hand and they were unable to cast it.
     */
    @Test
    public void testCastWithThadaAdelAcquisitor() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Islandwalk
        // Whenever Thada Adel, Acquisitor deals combat damage to a player, search that player's library for an artifact card and exile it. Then that player shuffles his or her library.
        // Until end of turn, you may play that card.
        addCard(Zone.BATTLEFIELD, playerA, "Thada Adel, Acquisitor", 1); // Creature {1}{U}{U} 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        // Sacrifice Bottle Gnomes: You gain 3 life.
        addCard(Zone.LIBRARY, playerB, "Bottle Gnomes", 8); // Creature {3} 1/3
        // Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerB, "Wildwood Rebirth"); // Instant {1}{G}

        attack(1, playerA, "Thada Adel, Acquisitor");
        setChoice(playerA, "Bottle Gnomes");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bottle Gnomes");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wildwood Rebirth", "Bottle Gnomes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bottle Gnomes");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertLife(playerA, 23);
        assertGraveyardCount(playerB, "Wildwood Rebirth", 1);
        assertPermanentCount(playerB, "Bottle Gnomes", 1);
    }

    @Test
    public void testCastWithThadaAdelAcquisitorReturnedFromBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Islandwalk
        // Whenever Thada Adel, Acquisitor deals combat damage to a player, search that player's library for an artifact card and exile it. Then that player shuffles his or her library.
        // Until end of turn, you may play that card.
        addCard(Zone.BATTLEFIELD, playerA, "Thada Adel, Acquisitor", 1); // Creature {1}{U}{U} 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Sacrifice Bottle Gnomes: You gain 3 life.
        addCard(Zone.LIBRARY, playerB, "Bottle Gnomes", 8); // Creature {3} 1/3
        // Return target creature you own to your hand.
        // Flashback {W}
        addCard(Zone.HAND, playerB, "Saving Grasp"); // Instant {U}

        attack(1, playerA, "Thada Adel, Acquisitor");
        setChoice(playerA, "Bottle Gnomes");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bottle Gnomes");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Saving Grasp", "Bottle Gnomes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bottle Gnomes");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertLife(playerA, 20);
        assertGraveyardCount(playerB, "Saving Grasp", 1);
        assertPermanentCount(playerB, "Bottle Gnomes", 1);
    }
}
