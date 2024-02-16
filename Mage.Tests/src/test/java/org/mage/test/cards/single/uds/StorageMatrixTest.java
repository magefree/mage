package org.mage.test.cards.single.uds;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * As long as Storage Matrix is untapped, each player chooses artifact, creature, or land during their untap step.
 * That player can untap only permanents of the chosen type this step.
 */

public class StorageMatrixTest extends CardTestPlayerBase {

    private static final String matrix = "Storage Matrix";
    private static final String clock = "Unwinding Clock";
    // Untap all artifacts you control during each other player's untap step.
    private static final String chalice = "Marble Chalice"; // T: You gain 1 life
    private static final String worker = "Mine Worker"; // T: You gain 1 life
    private static final String activated = "{T}: You gain 1 life";


    // Reported bug #11347
    @Test
    public void testOnlyAffectsActivePlayer() {
        addCard(Zone.HAND, playerA, matrix);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.BATTLEFIELD, playerA, clock);
        addCard(Zone.BATTLEFIELD, playerA, chalice);
        addCard(Zone.BATTLEFIELD, playerB, worker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, matrix);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, activated);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerB, activated);
        checkLife("gain 1st turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 21);
        checkLife("gain 1st turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, 21);

        setChoice(playerB, CardType.LAND.toString());
        checkPermanentTapped("worker not land doesn't untap", 2, PhaseStep.UPKEEP, playerB, worker, true, 1);
        checkPermanentTapped("chalice untaps", 2, PhaseStep.UPKEEP, playerA, chalice, false, 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, activated);
        checkLife("gain 2nd turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, 22);

        setChoice(playerA, CardType.CREATURE.toString());
        checkPermanentTapped("Wastes didn't untap", 3, PhaseStep.UPKEEP, playerA, "Wastes", true, 3);

        setChoice(playerB, CardType.ARTIFACT.toString());
        checkPermanentTapped("worker artifact does untap", 4, PhaseStep.UPKEEP, playerB, worker, false, 1);
        checkPermanentTapped("chalice untaps", 4, PhaseStep.UPKEEP, playerA, chalice, false, 1);

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, activated);

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 23);
    }
}
