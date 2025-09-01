package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ViviOrnitierTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.ViviOrnitier Vivi Ornitier} {1}{U}{R}
     * Legendary Creature — Wizard
     * {0}: Add X mana in any combination of {U} and/or {R}, where X is Vivi Ornitier’s power. Activate only during your turn and only once each turn.
     * Whenever you cast a noncreature spell, put a +1/+1 counter on Vivi Ornitier and it deals 1 damage to each opponent.
     * 0/3
     */
    private static final String vivi = "Vivi Ornitier";

    /**
     * Creatures you control get +2/+2.
     */
    private static final String dictate = "Dictate of Heliod";

    private static final String bolt = "Lightning Bolt";
    private static final String incinerate = "Incinerate";

    @Test
    public void test_NoPower() {
        addCard(Zone.BATTLEFIELD, playerA, vivi, 1);
        addCard(Zone.HAND, playerA, bolt);

        checkPlayableAbility("bolt can not be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bolt, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_2Power() {
        addCard(Zone.BATTLEFIELD, playerA, vivi, 1);
        addCard(Zone.BATTLEFIELD, playerA, dictate, 1);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.HAND, playerA, incinerate);

        checkPlayableAbility("1: bolt can be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bolt, true);
        checkPlayableAbility("1: incinerate can be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + incinerate, true);

        setChoice(playerA, "X=0"); // choose {U} color distribution for vivi on 2 power
        setChoice(playerA, "X=2"); // choose {R} color distribution for vivi on 2 power
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, incinerate, playerB);

        checkPlayableAbility("2: bolt can not be cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bolt, false);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20 - 3 - 1);
        assertPowerToughness(playerA, vivi, 3, 6);
    }
}
