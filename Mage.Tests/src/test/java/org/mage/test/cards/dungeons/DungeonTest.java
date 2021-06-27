package org.mage.test.cards.dungeons;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        Assert.assertNotNull("Dungeon is not null", dungeon);
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

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 4, 3);
        assertPermanentCount(playerA, "Goblin", 0);
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
        setChoice(playerA, "Yes"); // Goblin Lair

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 4, 3);
        assertPermanentCount(playerA, "Goblin", 1);
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
        setChoice(playerA, "Yes"); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, "No"); // Dark Pool

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 4, 3);
        assertPermanentCount(playerA, "Goblin", 1);
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
        setChoice(playerA, "Yes"); // Goblin Lair
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, "No"); // Dark Pool
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 4, 3);
        assertPermanentCount(playerA, "Goblin", 1);
        assertDungeonRoom(null, null);
        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, 1);
    }
}
