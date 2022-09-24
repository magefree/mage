package org.mage.test.cards.planeswalker;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class VivienTest extends CardTestPlayerBase {

    @Test
    public void testVivienArkbowRangerAbility1NoTargets() {
        setStrictChooseMode(true);
        // +1: Distribute two +1/+1 counters among up to two target creatures. They gain trample until end of turn.
        // −3: Target creature you control deals damage equal to its power to target creature or planeswalker.
        // −5: You may choose a creature card you own from outside the game, reveal it, and put it into your hand.
        addCard(Zone.HAND, playerA, "Vivien, Arkbow Ranger"); // Planeswalker {1}{G}{G}{G} - starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vivien, Arkbow Ranger", true);
        addTargetAmount(playerA, TestPlayer.TARGET_SKIP); // stop choosing (not targets)

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Distribute");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vivien, Arkbow Ranger", 1);
        assertCounterCount("Vivien, Arkbow Ranger", CounterType.LOYALTY, 5);

    }

    @Test
    public void testVivienArkbowRangerAbilityOnePossibleTargetWithOne() {
        setStrictChooseMode(true);
        // +1: Distribute two +1/+1 counters among up to two target creatures. They gain trample until end of turn.
        // −3: Target creature you control deals damage equal to its power to target creature or planeswalker.
        // −5: You may choose a creature card you own from outside the game, reveal it, and put it into your hand.
        addCard(Zone.HAND, playerA, "Vivien, Arkbow Ranger"); // Planeswalker {1}{G}{G}{G} - starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vivien, Arkbow Ranger", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Distribute");
        addTargetAmount(playerA, "Silvercoat Lion", 1);
        addTargetAmount(playerA, TestPlayer.TARGET_SKIP); // stop choosing (one target)

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vivien, Arkbow Ranger", 1);
        assertCounterCount("Vivien, Arkbow Ranger", CounterType.LOYALTY, 5);

        assertPowerToughness(playerB, "Silvercoat Lion", 2 + 1, 2 + 1);
    }

    @Test
    public void testVivienArkbowRangerAbilityOnePossibleTargetWithTwo() {
        setStrictChooseMode(true);
        // +1: Distribute two +1/+1 counters among up to two target creatures. They gain trample until end of turn.
        // −3: Target creature you control deals damage equal to its power to target creature or planeswalker.
        // −5: You may choose a creature card you own from outside the game, reveal it, and put it into your hand.
        addCard(Zone.HAND, playerA, "Vivien, Arkbow Ranger"); // Planeswalker {1}{G}{G}{G} - starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vivien, Arkbow Ranger", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Distribute");
        addTargetAmount(playerA, "Silvercoat Lion", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vivien, Arkbow Ranger", 1);
        assertCounterCount("Vivien, Arkbow Ranger", CounterType.LOYALTY, 5);

        assertPowerToughness(playerB, "Silvercoat Lion", 2 + 2, 2 + 2);
    }

    @Test
    public void testVivienArkbowRangerAbility1OneOwnPossibleTarget() {
        // +1: Distribute two +1/+1 counters among up to two target creatures. They gain trample until end of turn.
        // −3: Target creature you control deals damage equal to its power to target creature or planeswalker.
        // −5: You may choose a creature card you own from outside the game, reveal it, and put it into your hand.
        addCard(Zone.HAND, playerA, "Vivien, Arkbow Ranger"); // Planeswalker {1}{G}{G}{G} - starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vivien, Arkbow Ranger", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Distribute");
        addTargetAmount(playerA, "Silvercoat Lion", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vivien, Arkbow Ranger", 1);
        assertCounterCount("Vivien, Arkbow Ranger", CounterType.LOYALTY, 5);

        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);

    }

    @Test
    public void testVivienArkbowRangerAbility1TwoOwnPossibleTarget() {
        // +1: Distribute two +1/+1 counters among up to two target creatures. They gain trample until end of turn.
        // −3: Target creature you control deals damage equal to its power to target creature or planeswalker.
        // −5: You may choose a creature card you own from outside the game, reveal it, and put it into your hand.
        addCard(Zone.HAND, playerA, "Vivien, Arkbow Ranger"); // Planeswalker {1}{G}{G}{G} - starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vivien, Arkbow Ranger", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Distribute");
        addTargetAmount(playerA, "Silvercoat Lion", 1);
        addTargetAmount(playerA, "Pillarfield Ox", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vivien, Arkbow Ranger", 1);
        assertCounterCount("Vivien, Arkbow Ranger", CounterType.LOYALTY, 5);

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertPowerToughness(playerA, "Pillarfield Ox", 3, 5);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);
        assertAbility(playerA, "Pillarfield Ox", TrampleAbility.getInstance(), true);
    }
}
