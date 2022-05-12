
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SpellCastTriggerTest extends CardTestPlayerBase {

    /**
     * Tests Sunscorch Regent
     */
    @Test
    public void testSunscorchRegent() {
        // Creature - Dragon 4/3
        // Flying
        // Whenever an opponent casts a spell, put a +1/+1 counter on Sunscorch Regent and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Sunscorch Regent", 1);

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18); // 20 -3 +1
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertPowerToughness(playerA, "Sunscorch Regent", 5, 4);
    }

    /**
     * Monastery Mentor triggers are causing a "rollback" error.
     */
    @Test
    public void testMonasteryMentor() {
        // Prowess (Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.)
        // Whenever you cast a noncreature spell, put a 1/1 white Monk creature token with prowess onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Monastery Mentor", 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);

        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertPermanentCount(playerA, "Monk Token", 2);
        assertPowerToughness(playerA, "Monk Token", 2, 2);
        assertPowerToughness(playerA, "Monk Token", 1, 1);

        assertPowerToughness(playerA, "Monastery Mentor", 4, 4);
    }

    @Test
    public void testHarnessTheStormFirstTurn() {
        // Whenever you cast an instant or sorcery spell from your hand, you may cast target card with the same name as that spell from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Harness the Storm", 1);

        // Put two 1/1 red Devil creature tokens onto the battlefield. They have "When this creature dies, it deals 1 damage to any target."
        addCard(Zone.HAND, playerA, "Dance with Devils", 1); // {3}{R}
        addCard(Zone.GRAVEYARD, playerA, "Dance with Devils", 1); // {3}{R}

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dance with Devils");
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Dance with Devils", 2);
        assertPermanentCount(playerA, "Devil Token", 4);
    }

    /**
     * I had cast Dance with Devils the turn before. On this turn I was casting
     * Read the Bones. The enchantment should not have triggered and if it did
     * it should have asked me to cast read the bones.
     */
    @Test
    public void testHarnessTheStormThirdTurn() {
        // Whenever you cast an instant or sorcery spell from your hand, you may cast target card with the same name as that spell from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Harness the Storm", 1);

        // Put two 1/1 red Devil creature tokens onto the battlefield. They have "When this creature dies, it deals 1 damage to any target."
        addCard(Zone.HAND, playerA, "Dance with Devils", 1); // {3}{R}
        // Scry 2, then draw two cards. You lose 2 life.
        addCard(Zone.HAND, playerA, "Read the Bones", 1); // {2}{B}
        addCard(Zone.GRAVEYARD, playerA, "Read the Bones", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dance with Devils");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Read the Bones");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dance with Devils", 1);
        assertPermanentCount(playerA, "Devil Token", 2);
        assertGraveyardCount(playerA, "Read the Bones", 2);
        assertHandCount(playerA, 5); // one normally drawn + 4 from Read the Bones

    }
    
    @Test
    public void testDiamondKnightTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 6);

        // Vigilance
        // As Diamond Knight enters the battlefield, choose a color.
        // Whenever you cast a spell of the chosen color, put a +1/+1 counter on Diamond Knight.        
        addCard(Zone.HAND, playerA, "Diamond Knight", 1); // Creature 1/1 - {3}
        
        // Target 3 damage
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R}
        // Draw a card.
        // Escape—{2}{U}, Exile five other cards from your graveyard.
        addCard(Zone.HAND, playerA, "Glimpse of Freedom", 1); // Instant {1}{U}
        addCard(Zone.GRAVEYARD, playerA, "Mountain", 5); // Instant {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diamond Knight");      
        setChoice(playerA, "Blue");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Glimpse of Freedom");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse of Freedom");
        setChoice(playerA,"Mountain");
        setChoice(playerA,"Mountain");
        setChoice(playerA,"Mountain");
        setChoice(playerA,"Mountain");
        setChoice(playerA,"Mountain");
        
        

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Diamond Knight", 1);
        assertPowerToughness(playerA, "Diamond Knight", 3, 3);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);        
        assertLife(playerB, 17);        
        assertGraveyardCount(playerA, "Glimpse of Freedom", 1);
        assertExileCount(playerA, 5);
        assertHandCount(playerA, 3); 

    }
    
    
    
    
}
