package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MurderousRiderTest extends CardTestPlayerBase {
    @Test
    public void testAlreadyMoved() {
        // When Murderous Rider dies, put it on the bottom of its owner's library.
        addCard(Zone.BATTLEFIELD, playerA, "Murderous Rider");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // When target creature dies this turn, return that card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Graceful Reprieve");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        // Destroy target creature
        addCard(Zone.HAND, playerB, "Murder");

        castSpell(1, PhaseStep.UPKEEP, playerA,"Graceful Reprieve");
        addTarget(playerA, "Murderous Rider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder");
        addTarget(playerB, "Murderous Rider");
        setChoice(playerA, "When {this} dies");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Murderous Rider", 1);
    }

    @Test
    public void testStandard() {
        // When Murderous Rider dies, put it on the bottom of its owner's library.
        addCard(Zone.BATTLEFIELD, playerA, "Murderous Rider");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        // Destroy target creature
        addCard(Zone.HAND, playerB, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder");
        addTarget(playerB, "Murderous Rider");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Murderous Rider", 0);
        assertGraveyardCount(playerA, "Murderous Rider", 0);
        assertLibraryCount(playerA, "Murderous Rider", 1);
    }
}
