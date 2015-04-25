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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class XathridNecromancerTest extends CardTestPlayerBase {

    /**
     * My opponent had 2 Human Tokens from Gather the Townsfolk and a Xathrid Necromancer.
     * ( Whenever Xathrid Necromancer or another Human creature you control dies, put a 2/2 black Zombie creature token onto the battlefield tapped. )
     * All 3 of them died in combat but he only got 1 Zombie token.
     * There was no first strike damage involved.
     * 
     */
    @Test
    public void testDiesTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Creature - Human Wizard
        // 2/2
        // Whenever Xathrid Necromancer or another Human creature you control dies, put a 2/2 black Zombie creature token onto the battlefield tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Xathrid Necromancer", 1);
        // Put two 1/1 white Human creature tokens onto the battlefield.
        // Fateful hour - If you have 5 or less life, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerA, "Gather the Townsfolk");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Siege Mastodon", 1);
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gather the Townsfolk");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Pillarfield Ox");
        attack(2, playerB, "Siege Mastodon");

        block(2, playerA, "Human", "Silvercoat Lion");
        block(2, playerA, "Human", "Pillarfield Ox");
        block(2, playerA, "Xathrid Necromancer", "Siege Mastodon");
        
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Xathrid Necromancer", 1);
        assertGraveyardCount(playerA, "Gather the Townsfolk", 1);

        assertPermanentCount(playerA, "Human", 0);
        assertPermanentCount(playerA, "Zombie", 3);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
    }

}