package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IntermediateChirographyTest extends CardTestPlayerBase {

    /**
     * 3/20/2026 ruling: Intermediate Chirography's level 2 ability triggers just once
     * for your first life-losing event on each turn, no matter how much life you lose.
     */
    @Test
    public void testClassLevelsLoseLifeOnceAndModifiedCreatureDeathCreatesTokenAtEachEndStep() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Intermediate Chirography");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Shock", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intermediate Chirography");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}{B}: Level 2");
        addTarget(playerA, "Memnite");

        castSpell(2, PhaseStep.UPKEEP, playerB, "Shock", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}: Level 3");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", "Memnite");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Inkling Token", 2);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertLife(playerA, 16);
    }

    /**
     * 3/20/2026 ruling: If you lose life during a turn before Intermediate Chirography
     * has its level 2 ability, that ability won't trigger that turn even if you lose
     * life again later in the turn.
     */
    @Test
    public void testLevel2DoesNotTriggerIfLifeWasLostBeforeAbilityGainedThisTurn() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Intermediate Chirography");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Shock", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intermediate Chirography");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}{B}: Level 2");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 16);
        assertPowerToughness(playerA, "Memnite", 1, 1);
    }

    /**
     * 3/20/2026 ruling: Intermediate Chirography's level 3 ability will check as your
     * end step starts to see if any modified creatures died under your control this
     * turn. If none did, the ability won't trigger at all. It's not possible to cause
     * creatures to die during your end step in time to have the ability trigger.
     */
    @Test
    public void testLevel3DoesNotTriggerForModifiedCreatureDyingDuringEndStep() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Intermediate Chirography");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intermediate Chirography");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Level 2");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{B}: Level 3");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        addCounters(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", CounterType.P1P1, 1);

        castSpell(1, PhaseStep.END_TURN, playerB, "Shock", "Memnite");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Inkling Token", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
    }

    /**
     * 3/20/2026 ruling: An Aura controlled by another player does not cause a creature
     * you control to be modified.
     */
    @Test
    public void testOpponentControlledAuraDoesNotMakeCreatureModifiedForLevel3() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Intermediate Chirography");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Pacifism");
        addCard(Zone.HAND, playerB, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intermediate Chirography");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Level 2");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{B}: Level 3");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Pacifism", "Memnite");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", "Memnite");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Inkling Token", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
    }

    /**
     * 3/20/2026 ruling: A creature with a counter on it is considered modified no
     * matter what kind of counter it is or which player put it on that creature.
     */
    @Test
    public void testAnyCounterMakesCreatureModifiedForLevel3() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Intermediate Chirography");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intermediate Chirography");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Level 2");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{B}: Level 3");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        addCounters(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", CounterType.STUN, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", "Memnite");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Inkling Token", 2);
        assertGraveyardCount(playerA, "Memnite", 1);
    }

    /**
     * 3/20/2026 ruling: A creature that is equipped is considered modified no matter
     * who controls the Equipment that's attached to it.
     */
    @Test
    public void testEquipmentControlledByAnotherPlayerStillMakesCreatureModifiedForLevel3() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Intermediate Chirography");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Master Thief");
        addCard(Zone.HAND, playerB, "Gut Shot");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intermediate Chirography");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Level 2");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{B}: Level 3");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip {1}", "Memnite");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Master Thief");
        addTarget(playerB, "Bonesplitter");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Gut Shot", "Memnite");
        setChoice(playerB, true); // Pay 2 life for Gut Shot's Phyrexian mana.

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Inkling Token", 2);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Bonesplitter", 1);
    }
}
