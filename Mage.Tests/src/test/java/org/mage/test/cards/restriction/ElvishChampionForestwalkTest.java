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

package org.mage.test.cards.restriction;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

/*
 *  Elvish Champion
 *  Creature — Elf 2/2, 1GG
 *  Other Elf creatures get +1/+1 and have forestwalk. (They're unblockable as long as defending player controls a Forest.)
 */

public class ElvishChampionForestwalkTest extends CardTestPlayerBase {

    /**
     * Tests "If all other elves get the Forestwalk ability and can't be blockt from creatures whose controler has a forest in game"
     */

    @Test
    public void testCannotBlockCreatureWithForestwalk() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elvish Champion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Arbor Elf");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Defiant Elf");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Canyon Minotaur");

        attack(3, playerA, "Arbor Elf");
        attack(3, playerA, "Defiant Elf");
        block(3, playerB, "Silvercoat Lion", "Arbor Elf");
        block(3, playerB, "Canyon Minotaur", "Defiant Elf");

        setStopAt(3, Constants.PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Arbor Elf", 1);
        assertPermanentCount(playerA, "Defiant Elf", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 16);

    }

}
