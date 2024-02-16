package org.mage.test.cards.single.ltr;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class StormOfSarumanTest extends CardTestPlayerBase {

    static final String storm = "Storm of Saruman";

    @Test
    // Author: alexander-novo
    // A test for basic functionality of the card - makes sure it copies cards and makes them nonlegendary
    public void testCopiesNonLegendary() {
        String hound = "Isamaru, Hound of Konda";
        String bolt = "Lightning Bolt";

        // Bolt will be our first spell - to make sure it doesn't trigger
        // Isamaru will be our second spell - to make sure we get two, since it's legendary
        addCard(Zone.HAND, playerA, bolt, 1);
        addCard(Zone.HAND, playerA, hound, 1);
        addCard(Zone.BATTLEFIELD, playerA, storm, 1);

        // The mana needed to cast those spells
        addCard(Zone.BATTLEFIELD, playerA, "mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        checkStackObject("Bolt check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Whenever you cast your second spell each turn", 0);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hound);
        checkStackObject("Hound check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Whenever you cast your second spell each turn", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
        assertPermanentCount(playerA, hound, 2);
    }
}
