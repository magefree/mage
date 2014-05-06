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

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */


public class EvolveTest extends CardTestPlayerBase {

    @Test
    public void testCreatureComesIntoPlay() {

        // Cloudfin Raptor gets one +1/+1 because Mindeye Drake comes into play

        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Cloudfin Raptor", 1);

        addCard(Zone.HAND, playerA, "Mindeye Drake");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mindeye Drake");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Cloudfin Raptor", 1);
        assertPermanentCount(playerA, "Mindeye Drake", 1);

        assertPowerToughness(playerA, "Cloudfin Raptor", 1, 2);
        assertPowerToughness(playerA, "Mindeye Drake", 2, 5);
    }

    @Test
    public void testCreatureComesIntoPlayNoCounter() {

        // Experiment One gets no counter because Kird Ape is 1/1 with no Forest in play

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Experiment One", 1);

        addCard(Zone.HAND, playerA, "Kird Ape");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kird Ape");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 1);
        assertPermanentCount(playerA, "Kird Ape", 1);

        assertPowerToughness(playerA, "Experiment One", 1, 1);
        assertPowerToughness(playerA, "Kird Ape", 1, 1);
    }

    @Test
    public void testCreatureComesStrongerIntoPlayCounter() {

        // Experiment One gets a counter because Kird Ape is 2/2 with a Forest in play

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Experiment One", 1);

        addCard(Zone.HAND, playerA, "Kird Ape");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kird Ape");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 1);
        assertPermanentCount(playerA, "Kird Ape", 1);

        assertPowerToughness(playerA, "Experiment One", 2, 2);
        assertPowerToughness(playerA, "Kird Ape", 2, 3);
    }

    @Test
    public void testEvolveWithMasterBiomance() {

        // Experiment One gets a counter because Kird Ape is 2/2 with a Forest in play

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Experiment One", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);

        addCard(Zone.HAND, playerA, "Experiment One");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Experiment One");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 2);
        assertPermanentCount(playerA, "Master Biomancer", 1);

        // the first Experiment One get one counter from the second Experiment one that comes into play with two +1/+1 counters
        assertPowerToughness(playerA, "Experiment One", 2, 2);
        // the casted Experiment One got two counters from Master Biomancer
        assertPowerToughness(playerA, "Experiment One", 3, 3);

    }
    
    @Test
    public void testMultipleCreaturesComeIntoPlay() {

        // Cloudfin Raptor gets one +1/+1 because itself and other creatur return from exile

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Judge's Familiar", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Cloudfin Raptor", 1);
        addCard(Zone.HAND, playerA, "Mizzium Mortars", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.HAND, playerB, "Banisher Priest", 2);
        

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banisher Priest");
        addTarget(playerB, "Cloudfin Raptor");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banisher Priest");
        addTarget(playerB, "Judge's Familiar");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mizzium Mortars with overload");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Banisher Priest", 0);
        
        assertGraveyardCount(playerB, 2);
        assertGraveyardCount(playerA, 1);

        assertPermanentCount(playerA, "Cloudfin Raptor", 1);
        assertPermanentCount(playerA, "Judge's Familiar", 1);

        assertPowerToughness(playerA, "Cloudfin Raptor", 1, 2);
        
        
    }    
    
    @Test
    public void testMultipleCreaturesComeIntoPlaySuddenDisappearance() {

        // Sudden Disappearance
        // Sorcery {5}{W}
        // Exile all nonland permanents target player controls. Return the exiled cards 
        // to the battlefield under their owner's control at the beginning of the next end step.
        
        // Battering Krasis (2/1) and Crocanura (1/3) get both a +1/+1 counter each other because they come into play at the same time

        addCard(Zone.BATTLEFIELD, playerA, "Battering Krasis", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Crocanura", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.HAND, playerB, "Sudden Disappearance", 2);
        

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sudden Disappearance", playerA);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
       
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerA, 0);

        assertPermanentCount(playerA, "Battering Krasis", 1);
        assertPermanentCount(playerA, "Crocanura", 1);

        assertPowerToughness(playerA, "Battering Krasis", 3, 2);
        assertPowerToughness(playerA, "Crocanura", 2, 4);
        
        
    }        
    
    
}
