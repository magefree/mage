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
        checkPlayableAbility("Can't cast Titanic", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Titanic", false);

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

    @Test
    public void testGainProtectionByEnchantment() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        // Flying
        // When Brago, King Eternal deals combat damage to a player,
        // exile any number of target nonland permanents you control,
        // then return those cards to the battlefield under their owner's control.
        addCard(Zone.BATTLEFIELD, playerB, "Brago, King Eternal");
        // Enchant creature
        // When Pentarch Ward enters the battlefield, draw a card.
        // As Pentarch Ward enters the battlefield, choose a color.
        // Enchanted creature has protection from the chosen color. This effect doesn't remove Pentarch Ward.
        addCard(Zone.HAND, playerB, "Pentarch Ward"); // "{2}{W}"
        // Enchant creature
        // Enchanted creature gets +1/+1 and has "Whenever this creature attacks, tap target creature defending player controls."
        addCard(Zone.HAND, playerB, "Grasp of the Hieromancer");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Pentarch Ward", "Brago, King Eternal");
        setChoice(playerB, "White");

        checkPlayableAbility("Has protection", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast Grasp", false);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Pentarch Ward", 1);
        assertHandCount(playerB, "Grasp of the Hieromancer", 1);
        assertHandCount(playerB, 3);
    }

    /**
     * Pentarch Ward on Brago naming white.
     * Brago combat trigger resolves blinking Pentarch Ward.
     * Brago retains protection from white even though Pentarch Ward is now exiled,
     * making him unable to be re-enchanted by Pentarch Ward.
     */
    @Test
    public void testGainLooseProtectionByEnchantment() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        // Flying
        // When Brago, King Eternal deals combat damage to a player,
        // exile any number of target nonland permanents you control,
        // then return those cards to the battlefield under their owner's control.
        addCard(Zone.BATTLEFIELD, playerB, "Brago, King Eternal");
        // Enchant creature
        // When Pentarch Ward enters the battlefield, draw a card.
        // As Pentarch Ward enters the battlefield, choose a color.
        // Enchanted creature has protection from the chosen color.
        // This effect doesn't remove Pentarch Ward.
        addCard(Zone.HAND, playerB, "Pentarch Ward"); // "{2}{W}"

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Pentarch Ward", "Brago, King Eternal");
        setChoice(playerB, "White");

        attack(2, playerB, "Brago, King Eternal");
        addTarget(playerB, "Pentarch Ward");
        setChoice(playerB, "Brago, King Eternal");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertPermanentCount(playerB, "Pentarch Ward", 1);
        assertHandCount(playerB, 3);
    }
}
