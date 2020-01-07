/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VivienTest  extends CardTestPlayerBase {
    
    @Test
    public void VivienArkbowRangerAbility1NoTargetsTest() {
        // +1: Distribute two +1/+1 counters among up to two target creatures. They gain trample until end of turn.
        // −3: Target creature you control deals damage equal to its power to target creature or planeswalker.
        // −5: You may choose a creature card you own from outside the game, reveal it, and put it into your hand.
        addCard(Zone.HAND, playerA, "Vivien, Arkbow Ranger"); // Planeswalker {1}{G}{G}{G} - starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vivien, Arkbow Ranger");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Distribute");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vivien, Arkbow Ranger", 1);
        assertCounterCount("Vivien, Arkbow Ranger", CounterType.LOYALTY, 5);

    }
    
}
