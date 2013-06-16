package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class UndyingTest extends CardTestPlayerBase {

    /**
     * Tests boost weren't be applied second time when creature back to battlefield
     */
    @Test
    public void testWithBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Geralf's Messenger");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Last Gasp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Geralf's Messenger");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Geralf's Messenger", 1);
        assertPowerToughness(playerA, "Geralf's Messenger", 4, 3);
    }

    /**
     * Tests boost weren't be applied second time when creature back to battlefield
     */
    @Test
    public void testWithMassBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Strangleroot Geist");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Cower in Fear");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cower in Fear");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Strangleroot Geist", 1);
        // dies then returned with +1/+1 counter (and boost doesn't work anymore)
        assertPowerToughness(playerA, "Strangleroot Geist", 3, 2);
    }

    /**
     * Tests "Target creature gains undying until end of turn"
     */
    @Test
    public void testUndyingEvil() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Last Gasp");
        addCard(Zone.HAND, playerA, "Undying Evil");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Elite Vanguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Undying Evil", "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }

}
