package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
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
}
