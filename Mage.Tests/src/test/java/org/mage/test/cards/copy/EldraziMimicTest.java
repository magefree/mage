
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EldraziMimicTest extends CardTestPlayerBase {

    /**
     * Eldrazi Mimic also did not copy the last known P/T of a Drowner of Hope
     * that was killed with the trigger on the stack.
     *
     */
    @Test
    public void testCopyIfPermanentIsGone() {
        // Devoid (This card has no color.)
        // Flying
        // Whenever you cast a colorless spell, target opponent exiles the top card of their library.
        addCard(Zone.HAND, playerA, "Thought Harvester", 1); // {3}{U} 2/4
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Whenever another colorless creature enters the battlefield under your control, you may have the base power and toughness of Eldrazi Mimic
        // become that creature's power and toughness until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Mimic", 1); // 2/1

        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought Harvester");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Thought Harvester");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Terror", 1);
        assertGraveyardCount(playerA, "Thought Harvester", 1);

        assertPermanentCount(playerA, "Eldrazi Mimic", 1);
        assertPowerToughness(playerA, "Eldrazi Mimic", 2, 4);

    }

    /**
     * Eldrazi Mimic ability to change it's health and power on another
     * creatures entering the battlefield doesn't work after update.
     */
    @Test
    public void testNormalCopy() {
        addCard(Zone.HAND, playerA, "Composite Golem", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        // Whenever another colorless creature enters the battlefield under your control, you may have the base power and toughness of Eldrazi Mimic
        // become that creature's power and toughness until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Mimic", 1); // 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Composite Golem");
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Composite Golem", 4, 4);
        assertPowerToughness(playerA, "Eldrazi Mimic", 4, 4);

    }
}
