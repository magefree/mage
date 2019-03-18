
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class CounterspellTest extends CardTestPlayerBase {

    /**
        It looks like Boom//Bust can't be countered (although it says it's countered in the log).

        Code: Select all
            13:10: Benno casts Boom [8ce] targeting Mountain [4c8] Island [80c]
            13:10: Benno casts Counterspell [2b7] targeting Boom [8ce]
            13:10: Benno puts Boom [8ce] from stack into their graveyard
            13:10: Boom is countered by Counterspell [2b7]
            13:10: Benno puts Counterspell [2b7] from stack into their graveyard
            13:10: Mountain [4c8] was destroyed
            13:10: Island [80c] was destroyed
     */
    
    @Test
    public void testCounterSplitSpell() {
        // Boom - Sorcery   {1}{R}
        // Destroy target land you control and target land you don't control.        
        addCard(Zone.HAND, playerA, "Boom // Bust");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boom", "Mountain^Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Boom");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Boom // Bust", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
        
        assertPermanentCount(playerA, "Mountain", 2);
        assertPermanentCount(playerB, "Island", 2);
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
