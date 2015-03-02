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
 * @author LevelX2
 */
public class GeistOfSaintTraftTest extends CardTestPlayerBase {

    /**
     * Geist of Saint Traft - Legendary Spirit Cleric 2/2, {1}{W}{U}
     *
     * Hexproof
     * Whenever Geist of Saint Traft attacks, put a 4/4 white Angel creature token with flying onto the battlefield tapped and attacking. Exile that token at end of combat.
     *
     */
    @Test
    public void testTokenwillBeCreated() {

        addCard(Zone.BATTLEFIELD, playerB, "Geist of Saint Traft", 1);

        attack(2, playerB, "Geist of Saint Traft");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, "Geist of Saint Traft", 1);
        assertPowerToughness(playerB, "Geist of Saint Traft", 2, 2);
        assertPermanentCount(playerB, "Angel", 1);
        assertPowerToughness(playerB, "Angel", 4, 4);

        assertLife(playerA, 14);
        assertLife(playerB, 20);
    }


    @Test
    public void testTokenwillBeExiled() {

        addCard(Zone.BATTLEFIELD, playerB, "Geist of Saint Traft", 1);

        attack(2, playerB, "Geist of Saint Traft");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Geist of Saint Traft", 1);
        assertPowerToughness(playerB, "Geist of Saint Traft", 2, 2);
        assertPermanentCount(playerB, "Angel", 0);

        assertLife(playerA, 14);
        assertLife(playerB, 20);
    }
}
