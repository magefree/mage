
package org.mage.test.cards.single.wth;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ErtaisFamiliarTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.ErtaisFamiliar} <br>
     * Ertai's Familiar {1}{U} <br>
     * Creature — Illusion <br>
     * Phasing <br>
     * When Ertai’s Familiar phases out or leaves the battlefield, mill three cards. <br>
     * {U}: Until your next upkeep, Ertai’s Familiar can’t phase out. <br>
     * 2/2
     */
    private final String familiar = "Ertai's Familiar";

    @Test
    public void test_phaseout() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Casting the familiar for it to not start phasing out turn 1.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 3);
        assertPermanentCount(playerA, familiar, 0);
    }

    @Test
    public void test_activate_cant_phaseout() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, familiar);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Casting the familiar for it to not start phasing out turn 1.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{U}:");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, familiar, 1);
    }

    @Test
    public void test_activate_cant_phaseout_other_effect() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, familiar);
        // Put a +1/+1 counter on target creature. It phases out.
        addCard(Zone.HAND, playerA, "Slip Out the Back");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Casting the familiar for it to not start phasing out turn 1.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar);

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{U}:");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Slip Out the Back", familiar);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 1); // Slip Out the Back
        assertPermanentCount(playerA, familiar, 1);
        assertPowerToughness(playerA, familiar, 3, 3);
    }

    @Test
    public void test_activate_cant_phaseout_then_blink() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, familiar);
        // Exile target creature you control, then return that card to the battlefield under its owner’s control.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Blur");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        // Casting the familiar for it to not start phasing out turn 1.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, familiar);

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{U}:");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Blur", familiar);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 3 * 2 + 1); // 2 familiar trigger + Blur
        assertPermanentCount(playerA, familiar, 0);
    }
}