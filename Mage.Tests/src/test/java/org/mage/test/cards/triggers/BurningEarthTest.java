
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BurningEarthTest extends CardTestPlayerBase {

    /**
     * Burning Earth - It doesn't cause the damage it should. My opponent taps a
     * Blood Crypt and an Overgrown Tomb for black and green mana respectively
     * and casts their card all the while without taking any damage.
     *
     */
    @Test
    public void testBurningEarth() {
        // Destroy target artifact or creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Putrefy"); // {1}{B}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Blood Crypt", 1); // {B}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Overgrown Tomb", 1); // {B}{G}
        
        // Whenever a player taps a nonbasic land for mana, Burning Earth deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Burning Earth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Putrefy", "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Putrefy", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

}
