package org.mage.test.cards.single.trk;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DeathIsIrrelevantTest extends CardTestPlayerBase {

    @Test
    public void test_ArtifactCreaturesYouControlHaveIndestructible_OthersDoNot() {
        // Artifact creatures you control have indestructible.
        addCard(Zone.BATTLEFIELD, playerA, "Death Is Irrelevant");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // artifact creature 0/2
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // non-artifact creature 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring"); // non-creature artifact
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // opponent's artifact creature 1/1
        addCustomEffect_TargetDestroy(playerB);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "Ornithopter"); // try to destroy playerA's artifact creature
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "Grizzly Bears"); // destroy playerA's non-artifact creature
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "Sol Ring"); // destroy playerA's non-creature artifact
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "Memnite"); // destroy playerB's own artifact creature

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ornithopter", 1);
        assertAbility(playerA, "Ornithopter", IndestructibleAbility.getInstance(), true);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Sol Ring", 0);
        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertPermanentCount(playerB, "Memnite", 0);
        assertGraveyardCount(playerB, "Memnite", 1);
    }

    @Test
    public void test_EndStep_AssimilatesCreatureFromOwnGraveyard_GainsIndestructible() {
        // Artifact creatures you control have indestructible.
        // At the beginning of your end step, assimilate target creature card from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Death Is Irrelevant");
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // 2/2 Bear

        addTarget(playerA, "Grizzly Bears"); // target creature card in your graveyard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        assertAbility(playerA, "Grizzly Bears", IndestructibleAbility.getInstance(), true);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_EndStep_AssimilatesCreatureFromOpponentsGraveyard() {
        // Artifact creatures you control have indestructible.
        // At the beginning of your end step, assimilate target creature card from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Death Is Irrelevant");
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear

        addTarget(playerA, "Grizzly Bears"); // target creature card in opponent's graveyard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 0);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        assertAbility(playerA, "Grizzly Bears", IndestructibleAbility.getInstance(), true);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_OpponentsEndStep_DoesNotTrigger() {
        // At the beginning of your end step, assimilate target creature card from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Death Is Irrelevant");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCustomEffect_TargetDestroy(playerB);

        // Turn 1 has no creature in any graveyard, then turn 2 opponent kills their own bear before their end step
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "Grizzly Bears");
        checkStackSize("must not trigger on opponent's end step", 2, PhaseStep.END_TURN, playerB, 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_EndStep_NoncreatureCardInGraveyard_NotTargeted() {
        // At the beginning of your end step, assimilate target creature card from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Death Is Irrelevant");
        //
        addCard(Zone.GRAVEYARD, playerB, "Sol Ring"); // noncreature artifact card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, "Sol Ring", 1);
        assertPermanentCount(playerA, "Sol Ring", 0);
    }

    @Test
    public void test_LeavesBattlefield_AssimilatedCreatureLosesIndestructibleButRemainsBorgArtifact() {
        // Artifact creatures you control have indestructible.
        // At the beginning of your end step, assimilate target creature card from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Death Is Irrelevant");
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCustomEffect_TargetDestroy(playerB);

        addTarget(playerA, "Grizzly Bears"); // assimilate at turn 1 end step

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "Death Is Irrelevant");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Death Is Irrelevant", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        // keep assimilated traits
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        // lost static indestructible when enchantment left
        assertAbility(playerA, "Grizzly Bears", IndestructibleAbility.getInstance(), false);
    }
}
