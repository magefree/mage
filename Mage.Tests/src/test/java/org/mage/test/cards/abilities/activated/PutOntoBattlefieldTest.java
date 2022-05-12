package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class PutOntoBattlefieldTest extends CardTestPlayerBase {

    /**
     * Tests to put a token onto the battlefield
     */
    @Test
    public void testOozeFlux() {
        // Enchantment
        // {1}{G}, Remove one or more +1/+1 counters from among creatures you control: Put an X/X green Ooze creature token onto the battlefield, where X is the number of +1/+1 counters removed this way.
        addCard(Zone.BATTLEFIELD, playerA, "Ooze Flux");
        // Trample
        // Kalonian Hydra enters the battlefield with four +1/+1 counters on it.
        // Whenever Kalonian Hydra attacks, double the number of +1/+1 counters on each creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Hydra");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{G},");
        setChoice(playerA, "X=2"); // Remove how many
        setChoice(playerA,"Kalonian Hydra");
        setChoice(playerA, "X=2"); // Remove from Hydra

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Kalonian Hydra", 2, 2);
        assertPermanentCount(playerA, "Ooze Token", 1);
        assertPowerToughness(playerA, "Ooze Token", 2, 2);

    }

    
}