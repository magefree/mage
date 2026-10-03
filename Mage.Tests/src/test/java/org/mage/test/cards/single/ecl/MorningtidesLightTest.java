package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MorningtidesLight Morningtide's Light} {3}{W}
 * Sorcery
 * Exile any number of target creatures. At the beginning of the next end step,
 * return those cards to the battlefield tapped under their owners' control.
 * Until your next turn, prevent all damage that would be dealt to you.
 * Exile Morningtide's Light.
 */
public class MorningtidesLightTest extends CardTestPlayerBase {

    private static final String light = "Morningtide's Light";
    private static final String bears = "Grizzly Bears";
    private static final String giant = "Hill Giant";

    @Test
    public void test_ReturnsTappedUnderOwners() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, light);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerB, giant);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, light, bears + "^" + giant);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, bears, 1);
        assertPermanentCount(playerB, giant, 1);
        assertTapped(bears, true);
        assertTapped(giant, true);
        assertLife(playerA, 20);
        assertExileCount(playerA, light, 1);
    }
}
