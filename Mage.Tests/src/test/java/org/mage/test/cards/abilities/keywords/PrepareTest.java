package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class PrepareTest extends CardTestPlayerBase {

    private static final String CREATURE = "Goblin Glasswright";
    private static final String PREPARE_SPELL = "Craft with Pride";
    private static final String TOMEKEEPER = "Biblioplex Tomekeeper";

    @Test
    public void tomekeeperCanRePrepareCreatureWithPrepareSpell() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.HAND, playerA, TOMEKEEPER);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft with Pride");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("prepare spell consumed prepared designation", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, (info, player, game) -> {
                    Permanent permanent = game.getPermanent(getPermanent(CREATURE, playerA).getId());
                    Assert.assertNotNull(permanent);
                    Assert.assertFalse(permanent.isPrepared());
                    Assert.assertEquals(0, game.getExile().getAllCards(game).size());
                });
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TOMEKEEPER);
        setModeChoice(playerA, "1"); // Target creature becomes prepared.
        addTarget(playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("prepare copy is castable", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + PREPARE_SPELL, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
        Permanent permanent = getPermanent(CREATURE, playerA);
        Assert.assertNotNull(permanent);
        Assert.assertTrue(permanent.isPrepared());
    }

    @Test
    public void prepareSpellCannotBeCastFromHand() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        checkPlayableAbility("base is castable", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast Goblin Glasswright", true);
        checkPlayableAbility("prepare spell isn't castable", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast Craft with Pride", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void entersPreparedAndCastsPersistentExileCopy() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Glasswright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("prepare copy is castable", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast Craft with Pride", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft with Pride");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Glasswright", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
        assertExileCount(playerA, 0);
    }

    @Test
    public void sorceryPrepareSpellCannotBeCastAtInstantSpeed() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        checkPlayableAbility("sorcery prepare spell is not castable on an opponent's turn", 2,
                PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + PREPARE_SPELL, false);
        checkPlayableAbility("sorcery prepare spell remains castable during its controller's main phase", 3,
                PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + PREPARE_SPELL, true);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void costIncreaseAppliesToPrepareSpell() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        checkPlayableAbility("Thalia increases the noncreature prepare spell by one", 3,
                PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + PREPARE_SPELL, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, PREPARE_SPELL);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 1);
        assertTappedCount("Mountain", true, 2);
    }

    @Test
    public void costReductionAppliesToPrepareSpell() {
        addCard(Zone.HAND, playerA, "Quill-Blade Laureate");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCustomEffect_SpellCostModification(playerA, -1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Quill-Blade Laureate");
        checkPlayableAbility("reducer removes the generic mana from the prepare spell", 3,
                PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Twofold Intent", true);
        addTarget(playerA, "Quill-Blade Laureate");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Twofold Intent");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Plains", true, 1);
    }

    @Test
    public void preparedCopyCannotCastBaseCreature() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("base creature is not castable from prepare copy", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + CREATURE, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void prepareCopySurvivesStateBasedActions() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        checkPlayableAbility("copy remains through later SBA checks", 3, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + PREPARE_SPELL, true);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, CREATURE, 1);
    }

    @Test
    public void prepareCopyCeasesToExistWhenPermanentLeavesBattlefield() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Murder");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder", CREATURE);
        checkPlayableAbility("prepare copy is removed with its permanent", 2, PhaseStep.POSTCOMBAT_MAIN,
                playerA, "Cast " + PREPARE_SPELL, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, CREATURE, 0);
        assertExileCount(playerA, 0);
    }

    @Test
    public void cannotBecomePreparedTwiceAtTheSameTime() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("try to prepare again", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent permanent = game.getBattlefield().getAllActivePermanents(playerA.getId()).stream()
                    .filter(p -> p.getName().equals(CREATURE)).findFirst().orElse(null);
            Assert.assertNotNull(permanent);
            Assert.assertTrue(permanent.isPrepared());
            permanent.setPrepared(true, game);
        });

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, CREATURE, 1);
    }

    @Test
    public void becomingUnpreparedRemovesAssociatedCopy() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("make unprepared", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent permanent = game.getBattlefield().getAllActivePermanents(playerA.getId()).stream()
                    .filter(p -> p.getName().equals(CREATURE)).findFirst().orElse(null);
            Assert.assertNotNull(permanent);
            permanent.setPrepared(false, game);
            Assert.assertFalse(permanent.isPrepared());
        });
        checkPlayableAbility("removed copy cannot be cast", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + PREPARE_SPELL, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, 0);
    }

    @Test
    public void currentControllerCanCastPrepareCopy() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", CREATURE);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("current controller can cast copy", 2, PhaseStep.PRECOMBAT_MAIN,
                playerB, "Cast " + PREPARE_SPELL, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, PREPARE_SPELL);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Treasure Token", 1);
        assertExileCount(playerA, 0);
    }

    @Test
    public void prepareSpellIsIndependentOfPermanentOnceCast() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Murder");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PREPARE_SPELL);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder", CREATURE);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, CREATURE, 0);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

    @Test
    public void preparedCopyDoesNotTriggerEffectsThatRequireCastingACard() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Eye of the Storm");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PREPARE_SPELL);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 1);
        assertExileCount(playerA, 0);
    }

    @Test
    public void preparedCopyCountsAsCastFromExile() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "The Thirteenth Doctor");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PREPARE_SPELL);
        addTarget(playerA, "The Thirteenth Doctor");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("The Thirteenth Doctor", CounterType.P1P1, 1);
    }

    @Test
    public void alreadyPreparedPermanentCanStillPayPrepareAbilityCost() {
        addCard(Zone.HAND, playerA, "Lluwen, Exchange Student");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lluwen, Exchange Student");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Exile a creature card from your graveyard");
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Exile a creature card from your graveyard");
        setChoice(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        assertExileCount(playerA, "Grizzly Bears", 2);
        assertExileCount(playerA, "Lluwen, Exchange Student", 1);
    }

    @Test
    public void losingAbilitiesDoesNotRemovePreparedOrItsExiledCopy() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Skycoach Waypoint");
        addCard(Zone.HAND, playerB, "Darksteel Mutation");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Darksteel Mutation", CREATURE);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("ability loss leaves prepared copy castable", 3, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + PREPARE_SPELL, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, PREPARE_SPELL);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{3}, {T}: Target creature becomes prepared", CREATURE);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 1);
        assertExileCount(playerA, CREATURE, 1);
        Permanent permanent = getPermanent(CREATURE, playerA);
        Assert.assertNotNull(permanent);
        Assert.assertTrue(permanent.isPrepared());
    }

    @Test
    public void creatingPreparedCopyDoesNotTriggerMagecraftOrTwinningStaff() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Storm-Kiln Artist");
        addCard(Zone.BATTLEFIELD, playerA, "Twinning Staff");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("creating exile copy did not trigger magecraft", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Treasure Token", 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PREPARE_SPELL);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // One from Craft with Pride and one from casting it with Storm-Kiln Artist.
        // Twinning Staff did not create an additional prepared copy or spell copy.
        assertPermanentCount(playerA, "Treasure Token", 2);
        assertExileCount(playerA, 0);
    }

    @Test
    public void gainingPrepareCharacteristicsLaterDoesNotRetroactivelyPrepareCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Emeritus of Woe");
        addCard(Zone.BATTLEFIELD, playerA, "Skycoach Waypoint");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Nanogene Conversion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{3}, {T}: Target creature becomes prepared", "Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nanogene Conversion", "Emeritus of Woe");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Emeritus of Woe", 2);
        // Only the original Emeritus supplied a prepared copy. The Llanowar
        // Elves did not become prepared retroactively after copying it.
        assertExileCount(playerA, "Emeritus of Woe", 1);
    }

    @Test
    public void changingPreparedPermanentCopyDoesNotChangePreparedSpellCopy() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.HAND, playerA, "Mirrorform");
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 8);
        addCard(Zone.BATTLEFIELD, playerB, "Emeritus of Woe");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mirrorform", "Emeritus of Woe");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, CREATURE, 1);
        assertExileCount(playerA, "Emeritus of Woe", 0);
    }

    @Test
    public void phasingPreparedCopyRecreatesSpellFromCurrentCharacteristics() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.HAND, playerA, "Infinite Reflection");
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 8);
        addCard(Zone.BATTLEFIELD, playerB, "Dirgur Focusmage");
        addCard(Zone.BATTLEFIELD, playerB, "Teferi, Master of Time");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Infinite Reflection", "Dirgur Focusmage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("copying does not replace the associated spell", 1,
                PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE, 1);
        checkExileCount("copying does not create the new associated spell", 1,
                PhaseStep.PRECOMBAT_MAIN, playerA, "Dirgur Focusmage", 0);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB,
                "-3: Target creature you don't control phases out.", "Dirgur Focusmage");
        checkExileCount("phasing out removes the original associated spell", 2,
                PhaseStep.POSTCOMBAT_MAIN, playerA, CREATURE, 0);
        checkExileCount("phasing out does not create the copied associated spell", 2,
                PhaseStep.POSTCOMBAT_MAIN, playerA, "Dirgur Focusmage", 0);
        checkExileCount("phasing in uses the copied Preparation characteristics", 3,
                PhaseStep.PRECOMBAT_MAIN, playerA, "Dirgur Focusmage", 1);
        checkExileCount("the original associated spell is not recreated", 3,
                PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE, 0);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void counteredPrepareSpellStillConsumesPreparedDesignation() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, PREPARE_SPELL);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", PREPARE_SPELL);
        runCode("prepared designation was consumed on cast", 3, PhaseStep.PRECOMBAT_MAIN,
                playerA, (info, player, game) -> {
                    Permanent permanent = game.getBattlefield().getAllActivePermanents(playerA.getId()).stream()
                            .filter(p -> p.getName().equals(CREATURE)).findFirst().orElse(null);
                    Assert.assertNotNull(permanent);
                    Assert.assertFalse(permanent.isPrepared());
                });

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 0);
        assertExileCount(playerA, 0);
    }

    @Test
    public void tomekeeperCanUnprepareCreatureWithPrepareSpell() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.HAND, playerA, TOMEKEEPER);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, TOMEKEEPER);
        setModeChoice(playerA, "2"); // Target creature becomes unprepared.
        addTarget(playerA, CREATURE);
        checkPlayableAbility("unprepared creature has no prepare copy", 3, PhaseStep.POSTCOMBAT_MAIN,
                playerA, "Cast " + PREPARE_SPELL, false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0);
        Permanent permanent = getPermanent(CREATURE, playerA);
        Assert.assertNotNull(permanent);
        Assert.assertFalse(permanent.isPrepared());
    }

    @Test
    public void tomekeeperCannotPrepareCreatureWithoutPrepareSpell() {
        addCard(Zone.HAND, playerA, TOMEKEEPER);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TOMEKEEPER);
        setModeChoice(playerA, "1"); // Target creature becomes prepared.
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent permanent = getPermanent("Grizzly Bears", playerA);
        Assert.assertNotNull(permanent);
        Assert.assertFalse(permanent.isPrepared());
        assertExileCount(playerA, 0);
    }

    @Test
    public void canChoosePrepareSpellAsCardName() {
        addCard(Zone.HAND, playerA, CREATURE);
        addCard(Zone.HAND, playerA, "Meddling Mage");
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meddling Mage");
        setChoice(playerA, PREPARE_SPELL);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CREATURE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("chosen prepare spell name is prohibited", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "Cast " + PREPARE_SPELL, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Meddling Mage", 1);
        assertPermanentCount(playerA, CREATURE, 1);
        assertExileCount(playerA, CREATURE, 1);
    }
}
