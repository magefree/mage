package org.mage.test.cards.dungeons;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.command.Dungeon;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public class DungeonTest extends CardTestPlayerBase {

    private static final String TOMB_OF_ANNIHILATION = "Tomb of Annihilation";
    private static final String LOST_MINE_OF_PHANDELVER = "Lost Mine of Phandelver";
    private static final String DUNGEON_OF_THE_MAD_MAGE = "Dungeon of the Mad Mage";
    private static final String FLAMESPEAKER_ADEPT = "Flamespeaker Adept";
    private static final String GLOOM_STALKER = "Gloom Stalker";
    private static final String DUNGEON_CRAWLER = "Dungeon Crawler";
    private static final String SILVERCOAT_LION = "Silvercoat Lion";
    private static final String HAMA_PASHAR_RUIN_SEEKER = "Hama Pashar, Ruin Seeker";

    private void makeTester() {
        makeTester(playerA);
    }

    private void makeTester(TestPlayer player) {
        addCustomCardWithAbility(
                "tester", player,
                new SimpleActivatedAbility(new VentureIntoTheDungeonEffect(), new GenericManaCost(0))
        );
    }

    private Stream<Dungeon> makeStream(TestPlayer player) {
        return currentGame
                .getState()
                .getCommand()
                .stream()
                .filter(Dungeon.class::isInstance)
                .map(Dungeon.class::cast)
                .filter(dungeon -> dungeon.isControlledBy(player.getId()));
    }

    private Dungeon getCurrentDungeon(TestPlayer player) {
        Assert.assertTrue(
                "Players should not control more than one dungeon",
                makeStream(player).count() < 2
        );
        return makeStream(player).findFirst().orElse(null);
    }

    private void assertDungeonRoom(String dungeonName, String roomName) {
        assertDungeonRoom(playerA, dungeonName, roomName);
    }

    private void assertDungeonRoom(TestPlayer player, String dungeonName, String roomName) {
        Dungeon dungeon = getCurrentDungeon(player);
        if (dungeonName == null) {
            Assert.assertNull("There should be no dungeon", dungeon);
            return;
        }
        Assert.assertNotNull("Dungeon should not be null", dungeon);
        Assert.assertEquals("Dungeon should be " + dungeonName, dungeonName, dungeon.getName());
        Assert.assertEquals(
                "Current room is " + roomName,
                roomName, dungeon.getCurrentRoom().getName()
        );
    }

    @Test
    public void test__LostMineOfPhandelver_room1() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 2 + 2, 3);
        assertPermanentCount(playerA, "Goblin Token", 0);
        assertDungeonRoom(LOST_MINE_OF_PHANDELVER, "Cave Entrance");
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test__LostMineOfPhandelver_room2() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 2 + 2, 3);
        assertPermanentCount(playerA, "Goblin Token", 1);
        assertDungeonRoom(LOST_MINE_OF_PHANDELVER, "Goblin Lair");
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test__LostMineOfPhandelver_room3() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 2 + 2, 3);
        assertPermanentCount(playerA, "Goblin Token", 1);
        assertDungeonRoom(LOST_MINE_OF_PHANDELVER, "Dark Pool");
        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test__LostMineOfPhandelver_room4() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 2 + 2, 3);
        assertPermanentCount(playerA, "Goblin Token", 1);
        assertDungeonRoom(null, null);
        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test__LostMineOfPhandelver_multipleTurns() {
        makeTester();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{0}:");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Goblin Token", 1);
        assertDungeonRoom(null, null);
        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test__LostMineOfPhandelver_rollback() {
        makeTester();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{0}:");

        rollbackTurns(2, PhaseStep.END_TURN, playerA, 0);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Goblin Token", 1);
        assertDungeonRoom(LOST_MINE_OF_PHANDELVER, "Goblin Lair");
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test__LostMineOfPhandelver_rollbackDifferentChoice() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, SILVERCOAT_LION);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{0}:");

        rollbackTurns(2, PhaseStep.END_TURN, playerA, 0);

        rollbackAfterActionsStart();
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Storeroom
        addTarget(playerA, SILVERCOAT_LION);
        rollbackAfterActionsEnd();

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, SILVERCOAT_LION, 3, 3);
        assertCounterCount(playerA, SILVERCOAT_LION, CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Goblin Token", 1);
        assertDungeonRoom(LOST_MINE_OF_PHANDELVER, "Storeroom");
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test__Dungeon_multiplePlayers() {
        makeTester(playerA);
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);
        makeTester(playerB);
        addCard(Zone.BATTLEFIELD, playerB, FLAMESPEAKER_ADEPT);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{0}:");
        setChoice(playerB, DUNGEON_OF_THE_MAD_MAGE);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{0}:");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{0}:");
        setChoice(playerB, true); // Goblin Bazaar
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{0}:");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 2 + 2, 3);
        assertPowerToughness(playerB, FLAMESPEAKER_ADEPT, 2 + 2 + 2, 3);
        assertPermanentCount(playerA, "Goblin Token", 1);
        assertPermanentCount(playerB, "Treasure Token", 1);
        assertDungeonRoom(playerA, LOST_MINE_OF_PHANDELVER, "Dark Pool");
        assertDungeonRoom(playerB, DUNGEON_OF_THE_MAD_MAGE, "Lost Level");
        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1 + 1);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
    }

    @Test
    public void test__CompletedDungeonCondition_true() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, GLOOM_STALKER);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertAbility(playerA, GLOOM_STALKER, DoubleStrikeAbility.getInstance(), true);
        assertDungeonRoom(null, null);
    }

    @Test
    public void test__CompletedDungeonCondition_false() {
        addCard(Zone.BATTLEFIELD, playerA, GLOOM_STALKER);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertAbility(playerA, GLOOM_STALKER, DoubleStrikeAbility.getInstance(), false);
        assertDungeonRoom(null, null);
    }

    @Test
    public void test__CompletedDungeonCondition_falseThenTrue() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, GLOOM_STALKER);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();
        assertAbility(playerA, GLOOM_STALKER, DoubleStrikeAbility.getInstance(), false);
        assertDungeonRoom(null, null);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertAbility(playerA, GLOOM_STALKER, DoubleStrikeAbility.getInstance(), true);
        assertDungeonRoom(null, null);
    }

    @Test
    public void test__CompletedDungeonTriggeredAbility() {
        makeTester();
        addCard(Zone.GRAVEYARD, playerA, DUNGEON_CRAWLER);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // return Dungeon Crawler

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, DUNGEON_CRAWLER, 1);
        assertDungeonRoom(null, null);
    }

    @Test
    public void test__HamaPasharRuinSeeker_DoubleController() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);
        addCard(Zone.BATTLEFIELD, playerA, HAMA_PASHAR_RUIN_SEEKER);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 2 + 2 + 2, 3);
        assertPermanentCount(playerA, "Goblin Token", 2);
        assertDungeonRoom(LOST_MINE_OF_PHANDELVER, "Dark Pool");
        assertLife(playerA, 20 + 1 + 1);
        assertLife(playerB, 20 - 1 - 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test__HamaPasharRuinSeeker_DontDoubleOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);
        addCard(Zone.BATTLEFIELD, playerB, HAMA_PASHAR_RUIN_SEEKER);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, true); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, false); // Dark Pool

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 2 + 2, 3);
        assertPermanentCount(playerA, "Goblin Token", 1);
        assertDungeonRoom(LOST_MINE_OF_PHANDELVER, "Dark Pool");
        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, 0);
    }
}
