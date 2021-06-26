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

    private Dungeon getDungeon(String name) {
        return currentGame
                .getState()
                .getCommand()
                .stream()
                .filter(Dungeon.class::isInstance)
                .map(Dungeon.class::cast)
                .filter(commandObject -> commandObject.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Test
    public void testDungeon() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, FLAMESPEAKER_ADEPT);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}:");
        setChoice(playerA, LOST_MINE_OF_PHANDELVER);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, FLAMESPEAKER_ADEPT, 4, 3);
        Dungeon dungeon = getDungeon(LOST_MINE_OF_PHANDELVER);
        Assert.assertNotNull("Dungeon is not null", dungeon);
        Assert.assertEquals(
                "Dungeon is on its first room",
                "Cave Entrance", dungeon.getCurrentRoom().getName()
        );
    }
}
