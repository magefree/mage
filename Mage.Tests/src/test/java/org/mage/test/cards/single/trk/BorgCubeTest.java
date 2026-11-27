package org.mage.test.cards.single.trk;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class BorgCubeTest extends CardTestPlayerBase {

    @Test
    public void test_AffinityForArtifactCreatures_ReducesGenericCostOnly() {
        // Affinity for artifact creatures
        // Whenever this Spacecraft enters or attacks, assimilate a creature card exiled with it. If you can't, exile up to one target creature.
        addCard(Zone.HAND, playerA, "Borg Cube"); // {5}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2); // {2}{W}{B} total -> needs 3 artifact creatures, only 2 controlled
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // artifact creature (counts)
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // artifact creature (counts)
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // non-artifact creature (does not count)
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic"); // non-creature artifact (does not count)
        //
        addCard(Zone.HAND, playerA, "Island"); // 5th mana source after land drop -> {3}{W}{B} pays {5}{W}{B} - {2}

        // Before playing Island, 4 mana is not enough (cost is {3}{W}{B})
        checkPlayableAbility("not enough mana before land drop", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Borg Cube", false);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // choose 0 targets on ETB

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Borg Cube", 1);
    }

    @Test
    public void test_EntersExilesCreature_Station12GrantsFlyingAndBecomes66_AttackAssimilates() {
        // Whenever this Spacecraft enters or attacks, assimilate a creature card exiled with it. If you can't, exile up to one target creature.
        // Station
        // 12+ | Artifact creatures you control have flying. 6/6
        addCard(Zone.HAND, playerA, "Borg Cube"); // {5}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // artifact creature 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 2); // two 6/4 non-artifact creatures to station 12 charge counters
        //
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3 Giant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube");
        addTarget(playerA, "Hill Giant"); // nothing exiled yet -> exile Hill Giant
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkExileCount("Hill Giant exiled on ETB", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Hill Giant", 1);
        checkType("not a creature before Station 12", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube", CardType.CREATURE, false);
        checkAbility("Memnite has no flying before Station 12", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", FlyingAbility.class, false);

        // Station twice with the two 6/4 Craw Wurms -> 12 charge counters
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm"); // first 6 charge counters
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkType("not a creature at 6 charge counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube", CardType.CREATURE, false);
        checkAbility("Memnite has no flying at 6 charge counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", FlyingAbility.class, false);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm"); // second 6 charge counters -> 12 total
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkType("creature at Station 12+", 1, PhaseStep.BEGIN_COMBAT, playerA, "Borg Cube", CardType.CREATURE, true);
        checkPT("6/6 at Station 12+", 1, PhaseStep.BEGIN_COMBAT, playerA, "Borg Cube", 6, 6);
        checkAbility("Borg Cube has flying at Station 12+", 1, PhaseStep.BEGIN_COMBAT, playerA, "Borg Cube", FlyingAbility.class, true);
        checkAbility("Memnite gains flying at Station 12+", 1, PhaseStep.BEGIN_COMBAT, playerA, "Memnite", FlyingAbility.class, true);
        checkAbility("Craw Wurm does not gain flying", 1, PhaseStep.BEGIN_COMBAT, playerA, "Craw Wurm", FlyingAbility.class, false);

        // Turn 3: Borg Cube attacks -> assimilates exiled Hill Giant (gains flying as controlled artifact creature)
        attack(3, playerA, "Borg Cube", playerB);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // up to one target creature (not needed when assimilating)

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertExileCount(playerB, "Hill Giant", 0);
        // gain
        assertType("Hill Giant", CardType.ARTIFACT, true);
        assertType("Hill Giant", CardType.CREATURE, true);
        assertSubtype("Hill Giant", SubType.BORG);
        assertCounterCount(playerA, "Hill Giant", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Hill Giant", 3 + 1, 3 + 1);
        assertAbility(playerA, "Hill Giant", FlyingAbility.getInstance(), true);
        // lost
        assertNotSubtype("Hill Giant", SubType.GIANT);
        assertLife(playerB, 20 - 6);
    }

    @Test
    public void test_AttackWithExiledCreatureAndTargetChosen_AssimilatesAndDoesNotExileTarget() {
        // Whenever this Spacecraft enters or attacks, assimilate a creature card exiled with it. If you can't, exile up to one target creature.
        // Station
        // 12+ | Artifact creatures you control have flying. 6/6
        addCard(Zone.HAND, playerA, "Borg Cube"); // {5}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 2); // 6/4 x2 to station to 12
        //
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3 Giant
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2 Cat

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube");
        addTarget(playerA, "Hill Giant"); // ETB exiles Hill Giant
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Even though Silvercoat Lion is targeted on attack, assimilating Hill Giant succeeds so Silvercoat Lion is NOT exiled
        attack(3, playerA, "Borg Cube", playerB);
        addTarget(playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertExileCount(playerB, "Silvercoat Lion", 0);
    }

    @Test
    public void test_ExiledTokenCeasesToExist_NextAttackExilesAnotherCreature() {
        // Whenever this Spacecraft enters or attacks, assimilate a creature card exiled with it. If you can't, exile up to one target creature.
        // Station
        // 12+ | Artifact creatures you control have flying. 6/6
        addCard(Zone.HAND, playerA, "Borg Cube"); // {5}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 2); // 6/4 x2 to station to 12
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerB, "Raise the Alarm"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Raise the Alarm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube");
        addTarget(playerA, "Soldier Token"); // exile token -> ceases to exist in exile
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // No creature card is exiled with Borg Cube (the exiled token ceased to exist), so attack exiles Grizzly Bears
        attack(3, playerA, "Borg Cube", playerB);
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Soldier Token", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertExileCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_BlinkBeforeAttack_DoesNotAssimilatePreviouslyExiledCreature() {
        // Whenever this Spacecraft enters or attacks, assimilate a creature card exiled with it. If you can't, exile up to one target creature.
        // Station
        // 12+ | Artifact creatures you control have flying. 6/6
        addCard(Zone.HAND, playerA, "Borg Cube"); // {5}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 2); // 6/4 x2
        addCustomEffect_TargetBlink(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3 Giant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube");
        addTarget(playerA, "Hill Giant"); // first instance exiles Hill Giant
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Blink makes Borg Cube a new object; its linked attack ability cannot see Hill Giant exiled by the old object
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Borg Cube");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // second ETB chooses 0 targets
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Craw Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(3, playerA, "Borg Cube", playerB);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // choose 0 targets

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Hill Giant", 1);
        assertPermanentCount(playerA, "Hill Giant", 0);
    }

    @Test
    public void test_EntersWithZeroTargets_DoesNotExileAnything() {
        // Whenever this Spacecraft enters or attacks, assimilate a creature card exiled with it. If you can't, exile up to one target creature.
        addCard(Zone.HAND, playerA, "Borg Cube"); // {5}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Cube");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // decline optional target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Borg Cube", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertExileCount(playerB, 0);
    }
}
