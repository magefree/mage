package org.mage.test.cards.abilities.keywords;

import mage.Constants;
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
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Geralf's Messenger");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Constants.Zone.HAND, playerA, "Last Gasp");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Geralf's Messenger");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Geralf's Messenger", 1);
        assertPowerToughness(playerA, "Geralf's Messenger", 4, 3);
    }

    /**
     * Tests "Target creature gains undying until end of turn"
     */
    @Test
    public void testUndyingEvil() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Constants.Zone.HAND, playerA, "Last Gasp");
        addCard(Constants.Zone.HAND, playerA, "Undying Evil");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Elite Vanguard");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Undying Evil", "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }

}
