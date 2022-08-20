package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SplitSecondTest extends CardTestPlayerBase {

    @Test
    public void testCounterSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.HAND, playerA, "Sudden Shock");
        addCard(Zone.HAND, playerA, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Sudden Shock");

        setStopAt(1, PhaseStep.END_TURN);

        // TODO: Needed, see https://github.com/magefree/mage/issues/8973
        try {
            execute();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about trying to use Counterspell, but got:\n" + e.getMessage());
            }
        }

        assertHandCount(playerA, "Counterspell", 1);
        assertGraveyardCount(playerA, "Sudden Shock", 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void testCopiedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Doublecast");
        addCard(Zone.HAND, playerA, "Sudden Shock");
        addCard(Zone.HAND, playerA, "Raging Goblin");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doublecast");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Shock", playerB);

        // No split second spells are on the stack, effect should not apply anymore
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Raging Goblin");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2 - 2);
        assertPermanentCount(playerA, "Raging Goblin", 1);
    }
}
