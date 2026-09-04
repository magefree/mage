package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TyvarTheBellicoseTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Llanowar Elves", 2, 2);
    }

    @Test
    public void testGrantedAbility() {
        // Tyvar starts as a 5/4
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");

        // Enduring Vitality grants "{T}: Add one mana of any color." to all creatures you control
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Vitality");

        // The ability prompts the user to pick a color of mana to add.
        setChoice(playerA, "Green");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add one mana of any color");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Tyvar should receive a +1/+1 counter because he successfully produced 1 mana.
        assertPowerToughness(playerA, "Tyvar the Bellicose", 6, 5);
    }

    @Test
    public void testSelfSacrificingManaDork() {
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");
        addCard(Zone.BATTLEFIELD, playerA, "Blood Pet");

        // Fix: Use the exact string XMage registers for the ability
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Add {B}.");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Blood Pet", 1);
        assertPowerToughness(playerA, "Tyvar the Bellicose", 5, 4);
    }

    @Test
    public void testNonManaAbilityProducingMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");
        addCard(Zone.BATTLEFIELD, playerA, "Radha, Heir to Keld");

        attack(1, playerA, "Radha, Heir to Keld");

        // Fix: Order the simultaneous triggers on the stack
        setChoice(playerA, "Whenever {this} attacks, you may add {R}{R}.");

        // Fix: Choose "Yes" for Radha's "may" ability
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Radha, Heir to Keld", 2, 2);
    }

    @Test
    public void testTriggersOnlyOnceEachTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Twiddle");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twiddle", "Llanowar Elves");

        // Fix: Answer "Yes" to the Twiddle prompt asking "Untap that permanent?"
        setChoice(playerA, true);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Llanowar Elves", 2, 2);
    }
}