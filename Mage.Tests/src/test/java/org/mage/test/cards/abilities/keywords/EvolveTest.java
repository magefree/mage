package org.mage.test.cards.abilities.keywords;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */


public class EvolveTest extends CardTestPlayerBase {

    @Test
    public void testCreatureComesIntoPlay() {

        // Cloudfin Raptor gets one +1/+1 because Mindeye Drake comes into play

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Cloudfin Raptor", 1);

        addCard(Constants.Zone.HAND, playerA, "Mindeye Drake");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mindeye Drake");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Cloudfin Raptor", 1);
        assertPermanentCount(playerA, "Mindeye Drake", 1);

        assertPowerToughness(playerA, "Cloudfin Raptor", 1, 2);
        assertPowerToughness(playerA, "Mindeye Drake", 2, 5);
    }

    @Test
    public void testCreatureComesIntoPlayNoCounter() {

        // Experiment One gets no counter because Kird Ape is 1/1 with no Forest in play

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Experiment One", 1);

        addCard(Constants.Zone.HAND, playerA, "Kird Ape");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Kird Ape");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 1);
        assertPermanentCount(playerA, "Kird Ape", 1);

        assertPowerToughness(playerA, "Experiment One", 1, 1);
        assertPowerToughness(playerA, "Kird Ape", 1, 1);
    }

    @Test
    public void testCreatureComesStrongerIntoPlayCounter() {

        // Experiment One gets a counter because Kird Ape is 2/2 with a Forest in play

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Experiment One", 1);

        addCard(Constants.Zone.HAND, playerA, "Kird Ape");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Kird Ape");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 1);
        assertPermanentCount(playerA, "Kird Ape", 1);

        assertPowerToughness(playerA, "Experiment One", 2, 2);
        assertPowerToughness(playerA, "Kird Ape", 2, 3);
    }

    @Test
    public void testEvolveWithMasterBiomance() {

        // Experiment One gets a counter because Kird Ape is 2/2 with a Forest in play

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Experiment One", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);

        addCard(Constants.Zone.HAND, playerA, "Experiment One");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Experiment One");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 2);
        assertPermanentCount(playerA, "Master Biomancer", 1);

        // the first Experiment One get one counter from the second Experiment one that comes into play with two +1/+1 counters
        assertPowerToughness(playerA, "Experiment One", 2, 2);
        // the casted Experiment One got two counters from Master Biomancer
        assertPowerToughness(playerA, "Experiment One", 3, 3);

    }
}
