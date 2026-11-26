package org.mage.test.cards.single.tmt;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RaphaelMostAttitudeTest extends CardTestPlayerBase {

    private void prepareExiledCards() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Raphael, Most Attitude");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Ornithopter", 2);
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.LIBRARY, playerA, "Ornithopter", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter");
        setChoice(playerA, true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter");
        setChoice(playerA, true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        attack(1, playerA, "Raphael, Most Attitude");
    }

    @Test
    public void testOnlyOneCardPlayable() {
        prepareExiledCards();

        checkPlayableAbility("one card playable", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ornithopter", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ornithopter", true);
        setChoice(playerA, false);
        checkPlayableAbility("no second card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ornithopter", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Raphael, Most Attitude", 1);
        assertPermanentCount(playerA, "Ornithopter", 3);
        assertExileCount(playerA, "Ornithopter", 1);
    }

    @Test
    public void testBlinkRemovesPermission() {
        prepareExiledCards();

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Raphael, Most Attitude", true);

        checkPlayableAbility("no card playable", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ornithopter", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Raphael, Most Attitude", 1);
        assertPermanentCount(playerA, "Ornithopter", 2);
        assertExileCount(playerA, "Ornithopter", 2);
    }
}
