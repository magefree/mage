package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class ExaltedTest extends CardTestPlayerBase {

    /**
     * Tests multiple exalted
     */
    @Test
    public void testBeingBlocked() {
        addCard(Zone.BATTLEFIELD, playerB, "Sublime Archangel");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 2);

        attack(2, playerB, "Llanowar Elves");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        // 1/1 and +4/+4
        assertLife(playerA, 15);
    }

    @Test
    public void testAbilityCounters() {
        addCard(Zone.HAND, playerA, "Emissary of Soulfire", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Snare Thopter"); // 3/2 natively
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emissary of Soulfire", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emissary of Soulfire", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}: ", "Snare Thopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}: ", "Snare Thopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}: ", "Snare Thopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        attack(1, playerA, "Snare Thopter");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // 3/2 and +3/+3
        assertPowerToughness(playerA, "Snare Thopter", 6, 5);
        assertLife(playerB, 14);
    }
}
