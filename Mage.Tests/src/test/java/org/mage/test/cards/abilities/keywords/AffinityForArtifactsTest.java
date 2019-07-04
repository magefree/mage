package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class AffinityForArtifactsTest extends CardTestPlayerBase {

    @Test
    public void testCastForCheaperCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Myr Enforcer Artifact Creature — Myr  {7} 
        // Affinity for artifacts (This spell costs {1} less to cast for each artifact you control.)
        addCard(Zone.HAND, playerA, "Myr Enforcer");

        addCard(Zone.BATTLEFIELD, playerA, "Myr Enforcer", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Enforcer");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Myr Enforcer", 4);
    }

    /**
     * Tests that cost wasn't reduced too much. 3 Mountains is not enough to cast Myr Enforcer.
     */
    @Test
    public void testCorrectCostReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Myr Enforcer");

        addCard(Zone.BATTLEFIELD, playerA, "Myr Enforcer", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Enforcer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Myr Enforcer", 3);
    }
}
