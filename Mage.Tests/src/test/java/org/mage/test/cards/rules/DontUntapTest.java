
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DontUntapTest extends CardTestPlayerBase {

    /**
     * Test that the attackers blocked by creatures boosted with
     * Triton Tactics do not untap in their controllers next untap step
     */
    @Test
    public void testTritonTactics() {
        // Up to two target creatures each get +0/+3 until end of turn. Untap those creatures.
        // At this turn's next end of combat, tap each creature that was blocked by one of those
        // creatures this turn and it doesn't untap during its controller's next untap step.        
        addCard(Zone.HAND, playerA, "Triton Tactics");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // {T}: You gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        
        activateAbility(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: You gain 1 life");
        
        attack(4, playerB, "Silvercoat Lion");        
        castSpell(4, PhaseStep.DECLARE_ATTACKERS, playerA, "Triton Tactics", "Soulmender");
        block(4, playerA, "Soulmender", "Silvercoat Lion");
        
        setStopAt(6, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Triton Tactics", 1);

        assertPowerToughness(playerA, "Soulmender", 1, 1);
        
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertTapped("Silvercoat Lion", true); // Should be stilled tapped in turn 6 because it was blocked in turn 4 with Triton Tactics
        
        assertLife(playerA, 21);
        assertLife(playerB, 20);

    }
    
    /**
     * I used Ajani Vengeant's +1 on a Sublime Archangel and it untap on it's controller's upkeep.
     */
    @Test
    public void TestAjaniVengeantFirst() {

        addCard(Zone.BATTLEFIELD, playerA, "Sublime Archangel", 1); // 4/3

        // +1: Target permanent doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerB, "Ajani Vengeant", 1);
        
        attack(1, playerA, "Sublime Archangel");
                
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "+1: Target permanent doesn't","Sublime Archangel");
        
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15); // 4 + 1 from Exalted
        
        assertTapped("Sublime Archangel", true);

    }
     
    /**
     * Issue #10746: Incorrect implementations for Misstep; Pollen Lullaby; Icebreaker Kraken
     */
    @Test
    public void testExhaustionEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Reassembling Skeleton", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, "Exhaustion", 1);
        
        attack(1, playerA, "Silvercoat Lion");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Exhaustion", playerA);
        activateAbility(2, PhaseStep.END_TURN, playerA, "{1}{B}:"); // Reassembling Skeleton enters after spell is cast, should remain tapped

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Silvercoat Lion", true);
        assertTapped("Reassembling Skeleton", true);
        assertTapped("Swamp", true);
    }

    @Test
    public void testManaVaporsEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Mana Vapors", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mana Vapors", playerA);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Mountain", true);
    }

    @Test
    public void testMisstepEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Reassembling Skeleton", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, "Misstep", 1);
        
        attack(1, playerA, "Silvercoat Lion");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Misstep", playerA);
        activateAbility(2, PhaseStep.END_TURN, playerA, "{1}{B}:"); // Reassembling Skeleton enters after spell is cast, should remain tapped

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Silvercoat Lion", true);
        assertTapped("Reassembling Skeleton", true);
    }

    @Test
    public void testPollenLullabyEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Reassembling Skeleton", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Pollen Lullaby", 1);

        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, "Island", 8);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 8);
        skipInitShuffling();

        attack(1, playerA, "Silvercoat Lion");
        // Prevent all combat damage that would be dealt this turn. Clash with an opponent. If you win, creatures that player controls don't untap during the player's next untap step.
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, "Pollen Lullaby");
        setChoice(playerB, "PlayerA");
        // Choose to keep cards on top
        setChoice(playerB, false);
        setChoice(playerA, false);

        activateAbility(2, PhaseStep.END_TURN, playerA, "{1}{B}:"); // Reassembling Skeleton enters after spell is cast, should remain tapped

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Silvercoat Lion", true);
        assertTapped("Reassembling Skeleton", true);
        assertLife(playerB, 20);
    }

    @Test
    public void testIcebreakerKrakenEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Marble Chalice", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Reassembling Skeleton", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 12);
        addCard(Zone.HAND, playerB, "Icebreaker Kraken", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");
        attack(1, playerA, "Silvercoat Lion");

        // When Icebreaker Kraken enters the battlefield, artifacts and creatures target opponent controls don't untap during that player's next untap step.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Icebreaker Kraken");
        addTarget(playerB, playerA);

        activateAbility(2, PhaseStep.END_TURN, playerA, "{1}{B}:"); // Reassembling Skeleton enters after effect is in play, should remain tapped

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Silvercoat Lion", true);
        assertTapped("Marble Chalice", true);
        assertTapped("Reassembling Skeleton", true);
    }
}