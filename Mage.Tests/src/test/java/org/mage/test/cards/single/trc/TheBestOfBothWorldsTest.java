package org.mage.test.cards.single.trc;

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
public class TheBestOfBothWorldsTest extends CardTestPlayerBase {

    @Test
    public void test_AllThreeChapters_AssimilateBoostThenExileAndReturnToOwner() {
        // I — Assimilate target nonartifact creature an opponent controls.
        // II — Artifact creatures you control get +2/+2 until end of turn.
        // III — Exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // artifact creature 0/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // non-artifact creature 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear

        // Turn 1: Chapter I assimilates Grizzly Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Grizzly Bears"); // Chapter I target

        checkPermanentCount("Chapter I: Grizzly Bears under playerA", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", 1);
        checkType("Chapter I: becomes Artifact", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", CardType.ARTIFACT, true);
        checkSubType("Chapter I: becomes Borg", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", SubType.BORG, true);
        checkSubType("Chapter I: loses Bear", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", SubType.BEAR, false);
        checkPermanentCounters("Chapter I: gets +1/+1 counter", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", CounterType.P1P1, 1);
        checkPT("Chapter I: 3/3", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", 2 + 1, 2 + 1);

        // Turn 3: Chapter II boosts artifact creatures you control (+2/+2 until end of turn)
        checkPT("Chapter II: Grizzly Bears boosted to 5/5", 3, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", 2 + 1 + 2, 2 + 1 + 2);
        checkPT("Chapter II: Ornithopter boosted to 2/4", 3, PhaseStep.BEGIN_COMBAT, playerA, "Ornithopter", 0 + 2, 2 + 2);
        checkPT("Chapter II: Silvercoat Lion not boosted", 3, PhaseStep.BEGIN_COMBAT, playerA, "Silvercoat Lion", 2, 2);

        // Turn 5: Chapter III exiles Grizzly Bears and returns it under playerB's control as a fresh 2/2 Bear
        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Best of Both Worlds", 0);
        assertGraveyardCount(playerA, "The Best of Both Worlds", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertType("Grizzly Bears", CardType.ARTIFACT, false);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BEAR);
        assertNotSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerB, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_ChapterI_CannotTargetArtifactCreatureOrOwnCreature() {
        // I — Assimilate target nonartifact creature an opponent controls.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // own nonartifact creature (not opponent's)
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // opponent's artifact creature (not nonartifact)

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Best of Both Worlds", 1);
        assertPermanentCount(playerB, "Memnite", 1);
        assertCounterCount(playerB, "Memnite", CounterType.P1P1, 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertType("Grizzly Bears", CardType.ARTIFACT, false);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
    }

    @Test
    public void test_ChapterI_CopiedAssimilatesTwoCreatures_ChapterIIIReturnsBoth() {
        // I — Assimilate target nonartifact creature an opponent controls.
        // III — Exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Strionic Resonator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3 Giant

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3); // pay The Best of Both Worlds
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Grizzly Bears"); // original Chapter I target
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true); // wait for Saga spell to enter and put Chapter I on stack

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 2); // pay Strionic Resonator
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Copy target triggered", "stack ability (I - Assimilate");
        setChoice(playerA, true); // choose new targets for copy -> yes
        addTarget(playerA, "Hill Giant"); // copy targets Hill Giant

        checkPermanentCount("both assimilated on turn 1 - Grizzly Bears", 1, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", 1);
        checkPermanentCount("both assimilated on turn 1 - Hill Giant", 1, PhaseStep.BEGIN_COMBAT, playerA, "Hill Giant", 1);

        // Turn 5: Chapter III exiles BOTH assimilated creatures and returns BOTH under playerB's control
        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Hill Giant", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Hill Giant", 1);
        assertType("Grizzly Bears", CardType.ARTIFACT, false);
        assertSubtype("Grizzly Bears", SubType.BEAR);
        assertCounterCount(playerB, "Grizzly Bears", CounterType.P1P1, 0);
        assertType("Hill Giant", CardType.ARTIFACT, false);
        assertSubtype("Hill Giant", SubType.GIANT);
        assertCounterCount(playerB, "Hill Giant", CounterType.P1P1, 0);
    }

    @Test
    public void test_ChapterIII_StillExilesAndReturnsIfNoLongerBorgOrControlledByYou() {
        // I — Assimilate target nonartifact creature an opponent controls.
        // III — Exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear
        // You control enchanted creature.
        addCard(Zone.HAND, playerB, "Control Magic"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        // Until end of turn, target creature loses all abilities and becomes a blue Frog with base power and toughness 1/1.
        addCard(Zone.HAND, playerB, "Turn to Frog"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        // Turn 1: Chapter I assimilates Grizzly Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Grizzly Bears");

        // Turn 2: playerB regains control of Grizzly Bears with Control Magic
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {U}", 4);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Control Magic", "Grizzly Bears");

        // Turn 5: in response to Chapter III (already on the stack at start of precombat main), turn Grizzly Bears into a non-Borg Frog
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Frog", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        // Chapter III still exiled Grizzly Bears (sending Control Magic to graveyard) and returned it as a fresh 2/2 Bear
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Control Magic", 1);
        assertPermanentCount(playerB, "Control Magic", 0);
        assertType("Grizzly Bears", CardType.ARTIFACT, false);
        assertSubtype("Grizzly Bears", SubType.BEAR);
        assertNotSubtype("Grizzly Bears", SubType.BORG);
        assertNotSubtype("Grizzly Bears", SubType.FROG);
        assertCounterCount(playerB, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_ChapterIII_AssimilatedTokenExiledAndDoesNotReturn() {
        // I — Assimilate target nonartifact creature an opponent controls.
        // III — Exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerB, "Raise the Alarm"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Raise the Alarm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Soldier Token"); // Chapter I assimilates one Soldier token

        checkPermanentCount("Chapter I: Soldier token under playerA", 1, PhaseStep.BEGIN_COMBAT, playerA, "Soldier Token", 1);
        checkType("Chapter I: token becomes Artifact", 1, PhaseStep.BEGIN_COMBAT, playerA, "Soldier Token", CardType.ARTIFACT, true);
        checkSubType("Chapter I: token becomes Borg", 1, PhaseStep.BEGIN_COMBAT, playerA, "Soldier Token", SubType.BORG, true);
        checkSubType("Chapter I: token loses Soldier", 1, PhaseStep.BEGIN_COMBAT, playerA, "Soldier Token", SubType.SOLDIER, false);

        // Turn 5: Chapter III exiles the token (ceases to exist and does not return)
        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 0);
        assertPermanentCount(playerB, "Soldier Token", 1); // only the other unassimilated Soldier token remains
    }

    @Test
    public void test_ChapterIII_AssimilatedCreatureBlinkedEarlier_NotExiledAgain() {
        // I — Assimilate target nonartifact creature an opponent controls.
        // III — Exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3); // pay The Best of Both Worlds
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cloudshift makes Grizzly Bears a new object under playerA's control, breaking the link to Chapter I
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears");

        // Turn 5: Chapter III must NOT exile or return the blinked Grizzly Bears to playerB
        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertType("Grizzly Bears", CardType.ARTIFACT, false);
        assertSubtype("Grizzly Bears", SubType.BEAR);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
    }

    @Test
    public void test_ChapterIII_AssimilatedDoubleFacedCreature_ReturnsFrontFaceUnderOwnersControl() {
        // I — Assimilate target nonartifact creature an opponent controls.
        // III — Exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Kessig Prowler"); // TDFC 2/1 Werewolf Horror

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Kessig Prowler");

        checkPermanentCount("Chapter I: Kessig Prowler under playerA", 1, PhaseStep.BEGIN_COMBAT, playerA, "Kessig Prowler", 1);

        // Turn 5: Chapter III exiles and returns Kessig Prowler under playerB's control
        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kessig Prowler", 0);
        assertPermanentCount(playerB, "Kessig Prowler", 1);
        assertType("Kessig Prowler", CardType.ARTIFACT, false);
        assertSubtype("Kessig Prowler", SubType.WEREWOLF);
        assertSubtype("Kessig Prowler", SubType.HORROR);
        assertNotSubtype("Kessig Prowler", SubType.BORG);
        assertCounterCount(playerB, "Kessig Prowler", CounterType.P1P1, 0);
        assertPowerToughness(playerB, "Kessig Prowler", 2, 1);
    }
}
