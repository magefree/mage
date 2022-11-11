
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InfiniteReflectionTest extends CardTestPlayerBase {

    /**
     *
     */
    @Test
    public void testCopyAsEnters() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
//        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise", 1);
//        addCard(Zone.HAND, playerA, "Nantuko Husk", 1);// {2}{B}
        addCard(Zone.GRAVEYARD, playerA, "Pillarfield Ox", 1);
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1); // {B}

        // Enchant creature
        // When Infinite Reflection enters the battlefield attached to a creature, each other nontoken creature you control becomes a copy of that creature.
        // Nontoken creatures you control enter the battlefield as a copy of enchanted creature.
        addCard(Zone.HAND, playerA, "Infinite Reflection", 1); // {5}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infinite Reflection", "Silvercoat Lion", true);
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nantuko Husk");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Pillarfield Ox");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
    }

}
