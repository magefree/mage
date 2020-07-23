package org.mage.test.cards.single.bok;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JRHerlehy  Created on 12/27/17.
 */
public class PatronOfTheNezumiTest extends CardTestPlayerBase {

    // Rat offering
    // Whenever a permanent is put into an opponent's graveyard, that player loses 1 life.

    private final String patron = "Patron of the Nezumi";
    private final String thopter = "Ornithopter";
    private final String elspeth = "Elspeth, Sun's Champion";
    private final String elspethAbility = "+1: Create three";
    private final String elesh = "Elesh Norn, Grand Cenobite";
    private final String sinkhole = "Sinkhole";

    @Test
    public void testEffectWithTokens() {
        this.setupTest();

        this.addCard(Zone.BATTLEFIELD, playerB, elspeth);

        this.activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, elspethAbility);
        this.activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerB, elspethAbility);

        this.castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, elesh);

        this.setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        this.execute();

        this.assertPermanentCount(playerB, 1);
        this.assertLife(playerA, 20);
        this.assertLife(playerB, 14);
    }

    @Test
    public void testEffectWithRIP() {
        this.setupTest();

        this.addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace");
        this.addCard(Zone.BATTLEFIELD, playerB, thopter, 5);

        this.castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, elesh);

        this.setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        this.execute();

        this.assertExileCount(playerB, 5);
        this.assertLife(playerA, 20);
        this.assertLife(playerB, 20);
    }

    @Test
    public void testEffectWithCreatures() {
        this.setupTest();

        this.addCard(Zone.BATTLEFIELD, playerB, thopter, 5);

        this.castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, elesh);

        this.setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        this.execute();

        this.assertPermanentCount(playerB, 0);
        this.assertLife(playerA, 20);
        this.assertLife(playerB, 15);
    }

    private void setupTest() {
        this.addCard(Zone.BATTLEFIELD, playerA, patron);
        this.addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        this.addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        this.addCard(Zone.HAND, playerA, sinkhole);
        this.addCard(Zone.HAND, playerA, elesh);
    }
}
