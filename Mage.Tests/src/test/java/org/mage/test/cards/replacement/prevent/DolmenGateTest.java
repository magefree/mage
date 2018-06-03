
package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DolmenGateTest extends CardTestPlayerBase {

    @Test
    public void testPreventForAttackingCreature() {
        // Prevent all combat damage that would be dealt to attacking creatures you control.
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        addCard(Zone.BATTLEFIELD, playerB, "Dolmen Gate");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        
        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Pillarfield Ox", "Silvercoat Lion");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Silvercoat Lion", 0); // damage prevented because attacking, so it does not die
    }
    /**
     * In a commander game I was playing, a player blocked with a Dolmen Gate on the field, and the gate 
     * prevented the damage that was dealt to their blocking creature (it should only prevent damage to the player's attacking creatures).
     */
    @Test
    public void testDontPreventForBlockingCreature() {
        // Prevent all combat damage that would be dealt to attacking creatures you control.
        addCard(Zone.BATTLEFIELD, playerA, "Dolmen Gate");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");
        
        attack(2, playerB, "Pillarfield Ox");
        block(2, playerA, "Silvercoat Lion", "Pillarfield Ox");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1); // no damage prevented because blocking, so it has to die
    }

}
