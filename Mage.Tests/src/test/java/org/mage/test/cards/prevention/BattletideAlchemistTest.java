
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author notgreat
 */
public class BattletideAlchemistTest extends CardTestPlayerBase {

    @Test
    public void testBattletideAlchemist() {
        // If a source would deal damage to a player, you may prevent X of that damage,
        // where X is the number of Clerics you control.
        addCard(Zone.BATTLEFIELD, playerA, "Mystic Monastery", 7);
        addCard(Zone.HAND, playerA, "Boltwave", 3); // R, deal 3 to opponent
        addCard(Zone.HAND, playerA, "Ego Erasure"); // 2U, lose all creature types

        addCard(Zone.HAND, playerA, "Battletide Alchemist");
        addCard(Zone.BATTLEFIELD, playerA, "Battletide Alchemist");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boltwave", true);
        setChoice(playerA, true); //prevent 1 damage = deal 2
        checkLife("first bolt", 1, PhaseStep.BEGIN_COMBAT, playerB, 18);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Battletide Alchemist", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Boltwave", true);
        setChoice(playerB, "Battletide Alchemist"); //Order prevention effects
        setChoice(playerA, true); //prevent 2 damage, skip the next 2 = deal 1
        setChoice(playerA, false);
        checkLife("second bolt", 1, PhaseStep.END_TURN, playerB, 17);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ego Erasure",  playerA);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Boltwave");
        setChoice(playerB, "Battletide Alchemist"); //Order prevention effects
        //no choice on applying prevention, since it has been reduced to 0
        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 14);
    }
}
