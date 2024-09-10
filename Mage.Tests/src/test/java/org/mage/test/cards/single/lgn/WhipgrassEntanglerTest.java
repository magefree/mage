package org.mage.test.cards.single.lgn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WhipgrassEntanglerTest extends CardTestPlayerBase {

    /**
     * Whipgrass Entangler
     * {2}{W}
     * Creature — Human Cleric
     * <p>
     * {1}{W}: Until end of turn, target creature gains "This creature can't attack or block unless its controller pays {1} for each Cleric on the battlefield."
     * <p>
     * 1/3
     */
    private static final String entangler = "Whipgrass Entangler";

    @Test
    public void gainAbilityHimselfAndPay() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, entangler);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 20);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", entangler);
        attack(1, playerA, entangler);
        setChoice(playerA, true); // yes to "pay {1} for Entangler to attack"

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1);
        assertTappedCount("Plains", true, 2 * 1 + 1 * 1);
    }

    @Test
    public void gainAbilityTwiceAndPay() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, entangler);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 20);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", entangler);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", entangler);
        attack(1, playerA, entangler);
        setChoice(playerA, "Whipgrass Entangler"); // two of the same replacement effect to choose from.
        setChoice(playerA, true); // yes to "pay {1} for Entangler to attack"
        setChoice(playerA, true); // yes to "pay {1} for Entangler to attack"

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1);
        assertTappedCount("Plains", true, 2 * 2 + 1 * 2);
    }

    @Test
    public void gainAbilityWithTwoClericAndPay() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, entangler);
        addCard(Zone.BATTLEFIELD, playerA, "Akroma's Devoted"); // 2/4 Cleric
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 20);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", entangler);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", "Akroma's Devoted");
        attack(1, playerA, entangler);
        setChoice(playerA, true); // yes to "pay {2} for Entangler to attack"
        attack(1, playerA, "Akroma's Devoted");
        setChoice(playerA, true); // yes to "pay {2} for Akroma's Devoted to attack"

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1 - 2);
        assertTappedCount("Plains", true, 2 * 2 + 2 * 2);
    }

    @Test
    public void gainAbilityFromTwoEntanglerAndPay() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Akroma's Devoted"); // 2/4 Cleric
        addCard(Zone.BATTLEFIELD, playerA, entangler, 1);

        // In order to make sure the ability is activated from both entanglers, we exile one and play the second one after
        addCard(Zone.HAND, playerA, entangler);
        addCard(Zone.HAND, playerA, "Swords to Plowshares", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 20);

        // Activate first entangler
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", "Akroma's Devoted");
        // Exile first entangler
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", entangler, true);

        // Cast second entangler
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, entangler, true);
        // Activate second entangler
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", "Akroma's Devoted");

        attack(1, playerA, "Akroma's Devoted");
        setChoice(playerA, "Akroma's Devoted"); // two of the same replacement effect to choose from.
        setChoice(playerA, true); // yes to "pay {2} for Akroma's Devoted to attack"
        setChoice(playerA, true); // yes to "pay {2} for Akroma's Devoted to attack"

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertTappedCount("Plains", true, 2 * 2 + 2 * 2 + 3 + 1);
    }

    @Test
    public void gainAbilityWithCloneAndPay() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Akroma's Devoted"); // 2/4 Cleric
        addCard(Zone.BATTLEFIELD, playerA, entangler);
        addCard(Zone.HAND, playerA, "Sakashima the Impostor"); // Clone with different name for easy targets

        // In order to make sure the ability is activated from the clone, we exile the original one before activating Sakashima
        addCard(Zone.HAND, playerA, "Swords to Plowshares", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 20);

        // Activate the original Entangler
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", "Akroma's Devoted");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // Cast Sakashima
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sakashima the Impostor", true);
        setChoice(playerA, true); // yes to clone.
        setChoice(playerA, entangler); // copy entangler
        // Exile original entangler
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", entangler, true);
        // Activate Sakashima.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Until end of turn, ", "Akroma's Devoted");

        attack(1, playerA, "Akroma's Devoted");
        setChoice(playerA, "Akroma's Devoted"); // two of the same replacement effect to choose from.
        setChoice(playerA, true); // yes to "pay {2} for Akroma's Devoted to attack"
        setChoice(playerA, true); // yes to "pay {2} for Akroma's Devoted to attack"

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertTappedCount("Tundra", true, 2 * 2 + 2 * 2 + 4 + 1);
    }
}
