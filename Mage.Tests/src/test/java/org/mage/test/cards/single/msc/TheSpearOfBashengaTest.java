package org.mage.test.cards.single.msc;

import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class TheSpearOfBashengaTest extends CardTestPlayerBase {

    // When The Spear of Bashenga enters, if there is no monarch, you become the monarch.
    // Equipped creature gets +2/+2 and has vigilance.
    // Whenever equipped creature attacks the monarch, destroy target tapped nonland permanent that player controls.
    // Equip {2}
    private static final String spear = "The Spear of Bashenga";

    // When Crown-Hunter Hireling enters, you become the monarch.
    private static final String hireling = "Crown-Hunter Hireling"; // {4}{R}
    private static final String mindStone = "Mind Stone"; // {T}: Add {C}.
    private static final String solRing = "Sol Ring"; // {T}: Add {C}{C}.
    private static final String chandra = "Chandra Ablaze"; // planeswalker, loyalty 5

    private static final String lion = "Silvercoat Lion"; // 2/2

    @Test
    public void test_Enters_NoMonarch_BecomeMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, spear);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spear);
        checkMonarch("A is the monarch", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, spear, 1);
    }

    @Test
    public void test_Enters_AlreadyMonarch_MonarchDoesNotChange() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, spear);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerB, hireling);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, hireling);
        checkMonarch("B is the monarch", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, playerB);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, spear);
        checkMonarch("B is still the monarch", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, playerB);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, spear, 1);
    }

    @Test
    public void test_EquippedCreatureGetsBoostAndVigilance() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, spear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        attack(1, playerA, lion, playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, lion, 2 + 2, 2 + 2);
        assertAbility(playerA, lion, VigilanceAbility.getInstance(), true);
        assertTapped(lion, false);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_AttacksMonarch_DestroyTappedNonlandPermanent() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, spear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerB, hireling);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerB, mindStone); // tapped to cast hireling

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, hireling);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        attack(3, playerA, lion, playerB);
        addTarget(playerA, mindStone);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, mindStone, 1);
        assertPermanentCount(playerB, hireling, 1);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_AttacksNonMonarch_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, spear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5 + 2);
        addCard(Zone.BATTLEFIELD, playerB, mindStone, 1, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spear, true);
        checkMonarch("A is the monarch", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        attack(1, playerA, lion, playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, mindStone, 1);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_AttacksPlaneswalkerOfMonarch_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, spear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerB, hireling);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerB, mindStone); // tapped to cast hireling
        addCard(Zone.BATTLEFIELD, playerB, chandra);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, hireling);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        attack(3, playerA, lion, chandra);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, mindStone, 1);
        assertCounterCount(playerB, chandra, CounterType.LOYALTY, 5 - 4);
        assertLife(playerB, 20);
    }

    @Test
    public void test_AttacksMonarch_CantTargetLandsUntappedOrOwnPermanents() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, spear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, solRing); // tapped to pay equip
        addCard(Zone.HAND, playerB, hireling);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5); // tapped to cast hireling

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, hireling);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);
        attack(3, playerA, lion, playerB);
        // no legal targets: tapped lands, untapped hireling and own tapped Sol Ring

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped(solRing, true);
        assertTappedCount("Mountain", true, 5);
        assertPermanentCount(playerA, solRing, 1);
        assertPermanentCount(playerB, hireling, 1);
        assertPermanentCount(playerB, "Mountain", 5);
        assertLife(playerB, 20 - 4);
    }
}
