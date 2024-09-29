package org.mage.test.cards.single.mkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CovetedFalconTest extends CardTestPlayerBase {

    /*
     * Coveted Falcon {1}{U}{U}
     * Artifact Creature - Bird (1/4)
     * Flying
     * Whenever Coveted Falcon attacks, gain control of target permanent you own but don't control.
     * Disguise {1}{U}
     * When Coveted Falcon is turned face up, target opponent gains control of any number of target permanents you control.
     * Draw a card for each one they gained control of this way.
     */

    @Test
    public void test_GiveControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Coveted Falcon", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coveted Falcon using Disguise", true);

        // When Coveted Falcon is turned face up, target opponent gains control of any number of target permanents you control.
        // Draw a card for each one they gained control of this way.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}: Turn this face-down permanent face up.");
        addTarget(playerA, playerB);
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_TargetChangesControllerInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Coveted Falcon", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        // Turn Against {4}{R}
        // Instant
        // Devoid
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        addCard(Zone.HAND, playerB, "Turn Against", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coveted Falcon using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}: Turn this face-down permanent face up.");
        addTarget(playerA, playerB);
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Turn Against", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_TargetLeavesAndReturnsUnderYourControlInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Goblin", 1); // 2/2 Zombie Goblin w/ Persist
        addCard(Zone.HAND, playerA, "Coveted Falcon", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Murder", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coveted Falcon using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}: Turn this face-down permanent face up.");
        addTarget(playerA, playerB);
        addTarget(playerA, "Putrid Goblin");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder", "Putrid Goblin");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Putrid Goblin", 0);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_TargetLeavesAndReturnsUnderOpponentsControlInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 2);
        // Treacherous Pit-Dweller {B}{B}
        // Creature - Demon (4/3)
        // When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.
        // Undying
        addCard(Zone.BATTLEFIELD, playerA, "Treacherous Pit-Dweller", 1);
        addCard(Zone.HAND, playerA, "Coveted Falcon", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Murder", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coveted Falcon using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}: Turn this face-down permanent face up.");
        addTarget(playerA, playerB);
        addTarget(playerA, "Treacherous Pit-Dweller");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder", "Treacherous Pit-Dweller");
        // When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Treacherous Pit-Dweller", 1);
        assertHandCount(playerA, 0);
    }

    /*
     * Guardian Beast {3}{B}
     * Creature - Beast (2/4)
     * As long as Guardian Beast is untapped, noncreature artifacts you control can't be enchanted,
     * they have indestructible, and other players can't gain control of them.
     * This effect doesn't remove Auras already attached to those artifacts.
     */

    // When you turn Coveted Falcon face up while controlling Guardian Beast, you can target noncreature artifacts,
    // but your opponent shouldn't gain control of them, and you shouldn't draw cards for them.
    // If Guardian Beast dies after Falcon's turn-up trigger resolves, you should still keep those artifacts.
    @Test
    public void test_GiveArtifactAndNonartifactWhileControllingGuardianBeast() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Guardian Beast", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic", 1); // Artifact w/ Indestructible
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // Should be given away normally
        addCard(Zone.HAND, playerA, "Coveted Falcon", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Murder", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coveted Falcon using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}: Turn this face-down permanent face up.");
        addTarget(playerA, playerB);
        addTarget(playerA, "Darksteel Relic^Grizzly Bears");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Murder", "Guardian Beast");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Darksteel Relic", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, 1);
    }


    // If you target Guardian Beast AND one or more noncreature artifacts to give away, you should only give away the Beast.
    // Test is duplicated to catch glitches that only occur when the targets are ordered a certain way.
    // TODO Doesn't work properly
    @Test
    @Ignore
    public void test_GiveGuardianBeastAndArtifactsA() {
        setupGiveGuardianBeastAndArtifactsTest(true);
    }

    @Test
    @Ignore
    public void test_GiveGuardianBeastAndArtifactsB() {
        setupGiveGuardianBeastAndArtifactsTest(false);
    }

    private void setupGiveGuardianBeastAndArtifactsTest(final boolean guardianBeastFirst) {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Guardian Beast", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic", 1);
        addCard(Zone.HAND, playerA, "Coveted Falcon", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coveted Falcon using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}: Turn this face-down permanent face up.");
        addTarget(playerA, playerB);
        addTarget(playerA, guardianBeastFirst ? "Guardian Beast^Darksteel Relic" : "Darksteel Relic^Guardian Beast");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Guardian Beast", 1);
        assertPermanentCount(playerB, "Darksteel Relic", 0);
        assertHandCount(playerA, 1);
    }
}
