
package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DemonicPactTest extends CardTestPlayerBase {

    @Test
    public void testModes() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // At the beginning of your upkeep, choose one that hasn't been chosen
        // (1) - Demonic Pact deals 4 damage to any target and you gain 4 life;
        // (2) - Target opponent discards two cards
        // (3) - Draw two cards
        // (4) - You lose the game.
        addCard(Zone.HAND, playerA, "Demonic Pact"); // Enchantment {2}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Pact");

        setModeChoice(playerA, "3");

        setModeChoice(playerA, "2");
        addTarget(playerA, playerB);

        setModeChoice(playerA, "1");
        addTarget(playerA, playerB);

        setStopAt(7, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Demonic Pact", 1);
        assertLife(playerA, 24);
        assertLife(playerB, 16);
        assertHandCount(playerB, 1); // discard 2 + 3 from regular draws

        assertHandCount(playerA, 5); // two from Demonic Pact + 3 from regular draws

    }

    /*
        The rollback to the start of the turn does not correctly reset the already selected choices from Demonic Pact. They are not available again.
     */
    @Test
    public void testModeAfterRollback() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // At the beginning of your upkeep, choose one that hasn't been chosen
        // (1) - Demonic Pact deals 4 damage to any target and you gain 4 life;
        // (2) - Target opponent discards two cards
        // (3) - Draw two cards
        // (4) - You lose the game.
        addCard(Zone.HAND, playerA, "Demonic Pact"); // Enchantment {2}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Pact");

        setModeChoice(playerA, "1");
        addTarget(playerA, playerB);

        rollbackTurns(3, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1); // 1 from regular draws
        assertHandCount(playerB, 1); // 1 from regular draw

        assertLife(playerA, 24);
        assertLife(playerB, 16);

    }
}
