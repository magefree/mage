package org.mage.test.cards.designations;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class StartYourEnginesTest extends CardTestPlayerBase {

    private static final String sarcophagus = "Walking Sarcophagus";

    private void assertSpeed(Player player, int speed) {
        Assert.assertEquals(player.getName() + " speed should be " + speed, speed, player.getSpeed());
    }

    @Test
    public void testRegular() {
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 0);
    }

    @Test
    public void testSpeed1() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.HAND, playerA, sarcophagus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sarcophagus);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 1);
        assertPowerToughness(playerA, sarcophagus, 2, 1);
    }

    private static final String goblet = "Onyx Goblet";

    @Test
    public void testSpeed2() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerA, goblet);
        addCard(Zone.HAND, playerA, sarcophagus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sarcophagus);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 2);
        assertPowerToughness(playerA, sarcophagus, 2, 1);
    }

    @Test
    public void testSpeed1OppTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerA, goblet);
        addCard(Zone.HAND, playerA, sarcophagus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sarcophagus);

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target", playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 1);
        assertPowerToughness(playerA, sarcophagus, 2, 1);
    }

    @Test
    public void testSpeed3() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerA, goblet);
        addCard(Zone.HAND, playerA, sarcophagus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sarcophagus);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target", playerB);

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 3);
        assertPowerToughness(playerA, sarcophagus, 2, 1);
    }

    @Test
    public void testSpeed4() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerA, goblet);
        addCard(Zone.HAND, playerA, sarcophagus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sarcophagus);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target", playerB);

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target", playerB);

        activateAbility(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Target", playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 4);
        assertPowerToughness(playerA, sarcophagus, 2 + 1, 1 + 2);
    }

    private static final String surveyor = "Loxodon Surveyor";

    @Test
    public void testSpeed4Graveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.GRAVEYARD, playerA, surveyor);

        runCode("Increase player speed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            player.initSpeed(game);
            player.increaseSpeed(game);
            player.increaseSpeed(game);
            player.increaseSpeed(game);
        });
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertSpeed(playerA, 4);
        assertGraveyardCount(playerA, surveyor, 0);
        assertExileCount(playerA, surveyor, 1);
    }
}
