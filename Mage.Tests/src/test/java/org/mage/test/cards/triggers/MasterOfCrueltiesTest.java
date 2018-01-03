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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MasterOfCrueltiesTest extends CardTestPlayerBase {

    /*
    The ability of an Alesha-resurrected Master of Cruelties triggered in an EDH game despite being blocked by a creature.
     */
    @Test
    public void testMasterWasAleshaAnimated() {
        // First strike
        // Whenever Alesha, Who Smiles at Death attacks, you may pay {W/B}{W/B}. If you do, return target creature card with power 2 or less from your graveyard to the battlefield tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerB, "Alesha, Who Smiles at Death"); // 3/2

        // First strike
        // Deathtouch
        // Master of Cruelties can only attack alone.
        // Whenever Master of Cruelties attacks a player and isn't blocked, that player's life total becomes 1. Master of Cruelties assigns no combat damage this combat.
        addCard(Zone.GRAVEYARD, playerB, "Master of Cruelties"); // 1/4

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        attack(2, playerB, "Alesha, Who Smiles at Death");
        setChoice(playerB, "Yes");
        addTarget(playerB, "Master of Cruelties");

        block(2, playerA, "Silvercoat Lion", "Master of Cruelties");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Master of Cruelties", 1);
        assertTapped("Master of Cruelties", true);
        assertTapped("Alesha, Who Smiles at Death", true);

        assertLife(playerA, 17);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

    }

}
