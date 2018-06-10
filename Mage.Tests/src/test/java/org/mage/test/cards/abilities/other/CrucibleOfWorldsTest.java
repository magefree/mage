

package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CrucibleOfWorldsTest extends CardTestPlayerBase {

    /** 
     * Crucible of Worlds
     * Artifact, 3 (3)
     * You may play land cards from your graveyard.
     *
     */

    @Test
    public void testPlayLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Crucible of Worlds");
        addCard(Zone.GRAVEYARD, playerA, "Swamp");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Swamp");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Swamp", 0);

    }

    @Test
    public void testCantPlayMoreThanOneLandPerTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Crucible of Worlds");
        addCard(Zone.GRAVEYARD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, "Plains");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Swamp");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Plains");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Swamp", 0);
        assertPermanentCount(playerA, "Plains", 0);
        assertGraveyardCount(playerA, "Plains", 1);

    }
    
}
