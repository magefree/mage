package org.mage.test.cards.single.isd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author North
 */
public class GutterGrimeTest extends CardTestPlayerBase {

    /**
     * If Gutter Grime leaves the battlefield, the power and toughness of each Ooze token it created will become 0.
     * Unless another effect is raising its toughness above 0, each of these Ooze tokens will be put into its owner's
     * graveyard the next time state-based actions are checked.
     */
    @Test
    public void testScenario1() {
        addCard(Zone.BATTLEFIELD, playerA, "Gutter Grime");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.HAND, playerA, "Naturalize", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Runeclaw Bear");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Naturalize", "Gutter Grime");

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Ooze Token", 0);
    }

    @Test
    public void testScenario2() {
        // Creature tokens you control get +1/+1 and have vigilance.
        addCard(Zone.BATTLEFIELD, playerA, "Intangible Virtue");
        // Whenever a nontoken creature you control dies, put a slime counter on Gutter Grime, then put a green Ooze creature token onto
        // the battlefield with "This creature's power and toughness are each equal to the number of slime counters on Gutter Grime."
        addCard(Zone.BATTLEFIELD, playerA, "Gutter Grime");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Runeclaw Bear");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Runeclaw Bear", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Gutter Grime", 1);
        assertPermanentCount(playerA, "Intangible Virtue", 1);

        assertPermanentCount(playerA, "Ooze Token", 1);
        assertPowerToughness(playerA, "Ooze Token", 2, 2, Filter.ComparisonScope.Any);
    }
}
