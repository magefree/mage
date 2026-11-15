package org.mage.test.cards.single.ecl;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.f.FlameChainMauler Flame-Chain Mauler} {1}{R}
 * Creature — Elemental Warrior 2/2
 * {1}{R}: This creature gets +1/+0 and gains menace until end of turn.
 */
public class FlameChainMaulerTest extends CardTestPlayerBase {

    private static final String mauler = "Flame-Chain Mauler";

    @Test
    public void test_PumpAndMenaceEndAtEndOfTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, mauler);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{R}: {this} gets +1/+0");

        checkPT("pumped", 1, PhaseStep.END_TURN, playerA, mauler, 3, 2);
        checkAbility("has menace while pumped", 1, PhaseStep.END_TURN, playerA, mauler, MenaceAbility.class, true);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Both the +1/+0 and the menace grant are "until end of turn", so both
        // must be gone on the following turn.
        assertPowerToughness(playerA, mauler, 2, 2);
        assertAbility(playerA, mauler, new MenaceAbility(), false);
    }
}
