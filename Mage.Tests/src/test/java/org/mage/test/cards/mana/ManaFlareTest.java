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
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ManaFlareTest extends CardTestPlayerBase {

    @Test
    public void testIsland() {
        // Whenever a player taps a land for mana, that player adds one mana to his or her mana pool of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Mana Flare", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Creature {U}{U}
        // {U},{T} :Return target permanent you control to its owner's hand.
        addCard(Zone.HAND, playerA, "Vedalken Mastermind", 1);

        // because available mana calculation does not work correctly with Mana Flare we have to tap the land manually
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U} to your mana pool");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vedalken Mastermind");        
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Vedalken Mastermind", 1);

    }
    
    
    /**
     * Mana Flare is only adding colorless mana, at least off of dual lands (Watery Grave in this instance).
     * Island only adds colorless. Plains adds white though.
     */
    @Test
    public void testWateryGrave() {
        // {T}: Add one mana of any color to your mana pool. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerB, "Mana Flare", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Watery Grave", 1);

        // Creature {B}{B}
        // {B}: Nantuko Shade gets +1/+1 until end of turn.
        addCard(Zone.HAND, playerB, "Nantuko Shade", 1);

        // because available mana calculation does not work correctly with Mana Flare we have to tap the land manually
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {B} to your mana pool");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Nantuko Shade");        
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Nantuko Shade", 1);

    }

    
}
