package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SpitemareTest extends CardTestPlayerBase {
    
    /**
     * Reported bug: Spitemare's ability isn't dealing damage to creatures.
     */
    @Test
    public void combatDamageTargetCreature() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        
        /**
         * {2}{R/W}{R/W}
         * Creature - Elemental (3/3)
         * Whenever Spitemare is dealt damage, it deals that much damage to any target.
         */        
        addCard(Zone.BATTLEFIELD, playerB, "Spitemare", 1);
        
        attack(1, playerA, "Bronze Sable");
        block(1, playerB, "Spitemare", "Bronze Sable");
        addTarget(playerB, "Grizzly Bears");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerA, "Bronze Sable", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Spitemare", 1);
    }
    
    /**
     * Reported bug: Spitemare's ability isn't dealing damage to creatures.
     */
    @Test
    public void nonCombatDamageTargetCreature() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant", 1); // 3/3
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R} deals 3 to target creature/player
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        
        /**
         * {2}{R/W}{R/W}
         * Creature - Elemental (3/3)
         * Whenever Spitemare is dealt damage, it deals that much damage to any target.
         */        
        addCard(Zone.BATTLEFIELD, playerB, "Spitemare", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Spitemare");
        addTarget(playerB, "Hill Giant");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Hill Giant", 1);
        assertGraveyardCount(playerB, "Spitemare", 1);
    }
    
    @Test
    public void combatDamageTargetPlayer() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable", 1); // 2/1
        
        /**
         * {2}{R/W}{R/W}
         * Creature - Elemental (3/3)
         * Whenever Spitemare is dealt damage, it deals that much damage to any target.
         */        
        addCard(Zone.BATTLEFIELD, playerB, "Spitemare", 1);
        
        attack(1, playerA, "Bronze Sable");
        block(1, playerB, "Spitemare", "Bronze Sable");
        addTarget(playerB, playerA);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerA, "Bronze Sable", 1);
        assertPermanentCount(playerB, "Spitemare", 1);
        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }
    
    @Test
    public void nonCombatDamageTargetPlayer() {
        
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R} deals 3 to target creature/player
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        
        /**
         * {2}{R/W}{R/W}
         * Creature - Elemental (3/3)
         * Whenever Spitemare is dealt damage, it deals that much damage to any target.
         */        
        addCard(Zone.BATTLEFIELD, playerB, "Spitemare", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Spitemare");
        addTarget(playerB, playerA);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Spitemare", 1);
        assertLife(playerA, 17);
        assertLife(playerB, 20);
    }
}
