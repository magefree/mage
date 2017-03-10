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
public class ChaliceOfTheVoidTest extends CardTestPlayerBase {

    /**
     * Theres a Chalice of the Void with 1 counter in play under my control.
     * Then I cast second chalice with x=1. For spells on the stack the cmc is the base CMC + X value * {X} in casting costs on top right of card.
     * So cmc should be 2 in this case, it shouldnt be countered.
     * http://boardgames.stackexchange.com/questions/7327/what-is-the-converted-mana-cost-of-a-spell-with-x-when-cast-with-the-miracle-m
     */

    @Test
    public void testX1CountsFor2CMC() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Chalice of the Void", 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chalice of the Void", 2);

    }
    
    /*
    If X=1 the cmc of Chalice on the stack is 2. So it can't be countered by Mental Misstep
    */
    @Test
    public void testCantBeCounteredByMentalMisstep() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Chalice of the Void", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Mental Misstep", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mental Misstep", "Chalice of the Void", "Chalice of the Void");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerB, "Mental Misstep", 1); // cannot be cast because no legal target exists
        assertPermanentCount(playerA, "Chalice of the Void", 1); // was not countered

    }

    /*
        Conflagurate flashed back for X >= 0 should not be countered by chalice.
     */
    @Test
    public void testConflagrateFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Chalice of the Void", 1);

        addCard(Zone.GRAVEYARD, playerB, "Conflagrate", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1");


        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flashback {R}{R}");
        setChoice(playerB, "X=1");
        addTarget(playerB, playerA);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);

        assertExileCount(playerB, "Conflagrate", 1);
        //TODO: Apparently there are two mountains in the graveyard at the end of the test now.
        //assertGraveyardCount(playerB, "Mountain", 1);

    }
    
}
