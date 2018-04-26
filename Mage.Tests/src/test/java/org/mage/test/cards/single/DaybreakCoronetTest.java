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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DaybreakCoronetTest extends CardTestPlayerBase {

    // Daybreak Coronet {W}{W}
    // Enchantment — Aura
    // Enchant creature with another Aura attached to it
    // Enchanted creature gets +3/+3 and has first strike, vigilance, and lifelink. (Damage dealt by the creature also causes its controller to gain that much life.)


    @Test
    public void testCantEnchantTargetWithoutAura() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Daybreak Coronet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daybreak Coronet", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Daybreak Coronet", 0);

    }

    @Test
    public void testCanBeCastIfEnchantedByAura() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Holy Strength");
        addCard(Zone.HAND, playerA, "Daybreak Coronet");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daybreak Coronet", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Daybreak Coronet", 1);

    }
    /*
    If Daybreak Coronet is already attached to a permanent and
    the other aura is destroyed. The target for Daybreak Coronet
    gets illegal and the Daybreak Coronet has to go to graveyard.
    */
    @Test
    public void testTargetGetsIllegal() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Holy Strength");
        addCard(Zone.HAND, playerA, "Daybreak Coronet");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.HAND, playerB, "Demystify");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daybreak Coronet", "Silvercoat Lion");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Demystify", "Holy Strength");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Holy Strength", 1);
        assertGraveyardCount(playerB, "Demystify", 1);
        assertPermanentCount(playerA, "Daybreak Coronet", 0);
        assertGraveyardCount(playerA, "Daybreak Coronet", 1);

    }
}
