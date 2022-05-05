
package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ArrestTest extends CardTestPlayerBase {

    @Test
    public void testArrest1() {
        addCard(Zone.HAND, playerA, "Arrest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        // {3}{G}: Create a 1/1 green Saproling creature token.
        // {3}{W}: Creatures you control get +1/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Selesnya Guildmage");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Arrest", "Selesnya Guildmage");
        attack(2, playerB, "Selesnya Guildmage");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Arrest", 1);
        assertPermanentCount(playerB, "Saproling Token", 0); // can't use ability so no Saproling

        assertLife(playerA, 20); // can't attack so no damage to player
        assertLife(playerB, 20);

    }

}
