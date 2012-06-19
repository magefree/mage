package org.mage.test.cards.abilities.keywords;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class AffinityForArtifactsTest extends CardTestPlayerBase {

    @Test
    public void testCastForCheaperCost() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Constants.Zone.HAND, playerA, "Myr Enforcer");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Myr Enforcer", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Enforcer");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

       assertPermanentCount(playerA, "Myr Enforcer", 4);
    }

    /**
     * Tests that cost wasn't reduced too much. 3 Mountains is not enough to cast Myr Enforcer.
     */
    @Test
    public void testCorrectCostReduction() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerA, "Myr Enforcer");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Myr Enforcer", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Enforcer");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Myr Enforcer", 3);
    }
}
