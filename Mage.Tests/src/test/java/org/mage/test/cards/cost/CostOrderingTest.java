package org.mage.test.cards.cost;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CostOrderingTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        // You can choose the order to pay costs when paying 2 or more costs with the "additional" tag

        addCard(Zone.HAND, playerA, "Creakwood Safewright");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Dawnhand Dissident");
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde");
        addCard(Zone.GRAVEYARD, playerA, "Champions of the Perfect");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Creakwood Safewright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Blight 2:");
        addTarget(playerA, "Champions of the Perfect");
        setChoice(playerA, "Barbarian Horde");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Champions of the Perfect");
        setChoice(playerA, "Remove three counters from among creatures you control");
        setChoice(playerA, "Creakwood Safewright");
        setChoice(playerA, "X=3");
        setChoice(playerA, "Creakwood Safewright");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Champions of the Perfect", 1);
        assertExileCount(playerA, "Creakwood Safewright", 1);
    }

    @Test
    public void testOrCost() {
        // "X or Y" costs are treated as a single cost for purposes of cost ordering.
        // You choose which one when you pay the OrCost, which may be after other costs

        addCard(Zone.HAND, playerA, "Creakwood Safewright");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Dawnhand Dissident");
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde");
        addCard(Zone.GRAVEYARD, playerA, "Bayou Groff");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Creakwood Safewright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Blight 2:");
        addTarget(playerA, "Bayou Groff");
        setChoice(playerA, "Barbarian Horde");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bayou Groff");
        setChoice(playerA, "Remove three counters from among creatures you control");
        setChoice(playerA, "Creakwood Safewright");
        setChoice(playerA, "X=3");
        setChoice(playerA, true);
        setChoice(playerA, "Creakwood Safewright");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Bayou Groff", 1);
        assertGraveyardCount(playerA, "Creakwood Safewright", 1);
    }

    @Test
    public void testSingleCost() {
        // The player is not prompted when only one cost with the "additional" tag is required

        addCard(Zone.HAND, playerA, "Creakwood Safewright");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Dawnhand Dissident");
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde");
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Creakwood Safewright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Blight 2:");
        addTarget(playerA, "Balduvian Bears");
        setChoice(playerA, "Barbarian Horde");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        setChoice(playerA, "Creakwood Safewright");
        setChoice(playerA, "X=3");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertPowerToughness(playerA, "Creakwood Safewright", 5, 5);
    }
}
