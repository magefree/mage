
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AlmsBeastTest extends CardTestPlayerBase {

    @Test
    public void testLifelink() {
        // Creatures blocking or blocked by Alms Beast have lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Alms Beast");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Alms Beast", "Silvercoat Lion");
        
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        
        assertLife(playerB, 22); // 20 + 2 from lifelink
    }

    @Test
    public void testNoLifelinkAfterCombat() {
        // {T}: Rootwater Hunter deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Rootwater Hunter");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        // Prevent all damage that would be dealt to target creature this turn.
        addCard(Zone.HAND, playerA, "Shielded Passage");

        
        // Creatures blocking or blocked by Alms Beast have lifelink.
        addCard(Zone.BATTLEFIELD, playerB, "Alms Beast");

        attack(2, playerB, "Alms Beast");
        block(2, playerA, "Rootwater Hunter", "Alms Beast");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Shielded Passage", "Rootwater Hunter");
        
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: ", playerB); // no life because lifelink ends at combat end
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Shielded Passage", 1);
        assertPermanentCount(playerA, "Rootwater Hunter", 1);
        
        assertLife(playerA, 21); // 20 + 1 from lifelink block
        assertLife(playerB, 19); // -1 from Rootwater Hunter
    }
}