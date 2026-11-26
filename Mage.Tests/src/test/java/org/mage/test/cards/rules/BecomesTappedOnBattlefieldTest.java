package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 603.2e
 * Some trigger events use the word “becomes” (for example, “becomes attached” or “becomes
 * blocked”). These trigger only at the time the named event happens—they don’t trigger if that
 * state already exists or retrigger if it persists. An ability that triggers when a permanent
 * “becomes tapped” or “becomes untapped” doesn’t trigger if the permanent enters the battlefield
 * in that state.
 *
 * @author JayDi85
 */
public class BecomesTappedOnBattlefieldTest extends CardTestPlayerBase {

    @Test
    public void test_EntersTapped_ShamblingGhoul() {
        // This creature enters tapped.
        addCard(Zone.HAND, playerA, "Shambling Ghoul"); // {1}{B}
        // {T}: Add {B}.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Creatures you control are every creature type.
        addCard(Zone.BATTLEFIELD, playerA, "Maskwood Nexus");
        // Whenever one or more nontoken Merfolk you control become tapped, create a 1/1 blue Merfolk
        // creature token with hexproof.
        addCard(Zone.BATTLEFIELD, playerA, "Deeproot Pilgrimage");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shambling Ghoul");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Shambling Ghoul", true);
        assertPermanentCount(playerA, "Merfolk Token", 0);
    }

    @Test
    public void test_EntersTapped_DenOfTheBugbear() {
        // If you control two or more other lands, this land enters tapped.
        addCard(Zone.HAND, playerA, "Den of the Bugbear");
        // Two other lands are already on the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        // All lands are 1/1 creatures that are still lands.
        addCard(Zone.BATTLEFIELD, playerA, "Living Plane");
        // Creatures you control are every creature type.
        addCard(Zone.BATTLEFIELD, playerA, "Maskwood Nexus");
        // Whenever one or more nontoken Merfolk you control become tapped, create a 1/1 blue Merfolk
        // creature token with hexproof.
        addCard(Zone.BATTLEFIELD, playerA, "Deeproot Pilgrimage");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Den of the Bugbear");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Den of the Bugbear", true);
        assertPermanentCount(playerA, "Merfolk Token", 0);
    }

    @Test
    public void test_EntersTapped_BloodCrypt() {
        // As this land enters, you may pay 2 life. If you don't, it enters tapped.
        addCard(Zone.HAND, playerA, "Blood Crypt");
        //
        // All lands are 1/1 creatures that are still lands.
        addCard(Zone.BATTLEFIELD, playerA, "Living Plane");
        // Creatures you control are every creature type.
        addCard(Zone.BATTLEFIELD, playerA, "Maskwood Nexus");
        // Whenever one or more nontoken Merfolk you control become tapped, create a 1/1 blue Merfolk
        // creature token with hexproof.
        addCard(Zone.BATTLEFIELD, playerA, "Deeproot Pilgrimage");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blood Crypt");
        setChoice(playerA, false); // decline to pay life

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Blood Crypt", true);
        assertPermanentCount(playerA, "Merfolk Token", 0);
    }

    @Test
    public void test_BecomesTappedInUpkeep_Carnophage() {
        // At the beginning of your upkeep, tap this creature unless you pay 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Carnophage");
        //
        // Whenever a creature an opponent controls becomes tapped, if it isn't being declared as an
        // attacker, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Verity Circle");

        setChoice(playerA, false); // decline to pay 1 life
        setChoice(playerB, true); // draw from Verity Circle

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertTapped("Carnophage", true);
        assertHandCount(playerB, 1);
    }
}
