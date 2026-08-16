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
    public void test_OwnAndGainAbilities() {
        // Exalted
        // Other creatures you control have exalted.
        addCard(Zone.BATTLEFIELD, playerB, "Sublime Archangel");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 2);

        attack(2, playerB, "Llanowar Elves");
        setChoice(playerB, "exalted", 4 - 1); // x4 triggers from x4 creatures with exalted

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        // 1/1 and +4/+4
        assertLife(playerA, 15);
    }

    @Test
    public void test_StackedAbilityCounters() {
        // When Emissary of Soulfire enters the battlefield, you get {E}{E}{E}.
        // Pay {E}{E}: Put an exalted counter on target creature you control. Activate only as a sorcery.
        addCard(Zone.HAND, playerA, "Emissary of Soulfire", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Snare Thopter"); // 3/2 natively
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);

        // prepare source
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emissary of Soulfire", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emissary of Soulfire", true);

        // counter 1
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}: ", "Snare Thopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // counter 2
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}: ", "Snare Thopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // counter 3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}: ", "Snare Thopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // make it triggers x3 due x3 counters
        attack(1, playerA, "Snare Thopter");
        setChoice(playerA, "exalted", 3 - 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // 3/2 and +3/+3
        assertPowerToughness(playerA, "Snare Thopter", 6, 5);
        assertLife(playerB, 14);
    }
}
