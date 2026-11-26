package org.mage.test.cards.single.trc;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author JayDi85
 */
public class AssimilateTheWoundedTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void test_Multiplayer_AssimilatesUpToOneCreaturePerOpponent() {
        // For each opponent, assimilate up to one target creature card from that player's graveyard
        // that was put there from the battlefield this turn.
        addCard(Zone.HAND, playerA, "Assimilate the Wounded"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCustomEffect_TargetDestroy(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2 Cat, second dead creature for playerB
        addCard(Zone.BATTLEFIELD, playerC, "Hill Giant"); // 3/3 Giant

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Hill Giant");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilate the Wounded");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerD has no eligible creature
        addTarget(playerA, "Hill Giant"); // playerC's dead creature
        addTarget(playerA, "Grizzly Bears"); // up to one from playerB's graveyard (Silvercoat Lion stays)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 0);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertGraveyardCount(playerC, "Hill Giant", 0);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        assertType("Hill Giant", CardType.ARTIFACT, true);
        assertType("Hill Giant", CardType.CREATURE, true);
        assertSubtype("Hill Giant", SubType.BORG);
        assertCounterCount(playerA, "Hill Giant", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Hill Giant", 3 + 1, 3 + 1);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
        assertNotSubtype("Hill Giant", SubType.GIANT);
    }

    @Test
    public void test_Multiplayer_OneTargetExiledInResponse_StillAssimilatesRemainingTarget() {
        // For each opponent, assimilate up to one target creature card from that player's graveyard
        // that was put there from the battlefield this turn.
        addCard(Zone.HAND, playerA, "Assimilate the Wounded"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCustomEffect_TargetDestroy(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear
        // Exile target card from a graveyard.
        addCard(Zone.HAND, playerB, "Coffin Purge"); // {B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Hill Giant"); // 3/3 Giant

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Hill Giant");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilate the Wounded");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerD has no eligible creature
        addTarget(playerA, "Hill Giant"); // playerC's dead creature
        addTarget(playerA, "Grizzly Bears"); // playerB's dead creature
        // In response, playerB exiles Grizzly Bears so it becomes an illegal target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Coffin Purge", "Grizzly Bears", "Assimilate the Wounded");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertType("Hill Giant", CardType.ARTIFACT, true);
        assertSubtype("Hill Giant", SubType.BORG);
        assertCounterCount(playerA, "Hill Giant", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Hill Giant", 3 + 1, 3 + 1);
    }

    @Test
    public void test_CastOnOpponentsTurn_AssimilatesDeadCreature() {
        // For each opponent, assimilate up to one target creature card from that player's graveyard
        // that was put there from the battlefield this turn.
        addCard(Zone.HAND, playerA, "Assimilate the Wounded"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears"); // 2/2 Bear
        addCustomEffect_TargetDestroy(playerD);

        // On turn 2 (playerD's turn), playerD's Grizzly Bears dies and playerA casts Assimilate the Wounded at instant speed
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerD, "target destroy", "Grizzly Bears");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilate the Wounded");
        addTarget(playerA, "Grizzly Bears"); // playerD's dead creature
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerC
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerB

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerD, "Grizzly Bears", 0);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_DoubleFacedCreatureDiedThisTurn_AssimilatesFrontFace() {
        // For each opponent, assimilate up to one target creature card from that player's graveyard
        // that was put there from the battlefield this turn.
        addCard(Zone.HAND, playerA, "Assimilate the Wounded"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCustomEffect_TargetDestroy(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Delver of Secrets"); // TDFC 1/1 Human Wizard

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Delver of Secrets");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilate the Wounded");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerD
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerC
        addTarget(playerA, "Delver of Secrets"); // playerB's dead TDFC

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Delver of Secrets", 1);
        assertGraveyardCount(playerB, "Delver of Secrets", 0);
        // gain
        assertType("Delver of Secrets", CardType.ARTIFACT, true);
        assertType("Delver of Secrets", CardType.CREATURE, true);
        assertSubtype("Delver of Secrets", SubType.BORG);
        assertCounterCount(playerA, "Delver of Secrets", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Delver of Secrets", 1 + 1, 1 + 1);
        // lost
        assertNotSubtype("Delver of Secrets", SubType.HUMAN);
        assertNotSubtype("Delver of Secrets", SubType.WIZARD);
    }

    @Test
    public void test_NoEligibleTargets_CanStillBeCastWithZeroTargets() {
        // For each opponent, assimilate up to one target creature card from that player's graveyard
        // that was put there from the battlefield this turn.
        addCard(Zone.HAND, playerA, "Assimilate the Wounded"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilate the Wounded");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerD: choose 0 targets
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerC: choose 0 targets
        addTarget(playerA, TestPlayer.TARGET_SKIP); // playerB: choose 0 targets

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Assimilate the Wounded", 1);
    }
}
