package org.mage.test.cards.single.mor;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class StinkdrinkerBanditTest extends CardTestPlayerBase {

    private String stinkdrinker = "Stinkdrinker Bandit";
    
    /**
     *  Reported bug: Stinkdrinker Bandit is incorrectly giving the +2/+1 bonus even to Rogues that do get blocked.
     */
    @Test
    public void unblockedRoguesTest() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Agent of Horizons"); // 3/2 rogue
        addCard(Zone.BATTLEFIELD, playerA, "Amphin Cutthroat"); // 2/4 rogue
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable"); // 2/1
        
        // Whenever a Rogue you control attacks and isn't blocked, it gets +2/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Stinkdrinker Bandit"); // 2/1 rogue
        
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Omens"); // 0/4
        
        attack(1, playerA, "Agent of Horizons"); 
        attack(1, playerA, "Amphin Cutthroat"); 
        attack(1, playerA, "Stinkdrinker Bandit"); 
        attack(1, playerA, "Bronze Sable"); 
        
        block(1, playerB, "Wall of Omens", "Stinkdrinker Bandit");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPowerToughness(playerA, "Stinkdrinker Bandit", 2, 1); // blocked, so stays 2/1
        assertPowerToughness(playerA, "Agent of Horizons", 5, 3); // 3/2 + 2/1
        assertPowerToughness(playerA, "Amphin Cutthroat", 4, 5); // 2/4 + 2/1
        assertPowerToughness(playerA, "Bronze Sable", 2, 1);
        assertPermanentCount(playerB, "Wall of Omens", 1);
        assertPowerToughness(playerB, "Wall of Omens", 0, 4);        
        assertLife(playerA, 20);
        assertLife(playerB, 9); // Agent pumped to 5, Amphin pumped to 4, Sable stays at 2 power (11 damage)
    }

    @Test
    public void stinkDrinkerPesterMite(){
        String pestermite = "Pestermite";
        addCard(Zone.BATTLEFIELD, playerA, stinkdrinker);
        addCard(Zone.BATTLEFIELD, playerA, pestermite);

        attack(1, playerA, pestermite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertPowerToughness(playerA, pestermite, 4, 2);
        assertPowerToughness(playerA, stinkdrinker, 2, 1);
    }
}
