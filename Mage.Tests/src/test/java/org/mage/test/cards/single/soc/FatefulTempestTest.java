package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

public class FatefulTempestTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void testVotesMillDamageAndPlayExiledCardsUntilEndOfNextTurn() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.HAND, playerA, "Fateful Tempest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        addCard(Zone.LIBRARY, playerA, "Mountain"); // sixth card, drawn on turn 5
        addCard(Zone.LIBRARY, playerA, "Mountain"); // fifth card, exiled by Fateful Tempest
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // fourth card, exiled by Fateful Tempest
        addCard(Zone.LIBRARY, playerA, "Shock"); // third card, milled by Fateful Tempest
        addCard(Zone.LIBRARY, playerA, "Shock"); // second card, milled by Fateful Tempest
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // top card, drawn in opening hand

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fateful Tempest");
        setChoice(playerA, "Yes");
        setChoice(playerD, "Yes");
        setChoice(playerC, "No");
        setChoice(playerB, "No");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkGraveyardCount("two shocks milled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", 2);
        checkExileCount("one bolt exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkExileCount("one mountain exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain", 1);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, playerB);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 15);
        assertLife(playerC, 18);
        assertLife(playerD, 18);
        assertExileCount(playerA, "Lightning Bolt", 0);
        assertExileCount(playerA, "Mountain", 1);
    }
}
