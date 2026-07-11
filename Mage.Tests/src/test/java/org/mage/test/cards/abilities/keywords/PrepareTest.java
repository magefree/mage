package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
