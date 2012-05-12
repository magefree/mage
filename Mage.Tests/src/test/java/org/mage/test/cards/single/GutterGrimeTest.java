package org.mage.test.cards.single;

import mage.Constants.PhaseStep;
import mage.Constants.Zone;
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
        assertPermanentCount(playerA, "Ooze", 0);
    }

    @Test
    public void testScenario2() {
        addCard(Zone.BATTLEFIELD, playerA, "Intangible Virtue");
        addCard(Zone.BATTLEFIELD, playerA, "Gutter Grime");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Runeclaw Bear");

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertPowerToughness(playerA, "Ooze", 2, 2, Filter.ComparisonScope.Any);
    }
}
