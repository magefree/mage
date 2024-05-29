package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801, Susucr
 */
public class SerraParagonTest extends CardTestPlayerBase {

    private static final String paragon = "Serra Paragon";
    private static final String swamp = "Swamp";
    private static final String mox = "Mox Pearl";
    private static final String sinkhole = "Sinkhole";
    private static final String naturalize = "Naturalize";

    @Test
    public void testLandGainsAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Swamp");
        addCard(Zone.BATTLEFIELD, playerA, paragon);
        addCard(Zone.GRAVEYARD, playerA, swamp);
        addCard(Zone.HAND, playerA, sinkhole);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, swamp);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, sinkhole, swamp);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 2);
        assertExileCount(playerA, swamp, 1);
    }

    @Test
    public void testLandPlayedOnce() {
        addCard(Zone.BATTLEFIELD, playerA, paragon);
        addCard(Zone.GRAVEYARD, playerA, swamp, 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, swamp);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, swamp);

        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
            Assert.fail("Two lands were played");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: Play Swamp")) {
                Assert.fail("Should have had error about not being able to play land, but got:\n" + e.getMessage());
            }
        }
    }

    @Test
    public void testNonlandGainsAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, paragon);
        addCard(Zone.GRAVEYARD, playerA, mox);
        addCard(Zone.HAND, playerA, naturalize);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mox);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, naturalize, mox);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 2);
        assertPermanentCount(playerA, mox, 0);
        assertExileCount(playerA, mox, 1);
    }

    @Test
    public void testNonlandPlayedOnce() {
        addCard(Zone.BATTLEFIELD, playerA, paragon);
        addCard(Zone.GRAVEYARD, playerA, mox, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mox);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, mox);

        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
            Assert.fail("Two spells were cast from graveyard");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: Cast Mox Pearl")) {
                Assert.fail("Should have had error about not being able to cast spell, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * bug: Chromatic Star was not triggering the gained trigger.
     */
    @Test
    public void testChromaticStar() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, paragon);
        addCard(Zone.GRAVEYARD, playerA, "Chromatic Star");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chromatic Star", true);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}, Sacrifice");
        setChoice(playerA, "Red"); // mana added with Star
        setChoice(playerA, "When this permanent is put into a graveyard from the battlefield, exile it and you gain 2 life."); // stack triggers
        // Last trigger is: "When {this} is put into a graveyard from the battlefield, draw a card." from Chromatic Star

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertLife(playerA, 20 + 2);
        assertExileCount(playerA, "Chromatic Star", 1);
    }

    private static void checkEnergyCount(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getCountersCount(CounterType.ENERGY));
    }

    @Test
    public void testAetherworksMarvel() {
        setStrictChooseMode(true);

        // Whenever a permanent you control is put into a graveyard from the battlefield, you get {E}
        addCard(Zone.BATTLEFIELD, playerA, "Aetherworks Marvel");
        addCard(Zone.BATTLEFIELD, playerA, paragon);
        addCard(Zone.GRAVEYARD, playerA, "Chromatic Star");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Creeping Corrosion"); // Destroy all artifacts

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chromatic Star", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Creeping Corrosion", true);
        setChoice(playerA, "When this permanent is put into a graveyard from the battlefield, exile it and you gain 2 life."); // stack triggers
        setChoice(playerA, "Whenever a permanent you control is put into a graveyard from the battlefield, you get {E}", 2); // stack triggers
        // Last trigger is: "When {this} is put into a graveyard from the battlefield, draw a card." from Chromatic Star
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("energy counter is 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertExileCount(playerA, "Chromatic Star", 1);
        assertGraveyardCount(playerA, 2);
        assertLife(playerA, 20 + 2);
    }
}
