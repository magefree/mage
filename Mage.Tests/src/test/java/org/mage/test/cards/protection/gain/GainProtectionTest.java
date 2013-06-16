package org.mage.test.cards.protection.gain;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class GainProtectionTest extends CardTestPlayerBase {

    @Test
    public void testGainProtectionFromSpellColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Apostle's Blessing");
        addCard(Zone.HAND, playerA, "Titanic Growth");

        setChoice(playerA, "Green");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apostle's Blessing", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Titanic Growth", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
    }

    @Test
    public void testGainProtectionFromAnotherColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Apostle's Blessing");
        addCard(Zone.HAND, playerA, "Titanic Growth");

        setChoice(playerA, "Black");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apostle's Blessing", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Titanic Growth", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 6, 5);
    }

    @Test
    public void testGainProtectionFromArtifacts() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Apostle's Blessing");
        addCard(Zone.HAND, playerA, "Titanic Growth");

        setChoice(playerA, "Artifacts");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apostle's Blessing", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Titanic Growth", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 6, 5);
    }

}
