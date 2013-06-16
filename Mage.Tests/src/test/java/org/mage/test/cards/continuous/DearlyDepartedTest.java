package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class DearlyDepartedTest extends CardTestPlayerBase {

    /**
     * Tests that Dearly Departed doesn't give counter while being in hand
     */
    @Test
    public void testDoesntWorkFromHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Dearly Departed");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
    }

    /**
     * Tests works in owner's graveyard
     */
    @Test
    public void testInGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Elite Vanguard");
        addCard(Zone.GRAVEYARD, playerA, "Dearly Departed");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }

    /**
     * Tests doesn't work in opponent's graveyard
     */
    @Test
    public void testInOpponentGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Elite Vanguard");
        addCard(Zone.GRAVEYARD, playerB, "Dearly Departed");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
    }

}