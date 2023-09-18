
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.c.CircleOfAffliction Circle of Affliction}
 * {1}{B}
 * Enchantment
 * As Circle of Affliction enters the battlefield, choose a color.
 * Whenever a source of the chosen color deals damage to you, you may pay {1}.
 * If you do, target player loses 1 life and you gain 1 life.
 *
 *  @author LevelX2
 */
public class CircleOfAfflictionTest extends CardTestPlayerBase {

    /**
     * Test that it works for a single attacker of the chosen color.
     */
    @Test
    public void testOneAttackerDamage() {
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1); // 3/3 {3}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
                
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "Red");
        
        attack(2, playerB, "Hill Giant");
        addTarget(playerA, playerB); // Circle of Affliction drain ability
        setChoice(playerA, true);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
            
        assertLife(playerA, 18);
        assertLife(playerB, 19);
    }
    
    /**
     * Test that it works for multiple attackers of the same chosen color.
     */
    @Test
    public void testTwoAttackersDamage() {
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);// {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2); // {1}{W} 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "White");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Silvercoat Lion");
        addTarget(playerA, playerB);
        setChoice(playerA, true);
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Circle of Affliction", 1);

        assertLife(playerA, 18);
        assertLife(playerB, 18);
    }
    
    /**
     * Test that it works for both spells and combat damage from sources of the chosen color.
     */
    @Test
    public void testMixOfSpellsAndCombatDamage() {
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);// {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        addCard(Zone.HAND, playerB, "Lava Spike", 2); // {R} deals 3 damage to target player
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 2); // {3}{R} 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "Red");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lava Spike", playerA);        
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        attack(2, playerB, "Hill Giant");
        attack(2, playerB, "Hill Giant");
        addTarget(playerA, playerB);
        setChoice(playerA, true);
        addTarget(playerA, playerB);
        setChoice(playerA, true);
        
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lava Spike", playerA);
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Circle of Affliction", 1);

        assertLife(playerA, 12); // 12 damage - 4 drains = 8 net life total loss
        assertLife(playerB, 16); // 4 drains
    }
    
    /**
     * Check that if there are multiple attackers, ensure that it only works for those of the chosen color.
     */
    @Test
    public void testTwoAttackersDamageDifferentColors() {
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);// {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // {1}{W} 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1); // {3}{R} 3/3

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "White");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Hill Giant");
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Circle of Affliction", 1);

        assertLife(playerA, 16); // 5 life loss from combat - 1 drain = 4 net life total loss
        assertLife(playerB, 19);
    }
}
