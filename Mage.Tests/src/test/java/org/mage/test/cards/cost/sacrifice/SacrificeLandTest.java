package org.mage.test.cards.cost.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.sba.PlaneswalkerRuleTest;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Random;

/**
 * Reported bugs: https://github.com/magefree/mage/issues/8980
 *      Cards like Soldevi Excavations, Heart of Yavimaya, and Lake of the Dead
 *      will sometimes immediately go to the graveyard without asking the controller if they wish to sacrifice a land
 *      even though they had one available
 * @author Alex-Vasile
 */
public class SacrificeLandTest extends CardTestPlayerBase {

    /**
     * Perhaps it is a rollback issue with saving state
     */
    @Test
    public void testRollback() {
        // If Soldevi Excavations would enter the battlefield, sacrifice an untapped Island instead.
        // If you do, put Soldevi Excavations onto the battlefield. If you don't, put it into its owner's graveyard.
        String soldeviExcavations = "Soldevi Excavations";

        addCard(Zone.HAND, playerA, soldeviExcavations);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        Random random = new Random();
        boolean sacFirstLand = random.nextBoolean();
        boolean sacSecondLand = random.nextBoolean();

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, soldeviExcavations);
        setChoice(playerA, sacFirstLand);

        rollbackTurns(1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        rollbackAfterActionsStart();
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, soldeviExcavations);
        setChoice(playerA, sacSecondLand);
        rollbackAfterActionsEnd();

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, soldeviExcavations, sacSecondLand ? 1 : 0);
        assertGraveyardCount(playerA, "Island", sacSecondLand ? 1 : 0);
    }

    /**
     * Perhaps it will come up with playing multiple on seperate turns
     */
    @Test
    public void testMultiple() {
        // If Soldevi Excavations would enter the battlefield, sacrifice an untapped Island instead.
        // If you do, put Soldevi Excavations onto the battlefield. If you don't, put it into its owner's graveyard.
        String soldeviExcavations = "Soldevi Excavations";
        // Sacrifice a Forest
        String heartofYavimaya = "Heart of Yavimaya";
        // Sacrifice a Swamp
        String lakeOfTheDead = "Lake of the Dead";

        addCard(Zone.HAND, playerA, soldeviExcavations);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        addCard(Zone.HAND, playerA, heartofYavimaya);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        addCard(Zone.HAND, playerA, lakeOfTheDead);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, soldeviExcavations);
        setChoice(playerA, "Yes");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, heartofYavimaya);
        setChoice(playerA, "Yes");

        playLand(5, PhaseStep.PRECOMBAT_MAIN, playerA, lakeOfTheDead);
        setChoice(playerA, "Yes");

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, soldeviExcavations, 1);
        assertGraveyardCount(playerA, "Island", 1);

        assertPermanentCount(playerA, heartofYavimaya, 1);
        assertGraveyardCount(playerA, "Forest", 1);

        assertPermanentCount(playerA, lakeOfTheDead, 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }
}
