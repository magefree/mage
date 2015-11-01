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
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InfiniteReflectionTest extends CardTestPlayerBase {

    /**
     *
     */
    @Test
    public void testCopyAsEnters() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
//        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise", 1);
//        addCard(Zone.HAND, playerA, "Nantuko Husk", 1);// {2}{B}
        addCard(Zone.GRAVEYARD, playerA, "Pillarfield Ox", 1);
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1); // {B}

        // Enchant creature
        // When Infinite Reflection enters the battlefield attached to a creature, each other nontoken creature you control becomes a copy of that creature.
        // Nontoken creatures you control enter the battlefield as a copy of enchanted creature.
        addCard(Zone.HAND, playerA, "Infinite Reflection", 1); // {5}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infinite Reflection", "Silvercoat Lion");
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nantuko Husk");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Pillarfield Ox");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
    }

}
