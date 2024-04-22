package org.mage.test.cards.single.dtk;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class QarsiDeceiverTest extends CardTestPlayerBase {

    /** Qarsi Deceiver {1}{U}
     * Creature — Naga Wizard
     * {T}: Add {C}. Spend this mana only to cast a face-down creature spell, pay a mana cost to turn a
     * manifested creature face up, or pay a morph cost. (A megamorph cost is a morph cost.)
     */
    private static final String qd = "Qarsi Deceiver";
    private static final String mystic = "Mystic of the Hidden Way";
    /** Mystic of the Hidden Way {4}{U}
     Creature — Human Monk
     Mystic of the Hidden Way can’t be blocked.
     Morph {2}{U} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
     */

    @Test
    public void testCostReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, qd);
        addCard(Zone.HAND, playerA, mystic);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, qd);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, mystic + " using Morph");

        checkPT("morph", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2);

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}: Turn");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, mystic, 3, 2);
    }
}
