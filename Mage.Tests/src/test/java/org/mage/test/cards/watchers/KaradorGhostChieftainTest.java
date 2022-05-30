package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.k.KaradorGhostChieftain Karador, Ghost Chieftain}
 * {5}{W}{B}{G}
 * Legendary Creature — Centaur Spirit
 * This spell costs {1} less to cast for each creature card in your graveyard.
 * During each of your turns, you may cast a creature spell from your graveyard.
 * 3/4
 *
 * @author BetaSteward
 */
public class KaradorGhostChieftainTest extends CardTestPlayerBase {

    /**
     * Test that you can cast a spell from the graveyard.
     */
    @Test
    public void testPlayFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Karador, Ghost Chieftain");
        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
                
        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertGraveyardCount(playerA, "Raging Goblin", 0);
    }

    /**
     * Test that you can cast from your graveyard.
     */
    @Test
    public void testPlayOneFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Karador, Ghost Chieftain");
        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
                
        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertGraveyardCount(playerA, "Raging Goblin", 1);
    }

    /**
     * Test that can cast only one spell from your graveyard per turn
     */
    @Test
    public void testPlayOneFromGraveyardPerTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Karador, Ghost Chieftain");
        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        checkPlayableAbility("Can't cast 2nd spell this turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Raging", false);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        checkPlayableAbility("Can't cast 2nd spell this turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Raging", false);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 2);
        assertGraveyardCount(playerA, "Raging Goblin", 0);
    }
}
